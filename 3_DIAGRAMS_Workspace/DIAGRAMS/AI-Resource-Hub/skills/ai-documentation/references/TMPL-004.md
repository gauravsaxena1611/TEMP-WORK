# TMPL-004: AI Agent Context & Plan — Routing Index
## Select the correct variant below before using

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-004 has been split into three purpose-specific variants.
This file is a router — it does NOT contain a usable template.
Load the correct variant file based on your use case.
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

## Which variant do you need?

Ask: **What is the PRIMARY purpose of this document?**

```
Equip an agent with identity, knowledge, and constraints
BEFORE it starts a task (agent reads it once)
  → TMPL-004A: Agent Context Brief
  → Load: references/TMPL-004A.md

Define a multi-step execution blueprint for an agent
to FOLLOW during task execution (agent executes phases)
  → TMPL-004B: Agentic Workflow Plan
  → Load: references/TMPL-004B.md

Maintain persistent cross-session memory that an agent
reads AND updates after every session
  → TMPL-004C: Living Context Document
  → Load: references/TMPL-004C.md
```

## Quick Differentiator

| Scenario | Use |
|----------|-----|
| "I need to brief an agent before it starts working" | TMPL-004A |
| "I need to give an agent step-by-step instructions for a complex task" | TMPL-004B |
| "I need the agent to remember what it learned across sessions" | TMPL-004C |
| "I need to define what an agent should and shouldn't do" | TMPL-004A |
| "I need to automate a multi-phase workflow" | TMPL-004B |
| "I need a context file that evolves over time" | TMPL-004C |

## Relationship to ARCH-001 .ctx.md Files

ARCH-001 `.ctx.md` files are **skill-specific operational context** that
a skill reads to understand the current project's conventions (e.g.,
`doc-standards.ctx.md` for the `ai-documentation` skill).

TMPL-004C is **project-level living context** spanning multiple skills
and sessions. The two serve different scopes and are complementary.

For the full decision on when to use which, see:
`ARCH-001-TMPL004-Reconciliation.md`

---

*TMPL-004 router v1.1 | Variants: TMPL-004A, TMPL-004B, TMPL-004C*
*This file was split from the original TMPL-004 v1.0 as part of Phase 2 WP-2.1*
