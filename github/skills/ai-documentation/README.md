# AI Documentation Skill
## Installation Guide & Quickstart

**Version:** 1.1.0 | **Updated:** 2026-04-04 | **Status:** Production Ready

---

## What This Skill Does

The `ai-documentation` skill produces AI-optimized, verified, standards-compliant
documentation structured for both human readers and AI agent retrieval. It
routes every documentation request through a seven-phase workflow, selects the
appropriate template type, and integrates research and verification skills
automatically where needed.

**Five template types are supported:**
- **TMPL-001** — Research & Knowledge Synthesis
- **TMPL-002** — Technical Reference
- **TMPL-003** — Procedure & Workflow / Runbook
- **TMPL-004** — AI Agent Context & Plan
- **TMPL-005** — Decision Record

---

## Prerequisites

Before installing this skill, ensure the following skills are installed in
the same Claude project:

| Skill | Why Required | Installation |
|-------|-------------|-------------|
| `research-orchestrator` | Invoked in Phase 2 for TMPL-001 and conditional TMPL-002/TMPL-004 | Install separately |
| `verification` | Invoked in Phase 4 for all documents with external factual claims | Install separately |

Optional but recommended:
- `skills-updater` — processes pending observations from this skill to propose updates

---

## Installation — Claude Projects

**Step 1** — Unzip the skill package:
```
ai-documentation-skill.zip
```

**Step 2** — Upload all files to your Claude project's knowledge folder,
preserving the directory structure:
```
ai-documentation/
├── SKILL.md
├── README.md
├── MANIFEST.md
├── references/
│   ├── TMPL-000_conventions.md
│   ├── TMPL-001.md through TMPL-005.md
├── context-templates/
│   └── doc-standards.ctx.template.md
└── examples/
    └── EX-001.md through EX-005.md
```

**Step 3** — Verify installation by invoking the skill:
```
"Write a doc" or "document this" or "I need a runbook"
```

The skill should respond by asking the four Phase 0 intent-capture questions
and offer to set up the documentation context if `doc-standards.ctx.md` is
not yet present.

---

## Installation — API / System Prompt

For API deployments, inject the SKILL.md content into the system prompt.
Reference files (`TMPL-000_conventions.md`, template files) must be available
to the model via tool calls or appended to the system prompt as additional context.

The minimal system prompt injection is the SKILL.md content verbatim. All
reference file content is loaded dynamically by the skill during execution.

---

## First Use — Context Setup

On first use, the skill will ask four questions to create the `doc-standards.ctx.md`
context file. This file stores your project's document numbering convention,
active parent documents, and project-specific overrides. It is read at the
start of every subsequent session.

Answer the four questions honestly — they can be updated later by the
`skills-updater` or by manual editing of `doc-standards.ctx.md`.

The populated example `examples/EX-004_CTX-DOCSTD-Agent-Context.md` shows
what a complete context file looks like for this project.

---

## Quickstart — Creating Your First Document

**1. Invoke the skill:**
```
I need a decision record for our choice to use PostgreSQL for the Order Service.
```

**2. The skill will:**
- Classify the document type (TMPL-005 — Decision Record)
- Confirm the classification with you
- Ask Phase 0 clarifying questions if needed
- Draft the complete document
- Run the Phase 6 pre-publish checklist
- Present the final document

**3. Example document outputs:**
See the `examples/` directory for one fully-populated example per template type.

---

## Skill Trigger Phrases

The skill activates on any of the following phrases (not exhaustive):

| Phrase | Template Selected |
|--------|-----------------|
| "write a doc", "create documentation", "document this" | Phase 1 decision tree |
| "I need a runbook", "write a guide for" | TMPL-003 |
| "create a decision record", "write up what we decided" | TMPL-005 |
| "document this architecture", "I need a technical reference" | TMPL-002 |
| "write a research synthesis", "what does research say about" | TMPL-001 |
| "create an agent context", "document this workflow" | TMPL-004 |
| "update this document", "improve this doc", "audit this document" | Update flow |

---

## Version History

| Version | Date | Key Changes |
|---------|------|------------|
| 1.1.0 | 2026-04-04 | Phase 1: Fixed non-standard frontmatter; token-aware chunking rules; precise research triggers; security content handling; 5 example documents; RAG supersession lifecycle; package manifest |
| 1.0.0 | 2026-04-01 | Initial release |

---

## File Reference

See `MANIFEST.md` for a complete listing of all files in this package with
descriptions and version information.

---

## Related Documents

- `PROJ-001 AI-Optimized Documentation Protocol` — project charter and design decisions
- `PROJ-002 Enhancement Roadmap` — Phase 2 planning (pending approval)
- `RULES-001 Documentation Standards` — base structural governance
- `Truth & Verification Standards` — verification methodology integrated by this skill
