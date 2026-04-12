# [System Name]: Data & Flow Inventory
## [Subtitle — e.g., "Entity Model, Data Stores, and Flow Diagrams — v1.0"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-008: DATA & FLOW INVENTORY  (v2.0)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: Structured inventory of all data entities, data stores,
data flows, transformations, and PII classifications for a single
system. The primary feed for DFD (Level 0 and Level 1) and ERD
diagrams in the generating-architecture-diagrams skill.

AUTHORING ORDER:
  1. TMPL-006 (System Context Pack) must exist — provides
     data_classifications[] summary.
  2. TMPL-007 (Container & Component Inventory) should exist —
     data stores here reference containers defined there.
  3. This document provides entity-level detail that neither
     TMPL-006 nor TMPL-007 contains.

THIS IS NOT FOR:
  - System-level context (actors, integrations) → TMPL-006
  - Container and component structure → TMPL-007
  - Sequence/interaction flows → TMPL-009
  - Deployment infrastructure → TMPL-010
  - Portfolio multi-system data mesh → TMPL-011

DIAGRAM SKILL FEED:
  entities[]    → ERD nodes
  relationships[] → ERD edges
  data_stores[] → DFD data-store rectangles
  flows[]       → DFD arrows (processes, external entities, stores)
  transformations[] → DFD process circles/rectangles

VERIFICATION REQUIREMENT (Verification Mode = Medium-High):
  ✅ verified  ⚠️ flagged  🚩 outlier  ❌ removed
  ❓ unresolved  💡 inference  🗑️ bogus caught

METHODOLOGY PHASE BINDING (from [030]):
  Typically Phase-4-HighLevel (DFD L0) or Phase-5-DetailedDesign
  (DFD L1, ERD).
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ────────────────────────────────
document_id: "[XXX Short-Title]-DataFlow"
title: "[System Name] — Data & Flow Inventory"
version: "1.0"
created: "YYYY-MM-DD"
status: "Draft | Review | Final"
parent_document: "[TMPL-006 System Context Pack for this system]"
template_version_used: "TMPL-008 v2.0"

# ── METHODOLOGY PHASE ─────────────────────────────────────────
methodology_phase: "Phase-5-DetailedDesign"
design_methodology_ref: "[030 System Design Methodology, Section 4]"

# ── SYSTEM BACK-REFERENCE ─────────────────────────────────────
system_id: ""           # Must match system.system_id in TMPL-006
system_name: ""
context_pack_ref: ""
container_inventory_ref: ""  # Document ID of TMPL-007 for this system

# ── DATA ENTITIES (ERD nodes) ─────────────────────────────────
entities:
  - id: ""              # "ENT-001"
    name: ""            # "Order"
    description: ""     # One sentence
    primary_key: ""     # "order_id (UUID)"
    attributes:
      - name: ""        # "customer_id"
        type: ""        # "UUID | VARCHAR(255) | TIMESTAMP | DECIMAL | BOOLEAN"
        nullable: false
        pii: false      # true if this attribute is personally identifiable
        phi: false      # true if protected health information
        financial: false
    data_store_id: ""   # References data_stores[].id where this entity lives
    verification: ""    # ✅ | 💡 | ⚠️

# ── ENTITY RELATIONSHIPS (ERD edges) ─────────────────────────
relationships:
  - id: ""              # "REL-001"
    from_entity: ""     # Entity ID
    to_entity: ""       # Entity ID
    cardinality: ""     # "1:1" | "1:N" | "N:M"
    label: ""           # "places" | "contains" | "belongs_to"
    relationship_type: "" # "identifying" | "non-identifying" | "associative"

