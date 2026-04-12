---
name: ai-documentation
description: >
  Produces AI-optimized, verified, standards-compliant documentation for
  any topic or system. Fourteen templates: research synthesis (TMPL-001),
  technical reference (TMPL-002), procedure runbook (TMPL-003), agent
  context (TMPL-004 family), decision record (TMPL-005), architecture pack
  (TMPL-006 System Context, TMPL-007 Container Inventory, TMPL-008 Data
  & Flow, TMPL-009 Sequences, TMPL-010 Deployment, TMPL-011 Portfolio)
  feeding generating-architecture-diagrams, session records (TMPL-012),
  AI model cards (TMPL-013), incident post-mortems (TMPL-014). Integrates
  research-orchestrator and verification skills. Triggers on: write a doc,
  document this architecture, system context pack, container inventory, data
  flow inventory, sequence catalog, deployment topology, portfolio register,
  integration map, I need a runbook, create a decision record, research
  synthesis, create an agent context, technical reference, write up what we
  decided, update existing document, audit this document, verify document
  standards.
version: 2.0.0
allowed-tools:
  - Read
  - Write
  - web_search
metadata:
  author: Project Team
  integrates_with:
    - research-orchestrator
    - verification
    - generating-architecture-diagrams
  parent_document: PROJ-001 AI-Optimized Documentation Protocol
  changelog:
    - version: "2.0.0"
      date: "2026-04-12"
      changes: >
        Major update. Added TMPL-006 System Context Pack, TMPL-007 Container
        & Component Inventory, TMPL-008 Data & Flow Inventory, TMPL-009
        Sequence & Interaction Catalog, TMPL-010 Deployment Topology Sheet,
        TMPL-011 Portfolio Register. Renumbered prior TMPL-006/007/008 to
        TMPL-012/013/014. Retrofitted TMPL-001 to 005 with methodology_phase
        tag and mandatory 7-label verification enforcement. Added Diagram-Ready
        Quality Gate. Added living-context sync block to all architecture
        templates. Added stakeholder-view separation per 050. Added 12-principles
        and CAP/PACELC fields per 011. Added generating-architecture-diagrams
        as integrated skill. Full justification in CHANGELOG.md.
    - version: "1.4.0"
      date: "2026-04-11"
      changes: "Added TMPL-006 Session Record, TMPL-007 AI Model Card, TMPL-008 Incident Post-Mortem"
    - version: "1.1.0"
      date: "2026-04-04"
      changes: "Phase 1 fixes"
    - version: "1.0.0"
      date: "2026-04-01"
      changes: "Initial release"
---

# AI Documentation Skill

> Produces AI-optimized, verified, standards-compliant documentation —
> human-readable AND machine-parseable, with full architecture corpus
> support feeding the generating-architecture-diagrams skill.

---

## Required Contexts

| Context ID | File | Type | Purpose | If Missing |
|------------|------|------|---------|------------|
| CTX-DOCSTD | doc-standards.ctx.md | DECLARED | Project numbering, active parent docs, project conventions | Run Context Setup below |

---

## Context Resolution

1. Check if `doc-standards.ctx.md` exists in project knowledge
2. If **MISSING** → Run "Context Setup" below
3. If **EXISTS** → Quick-confirm numbering is still accurate
4. If **STALE** (30+ days) → Show current values, ask user to confirm

---

## Context Setup (First Use)

Ask these questions in a single batch when `doc-standards.ctx.md` does not exist:

```
Setting up documentation standards context. Please answer:

1. NUMBERING: What prefix does your project use?
   (e.g., "000-series masters, 010-series children" per RULES-001)

2. PARENT DOCS: What master/parent document does this belong under?
   (document ID or "none / standalone")

3. PROJECT NAME: What project or system is being documented?

4. ARCHITECTURE CORPUS: Does this doc feed generating-architecture-diagrams?
   (yes / no — affects template selection and quality gate)

5. EXISTING CONVENTIONS: Any project-specific conventions beyond RULES-001?
   (or "none — follow RULES-001 + AI-optimization layer")
```

Save to `doc-standards.ctx.md` via `context-templates/doc-standards.ctx.template.md`.

---

## Phase 0: Capture Intent

Ask (or infer from context):

1. **What is being documented?**
2. **Who will use this?** (human / AI agent / diagram skill)
3. **Is there existing content to build from?**
4. **Specific format required?**
5. **Will this feed the diagram skill?** → prefer TMPL-006 through TMPL-011

