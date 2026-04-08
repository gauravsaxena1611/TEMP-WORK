# Template T05: ICRM-Data-Flow
## Data Flow Diagram

**Template ID:** T05
**Diagram Type:** Data Flow Diagram
**Target Audience:** Technical / Data teams
**Version:** 1.0
**Created:** 2026-04-07
**Status:** Active — update visual style values below to match your organisation standards

---

## PURPOSE

Shows how data moves through the system — sources, transformations, destinations. ETL/pipeline style with data stores shown as cylinders.

---

## VISUAL STYLE SPECIFICATION

### Color Palette

| Element | Fill Color | Border Color | Text Color | Notes |
|---------|-----------|-------------|------------|-------|
| Internal Application (core) | `#dae8fc` | `#6c8ebf` | `#000000` | Blue — primary system |
| Internal Systems (same org) | `#d5e8d4` | `#82b366` | `#000000` | Green — internal |
| External Systems | `#f5f5f5` | `#666666` | `#333333` | Grey — external/third-party |
| Data Stores / Databases | `#fff2cc` | `#d6b656` | `#000000` | Yellow — data |
| Users / Actors | `#e1d5e7` | `#9673a6` | `#000000` | Purple — human actors |
| Integration Flows (arrows) | `#333333` | N/A | `#333333` | Dark grey lines |
| Boundary / Group Box | `transparent` | `#999999` | `#666666` | Light grey dashed border |

> ⚠️ **TO CUSTOMISE:** Replace the hex color values above with your organisation's actual brand colors.
> These defaults follow the draw.io standard palette and will produce consistent, professional diagrams.

### Typography

| Element | Font | Size | Style |
|---------|------|------|-------|
| System / Box Title | Helvetica | 14px | Bold |
| Box Description | Helvetica | 11px | Regular |
| Arrow / Flow Label | Helvetica | 10px | Regular, italic |
| Group / Zone Label | Helvetica | 12px | Bold, uppercase |
| Diagram Title (header) | Helvetica | 18px | Bold |

### Box Dimensions

| Element | Width | Height | Shape |
|---------|-------|--------|-------|
| Application (primary) | 200px | 80px | Rounded rectangle |
| Internal system | 160px | 60px | Rounded rectangle |
| External system | 160px | 60px | Rectangle (sharp corners) |
| Database | 120px | 60px | Cylinder |
| User / Actor | 60px | 80px | Person icon |
| Group boundary | Variable | Variable | Dashed rectangle |

### Arrow / Line Styles

| Flow Type | Line Style | Arrow Head | Color |
|-----------|-----------|------------|-------|
| Synchronous call (REST/SOAP) | Solid | Filled arrow | `#333333` |
| Asynchronous (MQ/Event) | Dashed | Open arrow | `#333333` |
| Data flow (batch/ETL) | Dotted | Filled arrow | `#666666` |
| Planned / future integration | Dashed | Open arrow | `#999999` |

---

## REQUIRED ELEMENTS

Every diagram using this template MUST include:

- [ ] **Title block** (top-left): Application name + CSI number + Diagram type + Version + Date
- [ ] **Legend box** (bottom-right): Color meanings for all domain types used
- [ ] **Version label**:  visible on the diagram canvas
- [ ] **North label**: "Upstream Systems" (for HLLFD only)
- [ ] **South label**: "Downstream Systems" (for HLLFD only)
- [ ] **Workflow numbers**: Numbered arrows (1, 2, 3...) for all integration flows

---

## LAYOUT CONVENTIONS

### For HLLFD (T01) and Integration Maps (T08):
```
[ Upstream Systems ]     [ Upstream Systems ]
         |                       |
         ↓                       ↓
    [ PRIMARY APPLICATION — centre of canvas ]
         |                       |
         ↓                       ↓
[ Downstream Systems ]  [ Downstream Systems ]
```

### For C4 Context (T02):
```
[ External User ]  [ External User ]
       |                  |
       ↓                  ↓
  [ PRIMARY SYSTEM — centre ]
       |                  |
       ↓                  ↓
[ External System ] [ External System ]
```

### Canvas Size
- Default: 1600px × 900px (16:9 ratio — suitable for presentations)
- Minimum margins: 40px on all sides

---

## EXAMPLE PROMPT TO USE THIS TEMPLATE

```
/generate-diagram
Application: 176482-ORM Metric
Type: Data Flow Diagram
Template: T05
Source: #file:ICRM/Vertical-3/176482-ORM Metric/docs/Application-Understanding.md
```

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-07 | Initial | Template stub created | Workspace setup |

> **Next step:** Update the color palette table above with your organisation's actual colors,
> then change this entry to reflect the customisation date and who approved the colors.
