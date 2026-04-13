# Compliance Risk Assessment (CRA): Container & Component Inventory
## C4 Level 2 & Level 3 Reference — CSI-180236

```yaml
---
# ── RULES-001 STANDARD FIELDS ────────────────────────────────
document_id: "CRA-007 Container & Component Inventory"
title: "CRA — Container & Component Inventory"
version: "1.0"
created: "2026-04-12"
status: "Final"
parent_document: "CRA-006 System Context Pack"
template_version_used: "TMPL-007 v2.0"

# ── METHODOLOGY PHASE ─────────────────────────────────────────
methodology_phase: "Phase-4-HighLevel"
design_methodology_ref: "Documentation_Standards.md, Section 3"

# ── SYSTEM BACK-REFERENCE ─────────────────────────────────────
system_id: "SYS-CRA-180236"
system_name: "Compliance Risk Assessment (CRA)"
context_pack_ref: "CRA-006 System Context Pack"

# ── C4 LEVEL 2: CONTAINERS ────────────────────────────────────
containers:
  - id: "CNT-001"
    name: "CRA UI / Assessment Interface"
    type: "web-app"
    technology: "Web front-end within CRC platform — technology stack not disclosed"
    description: "Provides the Preparer and Approver user interface for starting, rating, rationale-entry, validation, and submission of GAU assessments"
    responsibilities:
      - "Assessment initiation and Business Activity rating capture"
      - "L2/L1 qualitative rating assignment with mandatory rationale fields"
      - "Validate section counter — prevents submission until counter = 0"
      - "Conclusion section fields (Business Profile, IR Themes, CE Themes, RR Commentary, Recommendation)"
      - "Issue tagging/untagging interface to ICAPS"
      - "Attachments upload and Workflow History display"
      - "Third-party dashboard view (Aggregation tab)"
    port: "443"
    protocol: "HTTPS"
    owner_team: "GFT (Technology Team)"
    scaling_policy: "❓ Not specified in source materials"
    trust_boundary_id: "TB-002"
    health_check: "❓ Not specified"
    data_stores: []
    verification: "💡"

  - id: "CNT-002"
    name: "CRA Rating Results Service"
    type: "api"
    technology: "Internal microservice — technology stack not disclosed"
    description: "Core calculation engine that computes and stores publication-level, L2, L1, and overall GAU Inherent Risk, Control Environment, and Residual Risk ratings using the systemic hybrid methodology"
    responsibilities:
      - "100% systemic publication-level Inherent Risk calculation (Severity × Likelihood)"
      - "100% systemic publication-level Control Environment calculation (SOC × CEff)"
      - "100% systemic Residual Risk calculation via MCA GRC matrix"
      - "Aggregation of publication ratings to L2 → L1 → GAU levels"
      - "Applying Business Activity rating across all publications within a GAU"
      - "Storing and serving all rating results for display and reporting"
    port: "❓"
    protocol: "Internal"
    owner_team: "GFT (Technology Team)"
    scaling_policy: "❓ Not specified"
    trust_boundary_id: "TB-002"
    health_check: "❓ Not specified"
    data_stores: ["DS-001"]
    verification: "💡"

  - id: "CNT-003"
    name: "CRA Scoping Service"
    type: "api"
    technology: "Internal microservice — technology stack not disclosed"
    description: "Determines which publications are in scope for each GAU assessment based on obligation mapping from the Regulatory Inventory; maps GAUs to their applicable publications and L2 risk stripes"
    responsibilities:
      - "Obligation-based publication scoping per GAU (2025 target state)"
      - "Mapping publications to L2 compliance risk stripes via obligation IDs"
      - "Determining applicability factor for each publication-GAU pair"
      - "Identifying when a publication appears multiple times within a GAU across different L2s"
    port: "❓"
    protocol: "Internal"
    owner_team: "GFT (Technology Team)"
    scaling_policy: "❓ Not specified"
    trust_boundary_id: "TB-002"
    health_check: "❓ Not specified"
    data_stores: ["DS-001"]
    verification: "💡"

  - id: "CNT-004"
    name: "CRA Aggregation Service"
    type: "api"
    technology: "Internal microservice — technology stack not disclosed"
    description: "Aggregates individual GAU-level assessment ratings upward to country, regional, and global business levels for governance reporting; not included in the 2024 CRA process documentation per meeting notes"
    responsibilities:
      - "Risk-weighted average aggregation of inherent risk ratings"
      - "Roll-up of control environment ratings across GAUs"
      - "Production of consolidated views at country / region / global-business levels"
      - "Generation of aggregated reports for ICRM Methodology Team"
    port: "❓"
    protocol: "Internal"
    owner_team: "GFT (Technology Team)"
    scaling_policy: "❓ Not specified"
    trust_boundary_id: "TB-002"
    health_check: "❓ Not specified"
    data_stores: ["DS-001"]
    verification: "💡"

  - id: "CNT-005"
    name: "Third-Party Dashboard"
    type: "web-app"
    technology: "Separate microservice — third-party component not directly part of CRA core"
    description: "Independent dashboard maintained by the CRA team providing business stakeholders visibility into third-party risk profiles; refreshes daily from MCA data, independently from the CRA annual snapshot"
    responsibilities:
      - "Daily refresh of MCA data for third-party risk profiles"
      - "Searchable view by GRC level, third-party name, or GAU"
      - "Supports Business Profile conclusion section with third-party relationship identification"
      - "Operates independently from CRA core annual snapshot architecture"
    port: "❓"
    protocol: "HTTPS"
    owner_team: "GFT (Technology Team)"
    scaling_policy: "❓ Not specified"
    trust_boundary_id: "TB-002"
    health_check: "❓ Not specified"
    data_stores: ["DS-003"]
    verification: "✅"

  - id: "CNT-006"
    name: "CRA Data Snapshot Store"
    type: "database"
    technology: "On-premise relational/file store — technology not disclosed"
    description: "Stores the single-date snapshot of upstream data (MCA, Regulatory Inventory, ICAPS) taken on the annual data date; provides a consistent, locally stored data foundation for the entire assessment window"
    responsibilities:
      - "Storing the October 13 (2025) single data pull snapshot"
      - "Providing consistent read-only data access to all CRA calculation services during the assessment window"
      - "Eliminating runtime dependency on upstream systems during assessments"
      - "Supporting data traceability — all calculations traceable to one source snapshot"
    port: "❓"
    protocol: "Internal SQL / file"
    owner_team: "GFT (Technology Team)"
    scaling_policy: "Single instance — batch write at snapshot date; read-only thereafter"
    trust_boundary_id: "TB-001"
    health_check: "❓ Not specified"
    data_stores: []
    verification: "✅"

  - id: "CNT-007"
    name: "CRA Reporting & Output Service"
    type: "api"
    technology: "Internal reporting service — technology not disclosed"
    description: "Generates assessment reports, aggregated risk views, and board-level presentations from finalised assessment data; feeds output to governance committees and the GCB Xceptor RPA downstream consumer"
    responsibilities:
      - "Generating individual GAU assessment reports"
      - "Producing aggregated country / regional / global business reports"
      - "Preparing board-level State of Risk report inputs"
      - "Providing data feed to GCB Xceptor RPA (CSI-173877) for downstream automation"
    port: "❓"
    protocol: "Internal"
    owner_team: "GFT (Technology Team) + ICRM Methodology Team"
    scaling_policy: "❓ Not specified"
    trust_boundary_id: "TB-002"
    health_check: "❓ Not specified"
    data_stores: ["DS-001", "DS-002"]
    verification: "💡"

# ── CONTAINER DEPENDENCIES ────────────────────────────────────
container_dependencies:
  - from_id: "CNT-001"
    to_id: "CNT-002"
    label: "Submits L2/L1 qualitative ratings and rationale; reads back calculated publication ratings"
    technology: "Internal API"
    direction: "bidirectional"
    sla_contract: "❓ Not specified"

  - from_id: "CNT-001"
    to_id: "CNT-003"
    label: "Reads scoped publications and obligation mappings for display"
    technology: "Internal API"
    direction: "sync"
    sla_contract: "❓ Not specified"

  - from_id: "CNT-001"
    to_id: "CNT-005"
    label: "Embeds third-party dashboard view in Aggregation tab"
    technology: "Internal / iframe"
    direction: "sync"
    sla_contract: "Daily refresh"

  - from_id: "CNT-002"
    to_id: "CNT-006"
    label: "Reads upstream snapshot data for systemic calculations"
    technology: "Internal data access"
    direction: "sync"
    sla_contract: "Read-only after data date"

  - from_id: "CNT-003"
    to_id: "CNT-006"
    label: "Reads obligation mappings and publication data from snapshot"
    technology: "Internal data access"
    direction: "sync"
    sla_contract: "Read-only after data date"

  - from_id: "CNT-004"
    to_id: "CNT-002"
    label: "Reads individual GAU ratings for roll-up aggregation"
    technology: "Internal API"
    direction: "sync"
    sla_contract: "❓ Not specified"

  - from_id: "CNT-004"
    to_id: "CNT-007"
    label: "Sends aggregated results for report generation"
    technology: "Internal API"
    direction: "async"
    sla_contract: "❓ Not specified"

  - from_id: "CNT-006"
    to_id: "INT-002"
    label: "Populated from MCA annual snapshot (October 13 data pull)"
    technology: "Internal batch extract"
    direction: "inbound"
    sla_contract: "Annual — data date only"

  - from_id: "CNT-006"
    to_id: "INT-005"
    label: "Populated from Regulatory Inventory annual snapshot"
    technology: "Internal batch extract"
    direction: "inbound"
    sla_contract: "Annual — data date only"

  - from_id: "CNT-006"
    to_id: "INT-004"
    label: "Populated from ICAPS open issues annual snapshot"
    technology: "Internal batch extract"
    direction: "inbound"
    sla_contract: "Annual — data date only"

  - from_id: "CNT-007"
    to_id: "INT-014"
    label: "Sends CRA results to GCB Xceptor RPA for downstream automation"
    technology: "Internal RPA feed"
    direction: "outbound"
    sla_contract: "Post-cycle completion"

# ── C4 LEVEL 3: COMPONENTS ────────────────────────────────────
components:
  - id: "CMP-001"
    parent_container_id: "CNT-002"
    name: "Inherent Risk Calculator"
    type: "service"
    technology: "Internal calculation engine"
    description: "Implements the severity × likelihood formula: pulls publication tier from Regulatory Inventory, aggregates Significance of Impact from MCA, computes Complexity from PARCUM, applies Applicability from obligation mapping, produces publication-level IR tier"
    interfaces:
      - "Calculate IR: publication_id + GAU_id → IR Tier 1-5"
      - "Read Business Activity rating for likelihood aggregation"
    dependencies: ["CMP-003", "CMP-004"]
    verification: "💡"

  - id: "CMP-002"
    parent_container_id: "CNT-002"
    name: "Control Environment Calculator"
    type: "service"
    technology: "Internal calculation engine"
    description: "Implements Strength of Controls decision tree (MCA design attributes) and Control Effectiveness waterfall (ICAPS issue count × severity), combines to produce publication-level CE rating (Effective / Partially Ineffective / Ineffective)"
    interfaces:
      - "Calculate CE: publication_id + GAU_id → CE rating"
      - "Aggregate L2 CE using highest-deficiency waterfall"
    dependencies: ["CMP-003", "CMP-004"]
    verification: "💡"

  - id: "CMP-003"
    parent_container_id: "CNT-002"
    name: "Residual Risk Matrix Engine"
    type: "service"
    technology: "Internal lookup / matrix engine"
    description: "Applies the MCA GRC Residual Risk Matrix to intersect IR Tier and CE rating at every level (publication, L2, L1, GAU) to produce the final Residual Risk tier; outputs are read-only for all user roles"
    interfaces:
      - "Calculate RR: IR_tier + CE_rating → RR Tier 1-5 (via matrix lookup)"
    dependencies: []
    verification: "✅"

  - id: "CMP-004"
    parent_container_id: "CNT-002"
    name: "Aggregation Engine"
    type: "service"
    technology: "Internal roll-up engine"
    description: "Rolls up publication-level ratings to L2, then L2 to L1, then L1 to overall GAU; applies qualitative SME overrides entered by Preparers; implements highest-deficiency waterfall for CE aggregation"
    interfaces:
      - "Roll up: publication ratings → L2 → L1 → GAU"
      - "Apply: Preparer qualitative L2 override → L1 recalculation"
    dependencies: []
    verification: "💡"

  - id: "CMP-005"
    parent_container_id: "CNT-002"
    name: "Validation Gate"
    type: "validator"
    technology: "Internal rules engine"
    description: "Tracks all mandatory fields across Inherent Risk, Control Environment, and Conclusion sections; maintains the validate counter displayed to the Preparer; blocks submission until counter reaches zero"
    interfaces:
      - "Validate: assessment_id → {missing_fields[], counter: int}"
      - "Block submission if counter > 0"
    dependencies: []
    verification: "✅"

  - id: "CMP-006"
    parent_container_id: "CNT-003"
    name: "Obligation Mapper"
    type: "service"
    technology: "Internal mapping service"
    description: "Implements the 2025 obligation-based publication scoping: for a given GAU, identifies all publications whose obligations are mapped to compliance L2 risks; a publication may appear multiple times if its obligations span multiple L2 risk stripes"
    interfaces:
      - "Scope: GAU_id → [publication_id × L2_risk_stripe]"
      - "Calculate Applicability: obligation_count / total_obligations → Low/Medium/High"
    dependencies: []
    verification: "✅"

# ── COMPONENT DEPENDENCIES ────────────────────────────────────
component_dependencies:
  - from_id: "CMP-001"
    to_id: "CMP-004"
    label: "Passes publication-level IR tiers for roll-up"
    technology: "Internal"

  - from_id: "CMP-002"
    to_id: "CMP-004"
    label: "Passes publication-level CE ratings for roll-up"
    technology: "Internal"

  - from_id: "CMP-004"
    to_id: "CMP-003"
    label: "Passes aggregated IR and CE results for RR matrix calculation"
    technology: "Internal"

  - from_id: "CMP-004"
    to_id: "CMP-005"
    label: "Notifies validation gate as mandatory fields are completed"
    technology: "Internal"

# ── CROSS-CUTTING CONCERNS ────────────────────────────────────
cross_cutting:
  auth_mechanism: "Entitlement-based access via City Marketplace; role-differentiated (Preparer vs Approver); session-based within CRC platform ✅"
  logging: "❓ Specific logging framework not disclosed in source materials; Workflow History feature provides user-action audit trail within the tool ✅"
  tracing: "❓ Distributed tracing not specified in source materials"
  config_management: "❓ Not specified; on-premise CTI infrastructure implies internal config management"
  feature_flags: "❓ Not specified"
  api_versioning: "❓ Not specified; CRC platform versioning implied by multi-year cycle history"

# ── LIVING CONTEXT SYNC ───────────────────────────────────────
living_context:
  last_verified: "2026-04-12"
  last_verified_by: "ai-documentation skill — synthesised from project source documents"
  drift_check_date: "2026-04-12"
  drift_status: "clean"
  linked_commit: ""
  linked_adr: []
  next_review_trigger: "GFT architecture review or annual CRA cycle methodology change"

# ── AI-OPTIMISATION FIELDS ────────────────────────────────────
intent: >
  Enable the generating-architecture-diagrams skill and engineers to produce
  accurate C4 L2 and L3 diagrams for CRA (CSI-180236) by providing a structured
  inventory of all runtime containers and their internal components, sourced from
  available project documentation and marked with inference labels where internal
  architecture was not explicitly disclosed.

consumption_context:
  - ai-reasoning
  - rag-retrieval
  - agentic-execution
  - human-reading

triggers:
  - "CRA container diagram"
  - "CRA microservices architecture"
  - "CRA internal components"
  - "CRA C4 L2"
  - "CRA calculation engine components"

negative_triggers:
  - "CRA system-level actors and integrations"   # → CRA-006
  - "CRA data flows and entities"                # → CRA-008

confidence_overall: "medium-high"
confidence_note: >
  Container names (UI, Rating Results, Scoping, Aggregation) confirmed from
  meeting notes. Third-party dashboard confirmed from training transcript.
  Internal component decomposition (CMP-001 through CMP-006) is 💡 inferred
  from the detailed methodology description in source documents — exact
  implementation details not disclosed. ❓ fields indicate information
  unavailable in source materials.
---
```

