# Decision Record: RAG Chunking Strategy for AI Documentation System
## Architecture: Token-Aware Recursive Markdown-Header Chunking Adopted

```yaml
---
document_id: "EX-005 Decision-Chunking-Strategy"
title: "Decision: Token-Aware Recursive Markdown-Header Chunking Adopted as Primary RAG Strategy"
version: "1.0"
created: "2026-04-04"
status: "Accepted"
parent_document: "PROJ-001 AI-Optimized Documentation Protocol"
template_version_used: "TMPL-005 v1.1"

decision_date: "2026-04-04"
decision_makers: ["Project Team", "ai-documentation Skill v1.1.0"]
decision_type: "architecture"
applies_to: "All documents produced by the ai-documentation skill in this project"
supersedes: ""
superseded_by: ""

intent: >
  Enable documentation authors and AI agents to understand what chunking
  strategy was formally adopted for this project's documentation corpus,
  why it was selected over alternatives, and what constraints it creates
  for document authoring.

consumption_context:
  - human-reading
  - ai-reasoning
  - rag-retrieval

triggers:
  - "why do we use 300 to 500 words per section"
  - "what chunking strategy was decided for this project"
  - "why token-aware chunking"
  - "chunking decision rationale"
  - "why recursive header chunking"
  - "why not semantic chunking"

negative_triggers:
  - "what is the optimal chunk size generally" # → EX-001 research synthesis
  - "how to implement chunking in a vector database" # → TMPL-003 procedure

volatility: "snapshot"
research_validated: false
review_trigger: "When EX-001 RAG Chunking Research is re-researched and produces findings that materially differ from current conventions"

confidence_overall: "high"
confidence_note: "Decision is authoritative — reflects actual project decision made on 2026-04-04 based on research synthesis EX-001"
---
```

---

> ## 🤖 AI Summary
> **Decision:** The project adopted token-aware recursive Markdown-header chunking, targeting 300–500 words (≈ 400–500 tokens) per `##` section, with mandatory context anchor sentences and optional contextual retrieval headers for embedding.
> **Status:** ACCEPTED
> **Date:** 2026-04-04
> **Scope:** All documents produced by or conforming to the ai-documentation skill and TMPL-000 conventions
> **Key Constraint Created:** Section length targets are now token-based (300–500 words), not the original word-count rule (150–200 words). Documents written under the old rule are technically non-compliant and should be updated on next revision.
> **Cannot Be Changed Without:** A new TMPL-005 decision record, triggered by materially new research findings from EX-001 re-research
> **Supersedes:** None (replaces an informal word-count convention from TMPL-000 v1.0, which was not a formal decision record)
> **Superseded By:** None

---

## TABLE OF CONTENTS

