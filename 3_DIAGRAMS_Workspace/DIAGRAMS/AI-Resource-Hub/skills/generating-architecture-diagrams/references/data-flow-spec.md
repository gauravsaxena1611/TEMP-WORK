# Data Flow Diagram (DFD) Specification
## Reference for generating-architecture-diagrams skill

Based on Tom DeMarco / Yourdon-DeMarco notation and Gane-Sarson notation,
adapted for draw.io XML generation. Shows how data moves through a system.

---

## 1. Purpose

Shows the flow of data between processes, data stores, and external entities.
Answers: "Where does data come from, how is it processed, and where does it go?"

### Audience
Business analysts, data architects, solution architects, compliance teams.

### When to Use Over Other Types
- When the **movement of data** is the primary concern
- When documenting data pipelines, ETL flows, or data lineage
- When explaining how data transforms as it passes through the system
- When compliance or audit requires data flow documentation

---

## 2. DFD Levels

### Level 0 — Context Diagram
Single process bubble representing the entire system, with external entities
and high-level data flows. Similar to C4 Context but focused on data, not systems.

### Level 1 — Major Processes
Breaks the system into major processes (3-7 typically), showing data stores
and the data flows between them.

### Level 2+ — Detailed Sub-Processes
Decomposes a single Level 1 process into more detail. Each Level 1 process
can have its own Level 2 diagram.

**Default to Level 1** unless user specifies otherwise.

---

## 3. Element Styles

### Process (Yourdon-DeMarco Style)
- Shape: Circle (rounded)
- Fill: #DAE8FC, Border: #6C8EBF
- Label: Process number (top, bold), Process name (center, 13px)
- Number format: 1.0, 2.0, 3.0 (Level 1) or 1.1, 1.2 (Level 2)

```
draw.io style: ellipse;whiteSpace=wrap;html=1;fillColor=#DAE8FC;strokeColor=#6C8EBF;fontSize=13;
```

### External Entity
- Shape: Rectangle
- Fill: #F5F5F5, Border: #666666
- Label: Entity name (bold, 13px)
- Can appear multiple times on diagram (duplicate marker: * or letter suffix)

```
draw.io style: rounded=0;whiteSpace=wrap;html=1;fillColor=#F5F5F5;strokeColor=#666666;fontSize=13;fontStyle=1;
```

### Data Store
- Shape: Open-ended rectangle (two horizontal lines)
- Fill: #FFF9C4, Border: #F9A825
- Label: Store ID (left, e.g., D1), Store name (center, 13px)

```
draw.io style: shape=partialRectangle;whiteSpace=wrap;html=1;top=0;bottom=0;fillColor=#FFF9C4;strokeColor=#F9A825;fontSize=13;
```

Alternative cylinder shape for databases:
```
draw.io style: shape=cylinder3;whiteSpace=wrap;html=1;fillColor=#FFF9C4;strokeColor=#F9A825;fontSize=13;boundedLbl=1;backgroundOutline=1;size=15;
```

---

## 4. Data Flow Arrow Styles

| Flow Type | Arrow Style | Color | Usage |
|-----------|-------------|-------|-------|
| Data Flow | Solid arrow, labeled | #2874A6 | Normal data movement |
| Bidirectional Flow | Double-headed arrow | #1A5276 | Read/write to data store |
| Real-time Stream | Solid arrow, thicker | #8E44AD | Kafka, event streams |
| Batch Transfer | Dashed arrow | #229954 | Scheduled file transfers, ETL |
| Error/Reject Flow | Solid arrow | #C0392B | Rejected records, error data |

### Arrow Label Format
Every data flow arrow MUST be labeled with the data it carries:
```
[Data Name] or [Data Description]
```

**Examples:**
- `Customer Order`
- `Validated Payment Record`
- `Daily Transaction File`
- `Error Notification`
- `Enriched Assessment Data`

---

## 5. DFD Rules (Enforced)

### Balancing Rules
1. Inputs and outputs of a parent process must match the child diagram
2. A process must have at least one input and one output
3. A data store must have at least one flow in and one flow out
4. External entities cannot communicate directly with data stores (must go through a process)
5. Two external entities cannot communicate directly (must go through a process)
6. Data cannot flow directly between two data stores without a process

### Naming Rules
- Processes: Verb + Object (e.g., "Validate Payment", "Generate Report")
- Data Stores: Noun (e.g., "Customer Database", "Order Cache")
- External Entities: Noun (e.g., "Customer", "Payment Gateway")
- Data Flows: Noun describing the data (e.g., "Order Details", "Payment Confirmation")

---

## 6. Layout Guidelines

### Level 0
- Central process in the middle
- External entities arranged around the perimeter
- Data flows connecting them with labeled arrows

### Level 1
- Processes arranged in logical flow (left-to-right or top-to-bottom)
- Data stores along the bottom or right side
- External entities along the top or left side
- Minimize crossing arrows

---

## 7. Sizing Guidelines

| Element | Recommended Size |
|---------|-----------------|
| Canvas | 2500x1800 minimum |
| Process circle | 120px diameter |
| External entity | 140x60px |
| Data store | 160x40px |
| Spacing between elements | Minimum 80px |
| Arrow label font | 10-11px |
| Element label font | 13px bold |

---

## 8. DFD Quality Checklist

```
☐ DFD level stated in diagram title (Level 0, Level 1, etc.)
☐ All processes numbered (1.0, 2.0 for L1; 1.1, 1.2 for L2)
☐ All processes have at least one input and one output
☐ All data flows labeled with data description
☐ No direct flows between two external entities
☐ No direct flows between external entity and data store
☐ No direct flows between two data stores
☐ External entities clearly distinguished from processes
☐ Data stores labeled with ID and name
☐ Balancing rule satisfied (L1 inputs/outputs match L0)
☐ No assumed data flows — all from sources
☐ TBD markers on flows with unknown data
```

---

## 9. Sources

| Source | Relevance |
|--------|-----------|
| Tom DeMarco, Structured Analysis and System Specification (1979) | DFD notation standard |
| Gane & Sarson, Structured Systems Analysis (1979) | Alternative DFD notation |
| ISO/IEC 19510:2013 (BPMN-adjacent data flow) | Process modeling standards |
