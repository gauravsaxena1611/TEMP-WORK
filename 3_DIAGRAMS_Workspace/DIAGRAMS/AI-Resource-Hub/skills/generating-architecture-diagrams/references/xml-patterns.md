# draw.io XML Reference Patterns
## Copy-paste-ready XML snippets for generating-architecture-diagrams skill

Use these patterns as structural templates when building diagram elements.
Replace placeholder values ([BRACKETS]) with actual content.

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
  fillColor=[FILL];strokeColor=[BORDER];verticalAlign=top;dashed=[0|1];"
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
  &lt;font style=&quot;color: rgb(102, 102, 102);&quot;&gt;• [Detail 1]&lt;/font&gt;
  &lt;/div&gt;&lt;div style=&quot;text-align: left;&quot;&gt;
  &lt;font style=&quot;color: rgb(102, 102, 102);&quot;&gt;• [Detail 2]&lt;/font&gt;
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
  style="rounded=1;whiteSpace=wrap;html=1;fontSize=11;
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
  &lt;font style=&quot;color: rgb(102, 102, 102);&quot;&gt;• [Purpose 1]&lt;/font&gt;
  &lt;/div&gt;&lt;div style=&quot;text-align: left;&quot;&gt;
  &lt;font style=&quot;color: rgb(102, 102, 102);&quot;&gt;• [Purpose 2]&lt;/font&gt;
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
  style="rounded=1;whiteSpace=wrap;html=1;fontSize=14;fontStyle=1;
  fillColor=#FFF8E1;strokeColor=#F9A825;verticalAlign=top;"
  parent="1" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="[W]" height="130" as="geometry"/>
</mxCell>
```

---

## 8. Connector Arrow (Unidirectional)

```xml
<mxCell id="[flow-id]"
  value="[Step#]. [Description]"
  style="endArrow=classic;html=1;strokeWidth=[WIDTH];strokeColor=[COLOR];
  edgeStyle=orthogonalEdgeStyle;rounded=0;fontSize=10;"
  parent="1" source="[source-vertex-id]" target="[target-vertex-id]" edge="1">
  <mxGeometry relative="1" as="geometry"/>
</mxCell>
```

---

## 9. Connector Arrow (Bidirectional)

```xml
<mxCell id="[flow-id]"
  value="[Step#]. [Description]"
  style="endArrow=classic;startArrow=classic;startFill=1;html=1;
  strokeWidth=[WIDTH];strokeColor=[COLOR];
  edgeStyle=orthogonalEdgeStyle;rounded=0;fontSize=10;"
  parent="1" source="[source-vertex-id]" target="[target-vertex-id]" edge="1">
  <mxGeometry relative="1" as="geometry"/>
</mxCell>
```

---

## 10. Connector Arrow (Dashed — for Kafka/Async)

```xml
<mxCell id="[flow-id]"
  value="[Step#]. [Description]"
  style="endArrow=classic;html=1;strokeWidth=2;strokeColor=#8E44AD;
  dashed=1;edgeStyle=orthogonalEdgeStyle;rounded=0;fontSize=10;"
  parent="1" source="[source-vertex-id]" target="[target-vertex-id]" edge="1">
  <mxGeometry relative="1" as="geometry"/>
</mxCell>
```

---

## 11. Title

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

## 12. Legend Container

```xml
<mxCell id="legend-container" value="Key"
  style="rounded=1;whiteSpace=wrap;html=1;fontSize=14;fontStyle=1;
  fillColor=#FFFDE7;strokeColor=#F9A825;verticalAlign=top;"
  parent="1" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="250" height="[H]" as="geometry"/>
</mxCell>
```

### Legend Line Entry

```xml
<mxCell id="legend-[type]"
  value=""
  style="endArrow=classic;html=1;strokeWidth=2;strokeColor=[COLOR];[dashed=1;]"
  parent="legend-container" edge="1">
  <mxGeometry relative="1" as="geometry">
    <mxPoint x="10" y="[Y]" as="sourcePoint"/>
    <mxPoint x="60" y="[Y]" as="targetPoint"/>
  </mxGeometry>
</mxCell>
<mxCell id="legend-[type]-label"
  value="[Integration Type Name]"
  style="text;html=1;fontSize=10;align=left;"
  parent="legend-container" vertex="1">
  <mxGeometry x="70" y="[Y-10]" width="150" height="20" as="geometry"/>
</mxCell>
```

---

## 13. Workflow Notes Section

```xml
<mxCell id="notes-section"
  value="&lt;div style=&quot;text-align: left; font-size: 11px;&quot;&gt;
  &lt;b&gt;Workflow 1 — [Name]&lt;/b&gt;&lt;br&gt;
  1. [Step description]&lt;br&gt;
  2. [Step description]&lt;br&gt;
  &lt;br&gt;
  &lt;b&gt;Workflow A — [Name]&lt;/b&gt;&lt;br&gt;
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

## 14. Application Metadata Notes

```xml
<mxCell id="metadata-notes"
  value="&lt;div style=&quot;text-align: left; font-size: 10px;&quot;&gt;
  • [APP] went live in [Date]&lt;br&gt;
  • Deployed on [Platform]&lt;br&gt;
  • UI integrated with [System]&lt;br&gt;
  • [Acronym] = [Expansion]&lt;br&gt;
  &lt;/div&gt;"
  style="shape=note;whiteSpace=wrap;html=1;backgroundOutline=1;size=15;
  fontSize=9;fillColor=#E8F5E9;strokeColor=#66BB6A;align=left;
  verticalAlign=top;spacingLeft=10;spacingTop=10;"
  parent="1" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="300" height="[H]" as="geometry"/>
</mxCell>
```

---

## 15. Gateway/Firewall Box (for External Systems)

```xml
<mxCell id="gateway-[name]"
  value="&lt;b&gt;Firewall / Gateway&lt;/b&gt;"
  style="rounded=1;whiteSpace=wrap;html=1;fontSize=11;fontStyle=1;
  fillColor=#F5B7B1;strokeColor=#922B21;dashed=1;dashPattern=5 5;"
  parent="[external-container-id]" vertex="1">
  <mxGeometry x="[X]" y="[Y]" width="[W]" height="40" as="geometry"/>
</mxCell>
```
