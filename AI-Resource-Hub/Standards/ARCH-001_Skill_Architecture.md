# Skill Architecture & Living Context Framework
## Governing Standard for Skill Creation, Maintenance & Self-Updating Dependencies

**Document ID:** ARCH-001 Skill Architecture  
**Parent Document:** RULES-001 Documentation Standards  
**Version:** 2.0  
**Created:** March 28, 2026  
**Updated:** 2026-04-05  
**Status:** Active

---

## PURPOSE

**This document covers:**
- The architectural standard governing how ALL Claude Skills are created, structured, and maintained
- The Living Context system: how skills externalize volatile dependencies into self-updating context files
- The Context Resolution Lifecycle: what happens every time any skill is invoked
- The Passive Learning mechanism: how Claude observes and proposes context updates during normal work
- The Skills Updater meta-skill specification
- Integration requirements for the Skill Creator Pro prompt (PROMPT-001)
- Context file templates, type classifications, and freshness protocols
- **The Project Brain Layer: how project-level documentation in docs/ serves as the single authoritative context store**
- **The archives/ Raw Knowledge Ingestion Repository: how source materials feed the Project Brain**
- **The migration path from v1 .ctx.md context files to v2 docs/ Project Brain files (Mode C)**
- **AI Documentation template type assignments for all Project Brain documents**

**This document does NOT cover:**
- General Claude usage — See Claude Fundamentals documentation
- Specific skill content or workflows — Each skill has its own SKILL.md
- MCP server development — See MCP documentation
- Anthropic API usage — See Anthropic platform documentation
- Content of individual skill SKILL.md files — each skill's own file governs its workflow
- How to create ai-documentation TMPL templates — see ai-documentation skill

---

## TABLE OF CONTENTS

