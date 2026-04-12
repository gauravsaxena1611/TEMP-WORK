# ai-documentation Skill — CHANGELOG
## Version 2.0.0 — 2026-04-12

**Document ID:** CHANGELOG-ai-doc-v2.0.0
**Parent Skill:** ai-documentation v2.0.0
**Author:** Claude Sonnet 4.6 — ai-documentation skill update execution
**Governed by:** [RULES-001 Documentation Standards] | [013 Truth, Verification & Doc Governance]

---

## REVISION HISTORY

| Version | Date | Change Type | Summary |
|---------|------|-------------|---------|
| 2.0.0 | 2026-04-12 | Major | Architecture pack TMPL-006–011, renumber TMPL-012–014, verification enforcement, methodology binding, diagram-ready gate, living-context sync |
| 1.4.0 | 2026-04-11 | Update | Added TMPL-006 Session Record, TMPL-007 AI Model Card, TMPL-008 Incident Post-Mortem |
| 1.1.0 | 2026-04-04 | Update | Phase 1 — frontmatter, chunking, research triggers |
| 1.0.0 | 2026-04-01 | Initial | Skill created |

---

## VERSION 2.0.0 — CHANGE DETAIL

### Change 1 — Renumber Existing TMPL-006/007/008

**What Changed:**
- TMPL-006 (Session & Meeting Record) → **TMPL-012**
- TMPL-007 (AI Model & System Card) → **TMPL-013**
- TMPL-008 (Incident Post-Mortem) → **TMPL-014**

**Why:**
The plan explicitly reserves TMPL-006 through TMPL-011 for the six new
architecture templates. The existing TMPL-006/007/008 (operational/governance
documents) are logically distinct from the architecture corpus. Renumbering
them to TMPL-012/013/014 groups the architecture templates together
(006–011) and the operational records together (012–014), which improves
both human navigation and AI agent template selection.

**Impact on Other Documents:**
- SKILL.md decision tree updated to reference new numbers
- Reference file table updated
- No content changes to the templates themselves — file renamed only

**Reverse Cross-Reference:**
- [PLAN-1, Gap 7: No portfolio/multi-system scaffolding] — numbering
  reserves space for all six architecture templates contiguously
- [RULES-001 Documentation Standards, Section 2.1] — numbering convention

---

### Change 2 — TMPL-006: System Context Pack (NEW)

**File Created:** `references/TMPL-006.md`

**What It Does:**
The canonical per-application architecture document. Structured YAML
front-matter with 17 sections providing all fields a downstream diagramming
agent needs: system identity, actors, external integrations, trust boundaries,
data classifications, SLAs, tech stack, 12 principles applied, CAP/PACELC
stance, failure modes, evolvability notes, stakeholder views, and a
diagram generation manifest.

**Justifying Project Documents:**

| Gap Closed | Source Document | Specific Section |
|------------|----------------|-----------------|
| No architecture-aware template | [011 Core Architectural Principles] | Sections 2–13 — 12 principles applied table |
| No CAP/PACELC documentation | [011 Core Architectural Principles] | Section 13 — Trade-off analysis framework |
| No stakeholder-view separation | [050 Stakeholder Communication] | Section 4 — Architect-as-translator model |
| No diagram-feeder sections | [020 Diagramming Frameworks] | Section 4 — Diagram taxonomy |
| No methodology binding | [030 System Design Methodology] | Section 4 — 8-phase flow |
| Verification labels under-applied | [013 Truth, Verification & Doc Governance] | Section 3.5 — 7-label system |
| No living-context sync | [040 Living Architecture & Drift Control] | Section 6 |
| No trust boundary documentation | [011 Core Architectural Principles] | Section 9 — Security-by-design |
| No Gestalt/WCAG notes | [012 Gestalt & Visual Clarity Standards] | All |

**Reverse Cross-References:**
- [011 Core Architectural Principles, Section 2–13] references TMPL-006 Section 7
- [013 Truth, Verification & Doc Governance, Section 3.5] applies to all TMPL-006 claims
- [030 System Design Methodology, Section 4] — methodology_phase field binds here
- [040 Living Architecture & Drift Control, Section 6] — living_context block
- [050 Stakeholder Communication, Section 4] — stakeholder_views field

---

### Change 3 — TMPL-007: Container & Component Inventory (NEW)

**File Created:** `references/TMPL-007.md`

