# Token Budget Guidance & Package Versioning
## Operational Reference: Context Planning and Release Management

```yaml
---
document_id: "OPS-003 Token-Budget-And-Versioning"
title: "Token Budget Guidance & Skill Package Versioning Strategy"
version: "1.0"
created: "2026-04-04"
status: "Final"
parent_document: "PROJ-001 AI-Optimized Documentation Protocol"
template_version_used: "TMPL-002 v1.1"

intent: >
  Provide token budget estimates per template type to guide context planning
  for agents, and define the skill package versioning strategy that governs
  how the ai-documentation skill package is released, versioned, and upgraded.

consumption_context:
  - human-reading
  - ai-reasoning

triggers:
  - "token budget for documentation templates"
  - "how many tokens is a TMPL-001 document"
  - "skill package versioning"
  - "how to version the documentation skill"
  - "OPS-003"
  - "context budget planning"

negative_triggers:
  - "chunk size guidance" # → TMPL-000 §7
  - "how to release a Claude skill" # → Anthropic documentation

volatility: "fast-changing"
review_trigger: "2026-10-04 — or when empirical data from real documents significantly changes the estimates"

confidence_overall: "conditional"
confidence_note: "Token budget estimates are calibrated from template structure analysis; empirical validation from real documents is noted as pending in §2"
---
```

---

> ## 🤖 AI Summary
> **Core Purpose:** Two operational references in one document — (1) per-template token budget estimates for agent context planning, and (2) the complete semantic versioning strategy for the ai-documentation skill package.
> **Token Budget Status:** Estimates based on template structure analysis; flag as ⚠️ until empirically validated against real documents.
> **Versioning Strategy:** Semantic versioning (MAJOR.MINOR.PATCH) with defined rules for when each component increments.
> **Trust Level:** CONDITIONAL — token budgets are estimates, not measured values
> **Review By:** October 2026, or when real document measurements provide empirical data

---

## TABLE OF CONTENTS

