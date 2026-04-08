# CTX-DOCSTD: Documentation Standards Agent Context
## AI Agent Context Brief — Project Documentation Conventions and Current State

```yaml
---
document_id: "EX-004 CTX-DOCSTD-Agent-Context"
title: "CTX-DOCSTD — Documentation Standards Agent Context Brief"
version: "1.0"
created: "2026-04-04"
status: "Living"
parent_document: "PROJ-001 AI-Optimized Documentation Protocol"
template_version_used: "TMPL-004 v1.1"

agent_write_access: true
agent_write_sections: ["8"]

intent: >
  Enable the ai-documentation skill and any documentation-related AI agent
  to understand the project's document numbering conventions, active parent
  documents, current document count, and project-specific overrides that
  govern all documentation work in this project.

consumption_context:
  - ai-reasoning
  - agentic-execution

triggers:
  - "what is the documentation context for this project"
  - "what document number should I use"
  - "what are the active parent documents"
  - "what are the project documentation conventions"
  - "CTX-DOCSTD"
  - "doc standards context"
  - "current document numbering"

negative_triggers:
  - "how to create a document" # → ai-documentation SKILL.md
  - "what template should I use" # → SKILL.md Phase 1 decision tree

volatility: "living"
research_validated: false
research_validated_date: ""
research_queries_used: []

review_trigger: "After every documentation session — update Section 8 with observations"

confidence_overall: "high"
confidence_note: "Reflects actual project state as of 2026-04-04"
---
```

---

> ## 🤖 AI Summary
> **Core Purpose:** Provides the documentation standards context that governs all document creation in this project — number ranges, active parents, conventions, and current state.
> **Key Facts for Agent Use:**
> - Document numbering: PROJ-series for project charters, TMPL-series for templates, EX-series for examples, 000–099 for regular hierarchy
> - Active parent documents: PROJ-001 (project charter for AI documentation protocol)
> - Next available number in 000-series: 070 (update after each new document created)
> **Trust Level:** HIGH — reflects actual project state
> **Research Currency:** Not research-dependent — project state record
> **Do NOT Use This For:** Template selection logic (use SKILL.md); document content guidance (use TMPL-000_conventions.md)
> **Review By:** After every documentation session — this is a living document

---

## TABLE OF CONTENTS

