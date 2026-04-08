# [Procedure Name]
## [Subtitle — e.g., "Production Deployment Runbook" or "Test Execution Guide"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-003: PROCEDURE & WORKFLOW / RUNBOOK
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: Deployment guides, testing procedures, operational
runbooks, onboarding workflows, user guides, release processes,
incident response procedures, maintenance procedures,
data migration steps, CI/CD pipeline documentation.

AUTHORING WORKFLOW:
  1. NO research skill trigger — procedures are authoritative
     by definition (they capture how YOUR team does something)
  2. Verification skill runs for any external claims
     (e.g., "this tool requires X" — verify against tool docs)
  3. Have a second person execute the procedure dry-run to
     validate accuracy before finalizing

KEY PRINCIPLE:
  Every step must be executable. A reader (human or AI agent)
  should be able to complete the procedure with zero ambiguity.
  If a step requires judgment, that judgment must be documented.
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ──────────────────────────────
document_id: "[XXX Short-Title]"
title: ""
version: "1.0"
created: "YYYY-MM-DD"
status: "Draft | Review | Final"
parent_document: ""

# ── PROCEDURE METADATA ───────────────────────────────────────
procedure_type: "deployment | testing | operations | onboarding | user-guide | maintenance | incident-response | migration | other"
estimated_duration: "[N minutes | N hours]"
frequency: "one-time | per-release | weekly | on-demand | incident-triggered"
last_executed: "YYYY-MM-DD"
last_executed_by: ""
execution_validated: false   # true once dry-run validated

# ── AI-OPTIMIZATION EXTENSION ───────────────────────────────
intent: >
  Enable [who] to [complete what task] by providing step-by-step
  instructions for [procedure name] in [environment/context].

consumption_context:
  - human-reading
  - agentic-execution    # Structured for AI-assisted or AI-driven execution

triggers:
  - "how to [task]"
  - "[procedure name] steps"
  - "deploy [system]"
  - "[action] runbook"
  - "[system] [procedure type]"

negative_triggers:
  - "why do we [task]"    # → TMPL-005 Decision Record
  - "[system] architecture"  # → TMPL-002 Technical Reference

volatility: "stable"
  # stable: changes when system changes or process is updated
  # Update version whenever steps change

research_validated: false
review_trigger: "When underlying system version changes OR after each execution if issues found"

template_version_used: "TMPL-003 v1.1"  # Record which template version was active when this doc was written

