# Skills Updater Integration Specification
## Formal Integration Protocol: ai-documentation ↔ skills-updater

```yaml
---
document_id: "ARCH-002 Skills-Updater-Integration"
title: "Skills Updater Integration Specification — ai-documentation ↔ skills-updater"
version: "1.0"
created: "2026-04-04"
status: "Final"
parent_document: "PROJ-001 AI-Optimized Documentation Protocol"
template_version_used: "TMPL-002 v1.1"

intent: >
  Define the formal integration protocol between the ai-documentation skill
  and the skills-updater meta-skill, specifying the observation format,
  trigger conditions, data flow, and escalation rules that enable the
  documentation skill to feed the continuous improvement loop.

consumption_context:
  - human-reading
  - ai-reasoning
  - agentic-execution

triggers:
  - "skills updater integration for ai-documentation"
  - "how does the documentation skill feed skills-updater"
  - "ARCH-002"
  - "continuous improvement loop for documentation skill"
  - "observation format for skills-updater"

negative_triggers:
  - "how to run the quarterly refresh" # → OPS-001
  - "what is skills-updater" # → skills-updater SKILL.md

volatility: "stable"
review_trigger: "When skills-updater SKILL.md major version changes; when ai-documentation SKILL.md major version changes"

confidence_overall: "high"
confidence_note: "Architecture decision for this project — reflects actual integration design"
---
```

---

> ## 🤖 AI Summary
> **Core Purpose:** Defines exactly how the ai-documentation skill feeds observations to the skills-updater meta-skill, enabling the continuous improvement loop described in PROJ-001 Section 9.5.
> **Three Integration Points:** Post-execution observations → CTX-DOCSTD; Quarterly research findings → skills-updater; Health check → corpus review
> **Key Format:** Observation entries in CTX-DOCSTD Section 10 using the standardized OBSERVATION block format
> **Trust Level:** HIGH — architectural specification
> **Do NOT Use This For:** Running the quarterly refresh (use OPS-001); understanding what skills-updater does (see skills-updater SKILL.md)
> **Review By:** When either skill's major version changes

---

## TABLE OF CONTENTS

