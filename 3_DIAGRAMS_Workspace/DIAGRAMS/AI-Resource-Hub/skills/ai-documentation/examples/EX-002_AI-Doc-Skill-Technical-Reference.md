# AI Documentation Skill: Technical Reference
## System Specification — Skill Architecture, Workflow Engine, and Integration Points

```yaml
---
document_id: "EX-002 AI-Doc-Skill-Technical-Reference"
title: "AI Documentation Skill — Technical Reference"
version: "1.0"
created: "2026-04-04"
status: "Final"
parent_document: "PROJ-001 AI-Optimized Documentation Protocol"
template_version_used: "TMPL-002 v1.1"

system_name: "ai-documentation"
system_version: "1.1.0"
applies_from: "2026-04-04"
supersedes_version: "1.0.0"

intent: >
  Enable developers and Claude project administrators to understand how the
  ai-documentation skill is structured, what its seven workflow phases do,
  which reference files it requires, and how it integrates with the
  research-orchestrator and verification skills.

consumption_context:
  - human-reading
  - ai-reasoning
  - rag-retrieval

triggers:
  - "how does the ai-documentation skill work"
  - "ai-documentation skill architecture"
  - "what templates does the documentation skill use"
  - "how does the skill select a template"
  - "what reference files does the documentation skill need"
  - "ai-documentation skill integration points"

negative_triggers:
  - "how to create a document" # → ai-documentation SKILL.md for workflow
  - "what template should I use" # → SKILL.md Phase 1 decision tree
  - "ai documentation best practices" # → EX-001 research synthesis

volatility: "stable"
research_validated: false
research_validated_date: ""
research_queries_used: []

review_trigger: "When skill version increments to 1.2.0 or higher"

confidence_overall: "high"
confidence_note: "Direct system knowledge — describes the actual skill as implemented"
---
```

---

> ## 🤖 AI Summary
> **System:** ai-documentation v1.1.0
> **Core Purpose:** A Claude skill that produces AI-optimized, standards-compliant documentation by routing requests through a seven-phase workflow across five template types.
> **Key Specifications:**
> - Seven workflow phases: Intent → Template Selection → Research → Draft → Verify → Cross-Ref → Output
> - Five template types: TMPL-001 through TMPL-005 loaded from `references/` directory
> - Integrates research-orchestrator (Phase 2) and verification (Phase 4) as upstream skills
> **Trust Level:** HIGH — direct system knowledge, version-locked to 1.1.0
> **Version Validity:** v1.1.0 (Phase 1 update — see Revision History for changes from v1.0.0)
> **Do NOT Use This For:** Day-to-day document creation (use the skill directly); template content authoring guidance (use TMPL-000_conventions.md)

---

## TABLE OF CONTENTS

