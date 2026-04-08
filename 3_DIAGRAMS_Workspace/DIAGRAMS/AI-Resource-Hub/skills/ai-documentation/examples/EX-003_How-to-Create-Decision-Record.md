# Creating a Decision Record Using TMPL-005
## Step-by-Step Procedure for Documenting Formal Decisions

```yaml
---
document_id: "EX-003 Create-Decision-Record-Procedure"
title: "Creating a Decision Record Using TMPL-005 — Step-by-Step Procedure"
version: "1.0"
created: "2026-04-04"
status: "Final"
parent_document: "PROJ-001 AI-Optimized Documentation Protocol"
template_version_used: "TMPL-003 v1.1"

procedure_type: "operations"
estimated_duration: "30–60 minutes"
frequency: "on-demand"
last_executed: "2026-04-04"
last_executed_by: "Project Team"
execution_validated: true

intent: >
  Enable any project team member or AI agent to create a complete, standards-compliant
  TMPL-005 Decision Record from scratch, by following a step-by-step procedure covering
  decision capture, options documentation, rationale writing, and cross-reference setup.

consumption_context:
  - human-reading
  - agentic-execution

triggers:
  - "how to create a decision record"
  - "steps to write a TMPL-005"
  - "how to document a decision"
  - "decision record procedure"
  - "how to use TMPL-005"

negative_triggers:
  - "what is a decision record" # → TMPL-005 template or EX-005 example
  - "why do we document decisions" # → PROJ-001 Section 2 or TMPL-005 template guidance
  - "decision record template fields" # → TMPL-005.md directly

volatility: "stable"
research_validated: false
research_validated_date: ""
research_queries_used: []

review_trigger: "When TMPL-005 is updated to a new major version"

confidence_overall: "high"
confidence_note: "Direct procedural knowledge — validated by dry-run execution on 2026-04-04"
---
```

---

> ## 🤖 AI Summary
> **Core Purpose:** Step-by-step procedure for creating a complete TMPL-005 Decision Record, from deciding the decision is ready to document through publishing and cross-referencing.
> **Key Steps:** Confirm decision finality → Load template → Complete frontmatter → Write all sections → Run Phase 6 checklist → Update parent doc → Update RAG index
> **Trust Level:** HIGH — procedure validated by execution
> **Estimated Duration:** 30–60 minutes per decision record
> **Do NOT Use This For:** Documenting decisions that are still under deliberation; understanding why we use decision records (see PROJ-001); superseding an existing record (see Section 6)
> **Review By:** When TMPL-005 template is updated to a new major version

---

## TABLE OF CONTENTS

