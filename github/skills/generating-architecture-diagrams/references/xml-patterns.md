# draw.io XML Patterns — v4.0
## Copy-paste-ready XML snippets for generating-architecture-diagrams skill

**Version:** 4.0
**Updated:** April 11, 2026
**Source alignment:** jgraph/drawio-mcp skill-cli + shared/xml-reference.md

Use these patterns as structural templates when building diagram elements.
Replace placeholder values ([BRACKETS]) with actual content.

REMINDER: NEVER add XML comments to generated output. Use descriptive IDs instead.

---

## 0. Minimal Valid Diagram Template

```xml
<mxGraphModel adaptiveColors="auto" dx="4000" dy="2500" grid="1" gridSize="10"
  guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="0"
  pageScale="1" pageWidth="1600" pageHeight="1200" math="0" shadow="0">
  <root>
    <mxCell id="0"/>
    <mxCell id="1" parent="0"/>
    <mxCell id="diagram-title" value="[APP] - [Diagram Type]"
      style="text;html=1;strokeColor=none;fillColor=none;align=center;
      verticalAlign=middle;whiteSpace=wrap;fontSize=20;fontStyle=1;"
      vertex="1" parent="1">
      <mxGeometry x="0" y="-80" width="800" height="40" as="geometry"/>
    </mxCell>
  </root>
</mxGraphModel>
```

---

## 1. Swimlane Container (Section)

```xml
<mxCell id="[section-id]" value="[Section Title]"
  style="swimlane;whiteSpace=wrap;html=1;fontSize=16;fontStyle=1;
  fillColor=[FILL];strokeColor=[BORDER];startSize=40;
  verticalAlign=middle;align=center;"
  parent="1" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="[W]" height="[H]" as="geometry"/>
</mxCell>
```

**Typical swimlane positions (HLLFD):**
- Inputs: `x="-500" y="-270" width="300" height="890"`
- Processing: `x="10" y="-270" width="630" height="890"`
- Consumes: `x="710" y="-270" width="280" height="890"`

---

## 2. Domain Grouping Container (Inside Swimlane)

```xml
<mxCell id="[group-id]" value="[Group Name]"
  style="rounded=1;whiteSpace=wrap;html=1;fontSize=12;fontStyle=1;
  fillColor=[FILL];strokeColor=[BORDER];verticalAlign=top;container=1;
  pointerEvents=0;dashed=[0|1];"
  parent="[swimlane-id]" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="[W]" height="[H]" as="geometry"/>
</mxCell>
```

Use `dashed=1` for External Systems containers.

---

## 3. Application/System Box

```xml
<mxCell id="[system-id]"
  value="&lt;font style=&quot;font-size: 13px;&quot;&gt;&lt;b&gt;[System Name]&lt;/b&gt;
  &lt;br&gt;&lt;br&gt;&lt;div style=&quot;text-align: left;&quot;&gt;
  &lt;font style=&quot;color: rgb(102, 102, 102);&quot;&gt;- [Detail 1]&lt;/font&gt;
  &lt;/div&gt;&lt;div style=&quot;text-align: left;&quot;&gt;
  &lt;font style=&quot;color: rgb(102, 102, 102);&quot;&gt;- [Detail 2]&lt;/font&gt;
  &lt;/div&gt;&lt;/font&gt;"
  style="rounded=1;whiteSpace=wrap;html=1;fontSize=9;
  fillColor=[FILL];strokeColor=[BORDER];"
  parent="[parent-id]" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="[W]" height="[H]" as="geometry"/>
</mxCell>
```

Minimum size: `width="160" height="80"` for boxes with bullet points.

---

## 4. Core Components Container (Inside Processing)

```xml
<mxCell id="core-components"
  value="&lt;font style=&quot;color: rgb(102, 102, 102); font-size: 15px;&quot;&gt;
  &lt;b&gt;[APP] Core Components&lt;/b&gt;&lt;/font&gt;"
  style="rounded=1;whiteSpace=wrap;html=1;fontSize=11;container=1;pointerEvents=0;
  fillColor=#E4E4E4;strokeColor=#6c8ebf;verticalAlign=top;"
  parent="[processing-section-id]" vertex="1">
  <mxGeometry x="45" y="80" width="540" height="780" as="geometry"/>
</mxCell>
```

---

## 5. Database Cylinder

```xml
<mxCell id="[db-id]"
  value="&lt;font style=&quot;font-size: 13px;&quot;&gt;&lt;b&gt;[DB Name]&lt;/b&gt;
  &lt;br&gt;&lt;br&gt;&lt;div style=&quot;text-align: left;&quot;&gt;
  &lt;font style=&quot;color: rgb(102, 102, 102);&quot;&gt;- [Purpose 1]&lt;/font&gt;
  &lt;/div&gt;&lt;div style=&quot;text-align: left;&quot;&gt;
  &lt;font style=&quot;color: rgb(102, 102, 102);&quot;&gt;- [Purpose 2]&lt;/font&gt;
  &lt;/div&gt;&lt;/font&gt;"
  style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;
  backgroundOutline=1;size=15;fontSize=9;
  fillColor=[FILL];strokeColor=[BORDER];"
  parent="1" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="180" height="115" as="geometry"/>
</mxCell>
```