1. [Decision Statement](#1-decision-statement)
2. [Context & Problem](#2-context--problem)
3. [Decision Drivers](#3-decision-drivers)
4. [Options Considered](#4-options-considered)
5. [Decision Rationale](#5-decision-rationale)
6. [Consequences & Constraints](#6-consequences--constraints)
7. [Implementation Notes](#7-implementation-notes)
8. [Review Conditions](#8-review-conditions)
9. [Cross-References](#9-cross-references)
10. [Revision History](#10-revision-history)

---

## 1. Decision Statement
[TYPE: DECISION]

### 1.1 The Decision

**The project adopted recursive Markdown-header chunking with a 300–500 word
(approximately 400–500 token) target per `##` section as the primary RAG
chunking strategy for all ai-documentation skill output.**

All documents produced by the `ai-documentation` skill must be authored
with section lengths in the 300–500 word range, opened by a context anchor
sentence naming the subject, and structured to be self-contained when
retrieved in isolation. The previous informal 150–200 word rule is retired.
Documents conforming to the old rule are non-compliant and should be
updated on their next revision.

### 1.2 Status

| Attribute | Value |
|-----------|-------|
| **Status** | ACCEPTED |
| **Date Made** | 2026-04-04 |
| **Made By** | Project Team (Phase 1 execution) |
| **Applies To** | All documents produced using the ai-documentation skill; all documents conforming to TMPL-000 conventions |
| **Effective Date** | 2026-04-04 — immediate |

### 1.3 The Resulting Constraint

**Because of this decision:**

All `##` sections in ai-documentation skill output must target 300–500 words.
Sections shorter than 200 words are acceptable only when semantic completeness
is achieved at that length. Sections longer than 600 words must be split.
Every section must open with a context anchor sentence that explicitly names
the document subject, system, or product. All documents authored before
2026-04-04 that contain sections in the 150–200 word range are
non-compliant and must be updated on their next content revision.

---

## 2. Context & Problem
[TYPE: EXPLANATION]

### 2.1 The Problem Being Solved

The original TMPL-000 conventions (v1.0) specified a chunking rule of
"New `##` heading every 150–200 words." This rule was designed from first
principles without research validation. The Phase 1 gap analysis
(Gap G-02 in PROJ-002) identified that this rule was calibrated in the
wrong unit: RAG systems operate in tokens, not words. At approximately
0.75 tokens per English word, the 150–200 word rule produces chunks of
200–270 tokens — significantly below the 256-token floor established by
2025–2026 benchmark research and the 400–512 token production sweet spot.

Documents written to the old rule produce chunks that retrieve correctly
(small chunks have good precision) but give the retrieval model insufficient
context to generate accurate answers — the exact failure mode documented
in the FloTorch benchmark study.

### 2.2 Why a Decision Was Needed

A formal decision record was needed because:
- The change affects all documents already written using the skill
- Any document author using TMPL-000 conventions needed a clear, authoritative
  rule — not an informal comment revision
- The new rule required research backing, which is captured in EX-001 and cited here
- Future changes to the rule require a superseding decision record — this
  establishes the baseline

### 2.3 Timeline

| Date | Event |
|------|-------|
| 2026-04-01 | PROJ-001 created; original 150–200 word rule embedded in TMPL-000 v1.0 |
| 2026-04-04 | Gap G-02 identified in PROJ-002 gap analysis — rule calibrated to wrong unit |
| 2026-04-04 | EX-001 research synthesis completed — confirms 300–500 word target |
| 2026-04-04 | This decision formalised; TMPL-000 v1.1 updated accordingly |

---

## 3. Decision Drivers
[TYPE: REFERENCE]

### 3.1 Primary Drivers (Must-Have)

| Driver | Requirement | Why Non-Negotiable |
|--------|-------------|-------------------|
| Token-calibration | Rule must be expressed in token-appropriate units | RAG systems operate in tokens; a word-count rule that produces sub-optimal token chunks defeats the purpose of the convention |
| Research backing | Rule must be grounded in independent benchmark evidence | The system's credibility depends on its conventions being evidence-based, not first-principles guesses |
| Semantic completeness | Each chunk must contain a complete, independently useful idea | A chunk that is too small provides insufficient answer context; this is the specific failure the rule must prevent |

### 3.2 Secondary Drivers (Strong Preference)

| Driver | Preference | Weight |
|--------|-----------|--------|
| Backward compatibility | Old documents should not be immediately broken — only flagged for update on revision | MEDIUM |
| Author simplicity | The rule should be expressible in words (the unit authors think in), not raw token counts | MEDIUM |
| Structural alignment | The rule should reinforce Markdown heading discipline already in RULES-001 | HIGH |

### 3.3 Constraints

- Rule must be expressible in words (token counts are difficult for human authors to estimate)
- Must not require specialised tooling to apply — any author writing Markdown must be able to comply without a token counter
- Must be compatible with the existing five template types (TMPL-001 through TMPL-005)

### 3.4 What Was NOT a Driver

Minimising total document length was not a driver. The longer sections
produced by this rule (300–500 words vs 150–200 words) increase document
length. This is an accepted consequence — retrieval quality takes precedence
over document brevity.

---

## 4. Options Considered
[TYPE: REFERENCE]

#### Option A: Recursive Markdown-Header Chunking, 300–500 words ✅ CHOSEN

**Description:** Split documents at `##` heading boundaries. Target 300–500
words per section. Enforce context anchor sentences. Add contextual retrieval
headers before embedding (optional, for vector database users).

**Pros:**
- Directly addresses Gap G-02 — token-calibrated to the 400–512 token sweet spot
- Backed by the highest-performing method in the FloTorch benchmark (69% accuracy)
- Preserves author-defined section structure — aligns with document intent
- Author-friendly — word count is a practical guide; no token counter needed

**Cons:**
- Longer sections increase total document length
- Non-compliant documents (written to old 150–200 word rule) need retroactive update on revision

---

#### Option B: Keep 150–200 word rule ❌ REJECTED

**Description:** Retain the existing rule from TMPL-000 v1.0.

**Why rejected:** The rule produces chunks of approximately 200–270 tokens,
which is below the 256-token retrieval floor established by the Vectara/NAACL
2025 benchmark (Primary Driver: token-calibration). Retaining it would mean
the conventions system actively produces sub-optimal RAG output, contradicting
the system's stated purpose.

---

#### Option C: Fixed token-count rule (e.g., "exactly 400 tokens per section") ❌ REJECTED

**Description:** Express the rule directly in tokens, requiring authors to
count tokens as they write.

**Why rejected:** Fails the "author simplicity" constraint. Requiring authors
to count tokens while writing is impractical without specialised tooling.
Word count approximation (300–500 words ≈ 400–500 tokens) achieves the same
result without tooling requirements.

---

#### Option D: Semantic chunking at topic boundaries ❌ REJECTED

**Description:** Replace all heading-based guidance with a rule to split
at semantic topic boundaries, regardless of heading structure.

**Why rejected:** The FloTorch benchmark demonstrates that for structured
technical documentation, semantic chunking underperforms recursive header
chunking by 15 percentage points (54% vs 69% accuracy). This project produces
structured technical documentation exclusively — semantic chunking is the
lower-performing method for this document type.

---

## 5. Decision Rationale
[TYPE: DECISION]

### 5.1 Why Recursive Markdown-Header Chunking Was Selected

**On token-calibration:** The 300–500 word target converts to approximately
400–500 tokens — directly within the 400–512 token sweet spot confirmed by
three independent Tier 1/2 sources. No other evaluated option achieves
this calibration without requiring specialised tooling.

**On research backing:** Option A is the only option directly supported by
independent benchmark evidence. The FloTorch and Vectara/NAACL 2025 studies
both confirm recursive header-based chunking as the highest-performing method
for structured technical documentation. The decision is grounded in research,
not convention.

**On author simplicity:** Word count is a practical proxy that every author
can apply without tooling. The approximation (0.75 tokens per word) is
well-established and introduces acceptable imprecision — a section of 320
words might be 390 tokens or 410 tokens, both well within the target range.

### 5.2 Trade-offs Accepted

| Trade-off | Why Accepted | Mitigation |
|----------|-------------|------------|
| Documents are longer (300–500 words vs 150–200 words per section) | Retrieval quality over document brevity — the system's purpose is effective AI retrieval, not compact documents | No mitigation needed — this is the correct trade-off |
| Non-compliant legacy documents need retroactive updates | The old rule is incorrect; documents written to it produce sub-optimal chunks. Update on revision is a reasonable grace period. | Flag non-compliant documents in the documentation health score (Phase 4) |

### 5.3 Assumptions Made

| Assumption | Risk If False | Monitoring |
|-----------|--------------|------------|
| 0.75 tokens per English word is a reliable approximation | If actual token density differs significantly, the word-count guidance will not achieve the target token range | Monitor EX-001 re-research; if token density varies significantly by document type, update the approximation |
| 400–512 tokens remains the production sweet spot as models evolve | Future models with larger effective context windows may shift the optimal range | Annual review of EX-001 research synthesis; update decision when range shifts materially |

---

## 6. Consequences & Constraints
[TYPE: REFERENCE]

### 6.1 Positive Consequences

- Documents produced by the skill are optimally sized for vector database retrieval
- Retrieved chunks provide sufficient context for LLMs to generate accurate answers
- Authoring guidance is now research-backed rather than first-principles

### 6.2 Negative Consequences

- All documents authored under TMPL-000 v1.0 (150–200 word rule) are technically
  non-compliant and require updating on their next content revision
- Individual sections in documents conforming to this rule will be approximately
  twice as long as under the previous convention

### 6.3 Constraints Created

**The following are NOW REQUIRED by this decision:**
- Every `##` section in ai-documentation skill output must target 300–500 words
- Every `##` section must open with a context anchor sentence naming the subject
- TMPL-000_conventions.md Section 7 is the authoritative chunking guidance
- Documents written before 2026-04-04 must migrate to this rule on their next content revision

**The following are NOW PROHIBITED by this decision:**
- The 150–200 word heading cadence rule from TMPL-000 v1.0 — it is retired
- Sections shorter than 200 words are only acceptable when semantic completeness
  is genuinely achieved at that length (not as a convenience shortcut)

**Exception process:** Document authors who believe a specific document type
genuinely requires different section lengths may raise a question for the
project team. If warranted, a domain variant guidance section can be added
to TMPL-000 — this does not require a new decision record.

### 6.4 Impact on Existing Documents

| Document | Impact | Action Required |
|----------|--------|----------------|
| TMPL-000_conventions.md | Section 7 rewritten — complete | Done (v1.1) |
| TMPL-001 through TMPL-005 | Internal author-guidance comments referenced old rule | Update comment text on next template revision |
| All pre-existing project documents written under v1.0 rule | Non-compliant | Update on next content revision — not emergency retroactive change |

---

## 7. Implementation Notes
[TYPE: PROCEDURE]

### 7.1 Implementation Approach

The decision is implemented through TMPL-000_conventions.md Section 7 (v1.1),
which is loaded by the `ai-documentation` skill during every Phase 3 drafting
session. No additional tooling is required. Authors applying the skill automatically
receive the correct guidance through the conventions file.

For document authors not using the skill directly, the rule summary is:
target 300–500 words per `##` section, open every section with a context
anchor sentence, and verify self-containment.

### 7.2 Implementation Risks

| Risk | Likelihood | Impact | Mitigation |
|------|-----------|--------|------------|
| Authors continue using the old 150–200 word rule from memory | MEDIUM | MEDIUM — documents are retrievable but sub-optimal | TMPL-000 v1.1 is authoritative; old rule is not referenced anywhere in the updated system |
| Word-count approximation produces sections outside the token range | LOW | LOW — approximation is directionally correct | No active mitigation needed; acceptable imprecision |

### 7.3 Success Indicators

- All new documents produced by the skill have `##` sections in the 300–500 word range
- No sections in new documents open without a context anchor sentence
- Documentation health score (Phase 4 WP-4.3) shows declining count of non-compliant sections over time

---

## 8. Review Conditions
[TYPE: REFERENCE]

### 8.1 Conditions That Would Trigger Re-evaluation

- EX-001 RAG Chunking Research is re-researched (next due October 2026) and
  produces findings where the optimal token range has shifted materially (e.g.,
  to 512–1,024 tokens due to model context window improvements)
- A Tier 1 study directly contradicts the FloTorch 69% vs 54% accuracy finding
  with a different result for structured technical documentation
- The project adopts a specialised embedding model with significantly different
  optimal chunk size characteristics

### 8.2 What Has NOT Changed This Decision

| Date | Review Trigger | Outcome | Reviewer |
|------|---------------|---------|---------|
| 2026-04-04 | Initial adoption | ACCEPTED | Project Team |

### 8.3 Supersession Process

To supersede this decision, complete the six-step supersession process
defined in TMPL-005 Section 8.3 and TMPL-000_conventions.md Section 12.
The most likely trigger is the October 2026 EX-001 re-research finding
a materially different optimal range.

---

## 9. Cross-References
[TYPE: REFERENCE]

### 9.1 Documents That Led to This Decision

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [VALIDATED_BY] | EX-001 RAG Chunking Research | Sections 3.1, 3.2, 3.3 | Research synthesis that provided the evidence base for this decision |
| [DEPENDS_ON] | PROJ-002 Enhancement Roadmap | Gap G-02 | Gap identification that triggered this decision |

### 9.2 Documents Governed by This Decision

| Relationship | Document | Section | How Governed |
|-------------|----------|---------|-------------|
| [IMPLEMENTS] | TMPL-000_conventions.md v1.1 | Section 7 | Conventions file implements this decision as authoring rules |
| [APPLIES] | All documents using TMPL-001 through TMPL-005 | All sections | Section length targets enforced by Phase 6 checklist |
| [APPLIES] | ai-documentation SKILL.md | Phase 3.4, Phase 6 | Skill enforces this rule during drafting and pre-publish |

### 9.3 Decisions Related to This One

| Relationship | Document | Description |
|-------------|----------|-------------|
| [SEE_ALSO] | Future: EX-005 superseding record | To be created if EX-001 re-research triggers a change |

---

## 10. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-04 | Initial | Decision record created | Phase 1 WP-1.5 — TMPL-005 example, formalising the Phase 1 chunking rule decision |

---

*Template: TMPL-005 Decision Record v1.1 | Parent: PROJ-001 AI-Optimized Documentation Protocol*