If all five are clear — proceed without asking.

---

## Phase 1: Template Selection

Run the decision tree. Announce selection and confirm before loading template.

```
DECISION TREE — What is the PRIMARY purpose of this document?

╔══════════════════════════════════════════════════════════════╗
║           ARCHITECTURE DOCUMENTS (TMPL-006–011)             ║
║        Primary feed for generating-architecture-diagrams     ║
╚══════════════════════════════════════════════════════════════╝

├── Canonical per-application architecture reference
│   (business purpose, actors, integrations, 12 principles,
│    CAP/PACELC, trust boundaries, SLAs, failure modes)
│   → TMPL-006: System Context Pack
│   → Load: references/TMPL-006.md
│   → AUTHOR FIRST — prerequisite for TMPL-007 through 010

├── C4 L2 container + L3 component inventory
│   (all deployable units, tech, ports, dependencies, owners)
│   → TMPL-007: Container & Component Inventory
│   → Load: references/TMPL-007.md
│   → REQUIRES TMPL-006 first

├── Data entities, stores, flows (DFD + ERD feed)
│   (entity model, data stores, flows L0/L1, PII classification)
│   → TMPL-008: Data & Flow Inventory
│   → Load: references/TMPL-008.md
│   → REQUIRES TMPL-006 and TMPL-007 first

├── Business flows as ordered interaction sequences
│   (sequence diagrams, swimlane diagrams, step-by-step)
│   → TMPL-009: Sequence & Interaction Catalog
│   → Load: references/TMPL-009.md
│   → REQUIRES TMPL-006 and TMPL-007 first

├── Infrastructure environments, nodes, container placements
│   (deployment diagrams, CI/CD pipeline, DR strategy)
│   → TMPL-010: Deployment Topology Sheet
│   → Load: references/TMPL-010.md
│   → REQUIRES TMPL-006 and TMPL-007 first

└── Master index for a PORTFOLIO of multiple systems
    (APM, cross-system integrations, shared services, governance)
    → TMPL-011: Portfolio Register
    → Load: references/TMPL-011.md
    → 000-level master; TMPL-006 per system are its children

╔══════════════════════════════════════════════════════════════╗
║                    GENERAL DOCUMENTS                         ║
╚══════════════════════════════════════════════════════════════╝

├── Synthesizing knowledge FROM external sources
│   → TMPL-001: Research & Knowledge Synthesis

├── Describing HOW a system/API/component WORKS
│   → TMPL-002: Technical Reference

├── Telling someone HOW TO DO something step-by-step
│   → TMPL-003: Procedure & Workflow

├── Context or instructions FOR an AI agent
│   → TMPL-004 family:
│   ├── Briefing before work starts → TMPL-004A: Agent Context Brief
│   ├── Multi-step workflow → TMPL-004B: Agentic Workflow Plan
│   └── Persistent cross-session memory → TMPL-004C: Living Context

├── Capturing a DECISION that was made
│   → TMPL-005: Decision Record

├── Recording a MEETING or working session
│   → TMPL-012: Session & Meeting Record

├── Documenting an AI MODEL or AI-enabled system
│   → TMPL-013: AI Model & System Card

└── Capturing an INCIDENT POST-MORTEM
    → TMPL-014: Incident Post-Mortem
```

**Architecture template sequencing:**
New system: TMPL-006 → 007 → 008 → 009 → 010 (in order)
Portfolio: TMPL-011 (master) + one TMPL-006 per system

**Announce selection:**
> "This looks like a [TMPL-00X: Name]. [One sentence why.]
> Shall I proceed, or does a different type fit better?"

---

## Phase 2: Pre-Authoring Research (Conditional)

| Template | Research Trigger |
|----------|-----------------|
| TMPL-001 | **ALWAYS** |
| TMPL-002 | Conditional — if normative language, performance claims, or external specs cited |
| TMPL-003 | **NEVER** — procedure is authoritative |
| TMPL-004 family | **NEVER** — agent context is direct knowledge |
| TMPL-005 | Conditional — if technology trade-offs with external best-practice implications |
| TMPL-006 | Conditional — for CAP/PACELC novel arguments or "industry standard" SLA benchmarks |
| TMPL-007 | **NEVER** — direct system knowledge |
| TMPL-008 | **NEVER** — direct system knowledge |
| TMPL-009 | **NEVER** — direct system knowledge |
| TMPL-010 | **NEVER** — direct system knowledge |
| TMPL-011 | Conditional — for APM methodology claims or integration pattern recommendations |
| TMPL-012 | **NEVER** |
| TMPL-013 | Conditional — regulatory compliance claims; benchmark comparisons |
| TMPL-014 | **NEVER** |