---

> ## 🤖 AI Summary
> **System:** CRA CSI-180236 — Container & Component Inventory
> **Core Purpose:** C4 L2/L3 inventory of all CRA runtime containers and key internal components derived from project source documentation.
> **Container Count:** 7 (UI, Rating Results, Scoping, Aggregation, Third-Party Dashboard, Data Snapshot Store, Reporting)
> **Component Count:** 6 (IR Calculator, CE Calculator, RR Matrix Engine, Aggregation Engine, Validation Gate, Obligation Mapper)
> **Confidence Note:** Container names confirmed ✅; component decomposition 💡 inferred from methodology detail; technology stack ❓ not disclosed
> **Living-Context Status:** Last verified 2026-04-12 | Drift: clean
> **Diagram Generation Ready:** ✅ Yes (with inference label awareness)

---

## TABLE OF CONTENTS

1. [Container Inventory — C4 Level 2](#1-container-inventory--c4-level-2)
2. [Container Dependency Map](#2-container-dependency-map)
3. [Component Inventory — C4 Level 3](#3-component-inventory--c4-level-3)
4. [Cross-Cutting Concerns](#4-cross-cutting-concerns)
5. [Diagram Generation Notes](#5-diagram-generation-notes)
6. [Sources & Cross-References](#6-sources--cross-references)
7. [Revision History](#7-revision-history)

---

## 1. Container Inventory — C4 Level 2

[TYPE: REFERENCE]

The CRA system (CSI-180236) is composed of seven containers. ✅ Four are core microservices identified by name in meeting notes and training materials. The Data Snapshot Store reflects the 2024-introduced single-data-pull architecture. The Third-Party Dashboard is explicitly described as an independent component. The Reporting Service is inferred from described governance output capabilities.

| ID | Container | Type | Owner Team | Trust Boundary | Verification |
|----|-----------|------|------------|----------------|-------------|
| CNT-001 | CRA UI / Assessment Interface | web-app | GFT | TB-002 (CRC Platform) | 💡 |
| CNT-002 | CRA Rating Results Service | api | GFT | TB-002 (CRC Platform) | 💡 |
| CNT-003 | CRA Scoping Service | api | GFT | TB-002 (CRC Platform) | 💡 |
| CNT-004 | CRA Aggregation Service | api | GFT | TB-002 (CRC Platform) | 💡 |
| CNT-005 | Third-Party Dashboard | web-app | GFT | TB-002 (CRC Platform) | ✅ |
| CNT-006 | CRA Data Snapshot Store | database | GFT | TB-001 (Internal Network) | ✅ |
| CNT-007 | CRA Reporting & Output Service | api | GFT + ICRM | TB-002 (CRC Platform) | 💡 |

### 1.1 Container Descriptions

**CNT-001 — CRA UI / Assessment Interface:** 💡 The primary user-facing interface within the CRC platform. Preparers use it to start assessments, assign Business Activity ratings, enter qualitative L2/L1 ratings with mandatory rationale, manage issue tagging, complete conclusion fields, validate, and submit. Approvers use it to review and approve or return assessments. Provides access to the Validate section (mandatory-field counter), Attachments, Workflow History, and embedded Third-Party Dashboard. ✅

**CNT-002 — CRA Rating Results Service:** 💡 The core calculation engine of the CRA. Implements the 2025 hybrid methodology — 100% systemic publication-level ratings, with support for Preparer qualitative overrides at L2/L1. Owns the GRC matrix lookup for Residual Risk. The most architecturally critical container in the system.

**CNT-003 — CRA Scoping Service:** 💡 Determines the scope of each GAU assessment under the 2025 obligation-based mapping model. ✅ Ensures that publications appear once per L2 risk stripe they are obligation-mapped to, which may result in a single publication appearing multiple times in one GAU assessment.

**CNT-004 — CRA Aggregation Service:** 💡 Operates post-submission to produce the consolidated risk picture for governance reporting. Note: the 2024 architectural review notes indicate this service was not fully included in documentation at that time. ⚠️

**CNT-005 — Third-Party Dashboard:** ✅ An independently operating component described explicitly in training materials as a "third-party microservice not directly related to CRA" that refreshes daily from MCA data. Provides searchable third-party risk profiles by GAU, GRC level, or third-party name within the Aggregation tab of the CRC module.

**CNT-006 — CRA Data Snapshot Store:** ✅ The local data store holding the annual snapshot of upstream system data. Introduced in 2024 to eliminate data mixing issues. Written once at the annual data date (October 13 for the 2025 cycle) and read-only thereafter throughout the assessment window. Stores data from MCA, Regulatory Inventory, and ICAPS.

**CNT-007 — CRA Reporting & Output Service:** 💡 Handles report generation for individual assessments, aggregated views, and board-level outputs. Also provides the data feed to the downstream GCB Xceptor RPA (INT-014, CSI-173877).

---

## 2. Container Dependency Map

[TYPE: REFERENCE]

| From | To | Label | Direction | Verification |
|------|----|-------|-----------|-------------|
| CNT-001 (UI) | CNT-002 (Rating Results) | Submits qualitative ratings; reads calculated results | Bidirectional | 💡 |
| CNT-001 (UI) | CNT-003 (Scoping) | Reads scoped publications for display | Sync | 💡 |
| CNT-001 (UI) | CNT-005 (Third-Party Dashboard) | Embeds dashboard in Aggregation tab | Sync | ✅ |
| CNT-002 (Rating Results) | CNT-006 (Snapshot Store) | Reads snapshot data for systemic calculations | Sync read-only | ✅ |
| CNT-003 (Scoping) | CNT-006 (Snapshot Store) | Reads obligation mappings and publication data | Sync read-only | ✅ |
| CNT-004 (Aggregation) | CNT-002 (Rating Results) | Reads individual GAU ratings for roll-up | Sync | 💡 |
| CNT-004 (Aggregation) | CNT-007 (Reporting) | Sends aggregated results | Async | 💡 |
| CNT-006 (Snapshot Store) | INT-002 (MCA) | Populated from MCA at annual data date | Inbound batch | ✅ |
| CNT-006 (Snapshot Store) | INT-005 (Reg Inventory) | Populated from Reg Inventory at annual data date | Inbound batch | ✅ |
| CNT-006 (Snapshot Store) | INT-004 (ICAPS) | Populated from ICAPS open issues at annual data date | Inbound batch | ✅ |
| CNT-007 (Reporting) | INT-014 (GCB Xceptor RPA) | Sends CRA results for downstream RPA automation | Outbound | ✅ |
| CNT-005 (Third-Party Dashboard) | INT-002 (MCA) | Refreshes daily from MCA data | Inbound daily | ✅ |

### 2.1 Dependency Narrative

The CRA system follows a **hub-and-spoke data pattern** centered on the Data Snapshot Store (CNT-006). ✅ All core calculation containers (Rating Results, Scoping) read from this single local store rather than querying upstream systems directly during the assessment window — this is the architectural realisation of the single-data-pull design decision made in 2024.

The UI (CNT-001) acts as the sole human-facing interface, orchestrating calls to Rating Results and Scoping to present assessors with a consistent view. The Third-Party Dashboard (CNT-005) is deliberately decoupled — it maintains its own daily refresh cycle from MCA, operating independently of the annual snapshot. ✅ This is not a coupling risk but an intentional architectural separation of concerns between the annual compliance assessment and the daily third-party monitoring function.

The Aggregation Service (CNT-004) operates downstream of individual GAU completions, and its outputs flow into Reporting (CNT-007), which is the terminal point for both internal governance consumers (ICRM, BRCC, Board) and the external RPA downstream system. ✅

---

## 3. Component Inventory — C4 Level 3

[TYPE: REFERENCE]

L3 decomposition is provided for CNT-002 (Rating Results Service) and CNT-003 (Scoping Service) as these contain the most architecturally significant internal logic. Components are 💡 inferred from the detailed methodology descriptions in source documents — exact implementation structure not confirmed.

### 3.1 Components of CNT-002: CRA Rating Results Service

| ID | Component | Type | Responsibility | Verification |
|----|-----------|------|---------------|-------------|
| CMP-001 | Inherent Risk Calculator | service | Severity × Likelihood → Publication IR Tier | 💡 |
| CMP-002 | Control Environment Calculator | service | SOC decision tree + CEff waterfall → Publication CE rating | 💡 |
| CMP-003 | Residual Risk Matrix Engine | service | IR Tier × CE rating → RR Tier (GRC matrix lookup) | ✅ |
| CMP-004 | Aggregation Engine | service | Publication → L2 → L1 → GAU roll-up; applies qualitative overrides | 💡 |
| CMP-005 | Validation Gate | validator | Mandatory-field counter; blocks submission until counter = 0 | ✅ |

### 3.2 Components of CNT-003: CRA Scoping Service

| ID | Component | Type | Responsibility | Verification |
|----|-----------|------|---------------|-------------|
| CMP-006 | Obligation Mapper | service | GAU_id → [publication × L2 risk stripe] via obligation-based mapping | ✅ |

### 3.3 Component Dependency Map

| From | To | Label | Verification |
|------|----|-------|-------------|
| CMP-001 (IR Calculator) | CMP-004 (Aggregation Engine) | Publication IR tiers → L2 roll-up | 💡 |
| CMP-002 (CE Calculator) | CMP-004 (Aggregation Engine) | Publication CE ratings → L2 highest-deficiency waterfall | 💡 |
| CMP-004 (Aggregation Engine) | CMP-003 (RR Matrix Engine) | Aggregated IR + CE → Residual Risk matrix lookup | 💡 |
| CMP-004 (Aggregation Engine) | CMP-005 (Validation Gate) | Notifies gate as mandatory fields complete | ✅ |

---

## 4. Cross-Cutting Concerns

[TYPE: REFERENCE]

| Concern | Implementation | Verification |
|---------|---------------|-------------|
| Authentication | Entitlement-based via City Marketplace; role-separated Preparer / Approver within CRC | ✅ |
| Authorisation | Preparer role: create, edit, submit. Approver role: review, approve, return. ICRM Methodology Team: admin. Enforced by CRC platform entitlements | ✅ |
| Audit Trail | Workflow History within CRC tool records all user actions with timestamps | ✅ |
| Data Integrity | Single annual snapshot eliminates data mixing; read-only snapshot during assessment window | ✅ |
| Logging | ❓ Specific logging framework not disclosed | ❓ |
| Distributed Tracing | ❓ Not specified in source materials | ❓ |
| Config Management | ❓ Not specified; on-premise CTI infrastructure implies internal config management | ❓ |
| API Versioning | ❓ Not specified; CRC platform versioning implied by multi-year cycle history | ❓ |

---

## 5. Diagram Generation Notes

[TYPE: REFERENCE]

**C4 L2 Generation:**
- Source: `containers[]` and `container_dependencies[]` in YAML front-matter
- Group CNT-001 through CNT-005 and CNT-007 inside TB-002 (CRC Platform boundary)
- Place CNT-006 (Snapshot Store) inside TB-001 (Internal Network)
- Annotate CNT-005 (Third-Party Dashboard) with "daily refresh" label to distinguish from annual snapshot pattern
- Use dashed borders on 💡 containers to signal inferred architecture

**C4 L3 Generation:**
- Decompose CNT-002 (Rating Results) into CMP-001 through CMP-005
- Decompose CNT-003 (Scoping) into CMP-006
- Skip L3 for CNT-004, CNT-005, CNT-006, CNT-007 — insufficient internal detail available
- Mark all L3 components with 💡 inference indicator except CMP-003 (RR Matrix Engine) and CMP-005 (Validation Gate) which are ✅

**Verification Label Reminder:** Every diagram node must map to a container or component with a verification label. ✅ containers can be rendered with solid lines; 💡 components with dashed or italic labelling per diagram skill conventions.

---

## DIAGRAM-READY QUALITY GATE

```
[✅] containers[] has min 1 entry — 7 containers defined
[✅] Every container has owner_team populated
[✅] container_dependencies[] covers known inter-container calls — 12 dependencies
[✅] Components documented for CNT-002 (5 components) and CNT-003 (1 component)
[✅] cross_cutting.auth_mechanism populated
[✅] Every container and component carries a verification label (✅/💡)
[✅] living_context.last_verified populated
[✅] living_context.drift_status populated
[✅] Parent document CRA-006 referenced
[⚠️] Technology stack details largely ❓ — on-premise CTI internals not disclosed
[⚠️] cross_cutting logging/tracing/config ❓ — not available in source materials
```

---

## 6. Sources & Cross-References

[TYPE: REFERENCE]

| Source | Content Sourced |
|--------|----------------|
| `notes.md` | Microservices: UI, CRA Rating Results, Scoping, Aggregation; third-party dashboard independence; 2024 single-snapshot architecture |
| `2025_CRA_Training_Transcript.md` | Third-party dashboard daily refresh; Aggregation tab location; data date lock |
| `CRA_Study_Guide.md` | Platform description as CRC; systemic calculation scope |
| `notebook-llm-notes.md` | Component responsibilities derived from methodology detail |

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DEPENDS_ON] | CRA-006 System Context Pack | Sections 3–6 | System context, trust boundaries, integrations |
| [FEEDS] | CRA-008 Data & Flow Inventory | Data Stores section | Data stores CNT-006 defined here referenced there |
| [FEEDS] | CRA-009 Sequence Catalog | Participants | Container IDs used as sequence participants |
| [FEEDS] | CRA-010 Deployment Topology | Container Placements | Container IDs mapped to deployment nodes |
| [GOVERNED_BY] | RULES-001 Documentation Standards | All | Document format |
| [GOVERNED_BY] | Truth & Verification Standards | Sections 1-4 | Verification labels |

---

## 7. Revision History

[TYPE: REFERENCE]

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-12 | Initial | Document created — container names confirmed from source; component decomposition 💡 inferred from methodology detail | Project documentation generation |

---

*TMPL-007 v2.0 — Container & Component Inventory | Document: CRA-007 | Parent: CRA-006 System Context Pack*
*All claims carry verification labels. 💡 denotes inference from methodology documentation. ❓ denotes information not available in source materials.*
