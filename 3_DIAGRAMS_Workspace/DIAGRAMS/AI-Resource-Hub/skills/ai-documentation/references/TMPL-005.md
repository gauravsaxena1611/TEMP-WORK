# Decision Record: [Decision Title]
## [Subtitle — e.g., "Architecture: Database Selection for Order Service"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-005: DECISION RECORD
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: Architecture decisions, technology choices, design
decisions, strategic pivots, policy decisions, process choices,
tooling selections, standard adoptions.

ALSO KNOWN AS: Architecture Decision Record (ADR), Decision Log

AUTHORING WORKFLOW:
  1. NO research skill trigger — the DECISION is authoritative
     (it captures what your team chose, not industry consensus)
  2. Research that INFORMED the decision belongs in a TMPL-001
     document that this decision references
  3. Verification skill runs ONLY if external factual claims
     are made (e.g., "PostgreSQL supports X feature")
  4. This document is written AFTER the decision is made,
     not during deliberation

KEY PRINCIPLES:
  1. IMMUTABLE: Once finalized, this document captures a
     historical fact. Do not revise the decision — supersede it.
  2. AUTHORITATIVE: The decision stated here IS the team's
     position. No confidence markers needed — it's a fact.
  3. TRACEABLE: Every consequence and follow-up is tracked.
     Other documents reference this as their authority.
  4. HONEST: Document rejected options fairly. Future team
     members need to understand why alternatives were not chosen.

SUPERSESSION: When a decision changes, create a new TMPL-005
and mark this one as SUPERSEDED in the frontmatter.
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ──────────────────────────────
document_id: "[XXX Decision-Title]"
title: "Decision: [Title]"
version: "1.0"
created: "YYYY-MM-DD"
status: "Proposed | Accepted | Superseded | Deprecated"
parent_document: ""

# ── DECISION METADATA ────────────────────────────────────────
decision_date: "YYYY-MM-DD"   # When the decision was formally made
decision_makers: []            # Who made this decision
decision_type: "architecture | technology | design | process | policy | standard"
applies_to: ""                 # What system, team, or scope this covers
supersedes: ""                 # Document ID if this replaces an earlier decision
superseded_by: ""              # Document ID if this has been superseded (set when superseding)

# ── AI-OPTIMIZATION EXTENSION ───────────────────────────────
intent: >
  Enable [developers / agents / architects] to understand what
  was decided about [topic], why it was decided, and what
  constraints follow from that decision.

consumption_context:
  - human-reading
  - ai-reasoning
  - rag-retrieval    # Agents cite this as authority for constraints

triggers:
  - "why do we use [technology/approach]"
  - "what was decided about [topic]"
  - "[technology] decision rationale"
  - "can we change [approach]"
  - "[system] architecture decision"

negative_triggers:
  - "how to use [technology]"         # → TMPL-002 or TMPL-003
  - "what does research say about"    # → TMPL-001
  - "step by step [task]"             # → TMPL-003

volatility: "snapshot"
  # snapshot: point-in-time capture — this is a historical fact.
  # Decision itself does not change. If decision changes,
  # this is SUPERSEDED and a new record is created.

research_validated: false
  # This document does not require research validation.
  # Research that informed the decision is in referenced TMPL-001.

review_trigger: "[Condition that would prompt re-evaluation — e.g., 'If system reaches 10M users' or 'Annual review of technology stack']"

template_version_used: "TMPL-005 v1.1"  # Record which template version was active when this doc was written

