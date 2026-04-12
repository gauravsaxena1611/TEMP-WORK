# Context Templates for generating-architecture-diagrams

Templates for creating project-level context files per the Living Context
Architecture [ARCH-001, Section 2]. These templates are populated on first
use of the skill in a new project.

## Templates

| Template | Context ID | Type | Purpose |
|----------|-----------|------|---------|
| `org-color-palette.ctx.template.md` | CTX-PALETTE | DECLARED | Organization domain colors, arrow colors, component colors |
| `org-domain-groups.ctx.template.md` | CTX-DOMAINS | DECLARED | How the organization classifies system domains |
| `app-registry.ctx.template.md` | CTX-APPREG | DECLARED | Known applications, short names, IDs, domain ownership |

## When These Are Created

Context files are created when the skill is invoked for the first time
on a new project and the corresponding `.ctx.md` file does not exist.

For this skill, all three contexts are optional but recommended:
- **CTX-PALETTE**: If missing, the default palette from `references/color-palette.md` is used
- **CTX-DOMAINS**: If missing, a generic Internal/External/Third-Party grouping is used
- **CTX-APPREG**: Built incrementally from source documents across diagram sessions

## Where Context Files Live

- **Claude Code**: `.claude/contexts/` in the project directory
- **Claude.ai Projects**: Project Knowledge Files
