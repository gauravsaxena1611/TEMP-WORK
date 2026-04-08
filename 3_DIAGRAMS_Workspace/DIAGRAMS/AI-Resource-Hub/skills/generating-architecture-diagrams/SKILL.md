---
name: generating-architecture-diagrams
description: Generates valid, editable draw.io XML architectural diagrams from project transcripts, design documents, Confluence pages, and PPT notes. Produces HLLFD, C4 Context/Container/Component diagrams, Sequence Diagrams, Data Flow Diagrams (DFD), ERD, Deployment Diagrams, and Integration Maps with domain color coding and workflow numbering. Use when user says create architecture diagram, generate HLLFD, logical flow diagram, C4 diagram, sequence diagram, data flow diagram, DFD, ERD, entity relationship, deployment diagram, infrastructure diagram, network diagram, integration map, interface diagram, system landscape, draw.io diagram, drawio XML, upstream downstream, solution architecture, application architecture, microservices diagram, API flow, update diagram, review drawio, visualize architecture, map integrations, show architecture, convert to drawio, system context, technical architecture, or requests any architectural visualization as a drawio file.
---

# GENERATING ARCHITECTURE DIAGRAMS
## Enterprise draw.io Architectural Diagram Generation Engine

**Skill ID:** SKILL-ARCHDIAG  
**Parent:** ARCH-001 Skill Architecture & Living Context Framework  
**Version:** 3.0  
**Created:** March 27, 2026  
**Last Updated:** March 29, 2026  
**Status:** Production  

Produces valid draw.io XML architectural diagrams from unstructured project
inputs. Outputs editable `.drawio` files following organization domain standards.

---

## CORE MANDATE

- Generate **valid, well-formed draw.io XML** — zero broken files
- Extract facts **only** from provided sources — NEVER assume or fabricate
- Mark missing information as `TBD – not specified in sources`
- Label every system with **Short Name + ID** when available
- Apply organization color palette and workflow numbering consistently
- Read color palette from context (`CTX-PALETTE`) or fall back to defaults in `references/color-palette.md`
- Output filename: `[APP_SHORT]-[DiagramType].drawio`
- Append `-v2`, `-v3` etc. on iterative updates

---

## WHEN TO USE

### Diagram Type Router

| User Says / Requests | Diagram Type | Reference to Load |
|----------------------|-------------|-------------------|
| "HLLFD", "high level logical flow", "logical flow", "architecture diagram", "upstream downstream", "high level architecture" | HLLFD | `references/hllfd-spec.md` |
| "C4 context", "context diagram", "system context", "who uses the system" | C4 Context (L1) | `references/c4-spec.md` |
| "C4 container", "container diagram", "what's inside the system", "technology building blocks" | C4 Container (L2) | `references/c4-spec.md` |
| "C4 component", "component diagram", "internal structure of [container]", "zoom into [service]" | C4 Component (L3) | `references/c4-spec.md` |
| "sequence diagram", "interaction flow", "message flow", "call sequence", "request-response flow", "API flow diagram" | Sequence Diagram | `references/sequence-spec.md` |
| "data flow", "DFD", "data flow diagram", "how data moves", "data pipeline diagram", "ETL diagram", "data lineage" | Data Flow Diagram | `references/data-flow-spec.md` |
| "ERD", "entity relationship", "database diagram", "data model", "schema diagram", "table relationships" | ERD | `references/erd-spec.md` |
| "deployment diagram", "infrastructure diagram", "network diagram", "cloud architecture", "hosting diagram", "topology diagram", "environment diagram" | Deployment Diagram | `references/deployment-spec.md` |
| "integration map", "interface diagram", "system integration", "integration landscape", "connectivity diagram", "API map" | Integration Map | `references/integration-spec.md` |
| "update this diagram", "add [integration] to the diagram", "modify the drawio" | Update Existing | Parse existing file or conversation history |
| "review this drawio", "fix colors", "apply standards", "standardize this diagram" | Review/Standardize | Parse file, compare against standards |
| "system landscape", "solution architecture", "application architecture", "technical architecture", "software architecture", "microservices diagram" | Default: HLLFD | Confirm type with user before proceeding |
| "convert image to drawio", "recreate this diagram", "make this editable" | Recreate from Image | Analyze image, recreate as editable XML |
| No type specified | Default: HLLFD | Confirm with user before proceeding |

### Comprehensive Trigger Phrases

**Creation triggers:** create, generate, build, make, produce, draw, design, draft, sketch, render, compose, construct, visualize, map out, lay out, put together, diagram, chart

