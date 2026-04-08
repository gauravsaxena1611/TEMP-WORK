# [Project/Domain Name]: Living Context
## [Subtitle — e.g., "Cross-Session Agent Memory: AI Documentation Project"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-004C: LIVING CONTEXT DOCUMENT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: Persistent, cross-session context that an agent reads
at the start of EVERY session and updates at the end of EVERY
session. This document is the agent's long-term memory for a
project or domain.

THIS IS NOT FOR:
  - One-time task briefing → use TMPL-004A
  - Single-session workflow execution → use TMPL-004B
  - Human-readable project documentation → use TMPL-001/002

RELATIONSHIP TO ARCH-001:
  ARCH-001 .ctx.md files are SKILL-specific operational context
  (e.g., doc-standards.ctx.md for the ai-documentation skill).
  TMPL-004C is PROJECT-LEVEL living context that spans multiple
  skills and sessions.
  See: ARCH-001-TMPL004-Reconciliation for the full decision.

WRITE ACCESS RULES:
  Agent has write access to Sections 3.2, 6, and 7 ONLY.
  All other sections are HUMAN-authored and HUMAN-updated.
  Agent MUST NOT modify Sections 1, 2, 3.1, 4, 5, 8, 9.
  Appends to Sections 6 and 7 are append-only — no editing
  of prior entries.

SESSION LIFECYCLE:
  START OF SESSION:
    1. Agent reads full document (or Sections 1–4 minimum)
    2. Agent checks Section 3.2 for recent changes
    3. Agent checks Section 6.2 for pending observations
       that affect today's work
  END OF SESSION:
    1. Agent appends to Section 6.2 (new observations)
    2. Agent updates Section 3.2 (current state changes)
    3. Agent appends to Section 7 (session log entry)

HUMAN MAINTENANCE:
  After reviewing Section 6.2 pending observations:
    APPLY → move to Section 6.1 with confirmation date
    REJECT → move to Section 6.3 with rejection reason
    DEFER → annotate with "DEFER — [reason]"
  Refresh Sections 2 and 4 when facts change.

AGENT VALIDATION CHECKLIST (run after authoring):
  [ ] Does Section 2 contain only facts that rarely change?
  [ ] Does Section 3.1 contain only current, volatile state?
  [ ] Is Section 4.1 (never do) an absolute list, not guidelines?
  [ ] Are all Section 5 context dependencies listed with staleness dates?
  [ ] Are agent write boundaries clearly marked on each section?
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ──────────────────────────────
document_id: "[XXX Short-Title]-living-context"
title: "[Project/Domain] — Living Context"
version: "1.0"
created: "YYYY-MM-DD"
status: "Living"
parent_document: ""
template_version_used: "TMPL-004C v1.1"

# ── LIVING CONTEXT METADATA ───────────────────────────────────
tmpl_variant: "living-context"
context_domain: ""             # What project or domain this context covers
serves_agents: []              # Which agents / skills read this context
agent_write_access: true       # YES — agents write to Sections 3.2, 6, 7 only
agent_write_sections: ["3.2", "6", "7"]

# ── CURRENCY TRACKING ────────────────────────────────────────
last_agent_session: "YYYY-MM-DD"
last_human_review: "YYYY-MM-DD"
pending_observations_count: 0  # Update this when Section 6.2 has new items

# ── AI-OPTIMIZATION EXTENSION ────────────────────────────────
intent: >
  Maintain and accumulate project knowledge, current state, and
  agent learnings across all sessions working on [project/domain],
  enabling any agent in this context to pick up work without
  re-establishing context from scratch.

consumption_context:
  - ai-reasoning
  - agentic-execution

triggers:
  - "[project name] context"
  - "what do we know about [domain]"
  - "what is the current state of [project]"
  - "living context for [project]"
  - "[project abbreviation or alias]"

negative_triggers:
  - "[specific task — use a workflow plan instead]"
  - "[one-off question — answer from stable knowledge directly]"

volatility: "living"
  # living: this document is updated continuously

review_trigger: "After every agent session (agent updates Sections 3.2, 6, 7); human reviews Section 6.2 weekly or before major decisions"

