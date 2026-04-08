---
name: ai-documentation
description: >
  Produces AI-optimized, verified, standards-compliant documentation for
  any topic or system — structured for both human readers and AI agent
  retrieval. Selects from five template types: research synthesis
  (TMPL-001), technical reference (TMPL-002), procedure runbook
  (TMPL-003), agent context plan (TMPL-004), or decision record
  (TMPL-005). Integrates research-orchestrator and verification skills
  automatically based on document type. Triggers on: write a doc,
  create documentation, document this, I need a runbook, write a guide
  for, create a decision record, document this architecture, write a
  research synthesis, create an agent context, document this workflow,
  I need a technical reference, write up what we decided, document this
  procedure, create a knowledge doc, write this up properly, I need a
  template for. Also triggers on: update existing document, improve
  this doc, audit this document, verify document standards, retrofit to
  AI-optimization standards.
version: 1.4.0
allowed-tools:
  - Read
  - Write
  - web_search
metadata:
  author: Project Team
  integrates_with:
    - research-orchestrator
    - verification
  parent_document: PROJ-001 AI-Optimized Documentation Protocol
  changelog:
    - version: "1.1.0"
      date: "2026-04-04"
      changes: "Phase 1 — Fixed non-standard frontmatter, third-person description, allowed-tools, token-aware chunking refs, precise research triggers, security checklist, contradiction protocol, hybrid document guidance"
    - version: "1.0.0"
      date: "2026-04-01"
      changes: "Initial release"
---

# AI Documentation Skill

> Produces AI-optimized, verified, standards-compliant documentation for
> any purpose — human-readable AND machine-parseable.

---

## Required Contexts

| Context ID | File | Type | Purpose | If Missing |
|------------|------|------|---------|------------|
| CTX-DOCSTD | doc-standards.ctx.md | DECLARED | Project document numbering, active parent docs, project-specific conventions | Run Context Creation (Section: Context Setup) |

---

## Context Resolution

Before executing any workflow step, resolve required contexts:

1. Check if `doc-standards.ctx.md` exists in project knowledge
2. If **MISSING** → Run Section "Context Setup" below
3. If **EXISTS** → Quick-confirm current numbering is still accurate
4. If **STALE** (30+ days) → Show current values, ask user to confirm

---

## Context Setup (First Use)

Ask these questions in a single batch when `doc-standards.ctx.md` does not exist:

```
Setting up your documentation standards context. Please answer:

1. NUMBERING: What numbering prefix does your project use for documents?
   (e.g., "000-series for masters, 010-series for children" per RULES-001)
   Or: do you use the TMPL-XXX convention established in this project?

2. PARENT DOCS: What is the master/parent document this new document
   belongs under? (document ID or "none / standalone")

3. PROJECT NAME: What is the project or system being documented?
   (used to auto-fill context in templates)

4. EXISTING CONVENTIONS: Any project-specific documentation conventions
   beyond RULES-001 that I should know about?
   (or "none — follow RULES-001 + AI-optimization layer")
```

Save responses to `doc-standards.ctx.md` using the template in
`context-templates/doc-standards.ctx.template.md`.

---

## Phase 0: Capture Intent

Before selecting a template, understand what the user needs.

Ask (or extract from conversation context if already stated):

1. **What is being documented?** (topic, system, decision, process, research area)
2. **Who will use this document?** (human reader / AI agent / both)
3. **Is there existing content to build from?** (notes, code, prior docs, conversation)
4. **Is there a specific format required?** (or free to select best template)

If all four are clear from the conversation — proceed without asking.

---

## Phase 1: Template Selection

Run the decision tree. State the classification to the user and ask for
confirmation before loading the template.