confidence_overall: "high"
confidence_note: "Procedure is authoritative — reflects how this team executes this task"
---
```

---

> ## 🤖 AI Summary
> **Procedure:** [Name — what this procedure accomplishes]
> **Who Executes This:** [Role — developer / operator / QA / end user]
> **Duration:** [Estimated time]
> **Prerequisites:** [Top 3 things needed before starting]
> **Outcome:** [What state the system is in when procedure completes successfully]
> **Trust Level:** HIGH — authoritative team procedure, last executed [date]
> **Do NOT Use This For:** Understanding why the procedure exists (see Decision Records)

---

## TABLE OF CONTENTS

1. [Prerequisites & Access Requirements](#1-prerequisites--access-requirements)
2. [Pre-Execution Checklist](#2-pre-execution-checklist)
3. [Procedure Steps](#3-procedure-steps)
4. [Verification & Validation](#4-verification--validation)
5. [Rollback Procedure](#5-rollback-procedure)
6. [Troubleshooting Guide](#6-troubleshooting-guide)
7. [Post-Execution Checklist](#7-post-execution-checklist)
8. [Execution Log](#8-execution-log)
9. [Cross-References](#9-cross-references)
10. [Revision History](#10-revision-history)

<!--
SECTION SELECTION GUIDE:
Sections 1, 3, 4, 9, 10 are ALWAYS required.
Section 5 (Rollback) required for: deployments, migrations, data changes.
Section 6 (Troubleshooting) required for: operations, incident response.
Section 8 (Execution Log) required for: audited procedures (deployments, releases).
Remove unused sections — do not leave them empty.
-->

---

## 1. Prerequisites & Access Requirements
[TYPE: REFERENCE]

<!--
CHUNKING NOTE: This section is retrieved first.
An agent or person needs to know if they CAN execute
this procedure before reading the steps.
Be complete — missing prerequisites cause failures mid-procedure.
-->

### 1.1 Who Can Execute This Procedure

| Attribute | Requirement |
|-----------|-------------|
| **Required Role** | [Job role or team — e.g., "Backend Developer" / "DevOps Engineer"] |
| **Required Permissions** | [System permissions needed — e.g., "AWS IAM: DeployRole"] |
| **Approval Required** | YES / NO — [If yes: who approves and how] |
| **Minimum Experience** | [e.g., "Familiar with Kubernetes" / "No prerequisites"] |

### 1.2 Required Tools & Access

| Tool / Access | Version / Type | How to Obtain | Verify With |
|--------------|---------------|---------------|-------------|
| `[tool-name]` | `[version]` | [Link or instruction] | `[verification command]` |
| [AWS Console access] | [Role: XYZ] | [Request via IT ticket] | [Test URL] |
| [VPN] | | [IT helpdesk] | [Ping test] |

### 1.3 Required Environment Variables / Credentials

| Variable | Source | How to Obtain |
|----------|--------|--------------|
| `[ENV_VAR]` | [1Password / AWS Secrets / CI env] | [Instruction] |

### 1.4 Sensitive Content Rules for This Document
[TYPE: REFERENCE]

This procedure document describes what actions to take and what tools to use.
It must never document the actual sensitive values used in those actions.
These rules apply to all procedure documents in this system.
✅ `[VERIFIED — TMPL-000 conventions, Section 10, v1.1]`

**Never document the following in this procedure's body text:**
- Actual credentials, passwords, API keys, tokens, or passphrases —
  even in command examples meant to be illustrative
- Real user IDs, email addresses, or account names in example output
- Production server addresses, internal IP ranges, or private hostnames
- Production-environment configuration values (database names, bucket names,
  queue URLs) — use placeholder syntax in all examples

**Approved placeholder syntax for commands and examples:**

```bash
# Always use placeholders in example commands:
export DB_PASSWORD=<YOUR_DB_PASSWORD>
aws s3 cp ./build s3://<YOUR_BUCKET_NAME>/releases/
kubectl apply -f manifests/ --namespace=<TARGET_NAMESPACE>
curl -H "Authorization: Bearer <YOUR_API_TOKEN>" https://<API_HOST>/api/v1/status
```

**Reference sources, not values:** The credentials table in Section 1.3 must
reference WHERE to obtain credentials, not the credential values themselves.
If this procedure is executed by an AI agent, the agent retrieves credentials
from the declared source at runtime — never from this document.

### 1.5 System State Prerequisites

The following must be true BEFORE starting this procedure:

- [ ] [Prerequisite 1 — e.g., "All tests passing in CI"]
- [ ] [Prerequisite 2 — e.g., "Staging deployment successful"]
- [ ] [Prerequisite 3 — e.g., "Change ticket approved: [ticket system]"]
- [ ] [Prerequisite 4 — e.g., "Team lead notified in [Slack channel]"]

---

## 2. Pre-Execution Checklist
[TYPE: PROCEDURE]

<!--
WHY THIS SECTION EXISTS:
A fast verification that everything is ready BEFORE starting.
For AI agents: this is the gate before step execution begins.
Complete sequentially. Do not skip items.
-->

**Execute this checklist immediately before starting Section 3.**

```
PRE-EXECUTION VERIFICATION

ENVIRONMENT
  [ ] Target environment: [name] is confirmed
  [ ] Previous procedure execution completed successfully
      (Last execution: [date] — see Section 8)
  [ ] No in-progress conflicting operations
      (Check: [monitoring URL or command])

DEPENDENCIES
  [ ] [Upstream service A] is healthy
      (Verify: [health check URL or command])
  [ ] [Upstream service B] is reachable
  [ ] Database connections available
      (Verify: [command or URL])

BACKUPS / SAFETY
  [ ] [Database backup taken if applicable]
  [ ] [Snapshot created if applicable]
  [ ] Rollback procedure reviewed (Section 5)

