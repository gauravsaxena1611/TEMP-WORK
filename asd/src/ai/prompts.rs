pub fn summary_prompt(transcript: &str) -> String {
    format!(r#"You are a meeting assistant. Analyse the following meeting transcript and produce a structured summary.

TRANSCRIPT:
{transcript}

Provide your response in the following format:

## Meeting Summary
[2-4 sentence overview of what was discussed and decided]

## Key Points
- [point 1]
- [point 2]

## Action Items
- [OWNER if mentioned] [action description] [due date if mentioned]

## Decisions Made
- [decision 1]

If no action items or decisions were identified, write "None identified."
Keep the summary concise and factual. Do not invent information not present in the transcript.
"#)
}

pub fn actions_extraction_prompt(transcript: &str) -> String {
    format!(r#"Extract all action items from the following meeting transcript.
Return ONLY a JSON array. Each item must have: "description", "owner" (string or null), "due_date" (ISO date string or null).
If no action items exist, return an empty array: []

TRANSCRIPT:
{transcript}

JSON:"#)
}

pub fn decisions_extraction_prompt(transcript: &str) -> String {
    format!(r#"Extract all decisions made in the following meeting transcript.
Return ONLY a JSON array. Each item must have: "description", "context" (brief context string or null).
If no decisions exist, return an empty array: []

TRANSCRIPT:
{transcript}

JSON:"#)
}

pub fn brief_summary_prompt(transcript: &str) -> String {
    format!(r#"In one paragraph (max 100 words), summarise the key outcome of this meeting.
Be factual. Only include what is explicitly stated in the transcript.

TRANSCRIPT:
{transcript}

Summary:"#)
}