```
DECISION TREE

What is the PRIMARY purpose of this document?

├── Synthesizing knowledge FROM external sources
│   (research, industry standards, benchmarks, best practices,
│    technology comparisons, what the field recommends)
│   → TMPL-001: Research & Knowledge Synthesis
│   → Load: references/TMPL-001.md

├── Describing HOW a system, API, or component WORKS
│   (endpoints, schemas, architecture, configuration,
│    integration contracts, data models, system specs)
│   → TMPL-002: Technical Reference
│   → Load: references/TMPL-002.md

├── Telling someone HOW TO DO something step-by-step
│   (deployment, testing, onboarding, operations, runbooks,
│    user guides, release processes, incident response)
│   → TMPL-003: Procedure & Workflow
│   → Load: references/TMPL-003.md

├── Providing context or instructions FOR an AI agent
│   (agent briefings, agentic workflow plans, living context,
│    agent behavioral contracts, self-learning documents)
│   → TMPL-004 family — ask ONE follow-up question:
│   │
│   ├── "Briefing the agent before it starts work"
│   │   (who the agent is, its domain knowledge, constraints, tools)
│   │   → TMPL-004A: Agent Context Brief
│   │   → Load: references/TMPL-004A.md
│   │
│   ├── "Defining a multi-step workflow the agent executes"
│   │   (phases, gates, inputs/outputs, stop conditions)
│   │   → TMPL-004B: Agentic Workflow Plan
│   │   → Load: references/TMPL-004B.md
│   │
│   └── "Persistent cross-session memory the agent updates"
│       (stable knowledge + volatile state + observations log)
│       → TMPL-004C: Living Context Document
│       → Load: references/TMPL-004C.md

├── Capturing a DECISION that was made
    (architecture decisions, technology choices, design decisions,
     policy decisions, any formal "we decided X because Y")
    → TMPL-005: Decision Record
    → Load: references/TMPL-005.md

├── Recording a meeting or working session
    (decisions made, action items, discussion summary, parking lot)
    → TMPL-006: Session & Meeting Record
    → Load: references/TMPL-006.md

├── Documenting an AI model or AI-enabled system
    (transparency, compliance, regulatory, governance, model cards)
    → TMPL-007: AI Model & System Card
    → Load: references/TMPL-007.md

└── Capturing an incident post-mortem
    (root cause analysis, timeline, corrective actions, lessons learned)
    → TMPL-008: Incident Post-Mortem
    → Load: references/TMPL-008.md
```

**Hybrid documents:** If the user's need spans two types — split.
Create two documents and link them. Never merge templates.

**Announce selection:**
> "This looks like a [TMPL-00X: Name] document. [One sentence why.]
> Shall I proceed with this template, or does a different type fit better?"

---

## Phase 2: Pre-Authoring Research (Conditional)

Read the trigger rules carefully. Apply the precise criteria below — not
general judgment.

| Template | Trigger | Precise Criteria |
|----------|---------|-----------------|
| TMPL-001 | **ALWAYS** | Run research before writing any content |
| TMPL-002 | **CONDITIONAL** | Trigger research if ANY of these are true: (1) section uses normative language — "best practices", "recommendations", "should", "must", "avoid", "never" outside a code block; (2) document describes performance characteristics, capacity limits, or SLA values; (3) document references external specifications, standards, or third-party behaviour |
| TMPL-003 | **NEVER** | Procedure is authoritative — captures how YOUR team does it |
| TMPL-004 | **CONDITIONAL** | Trigger research if ANY of these are true: (1) agent design references a named pattern (ReAct, chain-of-thought, RAG) without a source; (2) document specifies token budgets, context limits, or performance expectations; (3) document describes multi-agent coordination patterns; (4) document specifies evaluation or quality criteria for agent output |
| TMPL-005 | **NEVER** | Decision is a historical fact — not research-derived |

**If research IS triggered:**

Invoke the research-orchestrator skill with:
- Research question derived from document intent
- Mode: "comprehensive report" for TMPL-001 / "strategic insight" for TMPL-004
- Instruct research skill to: capture all source URLs, tier-classify sources,
  run 90/10 outlier analysis, flag industry-funded sources

**Research output mapping** — apply findings to the document as follows:

| Research Output | Document Destination |
|----------------|---------------------|
| Verified claims (Tier 1/2) | Body sections tagged `✅ [VERIFIED — Source, Date]` |
| Flagged claims | Body sections tagged `⚠️ [FLAGGED — reason]` |
| Source list | Sources & References table |
| Outlier sources | `🚩 [OUTLIER — analysis]` in relevant body section |
| Open questions | TMPL-001 Section 7 or frontmatter `known_gaps` |
| Confidence score | Frontmatter `confidence_overall` (high >80% verified; medium 50–80%; low <50%) |
| Queries used | Frontmatter `research_queries_used` |

Log the queries used in the document's frontmatter `research_queries_used` field.

Tell the user:
> "Running research first — this informs the document structure and
> gives us verified, sourced content to work with."

**If research is NOT triggered:** Proceed directly to Phase 3.

---

## Phase 3: Draft the Document

### 3.1 Load the Selected Template

Read the full template from `references/`:
- `references/TMPL-001.md` — Research Synthesis
- `references/TMPL-002.md` — Technical Reference
- `references/TMPL-003.md` — Procedure & Workflow
- `references/TMPL-004.md` — AI Agent Context & Plan
- `references/TMPL-005.md` — Decision Record

Also read: `references/TMPL-000_conventions.md`

### 3.2 Fill Frontmatter First

Complete the entire YAML frontmatter block before writing any content.
No placeholders may remain in frontmatter when the document is published.

Key fields to populate:
- `document_id` — from CTX-DOCSTD numbering convention
- `parent_document` — from CTX-DOCSTD parent doc setting
- `template_version_used` — record the active template version (e.g., `TMPL-001 v1.1`)
- `intent` — derive from Phase 0 capture
- `triggers` and `negative_triggers` — minimum 3 triggers, 2 negative triggers
- `volatility` — assess based on content type
- `review_trigger` — set BEFORE writing content, never leave blank

### 3.3 Write the AI Summary Block

Write this before any content sections. Return to complete it after
the document body is written.

```markdown
> ## 🤖 AI Summary
> **Core Purpose:** [One sentence]
> **Key Claims/Findings:**
> - [Most important finding/fact with confidence]
> - [Second]
> - [Third]
> **Trust Level:** HIGH / MEDIUM / CONDITIONAL — [reason if conditional]
> **Primary Dependencies:** [Docs this relies on]
> **Research Currency:** [Date validated OR "Not research-dependent"]
> **Do NOT Use This For:** [1-3 exclusions]
> **Review By:** [date or trigger condition]
```

### 3.4 Write Content Sections

For each section:

1. **Add semantic type tag** immediately after the heading:
   `[TYPE: REFERENCE]`, `[TYPE: DECISION]`, `[TYPE: PROCEDURE]`,
   `[TYPE: EXPLANATION]`, `[TYPE: RESEARCH_FINDING]`, `[TYPE: OPEN_QUESTION]`

2. **Apply chunking rules** — see `references/TMPL-000_conventions.md` Section 7.
   Target 300–500 words per section. Open every section with a context anchor
   sentence naming the subject explicitly.

3. **Apply confidence markers** to significant claims:
   - ✅ `[VERIFIED — Tier 1/2, Source, Date]`
   - ⚠️ `[FLAGGED — reason]`
   - 🔬 `[RESEARCH_FINDING — confidence: HIGH/MEDIUM/LOW]`
   - ❓ `[UNRESOLVED — status]`
   - 💡 `[INFERENCE — basis]`
   - 🗑️ `[SUPERSEDED — by: doc/date]`
   
   **Plaintext fallback** for non-emoji toolchains:
   `[VERIFIED]`, `[FLAGGED]`, `[RESEARCH_FINDING]`, `[UNRESOLVED]`,
   `[INFERENCE]`, `[SUPERSEDED]`