# ── DATA STORES (DFD data-store rectangles) ───────────────────
data_stores:
  - id: ""              # "DS-001"
    name: ""            # "orders_db"
    container_id: ""    # References containers[].id in TMPL-007
    type: ""            # relational | document | key-value | time-series
                        # | graph | object-store | file-store | search-index
                        # | cache | message-queue | data-warehouse | data-lake
    technology: ""      # "PostgreSQL 15" | "S3" | "Redis 7"
    entities_stored: [] # List of entity IDs stored here
    data_classifications: []  # "PII" | "financial" | "public" | etc.
    retention_policy: ""      # "7 years" | "30 days" | "indefinite"
    backup_policy: ""         # "daily snapshot" | "WAL streaming" | "none"
    encryption_at_rest: ""    # "AES-256" | "vendor-managed" | "none"
    owner_team: ""
    verification: ""    # ✅ | 💡 | ⚠️

# ── DATA FLOWS (DFD arrows) ────────────────────────────────────
# Model flows between: actors, processes (containers/components),
# and data stores. Matches DFD Gane-Sarson or Yourdon-DeMarco notation.
flows:
  - id: ""              # "FLW-001"
    name: ""            # "Submit Order"
    from_id: ""         # Actor ID (TMPL-006) | container ID | data store ID
    from_type: ""       # actor | process | data-store
    to_id: ""           # Same options
    to_type: ""         # actor | process | data-store
    data_description: "" # What data moves: "Order payload (JSON)"
    data_classification: "" # PII | financial | public | internal
    protocol: ""        # REST | gRPC | SQL query | S3 PUT | Kafka topic | etc.
    frequency: ""       # "real-time" | "batch-daily" | "event-driven"
    volume: ""          # "~500 req/s peak" | "~2M records/day"
    dfd_level: ""       # L0 | L1 | L2
    verification: ""    # ✅ | 💡 | ⚠️

# ── TRANSFORMATIONS / PROCESSES (DFD process circles) ─────────
transformations:
  - id: ""              # "TRF-001"
    name: ""            # "Validate & Enrich Order"
    container_id: ""    # Which container performs this transformation
    inputs: []          # List of flow IDs flowing in
    outputs: []         # List of flow IDs flowing out
    description: ""     # What transformation is applied
    pii_handling: ""    # "masked before logging" | "passed through" | "N/A"

# ── PII / SENSITIVE DATA INVENTORY ────────────────────────────
# Consolidated view for compliance and security review
pii_inventory:
  - entity_id: ""
    attribute: ""
    classification: ""  # PII | PHI | financial | credentials
    purpose: ""         # Why this data is collected
    legal_basis: ""     # GDPR/PIPEDA/CCPA basis: "consent" | "contract" | "legal obligation"
    retention: ""
    deletion_mechanism: "" # "hard delete" | "pseudonymisation" | "not defined"

# ── LIVING-CONTEXT SYNC BLOCK (from [040] / [042]) ────────────
living_context:
  last_verified: "YYYY-MM-DD"
  last_verified_by: ""
  drift_check_date: "YYYY-MM-DD"
  drift_status: ""      # clean | minor-drift | significant-drift
  linked_commit: ""
  linked_adr: []
  next_review_trigger: ""
  schema_migration_ref: "" # Link to latest migration file/PR

# ── AI-OPTIMIZATION FIELDS ────────────────────────────────────
intent: >
  Enable the generating-architecture-diagrams skill to produce
  accurate DFD and ERD diagrams for [system_name] by providing
  a verified inventory of all data entities, stores, flows,
  transformations, and PII classifications.

consumption_context:
  - ai-reasoning
  - rag-retrieval
  - agentic-execution
  - human-reading

triggers:
  - "DFD for [system]"
  - "ERD for [system]"
  - "data flow diagram [system]"
  - "what data does [system] store"
  - "PII inventory for [system]"
  - "entity relationship diagram [system]"

negative_triggers:
  - "container or service structure → TMPL-007"
  - "sequence interactions → TMPL-009"
  - "deployment infrastructure → TMPL-010"

