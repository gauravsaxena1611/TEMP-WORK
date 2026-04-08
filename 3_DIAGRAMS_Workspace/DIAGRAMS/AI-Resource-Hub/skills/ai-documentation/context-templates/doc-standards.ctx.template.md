# Context: Documentation Standards
## Project-Specific Documentation Configuration — CTX-DOCSTD

```markdown
# Context: Documentation Standards

## Context Metadata

| Field | Value |
|-------|-------|
| **Context ID** | CTX-DOCSTD |
| **Type** | DECLARED |
| **Source** | User-provided |
| **Created** | {{DATE}} |
| **Last Verified** | {{DATE}} |
| **Last Updated** | {{DATE}} |
| **Verified By** | User confirmation |
| **Confidence** | HIGH |
| **Skills That Use This** | ai-documentation, skills-updater |
| **Template Version** | CTX-DOCSTD v1.2 |

---

## 1. Project Identity

| Attribute | Value |
|-----------|-------|
| **Project Name** | {{PROJECT_NAME}} |
| **Project Type** | {{e.g., Software Development / Research / Knowledge Base / AI Agent Project}} |
| **Documentation Root** | {{Where docs live — project folder / Claude project / file path}} |
| **Primary Language** | {{e.g., English}} |

---

## 2. Document Numbering Convention

| Series | Range | Purpose | Example | Next Available |
|--------|-------|---------|---------|---------------|
| {{Master/Parent}} | {{000–009}} | {{Top-level project docs}} | {{000 Project Master}} | {{e.g., 002}} |
| {{First-level}} | {{010–099}} | {{Major topic areas}} | {{010 Architecture}} | {{e.g., 050}} |
| {{Sub-children}} | {{011–019}} | {{Sub-topics of 010}} | {{011 API Design}} | {{N/A}} |
| {{TMPL series}} | {{TMPL-00X}} | {{Template files}} | {{TMPL-001}} | {{TMPL-006}} |
| {{PROJ series}} | {{PROJ-00X}} | {{Project charters}} | {{PROJ-001}} | {{PROJ-003}} |
| {{EX series}} | {{EX-00X}} | {{Populated examples}} | {{EX-001}} | {{EX-006}} |

**Critical:** Update "Next Available" after every new document is created.

---

## 3. Active Parent Documents

Documents currently accepting children — new docs link to one of these:

| Document ID | Title | Accepts Child Types | Status |
|-------------|-------|---------------------|--------|
| {{Doc ID}} | {{Title}} | {{e.g., All / Research only}} | Active |
| {{Doc ID}} | {{Title}} | {{Types}} | Active |

---

## 4. RAG & Retrieval Configuration

| Attribute | Value |
|-----------|-------|
| **RAG System** | {{none / chroma / pinecone / weaviate / pgvector / custom / [name]}} |
| **Embedding Model** | {{text-embedding-3-large / cohere-embed-v4 / unspecified}} |
| **Search Mode** | {{semantic / hybrid (BM25 + vector) / keyword}} |
| **Chunk Overlap %** | {{10–25% recommended, or "default"}} |
| **Index Metadata Fields** | {{document_id, template_type, status, volatility — or "none"}} |
| **Superseded Content Filter** | {{status: superseded excluded by default — YES / NO / not configured}} |

---

## 5. LLM Target & Agent Configuration

| Attribute | Value |
|-----------|-------|
| **Primary LLM** | {{claude-opus-4 / claude-sonnet-4 / gpt-4o / [model]}} |
| **Context Window (tokens)** | {{200,000 / 128,000 / unspecified}} |
| **Multi-Agent Patterns in Use** | {{none / orchestrator-subagent / parallel agents / [pattern]}} |
| **Agent Frameworks** | {{none / ARCH-001 Living Context / Claude Projects / [framework]}} |
| **TMPL-004 Variant in Use** | {{004A only / 004B only / 004C only / mixed / not applicable}} |

---

## 6. Regulatory & Compliance Context

| Attribute | Value |
|-----------|-------|
| **Regulatory Context** | {{none / EU-AI-Act / NIST-AI-RMF / HKMA / ISO-42001 / [framework]}} |
| **Data Classification** | {{public / internal / confidential / restricted}} |
| **Retention Policy** | {{[duration] / indefinite / unspecified}} |
| **PII In Scope** | {{yes — see Section 7 / no}} |
| **Mandatory Human Review Gates** | {{none / before-Final-status / before-publication / [condition]}} |

---

## 7. Project-Specific Conventions

Conventions that OVERRIDE or EXTEND the baseline RULES-001 + AI-optimization layer:

### 7.1 Naming Overrides
{{e.g., "We use kebab-case filenames: project-name-topic.md"}}
{{or: "None — follow RULES-001 exactly"}}

### 7.2 Required Sections (All Docs Must Include)
{{e.g., "All documents must include a 'Team Context' section with owner and stakeholders"}}
{{or: "None — follow template defaults"}}

### 7.3 Forbidden Sections or Content
{{e.g., "Do not include competitive analysis in any public-facing doc"}}
{{or: "None"}}

### 7.4 Confidence Threshold Policy
{{e.g., "Only Tier 1 sources acceptable for security-related claims"}}
{{or: "Standard tier system — follow Truth & Verification Standards"}}

### 7.5 Review Cycle Override
{{e.g., "All documents reviewed monthly" or "Follow volatility-based triggers from TMPL-000"}}

### 7.6 Template Version in Use
{{e.g., "TMPL-001 v1.1, TMPL-002 v1.1, TMPL-003 v1.1, TMPL-004A/B/C v1.1, TMPL-005 v1.1"}}

---

## 8. Template Usage History

Documents created so far using this system:

| Document ID | Title | Template Used | Date Created | Status |
|-------------|-------|--------------|--------------|--------|
| {{ID}} | {{Title}} | {{TMPL-00X}} | {{Date}} | {{Draft/Final/Living}} |

---

## 9. Session Handoff Log

Most recent handoff block (from SESSION HANDOFF pattern — TMPL-000 Section 14):

```
## SESSION HANDOFF — {{DATE}}

Work Completed:
- {{Document and status}}

Work In Progress:
- {{Document and what remains}}

Open Questions:
- {{Question}}

Next Session Should Start By:
{{Single action}}
```

---

## 10. Pending Observations

Items for skills-updater to process. Agent appends here; skills-updater clears on processing:

| Date | Observation | Type | Status |
|------|-------------|------|--------|
| {{DATE}} | Initial creation | Setup | Resolved |

---

## 11. Change Log

| Date | What Changed | How Discovered | Confirmed By |
|------|-------------|----------------|--------------|
| {{DATE}} | Initial creation | User interview | User |
```