**Diagram noun triggers:** architecture diagram, system diagram, flow diagram, logical flow, block diagram, box-and-arrow diagram, component diagram, integration diagram, interface diagram, connectivity map, dependency map, service map, topology map, landscape diagram, overview diagram

**Technology triggers:** draw.io, drawio, diagrams.net, mxGraph, XML diagram, editable diagram

**Context triggers:** from transcript, from design doc, from Confluence, from PPT, from specification, from meeting notes, from requirements, from project description, from this document, based on this information

**Modification triggers:** update diagram, revise diagram, add to diagram, extend diagram, modify diagram, iterate on diagram, refine diagram, fix diagram, improve diagram, apply standards to diagram, standardize diagram, recolor diagram

---

## REQUIRED CONTEXTS

<!-- Living Context Architecture [ARCH-001, Section 2] -->

| Context ID | Name | Type | Purpose | If Missing |
|------------|------|------|---------|------------|
| CTX-PALETTE | org-color-palette | DECLARED | Organization domain colors, arrow colors, component colors | Use default palette from `references/color-palette.md` and ask user if they want to customize |
| CTX-DOMAINS | org-domain-groups | DECLARED | How the organization classifies system domains (e.g., Core Platform, Enterprise Services, External) | Ask user for their domain classification; use generic Internal/External/Third-Party if unknown |
| CTX-APPREG | app-registry | DECLARED | Known applications, short names, IDs (CAI/CSI), domain ownership | Build incrementally from source documents; ask user for app identity on each diagram |

**Context Resolution:** Before executing this workflow, resolve all required
contexts per the Living Context Framework [ARCH-001, Section 5]:
1. Check if each required context file exists
2. If missing → For CTX-PALETTE and CTX-DOMAINS, use defaults from `references/color-palette.md` and ask user if customization is desired; for CTX-APPREG, build from source material
3. If stale (>60 days) → Confirm with user: "Are your domain colors and groupings still current?"
4. If current → Proceed to workflow

---

## INPUTS

### Required
- **Application identity**: Name, Short Name, ID/CAI/CSI (ask if not provided)
- **At least one source**: Transcript, design doc, Confluence page, PPT, existing diagram, or description
- **Diagram type**: One of the supported types (default HLLFD if unspecified)

### Optional
- Additional source documents (enables cross-validation)
- Existing `.drawio` file (for updates)
- Specific workflow focus ("just the batch flow")
- Custom output filename
- Organization color palette context file
- Target audience specification (technical, business, executive)

### Validation
- No application name → ask before proceeding
- No source documents → ask what's available; never generate from imagination
- ID/CAI/CSI not in sources → use TBD, never invent
- Diagram type ambiguous → present options and confirm

---

## PROCESS

### Step 1: Load References

Before generating any diagram, read these files:

**Always load (every diagram):**
- `references/xml-rules.md` — Mandatory XML generation rules and failure mode prevention
- `references/color-palette.md` — Default domain colors, arrow colors, component colors
- Check for `CTX-PALETTE` context override → if exists, use organization colors instead of defaults

**Load by diagram type:**

| Diagram Type | Load These References |
|-------------|---------------------|
| HLLFD | `references/hllfd-spec.md` + `references/workflow-numbering.md` |
| C4 Context, Container, or Component | `references/c4-spec.md` |
| Sequence Diagram | `references/sequence-spec.md` |
| Data Flow Diagram | `references/data-flow-spec.md` |
| ERD | `references/erd-spec.md` |
| Deployment Diagram | `references/deployment-spec.md` |
| Integration Map | `references/integration-spec.md` |

**When building XML elements:**
- `references/xml-patterns.md` — Copy-paste-ready XML snippets

**When template reference needed:**
- `templates/High_Level_Diagram.drawio` — Blank HLLFD structure
- `templates/ATPC_High_Level_Diagram_v2.drawio` — Working sample with styles

### Step 2: Source Material Analysis

Read ALL provided documents completely. Extract and inventory:

**For every system mentioned:**
- Full name, Short name, ID/CAI/CSI (if stated)
- Owning domain — read from `CTX-DOMAINS` context or ask user
- Role: upstream source, central component, or downstream consumer
- Integration: protocol (REST, Kafka, DB, File, Batch, MQ, gRPC, GraphQL, SOAP, SFTP), data exchanged, direction, sync/async

**For every user type:**
- Role name and acronym, entry point, workflow steps, actions performed

