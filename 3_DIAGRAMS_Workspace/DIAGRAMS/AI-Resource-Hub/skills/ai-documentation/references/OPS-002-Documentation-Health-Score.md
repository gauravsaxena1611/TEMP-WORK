# Documentation Health Score
## Corpus Audit Mechanism & Health Dashboard Template

```yaml
---
document_id: "OPS-002 Documentation-Health-Score"
title: "Documentation Health Score — Corpus Audit Mechanism"
version: "1.0"
created: "2026-04-04"
status: "Final"
parent_document: "PROJ-001 AI-Optimized Documentation Protocol"
template_version_used: "TMPL-002 v1.1"

intent: >
  Define the documentation corpus health score — five dimensions measuring
  freshness, AI-optimization compliance, cross-reference integrity, template
  currency, and coverage — enabling systematic corpus audits that surface
  degradation before it affects retrieval quality or agent accuracy.

consumption_context:
  - human-reading
  - agentic-execution

triggers:
  - "documentation health score"
  - "corpus audit"
  - "OPS-002"
  - "how healthy is our documentation"
  - "documentation health check"
  - "audit all documents"

negative_triggers:
  - "AI readiness score for one document" # → AI-Readiness-Test-Protocol.md
  - "quarterly research refresh" # → OPS-001

volatility: "stable"
review_trigger: "When new template types are added (add coverage dimension entries); when AI-Readiness-Test-Protocol.md changes pass thresholds"

confidence_overall: "high"
confidence_note: "Audit framework derived from TMPL-000 conventions and AI-Readiness-Test-Protocol"
---
```

---

> ## 🤖 AI Summary
> **Core Purpose:** Defines how to measure and track the health of a project's entire documentation corpus across five dimensions — produces a single composite score and a prioritized remediation list.
> **Five Dimensions:** Freshness (20 pts) · AI-Readiness Compliance (30 pts) · Cross-Reference Integrity (20 pts) · Template Currency (15 pts) · Coverage (15 pts)
> **Total:** 100 points. Target: ≥80 for human-read corpora; ≥90 for agent-consumed corpora.
> **Run Frequency:** Monthly for active projects; quarterly for stable projects.
> **Trust Level:** HIGH — audit framework derived from verified conventions
> **Do NOT Use This For:** Scoring individual documents (use AI-Readiness-Test-Protocol.md); running the quarterly research refresh (use OPS-001)
> **Review By:** When new template types added or AI-Readiness thresholds change

---

## TABLE OF CONTENTS

