# 3. DIAGRAMS — Architecture Workspace
## ICRM Portfolio | AI-Assisted Architecture Documentation & Diagrams

**Version:** 1.0
**Created:** 2026-04-07
**Root Path:** `C:\Users\gs16504\Documents\3. DIAGRAMS`

---

## QUICK START

### First time opening this workspace

1. Open VS Code or IntelliJ
2. Open folder: `C:\Users\gs16504\Documents\3. DIAGRAMS`
3. That's it — Copilot picks up `.github/copilot-instructions.md` automatically

### Working on a new application

```
1. Create folder:   ICRM/{Vertical}/{CSI}-{AppName}/
2. Create subfolders inside it:
                    archives/transcripts/
                    archives/confluence-pages/
                    archives/project-docs/
                    docs/
                    diagrams/
3. Drop source files into archives/
4. Run: /ai-documentation  (creates docs/)
5. Run: /generate-diagram  (creates diagrams/)
```

### Working on an existing application (new transcript arrived)

```
1. Drop new transcript into archives/transcripts/
2. Run: /ai-documentation
        "Update 176482-ORM Metric from new transcript in archives/transcripts/{file}"
3. Run: /generate-diagram if diagrams need updating
```

---

## WORKSPACE STRUCTURE

```
3. DIAGRAMS\
│
├── .github\                          ← Copilot configuration (do not modify)
│   ├── copilot-instructions.md       ← Global rules — auto-injected every session
│   └── prompts\
│       ├── ai-documentation.prompt.md    ← /ai-documentation trigger
│       └── generate-diagram.prompt.md    ← /generate-diagram trigger
│
├── AI-Resource-Hub\                  ← All AI skills, templates, standards
│   ├── skills\
│   │   ├── ai-documentation\
│   │   └── generating-architecture-diagrams\
│   ├── diagram-templates\            ← T01–T10 visual style templates
│   └── standards\
│       ├── Documentation_Standards.md
│       └── Verification_Standards.md
│
└── ICRM\                             ← Portfolio (add more portfolios at this level)
    ├── README.md
    ├── Vertical-1\
    ├── Vertical-2\
    └── Vertical-3\
        └── 176482-ORM Metric\        ← Application (CSI-Name format)
            ├── README.md
            ├── archives\             ← Source material goes here
            ├── docs\                 ← Documentation output
            └── diagrams\            ← draw.io output
```

---

## ADDING A NEW PORTFOLIO

1. Create folder at root level: e.g., `MCA\`
2. Copy folder structure from `ICRM\`
3. Update `ICRM/README.md` pattern for the new portfolio
4. The `.github/copilot-instructions.md` applies to all portfolios automatically

---

## PROMPT CHEAT SHEET

### VS Code
| Task | Command |
|------|---------|
| Document new application | `/ai-documentation` then describe the app and reference archive files |
| Update existing docs | `/ai-documentation` then "update {app} from new transcript in archives/" |
| Create HLLFD diagram | `/generate-diagram` Type: HLLFD, Template: T01 |
| Create C4 Context diagram | `/generate-diagram` Type: C4 Context, Template: T02 |
| Create sequence diagram | `/generate-diagram` Type: Sequence, Template: T04 |
| Document an architecture decision | `/ai-documentation` then "create decision record for {decision}" |
| Create meeting notes | `/ai-documentation` then "create meeting record from transcript" |

### IntelliJ (manual reference)
```
#file:.github/prompts/ai-documentation.prompt.md
#file:AI-Resource-Hub/skills/ai-documentation/SKILL.md
#file:{source file path}

Your request here
```

---

## UPDATING SKILLS

When you update a skill on your personal Claude Pro account:
1. Export the updated files
2. Copy into `AI-Resource-Hub/skills/{skill-name}/`
3. Replace old files — done. No restart needed.

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-07 | Initial | Workspace created | Initial setup |
