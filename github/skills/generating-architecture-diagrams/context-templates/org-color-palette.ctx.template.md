# Organization Color Palette
## Context File for generating-architecture-diagrams skill

**Context ID:** CTX-PALETTE  
**Type:** DECLARED  
**Created:** [DATE]  
**Last Validated:** [DATE]  
**Freshness:** [CURRENT / STALE / UNKNOWN]  

---

## 1. Domain Container Colors

Colors applied to system grouping containers (swimlanes, grouped boxes):

| Domain | Fill | Border | Usage |
|--------|------|--------|-------|
| [Primary Domain Name] | [#HEX] | [#HEX] | [Description] |
| [Secondary Domain Name] | [#HEX] | [#HEX] | [Description] |
| [Third Domain Name] | [#HEX] | [#HEX] | [Description] |
| External Systems | [#HEX] | [#HEX] | Vendor or outside organization (dashed border) |
| Gateway / Firewall Zone | [#HEX] | [#HEX] | External access boundary |
| User Actors | [#HEX] | [#HEX] | Human interaction layer |

---

## 2. Integration Arrow Colors

Colors applied to connectors/edges between systems:

| Integration Type | Hex | Line Style | Width |
|-----------------|-----|-----------|-------|
| REST / HTTP API | [#HEX] | Solid | 2px |
| Kafka / Messaging | [#HEX] | Dashed | 2px |
| Direct Database | [#HEX] | Solid thick | 3px |
| File Transfer | [#HEX] | Dotted | 2px |
| Batch / Scheduler | [#HEX] | Solid thin | 1.5px |
| [Custom Type] | [#HEX] | [Style] | [Width] |

---

## 3. Component Colors

Colors applied to boxes inside the Processing/central section:

| Component Type | Fill | Border |
|---------------|------|--------|
| UI / Frontend | [#HEX] | [#HEX] |
| Backend Service | [#HEX] | [#HEX] |
| Batch Processor | [#HEX] | [#HEX] |
| Integration Adapter | [#HEX] | [#HEX] |
| [Custom Type] | [#HEX] | [#HEX] |

---

## 4. Database Colors

| DB Type | Fill | Border |
|---------|------|--------|
| Primary Application DB | [#HEX] | [#HEX] |
| Cache DB | [#HEX] | [#HEX] |
| Reporting DB | [#HEX] | [#HEX] |

---

## 5. User Type Colors

| User Type | Fill | Border |
|-----------|------|--------|
| Business Users | [#HEX] | [#HEX] |
| Admin Users | [#HEX] | [#HEX] |
| System Users | [#HEX] | [#HEX] |

---

## CONTEXT METADATA

- **Source:** [How this palette was established — brand guidelines, team decision, etc.]
- **Last Validated By:** [Person/role who confirmed these colors]
- **Refresh Trigger:** Change in organization brand guidelines or domain restructuring
