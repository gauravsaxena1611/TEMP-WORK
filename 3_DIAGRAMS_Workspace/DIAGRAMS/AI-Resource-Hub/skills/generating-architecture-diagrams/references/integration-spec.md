# Integration / Interface Map Specification
## Reference for generating-architecture-diagrams skill

Shows all system-to-system interfaces and integration points in a landscape
view. Combines structural positioning with integration protocol detail.

---

## 1. Purpose

Provides a comprehensive map of how all systems in a landscape connect to
each other, what protocols they use, and what data they exchange. Answers:
"What systems talk to what, through which interfaces, using which protocols?"

### Audience
Integration architects, middleware teams, enterprise architects, governance.

### When to Use Over Other Types
- When documenting the full integration landscape across multiple systems
- When planning middleware, ESB, or API gateway configurations
- When conducting integration impact analysis ("if System X changes, who is affected?")
- When auditing interface contracts for compliance or modernization

---

## 2. Layout Styles

### Hub-and-Spoke Layout
Central system (ESB, API Gateway, or primary application) in the center,
connected systems arranged radially around it.

```
         [System A]
              \
  [System B] — [Central Hub] — [System C]
              /          \
         [System D]    [System E]
```

### Landscape / Grid Layout
Systems arranged in a grid or landscape view grouped by domain,
with integration lines crossing between groups.

```
┌─ Domain A ──────┐    ┌─ Domain B ──────┐
│ [System 1]      │    │ [System 4]      │
│ [System 2]      │───→│ [System 5]      │
│ [System 3]      │    │ [System 6]      │
└─────────────────┘    └─────────────────┘
         ↕                      ↕
┌─ Domain C ──────┐    ┌─ External ──────┐
│ [System 7]      │    │ [System 9]      │
│ [System 8]      │    │ [System 10]     │
└─────────────────┘    └─────────────────┘
```

**Default to Landscape/Grid** for enterprise views.
**Use Hub-and-Spoke** when a single system is the focal point.

---

## 3. Element Styles

### System Box
- Shape: Rounded rectangle
- Fill: Domain-specific color from `CTX-PALETTE` or `references/color-palette.md`
- Border: Domain-specific border color
- Label: System name (bold, 13px), ID/CAI below (10px), brief description (10px gray)

### Domain Group Container
- Shape: Rounded rectangle container
- Fill: Light domain color (10% opacity), Border: Domain color
- Label: Domain name at top (bold, 14px)

### Middleware / Integration Layer
- Shape: Horizontal bar or elongated rectangle
- Fill: #E8EAF6, Border: #5C6BC0
- Label: "ESB", "API Gateway", "MQ Broker", middleware name

### Interface Point (Optional Detail Level)
- Shape: Small circle or diamond on the edge of a system box
- Fill: varies by protocol (same as arrow color)
- Label: Interface ID or endpoint name (9px)

---

## 4. Integration Arrow Styles

Use the same integration arrow colors as the HLLFD color palette:

| Integration Type | Hex | Line Style | Width |
|-----------------|-----|-----------|-------|
| REST / HTTP API | #2874A6 | Solid | 2px |
| Kafka / Messaging | #8E44AD | Dashed | 2px |
| Direct Database | #7B7D7D | Solid thick | 3px |
| File Transfer / SFTP | #E67E22 | Dotted | 2px |
| Batch / Scheduler | #229954 | Solid thin | 1.5px |
| gRPC | #00695C | Solid | 2px |
| SOAP / WSDL | #4E342E | Solid | 2px |
| GraphQL | #E91E63 | Solid | 2px |
| Event Notification | #C2185B | Dashed thin | 1.5px |
| ETL / Data Pipeline | #145A32 | Solid | 2.5px |

### Arrow Label Format
```
[Protocol]: [Data Description] ([Direction])
```

**Examples:**
- `REST: Customer Data (→)`
- `Kafka: OrderCreated Event (↔)`
- `SFTP: Daily Batch File (←)`
- `JDBC: Transaction Records (→)`

---

## 5. Integration Detail Table (Companion)

For each integration line, an optional detail note or companion table can be
generated alongside the diagram:

| # | Source | Target | Protocol | Data | Direction | Frequency | SLA |
|---|--------|--------|----------|------|-----------|-----------|-----|
| 1 | System A | System B | REST | Customer | → | Real-time | 200ms |
| 2 | System A | System C | Kafka | Orders | → | Event-driven | 5s |
| 3 | System D | System A | SFTP | Batch File | ← | Daily 2AM | 1hr |

Include this table in the Workflow Notes section of the diagram when
the integration map has >10 connections.

---

## 6. Sizing Guidelines

| Element | Recommended Size |
|---------|-----------------|
| Canvas | 3500x2500 minimum (scales with system count) |
| System box | 160x80px |
| Domain container | Varies — contains child systems |
| Middleware bar | 400-600px wide, 50px tall |
| Spacing between systems | Minimum 60px |
| Spacing between domains | Minimum 120px |
| Font: system name | 13px bold |
| Font: domain name | 14px bold |
| Font: arrow label | 10px |

---

## 7. Integration Map Quality Checklist

```
☐ All systems from sources shown with names and IDs
☐ Systems grouped by domain in labeled containers
☐ Every integration line labeled with protocol and data
☐ Arrow colors match integration type per color palette
☐ Direction of data flow clear on every arrow
☐ Middleware/gateway shown when present in architecture
☐ Integration detail table present for complex maps (>10 connections)
☐ Legend present with integration type color key
☐ No assumed integrations — all from sources
☐ TBD markers on interfaces with unknown protocols or data
☐ Domain colors consistent with organization palette
```

---

## 8. Sources

| Source | Relevance |
|--------|-----------|
| TOGAF 10 (The Open Group) | Enterprise integration patterns, application communication diagrams |
| Enterprise Integration Patterns (Hohpe & Woolf, 2003) | Messaging patterns, integration styles |
| ISO/IEC/IEEE 42010:2022 | Architecture description viewpoints |
