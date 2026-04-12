# AI Documentation Skill — Package Manifest
## Complete File Listing, Descriptions, and Changelog

**Package:** `ai-documentation-skill.zip`
**Package Version:** 1.1.0
**Release Date:** 2026-04-04
**Compatibility:** Claude Projects (claude.ai), Claude API with system prompt injection

---

## Package Contents

### Root Files

| File | Description | Version | Status |
|------|-------------|---------|--------|
| `SKILL.md` | Skill entry point — seven-phase workflow engine, template routing, checklist enforcement. Loaded by Claude on skill invocation. | 1.1.0 | Updated |
| `README.md` | Installation guide, quickstart, trigger phrase reference. For human setup only — not loaded during skill execution. | 1.0 | New |
| `MANIFEST.md` | This file — complete package listing and changelog. | 1.0 | New |

---

### references/ — Template and Conventions Files

All files in `references/` are loaded dynamically by the skill during execution.
The skill reads the appropriate file based on the template type selected in Phase 1.

| File | Description | Version | Status |
|------|-------------|---------|--------|
| `TMPL-000_conventions.md` | AI-optimization conventions quick reference. Loaded during every Phase 3 drafting session. Contains: frontmatter schema, AI Summary format, section type tags, confidence markers, relationship types, chunking rules, research trigger criteria, security rules, volatility definitions, and RAG supersession lifecycle. | 1.1 | Updated |
| `TMPL-001.md` | Research & Knowledge Synthesis template. Use for: industry research, best practice synthesis, technology evaluation, competitive analysis, literature reviews, standards documentation. | 1.0 | Unchanged |
| `TMPL-002.md` | Technical Reference template. Use for: API documentation, system architecture specs, database schemas, configuration references, integration contracts. Now includes Section 6.4 (Sensitive Content Rules). | 1.1 | Updated |
| `TMPL-003.md` | Procedure & Workflow template. Use for: deployment guides, testing procedures, operational runbooks, onboarding workflows, user guides, incident response. Now includes Section 1.4 (Sensitive Content Rules). | 1.1 | Updated |
| `TMPL-004.md` | AI Agent Context & Plan template. Use for: agent briefings, agentic workflow plans, living context documents, agent behavioural contracts. Note: planned for split into three distinct variants in Phase 2. | 1.0 | Unchanged |
| `TMPL-005.md` | Decision Record template. Use for: architecture decisions, technology choices, design decisions, policy decisions. Now includes updated Section 8.3 with six-step supersession process including RAG index actions. | 1.1 | Updated |

---

### context-templates/ — Context File Templates

| File | Description | Version | Status |
|------|-------------|---------|--------|
| `doc-standards.ctx.template.md` | Template for creating the `doc-standards.ctx.md` context file (CTX-DOCSTD). Used by the skill during first-use Context Setup. The generated context file stores project numbering conventions, active parents, and project-specific overrides. | 1.0 | Unchanged |

---

### examples/ — Populated Example Documents

Each example is a production-quality, fully-populated document demonstrating
one template type. These are real documents that also serve as examples —
they are not annotated teaching materials.

| File | Template | Topic | Status |
|------|----------|-------|--------|
| `EX-001_RAG-Chunking-Research.md` | TMPL-001 | RAG Chunking Strategies for Technical Documentation — 2025–2026 Research Synthesis. Contains full findings from research that motivated the Phase 1 chunking rule change. | New |
| `EX-002_AI-Doc-Skill-Technical-Reference.md` | TMPL-002 | AI Documentation Skill Technical Reference. Documents the skill itself using the skill's own template — full system architecture, workflow phases, template library, and integration points. | New |
| `EX-003_How-to-Create-Decision-Record.md` | TMPL-003 | Step-by-step procedure for creating a TMPL-005 Decision Record. Validated by execution — immediately usable by any team member. | New |
| `EX-004_CTX-DOCSTD-Agent-Context.md` | TMPL-004 | Documentation Standards Agent Context Brief. The actual living context file for this project — project identity, numbering conventions, active parents, agent behavioural contract, and Phase 1 status. | New |
| `EX-005_Decision-Chunking-Strategy.md` | TMPL-005 | Decision Record: Token-Aware Recursive Markdown-Header Chunking Adopted. Formalises the actual Phase 1 chunking decision — six options considered, full rationale, all constraints documented. | New |

---

## What Changed in v1.1.0

This release addresses all Phase 1 gaps identified in PROJ-002 Enhancement Roadmap.

### SKILL.md (v1.0.0 → v1.1.0)

