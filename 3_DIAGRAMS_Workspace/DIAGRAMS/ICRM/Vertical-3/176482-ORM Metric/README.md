# 176482-ORM Metric
## Application Architecture Documentation

**Application:** ORM Metric
**CSI:** 176482
**Portfolio:** ICRM
**Vertical:** Vertical-3
**Version:** 1.0
**Created:** 2026-04-07
**Status:** ⏳ Awaiting documentation

---

## HOW TO USE THIS FOLDER

### Step 1 — Add your source material

Drop all source files into the appropriate `archives/` subfolder:

| Material Type | Folder |
|--------------|--------|
| Call transcripts, meeting notes | `archives/transcripts/` |
| Confluence page exports | `archives/confluence-pages/` |
| Project documents, specs, requirements | `archives/project-docs/` |
| Email threads, decisions via email | `archives/emails/` |

### Step 2 — Create documentation

```
/ai-documentation
Application: 176482-ORM Metric
Source: #file:ICRM/Vertical-3/176482-ORM Metric/archives/transcripts/{filename}
Create: Application Understanding and Workflows
```

This creates the `docs/` folder with all structured documentation.

### Step 3 — Create diagrams

```
/generate-diagram
Application: 176482-ORM Metric
Type: HLLFD
Template: T01
Source: #file:ICRM/Vertical-3/176482-ORM Metric/docs/Application-Understanding.md
```

This creates draw.io files in the `diagrams/` folder.

---

## DOCUMENTATION STATUS

| Document | Status | Last Updated |
|---------|--------|-------------|
| Application-Understanding.md | ⏳ Not created | — |
| Application-Workflows.md | ⏳ Not created | — |
| Contracts.md | ⏳ Not created | — |
| Data-Models.md | ⏳ Not created | — |
| Users.md | ⏳ Not created | — |

## DIAGRAMS STATUS

| Diagram | Template | Status | Last Updated |
|---------|---------|--------|-------------|
| 176482-HLLFD.drawio | T01 | ⏳ Not created | — |

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-07 | Initial | Application folder created | Initial workspace setup |
