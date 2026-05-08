use anyhow::{Context, Result};
use ort::{session::Session, value::Tensor};
use std::path::Path;
use tokenizers::Tokenizer;

pub struct EmbeddingModel {
    session: Session,
    tokenizer: Tokenizer,
}

impl EmbeddingModel {
    pub fn new(model_path: &Path, tokenizer_path: &Path) -> Result<Self> {
        if !model_path.exists() {
            return Err(anyhow::anyhow!(
                "ONNX model not found: {:?}\nDownload all-MiniLM-L6-v2 from HuggingFace (onnx/model.onnx)",
                model_path
            ));
        }
        if !tokenizer_path.exists() {
            return Err(anyhow::anyhow!(
                "Tokenizer not found: {:?}\nDownload tokenizer.json from HuggingFace",
                tokenizer_path
            ));
        }

        let session = Session::builder()
            .context("Failed to create ONNX session builder")?
            .commit_from_file(model_path)
            .context("Failed to load ONNX embedding model")?;

        let tokenizer = Tokenizer::from_file(tokenizer_path)
            .map_err(|e| anyhow::anyhow!("Failed to load tokenizer: {}", e))?;

        tracing::info!("Embedding model loaded: {:?}", model_path);
        Ok(Self { session, tokenizer })
    }

    /// Embed text into a 384-dim L2-normalised vector.
    pub fn embed(&mut self, text: &str) -> Result<Vec<f32>> {
        let encoding = self
            .tokenizer
            .encode(text, true)
            .map_err(|e| anyhow::anyhow!("Tokenization failed: {}", e))?;

        let seq_len = encoding.get_ids().len().min(128);

        let input_ids: Vec<i64> = encoding.get_ids()[..seq_len].iter().map(|&x| x as i64).collect();
        let attention_mask: Vec<i64> =
            encoding.get_attention_mask()[..seq_len].iter().map(|&x| x as i64).collect();
        let token_type_ids: Vec<i64> =
            encoding.get_type_ids()[..seq_len].iter().map(|&x| x as i64).collect();

        let input_ids_t = Tensor::from_array(([1usize, seq_len], input_ids))
            .context("Failed to create input_ids tensor")?;
        let attention_mask_t = Tensor::from_array(([1usize, seq_len], attention_mask))
            .context("Failed to create attention_mask tensor")?;
        let token_type_ids_t = Tensor::from_array(([1usize, seq_len], token_type_ids))
            .context("Failed to create token_type_ids tensor")?;

        let outputs = self.session.run(ort::inputs! {
            "input_ids"      => input_ids_t,
            "attention_mask" => attention_mask_t,
            "token_type_ids" => token_type_ids_t,
        })?;

        // last_hidden_state: [1, seq_len, 384] flat row-major — mean-pool over seq dimension
        let (hidden_shape, hidden_data) = outputs["last_hidden_state"]
            .try_extract_tensor::<f32>()
            .context("Failed to extract last_hidden_state")?;

        let seq = hidden_shape[1] as usize;
        let dims = 384usize;

        let mean: Vec<f32> = (0..dims)
            .map(|i| {
                let sum: f32 = (0..seq).map(|j| hidden_data[j * dims + i]).sum();
                sum / seq as f32
            })
            .collect();

        // L2 normalise
        let norm = mean.iter().map(|x| x * x).sum::<f32>().sqrt();
        Ok(mean.iter().map(|x| x / norm.max(1e-8)).collect())
    }
}
