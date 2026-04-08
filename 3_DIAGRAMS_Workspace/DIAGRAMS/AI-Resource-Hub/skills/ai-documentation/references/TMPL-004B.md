# [Workflow Name]: Agentic Workflow Plan
## [Subtitle — e.g., "Execution Blueprint: Automated Test Suite Generation for Order Service"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-004B: AGENTIC WORKFLOW PLAN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: A multi-step execution blueprint the agent follows
during task execution. Defines phases, gates, inputs, outputs,
and stop conditions. The agent is READ-ONLY.

THIS IS NOT FOR:
  - Agent identity and domain knowledge → use TMPL-004A (context brief)
  - Cross-session state persistence → use TMPL-004C (living context)
  - Human-readable process documentation → use TMPL-003

RELATIONSHIP TO TMPL-004A:
  A workflow plan is often paired with a context brief.
  The context brief tells the agent WHO it is.
  This workflow plan tells the agent WHAT to do.
  Load the context brief (004A) first, then this document.

AUTHORING PRINCIPLES:
  - Every phase has exactly: INPUT, STEPS, OUTPUT, GATE.
  - Gates are binary — pass or escalate. No partial gates.
  - Steps use action verbs: Fetch, Validate, Generate, Submit.
  - Never use "check if" in a step — always specify what to
    do when the check passes AND when it fails.
  - The workflow must be executable by a cold-start agent.

AGENT VALIDATION CHECKLIST (run after authoring):
  [ ] Can a cold-start agent execute Phase 1 with only this
      document and the inputs listed in Section 2?
  [ ] Does every phase gate specify what happens on FAIL?
  [ ] Are all tool names concrete (not "use a search tool")?
  [ ] Is the output specification in Section 6 schema-complete?
  [ ] Would this plan still work if interrupted mid-phase and
      resumed? (Check for idempotent steps.)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ──────────────────────────────
document_id: "[XXX Short-Title]-workflow-plan"
title: "[Workflow Name] — Agentic Workflow Plan"
version: "1.0"
created: "YYYY-MM-DD"
status: "Final | Review"
parent_document: ""
template_version_used: "TMPL-004B v1.1"

# ── WORKFLOW PLAN METADATA ────────────────────────────────────
tmpl_variant: "workflow-plan"
workflow_name: ""            # Short identifier for this workflow
paired_context_brief: ""     # Document ID of the TMPL-004A brief, if any
agent_type: "general | specialized | orchestrator | subagent"
task_domain: ""
agent_write_access: false    # ALWAYS false for workflow plans

# ── WORKFLOW METRICS ─────────────────────────────────────────
estimated_phases: "[N]"
estimated_total_duration: "[N minutes / hours]"
human_approval_gates: "[N — how many times the agent must pause for human input]"
resumable: "yes | no"        # Can this workflow resume if interrupted mid-phase?

# ── AI-OPTIMIZATION EXTENSION ────────────────────────────────
intent: >
  Define the step-by-step execution blueprint for [workflow name],
  enabling [agent name/type] to [achieve end state] through [N]
  sequential phases with explicit gates and stop conditions.

consumption_context:
  - ai-reasoning
  - agentic-execution

triggers:
  - "[task phrase that should invoke this workflow]"
  - "[workflow name or alias]"
  - "[domain + action that starts this workflow]"

negative_triggers:
  - "[task that should use a different workflow]"
  - "[question about this system — use technical reference instead]"

volatility: "stable"
  # stable: workflow steps change only when the underlying system or process changes

research_validated: false
review_trigger: "When the underlying process or system changes; after any workflow failure post-mortem"

