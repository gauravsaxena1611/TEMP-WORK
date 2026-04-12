# [System Name]: Container & Component Inventory
## [Subtitle — e.g., "C4 Level 2 & Level 3 Reference — v1.0"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-007: CONTAINER & COMPONENT INVENTORY  (v2.0)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: Structured inventory of all C4 Level 2 containers
and Level 3 components within a single system. The primary
feed for C4 L2 Container diagrams and C4 L3 Component diagrams
in the generating-architecture-diagrams skill.

AUTHORING ORDER:
  1. TMPL-006 (System Context Pack) must exist first.
  2. This document expands the tech_stack and external_integrations
     from TMPL-006 into explicit containers and components.
  3. TMPL-008 (Data & Flow Inventory) references data stores
     listed in this document.

THIS IS NOT FOR:
  - System-level context (actors, external systems) → TMPL-006
  - Data entities and flow diagrams → TMPL-008
  - Sequence or interaction flows → TMPL-009
  - Deployment environments → TMPL-010
  - Portfolio overview → TMPL-011

DIAGRAM SKILL FEED:
  The generating-architecture-diagrams skill reads:
  - containers[] → C4 L2 nodes and edges
  - components[]  → C4 L3 nodes inside their parent container
  - container_dependencies[] → diagram arrows at L2
  - component_dependencies[] → diagram arrows at L3

VERIFICATION REQUIREMENT (Verification Mode = Medium-High):
  ✅ verified  ⚠️ flagged  🚩 outlier  ❌ removed
  ❓ unresolved  💡 inference  🗑️ bogus caught
  Per [013 Truth, Verification & Doc Governance, Section 3.5]

METHODOLOGY PHASE BINDING (from [030]):
  Typically Phase-5-DetailedDesign or Phase-4-HighLevel.
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ────────────────────────────────
document_id: "[XXX Short-Title]-Containers"
title: "[System Name] — Container & Component Inventory"
version: "1.0"
created: "YYYY-MM-DD"
status: "Draft | Review | Final"
parent_document: "[TMPL-006 System Context Pack for this system]"
template_version_used: "TMPL-007 v2.0"

# ── METHODOLOGY PHASE ─────────────────────────────────────────
methodology_phase: "Phase-5-DetailedDesign"
# Options: Phase-4-HighLevel | Phase-5-DetailedDesign
design_methodology_ref: "[030 System Design Methodology, Section 4]"

# ── SYSTEM BACK-REFERENCE ─────────────────────────────────────
system_id: ""           # Must match system.system_id in TMPL-006
system_name: ""
context_pack_ref: ""    # Document ID of the TMPL-006 for this system

# ── C4 LEVEL 2: CONTAINERS ────────────────────────────────────
# A "container" in C4 = any separately deployable / runnable unit:
# web app, API, mobile app, database, message queue, cache, etc.
# consumed by: C4 L2 Container diagram
containers:
  - id: ""              # "CNT-001" — stable, used in all diagram refs
    name: ""            # "API Gateway"
    type: ""            # web-app | api | mobile-app | desktop-app
                        # | database | cache | message-queue | file-store
                        # | batch-job | function | service-bus | cdn
    technology: ""      # "Node.js 20 + Express 4" | "PostgreSQL 15"
    description: ""     # One sentence: what this container does
    responsibilities: []  # ["Authenticates inbound requests",
                          #  "Routes to downstream services"]
    port: ""            # "443" | "5432" | "6379" — primary listen port
    protocol: ""        # HTTPS | gRPC | AMQP | TCP | WebSocket
    owner_team: ""      # "Platform Team" | "Auth Squad"
    scaling_policy: ""  # "horizontal auto-scale 2–20 pods" | "single-instance"
    trust_boundary_id: ""  # References trust_boundaries[].id in TMPL-006
    health_check: ""    # "/health" | "TCP:5432" | "none"
    data_stores: []     # List of database/cache container IDs this container owns
    # Verification label for this container's claimed tech:
    verification: ""    # ✅ | ⚠️ | 💡

# ── C4 LEVEL 2: CONTAINER DEPENDENCIES ───────────────────────
# Edges in the C4 L2 diagram
container_dependencies:
  - from_id: ""         # Source container ID
    to_id: ""           # Target container ID (or external integration ID from TMPL-006)
    label: ""           # "Authenticates user sessions" — diagram edge label
    technology: ""      # "REST/HTTPS" | "gRPC" | "AMQP topic"
    direction: ""       # sync | async | bidirectional
    sla_contract: ""    # "p99 < 50ms" | "at-least-once delivery" | "none"

