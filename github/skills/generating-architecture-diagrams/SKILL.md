---
name: generating-architecture-diagrams
description: Generates industry-grade draw.io XML architecture diagrams — with optional Structurizr DSL, PlantUML, and Mermaid emitters. Enforces System Design Bible (000–060): 12 architectural principles, Gestalt/WCAG visual rules, 27 anti-pattern guards, chaining model, stakeholder variants (business/technical), methodology-phase tagging, and portfolio mode. Phase 0 runs a decision matrix before any XML is generated. Triggers: architecture diagram, HLLFD, C4 context/container/component/code, DFD, Gane-Sarson, Yourdon-Coad, ERD, Crow's Foot, deployment diagram, infrastructure diagram, integration map, swimlane, BPMN, state diagram, draw.io, drawio XML, upstream downstream, solution architecture, application architecture, microservices diagram, API flow, update diagram, visualize architecture, system context, technical architecture, business-facing diagram, stakeholder view, portfolio landscape.
---

# GENERATING ARCHITECTURE DIAGRAMS
## Industry-Grade, Methodology-Driven Architecture Diagram Engine — v5.0

**Skill ID:** SKILL-ARCHDIAG
**Parent:** ARCH-001 Skill Architecture & Living Context Framework
**Version:** 5.0
**Created:** 2026-03-27
**Last Updated:** 2026-04-12
**Status:** Production
**System Design Bible Alignment:** Full — all six branches (010–060) integrated

**Cross-References (Bidirectional):**

| Relationship | Target | Reason |
|---|---|---|
| [GOVERNED_BY] | [000 System Design Bible, Section 3] | Root governance corpus |
| [APPLIES] | [011 Core Architectural Principles] | 12 principles embedded as validators |
| [APPLIES] | [012 Gestalt & Visual Clarity Standards] | Layout engine, WCAG rules, QA gate |
| [APPLIES] | [020 Diagramming Frameworks, Section 5] | Decision matrix (Phase 0) |
| [APPLIES] | [020 Diagramming Frameworks, Section 8] | Chaining model |
| [APPLIES] | [021 C4 Model] | C4 L1–L4 playbooks |
| [APPLIES] | [023 Data Flow Diagrams] | DFD Gane-Sarson + Yourdon-Coad playbooks |
| [APPLIES] | [024 Swimlane & Workflow Diagrams] | Swimlane/BPMN playbook |
| [APPLIES] | [025 Sequence, ERD & Deployment Diagrams] | Sequence, ERD, Deployment playbooks |
| [APPLIES] | [030 System Design Methodology, Section 5] | Methodology-phase tagging |
| [APPLIES] | [033 Portfolio & Multi-System Architecture] | Portfolio mode |
| [APPLIES] | [040 Living Architecture & Drift Control] | Last-verified date stamp, living-context link |
| [APPLIES] | [051 Business-Facing Diagramming] | Stakeholder variants (business vs technical) |
| [APPLIES] | [052 Common Pitfalls & Anti-Patterns] | Anti-Pattern Guard (27 patterns blocked) |
| [APPLIES] | [060 Tooling & Output Standards] | Output format, emitter selection |
| [EXTENDED_BY] | [ARCH-001, Section 5] | Living Context Architecture for context files |

---

## CORE MANDATE

- Generate **valid, well-formed draw.io XML** — zero broken files, zero empty files
- Use `<mxGraphModel adaptiveColors="auto">` as root — never `<mxfile>`
- **NEVER include XML comments** (`<!-- -->`) — causes parse errors and empty files
- Every edge **must** have `<mxGeometry relative="1" as="geometry" />` — never self-closing
- **Run Phase 0 first** — apply the [020, Section 5] decision matrix before generating any diagram
- **Run Anti-Pattern Guard** — block all 27 patterns from [052] before writing XML
- **Run Pre-Publish Visual QA Gate** — enforce [012] Gestalt/WCAG rules before delivery
- Extract facts **only** from provided sources — NEVER assume or fabricate
- Mark missing information as `TBD - not specified in sources`
- Embed **last-verified date** and methodology phase in every diagram's metadata
- Output filename: `[APP_SHORT]-[DiagramType]-[Audience].drawio`

---

## PHASE 0 — DIAGRAM SELECTION (MANDATORY FIRST STEP)

> **Source:** [020 Diagramming Frameworks, Section 5] ✅ — Decision Matrix
> Run this phase before generating any diagram. Never skip.

### Step 0.1 — Read the System Context Pack

Before proposing a diagram set, check for the project's System Context Pack (TMPL-006). If present, extract:
- Primary audience(s) and their technical background
- Architectural intent (structural view? process view? data view? infrastructure view?)
- Current methodology phase (see [030, Section 5] — Phase 2 Constraints through Phase 8 Review)
- Existing diagrams in the set (to respect the chaining model)

If no TMPL-006 exists, ask the user: **"Who is the primary audience? What question does this diagram need to answer?"**

### Step 0.2 — Run the Decision Matrix

Apply [020 Diagramming Frameworks, Section 5] to select the correct diagram type:

| Primary Audience | Question to Answer | Diagram Type | Methodology Phase | Playbook |
|---|---|---|---|---|
| Business executives, product owners | What is this system and who does it interact with? | **C4 Level 1 — System Context** | Phase 2 | [PLAYBOOK-C4L1] |
| Technical leads, architects | What are the major technical building blocks? | **C4 Level 2 — Container** | Phase 4 | [PLAYBOOK-C4L2] |
| Developers (design phase) | What are the internal components of a container? | **C4 Level 3 — Component** | Phase 5 | [PLAYBOOK-C4L3] |
| Developers (implementation) | How does code map to design? | **C4 Level 4 — Code** (optional) | Phase 5 | [PLAYBOOK-C4L4] |
| Business analysts, compliance, security | Where does data come from, go to, and transform? | **DFD (Gane-Sarson or Yourdon-Coad)** | Phase 3/5 | [PLAYBOOK-DFD] |
| Business stakeholders, process owners | Who does what, and who is accountable? | **Swimlane / BPMN** | Phase 4 | [PLAYBOOK-SWIM] |
| Developers, architects | In what order do components exchange messages? | **Sequence Diagram** | Phase 4/5 | [PLAYBOOK-SEQ] |
| DBAs, data modellers | How are data entities structured and related? | **ERD (Crow's Foot)** | Phase 5 | [PLAYBOOK-ERD] |
| Infrastructure architects, DevOps | Where does software run and how is it deployed? | **Deployment Diagram** | Phase 6 | [PLAYBOOK-DEPLOY] |
| Architects, enterprise leads | How do systems in a portfolio integrate? | **Integration Map** | Phase 7 | [PLAYBOOK-INTMAP] |
| Developers, system designers | How does the system move between states? | **State Diagram** | Phase 5 | [PLAYBOOK-STATE] |
| All — multi-system scope | What is the enterprise landscape of systems? | **Portfolio Landscape** | Phase 7/8 | [PLAYBOOK-PORTFOLIO] |

**Three traps to avoid** (from [020, Section 5]):
1. Do not let organizational title determine diagram choice — a developer presenting to executives uses C4 L1, not C4 L3
2. Do not reuse the diagram type you know best — a swimlane cannot show data transformation; a DFD cannot show accountability
3. Do not produce one diagram when two are needed — structural view + process view = C4 Container + Swimlane, not a hybrid

### Step 0.3 — Propose Diagram Set with Justification

Before generating XML, output a brief **Diagram Selection Proposal** in this format:

```
DIAGRAM SELECTION PROPOSAL
──────────────────────────
Detected audience:  [from source / user input]
Architectural intent: [structural / behavioral / flow / data / deployment]
Current methodology phase: [Phase N — Name] per [030, Section 5]

Proposed diagram(s):
1. [Type] — because [audience + intent justification referencing 020 §5]
2. [Type] — because [chaining rationale referencing 020 §8] (if applicable)

Stakeholder variant: Business / Technical / Both
Emitter: draw.io XML [+ optional: Structurizr DSL / PlantUML / Mermaid]

Proceed? (Y to generate / N to revise)
```

If the user has already specified a diagram type and it matches the matrix, confirm and proceed. If it does not match, flag the mismatch and ask for confirmation before overriding the matrix.

---

## WHEN TO USE — DIAGRAM TYPE ROUTER

| User Says / Requests | Diagram Type | Playbook |
|---|---|---|
| "HLLFD", "high level logical flow", "architecture diagram", "upstream downstream" | HLLFD (maps to C4 L2 + Integration Map) | Run Phase 0 first; then [PLAYBOOK-C4L2] |
| "C4 context", "system context", "who uses the system" | C4 Level 1 | [PLAYBOOK-C4L1] |
| "C4 container", "container diagram", "building blocks" | C4 Level 2 | [PLAYBOOK-C4L2] |
| "C4 component", "component diagram", "zoom into [service]" | C4 Level 3 | [PLAYBOOK-C4L3] |
| "C4 code diagram", "class diagram for [component]" | C4 Level 4 | [PLAYBOOK-C4L4] |
| "DFD", "data flow", "Gane-Sarson", "Yourdon-Coad", "data pipeline" | DFD | [PLAYBOOK-DFD] |
| "swimlane", "BPMN", "cross-functional", "who does what", "process map" | Swimlane/BPMN | [PLAYBOOK-SWIM] |
| "sequence diagram", "message flow", "call sequence", "API flow" | Sequence | [PLAYBOOK-SEQ] |
| "ERD", "entity relationship", "Crow's Foot", "data model", "schema" | ERD | [PLAYBOOK-ERD] |
| "deployment diagram", "infrastructure", "cloud architecture", "topology" | Deployment | [PLAYBOOK-DEPLOY] |
| "integration map", "interface diagram", "system integration landscape" | Integration Map | [PLAYBOOK-INTMAP] |
| "state diagram", "state machine", "state transitions" | State | [PLAYBOOK-STATE] |
| "portfolio", "enterprise landscape", "all systems", "multi-system" | Portfolio Landscape | [PLAYBOOK-PORTFOLIO] |
| "business view", "executive view", "non-technical" | Stakeholder Variant: Business | [STAKEHOLDER-VARIANTS] |
| "update diagram", "add to drawio", "modify diagram" | Update Existing | Parse file, re-run Anti-Pattern Guard |
| No type specified | Default: run Phase 0 decision matrix | — |

---

## REQUIRED CONTEXTS

| Context ID | Name | Purpose | If Missing |
|---|---|---|---|
| CTX-PALETTE | org-color-palette | Organization domain colors, WCAG-verified contrast | Use default palette; warn if WCAG 4.5:1 not confirmed |
| CTX-DOMAINS | org-domain-groups | Domain classification (Core Platform, Enterprise Services, External) | Ask user; default to Internal/External/Third-Party |
| CTX-APPREG | app-registry | Known applications, short names, IDs, domain ownership | Build from source; ask per diagram |
| CTX-SYSPACK | system-context-pack | TMPL-006 fields: audience, intent, phase, scope | Ask if Phase 0 cannot answer Step 0.1 |
| CTX-CHAINIDS | chaining-id-registry | Shared entity IDs across Container → Sequence → Deployment | Initialize on first C4 L2 diagram; reuse in Sequence and Deployment |

---

## INPUTS

### Required
- **Application/system identity**: Name, short name, ID
- **At least one source**: Transcript, design doc, Confluence, PPT, existing diagram, or description
- **Diagram type**: One of the supported types (Phase 0 resolves if unspecified)

### Optional
- Target audience (if not derivable from source material)
- Methodology phase (from [030, Section 5])
- Stakeholder variant request (business / technical / both)
- Output emitter override (Structurizr DSL / PlantUML / Mermaid)
- Existing `.drawio` file (for updates)
- Custom output filename

### Validation
- No application name → ask before proceeding
- No source documents → ask what is available; never generate from imagination
- Diagram type ambiguous → run Phase 0 and present proposal
- Audience unknown → default to technical; note assumption in proposal

---

## PROCESS — NINE STEPS

### Step 0: Phase 0 — Diagram Selection
Run the full Phase 0 protocol above. Do not skip. Produce the Diagram Selection Proposal and confirm with user or auto-proceed if context is clear.

### Step 1: Load References
**Always load (every diagram):**
- `references/xml-rules.md` — Official draw.io XML rules (v4.0)
- `references/color-palette.md` — Default domain colors, WCAG-verified

**Load by diagram type:**

| Diagram Type | References to Load |
|---|---|
| HLLFD | `references/hllfd-spec.md` + `references/workflow-numbering.md` |
| C4 L1/L2/L3/L4 | `references/c4-spec.md` |
| DFD | `references/data-flow-spec.md` |
| Swimlane/BPMN | `references/swimlane-spec.md` |
| Sequence | `references/sequence-spec.md` |
| ERD | `references/erd-spec.md` |
| Deployment | `references/deployment-spec.md` |
| Integration Map | `references/integration-spec.md` |
| State | `references/state-spec.md` |
| Portfolio | `references/c4-spec.md` + `references/integration-spec.md` |

**Load the per-diagram playbook** from Section [PER-DIAGRAM PLAYBOOKS] below.

### Step 2: Parse Source Material

Extract from sources ONLY — never invent:

| Extract | Notes |
|---|---|
| Application name | Primary name + short name/code |
| System ID | CAI / CSI / App ID |
| Integrations | Each connected system |
| Integration type | API / DB / File / Message / UI |
| Data flows | Direction and content |
| Domain classification | Business domain ownership |
| Users/actors | Who accesses the system |
| Technology | Platform, language, hosting |
| Trust boundaries | DMZ, internal, external zones |
| Planned/future items | Flag with PLANNED label |

Ambiguous → TBD. Conflicting sources → flag both, note conflict.

### Step 3: Run the Anti-Pattern Guard

Before selecting shapes or writing XML, check every source extraction against the [ANTI-PATTERN GUARD] section. Block the diagram if any Category B (Scope) violations are confirmed. Warn for all other categories. Record each flag in the output gap report.

### Step 4: Run the 12-Principle Validators

Apply the [PRINCIPLE VALIDATORS] section. Each principle produces a go/warn/block result. Block result = stop and fix before continuing.

### Step 5: Select Shapes and Plan Layout

Apply the per-diagram playbook's shape rules and then apply the [GESTALT + WCAG LAYOUT ENGINE].

Before writing XML:
1. List all vertices with unique, meaningful IDs
2. Apply the chaining model — reuse IDs from CTX-CHAINIDS if a linked diagram already exists
3. Group by domain/swimlane using proximity rules
4. Assign coordinates: 200px horizontal / 120px vertical minimum gaps; 10px grid alignment
5. Plan orthogonal edge routing; minimize crossings by repositioning before routing
6. Confirm stakeholder variant (business vs technical) per [STAKEHOLDER VARIANTS]

### Step 6: Generate XML

Follow `references/xml-rules.md`. Core invariants:
1. Root: `<mxGraphModel adaptiveColors="auto">` — never `<mxfile>`
2. NEVER include XML comments
3. Every edge: expanded form with geometry child — never self-closing
4. All vertices defined before all edges
5. Unique IDs — reuse from CTX-CHAINIDS for chained elements
6. Escape all special characters: `&amp;` `&lt;` `&gt;` `&quot;`
7. Embed metadata: `last-verified`, `methodology-phase`, `audience-variant`, `diagram-type`

### Step 7: Run Pre-Publish Visual QA Gate

Apply the full [PRE-PUBLISH VISUAL QA GATE] from [012 Gestalt & Visual Clarity Standards, Section 11.3] + [052, Section 12]. All items must pass before delivery. Any fail → revise, do not deliver.

### Step 8: Select Output Emitter and Deliver

Default: draw.io XML. Optional emitters: Structurizr DSL, PlantUML, Mermaid (see [OUTPUT EMITTERS] section).

| Environment | Action |
|---|---|
| Claude.ai chat | Write to `/mnt/user-data/outputs/[filename]`; use `present_files` |
| Claude Code CLI | Write to project working directory |
| VS Code / IntelliJ draw.io plugin | Write `.drawio` — plugin opens natively |

### Step 9: Post-Execution — Context Updates

After delivery, check for observations:
- New application not in CTX-APPREG → log observation
- New chain ID not in CTX-CHAINIDS → log and update
- Audience preference expressed → log for CTX-SYSPACK
- Color scheme preference → log for CTX-PALETTE

Log to `pending-observations.ctx.md` per ARCH-001 protocol.

---

## PER-DIAGRAM PLAYBOOKS

Each playbook is the canonical build guide for that diagram type. Embed the playbook before writing any XML.

---

### [PLAYBOOK-C4L1] C4 Level 1 — System Context Diagram

**Source:** [021 C4 Model, Section 2.1] ✅ Simon Brown, c4model.com
**Methodology Phase:** Phase 2 — Constraints [030, Section 5]
**Purpose:** Show the system as a single box; its human users; its external system dependencies. No internals exposed.
**Audience:** Business executives, product owners, non-technical stakeholders, all C-suite — anyone who needs to understand what the system is without understanding how it works.
**Inputs Required:** Application name; user types; external systems (names only, not internals); brief system purpose statement.

**Build Order:**
1. Place the primary system as a single box at center (use distinctive fill — e.g., #1168BD)
2. Place human actors (person shapes) outside and above the system box
3. Place external systems outside and below/around the system box
4. Draw labeled directional arrows showing relationships (not data fields — relationship descriptions only)
5. Add a title and scope note: "This diagram shows [System Name] and its external relationships. It does not show internal components."
6. Apply trust boundaries if external systems span security zones (dashed enclosure per [B7])
7. Add version, date, audience-variant label

**Notation Standard:** C4 Model notation (Simon Brown) — boxes, persons, arrows with relationship descriptions. No database symbols, no technology labels in L1.

**Color/Shape Rules:**
- Primary system: Blue fill (#1168BD), white text — clearly distinguished from surroundings
- Persons (users): Person shape, grey fill (#08427B for internal users, lighter grey for external users)
- External systems: Grey box (#999999 fill, white text)
- Arrows: Black, directional, labeled with relationship description (not protocol)
- Containers: None at L1 — single system box only

**Anti-Patterns to Block (from [052]):**
- A1: No mixing of internal container detail into the L1 diagram
- B1: God Diagram — if more than 15 external systems appear, split into domain-focused L1 variants
- B7: Missing trust boundaries — if external systems include internet-facing inputs, DMZ zone must be shown
- C1: Audience mismatch — if technology labels appear, remove; this is for business audience

**QA Checklist:**
- [ ] System is a single box — no internal components shown
- [ ] Every actor and external system has a name and brief description
- [ ] Every arrow has a relationship description (not just a line)
- [ ] No technology labels on L1 (no "REST API", "PostgreSQL", etc.)
- [ ] Trust boundary shown if any external system is internet-facing
- [ ] Diagram passes business-audience readability check: can a non-technical executive understand it in 60 seconds?

---

### [PLAYBOOK-C4L2] C4 Level 2 — Container Diagram

**Source:** [021 C4 Model, Section 2.2] ✅
**Methodology Phase:** Phase 4 — High-Level Design [030, Section 5]
**Purpose:** Zoom into the system from L1. Show the major technical building blocks (web apps, APIs, databases, message brokers, mobile apps) and how they communicate. Show the technology for each container.
**Audience:** Technical leads, architects, senior developers, infrastructure architects.
**Inputs Required:** C4 L1 diagram (or source equivalent); list of containers with technology labels; primary communication protocols; deployment environment if relevant.

**Build Order:**
1. Draw the system boundary (dashed or solid enclosure with system name)
2. Place containers inside the boundary — group by function (frontend, backend, data, integration)
3. Place external systems and actors outside the boundary (carry forward from L1)
4. Draw labeled arrows: protocol + brief data description (e.g., "Reads/writes orders [HTTPS]")
5. Add technology labels to each container (e.g., "[Java 17, Spring Boot]")
6. Add trust boundaries — distinguish DMZ from internal network from PII zones
7. Initialize CTX-CHAINIDS — assign stable IDs to each container for Sequence and Deployment chaining
8. Add version, date, methodology phase tag, audience-variant label

**Notation Standard:** C4 Model — container shapes per type (see shape rules below), technology in square brackets.

**Color/Shape Rules:**
- Web Application: Rounded rectangle, blue fill
- API / Microservice: Rounded rectangle, blue fill (distinguish with "API" label)
- Database: Cylinder shape, grey/blue fill
- Message Broker / Queue: Trapezoid or distinctive shape, green/teal fill
- Mobile App: Rounded rectangle with mobile icon indicator, blue fill
- External System (outside boundary): Grey box — same as L1
- System Boundary: Dashed rectangle, light grey stroke, label at top-left

**Principle Validators Active (from [011]):**
- P1 Separation of Concerns: Each container must have exactly one primary responsibility — flag if a container appears to do two unrelated things
- P3 Cohesion/Coupling: Containers in the same domain should be visually proximate; containers communicating frequently should minimize crossing edges
- P9 Security-by-Design: Trust boundaries must be drawn; internet-facing containers must be in the DMZ or externally annotated

**Anti-Patterns to Block:**
- A1: No mixing L1 context with L2 container detail on the same diagram
- B7: Trust boundaries mandatory — missing zones block diagram generation
- E3: Unlabeled edges — every arrow must carry protocol + data description

**QA Checklist:**
- [ ] System boundary drawn and labeled
- [ ] Every container has: name, technology label, brief description
- [ ] Every arrow has: protocol and data/service description
- [ ] Trust boundaries drawn for all security zones
- [ ] CTX-CHAINIDS initialized with stable container IDs
- [ ] External systems match L1 (no new externals introduced without explanation)
- [ ] Separation of Concerns check: every container has exactly one primary responsibility

---

### [PLAYBOOK-C4L3] C4 Level 3 — Component Diagram

**Source:** [021 C4 Model, Section 2.3] ✅
**Methodology Phase:** Phase 5 — Detailed Design [030, Section 5]
**Purpose:** Zoom into a single container from L2. Show the internal components and their interactions. One Component diagram per container in scope.
**Audience:** Developers (design and implementation), technical architects reviewing a specific service.
**Inputs Required:** The parent C4 L2 diagram or source equivalent; component list for the target container; internal API/call patterns.

**Build Order:**
1. Draw the container boundary (clear label: "Container: [Name]")
2. Place components inside — group by layer (controllers, services, repositories, domain models)
3. Show the external elements the container interacts with (other containers from L2 — show as external boxes, not expanded)
4. Draw labeled arrows: interface type + data description
5. Reuse CTX-CHAINIDS container IDs from L2 for the external references
6. Add technology labels per component if relevant
7. Add version, date, methodology phase tag

**Notation Standard:** C4 Model — component shapes, technology labels, relationship descriptions.

**Color/Shape Rules:**
- Controller/Handler: Rounded rectangle, light blue
- Service/Business Logic: Rounded rectangle, blue
- Repository/Data Access: Rounded rectangle, grey-blue
- Domain Model: No fill or light yellow — not a runtime component, a data structure
- External Container references: Grey rounded rectangle (from L2 palette)

**Anti-Patterns to Block:**
- A1: Do not mix L2 container level with L3 component level in one diagram
- B1: God Diagram — if component count exceeds 20, split by sub-domain or layer
- E3: All inter-component relationships must carry labeled arrows

**QA Checklist:**
- [ ] Exactly one container is zoomed into — boundary label confirms it
- [ ] All internal components shown; all external references are L2 containers (not further decomposed)
- [ ] CTX-CHAINIDS reused for all external container references
- [ ] Component count ≤ 20 (God Diagram check)
- [ ] Every arrow carries relationship description

---

### [PLAYBOOK-C4L4] C4 Level 4 — Code Diagram (Optional)

**Source:** [021 C4 Model, Section 2.4] ✅ — Level 4 is formally optional per Simon Brown (c4model.com, 2021)
**Methodology Phase:** Phase 5 — Detailed Design [030, Section 5]
**Purpose:** Show how a component maps to code artifacts (classes, interfaces, functions). Use only for the most complex or critical components where code-level documentation is warranted.
**Audience:** Developers implementing or reviewing a specific component.
**Note:** ⚠️ Level 4 diagrams are high-maintenance — they drift from code rapidly. Only produce at explicit request. Prefer code-generation tools (IDEs, documentation generators) over manual L4 diagrams.

**Build Order:**
1. Confirm explicit user request for L4 — do not auto-generate
2. Scope to a single component from L3
3. Show primary classes/interfaces and their relationships (inheritance, composition, dependency)
4. Label relationships with cardinality and relationship type
5. Add "Valid as of [date] — verify against codebase before use" warning label

**Anti-Patterns to Block:**
- B4: Over-Promising Completeness — L4 must carry an explicit scope declaration
- D1: Stale Diagrams — L4 diagrams MUST carry a verified-against-codebase date

---

### [PLAYBOOK-DFD] Data Flow Diagram

**Source:** [023 Data Flow Diagrams] ✅ — DeMarco (1979); Gane & Sarson (1979); Yourdon-Coad notation
**Methodology Phase:** Phase 3 (DFD L0) / Phase 5 (DFD L1–L2) [030, Section 5]
**Purpose:** Show how data moves through the system, is transformed, and exits. Not about who does what (that is swimlane) — about data transformation flows.
**Audience:** Business analysts, compliance officers, security architects, data engineers.
**Inputs Required:** External entities; major processes/transformations; data stores; data flow descriptions.

**Notation Selection — Gane-Sarson (default) vs Yourdon-Coad:**

| Choice | When to Use | Shape Differences |
|---|---|---|
| **Gane-Sarson (default)** | Enterprise IT, compliance, business analyst audiences | Rounded rectangle = process; open rectangle = data store; box = external entity |
| **Yourdon-Coad** | Academic, computer science, developer audiences | Circle = process; open-ended parallel lines = data store; rectangle = external entity |

**Build Order (DFD Level 0 — Context):**
1. Place the system as a single process bubble at center
2. Place all external entities around the perimeter
3. Draw all data flows between external entities and the central process (bidirectional where applicable)
4. Label every data flow with the data name (not protocol — "Customer Order", not "HTTP POST")

**Build Order (DFD Level 1):**
1. Explode the central L0 process into N sub-processes (numbered 1.0, 2.0, etc.)
2. Place data stores between sub-processes
3. Draw all data flows — enforce the four integrity rules:
   - Conservation of Data: every input into a process must appear in outputs
   - No Direct Entity-to-Store Access: external entities cannot read/write stores directly — must go through a process
   - Mandatory Inputs/Outputs: every process must have at least one input and one output
   - Data-Only Flows: control signals and instructions are not data flows (exclude them)
4. Check every process: if inputs do not explain outputs, the process is a Black Hole or Miracle — flag and fix

**Color/Shape Rules (Gane-Sarson):**
- Processes: Rounded rectangle, light blue fill, process number + name
- Data Stores: Open rectangle (no left/right walls), white/light grey fill, Dn label
- External Entities: Rectangle, grey fill
- Data Flows: Directed arrow, labeled with data name

**Anti-Patterns to Block:**
- Direct entity-to-store access (Black Hole / Miracle violation)
- Unlabeled data flows
- Control flows disguised as data flows
- Mixing DFD levels on one diagram (A1)

**QA Checklist:**
- [ ] Correct notation (Gane-Sarson default; Yourdon-Coad only if requested)
- [ ] All four DFD integrity rules satisfied
- [ ] Every data flow labeled with data name (not protocol)
- [ ] No direct entity-to-store access
- [ ] Data store IDs (D1, D2...) consistent across levels

---

### [PLAYBOOK-SWIM] Swimlane / BPMN 2.0 Workflow Diagram

**Source:** [024 Swimlane & Workflow Diagrams] ✅ — Rummler & Brache (1990); OMG BPMN 2.0 (ISO/IEC 19510:2013)
**Methodology Phase:** Phase 4 — High-Level Design [030, Section 5]
**Purpose:** Show who does what, in what order, with whom they hand off. Accountability and process flow across actors. Not data transformation (DFD) — actor responsibility.
**Audience:** Business stakeholders, process owners, operations teams, cross-functional teams.
**Inputs Required:** Actor list (roles, systems, teams); process steps in order; decision points; handoff triggers.

**Build Order:**
1. Define actors (roles — never individual people's names) and create one lane per actor
2. Map the happy path first — left to right, without exceptions
3. Add BPMN Gateway shapes for decisions (XOR = exclusive or; AND = parallel; OR = inclusive)
4. Add exception branches using BPMN Boundary Events:
   - Interrupting Boundary Error Event: for failures that stop the process
   - Non-Interrupting Boundary Timer Event: for timeout handling
5. Add Start Event (green circle) and End Event(s) (red circle with thick border)
6. Ensure every handoff arrow crosses a lane boundary (handoffs between actors = cross-lane flows)
7. Apply Gestalt proximity: steps within one actor's lane stay visually contained; handoffs are visually prominent

**BPMN 2.0 Core Notation:**

| Element | Shape | Use |
|---|---|---|
| Start Event | Thin-border circle | Beginning of process |
| End Event | Thick-border circle | Termination |
| Task | Rounded rectangle | Any unit of work |
| XOR Gateway | Diamond with X | Exactly one path taken |
| AND Gateway | Diamond with + | All paths taken in parallel |
| OR Gateway | Diamond with O | One or more paths taken |
| Sequence Flow | Solid arrow | Normal flow |
| Message Flow | Dashed arrow | Flow between pools |
| Pool | Large container | Distinct organization/system |
| Lane | Division within pool | Role within organization |
| Boundary Event | Circle attached to task edge | Exception or timer |

**Anti-Patterns to Block:**
- Person-named lanes (C3): lanes must be roles (Customer, Finance Team), never names (John Smith)
- Spaghetti Lane (E2): more than 5 edge crossings → decompose or reorganize
- Missing Failure Paths (B5): every task that can fail must have a Boundary Error Event
- Missing Actors (C3): every handoff must go to a named lane — no "system" or unnamed recipients

**QA Checklist:**
- [ ] One lane per actor role (not individual names)
- [ ] Start and End Events present
- [ ] Every decision is a BPMN Gateway (not free text)
- [ ] Every task that can fail has a Boundary Error Event
- [ ] All cross-lane arrows represent genuine handoffs
- [ ] Happy path is traceable left-to-right without confusion
- [ ] Exception flows are visually distinct from happy path

---

### [PLAYBOOK-SEQ] Sequence Diagram

**Source:** [025 Sequence, ERD & Deployment Diagrams, Section 3] ✅ — OMG UML 2.5.1
**Methodology Phase:** Phase 4 (high-level) / Phase 5 (detailed) [030, Section 5]
**Purpose:** Show how components exchange messages in time-ordered sequence for a specific use case or interaction. Complements C4 L2 Container diagram (which shows structure) with interaction detail (which shows behavior).
**Audience:** Developers and architects who need to understand a specific runtime interaction.
**Inputs Required:** Participants (carry forward from C4 L2 via CTX-CHAINIDS); message sequence for the target use case; response patterns; error conditions.
**Chaining:** Use container IDs from CTX-CHAINIDS as lifeline names — ensures consistency with the parent C4 L2 diagram.

**Build Order:**
1. List participants (lifelines) — map to container IDs from CTX-CHAINIDS
2. Create lifeline boxes at top; draw activation bars for the duration of each participation
3. Draw synchronous messages (solid arrowhead) left-to-right in time order
4. Draw return messages (dashed arrowhead) right-to-left
5. Draw asynchronous messages (open arrowhead) where applicable
6. Add alt/opt/loop combined fragments for conditional, optional, and repeated flows
7. Add error/exception flows as separate alt fragments with explicit conditions labeled
8. Add a title: "Sequence Diagram: [Use Case Name] — [System Name]"

**Notation Standard (UML 2.5.1):**

| Element | Representation |
|---|---|
| Lifeline | Box at top, dashed vertical line below |
| Activation bar | Thin rectangle on lifeline during active execution |
| Synchronous message | Solid filled arrowhead |
| Return message | Dashed open arrowhead |
| Asynchronous message | Open (non-filled) arrowhead |
| Self-call | Arrow back to same lifeline |
| Combined fragment | Rectangle with fragment type label (alt / opt / loop / par) |

**Anti-Patterns to Block:**
- A5: Unlabeled messages — every arrow must carry a message name
- B7: Trust boundary crossings must be visually annotated (dashed box around messages crossing security zones)
- D3: Lifeline names must match CTX-CHAINIDS / C4 L2 component names exactly

**QA Checklist:**
- [ ] All lifeline names match CTX-CHAINIDS entries
- [ ] Every message labeled (name + optional parameters)
- [ ] Return messages shown for all synchronous calls
- [ ] At least one alt fragment for error/exception flow
- [ ] Title includes use case name and system name

---

### [PLAYBOOK-ERD] Entity-Relationship Diagram (Crow's Foot Notation)

**Source:** [025 Sequence, ERD & Deployment Diagrams, Section 4] ✅ — Chen (1976, ACM TODS); Crow's Foot notation
**Methodology Phase:** Phase 5 — Detailed Design [030, Section 5]
**Purpose:** Model how data entities are structured and related at the schema level. Shows data at rest — complements the DFD (which shows data in motion).
**Audience:** Database architects, data modelers, backend developers, compliance reviewers.
**Inputs Required:** Entity names; attributes per entity; relationship types (one-to-one, one-to-many, many-to-many); primary/foreign keys.

**Build Order:**
1. List all entities — map to data stores from DFD via shared IDs where available
2. Define attributes per entity (primary key, foreign keys, non-key attributes)
3. Draw entities as rectangles with attribute lists inside
4. Draw relationships using Crow's Foot notation:
   - One-to-One: single bar on both ends
   - One-to-Many: single bar on "one" side, crow's foot on "many" side
   - Many-to-Many: crow's foot on both ends (then decompose to junction entity)
5. Label every relationship with a verb phrase (e.g., "Customer PLACES Order")
6. Add cardinality notation (mandatory/optional: circle = zero/optional, bar = one/mandatory)

**Crow's Foot Notation Quick Reference:**

| Symbol | Meaning |
|---|---|
| `|` (single bar) | Exactly one |
| `O` (circle) | Zero (optional) |
| `<` (crow's foot) | Many |
| `|O` | Zero or one |
| `||` | Exactly one (mandatory) |
| `O<` | Zero or many |
| `|<` | One or many |

**Color/Shape Rules:**
- Entity: Rectangle, white fill, entity name in header (blue background)
- Primary key: Underlined attribute
- Foreign key: Marked with FK
- Relationship line: Black, with Crow's Foot symbols at each end

**Anti-Patterns to Block:**
- Many-to-Many without junction entity for physical ERDs
- Unlabeled relationships (E3)
- Missing cardinality on all relationship ends

**QA Checklist:**
- [ ] All entities from DFD data stores represented
- [ ] Every relationship has Crow's Foot cardinality on both ends
- [ ] Every relationship labeled with verb phrase
- [ ] All M:N relationships decomposed to junction entities
- [ ] Primary and foreign keys explicitly marked

---

### [PLAYBOOK-DEPLOY] Deployment Diagram

**Source:** [025 Sequence, ERD & Deployment Diagrams, Section 5] ✅ — OMG UML 2.5.1 structural diagrams
**Methodology Phase:** Phase 6 — Validation [030, Section 5]
**Purpose:** Show where software runs and how runtime artifacts are distributed across infrastructure nodes. The infrastructure complement to a C4 Container diagram.
**Audience:** Infrastructure architects, DevOps, cloud engineers, security architects.
**Inputs Required:** Container list from C4 L2 (CTX-CHAINIDS); hosting environment (cloud/on-prem); node types; network zones; deployment artifacts per node.
**Chaining:** Nodes in the Deployment diagram correspond to containers from CTX-CHAINIDS — the same entity IDs must appear in both diagrams.

**Build Order:**
1. Define deployment environments (Production, Staging, DR) as top-level nodes
2. Within each environment, define infrastructure nodes (VMs, containers, clusters, availability zones)
3. Assign deployment artifacts to nodes — each artifact maps to a container from CTX-CHAINIDS
4. Draw communication paths between nodes with protocol labels
5. Add trust boundary enclosures (Internet Zone, DMZ, Internal Network, PCI Zone, etc.)
6. Show autoscaling groups and replica counts where applicable
7. Mark stateless vs stateful containers explicitly (from [011 P6 — Scalability])

**Node Type Notation (UML 2.5.1):**

| Node Type | Shape |
|---|---|
| Physical server | 3D box |
| Virtual machine / container instance | Nested box inside node |
| Deployment artifact (JAR, container image) | Document shape within node |
| Communication path | Directed line with protocol label |
| Cluster / auto-scaling group | Dashed boundary with count label |

**Principle Validators Active:**
- P6 Scalability: Stateless vs stateful labeled; horizontal scale indicators for stateless services
- P7 Reliability: Redundancy and failover paths shown; single points of failure flagged
- P9 Security-by-Design: All trust boundaries and network zone labels present

**Anti-Patterns to Block:**
- B7: Missing trust boundaries — deployment diagrams must show network zones
- B6: Missing non-functional concerns — SLAs, replica counts, redundancy must be annotated
- D3: Artifact names must match CTX-CHAINIDS container names exactly

**QA Checklist:**
- [ ] Every container from CTX-CHAINIDS is deployed to a node
- [ ] All network zones (Internet, DMZ, Internal) explicitly labeled with dashed enclosures
- [ ] Stateless vs stateful marked for all services
- [ ] Redundancy / replica count shown for critical services
- [ ] All communication paths carry protocol label
- [ ] Artifact names match C4 L2 container names

---

### [PLAYBOOK-INTMAP] Integration Map

**Source:** [033 Portfolio & Multi-System Architecture, Section 5] + [060, Section 7] ✅
**Methodology Phase:** Phase 7 — Documentation [030, Section 5]
**Purpose:** Show the full landscape of system-to-system integrations across a portfolio or domain. Answer: what connects to what, how, and via what integration pattern.
**Audience:** Architects, enterprise leads, integration engineers, governance boards.
**Inputs Required:** System registry (CTX-APPREG); integration inventory; integration patterns (REST API, event bus, file transfer, DB replication, ESB); data classifications per flow.

**Build Order:**
1. Group systems by domain (using CTX-DOMAINS)
2. Place domain groups as swim-zone containers
3. Place systems inside domain containers
4. Draw integration arrows between systems: one arrow per integration, not per API call
5. Label each arrow: integration type (sync/async/batch) + data domain + integration pattern
6. Add integration pattern legend
7. For multi-system portfolio scope: add pattern evolution overlay (point-to-point → API gateway → event mesh per [033])

**Integration Pattern Color Coding:**
- Synchronous REST/gRPC: Solid blue arrow
- Asynchronous/Event-driven: Dashed green arrow
- Batch/File transfer: Dotted orange arrow
- Database replication/ETL: Dashed grey arrow
- ESB/Middleware-mediated: Double-headed purple arrow

**Anti-Patterns to Block:**
- B4: Must carry explicit scope declaration ("This map covers domain X as of [date]")
- D1: Must carry last-verified date and owner
- F1: Must identify source-of-truth location

---

### [PLAYBOOK-STATE] State Diagram

**Source:** [025 Sequence, ERD & Deployment Diagrams] + UML 2.5.1 ✅
**Methodology Phase:** Phase 5 — Detailed Design [030, Section 5]
**Purpose:** Show how an entity (order, user account, job, message) moves through states in response to events/conditions.
**Audience:** Developers, systems analysts, product managers reviewing lifecycle behavior.
**Inputs Required:** Entity name; exhaustive list of states; events/conditions that trigger transitions; entry and exit actions per state.

**Build Order:**
1. List all states for the entity
2. Define the initial pseudostate (filled circle) and terminal state(s) (circle with thick border)
3. Draw states as rounded rectangles with state name and optional entry/do/exit actions
4. Draw transitions as labeled arrows: [event] / [action]
5. Add guard conditions in square brackets where transitions are conditional
6. Verify completeness: every state must have at least one entry and one exit (except start and terminal)

**Anti-Patterns to Block:**
- Orphan states (nodes with no exit or no entry — except terminal/initial)
- Unlabeled transitions
- Missing guard conditions on conditional transitions

**QA Checklist:**
- [ ] Initial pseudostate present
- [ ] At least one terminal state
- [ ] Every transition labeled with event/action
- [ ] No orphan states
- [ ] Guard conditions on all conditional transitions

---

### [PLAYBOOK-PORTFOLIO] Portfolio Landscape Diagram

**Source:** [033 Portfolio & Multi-System Architecture] ✅ — TOGAF ADM; Zachman Framework
**Methodology Phase:** Phase 7–8 [030, Section 5]
**Purpose:** Show the enterprise landscape of IT systems across multiple domains — their relationships, integration patterns, and strategic categorization. Generated from multiple System Context Packs.
**Audience:** Enterprise architects, CTO, engineering leadership, governance boards.
**Inputs Required:** Multiple System Context Packs (TMPL-006) or CTX-APPREG registry; domain taxonomy (CTX-DOMAINS); integration inventory.

**Build Order:**
1. Load all systems from CTX-APPREG
2. Group systems by business domain (CTX-DOMAINS) — create domain containers
3. Apply TIME model categorization if provided (Tolerate / Invest / Migrate / Eliminate)
4. Show inter-domain integration patterns (carry forward from Integration Maps)
5. Highlight systems involved in active migration (Strangler Fig patterns — dashed borders)
6. Add strategic overlay (2x2 grid: strategic importance vs. technical health — optional)
7. Limit displayed detail to domain-level — do not show container-level internals

**Anti-Patterns to Block:**
- B1: God Diagram — scope the portfolio landscape to no more than 3–5 domains per canvas; split if larger
- A1: Do not mix system-context level with container-level detail
- B4: Explicit scope declaration mandatory ("This landscape covers [domains] as of [date]")

---

## 12 PRINCIPLE VALIDATORS

> **Source:** [011 Core Architectural Principles] ✅ — Applied to every diagram as validators
> Run these checks at Step 4 after source parsing. Each returns: ✅ Pass / ⚠️ Warn / 🚫 Block.

| # | Principle | Diagram Check | Result |
|---|---|---|---|
| P1 | **Separation of Concerns** | Each container/component in the diagram has exactly one primary responsibility. If a component appears to do two unrelated things, flag it. | 🚫 Block if container violates SoC — request source clarification before generating |
| P2 | **Modularity** | Diagram respects defined module/service boundaries. No diagram crosses boundaries without an explicit integration arrow. | ⚠️ Warn if boundaries are ambiguous |
| P3 | **Cohesion & Coupling** | Highly coupled components are visually co-located. Loosely coupled components have fewer crossing arrows. | ⚠️ Warn if high-coupling layout is counter-intuitive |
| P4 | **Abstraction Layers** | Each diagram occupies exactly one abstraction level (C4 L1 / L2 / L3 / L4). No mixing of levels. | 🚫 Block on abstraction mixing (Anti-Pattern A1) |
| P5 | **SOLID at System Scale** | Services/components obey Open/Closed at minimum: new functionality = new component, not modification of existing. Extension points are visible. | ⚠️ Warn if system has no clear extension point |
| P6 | **Scalability** | Stateless vs stateful services marked on Container and Deployment diagrams. Horizontal scale indicators present for stateless services. | ⚠️ Warn if stateless/stateful not marked on L2 or Deployment |
| P7 | **Reliability & Availability** | No single points of failure left unmarked. Redundancy shown on Deployment. Circuit breakers shown on Sequence where applicable. | ⚠️ Warn if SPOF not marked |
| P8 | **Maintainability** | Diagram has version, owner, last-verified date. Source-of-truth location declared. | 🚫 Block if no version or date — diagram cannot be delivered without governance metadata |
| P9 | **Security-by-Design** | Trust boundaries visible. Internet Zone, DMZ, Internal Network distinguished. All cross-boundary calls annotated with auth/encryption. | 🚫 Block on C4 L2 and Deployment if trust boundaries missing |
| P10 | **Cost-Awareness** | No diagram encodes performance commitments (latency, throughput SLAs) that are unverified. Numbers on arrows must have a verified source. | ⚠️ Warn on Fictional Precision (Anti-Pattern B3) |
| P11 | **Evolvability** | Planned/future components shown with dashed borders and "PLANNED" labels. Current and future state not mixed on same diagram without explicit versioning. | ⚠️ Warn if PLANNED elements have no version label |
| P12 | **Trade-Off Analysis** | Any CAP/PACELC or consistency vs availability trade-off that is architecturally significant should appear as an annotation or ADR reference on the relevant container/service. | ⚠️ Warn if distributed data service has no consistency annotation |

---

## GESTALT + WCAG LAYOUT ENGINE

> **Source:** [012 Gestalt & Visual Clarity Standards] ✅ — Wertheimer (1923); Sweller (1988); W3C WCAG 2.1
> Apply at Step 5 before writing any XML.

### Core Constraint: 3–4 Chunk Working Memory Budget

**Every diagram canvas must not exceed 3–4 novel "chunks" of information at the top-level grouping.** [Source: Sweller (1988) Tier 1; Cowan (2001) revision — 3–4 for novel audiences]. A "chunk" at the diagram level is a domain container or swimlane section. If the diagram has more than 4 top-level visual groups, it must be decomposed. ✅ HIGH

### Layout Rules (Derived from Gestalt Principles [012, Section 3])

| Rule | Source Principle | Enforcement |
|---|---|---|
| Cluster same-domain services spatially | Proximity (Wertheimer 1923) | All services of domain X placed within 80px proximity group; domains separated by ≥ 80px |
| All instances of element type X use identical shape and fill | Similarity | One shape = one semantic type. No style deviations within a type |
| Domain groupings use bounding containers with text labels | Common Region (Rock & Palmer) | Every bounding box has a text label and explicit semantic meaning |
| No diagonal connectors; dominant flow direction (top-to-bottom or left-to-right) | Continuity | `edgeStyle=orthogonalEdgeStyle` on all edges; exit/entry anchors enforce flow direction |
| Primary components (services, APIs) have higher contrast than containers/boundaries | Figure-Ground | Service fill contrast > boundary fill contrast |
| No decorative elements (shadows, gradients, icons without semantic meaning) | Extraneous Load (Sweller) | Remove all non-semantic decoration |

### WCAG 2.1 Contrast Requirements (W3C, 2018 — Tier 1 ✅)

| Element Type | Minimum Contrast Ratio | WCAG Reference |
|---|---|---|
| Text labels (normal) | **4.5:1** | WCAG 2.1 AA §1.4.3 |
| Graphical elements and borders | **3:1** | WCAG 2.1 AA §1.4.11 |
| Connector labels (9–10pt) | **4.5:1** | WCAG 2.1 AA §1.4.3 |

**Color must never be the sole encoding mechanism.** Always pair color with shape or label. ✅ HIGH [WCAG 2.1 §1.4.1]

### Spacing Standards (from [012, Section 11.2])

| Context | Minimum Spacing |
|---|---|
| Between elements within a group | 20px |
| Between groups within a subsystem | 50px |
| Between top-level subsystem boundaries | 80px |
| Minimum arrowhead straight segment | 20px |
| Element placement grid | 10px grid (align all) |
| Horizontal gap between shapes | 200px (preferred) |
| Vertical gap between shapes | 120px (preferred) |

### Typography Rules (from [012, Section 6])

| Label Type | Font | Size | Weight |
|---|---|---|---|
| Title | Sans-serif | 16–18pt | Bold |
| Component name | Sans-serif | 12–14pt | Regular/Bold |
| Type annotation | Sans-serif | 10–11pt | Light |
| Connector label | Sans-serif | 9–10pt | Regular |

**Co-locate all labels with their target element.** No remote legends for primary encodings (Split-Attention Effect violation — Chandler & Sweller, 1991 Tier 1 ✅).

### Maximum Element Count

- Diagram canvas: **≤ 20 distinct shape instances** (God Diagram threshold — [052, B1])
- Top-level visual groups: **≤ 3–4** (working memory budget)
- Semantic colors per diagram: **≤ 5** (CLT + Gestalt recommendation)
- Label hierarchy levels: **≤ 3** (title / component name / annotation)

---

## ANTI-PATTERN GUARD

> **Source:** [052 Common Pitfalls & Anti-Patterns] ✅ — Full catalog of 27 patterns
> Run at Step 3. Each violation has a severity: 🚫 Block / ⚠️ Warn / 💡 Inform.

### Quick Reference — 27 Anti-Patterns by Severity

**🚫 BLOCK — diagram generation halted until fixed:**

| ID | Anti-Pattern | Detection Signal | Fix |
|---|---|---|---|
| A1 | Mixing Abstraction Levels | C4 levels mixed; business goals alongside DB columns | Separate into distinct diagrams per abstraction level |
| B1 | God Diagram | >20 shape instances; multiple incompatible questions | Split by domain or abstraction level |
| B7 | Missing Trust Boundaries | No DMZ/Internet/Internal zone markers on C4 L2, Sequence, Deployment | Add dashed trust boundary enclosures with zone labels |
| F2 | No Version | Diagram has no version number | Add vX.Y, creation date, last-updated date |

**⚠️ WARN — generated with flag, user must resolve before publishing:**

| ID | Anti-Pattern | Detection Signal | Fix |
|---|---|---|---|
| A2 | Undefined Notation | Non-standard shapes with no legend | Add legend; or switch to C4/BPMN/UML notation |
| A3 | Missing Legend | Color/line types used with no legend | Add legend box |
| A4 | Inconsistent Iconography | Same element type has different shapes | Normalize to one shape per type |
| A5 | Ambiguous Arrows | Arrow direction ambiguous; no label | Add label with protocol and direction |
| A6 | Color-Without-Meaning | Color used without shape/label backup | Pair every color with shape or label encoding |
| B2 | Over-Decoration | Shadows, gradients, decorative icons | Remove non-semantic decoration |
| B3 | Fictional Precision | Performance numbers without source | Remove or move to ADR with verified source |
| B4 | Over-Promising Completeness | Title says "complete" but scope is partial | Add explicit scope note |
| B5 | Missing Failure Paths | Happy path only; no error branches | Add BPMN Boundary Events or alt fragments |
| B6 | Missing Non-Functional Concerns | No auth, no data classification shown | Add security annotations, data classification labels |
| C1 | Audience Mismatch | Technical symbols in executive diagram | Render stakeholder variant: Business |
| C2 | Jargon Overload | Technical abbreviations in business-facing view | Switch to plain-language labels |
| C3 | Missing Actors | Handoffs to unnamed recipients | Name every participant |
| C4 | Hidden Assumptions | Implicit technology or org constraints | Add Assumptions & Constraints callout box |
| D1 | Stale Diagrams | No "valid as of" date or last-verified > 90 days | Add living-context date stamp |
| D2 | Copy-Paste Drift | Same component with different names across diagrams | Normalize to CTX-CHAINIDS + CTX-APPREG canonical names |
| D3 | Inconsistent Naming | Component names don't match codebase/registry | Reconcile against CTX-APPREG |
| D4 | Broken Cross-References | Referenced diagrams don't exist | Remove or replace with TBD + pending marker |
| E1 | Tool-Driven Layout | Auto-layout with no semantic grouping | Manual review; apply proximity grouping |
| E2 | Spaghetti Connectors | >5 connector crossings | Decompose or reorganize layout |
| E3 | Undocumented Edges | Unlabeled connectors | Every connector: protocol + data + direction |
| F1 | No Source-of-Truth | No Git path or canonical location declared | Add source-of-truth metadata |
| F3 | No Owner | No team/individual owner declared | Add owner name + contact channel |
| F4 | Decorative Diagrams | Diagram adds no information over surrounding text | Apply information-value test |

---

## CHAINING MODEL

> **Source:** [020 Diagramming Frameworks, Section 8] ✅
> Diagrams are not isolated artefacts — they form chains. Implement via CTX-CHAINIDS.

### Primary Chain — Structure → Interaction → Infrastructure

```
C4 L2 Container  →  Sequence Diagram  →  Deployment Diagram
[container IDs initialized]  [container IDs reused as lifelines]  [container IDs reused as artifacts]
```

- C4 Container diagram initializes CTX-CHAINIDS with stable entity IDs for every container
- Sequence diagram reuses those IDs as lifeline names — never renamed
- Deployment diagram reuses those IDs as deployed artifact names — never renamed

### Chain 2 — Context → Data → Schema

```
C4 L1 Context  →  DFD Level 0  →  ERD
[external entities identified]  [data flows traced; data stores identified]  [data stores → entities in ERD]
```

### Chain 3 — Process → Interaction

```
Swimlane/BPMN  →  Sequence Diagram
[business process steps]  [technical interaction detail for automated steps]
```

### Chain 4 — Portfolio → Integration Map → C4 Context

```
Portfolio Landscape  →  Integration Map  →  C4 Context (per system)
[enterprise system list]  [cross-system integration detail]  [per-system boundary detail]
```

### CTX-CHAINIDS Management

- **Initialize:** When first C4 L2 diagram is generated, create `CTX-CHAINIDS` context file with all container IDs
- **Update:** When Sequence or Deployment diagram is generated, read CTX-CHAINIDS and reuse IDs
- **Conflict:** If source material uses different names for the same component, reconcile against CTX-CHAINIDS and add alias to CTX-APPREG
- **Format per entry:** `[ID]: [CanonicalName] | [ShortCode] | [TechnologyLabel] | [DomainOwner]`

---

## STAKEHOLDER VARIANTS

> **Source:** [051 Business-Facing Diagramming] ✅
> Every diagram can be rendered in two variants. The audience determines the default; both can be produced on request.

### Business Variant (B)

**Use when:** Primary audience is non-technical — executives, product managers, business analysts, sales, legal, finance, operations.

**Rules:**
- Remove all technology labels (no "Java 17", "PostgreSQL", "REST API") — replace with plain-language descriptions ("stores customer orders", "connects the website to the payment provider")
- Replace technical component names with business-language equivalents: "API Gateway" → "the connector that handles all customer requests"
- Limit to ≤ 8 elements per canvas (CLT working memory for non-expert audience)
- Color palette: high contrast, 3–4 colors maximum, WCAG 4.5:1 mandatory
- No acronyms without full expansion
- Add a one-sentence "What this diagram shows" note at the top
- Format: Slide-ready (landscape, 16:9, title + diagram + 2-line summary)
- Diagram type: Default to C4 L1 (System Context) for executive audience [051, Section 6]

**Audience-to-Diagram Mapping (from [051, Section 6]):**

| Audience | Preferred Diagram | Key Adaptation |
|---|---|---|
| C-suite / Board | C4 L1 Context | Business outcomes as labels; no technology |
| Product Managers | C4 L1 + Swimlane (happy path only) | User journey emphasis; actor names in business terms |
| Sales / Marketing | C4 L1 simplified | Focus on customer touchpoints and value delivery |
| Legal / Compliance | DFD L0 + Trust Boundary overlay | Data classification, jurisdiction boundaries |
| Finance | Integration Map (simplified, cost overlay) | Cost centers per domain; no technical detail |
| Operations | Swimlane (operational process only) | Roles, handoffs, SLA windows |

### Technical Variant (T)

**Use when:** Primary audience is technical — developers, architects, infrastructure engineers, security architects, DBAs.

**Rules:**
- Include full technology labels
- Use standard notation (C4, BPMN, UML, Crow's Foot)
- Show trust boundaries, auth mechanisms, protocol labels
- Include CTX-CHAINIDS entity IDs in metadata
- Reference format (e.g., "[Container-ID: api-gateway-01]" in tooltip or metadata tag)
- Format: Reference-grade (scalable canvas, all details, full legend)

### Naming Convention for Variants

- Business variant: `[APP_SHORT]-[DiagramType]-Business-v[X.Y].drawio`
- Technical variant: `[APP_SHORT]-[DiagramType]-Technical-v[X.Y].drawio`

---

## PRE-PUBLISH VISUAL QA GATE

> **Source:** [012 Gestalt & Visual Clarity Standards, Section 11.3] + [052, Section 12] ✅
> Run at Step 7. All items must pass. Any fail → revise before delivery.

```
═══════════════════════════════════════════════════════════════════
PRE-PUBLISH VISUAL QA GATE — SKILL-ARCHDIAG v5.0
Sources: [012 §11.3] + [052 §12]
═══════════════════════════════════════════════════════════════════

ANTI-PATTERN SCAN (from [052, Section 12])
[ ] A1 — No mixed abstraction levels
[ ] A2 — All notation defined in legend or standard
[ ] A3 — Legend present if non-standard symbols used
[ ] A4 — Consistent iconography throughout
[ ] A5 — No ambiguous arrows; every connector labeled
[ ] A6 — Color paired with shape/label; not sole encoding
[ ] B1 — ≤20 shape instances (God Diagram check)
[ ] B3 — No unverified performance numbers on arrows
[ ] B4 — Scope note present
[ ] B5 — Failure paths shown (for behavioral/process diagrams)
[ ] B6 — Non-functional concerns annotated
[ ] B7 — Trust boundaries present (for C4 L2, Sequence, Deployment)
[ ] C1 — Audience variant correct (business vs technical)
[ ] D1 — Version number + last-verified date present
[ ] E2 — Connector crossings ≤5
[ ] E3 — All edges labeled (protocol + data + direction)
[ ] F1 — Source-of-truth location declared
[ ] F2 — Version number in format vX.Y
[ ] F3 — Owner / team name declared

COGNITIVE LOAD CHECKS (from [012, §11.3])
[ ] ≤20 shape instances total
[ ] ≤4 top-level visual groups (working memory budget)
[ ] ≤5 semantic colors used
[ ] No decorative elements (shadows, gradients, icons without semantics)
[ ] Single coherent question answered by this diagram

GESTALT CHECKS (from [012, §8])
[ ] Proximity: Same-domain elements spatially clustered; external elements separated
[ ] Similarity: Every instance of an element type is visually identical (shape + color + style)
[ ] Continuity: All connectors use orthogonal routing; dominant flow direction consistent
[ ] Figure-Ground: Primary components have higher contrast than bounding containers
[ ] Common Region: Every bounding box has a text label and semantic meaning
[ ] Connectedness: Every connector is labeled and directional

COLOR AND CONTRAST CHECKS (WCAG 2.1 — W3C, 2018, Tier 1 ✅)
[ ] All text labels ≥ 4.5:1 contrast ratio against background
[ ] All graphical elements ≥ 3:1 contrast ratio
[ ] Diagram interpretable in grayscale
[ ] Red-green pairs reviewed for deuteranopia safety

TYPOGRAPHY CHECKS (from [012, §6])
[ ] All labels in sans-serif font
[ ] Component names ≥ 12pt
[ ] Connector labels ≥ 9pt
[ ] Component names on shape directly — no remote legends

ALIGNMENT AND WHITESPACE CHECKS (from [012, §11.2])
[ ] All elements on 10px grid
[ ] ≥20px spacing between elements within a group
[ ] ≥50px spacing between groups within a subsystem
[ ] ≥80px spacing between top-level subsystem boundaries
[ ] ≥20px arrowhead straight segment before target

XML STRUCTURE CHECKS (from references/xml-rules.md)
[ ] Root: <mxGraphModel adaptiveColors="auto"> — not <mxfile>
[ ] First two children: <mxCell id="0"/> and <mxCell id="1" parent="0"/>
[ ] Zero XML comments
[ ] All edges: expanded form with geometry child
[ ] All edge source/target reference existing vertex IDs
[ ] All IDs unique throughout
[ ] No unescaped & < > " in attribute values

GOVERNANCE METADATA (embedded in diagram)
[ ] last-verified date: [YYYY-MM-DD]
[ ] methodology-phase: [Phase N — Name]
[ ] audience-variant: [Business / Technical]
[ ] diagram-type: [Type ID from playbook]
[ ] chaining-ids: [CTX-CHAINIDS reference, if applicable]

CHAINING CONSISTENCY (if diagram is part of a chain)
[ ] Entity IDs match CTX-CHAINIDS
[ ] Entity names match CTX-APPREG canonical names
[ ] No new entities introduced without updating CTX-CHAINIDS

═══════════════════════════════════════════════════════════════════
RESULT:
[ ] ALL PASS → Deliver to user
[ ] ANY FAIL → Revise. Do not deliver with known violations.
═══════════════════════════════════════════════════════════════════
```

---

## METHODOLOGY-PHASE TAGGING

> **Source:** [030 System Design Methodology, Section 5] ✅

Every diagram generated by this skill must be tagged with its methodology phase. Embed in draw.io metadata using `<object>` tag with `methodology-phase` attribute, and display in diagram title area.

| Phase | Name | Diagrams Produced |
|---|---|---|
| Phase 2 | Constraints | C4 L1 Context |
| Phase 3 | Capabilities | DFD Level 0 |
| Phase 4 | High-Level Design | C4 L2 Container; Swimlane; High-level Sequence |
| Phase 5 | Detailed Design | C4 L3 Component; DFD L1–L2; ERD; Detailed Sequence; C4 L4 (optional) |
| Phase 6 | Validation | Deployment Diagram |
| Phase 7 | Documentation | Integration Map; Portfolio Landscape |
| Phase 8 | Review | All diagrams reviewed and baselined |

**Tagging format in diagram title:**
`[System Name] — [Diagram Type] — Phase [N]: [Phase Name] — v[X.Y]`

Example: `OrderService — C4 Container — Phase 4: High-Level Design — v1.0`

---

## PORTFOLIO MODE

> **Source:** [033 Portfolio & Multi-System Architecture] ✅
> Triggered when: "portfolio", "enterprise landscape", "all systems", "multiple systems", or when >3 System Context Packs are provided.

### Activation

Portfolio Mode is activated when:
- User explicitly requests a portfolio landscape diagram
- Multiple System Context Packs (TMPL-006) are provided as input
- CTX-APPREG contains >5 systems and user requests a "landscape" view

### Execution

1. Load all systems from CTX-APPREG
2. Run Phase 0 → confirm diagram type as [PLAYBOOK-PORTFOLIO]
3. Group systems by business domain (CTX-DOMAINS)
4. For each domain, generate a domain container using the Integration Map color scheme
5. Apply TIME model overlay if provided (Tolerate/Invest/Migrate/Eliminate) or Gartner quadrant if specified
6. Show inter-domain integration patterns from Integration Maps
7. Apply Anti-Pattern Guard: mandatory B1 (God Diagram) check — max 5 domains per canvas
8. Generate draw.io XML following xml-rules.md
9. Optionally: generate Structurizr DSL workspace file for interactive navigation

---

## OUTPUT EMITTERS

> **Source:** [060 Tooling & Output Standards] + [021 C4 Model, Section 14] ✅
> Primary: draw.io XML. Optional: Structurizr DSL, PlantUML, Mermaid.

### Primary Emitter — draw.io XML

All diagrams default to draw.io XML. See XML GENERATION SECTION below for full rules.

**Delivery:**

| Environment | File Output | Action |
|---|---|---|
| Claude.ai chat | `/mnt/user-data/outputs/[filename].drawio` | `present_files` tool |
| Claude Code CLI | Project working directory | Direct file write |
| VS Code / IntelliJ draw.io plugin | Workspace directory | `.drawio` opens natively |

### Optional Emitter — Structurizr DSL

Emit when: user requests "Structurizr DSL", "diagrams as code", or C4 diagram set for a Structurizr workspace.

```
workspace "[System Name]" "[Description]" {
  model {
    user = person "[User Name]" "[Description]"
    [systemName] = softwareSystem "[System Name]" "[Description]" {
      [containerName] = container "[Container Name]" "[Description]" "[Technology]"
    }
    user -> [systemName] "[Relationship]"
  }
  views {
    systemContext [systemName] "Context" { include * }
    container [systemName] "Container" { include * }
  }
}
```

### Optional Emitter — PlantUML

Emit when: user requests "PlantUML" or "@startuml" format.

Supported diagram types: C4 (via C4-PlantUML library), Sequence, State, ERD.

```
@startuml [DiagramName]
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Context.puml
[content per playbook]
@enduml
```

### Optional Emitter — Mermaid

Emit when: user requests "Mermaid" or markdown-embedded diagrams.

Supported diagram types: C4 Context (via C4 plugin), Sequence, ERD, State, Flowchart.

````
```mermaid
[diagramType]
  [content per playbook]
```
````

**Note:** Mermaid has limited C4 support. For L2/L3 diagrams, prefer Structurizr DSL or draw.io. Flag this limitation to the user.

---

## XML GENERATION — CRITICAL RULES (INLINE SUMMARY)

> Full rules in `references/xml-rules.md`. These are the invariants.

### Mandatory XML Structure

```xml
<mxGraphModel adaptiveColors="auto" dx="4000" dy="2500" grid="1" gridSize="10"
  guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="0"
  pageScale="1" pageWidth="1600" pageHeight="1200" math="0" shadow="0">
  <root>
    <mxCell id="0"/>
    <mxCell id="1" parent="0"/>
    [ALL VERTICES HERE FIRST]
    [ALL EDGES AFTER ALL VERTICES]
  </root>
</mxGraphModel>
```

### Vertex (Shape) Pattern

```xml
<mxCell id="meaningful-id" value="Label Text" style="rounded=1;whiteSpace=wrap;"
  vertex="1" parent="1">
  <mxGeometry x="100" y="100" width="120" height="60" as="geometry"/>
</mxCell>
```

### Governance Metadata Embedding

```xml
<object id="diagram-meta" label="" last-verified="2026-04-12"
  methodology-phase="Phase 4: High-Level Design"
  audience-variant="Technical"
  diagram-type="C4-L2-Container"
  chaining-ids="CTX-CHAINIDS-v1"
  placeholders="1">
  <mxCell style="text;html=1;strokeColor=none;fillColor=none;" vertex="1" parent="1">
    <mxGeometry x="10" y="10" width="400" height="20" as="geometry"/>
  </mxCell>
</object>
```

### Edge Pattern — ALWAYS EXPANDED, NEVER SELF-CLOSING

```xml
<mxCell id="edge-id" value="Label [Protocol]" style="edgeStyle=orthogonalEdgeStyle;rounded=1;"
  edge="1" source="vertex-a-id" target="vertex-b-id" parent="1">
  <mxGeometry relative="1" as="geometry" />
</mxCell>
```

### FORBIDDEN — Never generate these

```
FORBIDDEN: <!-- any XML comment -->
FORBIDDEN: <mxCell edge="1" source="a" target="b" />  (self-closing edge)
FORBIDDEN: <mxfile> wrapper
FORBIDDEN: base64-encoded or compressed XML
FORBIDDEN: double hyphens (--) anywhere in XML
FORBIDDEN: unescaped & < > " in attribute values
```

---

## EDGE AND LAYOUT RULES

- Use `edgeStyle=orthogonalEdgeStyle` — right-angle connectors standard
- Use `exitX`/`exitY` and `entryX`/`entryY` (0.0–1.0) to control connection sides
- Minimum **20px straight segment** before arrowheads
- `rounded=1` on edges for cleaner bends
- All shapes on 10px grid alignment
- Trust boundary enclosures: dashed stroke, light fill, semantic label required

### Container / Swimlane Rules

| Type | Style | When |
|---|---|---|
| Swimlane (titled) | `swimlane;startSize=30;` | Needs visible title bar |
| Group (invisible) | `group;` | No visual border needed |
| Custom container | `container=1;pointerEvents=0;` | Shape-as-container without connections |

Children: `parent="containerId"`, coordinates relative to container, not canvas.

---

## SHAPE LIBRARY GUIDANCE

**Standard shapes (no library search needed):**
Flowcharts, UML, ERD, C4, DFD, Integration Maps, Deployment using basic shapes.

**Domain shape libraries (use for):**
Cloud architecture (AWS/Azure/GCP), network topology (Cisco), P&ID, Kubernetes, BPMN-specific task types.

**Common shapes:**

| Shape | Style | Use |
|---|---|---|
| Rounded rectangle | `rounded=1;whiteSpace=wrap;` | Services, applications (default) |
| Diamond | `rhombus;whiteSpace=wrap;` | Decisions, gateways |
| Cylinder | `shape=cylinder3;whiteSpace=wrap;` | Databases, data stores |
| Person | `shape=mxgraph.basic.person;` | Users, actors |
| Document | `shape=mxgraph.flowchart.document;` | Files, documents |
| Dashed rectangle | `dashed=1;fillColor=none;` | Trust boundaries, domain containers |
| Filled circle | `ellipse;fillColor=#000000;` | State machine initial state |
| Thick-border circle | `ellipse;strokeWidth=4;` | State machine terminal state |

---

## FAILURE MODES AND PREVENTION

| Failure | Root Cause | Prevention |
|---|---|---|
| File is empty | Wrong root (mxfile) or XML parse error from comments | Use `<mxGraphModel>` root; zero comments |
| File won't open | Missing root cells, XML comments, double hyphens | Root cells mandatory; zero comments; no `--` |
| Floating connectors | Edge references non-existent vertex ID | Validate all source/target IDs before writing |
| Diagram blocked by Anti-Pattern Guard | God Diagram, missing trust boundaries, mixed abstraction | Resolve in Phase 0 / Step 3 before writing XML |
| WCAG contrast failure | Auto-palette colors with low contrast | Verify all text/background pairs before delivery |
| Chain ID mismatch | Container renamed between C4 L2 and Sequence/Deployment | Always read CTX-CHAINIDS before lifeline/artifact naming |
| Overlapping labels | Insufficient spacing | 200px horizontal / 120px vertical between shapes |
| Children escape container | Children using absolute vs relative coordinates | Coordinates relative to parent container |

---

## EDGE CASES AND HANDLING

| Scenario | Action |
|---|---|
| No sources provided | Ask what is available; never generate from imagination |
| Diagram type ambiguous | Run Phase 0 in full; present Diagram Selection Proposal |
| User selects wrong diagram for audience | Flag mismatch per Phase 0.2; ask for confirmation before overriding |
| User explicitly overrides Phase 0 | Honor the override; add warning note in gap report |
| >30 systems in source | Suggest Portfolio Mode + domain-scoped sub-diagrams |
| Source has no trust boundary information | Flag B7; generate with TBD zone labels; request source clarification |
| Chaining: Sequence before C4 L2 exists | Initialize CTX-CHAINIDS from sequence participants; note that C4 L2 should be generated next |
| User requests both business and technical variants | Generate both; deliver as separate files |
| Confidential/redacted information | Use `[REDACTED-SYSTEM]` placeholder; preserve structure |
| Mixed diagram request (multiple types in one ask) | Generate separate diagrams, one per type; chain where applicable |

---

## ERROR HANDLING

```
Warning: Generating Architecture Diagrams — Issue Detected:
- Problem: [Description]
- Anti-Pattern/Principle: [ID if applicable]
- Impact: [What this means for the diagram]
- Suggested action: [What the user should do]
```

| Error | Response |
|---|---|
| No sources | "I need at least one transcript, design doc, or description. What can you provide?" |
| XML validation failed | "XML has structural issues: [detail]. Regenerating with corrections..." |
| Anti-Pattern BLOCK triggered | "Diagram blocked: [Anti-Pattern ID] — [description of violation]. Please resolve: [fix]. Shall I proceed with the fix applied?" |
| Principle validator BLOCK | "Diagram blocked: Principle [PX] violation — [description]. Resolve before generation: [fix]" |
| WCAG contrast failure | "Color [X] on background [Y] fails WCAG 4.5:1 requirement. Auto-corrected to [Z]. Confirm?" |
| Chaining ID conflict | "Component '[name]' appears in CTX-CHAINIDS as '[canonical]' but source uses '[alias]'. Using canonical name. Updating CTX-APPREG with alias." |
| God Diagram threshold | "Diagram has [N] shape instances. 20 is the cognitive load ceiling. Suggest splitting into: [domain A diagram] + [domain B diagram]. Proceed with split or override?" |
| Emitter not supported | "Mermaid does not support [diagram type] natively. Recommend draw.io XML or PlantUML for this type. Proceed with draw.io?" |

---

## POST-EXECUTION

After delivery, check for context observations:
- New application → log to CTX-APPREG
- New chain ID → log to CTX-CHAINIDS
- Color/palette preference → log to CTX-PALETTE
- Audience preference → log to CTX-SYSPACK
- Methodology phase clarified → log to CTX-SYSPACK

Log to `pending-observations.ctx.md` per ARCH-001 Section 5 protocol.

---

## COMPANION SKILLS

| Skill | Integration |
|---|---|
| **verification** | All architectural claims in diagrams subject to HIGH verification standard |
| **research-orchestrator** | Invoke when researching an unfamiliar system before diagramming |
| **ai-documentation** | All documentation outputs (ADRs, architecture docs) follow TMPL-001/002 |
| **Skills Updater** | Maintains CTX-PALETTE, CTX-DOMAINS, CTX-APPREG, CTX-CHAINIDS freshness |
| **project-understanding** | Provides system context packs (TMPL-006) as Phase 0 input |

---

## SOURCES & REFERENCES

### Primary Sources (Tier 1) ✅

| Source | Title | Date | Relevance |
|---|---|---|---|
| jgraph/drawio-mcp | Official draw.io skill-cli SKILL.md | Apr 2026 | Root XML structure, file format, forbidden patterns |
| jgraph/drawio-mcp | shared/xml-reference.md | Apr 2026 | Edge routing, containers, layers, metadata, dark mode |
| W3C World Wide Web Consortium | WCAG 2.1 | 2018 | Color contrast standards (4.5:1 / 3:1) |
| Sweller, J. (Wiley/Cognitive Science) | Cognitive Load During Problem Solving: Effects on Learning | 1988 | 3–4 chunk working memory limit |
| Wagemans et al. (PMC/Psychological Bulletin) | A Century of Gestalt Psychology in Visual Perception | 2012 | Gestalt grouping principles |
| OMG / ISO/IEC 19510:2013 | BPMN 2.0 Specification | 2011/2013 | Swimlane/BPMN notation |
| OMG | UML 2.5.1 Specification | 2017 | Sequence, Deployment, State notation |
| ISO/IEC/IEEE 42010:2022 | Architecture Description | 2022 | Architecture viewpoints, completeness, governance |
| Gilbert & Lynch (MIT) | CAP Theorem Formal Proof | 2002 | Distributed system trade-off principles |
| Peter Chen (ACM TODS) | The Entity-Relationship Model | 1976 | ERD foundation |

### Secondary Sources (Tier 2) ✅

| Source | Title | Date | Relevance |
|---|---|---|---|
| Simon Brown (c4model.com) | C4 Model for Software Architecture | Current | C4 levels 1–4, God Diagram, anti-patterns |
| Tom DeMarco | Structured Analysis and System Specification | 1979 | DFD formalism origin |
| Gane & Sarson | Structured Systems Analysis: Tools and Techniques | 1979 | Gane-Sarson DFD notation |
| Rummler & Brache | Improving Performance | 1990 | Swimlane format origin |
| Ford, Parsons & Kua | Building Evolutionary Architectures | 2017 | Architectural drift and fitness functions |

### Internal Corpus Sources

| Source | Document ID | Used For |
|---|---|---|
| System Design Bible | [000] | Root governance structure |
| Core Architectural Principles | [011] | 12 Principle Validators |
| Gestalt & Visual Clarity Standards | [012] | Layout Engine + QA Gate |
| Diagramming Frameworks | [020] | Decision matrix, chaining model |
| C4 Model | [021] | C4 playbooks |
| Data Flow Diagrams | [023] | DFD playbook |
| Swimlane & Workflow Diagrams | [024] | Swimlane/BPMN playbook |
| Sequence, ERD & Deployment | [025] | Sequence, ERD, Deployment playbooks |
| System Design Methodology | [030] | Phase tagging |
| Portfolio & Multi-System Architecture | [033] | Portfolio mode |
| Living Architecture & Drift Control | [040] | Drift awareness, living-context link |
| Business-Facing Diagramming | [051] | Stakeholder variants |
| Common Pitfalls & Anti-Patterns | [052] | Anti-Pattern Guard (27 patterns) |
| Tooling & Output Standards | [060] | Output emitter selection |
| ARCH-001 | Skill Architecture | Context framework, living context |

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---|---|---|---|---|
| 5.0 | 2026-04-12 | Major | Complete methodology-driven upgrade. Added: Phase 0 Diagram Selection (020 §5 decision matrix); per-diagram playbooks for C4 L1–L4, DFD (Gane-Sarson + Yourdon-Coad), Swimlane/BPMN, Sequence, ERD (Crow's Foot), Deployment, Integration Map, State, Portfolio; 12 Principle Validators from 011; Gestalt + WCAG Layout Engine from 012; Anti-Pattern Guard (27 patterns from 052); Chaining Model with CTX-CHAINIDS from 020 §8; Stakeholder Variants (Business/Technical) from 051; Pre-Publish Visual QA Gate from 012 §11.3 + 052 §12; Methodology Phase Tagging from 030; Portfolio Mode from 033; optional Structurizr DSL/PlantUML/Mermaid emitters from 021 §14 + 060; full bidirectional cross-references to all six Bible branches (000–060); Documentation Standards compliance (RULES-001). | System Design Bible (000–060) authored; full alignment of SKILL-ARCHDIAG with all six pillars required. Plan 2 approved by project owner 2026-04-12. |
| 4.0 | 2026-04-11 | Major | XML engine overhaul: replaced xml-rules.md with jgraph/drawio-mcp official sources. Root element changed from `<mxfile>` to `<mxGraphModel adaptiveColors="auto">`. Zero XML comments policy. Edge geometry child mandated. | User-reported empty/non-opening files. Root cause: misalignment with official draw.io spec. |
| 3.0 | 2026-03-29 | Major | Living Context integration (ARCH-001/002). Expanded from 3 to 9 diagram types. 50+ trigger phrases. Externalized colors to CTX-PALETTE. Post-execution observation logging. | Alignment with Skill Development Suite REFINE workflow |
| 2.0 | 2026-03-27 | Major | Refactored to progressive disclosure architecture. References extracted. | PROMPT-001 alignment |
| 1.0 | 2026-03-27 | Initial | Full skill creation | Initial creation |

---

### Change Detail: Version 4.0 to 5.0

**Date:** 2026-04-12
**Sections Changed:** All sections rewritten or significantly extended.

**Initial Thought:**
Skill v4.0 was a production-grade XML generation engine but had no methodology awareness, no audience intelligence, no anti-pattern prevention, no Gestalt/WCAG enforcement, and no chaining. It produced correct XML but could produce the wrong diagram for the wrong audience without any warning.

**New Finding:**
The System Design Bible (000–060) was authored between 2026-04-11 and 2026-04-12. It contains a complete, verified knowledge corpus covering: 12 architectural principles (011), Gestalt/WCAG visual clarity rules (012), a diagram-selection decision matrix (020 §5), a chaining model (020 §8), full diagram type specifications (021–025), methodology phases and diagram sequencing (030 §5), portfolio architecture (033), drift awareness (040), stakeholder communication (051), and a complete anti-pattern catalog with 27 named patterns (052). All of these were verified against Tier 1/2 primary sources.

**Changes Made:**
- Phase 0 Diagram Selection: mandatory before any XML generation; uses [020 §5] decision matrix
- Per-diagram playbooks: 11 playbooks replacing the previous flat diagram router
- 12 Principle Validators: [011] principles applied as go/warn/block checks at Step 4
- Gestalt + WCAG Layout Engine: [012] rules applied at Step 5; working memory budget, proximity, similarity, WCAG contrast all enforced
- Anti-Pattern Guard: all 27 [052] patterns checked at Step 3; 4 block generation
- Chaining Model: CTX-CHAINIDS initialized at C4 L2; reused by Sequence and Deployment
- Stakeholder Variants: Business and Technical render modes from [051]
- Pre-Publish Visual QA Gate: [012 §11.3] + [052 §12] combined; run at Step 7
- Methodology Phase Tagging: [030 §5] phases embedded as diagram metadata
- Portfolio Mode: [033] activated for multi-system scope
- Output Emitters: Structurizr DSL, PlantUML, Mermaid added as optional emitters per [060]
- Documentation Standards: RULES-001 applied; bidirectional cross-references to all 21 corpus documents; revision history updated; sources organized by tier

**Impact on Other Documents:**
- `references/xml-rules.md`: Unchanged (v4.0 is correct)
- `references/c4-spec.md`: Playbooks now supersede most content; spec remains for XML pattern reference
- `references/data-flow-spec.md`, `sequence-spec.md`, `erd-spec.md`, `deployment-spec.md`, `integration-spec.md`: Playbooks extend these; specs remain for XML pattern reference
- New reference files needed: `references/swimlane-spec.md`, `references/state-spec.md` (to be authored; current skill uses inline playbook content until these exist)
- `context-templates/`: Add `chaining-id-registry.ctx.template.md` and `system-context-pack.ctx.template.md`

---

## DOCUMENTATION QUALITY GATE — FINAL STATUS

```
STRUCTURE
[x] document_id assigned: SKILL-ARCHDIAG v5.0
[x] Parent document referenced: ARCH-001 Skill Architecture & Living Context Framework
[x] Bidirectional cross-references to all 21 System Design Bible documents
[x] Naming convention: SKILL.md (skill format, not standard doc)
[x] Revision History table present and current (v1.0 → v5.0)
[x] Change Detail block for major version (v4.0 → v5.0)

CONTENT
[x] Phase 0 Diagram Selection — mandatory first step with decision matrix
[x] 11 per-diagram playbooks — each with purpose, audience, inputs, build order, notation, colors, anti-patterns, QA checklist
[x] 12 Principle Validators from [011]
[x] Gestalt + WCAG Layout Engine from [012]
[x] Anti-Pattern Guard — all 27 patterns from [052]
[x] Chaining Model with CTX-CHAINIDS
[x] Stakeholder Variants (Business / Technical) from [051]
[x] Pre-Publish Visual QA Gate combining [012 §11.3] + [052 §12]
[x] Methodology Phase Tagging from [030 §5]
[x] Portfolio Mode from [033]
[x] Optional emitters: Structurizr DSL, PlantUML, Mermaid

VERIFICATION
[x] Every design claim traced to a branch document with section reference
[x] All Tier 1/2 sources cited for external claims
[x] No unsourced statistics or floating "studies show" claims
[x] Verification level: HIGH (design/technical document per governance rules)

REFERENCES
[x] Sources table: Tier 1, Tier 2, Internal Corpus
[x] Cross-references use correct format: [Document ID, Section X.X]
[x] Bidirectional: each referenced document listed; reverse reference protocol noted

DOCUMENT IS READY.
```

---

*Skill SKILL-ARCHDIAG v5.0 — Governed by [000 System Design Bible]. All external claims traced to Tier 1/2 sources. Bidirectional references to all six Bible branches (010–060). Last updated: 2026-04-12.*

**END OF SKILL.MD**