1. [Project Identity](#1-project-identity)
2. [Document Numbering Convention](#2-document-numbering-convention)
3. [Active Parent Documents](#3-active-parent-documents)
4. [Project-Specific Conventions](#4-project-specific-conventions)
5. [Template Usage History](#5-template-usage-history)
6. [Agent Behavioural Contract](#6-agent-behavioural-contract)
7. [Current Project State](#7-current-project-state)
8. [Observations & Learnings Log](#8-observations--learnings-log)
9. [Revision History](#9-revision-history)

---

## 1. Project Identity
[TYPE: REFERENCE]

### 1.1 Project Overview

The AI Documentation Skill project is building a reusable, AI-optimized
documentation system consisting of a Claude skill (`ai-documentation`),
five Markdown document templates (TMPL-001 through TMPL-005), an
AI-optimization conventions layer (TMPL-000), and a context file system.
The project is governed by PROJ-001 and documented using RULES-001.

| Attribute | Value |
|-----------|-------|
| **Project Name** | AI Documentation Skill — Phase 1 |
| **Project Type** | Knowledge Management / Skill Development |
| **Documentation Root** | Claude project knowledge folder |
| **Governing Standards** | RULES-001 Documentation Standards; Truth & Verification Standards |
| **Primary Skill** | `ai-documentation` v1.1.0 |

---

## 2. Document Numbering Convention
[TYPE: REFERENCE]

### 2.1 Number Series in Use

The AI Documentation Skill project uses the following number series.
Every document must use one of these series — do not invent new ones.

| Series | Range | Purpose | Current Next Available |
|--------|-------|---------|----------------------|
| Master (000-series) | 000–009 | Top-level project master docs | 002 |
| First-level children | 010–099 | Major topic areas under masters | 070 |
| Sub-children (0X1–0X9) | e.g., 011–019 | Sub-topics of parent 010 | N/A (none in use) |
| PROJ-series | PROJ-001+ | Project charters and session records | PROJ-003 |
| TMPL-series | TMPL-000 to TMPL-009 | Template files | TMPL-006 |
| EX-series | EX-001+ | Populated example documents | EX-006 |
| CTX-series | CTX-001+ | Context files (not for project knowledge indexing) | CTX-002 |

### 2.2 Naming Rules

Every document ID follows the format: `[PREFIX]-[NUMBER] [Short-Title]`

Examples:
- `PROJ-001 AI-Optimized Documentation Protocol`
- `TMPL-001 Research-Knowledge-Synthesis`
- `EX-003 Create-Decision-Record-Procedure`
- `070 Competitive-Analysis-LLM-Documentation`

File names follow: `[PREFIX]-[NUMBER]_[Short-Title].md` or `[NUMBER]_[Short-Title].md`

**Critical:** After every new document is created, update the "Current Next Available"
column in Section 2.1 of this document (write access permitted in Section 8 only).

---

## 3. Active Parent Documents
[TYPE: REFERENCE]

### 3.1 Documents Currently Accepting Children

New documents link to one of the parents below. If none fits, consult the
project team before creating a new master document.

| Document ID | Title | Accepts Child Types | Status |
|-------------|-------|---------------------|--------|
| PROJ-001 | AI-Optimized Documentation Protocol | All — project charter | Active |
| PROJ-002 | Enhancement Roadmap — Phase 2 | Planning and analysis documents | Active |

### 3.2 Closed / Completed Parents

| Document ID | Title | Closed Reason |
|-------------|-------|--------------|
| None currently | — | — |

---

## 4. Project-Specific Conventions
[TYPE: REFERENCE]

### 4.1 Conventions That Override RULES-001

The AI Documentation Skill project extends RULES-001 with the AI-optimization
layer. The following conventions apply in addition to RULES-001.

**Required in every document:**
- Extended YAML frontmatter block (TMPL-000 conventions Section 2)
- AI Summary block (TMPL-000 conventions Section 3)
- Section semantic type tags `[TYPE: ...]` on every `##` heading
- Inline confidence markers on all external claims
- `template_version_used` field in frontmatter
- `review_trigger` must never be left blank

**Chunking target:**
- 300–500 words per `##` section (token-aware — see TMPL-000 conventions Section 7)
- Context anchor sentence at the start of every section

**Confidence marker style:**
- Emoji markers (✅ ⚠️ 🔬 ❓ 💡 🗑️) are preferred
- ASCII fallback (`[VERIFIED]`, `[FLAGGED]`, etc.) acceptable for non-emoji toolchains
- Do not mix styles within a single document

### 4.2 Conventions That Do NOT Apply

The following RULES-001 structures are not used in this project:

- Persona document template (RULES-001 Section 7.3) — not applicable to this project type
- Seasonal business calendar documents — not applicable

---

## 5. Template Usage History
[TYPE: REFERENCE]

### 5.1 Documents Created Using This System

| Document ID | Title | Template Used | Date Created | Status |
|-------------|-------|--------------|--------------|--------|
| PROJ-001 | AI-Optimized Documentation Protocol | N/A (project charter) | 2026-04-01 | Final |
| PROJ-002 | Enhancement Roadmap | N/A (planning doc) | 2026-04-04 | Draft |
| EX-001 | RAG Chunking Research | TMPL-001 v1.1 | 2026-04-04 | Final |
| EX-002 | AI Doc Skill Technical Reference | TMPL-002 v1.1 | 2026-04-04 | Final |
| EX-003 | Create Decision Record Procedure | TMPL-003 v1.1 | 2026-04-04 | Final |
| EX-004 | CTX-DOCSTD Agent Context (this doc) | TMPL-004 v1.1 | 2026-04-04 | Living |
| EX-005 | Decision: Chunking Strategy | TMPL-005 v1.1 | 2026-04-04 | Final |

---

## 6. Agent Behavioural Contract
[TYPE: REFERENCE]

### 6.1 What the Agent Is Permitted to Do

The `ai-documentation` skill and any agent using this context file is
permitted to perform the following actions without asking for confirmation:

- Read any document in this project's knowledge folder
- Read `doc-standards.ctx.md` (this document) to check current state
- Apply the document numbering convention from Section 2 when creating new documents
- Reference the active parent documents from Section 3

### 6.2 What the Agent Must Ask Before Doing

The agent must ask for explicit confirmation before:

- Creating a new document number series not listed in Section 2.1
- Changing the "Next available number" counter without creating a corresponding document
- Adding a new Active Parent Document to Section 3.1
- Modifying any section of this document other than Section 8

### 6.3 What the Agent Must Never Do

- Set a `document_id` that has already been used
- Skip the Phase 6 Pre-Publish Checklist
- Leave `review_trigger` blank in any document it creates
- Merge two document types into one (always split hybrid documents)
- Create a document with placeholder text still in the frontmatter
- Include credentials, real PII, or production values in any document body

### 6.4 Agent Write Access

The agent has write access to **Section 8 only** of this document. After
every documentation session, the agent appends an observation to the
Section 8 observations table. The agent must not modify Sections 1–7.

---

## 7. Current Project State
[TYPE: REFERENCE]

### 7.1 Phase 1 Completion Status

Phase 1 of the Enhancement Roadmap (PROJ-002) is in execution as of 2026-04-04.

| Work Package | Status | Deliverable |
|-------------|--------|-------------|
| WP-1.1 — SKILL.md Standards Alignment | ✅ Complete | SKILL.md v1.1.0 |
| WP-1.2 — Token-Aware Chunking Rules | ✅ Complete | TMPL-000 v1.1 Section 7 |
| WP-1.3 — Research Trigger Precision | ✅ Complete | TMPL-000 v1.1 Section 8 |
| WP-1.4 — Security Content Handling | ✅ Complete | TMPL-000 Section 10; TMPL-002 §6.4; TMPL-003 §1.4 |
| WP-1.5 — Populated Example Documents | ✅ Complete | EX-001 through EX-005 |
| WP-1.6 — RAG Lifecycle Superseded Docs | ✅ Complete | TMPL-000 v1.1 Section 12; TMPL-005 §8.3 |
| WP-1.7 — Skill Package Manifest | ✅ Complete | README.md, MANIFEST.md |

### 7.2 Known Open Items

- Phase 2 not yet started — awaiting user approval of Phase 1
- TMPL-004 split into three variants is planned for Phase 2 WP-2.1
- CTX-DOCSTD next available numbers must be updated after each new document

---

## 8. Observations & Learnings Log
[TYPE: REFERENCE]

*Agent write access: YES — append rows to this section only after each documentation session.*

### 8.1 Pending Observations

| Date | Observation | Type | Status |
|------|-------------|------|--------|
| 2026-04-04 | Phase 1 executed successfully. TMPL-000 conventions Section 7 changed from word-count to token-aware guidance. All existing documents referencing the old 150–200 word rule will need review when updated. | Template evolution | Open — flag for Phase 2 |
| 2026-04-04 | TMPL-004 is a known split candidate. Current template is functional but mixes three structurally different document types. Agents using TMPL-004 may select the wrong structure until the split is complete. | Gap | Open — WP-2.1 |

### 8.2 Resolved Observations

| Date | Observation | Resolution | Resolved Date |
|------|-------------|------------|---------------|
| 2026-04-01 | Initial: SKILL.md used non-standard `integrates_with` frontmatter field | WP-1.1 — removed; moved to `metadata` block | 2026-04-04 |
| 2026-04-01 | Initial: Chunking rule calibrated in words not tokens | WP-1.2 — TMPL-000 Section 7 rewritten | 2026-04-04 |

---

## 9. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-04 | Initial | Document created | Phase 1 WP-1.5 — TMPL-004 example, serving as the actual living context document for the AI Documentation project |

---

*Template: TMPL-004 AI Agent Context & Plan v1.1 | Parent: PROJ-001 AI-Optimized Documentation Protocol*
*Living document — Section 8 updated by ai-documentation skill after each session*