---

## 6. User/Actor Shape

```xml
<mxCell id="[user-id]"
  value="[Role Name]"
  style="shape=mxgraph.basic.person;fillColor=[FILL];strokeColor=[BORDER];
  fontSize=11;fontStyle=1;"
  parent="1" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="60" height="70" as="geometry"/>
</mxCell>
```

---

## 7. User Group Container

```xml
<mxCell id="users-container" value="[APP] User Types"
  style="rounded=1;whiteSpace=wrap;html=1;fontSize=14;fontStyle=1;container=1;
  pointerEvents=0;fillColor=#FFF8E1;strokeColor=#F9A825;verticalAlign=top;"
  parent="1" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="[W]" height="130" as="geometry"/>
</mxCell>
```

---

## 8. Connector Arrow (Unidirectional) — ALWAYS EXPANDED

```xml
<mxCell id="[flow-id]"
  value="[Step#]. [Description]"
  style="endArrow=classic;html=1;strokeWidth=[WIDTH];strokeColor=[COLOR];
  edgeStyle=orthogonalEdgeStyle;rounded=0;fontSize=10;"
  parent="1" source="[source-vertex-id]" target="[target-vertex-id]" edge="1">
  <mxGeometry relative="1" as="geometry" />
</mxCell>
```

---

## 9. Connector Arrow (Bidirectional) — ALWAYS EXPANDED

```xml
<mxCell id="[flow-id]"
  value="[Step#]. [Description]"
  style="endArrow=classic;startArrow=classic;startFill=1;html=1;
  strokeWidth=[WIDTH];strokeColor=[COLOR];
  edgeStyle=orthogonalEdgeStyle;rounded=0;fontSize=10;"
  parent="1" source="[source-vertex-id]" target="[target-vertex-id]" edge="1">
  <mxGeometry relative="1" as="geometry" />
</mxCell>
```

---

## 10. Connector Arrow (Dashed — for Async/Kafka/Planned) — ALWAYS EXPANDED

```xml
<mxCell id="[flow-id]"
  value="[Step#]. [Description]"
  style="endArrow=classic;html=1;strokeWidth=2;strokeColor=#8E44AD;
  dashed=1;edgeStyle=orthogonalEdgeStyle;rounded=0;fontSize=10;"
  parent="1" source="[source-vertex-id]" target="[target-vertex-id]" edge="1">
  <mxGeometry relative="1" as="geometry" />
</mxCell>
```

---

## 11. Edge with Explicit Waypoints — ALWAYS EXPANDED

```xml
<mxCell id="[flow-id]"
  value="[Label]"
  style="edgeStyle=orthogonalEdgeStyle;"
  edge="1" source="[source-id]" target="[target-id]" parent="1">
  <mxGeometry relative="1" as="geometry">
    <Array as="points">
      <mxPoint x="[X1]" y="[Y1]"/>
      <mxPoint x="[X2]" y="[Y2]"/>
    </Array>
  </mxGeometry>
</mxCell>
```

---

## 12. Floating Edge (No source/target vertex) — ALWAYS EXPANDED

Use for legend line entries where no real source/target vertex exists:

```xml
<mxCell id="legend-[type]"
  value=""
  style="endArrow=classic;html=1;strokeWidth=2;strokeColor=[COLOR];"
  parent="legend-container" edge="1">
  <mxGeometry relative="1" as="geometry">
    <mxPoint x="10" y="[Y]" as="sourcePoint"/>
    <mxPoint x="60" y="[Y]" as="targetPoint"/>
  </mxGeometry>
</mxCell>
```

---

## 13. Title

```xml
<mxCell id="diagram-title"
  value="[APP FULL NAME] ([APP SHORT]) - High Level Architecture"
  style="text;html=1;strokeColor=none;fillColor=none;align=center;
  verticalAlign=middle;whiteSpace=wrap;rounded=0;fontSize=20;fontStyle=1;"
  parent="1" vertex="1">
  <mxGeometry x="-420" y="-620" width="800" height="30" as="geometry"/>
</mxCell>
```

---

## 14. Legend Container

```xml
<mxCell id="legend-container" value="Key"
  style="rounded=1;whiteSpace=wrap;html=1;fontSize=14;fontStyle=1;container=1;
  pointerEvents=0;fillColor=#FFFDE7;strokeColor=#F9A825;verticalAlign=top;"
  parent="1" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="250" height="[H]" as="geometry"/>
</mxCell>

<mxCell id="legend-label-[type]"
  value="[Integration Type Name]"
  style="text;html=1;fontSize=10;align=left;"
  parent="legend-container" vertex="1">
  <mxGeometry x="70" y="[Y]" width="150" height="20" as="geometry"/>
</mxCell>
```

---

## 15. Workflow Notes Section

