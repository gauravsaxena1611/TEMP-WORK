# AI-Readiness Test Protocol
## Scoring Rubric for Document AI-Optimization Compliance

```yaml
---
document_id: "AI-Readiness-Test-Protocol"
title: "AI-Readiness Test Protocol — Document Scoring Rubric"
version: "1.0"
created: "2026-04-04"
status: "Final"
parent_document: "PROJ-001 AI-Optimized Documentation Protocol"
template_version_used: "TMPL-002 v1.1"

intent: >
  Enable authors, reviewers, and AI agents to score any document in this
  project on a 100-point rubric that measures AI-optimization compliance
  across six dimensions, producing an objective readiness verdict and
  a prioritized remediation list.

consumption_context:
  - human-reading
  - ai-reasoning
  - agentic-execution

triggers:
  - "AI readiness test"
  - "score this document for RAG"
  - "document quality check"
  - "AI-readiness rubric"
  - "is this document AI-ready"
  - "document compliance score"
  - "run readiness test on this document"

negative_triggers:
  - "pre-publish checklist" # → SKILL.md Phase 6 (quick pass/fail, not scored)
  - "how to write a document" # → TMPL-000 conventions

volatility: "stable"
research_validated: true
research_validated_date: "2026-04-04"
research_queries_used:
  - "RAG document quality metrics structured technical docs 2026"
  - "LLM retrieval accuracy document structure factors"

review_trigger: "When TMPL-000 conventions are updated in a way that changes scoring criteria"

confidence_overall: "high"
confidence_note: "Rubric dimensions derived from TMPL-000 conventions and EX-001 RAG research synthesis"
---
```

---

> ## 🤖 AI Summary
> **Core Purpose:** A 100-point scoring rubric that produces an objective AI-readiness score for any document in this project, with dimension breakdowns and a remediation priority list.
> **Six Dimensions:** Frontmatter Completeness (20 pts) · Chunking Quality (25 pts) · Confidence Markers (20 pts) · Cross-Reference Integrity (15 pts) · Negative Space Compliance (10 pts) · Security Compliance (10 pts)
> **Pass Thresholds:** 80/100 for human-read documents; 90/100 for agent-consumed documents
> **Trust Level:** HIGH — rubric derived from verified conventions (TMPL-000 v1.1) and EX-001 research
> **Do NOT Use This For:** Quick pre-publish checklist (use SKILL.md Phase 6 instead); template selection guidance
> **Review By:** When TMPL-000 conventions update scoring-relevant sections

---

## TABLE OF CONTENTS