**What It Does:**
Structured inventory of all C4 Level 2 containers and Level 3 components.
Feeds C4 L2 Container diagrams and C4 L3 Component diagrams directly.
containers[] and components[] provide the node/edge data structures a
diagram agent can parse without interpretation.

**Justifying Project Documents:**

| Gap Closed | Source Document | Specific Section |
|------------|----------------|-----------------|
| No diagram-feeder for containers | [021 C4 Model] | Sections 2–3 — C4 L2 and L3 definitions |
| No component detail | [011 Core Architectural Principles] | Section 4 — Abstraction layers |
| No cross-cutting concern documentation | [011 Core Architectural Principles] | Section 9 — Security-by-design |

**Reverse Cross-References:**
- [021 C4 Model, Section 2–3] — container and component definitions
- [040 Living Architecture & Drift Control] — living_context block mandatory

---

### Change 4 — TMPL-008: Data & Flow Inventory (NEW)

**File Created:** `references/TMPL-008.md`

**What It Does:**
Complete inventory of data entities (ERD feed), data stores, data flows
at Level 0 and Level 1 (DFD feed), transformations, and PII classifications.
Provides the structured data a diagram agent needs to generate DFD and ERD
diagrams without reading prose.

**Justifying Project Documents:**

| Gap Closed | Source Document | Specific Section |
|------------|----------------|-----------------|
| No DFD diagram feed | [023 Data Flow Diagrams] | Section 2–4 — DFD L0/L1 notation |
| No ERD diagram feed | [025 Sequence, ERD & Deployment Diagrams] | ERD section |
| No PII documentation | [011 Core Architectural Principles] | Section 9 — Security-by-design |
| No data classification detail | [013 Truth, Verification & Doc Governance] | Section 3 — Governance |

---

### Change 5 — TMPL-009: Sequence & Interaction Catalog (NEW)

**File Created:** `references/TMPL-009.md`

**What It Does:**
Ordered catalog of all critical business flows as structured step lists.
Each flow's steps[] YAML list is directly parseable by a diagram agent
to generate sequence diagrams and swimlane diagrams without interpretation.
Includes participant register, error flows, and SLA targets per flow.

**Justifying Project Documents:**

| Gap Closed | Source Document | Specific Section |
|------------|----------------|-----------------|
| No sequence diagram feed | [025 Sequence, ERD & Deployment Diagrams] | Sequence section |
| No swimlane diagram feed | [024 Swimlane & Workflow Diagrams] | All |
| No business flow documentation | [030 System Design Methodology] | Phase 5 — Detailed Design |

---

### Change 6 — TMPL-010: Deployment Topology Sheet (NEW)

**File Created:** `references/TMPL-010.md`

**What It Does:**
Structured mapping of all environments, networks, deployment nodes, and
container placements. The container_placements[] YAML list is the JOIN
between logical containers (TMPL-007) and physical infrastructure. Feeds
deployment diagram generation directly. Also documents CI/CD pipeline and
DR strategy.

**Justifying Project Documents:**

| Gap Closed | Source Document | Specific Section |
|------------|----------------|-----------------|
| No deployment diagram feed | [025 Sequence, ERD & Deployment Diagrams] | Deployment section |
| No infrastructure mapping | [011 Core Architectural Principles] | Section 7 — Reliability & availability |
| No DR documentation | [011 Core Architectural Principles] | Section 7 — RPO/RTO |
| No IaC drift check reference | [041 Automated Documentation & Real-Time Integrity] | IaC integration |

---

### Change 7 — TMPL-011: Portfolio Register (NEW)

**File Created:** `references/TMPL-011.md`

**What It Does:**
000-level master document for a portfolio of systems. Provides system
inventory, APM classification (TIME model), cross-system integration map,
shared services, DDD bounded contexts, portfolio technical debt summary,
and governance rules. Feeds System Landscape and Integration Map diagrams.

**Justifying Project Documents:**

| Gap Closed | Source Document | Specific Section |
|------------|----------------|-----------------|
| No portfolio/multi-system scaffolding | [033 Portfolio & Multi-System Architecture] | Sections 2–6 |
| No APM classification | [033 Portfolio & Multi-System Architecture] | Section 2 — TIME model |
| No bounded context documentation | [033 Portfolio & Multi-System Architecture] | Section 4 — DDD |
| No integration pattern documentation | [033 Portfolio & Multi-System Architecture] | Section 3 |
| No portfolio technical debt view | [033 Portfolio & Multi-System Architecture] | Section 6 |
| No governance documentation | [033 Portfolio & Multi-System Architecture] | Section 7 |

