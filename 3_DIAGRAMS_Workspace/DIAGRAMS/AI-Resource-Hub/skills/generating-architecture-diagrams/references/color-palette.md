# Default Domain Color Palette Standard
## Reference for generating-architecture-diagrams skill
## DEFAULT PALETTE — Overridden by CTX-PALETTE context when available

This file provides the default color palette based on ICRM domain standards.
Organizations can customize their palette via the `CTX-PALETTE` context file.
When `CTX-PALETTE` exists, use those colors instead of these defaults.

---

## 1. Domain Container Colors

Apply to **system grouping containers** (swimlanes, grouped boxes):

| Domain | Fill | Border | Usage |
|--------|------|--------|-------|
| ICRM / CRC Applications | #D6EAF8 | #2E86C1 | Primary application domain |
| Non-ICRM Citi Internal | #FCF3CF | #B7950B | Other Citi domains (Ref Data, Enterprise, Controls) |
| Reference Data Systems | #EBDEF0 | #7D3C98 | Reference data providers |
| Controls Domain | #E8F8F5 | #117864 | Controls and compliance systems |
| External Systems | #FADBD8 | #C0392B | Vendor or outside Citi (dashed border) |
| Gateway / Firewall Zone | #F5B7B1 | #922B21 | External access boundary (double border) |
| User Actors | #FDEBD0 | #AF601A | Human interaction layer |

---

## 2. Integration Arrow Colors

Apply to **connectors/edges** between systems:

| Integration Type | Hex | Line Style | Width | draw.io Style Fragment |
|-----------------|-----|-----------|-------|----------------------|
| REST / HTTP API | #2874A6 | Solid | 2px | `strokeColor=#2874A6;strokeWidth=2;` |
| Kafka / Messaging | #8E44AD | Dashed | 2px | `strokeColor=#8E44AD;strokeWidth=2;dashed=1;` |
| Direct Database | #7B7D7D | Solid thick | 3px | `strokeColor=#7B7D7D;strokeWidth=3;` |
| File Transfer / MDC | #E67E22 | Dotted | 2px | `strokeColor=#E67E22;strokeWidth=2;dashed=1;dashPattern=1 2;` |
| Batch / Scheduler | #229954 | Solid thin | 1.5px | `strokeColor=#229954;strokeWidth=1.5;` |
| User Interaction | #17A589 | Solid | 2px | `strokeColor=#17A589;strokeWidth=2;` |
| Event Notification | #C2185B | Dashed | 1.5px | `strokeColor=#C2185B;strokeWidth=1.5;dashed=1;` |
| Internal Service Call | #1A5276 | Solid | 2px | `strokeColor=#1A5276;strokeWidth=2;` |
| ETL / Data Pipeline | #145A32 | Solid | 2.5px | `strokeColor=#145A32;strokeWidth=2.5;` |

---

## 3. Central Application Component Colors

Apply to **boxes inside the Processing section**:

| Component Type | Fill | Border |
|---------------|------|--------|
| UI / Frontend | #D4E6F1 | #2E86C1 |
| Backend Service | #D5F5E3 | #239B56 |
| Microservice | #D1F2EB | #148F77 |
| Batch Processor | #E8F8F5 | #117A65 |
| Poller | #EAFAF1 | #1D8348 |
| Integration Adapter | #FEF9E7 | #B7950B |
| Workflow Engine | #EBF5FB | #2E86C1 |
| Auth Component | #F5EEF8 | #6C3483 |

---

## 4. Database Colors

Apply to **cylinder shapes** for persistence:

| DB Type | Fill | Border |
|---------|------|--------|
| Primary Application DB | #D5DBDB | #424949 |
| Cache DB (Redis, Elasticsearch) | #EBEDEF | #7B7D7D |
| Reporting DB | #F2F3F4 | #616A6B |
| Integration DB | #FFE0B2 | #FF9800 |

---

## 5. User Type Colors

Apply to **person/actor shapes**:

| User Type | Fill | Border |
|-----------|------|--------|
| Business Users | #FDEBD0 | #AF601A |
| Operations Users | #FAD7A0 | #CA6F1E |
| Admin Users | #F5CBA7 | #BA4A00 |
| System Users | #D6DBDF | #566573 |
| ICRM / PFICRM Users | #FDEBD0 | #AF601A |

---

## 6. Workflow-to-Color Alignment

When numbering arrows by workflow, use these color associations:

| Workflow Type | Arrow Color | Numbering Prefix |
|--------------|-------------|-----------------|
| Create Flow | #2874A6 (Blue) | 1, 2, 3 |
| Update Flow | #1A5276 (Dark Blue) | A, B, C |
| Batch Flow | #229954 (Green) | I, II, III |
| Manual Upload | #E67E22 (Orange) | a, b, c |
| Kafka Flow | #8E44AD (Purple) | K1, K2 |
| DB Flow | #7B7D7D (Gray) | D1, D2 |
| Error Flow | #C0392B (Red) | E1, E2 |

---

## 7. Border Style Standards

| Category | Border Style |
|----------|-------------|
| Primary Application container | Solid thick (2px) |
| External Systems container | Dashed |
| Gateway / Firewall | Double border |
| Databases | Cylinder shape (`shape=cylinder3`) |
| Users | Person shape or rounded rectangle |
| Notes / Legend | Sticky note shape or light-bordered rectangle |

---

## 8. Typography

| Element | Font Size | Font Style |
|---------|-----------|------------|
| Diagram title | 20px | Bold |
| Section headers (swimlane titles) | 16px | Bold |
| Component names | 13px | Bold |
| Bullet text inside components | 10-11px | Regular, color #666666 |
| Arrow labels | 10-11px | Regular |
| Legend text | 10px | Regular |
| Metadata/notes | 10px | Regular |

---

## 9. Accessibility Rules

- Maintain contrast ratio between fill and text colors
- Avoid similar shades for adjacent domain containers
- Use bold arrows (2px+) for main workflow paths
- Use lighter shades for grouping containers, stronger colors for individual components
- Enable shadow on central application container for visual emphasis
- Use rounded corners (6px radius) on all component boxes

---

## 10. Legend Requirements

Every diagram MUST include a legend showing:
- Domain color swatches with labels
- Arrow color swatches with integration type labels
- Workflow numbering style examples
- Database icon explanation
- User icon explanation