When triggered, invoke research-orchestrator skill. Log queries in `research_queries_used`.

---

## Phase 3: Document Authoring

### 3.1 Load and Apply the Template

1. Read the full template from `references/TMPL-00X.md`
2. Remove all `<!-- comment -->` author guidance blocks before output
3. Populate every YAML front-matter field — no blanks left as `""`
4. Write at least one context anchor sentence per `##` section

### 3.2 Mandatory Methodology Phase Tag

**Populate `methodology_phase` in YAML for every document.**
Reference: [030 System Design Methodology, Section 4]

| Phase | Tag | Typical Templates |
|-------|-----|-----------------|
| 1 — Requirements | `Phase-1-Requirements` | TMPL-006 (initial) |
| 2 — Constraints | `Phase-2-Constraints` | TMPL-006 (SLAs, CAP) |
| 3 — Capabilities | `Phase-3-Capabilities` | TMPL-011, TMPL-008 L0 |
| 4 — High-Level Design | `Phase-4-HighLevel` | TMPL-006 (full), TMPL-007 draft |
| 5 — Detailed Design | `Phase-5-DetailedDesign` | TMPL-007, 008, 009, 010 |
| 6 — Validation | `Phase-6-Validation` | TMPL-002, TMPL-009 test flows |
| 7 — Documentation | `Phase-7-Documentation` | Any template at final authoring |
| 8 — Review | `Phase-8-Review` | TMPL-005, living docs |

Non-architecture documents (TMPL-001–005, 012–014): set
`methodology_phase: "N/A — not architecture corpus"`

### 3.3 Mandatory Verification Labels

**Every factual claim in the document body MUST carry a label.**

| Label | Use When |
|-------|----------|
| ✅ | Verified — named primary source OR direct system knowledge |
| ⚠️ | Flagged — Tier 3 source or partially corroborated |
| 🚩 | Outlier — contradicts majority position; analyzed critically |
| ❌ | Removed — unverifiable; claim cut |
| ❓ | Unresolved — cannot verify yet; logged as known gap |
| 💡 | Inference — logical deduction from verified facts |
| 🗑️ | Bogus caught — unsourced stat, circular citation, viral claim |

Source tiers per [013 Truth, Verification & Doc Governance, Section 3.4]:
- Tier 1 (peer-reviewed / government / official vendor docs): ✅ freely
- Tier 2 (credible journalism, reputable bylined blogs): ✅ as support
- Tier 3 (trade pubs, un-bylined blogs): ⚠️ with corroboration
- Tier 4 (anonymous / undated / PR / circular): ❌ always excluded

Architecture templates (TMPL-006–011): most claims are direct knowledge.
Label as `✅ [SOURCE: direct — code/config/IaC]` or `💡` for design intent.

### 3.4 Section Type Tags

Tag every `##` section:
`[TYPE: CONTEXT]` `[TYPE: RESEARCH_FINDING]` `[TYPE: REFERENCE]`
`[TYPE: PROCEDURE]` `[TYPE: DECISION]` `[TYPE: EXAMPLE]`
`[TYPE: INFERENCE]` `[TYPE: SUPERSEDED]`

### 3.5 Cross-Reference Format

Format: `[RELATIONSHIP] → [Document ID, Section X.X] — [one-line reason]`

Relationship types:
`[DEPENDS_ON]` `[SUPERSEDES]` `[APPLIES]` `[CONTRADICTS]`
`[EXTENDS]` `[VALIDATED_BY]` `[IMPLEMENTS]` `[SEE_ALSO]`

### 3.6 Living-Context Sync Block (Architecture Templates Only)

For all TMPL-006 through TMPL-011, `living_context` block is MANDATORY.
Per [040 Living Architecture & Drift Control, Section 6]:

- `last_verified` — date confirmed against running system
- `drift_check_date` — date drift last assessed vs. codebase
- `drift_status` — `clean` | `minor-drift` | `significant-drift`
- `linked_commit` — Git SHA of code this document reflects
- `next_review_trigger` — date OR event condition, never blank

### 3.7 Remove Scaffolding

Remove all `<!-- comment -->` guidance blocks. No empty sections.

---

## Phase 4: Verification

Run on: external factual claims, all TMPL-001/002 docs, `[TYPE: RESEARCH_FINDING]` sections,
and TMPL-006 Section 8 (CAP/PACELC) when claims are novel.