---

### Change 8 — Mandatory methodology_phase Tag (Retrofit TMPL-001–005)

**What Changed:**
All templates (TMPL-001 through TMPL-014) now require a `methodology_phase`
field in YAML front-matter. For architecture templates this is a specific
phase value. For non-architecture templates it is `N/A — not architecture corpus`.

**Why:**
Documents should declare WHERE in the design lifecycle they were produced.
This enables an agent reading multiple documents to understand their
temporal relationship and phase dependency.

**Justifying Project Documents:**
- [030 System Design Methodology, Section 4] — the 8-phase flow
- [PLAN-1, Gap 6: No methodology binding] — explicit requirement

**Reverse Cross-References:**
- [030 System Design Methodology, Section 4] → applies methodology phase tags

---

### Change 9 — Mandatory 7-Label Verification System (Retrofit TMPL-001–005)

**What Changed:**
Phase 3.3 of SKILL.md now states explicitly: "Every factual claim in the
document body MUST carry a label." Previously this was encouraged but not
mandatory. The seven labels are now defined inline in SKILL.md with a
lookup table. Architecture templates carry additional guidance distinguishing
direct system knowledge (✅) from design intent (💡).

**Why:**
The verification standard was under-applied. Documents were being produced
with unlabeled claims, undermining the corpus's epistemic reliability.

**Justifying Project Documents:**
- [013 Truth, Verification & Doc Governance, Section 3.5] — 7-label system
- [PLAN-1, Gap 5: Verification labels under-applied] — explicit requirement
- [Truth_Verification_Standards_Claude.docx] — governing verification standard

---

### Change 10 — Diagram-Ready Quality Gate (Phase 6B) (NEW)

**What Changed:**
A new Phase 6B "Diagram-Ready Quality Gate" added to SKILL.md. This gate
runs BEFORE the generating-architecture-diagrams skill is invoked. It has
one checklist per architecture template type (TMPL-006 through TMPL-011),
verifying that all mandatory YAML fields are populated and all linked
documents exist.

**Why:**
Without this gate, diagram generation fails mid-execution because required
YAML fields are missing. The gate catches gaps before the diagram skill
is invoked, saving token budget and preventing frustrating partial failures.

**Justifying Project Documents:**
- [PLAN-1, Gap 3: No diagram-feeder sections] — explicit requirement
- [020 Diagramming Frameworks] — defines what fields each diagram type needs
- [052 Common Pitfalls & Anti-Patterns] — diagrams generated from stale/incomplete data

---

### Change 11 — Living-Context Sync Block (NEW in all arch templates)

**What Changed:**
All six architecture templates (TMPL-006 through TMPL-011) include a
mandatory `living_context` YAML block with fields: `last_verified`,
`drift_check_date`, `drift_status`, `linked_commit`, and
`next_review_trigger`. SKILL.md Phase 3.6 documents this block and makes
it mandatory. TMPL-011 additionally tracks `context_packs_verified` and
`context_packs_stale` for portfolio-level freshness.

**Why:**
Architecture documents silently go stale without a mechanism to track
their freshness. The living-context sync block installs that mechanism,
consistent with the philosophy of [040 Living Architecture & Drift Control].

**Justifying Project Documents:**
- [040 Living Architecture & Drift Control, Section 2] — the core problem: architecture rots
- [040 Living Architecture & Drift Control, Section 6] — living-context connection
- [042 Architectural Drift Mitigation] — drift status taxonomy
- [PLAN-1, Gap 2: No living-context template] — explicit requirement

---

### Change 12 — Stakeholder View Separation (TMPL-006 Section 11)

**What Changed:**
TMPL-006 includes a `stakeholder_views` YAML section with three distinct
views: business_view (C-suite/PM audience), technical_view (engineers),
and security_view (CISO/compliance). Each view specifies its audience,
key message, and recommended diagram type. Section 11 of the template
prose enforces this separation.

**Why:**
Collapsing all views into one diagram violates the core insight of Branch
050: a diagram's value is measured by whether the RIGHT audience can read it.
A single diagram serving all audiences serves none of them well.

