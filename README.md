# ICRM AI Workspace — Complete Reference Guide
> Your single reference document for everything in this workspace.

---

## QUICK START — First Time Only

1. **Unzip** `ICRM-AI-Workspace.zip` anywhere on your VDI (no admin needed)
2. **Open VS Code** → `File > Open Folder` → select the `ICRM-AI-Workspace/` folder
3. **Wait 5 minutes** — Copilot builds its index in the background
4. **Open Copilot Chat** panel — type `/` to see all available commands
5. Done. Setup is permanent — VS Code remembers this workspace.

> **Every session after this:** just open VS Code, it reopens your workspace automatically.

---

## WORKSPACE FOLDER STRUCTURE

```
ICRM-AI-Workspace/
│
├── .github/                              ← Copilot reads this automatically
│   ├── copilot-instructions.md           ← Always-on: standards + skill routing (every session)
│   ├── instructions/
│   │   └── archives.instructions.md      ← Auto-enforces archives as read-only
│   ├── prompts/                          ← All your /slash commands (30 total)
│   │   ├── start-session.prompt.md
│   │   ├── load-app-context.prompt.md
│   │   ├── diagram-*.prompt.md           ← 11 diagram prompts
│   │   └── doc-*.prompt.md               ← 16 documentation prompts
│   └── skills/                           ← Your two AI skills (don't edit these)
│       ├── generating-architecture-diagrams/
│       │   ├── SKILL.md                  ← Main skill (82KB)
│       │   ├── references/               ← 13 spec files
│       │   │   ├── hllfd-spec.md
│       │   │   ├── c4-spec.md
│       │   │   ├── sequence-spec.md
│       │   │   ├── swimlane-spec.md
│       │   │   ├── data-flow-spec.md
│       │   │   ├── erd-spec.md
│       │   │   ├── deployment-spec.md
│       │   │   ├── integration-spec.md
│       │   │   ├── state-spec.md
│       │   │   ├── xml-rules.md
│       │   │   ├── xml-patterns.md
│       │   │   ├── color-palette.md
│       │   │   └── workflow-numbering.md
│       │   ├── templates/                ← Base draw.io templates
│       │   │   ├── High_Level_Diagram.drawio
│       │   │   └── ATPC_High_Level_Diagram_v2.drawio
│       │   └── context-templates/        ← Org-level context files
│       │
│       └── ai-documentation/
│           ├── SKILL.md                  ← Main skill
│           ├── references/               ← All 14 document templates (TMPL-001 to TMPL-014)
│           ├── examples/                 ← 5 real example outputs
│           └── context-templates/        ← Doc standards context template
│
├── ICRM/                                 ← All your application work goes here
│   ├── Vertical-1/
│   │   └── <csi>-<appname>/
│   │       ├── diagrams/                ← Save your .drawio output files here
│   │       ├── docs/                    ← All AI-generated documents
│   │       │   ├── Application Understanding.md
│   │       │   ├── Contracts.md
│   │       │   ├── Work Audit.md
│   │       │   ├── Data Models.md
│   │       │   └── features/<feature-name>/
│   │       └── archives/                ← READ ONLY — source material only
│   │           ├── confluence-pages/
│   │           ├── transcripts/
│   │           ├── emails/
│   │           ├── project-docs/
│   │           └── samples/payloads/ wsdls/
│   ├── Vertical-2/
│   └── Vertical-3/
│
├── AI-Resource-Hub/                      ← Shared reference material
│   └── Standards/
│       ├── Documentation_Standards.md
│       ├── ARCH-001_Skill_Architecture.md
│       └── ARCH-002_Context_Templates.md
│
└── workspaces/                           ← VS Code workspace files
    ├── _master.code-workspace            ← Opens all verticals
    └── Vertical-1/
        └── sample-app.code-workspace     ← App-scoped (copy & rename per app)
```

---

## HOW TO OPEN THE RIGHT WORKSPACE

**Single application** (recommended — keeps Copilot focused on one app):
`File > Open Workspace from File` → `workspaces/Vertical-X/<appname>.code-workspace`

