# Compliance Risk Assessment (CRA): System Context Pack
## Canonical Architecture Reference — CSI-180236

```yaml
---
# ── RULES-001 STANDARD FIELDS ───────────────────────────────
document_id: "CRA-006 System Context Pack"
title: "Compliance Risk Assessment (CRA) — System Context Pack"
version: "1.0"
created: "2026-04-12"
status: "Final"
parent_document: "RULES-001 Documentation Standards"
template_version_used: "TMPL-006 v2.0"

# ── METHODOLOGY PHASE ───────────────────────────────────────
methodology_phase: "Phase-7-Documentation"
design_methodology_ref: "[Documentation Standards, Section 3]"

# ── SYSTEM IDENTITY ──────────────────────────────────────────
system:
  name: "CRA"
  full_name: "Compliance Risk Assessment Application"
  system_id: "SYS-CRA-180236"
  csi_id: "CSI-180236"
  domain: "Compliance Risk Management"
  capability: "Systematically identifies, assesses, and distributes compliance risk ratings (Inherent, Control Environment, Residual) for all applicable Citi business units via the Citi Risk & Controls (CRC) platform"
  system_type: "enterprise-risk-platform"
  lifecycle_phase: "active"
  criticality: "business-critical"

# ── ACTORS ───────────────────────────────────────────────────
actors:
  - id: "ACT-001"
    name: "Preparer (Business / Compliance Analyst)"
    type: "human"
    description: "Initiates, rates, and documents the CRA assessment; assigns business activity rating and qualitative L2/L1 ratings; mandatory rationale author"
    trust_level: "trusted"

  - id: "ACT-002"
    name: "Approver (Senior Compliance Manager)"
    type: "human"
    description: "Reviews, challenges, and approves completed assessment submissions; moves assessment to Completed or returns to Preparer"
    trust_level: "trusted"

  - id: "ACT-003"
    name: "ICRM Methodology Team"
    type: "human"
    description: "Operational administrators of the CRA process and CRC platform; manages entity universe, data validation, scheduling, and aggregated reporting"
    trust_level: "trusted"

  - id: "ACT-004"
    name: "Enterprise Compliance & Compliance Assurance Programs"
    type: "human"
    description: "Primary Makers and Checkers; subject-matter experts who define methodology, perform core risk/control analysis, and initiate qualitative overrides"
    trust_level: "trusted"

  - id: "ACT-005"
    name: "Technology Team (GFT)"
    type: "human"
    description: "Develops and maintains the CRA application; runs data extraction jobs; manages DEV/UAT/PROD environments"
    trust_level: "trusted"

  - id: "ACT-006"
    name: "Senior Management & Governance Committees (BRCC / GBRCC)"
    type: "human"
    description: "Primary consumers of aggregated CRA results; set risk appetite and make strategic resource-allocation decisions"
    trust_level: "trusted"

  - id: "ACT-007"
    name: "Internal Audit"
    type: "human"
    description: "Third line of defence; provides audit findings as control environment inputs; uses CRA results for audit-plan risk-based prioritisation"
    trust_level: "trusted"

  - id: "ACT-008"
    name: "Business Teams"
    type: "human"
    description: "First line of defence; validate data accuracy for their assessment entity; evidence control effectiveness; develop corrective action plans (CAPs)"
    trust_level: "trusted"

# ── EXTERNAL INTEGRATIONS ────────────────────────────────────
external_integrations:
  - id: "INT-001"
    name: "CRC-Compliance Risk Management (CSI-176046)"
    type: "database"
    direction: "inbound"
    protocol: "internal-platform"
    data_classification: "internal"
    sla_dependency: "required"
    owner_team: "GFT / ICRM"

  - id: "INT-002"
    name: "CRC-Managers Control Assessment / MCA (CSI-176101)"
    type: "database"
    direction: "inbound"
    protocol: "internal-platform"
    data_classification: "internal"
    sla_dependency: "required"
    owner_team: "GFT / First Line"

  - id: "INT-003"
    name: "Compliance Data Engine (CSI-169477)"
    type: "api"
    direction: "inbound"
    protocol: "internal"
    data_classification: "internal"
    sla_dependency: "required"
    owner_team: "GFT"

  - id: "INT-004"
    name: "CRC-Integrated Corrective Action Plan System / ICAPS (CSI-176041)"
    type: "database"
    direction: "inbound"
    protocol: "internal-platform"
    data_classification: "internal"
    sla_dependency: "required"
    owner_team: "GFT"

  - id: "INT-005"
    name: "Policy Management & Mapping — Regulatory Inventory (CSI-179911)"
    type: "database"
    direction: "inbound"
    protocol: "internal"
    data_classification: "internal"
    sla_dependency: "required"
    owner_team: "ICRM"

  - id: "INT-006"
    name: "Fair Lending Risk Assessment (CSI-179912)"
    type: "api"
    direction: "inbound"
    protocol: "internal"
    data_classification: "internal"
    sla_dependency: "required"
    owner_team: "ICRM"

  - id: "INT-007"
    name: "Business Activity Warehouse (CSI-153099)"
    type: "database"
    direction: "inbound"
    protocol: "internal"
    data_classification: "internal"
    sla_dependency: "required"
    owner_team: "GFT"

  - id: "INT-008"
    name: "Global Sanctions Risk Management (CSI-154357)"
    type: "api"
    direction: "inbound"
    protocol: "internal"
    data_classification: "internal"
    sla_dependency: "optional"
    owner_team: "ICRM"

  - id: "INT-009"
    name: "AML-NS Cross Sector Monitoring NAM (CSI-162404)"
    type: "api"
    direction: "inbound"
    protocol: "internal"
    data_classification: "internal"
    sla_dependency: "optional"
    owner_team: "AML"

  - id: "INT-010"
    name: "Pearl (CSI-160992)"
    type: "database"
    direction: "inbound"
    protocol: "internal"
    data_classification: "internal"
    sla_dependency: "optional"
    owner_team: "GFT"

  - id: "INT-011"
    name: "SPIDER Securities Position Disclosure & Enterprise Reporting (CSI-161668)"
    type: "database"
    direction: "inbound"
    protocol: "internal"
    data_classification: "internal"
    sla_dependency: "optional"
    owner_team: "GFT"

  - id: "INT-012"
    name: "Olympus / Best Execution Extraction (CSI-167969/168868)"
    type: "database"
    direction: "inbound"
    protocol: "internal"
    data_classification: "internal"
    sla_dependency: "optional"
    owner_team: "GFT"

  - id: "INT-013"
    name: "Reg-Rep LOPR Large Options Position Reporting (CSI-1183)"
    type: "database"
    direction: "inbound"
    protocol: "internal"
    data_classification: "internal"
    sla_dependency: "optional"
    owner_team: "GFT"

  - id: "INT-014"
    name: "GCB Xceptor RPA APAC and EMEA (CSI-173877)"
    type: "api"
    direction: "outbound"
    protocol: "internal-rpa"
    data_classification: "internal"
    sla_dependency: "optional"
    owner_team: "GCB Technology"

  - id: "INT-015"
    name: "SCA PARCUM / Parkham"
    type: "database"
    direction: "inbound"
    protocol: "internal"
    data_classification: "internal"
    sla_dependency: "required"
    owner_team: "GFT"

# ── TRUST BOUNDARIES ─────────────────────────────────────────
trust_boundaries:
  - id: "TB-001"
    name: "Citi Internal Network — CTI Hosted"
    type: "network"
    components_inside: ["CRA Application", "CRC Platform", "MCA", "ICAPS", "Regulatory Inventory"]
    controls: ["Internal IAM", "Role-based entitlements via City Marketplace", "Access Control Lists"]

  - id: "TB-002"
    name: "CRC Platform Boundary"
    type: "service-perimeter"
    components_inside: ["CRA Core Module", "Aggregation Service", "Third-Party Dashboard"]
    controls: ["CRC entitlement roles", "Preparer/Approver access model", "Session-based authentication"]

  - id: "TB-003"
    name: "Data Classification — Internal PII Boundary"
    type: "data-classification"
    components_inside: ["Assessment data", "Internal employee PII"]
    controls: ["Internal data-classification policy", "PII handling standards"]

# ── DATA CLASSIFICATION SUMMARY ──────────────────────────────
data_classifications:
  - type: "internal"
    description: "All CRA assessment data — risk ratings, control evidence, corrective action plans"
    storage_location: "On-premise CTI infrastructure (CLMWAMLAI01P PROD)"
    encryption_at_rest: "Per Citi CTI standards"
    encryption_in_transit: "Per Citi CTI standards"

  - type: "PII"
    description: "Internal employee PII — assessor identities, approver names, maker/checker attribution"
    storage_location: "CRC Platform — On-premise"
    encryption_at_rest: "Per Citi CTI standards"
    encryption_in_transit: "Per Citi CTI standards"

# ── SLAs ─────────────────────────────────────────────────────
sla:
  availability_target: "Business-critical — per CTI SLA (specific target not sourced)"
  rpo: "❓ Not specified in source materials"
  rto: "❓ Not specified in source materials"
  p99_latency_ms: "❓ Not specified in source materials"
  throughput: "Annual batch cycle; not designed for continuous high-throughput"
  data_retention: "❓ Exact retention not specified; regulatory compliance expected"
  annual_cycle: "November through Q1 of following year (12-month minimum cadence)"

# ── TECH STACK ───────────────────────────────────────────────
tech_stack:
  languages: ["❓ Not specified in source materials"]
  frameworks: ["CRC (Citi Risk & Controls) enterprise platform"]
  databases: ["On-premise relational store — specifics not disclosed"]
  message_brokers: ["None identified in source materials"]
  infra_platform: "Internal CTI (Citi Technology Infrastructure) — on-premise"
  container_runtime: "On-premise container project (CTO-GFT-CRA-180236)"
  iac_tool: "❓ Not specified"
  observability: ["❓ Not specified in source materials"]

# ── 12 PRINCIPLES APPLIED ────────────────────────────────────
principles_applied:
  separation_of_concerns: "strong — separate microservices: UI, CRA rating results, scoping, aggregation"
  modularity: "strong — CRA is a distinct module within CRC; third-party dashboard is a separate component"
  cohesion_and_coupling: "partial — aggregation service decoupled but some legacy components being decommissioned"
  abstraction_layers: "partial — single data-pull snapshot pattern abstracts real-time upstream volatility"
  solid_at_scale: "❓ not assessed — internal platform architecture details not fully disclosed"
  scalability: "partial — annual batch workload; not designed for real-time scaling"
  reliability_availability: "partial — medium business criticality; single-snapshot approach reduces data drift risk"
  maintainability: "partial — active decommissioning of some components noted; ongoing enhancements per consent order"
  security_by_design: "strong — role-based entitlements, maker/checker governance, PII data classification enforced"
  cost_awareness: "❓ not assessed — internal cost model not disclosed"
  evolvability: "strong — annual methodology enhancements documented; GAU-SAU migration demonstrates structural evolution"
  tradeoff_analysis: "strong — explicit CP trade-off: single annual data snapshot chosen over real-time currency for assessment consistency"

# ── CAP / PACELC ─────────────────────────────────────────────
cap_pacelc:
  cap_choice: "CP"
  pacelc_choice: "PC/LC"
  rationale: >
    The single annual data-snapshot architecture (data date: October 13 for 2025 cycle)
    explicitly trades away real-time data currency (availability) in favour of
    consistency — all assessors work from the same validated dataset. This
    prevents mixed-snapshot assessments that caused data traceability issues
    prior to 2024. Latency is deliberately high (annual cadence); consistency
    of the shared snapshot is paramount.

# ── FAILURE MODES ─────────────────────────────────────────────
failure_modes:
  - id: "FM-001"
    scenario: "Upstream data feed failure — MCA or Regulatory Inventory data unavailable at snapshot date"
    impact: "Incomplete or missing publication-level inherent risk and control ratings for one or more GAUs"
    severity: "high"
    mitigation: "GFT Technology Team validates data feed success; ICRM Methodology Team notified; assessment paused pending data correction"
    detection: "Data validation step (Workflow 2.1); ICRM Methodology Team oversight"

  - id: "FM-002"
    scenario: "Stale data risk — snapshot taken October 13 used for assessments running through Q1 following year"
    impact: "Assessment reflects a 5–6 month old data state; significant regulatory or control changes not captured"
    severity: "medium"
    mitigation: "Business teams validate data accuracy at cycle start; qualitative SME overrides at L2/L1 allow adjustment; planned multiple snapshots in 2026"
    detection: "Business team data validation sign-off (Workflow 2.2)"

  - id: "FM-003"
    scenario: "Incomplete mandatory fields — preparer submits assessment with unvalidated rationale"
    impact: "Assessment cannot advance to Approved status; delays cycle completion"
    severity: "medium"
    mitigation: "Validate counter in CRC tool prevents submission until all mandatory fields complete; asterisk markers identify missing fields"
    detection: "CRC Validate section — systemic counter"

  - id: "FM-004"
    scenario: "Application outage during active assessment window"
    impact: "Preparers and approvers unable to access CRA module; cycle timeline at risk"
    severity: "high"
    mitigation: "GFT Support Manager manages SLA-based incident response; root cause analysis produced"
    detection: "Infrastructure monitoring; user-submitted support tickets"

  - id: "FM-005"
    scenario: "Data mixing — assessor inadvertently references off-snapshot MCA data"
    impact: "Inconsistent risk ratings across assessment entities; traceability breakdown"
    severity: "high"
    mitigation: "Single data-pull architecture (introduced 2024) stores snapshots locally; systemic publication ratings are read-only for assessors"
    detection: "ICRM Methodology Team oversight; data validation workflow"

# ── EVOLVABILITY ──────────────────────────────────────────────
evolvability:
  current_pattern: "modular platform (CRC module-based)"
  target_pattern: "GAU-SAU aligned, obligation-based mapping, fully systemic publication ratings"
  migration_strategy: "parallel-run"
  coupling_risks:
    - "Legacy assessment entity model partially replaced — some components still being decommissioned (noted in 2024-2025 architecture review)"
    - "Aggregation service not yet fully documented in public-facing architecture materials"
  extension_points:
    - "Third-party external dashboard — independent refresh cycle, extensible to new data sources"
    - "Multiple snapshot capability planned for 2026 cycle (September 2026 start, August 2027 end)"
    - "L3 risk assessment layer available but optional; can be activated per methodology update"

# ── STAKEHOLDER VIEWS ─────────────────────────────────────────
stakeholder_views:
  business_view:
    primary_audience: ["Business Teams", "Senior Management", "BRCC/GBRCC", "Board of Directors"]
    key_message: "CRA produces an annual compliance risk profile for every Citi GAU — showing where risk is high and how effectively controls are working"
    diagram_type: "C4-L1-Context-simplified"
  technical_view:
    primary_audience: ["Technology Team GFT", "ICRM Methodology Team", "Application Manager"]
    key_message: "CRA is a CRC platform module with 13 upstream data sources, microservices for rating/scoping/aggregation, and one downstream RPA consumer"
    diagram_type: "C4-L2-Container"
  security_view:
    primary_audience: ["Compliance Officers", "Internal Audit", "CISO"]
    key_message: "Internal PII processed; maker/checker governance enforced; all data classified Internal; entitlement-based access via City Marketplace"
    diagram_type: "C4-L1-with-trust-boundaries"

# ── DIAGRAM MANIFEST ──────────────────────────────────────────
diagram_manifest:
  - diagram_type: "C4-L1-Context"
    source_sections: ["actors", "external_integrations", "trust_boundaries"]
    priority: "high"
    status: "pending"
  - diagram_type: "DFD-L0"
    source_sections: ["external_integrations", "data_classifications"]
    source_documents: ["CRA-008 Data & Flow Inventory"]
    priority: "medium"
    status: "pending"
  - diagram_type: "Swimlane"
    source_documents: ["CRA-009 Sequence Catalog"]
    priority: "high"
    status: "pending"

# ── LIVING CONTEXT SYNC ───────────────────────────────────────
living_context:
  last_verified: "2026-04-12"
  last_verified_by: "ai-documentation skill — synthesised from project source documents"
  drift_check_date: "2026-04-12"
  drift_status: "clean"
  linked_commit: ""
  linked_adr: []
  next_review_trigger: "Annual CRA cycle start (November) or methodology change"
  automation_pipeline: "manual"

# ── AI OPTIMISATION FIELDS ────────────────────────────────────
intent: >
  Enable architects, compliance officers, and AI agents to understand
  the CRA application (CSI-180236) — its business purpose, assessment methodology,
  system boundaries, upstream data integrations, downstream consumers, and
  architectural trade-offs — as a single parseable source of truth.

consumption_context:
  - human-reading
  - ai-reasoning
  - rag-retrieval
  - agentic-execution

triggers:
  - "what is the CRA application"
  - "CRA CSI-180236 architecture"
  - "CRA upstream downstream dependencies"
  - "how does compliance risk assessment work at Citi"
  - "CRA system boundaries"

negative_triggers:
  - "step-by-step CRA workflow"      # → CRA-003 Workflow Runbook
  - "CRA methodology calculation"     # → CRA-002 Technical Reference
  - "CRA user roles"                  # → CRA-002 Section 4

confidence_overall: "high"
confidence_note: >
  All content synthesised directly from six primary project source documents:
  CRA_Dependencies.md, CRA_Study_Guide.md, CRA_User_and_Roles.md,
  CRA_Workflows.md, notes.md, 2025_CRA_Training_Transcript.md,
  notebook-llm-notes.md. Where source documents do not specify a value,
  ❓ is used explicitly. No fabricated data.
---
```