1. [System Overview](#1-system-overview)
2. [Architecture](#2-architecture)
3. [Workflow Engine — Seven Phases](#3-workflow-engine--seven-phases)
4. [Template Library](#4-template-library)
5. [Configuration Reference](#5-configuration-reference)
6. [Security Model](#6-security-model)
7. [Integration Points](#7-integration-points)
8. [Constraints & Limits](#8-constraints--limits)
9. [Sources & Cross-References](#9-sources--cross-references)
10. [Revision History](#10-revision-history)

---

## 1. System Overview
[TYPE: REFERENCE]

### 1.1 Purpose & Responsibility

The `ai-documentation` skill is a Claude skill (SKILL.md format) that routes
documentation requests through a structured seven-phase workflow, selecting
the appropriate template type and invoking upstream research and verification
skills where required. The skill produces documents that comply with both
RULES-001 (structural standards) and the AI-optimization layer (machine
readability, RAG chunking, confidence markers).

`ai-documentation` is responsible for: template selection, document drafting,
research invocation routing, verification routing, cross-reference management,
and the pre-publish quality checklist.

`ai-documentation` does NOT handle: raw research execution (delegated to
`research-orchestrator`), claim verification logic (delegated to `verification`),
context file lifecycle management (delegated to `skills-updater`), or actual
file persistence in external systems.

### 1.2 Technical Identity

| Attribute | Value |
|-----------|-------|
| **Skill Name** | `ai-documentation` |
| **Version** | `1.1.0` |
| **Format** | Anthropic SKILL.md v1 |
| **Allowed Tools** | `Read`, `Write`, `web_search` |
| **Required Context File** | `doc-standards.ctx.md` (CTX-DOCSTD) |
| **Package Format** | ZIP archive: `ai-documentation-skill.zip` |
| **Install Target** | Claude Projects (project knowledge) |

### 1.3 System Boundaries

```
[User request] → [ai-documentation skill]
                       │
                       ├── [research-orchestrator skill] (Phase 2 — conditional)
                       ├── [verification skill] (Phase 4 — conditional)
                       │
                       ├── [references/TMPL-000_conventions.md] (always)
                       ├── [references/TMPL-001..005.md] (one selected per session)
                       ├── [examples/EX-001..005.md] (on request)
                       └── [context-templates/doc-standards.ctx.template.md] (first use)

Output: → [Completed Markdown document]
         → [Updated CTX-DOCSTD context file (if conventions changed)]
```

---

## 2. Architecture
[TYPE: REFERENCE]

### 2.1 File Structure

The skill package is a ZIP archive containing the following files:

```
ai-documentation/
├── SKILL.md                              # Skill entry point — routing and workflow
├── README.md                             # Installation and quickstart guide
├── MANIFEST.md                           # Complete file listing and changelog
├── references/
│   ├── TMPL-000_conventions.md           # AI-optimization conventions (always loaded)
│   ├── TMPL-001.md                       # Research & Knowledge Synthesis template
│   ├── TMPL-002.md                       # Technical Reference template
│   ├── TMPL-003.md                       # Procedure & Workflow template
│   ├── TMPL-004.md                       # AI Agent Context & Plan template
│   └── TMPL-005.md                       # Decision Record template
├── context-templates/
│   └── doc-standards.ctx.template.md     # CTX-DOCSTD context file template
└── examples/
    ├── EX-001_RAG-Chunking-Research.md   # TMPL-001 populated example
    ├── EX-002_AI-Doc-Skill-Technical-Reference.md  # TMPL-002 populated example (this file)
    ├── EX-003_How-to-Create-Decision-Record.md     # TMPL-003 populated example
    ├── EX-004_CTX-DOCSTD-Agent-Context.md          # TMPL-004 populated example
    └── EX-005_Decision-Chunking-Strategy.md        # TMPL-005 populated example
```

### 2.2 Context Dependency

The skill requires a `doc-standards.ctx.md` context file (CTX-DOCSTD) to be
present in the Claude project knowledge. This file provides: document numbering
convention, active parent documents, and project-specific overrides. On first
use when the file is absent, the skill runs the Context Setup wizard to create it.

---

## 3. Workflow Engine — Seven Phases
[TYPE: REFERENCE]

The `ai-documentation` skill executes a fixed seven-phase workflow for every
documentation request. Phases are sequential. No phase is skipped unless
explicitly exempted by the phase's own rules.

| Phase | Name | Always Runs? | Output |
|-------|------|-------------|--------|
| 0 | Capture Intent | Yes | Four-field understanding of request |
| 1 | Template Selection | Yes | Confirmed template type |
| 2 | Pre-Authoring Research | Conditional (by template) | Research findings mapped to document structure |
| 3 | Draft Document | Yes | Complete document with frontmatter, summary block, body |
| 4 | Verification | Conditional (by content type) | Confidence markers applied, `research_validated` set |
| 5 | Cross-References & Hierarchy | Yes | Parent document updated, bidirectional refs confirmed |
| 6 | Pre-Publish Checklist | Yes | All 20+ checklist items verified |
| 7 | Output | Yes | File created, user briefed, observations logged |

### 3.1 Phase 2 Research Routing Logic

The skill evaluates each template type against a checklist to determine whether
to invoke `research-orchestrator`. The routing decision is binary — research
runs or it does not. There is no partial research.

| Template | Trigger | Routing Logic |
|----------|---------|--------------|
| `TMPL-001` | Always | Invoke research unconditionally before any drafting |
| `TMPL-002` | Conditional | Invoke if normative language, performance claims, or external spec references are detected |
| `TMPL-003` | Never | Procedures are authoritative; no research needed |
| `TMPL-004` | Conditional | Invoke if named agent patterns, token budgets, or evaluation criteria are referenced |
| `TMPL-005` | Never | Decision documents capture historical fact; no research needed |

### 3.2 Phase 6 Checklist Categories

The pre-publish checklist covers six categories. A document fails the checklist
if any item in any category is not met.

| Category | Items | Blocks Publication? |
|----------|-------|---------------------|
| Structure | 7 items — frontmatter, AI Summary, type tags, heading cadence | Yes |
| Content | 8 items — context anchors, self-containment, formatting rules | Yes |
| Security | 4 items — no credentials, no PII, placeholder syntax | Yes |
| References | 4 items — typed cross-refs, triggers, review_trigger | Yes |
| Verification | 3 items — research_validated, confidence_overall, Verification Record | Yes |
| Hierarchy | 3 items — parent updated, bidirectional refs, Revision History | Yes |

---

## 4. Template Library
[TYPE: REFERENCE]

The template library contains five document type templates. Each is a Markdown
file in `references/` with embedded author-guidance comments (removed during
authoring) and a complete YAML frontmatter schema.

| Template | File | Document Type | Research? | Verification? |
|----------|------|--------------|-----------|---------------|
| `TMPL-001` | `references/TMPL-001.md` | Research & Knowledge Synthesis | Always | Always |
| `TMPL-002` | `references/TMPL-002.md` | Technical Reference | Conditional | Conditional |
| `TMPL-003` | `references/TMPL-003.md` | Procedure & Workflow | Never | Conditional |
| `TMPL-004` | `references/TMPL-004.md` | AI Agent Context & Plan | Conditional | Conditional |
| `TMPL-005` | `references/TMPL-005.md` | Decision Record | Never | Never |

### 4.1 TMPL-000 Conventions File

`references/TMPL-000_conventions.md` is not a document template — it is the
quick-reference conventions guide loaded by the skill during Phase 3 drafting.
It covers: template selection, frontmatter fields, AI Summary format, section
type tags, confidence markers, relationship types, chunking rules, research
trigger criteria, security rules, and the RAG lifecycle for superseded documents.

---

## 5. Configuration Reference
[TYPE: REFERENCE]

### 5.1 CTX-DOCSTD Context File Fields

The `doc-standards.ctx.md` context file is the primary configuration source.
The skill reads this file at the start of every session.

| Field | Required | Purpose |
|-------|----------|---------|
| `Project Name` | Yes | Auto-fills document titles and intent statements |
| `Document Numbering Convention` | Yes | Drives `document_id` assignment in Phase 3.2 |
| `Next available number` | Yes | Prevents duplicate document IDs |
| `Active Parent Documents` | Yes | Drives `parent_document` field in Phase 3.2 |
| `Template Usage History` | No | Tracks documents created in this project |
| `Project-Specific Conventions` | No | Overrides applied on top of RULES-001 |
| `Observations Log` | No | Accumulated observations for skills-updater |
| `Change Log` | No | Context file change history |

### 5.2 SKILL.md Frontmatter Fields

| Field | Value | Notes |
|-------|-------|-------|
| `name` | `ai-documentation` | Skill discovery identifier |
| `version` | `1.1.0` | Semantic version — increment on any meaningful change |
| `allowed-tools` | `Read, Write, web_search` | Pre-approved; no per-invocation permission needed |
| `metadata.integrates_with` | `research-orchestrator, verification` | Informational — not a functional field |

---

## 6. Security Model
[TYPE: REFERENCE]

### 6.1 Credential Handling

The `ai-documentation` skill never stores, outputs, or passes credentials.
The skill's `web_search` tool is used only for research queries (Phase 2).
No authentication credentials are handled by the skill itself.

### 6.2 Document Content Security Enforcement

The skill enforces document content security rules through the Phase 6 checklist.
The security checklist category (4 items) blocks publication if:
- Any credential, password, API key, or token appears in the document body
- Real PII appears in examples without placeholder substitution
- Production-environment values appear without placeholder syntax

### 6.3 Sensitive Content Rules for This Document
[TYPE: REFERENCE]

This technical reference documents the skill's architecture using placeholder
values for all configuration examples. No production values appear in this
document.
✅ `[VERIFIED — TMPL-000 conventions, Section 10, v1.1]`

---

## 7. Integration Points
[TYPE: REFERENCE]

### 7.1 Upstream Dependencies (Skills Called by ai-documentation)

| Skill | Purpose | Phase Called | Condition |
|-------|---------|-------------|-----------|
| `research-orchestrator` | Execute web research; return tier-classified, verified findings | Phase 2 | TMPL-001 always; TMPL-002/004 conditional |
| `verification` | Apply Truth & Verification Standards to document content; return confidence markers | Phase 4 | Any document with external factual claims |

### 7.2 Downstream Consumers (Skills That Use This Skill's Output)

| Consumer | What It Uses |
|----------|-------------|
| `skills-updater` | Reads `pending_observations` from CTX-DOCSTD; proposes skill updates |
| Human authors | Use produced documents as project knowledge |
| AI agents (any) | Retrieve produced documents from vector index for reasoning context |

### 7.3 Context File Interactions

| Context File | Read | Write | When |
|-------------|------|-------|------|
| `doc-standards.ctx.md` | Yes | Observations only | Every session |
| `context-manifest.ctx.md` | Yes | Yes (TMPL-004 entries) | When creating TMPL-004 documents |

---

## 8. Constraints & Limits
[TYPE: REFERENCE]

### 8.1 SKILL.md Specification Constraints

| Constraint | Value | Source |
|-----------|-------|--------|
| Max description field length | 1,024 characters | Anthropic SKILL.md spec v1 |
| Description person | Third person required | Anthropic SKILL.md spec v1 |
| Supported frontmatter fields | `name`, `description`, `version`, `allowed-tools`, `metadata`, `compatibility`, `mode`, `disable-model-invocation`, `user-invocable` | Anthropic SKILL.md spec v1 |
| `integrates_with` field | Not supported in frontmatter — use `metadata` | Anthropic SKILL.md spec v1 |

### 8.2 Template Library Constraints

| Constraint | Value | Notes |
|-----------|-------|-------|
| Templates supported | 5 (TMPL-001 through TMPL-005) | TMPL-004 to be split into three variants in Phase 2 |
| Hybrid document rule | Split into two documents; never merge templates | Hard rule — no exceptions |
| Minimum triggers per document | 3 | Enforced by Phase 6 checklist |
| Minimum negative_triggers per document | 2 | Enforced by Phase 6 checklist |

### 8.3 Operational Constraints

| Constraint | Value | Notes |
|-----------|-------|-------|
| CTX-DOCSTD staleness threshold | 30 days | Older context files are flagged for user confirmation |
| Context anchor requirement | One per `##` section | Enforced by Phase 6 Content checklist |
| Chunk size target | 300–500 words per `##` section | Token-aware guidance — see TMPL-000 Section 7 |

---

## 9. Sources & Cross-References
[TYPE: REFERENCE]

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DEPENDS_ON] | PROJ-001 AI-Optimized Documentation Protocol | All | Parent project — this skill implements PROJ-001's implementation plan |
| [DEPENDS_ON] | RULES-001 Documentation Standards | All | Structural governance layer that the skill enforces |
| [DEPENDS_ON] | TMPL-000_conventions.md v1.1 | All | Conventions reference loaded during every Phase 3 session |
| [IMPLEMENTS] | PROJ-001 | Section 9.4 | This skill implements the Phase 4 skill integration planned in PROJ-001 |
| [SEE_ALSO] | EX-001 RAG Chunking Research | All | Research basis for the chunking conventions the skill enforces |
| [SEE_ALSO] | EX-005 Decision Record: Chunking Strategy | All | Decision record for the chunking approach the skill applies |

---

## 10. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-04 | Initial | Document created | Phase 1 WP-1.5 — TMPL-002 example, self-documenting the ai-documentation skill using the skill's own template |

### Change Detail: v1.0.0 → v1.1.0 (SKILL.md)

**Date:** 2026-04-04
**Sections Changed:** SKILL.md frontmatter, Phase 2, Phase 3.2, Phase 6

**Changes Made:**
- Removed non-standard `integrates_with` frontmatter field; moved to `metadata`
- Added `allowed-tools` field per Anthropic SKILL.md spec
- Rewrote description field to third person; verified under 1,024 characters
- Replaced vague research trigger criteria with precise checklists
- Added `template_version_used` frontmatter field to Phase 3.2 guidance
- Added Security checklist category to Phase 6
- Added Contradiction detection protocol to special cases
- Added Multi-step hybrid document creation guidance

**Impact on Other Documents:**
- TMPL-000_conventions.md updated with matching changes (Section 7, 8, 10, 11, 12)
- TMPL-002 and TMPL-003 updated with security sections (Section 6.4, 1.4 respectively)
- TMPL-005 updated with RAG-specific supersession steps (Section 8.3)

---

*Template: TMPL-002 Technical Reference v1.1 | Parent: PROJ-001 AI-Optimized Documentation Protocol*
