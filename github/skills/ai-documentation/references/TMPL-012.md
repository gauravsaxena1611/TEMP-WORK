# [Meeting / Session Title]
## [Subtitle — e.g., "Sprint Planning — Week 14" or "Architecture Review: Payment Service Redesign"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-012: SESSION & MEETING RECORD
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: Any structured meeting or working session where you
need a permanent, searchable record of: what was discussed,
what was decided, and what actions were committed to.

USE CASES:
  - Sprint planning, retrospectives, standups with decisions
  - Architecture or design review sessions
  - Stakeholder alignment or strategy meetings
  - AI agent working sessions (Claude, Copilot, etc.)
  - Project kick-offs and post-mortems (light-weight version)
  - Client or vendor meetings with committed actions

THIS IS NOT FOR:
  - Formal decisions requiring full rationale → use TMPL-005
  - Full incident post-mortems → use TMPL-008
  - Research syntheses → use TMPL-001

DISTINCTION FROM TMPL-005:
  TMPL-006 captures the DISCUSSION and ACTIONS from a session.
  TMPL-005 captures a SINGLE FORMAL DECISION with full context.
  When a decision made in a session is significant enough to
  warrant a formal record, create a TMPL-005 from the decision
  summary in Section 4 of this document.

AI-OPTIMIZATION NOTES:
  - Section 1 (header) is the highest-priority RAG chunk.
    Write it so an agent reading only Section 1 knows: what
    meeting, when, who, and what the most important outcome was.
  - Section 4 decisions and Section 5 actions are tagged with
    semantic markers for agent extraction.
  - Keep decision and action items atomic — one per entry.
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ──────────────────────────────
document_id: "[XXX Session-Title-Date]"
title: "[Meeting/Session Title] — [YYYY-MM-DD]"
version: "1.0"
created: "YYYY-MM-DD"
status: "Draft | Final"
parent_document: ""
template_version_used: "TMPL-006 v1.0"

# ── SESSION METADATA ─────────────────────────────────────────
session_type: "planning | review | alignment | retrospective | working-session | client | vendor | ai-session | other"
session_date: "YYYY-MM-DD"
session_duration: "[N hours / N minutes]"
facilitator: ""
participants: []              # Names or roles
quorum_met: "yes | no | n/a"  # Was required attendance present?
recording_available: "yes | no | link: [URL]"

# ── OUTCOME SUMMARY ──────────────────────────────────────────
outcome_type: "decisions-made | actions-only | discussion-only | blocked | inconclusive"
formal_decisions_count: 0     # Number of decisions needing TMPL-005 follow-up
open_actions_count: 0         # Total action items opened this session

# ── AI-OPTIMIZATION EXTENSION ────────────────────────────────
intent: >
  Provide a searchable, permanent record of the [session title]
  held on [date], capturing the agenda outcomes, decisions made,
  action items assigned, and context for future reference.

consumption_context:
  - human-reading
  - ai-reasoning
  - rag-retrieval

triggers:
  - "[meeting title] notes"
  - "[meeting title] [date]"
  - "what was decided in [session name]"
  - "action items from [date] [meeting]"
  - "[participant name] [topic] meeting"

negative_triggers:
  - "[topic] formal decision" # → TMPL-005 Decision Record if a full ADR is needed
  - "[topic] incident report" # → TMPL-008 Post-Mortem

volatility: "snapshot"
  # snapshot: session records are point-in-time — they do not change

review_trigger: "N/A — snapshot document; update only to correct factual errors within 48 hours of session"

