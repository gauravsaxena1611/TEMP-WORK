# State Diagram — draw.io XML Reference
## Version 1.0 | 2026-04-12 | Part of SKILL-ARCHDIAG v5.0

**Source:** [025 Sequence, ERD & Deployment Diagrams] + UML 2.5.1 ✅
**Parent:** SKILL.md [PLAYBOOK-STATE]

---

## XML Structure — UML State Machine

```xml
<mxGraphModel adaptiveColors="auto" dx="4000" dy="2500" grid="1" gridSize="10"
  guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="0"
  pageScale="1" pageWidth="1600" pageHeight="1200" math="0" shadow="0">
  <root>
    <mxCell id="0"/>
    <mxCell id="1" parent="0"/>

    <!-- INITIAL PSEUDOSTATE (filled black circle) -->
    <mxCell id="initial" value="" style="ellipse;fillColor=#000000;strokeColor=#000000;aspect=fixed;"
      vertex="1" parent="1">
      <mxGeometry x="200" y="100" width="30" height="30" as="geometry"/>
    </mxCell>

    <!-- STATE (rounded rectangle with name) -->
    <mxCell id="state-pending" value="Pending" style="rounded=1;whiteSpace=wrap;html=1;fillColor=#dae8fc;strokeColor=#6c8ebf;"
      vertex="1" parent="1">
      <mxGeometry x="350" y="90" width="120" height="50" as="geometry"/>
    </mxCell>

    <mxCell id="state-processing" value="Processing" style="rounded=1;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;"
      vertex="1" parent="1">
      <mxGeometry x="550" y="90" width="120" height="50" as="geometry"/>
    </mxCell>

    <mxCell id="state-completed" value="Completed" style="rounded=1;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;"
      vertex="1" parent="1">
      <mxGeometry x="750" y="90" width="120" height="50" as="geometry"/>
    </mxCell>

    <mxCell id="state-failed" value="Failed" style="rounded=1;whiteSpace=wrap;html=1;fillColor=#f8cecc;strokeColor=#b85450;"
      vertex="1" parent="1">
      <mxGeometry x="550" y="220" width="120" height="50" as="geometry"/>
    </mxCell>

    <!-- TERMINAL STATE (circle with thick border) -->
    <mxCell id="terminal" value="" style="ellipse;fillColor=#000000;strokeColor=#000000;aspect=fixed;strokeWidth=4;fillColor=#ffffff;"
      vertex="1" parent="1">
      <mxGeometry x="900" y="100" width="30" height="30" as="geometry"/>
    </mxCell>

    <!-- TRANSITIONS (labeled arrows) -->
    <mxCell id="t-init-pending" value="create / assign ID" style="edgeStyle=orthogonalEdgeStyle;"
      edge="1" source="initial" target="state-pending" parent="1">
      <mxGeometry relative="1" as="geometry" />
    </mxCell>

    <mxCell id="t-pending-processing" value="submit [valid] / start processing" style="edgeStyle=orthogonalEdgeStyle;"
      edge="1" source="state-pending" target="state-processing" parent="1">
      <mxGeometry relative="1" as="geometry" />
    </mxCell>

    <mxCell id="t-processing-completed" value="complete / notify user" style="edgeStyle=orthogonalEdgeStyle;"
      edge="1" source="state-processing" target="state-completed" parent="1">
      <mxGeometry relative="1" as="geometry" />
    </mxCell>

    <mxCell id="t-processing-failed" value="error [timeout | system failure]" style="edgeStyle=orthogonalEdgeStyle;"
      edge="1" source="state-processing" target="state-failed" parent="1">
      <mxGeometry relative="1" as="geometry" />
    </mxCell>

    <mxCell id="t-completed-terminal" value="" style="edgeStyle=orthogonalEdgeStyle;"
      edge="1" source="state-completed" target="terminal" parent="1">
      <mxGeometry relative="1" as="geometry" />
    </mxCell>

  </root>
</mxGraphModel>
```

---

## UML State Machine Shape Reference

| Element | draw.io Style | Notes |
|---|---|---|
| Initial pseudostate | `ellipse;fillColor=#000000;strokeColor=#000000;aspect=fixed;` | Solid black circle, no label |
| State | `rounded=1;whiteSpace=wrap;html=1;` | State name in label |
| Terminal state | `ellipse;strokeWidth=4;fillColor=#ffffff;strokeColor=#000000;aspect=fixed;` | Circle with thick outer ring |
| Choice pseudostate | `rhombus;whiteSpace=wrap;` | For conditional branching |
| Composite state (nested) | Outer: `swimlane;startSize=25;` | Inner states as children |
| Entry/Do/Exit actions | Multi-section rounded rect with separator lines | Label format: `name\n──────\nentry: action\ndo: action\nexit: action` |

---

## Transition Label Format

```
[event] [guard condition] / [action]

Examples:
  submit [valid] / assign ID
  timeout [retries > 3] / send to DLQ
  cancel / void transaction
```

---

## State Color Convention

| State Type | Fill Color | Hex |
|---|---|---|
| Initial/Draft | Light blue | #dae8fc |
| Active/Processing | Light green | #d5e8d4 |
| Waiting/Pending | Light yellow | #fff2cc |
| Error/Failed | Light red | #f8cecc |
| Completed/Terminal | White | #ffffff |
| Cancelled/Archived | Light grey | #f5f5f5 |

---

## Anti-Patterns (per [052] and UML 2.5.1)

- NEVER omit the initial pseudostate
- NEVER create an orphan state (state with no entry or no exit except terminal)
- NEVER leave a transition unlabeled — always include event [guard] / action
- NEVER omit guard conditions on conditional branches
- Flag if >15 states on one diagram — suggest decomposing to sub-state machines
