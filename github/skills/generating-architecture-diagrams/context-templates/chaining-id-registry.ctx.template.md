# Chaining ID Registry — Context Template
## CTX-CHAINIDS | SKILL-ARCHDIAG v5.0

**Source:** [020 Diagramming Frameworks, Section 8] — Chaining Model ✅
**Purpose:** Store stable entity IDs shared across linked diagrams (C4 L2 Container → Sequence → Deployment).
**Usage:** Read this file before generating any Sequence or Deployment diagram. Write to this file when a C4 L2 Container diagram is generated.

---

## Context Metadata

```yaml
context_id: CTX-CHAINIDS
system_name: [SYSTEM_NAME_HERE]
initialized_date: [YYYY-MM-DD]
last_updated: [YYYY-MM-DD]
initialized_from: [C4 L2 Container diagram filename]
status: active
```

---

## Container Registry

Each row = one container from the C4 L2 Container diagram.

| Entity ID | Canonical Name | Short Code | Technology | Domain Owner | Type | Notes |
|---|---|---|---|---|---|---|
| `[system]-web-app` | [Web Application Name] | [SHORT] | [Framework, Language] | [Team/Domain] | WebApp | |
| `[system]-api` | [API Name] | [SHORT] | [Framework, Language] | [Team/Domain] | API | |
| `[system]-db` | [Database Name] | [SHORT] | [Technology] | [Team/Domain] | Database | |
| `[system]-queue` | [Queue/Broker Name] | [SHORT] | [Technology] | [Team/Domain] | MessageBroker | |

---

## External Systems (from C4 L1 / L2)

| Entity ID | Canonical Name | Short Code | Owner | Type | Notes |
|---|---|---|---|---|---|
| `ext-[system]` | [External System Name] | [SHORT] | External | ExternalSystem | |

---

## Actor Registry

| Entity ID | Canonical Name | Short Code | Type | Notes |
|---|---|---|---|---|
| `actor-[role]` | [Role Name] | [SHORT] | Person/System | |

---

## Chain Usage Map

Records which diagrams use which entity IDs:

| Diagram | File | Entity IDs Used |
|---|---|---|
| C4 L2 Container | [filename].drawio | (all) |
| Sequence — [Use Case] | [filename].drawio | [list of IDs] |
| Deployment — [Env] | [filename].drawio | [list of IDs] |

---

## Alias Map

When source material uses different names for the same entity:

| Canonical ID | Canonical Name | Alias(es) Found in Source |
|---|---|---|
| `[id]` | [canonical name] | [alias 1], [alias 2] |

---

## Instructions for SKILL-ARCHDIAG v5.0

1. **Initialize this file** when the first C4 L2 Container diagram is generated for a system
2. **Read this file** before generating any Sequence or Deployment diagram
3. **Use Entity IDs** from this file as lifeline names (Sequence) and artifact names (Deployment)
4. **Never rename** an entity between diagram types — update this file if names change, then regenerate
5. **Log aliases** here when source documents use different names for the same component
