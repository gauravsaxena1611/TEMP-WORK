# Incident Post-Mortem: [Incident Title]
## [Subtitle — e.g., "P0 Outage — Payment Processing Down 47 Minutes — 2026-03-15"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-008: INCIDENT POST-MORTEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: Structured root cause analysis and remediation
tracking after any significant incident, outage, or failure.

USE CASES:
  - Production outages and service degradations
  - Security incidents and data breaches
  - Failed deployments with user impact
  - AI system misbehavior causing harm
  - Process failures with significant downstream impact
  - Near-misses worth learning from

THIS IS NOT FOR:
  - Routine bug reports → use your issue tracker
  - General retrospectives → use TMPL-006
  - Documenting a process decision → use TMPL-005

BLAMELESS CULTURE PRINCIPLES (apply throughout):
  1. Systems fail, not people. Seek systemic causes.
  2. Anyone in the same situation with the same information
     would have made the same decisions.
  3. The goal is learning and prevention, not punishment.
  4. Naming individuals is permitted for timeline accuracy
     but never in a way that assigns blame.
  5. "Human error" is never a root cause — it is a symptom
     of a system that allowed the error to occur.

AUTHORING TIMELINE:
  - Draft Section 1 (Header) within 2 hours of resolution
  - Complete timeline (Section 3) within 24 hours
  - Complete root cause analysis (Section 4) within 72 hours
  - Final document with corrective actions within 1 week
  - 30-day follow-up review date set before publishing

SECURITY NOTE FOR AI INCIDENTS:
  If this incident involves an AI model or AI-enabled system,
  reference the model card (TMPL-007) in cross-references.
  Do not include model weights, training data samples, or
  any information that could aid adversarial exploitation.
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ──────────────────────────────
document_id: "[XXX Incident-Title-Date]"
title: "Post-Mortem: [Incident Title] — [YYYY-MM-DD]"
version: "1.0"
created: "YYYY-MM-DD"
status: "Draft | Review | Final"
parent_document: ""
template_version_used: "TMPL-008 v1.0"

# ── INCIDENT METADATA ─────────────────────────────────────────
incident_id: "[INC-YYYYMMDD-NNN]"  # Unique incident identifier
severity: "P0 | P1 | P2 | P3"
severity_definition: ""             # Your org's severity criteria
incident_start: "YYYY-MM-DD HH:MM UTC"
incident_end: "YYYY-MM-DD HH:MM UTC"
total_duration_minutes: 0
detection_to_response_minutes: 0    # Time from incident start to first response
response_to_resolution_minutes: 0   # Time from first response to resolution

incident_type: "availability | performance | security | data | ai-behavior | process | other"
affected_systems: []
affected_regions: []
customer_impact: "none | minimal | moderate | significant | critical"

# ── IMPACT QUANTIFICATION ────────────────────────────────────
users_affected: 0             # Estimated total users affected
transactions_affected: 0      # Relevant transactions failed/degraded
error_rate_peak: ""           # e.g., "94% 5xx rate at peak"
revenue_impact_estimate: ""   # e.g., "$12,000 — 47 min × $255/min baseline"
sla_breach: "yes | no"
sla_breach_minutes: 0

# ── AI-OPTIMIZATION EXTENSION ────────────────────────────────
intent: >
  Provide a permanent, searchable record of the [incident title]
  incident on [date], capturing the timeline, root cause, impact,
  and all corrective actions to prevent recurrence.

consumption_context:
  - human-reading
  - ai-reasoning
  - rag-retrieval

triggers:
  - "[incident ID] post-mortem"
  - "[system name] [date] outage"
  - "[incident type] root cause"
  - "what caused [incident title]"
  - "[affected system] incident history"

negative_triggers:
  - "[system name] how to deploy" # → TMPL-003
  - "[system name] technical reference" # → TMPL-002

volatility: "snapshot"
  # snapshot: post-mortems are point-in-time records; corrective
  # actions are tracked via status updates in Section 7