**Portfolio work** (all apps visible):
`File > Open Workspace from File` → `workspaces/_master.code-workspace`

> With 20+ apps in one folder, Copilot's search returns results from all of them.
> App-specific workspace files limit search to only that app. Always use these for focused work.

---

## ALL SLASH COMMANDS — COMPLETE LIST

Type `/` in Copilot Chat to see everything. Here is every command.

---

### SESSION MANAGEMENT

| Command | What it does |
|---------|-------------|
| `/start-session` | Declare your app and today's task. Copilot confirms context and which skill to use. |
| `/load-app-context` | Reads the app's `docs/` and `archives/`, gives you a 5-line summary of what it knows. |

---

### DIAGRAM COMMANDS — 11 Types

> All diagrams output **draw.io XML**. Copy → [diagrams.net](https://diagrams.net) → `Extras > Edit Diagram` → paste.

#### `/diagram-hllfd` — High Level Logical Flow Diagram
Shows the high-level flow of a process or system. Best for executive overviews and initial sketches.
- Inputs: system name, flow to show, key actors
- *e.g. "end-to-end payment flow for csi-payments"*

#### `/diagram-c4-context` — C4 Level 1 System Context
Shows your system as a black box with all users and external systems around it. Business-facing.
- Inputs: system name, users/actors, external systems, one-line purpose
- *e.g. "csi-payments connecting to Core Banking, Customer Portal, Fraud Engine"*

#### `/diagram-c4-container` — C4 Level 2 Containers
Zooms into the system to show deployable units: APIs, DBs, UIs, queues, and how they communicate.
- Inputs: system name, list of containers, tech stack
- *e.g. "Spring Boot API, Oracle DB, IBM MQ, React frontend"*

#### `/diagram-c4-component` — C4 Level 3 Components
Zooms into a single container to show its internal components. Developer-level detail.
- Inputs: container name, components inside it, key interactions
- *e.g. "Zoom into Payment API — Controller, Service, Repository, Validator layers"*

#### `/diagram-sequence` — Sequence Diagram
Messages and calls between systems over time. Best for documenting one specific flow end-to-end.
- Inputs: scenario name, participants, include error flows yes/no
- *e.g. "JWT auth flow: Browser → API Gateway → Auth Service → User DB"*

#### `/diagram-swimlane` — Swimlane / BPMN
Process flow with multiple actors each in a lane. Shows handoffs and responsibilities.
- Inputs: process name, actors/lanes, key steps
- *e.g. "Customer onboarding — lanes: Customer, RM, Backend, Compliance"*

#### `/diagram-dfd` — Data Flow Diagram
How data enters, transforms, is stored, and exits. About data movement, not who does what.
- Inputs: system name, level (L0 overview or L1 detailed), data flows to show
- *e.g. "L0 DFD — customer order enters, validated, stored in DB, response exits"*

#### `/diagram-erd` — Entity Relationship Diagram
Database entities, attributes, and relationships. Uses Crow's Foot notation.
- Inputs: domain name, entity names, key relationships
- *e.g. "Customer, Account, Transaction, Product — with cardinality"*

#### `/diagram-deployment` — Deployment Topology
Where things run: servers, environments (DEV/SIT/UAT/PROD), network zones, load balancers.
- Inputs: system name, environments, infrastructure details
- *e.g. "csi-payments on WebSphere — DEV/UAT/PROD with load balancer and Oracle node"*

#### `/diagram-integration-map` — Integration Map
All upstream and downstream integrations for your application in one view.
- Inputs: central system, upstream systems, downstream systems, integration types
- *e.g. "csi-payments ← CRM, Core Banking; → GL, Reporting; mix of REST and MQ"*

#### `/diagram-state` — State Machine Diagram
All states of an entity and the events that trigger transitions between them.
- Inputs: entity name, all states, transition triggers
- *e.g. "Loan Application: Draft → Submitted → Under Review → Approved/Rejected → Disbursed → Closed"*

---

### DOCUMENTATION COMMANDS — 16 Types

> All documents saved to the application's `docs/` folder.

---

#### General Purpose Documents

| Command | Template | What it creates | When to use |
|---------|----------|----------------|-------------|
| `/doc-research-synthesis` | TMPL-001 | Structured research with verified claims and source tiers | After researching a topic — tech comparison, feasibility, options analysis |
| `/doc-technical-reference` | TMPL-002 | How a system, API, or component works | Documenting an API, service, or platform for developer reference |
| `/doc-runbook` | TMPL-003 | Step-by-step procedure document | Deployment steps, incident response, onboarding, any repeatable process |
| `/doc-decision-record` | TMPL-005 | Architectural decision with context, options, and rationale | Every significant technical or architectural decision — capture while fresh |
| `/doc-session-record` | TMPL-012 | Meeting/call notes with decisions and actions | After any stakeholder call, architecture review, or design session |
| `/doc-incident-postmortem` | TMPL-014 | Incident post-mortem with timeline, root cause, actions | After any production incident |

---

#### Architecture Documents — Build in This Order for a New Application

> These 5 templates build on each other. **Always start with TMPL-006 first.**

| Order | Command | Template | What it creates |
|-------|---------|----------|----------------|
| **1st** | `/doc-system-context-pack` | TMPL-006 | Canonical architecture reference: business purpose, actors, integrations, SLAs, failure modes. **Start here.** |
| **2nd** | `/doc-container-inventory` | TMPL-007 | All deployable units: containers, technology, ports, dependencies. Needs TMPL-006 first. |
| **3rd** | `/doc-data-flow-inventory` | TMPL-008 | Data entities, stores, flows (L0/L1), PII classification. Needs 006+007. |
| **3rd** | `/doc-deployment-topology` | TMPL-010 | Infrastructure environments, nodes, CI/CD, DR strategy. Needs 006+007. |
| **4th** | `/doc-sequence-catalog` | TMPL-009 | Business flows as ordered interaction sequences. Needs 006+007. |

After all five are done: `/doc-portfolio-register` (TMPL-011) — master index across all ICRM applications.

---

#### AI Agent Context Documents

| Command | Template | What it creates | When to use |
|---------|----------|----------------|-------------|
| `/doc-agent-context-brief` | TMPL-004A | Briefing for an AI agent before it starts work | When you want Copilot pre-loaded with specific context for a recurring task |
| `/doc-living-context` | TMPL-004C | Persistent cross-session memory document | When you want Copilot to "remember" key facts about an app across sessions |

---

#### Specialist Documents

| Command | Template | What it creates |
|---------|----------|----------------|
| `/doc-portfolio-register` | TMPL-011 | Master index across all ICRM applications (run after TMPL-006 exists for each app) |
| `/doc-ai-model-card` | TMPL-013 | Documentation for an AI model or AI-enabled system component |

---

## YOUR SKILLS — HOW THEY ACTUALLY WORK

### Diagram Skill

You don't always need a slash command. Just describe what you want:
```
Create a C4 context diagram for csi-payments
Draw an HLLFD showing the end-to-end lending process
I need an integration map for csi-lending
Show the sequence for the authentication flow
Draw an ERD for the customer and account tables
```

The skill will:
1. Run a **Phase 0 check** — proposes the right diagram type and asks you to confirm
2. Load only the relevant spec file from `references/` (keeps context lean)
3. Output complete draw.io XML

**Using the XML:**
1. Copy the XML block from Copilot's response
2. Go to [diagrams.net](https://diagrams.net) in your browser
3. `Extras > Edit Diagram` → paste → OK → your diagram appears
4. Save as `.drawio` → store in `ICRM/Vertical-X/<appname>/diagrams/`

---

### Documentation Skill

Just describe what you want to document:
```
Create an Application Understanding document for csi-payments
Write a decision record — we chose REST over SOAP due to WebSphere constraints
Create an architecture pack for csi-lending
Document the loan disbursement feature
Write a runbook for the monthly interest batch job
Take these call notes and create a session record: [paste notes]
```

The skill will:
1. Select the right template automatically
2. Ask clarifying questions if needed
3. Produce a fully structured, standards-compliant document
4. Apply verification labels to all factual claims (✅ verified · ⚠️ flagged · 💡 inference)

---

## ADDING A NEW APPLICATION

```
1. Create the folders:
   ICRM/Vertical-X/<csi>-<appname>/
       docs/
       diagrams/
       archives/
           confluence-pages/
           transcripts/
           project-docs/
           samples/

2. Drop all source material into archives/
   (Confluence exports, transcripts, emails, payloads, WSDLs, existing docs)

3. Copy and rename a workspace file:
   Copy:   workspaces/Vertical-1/sample-app.code-workspace
   Rename: workspaces/Vertical-X/<appname>.code-workspace
   Edit:   update the folder path inside the file to point at your new app folder

4. Open VS Code → File > Open Workspace from File → your new workspace file

5. Run /load-app-context
   → Copilot reads archives/ and summarises what it found

6. Run /doc-system-context-pack
   → Creates your first TMPL-006 architecture document

7. Run /diagram-c4-context
   → Creates your first C4 L1 diagram
```

---

## DAY-IN-THE-LIFE WORKFLOWS

### Architecture Diagram Session
```
Open VS Code → open app workspace file
/start-session  (declare app + "creating architecture diagrams")
/load-app-context
/diagram-c4-context  → paste XML into diagrams.net → save .drawio
/diagram-c4-container → repeat
/diagram-integration-map → repeat
```

### After a Stakeholder Call
```
/doc-session-record → paste your raw notes → structured doc created
/doc-decision-record → capture any decisions made
```

### Onboarding a New Application
```
Create folders → drop source material in archives/
/load-app-context → Copilot reads everything and summarises
/doc-system-context-pack → canonical architecture doc (TMPL-006)
/doc-container-inventory → TMPL-007
/diagram-c4-context → visual version of TMPL-006
/diagram-c4-container → visual version of TMPL-007
```

### Feature Documentation
```
/load-app-context (if new session)
"Document the [feature name] feature"
→ Copilot creates Feature Understanding.md in docs/features/<feature>/
"Create test scenarios for [feature name]"
→ Copilot creates Tests Scenarios.md
```

---

## MODELS

| Model | When to use |
|-------|-------------|
| **Claude Sonnet 4** | Default for everything. Most consistent with your personal Claude Pro output. |
| **Gemini 2.5 Pro** | Switch to this for very long sessions — its 1M token window handles large context better. |

Switch in the Copilot Chat model picker (top of chat panel).

---

## TROUBLESHOOTING

| Problem | Fix |
|---------|-----|
| Skill not triggering / files not found | Wait 5–10 min after adding new files — Copilot reindexes in background |
| Getting results from wrong application | Use app-specific `.code-workspace` file instead of master workspace |
| Copilot ignoring instructions | Check `copilot-instructions.md` uses `###` Markdown headers, not XML `<tags>` |
| Draw.io XML not rendering | Paste into `Extras > Edit Diagram` — not as plain text in the canvas |
| XML output cut off mid-way | Ask Copilot: "continue the XML from where it stopped" |
| Context window filling up in long sessions | Start a new Copilot Chat, re-run `/load-app-context`. Or switch to Gemini 2.5 Pro. |
| New archives content not picked up | Re-run `/load-app-context` after adding files |

---

## RULES — WHAT NOT TO DO

- ❌ Don't edit anything inside `.github/skills/` — skills work as-is
- ❌ Don't put application files outside `ICRM-AI-Workspace/` — Copilot can't see them
- ❌ Don't create or modify files inside `archives/` — read-only source material only
- ❌ Don't use XML tags in `.github/copilot-instructions.md` — known Copilot bug silently ignores them

---

*Last updated: 2026-04-12 | All 30 prompts · 2 skills · 11 diagram types · 14 doc templates*
