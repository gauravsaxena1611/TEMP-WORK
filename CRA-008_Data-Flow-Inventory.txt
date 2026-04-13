# Compliance Risk Assessment (CRA): Data & Flow Inventory
## Entity Model, Data Stores, and Flow Diagrams — CSI-180236

```yaml
---
# ── RULES-001 STANDARD FIELDS ────────────────────────────────
document_id: "CRA-008 Data & Flow Inventory"
title: "CRA — Data & Flow Inventory"
version: "1.0"
created: "2026-04-12"
status: "Final"
parent_document: "CRA-006 System Context Pack"
template_version_used: "TMPL-008 v2.0"

# ── METHODOLOGY PHASE ─────────────────────────────────────────
methodology_phase: "Phase-4-HighLevel"
design_methodology_ref: "Documentation_Standards.md, Section 3"

# ── SYSTEM BACK-REFERENCE ─────────────────────────────────────
system_id: "SYS-CRA-180236"
system_name: "Compliance Risk Assessment (CRA)"
context_pack_ref: "CRA-006 System Context Pack"
container_inventory_ref: "CRA-007 Container & Component Inventory"

# ── DATA ENTITIES ─────────────────────────────────────────────
entities:
  - id: "ENT-001"
    name: "GAU Assessment"
    description: "The top-level record for one complete compliance risk assessment of a Global Assessment Unit for a given cycle year"
    primary_key: "assessment_id (internal identifier)"
    attributes:
      - name: "assessment_id"
        type: "UUID or internal ID"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "gau_id"
        type: "VARCHAR"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "cycle_year"
        type: "INTEGER"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "data_date"
        type: "DATE"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "status"
        type: "ENUM: Not Started | Draft | Pending Approval | Completed"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "business_activity_rating"
        type: "ENUM: High | Medium | Low"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "preparer_id"
        type: "VARCHAR — internal employee identifier"
        nullable: false
        pii: true
        phi: false
        financial: false
      - name: "approver_id"
        type: "VARCHAR — internal employee identifier"
        nullable: true
        pii: true
        phi: false
        financial: false
      - name: "submitted_at"
        type: "TIMESTAMP"
        nullable: true
        pii: false
        phi: false
        financial: false
      - name: "approved_at"
        type: "TIMESTAMP"
        nullable: true
        pii: false
        phi: false
        financial: false
    data_store_id: "DS-001"
    verification: "💡"

  - id: "ENT-002"
    name: "Publication Risk Rating"
    description: "The systemically calculated Inherent Risk and Control Environment ratings for a specific publication within a specific GAU assessment — the most granular rating unit in the CRA"
    primary_key: "publication_rating_id"
    attributes:
      - name: "publication_id"
        type: "VARCHAR — Regulatory Inventory publication identifier"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "assessment_id"
        type: "FK → ENT-001"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "l2_risk_stripe"
        type: "VARCHAR"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "publication_tier"
        type: "INTEGER 1-5"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "significance_of_impact"
        type: "ENUM: Very High | High | Medium | Low"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "calculated_severity"
        type: "INTEGER 1-5"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "complexity_rating"
        type: "ENUM: High | Medium | Low"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "applicability_rating"
        type: "ENUM: High | Medium | Low"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "ir_publication_tier"
        type: "INTEGER 1-5"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "soc_rating"
        type: "ENUM: Strong | Adequate | Weak | N/A"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "ceff_rating"
        type: "ENUM: Effective | Partially Ineffective | Ineffective"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "ce_publication_rating"
        type: "ENUM: Effective | Partially Ineffective | Ineffective"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "rr_publication_tier"
        type: "INTEGER 1-5"
        nullable: false
        pii: false
        phi: false
        financial: false
    data_store_id: "DS-001"
    verification: "💡"

  - id: "ENT-003"
    name: "L2 Risk Rating"
    description: "The qualitative L2-level rating manually assigned by the Preparer for both Inherent Risk and Control Environment, with mandatory rationale text; the primary qualitative output of assessor SME judgment"
    primary_key: "l2_rating_id"
    attributes:
      - name: "assessment_id"
        type: "FK → ENT-001"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "l2_risk_stripe"
        type: "VARCHAR"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "l1_risk_category"
        type: "ENUM: Market Practices | Customer-Client Protection | Prudential & Regulatory"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "ir_l2_current_rating"
        type: "INTEGER 1-5 (Tier)"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "ir_l2_rationale"
        type: "TEXT — mandatory"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "ce_l2_current_rating"
        type: "ENUM: Effective | Partially Ineffective | Ineffective"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "ce_l2_rationale"
        type: "TEXT — mandatory"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "rr_l2_tier"
        type: "INTEGER 1-5 — systemic, read-only"
        nullable: false
        pii: false
        phi: false
        financial: false
    data_store_id: "DS-001"
    verification: "💡"

  - id: "ENT-004"
    name: "GAU Overall Rating"
    description: "The final rolled-up Inherent Risk tier, Control Environment rating, and Residual Risk tier at the Global Assessment Unit level, plus the mandatory conclusion documentation"
    primary_key: "assessment_id (FK → ENT-001)"
    attributes:
      - name: "overall_ir_tier"
        type: "INTEGER 1-5"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "overall_ce_rating"
        type: "ENUM: Effective | Partially Ineffective | Ineffective"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "overall_rr_tier"
        type: "INTEGER 1-5 — systemic, read-only"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "business_profile"
        type: "TEXT — mandatory conclusion field"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "ir_rating_themes"
        type: "TEXT — mandatory conclusion field"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "ce_rating_themes"
        type: "TEXT — mandatory conclusion field"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "rr_commentary"
        type: "TEXT — mandatory conclusion field"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "monitoring_testing_recommendation"
        type: "TEXT — mandatory conclusion field"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "legal_entity_considerations"
        type: "TEXT — optional"
        nullable: true
        pii: false
        phi: false
        financial: false
      - name: "other_recommendations"
        type: "TEXT — optional"
        nullable: true
        pii: false
        phi: false
        financial: false
    data_store_id: "DS-001"
    verification: "✅"

  - id: "ENT-005"
    name: "Mapped Issue"
    description: "An open issue from ICAPS that has been systemically mapped to a publication-GAU pair based on obligation IDs; forms the basis of the Control Effectiveness (CEff) waterfall calculation"
    primary_key: "issue_mapping_id"
    attributes:
      - name: "icaps_issue_id"
        type: "VARCHAR — ICAPS system identifier"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "assessment_id"
        type: "FK → ENT-001"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "publication_id"
        type: "VARCHAR"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "l2_risk_stripe"
        type: "VARCHAR"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "issue_severity"
        type: "ENUM — severity level from ICAPS"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "mapping_source"
        type: "ENUM: System-Mapped | Assessor-Added"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "is_tagged"
        type: "BOOLEAN — true = included in qualitative assessment; false = untagged by assessor"
        nullable: false
        pii: false
        phi: false
        financial: false
    data_store_id: "DS-001"
    verification: "✅"

  - id: "ENT-006"
    name: "Upstream Data Snapshot"
    description: "The local copy of data extracted from MCA, Regulatory Inventory (including publication tiers and obligation mappings), and ICAPS at the annual data date; provides the consistent read-only foundation for all CRA calculations"
    primary_key: "snapshot_id + source_system + data_date"
    attributes:
      - name: "snapshot_date"
        type: "DATE — October 13, 2025 for 2025 cycle"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "source_system"
        type: "ENUM: MCA | RegInventory | ICAPS | PARCUM"
        nullable: false
        pii: false
        phi: false
        financial: false
      - name: "data_payload"
        type: "Structured data — schema per source system"
        nullable: false
        pii: false
        phi: false
        financial: false
    data_store_id: "DS-002"
    verification: "✅"

# ── ENTITY RELATIONSHIPS ──────────────────────────────────────
relationships:
  - id: "REL-001"
    from_entity: "ENT-001"
    to_entity: "ENT-002"
    cardinality: "1:N"
    label: "contains"
    relationship_type: "identifying"

  - id: "REL-002"
    from_entity: "ENT-001"
    to_entity: "ENT-003"
    cardinality: "1:N"
    label: "includes"
    relationship_type: "identifying"

  - id: "REL-003"
    from_entity: "ENT-001"
    to_entity: "ENT-004"
    cardinality: "1:1"
    label: "produces"
    relationship_type: "identifying"

  - id: "REL-004"
    from_entity: "ENT-001"
    to_entity: "ENT-005"
    cardinality: "1:N"
    label: "has mapped issues"
    relationship_type: "non-identifying"

  - id: "REL-005"
    from_entity: "ENT-002"
    to_entity: "ENT-003"
    cardinality: "N:1"
    label: "informs"
    relationship_type: "non-identifying"

  - id: "REL-006"
    from_entity: "ENT-006"
    to_entity: "ENT-002"
    cardinality: "1:N"
    label: "provides data for calculation"
    relationship_type: "non-identifying"

# ── DATA STORES ───────────────────────────────────────────────
data_stores:
  - id: "DS-001"
    name: "CRA Assessment Store"
    container_id: "CNT-002"
    type: "relational"
    technology: "On-premise relational database — technology not disclosed"
    entities_stored: ["ENT-001", "ENT-002", "ENT-003", "ENT-004", "ENT-005"]
    data_classifications: ["internal", "internal-PII"]
    retention_policy: "❓ Exact retention period not specified; historical assessment data retained per prior cycle access noted in training"
    backup_policy: "❓ Not specified; CTI infrastructure standards apply"
    encryption_at_rest: "Per Citi CTI standards"
    encryption_in_transit: "Per Citi CTI standards"
    owner_team: "GFT (Technology Team)"
    verification: "💡"

  - id: "DS-002"
    name: "CRA Upstream Data Snapshot Store"
    container_id: "CNT-006"
    type: "relational"
    technology: "On-premise store — technology not disclosed; on CLMWAMLAI01P (PROD)"
    entities_stored: ["ENT-006"]
    data_classifications: ["internal"]
    retention_policy: "Annual cycle; previous snapshot retained until next data date"
    backup_policy: "❓ Not specified"
    encryption_at_rest: "Per Citi CTI standards"
    encryption_in_transit: "Per Citi CTI standards"
    owner_team: "GFT (Technology Team)"
    verification: "✅"

  - id: "DS-003"
    name: "Third-Party Dashboard Store"
    container_id: "CNT-005"
    type: "relational"
    technology: "On-premise — technology not disclosed; refreshed daily from MCA"
    entities_stored: []
    data_classifications: ["internal"]
    retention_policy: "Daily rolling — overwritten on each refresh"
    backup_policy: "❓ Not specified"
    encryption_at_rest: "Per Citi CTI standards"
    encryption_in_transit: "Per Citi CTI standards"
    owner_team: "GFT (Technology Team)"
    verification: "✅"

# ── DATA FLOWS ────────────────────────────────────────────────
flows:
  - id: "FLW-001"
    name: "Annual Upstream Data Extract (Snapshot)"
    from_id: "INT-002"
    from_type: "external-system"
    to_id: "DS-002"
    to_type: "data-store"
    data_description: "MCA data: Significance of Impact ratings, control design attributes, applicability/obligation mappings, GAU mapping"
    data_classification: "internal"
    protocol: "Internal batch extract"
    frequency: "Annual — data date only (October 13, 2025)"
    volume: "❓ Volume not specified"
    dfd_level: "L0"
    verification: "✅"

  - id: "FLW-002"
    name: "Annual Regulatory Inventory Extract (Snapshot)"
    from_id: "INT-005"
    from_type: "external-system"
    to_id: "DS-002"
    to_type: "data-store"
    data_description: "Publication tiers, obligation mappings to L2 compliance risks, GAU obligation assignments"
    data_classification: "internal"
    protocol: "Internal batch extract"
    frequency: "Annual — data date only"
    volume: "❓ Not specified"
    dfd_level: "L0"
    verification: "✅"

  - id: "FLW-003"
    name: "Annual ICAPS Issues Extract (Snapshot)"
    from_id: "INT-004"
    from_type: "external-system"
    to_id: "DS-002"
    to_type: "data-store"
    data_description: "All open issues at data date, with severity, obligation IDs, and GAU-publication pairs"
    data_classification: "internal"
    protocol: "Internal batch extract"
    frequency: "Annual — data date only"
    volume: "❓ Not specified"
    dfd_level: "L0"
    verification: "✅"

  - id: "FLW-004"
    name: "PARCUM Process & Dependency Data Extract"
    from_id: "INT-015"
    from_type: "external-system"
    to_id: "DS-002"
    to_type: "data-store"
    data_description: "Number of distinct processes per GAU/publication; ratio of external dependencies for Complexity calculation"
    data_classification: "internal"
    protocol: "Internal extract"
    frequency: "Annual — data date"
    volume: "❓ Not specified"
    dfd_level: "L0"
    verification: "✅"

  - id: "FLW-005"
    name: "Preparer — Start Assessment & Assign Business Activity"
    from_id: "ACT-001"
    from_type: "actor"
    to_id: "CNT-001"
    to_type: "process"
    data_description: "Business Activity rating (High/Medium/Low); GAU identifier"
    data_classification: "internal"
    protocol: "HTTPS — CRC web interface"
    frequency: "Once per assessment per GAU per cycle"
    volume: "❓ Number of GAUs not specified"
    dfd_level: "L0"
    verification: "✅"

  - id: "FLW-006"
    name: "Preparer — L2/L1 Qualitative Ratings and Rationale Submission"
    from_id: "ACT-001"
    from_type: "actor"
    to_id: "CNT-001"
    to_type: "process"
    data_description: "L2 and L1 IR/CE current ratings; mandatory rationale text for each L2; optional L1 qualitative override"
    data_classification: "internal"
    protocol: "HTTPS — CRC web interface"
    frequency: "Multiple times per assessment during Draft phase"
    volume: "❓ Not specified"
    dfd_level: "L1"
    verification: "✅"

  - id: "FLW-007"
    name: "Preparer — Conclusion Section Submission"
    from_id: "ACT-001"
    from_type: "actor"
    to_id: "CNT-001"
    to_type: "process"
    data_description: "Business Profile; IR/CE Themes; RR Commentary; Monitoring & Testing Recommendation; optional Legal Entity and Other fields"
    data_classification: "internal"
    protocol: "HTTPS — CRC web interface"
    frequency: "Once per assessment at completion"
    volume: "❓ Not specified"
    dfd_level: "L1"
    verification: "✅"

  - id: "FLW-008"
    name: "Calculated Publication Ratings — Display to Preparer"
    from_id: "CNT-002"
    from_type: "process"
    to_id: "ACT-001"
    to_type: "actor"
    data_description: "Publication-level IR tiers; CE ratings (SOC + CEff); L2 quantitative summaries; Residual Risk tiers at all levels"
    data_classification: "internal"
    protocol: "HTTPS — CRC web interface"
    frequency: "On each Save action; on assessment open"
    volume: "❓ Not specified"
    dfd_level: "L1"
    verification: "✅"

  - id: "FLW-009"
    name: "Approver — Review and Decision"
    from_id: "ACT-002"
    from_type: "actor"
    to_id: "CNT-001"
    to_type: "process"
    data_description: "Approve action (→ Completed) or Return action (→ Draft with comments)"
    data_classification: "internal-PII"
    protocol: "HTTPS — CRC web interface"
    frequency: "Once per assessment submission"
    volume: "❓ Not specified"
    dfd_level: "L0"
    verification: "✅"

  - id: "FLW-010"
    name: "CRA Results — Downstream Governance Reporting"
    from_id: "CNT-007"
    from_type: "process"
    to_id: "ACT-006"
    to_type: "actor"
    data_description: "Aggregated risk ratings by GAU, country, region, global business; State of Risk report content; BRCC presentation materials"
    data_classification: "internal"
    protocol: "Report / presentation — SharePoint and governance meetings"
    frequency: "Annual — post-cycle completion"
    volume: "❓ Not specified"
    dfd_level: "L0"
    verification: "✅"

  - id: "FLW-011"
    name: "CRA Results — CIA Testing Plan Feed"
    from_id: "CNT-007"
    from_type: "process"
    to_id: "ACT-003"
    to_type: "actor"
    data_description: "Residual risk ratings; Monitoring & Testing recommendation fields per GAU"
    data_classification: "internal"
    protocol: "Internal report / platform"
    frequency: "Annual — post-cycle"
    volume: "❓ Not specified"
    dfd_level: "L0"
    verification: "✅"

  - id: "FLW-012"
    name: "CRA Results — GCB Xceptor RPA Feed"
    from_id: "CNT-007"
    from_type: "process"
    to_id: "INT-014"
    to_type: "external-system"
    data_description: "CRA risk rating results consumed by RPA platform for downstream automation"
    data_classification: "internal"
    protocol: "Internal RPA feed"
    frequency: "Annual — post-cycle"
    volume: "❓ Not specified"
    dfd_level: "L0"
    verification: "✅"

  - id: "FLW-013"
    name: "Third-Party Dashboard — Daily MCA Refresh"
    from_id: "INT-002"
    from_type: "external-system"
    to_id: "DS-003"
    to_type: "data-store"
    data_description: "Daily updated MCA data for third-party risk profiles; independent of annual CRA snapshot"
    data_classification: "internal"
    protocol: "Internal daily extract"
    frequency: "Daily"
    volume: "❓ Not specified"
    dfd_level: "L0"
    verification: "✅"

# ── TRANSFORMATIONS ───────────────────────────────────────────
transformations:
  - id: "TRF-001"
    name: "Inherent Risk Publication Calculation"
    container_id: "CNT-002"
    inputs: ["FLW-001", "FLW-002", "FLW-004"]
    outputs: ["FLW-008"]
    description: "Aggregates Significance of Impact from MCA (4 factors → 1 score), applies severity tier adjustment, aggregates Business Activity + Complexity + Applicability to overall likelihood, produces publication-level IR tier"
    pii_handling: "N/A — no PII in calculation inputs"

  - id: "TRF-002"
    name: "Control Environment Publication Calculation"
    container_id: "CNT-002"
    inputs: ["FLW-001", "FLW-003"]
    outputs: ["FLW-008"]
    description: "Runs SOC decision tree on MCA design attributes (preventative/detective, automated/manual, frequency, missing controls) → Strong/Adequate/Weak; runs CEff waterfall on issue count × severity → CE rating; combines to publication CE rating"
    pii_handling: "N/A — no PII in calculation inputs"

  - id: "TRF-003"
    name: "Residual Risk GRC Matrix Lookup"
    container_id: "CNT-002"
    inputs: ["FLW-006"]
    outputs: ["FLW-008"]
    description: "Intersects IR Tier (1-5) with CE rating (Effective/Partially Ineffective/Ineffective) using the MCA GRC Residual Risk Matrix to produce Residual Risk Tier at publication, L2, L1, and GAU levels; outputs are read-only"
    pii_handling: "N/A"

  - id: "TRF-004"
    name: "L2 → L1 → GAU Rating Aggregation"
    container_id: "CNT-002"
    inputs: ["FLW-006"]
    outputs: ["FLW-008"]
    description: "Applies CE highest-deficiency waterfall for CE roll-up; applies qualitative Preparer overrides at L2/L1 for IR; produces final L1 and overall GAU IR, CE, and RR ratings"
    pii_handling: "N/A"

  - id: "TRF-005"
    name: "Aggregation to Country / Region / Global"
    container_id: "CNT-004"
    inputs: ["FLW-008"]
    outputs: ["FLW-010", "FLW-011"]
    description: "Risk-weighted average aggregation of IR ratings and CE roll-up across GAUs to country, regional, and global business levels; feeds governance reporting"
    pii_handling: "N/A — aggregated data only"

# ── PII INVENTORY ─────────────────────────────────────────────
pii_inventory:
  - entity_id: "ENT-001"
    attribute: "preparer_id"
    classification: "Internal PII"
    purpose: "Maker attribution — identifies which employee initiated and submitted the assessment"
    legal_basis: "Contract / employment relationship — internal compliance process"
    retention: "❓ Not specified; retained with assessment record"
    deletion_mechanism: "❓ Not defined in source materials"

  - entity_id: "ENT-001"
    attribute: "approver_id"
    classification: "Internal PII"
    purpose: "Checker attribution — identifies which employee approved the assessment"
    legal_basis: "Contract / employment relationship — internal compliance process"
    retention: "❓ Not specified; retained with assessment record"
    deletion_mechanism: "❓ Not defined in source materials"

# ── LIVING CONTEXT ────────────────────────────────────────────
living_context:
  last_verified: "2026-04-12"
  last_verified_by: "ai-documentation skill — synthesised from project source documents"
  drift_check_date: "2026-04-12"
  drift_status: "clean"
  linked_commit: ""
  linked_adr: []
  next_review_trigger: "Annual CRA methodology change or schema modification"
  schema_migration_ref: "❓ Not specified — on-premise CTI database"

# ── AI-OPTIMISATION FIELDS ────────────────────────────────────
intent: >
  Enable the generating-architecture-diagrams skill and compliance analysts
  to understand all CRA data entities, stores, flows, and PII classifications
  through a verified, structured inventory supporting DFD and ERD generation.

consumption_context:
  - ai-reasoning
  - rag-retrieval
  - agentic-execution
  - human-reading

triggers:
  - "CRA data flow diagram"
  - "CRA entity relationship diagram"
  - "what data does CRA store"
  - "CRA PII inventory"
  - "CRA data architecture"

confidence_overall: "medium-high"
confidence_note: >
  Entity attributes and relationships are 💡 inferred from detailed methodology
  descriptions (mandatory fields, rating scales, calculation inputs/outputs).
  Data flows are ✅ confirmed from workflow and training source documents.
  Exact database schema, retention policies, and deletion mechanisms are ❓
  not disclosed in source materials.
---
```

