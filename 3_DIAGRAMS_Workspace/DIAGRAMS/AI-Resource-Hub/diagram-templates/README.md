# Diagram Templates Index
## Organisation-Specific Visual Style Templates

**Document ID:** AI-Resource-Hub / Diagram Templates
**Version:** 1.0
**Created:** 2026-04-07
**Purpose:** Numbered visual style templates for consistent ICRM architecture diagrams

---

## HOW TO USE

When requesting a diagram, specify the template number:
```
/generate-diagram
Application: 176482-ORM Metric
Type: HLLFD
Template: T01
```

Each template file (T01–T10) defines:
- **Visual style** — colors, box shapes, font sizes, line styles
- **Layout conventions** — how elements are positioned
- **Required elements** — what must always appear (title, legend, version box, etc.)
- **Audience** — who this diagram is for

The template overrides the default color palette but does not change diagram structure rules.

---

## TEMPLATE REGISTRY

| ID | Name | Diagram Type | Audience | Status |
|----|------|-------------|----------|--------|
| T01 | ICRM Standard HLLFD | High Level Logical Flow | Technical / Architecture | ✅ Active |
| T02 | ICRM C4 Context | C4 Level 1 — System Context | Technical / Architecture | ✅ Active |
| T03 | ICRM C4 Container | C4 Level 2 — Containers | Technical / Architecture | ✅ Active |
| T04 | ICRM Sequence | Sequence / Message Flow | Technical | ✅ Active |
| T05 | ICRM Data Flow | Data Flow / ETL Pipeline | Technical / Data | ✅ Active |
| T06 | ICRM ERD | Entity Relationship | Technical / Data | ✅ Active |
| T07 | ICRM Deployment | Infrastructure / Deployment | Technical / Infrastructure | ✅ Active |
| T08 | ICRM Integration Map | Integration / Interface Map | Technical / Architecture | ✅ Active |
| T09 | ICRM Executive | Executive Summary View | Business / Executive | ✅ Active |
| T10 | ICRM Full Detail | Full Technical Architecture | Technical / Architecture | ✅ Active |

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-07 | Initial | Template registry created with 10 template stubs | Initial workspace setup |