**For the central application:**
- Internal components (UI, backend, batch jobs, pollers, adapters, microservices)
- Databases and their purposes
- Message queues, caches, and middleware
- Hosting environment (only if stated)

**Extraction rules:**
- Preserve naming exactly as per sources (case, spacing, acronyms)
- Capture every acronym with its expansion
- If detail is missing, leave blank — do not infer

### Step 3: Cross-Validation (Multi-Source Only)

When multiple sources are provided:
- Compare facts across documents
- Sources agree → confirmed
- Sources conflict → prefer most recent/most explicit; if unresolvable mark `TBD – conflicting descriptions`

### Step 4: Architecture Model

Organize extracted facts into diagram structure appropriate for the diagram type:

**For HLLFD:** Tri-sectional layout — Inputs, Processing, Consumes — per `references/hllfd-spec.md`
**For C4 Context:** Central system with radial users and external systems — per `references/c4-spec.md`
**For C4 Container:** System boundary with internal containers — per `references/c4-spec.md`
**For C4 Component:** Container boundary with internal components — per `references/c4-spec.md`
**For Sequence:** Lifelines and ordered message exchanges — per `references/sequence-spec.md`
**For DFD:** Processes, data stores, external entities, data flows — per `references/data-flow-spec.md`
**For ERD:** Entities, attributes, relationships, cardinality — per `references/erd-spec.md`
**For Deployment:** Nodes, containers, artifacts, infrastructure — per `references/deployment-spec.md`
**For Integration Map:** Systems, interfaces, protocols, data flows — per `references/integration-spec.md`

### Step 5: Validation Pass

Check every element against sources:
- System explicitly mentioned? → keep
- Integration type stated or assumed? → if assumed, remove or mark TBD
- ID/CAI/CSI confirmed? → if not, mark TBD
- Any element from templates but not from sources? → remove
- **Zero fabricated elements must survive this pass**

### Step 6: XML Generation

Follow `references/xml-rules.md` strictly. Generate in this exact order:

1. `<mxfile>` wrapper with `<diagram>` and `<mxGraphModel>` (`page="0"`)
2. Mandatory root cells (`id="0"` and `id="1"`)
3. All container/swimlane elements (structural scaffolding)
4. **ALL vertex shapes** (systems, components, users, databases, entities)
5. **ALL edge connectors** (AFTER all vertices — prevents orphaned references)
6. Legend section (where applicable per diagram type spec)
7. Notes section (where applicable)
8. Metadata notes (where applicable)

Apply styles from `CTX-PALETTE` context or `references/color-palette.md` defaults.
Apply numbering from `references/workflow-numbering.md` (for HLLFD and Integration Maps).
Use structural templates from `references/xml-patterns.md`.

### Step 7: Output Validation

Run the universal checklist before delivery:

```
☐ XML well-formed (all tags properly closed)
☐ mxCell id="0" and id="1" present
☐ All IDs unique across diagram
☐ All edges reference valid source and target IDs
☐ Vertices defined before edges in XML order
☐ page="0" set (infinite canvas)
☐ All shapes use correct styles per type spec
☐ Font sizes consistent (title 20, headers 16, components 13, details 10)
☐ No assumed or fabricated information
☐ TBD markers present for any gaps
```

Then run the diagram-type-specific checklist from the loaded reference spec.

### Step 8: Delivery

- **Filename**: `[APP_SHORT]-[DiagramType].drawio`
  - HLLFD: `ATPC-HLLFD.drawio`
  - C4 Context: `ATPC-C4-Context.drawio`
  - C4 Container: `ATPC-C4-Container.drawio`
  - C4 Component: `ATPC-C4-Component-[ContainerName].drawio`
  - Sequence: `ATPC-Sequence-[FlowName].drawio`
  - DFD: `ATPC-DFD.drawio`
  - ERD: `ATPC-ERD.drawio`
  - Deployment: `ATPC-Deployment.drawio`
  - Integration Map: `ATPC-IntegrationMap.drawio`
  - Updates: append `-v2`, `-v3` etc.
- **In Claude.ai chat**: Write to `/mnt/user-data/outputs/` and present via `present_files`
- **In Claude Code**: Write to project working directory
- **Multi-diagram requests**: Always generate separate files, never multi-page
- **After delivery**, provide in chat:
  - Brief summary of what was included
  - List of TBD items with what sources would resolve them
  - Any source conflicts found
  - Suggestion for next iteration or complementary diagram type

---

## DECISION POINTS

