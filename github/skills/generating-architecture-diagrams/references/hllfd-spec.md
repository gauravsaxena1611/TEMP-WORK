# High Level Logical Flow Diagram (HLLFD) Specification
## Reference for generating-architecture-diagrams skill

---

## 1. Overall Layout

The HLLFD uses a **tri-sectional horizontal layout** with users above and databases below:

```
┌─────────────────────────────────────────────────────────────────────────┐
│               [APP FULL NAME] ([SHORT]) - High Level Architecture       │
├──────────────┬──────────────────────────────────────┬───────────────────┤
│              │          USER TYPES                   │                   │
│              │  [Business] [Admin] [TPRO] [PFICRM]  │                   │
├──────────────┼──────────────────────────────────────┼───────────────────┤
│   INPUTS     │    PROCESSING - APP (CAI)            │   CONSUMES        │
│              │                                      │                   │
│ ┌──────────┐ │  ┌──────────────────────────────┐    │ ┌───────────────┐ │
│ │ ICRM     │ │  │     Core Components          │    │ │ Downstream    │ │
│ │ Systems  │→│→ │  [Frontend] ↔ [Backend]      │ →  │→│ System A      │ │
│ └──────────┘ │  │  [Batch] [Poller] [Adapter]  │    │ └───────────────┘ │
│              │  └──────────────────────────────┘    │                   │
│ ┌──────────┐ │                                      │ ┌───────────────┐ │
│ │ Non-ICRM │ │  ┌──────────┐  ┌────────────┐      │ │ Reporting     │ │
│ │ Citi     │ │  │ App DB   │  │ Cache DB   │      │ │ Platform      │ │
│ └──────────┘ │  └──────────┘  └────────────┘      │ └───────────────┘ │
│              │                                      │                   │
│ ┌──────────┐ │                                      │                   │
│ │ External │ │                                      │                   │
│ │(Gateway) │ │                                      │                   │
│ └──────────┘ │                                      │                   │
├──────────────┴──────────────────────────────────────┴───────────────────┤
│  LEGEND / KEY                    │   WORKFLOW NOTES + METADATA          │
└──────────────────────────────────┴──────────────────────────────────────┘
```

---

## 2. INPUTS Section (Left Swimlane)

**Title**: `Inputs` or `INPUTS`
**Style**: Swimlane, fill per dominant domain

### Domain Grouping (MANDATORY)

Upstream systems MUST be grouped by domain. Each group is a container inside the Inputs swimlane:

**Citi Internal Apps (ICRM/CRC Domain)**
- Fill: #D6EAF8, Border: #2E86C1
- Contains: Applications within the same ICRM organizational domain

**Non-ICRM Citi Internal (Other Domains)**
- Fill: #FCF3CF, Border: #B7950B
- Contains: Reference Data, Controls, Enterprise-level systems
- MUST be visually separated from ICRM systems so readers understand cross-domain dependencies

