# draw.io XML Generation Rules
## Reference for generating-architecture-diagrams skill

Rules derived from draw.io official documentation, drawio-ninja research project,
and GenAI-DrawIO-Creator academic framework (arXiv 2601.05162, Jan 2026).

---

## 1. Mandatory XML Structure

Every generated diagram MUST follow this hierarchy:

```xml
<mxfile host="app.diagrams.net">
  <diagram id="[meaningful-id]" name="[Diagram Title]">
    <mxGraphModel dx="4000" dy="2500" grid="1" gridSize="10" guides="1"
      tooltips="1" connect="1" arrows="1" fold="1" page="0" pageScale="1"
      pageWidth="1600" pageHeight="1200" background="#ffffff" math="0" shadow="0">
      <root>
        <mxCell id="0"/>
        <mxCell id="1" parent="0"/>
        <!-- ALL diagram elements here -->
      </root>
    </mxGraphModel>
  </diagram>
</mxfile>
```

---

## 2. Non-Negotiable Rules (12 Rules)

Violating ANY of these produces broken or corrupted files:

### Rule 1: Root Cells Are Mandatory
`<mxCell id="0"/>` (root container) and `<mxCell id="1" parent="0"/>` (default layer)
MUST always be the first two elements inside `<root>`.

### Rule 2: All IDs Must Be Unique
No two cells may share the same `id` attribute within a diagram. Use meaningful IDs
for important elements (e.g., `frontend-component`, `integration-db`, `inputs-section`).

### Rule 3: Vertices Need vertex="1", Edges Need edge="1"
These attributes are mutually exclusive. A cell is either a shape (vertex) or a
connector (edge), never both.

### Rule 4: Every Edge Must Have Valid source and target
Edge `source` and `target` attributes MUST reference existing vertex IDs. An edge
pointing to a non-existent ID creates an orphaned connector that breaks the diagram.

### Rule 5: Generate Vertices BEFORE Edges
Define ALL shapes first in the XML, then ALL connections. This prevents forward
references to vertices that don't exist yet when edges are parsed.

### Rule 6: Style Strings Use key=value; Format
Semicolon-separated, with a trailing semicolon. Example:
```
rounded=1;whiteSpace=wrap;html=1;fillColor=#DAE8FC;strokeColor=#6C8EBF;
```

### Rule 7: Use page="0" for Infinite Canvas
LLMs default to `page="1"` which creates fixed page boundaries that clip large
enterprise diagrams. Always set `page="0"` to create an infinite canvas that
scales to content.

### Rule 8: Generate Plain/Uncompressed XML
Never Base64-encode or compress the diagram content. AI should generate plain XML
that is human-readable and editable.

### Rule 9: All mxGeometry Elements Need as="geometry"
Every `<mxGeometry>` tag must include `as="geometry"` attribute:
```xml
<mxGeometry x="100" y="100" width="120" height="60" as="geometry"/>
```

### Rule 10: Use orthogonalEdgeStyle for Connectors
Produces professional right-angle routing for all connections:
```
edgeStyle=orthogonalEdgeStyle;rounded=0;
```

### Rule 11: HTML Content Must Be Properly Escaped
When using HTML in labels, set `html=1` in the style and encode HTML entities
within value attributes. Use `&lt;` for `<`, `&gt;` for `>`, `&amp;` for `&`,
`&quot;` for `"`.

### Rule 12: Use Meaningful Cell IDs
For important elements, use descriptive IDs instead of auto-generated numbers:
- `frontend-component`, `backend-component`
- `integration-db`, `app-database`
- `inputs-section`, `processing-section`, `consumes-section`
- `legend-container`, `notes-section`

This supports future automation and programmatic diagram updates.

---

## 3. Geometry and Layout Standards

| Property | Value | Rationale |
|----------|-------|-----------|
| Canvas size | `dx="4000" dy="2500"` minimum | Prevents clipping on large HLLFD diagrams |
| Swimlane widths | Inputs ~300px, Processing ~650px, Consumes ~300px | Balanced tri-sectional layout |
| Minimum shape size | 120x60 (standard), 160x80 (with bullet points) | Readability |
| Spacing between shapes | Minimum 40px | Prevent overlap |
| Spacing between sections | Minimum 80px | Visual separation |
| Database shapes | `shape=cylinder3` | Industry standard for persistence |
| User shapes | `shape=mxgraph.basic.person` or rounded rectangle | Visual distinction |
| Flow direction | Left-to-right (data flow), top-to-bottom (hierarchy) | Consistent reading direction |

---

## 4. Container and Grouping Rules

- Use `swimlane` style for main sections (Inputs, Processing, Consumes)
- Use `rounded=1` containers for internal groupings (e.g., "Core Components")
- Set `container=1` on parent shapes that contain children
- Child elements must reference parent via `parent="[parent-id]"`
- Use `verticalAlign=top` on container labels so title stays at top
- `startSize=40` on swimlanes sets header height

---

## 5. Common LLM Failure Modes

These are the most frequent XML generation failures observed in LLM outputs.
Every rule above specifically prevents one or more of these:

| # | Failure Mode | Prevention Rule | Symptom |
|---|---|---|---|
| F1 | Missing root cells | Rule 1 | File won't open at all |
| F2 | Edges referencing non-existent vertices | Rules 4, 5 | Floating connectors, crash on load |
| F3 | Duplicate IDs | Rule 2 | Elements overlap or disappear |
| F4 | Fixed page clipping content | Rule 7 | Diagram cuts off at page boundaries |
| F5 | Compressed/Base64 output | Rule 8 | Unreadable, can't be edited |
| F6 | Invalid style strings | Rule 6 | Missing colors, wrong shapes |
| F7 | Missing geometry `as` attribute | Rule 9 | Elements positioned at 0,0 |
| F8 | Orphaned edge connections | Rule 4 | Connectors point to nothing |
| F9 | Overlapping labels | Section 3 spacing | Unreadable diagram |
| F10 | Token-induced XML corruption | Rule 8 (plain XML) | Malformed tags, truncated elements |

---

## 6. Sources

| Source | URL | Relevance |
|--------|-----|-----------|
| draw.io Official AI Generation Guide | drawio.com/doc/faq/ai-drawio-generation | Rules 1, 2, 3, 6, 8, 9 |
| drawio-ninja (GitHub) | github.com/simonpo/drawio-ninja | Rules 5, 7, 12; Failure modes table |
| GenAI-DrawIO-Creator (arXiv) | arxiv.org/html/2601.05162v1 | Vertex-before-edges ordering; validation pipeline |
| draw.io XML Schema (mxGraph) | groups.google.com/g/drawio | Format stability since 2005 |