```xml
<mxCell id="notes-section"
  value="&lt;div style=&quot;text-align: left; font-size: 11px;&quot;&gt;
  &lt;b&gt;Workflow 1 - [Name]&lt;/b&gt;&lt;br&gt;
  1. [Step description]&lt;br&gt;
  2. [Step description]&lt;br&gt;
  &lt;br&gt;
  &lt;b&gt;Workflow A - [Name]&lt;/b&gt;&lt;br&gt;
  A. [Step description]&lt;br&gt;
  B. [Step description]&lt;br&gt;
  &lt;/div&gt;"
  style="shape=note;whiteSpace=wrap;html=1;backgroundOutline=1;size=15;
  fontSize=9;fillColor=#FFF9C4;strokeColor=#F9A825;align=left;
  verticalAlign=top;spacingLeft=10;spacingTop=10;"
  parent="1" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="350" height="[H]" as="geometry"/>
</mxCell>
```

---

## 16. Application Metadata Notes

```xml
<mxCell id="metadata-notes"
  value="&lt;div style=&quot;text-align: left; font-size: 10px;&quot;&gt;
  - [APP] went live in [Date]&lt;br&gt;
  - Deployed on [Platform]&lt;br&gt;
  - UI integrated with [System]&lt;br&gt;
  - [Acronym] = [Expansion]&lt;br&gt;
  &lt;/div&gt;"
  style="shape=note;whiteSpace=wrap;html=1;backgroundOutline=1;size=15;
  fontSize=9;fillColor=#E8F5E9;strokeColor=#66BB6A;align=left;
  verticalAlign=top;spacingLeft=10;spacingTop=10;"
  parent="1" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="300" height="[H]" as="geometry"/>
</mxCell>
```

---

## 17. Gateway/Firewall Box (for External Systems)

```xml
<mxCell id="gateway-[name]"
  value="&lt;b&gt;Firewall / Gateway&lt;/b&gt;"
  style="rounded=1;whiteSpace=wrap;html=1;fontSize=11;fontStyle=1;
  fillColor=#F5B7B1;strokeColor=#922B21;dashed=1;dashPattern=5 5;"
  parent="[external-container-id]" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="[W]" height="40" as="geometry"/>
</mxCell>
```

---

## 18. PLANNED / Future System (Dashed Border)

```xml
<mxCell id="[planned-id]"
  value="[System Name] (PLANNED)"
  style="rounded=1;whiteSpace=wrap;html=1;dashed=1;dashPattern=5 5;
  fillColor=#f5f5f5;strokeColor=#999999;fontColor=#666666;"
  parent="1" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="160" height="60" as="geometry"/>
</mxCell>
```

---

## 19. Tagged Element (Cross-Cutting Visibility)

```xml
<object id="[elem-id]" label="[Display Label]" tags="[tag1] [tag2]">
  <mxCell style="rounded=1;whiteSpace=wrap;html=1;fillColor=[FILL];strokeColor=[BORDER];"
    vertex="1" parent="1">
    <mxGeometry x="[X]" y="[Y]" width="[W]" height="[H]" as="geometry"/>
  </mxCell>
</object>
```

---

## 20. Metadata Shape with Placeholders

```xml
<object id="[elem-id]"
  label="&lt;b&gt;%component%&lt;/b&gt;&lt;br&gt;Owner: %owner%&lt;br&gt;Status: %status%"
  placeholders="1" component="[Name]" owner="[Team]" status="[Active|Planned|Deprecated]">
  <mxCell style="rounded=1;whiteSpace=wrap;html=1;fillColor=[FILL];strokeColor=[BORDER];"
    vertex="1" parent="1">
    <mxGeometry x="[X]" y="[Y]" width="180" height="80" as="geometry"/>
  </mxCell>
</object>
```

---

## 21. Layer Definition

```xml
<mxCell id="layer-[name]" value="[Layer Display Name]" parent="0"/>
```

Add this after `<mxCell id="1" parent="0"/>`. Then assign shapes to this layer via `parent="layer-[name]"`.

---

## QUICK REFERENCE: What to Replace

| Placeholder | Replace with |
|-------------|-------------|
| `[section-id]` | Descriptive ID, e.g., `section-inputs`, `section-processing` |
| `[system-id]` | Descriptive ID, e.g., `crm-system`, `auth-service` |
| `[db-id]` | Descriptive ID, e.g., `user-db`, `event-store` |
| `[flow-id]` | Descriptive ID, e.g., `flow-crm-to-auth`, `step-1-api-call` |
| `[FILL]` | Hex color from color-palette.md, e.g., `#DAE8FC` |
| `[BORDER]` | Hex color from color-palette.md, e.g., `#6C8EBF` |
| `[X]`, `[Y]` | Pixel coordinates (align to 10px grid) |
| `[W]`, `[H]` | Width/height in pixels (respect minimum sizes) |
| `[Step#]` | Workflow step number from workflow-numbering.md |
| `[parent-id]` | ID of the parent container (or "1" for top-level canvas) |