1. [Token Budget Guidance](#1-token-budget-guidance)
2. [Per-Template Token Budget Table](#2-per-template-token-budget-table)
3. [Context Planning for Agents](#3-context-planning-for-agents)
4. [Skill Package Versioning Strategy](#4-skill-package-versioning-strategy)
5. [Release Checklist](#5-release-checklist)
6. [Version History & Changelog Policy](#6-version-history--changelog-policy)
7. [Cross-References](#7-cross-references)
8. [Revision History](#8-revision-history)

---

## 1. Token Budget Guidance
[TYPE: EXPLANATION]

### 1.1 Why Token Budgets Matter

AI agents operating in constrained context windows need to know how much
context a retrieved document will consume before deciding whether to load it
in full, load specific sections, or summarize. Without token budget estimates,
agents either load too much (overflowing the context window) or truncate
documents at arbitrary points (losing critical content).

The token budgets in Section 2 are estimates derived from template structure
analysis and typical document lengths for each type. They represent:
- **Minimum:** The smallest useful document of this type (sparse content, few sections)
- **Typical:** What a well-populated, complete document looks like
- **Maximum:** A comprehensive document with all optional sections fully populated

⚠️ `[FLAGGED — These are structure-based estimates. Empirical validation from real documents has NOT yet been completed. Update this table after measuring 5+ real documents per template type. See gap WP-4.4 in PROJ-002.]`

### 1.2 How to Update This Table

To replace estimates with empirical data:
1. Count tokens of 5+ real completed documents per template type
2. Record min, mean, and max across the sample
3. Update Section 2 with empirical values
4. Change confidence marker from ⚠️ FLAGGED to ✅ VERIFIED
5. Increment document version (1.0 → 1.1)

---

## 2. Per-Template Token Budget Table
[TYPE: REFERENCE]

⚠️ `[FLAGGED — All values below are structure-based estimates pending empirical validation]`

| Template | Type | Min Tokens | Typical Tokens | Max Tokens | Context Cliff Risk | Notes |
|----------|------|-----------|---------------|------------|-------------------|-------|
| TMPL-001 | Research Synthesis | 3,000 | 8,000–12,000 | 18,000 | HIGH above 10k | Many finding sections; grows with research depth |
| TMPL-002 | Technical Reference | 2,500 | 5,000–9,000 | 15,000 | HIGH above 8k | Grows significantly with endpoint count and schemas |
| TMPL-002 + Microservice variant | Technical Reference | 3,500 | 7,000–11,000 | 18,000 | HIGH | Additional service mesh and SLA sections |
| TMPL-003 | Procedure & Workflow | 1,500 | 3,000–5,000 | 8,000 | MEDIUM | Step count is the main size driver |
| TMPL-003 + Deployment variant | Procedure | 2,500 | 4,500–7,000 | 11,000 | HIGH | GO/NO-GO gate adds significant content |
| TMPL-003 + Migration variant | Procedure | 3,000 | 5,000–8,000 | 12,000 | HIGH | Pre-migration checklist and validation queries |
| TMPL-004A | Agent Context Brief | 1,000 | 2,500–4,000 | 6,000 | MEDIUM | Keep Section 1 under 200 tokens; full doc budget here |
| TMPL-004B | Agentic Workflow Plan | 1,500 | 3,500–6,000 | 10,000 | HIGH | Grows with phase count and gate complexity |
| TMPL-004C | Living Context | 2,000 | 4,000–8,000 | 15,000 | HIGH | Grows unbounded as observations accumulate |
| TMPL-005 | Decision Record | 800 | 2,000–3,500 | 6,000 | LOW | Bounded by the decision itself; relatively compact |
| TMPL-006 | Session & Meeting Record | 500 | 1,500–3,000 | 5,000 | LOW | Bounded by meeting length |
| TMPL-007 | AI Model & System Card | 3,000 | 7,000–12,000 | 20,000 | HIGH | Regulatory sections add significant length |
| TMPL-008 | Incident Post-Mortem | 2,000 | 4,000–7,000 | 12,000 | HIGH | Timeline and corrective actions are the size drivers |

### 2.1 Context Cliff Risk Key

| Risk Level | Meaning | Recommended Agent Behaviour |
|-----------|---------|----------------------------|
| LOW | Typical documents fit well under the ~2,500 token retrieval sweet spot | Load full document |
| MEDIUM | Typical documents approach the context cliff | Load Sections 1–2 first; load additional sections on demand |
| HIGH | Typical documents exceed the context cliff | Always section-by-section retrieval; never load full document |

---

## 3. Context Planning for Agents
[TYPE: REFERENCE]

### 3.1 Section-First Loading Strategy

For any document with HIGH context cliff risk, agents should follow this
loading sequence rather than loading the full document:

```
STEP 1: Load document frontmatter + AI Summary block only
  → Assess: is this the right document? (triggers match? intent matches?)
  → If yes, continue. If no, discard and try next candidate.

STEP 2: Load Section 1 (always first — it's the highest-priority chunk)
  → Assess: enough context to answer the query?
  → If yes, stop. If no, continue.

STEP 3: Load the specific section most likely to contain the answer
  (use section headings to select; table of contents if present)
  → Assess: enough context now?
  → If yes, stop. If no, continue.

STEP 4: Load adjacent sections if the answer requires more context
  → Use cross-boundary bridge sentences to maintain coherence.

STEP 5: Load full document only as last resort
  → Document the token count used; log if over 5,000 tokens
```

### 3.2 Token Budget Allocation for Multi-Document Tasks

When an agent must load multiple documents for a task, allocate the
available context budget across documents based on expected utility:

| Task Type | Primary Document | Supporting Documents | Budget Split |
|-----------|----------------|---------------------|-------------|
| Answer specific technical question | TMPL-002 (relevant section) | TMPL-005 (related decision) | 70% / 30% |
| Guide a procedure execution | TMPL-003 (full) | TMPL-002 (reference) | 60% / 40% |
| Generate agent context for a task | TMPL-004A (full) | TMPL-004C §2–3 (stable + current state) | 50% / 50% |
| Research synthesis | TMPL-001 (exec summary + findings) | EX documents if referenced | 80% / 20% |

### 3.3 TMPL-004C Special Handling

Living context documents (TMPL-004C) grow unbounded as observations
accumulate. For any TMPL-004C document, agents should:

1. Load Sections 1–4 (stable knowledge + behavioral config) — always
2. Load Section 3.2 (current state, agent-writable) — always
3. Load Section 6.2 (pending observations) — only if observations affect today's task
4. Skip Section 7 (session log) — unless auditing session history

This selective loading keeps the typical TMPL-004C context footprint under
3,000 tokens even as the document grows.

---

## 4. Skill Package Versioning Strategy
[TYPE: REFERENCE]

### 4.1 Semantic Versioning Schema

The ai-documentation skill package follows semantic versioning:
**MAJOR.MINOR.PATCH**

| Component | Increments When | Example |
|-----------|----------------|---------|
| **MAJOR** | Breaking changes to the authoring workflow, template structure, or frontmatter schema that require all documents to be updated | 1.x.x → 2.0.0 |
| **MINOR** | New templates added, new sections added to TMPL-000, new reference files, new variant guidance | 1.3.x → 1.4.0 |
| **PATCH** | Bug fixes, typo corrections, clarifications, non-structural updates to existing content | 1.3.0 → 1.3.1 |

### 4.2 What Constitutes a Breaking Change (MAJOR)

A MAJOR version increment is required when any of the following occur:

| Change | Why It's Breaking |
|--------|-----------------|
| A frontmatter field is renamed or removed from a TMPL | Documents using the old field become non-compliant |
| A Phase 6 checklist item is added that existing compliant documents would fail | Documents that passed v1.x now fail v2.0 |
| Template selection routing logic changes in a way that changes which template is selected for a given input | Existing workflows produce different template selections |
| A relationship type is renamed in the controlled vocabulary | Existing cross-references become invalid |
| The skill name changes | Existing installations need re-configuration |

### 4.3 Version Decision Tree

When considering a change, ask:

```
Does this change require existing published documents to be updated to remain
compliant with the new version?
  YES → MAJOR
  NO → Continue ↓

Does this change add a new capability (template, section, file)?
  YES → MINOR
  NO → Continue ↓

Is this a fix, clarification, or correction to existing content?
  YES → PATCH
  NO → Reconsider whether the change is needed
```

### 4.4 Version Compatibility Matrix

Documents are compatible with the skill version that produced them
and all PATCH versions above it. Documents from a prior MINOR version
are usable but may lack features. Documents from a prior MAJOR version
require migration.

| Document Created With | Compatible With Skill Version | Notes |
|----------------------|------------------------------|-------|
| v1.0.x | v1.0.x | Fully compatible (same major + minor) |
| v1.0.x | v1.1.x | Compatible — document lacks template_version_used field |
| v1.0.x | v1.3.x | Compatible — document lacks Phase 2–3 enhancements |
| v1.0.x | v2.0.x | **Incompatible** — migration required |
| v1.3.x | v1.3.1 | Fully compatible (same major + minor) |

### 4.5 Package Release Naming

All package releases use the pattern:

```
ai-documentation-skill-v{MAJOR}.{MINOR}.{PATCH}.zip
```

Each release includes an updated `MANIFEST.md` with:
- Full changelog for this version
- List of files added, changed, or removed
- Compatibility matrix update
- Known limitations for this version

---

## 5. Release Checklist
[TYPE: PROCEDURE]

Complete all items before publishing a new package version.

```
PRE-RELEASE VALIDATION
  [ ] All Phase 1–4 audit checks pass (0 failures)
  [ ] TEST-001 skill test suite: all 31 tests pass (0 failures)
  [ ] AI-Readiness test on all EX-00X example documents: score ≥90
  [ ] SKILL.md description character count verified: under 1,024 chars
  [ ] SKILL.md version field updated to new version number
  [ ] MANIFEST.md changelog entry written for this release
  [ ] README.md version history updated
  [ ] All template `template_version_used` fields updated where applicable

BREAKING CHANGE CHECKS (MAJOR only)
  [ ] Deprecation notice issued at least one MINOR version before removal
  [ ] Migration guide written (Section 10 of OPS-002 or equivalent)
  [ ] All existing example documents (EX-001–EX-005) updated to new version
  [ ] Compatibility matrix in OPS-003 §4.4 updated

PACKAGING
  [ ] zip file created: ai-documentation-skill-v{version}.zip
  [ ] zip contents verified: all expected files present
  [ ] zip tested: install + first-use flow runs correctly
  [ ] zip size is reasonable (flag if >500KB — indicates accidental inclusions)
```

---

## 6. Version History & Changelog Policy
[TYPE: REFERENCE]

### 6.1 Current Version History

| Version | Date | Type | Summary |
|---------|------|------|---------|
| v1.0.0 | 2026-04-01 | Initial | First release |
| v1.1.0 | 2026-04-04 | Minor | Phase 1: SKILL.md standards fix, token-aware chunking, security rules, 5 examples, RAG lifecycle, README + MANIFEST |
| v1.2.0 | 2026-04-04 | Minor | Phase 2: TMPL-004 split, CTX-DOCSTD expanded, negative space conventions, AI-readiness test, session handoff, context window guidance |
| v1.3.0 | 2026-04-04 | Minor | Phase 3: TMPL-006/007/008, domain variant guidance, relationship vocabulary, hybrid search, quarterly refresh runbook |
| v1.3.1 | 2026-04-04 | Patch | Phase 2 fix: template_version_used retrofitted to TMPL-001, 002, 003, 005 |
| v1.4.0 | 2026-04-04 | Minor | Phase 4: test suite, skills-updater integration, health score, token budgets, versioning strategy |

### 6.2 Changelog Entry Format

Each MANIFEST.md changelog entry follows this format:

```markdown
## What Changed in v{version} ({phase or reason})

### {Filename} (v{old} → v{new})

| Change | Gap Addressed |
|--------|---------------|
| [Description of change] | [WP ID or gap ID] |

### New Files — v{version}

| File | Purpose | Gap Addressed |
|------|---------|---------------|
| [filename] | [what it does] | [gap ID] |
```

---

## 7. Cross-References
[TYPE: REFERENCE]

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [IMPLEMENTS] | PROJ-002 Enhancement Roadmap | WP-4.4, A-02 | This document is the WP-4.4 and A-02 deliverable |
| [DEPENDS_ON] | TMPL-000_conventions.md | Section 7 | Chunking and context cliff guidance informs token budget estimates |
| [SEE_ALSO] | ARCH-002 Skills-Updater-Integration | Section 4.1 | Health check integration |
| [SEE_ALSO] | TEST-001 Skill Test Suite | Section 1.3 | Release requires all 31 tests to pass |
| [SEE_ALSO] | OPS-001 Quarterly Research Refresh | All | Research updates may require re-validating token budget estimates |

---

## 8. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-04 | Initial | Token budget guidance + skill package versioning strategy created | Phase 4 WP-4.4 and A-02 |

---

*Template: TMPL-002 Technical Reference v1.1 | Parent: PROJ-001 AI-Optimized Documentation Protocol*
*Token budget estimates flagged ⚠️ — empirical validation pending*