Invoke verification skill. Populate `research_validated`, `research_validated_date`,
`confidence_overall`.

---

## Phase 5: Cross-References & Hierarchy

1. Update parent document — add this doc to parent's index
2. Check bidirectionality — reciprocal references must exist
3. Architecture templates — update TMPL-006 `diagram_manifest[]` status
4. Portfolio — update TMPL-011 context pack freshness tracker
5. TMPL-004 docs — add to `context-manifest.ctx.md`

---

## Phase 6: Pre-Publish Checklist

```
STRUCTURE
[ ] Extended frontmatter complete — no placeholder fields
[ ] template_version_used populated
[ ] methodology_phase populated (mandatory all templates)
[ ] AI Summary block present
[ ] Every ## section has [TYPE: ...] tag
[ ] Headings every 400–500 words minimum
[ ] document_id follows numbering convention
[ ] parent_document filled

CONTENT
[ ] ALL significant claims carry verification labels (✅/⚠️/💡/❓/🚩/❌/🗑️)
[ ] No floating tables or lists
[ ] Technical identifiers in backticks
[ ] Paragraphs 3–5 sentences max
[ ] Every section opens with context anchor sentence
[ ] No cross-section pronouns

SECURITY
[ ] No credentials, API keys, or tokens
[ ] No real PII — use <PLACEHOLDER>
[ ] URLs and connection strings use placeholders

REFERENCES
[ ] Cross-references include relationship types
[ ] triggers has min 3 entries
[ ] negative_triggers has min 2 entries
[ ] review_trigger set

VERIFICATION
[ ] research_validated date set or "N/A"
[ ] confidence_overall honest — not defaulted
[ ] Verification Record in TMPL-001 (mandatory)

HIERARCHY
[ ] Parent document updated
[ ] Bidirectional references confirmed
[ ] Revision History with v1.0 entry

ARCHITECTURE TEMPLATES ONLY (TMPL-006 through TMPL-011)
[ ] methodology_phase reflects actual design phase
[ ] living_context block fully populated
[ ] Diagram-Ready Quality Gate passed (Phase 6B below)
[ ] Stakeholder views separated (TMPL-006 Section 11)
[ ] Gestalt/WCAG notes if embedded visuals (TMPL-006 Section 13)
```

---

## Phase 6B: Diagram-Ready Quality Gate (Architecture Templates Only)

Run BEFORE invoking generating-architecture-diagrams skill.

```
TMPL-006 SYSTEM CONTEXT PACK
[ ] system.system_id populated
[ ] actors[] min 1 — id, name, type, trust_level
[ ] external_integrations[] min 1 — id, name, protocol, direction
[ ] trust_boundaries[] min 1
[ ] cap_pacelc.cap_choice not blank
[ ] diagram_manifest[] has C4-L1-Context entry
[ ] SLA targets sourced (not free-floating)
[ ] failure_modes[] min 3 for production systems

TMPL-007 CONTAINER & COMPONENT INVENTORY
[ ] containers[] min 1 — id, name, type, technology
[ ] container_dependencies[] covers known inter-container calls
[ ] cross_cutting.auth_mechanism not blank

TMPL-008 DATA & FLOW INVENTORY
[ ] entities[] min 1
[ ] data_stores[] min 1 with container_id from TMPL-007
[ ] flows[] covers L0 level
[ ] pii_inventory[] populated if pii: true on any entity

TMPL-009 SEQUENCE & INTERACTION CATALOG
[ ] participants[] covers actors and containers in any flow
[ ] flows[] min 1 with priority = critical or high
[ ] Every step has from_id, to_id, message, verification label
[ ] Error flows for all critical flows

TMPL-010 DEPLOYMENT TOPOLOGY SHEET
[ ] environments[] has production entry
[ ] nodes[] min 1 per environment
[ ] container_placements[] covers ALL TMPL-007 containers
[ ] DR RTO/RPO ≤ TMPL-006 SLA commitments

TMPL-011 PORTFOLIO REGISTER
[ ] systems[] min 2 entries
[ ] Every system has context_pack_ref
[ ] integrations[] covers all known cross-system integrations
[ ] apm.model populated
[ ] governance section populated
```

---

## Phase 7: Output

1. Create file: `[document-id]_[short-title].md`
2. Present summary: template, research, verification, methodology_phase,
   review_trigger, Diagram-Ready Gate status, items needing manual attention
