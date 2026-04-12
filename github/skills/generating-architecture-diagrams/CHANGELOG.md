# CHANGELOG — generating-architecture-diagrams Skill
## SKILL-ARCHDIAG

**Parent:** ARCH-001 Skill Architecture & Living Context Framework
**Document:** CHANGELOG.md

---

## [5.0] — 2026-04-12 — MAJOR RELEASE

### Summary

Full alignment with the System Design Bible (000–060). Evolves the skill from "produces draw.io files" to "produces industry-grade, methodology-driven, audience-correct diagrams backed by principles, checklists, and anti-pattern guards."

### Added

#### Phase 0 — Diagram Selection (mandatory)
- Decision matrix from [020 Diagramming Frameworks, Section 5] applied before any XML generation
- Reads System Context Pack (CTX-SYSPACK / TMPL-006) for audience + intent + phase
- Produces Diagram Selection Proposal (type + justification + stakeholder variant + emitter)
- Flags mismatches between user request and matrix recommendation

#### Per-Diagram Playbooks (11 new playbooks)
- `[PLAYBOOK-C4L1]` C4 Level 1 Context — Phase 2, business executive audience
- `[PLAYBOOK-C4L2]` C4 Level 2 Container — Phase 4, architects; initializes CTX-CHAINIDS
- `[PLAYBOOK-C4L3]` C4 Level 3 Component — Phase 5, developers
- `[PLAYBOOK-C4L4]` C4 Level 4 Code — Phase 5, optional; staleness warning
- `[PLAYBOOK-DFD]` Data Flow Diagram — Gane-Sarson (default) + Yourdon-Coad; four integrity rules
- `[PLAYBOOK-SWIM]` Swimlane/BPMN 2.0 — Phase 4; BPMN notation table; error boundary events
- `[PLAYBOOK-SEQ]` Sequence Diagram — Phase 4/5; CTX-CHAINIDS lifeline reuse
- `[PLAYBOOK-ERD]` ERD Crow's Foot — Phase 5; cardinality notation table
- `[PLAYBOOK-DEPLOY]` Deployment Diagram — Phase 6; CTX-CHAINIDS artifact reuse; stateless/stateful
- `[PLAYBOOK-INTMAP]` Integration Map — Phase 7; integration pattern color coding
- `[PLAYBOOK-STATE]` State Diagram — Phase 5; UML 2.5.1 state machine notation
- `[PLAYBOOK-PORTFOLIO]` Portfolio Landscape — Phase 7/8; TIME model; Portfolio Mode

#### 12 Principle Validators
- Source: [011 Core Architectural Principles]
- Each principle produces: ✅ Pass / ⚠️ Warn / 🚫 Block
- Block results halt XML generation until resolved
- Covers: SoC, Modularity, Cohesion/Coupling, Abstraction, SOLID, Scalability, Reliability, Maintainability, Security-by-Design, Cost-Awareness, Evolvability, Trade-Off Analysis

#### Gestalt + WCAG Layout Engine
- Source: [012 Gestalt & Visual Clarity Standards, Sections 3–7 and 11.2]
- Working memory budget: ≤3–4 top-level chunks (Sweller 1988, Cowan 2001 — Tier 1 ✅)
- WCAG 2.1 contrast: 4.5:1 text; 3:1 graphical (W3C, 2018 — Tier 1 ✅)
- Spacing standards: 20/50/80px inter-group; 200/120px preferred shape gaps
- Typography standards: sans-serif; 12pt minimum component names; 9pt connector labels
- Proximity, Similarity, Continuity, Figure-Ground, Common Region rules enforced

#### Anti-Pattern Guard
- Source: [052 Common Pitfalls & Anti-Patterns]
- All 27 patterns from 052 checked at Step 3
- 4 Block patterns: A1 (Mixed Abstraction), B1 (God Diagram), B7 (Missing Trust Boundaries), F2 (No Version)
- 23 Warn patterns flagged and reported to user
- Quick-reference table with detection signal and fix for each pattern

#### Chaining Model
- Source: [020 Diagramming Frameworks, Section 8]
- CTX-CHAINIDS context file initialized at C4 L2 generation
- Container IDs reused as Sequence lifelines and Deployment artifacts
- Four chain types documented: Structure→Interaction→Infrastructure; Context→Data→Schema; Process→Interaction; Portfolio→Integration→Context