---

> ## 🤖 AI Summary
> **System:** Compliance Risk Assessment (CRA) — CSI-180236
> **Core Purpose:** Annual enterprise-wide compliance risk assessment platform that calculates Inherent Risk, Control Environment, and Residual Risk ratings for every Citi Global Assessment Unit (GAU) and Service Assessment Unit (SAU), using a hybrid systemic + qualitative methodology within the Citi Risk & Controls (CRC) platform.
> **Key Specifications:**
> - ✅ Annual cycle: November open → Q1 close; data snapshot date October 13 (2025 cycle)
> - ✅ 13 upstream data sources; 1 downstream RPA consumer (GCB Xceptor)
> - ✅ On-premise CTI infrastructure; maker/checker governance; Internal + PII data classification
> - ✅ 2025: Fully systemic publication-level ratings; qualitative SME assessment at L2/L1
> - ✅ GAU-SAU universe (2025 target state); obligation-based publication mapping
> **Trust Level:** HIGH — synthesised from primary project source documents
> **Do NOT Use This For:** Step-by-step workflow instructions (→ CRA-003), calculation methodology detail (→ CRA-002)

---

## TABLE OF CONTENTS

1. [System Overview](#1-system-overview)
2. [Business Context & Purpose](#2-business-context--purpose)
3. [Actors & Stakeholders](#3-actors--stakeholders)
4. [External Integrations — Upstream](#4-external-integrations--upstream)
5. [External Integrations — Downstream](#5-external-integrations--downstream)
6. [Infrastructure & Deployment](#6-infrastructure--deployment)
7. [Architectural Principles — Applied](#7-architectural-principles--applied)
8. [CAP / PACELC Trade-Off Analysis](#8-cap--pacelc-trade-off-analysis)
9. [Failure Modes & Mitigations](#9-failure-modes--mitigations)
10. [Evolvability & Migration Strategy](#10-evolvability--migration-strategy)
11. [Stakeholder Views](#11-stakeholder-views)
12. [Diagram Generation Manifest](#12-diagram-generation-manifest)
13. [Sources & Cross-References](#13-sources--cross-references)
14. [Revision History](#14-revision-history)

---

## 1. System Overview

[TYPE: REFERENCE]

The **Compliance Risk Assessment (CRA)** application (`CSI-180236`) is the core module within Citi's **Citi Risk & Controls (CRC)** enterprise platform responsible for generating, maintaining, and distributing compliance risk ratings across the firm. ✅ It evaluates three interconnected dimensions — Inherent Risk, Control Environment, and Residual Risk — for every applicable Assessment Entity (2025 onward: Global Assessment Unit / GAU and Service Assessment Unit / SAU). ✅

The CRA is classified as `business-critical`, hosted on internal CTI (Citi Technology Infrastructure) on-premise servers, and processes **Internal** data including **Internal PII** (assessor and approver identities). ✅ It operates on a minimum annual cadence, with each cycle running from November through Q1 of the following year. ✅

`CRA` is responsible for: risk rating generation, assessment data storage, aggregation, reporting to governance committees, and supporting compliance assurance planning.
`CRA` does NOT handle: financial crime risk assessments (EWARA, EWORA, ABRA) — these are separate enterprise assessments.

| Attribute | Value |
|-----------|-------|
| **CSI ID** | `CSI-180236` |
| **System ID** | `SYS-CRA-180236` |
| **Platform** | Citi Risk & Controls (CRC) |
| **Hosting** | Internal CTI — On-Premise |
| **Lifecycle** | Production / Active |
| **Criticality** | Business-Critical (Medium) |
| **Data Classification** | Internal; Internal PII |
| **Assessment Cadence** | At minimum annual (≥12 months) |

---

## 2. Business Context & Purpose

[TYPE: CONTEXT]

CRA is one of four major enterprise-wide compliance risk assessments at Citi. ✅ The other three — EWARA (Anti-Money Laundering), EWORA (OFAC/Sanctions), and ABRA (Anti-Bribery) — address specialised compliance domains; CRA covers core compliance risks: Market Practices, Customer/Client Protection, and Prudential & Regulatory. ✅

The CRA process serves three primary business objectives:

**1. Identify and Remediate.** Proactively surface compliance-related weaknesses before they escalate to regulatory or reputational events. ✅

**2. Assess the Control Framework.** Provide an independent second-line challenge to first-line (Business Team) self-assessments, specifically the Manager's Control Assessment (MCA). ✅ The CRA serves as a credible challenger — not merely a rubber stamp of MCA ratings. ✅

**3. Drive Compliance Planning.** The final Residual Risk ratings feed directly into the Compliance Independent Assessment (CIA) monitoring and testing programme, and into annual Board and BRCC reporting. ✅

The assessment results are presented at the **CBNA-GBRCC** meeting, included in the **State of Risk report** for the Board of Directors, and socialised at all BRCC and control-function meetings across lines of business. ✅

---

## 3. Actors & Stakeholders

[TYPE: REFERENCE]

The CRA process operates across three lines of defence, with eight distinct actor roles. ✅

| Actor ID | Role | LoD | Primary Interaction |
|----------|------|-----|---------------------|
| ACT-001 | Preparer | 1st / 2nd | Initiates assessment; assigns ratings; authors rationale |
| ACT-002 | Approver | 2nd | Reviews, approves, or returns assessment |
| ACT-003 | ICRM Methodology Team | 2nd | Platform administration; data validation; aggregated reporting |
| ACT-004 | Enterprise Compliance & Compliance Assurance | 2nd | Methodology definition; qualitative overrides; Maker/Checker |
| ACT-005 | Technology Team (GFT) | Technology | Application development; data extraction; environment management |
| ACT-006 | Senior Management / BRCC / GBRCC / Board | Executive | Consumes aggregated results; sets risk appetite |
| ACT-007 | Internal Audit | 3rd | Provides control evidence inputs; uses results for audit planning |
| ACT-008 | Business Teams | 1st | Data validation sign-off; control evidence; CAP development |

**Maker/Checker Governance Summary:** ✅ The CRA enforces a strict maker/checker control at every workflow stage. The Preparer (Maker) completes the assessment; the Approver (Checker) reviews and finalises. The CRC platform enforces this through a submission workflow that prevents completion until all mandatory validation counters reach zero.

---

## 4. External Integrations — Upstream

[TYPE: REFERENCE]

The CRA application consumes data from thirteen upstream systems. ✅ For the 2025 cycle, all upstream data was reconciled into a single snapshot taken on **October 13, 2025** — the designated data date. ✅ This single-snapshot architecture was introduced in 2024 to resolve data traceability issues caused by mixed-date assessments. ✅

| INT ID | System | CSI | Data Contributed | Criticality |
|--------|--------|-----|-----------------|-------------|
| INT-001 | CRC-Compliance Risk Management | 176046 | Core compliance risk taxonomies, entity data | Required |
| INT-002 | MCA — Managers Control Assessment | 176101 | Significance of impact; control design attributes; applicability; issues | Required |
| INT-003 | Compliance Data Engine | 169477 | Aggregated compliance data from multiple sources | Required |
| INT-004 | ICAPS — Corrective Action Plan System | 176041 | Open/closed corrective action plans; mapped issues for control effectiveness | Required |
| INT-005 | Policy Management & Mapping / Reg Inventory | 179911 | Publication tiers; obligation mapping; regulatory scope | Required |
| INT-015 | SCA PARCUM / Parkham | — | Process counts; external dependency ratios for Complexity calculation | Required |
| INT-006 | Fair Lending Risk Assessment | 179912 | Specialised fair lending risk inputs | Required |
| INT-007 | Business Activity Warehouse | 153099 | Transactional and business activity data for inherent risk quantification | Required |
| INT-008 | Global Sanctions Risk Management | 154357 | Sanctions risk assessment data | Optional |
| INT-009 | AML-NS Cross Sector Monitoring NAM | 162404 | AML monitoring alerts and data | Optional |
| INT-010 | Pearl | 160992 | Transactional/reference data | Optional |
| INT-011 | SPIDER Securities Position & Disclosure | 161668 | Securities position and disclosure data | Optional |
| INT-012 | Olympus / Best Execution Extraction | 167969/168868 | Trade execution quality data | Optional |
| INT-013 | Reg-Rep LOPR | 1183 | Large options position regulatory reporting data | Optional |

**Data Consumption Modes:** ✅ The CRA consumes upstream data in three ways:
1. **Systemic Calculation Feed** — publication-level ratings at Inherent Risk and Control Environment levels are 100% systemically derived from upstream data (non-modifiable by assessors).
2. **Qualitative Informant** — upstream data (e.g., MCA significance of impact, ICAPS issues) surfaces to assessors as reference context for L2/L1 qualitative ratings.
3. **User-Initiated Search** — assessors can manually search ICAPS directly to add issues not automatically mapped.

---

## 5. External Integrations — Downstream

[TYPE: REFERENCE]

The CRA produces outputs consumed by three categories of downstream stakeholders. ✅

| Downstream Consumer | Type | Data Consumed | Integration Channel |
|--------------------|------|--------------|---------------------|
| GCB Xceptor RPA (CSI-173877) | External system | CRA results / ratings data | Automated RPA platform |
| CIA — Compliance Independent Assessment | Internal team | Residual risk ratings; Recommendation field content | Report / SharePoint |
| CBNA-GBRCC / BRCC / Board | Governance committees | Aggregated risk reports; State of Risk report | Formal governance presentation |

**Key downstream integration note:** The mandatory **Monitoring and Testing (Recommendation) field** in every CRA conclusion section functions as a direct communication channel from Preparers to CIA colleagues, specifying which L2 risks, publications, or third parties require priority attention in the forthcoming testing cycle. ✅

---

## 6. Infrastructure & Deployment

[TYPE: REFERENCE]

| Resource | Name / Environment | Type |
|----------|-------------------|------|
| X86 Production Server | `CLMWAMLAI01P` | On-Premise |
| X86 COB Server | `CLSWAMLAI01C` | On-Premise |
| Container Project (DEV/UAT/PROD) | `CTO-GFT-CRA-180236` | On-Premise |
| Container Project (UAT Load Test) | `CTO-GFT-LT-CRA-180236` | On-Premise |

The CRA microservices architecture includes the following components (as described in 2024-2025 architecture review): ✅ UI, CRA Rating Results, Scoping, and an Aggregation Service. Some legacy components were being decommissioned during the 2024-2025 transition. 💡 A third-party dashboard microservice operates independently from the CRA core system and refreshes daily from MCA data. ✅

---

## 7. Architectural Principles — Applied

[TYPE: RESEARCH_FINDING]

| # | Principle | Rating | Evidence |
|---|-----------|--------|---------|
| 1 | Separation of Concerns | ✅ Strong | Distinct microservices: UI, ratings, scoping, aggregation; third-party dashboard independent |
| 2 | Modularity | ✅ Strong | CRA is a standalone CRC module; separate third-party dashboard |
| 3 | Cohesion & Coupling | ⚠️ Partial | Aggregation service decoupled; some components being decommissioned indicate legacy coupling |
| 4 | Abstraction Layers | ✅ Strong | Single-snapshot architecture abstracts upstream real-time volatility from assessors |
| 5 | SOLID at System Scale | ❓ Not assessed | Internal platform architecture not fully disclosed |
| 6 | Scalability | ⚠️ Partial | Annual batch workload; not designed for continuous high-throughput real-time processing |
| 7 | Reliability & Availability | ✅ Strong | Maker/checker controls prevent partial submissions; validate counter prevents incomplete assessments |
| 8 | Maintainability | ✅ Strong | Annual methodology enhancements; active decommissioning of legacy components; documented procedures |
| 9 | Security by Design | ✅ Strong | Entitlement-based access; maker/checker governance; Internal PII controls; role separation |
| 10 | Cost Awareness | ❓ Not assessed | Internal cost model not disclosed in source materials |
| 11 | Evolvability | ✅ Strong | GAU-SAU migration (2025 target state); multiple snapshot planning (2026); L3 optional layer |
| 12 | Trade-Off Analysis | ✅ Strong | Explicit CP/consistency-over-availability choice; documented rationale for snapshot architecture |

---

## 8. CAP / PACELC Trade-Off Analysis

[TYPE: RESEARCH_FINDING]

**CAP Choice:** CP (Consistency-Partition Tolerance) ✅
**PACELC Choice:** PC/LC (Prioritise Consistency; accept higher Latency) ✅
**Rationale:** The single annual data-snapshot design explicitly trades away real-time data availability in favour of a consistent, point-in-time view shared by all assessors. Prior to 2024, mixed-snapshot assessments caused significant data traceability problems — assessors were working from different data states, making it impossible to trace discrepancies back to source. The October 13 snapshot date resolves this by giving every GAU assessment an identical, reconciled data foundation. Latency (annual cadence, 5-6 month assessment period) is a deliberate and accepted trade-off. ✅

**Implication for Data Stores:** The consistency choice requires the snapshot to be stored locally within the CRA platform rather than queried live from upstream systems. This increases storage requirements but eliminates runtime dependency on upstream system availability during the assessment window. ✅

---

## 9. Failure Modes & Mitigations

[TYPE: RESEARCH_FINDING]

| ID | Scenario | Severity | Mitigation | Detection |
|----|----------|----------|------------|-----------|
| FM-001 | Upstream feed failure at snapshot date | High | GFT validates feeds; ICRM pauses affected assessments; data corrected before proceeding | Workflow 2.1 data validation step ✅ |
| FM-002 | Data staleness — 5-6 month assessment window on Oct-13 snapshot | Medium | Business validates data at cycle start; qualitative SME override available at L2/L1; 2026 multi-snapshot planned | Business sign-off (Workflow 2.2) ✅ |
| FM-003 | Incomplete submission — missing mandatory fields | Medium | CRC validate counter prevents submission; asterisk markers flag missing fields | CRC Validate section (systemic) ✅ |
| FM-004 | Application outage during active assessment window | High | GFT SLA-based incident response; root cause analysis; support ticket management | Infrastructure monitoring; user tickets ✅ |
| FM-005 | Data mixing — assessor uses off-snapshot data | High | Read-only systemic ratings; locally stored snapshots; ICRM oversight | Data validation; ICRM oversight ✅ |

---

## 10. Evolvability & Migration Strategy

[TYPE: CONTEXT]

**Current Pattern:** Modular platform (CRC module-based with microservices)
**Target Pattern:** Fully GAU-SAU aligned; obligation-based mapping; 100% systemic publication ratings
**Migration Strategy:** Parallel-run (legacy assessment entities phased out over 2024-2025; target state reached in 2025 cycle)

**Known Coupling Risks:**
- Legacy assessment entity model partially replaced — decommissioned components noted during 2024-2025 ⚠️
- Aggregation service not fully documented in source materials ❓

**Extension Points:**
- Third-party external dashboard — independent daily refresh; extensible data source 💡
- Multiple snapshot capability planned for 2026 cycle (September 2026 through August 2027) 💡
- L3 risk assessment layer exists and is optional — can be formally activated in future methodology updates 💡

---

## 11. Stakeholder Views

[TYPE: CONTEXT]

### 11.1 Business View
**Audience:** Business Teams, BRCC/GBRCC, Board of Directors, Senior Management
**Message:** CRA produces an annual compliance risk profile for every Citi business unit, showing where raw compliance risk is highest, how well controls are working, and what the net residual risk exposure is. Results drive resource allocation and Board-level risk oversight.
**Recommended Diagram:** C4-L1-Context (simplified — actors and system only, no technology labels)

### 11.2 Technical View
**Audience:** Technology Team (GFT), ICRM Methodology Team, Application Manager
**Message:** CRA is a microservices-based CRC module consuming 13 upstream systems via annual snapshot. It exposes rating results to one downstream RPA consumer. Core services: UI, rating results, scoping, aggregation. Infrastructure: on-premise CTI servers and container projects.
**Recommended Diagram:** C4-L2-Container + upstream integration map

### 11.3 Security View
**Audience:** Compliance Officers, Internal Audit, CISO
**Message:** CRA processes Internal and Internal PII data. Access is entitlement-controlled via City Marketplace. Maker/checker governance is enforced at every assessment stage. Data is on-premise with CTI security controls.
**Recommended Diagram:** C4-L1 with trust boundaries annotated

---

## 12. Diagram Generation Manifest

[TYPE: REFERENCE]

| Diagram Type | Priority | Source Sections | Source Documents | Status |
|-------------|----------|----------------|-----------------|--------|
| C4-L1-Context | High | actors, external_integrations, trust_boundaries | This document | Pending |
| DFD-L0 | Medium | external_integrations, data_classifications | CRA-008 (to be authored) | Pending |
| Swimlane — Assessment Lifecycle | High | actors, workflows | CRA-009 (to be authored) | Pending |
| C4-L2-Container | Medium | tech_stack | CRA-007 (to be authored) | Pending |

---

## 13. Sources & Cross-References

[TYPE: REFERENCE]

### Primary Source Documents

| Source | Content Sourced |
|--------|----------------|
| `CRA_Dependencies.md` | All upstream (13) and downstream (1) integrations; infrastructure; data governance |
| `CRA_Study_Guide.md` | Assessment methodology; business purpose; risk scale definitions; four enterprise assessments |
| `CRA_User_and_Roles.md` | All actor roles and responsibilities; maker/checker matrix |
| `CRA_Workflows.md` | Seven lifecycle phases; minor workflows; role interactions |
| `notes.md` | Microservices architecture; single-snapshot rationale; third-party dashboard; 2025 CRA timeline |
| `2025_CRA_Training_Transcript.md` | 2025 GAU-SAU migration; obligation mapping; hybrid methodology; systemic calculation scope |
| `notebook-llm-notes.md` | Component analysis; upstream integration detail; downstream consumer detail |

### Cross-References

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DETAILED_BY] | CRA-002 Technical Reference | All | CRA methodology and calculation detail |
| [DETAILED_BY] | CRA-003 Workflow Runbook | All | CRA lifecycle procedures |
| [GOVERNED_BY] | RULES-001 Documentation Standards | All | Document format and standards |
| [GOVERNED_BY] | Truth & Verification Standards | Sections 1-4 | Verification label protocol |
| [FEEDS] | generating-architecture-diagrams skill | All | Diagram generation input |

---

## 14. Revision History

[TYPE: REFERENCE]

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-12 | Initial | Document created from synthesis of seven project source documents | Project documentation generation from all provided resources |

---

## DIAGRAM-READY QUALITY GATE

```
YAML FIELDS
[✅] system.system_id populated — SYS-CRA-180236
[✅] actors[] has min 1 entry with id, name, type, trust_level — 8 actors defined
[✅] external_integrations[] has min 1 entry with id, name, protocol, direction — 15 integrations
[✅] trust_boundaries[] has min 1 entry — 3 boundaries defined
[✅] data_classifications[] has min 1 entry — 2 classifications
[✅] tech_stack.infra_platform populated — Internal CTI on-premise
[✅] cap_pacelc.cap_choice populated — CP
[✅] diagram_manifest[] has C4-L1-Context entry

LINKED DOCUMENTS
[⚠️] CRA-007 Container Inventory — not yet authored
[⚠️] CRA-008 Data & Flow Inventory — not yet authored
[⚠️] CRA-009 Sequence Catalog — not yet authored
[⚠️] CRA-010 Deployment Topology — not yet authored

VERIFICATION
[✅] All significant claims carry labels (✅/⚠️/💡/❓)
[✅] SLA targets flagged ❓ where not sourced
[✅] No blank entries in actors, integrations, trust_boundaries
[✅] 5 failure modes defined (minimum 3 met)

LIVING CONTEXT
[✅] living_context.last_verified populated
[✅] living_context.drift_status populated
[✅] living_context.next_review_trigger populated
```

---

*TMPL-006 v2.0 — System Context Pack | Document: CRA-006 | Parent: RULES-001 Documentation Standards*
*All claims synthesised from primary project source documents. Verification labels applied per Truth & Verification Standards.*