| Change | Gap Addressed |
|--------|---------------|
| Removed non-standard `integrates_with` frontmatter field; moved to `metadata` block | G-01 |
| Added `allowed-tools` field per Anthropic SKILL.md spec (Read, Write, web_search) | G-01 |
| Rewrote description field to third person; verified under 1,024 characters | G-08 |
| Replaced vague TMPL-002/004 research trigger criteria with precise checklists | G-03 |
| Added research output field mapping table to Phase 2 | G-10 (partial) |
| Added `template_version_used` to Phase 3.2 frontmatter guidance | G-13 |
| Added Security checklist category (4 items) to Phase 6 | G-04 |
| Added plaintext confidence marker fallback syntax to Phase 3.4 | G-15 |
| Added Contradiction detection protocol to special cases | G-11 (partial) |
| Added Multi-step hybrid document creation guidance | B-02 (partial) |
| Added `skills-updater` to Related Skills section | A-03 (partial) |

### TMPL-000_conventions.md (v1.0 → v1.1)

| Change | Gap Addressed |
|--------|---------------|
| Section 2 — Added `template_version_used` and `known_gaps` to frontmatter schema | G-13 |
| Section 2 — Added template field deprecation policy | B-03 (partial) |
| Section 5 — Added plaintext confidence marker fallback table | G-15 |
| Section 7 — Complete rewrite: word-count rule → token-aware guidance (300–500 words target) | G-02 |
| Section 7 — Added context anchor sentence requirement and examples | G-02, G-09 |
| Section 7 — Added cross-boundary bridge pattern | G-02 |
| Section 7 — Added contextual retrieval optimisation pattern | G-09 |
| Section 7 — Added context cliff awareness note | G-04 (research context) |
| Section 8 — Replaced vague research trigger notes with precise per-template checklists | G-03 |
| Section 9 — Volatility definitions (unchanged, confirmed correct) | — |
| Section 10 — New: Sensitive Content Rules (credentials, PII, placeholder syntax) | G-04 |
| Section 11 — Renumbered from 10: Pre-Publish Checklist; added Security category and content anchor checks | G-04 |
| Section 12 — New: RAG Lifecycle for Superseded Documents (actions, grace period, source retention, cross-reference hygiene) | G-06 |

### TMPL-002.md (v1.0 → v1.1)

| Change | Gap Addressed |
|--------|---------------|
| Section 6.4 — New: Sensitive Content Rules with placeholder syntax examples | G-04 |

### TMPL-003.md (v1.0 → v1.1)

| Change | Gap Addressed |
|--------|---------------|
| Section 1.4 — New: Sensitive Content Rules with bash command placeholder examples | G-04 |
| Section 1.5 — Renumbered from 1.4: System State Prerequisites (unchanged) | — |

### TMPL-005.md (v1.0 → v1.1)

| Change | Gap Addressed |
|--------|---------------|
| Section 8.3 — Expanded supersession process from 5 bullets to 6 explicit steps with RAG index actions | G-06 |

### New Files

| File | Purpose | Gap Addressed |
|------|---------|---------------|
| `README.md` | Installation guide, prerequisites, quickstart, trigger phrases | A-01 |
| `MANIFEST.md` | Complete file listing, change log, install instructions | A-01 |
| `examples/EX-001` through `EX-005` | One production-quality example per template type | G-05 |

---

## Installation Verification Checklist

After installing the package in a Claude project, verify:

```
[ ] SKILL.md is present and readable at project root or in project knowledge
[ ] references/ directory contains all 6 files (TMPL-000 through TMPL-005)
[ ] context-templates/ directory contains doc-standards.ctx.template.md
[ ] examples/ directory contains EX-001 through EX-005
[ ] Prerequisites installed: research-orchestrator, verification
[ ] Skill activates on "write a doc" or "document this"
[ ] Context setup runs on first invocation (if doc-standards.ctx.md absent)
```

---

## Dependency Map

```
ai-documentation v1.1.0
    ├── research-orchestrator (external — must be installed separately)
    ├── verification (external — must be installed separately)
    ├── skills-updater (optional — recommended for ongoing maintenance)
    │
    ├── references/TMPL-000_conventions.md v1.1 (internal — always loaded)
    ├── references/TMPL-001.md through TMPL-005.md (internal — loaded by type)
    ├── context-templates/doc-standards.ctx.template.md (internal — first use only)
    └── examples/EX-001 through EX-005 (internal — on user request)
```

---

## Known Limitations — v1.1.0