3. If architecture template: "New TMPL-0XX created for [system].
   generating-architecture-diagrams can now be invoked."
4. Log post-execution observation

---

## Special Cases

### New System — Architecture Template Set

1. Confirm TMPL-006 does not exist yet for this system
2. Author TMPL-006 first — do not skip to any other template
3. After TMPL-006 approved → offer TMPL-007 next
4. After TMPL-007 → offer TMPL-008 and TMPL-010 in parallel
5. After TMPL-008 → offer TMPL-009
6. After all five → update TMPL-011 if portfolio register exists

### Existing System — Partial Documentation

1. Read all existing arch docs
2. Identify which TMPL-006–011 exist vs. missing
3. Check `living_context.drift_status` on existing docs
4. If `significant-drift` → flag before adding new docs
5. Author missing templates in sequence order

### Updating an Existing Document

1. Check for: frontmatter, AI Summary, section type tags,
   `methodology_phase`, `living_context` (arch docs), verification labels
2. If missing → offer "(A) targeted updates or (B) full retrofit to v2.0"
3. Update content, increment version, add revision history entry

### Retrofitting TMPL-001–005 to v2.0

1. Add `methodology_phase` (set `N/A — not architecture corpus` if applicable)
2. Audit all claims — add missing verification labels
3. Confirm `review_trigger` set
4. Confirm bidirectional cross-references
5. Do NOT add `living_context` — that is architecture templates only

### Contradiction During Research

1. Surface explicitly — name document and section contradicted
2. Present both positions with source tiers
3. Recommend: update old doc / flag old doc / defer
4. Add `[CONTRADICTS] →` in both documents
5. Do not finalize until resolved or deferred

### TMPL-005 Decision Records — Immutability

- Supersede: create NEW TMPL-005; set old to `status: Superseded`
- Never edit content of a finalized TMPL-005

---

## Reference Files

| File | Load When |
|------|-----------|
| `references/TMPL-000_conventions.md` | Always |
| `references/TMPL-001.md` | Research Synthesis |
| `references/TMPL-002.md` | Technical Reference |
| `references/TMPL-003.md` | Procedure & Workflow |
| `references/TMPL-004.md` | AI Agent — routing index |
| `references/TMPL-004A.md` | Agent Context Brief |
| `references/TMPL-004B.md` | Agentic Workflow Plan |
| `references/TMPL-004C.md` | Living Context Document |
| `references/TMPL-005.md` | Decision Record |
| `references/TMPL-006.md` | **System Context Pack** |
| `references/TMPL-007.md` | **Container & Component Inventory** |
| `references/TMPL-008.md` | **Data & Flow Inventory** |
| `references/TMPL-009.md` | **Sequence & Interaction Catalog** |
| `references/TMPL-010.md` | **Deployment Topology Sheet** |
| `references/TMPL-011.md` | **Portfolio Register** |
| `references/TMPL-012.md` | Session & Meeting Record |
| `references/TMPL-013.md` | AI Model & System Card |
| `references/TMPL-014.md` | Incident Post-Mortem |
| `references/TMPL-002-domain-variants.md` | TMPL-002 variants |
| `references/TMPL-003-variants.md` | TMPL-003 variants |
| `references/ARCH-001-TMPL004-Reconciliation.md` | TMPL-004 routing |
| `references/AI-Readiness-Test-Protocol.md` | AI-optimization scoring |
| `references/OPS-002-Documentation-Health-Score.md` | Corpus health audit |
| `references/OPS-003-Token-Budget-And-Versioning.md` | Token budget planning |
| `references/ARCH-002-Skills-Updater-Integration.md` | Observation flow |
| `tests/TEST-001_Skill-Test-Suite.md` | Validation before release |
| `examples/` | Populated template examples |

---

## Post-Execution Observations

After every task:

1. Did user correct numbering/naming/conventions? → Log to CTX-DOCSTD
2. Did a template section prove confusing? → Note for review
3. Did research contradict an existing document? → Surface, do not silently resolve
4. Did any checklist item consistently fail? → Flag for template update
5. Did diagram skill fail due to missing YAML fields? → Note fields for TMPL-006–011 improvement

---

## Related Skills

- **research-orchestrator** — Phase 2 for TMPL-001 and conditional types
- **verification** — Phase 4 for all claim-bearing content
- **generating-architecture-diagrams** — Downstream consumer of TMPL-006–011
- **skill-development-suite** — Skill creation, then document here
- **skills-updater** — Periodic processing of pending observations