volatility: "fast-changing"
review_trigger: ""
research_validated: false
confidence_overall: "conditional"
---
```

> **AI SUMMARY**
> **Core Purpose:** Complete data and flow inventory for [system_name] — all entities, stores, flows, and PII classifications.
> **Entity Count:** [N] | **Data Store Count:** [N] | **Flow Count:** [N]
> **PII Data Present:** Yes / No
> **Living-Context Status:** Last verified [date] | Drift: [status]
> **Diagram Generation Ready:** ✅ Yes / ❌ No

---

# [System Name]: Data & Flow Inventory

**Document ID:** [XXX]-DataFlow
**Parent Document:** [TMPL-006 Document ID, Section 12]
**Version:** 1.0
**Created:** YYYY-MM-DD
**Status:** Draft

**Cross-References:**

| Relationship | Target | Reason |
|---|---|---|
| [DEPENDS_ON] | [TMPL-006 System Context Pack] | System actors, external integrations, data classification summary |
| [DEPENDS_ON] | [TMPL-007 Container & Component Inventory] | Data store containers defined there; referenced here |
| [SEE_ALSO] | [TMPL-009 Sequence & Interaction Catalog] | Business flows reference entities and data stores defined here |
| [DEPENDS_ON] | [011 Core Architectural Principles, Section 9] | Security-by-design governs PII handling decisions |
| [DEPENDS_ON] | [013 Truth, Verification & Doc Governance, Section 3.5] | 7-label verification mandatory |
| [APPLIES] | [040 Living Architecture & Drift Control] | Living-context sync; schema changes trigger drift |

---

## TABLE OF CONTENTS

1. [Data Entity Model](#1-data-entity-model)
2. [Data Stores](#2-data-stores)
3. [Data Flows — Level 0 (Context)](#3-data-flows--level-0-context)
4. [Data Flows — Level 1 (Process Detail)](#4-data-flows--level-1-process-detail)
5. [Transformations & Processing](#5-transformations--processing)
6. [PII & Sensitive Data Inventory](#6-pii--sensitive-data-inventory)
7. [Diagram Generation Notes](#7-diagram-generation-notes)
8. [Sources & References](#8-sources--references)
9. [Revision History](#9-revision-history)

---

## 1. DATA ENTITY MODEL

[TYPE: REFERENCE]

<!-- Drawn from entities[] and relationships[] in YAML.
     ✅ = confirmed from live schema | 💡 = designed/inferred | ⚠️ = unverified -->

### 1.1 Entity Summary

| ID | Entity | Primary Key | Data Store | PII Fields | Verification |
|----|--------|-------------|------------|-----------|-------------|
| ENT-001 | | | | | |

### 1.2 Entity Relationships

| ID | From | To | Cardinality | Label | Type |
|----|------|----|-------------|-------|------|
| REL-001 | | | | | |

### 1.3 Entity Descriptions

**ENT-001 — [Entity Name]:** [One paragraph. What this entity represents in the business domain, key attributes, and any constraints worth noting.] [✅/💡/⚠️]

---

## 2. DATA STORES

[TYPE: REFERENCE]

<!-- One row per data store. Source from data_stores[] in YAML.
     Reference the container_id from TMPL-007 for full technical context. -->

| ID | Store Name | Type | Technology | Classifications | Retention | Encryption | Owner Team | Verification |
|----|------------|------|------------|----------------|-----------|------------|------------|-------------|
| DS-001 | | | | | | | | |

### 2.1 Storage Architecture Notes

[Prose: describe the storage architecture. Note any polyglot persistence trade-offs ⚠️, CQRS read/write store splits, or event sourcing patterns 💡.]

---

## 3. DATA FLOWS — LEVEL 0 (CONTEXT)

[TYPE: RESEARCH_FINDING]

<!-- DFD Level 0: one big process bubble for the entire system.
     Shows only external actors and external systems exchanging data.
     Matches the DFD L0 standard from [023 Data Flow Diagrams, Section 2]. -->

| ID | From | Type | To | Type | Data Description | Classification | Protocol | Verification |
|----|------|------|----|------|-----------------|----------------|----------|-------------|
| FLW-001 | | | | | | | | |

---

## 4. DATA FLOWS — LEVEL 1 (PROCESS DETAIL)

[TYPE: RESEARCH_FINDING]

<!-- DFD Level 1: system decomposed into 3–7 major processes.
     Shows flows between those processes, data stores, and external entities.
     Each process maps to a container or major component from TMPL-007. -->

| ID | From | Type | To | Type | Data Description | Volume | Frequency | Verification |
|----|------|------|----|------|-----------------|--------|-----------|-------------|
| FLW-010 | | | | | | | | |

---

## 5. TRANSFORMATIONS & PROCESSING

[TYPE: REFERENCE]

<!-- Source from transformations[] in YAML.
     Documents what happens to data between input and output. -->

| ID | Process Name | Container | Inputs | Outputs | PII Handling | Verification |
|----|-------------|-----------|--------|---------|-------------|-------------|
| TRF-001 | | | | | | |

---

## 6. PII & SENSITIVE DATA INVENTORY

[TYPE: RESEARCH_FINDING]

<!-- Consolidated compliance view. Source from pii_inventory[] in YAML.
     ✅ = confirmed legal basis documented | ⚠️ = legal basis not yet confirmed -->

| Entity | Attribute | Classification | Purpose | Legal Basis | Retention | Deletion Mechanism | Verification |
|--------|-----------|----------------|---------|------------|-----------|-------------------|-------------|
| | | | | | | | |

### 6.1 PII Risk Summary

[Prose: summary of PII risk surface. Identify highest-risk data attributes and existing controls. Note any gaps ⚠️ or unresolved items ❓.]

---

## 7. DIAGRAM GENERATION NOTES

[TYPE: REFERENCE]

**DFD L0 Generation:**
- Source: `flows[]` filtered by `dfd_level: L0`
- Actors from TMPL-006 `actors[]` serve as external entities
- External integrations from TMPL-006 serve as external entities

**DFD L1 Generation:**
- Source: `flows[]` filtered by `dfd_level: L1`
- Processes map to containers from TMPL-007
- Data stores from `data_stores[]`

**ERD Generation:**
- Source: `entities[]` and `relationships[]`
- Group entities by `data_store_id` to show bounded contexts
- Flag PII attributes with a visual indicator (e.g., lock icon)

---

## DIAGRAM-READY QUALITY GATE

```
[ ] entities[] has min 1 entry with id, name, attributes
[ ] data_stores[] has min 1 entry; each references a container_id from TMPL-007
[ ] flows[] covers at least the L0 level (all external data exchanges)
[ ] pii_inventory[] populated if any entity has pii: true attributes
[ ] Every entity, store, and flow carries a verification label (✅/💡/⚠️)
[ ] living_context.last_verified populated
[ ] living_context.schema_migration_ref set if any schema migrations exist
[ ] Parent TMPL-006 document ID in parent_document field
[ ] Bidirectional reference added in TMPL-006 Section 12
[ ] Revision history present
```

---

## 8. SOURCES & REFERENCES

[TYPE: REFERENCE]

| Reference | Document | Section | Topic |
|-----------|----------|---------|-------|
| REF-001 | [TMPL-006 System Context Pack] | data_classifications | System-level data classification summary |
| REF-002 | [TMPL-007 Container & Component Inventory] | containers[] | Data store container definitions |
| REF-003 | [023 Data Flow Diagrams] | 2–4 | DFD L0 and L1 notation standards |
| REF-004 | [025 Sequence, ERD & Deployment Diagrams] | ERD section | ERD notation standards |
| REF-005 | [011 Core Architectural Principles] | 9 | Security-by-design — PII handling |
| REF-006 | [013 Truth, Verification & Doc Governance] | 3.5 | 7-label verification |
| REF-007 | [040 Living Architecture & Drift Control] | 6 | Living-context sync |

---

## 9. REVISION HISTORY

[TYPE: REFERENCE]

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | YYYY-MM-DD | Initial | Document created | [Reason] |

---

*TMPL-008 v2.0 — Data & Flow Inventory. Governed by [RULES-001] and [013]. Verification labels mandatory per [013, Section 3.5]. Parent: [TMPL-006].*
