# ICRM Portfolio
## Architecture Documentation & Diagrams

**Portfolio:** ICRM
**Owner:** Architecture Team
**Version:** 1.0
**Created:** 2026-04-07

---

## OVERVIEW

This folder contains architecture documentation and diagrams for all applications
within the ICRM portfolio. It is organised by vertical, then by application using
the CSI naming convention.

---

## FOLDER STRUCTURE

```
ICRM/
├── Vertical-1/               ← Rename to actual vertical name
├── Vertical-2/               ← Rename to actual vertical name
├── Vertical-3/               ← Rename to actual vertical name
│   └── 176482-ORM Metric/   ← Sample application (CSI-AppName format)
│       ├── archives/         ← DROP SOURCE MATERIAL HERE
│       │   ├── transcripts/
│       │   ├── confluence-pages/
│       │   ├── project-docs/
│       │   └── emails/
│       ├── docs/             ← Created by /ai-documentation
│       └── diagrams/         ← Created by /generate-diagram
└── README.md                 ← This file
```

---

## APPLICATION NAMING CONVENTION

```
{6-digit-CSI}-{Application Name}

Examples:
  176482-ORM Metric
  284931-Payment Gateway
  103847-Customer Portal
```

## ADDING A NEW APPLICATION

1. Create folder: `ICRM/{Vertical}/{CSI}-{AppName}/`
2. Create subfolders: `archives/transcripts/`, `archives/confluence-pages/`, `docs/`, `diagrams/`
3. Drop source material into `archives/`
4. Run `/ai-documentation` to create `docs/`
5. Run `/generate-diagram` to create draw.io files in `diagrams/`

---

## DOCUMENT INDEX

| Application | Vertical | Docs Status | Diagrams | Last Updated |
|------------|---------|-------------|---------|-------------|
| 176482-ORM Metric | Vertical-3 | ⏳ Pending | ⏳ Pending | 2026-04-07 |

> Update this table as you add applications and complete documentation.

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-07 | Initial | Portfolio folder created | Initial workspace setup |