review_trigger: "30-day review: [YYYY-MM-DD] — check corrective action completion status"

confidence_overall: "high"
confidence_note: "Based on incident data, logs, and participant recollection — timeline accuracy depends on log quality"
---
```

---

> ## 🤖 AI Summary
> **Incident:** [Title] — [Severity] — [YYYY-MM-DD]
> **Duration:** [N minutes] — Detected: [HH:MM UTC] → Resolved: [HH:MM UTC]
> **Impact:** [N users affected] — [Customer impact level] — [SLA breach: YES/NO]
> **Root Cause (1 sentence):** [The single primary root cause — not a symptom]
> **Contributing Factors:** [N factors identified]
> **Corrective Actions:** [N total] — [N complete / N in progress / N not started]
> **30-Day Review Date:** [YYYY-MM-DD]
> **Blameless:** This document follows blameless post-mortem principles — no individuals are held responsible for systemic failures.

---

## TABLE OF CONTENTS

1. [Incident Header](#1-incident-header)
2. [Impact Assessment](#2-impact-assessment)
3. [Timeline](#3-timeline)
4. [Root Cause Analysis](#4-root-cause-analysis)
5. [Response Assessment](#5-response-assessment)
6. [Lessons Learned](#6-lessons-learned)
7. [Corrective Actions](#7-corrective-actions)
8. [30-Day Follow-Up Review](#8-30-day-follow-up-review)
9. [Cross-References](#9-cross-references)
10. [Revision History](#10-revision-history)

---

## 1. Incident Header
[TYPE: REFERENCE]

<!--
Complete this section within 2 hours of resolution.
An agent or reader who receives only this section must
understand: what broke, when, how long, and the severity.
Keep under 300 words.
-->

### 1.1 Incident Summary

The [system/service name] experienced a [incident type] incident on [date]
lasting [N minutes]. [1–2 sentences describing what users/customers experienced
during the incident — from their perspective, not the technical cause.]

| Attribute | Value |
|-----------|-------|
| **Incident ID** | [INC-YYYYMMDD-NNN] |
| **Severity** | [P0 / P1 / P2 / P3] |
| **Status** | RESOLVED |
| **Start Time** | [YYYY-MM-DD HH:MM UTC] |
| **End Time** | [YYYY-MM-DD HH:MM UTC] |
| **Total Duration** | [N hours N minutes] |
| **Affected Systems** | [System names] |
| **Incident Commander** | [Name / Role — for coordination only, not blame] |
| **Lead Responders** | [Names / Roles] |

### 1.2 One-Line Root Cause

**Root cause:** [One sentence describing the underlying systemic cause — this will be expanded in Section 4.]

*Note: This is a preliminary root cause pending full analysis. Update when Section 4 is complete.*

### 1.3 Incident Type Classification

| Classification | Value |
|---------------|-------|
| **Type** | [Availability / Performance / Security / Data / AI-Behavior / Process] |
| **Category** | [Infrastructure / Application / Dependency / Human-process / Configuration] |
| **First occurrence** | [YES — novel failure / NO — recurring — see [previous incident ID]] |

---

## 2. Impact Assessment
[TYPE: REFERENCE]

<!--
Quantify impact as precisely as possible.
Estimates are acceptable — clearly mark them as estimates.
Impact data drives prioritization of corrective actions.
-->

### 2.1 User Impact

| Metric | Value | Confidence |
|--------|-------|-----------|
| Users affected (total) | [N] | [Exact / Estimated ±N%] |
| Users experiencing full outage | [N] | [Exact / Estimated] |
| Users experiencing degraded service | [N] | [Exact / Estimated] |
| Geographic regions affected | [List] | |
| Customer segments affected | [e.g., "Enterprise customers on EU cluster"] | |

### 2.2 Service Impact

| Metric | Value | Normal Baseline |
|--------|-------|----------------|
| Error rate at peak | [e.g., 94%] | [e.g., <0.1%] |
| Availability during incident | [e.g., 6%] | [e.g., 99.9%] |
| Requests failed | [N] | N/A |
| Latency at peak | [e.g., p99 = 45s] | [e.g., p99 = 200ms] |
| [Service-specific metric] | | |

### 2.3 Business Impact

| Metric | Value | Basis |
|--------|-------|-------|
| Revenue impact (estimate) | [$X] | [Calculation method — e.g., "$255/min × 47 min"] |
| Transactions lost / failed | [N] | [Source — logs, payment processor] |
| SLA breach | [YES / NO] | [SLA terms reference] |
| SLA breach duration | [N minutes over threshold] | |
| Support tickets generated | [N] | |
| Escalations to leadership | [YES — [level] / NO] | |

### 2.4 Reputational Impact

[Describe any external visibility: social media, news coverage, customer communications sent]

- Customer communication sent: [YES — [channel, sent at HH:MM] / NO]
- Status page updated: [YES — [at HH:MM] / NO]
- External press coverage: [YES — [summary] / NO]

---

## 3. Timeline
[TYPE: REFERENCE]

<!--
Complete within 24 hours of resolution while events are fresh.
Use exact timestamps from logs wherever possible.
Mark estimated times clearly: "(estimated)".
Name individuals only for factual accuracy — not to assign blame.
Each entry is one atomic event. Be specific — "investigated the problem"
is not an entry; "discovered CPU spike on db-prod-02 via Datadog" is.
Blameless principle: "Engineer A ran the migration script" not
"Engineer A caused the outage by running the migration script".
-->

All times in UTC.

| Time (UTC) | Event | Source | Notes |
|-----------|-------|--------|-------|
| [HH:MM] | [Atomic event — specific, observable] | [Log / Alert / Person] | [Any relevant context] |
| [HH:MM] | [Incident detected by: alert / customer report / monitoring] | | |
| [HH:MM] | [First responder paged / notified] | | |
| [HH:MM] | [War room / incident channel created] | | |
| [HH:MM] | [Hypothesis formed: "[specific hypothesis]"] | | |
| [HH:MM] | [Hypothesis tested: [pass/fail]] | | |
| [HH:MM] | [Root cause identified] | | |
| [HH:MM] | [Mitigation action taken: "[specific action]"] | | |
| [HH:MM] | [Service restored for [X% of users]] | | |
| [HH:MM] | [Full service restored — incident resolved] | | |
| [HH:MM] | [Post-incident monitoring confirmed stable] | | |

### 3.1 Timeline Analysis

**Detection lag:** [N minutes from incident start to detection] — [within / outside target]
**Response lag:** [N minutes from detection to first action] — [within / outside target]
**Recovery time:** [N minutes from first action to resolution] — [within / outside target]

**Critical path identified:** [What sequence of events, if changed, would have most reduced total duration?]

---

## 4. Root Cause Analysis
[TYPE: REFERENCE]

<!--
BLAMELESS RULE: "Human error" is never a root cause.
If a human action contributed, ask WHY the system allowed or
encouraged that action, and make THAT the root cause.

Five-Whys method: start with the symptom, ask "why" 5 times.
Each answer should be more fundamental than the last.
Stop when you reach a systemic factor you can actually fix.

STRUCTURE: One primary root cause + N contributing factors.
All root causes must be systemic, not individual.
-->

### 4.1 Primary Root Cause

**Root cause:** [One clear sentence. Systemic, specific, actionable.]

[3–5 sentences expanding on the root cause: what condition existed, how it led
to the incident, and why it was not prevented by existing safeguards.]

**Why this is a root cause and not a symptom:**
[Explain: if you fixed this single thing, would the incident have been prevented?
If yes, this is a root cause. If no, this is a symptom — keep asking why.]

### 4.2 Five-Whys Analysis

| Level | Why? | Answer |
|-------|------|--------|
| Symptom | Why did users experience [observable symptom]? | [Answer] |
| Why 1 | Why did [answer to symptom]? | [Answer] |
| Why 2 | Why did [answer to Why 1]? | [Answer] |
| Why 3 | Why did [answer to Why 2]? | [Answer] |
| Why 4 | Why did [answer to Why 3]? | [Answer] |
| **Root Cause** | Why did [answer to Why 4]? | **[This is the root cause]** |

### 4.3 Contributing Factors

Factors that did not cause the incident independently but made it worse or more likely:

| Factor | Category | How It Contributed | Corrective Action |
|--------|----------|-------------------|------------------|
| [Factor 1] | [Monitoring / Process / Tooling / Training / Design] | [How it made things worse] | [See Section 7, action #] |
| [Factor 2] | | | |
| [Factor 3] | | | |

### 4.4 Why Existing Safeguards Failed

[For each existing safeguard (alert, test, review, circuit breaker) that should have
caught this earlier, explain why it did not:]

| Safeguard | Expected to Catch | Why It Did Not | Gap Correction |
|-----------|-----------------|----------------|----------------|
| [Alert name] | [What it should have detected] | [Why it missed this] | [See corrective action #] |
| [Test type] | [Scenario it should cover] | [Why it was insufficient] | [Action #] |

---

## 5. Response Assessment
[TYPE: REFERENCE]

<!--
Blameless assessment of the response itself.
What went well? What slowed us down?
This informs improvements to runbooks, alerting, and on-call.
-->

### 5.1 What Went Well

These aspects of the response were effective and should be reinforced:

- [What went well 1 — specific, observable — e.g., "War room stood up within 3 minutes of alert"]
- [What went well 2 — e.g., "Customer communication sent within 10 minutes of P0 declaration"]
- [What went well 3 — e.g., "Runbook was up-to-date and guided responders to the right diagnostics"]

### 5.2 What Slowed the Response

These aspects delayed resolution or made recovery harder:

| Bottleneck | Impact | Root Cause of Bottleneck | Corrective Action |
|-----------|--------|--------------------------|------------------|
| [Bottleneck 1 — e.g., "On-call engineer lacked access to prod logs"] | [N min delay] | [Access provisioning process] | [Action #] |
| [Bottleneck 2] | [Impact] | [Cause] | [Action #] |

### 5.3 Decision Points

[Key decisions made during the incident and whether they were correct in hindsight:]

| Decision | Made By | Made At | Assessment | If Different: |
|----------|---------|---------|------------|--------------|
| [e.g., "Rolled back instead of hotfix"] | [Role] | [HH:MM] | [Correct / Would change] | [What would have been better] |
| [e.g., "Did not escalate to C-suite at HH:MM"] | [Role] | [HH:MM] | [Correct / Would change] | |

---

## 6. Lessons Learned
[TYPE: EXPLANATION]

<!--
Systemic learnings — not individual lessons.
"Our engineers need to be more careful" is not a lesson.
"Our deployment process lacks a mandatory review step" is a lesson.
-->

### 6.1 Technical Lessons

The [incident title] revealed the following technical gaps that extend beyond this specific incident:

- **[Lesson 1]:** [What systemic technical gap was revealed and what it implies for future risk]
- **[Lesson 2]:** [Lesson]
- **[Lesson 3]:** [Lesson]

### 6.2 Process Lessons

The incident response and surrounding processes revealed:

- **[Process lesson 1]:** [e.g., "Our deployment approval process did not require a rollback plan"]
- **[Process lesson 2]:** [Lesson]

### 6.3 Monitoring & Observability Lessons

Gaps in monitoring/observability that this incident revealed:

- **[Monitoring lesson 1]:** [e.g., "We had no alert for sustained elevated database connection count"]
- **[Monitoring lesson 2]:** [Lesson]

### 6.4 What Would Have Prevented This Incident

[List the specific changes that, if already in place, would have prevented this incident from reaching users or significantly reduced its impact:]

1. [Prevention 1 — the single most impactful change]
2. [Prevention 2]
3. [Prevention 3]

---

## 7. Corrective Actions
[TYPE: PROCEDURE]

<!--
Each corrective action is atomic: one action, one owner, one due date.
Actions should address root causes and contributing factors — not symptoms.
Include both short-term mitigations (stop the bleeding) and long-term
fixes (prevent recurrence).
Status updates happen here — update this table as actions are completed.
-->

### 7.1 Corrective Action Log

| # | Action | Owner | Due Date | Priority | Status | Addresses |
|---|--------|-------|---------|----------|--------|-----------|
| CA-01 | [Specific action — action verb + deliverable] | [Name / Team] | [YYYY-MM-DD] | P0 / P1 / P2 | OPEN | [Root cause / Contributing factor #] |
| CA-02 | [Action] | | | | OPEN | |
| CA-03 | [Action] | | | | OPEN | |

**Total actions:** [N]
**Open:** [N] | **In Progress:** [N] | **Complete:** [N]
**Overdue:** [N]

### 7.2 Action Detail

For complex actions that require more context:

**CA-01 — [Action title]**

**Context:** [Why this action is needed — links to root cause or lesson]
**Deliverable:** [Exact output expected — document, code change, alert rule, runbook, etc.]
**Success criteria:** [How we know this action is complete and effective]
**Risk if not done:** [What happens if this action is skipped or delayed]
**Dependencies:** [Other actions or decisions this depends on]

---

**CA-02 — [Action title]**

[Detail for next action]

---

### 7.3 Rejected or Deferred Actions

Actions considered but deliberately not included — and why:

| Action Considered | Decision | Reason |
|------------------|----------|--------|
| [Action that was discussed] | REJECTED | [Why not worth doing] |
| [Action] | DEFERRED to [date] | [What needs to happen first] |

---

## 8. 30-Day Follow-Up Review
[TYPE: REFERENCE]

**Scheduled review date:** [YYYY-MM-DD — set before this document is published]

*Complete this section at the 30-day review.*

### 8.1 Corrective Action Status at 30 Days

| Action | Status | If Incomplete: Reason | New Target Date |
|--------|--------|----------------------|----------------|
| CA-01 | COMPLETE / PARTIAL / OPEN | | |
| CA-02 | | | |

### 8.2 Has the Incident Recurred?

[YES — [description and link to new incident] / NO]

### 8.3 Are Root Causes Fully Addressed?

[YES — all root causes and contributing factors have been addressed by completed corrective actions]
[PARTIAL — [which factors remain open and what risk they carry]]
[NO — [which root causes are unaddressed and why]]

### 8.4 Follow-Up Actions (if any)

| Action | Owner | Due Date |
|--------|-------|---------|
| [Any new actions identified at 30-day review] | | |

---

## 9. Cross-References
[TYPE: REFERENCE]

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DEPENDS_ON] | [TMPL-002 Technical Reference for affected system] | All | System architecture context |
| [SEE_ALSO] | [TMPL-003 Incident Response Runbook] | All | Runbook used during this incident — flag gaps |
| [SEE_ALSO] | [TMPL-007 Model Card if AI system involved] | Section 6 | AI system limitations documented |
| [SEE_ALSO] | [Previous incident post-mortem if recurring] | All | Pattern analysis for recurring failures |
| [IMPLEMENTS] | [Corrective actions from this post-mortem] | Section 7 | Actions that modify existing procedures |

---

## 10. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | [Date] | Initial | Post-mortem created — draft | Within 24 hours of incident resolution |
| 1.1 | [Date] | Update | Root cause analysis completed | 72-hour deadline |
| 1.2 | [Date] | Update | Corrective actions finalized | 1-week deadline |
| 2.0 | [Date] | Major | 30-day review completed | Follow-up review date |

---

*Template: TMPL-008 Incident Post-Mortem v1.0 | Parent: TMPL-000 Template Index*
*Blameless post-mortem — no individuals are held responsible for systemic failures*
