# Generating Architecture Diagrams — Claude Skill

**Skill ID:** SKILL-ARCHDIAG
**Version:** 4.0
**Last Updated:** April 11, 2026

Transforms Claude into an enterprise-grade architectural diagramming engine,
producing valid, editable draw.io XML from unstructured inputs like call
transcripts, project documents, Confluence pages, and design specifications.

XML generation rules are sourced directly from the official **jgraph/drawio-mcp**
repository — the draw.io team's own Claude skill. This is the single canonical
source of truth for all diagram generation.

## What's New in v4.0

Version 4.0 resolves all previously reported file issues (empty files, files that
won't open, broken arrows). Three critical XML misalignments were identified and
corrected by cross-referencing against the official draw.io skill-cli:

| Was (v3) | Now (v4) | Effect |
|----------|----------|--------|
| `<mxfile>` root wrapper | `<mxGraphModel adaptiveColors="auto">` | Files open correctly |
| XML comments allowed in examples | ZERO XML comments — strictly forbidden | Parser errors eliminated |
| Edge geometry not mandated | Expanded edge form always required | Arrowheads render correctly |

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
- Root element: `<mxGraphModel adaptiveColors="auto">` — correct format for all IDE plugins
- **Zero XML comments** in generated output — prevents parse errors
- Official edge/layout rules: 200px horizontal / 120px vertical / 20px arrowhead spacing
- Layers, tags, metadata/placeholders, dark mode support
- Extracts architectural facts from transcripts, design docs, PPTs, Confluence
- Applies organization domain color palette (customizable via Living Context)
- Enforces workflow numbering standards (7 distinct formats for HLLFD)
- Guards against 12 known LLM XML generation failure modes
- Marks missing information as TBD — never assumes or fabricates

### IDE Plugin Compatibility

This skill is designed for IDE-native draw.io plugins — no desktop app required:
- **VS Code**: `hediet.vscode-drawio` extension — `.drawio` files open automatically
- **IntelliJ**: `draw.io integration` plugin — `.drawio` files open automatically

## Skill Architecture

Uses **progressive disclosure** — Claude loads only what's needed per request.

Implements **Living Context Architecture** [ARCH-001] — organization-specific
data (colors, domains, app registry) lives in context files, not in the skill.

```
generating-architecture-diagrams/
├── SKILL.md                           # Core workflow + context declarations
├── references/
│   ├── xml-rules.md                   # Official XML rules (v4.0, from jgraph/drawio-mcp)
│   ├── xml-patterns.md                # Copy-paste XML snippets (21 patterns)
│   ├── color-palette.md               # Default colors (overridden by CTX-PALETTE)
│   ├── workflow-numbering.md          # Numbering standards (HLLFD, Integration)
│   ├── hllfd-spec.md                  # HLLFD layout specification
│   ├── c4-spec.md                     # C4 Levels 1, 2, 3 specification
│   ├── sequence-spec.md               # Sequence diagram specification
│   ├── data-flow-spec.md              # DFD specification
│   ├── erd-spec.md                    # ERD specification
│   ├── deployment-spec.md             # Deployment diagram specification
│   └── integration-spec.md            # Integration map specification
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
├── SETUP_GUIDE.md
└── UPGRADE_GUIDE.md
```

### Living Context Integration

| Context | Purpose | If Missing |
|---------|---------|-----------|
| **CTX-PALETTE** | Organization colors | Uses default palette |
| **CTX-DOMAINS** | Domain classification | Uses generic Internal/External |
| **CTX-APPREG** | Application registry | Builds incrementally |

## Installation

### Claude.ai Projects (Chat)
1. Download: `generating-architecture-diagrams-v4.0.zip`
2. In Claude.ai: **Settings → Capabilities → Skills → Upload**
3. Upload the ZIP — skill activates automatically on diagram requests

### Claude Code CLI
```bash
unzip generating-architecture-diagrams-v4.0.zip

# Global (all projects)
cp -r generating-architecture-diagrams ~/.claude/skills/

# Per-project
cp -r generating-architecture-diagrams .claude/skills/
```

### VS Code / IntelliJ
Place `generating-architecture-diagrams/` in your workspace's `.claude/skills/` directory.

## Trigger Phrases

**Creation:** create/generate/build/draw + architecture diagram, HLLFD, C4 diagram, sequence diagram, ERD, deployment diagram, integration map, drawio file, system diagram

**Modification:** update this diagram, add to diagram, fix diagram, apply standards to diagram, convert image to drawio

## How to Use

1. Provide source material: Transcript, design doc, Confluence, PPT, or description
2. Specify diagram type (HLLFD is default)
3. Specify the application name/ID
4. Claude generates the `.drawio` file
5. Open in VS Code or IntelliJ draw.io plugin
6. Iterate via conversation

## Version History

| Version | Date | Summary |
|---------|------|---------|
| 4.0 | 2026-04-11 | XML engine overhaul from official jgraph source — resolves all file issues |
| 3.0 | 2026-03-29 | Living Context integration, 9 diagram types, 50+ trigger phrases |
| 2.0 | 2026-03-27 | Progressive disclosure architecture, references extracted |
| 1.0 | 2026-03-27 | Initial creation |