confidence_overall: "high"
confidence_note: "Decision is authoritative — reflects actual team decision"
---
```

---

> ## 🤖 AI Summary
> **Decision:** [What was decided — one sentence, past tense]
> **Status:** [ACCEPTED / SUPERSEDED / DEPRECATED]
> **Date:** [Decision date]
> **Scope:** [What this applies to]
> **Key Constraint Created:** [Most important constraint that follows from this decision]
> **Cannot Be Changed Without:** [What would need to happen to revisit this]
> **Supersedes:** [Previous decision ID if applicable / None]
> **Superseded By:** [Newer decision ID if applicable / None]

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

<!--
CHUNKING NOTE: This section is retrieved most frequently.
It should be a complete, standalone answer to "what was decided?"
If an AI agent reads ONLY this section, it must know the decision
and its most important constraint.

WRITE IN: Past tense, active voice, declarative statements.
NOT: "We will use PostgreSQL" → YES: "We use PostgreSQL"
NOT: "It was decided that..." → YES: "The team decided to use..."
-->

### 1.1 The Decision

**[State the decision in one clear sentence.]**

[2-3 sentences expanding on the decision — what exactly was chosen,
at what scope, and what the primary implication is.]

### 1.2 Status

| Attribute | Value |
|-----------|-------|
| **Status** | [ACCEPTED / SUPERSEDED / DEPRECATED] |
| **Date Made** | [YYYY-MM-DD] |
| **Made By** | [Names or roles] |
| **Applies To** | [Scope — team / system / component / organization] |
| **Effective Date** | [When this takes effect — may differ from decision date] |

### 1.3 The Resulting Constraint

**Because of this decision:**
[State the primary constraint that follows. This is what other documents and agents reference when citing this decision. Example: "All Order Service data must be stored in PostgreSQL. No other database technology may be introduced in this service without a superseding decision record."]

---

## 2. Context & Problem
[TYPE: EXPLANATION]

<!--
WHY THIS SECTION EXISTS:
Future team members and AI agents need to understand the situation
that made this decision necessary. Without context, the decision
looks arbitrary. With context, the rationale is obvious.

Write for a reader who wasn't present when the decision was made.
-->

### 2.1 The Problem Being Solved

[2-4 sentences describing the problem, gap, or question that needed
a decision. Be specific — not "we needed a database" but "we needed
a persistence layer that could handle 10,000 writes/minute with
ACID guarantees and support for complex relational queries."]

### 2.2 Why a Decision Was Needed

[Why couldn't this be left to individual judgment? What forced
a formal, documented decision?]

- [Reason 1 — e.g., "Multiple teams needed to integrate with this component"]
- [Reason 2 — e.g., "Inconsistent approaches were causing integration failures"]
- [Reason 3 — e.g., "Cost of reversing this choice after implementation is high"]

### 2.3 Timeline

| Date | Event |
|------|-------|
| [Date] | [Problem first identified] |
| [Date] | [Analysis / research conducted] |
| [Date] | [Decision made] |
| [Date] | [Implementation started / complete] |

---

## 3. Decision Drivers
[TYPE: REFERENCE]

<!--
WHY THIS SECTION EXISTS:
The explicit criteria used to evaluate options.
Without documented drivers, future readers cannot assess whether
the decision is still appropriate as conditions change.

PRECISION: These should be the ACTUAL drivers, not idealized ones.
If cost was the primary driver, say so. If a technology champion
pushed for an option, say so. Honesty enables better future decisions.
-->

### 3.1 Primary Drivers (Must-Have)

These were non-negotiable requirements. An option failing any of these was eliminated.

| Driver | Requirement | Why Non-Negotiable |
|--------|-------------|-------------------|
| [Driver 1] | [Specific requirement] | [Why this had to be met] |
| [Driver 2] | [Specific requirement] | [Why] |

### 3.2 Secondary Drivers (Strong Preference)

These weighted heavily in favor of options that met them.

| Driver | Preference | Weight |
|--------|-----------|--------|
| [Driver] | [What was preferred] | HIGH / MEDIUM |

### 3.3 Constraints (Non-Negotiable External Constraints)

These were not drivers we chose — they were constraints we had to work within.

- [Constraint 1 — e.g., "Budget cap of $X/month for infrastructure"]
- [Constraint 2 — e.g., "Must be deployable on existing AWS account"]
- [Constraint 3 — e.g., "Team has no Go experience — new language requires justification"]

### 3.4 What Was NOT a Driver

[Being explicit about what was intentionally excluded from decision criteria
helps future readers understand the decision boundaries.]

- [Non-driver 1 — e.g., "Long-term vendor pricing was not a driver — short runway project"]
- [Non-driver 2]

---

## 4. Options Considered
[TYPE: REFERENCE]

<!--
WHY THIS SECTION EXISTS:
Documents what was evaluated, not just what was chosen.
This is critical for AI agents — an agent asked "why not use X?"
can answer from this section without having to reason from scratch.

FAIRNESS REQUIREMENT: Document rejected options accurately and fairly.
Do not make rejected options look worse than they were.
The chosen option should win on documented merits, not by being
contrasted with a strawman.
-->

### 4.1 Options Overview

| Option | Brief Description | Outcome |
|--------|------------------|---------|
| **[Option A — CHOSEN]** | [One line] | ✅ Selected |
| [Option B] | [One line] | ❌ Rejected |
| [Option C] | [One line] | ❌ Rejected |
| [Option D — if applicable] | [One line] | ⏸️ Deferred |

### 4.2 Detailed Option Analysis

#### Option A: [Name] ✅ CHOSEN

**Description:** [What this option is]

**Pros:**
- [Advantage 1]
- [Advantage 2]
- [Advantage 3]

**Cons:**
- [Disadvantage 1]
- [Disadvantage 2]

**Risk:** [Primary risk of choosing this option]

---

#### Option B: [Name] ❌ REJECTED

**Description:** [What this option is — describe it fairly]

**Pros:**
- [Advantage 1 — include real advantages]
- [Advantage 2]

**Cons:**
- [Disadvantage 1]
- [Disadvantage 2]

**Why rejected:** [Specific reason — reference decision drivers from Section 3]

---

#### Option C: [Name] ❌ REJECTED

[Repeat pattern]

**Why rejected:** [Reason — be precise. "Not good enough" is not acceptable.
Reference the specific driver it failed to meet.]

---

#### Option D: [Name] ⏸️ DEFERRED (if applicable)

**Description:** [What this option is]

**Why deferred (not rejected):** [What would need to change for this to be reconsidered]

**Revisit condition:** [Specific condition — e.g., "If team headcount exceeds 20 engineers"]

---

## 5. Decision Rationale
[TYPE: DECISION]

<!--
WHY THIS SECTION EXISTS:
The core of the decision record. Connects the drivers (Section 3)
to the chosen option (Section 4) with explicit reasoning.

WRITE AS: A logical argument. "Because of X (driver) and given
that Option A satisfies it better than B because of Y, we chose A."

THIS IS NOT: A generic endorsement of the chosen option.
It must directly address why it was chosen OVER the alternatives.
-->

### 5.1 Why [Chosen Option] Was Selected

[2-4 paragraphs of explicit reasoning. Each paragraph addresses one
key driver and explains how the chosen option satisfied it better
than the alternatives.]

**On [Primary Driver]:** [How the chosen option satisfied this driver.
Compare directly with how alternatives failed or fell short.]

**On [Secondary Driver]:** [Reasoning]

**On [Key Trade-off]:** [The chosen option has [weakness] — this was
accepted because [specific reason]. The trade-off was considered
acceptable because [reasoning].]

### 5.2 Trade-offs Accepted

These known downsides of the chosen option were explicitly accepted:

| Trade-off | Why Accepted | Mitigation |
|----------|-------------|------------|
| [Downside 1] | [Reasoning] | [How it's mitigated or managed] |
| [Downside 2] | [Reasoning] | [Mitigation] |

### 5.3 Assumptions Made

This decision depends on these assumptions remaining true:

| Assumption | Risk If False | Monitoring |
|-----------|--------------|------------|
| [Assumption 1] | [What would change] | [How to detect if assumption fails] |
| [Assumption 2] | [Impact] | [Signal to watch] |

---

## 6. Consequences & Constraints
[TYPE: REFERENCE]

<!--
WHY THIS SECTION EXISTS:
The most practically important section for AI agents and developers.
What MUST happen as a result of this decision?
What is NOW FORBIDDEN that wasn't before?
What is REQUIRED that wasn't required before?

This is what other documents reference when they cite this decision.
-->

### 6.1 Positive Consequences

What this decision enables or improves:

- [Positive consequence 1]
- [Positive consequence 2]
- [Positive consequence 3]

### 6.2 Negative Consequences

What this decision restricts, complicates, or costs:

- [Negative consequence 1]
- [Negative consequence 2]

### 6.3 Constraints Created

**The following are NOW REQUIRED by this decision:**
- [Requirement 1 — e.g., "All new services in this domain must use PostgreSQL"]
- [Requirement 2 — e.g., "Schema changes require a Flyway migration file"]
- [Requirement 3]

**The following are NOW PROHIBITED by this decision:**
- [Prohibition 1 — e.g., "MongoDB may not be used in Order Service"]
- [Prohibition 2]
- [Prohibition 3]

**Exception process:** [How to get an exception to these constraints — approval process]

### 6.4 Impact on Existing Systems / Documents

| System / Document | Impact | Action Required |
|------------------|--------|----------------|
| [System/Doc] | [How it's affected] | [What needs to change] |

---

## 7. Implementation Notes
[TYPE: PROCEDURE]

<!--
NOT a full procedure (use TMPL-003 for that).
Brief notes that help implementers execute this decision correctly.
-->

### 7.1 Implementation Approach

[1-3 paragraphs or a list — how this decision should be implemented.
Reference the relevant TMPL-003 procedure if one exists or should exist.]

### 7.2 Implementation Risks

| Risk | Likelihood | Impact | Mitigation |
|------|-----------|--------|------------|
| [Risk 1] | HIGH / MEDIUM / LOW | HIGH / MEDIUM / LOW | [How to reduce] |

### 7.3 Success Indicators

How to know the decision is being implemented correctly:

- [Indicator 1 — observable sign that implementation is on track]
- [Indicator 2]

---

## 8. Review Conditions
[TYPE: REFERENCE]

<!--
WHY THIS SECTION EXISTS:
Decisions should be revisited when conditions change.
This section defines WHEN to reconsider, not that it
should be reconsidered on a fixed schedule.
-->

### 8.1 Conditions That Would Trigger Re-evaluation

This decision should be revisited if any of the following occur:

- [Trigger 1 — e.g., "System load exceeds N req/sec sustained"]
- [Trigger 2 — e.g., "Team expands to include Go expertise"]
- [Trigger 3 — e.g., "Chosen technology is end-of-life or unsupported"]
- [Trigger 4 — e.g., "Assumption in Section 5.3 is proven false"]

### 8.2 What Has NOT Changed This Decision

[Document any time this decision was reviewed and upheld.
This prevents repeated re-litigation of settled decisions.]

| Date | Review Trigger | Outcome | Reviewer |
|------|---------------|---------|---------|
| [Date] | [What caused review] | UPHELD / SUPERSEDED | [Name/role] |

### 8.3 Supersession Process

To supersede this decision, complete all six steps in order:

**Step 1 — Create the replacement decision record**
Create a new TMPL-005 document covering the new decision. Set
`supersedes: [this document ID]` in the new document's frontmatter.

**Step 2 — Update this document's frontmatter**
Set `superseded_by: [new document ID]` and `status: Superseded`.

**Step 3 — Add the supersession warning block**
Insert this block immediately after the frontmatter closing `---`, before
any document body content:

```markdown
> ⚠️ SUPERSEDED: This document has been replaced by [New Document ID]
> dated [YYYY-MM-DD]. Do not use for new work or new cross-references.
> Current decision: [one-sentence summary of what replaced it].
```

**Step 4 — RAG index action (immediate)**
Apply a `status: superseded` metadata filter to this document's chunks
in the vector index. This excludes them from default retrieval queries
immediately. Do not wait for the 30-day grace period to act on this step.

**Step 5 — RAG index cleanup (after 30-day grace period)**
After 30 days, remove this document's chunks from the active vector index
entirely. Retain the source Markdown file indefinitely — archive to
`_superseded/` folder or prefix filename with `_SUPERSEDED_`. Only index
entries are removed; the source record is permanent.

**Step 6 — Update dependent documents**
Update every document containing `[APPLIES] →`, `[DEPENDS_ON] →`, or
`[IMPLEMENTS] →` references to this document. Either point to the new
decision or add: `⚠️ [FLAGGED — references superseded document; see [New ID]]`

---

## 9. Cross-References
[TYPE: REFERENCE]

### 9.1 Documents That Led to This Decision

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [VALIDATED_BY] | [TMPL-001 Research Doc] | [Section] | [Research that informed this decision] |
| [DEPENDS_ON] | [Constraint / Policy doc] | [Section] | [External constraint that drove this decision] |

### 9.2 Documents That Are Governed by This Decision

| Relationship | Document | Section | How Governed |
|-------------|----------|---------|-------------|
| [IMPLEMENTS] | [TMPL-002 Technical Ref] | [Section] | [How that doc implements this decision] |
| [APPLIES] | [TMPL-003 Procedure] | [Section] | [Procedure that follows this decision] |
| [APPLIES] | [ARCH-001] | [Section] | [Architecture constrained by this] |

### 9.3 Decisions Related to This One

| Relationship | Document | Relationship Description |
|-------------|----------|------------------------|
| [SUPERSEDES] | [Previous decision ID] | [What changed] |
| [SEE_ALSO] | [Related decision] | [How they relate] |

---

## 10. Revision History

<!--
IMPORTANT: Decision content is IMMUTABLE.
Only metadata changes (status, superseded_by) appear here.
If the DECISION itself changed, a new document was created — not this one.
-->

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | [Date] | Initial | Decision record created | [Context — when/why formalized] |

---

*Template: TMPL-005 Decision Record v1.0 | Parent: TMPL-000 Template Index*
