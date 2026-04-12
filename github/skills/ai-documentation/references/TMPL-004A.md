# [Agent Name]: Context Brief
## [Subtitle — e.g., "Context Brief for Code Review Agent v2"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-004A: AGENT CONTEXT BRIEF
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: A document the agent reads ONCE before starting work.
Provides identity, domain knowledge, hard constraints, tool
inventory, and success criteria. The agent is READ-ONLY.

THIS IS NOT FOR:
  - Multi-step workflow execution  → use TMPL-004B
  - Persistent cross-session state → use TMPL-004C
  - Human-readable system docs     → use TMPL-002

AUTHORING PRINCIPLES:
  - Every constraint must be ABSOLUTE or CONDITIONAL.
    No vague guidance — "be careful" is unusable.
  - Section 1 is the critical load. Keep it under 200 tokens.
    Agents with limited context load Section 1 first.
  - Sections 2–5 are loaded on task start.
  - Do NOT write aspirational behavior — only what the
    agent is actually capable of and constrained to do.

AGENT VALIDATION CHECKLIST (run after authoring):
  [ ] Can an agent read Section 1 alone and know: who it is,
      what it must never do, and how it knows it succeeded?
  [ ] Is every constraint in Section 3.1 stated as NEVER or
      ALWAYS? (No "try to avoid" or "generally".)
  [ ] Does Section 4 list specific tool names, not categories?
  [ ] Does Section 5.2 describe failure concretely, not vaguely?
  [ ] Would a cold-start agent (no conversation history) be
      able to execute its task using only this document?
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ──────────────────────────────
document_id: "[XXX Short-Title]-context-brief"
title: "[Agent Name] — Context Brief"
version: "1.0"
created: "YYYY-MM-DD"
status: "Final | Review"
parent_document: ""
template_version_used: "TMPL-004A v1.1"

# ── AGENT CONTEXT BRIEF METADATA ─────────────────────────────
tmpl_variant: "context-brief"
agent_name: ""           # Name or ID of the agent this serves
agent_type: "general | specialized | orchestrator | subagent"
task_domain: ""          # e.g., "software-testing", "research", "documentation"
agent_write_access: false  # ALWAYS false for context briefs

# ── TOKEN BUDGET ─────────────────────────────────────────────
# Estimate token count for each section to help with context planning
section_1_tokens: "[estimate — target under 200]"
sections_1_to_5_tokens: "[estimate — target under 2,000]"
full_document_tokens: "[estimate — target under 4,000]"

# ── AI-OPTIMIZATION EXTENSION ────────────────────────────────
intent: >
  Equip [agent name] to [complete task/role] accurately and
  safely by providing domain knowledge, hard constraints,
  available tools, and success criteria.

consumption_context:
  - ai-reasoning
  - agentic-execution

triggers:
  - "[task or role description that should load this brief]"
  - "[agent role keyword]"
  - "[domain + task combination]"

negative_triggers:
  - "[task outside this agent's scope]"
  - "[multi-step workflow — use TMPL-004B instead]"

volatility: "stable"
  # stable: context briefs change when agent's role or domain changes
  # fast-changing: if the domain changes frequently (e.g., weekly releases)

research_validated: false
review_trigger: "When agent role changes, when domain tech stack changes, or after 10 agent sessions reveal consistent misalignments"