**External Systems**
- Fill: #FADBD8, Border: #C0392B, dashed border
- MUST show a Firewall/Gateway box (Fill: #F5B7B1, Border: #922B21) BEFORE any external system
- No public internet exposure assumed — all internal applications

### Per-System Box Content

Each upstream system box includes:
- **Bold title**: System Short Name (13px)
- **CAI/CSI**: If known, shown below name or in parentheses
- **Bullet points** (10px, gray #666666): What data it provides, key services consumed

---

## 3. PROCESSING Section (Center Swimlane)

**Title**: `Processing - [APP_SHORT] ([CAI])` or `Processing - [APP_SHORT] Application`
**Style**: Swimlane, fill #DAE8FC, border #6C8EBF

### Core Components Container

Inside Processing, create a rounded rectangle labeled `[APP] Core Components`:
- Fill: #E4E4E4, Border: #6C8EBF
- Contains all internal application components

### Component Types and Layout

| Component | Position | Shape | Colors from color-palette.md |
|-----------|----------|-------|-----|
| UI / Frontend | Upper area of core box | Rounded rectangle | Section 3: UI/Frontend |
| Backend Service | Middle area | Rounded rectangle | Section 3: Backend Service |
| Batch Processor | Lower area | Rounded rectangle | Section 3: Batch Processor |
| Poller | Near batch | Rounded rectangle | Section 3: Poller |
| Integration Adapter / Proxy | Between inputs and core | Rounded rectangle | Section 3: Integration Adapter |

### Component Box Content

Each component shows:
- **Bold title**: Component name (13px)
- **Bullet points** (10px, gray): Role, responsibilities, data processed

### Database Placement

Databases sit below or at the bottom of the Processing section:
- Use cylinder shape (`shape=cylinder3`)
- Show each DB separately if multiple exist
- Label with: DB name (bold), purpose bullets (what data stored, role)
- Colors from color-palette.md Section 4

### Internal Interactions

Show arrows between components within Processing:
- Bidirectional arrows between Frontend ↔ Backend
- Arrows from Backend → Database (persist)
- Arrows from Poller → Integration sources
- Number these with appropriate workflow style

---

## 4. CONSUMES Section (Right Swimlane)

**Title**: `CONSUMES` or `CONSUMES - CITI Internal Services`
**Style**: Swimlane, fill #CCE5FF, border #36393D

### Downstream System Boxes

Each downstream system shows:
- System name (bold)
- What data is sent to it (bullets)
- Integration type visible via arrow color

### Common Downstream Categories
- Reporting platforms (Tableau, Cognos, MicroStrategy)
- Document storage services (iDocs, SharePoint)
- Issue tracking systems (iCAPS, Jira)
- Notification services (Email, SMS)
- Other consuming applications

---

## 5. USER Section (Above Processing)

**Position**: Centered above the Processing swimlane

### User Container

Create a container box for all user types:
- Title: `[APP] User Types`
- Fill: #FFF8E1, Border: #F9A825

### User Shapes Inside Container

Use person/actor shapes (or rounded rectangles with role names):
- Colors from color-palette.md Section 5
- Each user shows: Role Name, abbreviated title

### User-to-System Connections

Draw arrows from users down to the Frontend/UI component showing:
- Entry point into the system
- Workflow they trigger
- Numbered according to the workflow they participate in

---

## 6. Mandatory Supplementary Sections

### 6.1 Legend / Key (Bottom-Right)
Position near the bottom of the diagram. Must include:
- Domain color swatches with labels
- Arrow color swatches with integration type names
- Workflow numbering format examples
- DB cylinder icon explanation
- User shape explanation

### 6.2 Workflow Notes (Bottom-Right, adjacent to Legend)
Format as described in workflow-numbering.md Section 9.
Group by workflow, match numbering to arrow labels.

### 6.3 Application Metadata (Bottom-Right)
Include only confirmed facts:
- Application Short Name and CAI/CSI
- Domain (ICRM/CRC)
- Go-live date (if known)
- Hosting environment (if stated — e.g., OpenShift, ECS)
- Key technology details (if confirmed)
- Acronym expansions used in diagram

---

## 7. Sizing Guidelines

| Element | Recommended Size |
|---------|-----------------|
| Full diagram canvas | 4000x2500 minimum |
| Inputs swimlane | 300px wide, 890px tall |
| Processing swimlane | 630px wide, 890px tall |
| Consumes swimlane | 280px wide, 890px tall |
| Core Components container | 540x780 inside Processing |
| Individual component box | 160-175px wide, 80-90px tall |
| Database cylinder | 180px wide, 115px tall |
| User container | 400-500px wide, 130px tall |
| Legend box | 250px wide, variable height |
| Notes box | 350px wide, variable height |

---

## 8. HLLFD Quality Checklist

Before delivering an HLLFD, verify:
```
☐ Title present with APP name, short name, and "High Level Architecture"
☐ Three swimlanes present: Inputs, Processing, Consumes
☐ Upstream systems grouped by domain with correct container colors
☐ External systems behind Gateway/Firewall box
☐ Processing section titled with APP_SHORT (CAI)
☐ Core Components container present inside Processing
☐ All internal components shown with roles
☐ All databases shown as cylinders with purposes
☐ User types shown above Processing with entry points
☐ All arrows numbered and color-coded
☐ Legend present with all color/numbering keys
☐ Workflow Notes present and synchronized with arrows
☐ Application metadata present
☐ No assumed or fabricated elements
☐ TBD markers on any incomplete elements
```
