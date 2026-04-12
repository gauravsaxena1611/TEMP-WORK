# draw.io XML Generation Rules — v4.0
## Official Reference for generating-architecture-diagrams Skill

**Source:** jgraph/drawio-mcp — skill-cli/drawio/SKILL.md + shared/xml-reference.md
**This is the single source of truth for all draw.io XML generation in this skill.**
**Version:** 4.0
**Updated:** April 11, 2026

---

## 1. MANDATORY XML STRUCTURE

Every generated diagram MUST use this structure — no exceptions:

```xml
<mxGraphModel adaptiveColors="auto" dx="4000" dy="2500" grid="1" gridSize="10"
  guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="0"
  pageScale="1" pageWidth="1600" pageHeight="1200" math="0" shadow="0">
  <root>
    <mxCell id="0"/>
    <mxCell id="1" parent="0"/>
    [ALL VERTEX CELLS FIRST]
    [ALL EDGE CELLS AFTER ALL VERTICES]
  </root>
</mxGraphModel>
```

**Critical attributes:**
- `adaptiveColors="auto"` — enables automatic dark mode color adaptation
- `page="0"` — infinite canvas (prevents content clipping at page boundaries)
- `dx="4000" dy="2500"` — sufficient canvas for enterprise-scale diagrams

**What NOT to use:**
- Do NOT wrap in `<mxfile>` — the `<mxfile>` format implies compression and causes empty file symptoms
- Do NOT compress or Base64-encode output — always plain, human-readable XML
- Do NOT add a `<diagram>` wrapper element

---

## 2. STRICTLY FORBIDDEN PATTERNS

These patterns WILL cause files to fail to open, appear empty, or render broken:

```
FORBIDDEN #1: ANY XML COMMENTS
  <!-- this is forbidden --> is strictly illegal
  XML comments waste tokens, cause parse errors, and serve no purpose in diagram XML
  Use descriptive IDs instead of comments to document structure

FORBIDDEN #2: SELF-CLOSING EDGE ELEMENTS
  <mxCell id="e1" edge="1" source="a" target="b" />
  Self-closing edges do not render correctly — always use expanded form

FORBIDDEN #3: mxfile WRAPPER
  <mxfile host="app.diagrams.net"> ... </mxfile>
  Use <mxGraphModel> directly as the root element

FORBIDDEN #4: COMPRESSED OR BASE64 XML
  Never encode the diagram content — always output raw readable XML

FORBIDDEN #5: DOUBLE HYPHENS
  -- anywhere in the XML (even outside comments) can cause parse errors

FORBIDDEN #6: UNESCAPED SPECIAL CHARACTERS
  Use &amp; for &
  Use &lt;  for <
  Use &gt;  for >
  Use &quot; for "
```

---

## 3. VERTEX (SHAPE) RULES

### 3.1 Basic Vertex Pattern

```xml
<mxCell id="meaningful-id" value="Label Text"
  style="rounded=1;whiteSpace=wrap;"
  vertex="1" parent="1">
  <mxGeometry x="100" y="100" width="120" height="60" as="geometry"/>
</mxCell>
```

### 3.2 Non-Negotiable Vertex Rules

**R-V1: Root cells are mandatory and first**
`<mxCell id="0"/>` and `<mxCell id="1" parent="0"/>` MUST always be the first two elements in `<root>`.

**R-V2: All IDs must be unique**
No two cells may share the same `id` within a diagram. Use meaningful, descriptive IDs.

**R-V3: vertex="1" is required on all shapes**
All shape elements need `vertex="1"`. Shapes and connectors are mutually exclusive.

**R-V4: Every mxGeometry must have `as="geometry"`**
```xml
<mxGeometry x="100" y="100" width="120" height="60" as="geometry"/>
```
Without `as="geometry"`, elements render at position 0,0.

**R-V5: Generate ALL vertices BEFORE all edges**
Define every shape first, then all connectors. This prevents forward references when edges are parsed.