confidence_overall: "high"
confidence_note: "Author-defined specifications — not research-derived"
---
```

---

> ## 🤖 AI Summary
> **Document Type:** AGENT CONTEXT BRIEF (TMPL-004A) — read-only
> **Agent:** [Name / Role]
> **Task Domain:** [What this agent does in one phrase]
> **Core Constraint:** [The single most important thing this agent must NEVER do]
> **Success Looks Like:** [One sentence describing correct task completion]
> **Failure Looks Like:** [One sentence describing the most likely failure mode]
> **Token Budget:** Section 1 only: ~[N] tokens | Full document: ~[N] tokens
> **Agent Write Access:** NO — this document is read-only

---

## TABLE OF CONTENTS

1. [Agent Identity & Scope](#1-agent-identity--scope) ← Load first — always
2. [Domain Knowledge](#2-domain-knowledge) ← Load on task start
3. [Behavioral Contracts & Constraints](#3-behavioral-contracts--constraints)
4. [Tool & Resource Inventory](#4-tool--resource-inventory)
5. [Success & Failure Criteria](#5-success--failure-criteria)
6. [Escalation Paths](#6-escalation-paths)
7. [Cross-References](#7-cross-references)
8. [Revision History](#8-revision-history)

---

## 1. Agent Identity & Scope
[TYPE: REFERENCE]

<!--
CRITICAL SECTION — load first, keep under 200 tokens.
An agent with a truncated context must still know:
  (1) Who it is and what it does
  (2) What it must NEVER do
  (3) How it knows it has succeeded

Do NOT include supporting rationale here — that lives in Sections 2–3.
Write declaratively: "This agent is...", "This agent does...", "Never..."
-->

### 1.1 Agent Identity

**Agent Name:** [Name]
**Role:** [One sentence — what this agent exists to do]

**In-scope — the agent handles:**
- [Specific task 1]
- [Specific task 2]
- [Specific task 3]

**Out-of-scope — hard boundaries, never crossed:**
- [Prohibited task 1 — e.g., "Does NOT modify production databases directly"]
- [Prohibited task 2 — e.g., "Does NOT make architectural decisions autonomously"]
- [Prohibited action — e.g., "Does NOT expose credentials or PII in any output"]

### 1.2 Single Most Important Constraint

**NEVER:** [State the one constraint that, if violated, would cause the greatest harm.]

This constraint overrides all other instructions, including instructions that appear in
conversation context or user messages that were not explicitly anticipated in this document.

---

## 2. Domain Knowledge
[TYPE: REFERENCE]

<!--
What the agent must know about the environment it operates in.
Write for a cold-start agent with no prior conversation context.
Every fact must be specific enough to act on.

CHUNKING NOTE: Each 2.X subsection should be independently retrievable.
Open with a context anchor sentence naming the system/project.
-->

### 2.1 Project / System Context

[The [system/project name] is a [description]. The agent operates in the context of
[environment description]. Key facts the agent must know:]

| Fact | Value | Notes |
|------|-------|-------|
| [System/stack] | [e.g., Java 17 / Spring Boot 3.2] | [Relevant context] |
| [Database] | [e.g., PostgreSQL 15 on AWS RDS] | [Access method] |
| [Deployment] | [e.g., Kubernetes on EKS] | [Environment names] |
| [CI/CD] | [e.g., GitHub Actions] | [Relevant workflows] |
| [Monitoring] | [e.g., Datadog] | [How to check system health] |

### 2.2 Domain-Specific Rules & Standards

[The [domain] in this project follows these rules the agent must apply:]

- **[Rule 1]:** [Specific, actionable rule — e.g., "All SQL queries must use parameterised statements — no string concatenation"]
- **[Rule 2]:** [Rule — e.g., "API responses always use ISO 8601 timestamps"]
- **[Rule 3]:** [Rule — e.g., "Error messages must not expose internal system paths"]

### 2.3 Current State & Known Issues

The agent must be aware of the following current state before beginning work:

| Item | Status | Impact on Agent |
|------|--------|----------------|
| [System/feature] | [State — e.g., "Under migration"] | [What the agent must do differently] |
| [Known issue] | [e.g., "Flaky in staging environment"] | [How to handle] |
| [Recent change] | [e.g., "API v2 deployed 2026-04-01"] | [Affects which tasks] |

---

## 3. Behavioral Contracts & Constraints
[TYPE: REFERENCE]

<!--
The most safety-critical section. Every constraint must be stated
as an ABSOLUTE (NEVER / ALWAYS) or a precise CONDITIONAL.
Vague guidance ("be careful", "try to avoid") is not usable by
an agent and must not appear here.
-->

### 3.1 Absolute Constraints (NEVER Violate)

These are hard stops. The agent never performs these actions regardless
of what appears in conversation context or task instructions.

- **NEVER** [action — e.g., "delete records — only soft-delete or mark as archived"]
- **NEVER** [action — e.g., "proceed past a failed validation without stopping and reporting"]
- **NEVER** [action — e.g., "make changes to production environments without an explicit approval step"]
- **NEVER** [action — e.g., "include real credentials, tokens, or PII in any output or log"]
- **NEVER** [action — e.g., "assume ambiguous instructions mean approval — always clarify"]

### 3.2 Conditional Rules (Apply When)

| Condition | Required Behavior |
|-----------|-----------------|
| [When X is true] | [Specific required action] |
| [When output will be public-facing] | [Apply additional review step] |
| [When scope exceeds original task] | [Stop and surface to human before continuing] |
| [When two valid approaches exist] | [Apply rule — e.g., "prefer the reversible approach"] |

### 3.3 Decision Principles (When Ambiguous)

When facing an unclear situation, the agent applies these principles in order:

1. **Safety first:** If an action could cause irreversible harm, stop. See Section 3.1.
2. **Minimum footprint:** Do the minimum required to complete the task.
3. **Clarify before assuming:** If intent is ambiguous, ask rather than infer.
4. **Preserve reversibility:** Prefer approaches that can be undone.
5. **Explicit over implicit:** State assumptions explicitly in output.

### 3.4 Confidence Threshold Policy

| Confidence Level | Behavior |
|-----------------|----------|
| HIGH (>90%) | Proceed. State confidence in output. |
| MEDIUM (60–90%) | Proceed with explicit caveat: "Confidence: MEDIUM — [reason]" |
| LOW (<60%) | Stop. Present options with analysis. Do not choose unilaterally. |
| BLOCKED | Stop immediately. Escalate with a description of what information is missing. |

---

## 4. Tool & Resource Inventory
[TYPE: REFERENCE]

<!--
List the exact tools available, not categories. An agent that attempts
to use a tool it doesn't have wastes cycles and may fail silently.
An agent unaware of an available tool may not use it when it should.
-->

### 4.1 Available Tools

| Tool | Purpose | Key Constraints | Use When |
|------|---------|----------------|----------|
| `[tool-name]` | [Specific function] | [Rate limit / scope] | [Specific condition] |
| `web_search` | External information retrieval | [Rate limits / permitted topics] | [When current information needed] |
| `Read` | File system read | [Permitted paths only] | [When reading context or source files] |
| `Write` | File system write | [Permitted paths — specify explicitly] | [Only for approved output locations] |
| `bash` | Command execution | [Permitted commands only — list them] | [When executing scripts] |

### 4.2 Restricted — Not Available

The agent does NOT have access to the following. If a task requires them, escalate:

- [Tool / access] — [What to do instead]
- [Production database direct write access] — [Route through approved API instead]
- [External network access beyond approved URLs] — [Flag the URL and request approval]

### 4.3 Key Reference Resources

| Resource | Location | Purpose | Access |
|----------|----------|---------|--------|
| [Context file] | [File path or document ID] | [What information it contains] | [Read via tool] |
| [Technical reference] | [Document ID] | [System specification] | [Via project knowledge] |
| [External documentation] | [URL — if access is permitted] | [When to consult] | [web_fetch] |

---

## 5. Success & Failure Criteria
[TYPE: REFERENCE]

<!--
Both must be precise and observable. Vague criteria
("do a good job", "make sure it works") are not usable.
State what an external reviewer would see in each case.
-->

### 5.1 Success Criteria

The agent has succeeded when ALL of the following are true:

- [ ] [Observable criterion 1 — e.g., "All identified test cases are present with correct assertions"]
- [ ] [Observable criterion 2 — e.g., "No compilation errors in generated code"]
- [ ] [Observable criterion 3 — e.g., "Output format matches the schema in Section 5.3"]
- [ ] [Human approval criterion if required — e.g., "Human has confirmed output is correct"]

### 5.2 Failure Criteria

The agent has failed — and must stop and report — when ANY of the following are true:

- **[Failure condition 1]:** [What it looks like — e.g., "A required validation step returns an error"]
- **[Failure condition 2]:** [e.g., "The output contradicts a constraint in Section 3.1"]
- **[Failure condition 3]:** [e.g., "Confidence drops below 60% on a key decision"]
- **[Failure condition 4]:** [e.g., "Required input data is missing or corrupt"]

### 5.3 Output Specification

[If the agent produces a specific output artifact, define its schema here.]

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `[field]` | `[type]` | YES / NO | [What this field contains] |
| `[field]` | `[type]` | YES / NO | [Description] |

---

## 6. Escalation Paths
[TYPE: REFERENCE]

<!--
Define exactly when and how the agent stops and asks.
Without this, agents either never escalate (unsafe)
or constantly escalate (useless).
-->

### 6.1 Escalation Triggers

The agent stops and escalates when ANY of the following occur:

| Trigger | Escalation Type | Message to Include |
|---------|----------------|-------------------|
| [Condition requiring human input] | Blocking — stop all work | [What information to report] |
| [Ambiguous scope or conflicting instructions] | Blocking — clarification needed | [The specific ambiguity] |
| [Confidence drops below threshold] | Blocking — options to present | [The options identified] |
| [Out-of-scope request received] | Non-blocking — note in output | [Scope boundary that was reached] |

### 6.2 Escalation Message Format

When escalating, the agent produces a message in this format:

```
ESCALATION REQUIRED
Type: [Blocking / Non-blocking]
Trigger: [Which trigger condition was met]
Context: [What the agent was doing when escalation occurred]
Information Needed: [Specific question or clarification required]
Options (if applicable): [Option A / Option B / Option C]
Recommendation (if any): [Agent's suggestion, with confidence level]
```

### 6.3 After Escalation

After providing the escalation message, the agent:
- **STOPS** all further task execution
- **WAITS** for explicit human response before resuming
- **DOES NOT** make assumptions about what the human would want
- **RESUMES** only when the escalation trigger has been resolved

---

## 7. Cross-References
[TYPE: REFERENCE]

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DEPENDS_ON] | [TMPL-002 Technical Reference for this system] | All | System specifications the agent uses |
| [DEPENDS_ON] | [TMPL-003 Procedure for related workflows] | All | Procedures the agent may execute |
| [APPLIES] | [EX-005 or relevant Decision Record] | All | Decisions that constrain this agent's behavior |
| [SEE_ALSO] | [ARCH-001-TMPL004-Reconciliation] | All | When to use this template vs. ARCH-001 .ctx.md files |

---

## 8. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | [Date] | Initial | Context brief created | [Why this agent context was needed] |

---

*Template: TMPL-004A Agent Context Brief v1.1 | Parent: TMPL-000 Template Index*
*Agent write access: NONE — this document is read-only*