4. **Type cross-references** using the controlled vocabulary:
   `[DEPENDS_ON]`, `[SUPERSEDES]`, `[APPLIES]`, `[CONTRADICTS]`,
   `[EXTENDS]`, `[VALIDATED_BY]`, `[IMPLEMENTS]`, `[SEE_ALSO]`
   
   Format: `[RELATIONSHIP] → [Document ID, Section X.X] — [one-line reason]`

### 3.5 Remove Template Scaffolding

Remove all `<!-- comment -->` author guidance blocks. Remove section
placeholder text. Do not leave empty sections.

---

## Phase 4: Verification

**Always run verification on:**
- Any document containing external factual claims
- Any TMPL-001 or TMPL-002 document
- Any section tagged `[TYPE: RESEARCH_FINDING]`

Invoke the verification skill. Map output to inline confidence markers
and the Verification Record section (mandatory in TMPL-001).

Key fields to populate: `research_validated`, `research_validated_date`,
`confidence_overall`.

---

## Phase 5: Cross-References & Hierarchy

1. **Update parent document** — add this document to the parent's index
2. **Check bidirectionality** — confirm reciprocal references exist
3. **Validate relationship types** — every `[RELATIONSHIP] →` entry must use a valid type
4. **Context manifest** — add TMPL-004 documents to `context-manifest.ctx.md` if used

---

## Phase 6: Pre-Publish Checklist

```
STRUCTURE
[ ] Extended frontmatter complete — all fields filled, no placeholders
[ ] template_version_used field populated (e.g., "TMPL-001 v1.1")
[ ] AI Summary block present and complete
[ ] Every ## section has a [TYPE: ...] tag
[ ] Headings appear at least every 400–500 words (token-aware — see TMPL-000 §7)
[ ] document_id follows CTX-DOCSTD numbering convention
[ ] parent_document field is filled

CONTENT
[ ] All significant claims have inline confidence markers
[ ] No floating tables (each preceded by intro sentence)
[ ] No floating bullet lists (each preceded by intro sentence)
[ ] Technical identifiers wrapped in backticks
[ ] Paragraphs are 3–5 sentences max
[ ] Every section opens with a context anchor sentence naming the subject
[ ] Every section is self-contained if retrieved in isolation
[ ] No cross-section pronouns — name the subject explicitly every time

SECURITY
[ ] No credentials, passwords, API keys, or tokens in document body
[ ] No real PII in examples — placeholder syntax used (<PLACEHOLDER>)
[ ] Connection strings and URLs use placeholder values
[ ] Regulated industry: disclosure-restricted information excluded

REFERENCES
[ ] All cross-references include relationship types
[ ] triggers list has at least 3 specific entries
[ ] negative_triggers has at least 2 entries
[ ] review_trigger is set — document does not expire silently

VERIFICATION
[ ] research_validated date set (or noted as not applicable)
[ ] confidence_overall is honest — not defaulted to "high"
[ ] Verification Record section present (TMPL-001 mandatory, others optional)

HIERARCHY (per RULES-001)
[ ] Parent document updated with reference to this document
[ ] Bidirectional references confirmed
[ ] Revision History section present with v1.0 entry
```

If all items pass: announce "Document is ready. Proceeding to output."
If items fail: fix before proceeding.

---

## Phase 7: Output

1. **Create the file** with the document ID as filename:
   `[document-id]_[short-title].md`

2. **Present to user** with a summary covering: template used and version,
   research performed (yes/no, queries), verification status, key sections,
   `review_trigger` value, and any checklist items needing manual attention.

3. **Post-execution observation:** Note anything that may indicate CTX-DOCSTD
   needs updating, log to `pending_observations`.

---

## Handling Special Cases

### Updating an Existing Document

1. Read the existing document
2. Check for: extended frontmatter, AI Summary block, section type tags
3. If missing → offer retrofit: "(A) targeted updates only, or (B) full retrofit to standards"
4. If present → update content, increment version, add revision history entry
5. Check if `template_version_used` matches current template — if not, note drift