| # | Condition | Action |
|---|-----------|--------|
| D1 | Diagram type not specified | Default to HLLFD, confirm with user |
| D2 | Multiple sources provided | Run cross-validation (Step 3) |
| D3 | Sources insufficient for complete diagram | Generate partial with TBD markers, append gap report, title includes "Draft / Incomplete" |
| D4 | Existing `.drawio` file provided | Parse existing XML, merge changes, output with `-v[N]` suffix |
| D5 | Conversational iteration (no file) | Reconstruct from conversation history, state "modifying from our previous generation" |
| D6 | Application name/ID unknown | Ask user; or use TBD in title and filename |
| D7 | Diagram complexity >30 systems | Suggest splitting by domain or workflow |
| D8 | User requests unsupported diagram type | Inform what's available, offer closest match, or suggest building with generic XML patterns |
| D9 | User provides image (not .drawio) | Analyze image, recreate as editable XML using appropriate diagram type spec |
| D10 | Multiple diagram types requested | Generate separate files for each; suggest logical ordering |
| D11 | Organization palette context exists | Use CTX-PALETTE colors instead of default palette |
| D12 | User requests diagram for audience type | Adjust detail level — executive: C4 Context; technical: HLLFD or C4 Container; developer: C4 Component or Sequence |

---

## EDGE CASES

| Scenario | How to Handle |
|----------|---------------|
| No sources provided | Ask what's available; never generate from imagination |
| Rambling/conversational transcript | Extract facts methodically; ignore filler |
| System mentioned but no integration details | Show with TBD arrow, note in gap report |
| Multiple names for same system | Use primary name, note aliases in metadata |
| Future/planned integrations in sources | Include with dashed border and "PLANNED" label |
| Existing file uses different color scheme | Ask: "Apply organization palette or preserve existing?" |
| Unsupported diagram type requested | Inform what's available, offer closest match |
| Confidential/redacted information | Use `[REDACTED-SYSTEM]` placeholder, preserve structure |
| Very complex diagram (>30 systems) | Suggest splitting into multiple focused diagrams |
| User provides image (not .drawio) | Analyze image, recreate as editable XML |
| Mixed diagram request ("show me the architecture and the data flow") | Generate separate diagrams, one per type |
| Source mentions technologies without system names | Group as "Unnamed [Technology] Service (TBD)" |
| Conflicting domain classifications | Default to CTX-DOMAINS context; if unavailable, ask user |

---

## ERROR HANDLING

```
⚠️ Generating Architecture Diagrams — Issue Detected:
- Problem: [Description]
- Impact: [What this means for the diagram]
- Suggested action: [What the user should do]
```

| Error | Response |
|-------|----------|
| No sources | "I need at least one transcript, design doc, or description. What sources can you provide?" |
| XML validation failed | "XML has structural issues: [detail]. Regenerating with corrections..." |
| Conflicting sources | "Sources disagree on [detail]. Document A says X, Document B says Y. Marked as TBD. Which takes priority?" |
| App name missing | "Application name/ID not found. Please provide: 1) Full name, 2) Short name, 3) ID or confirm TBD" |
| Reference spec missing | "The [diagram type] specification is not available. I can generate using general draw.io best practices, or we can switch to a supported type." |
| Diagram too complex | "This diagram has [N] systems. I recommend splitting into [suggestion]. Shall I proceed with the full diagram or split?" |

---

## ENVIRONMENT BEHAVIOR

| Environment | File Output | Delivery |
|-------------|-------------|----------|
| Claude.ai chat | `/mnt/user-data/outputs/[filename].drawio` | `present_files` tool |
| Claude Code CLI | Project working directory | Direct file write |
| VS Code Copilot | Workspace directory | Save as `.drawio` file |

---

## POST-EXECUTION

After completing the workflow:
- Check if any observations during execution suggest context updates:
  - New application discovered not in CTX-APPREG → log observation
  - Color scheme preference expressed by user → log for CTX-PALETTE
  - Domain classification revealed → log for CTX-DOMAINS
- If observations exist → Log to `pending-observations.ctx.md` with date, affected context, current value, observed value, and confidence level
- Deliver output to user

---

## COMPANION SKILLS

| Skill | Integration |
|-------|-------------|
| **verification** | All facts in diagrams subject to verification standards |
| **research-orchestrator** | Invoke first when researching an application before diagramming |
| **Documentation Standards (RULES-001)** | Follow three-digit numbering when diagram is part of document set |
| **Skills Updater** | Maintains freshness of CTX-PALETTE, CTX-DOMAINS, CTX-APPREG contexts |