1. [Integration Overview](#1-integration-overview)
2. [Integration Point 1 — Post-Execution Observations](#2-integration-point-1--post-execution-observations)
3. [Integration Point 2 — Quarterly Research Findings](#3-integration-point-2--quarterly-research-findings)
4. [Integration Point 3 — Health Check Trigger](#4-integration-point-3--health-check-trigger)
5. [Observation Format Specification](#5-observation-format-specification)
6. [Skills-Updater Processing Rules](#6-skills-updater-processing-rules)
7. [Escalation & Human Approval](#7-escalation--human-approval)
8. [Integration Validation](#8-integration-validation)
9. [Cross-References](#9-cross-references)
10. [Revision History](#10-revision-history)

---

## 1. Integration Overview
[TYPE: EXPLANATION]

The `ai-documentation` skill integrates with the `skills-updater` meta-skill
through three distinct integration points. Together they implement the
continuous improvement loop described in PROJ-001 Section 9.5: the skill
produces observations from real usage; skills-updater processes them and
proposes improvements; a human approves changes; the skill is updated.

The integration is **pull-based, not push-based.** The ai-documentation
skill writes observations to a shared buffer (CTX-DOCSTD Section 10).
Skills-updater reads from that buffer on its next invocation. No
real-time messaging between skills occurs.

```
ai-documentation skill (producer)
  │
  ├── Post-execution → writes OBSERVATION blocks to CTX-DOCSTD §10
  │
  ├── Quarterly research → writes RESEARCH_FINDING blocks to CTX-DOCSTD §10
  │
  └── Checklist failures → writes CHECKLIST_GAP blocks to CTX-DOCSTD §10
                │
                ▼
          CTX-DOCSTD §10 (shared buffer — Pending Observations)
                │
                ▼
     skills-updater (consumer — reads on invocation)
       │
       ├── Classifies observations by type and priority
       ├── Groups related observations
       ├── Proposes specific skill/convention changes
       └── Presents proposals to human for approval
                │
                ▼
       Human approves / rejects proposals
                │
                ▼
       ai-documentation skill updated
       CTX-DOCSTD §10 cleared of processed observations
```

---

## 2. Integration Point 1 — Post-Execution Observations
[TYPE: REFERENCE]

### 2.1 When Observations Are Written

The ai-documentation skill writes an observation to CTX-DOCSTD Section 10
at the end of every documentation session — specifically in Phase 7 (Post-Execution
Observation Phase). An observation is written when ANY of the following occur:

| Trigger | Observation Type | Priority |
|---------|----------------|----------|
| User corrected a document numbering or naming assumption | `CONVENTION_DRIFT` | MEDIUM |
| A template section was confusing, insufficient, or consistently skipped | `TEMPLATE_GAP` | HIGH |
| Research returned findings that contradicted an existing project document | `CONTRADICTION` | HIGH |
| A Phase 6 checklist item failed repeatedly in this session | `CHECKLIST_GAP` | MEDIUM |
| User explicitly asked for a document type not covered by existing templates | `MISSING_TEMPLATE` | HIGH |
| The skill selected the wrong template and the user corrected it | `ROUTING_ERROR` | HIGH |
| A trigger phrase was not recognized that should have been | `TRIGGER_MISS` | MEDIUM |

### 2.2 Observation Write Location

Observations are appended to CTX-DOCSTD Section 10 (Pending Observations)
using the format defined in Section 5 of this document.

The ai-documentation skill **never deletes or modifies** previous observations —
it only appends. Only the skills-updater, after human approval, clears
processed observations from Section 10.

### 2.3 Observation Staleness

If Section 10 of CTX-DOCSTD contains more than **10 pending observations**,
the ai-documentation skill surfaces a warning at the start of the next session:

```
⚠️ SKILLS-UPDATER ATTENTION NEEDED
CTX-DOCSTD has [N] pending observations (threshold: 10).
Run: "invoke skills-updater to process pending observations"
Observations older than 30 days may reflect resolved issues.
```

---

## 3. Integration Point 2 — Quarterly Research Findings
[TYPE: REFERENCE]

### 3.1 When Research Findings Feed the Integration

After each quarterly research refresh (OPS-001), the executor writes
research findings that differ from current conventions to CTX-DOCSTD
Section 10 as `RESEARCH_FINDING` observations.

The findings follow the same observation format (Section 5) but with
`type: RESEARCH_FINDING` and a reference to the OPS-001 run.

### 3.2 Research Finding → Skills-Updater Flow

```
OPS-001 quarterly refresh identifies new research finding
    │
    ▼
Research finding recorded in CTX-DOCSTD §10 as RESEARCH_FINDING block
    │
    ▼
Skills-updater reads finding and classifies as:
  - Convention update needed (HIGH priority)
  - Monitoring flag only (LOW priority)
  - No action (finding confirms current convention)
    │
    ▼
Skills-updater proposes specific TMPL-000 convention change
Human approves → convention updated
    │
    ▼
CTX-DOCSTD §10 observation cleared, version incremented
```

---

## 4. Integration Point 3 — Health Check Trigger
[TYPE: REFERENCE]

### 4.1 Health Check Integration

The skills-updater health check (run via `run context health check` or
`check skill health`) reads CTX-DOCSTD and surfaces:

- Age of last agent session (`last_agent_session` field)
- Number of pending observations in Section 10
- Age of oldest unprocessed observation
- Whether any TMPL-000 conventions have exceeded their `review_trigger` date

### 4.2 Health Check Output

The health check produces a summary table:

```
AI-DOCUMENTATION SKILL HEALTH CHECK
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Last active session:          [YYYY-MM-DD] ([N days ago])
Pending observations:         [N] ([N] HIGH priority)
Oldest unprocessed obs:       [YYYY-MM-DD] ([N days old])
TMPL-000 last updated:        [YYYY-MM-DD]
Next quarterly refresh due:   [YYYY-MM-DD]
Conventions past review date: [N sections]

RECOMMENDED ACTIONS:
  [1] Process [N] high-priority observations
  [2] Run quarterly refresh (due [date])
  [3] Review [section name] — past review date
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

---

## 5. Observation Format Specification
[TYPE: REFERENCE]

### 5.1 Standard Observation Block

All observations written by the ai-documentation skill to CTX-DOCSTD
Section 10 must use this exact format:

```markdown
### OBS-[YYYY-MM-DD]-[NNN]

| Field | Value |
|-------|-------|
| **Type** | CONVENTION_DRIFT / TEMPLATE_GAP / CONTRADICTION / CHECKLIST_GAP / MISSING_TEMPLATE / ROUTING_ERROR / TRIGGER_MISS / RESEARCH_FINDING |
| **Priority** | HIGH / MEDIUM / LOW |
| **Date** | YYYY-MM-DD |
| **Session context** | [Brief description of what the ai-documentation skill was doing when observed] |
| **Observation** | [What was observed — specific, actionable, one paragraph max] |
| **Evidence** | [Specific evidence: user correction quoted, checklist item name, error message, etc.] |
| **Suggested action** | [What the observer (skill) suggests changing — optional but valuable] |
| **Affected files** | [Which file(s) would need to change: SKILL.md / TMPL-000 / TMPL-00X / etc.] |
| **Status** | PENDING |
```

### 5.2 Example Observation Entries

**Example — ROUTING_ERROR:**
```markdown
### OBS-2026-07-14-001

| Field | Value |
|-------|-------|
| **Type** | ROUTING_ERROR |
| **Priority** | HIGH |
| **Date** | 2026-07-14 |
| **Session context** | User requested documentation for a post-incident review |
| **Observation** | Skill selected TMPL-006 (Session Record) for a post-mortem request. User corrected to TMPL-008. The trigger phrase "incident review" is not included in TMPL-008's decision tree entry in SKILL.md. |
| **Evidence** | User input: "write up an incident review for last night's outage" — TMPL-008 was not offered |
| **Suggested action** | Add "incident review" as a trigger phrase to TMPL-008 in SKILL.md decision tree |
| **Affected files** | SKILL.md (decision tree section) |
| **Status** | PENDING |
```

**Example — TEMPLATE_GAP:**
```markdown
### OBS-2026-07-14-002

| Field | Value |
|-------|-------|
| **Type** | TEMPLATE_GAP |
| **Priority** | MEDIUM |
| **Date** | 2026-07-14 |
| **Session context** | Creating TMPL-007 AI Model Card for a recommendation system |
| **Observation** | TMPL-007 has no section for A/B testing or online evaluation methodology, which is standard for recommendation systems. The evaluation section (§5) only covers offline benchmarks. |
| **Evidence** | User manually added an A/B testing subsection that wasn't scaffolded by the template |
| **Suggested action** | Add optional Section 5.N "Online Evaluation & A/B Testing" to TMPL-007 |
| **Affected files** | TMPL-007.md |
| **Status** | PENDING |
```

---

## 6. Skills-Updater Processing Rules
[TYPE: REFERENCE]

When skills-updater processes the CTX-DOCSTD Section 10 observations, it
applies the following rules to classify, group, and propose changes.

### 6.1 Observation Classification

| Observation Count | Threshold | Skills-Updater Action |
|------------------|-----------|----------------------|
| 1 ROUTING_ERROR for same trigger | — | Propose adding trigger phrase to SKILL.md |
| 2+ ROUTING_ERROR for same trigger | — | Escalate: may indicate template selection logic flaw |
| 1 TEMPLATE_GAP for same section | — | Flag for consideration; do not propose change immediately |
| 2+ TEMPLATE_GAP for same section | — | Propose specific template addition |
| 1 MISSING_TEMPLATE for same document type | — | Flag; propose creating new template |
| 2+ MISSING_TEMPLATE for same type | — | Escalate: clear demand, recommend creating template immediately |
| Any CONTRADICTION observation | — | Escalate immediately; present both positions to human |
| Any CHECKLIST_GAP for same item | — | Propose adding validation or auto-fix to Phase 6 |

### 6.2 Grouping Related Observations

Before proposing changes, skills-updater groups observations that affect
the same file or the same part of the workflow. It proposes one change
per group, not one change per observation.

### 6.3 Change Proposal Format

Skills-updater presents each proposal in this format:

```
PROPOSED CHANGE — [Priority: HIGH / MEDIUM / LOW]
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Based on: OBS-[IDs] (N observations grouped)
Affects file: [file name]
Change type: [Addition / Modification / Deletion]

CURRENT (relevant excerpt):
[Current text from affected file]

PROPOSED CHANGE:
[New text or description of change]

RATIONALE:
[Why this change is warranted based on the observations]

RISK:
[Any potential downsides or breakage if change is applied]
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
APPROVE (apply change) / REJECT (discard) / MODIFY (edit proposal first)?
```

---

## 7. Escalation & Human Approval
[TYPE: REFERENCE]

### 7.1 What Requires Human Approval

All skills-updater change proposals require explicit human approval before
any file is modified. The human must respond with:
- **APPROVE** — apply the change as proposed
- **REJECT** — discard the proposal
- **MODIFY** — edit the proposed change, then approve

Skills-updater never modifies SKILL.md, any TMPL file, or TMPL-000
conventions without an explicit "APPROVE" or equivalent from the human.

### 7.2 What Gets Escalated (Beyond Standard Proposals)

The following conditions trigger immediate escalation to the human rather
than a standard proposal:

| Condition | Escalation Action |
|-----------|-----------------|
| CONTRADICTION observation detected | Immediately surface both positions; ask human to resolve before any other processing |
| 3+ ROUTING_ERROR observations for same trigger in one session | Flag possible systematic template selection flaw |
| Any observation suggesting a security rule gap | Escalate as HIGH; do not defer to batch processing |
| Observation indicates skills-updater itself needs updating | Note the irony; still escalate for human review |

### 7.3 Cleared Observations

After a proposal is **APPROVED** and the change applied:
1. Move the processed observation(s) from CTX-DOCSTD §10 to a `Resolved` sub-section
2. Record the date resolved and what change was made
3. Decrement `pending_observations_count` in CTX-DOCSTD frontmatter

After a proposal is **REJECTED**:
1. Add rejection note to the observation in CTX-DOCSTD §10
2. Change `Status: PENDING` to `Status: REJECTED — [reason]`
3. Leave in §10 for audit trail; do not delete

---

## 8. Integration Validation
[TYPE: REFERENCE]

### 8.1 Integration Health Checks

Run these checks to verify the integration is working correctly:

| Check | How to Verify | Expected Result |
|-------|-------------|-----------------|
| Observations are written post-session | After any ai-documentation session, inspect CTX-DOCSTD §10 | At least one new OBS block added |
| Observation format is correct | Check OBS blocks against Section 5.1 format | All required fields present, Status: PENDING |
| Skills-updater reads the buffer | Invoke skills-updater; check if it surfaces CTX-DOCSTD observations | Observations appear in skills-updater output |
| Approved changes are applied | After APPROVE, check the affected file | File updated; observation status changed |
| Cleared observations are archived | After processing, check CTX-DOCSTD §10 | Processed observations show Status: RESOLVED |

### 8.2 Known Integration Limitations

| Limitation | Impact | Planned Resolution |
|-----------|--------|-------------------|
| Integration is pull-based, not real-time | Skills-updater only processes observations when explicitly invoked | Acceptable — quarterly invocation is sufficient |
| No automated file modification | All changes require human approval and manual application | By design — human remains in the loop |
| CTX-DOCSTD §10 is a flat append-only buffer | No deduplication of similar observations | Skills-updater handles grouping during processing |
| Cross-session observation context is limited | Skill can't reference prior session observations in new session | Resolved by CTX-DOCSTD persistent buffer |

---

## 9. Cross-References
[TYPE: REFERENCE]

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [IMPLEMENTS] | PROJ-001 AI-Optimized Documentation Protocol | Section 9.5 | Implements the "living system" process from PROJ-001 |
| [DEPENDS_ON] | SKILL.md | Phase 7 Post-Execution Observation Phase | Defines what the skill observes and when |
| [DEPENDS_ON] | CTX-DOCSTD template | Section 10 | The shared buffer this integration uses |
| [DEPENDS_ON] | OPS-001 Quarterly Research Refresh | Step 9 | Research findings feed this integration |
| [SEE_ALSO] | TEST-001 Skill Test Suite | T3-01–T3-05 | Checklist gap observations are inputs to this integration |
| [IMPLEMENTS] | PROJ-002 Enhancement Roadmap | WP-4.2 | This document is the WP-4.2 deliverable |

---

## 10. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-04 | Initial | Integration specification created | Phase 4 WP-4.2 — formalising the skills-updater integration |

---

*Template: TMPL-002 Technical Reference v1.1 | Parent: PROJ-001 AI-Optimized Documentation Protocol*