NOTIFICATIONS
  [ ] [#ops-channel] notified: starting [procedure name]
  [ ] [On-call engineer] aware (if applicable)
```

**If any item fails:** Do NOT proceed. Resolve the issue and re-verify.

---

## 3. Procedure Steps
[TYPE: PROCEDURE]

<!--
FORMATTING RULES FOR STEPS:
1. Number every step — no sub-bullets at top level
2. Each step = one action. If a step has multiple actions, split it.
3. Commands in code blocks — never inline
4. Expected output in code blocks — what SUCCESS looks like
5. Decision points explicitly branched
6. Flag steps that are IRREVERSIBLE with ⚠️ IRREVERSIBLE
7. Flag steps that are SLOW with ⏱️ [N minutes]

AI AGENT EXECUTION NOTE:
If an AI agent executes this procedure, each numbered step
is an atomic unit. Step completion must be verified before
proceeding to next step.
-->

### Phase 1: [Phase Name — e.g., "Preparation"]

**Step 1: [Action verb — what to do]**

[1-2 sentence description of what this step does and why it matters.
Only include "why" if omitting it would cause confusion or danger.]

```bash
# [Comment describing what this command does]
[command here]
```

**Expected output:**
```
[What success looks like — paste actual output or describe]
```

**If output differs:** [What to do — check X / see troubleshooting Section 6.N / stop and escalate]

---

**Step 2: [Action verb]**
⚠️ **IRREVERSIBLE** — [Brief description of what cannot be undone]

[Description]

```bash
[command]
```

**Expected output:**
```
[Success output]
```

---

**Step 3: [Decision Point]**

```
DECISION: Is [condition] true?
  YES → Proceed to Step 4
  NO  → Go to Step 7 (alternative path)
  UNSURE → See Section 6.1 (troubleshooting)
```

---

**Step 4: [Action verb]**
⏱️ **[N minutes]** — This step takes approximately N minutes.

[Description]

```bash
[command]
```

**Monitor progress:**
```bash
# Run this in a separate terminal to watch progress
[monitoring command]
```

**Step complete when:** [Specific condition that signals completion]

---

### Phase 2: [Next Phase Name]

**Step 5: [Action verb]**

[Description]

```bash
[command]
```

---

<!--
CONTINUE PATTERN:
- Group related steps into phases (Phase 1, 2, 3...)
- Each phase should have 3-8 steps
- More than 10 phases suggests this procedure should be split
- Add as many steps as needed — do not compress steps for brevity
  A missed step is always worse than a long procedure
-->

### Phase N: [Final Phase — e.g., "Cleanup & Notification"]

**Step N: [Notify stakeholders / Clean up temp files / etc.]**

[Description]

```bash
[command if applicable]
```

**Procedure complete when:** [State the exact condition that indicates successful completion]

---

## 4. Verification & Validation
[TYPE: PROCEDURE]

<!--
WHY THIS SECTION EXISTS:
How do you KNOW the procedure worked?
These are the post-execution checks that confirm success.
Required before closing out the procedure.
-->

### 4.1 Success Criteria

The procedure is considered successful when ALL of the following are confirmed:

| Check | How to Verify | Expected Result |
|-------|--------------|----------------|
| [Check 1] | `[command or URL]` | [Expected output] |
| [Check 2] | [Navigation instruction] | [What to see] |
| [Check 3] | `[command]` | [Expected output] |

### 4.2 Smoke Tests

Run these quick tests to confirm core functionality:

```bash
# Test 1: [What this tests]
[test command or curl]

# Expected: [What to see]
```

```bash
# Test 2: [What this tests]
[test command]
```

### 4.3 Monitoring Confirmation

Verify no errors in monitoring systems:

| System | What to Check | Normal State |
|--------|--------------|-------------|
| [Monitoring tool] | [What to look at] | [What healthy looks like] |
| [Log system] | `[query or filter]` | [Expected] |

---

## 5. Rollback Procedure
[TYPE: PROCEDURE]

<!--
INCLUDE IF: Procedure makes changes that can fail after the point of no return.
SKIP IF: Procedure is read-only or completely reversible by re-running.

ROLLBACK PRINCIPLE:
- Rollback should bring the system to the state it was in BEFORE
  this procedure started
- Rollback steps must be as precise as the forward steps
- Rollback should be practiced separately if it's a critical procedure
-->

### 5.1 When to Rollback

Initiate rollback if:
- [Condition 1 — e.g., "Error rate exceeds 5% for more than 2 minutes post-deployment"]
- [Condition 2 — e.g., "Critical service health check fails after Step N"]
- [Condition 3]

Do NOT rollback if:
- [Condition where rollback would make things worse]
- [When to escalate instead]

### 5.2 Rollback Steps

**Before rolling back:**
- [ ] Notify [#ops-channel] of rollback intent
- [ ] Capture current state (logs, error messages)
- [ ] Confirm rollback decision with [role]

**Rollback Step 1: [Action]**

```bash
[rollback command]
```

**Rollback Step 2: [Action]**

```bash
[rollback command]
```

### 5.3 Post-Rollback Verification

After rollback, verify:
- [ ] System is in pre-procedure state: [how to verify]
- [ ] No data corruption: [how to verify]
- [ ] Stakeholders notified: [who and how]

**After rollback:** Document what happened in Section 8 and open [incident / ticket] in [system].

---

## 6. Troubleshooting Guide
[TYPE: REFERENCE]

<!--
WHY THIS SECTION EXISTS:
Captures known failure modes so operators don't have to figure them
out from scratch. Grows over time as new failure modes are discovered.
Add to this section every time a new issue is found during execution.
-->

### 6.1 [Common Failure 1 — Descriptive Name]

**Symptom:** [What the operator/agent observes]

**Diagnosis steps:**
```bash
[diagnostic command]
```

**Resolution:**
```bash
[fix command]
```

**If resolution fails:** [Escalation path — who to contact / what ticket to open]

---

### 6.2 [Common Failure 2]

**Symptom:** [Observable symptom]

**Cause:** [Root cause explanation]

**Resolution:**
[Steps or commands]

---

### 6.3 Escalation Matrix

| Situation | Escalate To | Contact Method | Expected Response Time |
|-----------|------------|----------------|----------------------|
| [Situation] | [Team/Person] | [Slack/PagerDuty/Email] | [SLA] |
| [Database issue] | [DBA Team] | [Contact method] | [Time] |
| [Security incident] | [Security Team] | [Method] | [Time] |

---

## 7. Post-Execution Checklist
[TYPE: PROCEDURE]

<!--
WHY THIS SECTION EXISTS:
The procedure isn't done until cleanup and notifications are complete.
For audited procedures, this section's completion is the official
end of the procedure.
-->

```
POST-EXECUTION VERIFICATION

VALIDATION COMPLETE
  [ ] All checks in Section 4 passed
  [ ] No alerts firing in monitoring
  [ ] Error rate within normal bounds

CLEANUP
  [ ] Temporary files removed
  [ ] Test data cleaned up (if applicable)
  [ ] Credentials / tokens revoked (if temporary ones used)

NOTIFICATIONS
  [ ] [#ops-channel] notified: [procedure name] complete
  [ ] Ticket/change record updated and closed
  [ ] [Stakeholders] notified of completion

DOCUMENTATION
  [ ] Execution log updated (Section 8)
  [ ] Any new failure modes added to Section 6
  [ ] Procedure improvements noted in revision history
```

---

## 8. Execution Log
[TYPE: REFERENCE]

<!--
WHY THIS SECTION EXISTS:
Audit trail for every execution of this procedure.
Required for: deployments, releases, data migrations, security procedures.
Optional but recommended for all other procedures.

This section is append-only — add new rows at top (most recent first).
-->

| Execution Date | Executed By | Version Used | Environment | Outcome | Notes |
|---------------|-------------|-------------|-------------|---------|-------|
| [YYYY-MM-DD] | [Name/Role] | [Doc v1.N] | [env name] | ✅ Success / ❌ Failed / ⚠️ Partial | [Key observations, issues found] |

---

## 9. Cross-References
[TYPE: REFERENCE]

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DEPENDS_ON] | [TMPL-002 Technical Reference] | [Section] | [This procedure operates on that system] |
| [APPLIES] | [TMPL-005 Decision Record] | [Section] | [Decision that defined this procedure] |
| [SEE_ALSO] | [Related procedure] | | [When to use that instead] |
| [VALIDATED_BY] | [External doc — e.g., tool documentation] | | [Step N command verified against] |

**Related Procedures:**

| Procedure | When to Use Instead |
|-----------|---------------------|
| [Proc name] | [Condition] |
| [Rollback-only procedure if exists] | [Condition] |

---

## 10. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | [Date] | Initial | Procedure documented | [Why this was documented now] |

---

**IMPORTANT MAINTENANCE NOTE:**
This document must be updated whenever:
- The underlying system changes (version update, config change, new tool)
- A new failure mode is discovered during execution
- The procedure is changed for any reason

Update `last_executed` and `execution_validated` in frontmatter after each execution.

---

*Template: TMPL-003 Procedure & Workflow v1.0 | Parent: TMPL-000 Template Index*
