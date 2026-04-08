# ARCH-001 vs TMPL-004 Family: When to Use Which
## Architecture Reference — AI Context File System Decision Guide

```yaml
---
document_id: "ARCH-001-TMPL004-Reconciliation"
title: "ARCH-001 vs TMPL-004 Family — Architecture Reference: When to Use Which"
version: "1.0"
created: "2026-04-04"
status: "Final"
parent_document: "PROJ-001 AI-Optimized Documentation Protocol"
template_version_used: "TMPL-002 v1.1"

intent: >
  Enable developers and AI agents to make the correct choice between ARCH-001
  .ctx.md skill context files and the TMPL-004 family (004A/B/C) when setting
  up context for AI agents, by defining the scope, authorship, and lifecycle
  differences between the two systems.

consumption_context:
  - human-reading
  - ai-reasoning
  - rag-retrieval

triggers:
  - "ARCH-001 vs TMPL-004"
  - "when to use context brief vs context file"
  - "difference between ctx file and TMPL-004"
  - "which agent context format should I use"
  - "ARCH-001 TMPL-004 reconciliation"
  - "should I use a ctx file or a living context document"

negative_triggers:
  - "how to fill in TMPL-004A" # → TMPL-004A.md template directly
  - "how to create a ctx file" # → ARCH-001 or ARCH-002 documentation
  - "what templates are available" # → SKILL.md decision tree

volatility: "stable"
research_validated: false
review_trigger: "When ARCH-001 or the TMPL-004 family undergoes a major version update"

confidence_overall: "high"
confidence_note: "Direct architectural knowledge — reflects actual design decisions made in this project"
---
```

---

> ## 🤖 AI Summary
> **Core Purpose:** Defines the boundary between ARCH-001 .ctx.md skill context files and the TMPL-004 family (004A/B/C), so developers and agents always pick the right tool.
> **Key Distinction:** ARCH-001 .ctx.md files are skill-specific operational context (narrow scope, agent updates them freely). TMPL-004 family documents are structured specifications (human-authored, agent reads or appends under strict rules).
> **Decision Rule:** If you're configuring a specific Claude skill → ARCH-001 .ctx.md. If you're specifying agent behavior, workflow, or cross-session memory → TMPL-004A, B, or C.
> **Trust Level:** HIGH — architectural decision for this project
> **Do NOT Use This For:** Filling in the templates (use the templates directly); understanding what ARCH-001 is (see ARCH-001 documentation)
> **Review By:** When either system has a major version update

---

## TABLE OF CONTENTS

