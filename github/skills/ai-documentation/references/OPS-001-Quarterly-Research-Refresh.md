# Quarterly Research Refresh
## Operational Runbook — AI Documentation Skill System

```yaml
---
document_id: "OPS-001 Quarterly-Research-Refresh"
title: "Quarterly Research Refresh — AI Documentation Skill System"
version: "1.0"
created: "2026-04-04"
status: "Final"
parent_document: "PROJ-001 AI-Optimized Documentation Protocol"
template_version_used: "TMPL-003 v1.1"

procedure_type: "operations"
estimated_duration: "2–4 hours"
frequency: "quarterly"
last_executed: "2026-04-04"
last_executed_by: "Project Team"
execution_validated: true

intent: >
  Enable any project team member to execute the quarterly research refresh
  that keeps the AI Documentation Skill system's conventions aligned with
  the current state of RAG, documentation, and AI agent best practices.

consumption_context:
  - human-reading
  - agentic-execution

triggers:
  - "quarterly research refresh"
  - "refresh documentation skill conventions"
  - "run the quarterly update"
  - "update TMPL-000 conventions from research"
  - "OPS-001"
  - "documentation skill maintenance"

negative_triggers:
  - "create a new document" # → ai-documentation SKILL.md
  - "AI readiness test" # → AI-Readiness-Test-Protocol.md

volatility: "stable"
review_trigger: "When research tooling or research skill version changes significantly"

confidence_overall: "high"
confidence_note: "Procedural document — validated by initial execution on 2026-04-04"
---
```

---

> ## 🤖 AI Summary
> **Core Purpose:** Step-by-step operational runbook for the quarterly research refresh that keeps AI documentation system conventions current as RAG and agent best practices evolve.
> **Schedule:** Every quarter (January, April, July, October) — or when triggered by a major benchmark release
> **Duration:** 2–4 hours per refresh cycle
> **Output:** Updated TMPL-000 conventions + updated EX-001 research synthesis + version increments + change log
> **Do NOT Use This For:** Ad-hoc document updates; creating new templates; running the AI-Readiness test
> **Review By:** When research tooling or skills change significantly

---

## TABLE OF CONTENTS