1. [How to Run a Corpus Audit](#1-how-to-run-a-corpus-audit)
2. [Dimension 1 — Freshness (20 pts)](#2-dimension-1--freshness-20-pts)
3. [Dimension 2 — AI-Readiness Compliance (30 pts)](#3-dimension-2--ai-readiness-compliance-30-pts)
4. [Dimension 3 — Cross-Reference Integrity (20 pts)](#4-dimension-3--cross-reference-integrity-20-pts)
5. [Dimension 4 — Template Currency (15 pts)](#5-dimension-4--template-currency-15-pts)
6. [Dimension 5 — Coverage (15 pts)](#6-dimension-5--coverage-15-pts)
7. [Composite Score & Thresholds](#7-composite-score--thresholds)
8. [Health Score Dashboard Template](#8-health-score-dashboard-template)
9. [Remediation Priority Guide](#9-remediation-priority-guide)
10. [Legacy Document Upgrade Strategy (E-02)](#10-legacy-document-upgrade-strategy-e-02)
11. [Corpus Audit Log](#11-corpus-audit-log)
12. [Cross-References](#12-cross-references)
13. [Revision History](#13-revision-history)

---

## 1. How to Run a Corpus Audit
[TYPE: PROCEDURE]

### 1.1 Scope Definition

A corpus audit measures all documents in a project that are:
- Owned by this project (not referenced-only external documents)
- Status: active or living (not superseded or archived)
- Created using or conformable to the ai-documentation skill templates

**Exclude from audit:**
- Superseded documents (status: superseded) — they are not expected to be current
- External documents (not owned by this project)
- Raw notes and drafts (status: draft) — score separately if desired

### 1.2 Audit Steps

1. **Inventory** — list all in-scope documents with: document ID, template type, `created` date, `review_trigger` date, `template_version_used`
2. **Score each dimension** — work through Dimensions 1–5 for the full corpus
3. **Calculate composite score** — weighted sum per Section 7
4. **Generate remediation list** — Section 9
5. **Record in audit log** — Section 11
6. **Write CTX-DOCSTD observation** — if score below threshold, write observation to CTX-DOCSTD §10

### 1.3 Frequency

| Project Type | Audit Frequency |
|-------------|----------------|
| Active (new documents created weekly) | Monthly |
| Stable (documents created monthly or less) | Quarterly |
| After any major system change | Immediate audit |
| Before any regulatory review | Immediate audit |

---

## 2. Dimension 1 — Freshness (20 pts)
[TYPE: REFERENCE]

Freshness measures whether documents are within their declared review cycles.
A stale document may contain outdated technical information, superseded
decisions, or conventions that have since changed.

### 2.1 Scoring

| Criterion | Max Pts | How to Score |
|-----------|---------|-------------|
| % of documents with `review_trigger` set (not blank) | 5 | (docs with review_trigger set / total docs) × 5 |
| % of `stable` documents reviewed within 12 months | 5 | (in-date stable docs / total stable docs) × 5 |
| % of `fast-changing` documents reviewed within 6 months | 5 | (in-date fast-changing docs / total fast-changing) × 5 |
| % of `living` documents updated within 30 days | 5 | (recently updated living docs / total living docs) × 5 |

**Snapshot and evergreen documents:** Count as always-current (do not reduce score for age).

### 2.2 Data Collection

For each document, record:
```
| Document ID | Volatility | Created | Last Updated | Review Trigger | In-Date? |
```

### 2.3 Freshness Signals That Need Immediate Action

- Any `living` document (TMPL-004C) not updated in >30 days → urgent
- Any `fast-changing` document not reviewed in >6 months → high priority
- `review_trigger` blank on more than 20% of documents → systematic gap

---

## 3. Dimension 2 — AI-Readiness Compliance (30 pts)
[TYPE: REFERENCE]

AI-readiness compliance is the highest-weighted dimension because it directly
affects RAG retrieval quality and agent accuracy. It is measured by applying
the AI-Readiness Test Protocol (TEST-001) to a sample or all documents.

### 3.1 Scoring Method

**Option A — Full corpus scoring** (preferred for small corpora, <20 documents):
Run AI-Readiness-Test-Protocol.md against every in-scope document.
Calculate mean score across all documents.

**Option B — Stratified sample** (for large corpora, >20 documents):
- Sample 5 documents per template type
- For each type: calculate mean AI-readiness score
- Weighted mean across all types by document count

### 3.2 Compliance Scoring

| Mean AI-Readiness Score (corpus) | Points Awarded |
|----------------------------------|----------------|
| ≥90 | 30 (full) |
| 80–89 | 22 |
| 70–79 | 15 |
| 60–69 | 8 |
| <60 | 0 |

### 3.3 Blocking Failures Override

If any document in the corpus has a **Category 6 Security blocking failure**
(score of 0 on any security criterion), the corpus score for Dimension 2
is automatically capped at 15 regardless of overall compliance scores.
Security failures require immediate remediation.

---

## 4. Dimension 3 — Cross-Reference Integrity (20 pts)
[TYPE: REFERENCE]

Cross-reference integrity verifies that the knowledge graph is intact —
that every reference points to a real, active document and that
bidirectional references are in place.

### 4.1 Scoring

| Criterion | Max Pts | How to Score |
|-----------|---------|-------------|
| % of cross-references that use typed relationship format `[TYPE] → [Doc ID]` | 5 | (typed refs / total refs) × 5 |
| % of referenced documents that exist and are active | 8 | (live refs / total refs) × 8 |
| % of applicable cross-references that are bidirectional | 4 | (bidirectional / applicable) × 4 |
| Parent document indexes are complete (all children listed) | 3 | (complete parent indexes / total parents) × 3 |

### 4.2 Data Collection

For each cross-reference in the corpus:
```
| Source Doc | Reference Type | Target Doc | Target Exists? | Typed? | Bidirectional? |
```

### 4.3 Common Integrity Failures

- References to superseded documents without update → fix immediately
- Orphaned documents (no parent reference, not a master document) → add parent
- Untyped references (`[Doc ID]` without relationship type) → add type
- Parent index missing new child documents → update parent

---

## 5. Dimension 4 — Template Currency (15 pts)
[TYPE: REFERENCE]

Template currency measures how many documents are using current template
versions versus older versions that may lack Phase 1–3 enhancements.

### 5.1 Scoring

| Criterion | Max Pts | How to Score |
|-----------|---------|-------------|
| % of documents with `template_version_used` field present | 5 | (docs with field / total docs) × 5 |
| % of documents using the current template version for their type | 7 | (current-version docs / total docs) × 7 |
| % of legacy documents (pre-template-system) flagged for upgrade | 3 | Flat 3 pts if all pre-system docs are identified and flagged; 0 if untracked |

### 5.2 Current Template Versions

Update this table after each template version increment:

| Template | Current Version | Minimum Acceptable |
|----------|----------------|-------------------|
| TMPL-001 | v1.1 | v1.0 (acceptable; v1.1 preferred) |
| TMPL-002 | v1.1 | v1.0 (acceptable; v1.1 preferred) |
| TMPL-003 | v1.1 | v1.0 (acceptable; v1.1 preferred) |
| TMPL-004A | v1.1 | v1.1 (minimum — split from monolithic v1.0) |
| TMPL-004B | v1.1 | v1.1 (minimum) |
| TMPL-004C | v1.1 | v1.1 (minimum) |
| TMPL-005 | v1.1 | v1.0 (acceptable; v1.1 preferred) |
| TMPL-006 | v1.0 | v1.0 (current) |
| TMPL-007 | v1.0 | v1.0 (current) |
| TMPL-008 | v1.0 | v1.0 (current) |

---

## 6. Dimension 5 — Coverage (15 pts)
[TYPE: REFERENCE]

Coverage measures whether the documentation corpus addresses the systems,
decisions, and processes that exist in the project. An incomplete corpus
creates knowledge gaps that agents will fail to fill — they hallucinate
rather than admit uncertainty.

### 6.1 Scoring

Coverage is assessed against a project-defined inventory. Before scoring,
the project maintainer must define what SHOULD be documented:

```
COVERAGE INVENTORY TEMPLATE

Systems that should have a TMPL-002 Technical Reference:
  - [System 1]: documented? YES/NO — Doc ID: [ID]
  - [System 2]: documented? YES/NO

Major decisions that should have a TMPL-005 Decision Record:
  - [Decision 1]: documented? YES/NO — Doc ID: [ID]
  - [Decision 2]: documented? YES/NO

Active AI agents that should have a TMPL-004A Context Brief:
  - [Agent 1]: documented? YES/NO — Doc ID: [ID]

Recurring procedures that should have a TMPL-003 Runbook:
  - [Procedure 1]: documented? YES/NO — Doc ID: [ID]
```

| Criterion | Max Pts | How to Score |
|-----------|---------|-------------|
| % of critical systems with a TMPL-002 document | 5 | (documented / total critical systems) × 5 |
| % of major decisions with a TMPL-005 document | 5 | (documented / total major decisions) × 5 |
| % of recurring procedures with a TMPL-003 runbook | 5 | (documented / total recurring procedures) × 5 |

### 6.2 Coverage Gap Severity

| Coverage Gap Type | Severity | Impact |
|------------------|----------|--------|
| Missing TMPL-002 for a production system | HIGH | Agents cannot answer technical questions about this system |
| Missing TMPL-005 for a major architectural decision | HIGH | Agents don't know why constraints exist; may suggest reversing them |
| Missing TMPL-004A for an active AI agent | MEDIUM | Agent operates without documented constraints |
| Missing TMPL-003 for a critical procedure | HIGH | Agents cannot guide execution of the procedure |

---

## 7. Composite Score & Thresholds
[TYPE: REFERENCE]

### 7.1 Composite Score Calculation

```
Composite Score = D1 (Freshness, /20)
               + D2 (AI-Readiness, /30)
               + D3 (Cross-Reference Integrity, /20)
               + D4 (Template Currency, /15)
               + D5 (Coverage, /15)
               ─────────────────────────
               Total: /100
```

### 7.2 Health Thresholds

| Score | Label | Meaning | Action |
|-------|-------|---------|--------|
| 90–100 | **Excellent** | Corpus is well-maintained | Monitor monthly |
| 80–89 | **Healthy** | Minor improvements needed | Remediate bottom-scoring dimensions within 30 days |
| 70–79 | **Fair** | Meaningful gaps present | Remediate within 14 days; schedule dedicated maintenance |
| 60–69 | **Degraded** | Significant reliability risk | Immediate remediation sprint; flag to team |
| <60 | **Critical** | Agents will produce unreliable output from this corpus | Stop new work until score ≥70 |

### 7.3 Override Conditions

Any of these conditions automatically caps the composite score at 50 regardless of total:

| Override Condition | Why It Overrides |
|-------------------|-----------------|
| Security blocking failure in any document | Credential exposure risk outweighs all other scores |
| >25% of cross-references point to missing documents | Knowledge graph is broken; agents will hallucinate |
| >50% of living documents are >30 days stale | Agent context is unreliable |

---

## 8. Health Score Dashboard Template
[TYPE: REFERENCE]

Use this template for monthly/quarterly health reports.

```
DOCUMENTATION CORPUS HEALTH REPORT
Project: [Project Name]
Audit Date: [YYYY-MM-DD]
Auditor: [Name / "ai-documentation skill"]
Documents In Scope: [N]
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DIMENSION SCORES
  D1  Freshness                    _____ / 20
      Docs with review_trigger     _____ / 5
      Stable docs in-date          _____ / 5
      Fast-changing docs in-date   _____ / 5
      Living docs recently updated _____ / 5

  D2  AI-Readiness Compliance      _____ / 30
      Mean AI-readiness score:     _____ / 100
      Blocking failures:           YES (score capped at 15) / NO

  D3  Cross-Reference Integrity    _____ / 20
      Typed references             _____ / 5
      Live references              _____ / 8
      Bidirectional references     _____ / 4
      Complete parent indexes      _____ / 3

  D4  Template Currency            _____ / 15
      template_version_used present _____ / 5
      Current template version     _____ / 7
      Legacy docs flagged          _____ / 3

  D5  Coverage                     _____ / 15
      Critical systems documented  _____ / 5
      Major decisions documented   _____ / 5
      Procedures documented        _____ / 5
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPOSITE SCORE                    _____ / 100

LABEL:   Excellent / Healthy / Fair / Degraded / Critical
OVERRIDE CONDITIONS TRIGGERED:  YES — [which] / NO

TOP 3 REMEDIATION ACTIONS (see Section 9):
  1. [Highest-impact action]
  2. [Second action]
  3. [Third action]

NEXT AUDIT DUE: [YYYY-MM-DD]
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

---

## 9. Remediation Priority Guide
[TYPE: REFERENCE]

When the composite score is below threshold, address dimensions in this order:

| Priority | Dimension | Why First |
|----------|-----------|-----------|
| 1 | **D2 Security blocking failure** | Credential exposure — immediate |
| 2 | **D3 Broken cross-references** | Knowledge graph is unreliable — high agent impact |
| 3 | **D5 Missing critical system coverage** | Agents hallucinate about undocumented systems |
| 4 | **D1 Stale living documents** | Agent context is unreliable |
| 5 | **D2 Low AI-readiness (non-security)** | RAG retrieval quality suffers |
| 6 | **D5 Missing decision records** | Agents may suggest reversing constrained decisions |
| 7 | **D4 Missing template_version_used** | Cannot identify upgrade candidates |
| 8 | **D1 Stale fast-changing documents** | Technical information may be outdated |
| 9 | **D3 Untyped cross-references** | Knowledge graph navigability reduced |
| 10 | **D4 Old template versions** | Missing Phase 1–3 enhancements |

---

## 10. Legacy Document Upgrade Strategy (E-02)
[TYPE: PROCEDURE]

### 10.1 What Is a Legacy Document

A legacy document is any document that:
- Predates the ai-documentation skill and template system (created before the `created` date of TMPL-000 v1.0)
- Does not have extended YAML frontmatter (`intent`, `triggers`, etc.)
- Does not have an AI Summary block
- Does not have section type tags (`[TYPE: ...]`)

### 10.2 Upgrade Priority Classification

Before investing effort in legacy upgrades, classify each document:

| Class | Criteria | Upgrade Priority |
|-------|----------|----------------|
| **Active & High-Traffic** | Frequently retrieved; affects agent decisions | HIGH — upgrade immediately |
| **Active & Low-Traffic** | Exists but rarely used; agents rarely encounter it | MEDIUM — upgrade on next revision |
| **Historical / Snapshot** | Point-in-time record; not expected to be current | LOW — add minimal AI Summary and `template_version_used`; no full retrofit needed |
| **Obsolete** | No longer describes any current system or decision | ARCHIVE — do not upgrade; remove from active index |

### 10.3 Upgrade Procedure

For each HIGH or MEDIUM priority legacy document:

**Step 1 — Classify the document type:**
Run Phase 1 template selection decision tree against the document's content.
Which template type best describes it?

**Step 2 — Retrofit frontmatter:**
Add the full extended YAML frontmatter block from the appropriate template.
Populate all required fields from the document's existing content.
Set `template_version_used` to the current template version.
Set `volatility` based on content type (evergreen / stable / snapshot).

**Step 3 — Add AI Summary block:**
Write an AI Summary block after the frontmatter.
Derive it from the document's existing content.
Mark `Trust Level: MEDIUM` if the document was not research-validated.

**Step 4 — Add section type tags:**
Add `[TYPE: ...]` tags to every `##` heading.
Infer type from context:
- Factual data / schemas → `[TYPE: REFERENCE]`
- Step-by-step instructions → `[TYPE: PROCEDURE]`
- Background / rationale → `[TYPE: EXPLANATION]`
- Choices made → `[TYPE: DECISION]`
- Unresolved items → `[TYPE: OPEN_QUESTION]`

**Step 5 — Add context anchor sentences:**
For any section that opens without naming the subject, add a context anchor
sentence as the first sentence of the section.

**Step 6 — Run AI-Readiness test:**
Score the retrofitted document. Target ≥80 for human-read documents.
Note remaining gaps in the document's Revision History.

**Step 7 — Update parent document index and cross-references:**
Add the retrofitted document to its parent's index.
Add `[IMPLEMENTS] → [current template ID]` cross-reference.

### 10.4 Upgrade Tracking

Track legacy upgrades in the corpus audit log (Section 11):

| Document ID | Pre-Upgrade AI-Readiness | Post-Upgrade AI-Readiness | Date Upgraded | Upgraded By |
|------------|------------------------|--------------------------|--------------|-------------|
| [Doc ID] | [N/A — no score] | [score] | [Date] | [Name] |

---

## 11. Corpus Audit Log
[TYPE: REFERENCE]

*Append one row per completed corpus audit.*

| Audit Date | Docs Audited | Composite Score | Label | Top Gap | Action Taken | Next Audit |
|-----------|-------------|----------------|-------|---------|-------------|-----------|
| 2026-04-04 | 7 (examples) | Baseline — not yet scored | N/A | Corpus too small to audit meaningfully | Establish audit baseline on next project milestone | 2026-07-04 |

---

## 12. Cross-References
[TYPE: REFERENCE]

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DEPENDS_ON] | AI-Readiness-Test-Protocol.md | All | Dimension 2 uses the per-document rubric from this protocol |
| [DEPENDS_ON] | TMPL-000_conventions.md | Sections 2, 7, 12 | Audit criteria derived from conventions |
| [IMPLEMENTS] | PROJ-002 Enhancement Roadmap | WP-4.3, E-01, E-02 | This document delivers WP-4.3, E-01 (health score), and E-02 (legacy upgrade) |
| [SEE_ALSO] | OPS-001 Quarterly Research Refresh | All | Quarterly refresh updates the conventions that drive D2 and D4 scores |
| [SEE_ALSO] | ARCH-002 Skills-Updater-Integration | Section 4 | Health check output feeds skills-updater observation buffer |

---

## 13. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-04 | Initial | Documentation health score and corpus audit mechanism created | Phase 4 WP-4.3, E-01, E-02 |

---

*Template: TMPL-002 Technical Reference v1.1 | Parent: PROJ-001 AI-Optimized Documentation Protocol*