confidence_overall: "conditional"
confidence_note: "Currency depends on last_agent_session and last_human_review dates — check before relying on current-state sections"
---
```

---

> ## 🤖 AI Summary
> **Document Type:** LIVING CONTEXT (TMPL-004C) — agent reads and updates this document
> **Domain:** [Project / Domain name]
> **Last Agent Session:** [YYYY-MM-DD] — [how recent this is]
> **Last Human Review:** [YYYY-MM-DD] — [how recently a human validated this]
> **Pending Observations:** [N] items in Section 6.2 awaiting human review
> **Agent Write Access:** YES — Sections 3.2, 6, and 7 only
> **Warning:** If last_agent_session or last_human_review is >30 days old, verify Section 3.2 current state with the human before acting on it.

---

## TABLE OF CONTENTS

1. [Document Identity & Purpose](#1-document-identity--purpose) ← Human-authored
2. [Stable Knowledge Base](#2-stable-knowledge-base) ← Human-authored, rarely changes
3. [Current State](#3-current-state) ← Section 3.1 human; 3.2 agent-writable
4. [Behavioral Configuration](#4-behavioral-configuration) ← Human-authored
5. [Context Dependencies](#5-context-dependencies) ← Human-authored
6. [Observations & Learnings Log](#6-observations--learnings-log) ← Agent-writable (append-only)
7. [Session Log](#7-session-log) ← Agent-writable (append-only)
8. [Open Questions & Human Input Needed](#8-open-questions--human-input-needed)
9. [Revision History](#9-revision-history)

---

## 1. Document Identity & Purpose
[TYPE: REFERENCE]
*Human-authored — agent must not modify this section.*

### 1.1 What This Context Covers

This living context document provides persistent memory for [project/domain name].
It covers [specific scope — e.g., "all documentation work in the AI Documentation
Skill project across any number of Claude sessions"].

**This context is read by:** [Which agents or skills consume this document]

**This context covers:** [Scope — specific project, domain, or system]

**This context does NOT cover:** [Explicit exclusions — other contexts exist for those areas]

### 1.2 How to Use This Document

**At the start of each session:**
1. Read Sections 1–4 (or at minimum: Section 2 stable knowledge + Section 3.2 recent changes)
2. Check `last_human_review` date — if >30 days, flag current-state sections as potentially stale
3. Check Section 6.2 for pending observations that affect today's work

**At the end of each session:**
1. Append to Section 6.2 with new observations (append-only — never edit previous entries)
2. Update Section 3.2 if current state changed during the session
3. Append a one-line entry to Section 7 session log

---

## 2. Stable Knowledge Base
[TYPE: REFERENCE]
*Human-authored — updated only when fundamental project facts change.*

<!--
STABLE = facts that are true for months or years.
If something changes more than once a quarter, it belongs in Section 3, not Section 2.
Each subsection should be independently retrievable — open with context anchor.
-->

### 2.1 Project / System Facts

The [project/system name] is [description]. The following facts are stable
and rarely change:

| Fact | Value | Last Verified |
|------|-------|--------------|
| [Technology stack] | [e.g., Java 17 / Spring Boot 3.2] | [Date] |
| [Database] | [e.g., PostgreSQL 15] | [Date] |
| [Deployment platform] | [e.g., AWS EKS] | [Date] |
| [Repository] | [e.g., github.com/org/repo] | [Date] |
| [Team] | [e.g., "Backend team, 5 engineers"] | [Date] |

### 2.2 Standards & Governing Decisions

The [project] follows these standards that govern all agent work:

| Standard / Decision | Document | Key Constraint |
|--------------------|----------|----------------|
| [Standard 1] | [Document ID] | [The specific rule the agent must follow] |
| [Decision 1] | [Decision record ID] | [The constraint that follows] |
| [Standard 2] | [Document ID] | [Rule] |

### 2.3 Domain Vocabulary

Key terms the agent must use consistently:

| Term | Definition | Do NOT use instead |
|------|-----------|-------------------|
| [Term] | [Precise definition] | [Common wrong term] |
| [Term] | [Definition] | [Synonym to avoid in this context] |

---

## 3. Current State
[TYPE: REFERENCE]
*Section 3.1: Human-authored. Section 3.2: Agent-writable.*

### 3.1 Current Phase & Major Milestones
*Human-authored — agent must not modify 3.1.*

| Milestone | Status | Date | Notes |
|-----------|--------|------|-------|
| [Milestone 1] | [Complete / In Progress / Blocked] | [Date] | [Context] |
| [Milestone 2] | [Status] | [Date] | [Notes] |
| [Current active goal] | In Progress | [Date] | [Description] |

**Current blockers (human-identified):**
- [Blocker 1 — what is blocked, why, who owns the unblock]
- [None] ← update when blockers clear

### 3.2 Recent Changes & Agent-Observed State
*Agent-writable — append in reverse chronological order. Format: [YYYY-MM-DD] [Description]*

<!-- Agent appends to this section. Do not edit previous entries. -->

| Date | Change | Observed By | Impact |
|------|--------|-------------|--------|
| [YYYY-MM-DD] | [What changed] | [human / agent] | [What this means for ongoing work] |

---

## 4. Behavioral Configuration
[TYPE: REFERENCE]
*Human-authored — agent must not modify this section.*

<!--
These are the standing behavioral rules for any agent working in this context.
More specific to this project than the general constraints in TMPL-004A.
-->

### 4.1 Standing Constraints (Never Do in This Context)

These apply to all agents working in this project:

- **NEVER** [project-specific prohibition 1 — e.g., "Modify archived decision records"]
- **NEVER** [prohibition 2 — e.g., "Create new document IDs without checking CTX-DOCSTD"]
- **NEVER** [prohibition 3 — e.g., "Change the status of a document to 'Final' without user confirmation"]

### 4.2 Standing Preferences (Always Apply in This Context)

These are the default choices when multiple approaches are valid:

- **PREFER** [preference 1 — e.g., "Append to existing documents over creating new ones when under 1,500 words"]
- **PREFER** [preference 2 — e.g., "Reference existing decision records over re-explaining decisions"]
- **PREFER** [preference 3 — e.g., "Use token-aware chunk targets from TMPL-000 Section 7"]

### 4.3 Confidence & Escalation Threshold

In this project context, escalate to the human when:
- Confidence is below 70% on any content claim (higher threshold than default 60%)
- A new document ID is needed and the human hasn't confirmed the series
- A decision contradicts an existing project decision record
- A change would affect 3+ documents simultaneously

---

## 5. Context Dependencies
[TYPE: REFERENCE]
*Human-authored — agent reads this to know what other context files exist.*

The following context files or documents are related to this living context.
The agent should check their currency before relying on them.

| Context ID | Document / File | Type | Last Updated | Staleness Risk | Load When |
|------------|----------------|------|-------------|----------------|-----------|
| CTX-DOCSTD | doc-standards.ctx.md | DECLARED | [Date] | LOW — changes infrequently | Every documentation session |
| [CTX-ID] | [file or document] | DECLARED / INFERRABLE | [Date] | HIGH / MEDIUM / LOW | [Specific condition] |
| [CTX-ID] | [file or document] | SCANNABLE | [Date] | [Risk] | [Condition] |

**Staleness thresholds for this project:**
- LOW risk: reliable for 30+ days
- MEDIUM risk: verify if >14 days old
- HIGH risk: verify at session start if >7 days old

---

## 6. Observations & Learnings Log
[TYPE: REFERENCE]
*Agent-writable (append-only). Human reviews 6.2 and promotes to 6.1 or 6.3.*

### 6.1 Validated Learnings (Human-Applied)

These learnings have been reviewed and confirmed by a human. They are active.

| ID | Date Applied | Learning | Evidence | Confidence |
|----|-------------|----------|----------|------------|
| L-001 | [Date] | [What was learned] | [How discovered / N occurrences] | HIGH |

### 6.2 Pending Observations (Agent-Observed — Awaiting Human Review)

*Agent appends here. Human reviews and moves entries to 6.1 (APPLY) or 6.3 (REJECT).*
*Maximum 20 pending entries before human review is required.*

**Review instructions:**
- **APPLY:** The observation is correct. Move to 6.1, set status to APPLIED.
- **REJECT:** The observation is incorrect or not actionable. Move to 6.3 with reason.
- **DEFER:** Keep here, annotate "DEFER — [reason, revisit date]".

| ID | Date | Observation | Evidence | Recommended Action | Confidence |
|----|------|-------------|----------|--------------------|------------|
| O-001 | [Date] | [What the agent observed or noticed] | [Specific evidence — quotes, counts, file refs] | [What change this suggests] | HIGH / MEDIUM / LOW |

### 6.3 Rejected Observations

| ID | Date Rejected | Observation | Rejection Reason |
|----|--------------|-------------|-----------------|
| R-001 | [Date] | [Observation text] | [Why it was rejected] |

---

## 7. Session Log
[TYPE: REFERENCE]
*Agent-writable — append one entry per session. Never edit previous entries.*

| Date | Session Summary | Sections Updated | Pending Items Left |
|------|----------------|------------------|--------------------|
| [YYYY-MM-DD] | [One sentence: what was accomplished] | [e.g., "3.2, 6.2"] | [What remains for next session] |

---

## 8. Open Questions & Human Input Needed
[TYPE: OPEN_QUESTION]
*Human reviews this section and provides answers or delegations.*

### 8.1 Blocking Questions (Agent Cannot Proceed Without Answer)

- [ ] [Question 1 — specific, answerable, explains why it's blocking]
- [ ] [Question 2]

### 8.2 Non-Blocking Questions (Input Improves Quality)

- [ ] [Question that would improve agent work but doesn't block it]
- [ ] [Question]

### 8.3 Context Files Needing Creation or Refresh

| Context ID | File | Status | Action Needed | Owner |
|------------|------|--------|--------------|-------|
| [CTX-ID] | [file] | MISSING / STALE | [Create / Refresh] | [Human / Agent] |

---

## 9. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | [Date] | Initial | Living context document created | [Why this was created — what prompted the need for persistent cross-session context] |

---

*Template: TMPL-004C Living Context Document v1.1 | Parent: TMPL-000 Template Index*
*Agent write access: Sections 3.2, 6 (append-only), and 7 (append-only)*
*Human must not be bypassed for Section 6.1 — agent cannot self-promote its own observations*
