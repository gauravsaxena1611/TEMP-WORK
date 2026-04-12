# TMPL-003 Variant Guidance
## Supplementary Structure for Deployment Runbooks, Incident Response, and Database Migrations

**Source:** TMPL-000 Template Index v1.2 | Phase 3 WP-3.5
**Load when:** Using TMPL-003 for a deployment runbook, incident response playbook, or database migration procedure
**Version:** 1.0 | **Created:** 2026-04-04

---

## Overview

The base TMPL-003 Procedure & Workflow template covers generic step-by-step procedures.
When authoring specialized operational procedures, specific structural additions improve
safety, completeness, and agent executability. This file defines those additions — load
it alongside TMPL-003, not instead of it.

**How to use this file:**
1. Load TMPL-003 as the base template
2. Set `procedure_type` in frontmatter to the correct variant
3. Load the matching variant section below
4. Add variant-specific sections at the indicated positions in the base template

---

## Variant A — Deployment Runbook

**Use when:** `procedure_type: deployment`
Documenting a production or staging deployment for a software service, API, infrastructure component, or ML model.

### A.1 Additional Frontmatter Fields

```yaml
# ── DEPLOYMENT METADATA ───────────────────────────────────────
deployment_strategy: "rolling | blue-green | canary | recreate | feature-flag"
target_environment: "production | staging | dev | [custom]"
change_ticket: ""             # Change management ticket ID (if required)
approval_required: "yes | no" # Was formal change approval obtained?
approved_by: ""               # Name / Role if approval required
deployment_window: ""         # Allowed deployment times — e.g., "Tue–Thu 14:00–16:00 UTC"
rollback_rto_minutes: 0       # Target time to complete rollback if needed
requires_downtime: "yes | no | maintenance-mode"
downtime_duration_minutes: 0  # If yes: expected downtime
```

### A.2 Required Additional Section: Pre-Deployment Decision Gate

Insert as Section 1.5 (after existing prerequisites section):

```markdown
## 1.5 Pre-Deployment Decision Gate — GO / NO-GO
[TYPE: PROCEDURE]

This gate must be completed immediately before starting Section 3 (Step-by-Step).
If any item is NO-GO, stop and contact [role/escalation path] before proceeding.

```
PRE-DEPLOYMENT GO / NO-GO

ENVIRONMENT HEALTH
  [ ] GO: All upstream dependencies are healthy (run: [health check command])
  [ ] GO: No active incidents on dependent services (check: [status page URL])
  [ ] GO: Database is in expected state (run: [verify command / SQL])
  [ ] GO: No in-progress deployments in parallel (check: [deployment dashboard])

CHANGE MANAGEMENT
  [ ] GO: Change ticket [ID] is approved — approval confirmed by: [role]
  [ ] GO: Deployment is within the approved window: [window from frontmatter]
  [ ] GO: Rollback plan reviewed (Section 4) and rollback time [N min] is acceptable

ARTIFACT VERIFICATION
  [ ] GO: Artifact [name] version [version] matches the approved change ticket
  [ ] GO: Artifact checksum verified: [checksum value or "N/A"]
  [ ] GO: Staging deployment of this version was successful on [date]
  [ ] GO: Test results for this version are green (link: [CI/CD URL])