#### Stakeholder Variants
- Source: [051 Business-Facing Diagramming]
- Business Variant: plain-language labels, no tech jargon, ≤8 elements, slide-ready format, 3–4 colors
- Technical Variant: full notation, trust boundaries, CTX-CHAINIDS IDs, reference-grade format
- Audience-to-diagram mapping table (6 business personas)
- Naming convention: `[APP]-[Type]-Business-vX.Y.drawio` and `[APP]-[Type]-Technical-vX.Y.drawio`

#### Pre-Publish Visual QA Gate
- Source: [012 §11.3] + [052 §12]
- Combined 40-item checklist: Anti-Pattern Scan (19 items) + Cognitive Load (5) + Gestalt (6) + Color/Contrast (5) + Typography (4) + Alignment/Whitespace (5) + XML Structure (7) + Governance Metadata (5) + Chaining Consistency (3)
- Hard rule: any fail → revise; do not deliver

#### Methodology Phase Tagging
- Source: [030 System Design Methodology, Section 5]
- Every diagram tagged with Phase N — Name
- Embedded in draw.io metadata as `methodology-phase` attribute
- Displayed in diagram title area: `[System] — [Type] — Phase [N]: [Name] — v[X.Y]`

#### Portfolio Mode
- Source: [033 Portfolio & Multi-System Architecture]
- Activated when >3 System Context Packs provided or explicit portfolio request
- TIME model overlay support
- Max 5 domains per canvas (God Diagram guard for portfolio scope)
- Optional Structurizr DSL workspace output for interactive navigation

#### Output Emitters (optional alternatives to draw.io)
- Structurizr DSL: C4 levels, workspace format, recommended for C4 diagram sets
- PlantUML: C4 via C4-PlantUML library, Sequence, State, ERD
- Mermaid: C4 Context (limited), Sequence, ERD, State, Flowchart; limitation flagged to user

#### New Context Files
- `CTX-SYSPACK` (system-context-pack.ctx.template.md): Phase 0 input
- `CTX-CHAINIDS` (chaining-id-registry.ctx.template.md): cross-diagram entity IDs

#### New Reference Files
- `references/swimlane-spec.md`: BPMN 2.0 XML patterns, pool/lane structure, shape styles
- `references/state-spec.md`: UML state machine XML patterns, transition format

#### Governance Metadata in Diagrams
- `last-verified` date embedded in every diagram
- `methodology-phase` embedded
- `audience-variant` embedded
- `chaining-ids` reference embedded
- All via `<object>` tag with placeholders on draw.io diagram canvas

### Changed

#### Process Flow
- Expanded from 6 steps to 9 steps
- Step 0 (Phase 0 Diagram Selection) added as mandatory first step
- Step 3 (Anti-Pattern Guard) added
- Step 4 (12 Principle Validators) added
- Step 7 (Pre-Publish Visual QA Gate) formalized from informal checklist
- Step 8 renamed from "Validate and Deliver" to "Select Output Emitter and Deliver"

#### Diagram Type Router
- Expanded from 11 to 16 rows
- Added: DFD with Yourdon-Coad notation, State Diagram, Portfolio Landscape, Business Variant, Update Existing
- HLLFD now explicitly mapped to C4 L2 + Integration Map (was previously standalone type)

#### Required Contexts
- Added `CTX-SYSPACK` (system context pack)
- Added `CTX-CHAINIDS` (chaining ID registry)

#### Error Handling
- Added Anti-Pattern BLOCK error format
- Added Principle Validator BLOCK error format
- Added WCAG contrast failure error with auto-correction prompt
- Added Chaining ID conflict error with resolution action
- Added God Diagram threshold error with split suggestion

### Fixed

- L4 diagram staleness: now requires explicit user request and carries codebase-verification warning
- Trust boundary enforcement: B7 now a BLOCK-level anti-pattern (was previously a warning)
- WCAG contrast: now enforced at layout engine step, not just mentioned in guidelines

### Deprecated

- None — all v4.0 functionality preserved

---

## [4.0] — 2026-04-11 — MAJOR

XML engine overhaul. Root element changed to `<mxGraphModel adaptiveColors="auto">`. Zero XML comments policy. Edge geometry child mandated. Official jgraph/drawio-mcp sources as root of truth.

---

## [3.0] — 2026-03-29 — MAJOR

Living Context integration (ARCH-001/002). Expanded from 3 to 9 diagram types. 50+ trigger phrases. CTX-PALETTE externalized. Post-execution observation logging.

---

## [2.0] — 2026-03-27 — MAJOR

Refactored to progressive disclosure architecture. References extracted. Templates bundled.

---

## [1.0] — 2026-03-27 — INITIAL

Full skill creation with all content in single SKILL.md.
