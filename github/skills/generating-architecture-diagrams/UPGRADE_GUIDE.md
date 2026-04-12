# Upgrade Guide — v3.0 to v4.0
## generating-architecture-diagrams Skill

**Date:** April 11, 2026
**From:** v3.0 (March 29, 2026)
**To:** v4.0 (April 11, 2026)

---

## What Changed and Why

Version 4.0 resolves a critical set of XML generation issues that caused files to
appear empty, fail to open, or render with broken arrows. The root causes were
identified by cross-referencing our xml-rules.md against the official draw.io
team's own Claude skill published at: https://github.com/jgraph/drawio-mcp

Three critical misalignments were found and corrected.

---

## Files to Replace

Copy the following files from this upgrade package into your skill directory,
replacing the existing files:

| File to Replace | Why |
|----------------|-----|
| `SKILL.md` | Updated XML invariants, forbidden patterns, edge/layout rules, IDE notes |
| `references/xml-rules.md` | Complete replacement with official jgraph source rules |
| `references/xml-patterns.md` | All patterns corrected, 9 new patterns added |
| `CHANGELOG.md` | Updated with v4.0 entry |

**Files that do NOT need to be changed:**

| File | Status |
|------|--------|
| `references/hllfd-spec.md` | No changes — content correct |
| `references/c4-spec.md` | No changes — content correct |
| `references/sequence-spec.md` | No changes — content correct |
| `references/data-flow-spec.md` | No changes — content correct |
| `references/erd-spec.md` | No changes — content correct |
| `references/deployment-spec.md` | No changes — content correct |
| `references/integration-spec.md` | No changes — content correct |
| `references/workflow-numbering.md` | No changes |
| `references/color-palette.md` | No changes |
| `context-templates/` (all files) | No changes |
| `templates/` (all .drawio files) | No changes |
| `README.md` | Minor update recommended (version bump) |
| `SETUP_GUIDE.md` | No changes required |

---

## Installation Steps

### Step 1: Back up current skill files

```bash
cp ~/.claude/skills/generating-architecture-diagrams/SKILL.md \
   ~/.claude/skills/generating-architecture-diagrams/SKILL.md.v3.bak

cp ~/.claude/skills/generating-architecture-diagrams/references/xml-rules.md \
   ~/.claude/skills/generating-architecture-diagrams/references/xml-rules.md.v3.bak

cp ~/.claude/skills/generating-architecture-diagrams/references/xml-patterns.md \
   ~/.claude/skills/generating-architecture-diagrams/references/xml-patterns.md.v3.bak
```

### Step 2: Copy new files

```bash
cp SKILL.md ~/.claude/skills/generating-architecture-diagrams/SKILL.md
cp references/xml-rules.md ~/.claude/skills/generating-architecture-diagrams/references/xml-rules.md
cp references/xml-patterns.md ~/.claude/skills/generating-architecture-diagrams/references/xml-patterns.md
cp CHANGELOG.md ~/.claude/skills/generating-architecture-diagrams/CHANGELOG.md
```

### Step 3: Verify installation

Open Claude and ask: "Generate a simple architecture diagram with two services
and a database" — the resulting .drawio file should open in your VS Code draw.io
extension or IntelliJ draw.io plugin immediately.

---

## IDE Plugin Note

This skill is designed for use with IDE draw.io plugins — no desktop draw.io
application is required.

**VS Code:** Install `hediet.vscode-drawio` extension. `.drawio` files will
open automatically in the visual editor.

**IntelliJ:** Install the `draw.io integration` plugin. `.drawio` files will
open automatically in the diagram viewer.

Both plugins support the `<mxGraphModel>` format that this skill now generates.
When Claude writes a `.drawio` file to your workspace, simply open it in your IDE.

---

## Three Critical Rules to Remember

After upgrading, the three most important rules from the new xml-rules.md are:

1. **Root element is `<mxGraphModel adaptiveColors="auto">`** — never `<mxfile>`
2. **ZERO XML comments anywhere** — `<!-- -->` is completely forbidden
3. **Edges are always expanded** — `<mxGeometry relative="1" as="geometry" />` child is required

These three changes alone resolve the majority of reported file issues.

---

## Verification Sources

The rules in xml-rules.md v4.0 are sourced directly from:
- https://github.com/jgraph/drawio-mcp/blob/main/skill-cli/drawio/SKILL.md
- https://github.com/jgraph/drawio-mcp/blob/main/shared/xml-reference.md

These are the draw.io team's own canonical references for AI-generated diagrams.
