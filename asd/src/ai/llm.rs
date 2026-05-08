use anyhow::{anyhow, Context, Result};
use reqwest::Client;
use serde::{Deserialize, Serialize};
use std::time::Duration;

#[derive(Serialize)]
struct CompletionRequest {
    prompt: String,
    temperature: f32,
    n_predict: u32,
    stop: Vec<String>,
    stream: bool,
}

#[derive(Deserialize)]
struct CompletionResponse {
    content: String,
}

pub struct LlmClient {
    client: Client,
    endpoint: String,
    temperature: f32,
    max_tokens: u32,
}

impl LlmClient {
    pub fn new(endpoint: &str, temperature: f32, max_tokens: u32, timeout_secs: u64) -> Result<Self> {
        let client = Client::builder()
            .timeout(Duration::from_secs(timeout_secs))
            .build()
            .context("Failed to create HTTP client")?;
        Ok(Self {
            client,
            endpoint: endpoint.to_string(),
            temperature,
            max_tokens,
        })
    }

    pub async fn health_check(&self) -> bool {
        let url = format!("{}/health", self.endpoint);
        self.client
            .get(&url)
            .send()
            .await
            .map(|r| r.status().is_success())
            .unwrap_or(false)
    }

    pub async fn complete(&self, prompt: &str) -> Result<String> {
        let url = format!("{}/completion", self.endpoint);
        let req = CompletionRequest {
            prompt: prompt.to_string(),
            temperature: self.temperature,
            n_predict: self.max_tokens,
            stop: vec!["</answer>".to_string(), "<|end|>".to_string()],
            stream: false,
        };

        let response = self
            .client
            .post(&url)
            .json(&req)
            .send()
            .await
            .context("LLM request failed — is llama-server running on localhost:8080?")?;

        if !response.status().is_success() {
            return Err(anyhow!("LLM server error: {}", response.status()));
        }

        let completion: CompletionResponse = response
            .json()
            .await
            .context("Failed to parse LLM response")?;

        Ok(completion.content.trim().to_string())
    }
}