**R-V6: Meaningful cell IDs**
Use descriptive IDs, not sequential numbers:
- Good: `auth-service`, `user-db`, `api-gateway`, `legend-container`
- Bad: `cell2`, `cell3`, `mxCell4`

### 3.3 Minimum Sizes

| Shape Type | Minimum Width | Minimum Height |
|-----------|--------------|----------------|
| Standard shape | 120px | 60px |
| Shape with bullet points | 160px | 80px |
| Database cylinder | 160px | 90px |
| User actor | 50px | 60px |
| Swimlane section | 280px | varies |

---

## 4. EDGE (CONNECTOR) RULES

### 4.1 MANDATORY: Expanded Edge Pattern

Every edge MUST use the expanded form with a child `<mxGeometry>` element:

```xml
<mxCell id="edge-id" value="Label"
  style="edgeStyle=orthogonalEdgeStyle;"
  edge="1" source="vertex-a-id" target="vertex-b-id" parent="1">
  <mxGeometry relative="1" as="geometry" />
</mxCell>
```

**The self-closing form is NEVER valid:**
```
INVALID: <mxCell id="e1" edge="1" source="a" target="b" />
```

### 4.2 Edge Rules

**R-E1: edge="1" is required**
All connector elements need `edge="1"`. Vertices and edges are mutually exclusive.

**R-E2: source and target must reference existing vertex IDs**
Both `source` and `target` MUST reference IDs that exist as `vertex="1"` elements. An edge pointing to a non-existent ID creates an orphaned connector.

**R-E3: Every edge must have a geometry child element**
`<mxGeometry relative="1" as="geometry" />` is required even when there are no waypoints.

**R-E4: Use orthogonalEdgeStyle as the default**
```
edgeStyle=orthogonalEdgeStyle;
```
This produces professional right-angle routing.

**R-E5: Edge labels**
Set `value` directly on the edge `mxCell`. Do NOT wrap edge labels in HTML to reduce font size — the default 11px is already appropriate.

### 4.3 Edge Spacing Rules (Critical for Correct Rendering)

- **Space nodes generously** — at least 60px apart, prefer **200px horizontal / 120px vertical** gaps
- **Leave room for arrowheads** — the final segment before the target must be at least **20px long**
- If nodes are close together or nearly aligned on one axis, the auto-router may leave no room for the arrowhead — fix by increasing node spacing or adding explicit waypoints

### 4.4 Connection Side Control

Use `exitX`/`exitY`/`entryX`/`entryY` (values 0.0 to 1.0) to control which side edges connect on:
- Left side: `exitX="0" exitY="0.5"`
- Right side: `exitX="1" exitY="0.5"`
- Top: `exitX="0.5" exitY="0"`
- Bottom: `exitX="0.5" exitY="1"`

Spread connections across different sides to prevent overlap.

### 4.5 Waypoints for Complex Routing

Add explicit waypoints when edges would otherwise overlap:

```xml
<mxCell id="e1" style="edgeStyle=orthogonalEdgeStyle;" edge="1" parent="1" source="a" target="b">
  <mxGeometry relative="1" as="geometry">
    <Array as="points">
      <mxPoint x="300" y="150"/>
      <mxPoint x="300" y="250"/>
    </Array>
  </mxGeometry>
</mxCell>
```

---

## 5. COMMON SHAPE STYLES

### 5.1 Style Properties Reference

| Property | Values | Use for |
|----------|--------|---------|
| `rounded=1` | 0 or 1 | Rounded corners |
| `whiteSpace=wrap` | wrap | Text wrapping |
| `fillColor=#dae8fc` | hex color | Background color |
| `strokeColor=#6c8ebf` | hex color | Border color |
| `fontColor=#333333` | hex color | Text color |
| `shape=cylinder3` | shape name | Database cylinders |
| `shape=mxgraph.flowchart.document` | shape name | Document shapes |
| `shape=mxgraph.basic.person` | shape name | User/actor |
| `ellipse` | keyword | Circles/ovals |
| `rhombus` | keyword | Diamonds/decisions |
| `edgeStyle=orthogonalEdgeStyle` | keyword | Right-angle connectors |
| `edgeStyle=elbowEdgeStyle` | keyword | Elbow connectors |
| `dashed=1` | 0 or 1 | Dashed lines (async/planned) |
| `swimlane` | keyword | Swimlane containers |
| `group` | keyword | Invisible container |
| `container=1` | 0 or 1 | Enable container behavior |
| `pointerEvents=0` | 0 or 1 | Container does not capture child connections |
| `startSize=30` | px | Swimlane header height |
| `html=1` | 0 or 1 | Enable HTML in label value |