---

## SOURCES & REFERENCES

| Source | Title | Date | Relevance | Tier |
|--------|-------|------|-----------|------|
| draw.io | Official AI Generation Guide | Current | XML rules 1-9 | Tier 1 ✅ |
| drawio-ninja (GitHub) | drawio-ninja Research Project | Current | Vertex-before-edges, page=0, meaningful IDs | Tier 2 ✅ |
| GenAI-DrawIO-Creator | arXiv 2601.05162 | Jan 2026 | Validation pipeline, ordering rules | Tier 1 ✅ |
| Simon Brown | C4 Model (c4model.com) | Current | C4 diagram levels 1-3 specification | Tier 1 ✅ |
| Anthropic | Skill Authoring Best Practices | Current | Skill structure, progressive disclosure | Tier 1 ✅ |
| ARCH-001 | Skill Architecture & Living Context Framework | Mar 2026 | Context architecture standard | Project ✅ |
| ARCH-002 | Context Template Generator | Mar 2026 | Context template standard | Project ✅ |
| PROMPT-001 | Skill Creator Pro | Mar 2026 | Skill creation workflow | Project ✅ |
| RULES-001 | Documentation Standards | Jan 2026 | Document formatting standard | Project ✅ |
| ISO/IEC/IEEE 42010:2022 | Architecture Description | 2022 | Architecture documentation standard | Tier 1 ✅ |
| UML 2.5.1 | OMG Specification | Dec 2017 | Sequence and deployment diagram notation | Tier 1 ✅ |
| Tom DeMarco | Structured Analysis DFD | 1979 | Data Flow Diagram notation standard | Tier 1 ✅ |
| Peter Chen | ER Model | 1976 | Entity-Relationship diagram notation | Tier 1 ✅ |

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 3.0 | 2026-03-29 | Major | Complete skill refinement: Integrated Living Context Architecture (ARCH-001/002) with Required Contexts section and context-templates/. Expanded from 3 to 9 diagram types (added C4 Component, Sequence, DFD, ERD, Deployment, Integration Map). Rewrote description as single-line (fixed YAML anti-pattern). Added 50+ trigger phrases. Externalized ICRM-specific colors to CTX-PALETTE context. Added Post-Execution observation section. Added Documentation Standards compliance. Added comprehensive Sources table with tier ratings. | Alignment with Skill Development Suite REFINE workflow, ARCH-001, ARCH-002, PROMPT-001, RULES-001, and industry diagramming standards |
| 2.0 | 2026-03-27 | Major | Refactored from single-file to progressive disclosure architecture. SKILL.md reduced from 716 to ~400 lines. References extracted. Templates bundled. | PROMPT-001 framework alignment |
| 1.0 | 2026-03-27 | Initial | Full skill creation with all content in single SKILL.md | Initial creation |

### Change Detail: Version 2.0 → 3.0

**Date:** 2026-03-29  
**Sections Changed:** All sections rewritten or expanded  

**Initial Thought:**  
Skill supported only 3 diagram types (HLLFD, C4 Context, C4 Container) with ICRM-specific color palette hardcoded in the kernel. Description used multi-line YAML `>` syntax. No Living Context Architecture integration.

**New Finding:**  
Skill Development Suite REFINE workflow identified: (1) YAML description anti-pattern causing potential trigger failures, (2) ICRM-specific data violates ARCH-001 separation of concerns, (3) missing industry-standard diagram types limits utility, (4) trigger coverage was incomplete for common architectural visualization requests.

**Changes Made:**  
- Single-line description with 50+ trigger phrases across 5 categories
- 9 diagram types with dedicated reference specs
- Living Context Architecture with 3 context dependencies (CTX-PALETTE, CTX-DOMAINS, CTX-APPREG)
- Context templates directory per ARCH-002
- Post-Execution observation logging
- Documentation Standards (RULES-001) compliance in header
- Comprehensive Sources table with verification tier ratings

**Impact on Other Documents:**  
- New reference specs added: `sequence-spec.md`, `data-flow-spec.md`, `erd-spec.md`, `deployment-spec.md`, `integration-spec.md`
- Context templates added: `org-color-palette.ctx.template.md`, `org-domain-groups.ctx.template.md`, `app-registry.ctx.template.md`
- README.md updated with new architecture and diagram types
- CHANGELOG.md updated with v3.0 entry

---

**END OF SKILL.MD**