confidence_overall: "high"
confidence_note: "Author-defined execution blueprint — validated against known system capabilities"
---
```

---

> ## 🤖 AI Summary
> **Document Type:** AGENTIC WORKFLOW PLAN (TMPL-004B) — read-only execution blueprint
> **Workflow:** [Name]
> **End State:** [What completing this workflow produces in one sentence]
> **Phases:** [N] phases — [Phase 1 name] → [Phase 2 name] → ... → [Final phase name]
> **Human Gates:** [N] explicit human approval points
> **Stop Conditions:** [N] conditions that halt the workflow and require escalation
> **Resumable:** [YES / NO]
> **Paired Context Brief:** [TMPL-004A document ID, if any / None]

---

## TABLE OF CONTENTS

1. [Mission & Objective](#1-mission--objective)
2. [Pre-Flight Checklist](#2-pre-flight-checklist)
3. [Workflow Overview](#3-workflow-overview)
4. [Phase Detail](#4-phase-detail)
5. [Stop Conditions & Escalation](#5-stop-conditions--escalation)
6. [Output Specification](#6-output-specification)
7. [Resumability & Recovery](#7-resumability--recovery)
8. [Cross-References](#8-cross-references)
9. [Revision History](#9-revision-history)

---

## 1. Mission & Objective
[TYPE: REFERENCE]

<!--
CHUNKING NOTE: This section is loaded first. An agent reading
only this section must know: what the workflow produces, where
it starts, and the single most important constraint on execution.
Keep under 300 words.
-->

### 1.1 The Objective

**Complete this workflow when:** [Specific condition or trigger — e.g., "A new feature branch is ready for test generation"]

**The workflow is complete when:** [Measurable, observable end state — e.g., "A complete JUnit 5 test suite is present in the /tests directory, covering all endpoints in the feature branch, with all tests passing in CI"]

**The workflow must NEVER produce:** [Specific prohibited output — the most important safety constraint on this workflow's output]

### 1.2 Inputs Required Before Starting

The following inputs must be available before Phase 1 begins. If any are missing, escalate immediately — do not attempt to infer or substitute:

| Input | Source | Format | Required? |
|-------|--------|--------|-----------|
| [Input 1] | [Where it comes from] | [Format/type] | YES |
| [Input 2] | [Source] | [Format] | YES |
| [Input 3 — optional] | [Source] | [Format] | NO — [default if absent] |

### 1.3 Single Most Important Constraint on This Workflow

**NEVER:** [The one constraint that, if violated during workflow execution, would cause the greatest harm. This overrides task pressure to move faster or skip steps.]

---

## 2. Pre-Flight Checklist
[TYPE: PROCEDURE]

Complete this checklist before executing Phase 1. Do not begin if any item fails.

```
ENVIRONMENT
  [ ] All required inputs from Section 1.2 are present and accessible
  [ ] [System] is in the expected state: [how to verify]
  [ ] No conflicting operations in progress: [how to check]
  [ ] Paired context brief loaded (if applicable): [TMPL-004A document ID]

TOOL ACCESS
  [ ] Required tools are available and responding: [list tools from Section 4]
  [ ] File paths for output are writable: [specify paths]
  [ ] External resources accessible (if needed): [list URLs or systems]

CONSTRAINTS CONFIRMED
  [ ] Section 1.3 constraint reviewed and understood
  [ ] Human approval gates identified: Phases [N, N] require approval
  [ ] Resumability status noted: [YES — see Section 7 / NO — full restart required on interruption]
```

---

## 3. Workflow Overview
[TYPE: REFERENCE]

The [workflow name] workflow executes [N] sequential phases. Each phase has
a defined input, output, and gate. No phase begins until the previous phase's
gate has passed.

```
START → Pre-flight checklist

Phase 1: [Name]
  Input:  [What this phase receives]
  Output: [What this phase produces]
  Gate:   [Binary pass/fail criterion]
  ↓ (gate passes)
Phase 2: [Name]
  Input:  [Phase 1 output + any additional inputs]
  Output: [What this phase produces]
  Gate:   [Binary criterion]
  ↓
[...repeat for each phase...]
  ↓
Phase N: [Name — Final]
  Input:  [Previous phase output]
  Output: [Final deliverable — matches Section 6 spec]
  Gate:   [Success criteria from Section 6]
  ↓