1. [How to Run the Test](#1-how-to-run-the-test)
2. [Dimension 1 — Frontmatter Completeness (20 pts)](#2-dimension-1--frontmatter-completeness-20-pts)
3. [Dimension 2 — Chunking Quality (25 pts)](#3-dimension-2--chunking-quality-25-pts)
4. [Dimension 3 — Confidence Markers (20 pts)](#4-dimension-3--confidence-markers-20-pts)
5. [Dimension 4 — Cross-Reference Integrity (15 pts)](#5-dimension-4--cross-reference-integrity-15-pts)
6. [Dimension 5 — Negative Space Compliance (10 pts)](#6-dimension-5--negative-space-compliance-10-pts)
7. [Dimension 6 — Security Compliance (10 pts)](#7-dimension-6--security-compliance-10-pts)
8. [Scoring Worksheet](#8-scoring-worksheet)
9. [Interpreting Results](#9-interpreting-results)
10. [Remediation Priority Guide](#10-remediation-priority-guide)
11. [Cross-References](#11-cross-references)
12. [Revision History](#12-revision-history)

---

## 1. How to Run the Test
[TYPE: PROCEDURE]

The AI-Readiness Test scores any project document against six dimensions.
The test can be run by a human reviewer, by the `ai-documentation` skill
when requested, or as part of a documentation health check.

**Running the test manually:**
1. Open the document to be tested
2. Work through Dimensions 1–6 in Section 2–7, scoring each criterion
3. Record scores in the Scoring Worksheet (Section 8)
4. Calculate the total and apply the verdict from Section 9
5. Generate a remediation list for any dimension scoring below 60% of its maximum

**Running the test via the ai-documentation skill:**
```
"Run the AI-readiness test on [document name / paste document]"
or
"Score this document for AI optimization"
```

The skill will evaluate the document against this rubric and return:
- Total score with dimension breakdown
- Pass/Fail verdict for the document's consumption context
- Ordered remediation list (highest-impact fixes first)

**Test scope:** Score one document at a time. For corpus-level scoring,
run the test on each document and aggregate in the Documentation Health
Score (WP-4.3 deliverable, available in Phase 4).

---

## 2. Dimension 1 — Frontmatter Completeness (20 pts)
[TYPE: REFERENCE]

The frontmatter is the machine-readable identity card of every document.
Incomplete frontmatter prevents correct routing, retrieval, and staleness
management. Each criterion is scored as: Full (all points) / Partial (half) / Missing (0).

| Criterion | Max Points | Full Credit | Partial Credit | No Credit |
|-----------|-----------|-------------|----------------|-----------|
| All required YAML fields present with no placeholder values | 5 | All fields filled, zero `{{placeholder}}` remaining | 1–2 placeholders remaining | 3+ placeholders or entire block missing |
| `template_version_used` field present and populated | 2 | Field present, value like "TMPL-001 v1.1" | Field present but generic value | Field missing |
| `triggers` contains ≥3 specific, distinct query phrases | 4 | 3+ specific triggers (not generic) | 2 triggers or triggers are too generic | 0–1 triggers |
| `negative_triggers` contains ≥2 specific exclusion phrases | 3 | 2+ specific negative triggers | 1 negative trigger | Missing |
| `review_trigger` is set to a date or named condition (not blank) | 3 | Specific date or named condition | "When things change" (vague) | Blank or "N/A" |
| `confidence_overall` is accurate (not defaulted to "high") | 3 | Matches actual verification status | Slightly optimistic but defensible | "high" on unverified content |

**Dimension 1 Maximum: 20 points**

---

## 3. Dimension 2 — Chunking Quality (25 pts)
[TYPE: REFERENCE]

Chunking quality is the most directly impactful dimension for RAG
retrieval accuracy. Documents with poor chunking retrieve correctly
but give the LLM insufficient or misleading context to generate correct answers.
✅ `[VERIFIED — EX-001 RAG Chunking Research, Section 6.1]`

| Criterion | Max Points | Full Credit | Partial Credit | No Credit |
|-----------|-----------|-------------|----------------|-----------|
| Every `##` section is 300–500 words (token-aware target) | 6 | All sections in range | <20% of sections out of range | >20% of sections out of range |
| Every `##` section opens with a context anchor sentence naming the subject | 6 | All sections have anchor sentences | >75% have anchor sentences | <75% have anchor sentences |
| No section exceeds 600 words | 4 | All sections under 600 words | 1 section over 600 words | 2+ sections over 600 words |
| Every table and code block is preceded by a context sentence | 4 | All tables/blocks have context sentences | >75% have context sentences | <75% have context sentences |
| No cross-section pronouns ("this step", "it", "the above") | 3 | Zero cross-section pronouns | 1–2 instances | 3+ instances |
| All bullet lists are preceded by an introductory sentence | 2 | All lists have intro sentences | >75% have intro sentences | <75% have intro sentences |

**Dimension 2 Maximum: 25 points**

---

## 4. Dimension 3 — Confidence Markers (20 pts)
[TYPE: REFERENCE]

Confidence markers tell AI agents how much to trust each claim and whether
to seek corroboration before acting. Missing markers on external claims
force the agent to make unguided trust judgments.

| Criterion | Max Points | Full Credit | Partial Credit | No Credit |
|-----------|-----------|-------------|----------------|-----------|
| All external factual claims (from research, specs, benchmarks) carry a confidence marker | 8 | 100% of external claims marked | 75–99% marked | <75% marked |
| Confidence level assigned is accurate (HIGH only for Tier 1/2 verified claims) | 4 | All HIGH claims are genuinely verified; MEDIUM and LOW used appropriately | 1–2 over-confident claims | Systematic over-confidence |
| `research_validated` and `research_validated_date` fields set accurately | 4 | Both fields set correctly (true + date OR false) | One field set, other missing | Both missing or contradictory |
| Verification Record section present (required for TMPL-001; optional others) | 4 | Present and complete (TMPL-001); present if document has research content | Present but incomplete | Missing when it should exist |

**Dimension 4 Maximum: 20 points**

---

## 5. Dimension 4 — Cross-Reference Integrity (15 pts)
[TYPE: REFERENCE]

Cross-references create a navigable knowledge graph. Broken or untyped
references degrade the graph and mislead AI agents that follow references
expecting to find the declared content.

| Criterion | Max Points | Full Credit | Partial Credit | No Credit |
|-----------|-----------|-------------|----------------|-----------|
| All cross-references use the typed relationship format `[RELATIONSHIP] → [Doc ID, Section]` | 5 | All references typed | >75% typed | <75% typed |
| All referenced documents exist and are accessible | 5 | All referenced documents verifiably exist | 1 reference points to missing document | 2+ references broken |
| Bidirectional references are in place (if Doc A references Doc B, Doc B references Doc A) | 3 | All applicable bidirectional refs confirmed | >75% bidirectional | <75% bidirectional |
| Parent document has been updated with a reference to this document | 2 | Parent updated with entry in document index | Parent has partial entry | Parent not updated |

**Dimension 4 Maximum: 15 points**

---

## 6. Dimension 5 — Negative Space Compliance (10 pts)
[TYPE: REFERENCE]

Negative space compliance measures how well a document avoids the anti-patterns
documented in TMPL-000 Section 13. Each anti-pattern found costs points.

| Anti-Pattern Found | Points Deducted |
|-------------------|----------------|
| Vague superlatives ("robust", "seamlessly", "cutting-edge") without evidence | −1 per occurrence (max −3) |
| Sourceless normative claims ("best practices recommend" with no source) | −1 per occurrence (max −3) |
| Vague cross-references ("see the documentation") | −1 per occurrence (max −2) |
| Question-form headings ("## What is X?") | −1 per occurrence (max −2) |
| Dense paragraphs >5 sentences | −1 per occurrence (max −2) |
| Hedging chains (3+ qualifiers on one claim) | −1 per occurrence (max −2) |
| Version-ambiguous claims (no version specified for versioned system) | −1 per occurrence (max −2) |

**Scoring:** Start at 10 points. Deduct for each instance found. Minimum 0.

**Dimension 5 Maximum: 10 points**

---

## 7. Dimension 6 — Security Compliance (10 pts)
[TYPE: REFERENCE]

Security compliance is binary per criterion — either the violation exists
or it doesn't. Any criterion scored at zero is a **blocking failure**: the
document cannot be published until the violation is corrected, regardless
of total score.
✅ `[VERIFIED — TMPL-000 conventions Section 10, v1.1]`

| Criterion | Max Points | Pass | Fail (Blocking) |
|-----------|-----------|------|----------------|
| No credentials, passwords, API keys, or tokens in document body | 4 | Zero instances | Any instance — document blocked from publication |
| No real PII in examples (all replaced with `<PLACEHOLDER>` syntax) | 3 | Zero real PII instances | Any real PII — document blocked |
| No production-specific values in configuration examples | 2 | All examples use placeholder syntax | Any production value — document blocked |
| Regulated industry: disclosure-restricted information excluded (if applicable) | 1 | Excluded or N/A | Present — document blocked |

**Dimension 6 Maximum: 10 points**

⚠️ Any Dimension 6 criterion scoring 0 blocks publication regardless of total score.

---

## 8. Scoring Worksheet
[TYPE: REFERENCE]

Use this worksheet to record scores and calculate totals.

```
DOCUMENT: [Document ID and Title]
TESTER: [Name or "ai-documentation skill"]
DATE: [YYYY-MM-DD]
CONSUMPTION CONTEXT: [human-read / agent-consumed / both]

DIMENSION SCORES
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
D1 — Frontmatter Completeness    _____ / 20
     D1.1 Required fields             _____ / 5
     D1.2 template_version_used       _____ / 2
     D1.3 Triggers (≥3)               _____ / 4
     D1.4 Negative triggers (≥2)      _____ / 3
     D1.5 review_trigger set          _____ / 3
     D1.6 confidence_overall accurate _____ / 3

D2 — Chunking Quality            _____ / 25
     D2.1 Section length 300–500w     _____ / 6
     D2.2 Context anchor sentences    _____ / 6
     D2.3 No section >600 words       _____ / 4
     D2.4 Tables/code have context    _____ / 4
     D2.5 No cross-section pronouns   _____ / 3
     D2.6 Lists have intro sentences  _____ / 2

D3 — Confidence Markers          _____ / 20
     D3.1 All external claims marked  _____ / 8
     D3.2 Confidence level accurate   _____ / 4
     D3.3 research_validated set      _____ / 4
     D3.4 Verification Record         _____ / 4

D4 — Cross-Reference Integrity   _____ / 15
     D4.1 Typed relationship format   _____ / 5
     D4.2 Referenced docs exist       _____ / 5
     D4.3 Bidirectional refs          _____ / 3
     D4.4 Parent updated              _____ / 2

D5 — Negative Space Compliance   _____ / 10
     Start: 10, deduct per instance
     Instances found: [list]
     Total deductions: ─────────────  _____

D6 — Security Compliance         _____ / 10
     D6.1 No credentials              _____ / 4  [BLOCKING if 0]
     D6.2 No PII in examples          _____ / 3  [BLOCKING if 0]
     D6.3 No production values        _____ / 2  [BLOCKING if 0]
     D6.4 Regulated content excluded  _____ / 1  [BLOCKING if 0, when applicable]
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TOTAL SCORE                      _____ / 100

BLOCKING FAILURES (D6 zeros): [List any / None]
```

---

## 9. Interpreting Results
[TYPE: REFERENCE]

### 9.1 Pass Thresholds

| Context | Threshold | Rationale |
|---------|-----------|-----------|
| Human-read documents (consumption_context: human-reading only) | **80 / 100** | Human readers tolerate imperfect structure better than agents |
| Agent-consumed documents (consumption_context includes ai-reasoning or agentic-execution) | **90 / 100** | Agents act on documents without critical judgment — quality must be higher |
| Living context documents (TMPL-004C) | **90 / 100** | Agents update these documents — errors compound over time |

### 9.2 Score Interpretation Table

| Total Score | Label | Meaning |
|-------------|-------|---------|
| 95–100 | **Excellent** | Fully optimized. Publish without changes. |
| 90–94 | **Strong Pass** | Minor improvements possible but not required for publication. |
| 80–89 | **Pass (human-read)** | Acceptable for human-read documents. Not acceptable for agent-consumed. |
| 70–79 | **Conditional** | Publish only if urgency demands it. Schedule remediation within 2 weeks. |
| 60–69 | **Weak — Remediate Before Use** | Do not publish for agent consumption. Remediate top two dimensions. |
| Below 60 | **Fail** | Do not publish. Significant remediation required. |

### 9.3 Blocking Failures Override Total Score

If any Dimension 6 criterion scores 0 (a security violation), the document
is blocked from publication regardless of its total score. A document
scoring 95/100 with a credential violation is still blocked.

---

## 10. Remediation Priority Guide
[TYPE: REFERENCE]

When a document fails or scores below threshold, prioritize remediation
in this order (highest impact per unit of effort):

| Priority | Dimension | Why This First |
|----------|-----------|----------------|
| 1 | **D6 Security (any blocking failure)** | Legal/privacy requirement — publish-blocking |
| 2 | **D2 Chunking Quality — context anchor sentences** | Highest RAG retrieval impact; fastest to fix |
| 3 | **D1 Frontmatter — triggers and review_trigger** | Enables correct retrieval routing; 15 min to fix |
| 4 | **D3 Confidence markers on external claims** | Agent trust calibration; fix while reviewing content |
| 5 | **D2 Chunking Quality — section length** | Requires splitting or expanding sections; medium effort |
| 6 | **D4 Cross-references — typed relationships** | Find-and-replace; low effort, high signal |
| 7 | **D5 Negative space — sourceless normative claims** | Requires sourcing or downgrading claims; medium effort |
| 8 | **D4 Bidirectional references** | Low impact per fix, but compounds across corpus |
| 9 | **D5 Negative space — vague superlatives** | Low effort, steady quality improvement |

**Quick wins (fix in <15 min each):**
- Add `review_trigger` to frontmatter (D1.5)
- Add `template_version_used` to frontmatter (D1.2)
- Add 2+ negative triggers (D1.4)
- Add relationship types to existing cross-references (D4.1)
- Add introductory sentences to bullet lists (D2.6)

---

## 11. Cross-References
[TYPE: REFERENCE]

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [VALIDATED_BY] | EX-001 RAG Chunking Research | Section 3.1, 3.3 | Research basis for Dimension 2 scoring criteria |
| [DEPENDS_ON] | TMPL-000_conventions.md v1.1 | Sections 7, 10, 13 | Conventions that define the scoring criteria |
| [IMPLEMENTS] | PROJ-002 Enhancement Roadmap | WP-2.8 | This document is the WP-2.8 deliverable |
| [SEE_ALSO] | SKILL.md Phase 6 | Pre-Publish Checklist | Quick pass/fail checklist (not scored); run before this protocol |

---

## 12. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-04 | Initial | Document created | Phase 2 WP-2.8 — AI-Readiness Test Protocol (100-point scoring rubric across 6 dimensions) |

---

*Template: TMPL-002 Technical Reference v1.1 | Parent: PROJ-001 AI-Optimized Documentation Protocol*