1. [The Two Systems — Summary](#1-the-two-systems--summary)
2. [Scope Comparison](#2-scope-comparison)
3. [Authorship & Write Access](#3-authorship--write-access)
4. [Lifecycle & Update Frequency](#4-lifecycle--update-frequency)
5. [Decision Tree — Which to Use](#5-decision-tree--which-to-use)
6. [When to Use Both](#6-when-to-use-both)
7. [Cross-References](#7-cross-references)
8. [Revision History](#8-revision-history)

---

## 1. The Two Systems — Summary
[TYPE: EXPLANATION]

This project uses two distinct systems for providing AI agents with context.
They serve different purposes and should not be conflated or substituted
for each other.

**System 1 — ARCH-001 `.ctx.md` Skill Context Files**
Small, operational context files that a specific Claude skill reads to
understand the current project's conventions, state, and configuration.
Each file belongs to one skill. The agent updates them freely during
execution. Examples: `doc-standards.ctx.md` (for the `ai-documentation`
skill), `test-standards.ctx.md` (for a test generation skill).

**System 2 — TMPL-004 Family (004A, 004B, 004C)**
Structured specification documents authored by humans to define agent
behavior, execution blueprints, or persistent cross-session memory.
The documents are designed for a specific agent role or workflow, not
a specific skill. Agent write access is restricted and controlled.

The two systems are **complementary, not competing**. A project can and
often will use both simultaneously — a skill reads its `.ctx.md` file
AND a TMPL-004A context brief in the same session.

---

## 2. Scope Comparison
[TYPE: REFERENCE]

The ARCH-001 `.ctx.md` files and TMPL-004 family documents differ in
their scope of applicability.

| Dimension | ARCH-001 `.ctx.md` | TMPL-004A | TMPL-004B | TMPL-004C |
|-----------|-------------------|-----------|-----------|-----------|
| **Scope** | One skill's operational context | One agent's identity & constraints | One specific workflow's execution steps | One project's cross-session memory |
| **Consumed by** | The specific skill it belongs to | Any agent performing the described role | Any agent executing the described workflow | Any agent working on the described project |
| **Content type** | Project conventions, numbering, state | Identity, knowledge, constraints, tools | Phases, gates, steps, stop conditions | Stable facts + volatile state + learnings |
| **Typical size** | 500–2,000 tokens | 1,500–4,000 tokens | 2,000–6,000 tokens | 2,000–8,000 tokens (grows over time) |
| **Portability** | Skill-specific — not portable across skills | Reusable by any agent with the same role | Reusable for the same workflow | Reusable across all agents on the project |

---

## 3. Authorship & Write Access
[TYPE: REFERENCE]

The write access model is the most important structural difference between
the two systems.

| Dimension | ARCH-001 `.ctx.md` | TMPL-004A | TMPL-004B | TMPL-004C |
|-----------|-------------------|-----------|-----------|-----------|
| **Initial author** | Human (via context setup wizard) | Human only | Human only | Human only |
| **Agent write access** | YES — agent updates freely during execution | NONE — read only | NONE — read only (except resumability log) | PARTIAL — Sections 3.2, 6, 7 only (append-only) |
| **Update model** | Agent overwrites or appends as needed | Human updates on role/domain change | Human updates on workflow change | Agent appends; human validates and applies learnings |
| **Conflicts handled by** | Last write wins (skill manages its own file) | Human — only human can change 004A | Human — only human can change 004B | Human validates agent-observed changes before applying |
| **Audit trail** | Change Log table at bottom of file | Revision History | Revision History + Section 7.2 execution log | Revision History + Section 6 observations log + Section 7 session log |

The critical rule: **agents trust `.ctx.md` files completely and update them
freely**. Agents treat TMPL-004A and TMPL-004B as authoritative instructions
they cannot modify. Agents treat TMPL-004C as a memory they can append to
but never revise.

---

## 4. Lifecycle & Update Frequency
[TYPE: REFERENCE]

The update frequency of each document type reflects its purpose.

| Type | Update Frequency | Trigger | Who Updates |
|------|-----------------|---------|------------|
| ARCH-001 `.ctx.md` | Every session (or when conventions change) | Skill post-execution observation phase | Agent (operational updates) + Human (corrections) |
| TMPL-004A | Low — months between updates | Agent role changes; domain tech stack changes | Human only |
| TMPL-004B | Low — when the underlying process changes | Workflow redesign; system change that affects steps | Human only |
| TMPL-004C | Every session | Agent session completes | Agent appends; Human validates weekly |

A `.ctx.md` file that hasn't been updated in 30+ days is a warning sign —
the agent may have stopped updating it or the skill may have drifted.

A TMPL-004C that hasn't been updated in 30+ days is also a warning sign —
sessions have occurred without proper context persistence.

A TMPL-004A or TMPL-004B that hasn't changed in 6+ months is NORMAL —
they represent stable specifications.

---

## 5. Decision Tree — Which to Use
[TYPE: REFERENCE]

Use this decision tree when deciding which system to use for a new context
or agent specification need.

```
Are you configuring a SPECIFIC Claude skill's project settings?
(e.g., "tell the ai-documentation skill what numbering system this project uses")
  YES → ARCH-001 .ctx.md file
         Follow ARCH-001/002 documentation to create it
  NO  → Continue ↓

Are you specifying what an agent IS and how it should behave?
(its identity, domain knowledge, constraints, tools)
  YES → TMPL-004A: Agent Context Brief
  NO  → Continue ↓

Are you defining a multi-step workflow for an agent to EXECUTE?
(phases, gates, inputs/outputs, stop conditions)
  YES → TMPL-004B: Agentic Workflow Plan
  NO  → Continue ↓

Do you need persistent memory that accumulates across many sessions?
(facts the agent learns, state that changes session to session)
  YES → TMPL-004C: Living Context Document
  NO  → Continue ↓

Are you documenting what a system DOES for humans and agents to read?
  → TMPL-002: Technical Reference

Are you documenting how to DO something step by step for humans?
  → TMPL-003: Procedure & Workflow
```

---

## 6. When to Use Both
[TYPE: EXPLANATION]

Using ARCH-001 `.ctx.md` and TMPL-004 family documents together is
common and correct. The two systems solve different problems in the
same session.

**Common combination: `.ctx.md` + TMPL-004A**

A session starts with the skill reading its `.ctx.md` file to understand
project conventions, AND the agent reading a TMPL-004A context brief to
understand its specific role and constraints for this task. The `.ctx.md`
answers "what are this project's documentation standards?" The TMPL-004A
answers "who am I and what am I here to do?"

**Common combination: `.ctx.md` + TMPL-004C**

The `doc-standards.ctx.md` skill context file captures documentation
conventions (the skill's operational context). The TMPL-004C living
context captures project-level facts, decisions, and cross-session
learnings (the broader project memory). They are complementary — neither
replaces the other.

**Uncommon but valid: TMPL-004A + TMPL-004B**

A complex agent deployment may pair a context brief (who the agent is)
with a workflow plan (what it's doing in this specific session). Load the
context brief first; the workflow plan references it with a `paired_context_brief`
frontmatter field.

**Anti-pattern to avoid: Using TMPL-004C instead of a `.ctx.md` file**

TMPL-004C is not a replacement for skill `.ctx.md` files. TMPL-004C is
project-wide; `.ctx.md` files are skill-specific. Using a single TMPL-004C
for what should be a skill `.ctx.md` causes context pollution — the agent
reads project-wide information when it only needed skill-specific conventions.

---

## 7. Cross-References
[TYPE: REFERENCE]

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [SEE_ALSO] | ARCH-001 Skill Architecture & Living Context Framework | All | Primary documentation for `.ctx.md` files |
| [SEE_ALSO] | ARCH-002 Context Template Generator | All | Tool for creating `.ctx.md` files |
| [IMPLEMENTS] | PROJ-002 Enhancement Roadmap | WP-2.6 | This document is the WP-2.6 reconciliation deliverable |
| [DEPENDS_ON] | TMPL-004A Agent Context Brief | All | One of the three systems described here |
| [DEPENDS_ON] | TMPL-004B Agentic Workflow Plan | All | One of the three systems described here |
| [DEPENDS_ON] | TMPL-004C Living Context Document | All | One of the three systems described here |

---

## 8. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-04 | Initial | Document created | Phase 2 WP-2.6 — ARCH-001 / TMPL-004 reconciliation required after TMPL-004 split into three variants |

---

*Template: TMPL-002 Technical Reference v1.1 | Parent: PROJ-001 AI-Optimized Documentation Protocol*