### 5.2 Shape Examples

**Application / Service (default):**
```xml
<mxCell id="auth-service" value="Auth Service"
  style="rounded=1;whiteSpace=wrap;fillColor=#dae8fc;strokeColor=#6c8ebf;"
  vertex="1" parent="1">
  <mxGeometry x="200" y="200" width="120" height="60" as="geometry"/>
</mxCell>
```

**Database:**
```xml
<mxCell id="user-db" value="User DB"
  style="shape=cylinder3;whiteSpace=wrap;fillColor=#ffe6cc;strokeColor=#d6b656;"
  vertex="1" parent="1">
  <mxGeometry x="400" y="200" width="120" height="90" as="geometry"/>
</mxCell>
```

**Decision diamond:**
```xml
<mxCell id="decision-1" value="Valid?"
  style="rhombus;whiteSpace=wrap;fillColor=#fff2cc;strokeColor=#d6b656;"
  vertex="1" parent="1">
  <mxGeometry x="200" y="320" width="120" height="80" as="geometry"/>
</mxCell>
```

**Swimlane section:**
```xml
<mxCell id="section-inputs" value="Inputs"
  style="swimlane;startSize=30;whiteSpace=wrap;html=1;fontSize=14;fontStyle=1;
  fillColor=#f5f5f5;strokeColor=#666666;"
  vertex="1" parent="1">
  <mxGeometry x="0" y="0" width="280" height="600" as="geometry"/>
</mxCell>
```

**Child inside swimlane (relative coordinates):**
```xml
<mxCell id="child-service" value="Child Service"
  style="rounded=1;whiteSpace=wrap;"
  vertex="1" parent="section-inputs">
  <mxGeometry x="20" y="50" width="120" height="60" as="geometry"/>
</mxCell>
```

**Invisible group container:**
```xml
<mxCell id="grp-1" value="" style="group;" vertex="1" parent="1">
  <mxGeometry x="100" y="100" width="300" height="200" as="geometry"/>
</mxCell>
```

**Custom container shape:**
```xml
<mxCell id="domain-box" value="Core Platform"
  style="rounded=1;whiteSpace=wrap;container=1;pointerEvents=0;
  fillColor=#e8f5e9;strokeColor=#66bb6a;verticalAlign=top;fontStyle=1;"
  vertex="1" parent="1">
  <mxGeometry x="100" y="100" width="400" height="300" as="geometry"/>
</mxCell>
```

---

## 6. CONTAINERS AND GROUPS

### 6.1 Container Types

| Type | Style | When to use |
|------|-------|-------------|
| **Swimlane** (titled) | `swimlane;startSize=30;` | Visible title bar needed, or container has connections |
| **Group** (invisible) | `group;` | No visual border, no direct connections on container |
| **Custom container** | Add `container=1;pointerEvents=0;` to any shape | Any shape acting as container without its own connections |

### 6.2 Container Rules

- Always add `pointerEvents=0;` to containers not intended to capture connections
- Only omit `pointerEvents=0` when the container itself is connectable (use swimlane for this — it handles it correctly)
- Children must use `parent="containerId"` and coordinates relative to the container
- Do NOT place shapes visually on top of other shapes to simulate containment — use proper `parent` attribute

### 6.3 Architecture Container with Swimlane Example