confidence_overall: "high"
confidence_note: "Session record — reflects what was discussed and agreed; validated by participants"
---
```

---

> ## 🤖 AI Summary
> **Session:** [Title] — [YYYY-MM-DD]
> **Type:** [Session type]
> **Participants:** [N] — [Names or key roles]
> **Most Important Outcome:** [One sentence — the single most significant decision or action from this session]
> **Decisions Made:** [N] — [Highest-priority decision in one phrase]
> **Open Actions:** [N] items — [N] owners — earliest due [date]
> **Formal Decision Records Needed:** [N] — [Topic(s) requiring TMPL-005 follow-up / None]
> **Next Meeting:** [Date if scheduled / TBD]

---

## TABLE OF CONTENTS

1. [Session Header](#1-session-header)
2. [Agenda & Discussion Summary](#2-agenda--discussion-summary)
3. [Parking Lot](#3-parking-lot)
4. [Decisions Made](#4-decisions-made)
5. [Action Items](#5-action-items)
6. [Next Steps](#6-next-steps)
7. [Appendix](#7-appendix)
8. [Revision History](#8-revision-history)

---

## 1. Session Header
[TYPE: REFERENCE]

<!--
CHUNKING NOTE: This section is the most frequently retrieved.
Write it so an agent reading ONLY Section 1 can answer:
  "What was this meeting about and what came out of it?"
Keep under 300 words.
-->

### 1.1 Session Details

The [session title] was held on [date] to [purpose — one sentence].

| Attribute | Value |
|-----------|-------|
| **Session Type** | [Type from frontmatter] |
| **Date** | [YYYY-MM-DD] |
| **Time** | [HH:MM – HH:MM timezone] |
| **Duration** | [N hours] |
| **Facilitator** | [Name / Role] |
| **Note-taker** | [Name / Role / "AI-assisted"] |
| **Location** | [Room / Video link / "Async"] |

### 1.2 Participants

| Name / Role | Present? | Notes |
|-------------|---------|-------|
| [Name — Role] | ✅ Yes | [Any relevant context — e.g., "Joined late"] |
| [Name — Role] | ✅ Yes | |
| [Name — Role] | ❌ No | [Reason — e.g., "Represented by [name]"] |

**Quorum:** [Met / Not met — explain if not met and whether decisions are still valid]

### 1.3 Session Purpose & Outcome

**Purpose:** [1–2 sentences — why this session was called and what it needed to resolve]

**Outcome:** [1–2 sentences — what was actually achieved. Be honest: "discussion only, no decisions" is a valid outcome]

---

## 2. Agenda & Discussion Summary
[TYPE: EXPLANATION]

<!--
WHY THIS SECTION EXISTS:
Captures what was discussed for each agenda item.
Not a verbatim transcript — a structured summary.
Each agenda item gets its own subsection.
Write at ~150–250 words per item to stay in optimal chunk range.
AI agents extract context from here to understand WHY decisions were made.
-->

### 2.1 [Agenda Item 1 Title]

**Presented by:** [Name / Role]
**Time allocated:** [N min] | **Time used:** [N min]

[2–4 sentences summarizing what was discussed for this item. Include the key
perspectives raised, data or evidence presented, and the nature of the discussion
(consensus reached quickly vs. contested vs. tabled for later).]

**Key points raised:**
- [Point 1 — who raised it and what the substance was]
- [Point 2]
- [Point 3 — include dissenting views where relevant]

**Outcome for this item:** [Decided / Action assigned / Tabled / No action needed]
[If decided: summarize decision in one sentence — full record in Section 4]
[If tabled: state the reason and when it will be revisited]

---

### 2.2 [Agenda Item 2 Title]

**Presented by:** [Name / Role]
**Time allocated:** [N min] | **Time used:** [N min]

[Discussion summary]

**Key points raised:**
- [Point 1]
- [Point 2]

**Outcome for this item:** [Decided / Action assigned / Tabled / No action]

---

### 2.3 [Agenda Item N — repeat pattern]

<!--
Add as many 2.N subsections as agenda items.
Keep each at 150–250 words for optimal RAG chunking.
Use sub-bullets for granular points, not paragraphs.
-->

---

## 3. Parking Lot
[TYPE: OPEN_QUESTION]

<!--
WHY THIS SECTION EXISTS:
Items that came up during the session but were explicitly deferred —
not forgotten, not rejected, but consciously parked for another time.
AI agents and future readers know these exist and are not yet resolved.
-->

| Item | Raised By | Parked Reason | Owner | Revisit When |
|------|-----------|---------------|-------|-------------|
| [Topic that came up but was not addressed] | [Name] | [Out of scope / time / needs more info] | [Who will follow up] | [Date / condition] |
| [Item 2] | | | | |

**If parking lot is empty:** "No items parked this session."

---

## 4. Decisions Made
[TYPE: DECISION]

<!--
WHY THIS SECTION EXISTS:
The authoritative record of every decision made in this session.
Each decision gets one entry. Keep them atomic — one decision per entry.
For decisions significant enough to warrant full context,
create a TMPL-005 Decision Record and reference it here.

SEMANTIC TAGGING FOR AI EXTRACTION:
Each decision entry is tagged [DECISION] for agent extraction.
-->

### 4.1 Decision Log

| # | Decision | Made By | Confidence | TMPL-005 Needed? |
|---|----------|---------|------------|-----------------|
| D-01 | [Decision stated clearly in past tense] | [Consensus / Name] | HIGH / MEDIUM | YES — [topic] / NO |
| D-02 | [Decision] | | HIGH / MEDIUM | YES / NO |
| D-03 | [Decision] | | | YES / NO |

**If no decisions were made:** "No formal decisions reached this session. See parking lot for deferred items."

### 4.2 Decision Detail

For each decision that needs more context than the table row provides:

**D-01 — [Decision title]** `[DECISION]`

[2–3 sentences providing context: what problem this resolves, what alternatives were considered (briefly), and any conditions or exceptions that apply.]

**Constraint created:** [If this decision creates a rule that other work must follow, state it explicitly here.]
**TMPL-005 required:** [YES — [who creates it, by when] / NO]

---

**D-02 — [Decision title]** `[DECISION]`

[Detail]

---

## 5. Action Items
[TYPE: PROCEDURE]

<!--
WHY THIS SECTION EXISTS:
Every commitment made in this session, tracked with owner and due date.
Each action is atomic — one action, one owner, one due date.
AI agents extract action items from this section to drive follow-up.

SEMANTIC TAGGING: Each item is tagged [ACTION] for agent extraction.
Status on creation is always OPEN. Update during follow-up.
-->

### 5.1 Action Item Log

| # | Action | Owner | Due Date | Priority | Status |
|---|--------|-------|---------|----------|--------|
| A-01 | [Specific action — action verb + deliverable] `[ACTION]` | [Name] | [YYYY-MM-DD] | HIGH / MED / LOW | OPEN |
| A-02 | [Action] `[ACTION]` | [Name] | [YYYY-MM-DD] | | OPEN |
| A-03 | [Action] `[ACTION]` | [Name] | [YYYY-MM-DD] | | OPEN |

**Total open actions:** [N]
**Earliest due:** [YYYY-MM-DD — which action]

### 5.2 Action Detail

For complex actions needing more context than the table row provides:

**A-01 — [Action title]** `[ACTION]`

**Context:** [Why this action exists — what problem it solves or decision it implements]
**Deliverable:** [Exactly what is expected — document, code, decision, meeting, etc.]
**Success criteria:** [How the owner knows the action is complete]
**Blockers (if any):** [What might prevent completion — flag immediately if blocked]
**Depends on:** [Other actions or decisions this depends on, if any]

---

## 6. Next Steps
[TYPE: REFERENCE]

### 6.1 Next Meeting

| Attribute | Value |
|-----------|-------|
| **Scheduled** | [YYYY-MM-DD HH:MM timezone / TBD] |
| **Purpose** | [What the next session needs to cover] |
| **Pre-work required** | [What participants need to do before the next session] |
| **Owner to schedule** | [Name] |

### 6.2 Follow-Up Documents to Create

| Document | Template | Owner | Due |
|----------|----------|-------|-----|
| [Document that needs to be created as follow-up] | [TMPL-00X] | [Name] | [Date] |
| [e.g., "Decision record for architecture choice"] | TMPL-005 | [Name] | [Date] |

### 6.3 Escalations Required

[If any issues raised in this session need escalation to a higher authority or different team:]

| Issue | Escalation Target | Owner | By When |
|-------|-----------------|-------|---------|
| [Issue requiring escalation] | [Who or what level] | [Name] | [Date] |

**If no escalations required:** "No escalations required from this session."

---

## 7. Appendix
[TYPE: REFERENCE]

<!--
Optional section. Include only if the session produced supporting materials.
Do not replicate content available elsewhere — link or reference it.
-->

### 7.1 Materials Presented

| Title | Author | Format | Location |
|-------|--------|--------|----------|
| [Presentation or document presented] | [Name] | [Slide deck / doc / demo] | [URL or path] |

### 7.2 Data & Evidence Referenced

| Data Point | Source | Used For |
|-----------|--------|---------|
| [Metric or finding cited] | [Document or speaker] | [Which agenda item] |

### 7.3 Verbatim Quotes (Significant Only)

*Include only quotes that will matter to future readers — decisions, commitments, or context-setting statements.*

> "[Quote]" — [Name], [context]

---

## 8. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | [Date] | Initial | Session record created | [Meeting title and date] |
| 1.1 | [Date] | Correction | [What was corrected] | [Factual error found within 48 hours] |

---

*Template: TMPL-006 Session & Meeting Record v1.0 | Parent: TMPL-000 Template Index*
*Snapshot document — content fixed after 48-hour correction window*