1. [Prerequisites & Access Requirements](#1-prerequisites--access-requirements)
2. [Pre-Execution Checklist](#2-pre-execution-checklist)
3. [Step-by-Step Procedure](#3-step-by-step-procedure)
4. [Rollback / Undo Procedure](#4-rollback--undo-procedure)
5. [Troubleshooting](#5-troubleshooting)
6. [Special Case: Superseding an Existing Decision](#6-special-case-superseding-an-existing-decision)
7. [Cross-References](#7-cross-references)
8. [Revision History](#8-revision-history)

---

## 1. Prerequisites & Access Requirements
[TYPE: REFERENCE]

### 1.1 Who Can Execute This Procedure

| Attribute | Requirement |
|-----------|-------------|
| **Required Role** | Any project team member; or an AI agent with write access to project documentation |
| **Required Permissions** | Write access to the project documentation folder |
| **Approval Required** | The DECISION itself must be approved before documenting. This procedure documents an already-made decision — it does not make one. |
| **Minimum Experience** | Familiarity with Markdown; awareness of the decision being documented |

### 1.2 Required Tools & Access

| Tool / Access | Version / Type | How to Obtain | Verify With |
|--------------|---------------|---------------|-------------|
| TMPL-005 template | v1.1 | Load from `references/TMPL-005.md` in skill package | File is readable and contains frontmatter |
| ai-documentation skill | v1.1.0 | Installed in Claude project knowledge | Invoke skill and confirm version |
| Text editor or Claude | Any | — | — |
| Access to project doc folder | Write access | Project admin | Can create a new `.md` file in the folder |

### 1.3 Required Environment Variables / Credentials

This procedure requires no credentials or environment variables.

### 1.4 Sensitive Content Rules for This Document
[TYPE: REFERENCE]

This procedure document does not involve credentials or sensitive data.
Any decision record created using this procedure must follow the sensitive
content rules in TMPL-000_conventions.md Section 10 if the decision
involves system credentials, infrastructure values, or PII.
✅ `[VERIFIED — TMPL-000 conventions, Section 10, v1.1]`

### 1.5 System State Prerequisites

The following must be true BEFORE starting this procedure:

- [ ] The decision is finalised — not still under deliberation
- [ ] You can state the decision in one clear, past-tense sentence
- [ ] You know who made the decision and on what date
- [ ] You can identify at least one alternative that was considered and rejected
- [ ] The next available document number is known (from CTX-DOCSTD)

---

## 2. Pre-Execution Checklist
[TYPE: PROCEDURE]

Complete this checklist before starting Section 3.

```
DECISION READINESS
  [ ] Decision is final — confirmed with decision-maker(s)
  [ ] Decision can be stated in one sentence, past tense, active voice
        Example: "The team selected PostgreSQL as the Order Service database."
        NOT: "We will use PostgreSQL" or "It was decided that..."
  [ ] Decision date is known: YYYY-MM-DD
  [ ] Decision-maker(s) are identified (names or roles)

DOCUMENT SETUP
  [ ] Next available document number obtained from CTX-DOCSTD
  [ ] Parent document confirmed (which master document this records under)
  [ ] TMPL-005.md template file is accessible
  [ ] TMPL-000_conventions.md is accessible for frontmatter reference

CROSS-REFERENCE PREPARATION
  [ ] Any TMPL-001 research document that informed this decision is identified
  [ ] Any existing decisions this supersedes are identified (or "none")
  [ ] Any systems or documents governed by this decision are identified
```

---

## 3. Step-by-Step Procedure
[TYPE: PROCEDURE]

### Step 1 — Create the document file

Create a new Markdown file in the project documentation folder using the
naming convention from CTX-DOCSTD:

```
Filename format: [document-id]_[short-title].md
Example: 085_Decision_Database-Selection.md
```

Copy the complete contents of `references/TMPL-005.md` into the new file.

**Verification:** File exists and contains the TMPL-005 frontmatter block.

---

### Step 2 — Complete the YAML frontmatter

Fill every frontmatter field before writing any section content. No
placeholder values may remain when the document is published.

Work through each field in the frontmatter block:

```yaml
# ── RULES-001 STANDARD FIELDS ──────────────────────────────
document_id: "[number] [Decision-Title]"   # Use next available number from CTX-DOCSTD
title: "Decision: [Title in Title Case]"   # Prefix with "Decision:" always
version: "1.0"                             # Always 1.0 for new records
created: "YYYY-MM-DD"                      # Today's date
status: "Accepted"                         # Accepted | Proposed | Superseded
parent_document: "[Parent Doc ID]"         # From CTX-DOCSTD active parents

# ── DECISION METADATA ────────────────────────────────────────
decision_date: "YYYY-MM-DD"               # When decision was formally made
decision_makers: ["Name/Role 1", "Name/Role 2"]
decision_type: "[architecture|technology|design|process|policy|standard]"
applies_to: "[scope — team, system, or component]"
supersedes: ""                             # Leave blank if no prior decision

# ── AI-OPTIMIZATION EXTENSION ────────────────────────────────
template_version_used: "TMPL-005 v1.1"
intent: >
  Enable [developers/agents/architects] to understand what was decided
  about [topic], why, and what constraints follow.
triggers:
  - "why do we use [technology/approach]"
  - "what was decided about [topic]"
  - "[technology] decision rationale"
review_trigger: "[Condition that would prompt re-evaluation]"
```

**Verification:** All fields populated. No `[placeholder]` text remains.
`status` is `Accepted`. `decision_date` is a real date, not today's date
(unless the decision was made today).

---

### Step 3 — Write the AI Summary block

Write the AI Summary immediately after the frontmatter closing `---`.
Complete it now with what you know; you will refine it after writing
all sections.

```markdown
> ## 🤖 AI Summary
> **Decision:** [The decision in one sentence, past tense]
> **Status:** ACCEPTED
> **Date:** [YYYY-MM-DD]
> **Scope:** [What this applies to]
> **Key Constraint Created:** [Most important constraint from this decision]
> **Cannot Be Changed Without:** [What would need to happen — a superseding decision record]
> **Supersedes:** [Previous decision ID / None]
> **Superseded By:** [None — to be updated if superseded]
```

**Verification:** AI Summary is present, complete, no placeholder text.

---

### Step 4 — Write Section 1: Decision Statement

The `ai-documentation` skill for the `TMPL-005` Decision Record identifies
Section 1 as the most frequently retrieved chunk. Write it to stand alone.

**Section 1.1 — The Decision:** Write the decision in one clear sentence
(past tense, active voice). Then expand in 2–3 sentences: what exactly was
chosen, at what scope, and what the primary implication is.

**Section 1.2 — Status table:** Fill the status table completely.

**Section 1.3 — The Resulting Constraint:** This is the most practically
important field for AI agents citing this decision. State the hard constraint
that now applies. Be specific: not "use PostgreSQL" but "All Order Service
data must be stored in PostgreSQL. No other database technology may be
introduced in this service without a superseding decision record."

**Verification:** Section 1 reads as a standalone summary. A reader with
only Section 1 knows the decision, its status, its scope, and its primary constraint.

---

### Step 5 — Write Sections 2–5: Context, Drivers, Options, Rationale

Write these sections in order, as each section sets up the next.

**Section 2 — Context & Problem:** Explain why a decision was needed. Write
for a reader who was not present. Include the timeline table.

**Section 3 — Decision Drivers:** List the must-have requirements that
options were evaluated against. Be honest — if cost was the primary driver,
say so.

**Section 4 — Options Considered:** Document every option that was seriously
considered. Mark the chosen option `✅ CHOSEN`, rejected options `❌ REJECTED`.
For each rejected option, state the specific driver it failed to meet — not
just "not good enough."

**Section 5 — Decision Rationale:** Connect the drivers (Section 3) to the
chosen option (Section 4) with explicit reasoning. State the trade-offs
that were accepted and the assumptions the decision depends on.

**Verification:** Every rejected option has a specific rejection reason.
The rationale in Section 5 refers to the specific drivers in Section 3.

---

### Step 6 — Write Sections 6–8: Consequences, Implementation, Review

**Section 6 — Consequences & Constraints:** List the positive and negative
consequences. State explicitly what is now REQUIRED and what is now PROHIBITED
as a result of this decision. This is what other documents and AI agents cite.

**Section 7 — Implementation Notes:** Brief notes on how the decision should
be implemented. If a full TMPL-003 procedure exists or should exist, reference
it here.

**Section 8 — Review Conditions:** List specific events that would trigger
re-evaluation. Do not set a calendar date unless the decision has a natural
time-bounded validity. Confirm Section 8.3 (Supersession Process) matches
the current template version.

---

### Step 7 — Complete the cross-references section

Section 9 of the Decision Record links this decision into the project
knowledge graph. Complete all sub-tables:

- **9.1 Documents that led to this decision** — link the TMPL-001 research
  that informed it using `[VALIDATED_BY]`. Link any policy or constraint
  document using `[DEPENDS_ON]`.
- **9.2 Documents governed by this decision** — link any TMPL-002, TMPL-003,
  or architecture documents that must now comply, using `[IMPLEMENTS]` or `[APPLIES]`.
- **9.3 Related decisions** — link any decisions this supersedes using `[SUPERSEDES]`.

---

### Step 8 — Remove all template scaffolding

Remove every `<!-- comment -->` block from the template. Remove all
`[placeholder]` text in section bodies. Remove any sections not applicable
to this decision — do not leave them empty.

**Verification:** No HTML comments remain. No `[placeholder]` text remains.
Only sections with real content are present.

---

### Step 9 — Run the Phase 6 Pre-Publish Checklist

Invoke the `ai-documentation` skill's Phase 6 checklist, or run it manually:

```
STRUCTURE
[ ] Frontmatter complete — all fields filled, no placeholders
[ ] template_version_used field populated: "TMPL-005 v1.1"
[ ] AI Summary block present and complete
[ ] Every ## section has a [TYPE: ...] tag
[ ] document_id follows CTX-DOCSTD convention

CONTENT
[ ] Section 1 is standalone-readable
[ ] Every rejected option has a specific rejection reason
[ ] Resulting constraint is explicit and specific
[ ] No cross-section pronouns — subject named every time

SECURITY
[ ] No credentials or sensitive values in document body

REFERENCES
[ ] triggers list has at least 3 entries
[ ] negative_triggers has at least 2 entries
[ ] review_trigger is set

HIERARCHY
[ ] Parent document updated — this document added to parent's index
[ ] Bidirectional references confirmed (documents cited in Section 9 reference this document back)
[ ] Revision History v1.0 entry present with today's date
```

---

### Step 10 — Update parent document and add to vector index

**Update the parent document:** Add this decision record to the parent
document's Document Index table. Set status to `Final`.

**Add to vector index (if applicable):** If the project uses a vector
database for retrieval, add the document to the active index with these
metadata fields:
```
document_id: [document ID]
template_type: TMPL-005
status: active
decision_date: [YYYY-MM-DD]
applies_to: [scope]
```

**Verification:** Parent document index updated. Document is retrievable
if a vector index is in use.

---

## 4. Rollback / Undo Procedure
[TYPE: PROCEDURE]

A published Decision Record is immutable — the content of the decision
cannot be edited after publication. However, if a decision record was created
in error (e.g., the decision was not final, or the document has serious
errors), the following rollback applies:

1. Set `status: Proposed` (not Accepted) in the frontmatter
2. Add a note to the AI Summary block: `⚠️ STATUS: Proposed — decision not yet finalised`
3. Remove from the vector index if added
4. Do NOT delete the file — it remains as a draft record
5. Notify any team members who may have seen the published record

If the decision content was correct but the document has errors (wrong scope,
missing options, incorrect date), create a corrected record with the same
document ID and version `1.1`. Add a Revision History entry explaining the
correction. Do not alter Section 1 (Decision Statement) — only metadata
and supporting sections may be corrected.

---

## 5. Troubleshooting
[TYPE: REFERENCE]

| Problem | Cause | Resolution |
|---------|-------|-----------|
| Cannot state the decision in one sentence | Decision is not yet finalised | Stop. Do not create the record until the decision is settled. Return to the deliberation process. |
| No rejected alternatives to document | Decision was made without evaluating options | Document the single option chosen and note: "No alternatives were formally evaluated. Decision was made based on [constraint/requirement]." |
| Conflicting information about what was decided | Decision was not formally made — only discussed | Stop. Convene the decision-makers and confirm the decision before documenting. |
| Uncertainty about which driver was primary | Drivers were not articulated during the decision | Document the drivers you can reconstruct with a note: "Drivers reconstructed from recollection — not formally articulated at time of decision." |
| Another document contradicts this decision | Cross-document conflict | Add `[CONTRADICTS]` cross-reference in both documents. Surface to team for resolution. Do not silently override the other document. |

---

## 6. Special Case: Superseding an Existing Decision
[TYPE: PROCEDURE]

When a decision needs to change, create a new TMPL-005 — never edit
the existing one. Follow the full Steps 1–10 above for the new document,
then complete these additional steps:

**Step A — In the NEW document's frontmatter:**
```yaml
supersedes: "[Superseded Document ID]"
```

**Step B — In the OLD document:**
```yaml
status: "Superseded"
superseded_by: "[New Document ID]"
```
Add this block immediately after the frontmatter:
```markdown
> ⚠️ SUPERSEDED: This document has been replaced by [New Document ID]
> dated [YYYY-MM-DD]. Do not use for new work.
```

**Step C — RAG index (immediate):**
Apply `status: superseded` metadata filter to the old document's chunks.

**Step D — RAG index (after 30 days):**
Remove old document's chunks from active index. Archive source file.

**Step E — Dependent documents:**
Update all documents with `[APPLIES]` or `[DEPENDS_ON]` references to
the old decision to point to the new one.

---

## 7. Cross-References
[TYPE: REFERENCE]

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [APPLIES] | TMPL-005 Decision Record template | All | This procedure describes how to execute TMPL-005 |
| [IMPLEMENTS] | PROJ-001 AI-Optimized Documentation Protocol | Section 9.3 | Implements the Phase 3 document population plan |
| [SEE_ALSO] | EX-005 Decision Record: Chunking Strategy | All | Populated example of a completed TMPL-005 document |
| [SEE_ALSO] | TMPL-000_conventions.md | Section 12 | RAG lifecycle rules applied in Step 10 |

---

## 8. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-04 | Initial | Document created | Phase 1 WP-1.5 — TMPL-003 example, demonstrating procedure template with a practical, immediately-useful task |

---

*Template: TMPL-003 Procedure & Workflow v1.1 | Parent: PROJ-001 AI-Optimized Documentation Protocol*