---

> ## 🤖 AI Summary
> **System:** CRA CSI-180236 — Data & Flow Inventory
> **Core Purpose:** Entity model, data stores, data flows, and PII inventory for the CRA assessment platform.
> **Entity Count:** 6 | **Data Store Count:** 3 | **Flow Count:** 13 (8 L0, 5 L1)
> **PII Data Present:** Yes — Internal PII (preparer_id, approver_id in ENT-001)
> **Confidence:** Entity attributes 💡 inferred from methodology; flows ✅ confirmed from workflow documents
> **Living-Context Status:** Last verified 2026-04-12 | Drift: clean

---

## TABLE OF CONTENTS

1. [Data Entity Model](#1-data-entity-model)
2. [Data Stores](#2-data-stores)
3. [Data Flows — Level 0 (Context)](#3-data-flows--level-0-context)
4. [Data Flows — Level 1 (Process Detail)](#4-data-flows--level-1-process-detail)
5. [Transformations & Processing](#5-transformations--processing)
6. [PII & Sensitive Data Inventory](#6-pii--sensitive-data-inventory)
7. [Diagram Generation Notes](#7-diagram-generation-notes)
8. [Sources & Cross-References](#8-sources--cross-references)
9. [Revision History](#9-revision-history)

---

## 1. Data Entity Model

[TYPE: REFERENCE]

The CRA data model centres on the **GAU Assessment** as the root aggregate, with Publication Risk Ratings and L2 Risk Ratings as its primary children. Mapped Issues from ICAPS feed the Control Effectiveness calculation. The Upstream Data Snapshot is a distinct store holding the annual point-in-time copy of all source system data.

### 1.1 Entity Summary

| ID | Entity | Primary Key | Data Store | PII Fields | Verification |
|----|--------|-------------|------------|-----------|-------------|
| ENT-001 | GAU Assessment | assessment_id | DS-001 (Assessment Store) | preparer_id, approver_id | 💡 |
| ENT-002 | Publication Risk Rating | publication_rating_id | DS-001 | None | 💡 |
| ENT-003 | L2 Risk Rating | l2_rating_id | DS-001 | None | 💡 |
| ENT-004 | GAU Overall Rating | assessment_id (FK) | DS-001 | None | ✅ |
| ENT-005 | Mapped Issue | issue_mapping_id | DS-001 | None | ✅ |
| ENT-006 | Upstream Data Snapshot | snapshot_id + source + date | DS-002 | None | ✅ |

### 1.2 Entity Relationships

| ID | From | To | Cardinality | Label | Verification |
|----|------|----|-------------|-------|-------------|
| REL-001 | ENT-001 GAU Assessment | ENT-002 Publication Risk Rating | 1:N | contains | 💡 |
| REL-002 | ENT-001 GAU Assessment | ENT-003 L2 Risk Rating | 1:N | includes | 💡 |
| REL-003 | ENT-001 GAU Assessment | ENT-004 GAU Overall Rating | 1:1 | produces | ✅ |
| REL-004 | ENT-001 GAU Assessment | ENT-005 Mapped Issue | 1:N | has mapped issues | ✅ |
| REL-005 | ENT-002 Publication Risk Rating | ENT-003 L2 Risk Rating | N:1 | informs | 💡 |
| REL-006 | ENT-006 Upstream Snapshot | ENT-002 Publication Risk Rating | 1:N | provides data for | ✅ |

### 1.3 Entity Descriptions

**ENT-001 — GAU Assessment:** 💡 The master record for a single GAU's annual compliance risk assessment cycle. Tracks the full lifecycle from Not Started through Draft, Pending Approval, to Completed. Stores the data date (single snapshot date), Business Activity rating, and maker/checker identity (Internal PII). One per GAU per cycle year.

**ENT-002 — Publication Risk Rating:** 💡 The most granular rating unit in the system. One record exists per publication × L2 risk stripe combination within a GAU assessment. All fields are systemically calculated and read-only to assessors. The foundation from which qualitative L2 ratings are informed.

**ENT-003 — L2 Risk Rating:** 💡 Stores the Preparer's qualitative assessment at the Level 2 compliance risk stripe for both Inherent Risk and Control Environment. The mandatory rationale text fields in this entity are the primary record of SME judgment in the assessment.

**ENT-004 — GAU Overall Rating:** ✅ The final aggregated output record of a completed assessment. Contains the overall IR tier, CE rating, and Residual Risk tier (all systemic), plus all five mandatory conclusion text fields and two optional fields. This is the primary record consumed by downstream governance reporting.

**ENT-005 — Mapped Issue:** ✅ Each open ICAPS issue mapped to a publication-GAU pair via obligation IDs. The `mapping_source` attribute distinguishes system-mapped issues from assessor-added ones. The `is_tagged` boolean tracks assessor untagging decisions — though these do not affect systemic calculations, they inform the qualitative record.

**ENT-006 — Upstream Data Snapshot:** ✅ The local copy of all upstream system data at the annual data date. Structured by source system and date. Provides the consistent, immutable data foundation for the entire assessment window. Eliminates real-time dependency on MCA, Reg Inventory, ICAPS, and PARCUM during assessments.

---

## 2. Data Stores

[TYPE: REFERENCE]

| ID | Store Name | Type | Technology | Classifications | Owner Team | Verification |
|----|------------|------|------------|----------------|------------|-------------|
| DS-001 | CRA Assessment Store | relational | On-premise (CTI) — not disclosed | Internal, Internal-PII | GFT | 💡 |
| DS-002 | CRA Upstream Data Snapshot Store | relational | On-premise (CTI) — CLMWAMLAI01P PROD | Internal | GFT | ✅ |
| DS-003 | Third-Party Dashboard Store | relational | On-premise (CTI) | Internal | GFT | ✅ |

### 2.1 Storage Architecture Notes

The CRA storage architecture follows a **read-authority pattern**: DS-002 (Snapshot Store) is the authoritative source for all calculation inputs, written once annually and read-only thereafter. ✅ DS-001 (Assessment Store) is the read-write operational store for in-flight assessments. DS-003 (Third-Party Dashboard Store) operates on a separate daily refresh cycle entirely independent of the annual snapshot. ✅

All three stores are on-premise within CTI infrastructure, classifying their data as Internal. DS-001 additionally holds Internal PII (assessor/approver identities). ✅ Exact technology choices (database engine, version) are not disclosed in source materials. ❓

---

## 3. Data Flows — Level 0 (Context)

[TYPE: RESEARCH_FINDING]

L0 shows the CRA system as a single process, exchanging data only with external actors and systems.

| ID | From | To | Data Description | Classification | Frequency | Verification |
|----|------|----|-----------------|----------------|-----------|-------------|
| FLW-001 | MCA (INT-002) | CRA System | Significance of Impact, control design attributes, obligation mappings | Internal | Annual — data date | ✅ |
| FLW-002 | Reg Inventory (INT-005) | CRA System | Publication tiers, obligation mappings, GAU assignments | Internal | Annual — data date | ✅ |
| FLW-003 | ICAPS (INT-004) | CRA System | Open issues, severity, obligation IDs | Internal | Annual — data date | ✅ |
| FLW-004 | PARCUM (INT-015) | CRA System | Process counts, external dependency ratios | Internal | Annual — data date | ✅ |
| FLW-005 | Preparer (ACT-001) | CRA System | Business Activity rating, qualitative ratings, rationale, conclusion | Internal | Assessment window | ✅ |
| FLW-008 | CRA System | Preparer (ACT-001) | Calculated publication and L2/L1/GAU ratings | Internal | On-demand | ✅ |
| FLW-009 | Approver (ACT-002) | CRA System | Approve or Return decision | Internal-PII | Per submission | ✅ |
| FLW-010 | CRA System | Senior Mgmt / BRCC / Board (ACT-006) | Aggregated reports, State of Risk | Internal | Annual post-cycle | ✅ |
| FLW-011 | CRA System | ICRM / CIA (ACT-003) | Risk ratings, Recommendation fields | Internal | Annual post-cycle | ✅ |
| FLW-012 | CRA System | GCB Xceptor RPA (INT-014) | CRA risk results for downstream automation | Internal | Annual post-cycle | ✅ |
| FLW-013 | MCA (INT-002) | Third-Party Dashboard | MCA data for third-party risk profiles | Internal | Daily | ✅ |

---

## 4. Data Flows — Level 1 (Process Detail)

[TYPE: RESEARCH_FINDING]

L1 decomposes the CRA into its core processes showing internal data movement.

| ID | From | To | Data Description | Volume / Frequency | Verification |
|----|------|----|-----------------|-------------------|-------------|
| FLW-006 | Preparer (ACT-001) → CNT-001 | CNT-002 | L2/L1 qualitative ratings, rationale text, Business Activity | Per save during assessment | ✅ |
| FLW-007 | Preparer (ACT-001) → CNT-001 | DS-001 | Conclusion fields (mandatory + optional) | Once per assessment at completion | ✅ |
| DS-002 → CNT-002 | CNT-002 | DS-001 | Snapshot data → Calculated ratings | On calculation trigger | ✅ |
| CNT-002 → DS-001 | DS-001 | CNT-007 | Final ratings → Report generation | Post-approval | 💡 |
| CNT-004 (Aggregation) | CNT-007 | DS-001 (aggregated) | Rolled-up country/region/global ratings | Post-calibration | 💡 |

---

## 5. Transformations & Processing

[TYPE: REFERENCE]

| ID | Process Name | Container | Key Inputs | Output | PII Handling | Verification |
|----|-------------|-----------|-----------|--------|-------------|-------------|
| TRF-001 | Inherent Risk Publication Calculation | CNT-002 | MCA SoI, Reg Inventory tier, PARCUM complexity, Applicability | Publication-level IR tier (1-5) | N/A | 💡 |
| TRF-002 | Control Environment Publication Calculation | CNT-002 | MCA design attributes (SOC decision tree), ICAPS issues (CEff waterfall) | Publication-level CE rating | N/A | 💡 |
| TRF-003 | Residual Risk GRC Matrix Lookup | CNT-002 | IR Tier + CE rating | RR Tier (read-only) at pub/L2/L1/GAU | N/A | ✅ |
| TRF-004 | L2 → L1 → GAU Aggregation | CNT-002 | Publication ratings + Preparer L2 qualitative overrides | L1 and overall GAU IR/CE/RR ratings | N/A | 💡 |
| TRF-005 | Cross-GAU Aggregation | CNT-004 | Individual GAU ratings post-calibration | Country/Region/Global aggregated ratings | N/A | 💡 |

---

## 6. PII & Sensitive Data Inventory

[TYPE: RESEARCH_FINDING]

The CRA processes a limited but defined set of Internal PII, exclusively tied to maker/checker identity attribution within the assessment platform. ✅

| Entity | Attribute | Classification | Purpose | Legal Basis | Retention | Deletion | Verification |
|--------|-----------|----------------|---------|------------|-----------|---------|-------------|
| ENT-001 | preparer_id | Internal PII | Maker attribution — audit trail | Employment / internal compliance obligation | ❓ With assessment record | ❓ Not defined | ✅ |
| ENT-001 | approver_id | Internal PII | Checker attribution — audit trail | Employment / internal compliance obligation | ❓ With assessment record | ❓ Not defined | ✅ |

### 6.1 PII Risk Summary

The PII surface area of the CRA is **deliberately narrow** — only internal employee identifiers used for maker/checker attribution. ✅ No customer PII, financial PII, or sensitive personal data enters the CRA assessment data model. The CRA_Dependencies.md source confirms that while the application processes Internal PII, this is specifically "Internal employee PII" for assessor identity tracking within the compliance governance process. ✅

**Known gaps:** Retention period and deletion mechanism for preparer_id and approver_id are not specified in available source materials. ❓ These should be confirmed with the GFT Application Manager and ICRM Methodology Team against Citi's internal data retention policies.

---

## 7. Diagram Generation Notes

[TYPE: REFERENCE]

**DFD L0 Generation:**
- Source: `flows[]` filtered by `dfd_level: L0`
- System boundary: label as "CRA Application (CSI-180236)"
- External entities (left): MCA, Reg Inventory, ICAPS, PARCUM, Preparer, Approver
- External entities (right): Senior Mgmt/Board, CIA/ICRM, GCB Xceptor RPA
- Third-Party Dashboard shown as sub-process with its own MCA arrow (daily refresh)

**DFD L1 Generation:**
- Source: `flows[]` filtered by `dfd_level: L1`
- Processes: CNT-001 (UI), CNT-002 (Rating Results), CNT-003 (Scoping), CNT-004 (Aggregation), CNT-005 (Third-Party Dashboard), CNT-007 (Reporting)
- Data stores: DS-001, DS-002, DS-003
- Highlight the snapshot boundary: DS-002 with "Annual Write / Read-Only During Assessment" annotation

**ERD Generation:**
- Source: `entities[]` and `relationships[]`
- Root aggregate: ENT-001 (GAU Assessment) at centre
- Group ENT-002 and ENT-003 as children of ENT-001
- ENT-006 (Snapshot) shown in a separate "Upstream Data" bounded context
- Flag PII attributes (preparer_id, approver_id) with 🔒 indicator

---

## DIAGRAM-READY QUALITY GATE

```
[✅] entities[] — 6 entities defined with primary keys and attributes
[✅] data_stores[] — 3 stores; each references container_id from TMPL-007
[✅] flows[] — 13 flows covering full L0 level
[✅] pii_inventory[] — populated; 2 PII attributes documented
[✅] Every entity, store, and flow carries a verification label
[✅] living_context.last_verified populated
[⚠️] living_context.schema_migration_ref ❓ — on-premise CTI; not disclosed
[✅] Parent CRA-006 System Context Pack referenced
[⚠️] Retention and deletion mechanisms ❓ — not confirmed in source materials
```

---

## 8. Sources & Cross-References

[TYPE: REFERENCE]

| Source | Content Sourced |
|--------|----------------|
| `CRA_Study_Guide.md` | Entity attributes derived from mandatory rating scales, score definitions, assessment output descriptions |
| `2025_CRA_Training_Transcript.md` | Mandatory conclusion fields confirmed; issue mapping logic; data stores; flows |
| `CRA_Workflows.md` | Data flows for each workflow phase; maker/checker data exchanges |
| `CRA_Dependencies.md` | PII classification (Internal employee PII); data governance standards |
| `notebook-llm-notes.md` | Data flow analysis — upstream consumption modes; downstream usage |

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DEPENDS_ON] | CRA-006 System Context Pack | data_classifications | System-level data classification summary |
| [DEPENDS_ON] | CRA-007 Container & Component Inventory | containers[] | Data store container references (CNT-002, CNT-005, CNT-006) |
| [FEEDS] | CRA-009 Sequence Catalog | Steps | Entity IDs referenced in sequence interaction data payloads |
| [GOVERNED_BY] | RULES-001 Documentation Standards | All | Document format |
| [GOVERNED_BY] | Truth & Verification Standards | Sections 1-4 | Verification labels |

---

## 9. Revision History

[TYPE: REFERENCE]

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-12 | Initial | Document created — entities and flows synthesised from methodology and workflow source documents; attributes 💡 inferred | Project documentation generation |

---

*TMPL-008 v2.0 — Data & Flow Inventory | Document: CRA-008 | Parent: CRA-006 System Context Pack*
*All claims carry verification labels. 💡 denotes inference from methodology documentation. ❓ denotes information not available.*