# ── C4 LEVEL 3: COMPONENTS ────────────────────────────────────
# Components = major structural building blocks INSIDE a container.
# Only document components when internal structure is significant
# (e.g., a monolith or a container with complex internal routing).
# consumed by: C4 L3 Component diagram
components:
  - id: ""              # "CMP-001"
    parent_container_id: ""   # Must match containers[].id above
    name: ""            # "OrderController"
    type: ""            # controller | service | repository | facade
                        # | adapter | gateway | handler | scheduler
                        # | validator | mapper | factory | saga
    technology: ""      # "Spring Boot @RestController" | "Python class"
    description: ""     # One sentence: responsibility
    interfaces: []      # ["POST /orders", "GET /orders/{id}"]
    dependencies: []    # List of other component IDs this calls
    verification: ""    # ✅ | ⚠️ | 💡

# ── COMPONENT DEPENDENCIES (C4 L3 edges) ──────────────────────
component_dependencies:
  - from_id: ""
    to_id: ""
    label: ""
    technology: ""

# ── SHARED / CROSS-CUTTING CONCERNS ──────────────────────────
cross_cutting:
  auth_mechanism: ""    # "JWT validated at API Gateway" | "mTLS between services"
  logging: ""           # "Structured JSON → Datadog" | "stdout → CloudWatch"
  tracing: ""           # "OpenTelemetry → Jaeger" | "AWS X-Ray" | "none"
  config_management: "" # "AWS Parameter Store" | "Vault" | "env vars" | "none"
  feature_flags: ""     # "LaunchDarkly" | "none"
  api_versioning: ""    # "URI versioning /v1/" | "header versioning" | "none"

# ── LIVING-CONTEXT SYNC BLOCK (from [040] / [042]) ────────────
living_context:
  last_verified: "YYYY-MM-DD"
  last_verified_by: ""
  drift_check_date: "YYYY-MM-DD"
  drift_status: ""      # clean | minor-drift | significant-drift
  linked_commit: ""
  linked_adr: []
  next_review_trigger: ""

# ── AI-OPTIMIZATION FIELDS ────────────────────────────────────
intent: >
  Enable the generating-architecture-diagrams skill and engineers
  to produce accurate C4 L2 container and C4 L3 component diagrams
  for [system_name] by providing a complete, structured, and
  verified inventory of all runtime units and their dependencies.

consumption_context:
  - ai-reasoning
  - rag-retrieval
  - agentic-execution
  - human-reading

triggers:
  - "C4 container diagram for [system]"
  - "C4 component diagram for [container]"
  - "what containers does [system] have"
  - "[system] internal architecture detail"
  - "dependencies between [system] services"

negative_triggers:
  - "system-level actors and integrations → TMPL-006"
  - "data entities and flow diagrams → TMPL-008"

