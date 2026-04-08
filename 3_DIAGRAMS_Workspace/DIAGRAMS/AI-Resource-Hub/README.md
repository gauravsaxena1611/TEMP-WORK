# AI Resource Hub
## Architecture Team — AI Skills, Templates & Standards

**Version:** 1.0
**Created:** 2026-04-07
**Owner:** Architecture Team
**Purpose:** Central repository of all AI skills, diagram templates, and standards
used across all portfolio work.

---

## WHAT IS THIS FOLDER?

This folder is the "brain" of the workspace. It contains everything that makes
GitHub Copilot behave like a trained architecture assistant. You never work
directly in this folder — the prompt files in `.github/prompts/` read from here
automatically.

**Do not move or rename anything in this folder** — the prompt files reference
specific paths.

---

## CONTENTS

```
AI-Resource-Hub/
├── skills/
│   ├── ai-documentation/         ← Skill: create documentation from source material
│   │   ├── SKILL.md              ← Core workflow (Copilot reads this)
│   │   ├── references/           ← 17 document templates (TMPL-001 to TMPL-008)
│   │   ├── context-templates/    ← Context file templates
│   │   └── examples/             ← Populated example documents
│   │
│   └── generating-architecture-diagrams/   ← Skill: create draw.io diagrams
│       ├── SKILL.md              ← Core workflow (Copilot reads this)
│       ├── references/           ← Diagram type specifications (HLLFD, C4, Sequence, etc.)
│       ├── context-templates/    ← Color palette and app registry templates
│       └── templates/            ← Draw.io baseline template files
│
├── diagram-templates/            ← Org-specific numbered visual style templates
│   ├── README.md                 ← Template registry and usage guide
│   ├── T01-ICRM-HLLFD.md        ← Standard HLLFD visual style
│   ├── T02-ICRM-C4-Context.md   ← Standard C4 Context visual style
│   ├── T03-ICRM-C4-Container.md
│   ├── T04-ICRM-Sequence.md
│   ├── T05-ICRM-Data-Flow.md
│   ├── T06-ICRM-ERD.md
│   ├── T07-ICRM-Deployment.md
│   ├── T08-ICRM-Integration-Map.md
│   ├── T09-ICRM-Executive.md
│   └── T10-ICRM-Full-Detail.md
│
└── standards/
    ├── Documentation_Standards.md   ← RULES-001: naming, structure, revision tracking
    └── Verification_Standards.md    ← RULES-002: source quality, fact checking
```

---

## SKILLS QUICK REFERENCE

### `/ai-documentation` — Create documentation

Trigger with: call transcript, Confluence export, meeting notes, spec document

Produces:
- `Application-Understanding.md`
- `Application-Workflows.md`
- `Contracts.md` (architectural decisions)
- `Data-Models.md`
- `Users.md`

Supported document types (templates):
| Template | Use For |
|---------|---------|
| TMPL-001 | Research synthesis (technology comparisons, vendor analysis) |
| TMPL-002 | Technical reference (system specs, API docs, application overview) |
| TMPL-003 | Procedure & workflow (how-to guides, runbooks, processes) |
| TMPL-005 | Decision record (architectural decisions, technology choices) |
| TMPL-006 | Meeting / session record (call notes, workshop outputs) |

### `/generate-diagram` — Create draw.io diagrams

Trigger with: application docs (docs/ folder) or raw transcripts

Supported diagram types:
| Type | Template | Best For |
|------|---------|---------|
| HLLFD | T01 | Default — upstream/downstream integration view |
| C4 Context | T02 | Who interacts with the system |
| C4 Container | T03 | Technology building blocks inside the system |
| Sequence | T04 | Step-by-step interaction flows |
| Data Flow | T05 | How data moves through the system |
| ERD | T06 | Database schema and entity relationships |
| Deployment | T07 | Infrastructure and hosting topology |
| Integration Map | T08 | All API/interface connections |
| Executive | T09 | Business-audience simplified view |
| Full Detail | T10 | Maximum technical detail |

---

## UPDATING THIS HUB

When you refine skills on your personal Claude Pro account:
1. Export the updated skill files
2. Copy them into the relevant folder here, replacing the old versions
3. No reinstall needed — Copilot reads the files fresh each session

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-07 | Initial | AI Resource Hub created with 2 skills, 10 diagram templates, 2 standards | Initial workspace setup |