COMMUNICATIONS
  [ ] GO: [#ops-channel] notified with: deployment starting, expected duration, rollback contact
  [ ] GO: On-call engineer confirmed available during deployment window
  [ ] GO: [If customer-impacting]: Customer communication sent at [HH:MM]
```

**Decision:** GO ☐ | NO-GO ☐ | CONDITIONAL GO ☐ (explain: _______)
```

### A.3 Required Additional Section: Canary / Staged Rollout (if applicable)

Insert as Section 3.N when strategy is canary or rolling:

```markdown
## 3.N Canary / Staged Rollout Procedure
[TYPE: PROCEDURE]

### Stage 1: [N]% Traffic

**Route [N]% of traffic to new version. Monitor for [N minutes].**

Proceed to Stage 2 ONLY if all gates pass:
```
  [ ] Error rate on new version < [threshold] for [N] minutes
  [ ] Latency p99 on new version < [threshold] for [N] minutes
  [ ] No new error types appearing in logs
  [ ] Business metric [metric name] not degraded by >[N]%
```

**If gate fails:** Roll back Stage 1 (run: [rollback command]). Stop deployment. File incident.

### Stage 2: [N]% Traffic

[Repeat pattern — increase traffic percentage, re-run gates]

### Stage 3: 100% Traffic (Full Cutover)

**Promote new version to 100% traffic.**

Post-cutover monitoring: hold war room for [N minutes] monitoring:
- [Key metric 1 with threshold]
- [Key metric 2 with threshold]
```

### A.4 Required Additional Section: Post-Deployment Validation

Insert as Section 3 final step group:

```markdown
### Post-Deployment Validation

Run these checks immediately after deployment completes.
Deployment is not COMPLETE until ALL pass.

```
POST-DEPLOYMENT VERIFICATION

FUNCTIONAL
  [ ] Health check endpoint returns 200: [URL]
  [ ] [Critical user journey] works end-to-end: [test command or manual step]
  [ ] [Second critical journey] works: [test]

MONITORING
  [ ] Application metrics are flowing in [monitoring tool]
  [ ] Error rate is < [threshold] in the 5 minutes post-deploy
  [ ] No unexpected alerts have fired

NOTIFICATION
  [ ] [#ops-channel] notified: deployment complete, version [version], status [OK/DEGRADED]
  [ ] Change ticket [ID] updated with completion time
  [ ] On-call engineer stood down (or noted if staying on for extended monitoring)
```
```

### A.5 Required Rollback Specification (Expand Section 4)

The base TMPL-003 Section 4 (Rollback) must include for deployments:

```markdown
### 4.1 Rollback Decision Criteria — When to Roll Back

**Roll back immediately (no approval needed) if ANY of the following:**
- Error rate exceeds [X]% sustained for >[N] minutes
- [Critical business metric] drops below [threshold]
- Payment processing / [core function] is unavailable
- Data corruption is detected or suspected

**Consider rollback (escalate to [role] for decision) if:**
- Non-critical error rate elevated but below hard threshold
- Performance degraded but within SLA
- Unusual patterns observed but no customer impact confirmed

**Do NOT roll back for:**
- Expected temporary elevation during traffic warm-up (first [N] minutes)
- Non-production environment issues
- Cosmetic UI issues with no functional impact

### 4.2 Rollback Procedure

Estimated rollback time: [N minutes] | Target RTO: [frontmatter value]

Step 1: [Command or action to initiate rollback]
Step 2: [Verification step]
Step 3: [Confirm previous version is serving traffic]
Step 4: Notify [#ops-channel]: rollback complete, previous version restored
Step 5: Open incident [link to incident template] — all rollbacks require an incident record
```

---

## Variant B — Incident Response Playbook

**Use when:** `procedure_type: incident-response`
Documenting the response procedure for a specific class of incidents (not a single incident post-mortem — that is TMPL-008).

### B.1 Additional Frontmatter Fields

```yaml
# ── INCIDENT RESPONSE METADATA ───────────────────────────────
incident_class: ""            # What type of incident this covers — e.g., "Database failover"
severity_scope: "P0 | P1 | P2 | all"
on_call_rotation: ""          # Which on-call rotation owns this
escalation_path: []           # Ordered list: [L1 responder, L2 escalation, L3 leadership]
war_room_channel: ""          # e.g., "#incident-response" or Slack workspace
status_page_url: ""
customer_comm_required: "P0-only | P1+ | always | never"
```

### B.2 Required Section: Severity Classification Matrix

Insert as Section 1.5:

```markdown
## 1.5 Severity Classification Matrix
[TYPE: REFERENCE]

Classify the incident severity FIRST. The rest of this playbook branches by severity.

| Severity | Criteria | Examples | SLA | Escalation |
|----------|----------|---------|-----|------------|
| **P0 — Critical** | [Criteria — e.g., ">20% users cannot complete core action"] | [Example] | [Resolution: Nhrs] | [Immediate: L2 + Director] |
| **P1 — High** | [Criteria] | [Example] | [Resolution: Nhrs] | [Within N min: L2] |
| **P2 — Medium** | [Criteria] | [Example] | [Resolution: Nhrs] | [L1 handles; L2 notified] |
| **P3 — Low** | [Criteria] | [Example] | [Resolution: next business day] | [L1 handles] |
```

### B.3 Required Section: Communication Templates

Insert as Section 3.N:

```markdown
## 3.N Communication Templates
[TYPE: REFERENCE]

Use these templates verbatim. Customize only the bracketed fields.
Do not improvise wording during an active incident.

### Internal — War Room Opening

```
🚨 INCIDENT DECLARED — [Severity]
System: [system/service]
Impact: [User-facing description — what users cannot do]
Started: [HH:MM UTC]
Incident Commander: [Name]
War room: [#channel]
Status page: [URL — update this first]
All hands needed: [role list]
```

### External — Status Page (Initial)

```
We are investigating reports of [user-facing impact description].
Our team is actively working to resolve this issue.
Next update: [HH:MM UTC]
```

### External — Status Page (Update)

```
We have identified the cause of [user-facing description].
[Brief: what is being done — no technical jargon].
Estimated resolution: [HH:MM UTC / "We will update again in N minutes"].
```

### External — Status Page (Resolution)

```
This incident has been resolved as of [HH:MM UTC].
[One sentence: what happened and what was done].
We apologize for the impact. A full post-mortem will be published within [N days].
```
```

### B.4 Required Section: Escalation Decision Tree

Insert as Section 3.N+1:

```markdown
## 3.N+1 Escalation Decision Tree
[TYPE: PROCEDURE]

Use this tree at any point during the incident when the current level cannot make progress.

```
[N] minutes without progress on root cause identification?
  → Escalate: page [L2 role]

[N] minutes without a mitigation in place?
  → Escalate: notify [Director/VP of Engineering]

Customer-facing impact lasting >[N] minutes?
  → External communication REQUIRED (use template in Section 3.N)
  → Notify [Customer Success lead]

Data loss suspected or confirmed?
  → IMMEDIATELY: page [Data team / Security team]
  → IMMEDIATELY: notify [Legal / Compliance]
  → STOP: do not attempt recovery without [Data team] present

Security incident suspected (unauthorized access, breach)?
  → IMMEDIATELY: page [Security team]
  → IMMEDIATELY: preserve logs — do NOT rotate or delete anything
  → Do NOT communicate externally without [Legal] sign-off
```
```

---

## Variant C — Database Migration Procedure

**Use when:** `procedure_type: migration`
Documenting a schema migration, data migration, or database version upgrade.

### C.1 Additional Frontmatter Fields

```yaml
# ── DATABASE MIGRATION METADATA ──────────────────────────────
migration_type: "schema | data | version-upgrade | platform-migration"
database_system: ""           # e.g., "PostgreSQL 15.4"
target_database_version: ""   # For version upgrades
migration_tool: ""            # e.g., "Flyway 9.22 / Liquibase / Alembic / manual SQL"
migration_id: ""              # Tool-assigned migration ID or version number
zero_downtime: "yes | no"     # Can this be done without stopping the service?
estimated_duration_minutes: 0
estimated_rows_affected: 0
reversible: "yes | partial | no"  # Can this migration be rolled back?
rollback_complexity: "simple | complex | data-loss-risk | irreversible"
```

### C.2 Required Section: Pre-Migration Safety Checklist

Insert as Section 2 replacement for this variant (extends base pre-execution checklist):

```markdown
## 2. Pre-Migration Safety Checklist
[TYPE: PROCEDURE]

Complete ALL items before executing the migration. This checklist is
more extensive than the standard pre-execution checklist because
database migrations carry data integrity and data loss risk.

```
BACKUP VERIFICATION
  [ ] Full database backup taken within last [N hours]: [backup ID / path]
  [ ] Backup integrity verified (restore test passed): [test date]
  [ ] Point-in-time recovery (PITR) confirmed active: [verify command]
  [ ] Backup stored in separate location from primary: [location]

MIGRATION VALIDATION
  [ ] Migration script reviewed by second engineer: [reviewer name]
  [ ] Migration tested in staging environment on [date]: [result]
  [ ] Staging data volume comparable to production: [staging: N rows / prod: N rows]
  [ ] Estimated duration verified in staging: [N minutes]
  [ ] Rollback tested in staging: [YES — N minutes / NO — irreversible noted]

PERFORMANCE IMPACT ASSESSMENT
  [ ] Lock contention analysis completed: [tool used / result]
  [ ] Migration can run without full table lock: [YES / NO — mitigations below]
  [ ] Peak traffic window avoided: deployment window [HH:MM–HH:MM UTC]
  [ ] Read replica availability confirmed if locks expected: [replicas: N]

DATABASE STATE
  [ ] No active long-running transactions: [verify: SELECT * FROM pg_stat_activity...]
  [ ] Replication lag < [N seconds]: [check command]
  [ ] Disk space sufficient: [current: N% used / required: NGB additional]

SERVICE COORDINATION
  [ ] [Service A] prepared for migration (migration-aware code deployed): [version]
  [ ] [Service B] prepared: [version]
  [ ] Dual-write mode activated (if zero-downtime): [YES / N/A]
  [ ] Feature flag [flag name] toggled to old path: [YES / N/A]
```

### C.3 Required Section: Zero-Downtime Migration Strategy (when applicable)

Insert as Section 3 preamble when `zero_downtime: yes`:

```markdown
## 3.N Zero-Downtime Migration Strategy
[TYPE: PROCEDURE]

This migration uses the [expand-contract / dual-write / shadow table] pattern
to avoid service downtime. The migration runs in [N] phases.

**Phase sequence overview:**

Phase 1 — EXPAND: Add new structure without removing old
  Duration: [N min] | Risk: LOW | Rollback: Drop added columns/tables

Phase 2 — MIGRATE: Copy / transform data in batches
  Duration: [N min] | Risk: MEDIUM | Rollback: Truncate new table + reset flag

Phase 3 — CUTOVER: Switch reads/writes to new structure
  Duration: [N min] | Risk: HIGH | Rollback: Switch flags back (fast)

Phase 4 — CONTRACT: Remove old structure after validation period
  Duration: [N min] | Wait: [N days] | Rollback: IRREVERSIBLE after this phase

**Safety gates between phases:**
Each phase gate must be validated before proceeding to the next phase.
An engineer must explicitly confirm the gate — no automated progression.
```

### C.4 Required Section: Post-Migration Validation

Insert as final step group in Section 3:

```markdown
### Post-Migration Validation

Run ALL validation queries before declaring migration complete.

```
STRUCTURAL VERIFICATION
  [ ] Schema matches expected: [describe check or query]
  [ ] All indexes present and valid (no invalid indexes):
      SELECT schemaname, tablename, indexname FROM pg_indexes WHERE ...
  [ ] Foreign key constraints are intact:
      SELECT conname FROM pg_constraint WHERE contype = 'f'...
  [ ] Statistics updated: ANALYZE [table_name];

DATA INTEGRITY
  [ ] Row count matches pre-migration count (±[tolerance]):
      SELECT COUNT(*) FROM [table]; -- Expected: [N]
  [ ] Null counts in new columns within expected range: [query]
  [ ] Sample spot-check: [specific business logic query to verify data is correct]
  [ ] Checksums match (if full data migration): [checksums command]

APPLICATION VALIDATION
  [ ] [Critical query 1] returns correct results: [query / test]
  [ ] [Critical query 2]: [query / test]
  [ ] No application errors related to database in [N] minutes post-migration
  [ ] Read replica replication caught up: lag < [N seconds]

PERFORMANCE
  [ ] Query performance for [critical query] within [N]ms: [EXPLAIN ANALYZE result]
  [ ] No new long-running queries detected: [check pg_stat_activity]
```
```

---

*Source: TMPL-000 Variant Guidance v1.0 — Phase 3 WP-3.5*
*Use alongside TMPL-003, not instead of it.*