1. [Core Architecture Principles](#1-core-architecture-principles)
2. [The Skill Kernel + Living Context Model](#2-the-skill-kernel--living-context-model)
3. [Context File Type System](#3-context-file-type-system)
4. [Context File Format Standard](#4-context-file-format-standard)
5. [The Context Resolution Lifecycle](#5-the-context-resolution-lifecycle)
6. [The Passive Learning Mechanism](#6-the-passive-learning-mechanism)
7. [The Skills Updater Meta-Skill](#7-the-skills-updater-meta-skill)
8. [Integration with Skill Creator Pro (PROMPT-001)](#8-integration-with-skill-creator-pro-prompt-001)
9. [Integration with Existing Skills](#9-integration-with-existing-skills)
10. [Platform-Specific Considerations](#10-platform-specific-considerations)
11. [Context File Size & Hygiene Rules](#11-context-file-size--hygiene-rules)
12. [Anti-Patterns & Known Risks](#12-anti-patterns--known-risks)
13. [Quality Checklist](#13-quality-checklist)
14. [Project Brain Layer — docs/ as Authoritative Context Store](#14-project-brain-layer--docs-as-authoritative-context-store)
15. [archives/ — Raw Knowledge Ingestion Repository](#15-archives--raw-knowledge-ingestion-repository)

---

## 1. CORE ARCHITECTURE PRINCIPLES

### 1.1 The Fundamental Problem This Architecture Solves

Skills are markdown-based instruction packages that encode workflows, templates, and quality standards. They work well for stable, reusable logic. They fail when they embed volatile, project-specific information — database schemas, API contracts, coding conventions, business rules — that changes independently of the skill workflow itself.

When a skill hardcodes project-specific data, three failure modes emerge. First, the skill produces stale output when the underlying data changes without the skill being updated. Second, the skill becomes single-project — updating it for Project A breaks it for Project B. Third, maintenance becomes invisible — there is no signal that a dependency has changed until incorrect output is noticed, sometimes weeks later.

This architecture solves all three by separating stable skill logic from volatile project context, and by making Claude an active participant in keeping context current.

### 1.2 Design Principles

| Principle | Description | Analogy |
|-----------|-------------|---------|
| **Separation of Concerns** | Skill logic and project context live in separate files. Skills never contain project-specific data. | Like separating `@Service` logic from `application.yml` configuration in Spring Boot |
| **Dependency Injection** | Skills declare what context they need. The context resolution system provides it at invocation time. | Like `@Autowired` — the skill declares a dependency, the system injects it |
| **Living Documentation** | Context files are read AND written by Claude. They evolve with each use rather than requiring manual maintenance. | Like a Learnings.md file that accumulates knowledge |
| **Progressive Disclosure** | Only the context files relevant to the current task are loaded into the conversation. | Anthropic's core skill design principle: agent loads only what it needs |
| **Observe-Confirm-Update** | Claude never silently updates context. It observes, confirms, then writes. | Like a Git commit — changes are explicit and reviewed |
| **Portability** | The same skill works across multiple projects because project-specific data lives outside the skill. | Like Maven profiles — same build logic, different configurations |
| **Project Brain Authority** | When a docs/ Project Brain file and a skill kernel conflict, the docs/ file wins. | Like `application.yml` overriding hardcoded defaults in Spring Boot |
| **AI Documentation Compliance** | All Project Brain documents use templates from the ai-documentation skill (TMPL-002, TMPL-003, TMPL-004A/C, TMPL-005). | Like a company coding standard — every doc is predictably structured |
| **Provenance by Default** | Every agent action is logged to docs/Work Audit.md with a Work-ID appearing in every modified file's revision history. | Like Git commits — every change is explicit, attributed, and reversible |
| **Archive Before Derive** | Raw source materials are stored immutably in docs/archives/ before being processed into Project Brain documents. | Like raw data vs processed data pipelines |

### 1.3 Architectural Relationship to Existing Standards

This document works alongside — never replaces — the following project documents:

- **RULES-001 Documentation Standards** — Governs the format, naming, revision tracking, and cross-referencing of all documents including context files [RULES-001, All Sections]
- **Truth & Verification Standards** — Governs the accuracy and sourcing of any factual content within context files [Truth & Verification Standards, Sections 1-4]
- **PROMPT-001 Skill Creator Pro** — Governs the workflow for creating new skills. This architecture adds mandatory phases to that workflow [PROMPT-001, All Phases]

---

## 2. THE SKILL KERNEL + LIVING CONTEXT MODEL

### 2.1 Architecture Overview

Every skill in this system consists of two distinct components:

**The Skill Kernel** (installed, stable, reusable): Contains the workflow logic, output templates, quality checks, and process steps. This is the SKILL.md file and its bundled resources. It NEVER contains project-specific data.

**The Living Context** (project-level, dynamic, self-updating): Contains all project-specific and volatile information the skill needs. In v2+, this lives in the `docs/` Project Brain committed to the project repository.

### 2.2 Structural Diagram

```
INSTALLED SKILL PACKAGE (Portable — works across all projects)
skill-name/
├── SKILL.md                      ← Workflow logic + context declarations
├── references/                   ← Stable reference documentation
├── assets/                       ← Templates, static files
└── context-templates/            ← Templates for Project Brain documents

PROJECT-LEVEL CONTEXT — PROJECT BRAIN (v2+)
docs/ in project repository             ← single authoritative store

  Human-Primary Documents (also agent-readable):
  ├── docs/Application Understanding.md  [TMPL-002] — absorbs CTX-BIZ
  ├── docs/Application Workflows.md      [TMPL-003]
  ├── docs/Application Features.md       [TMPL-002]
  ├── docs/Contracts.md                  [TMPL-002 + TMPL-005/decision]
  ├── docs/Work Audit.md                 [TMPL-004C] — provenance log + pending observations
  ├── docs/Data Models.md                [TMPL-002] — absorbs CTX-DB
  └── docs/Users.md                      [TMPL-002]

  Agent-Primary Documents (compact, structured):
  ├── docs/context/api-contract.md       [TMPL-002] — absorbs CTX-API
  ├── docs/context/environment-config.md [TMPL-002] — absorbs CTX-ENV
  └── docs/context/context-manifest.md   [TMPL-002] — absorbs CTX-MANIFEST

  Feature-Level Documents (per feature):
  └── docs/features/<n>/
      ├── Feature Understanding.md        [TMPL-002]
      ├── Feature Work Audit.md           [TMPL-004C]
      ├── Tests Scenarios.md              [TMPL-003]
      └── Test Data Setup.md              [TMPL-003]

  Raw Knowledge Ingestion:
  └── docs/archives/
      ├── INGESTION-LOG.md               [TMPL-002]
      └── <source-type-subfolders>/

  Agent Entry Point:
  └── CLAUDE.md (project root)           [TMPL-004A]

LEGACY CONTEXT STORE (v1 — deprecated for new projects)
.claude/contexts/*.ctx.md               ← Claude Code (v1 only)
Project Knowledge *.ctx.md files        ← Claude.ai Projects (v1 only)
```

**Version policy:** All new projects created from v2.0+ of this architecture use the docs/ Project Brain exclusively. Existing projects using .ctx.md files must run project-understanding Mode C to migrate. See Section 14 for the full Project Brain specification and Section 5.3 for Mode C.

### 2.3 How Skills Declare Context Dependencies

Every SKILL.md file includes a `## Required Contexts` section that declares what context files it needs and what to do if they are missing. In v2+, context IDs map to `docs/` files rather than `.ctx.md` files.

---

## 3. CONTEXT FILE TYPE SYSTEM

### 3.1 Type Definitions

Every context file is classified into one of three types.

### 3.1.1 Type: SCANNABLE

**Definition:** Information Claude can discover autonomously by reading project files. No human input is needed after initial confirmation.

**Source examples:** Database migration files, OpenAPI/Swagger specifications, pom.xml/build.gradle, project directory structure, application.yml.

**Creation workflow:** Claude reads source files → extracts and structures information → presents to user for confirmation → saves.

**Update workflow:** Re-reads source files on invocation → compares against stored context → presents diff for approval.

### 3.1.2 Type: INFERRABLE

**Definition:** Information Claude can detect by analyzing code patterns, but which requires human confirmation because it represents team conventions rather than explicit configuration.

**Source examples:** Coding standards, naming conventions, annotation usage, test patterns, architectural patterns.

**Creation workflow:** Claude scans representative sample files → identifies recurring patterns with evidence counts → asks user to confirm, correct, or supplement.

**Update workflow:** Passive Learning mechanism (Section 6) surfaces pattern observations for review.

### 3.1.3 Type: DECLARED

**Definition:** Information that exists only in human knowledge — decisions made in meetings, business rules not yet codified in code, environment details.

**Source examples:** Business rules, domain constraints, environment URLs, team workflow preferences.

**Creation workflow:** Questions presented in a single batch. User provides answers. Claude structures into context file.

**Update workflow:** Show current values, ask: "Are these still accurate?" For DECLARED past 30 days: ask specific questions rather than blanket confirmation.

### 3.2 Type Selection Decision Guide

```
Can Claude find this in a project file that is deterministic and machine-readable?
  YES → SCANNABLE

  NO → Can Claude infer this by analyzing patterns across source files?
    YES → INFERRABLE
    NO  → DECLARED
```

### 3.3 New Context Types for Project Brain (v2+)

Three new context type identifiers are defined for the Project Brain layer.

| Context ID | Maps to | Type | Purpose |
|-----------|---------|------|---------|
| CTX-CONTRACTS | `docs/Contracts.md` | DECLARED (human-gated) | Architectural taste — design rules, coding standards, invariants. Agent reads; never auto-writes. Amendments require explicit human approval. |
| CTX-AUDIT | `docs/Work Audit.md` | Living (TMPL-004C) | Provenance log of all work — human and agent. Agents append to §3.2, §6.2, §7 only. Replaces CTX-OBS for pending observations. |
| CTX-FEATURE | `docs/features/<n>/` | SCANNABLE | Per-feature documentation folder. Scanned by skills that need feature-level context. |

**Deprecation notice:** `pending-observations.ctx.md` (CTX-OBS) is deprecated in v2+. All pending observations are logged to `docs/Work Audit.md §6.2` (TMPL-004C Pending Observations). Existing CTX-OBS files are migrated during Mode C.

---

## 4. CONTEXT FILE FORMAT STANDARD

### 4.1 File Naming Convention

**Legacy (.ctx.md format — v1 only):** `[descriptive-name].ctx.md`

**Project Brain documents (v2+)** use standard `.md` naming and follow ai-documentation TMPL conventions:

```
CLAUDE.md                                    ← Project root
docs/Application Understanding.md
docs/Contracts.md
docs/Work Audit.md
docs/Data Models.md
docs/context/api-contract.md
docs/context/environment-config.md
docs/context/context-manifest.md
docs/features/<feature-name>/Feature Understanding.md
docs/features/<feature-name>/Feature Work Audit.md
docs/features/<feature-name>/Tests Scenarios.md
docs/features/<feature-name>/Test Data Setup.md
docs/archives/INGESTION-LOG.md
```

Feature folder names use lowercase-hyphenated convention: `order-management`, `payment-processing`.

### 4.2 Context File Template (v1 .ctx.md format — retained for reference)

Legacy context files follow the standard template with Context Metadata, Content, Observations Log, and Change Log sections.

### 4.3 Context Manifest File (v2+)

In v2+, `docs/context/context-manifest.md` [TMPL-002] serves as the freshness index. It tracks last-updated dates and Work-IDs for every docs/ file.

---

## 5. THE CONTEXT RESOLUTION LIFECYCLE

### 5.1 Overview

Every time any skill is invoked, the Context Resolution Lifecycle runs automatically before the skill's main workflow begins. This lifecycle has four phases: Discovery, Creation (if needed), Refresh (if stale), and Load & Execute.

### 5.2 Standard Modes (Mode A and Mode B)

**Mode A — Full Ingestion (first use):** All context files are created from scratch. Skills prompt for SCANNABLE scans, INFERRABLE pattern confirmation, and DECLARED question batches.

**Mode B — Incremental Update:** Existing contexts are validated for freshness. Changes detected in SCANNABLE sources are presented as diffs. DECLARED contexts past threshold are re-confirmed.

### 5.3 Mode C — Migration from v1 to v2

Mode C is triggered when a project has `.ctx.md` files but no `docs/` folder with Project Brain documents. It is executed by the `project-understanding` skill.

**Detection:**
```
Check for .claude/contexts/*.ctx.md OR Project Knowledge *.ctx.md files
AND absence of docs/Contracts.md
→ If both: Mode C (Migration)
```

**Migration steps:**

| Step | Action | Output |
|------|--------|--------|
| 1 | Detect v1 ctx files, confirm with user | Confirmation gate |
| 2 | Read all existing ctx files | Content loaded |
| 3 | Scaffold full docs/ folder structure | Empty hierarchy created |
| 4 | Migrate CTX-DB → docs/Data Models.md [TMPL-002] | Populated |
| 5 | Migrate CTX-API → docs/context/api-contract.md [TMPL-002] | Populated |
| 6 | Migrate CTX-ENV → docs/context/environment-config.md [TMPL-002] | Populated |
| 7 | Migrate CTX-STD → base of docs/Contracts.md [TMPL-002] | Partially populated |
| 8 | Migrate CTX-BIZ → docs/Application Understanding.md [TMPL-002] | Populated |
| 9 | Migrate CTX-OBS entries → docs/Work Audit.md §6.2 | Observations transferred |
| 10 | Present Q22–34 for Contracts.md gaps | Contracts.md completed |
| 11 | Generate CLAUDE.md [TMPL-004A], Application Workflows.md, Features.md, Users.md | New docs generated |
| 12 | Create docs/Work Audit.md [TMPL-004C] with migration as first WRK entry | Provenance established |
| 13 | Archive v1 ctx files to docs/archives/v1-ctx-migration/ | v1 preserved for 30 days |
| 14 | Update docs/context/context-manifest.md | All docs/ indexed |
| 15 | Present migration summary; flag Contracts.md for human review | Migration complete |

**Post-migration human checklist:**
```
☐ Review docs/Contracts.md — verify and complete all sections
☐ Review CLAUDE.md — confirm "What You MUST NOT Do" list
☐ Spot-check docs/Data Models.md against actual schema
☐ Verify docs/context/api-contract.md is complete
☐ Commit docs/ folder to git
☐ After 30 days: clear docs/archives/v1-ctx-migration/ if migration confirmed correct
```

### 5.4 Phase 1: Load & Execute

Once all contexts are resolved, the skill's main workflow executes with full access to current context. The skill reads docs/ files as needed using the pointers defined in its `## Required Contexts` section.

### 5.5 Phase 2: Post-Execution Observation

After the skill completes, Claude performs a lightweight check for context update signals. Observations are logged to `docs/Work Audit.md §6.2` (not to a separate file).

---

## 6. THE PASSIVE LEARNING MECHANISM

### 6.1 What Passive Learning Is

Passive Learning is the mechanism by which Claude observes information during normal work that may indicate a context needs updating. Observations are queued for structured review rather than applied immediately.

### 6.2 How Passive Learning Works

During any interaction, Claude monitors for signals that contradict stored context: code patterns differing from Contracts.md standards, new business rules not in Application Understanding.md, tables not in Data Models.md, endpoints not in api-contract.md, explicit user corrections.

All observations are logged to `docs/Work Audit.md §6.2` (TMPL-004C Pending Observations).

### 6.3 Observation Thresholds

| Signal Type | Threshold Before Logging | Confidence |
|-------------|--------------------------|------------|
| User explicitly corrects Claude | Log immediately | HIGH |
| User mentions fact contradicting DECLARED context | Log immediately | HIGH |
| Code pattern differs from INFERRABLE context (1 occurrence) | Do not log | — |
| Code pattern differs from INFERRABLE context (3+ occurrences) | Log as pattern observation | MEDIUM |
| Source file change detected (SCANNABLE) | Log automatically | HIGH |

### 6.4 Contracts.md Observation Gate

The passive learning mechanism applies differently to `docs/Contracts.md` compared to all other context files.

**Standard context files:** Observations are logged to `docs/Work Audit.md §6.2`. The skills-updater processes them on the next review cycle.

**docs/Contracts.md:** Observations are NEVER auto-applied or silently queued. The process is:
1. During any skill run, if Claude observes a pattern that should be in Contracts.md — collect the observation.
2. At end of skill run: present the observation explicitly to the developer with proposed wording.
3. Developer decides: add it, modify the wording, or reject.
4. If approved: developer adds it manually. Claude never writes directly to Contracts.md.
5. Log the decision in docs/Work Audit.md §7 with the Work-ID.

Rationale: Contracts.md defines the architectural "taste" of the project. Auto-applying observations would allow agents to gradually shift the project's architectural standards without human oversight.

### 6.5 Passive Learning Boundaries

Claude MUST NOT silently update context files, over-log trivial observations, treat a single occurrence as evidence of a standard change, or assume a user action once replaces the established standard.

Claude SHOULD log explicit user corrections immediately, accumulate code pattern observations until threshold, present observations with evidence not just assertions, and accept "no, that was an exception" as a valid response.

---

## 7. THE SKILLS UPDATER META-SKILL

The Skills Updater is a meta-skill for maintaining context files across all installed skills. It runs a health check, processes pending observations in `docs/Work Audit.md §6.2`, refreshes stale contexts, and generates a health report. Invoke with: "run a health check on my skills", "check skill health", "refresh contexts".

In v2+, the Skills Updater reads from `docs/` instead of `.ctx.md` files. The health report covers docs/context/context-manifest.md staleness and Work Audit.md §6.2 pending observation count.

---

## 8. INTEGRATION WITH SKILL CREATOR PRO (PROMPT-001)

### 8.1 Mandatory Additions to Skill Creation Workflow

Every new skill created under this architecture must:

1. Include a `## Required Contexts` section mapping context IDs to docs/ files
2. Externalize all project-specific data to context dependencies
3. Include a Context Resolution Lifecycle as the first workflow phase
4. Provide context-templates/ with templates for each required context
5. Define passive learning signals for INFERRABLE contexts

### 8.2 Updated Skill Folder Structure

```
skill-name/
├── SKILL.md                    ← Workflow + ## Required Contexts section
├── references/                 ← Stable reference docs (NOT project-specific)
├── assets/                     ← Templates, static files
└── context-templates/          ← Templates for Project Brain documents this skill needs
```

---

## 9. INTEGRATION WITH EXISTING SKILLS

### 9.1 Retrofit Process

Existing v1 skills are retrofitted by: auditing SKILL.md for hardcoded project data → extracting to context dependencies → adding Required Contexts section → creating context-templates/ → adding Context Resolution Lifecycle instructions.

### 9.2 Priority Order

1. Skills used most frequently
2. Skills with the most project-specific data hardcoded
3. Skills used across multiple projects
4. Skills rarely used

---

## 10. PLATFORM-SPECIFIC CONSIDERATIONS

### 10.1 Claude Code (Full Capability)

Direct filesystem access to `docs/`. Skills read docs/ files using file tools. CLAUDE.md at project root auto-read by Claude Code. Archives scanned directly.

### 10.2 Claude.ai Projects (Partial Capability)

docs/ files uploaded to Project Knowledge. CLAUDE.md uploaded to Project Knowledge. Archives/ processed by user uploading files to conversation. Auto-refresh requires user to upload updated docs/ files.

### 10.3 Project Brain Storage (v2+)

For projects on v2+ of this architecture, context is stored in `docs/` in the project repository — the same location on both platforms.

| Aspect | Claude Code (v2+) | Claude.ai Projects (v2+) |
|--------|------------------|--------------------------|
| Context storage | `docs/` in project repo — skills read via file tools | `docs/` files uploaded to Project Knowledge |
| CLAUDE.md | Project root, auto-read | Upload to Project Knowledge |
| archives/ processing | project-understanding scans directly | User uploads archive files to conversation |
| Auto-refresh | File system timestamps | User-initiated with new uploads |

**Legacy note:** `.claude/contexts/` and the `.ctx.md` format remain valid for v1 skills and projects. Deprecated for new projects only.

---

## 11. CONTEXT FILE SIZE & HYGIENE RULES

### 11.1 Size Limits

| File Type | Maximum | Action if Exceeded |
|-----------|---------|-------------------|
| Individual context file | 200 lines | Split or summarize older entries |
| Observations Log (Work Audit.md §6.2) | 20 entries | Flag for immediate human review |
| Context Manifest | No hard limit | Keep concise as an index |

### 11.2 Hygiene Practices

Processed observations (validated or rejected) older than 30 days are summarized into the Work Audit.md §6.1 Validated Learnings and removed from §6.2. Evidence counts in INFERRABLE contexts should be refreshed periodically. Work Audit.md that exceeds 1,500 words rotates quarterly into `Work Audit - 2026-Q1.md` etc.

---

## 12. ANTI-PATTERNS & KNOWN RISKS

### 12.1 Anti-Patterns to Avoid

| Anti-Pattern | Why It's Wrong | Correct Approach |
|-------------|---------------|-----------------|
| Hardcoding project data in SKILL.md | Single-project lock-in, silent staleness | Externalize to context files with pointers |
| Over-logging observations | Noise drowns real signals | Apply threshold rules (Section 6.3) |
| Auto-updating without confirmation | Risk of propagating misunderstandings | Always confirm changes with the user |
| Front-loading all contexts | Wastes context window | Load only contexts required by the current skill |
| Monolithic context files | Becomes bloated and unmaintainable | Split by domain |
| Treating one-off patterns as new standards | False positive learning | Require 3+ occurrences or explicit user correction |
| **Splitting context between docs/ and .ctx.md (v2+)** | Two stores solving same problem will diverge | Choose one convention per project; migrate via Mode C |
| **Agents writing to Contracts.md without human approval** | Silently shifts architectural standards | Always surface and gate on developer approval |
| **Using archives/ as a working directory** | Breaks ingestion audit trail | Output goes to docs/; archives/ is read-only raw material |
| **Skipping Work Audit.md entries** | Breaks provenance trail | Every skill run that modifies docs/ must append a WRK-ID entry |

### 12.2 Known Risks and Mitigations

Risk: Context files conflict with each other. Mitigation: Skills Updater performs cross-context consistency checks.

Risk: User confirms stale data without reading. Mitigation: For DECLARED contexts past 60 days, ask specific questions rather than blanket confirmation.

Risk: Observation accumulation across conversations. Mitigation: Use docs/Work Audit.md §6.2 as the persistent observation store; it is committed to git.

Risk: Context file grows beyond size limits. Mitigation: Enforce size limits; summarize and archive when approaching limit.

---

## 13. QUALITY CHECKLIST

### 13.1 Before Finalizing Any New Skill

**Context Architecture Checks:**
- [ ] Skill contains `## Required Contexts` section with all dependencies declared
- [ ] Each context dependency maps to a docs/ file (v2+) with TMPL type specified
- [ ] No project-specific data appears anywhere in the SKILL.md kernel
- [ ] All workflow steps use context pointers (`Read from docs/Contracts.md §N`) instead of inline data
- [ ] Context templates exist in `context-templates/` for each required context
- [ ] Context creation questions are specific, batched, and include examples
- [ ] Passive learning signals are identified for INFERRABLE contexts

**Project Brain Compliance (v2+ projects):**
- [ ] All docs/ files have a TMPL type assignment (see §14)
- [ ] CLAUDE.md exists at project root (TMPL-004A)
- [ ] docs/Contracts.md exists and has been reviewed by the developer
- [ ] docs/Work Audit.md exists and follows TMPL-004C structure
- [ ] docs/archives/INGESTION-LOG.md exists and all archive files are logged
- [ ] No context split: project uses docs/ only (no active .ctx.md files alongside docs/)
- [ ] All Work-IDs are cross-referenced in modified file revision histories
- [ ] Contracts.md has never been auto-updated by an agent without developer approval

**Documentation Standards Checks (per RULES-001):**
- [ ] Document follows naming convention
- [ ] Parent document is referenced
- [ ] All claims are sourced where applicable
- [ ] Revision history is present and current

### 13.2 Before Running the Skills Updater

- [ ] All installed skills have `## Required Contexts` sections
- [ ] Context manifest exists and is current
- [ ] docs/Work Audit.md §6.2 (pending observations) has fewer than 20 entries
- [ ] User has time to review and approve changes

---

## 14. PROJECT BRAIN LAYER — docs/ as Authoritative Context Store

### 14.1 What the Project Brain Is

The Project Brain is the project-level documentation layer that serves as the single authoritative source of truth for both human developers and AI agents. It is a folder of structured markdown files committed to the project repository under `docs/`. It replaces the `.ctx.md` file store introduced in v1.

The Project Brain does two things that `.ctx.md` files could not:
1. It is committed to git — versioned, shared, and diffable alongside source code.
2. It is human-readable in a form that serves the project team, not just Claude.

### 14.2 Document Template Assignments

Every document in the Project Brain is assigned a template from the ai-documentation skill.

| Document | Template | Write Access | Update Frequency |
|----------|----------|--------------|-----------------|
| CLAUDE.md | TMPL-004A — Agent Context Brief | Human only | Low (months) |
| docs/Application Understanding.md | TMPL-002 — Technical Reference | Auto-updated by skills | Medium |
| docs/Application Workflows.md | TMPL-003 — Procedure & Workflow | Human-updated | Low |
| docs/Application Features.md | TMPL-002 | Auto-updated by skills | Medium |
| docs/Contracts.md | TMPL-002 + TMPL-005/decision | Human only — never agent | Change-controlled |
| docs/Work Audit.md | TMPL-004C — Living Context | Agent append (§3.2, §6, §7 only) | Every skill run |
| docs/Data Models.md | TMPL-002 | Auto-updated by skills | Medium |
| docs/Users.md | TMPL-002 | Human-updated | Low |
| docs/context/api-contract.md | TMPL-002 | Auto-updated by skills | Medium |
| docs/context/environment-config.md | TMPL-002 | Human-updated | Low |
| docs/context/context-manifest.md | TMPL-002 | Auto-updated by skills | Every skill run |
| docs/features/<n>/Feature Understanding.md | TMPL-002 | Auto-updated | Per feature |
| docs/features/<n>/Feature Work Audit.md | TMPL-004C | Agent append | Every relevant skill run |
| docs/features/<n>/Tests Scenarios.md | TMPL-003 | Auto-updated | Per feature |
| docs/features/<n>/Test Data Setup.md | TMPL-003 | Auto-updated | Per feature |
| docs/archives/INGESTION-LOG.md | TMPL-002 | Skills (append) + Human | Per ingestion |

### 14.3 Minimum ai-documentation Compliance

Every Project Brain file must include:
```
1. Extended frontmatter — document_id, title, version, status,
   parent_document, template_version_used (at minimum)
2. AI Summary block — immediately after frontmatter (🤖 AI Summary)
3. [TYPE: ...] tags — on every ## section
4. Revision History — at document foot; every row includes the Work-ID
5. Cross-references — [RELATIONSHIP] → [Document, Section] format
```

### 14.4 Work Audit.md as TMPL-004C Living Context

`docs/Work Audit.md` follows the full TMPL-004C (Living Context Document) structure:

- **§2 Stable Knowledge** — project facts (human-authored)
- **§3.1 Current Phase** — milestones (human-authored)
- **§3.2 Recent Changes** — agent-writable, reverse-chrono, the "last 5 entries" that skills scan
- **§4 Behavioral Configuration** — project-wide agent constraints
- **§5 Context Dependencies** — all docs/ files + staleness dates
- **§6.1 Validated Learnings** — human-confirmed observations
- **§6.2 Pending Observations** — replaces pending-observations.ctx.md (max 20 before human review)
- **§7 Session Log** — one row per skill run, Work-ID in every row

Agent write rules: agents append to §3.2, §6.2, and §7 ONLY. All other sections are human-authored.

### 14.5 Contracts.md as Architectural Constitution

`docs/Contracts.md` is a TMPL-002 (Technical Reference) that indexes architectural decisions. Each significant decision follows the TMPL-005 (Decision Record) structure inline:

```markdown
## ADR-001 — [Decision Name]
[TYPE: DECISION]
**Decision:** [What was decided]
**Rationale:** [Why]
**Rule:** [The specific constraint for agents]
**Anti-pattern:** [What must NOT be done]
**Applies to:** [Scope]
```

**Amendment policy:** Contracts.md is never auto-updated by agents. Agents propose additions via `docs/Work Audit.md §6.2`. The developer reviews and manually applies approved changes. Every amendment adds a row to Contracts.md's revision history with the Work-ID.

### 14.6 CLAUDE.md as Agent Entry Point

`CLAUDE.md` sits at the project root (not in docs/). It is the first file any agent reads. It follows TMPL-004A (Agent Context Brief) and contains: project overview, where to find things, what the agent must never do, how to log work, and when to stop and ask.

### 14.7 Feature-Level Context

Each feature gets its own subfolder under `docs/features/`. Created by `implementation-planner` when a new feature is first planned. Feature Work Audit.md mirrors Work Audit.md in structure but is feature-scoped. The same Work-ID appears in both the project-level Work Audit.md and the Feature Work Audit.md for feature-scoped work.

---

## 15. ARCHIVES/ — RAW KNOWLEDGE INGESTION REPOSITORY

### 15.1 Purpose

The `docs/archives/` folder is the project's immutable raw knowledge store. Every source document that feeds the Project Brain originates here: call transcripts, Confluence exports, requirement specs, API samples, vendor documentation.

- `docs/archives/` = source materials (never modified after placement)
- `docs/` = structured knowledge derived from source materials

### 15.2 Folder Structure

```
docs/archives/
├── INGESTION-LOG.md               ← Central tracking: what was processed, Work-ID, docs updated
├── confluence-pages/
├── transcripts/
├── project-docs/
├── emails/
├── design-docs/
├── vendor-docs/
├── onboarding/
└── samples/
    ├── payloads/
    ├── wsdls/
    ├── postman-collections/
    └── schemas/
```

### 15.3 File Naming Convention

**Format:** `[YYYY-MM-DD]_[source-type]_[description].[ext]`

| Source-type | Applies to |
|-------------|------------|
| `confluence` | Confluence page or space export |
| `transcript` | Meeting, call, or standup transcript |
| `email` | Email thread or key email |
| `spec` | Requirements spec, PRD, BRD, SOW |
| `design` | Architecture or design document |
| `vendor` | Vendor or third-party documentation |
| `handover` | Team handover or prior runbook |
| `payload` | API request/response sample |
| `wsdl` | WSDL or SOAP contract |
| `postman` | Postman/Insomnia collection |
| `schema` | JSON Schema, XSD, or OpenAPI original |

Date = when the document was received or created (not the ingestion date).

### 15.4 INGESTION-LOG.md Schema

The INGESTION-LOG.md [TMPL-002] tracks every archive file with columns: Work-ID, Received Date, Archive File, Source Type, Processed By, Docs Updated, Status.

Status codes:
- ✅ PROCESSED — ingested; docs/ updated; Work-ID recorded
- ⏳ UNPROCESSED — in archives/ but not yet processed
- ⚠️ PARTIAL — partially ingested; gaps noted in Work Audit.md
- 🗄️ SUPERSEDED — newer version exists; retained for history

### 15.5 Governance Rules

| Rule | Detail |
|------|--------|
| Who adds files | Human only. Skills may REQUEST but cannot write to archives/ |
| Who processes | project-understanding (scans INGESTION-LOG.md for ⏳ UNPROCESSED entries) |
| Immutability | Files in archives/ are never modified after placement |
| Never delete | Archive files are permanent historical records |
| v1-ctx-migration/ | Created automatically by Mode C; may be cleared after 30 days post-migration |
| Skills interaction | project-understanding: READ + LOG. code-analyzer: READ from samples/ (optional). All other skills: no archives/ access. |

---

## SOURCES & REFERENCES

### Anthropic Official Documentation

| Source | Title | Date | Relevance |
|--------|-------|------|-----------|
| Anthropic Engineering | Equipping Agents for the Real World with Agent Skills | Oct 2025 | Core skill architecture, progressive disclosure model |
| Anthropic | Agent Skills Documentation | Current | Skill format specification, YAML frontmatter, folder structure |
| Anthropic | Claude Code Memory Documentation | Current | CLAUDE.md, auto-memory, context persistence |
| Anthropic / agentskills.io | Agent Skills Open Standard | Dec 2025 | Cross-platform skill portability standard |

### Industry Practices & Community Research

| Source | Title | Date | Relevance |
|--------|-------|------|-----------|
| MindStudio | How to Build a Self-Learning Claude Code Skill with a Learnings.md File | Mar 2026 | Self-updating context pattern validation |
| Matthew Groff | Implementing CLAUDE.md and Agent Skills in Your Repository | 2026 | Living documentation as context, PR feedback loops |

### Project Documents

| Reference | Document | Section | Topic |
|-----------|----------|---------|-------|
| REF-001 | RULES-001 Documentation Standards | All | Document format, naming, revision tracking |
| REF-002 | Truth & Verification Standards | Sections 1-4 | Source verification, claim labelling |
| REF-003 | PROMPT-001 Skill Creator Pro | Phases 2-6 | Skill creation workflow integration |
| REF-004 | ai-documentation skill | TMPL-002, TMPL-003, TMPL-004A/C, TMPL-005 | Project Brain document templates |

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-03-28 | Initial | Document created | Establishing the Living Context Framework for skill architecture |
| 2.0 | 2026-04-05 | Major | Added §14 Project Brain Layer, §15 archives/ repository. Added Mode C migration (§5.3). Added CTX-CONTRACTS, CTX-AUDIT, CTX-FEATURE types (§3.3). Deprecated pending-observations.ctx.md — observations now in Work Audit.md §6.2. Updated §1.2 design principles (4 new principles), §2.2 structural diagram, §6 passive learning Contracts gate, §10 platform notes (v2+ storage table), §12 anti-patterns (4 new), §13 quality checklist (Project Brain compliance block). | Java Backend Skill Pack v3.0.0 upgrade — AI-Native Engineering layer |

---

**END OF DOCUMENT**

*This document is the governing standard for all skill creation and maintenance. It must be referenced by the Skill Creator Pro prompt (PROMPT-001) and applied to every new and existing skill.*
