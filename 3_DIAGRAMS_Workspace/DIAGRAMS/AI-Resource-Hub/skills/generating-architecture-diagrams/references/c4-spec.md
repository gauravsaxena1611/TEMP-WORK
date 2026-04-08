# C4 Diagram Specification
## Reference for generating-architecture-diagrams skill

Based on the C4 model by Simon Brown (c4model.com) and draw.io's built-in
C4 shape library. This reference covers Level 1 (Context) and Level 2 (Container).

---

## 1. C4 Context Diagram (Level 1)

### Purpose
Shows the system as a single box at the center, surrounded by users and
external systems. Answers: "What is this system, who uses it, and what
does it connect to?"

### Audience
Both technical and non-technical stakeholders. This is the highest-level
view — no internal details.

### Layout

```
         [Person A]     [Person B]
              \             /
               \           /
          ┌─────────────────────┐
          │                     │
          │   [SYSTEM NAME]     │
          │   Description       │
          │   Technology        │
          │                     │
          └─────────────────────┘
              /     |       \
             /      |        \
    [System X] [System Y] [System Z]
```

### Element Styles

**Central System (the system being documented):**
- Shape: Large rounded rectangle
- Fill: #438DD5, text color: #FFFFFF
- Label: System name (bold, 16px), brief description (12px), technology if confirmed

**Person / User Role:**
- Shape: `shape=mxgraph.basic.person` or draw.io C4 person shape
- Fill: #08427B, text color: #FFFFFF
- Label: Role name (bold), brief description of how they use the system

**External System (existing, not being built):**
- Shape: Rounded rectangle
- Fill: #999999, text color: #FFFFFF
- Label: System name (bold), brief description, technology if known

**System Under Development (related, being built):**
- Shape: Rounded rectangle
- Fill: #438DD5, text color: #FFFFFF (same as central but smaller)

### Relationship Arrows

Every arrow MUST have a label describing:
- What data flows (e.g., "Sends assessment status")
- What protocol is used (e.g., "via REST API", "via Kafka")

Arrow styles:
- Solid line: Synchronous communication
- Dashed line: Asynchronous communication
- Arrow direction: Shows data flow direction

### System Boundary
- Dashed rounded rectangle around the central system (optional)
- Label: Enterprise/organization name if relevant

### What NOT to Show at Level 1
- No internal components (services, databases, batch jobs)
- No internal communication patterns
- No deployment details
- No code-level elements

---

## 2. C4 Container Diagram (Level 2)

### Purpose
Zooms into the system boundary to show its internal containers. Answers:
"What are the major technology building blocks, and how do they communicate?"

### Audience
Technical stakeholders — developers, architects, DevOps.

### Layout

```
         [Person A]     [Person B]
              \             /
    ┌──────────────────────────────────┐
    │        System Boundary           │
    │                                  │
    │  ┌──────┐  ┌──────┐  ┌──────┐  │
    │  │Web   │  │API   │  │Batch │  │
    │  │App   │→ │Server│→ │Job   │  │
    │  └──────┘  └──────┘  └──────┘  │
    │                 ↓               │
    │           ┌──────────┐          │
    │           │ Database │          │
    │           └──────────┘          │
    └──────────────────────────────────┘
              /           \
    [External System X]  [External System Y]
```

### Element Styles

**System Boundary:**
- Shape: Dashed rounded rectangle encompassing all containers
- Fill: None (transparent), Border: #438DD5, dashed
- Label: System name + "(System Boundary)" at top

**Container — Web Application:**
- Fill: #438DD5, text color: #FFFFFF
- Label: Container name, description, technology (e.g., "React SPA")

**Container — API / Service:**
- Fill: #438DD5, text color: #FFFFFF
- Label: Service name, description, technology (e.g., "Java Spring Boot")

**Container — Database:**
- Shape: Cylinder (`shape=cylinder3`)
- Fill: #438DD5, text color: #FFFFFF
- Label: DB name, description, technology (e.g., "PostgreSQL")

**Container — Message Queue:**
- Fill: #438DD5, text color: #FFFFFF
- Label: Queue/topic name, description, technology (e.g., "Apache Kafka")

**Container — File System:**
- Fill: #438DD5, text color: #FFFFFF
- Label: Storage name, description, technology

**External Person/System** (outside boundary):
- Same styles as C4 Context diagram

### Relationship Arrows

Labels must include:
- Action description (e.g., "Reads/writes assessment data")
- Protocol and technology (e.g., "JDBC", "REST/HTTPS", "Kafka topic")

