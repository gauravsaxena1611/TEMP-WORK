# Generating Architecture Diagrams — Claude Skill

**Skill ID:** SKILL-ARCHDIAG  
**Version:** 3.0  
**Last Updated:** March 29, 2026  

Transforms Claude into an enterprise-grade architectural diagramming engine,
producing valid, editable draw.io XML from unstructured inputs like call
transcripts, project documents, Confluence pages, and design specifications.

## What It Does

### 9 Diagram Types

| Diagram Type | Description | Reference Spec |
|-------------|-------------|---------------|
| **HLLFD** | High Level Logical Flow — tri-sectional layout with domain grouping | `hllfd-spec.md` |
| **C4 Context (L1)** | System boundary with users and external systems | `c4-spec.md` |
| **C4 Container (L2)** | Internal containers within system boundary | `c4-spec.md` |
| **C4 Component (L3)** | Components within a single container | `c4-spec.md` |
| **Sequence Diagram** | Chronological message flow between participants | `sequence-spec.md` |
| **Data Flow Diagram** | How data moves between processes and stores | `data-flow-spec.md` |
| **ERD** | Entity-Relationship — database schema visualization | `erd-spec.md` |
| **Deployment Diagram** | Infrastructure topology and artifact placement | `deployment-spec.md` |
| **Integration Map** | System-to-system interface landscape | `integration-spec.md` |

### Core Capabilities

- Generates **valid, well-formed draw.io XML** — zero broken files
- Extracts architectural facts from transcripts, design docs, PPTs, Confluence
- Applies organization domain color palette (customizable via Living Context)
- Enforces workflow numbering standards (7 distinct formats for HLLFD)
- Guards against 10 known LLM XML generation failure modes
- Marks missing information as TBD — never assumes or fabricates
- Supports both file-based and conversational iterative refinement
- Outputs with consistent `[APP_SHORT]-[DiagramType].drawio` naming

## Skill Architecture

Uses **progressive disclosure** — Claude loads only what's needed per request.

Implements **Living Context Architecture** [ARCH-001] — organization-specific
data (colors, domains, app registry) lives in context files, not in the skill.

```
generating-architecture-diagrams/
├── SKILL.md                           # Core workflow + context declarations
├── references/
│   ├── color-palette.md               # Default colors (overridden by CTX-PALETTE)
│   ├── xml-rules.md                   # XML generation rules (every diagram)
│   ├── xml-patterns.md                # Copy-paste XML snippets (when building)
│   ├── workflow-numbering.md          # Numbering standards (HLLFD, Integration)
│   ├── hllfd-spec.md                  # HLLFD layout specification
│   ├── c4-spec.md                     # C4 Levels 1, 2, 3 specification
│   ├── sequence-spec.md              # Sequence diagram specification
│   ├── data-flow-spec.md             # DFD specification
│   ├── erd-spec.md                    # ERD specification
│   ├── deployment-spec.md            # Deployment diagram specification
│   └── integration-spec.md           # Integration map specification
├── templates/
│   ├── High_Level_Diagram.drawio      # Blank HLLFD template
│   └── ATPC_High_Level_Diagram_v2.drawio  # Working sample with styles
├── context-templates/                 # Living Context Architecture [ARCH-002]
│   ├── README.md                      # Template index
│   ├── org-color-palette.ctx.template.md      # Organization color overrides
│   ├── org-domain-groups.ctx.template.md      # Domain classification rules
│   └── app-registry.ctx.template.md           # Known applications registry
├── README.md
├── CHANGELOG.md
└── SETUP_GUIDE.md
```

### Living Context Integration

This skill separates stable workflow logic from volatile project data:

| Context | Purpose | If Missing |
|---------|---------|-----------|
| **CTX-PALETTE** | Organization colors | Uses default palette |
| **CTX-DOMAINS** | Domain classification | Uses generic Internal/External |
| **CTX-APPREG** | Application registry | Builds incrementally |