| Limitation | Phase | Work Package |
|-----------|-------|-------------|
| TMPL-004 serves three structurally different use cases in one template — can cause confusion | Phase 2 | WP-2.1 |
| CTX-DOCSTD context file captures only basic project state — expanded fields planned | Phase 2 | WP-2.4 |
| No TMPL types for Session Records, AI Model Cards, or Incident Post-Mortems | Phase 3 | WP-3.1, 3.2, 3.3 |
| No automated test suite for skill validation | Phase 4 | WP-4.1 |
| Token budget per template type not yet empirically measured | Phase 4 | WP-4.4 |

---

*Manifest generated: 2026-04-04 | ai-documentation skill v1.1.0*

---

## What Changed in v1.2.0 (Phase 2)

### SKILL.md (v1.1.0 → v1.2.0)

| Change | Gap Addressed |
|--------|---------------|
| Decision tree: TMPL-004 entry replaced with three-variant routing (004A/B/C) | WP-2.1 |
| Reference files table: added TMPL-004A, 004B, 004C entries | WP-2.1 |

### TMPL-000_conventions.md (v1.1 → v1.2)

| Change | Gap Addressed |
|--------|---------------|
| Section 13 — New: Negative Space Conventions (12 anti-patterns with correct alternatives) | WP-2.7 |
| Section 14 — New: Session Handoff Pattern (end-of-session format, start-of-session resume, clean start) | C-01 |
| Section 15 — New: Context Window Management (signals, section-by-section strategy, cross-section reference placeholders) | C-02 |

### TMPL-004.md

| Change | Gap Addressed |
|--------|---------------|
| Converted from a template to a routing index — directs users to TMPL-004A/B/C | WP-2.1 |

### context-templates/doc-standards.ctx.template.md (v1.0 → v1.2)

| Change | Gap Addressed |
|--------|---------------|
| Expanded from 4 questions to 11 structured sections | WP-2.4, C-03 |
| Section 4 — RAG & Retrieval Configuration (6 new fields) | WP-2.4 |
| Section 5 — LLM Target & Agent Configuration (5 new fields) | WP-2.4 |
| Section 6 — Regulatory & Compliance Context (5 new fields) | WP-2.4 |
| Section 9 — Session Handoff Log | C-01 |
| Section 10 — Pending Observations (for skills-updater) | A-03 |

### New Files — Phase 2

| File | Purpose | Gap Addressed |
|------|---------|---------------|
| `references/TMPL-004A.md` | Agent Context Brief template — agent identity, constraints, tools, success criteria | WP-2.1, D-01 |
| `references/TMPL-004B.md` | Agentic Workflow Plan template — phases, gates, stop conditions, output spec | WP-2.1, D-01 |
| `references/TMPL-004C.md` | Living Context Document template — stable knowledge + volatile state + agent-writable observations | WP-2.1 |
| `references/ARCH-001-TMPL004-Reconciliation.md` | Reference: when to use ARCH-001 .ctx.md vs TMPL-004 family | WP-2.6 |
| `references/AI-Readiness-Test-Protocol.md` | 100-point scored rubric across 6 dimensions with remediation priority guide | WP-2.8 |

### Phase 2 Items Carried From Phase 1 (Already Done)

| Item | Where Implemented |
|------|------------------|
| WP-2.2 Research-document handoff protocol | SKILL.md Phase 2 — research output mapping table |
| WP-2.3 Contradiction resolution protocol | SKILL.md special cases; TMPL-000 (implicit in supersession) |
| WP-2.5 Template version tracking | TMPL-000 Section 2, all template frontmatter schemas |
| B-02 Multi-step hybrid document guidance | SKILL.md — "Multi-Step Hybrid Document Creation" section |

---

## Known Limitations — v1.2.0

| Limitation | Phase | Work Package |
|-----------|-------|-------------|
| No example documents for TMPL-004A, 004B, 004C | Phase 3+ | Planned |
| TMPL-007 (AI Model Cards) not yet created | Phase 3 | WP-3.2 |
| TMPL-006 (Session Records) not yet created | Phase 3 | WP-3.1 |
| TMPL-008 (Incident Post-Mortem) not yet created | Phase 3 | WP-3.3 |
| No automated test suite | Phase 4 | WP-4.1 |

---

## What Changed in v1.3.0 (Phase 3)

### SKILL.md (v1.2.0 → v1.3.0)

| Change | Gap Addressed |
|--------|---------------|
| Decision tree: added TMPL-006, 007, 008 entries | WP-3.1, 3.2, 3.3 |
| Reference files table: added all Phase 3 new files | WP-3.1–3.8 |

### TMPL-000_conventions.md (v1.2 → v1.3)

| Change | Gap Addressed |
|--------|---------------|
| Section 16 — New: Expanded Relationship Type Vocabulary (12 types, SKOS/DC mapping, selection guide) | WP-3.6 |
| Section 17 — New: Hybrid Search Optimization (BM25 authoring rules, semantic authoring rules, chunk metadata, embedding model guidance) | WP-3.8 |