**Justifying Project Documents:**
- [050 Stakeholder Communication, Section 4] — architect as translator model
- [051 Business-Facing Diagramming] — audience analysis and diagram selection
- [012 Gestalt & Visual Clarity Standards] — cognitive load per audience
- [PLAN-1, Gap 4: No stakeholder-view separation] — explicit requirement

---

### Change 13 — generating-architecture-diagrams Added as Integrated Skill

**What Changed:**
SKILL.md `metadata.integrates_with` now includes `generating-architecture-diagrams`.
Phase 7 includes a notification step: "If architecture template: notify
generating-architecture-diagrams can now be invoked." Post-execution
observations include a new item for missing YAML fields that caused
diagram skill failures.

**Why:**
The architecture templates exist to feed the diagram skill. Explicitly
declaring this integration ensures the skill knows it is the upstream
producer in a producer→consumer pipeline, and that it must maintain YAML
structure integrity for the downstream consumer.

**Justifying Project Documents:**
- [PLAN-1] — "downstream diagram generation" is the explicit goal of this update
- [ARCH-002-Skills-Updater-Integration.md] — inter-skill integration patterns

---

### Change 14 — Gestalt/WCAG Notes Section (TMPL-006 Section 13)

**What Changed:**
TMPL-006 Section 13 provides a structured notes block for visual outputs:
color coding scheme, WCAG contrast compliance status, cognitive load
target, and typography specification. The section carries a `[DEPENDS_ON]`
cross-reference to [012 Gestalt & Visual Clarity Standards].

**Why:**
Architecture diagrams are visual communication artifacts. Their quality
is governed by perceptual science principles. Documenting the intended
visual standards in the context pack ensures consistency when the
diagram skill generates visuals.

**Justifying Project Documents:**
- [012 Gestalt & Visual Clarity Standards] — all sections
- [PLAN-1, Gap 8: Add Gestalt/WCAG notes section] — explicit requirement

---

## FILES ADDED

| File | Description |
|------|-------------|
| `references/TMPL-006.md` | System Context Pack (architecture) |
| `references/TMPL-007.md` | Container & Component Inventory (architecture) |
| `references/TMPL-008.md` | Data & Flow Inventory (architecture) |
| `references/TMPL-009.md` | Sequence & Interaction Catalog (architecture) |
| `references/TMPL-010.md` | Deployment Topology Sheet (architecture) |
| `references/TMPL-011.md` | Portfolio Register (architecture) |
| `CHANGELOG.md` | This document |

## FILES RENAMED

| Old Name | New Name | Reason |
|----------|----------|--------|
| `references/TMPL-006.md` | `references/TMPL-012.md` | Freed slot for System Context Pack |
| `references/TMPL-007.md` | `references/TMPL-013.md` | Freed slot for Container Inventory |
| `references/TMPL-008.md` | `references/TMPL-014.md` | Freed slot for Data & Flow Inventory |

## FILES MODIFIED

| File | What Changed |
|------|-------------|
| `SKILL.md` | Full rewrite to v2.0.0 — see changes above |

## FILES UNCHANGED

All existing reference files (TMPL-000 through TMPL-005, TMPL-004A/B/C,
domain-variants, OPS files, ARCH files, tests, examples) are unchanged
in content. TMPL-001 through TMPL-005 are not modified in this release —
they receive the `methodology_phase` retrofit through SKILL.md authoring
guidance (Phase 3.2), not through template file edits. Template file
retrofits are deferred to a minor release (v2.1.0) to avoid breaking
existing documents authored with those templates.

---

## KNOWN GAPS — DEFERRED TO v2.1.0

| Gap | Reason Deferred |
|-----|----------------|
| TMPL-001–005 template files not updated with methodology_phase field | Breaking change risk; SKILL.md authoring guidance covers it for new docs |
| No example documents for TMPL-006–011 | Requires a real system to document; deferred to first real use |
| TEST-001 test suite not updated for TMPL-006–011 | Deferred; tests need real validation scenarios |
| MANIFEST.md not updated | Deferred; MANIFEST update is a minor change |

---

## VERIFICATION STATUS

All claims in this changelog are:
- ✅ Changes 1–14: verified against SKILL.md diff and template files
- ✅ Project document references: verified by direct file read on 2026-04-12
- 💡 Impact assessments: logical inference from content structure

---

*CHANGELOG for ai-documentation skill v2.0.0. Cross-references verified. All 14 changes traced to project documents that justified them.*
