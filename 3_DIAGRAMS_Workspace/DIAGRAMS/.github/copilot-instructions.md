# GitHub Copilot — Global Workspace Instructions
## ICRM Architecture Team | AI-Assisted Architecture Workspace

**Version:** 1.0
**Created:** 2026-04-07
**Applies to:** Every conversation in this workspace, both VS Code and IntelliJ

---

## 1. WHO I AM

I am a **Senior Architect** working in the **ICRM portfolio** (and other portfolios).
My daily work involves:
- Joining architecture calls and capturing outputs as structured documents
- Creating draw.io architecture diagrams (HLLFD, C4, Sequence, Integration Maps, etc.)
- Producing application understanding documents from transcripts, Confluence exports, and meeting notes
- I do **NOT own or have access to any code repositories** — never suggest git operations, commits, or code repo actions

---

## 2. THE MANDATORY TWO-STEP WORKFLOW

Every piece of architecture work follows this sequence. Do not skip or reverse it.

```
STEP 1 — UNDERSTAND (run /ai-documentation)
  Input:  Raw source material in archives/ (transcripts, Confluence pages, emails, docs)
  Output: Structured docs/ folder with Application Understanding, Workflows, Data Models, etc.

STEP 2 — DIAGRAM (run /generate-diagram)
  Input:  The docs/ folder produced in Step 1
  Output: draw.io files in diagrams/ folder
```

**Never generate a diagram without first having documentation in docs/.**
**Never skip Step 1 because "you already know the system."**

---

## 3. APPLICATION NAMING CONVENTION

All applications in this workspace follow the format:

```
{6-digit-CSI-number}-{Application Name}

Examples:
  176482-ORM Metric
  284931-Payment Gateway
  103847-Customer Portal
```

Use this format for:
- Folder names under each vertical
- Document IDs
- Diagram output filenames: `{CSI}-{DiagramType}.drawio`
  Example: `176482-HLLFD.drawio`, `176482-C4-Context.drawio`

---

## 4. WORKSPACE FOLDER STRUCTURE

```
3. DIAGRAMS\                              ← Workspace root (always open this)
├── .github\                              ← Copilot configuration (do not modify)
├── AI-Resource-Hub\                      ← All skills, templates, standards (read-only)
│   ├── skills\
│   │   ├── ai-documentation\             ← Skill: document creation
│   │   └── generating-architecture-diagrams\  ← Skill: diagram generation
│   ├── standards\                        ← Documentation & Verification standards
│   ├── diagram-templates\                ← Org-specific numbered visual templates (T01–T10)
│   └── examples\
└── ICRM\                                 ← Portfolio folder
    └── {Vertical}\
        └── {CSI}-{AppName}\
            ├── archives\                 ← DROP SOURCE MATERIAL HERE
            │   ├── transcripts\
            │   ├── confluence-pages\
            │   ├── project-docs\
            │   └── emails\
            ├── docs\                     ← CREATED BY /ai-documentation
            └── diagrams\                 ← CREATED BY /generate-diagram
```

---

## 5. DOCUMENTATION STANDARDS (RULES-001)

Full standards: `AI-Resource-Hub/standards/Documentation_Standards.md`

Key rules always active:

**Naming:** Three-digit numbering system
- 000-009 = Master documents
- 010-099 = First-level children
- Format: `[XXX] [Topic] - [Subtitle]`

**Every document must have:**
- Document ID, version, parent reference, created date, status
- Revision History table at the end
- Sources & References section
- Cross-references using format: `[Document ID, Section X.X]`

**Hierarchy rule:** Break into a separate document when content exceeds ~1,500 words on a single sub-topic.

---

## 6. VERIFICATION STANDARDS

Full standards: `AI-Resource-Hub/standards/Verification_Standards.md`

Key rules always active:
- **Never assume facts** — every claim must come from the provided source material
- **Never fabricate** integration names, system names, data fields, or business rules
- **Mark gaps explicitly** as `TBD — not specified in sources`
- **Label outputs:** ✅ verified from source | ⚠️ inferred | ❌ removed | 💡 assumption labelled

Scale verification to task:
- Documenting from transcripts → HIGH (extract only what is stated)
- Diagramming → HIGH (only draw what is documented)
- Brainstorming architecture options → LOW (label as ideas, not facts)

---

## 7. DIAGRAM TEMPLATE SYSTEM

Org-specific numbered templates live in: `AI-Resource-Hub/diagram-templates/`

When a diagram is requested, ask: **"Which template number? (T01–T10 or none)"**

| Template | Style Description |
|----------|-----------------|
| T01 | Standard ICRM HLLFD — upstream/downstream integration map |
| T02 | Standard C4 Context — org color-coded, external systems grey |
| T03 | Standard C4 Container — technology stack focus |
| T04 | Standard Sequence Diagram — numbered steps, swimlane style |
| T05 | Standard Data Flow — ETL/pipeline style |
| T06 | Standard ERD — schema documentation style |
| T07 | Standard Deployment — cloud/infrastructure topology |
| T08 | Standard Integration Map — interface/API connectivity |
| T09 | Executive Summary Diagram — simplified, business-audience |
| T10 | Detailed Technical Architecture — full component detail |

If no template specified → default to T01 (HLLFD) and confirm with user.
Template files: `AI-Resource-Hub/diagram-templates/T0X-{name}.md`

---

## 8. OUTPUT RULES

| Output Type | Location | Filename Format |
|------------|----------|-----------------|
| Application Understanding | `docs/Application-Understanding.md` | Fixed name |
| Application Workflows | `docs/Application-Workflows.md` | Fixed name |
| Architectural Decisions | `docs/Contracts.md` | Fixed name |
| Data Models | `docs/Data-Models.md` | Fixed name |
| Feature docs | `docs/features/{feature-name}/` | Subfolder per feature |
| draw.io diagrams | `diagrams/` | `{CSI}-{DiagramType}.drawio` |
| Session records | `docs/sessions/` | `{YYYY-MM-DD}-{topic}.md` |

---

## 9. HOW TO USE PROMPT FILES

**In VS Code:** Type `/ai-documentation` or `/generate-diagram` in Copilot chat.

**In IntelliJ:** Manually reference the relevant files at the start of your message:
```
#file:.github/prompts/ai-documentation.prompt.md
#file:AI-Resource-Hub/skills/ai-documentation/SKILL.md

Now: document this application from the transcript in archives/transcripts/
```

**Always add source files to your message:**
- For documentation: `#file:ICRM/{Vertical}/{App}/archives/transcripts/{file}.md`
- For diagrams: `#file:ICRM/{Vertical}/{App}/docs/Application-Understanding.md`

---

## 10. IMPORTANT CONSTRAINTS

- ❌ Never suggest committing to any git repository
- ❌ Never suggest pushing, pulling, or branching
- ❌ Never suggest creating a GitHub repository
- ❌ Never access or reference code files from application repositories
- ✅ All work stays within `C:\Users\gs16504\Documents\3. DIAGRAMS\`
- ✅ Output is shared via Confluence upload, email, or SharePoint — not git

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-07 | Initial | Workspace instructions created | Initial setup of ICRM Architecture AI workspace |
