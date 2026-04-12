# Swimlane / BPMN 2.0 — draw.io XML Reference
## Version 1.0 | 2026-04-12 | Part of SKILL-ARCHDIAG v5.0

**Source:** [024 Swimlane & Workflow Diagrams] ✅ — OMG BPMN 2.0 (ISO/IEC 19510:2013); Rummler & Brache (1990)
**Parent:** SKILL.md [PLAYBOOK-SWIM]

---

## XML Structure — Pool and Lanes

```xml
<mxGraphModel adaptiveColors="auto" dx="4000" dy="2500" grid="1" gridSize="10"
  guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="0"
  pageScale="1" pageWidth="1600" pageHeight="1200" math="0" shadow="0">
  <root>
    <mxCell id="0"/>
    <mxCell id="1" parent="0"/>

    <!-- POOL (outermost container) -->
    <mxCell id="pool-1" value="Process Name" style="shape=pool;startSize=30;horizontal=1;fillColor=#dae8fc;strokeColor=#6c8ebf;"
      vertex="1" parent="1">
      <mxGeometry x="60" y="60" width="1200" height="400" as="geometry"/>
    </mxCell>

    <!-- LANE 1 inside pool -->
    <mxCell id="lane-customer" value="Customer" style="swimlane;startSize=30;horizontal=0;fillColor=#f5f5f5;strokeColor=#666666;"
      vertex="1" parent="pool-1">
      <mxGeometry x="0" y="0" width="1200" height="200" as="geometry"/>
    </mxCell>

    <!-- LANE 2 inside pool -->
    <mxCell id="lane-system" value="Order System" style="swimlane;startSize=30;horizontal=0;fillColor=#fff2cc;strokeColor=#d6b656;"
      vertex="1" parent="pool-1">
      <mxGeometry x="0" y="200" width="1200" height="200" as="geometry"/>
    </mxCell>

    <!-- START EVENT -->
    <mxCell id="start-1" value="Order Placed" style="ellipse;whiteSpace=wrap;html=1;aspect=fixed;fillColor=#d5e8d4;strokeColor=#82b366;"
      vertex="1" parent="lane-customer">
      <mxGeometry x="60" y="80" width="40" height="40" as="geometry"/>
    </mxCell>

    <!-- TASK -->
    <mxCell id="task-validate" value="Validate Order" style="rounded=1;whiteSpace=wrap;html=1;fillColor=#dae8fc;strokeColor=#6c8ebf;"
      vertex="1" parent="lane-system">
      <mxGeometry x="200" y="75" width="120" height="50" as="geometry"/>
    </mxCell>

    <!-- XOR GATEWAY -->
    <mxCell id="gw-valid" value="Valid?" style="rhombus;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;"
      vertex="1" parent="lane-system">
      <mxGeometry x="400" y="65" width="70" height="70" as="geometry"/>
    </mxCell>

    <!-- END EVENT -->
    <mxCell id="end-1" value="Order Confirmed" style="ellipse;whiteSpace=wrap;html=1;aspect=fixed;fillColor=#f8cecc;strokeColor=#b85450;strokeWidth=4;"
      vertex="1" parent="lane-customer">
      <mxGeometry x="900" y="80" width="40" height="40" as="geometry"/>
    </mxCell>

    <!-- SEQUENCE FLOW -->
    <mxCell id="sf-1" value="" style="edgeStyle=orthogonalEdgeStyle;"
      edge="1" source="start-1" target="task-validate" parent="pool-1">
      <mxGeometry relative="1" as="geometry" />
    </mxCell>

    <mxCell id="sf-2" value="" style="edgeStyle=orthogonalEdgeStyle;"
      edge="1" source="task-validate" target="gw-valid" parent="lane-system">
      <mxGeometry relative="1" as="geometry" />
    </mxCell>

  </root>
</mxGraphModel>
```

---

## BPMN Shape Style Reference

| BPMN Element | draw.io Style |
|---|---|
| Pool | `shape=pool;startSize=30;horizontal=1;` |
| Lane (horizontal) | `swimlane;startSize=30;horizontal=0;` |
| Start Event (None) | `ellipse;fillColor=#d5e8d4;strokeColor=#82b366;aspect=fixed;` |
| End Event (None) | `ellipse;strokeWidth=4;fillColor=#f8cecc;strokeColor=#b85450;aspect=fixed;` |
| Task | `rounded=1;whiteSpace=wrap;html=1;` |
| XOR Gateway | `rhombus;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;` |
| AND Gateway | `rhombus;whiteSpace=wrap;html=1;fillColor=#d5e8d4;` (add "+" in label) |
| Intermediate Timer | `ellipse;aspect=fixed;dashed=1;strokeColor=#d6b656;` (double-border) |
| Boundary Error Event | `ellipse;aspect=fixed;strokeColor=#b85450;fillColor=#f8cecc;` (attached to task edge) |
| Message Flow | `dashed=1;endArrow=open;` |
| Sequence Flow | `edgeStyle=orthogonalEdgeStyle;` |

---

## Lane Color Convention

| Lane Actor Type | Fill Color | Hex |
|---|---|---|
| Customer / External User | Light blue-grey | #f5f5f5 |
| Internal Business Role | Light yellow | #fff2cc |
| Automated System / Service | Light blue | #dae8fc |
| External System / Third Party | Light grey | #e0e0e0 |
| Management / Approver | Light green | #d5e8d4 |

---

## Anti-Patterns (per [052] and [024])

- NEVER use individual person names as lane titles — use roles only
- NEVER omit Start and End Events
- NEVER use a plain text label for a decision — use a BPMN Gateway diamond
- NEVER show only the happy path — add Boundary Error Events for failure conditions
- Flag if connector crossings exceed 5 (Spaghetti Lane — E2)