volatility: "fast-changing"
review_trigger: ""
research_validated: false
confidence_overall: "conditional"
---
```

> **AI SUMMARY**
> **Core Purpose:** Complete C4 L2/L3 inventory for [system_name] — all containers, components, and their dependencies.
> **Container Count:** [N]
> **Component Count:** [N] (across [N] containers)
> **Primary Tech Stack:** [comma-separated container technologies]
> **Living-Context Status:** Last verified [date] | Drift: [status]
> **Diagram Generation Ready:** ✅ Yes / ❌ No

---

# [System Name]: Container & Component Inventory

**Document ID:** [XXX]-Containers
**Parent Document:** [TMPL-006 Document ID, Section 12]
**Version:** 1.0
**Created:** YYYY-MM-DD
**Status:** Draft

**Cross-References:**

| Relationship | Target | Reason |
|---|---|---|
| [DEPENDS_ON] | [TMPL-006 System Context Pack] | Parent system context; actors, trust boundaries, SLAs |
| [SEE_ALSO] | [TMPL-008 Data & Flow Inventory] | Data stores defined here are referenced in flow diagrams |
| [SEE_ALSO] | [TMPL-009 Sequence & Interaction Catalog] | Container IDs used as sequence diagram participants |
| [SEE_ALSO] | [TMPL-010 Deployment Topology Sheet] | Containers mapped to deployment nodes there |
| [DEPENDS_ON] | [011 Core Architectural Principles, Section 4] | Abstraction layers principle governs L2 vs L3 split |
| [APPLIES] | [040 Living Architecture & Drift Control] | Drift check and living-context sync block |

---

## TABLE OF CONTENTS

1. [Container Inventory — C4 Level 2](#1-container-inventory--c4-level-2)
2. [Container Dependency Map](#2-container-dependency-map)
3. [Component Inventory — C4 Level 3](#3-component-inventory--c4-level-3)
4. [Cross-Cutting Concerns](#4-cross-cutting-concerns)
5. [Diagram Generation Notes](#5-diagram-generation-notes)
6. [Sources & References](#6-sources--references)
7. [Revision History](#7-revision-history)

---

## 1. CONTAINER INVENTORY — C4 LEVEL 2

[TYPE: REFERENCE]

<!-- Each row below is a deployable unit. Source from containers[] in YAML.
     ✅ = container confirmed running in production
     💡 = planned / designed, not yet deployed
     ⚠️ = container exists but details unverified -->

| ID | Container | Type | Technology | Owner Team | Port/Protocol | Trust Boundary | Status |
|----|-----------|------|------------|------------|--------------|----------------|--------|
| CNT-001 | | | | | | | |

### 1.1 Container Descriptions

For each container, provide a short paragraph if responsibilities are non-obvious. One sentence is sufficient for simple containers.

**CNT-001 — [Name]:** [Responsibility description] [✅/💡/⚠️]

---

## 2. CONTAINER DEPENDENCY MAP

[TYPE: REFERENCE]

<!-- Source from container_dependencies[] in YAML.
     Each row is one directed edge in the C4 L2 diagram. -->

| From | To | Label | Technology | Direction | SLA Contract |
|------|----|-------|------------|-----------|-------------|
| CNT-001 | CNT-002 | | | | |

### 2.1 Dependency Narrative

[Prose summary of the key data flows between containers. Identify any circular dependencies ⚠️ and tightly coupled pairs ⚠️.]

---

## 3. COMPONENT INVENTORY — C4 LEVEL 3

[TYPE: REFERENCE]

<!-- Only document when internal structure is architecturally significant.
     Skip this section for simple containers — note "Not decomposed at L3" instead.
     💡 = inferred from code structure | ✅ = confirmed by code review -->

### 3.1 Components of CNT-[N]: [Container Name]

| ID | Component | Type | Technology | Interfaces | Verification |
|----|-----------|------|------------|-----------|-------------|
| CMP-001 | | | | | |

### 3.2 Component Dependency Map

| From | To | Label | Technology |
|------|----|-------|------------|
| CMP-001 | CMP-002 | | |

---

## 4. CROSS-CUTTING CONCERNS

[TYPE: REFERENCE]

<!-- ✅ = active in production | 💡 = designed | ⚠️ = gap or partial -->

| Concern | Implementation | Status |
|---------|---------------|--------|
| Authentication | | |
| Authorisation | | |
| Logging | | |
| Distributed Tracing | | |
| Config Management | | |
| Feature Flags | | |
| API Versioning | | |

---

## 5. DIAGRAM GENERATION NOTES

[TYPE: REFERENCE]

<!-- Instructions for the generating-architecture-diagrams skill. -->

**C4 L2 Generation:**
- Source: `containers[]` and `container_dependencies[]` in YAML front-matter
- Group containers by `trust_boundary_id` for trust-boundary overlay
- Use `scaling_policy` field for annotation where relevant

**C4 L3 Generation:**
- Source: `components[]` filtered by `parent_container_id`
- Only generate L3 for containers flagged as `type: api | web-app | service`
- Skip L3 for databases, caches, queues — their internal structure is vendor-managed

**Verification Label Reminder:**
- Every node in the generated diagram must map back to a container or component with a verification label
- Unlabeled containers are a diagram quality gate failure

---

## DIAGRAM-READY QUALITY GATE

```
[ ] containers[] has min 1 entry with id, name, type, technology
[ ] Every container has owner_team populated
[ ] container_dependencies[] captures all known inter-container calls
[ ] Components documented for all containers of type api/web-app/service
[ ] cross_cutting auth_mechanism is not blank
[ ] Every container and component carries a verification label (✅/💡/⚠️)
[ ] living_context.last_verified populated
[ ] living_context.drift_status populated
[ ] Parent TMPL-006 document ID is in parent_document field
[ ] Bidirectional reference added in TMPL-006 Section 12
[ ] Revision history present
```

---

## 6. SOURCES & REFERENCES

[TYPE: REFERENCE]

| Reference | Document | Section | Topic |
|-----------|----------|---------|-------|
| REF-001 | [TMPL-006 System Context Pack] | All | System context, actors, trust boundaries |
| REF-002 | [011 Core Architectural Principles] | 4 | Abstraction layers — L2 vs L3 split |
| REF-003 | [021 C4 Model] | 2–3 | C4 L2 container and L3 component definitions |
| REF-004 | [013 Truth, Verification & Doc Governance] | 3.5 | 7-label verification system |
| REF-005 | [040 Living Architecture & Drift Control] | 6 | Living-context sync block |

---

## 7. REVISION HISTORY

[TYPE: REFERENCE]

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | YYYY-MM-DD | Initial | Document created | [Reason] |

---

*TMPL-007 v2.0 — Container & Component Inventory. Governed by [RULES-001] and [013]. Verification labels mandatory per [013, Section 3.5]. Parent: [TMPL-006].*
