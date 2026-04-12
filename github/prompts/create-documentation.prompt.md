---
name: create-documentation
description: Create a structured document using the ai-documentation skill
---

Read `.github/skills/ai-documentation/SKILL.md` and follow its instructions.

Document type: ${input:doc_type:e.g. Application Understanding, Decision Record, Runbook, Architecture Pack}
Subject: ${input:subject:What is being documented}

Follow the skill's template selection process and produce the complete document.
Save the output to the appropriate location in `docs/`.