```xml
<mxCell id="svc-boundary" value="User Service" style="swimlane;startSize=30;
  fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1">
  <mxGeometry x="100" y="100" width="300" height="200" as="geometry"/>
</mxCell>
<mxCell id="rest-api" value="REST API" style="rounded=1;whiteSpace=wrap;"
  vertex="1" parent="svc-boundary">
  <mxGeometry x="20" y="50" width="120" height="60" as="geometry"/>
</mxCell>
<mxCell id="svc-db" value="Database" style="shape=cylinder3;whiteSpace=wrap;"
  vertex="1" parent="svc-boundary">
  <mxGeometry x="160" y="50" width="120" height="60" as="geometry"/>
</mxCell>
```

---

## 7. LAYERS (FOR COMPLEX DIAGRAMS)

Layers control visibility and z-order. Add layers as `mxCell` elements with `parent="0"`:

```xml
<mxGraphModel adaptiveColors="auto">
  <root>
    <mxCell id="0"/>
    <mxCell id="1" parent="0"/>
    <mxCell id="layer-infra" value="Infrastructure" parent="0"/>
    <mxCell id="layer-annot" value="Annotations" parent="0" visible="0"/>
    <mxCell id="server-1" value="App Server" style="rounded=1;whiteSpace=wrap;"
      vertex="1" parent="layer-infra">
      <mxGeometry x="100" y="100" width="120" height="60" as="geometry"/>
    </mxCell>
    <mxCell id="note-1" value="Deprecated" style="text;"
      vertex="1" parent="layer-annot">
      <mxGeometry x="100" y="170" width="120" height="30" as="geometry"/>
    </mxCell>
  </root>
</mxGraphModel>
```

**Layer rules:**
- A layer is `mxCell` with `parent="0"` and no `vertex` or `edge` attribute
- Later layers render on top (higher z-order)
- `visible="0"` hides a layer by default
- Use layers for diagrams with distinct groupings viewers may want to toggle independently

---

## 8. TAGS (CROSS-CUTTING VISIBILITY FILTERS)

Tags allow filtering by category across the entire diagram. A single element can have multiple tags.
Tags require wrapping `mxCell` in an `<object>` element:

```xml
<object id="auth-svc" label="Auth Service" tags="critical v2">
  <mxCell style="rounded=1;whiteSpace=wrap;html=1;" vertex="1" parent="1">
    <mxGeometry x="100" y="100" width="120" height="60" as="geometry"/>
  </mxCell>
</object>
```

**Tag rules:**
- `<object>` wrapper is required — plain `mxCell` cannot have tags
- `label` attribute on `<object>` replaces `value` on `mxCell`
- Tags are space-separated in the `tags` attribute
- Users filter via Edit > Tags in draw.io
- Tags do not affect z-order or structural grouping

---

## 9. METADATA AND PLACEHOLDERS

Attach key-value properties to shapes and display them in labels:

```xml
<object id="auth-svc" label="&lt;b&gt;%component%&lt;/b&gt;&lt;br&gt;Owner: %owner%&lt;br&gt;Status: %status%"
  placeholders="1" component="Auth Service" owner="Team Backend" status="Active">
  <mxCell style="rounded=1;whiteSpace=wrap;html=1;" vertex="1" parent="1">
    <mxGeometry x="100" y="100" width="160" height="80" as="geometry"/>
  </mxCell>
</object>
```

**Placeholder rules:**
- Set `placeholders="1"` to enable `%key%` substitution
- Label must use `html=1` style when using HTML formatting
- Predefined: `%id%`, `%width%`, `%height%`, `%date%`, `%time%`, `%page%`, `%pagenumber%`, `%pagecount%`
- Use `%%` for a literal percent sign

---

## 10. DARK MODE

`adaptiveColors="auto"` on `<mxGraphModel>` enables automatic dark mode color adaptation.

**How it works:**
- `strokeColor`, `fillColor`, `fontColor` default to `"default"` — renders black in light, white in dark
- Explicit colors (e.g., `fillColor=#DAE8FC`) auto-invert for dark mode via RGB inversion + 180 degree hue rotation
- Use `light-dark()` only when automatic inverse is unsatisfactory:

```
fillColor=light-dark(#DAE8FC,#1A3A5C)
fontColor=light-dark(#333333,#CCCCCC)
```

For most diagrams, just set `adaptiveColors="auto"` and use explicit light-mode colors — dark mode is handled automatically.

---

## 11. XML WELL-FORMEDNESS CHECKLIST

Before writing the file, verify:

- [ ] Root element is `<mxGraphModel adaptiveColors="auto">` — not `<mxfile>` or anything else
- [ ] `<mxCell id="0"/>` is the very first element in `<root>`
- [ ] `<mxCell id="1" parent="0"/>` is the second element in `<root>`
- [ ] ZERO XML comments anywhere in the output (`<!-- -->` is completely absent)
- [ ] All vertex IDs are unique across the entire diagram
- [ ] All edge `source` attributes reference an existing vertex `id`
- [ ] All edge `target` attributes reference an existing vertex `id`
- [ ] Every edge uses expanded form with `<mxGeometry relative="1" as="geometry" />` child
- [ ] Every `<mxGeometry>` has `as="geometry"` attribute
- [ ] No `--` double hyphens anywhere in the XML
- [ ] All `&` are escaped as `&amp;` in attribute values
- [ ] All `<` are escaped as `&lt;` in attribute values
- [ ] All `>` are escaped as `&gt;` in attribute values
- [ ] All `"` within attribute values are escaped as `&quot;`
- [ ] All vertices defined BEFORE all edges in document order
- [ ] No compressed or Base64-encoded content

---

## 12. FAILURE MODE REFERENCE TABLE

| Symptom | Root Cause | Rule Violated |
|---------|-----------|---------------|
| File appears empty when opened | `<mxfile>` wrapper with implicit compression, OR XML comments causing parse error | Use `<mxGraphModel>` root; zero XML comments |
| File won't open at all | Missing root cells, XML comments, double hyphens, malformed XML | R-V1; zero comments; no `--` |
| Floating connectors / arrows point nowhere | Edge references non-existent vertex ID | R-E2: validate source/target IDs |
| Arrows look broken or arrowhead overlaps | Self-closing edge (no geometry child), OR arrowhead segment too short | R-E3: expanded edges; R-E3 spacing: 20px min segment |
| Elements at position 0,0 | `as="geometry"` missing on mxGeometry | R-V4 |
| Diagram content clipped at edge | `page="1"` used (fixed page size) | Use `page="0"` for infinite canvas |
| Overlapping labels, cramped diagram | Insufficient node spacing | Use 200px horizontal / 120px vertical gaps |
| Children escape their container | Children using absolute canvas coordinates instead of container-relative | Set `parent="containerId"`, use relative coords |
| Duplicate element IDs | Auto-generated sequential numbers colliding | R-V2: always unique meaningful IDs |
| Colors lost on open | Style string not semicolon-terminated | R-V3: `property=value;` format always |
| Text not showing | Missing `html=1` when value contains HTML entities | Add `html=1` to style when value has HTML |

---

## 13. SOURCES

| Source | URL | What it covers |
|--------|-----|----------------|
| draw.io skill-cli SKILL.md (official) | github.com/jgraph/drawio-mcp/blob/main/skill-cli/drawio/SKILL.md | Root XML format, file generation protocol, forbidden patterns |
| draw.io shared/xml-reference.md (official) | github.com/jgraph/drawio-mcp/blob/main/shared/xml-reference.md | Edge routing, containers, layers, tags, metadata, dark mode, well-formedness |
| draw.io shared/style-reference.md (official) | github.com/jgraph/drawio-mcp/blob/main/shared/style-reference.md | All shape types, style properties, color palettes, HTML labels |
| draw.io XML Schema (XSD) | github.com/jgraph/drawio-mcp/blob/main/shared/mxfile.xsd | Formal XML schema for validation |
| draw.io Style Reference (doc) | drawio.com/doc/faq/drawio-style-reference.html | Complete style reference |