### New Files — Phase 3

| File | Purpose | Gap Addressed |
|------|---------|---------------|
| `references/TMPL-006.md` | Session & Meeting Record template — agenda outcomes, decisions, action items, parking lot, next steps | WP-3.1 |
| `references/TMPL-007.md` | AI Model & System Card template — regulatory-aligned (EU AI Act Article 11, ISO/IEC 42001, NIST AI RMF, Mitchell et al. model cards) | WP-3.2 |
| `references/TMPL-008.md` | Incident Post-Mortem template — blameless culture, five-whys, severity matrix, corrective action tracking, 30-day review | WP-3.3 |
| `references/TMPL-002-domain-variants.md` | Domain variant additions for TMPL-002: Variant A (microservice — service mesh, API contract, SLA), Variant B (data pipeline — lineage, quality rules, PII, partitioning), Variant C (ML model — inference API, model monitoring, retraining triggers) | WP-3.4 |
| `references/TMPL-003-variants.md` | Procedure variant additions for TMPL-003: Variant A (deployment — GO/NO-GO gate, canary stages, rollback criteria), Variant B (incident response — severity matrix, comms templates, escalation tree), Variant C (database migration — safety checklist, zero-downtime pattern, post-migration validation queries) | WP-3.5 |
| `references/OPS-001-Quarterly-Research-Refresh.md` | Operational runbook for quarterly research refresh — standard query set, diff analysis, update decision framework, propagation tracking, refresh log | WP-3.7 |

### Unchanged Files — Phase 3

TMPL-001, TMPL-002, TMPL-003, TMPL-004 (router), TMPL-004A/B/C, TMPL-005, ARCH-001-TMPL004-Reconciliation, AI-Readiness-Test-Protocol, all EX-00X examples — unchanged from v1.2.0.

---

## Known Limitations — v1.3.0

| Limitation | Phase | Work Package |
|-----------|-------|-------------|
| No example documents for TMPL-006, 007, 008 | Phase 3 residual | Create alongside first real use |
| No example documents for TMPL-004A, 004B, 004C | Phase 2 residual | Create alongside first real use |
| No automated test suite | Phase 4 | WP-4.1 |
| Token budget table not yet empirically measured | Phase 4 | WP-4.4 |
| Skills updater integration not formally wired | Phase 4 | WP-4.2 |
| Documentation health score not yet built | Phase 4 | WP-4.3 |

---

## What Changed in v1.4.0 (Phase 4)

### SKILL.md (v1.3.0 → v1.4.0)

| Change | Gap Addressed |
|--------|---------------|
| Reference files table: added all Phase 4 new files | WP-4.1, 4.2, 4.3, 4.4 |

### New Files — Phase 4

| File | Purpose | Gap Addressed |
|------|---------|---------------|
| `tests/TEST-001_Skill-Test-Suite.md` | Complete skill test suite — 31 test cases across 6 categories (triggers, template selection, checklist compliance, research routing, frontmatter completeness, security). Pass standard for releases defined. Test results log template included. | WP-4.1, A-04 |
| `references/ARCH-002-Skills-Updater-Integration.md` | Formal integration spec between ai-documentation skill and skills-updater meta-skill — three integration points, observation format, data flow diagram, processing rules, escalation protocol, integration validation checklist | WP-4.2, A-03 |
| `references/OPS-002-Documentation-Health-Score.md` | Corpus health audit mechanism — five-dimension 100-point composite score (freshness, AI-readiness, cross-reference integrity, template currency, coverage), dashboard template, remediation priority guide, legacy document upgrade strategy | WP-4.3, E-01, E-02 |
| `references/OPS-003-Token-Budget-And-Versioning.md` | Per-template token budget estimates (flagged as pending empirical validation), context planning guide for agents (section-first loading strategy), TMPL-004C selective loading, semantic versioning strategy (MAJOR/MINOR/PATCH rules), compatibility matrix, release checklist, changelog policy | WP-4.4, A-02 |

---

## Phase 4 Known Limitations

| Limitation | Status | Notes |
|-----------|--------|-------|
| Token budgets are structure-based estimates, not empirically measured | OPEN — flag ⚠️ in OPS-003 | Validate by measuring 5+ real docs per template type |
| TEST-001 test suite has not been executed yet | OPEN | Run against installed skill before v1.4.0 production use |
| Skills-updater integration is defined but not runtime-tested | OPEN | Test integration by running an actual ai-documentation session and verifying CTX-DOCSTD §10 is updated |
| Health score baseline not yet established | OPEN | Run first audit when corpus has ≥10 documents |