### Contradiction Detected During Research

1. Surface explicitly — name the document and section contradicted
2. Present both positions with source tiers
3. Recommend: (A) update old document, (B) flag old document, or (C) defer
4. Add `[CONTRADICTS] →` cross-references in both documents
5. Do not finalize the new document until contradiction is resolved or deferred

### Multi-Step Hybrid Document Creation

When a topic spans two template types:
1. Create TMPL-001 first — let research findings inform the technical reference
2. Create TMPL-002 second — reference TMPL-001 as `[VALIDATED_BY]`
3. Link bidirectionally — TMPL-001 references TMPL-002 as `[IMPLEMENTS]`
4. Update parent document index to show both documents and their relationship

### TMPL-004 Living Context Mode

- Set `agent_write_access: true` and `volatility: living` in frontmatter
- Initialize Observations & Learnings Log (Section 8) with empty tables
- Agent has write access to Section 8 only

### Decision Records (TMPL-005) — Immutability

- **Creating:** Confirm decision is final before writing
- **Superseding:** Create a NEW TMPL-005. In the old document: set
  `status: Superseded`, `superseded_by: [new ID]`, and add this warning block:
  ```
  > ⚠️ SUPERSEDED: This document has been replaced by [Document ID].
  > Do not use for new work.
  ```
- **RAG index action:** Apply `status: superseded` metadata filter immediately.
  Remove from active index within 30 days.
- **Never edit decision content** in a finalized TMPL-005

---

## Reference Files

| File | When to Load |
|------|-------------|
| `references/TMPL-000_conventions.md` | Always — AI-optimization conventions |
| `references/TMPL-001.md` | doc type = Research Synthesis |
| `references/TMPL-002.md` | doc type = Technical Reference |
| `references/TMPL-003.md` | doc type = Procedure & Workflow |
| `references/TMPL-004.md` | doc type = AI Agent — routing index (read first to select variant) |
| `references/TMPL-004A.md` | doc type = Agent Context Brief |
| `references/TMPL-004B.md` | doc type = Agentic Workflow Plan |
| `references/TMPL-004C.md` | doc type = Living Context Document |
| `references/TMPL-005.md` | doc type = Decision Record |
| `references/TMPL-006.md` | doc type = Session & Meeting Record |
| `references/TMPL-007.md` | doc type = AI Model & System Card |
| `references/TMPL-008.md` | doc type = Incident Post-Mortem |
| `references/TMPL-002-domain-variants.md` | TMPL-002 for microservice, data pipeline, or ML model |
| `references/TMPL-003-variants.md` | TMPL-003 for deployment, incident response, or database migration |
| `references/ARCH-001-TMPL004-Reconciliation.md` | Choosing between ARCH-001 .ctx.md and TMPL-004 variants |
| `references/AI-Readiness-Test-Protocol.md` | Scoring a document for AI-optimization compliance |
| `references/OPS-002-Documentation-Health-Score.md` | Running a corpus health audit or legacy document upgrade |
| `references/OPS-003-Token-Budget-And-Versioning.md` | Context budget planning or skill package release |
| `references/ARCH-002-Skills-Updater-Integration.md` | Understanding the observation → skills-updater flow |
| `tests/TEST-001_Skill-Test-Suite.md` | Running skill validation tests before a release |
| `examples/` | User needs a populated example of any template type |

---

## Post-Execution Observation Phase

After every task, before closing:

1. Did user correct numbering, naming, or conventions? → Log to CTX-DOCSTD
2. Did a template section prove confusing or insufficient? → Note for review
3. Did research contradict an existing document? → Surface, do not silently resolve
4. Did any checklist item consistently fail? → Flag for template update

---

## Related Skills

- **research-orchestrator** — Phase 2 for TMPL-001 and conditional types
- **verification** — Phase 4 for all claim-bearing content
- **skill-development-suite** — skill creation, then document with this skill
- **skills-updater** — periodic processing of pending observations