### What NOT to Show at Level 2
- No internal classes or code structures
- No deployment infrastructure (servers, clusters)
- No network topology

---

## 3. C4 Component Diagram (Level 3)

### Purpose
Zooms into a single container to show its internal components. Answers:
"What are the major structural building blocks inside this container, and
how do they interact?"

### Audience
Developers working within a specific container — implementation-level detail.

### Layout

```
    ┌────────────────────────────────────────────────────┐
    │   Container Boundary: [Container Name]              │
    │                                                     │
    │  ┌──────────┐  ┌──────────┐  ┌──────────┐         │
    │  │Controller│→ │Service   │→ │Repository│         │
    │  │Layer     │  │Layer     │  │Layer     │         │
    │  └──────────┘  └──────────┘  └──────────┘         │
    │                     ↓                               │
    │              ┌──────────┐                           │
    │              │Event     │                           │
    │              │Publisher │                           │
    │              └──────────┘                           │
    └────────────────────────────────────────────────────┘
          ↕                    ↕                  ↕
    [Other Container]   [Database]         [Message Queue]
```

### Element Styles

**Container Boundary:**
- Shape: Dashed rounded rectangle
- Fill: None (transparent), Border: #438DD5, dashed
- Label: Container name + "(Container Boundary)" at top

**Component:**
- Shape: Rounded rectangle with stereotype label
- Fill: #85BBF0, text color: #FFFFFF
- Label: Component name (bold), description, technology
- Stereotype: `<<Controller>>`, `<<Service>>`, `<<Repository>>`, `<<Component>>`

**External Container/System** (outside boundary):
- Same styles as C4 Container diagram external elements
- Fill: #999999 for external systems, #438DD5 for sibling containers

### Relationship Arrows

Labels must include:
- Method or interface description (e.g., "Calls getUserById()")
- Technology (e.g., "Spring @Autowired", "REST internal", "JPA")

### What NOT to Show at Level 3
- No individual classes or methods (that's Level 4 — Code)
- No deployment details
- No network topology
- Components should be meaningful groupings, not every class

### C4 Component Quality Checklist

```
☐ Single container is the focus — boundary clearly labeled
☐ All components have name, description, and technology
☐ Stereotype labels present (<<Controller>>, <<Service>>, etc.)
☐ All relationships labeled with description and technology
☐ External containers/systems shown outside boundary
☐ Consistent with parent C4 Container diagram
☐ No individual class-level detail
☐ TBD markers on elements with missing details
```

---

## 5. C4 and HLLFD Relationship

The C4 Context Diagram and HLLFD show the SAME system from different perspectives:

| Aspect | HLLFD | C4 Context |
|--------|-------|-----------|
| Focus | Detailed data flows and workflows | System boundaries and interactions |
| Internal components | Shown (services, DBs, batch) | Hidden (single system box) |
| Workflow numbering | Required | Not applicable |
| Color coding | By domain and integration type | By element type (person/system) |
| Audience | Technical architects, P2B reviewers | All stakeholders including business |
| Layout | Horizontal tri-section | Central system with radial connections |

**Consistency Rule**: Both diagrams must show the same external systems,
the same users, and the same integrations. If a system appears in the HLLFD
inputs, it must appear in the C4 Context as an external system.

---

## 6. C4 Quality Checklist

```
☐ Central system clearly identified with name, description, technology
☐ All user roles shown as person shapes with descriptions
☐ All external systems shown with names and descriptions
☐ Every relationship arrow labeled with data description and protocol
☐ Sync vs async distinguished (solid vs dashed)
☐ No internal components shown at Level 1 (Context)
☐ System boundary shown at Level 2 (Container)
☐ All containers labeled with name, description, and technology
☐ Consistent with HLLFD if both exist for same system
☐ No assumed systems or integrations — all from sources
☐ TBD markers on elements with missing details
```

---

## 7. draw.io C4 Shape Library

draw.io includes a built-in C4 shape library. To use:
- In draw.io: Search → "C4" in shape libraries
- Shapes include: C4 Person, C4 Software System, C4 Container, C4 Component
- These shapes use metadata/placeholders for labels (name, description, technology)

When generating XML, you can use either:
- Standard mxCell shapes with C4-appropriate styling (recommended for this skill)
- draw.io's C4 library shapes with UserObject metadata

The standard approach is preferred because it gives full control over positioning
and styling without requiring the C4 library to be enabled in the user's draw.io.