Context files live at the project level, not inside the skill package.
They are created on first use and updated as Claude learns about the project.

## Installation

### Claude.ai Projects (Chat)
1. Download the ZIP file
2. In Claude.ai: **Settings → Capabilities → Skills → Upload**
3. Upload the ZIP — skill activates automatically on diagram requests

### Claude Code CLI
```bash
# Global (all projects)
cp -r generating-architecture-diagrams ~/.claude/skills/

# Per-project
cp -r generating-architecture-diagrams .claude/skills/
```

### VS Code Copilot (Claude model)
Place the folder in your workspace's `.claude/skills/` directory.

## Trigger Phrases

### Creation
- "Create a high level logical flow diagram for [app name]"
- "Generate an HLLFD from this transcript"
- "Create a C4 context diagram for [system]"
- "Build a C4 container diagram showing the internals"
- "Generate a C4 component diagram for the API service"
- "Create a sequence diagram for the login flow"
- "Draw a data flow diagram showing how orders are processed"
- "Generate an ERD for the database schema"
- "Create a deployment diagram for our cloud infrastructure"
- "Map all integrations between our systems"
- "Build a draw.io architecture diagram from these project docs"
- "Visualize the system architecture as a drawio file"
- "Show me the upstream and downstream systems"

### Modification
- "Update this diagram — add [integration details]"
- "Review and improve this drawio file"
- "Apply organization standards to this diagram"
- "Convert this image to an editable drawio"

### By Audience
- "Create an executive-level view of the architecture" → C4 Context
- "Show the technical architecture for developers" → HLLFD or C4 Container
- "Document the component internals for the dev team" → C4 Component or Sequence

## How to Use

1. **Provide source material**: Transcript, design doc, Confluence page, PPT, or description
2. **Specify diagram type**: HLLFD (default), C4, Sequence, DFD, ERD, Deployment, or Integration Map
3. **Specify the application**: Name, Short Name, CAI/CSI
4. Claude generates the `.drawio` file following all standards
5. **Iterate**: Modify via conversation or upload updated file

## Output

- **Filename**: `[APP_SHORT]-[DiagramType].drawio`
- **Updates**: Appended with `-v2`, `-v3`, etc.
- **Multi-diagram**: Always separate files (never multi-page)

## Companion Skills

| Skill | Integration |
|-------|-------------|
| **verification** | Validates all facts in diagram sources |
| **research-orchestrator** | Gathers source material before diagramming |
| **skills-updater** | Maintains context file freshness |

## Standards Compliance

| Standard | Compliance |
|----------|-----------|
| ARCH-001 Skill Architecture & Living Context Framework | ✅ Full |
| ARCH-002 Context Template Generator | ✅ Full |
| PROMPT-001 Skill Creator Pro | ✅ Full |
| RULES-001 Documentation Standards | ✅ Full |
| Truth & Verification Standards | ✅ Applied to all source claims |

## Sources

| Source | Type | Tier |
|--------|------|------|
| draw.io Official AI Generation Guidelines | Primary | Tier 1 ✅ |
| drawio-ninja (GitHub) | Community Research | Tier 2 ✅ |
| GenAI-DrawIO-Creator (arXiv 2601.05162, Jan 2026) | Academic | Tier 1 ✅ |
| C4 Model (c4model.com) | Industry Standard | Tier 1 ✅ |
| UML 2.5.1 (OMG) | Industry Standard | Tier 1 ✅ |
| Tom DeMarco, Structured Analysis (1979) | Academic | Tier 1 ✅ |
| Peter Chen, ER Model (1976) | Academic | Tier 1 ✅ |
| TOGAF 10 (The Open Group) | Industry Standard | Tier 1 ✅ |
| ISO/IEC/IEEE 42010:2022 | International Standard | Tier 1 ✅ |
| Anthropic Skill Authoring Best Practices | Platform Docs | Tier 1 ✅ |

## Version

3.0.0 — March 29, 2026