1. [When to Run This Refresh](#1-when-to-run-this-refresh)
2. [Pre-Refresh Setup](#2-pre-refresh-setup)
3. [Step-by-Step Refresh Procedure](#3-step-by-step-refresh-procedure)
4. [Decision: Update vs No-Action vs Major Revision](#4-decision-update-vs-no-action-vs-major-revision)
5. [Propagation — Updating Affected Documents](#5-propagation--updating-affected-documents)
6. [Refresh Log](#6-refresh-log)
7. [Cross-References](#7-cross-references)
8. [Revision History](#8-revision-history)

---

## 1. When to Run This Refresh
[TYPE: REFERENCE]

### 1.1 Scheduled Triggers

The quarterly research refresh runs on the first week of each quarter:

| Quarter | Target Run Date | Lead Time for Planning |
|---------|----------------|----------------------|
| Q1 | First week of January | Schedule in December |
| Q2 | First week of April | Schedule in March |
| Q3 | First week of July | Schedule in June |
| Q4 | First week of October | Schedule in September |

### 1.2 Event-Based Triggers (Run Immediately)

Run the refresh outside the quarterly schedule when ANY of the following occur:

| Event | Why It Triggers a Refresh | Documents to Check |
|-------|--------------------------|-------------------|
| New major RAG benchmark published (BEIR, MIRACL, MTEB update) | May change optimal chunk size guidance | TMPL-000 §7, EX-001 |
| Anthropic publishes an update to the SKILL.md spec | May change frontmatter standards | SKILL.md, TMPL-000 §2 |
| A new EU AI Act implementing act or guidance published | May change TMPL-007 requirements | TMPL-007, TMPL-000 regulatory notes |
| NIST AI RMF receives a major update | May change TMPL-007 governance framework | TMPL-007 §10.2 |
| A Tier 1 study directly contradicts a current convention | May require convention update | Whichever TMPL-000 section is affected |
| Six months have elapsed since last refresh (failsafe) | Conventions may have drifted | All sections |

---

## 2. Pre-Refresh Setup
[TYPE: PROCEDURE]

### 2.1 Gather Current State

Before running any research, record the current convention values that will be compared against new research findings.

Open TMPL-000_conventions.md and note the current values for each validation query:

| Convention | Current Value | Section |
|-----------|--------------|---------|
| Optimal chunk size target | [value from §7] | TMPL-000 §7 |
| Chunking method recommendation | [value] | TMPL-000 §7 |
| Contextual retrieval pattern | [current guidance] | TMPL-000 §7.4 |
| Context cliff threshold | [current value] | TMPL-000 §7.5 |
| Research currency date of EX-001 | [from EX-001 frontmatter] | EX-001 |
| Last SKILL.md spec update reference | [from SKILL.md changelog] | SKILL.md |

### 2.2 Tools Required

| Tool | Purpose | Access |
|------|---------|--------|
| ai-documentation skill | Creating and updating documents | Available in Claude project |
| research-orchestrator skill | Running research queries | Available in Claude project |
| verification skill | Verifying updated claims | Available in Claude project |
| EX-001 research synthesis | Current research baseline | In project examples/ |
| TMPL-000 conventions | Current conventions baseline | In project references/ |

---

## 3. Step-by-Step Refresh Procedure
[TYPE: PROCEDURE]

### Step 1 — Run the Standard Validation Queries

Invoke the research-orchestrator skill for each query below. These are the
same queries used to build EX-001 and TMPL-000 originally. Running them
again reveals what has changed since the last refresh.

Run each query independently and record findings in Step 2:

**Query Set A — Chunking & RAG:**
```
Query A1: "RAG chunking optimal size tokens structured technical documentation 2026"
Query A2: "recursive vs semantic chunking accuracy comparison 2026"
Query A3: "contextual retrieval chunk prepend embedding accuracy improvement"
Query A4: "hybrid search BM25 vector retrieval technical documentation benchmark"
Query A5: "LLM context cliff token threshold performance degradation"
```

**Query Set B — SKILL.md & Agent Standards:**
```
Query B1: "Anthropic SKILL.md specification update agent skills 2026"
Query B2: "Claude skill frontmatter allowed-tools specification current"
Query B3: "AI agent context document format best practices 2026"
```

**Query Set C — Regulatory (if TMPL-007 is in active use):**
```
Query C1: "EU AI Act implementing regulations technical documentation requirements 2026"
Query C2: "NIST AI RMF update 2026"
Query C3: "ISO 42001 AI management standard guidance 2026"
```

### Step 2 — Record Findings Against Current Conventions

For each query where research returns findings that differ from current conventions,
record a comparison row:

| Convention | Current Value | New Research Finding | Source (Tier) | Direction of Change |
|-----------|--------------|---------------------|---------------|-------------------|
| [Convention name] | [Current] | [New finding] | [Source, Tier] | Increase / Decrease / Different method / Unchanged |

**If no findings differ from current conventions:** Record "No material changes found" and proceed to Step 5.

### Step 3 — Run 90/10 Outlier Analysis

For each finding that differs from current conventions, identify whether it is:
- **Consensus change:** Multiple independent sources agree the convention should change
- **Outlier position:** One or two sources disagree while most still agree with current convention
- **Emerging finding:** Small number of sources beginning to move in a new direction

| Finding | Classification | Action |
|---------|---------------|--------|
| [Finding] | Consensus | Update convention in Step 4 |
| [Finding] | Outlier | Note in EX-001 Section 4; do not update convention yet |
| [Finding] | Emerging | Flag in TMPL-000 with ⚠️ note; schedule follow-up next quarter |

### Step 4 — Apply the Update Decision Framework (Section 4)

For each consensus finding, apply the decision framework in Section 4 to
determine whether to update, flag, or take no action.

### Step 5 — Update EX-001 Research Synthesis

Whether or not conventions change, EX-001 must be refreshed to reflect
current research currency:

1. Open `examples/EX-001_RAG-Chunking-Research.md`
2. Update the `research_validated_date` frontmatter field to today
3. Update `research_queries_used` to include new queries run this cycle
4. Add a Section 3.N for any new findings from Queries A1–A5
5. Update Section 4 with any new outlier positions identified
6. Update Section 6.3 (Expiry Conditions) — reset the 6-month review date
7. Increment version (1.0 → 1.1, or 1.1 → 1.2)
8. Add revision history entry

### Step 6 — Update TMPL-000 Conventions (if changes are warranted)

For each convention being updated:

1. Open `references/TMPL-000_conventions.md`
2. Navigate to the affected section
3. Update the convention text with the new guidance
4. Add or update the confidence marker to reflect current source quality
5. Update the footer with the current date
6. If the change is breaking (changes how existing documents should be structured),
   increment the minor version (v1.2 → v1.3)

### Step 7 — Check SKILL.md for Spec Compliance (Queries B1–B3)

If research reveals an update to the Anthropic SKILL.md specification:

1. Compare current SKILL.md frontmatter to the new spec
2. Identify any new supported fields that should be added
3. Identify any fields that have changed behavior
4. Update SKILL.md frontmatter as needed
5. Update MANIFEST.md with the change

### Step 8 — Run AI-Readiness Test on Updated Documents

After updating TMPL-000 or EX-001, run the AI-Readiness Test Protocol
on both documents to verify they still score 90+:

```
"Run the AI-readiness test on TMPL-000_conventions.md"
"Run the AI-readiness test on EX-001_RAG-Chunking-Research.md"
```

If either scores below 90, remediate before proceeding.

### Step 9 — Update the Refresh Log (Section 6)

Record this refresh cycle in the log below with findings and actions taken.

---

## 4. Decision: Update vs No-Action vs Major Revision
[TYPE: REFERENCE]

Apply this framework to each research finding before changing any convention.

### 4.1 Decision Criteria

| Finding Type | Criteria | Action |
|-------------|----------|--------|
| **Update — Minor** | Single Tier 1 or 2 sources show a refinement (e.g., optimal range shifts from 300–500 to 350–550 words) | Update convention; increment TMPL-000 patch version; note in refresh log |
| **Update — Breaking** | Multiple Tier 1/2 sources agree the convention is wrong in a way that changes how documents should be structured | Update convention; increment TMPL-000 minor version; flag all existing documents for refresh on next revision; add to MANIFEST.md |
| **Flag — Emerging** | 1–2 sources point in a new direction but consensus not yet reached | Add ⚠️ note in TMPL-000 section; note in EX-001 Section 4 as outlier; review next quarter |
| **No Action** | Findings confirm current conventions OR only Tier 3/4 sources disagree | Record "confirmed" in refresh log; no document changes |
| **Major Revision** | Research reveals a fundamental change in approach (e.g., semantic chunking becomes clearly superior for structured docs) | Escalate to team decision; create TMPL-005 decision record; update EX-001 with full synthesis; update TMPL-000 with major version increment |

### 4.2 Source Tier Reminder

| Tier | Examples | Weight |
|------|---------|--------|
| Tier 1 | Peer-reviewed papers (NAACL, ACL, EMNLP), W3C/ISO/NIST standards | Full weight — strong evidence |
| Tier 2 | Production engineering blogs (Anthropic, Weaviate, Pinecone, Chroma), major vendor documentation | Corroborating — use with Tier 1 |
| Tier 3 | Trade publications, individual practitioner blog posts | Indicator only — corroborate before acting |
| Tier 4 | Anonymous posts, undated content, circular citations | Exclude |

---

## 5. Propagation — Updating Affected Documents
[TYPE: PROCEDURE]

When TMPL-000 conventions change, identify and update all documents that
need to be updated as a result.

### 5.1 Impact Assessment

For each convention change, identify the scope of impact:

| Changed Convention | Documents That Must Be Updated | Update Priority |
|-------------------|-------------------------------|----------------|
| Chunk size target (§7) | All existing TMPL-001 through TMPL-008 instances; EX-001 through EX-005 | Low — update on next revision |
| Frontmatter field added (§2) | All existing documents (add the new field) | Medium — update within 1 month |
| Security rules changed (§10) | All TMPL-002 and TMPL-003 instances | High — update immediately |
| Relationship type renamed (§16) | All documents using the old type name | Medium — update on next revision |
| SKILL.md spec change | SKILL.md, MANIFEST.md | High — update immediately |

### 5.2 Document Update Tracking

Track which documents need updates and their status:

| Document | Change Required | Status | Updated By | Date |
|----------|----------------|--------|-----------|------|
| [Document ID] | [What needs changing] | PENDING / DONE | | |

### 5.3 Communication

If changes affect document authors beyond this project, notify via:
- [Slack channel / email / project notes — specify your channel]
- Update the MANIFEST.md "Known Limitations" section with any temporary compliance gaps
- Note in the README.md "Version History" section

---

## 6. Refresh Log
[TYPE: REFERENCE]

*Append one row per completed refresh cycle.*

| Date | Queries Run | Material Changes Found | Action Taken | TMPL-000 Version | EX-001 Version | Executed By |
|------|------------|----------------------|-------------|-----------------|----------------|-------------|
| 2026-04-04 | A1–A5 (initial build) | N/A — initial research | Built EX-001 and TMPL-000 §7 | v1.0 | v1.0 | Project Team |
| 2026-10-04 (planned) | A1–A5, B1–B3 | [To be filled] | [Action] | | | |

---

## 7. Cross-References
[TYPE: REFERENCE]

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DEPENDS_ON] | EX-001 RAG Chunking Research | All | Primary research baseline this refresh updates |
| [DEPENDS_ON] | TMPL-000_conventions.md | All | Primary target of convention updates |
| [DEPENDS_ON] | AI-Readiness-Test-Protocol.md | Step 8 | Validation step after conventions update |
| [IMPLEMENTS] | PROJ-001 AI-Optimized Documentation Protocol | Section 9.5 | Implements the "quarterly research trigger" process |
| [SEE_ALSO] | PROJ-002 Enhancement Roadmap | WP-3.7 | This document is the WP-3.7 deliverable |

---

## 8. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-04 | Initial | Document created | Phase 3 WP-3.7 — operational runbook for quarterly research refresh |

---

*Template: TMPL-003 Procedure & Workflow v1.1 | Parent: PROJ-001 AI-Optimized Documentation Protocol*