END → Output delivered → Workflow complete
```

**Human approval gates at:** Phase [N], Phase [N] — workflow pauses and waits for explicit human confirmation before proceeding.

---

## 4. Phase Detail
[TYPE: PROCEDURE]

<!--
Every phase follows this exact structure. Do not skip any field.
Steps use action verbs. Every step states its tool.
Every gate specifies both PASS and FAIL outcomes.
-->

### Phase 1: [Name]

**Objective:** [What completing Phase 1 achieves — one sentence]

**Pre-conditions:**
- [Condition 1 that must be true before Phase 1 begins]
- [Condition 2]

**Steps:**

**Step 1.1 — [Action verb + object]**
- Input: [What is read or received]
- Tool: `[tool-name]`
- Action: [Precise description of what the agent does]
- Output: [What is produced]
- On failure: [What to do if this step fails — retry once? Stop? Escalate?]

**Step 1.2 — [Action verb + object]**
- Input: [Step 1.1 output]
- Tool: `[tool-name]`
- Action: [Precise description]
- Decision: If [condition] then [action A]; else [action B]
- Output: [What is produced]
- On failure: [Failure handling]

**Step 1.3 — [Action verb + object]**
- Input: [Previous output]
- Tool: `[tool-name]`
- Action: [Description]
- Output: [Phase 1 output artifact]

**Phase 1 Gate — DO NOT PROCEED TO PHASE 2 UNLESS ALL PASS:**
```
[ ] [Gate criterion 1 — e.g., "Output artifact is present at specified path"]
[ ] [Gate criterion 2 — e.g., "No validation errors in step 1.2 output"]
[ ] [Gate criterion 3 — e.g., "Output format matches Section 6 spec"]
```

**Gate FAIL behavior:** [Stop? Retry Phase 1? Escalate to human? Specify exactly.]

---

### Phase 2: [Name]

[Repeat Phase 1 pattern exactly]

**Objective:** [One sentence]

**Pre-conditions:**
- Phase 1 gate passed
- [Additional conditions if any]

**Steps:**

**Step 2.1 — [Action verb + object]**
- Input: [Phase 1 output]
- Tool: `[tool-name]`
- Action: [Description]
- Output: [What is produced]
- On failure: [Handling]

**Step 2.2 — [Action verb + object]**
- Input: [Previous output]
- Decision: If [X] then [Y]; if [Z] then [W]
- Tool: `[tool-name]`
- Output: [Artifact]
- On failure: [Handling]

**⏸ HUMAN APPROVAL GATE — Pause here if this phase includes human approval:**
> The workflow pauses. The agent produces: [summary of what was done and what awaits approval].
> The agent waits for explicit human confirmation before proceeding.
> If no response after [timeout]: [behavior — re-prompt? Escalate? Hold?]

**Phase 2 Gate:**
```
[ ] [Gate criterion 1]
[ ] [Gate criterion 2]
[ ] [Human approval received: YES / N/A]
```

**Gate FAIL behavior:** [Handling]

---

### Phase N: [Final Phase Name]

[Repeat pattern]

**Workflow Complete When ALL of the following are true:**
```
[ ] [Final output artifact delivered to correct location]
[ ] [All success criteria in Section 6 met]
[ ] [Human approval received if required by workflow]
[ ] [Execution log updated if Section 7 requires it]
```

---

## 5. Stop Conditions & Escalation
[TYPE: REFERENCE]

The agent stops all execution and escalates to a human when ANY of the
following conditions occur. These are non-negotiable — task pressure
does not override stop conditions.

| Trigger | Phase | Escalation Type | Required Report |
|---------|-------|----------------|----------------|
| [Gate fails after retry] | Any | Blocking | Phase number, gate criterion that failed, retry output |
| [Required input missing or corrupt] | Pre-flight | Blocking | Which input, what was expected vs received |
| [Confidence drops below 60% on a key decision] | Any | Blocking | Decision point, options identified, recommendation |
| [Scope creep — task expands beyond Section 1.2 inputs] | Any | Non-blocking | What additional scope was detected |
| [Conflicting instructions encountered] | Any | Blocking | The contradiction and its sources |
| [Section 1.3 constraint would be violated by proceeding] | Any | Blocking — HARD STOP | What action would violate the constraint |

**Escalation message format:**
```
WORKFLOW STOP
Phase: [Current phase and step]
Trigger: [Which stop condition was met]
Context: [What the agent had completed before stopping]
Blocker: [Specific question or issue requiring human input]
Options: [Option A / Option B / Option C — if applicable]
Recommendation: [Agent's suggestion with confidence level]
```

After escalating, the agent does not resume until the stop condition is explicitly resolved.

---

## 6. Output Specification
[TYPE: REFERENCE]

<!--
Define the exact schema of the workflow's final output.
An agent that doesn't know what "done" looks like cannot
verify its own work. Every field must be specified.
-->

### 6.1 Output Artifact

| Attribute | Value |
|-----------|-------|
| **Location** | [File path or destination] |
| **Format** | [File type, schema, structure] |
| **Naming Convention** | [How the output is named] |
| **Size / Scale** | [Expected size range — e.g., "5–20 test cases", "100–500 lines"] |

### 6.2 Output Schema

[If structured output, define the schema:]

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `[field]` | `[type]` | YES / NO | [What this contains] |
| `[field]` | `[type]` | YES / NO | [Description] |

### 6.3 Quality Criteria

The output passes quality review when ALL of the following are true:

- [ ] [Quality criterion 1 — e.g., "Test coverage includes all endpoints in scope"]
- [ ] [Quality criterion 2 — e.g., "No placeholder or TODO content in final output"]
- [ ] [Quality criterion 3 — e.g., "Output compiles / runs without errors"]
- [ ] [Quality criterion 4 — e.g., "Output matches schema in Section 6.2"]

---

## 7. Resumability & Recovery
[TYPE: REFERENCE]

### 7.1 Resumability Status

**This workflow is:** [RESUMABLE / NOT RESUMABLE]

[If RESUMABLE:]
The workflow can be resumed after interruption at a phase boundary.
To resume, the agent must:
1. Identify the last successfully completed phase gate (check execution log)
2. Verify all outputs from completed phases are still intact
3. Re-run the pre-conditions check for the next phase
4. Proceed from the next phase

[If NOT RESUMABLE:]
If interrupted, restart the entire workflow from Phase 1. Do not attempt
to resume from a mid-workflow state — the output may be in an inconsistent state.

### 7.2 Execution Log (Append-Only)

If this workflow is resumable, the agent records progress here after each phase gate passes:

| Timestamp | Phase Completed | Gate Result | Output Location | Notes |
|-----------|----------------|-------------|----------------|-------|
| [YYYY-MM-DD HH:MM] | Phase 1 | PASS | [path] | [Any notable observations] |

---

## 8. Cross-References
[TYPE: REFERENCE]

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DEPENDS_ON] | [TMPL-004A context brief if paired] | All | Agent identity and domain constraints |
| [DEPENDS_ON] | [TMPL-002 Technical Reference] | All | System specifications used in workflow |
| [IMPLEMENTS] | [TMPL-005 Decision Record if applicable] | All | Decision that authorised this workflow |
| [APPLIES] | [TMPL-003 Procedure if this automates one] | All | Procedure this workflow executes |
| [SEE_ALSO] | [ARCH-001-TMPL004-Reconciliation] | All | When to use this vs. ARCH-001 .ctx.md |

---

## 9. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | [Date] | Initial | Workflow plan created | [What triggered creation of this workflow] |

---

*Template: TMPL-004B Agentic Workflow Plan v1.1 | Parent: TMPL-000 Template Index*
*Agent write access: NONE — this document is read-only (except Section 7.2 execution log)*
