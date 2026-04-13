# Compliance Risk Assessment (CRA): Technical Reference
## Assessment Methodology, Calculation Framework & User Roles — 2025 Cycle

```yaml
---
# ── RULES-001 STANDARD FIELDS ──────────────────────────────
document_id: "CRA-002 Technical Reference"
title: "CRA — Assessment Methodology, Calculation Framework & User Roles"
version: "1.0"
created: "2026-04-12"
status: "Final"
parent_document: "RULES-001 Documentation Standards"

# ── SYSTEM VERSION LOCK ─────────────────────────────────────
system_name: "Compliance Risk Assessment (CRA)"
system_version: "2025 Cycle"
applies_from: "2025-11-03"
supersedes_version: "2024 Cycle Methodology"

# ── AI-OPTIMISATION EXTENSION ──────────────────────────────
intent: >
  Enable compliance analysts, preparers, approvers, and AI agents to
  fully understand the 2025 CRA calculation methodology — including how
  Inherent Risk, Control Environment, and Residual Risk are determined,
  what each role does, and how the platform enforces the hybrid
  qualitative/quantitative approach within the CRC module.

consumption_context:
  - human-reading
  - ai-reasoning
  - rag-retrieval
  - agentic-execution

triggers:
  - "how is inherent risk calculated in CRA"
  - "CRA control environment methodology"
  - "what is residual risk in CRA"
  - "CRA 2025 hybrid approach"
  - "CRA user roles preparer approver"
  - "GAU-SAU CRA assessment"
  - "CRA publication rating calculation"

negative_triggers:
  - "CRA workflow step by step"          # → CRA-003 Workflow Runbook
  - "CRA architecture dependencies"      # → CRA-006 System Context Pack
  - "generate CRA diagram"               # → generating-architecture-diagrams

volatility: "annual"
template_version_used: "TMPL-002 v1.1"
research_validated: false
research_validated_date: "2026-04-12"
research_queries_used: []
review_trigger: "Next CRA methodology update or annual cycle launch"
confidence_overall: "high"
confidence_note: >
  All content sourced directly from: 2025_CRA_Training_Transcript.md,
  CRA_Study_Guide.md, CRA_User_and_Roles.md, CRA_Workflows.md,
  notebook-llm-notes.md. No fabricated data. Where source is ambiguous
  or incomplete, ❓ is used.

methodology_phase: "Phase-7-Documentation"
---
```

---

> ## 🤖 AI Summary
> **System:** CRA 2025 Cycle Methodology
> **Core Purpose:** Defines how the CRA application calculates Inherent Risk (Tier 1–5), Control Environment (Effective/Partially Ineffective/Ineffective), and Residual Risk (Tier 1–5) for each Citi Global Assessment Unit using a hybrid systemic + qualitative approach.
> **Key Specifications:**
> - ✅ Publication-level ratings 100% systemically calculated (2025 change)
> - ✅ L2/L1 ratings: qualitative SME assessment informed by quantitative data
> - ✅ Residual Risk: 100% systemic via MCA GRC matrix — non-modifiable
> - ✅ Assessment universe: GAU-SAU structure (2025 target state)
> - ✅ Single data date: October 13, 2025
> **Trust Level:** HIGH — sourced directly from 2025 CRA Training and methodology documents
> **Version Validity:** 2025 CRA Cycle (November 2025 — Q1 2026)
> **Do NOT Use This For:** Step-by-step tool navigation (→ CRA-003), system architecture (→ CRA-006)

---

## TABLE OF CONTENTS

1. [System Overview](#1-system-overview)
2. [Assessment Universe — GAU-SAU Structure](#2-assessment-universe--gau-sau-structure)
3. [The Three-Pillar Assessment Model](#3-the-three-pillar-assessment-model)
4. [Component 1 — Inherent Risk Calculation](#4-component-1--inherent-risk-calculation)
5. [Component 2 — Control Environment Assessment](#5-component-2--control-environment-assessment)
6. [Component 3 — Residual Risk Determination](#6-component-3--residual-risk-determination)
7. [The Hybrid Execution Methodology](#7-the-hybrid-execution-methodology)
8. [User Roles & Responsibilities](#8-user-roles--responsibilities)
9. [Conclusion & Final Documentation Requirements](#9-conclusion--final-documentation-requirements)
10. [Data Management & Governance](#10-data-management--governance)
11. [Integration Points](#11-integration-points)
12. [Constraints & Known Limitations](#12-constraints--known-limitations)
13. [Version Changes — 2025 vs 2024](#13-version-changes--2025-vs-2024)
14. [Sources & Cross-References](#14-sources--cross-references)
15. [Revision History](#15-revision-history)

---

## 1. System Overview

[TYPE: REFERENCE]

The **Compliance Risk Assessment (CRA)** is a structured, mandatory, minimum-annual process within Citi's Independent Compliance Risk Management (ICRM) framework. ✅ It evaluates compliance risk — defined as the risk of legal/regulatory sanctions, financial loss, or reputational damage from failure to comply with laws, regulations, and applicable codes — across every applicable business unit. ✅

The **Citi Risk & Controls (CRC)** platform hosts the CRA module, which is the authoritative system for calculating and distributing Inherent Risk, Control Environment, and Residual Risk ratings. ✅ The 2025 cycle represents the **target state** for consent order deliverables, introducing major structural and methodological changes from prior years. ✅

**System Boundaries:**

```
Upstream data sources (13 systems, reconciled Oct-13 snapshot)
    ↓
CRC / CRA Module (CSI-180236)
  ├── Systemic calculation engine (publication-level ratings)
  ├── Assessment interface (Preparer: L2/L1 qualitative ratings + rationale)
  ├── Validation engine (mandatory-field counter)
  ├── Approval workflow (Preparer → Approver)
  ├── Aggregation engine (L2 → L1 → GAU → Regional/Global)
  └── Third-party dashboard (independent daily refresh)
    ↓
Downstream consumers (CIA testing plans; GBRCC; Board; RPA)
```

**Core Formula:** `Inherent Risk` × `Control Environment` = `Residual Risk` (via GRC matrix) ✅

---

## 2. Assessment Universe — GAU-SAU Structure

[TYPE: REFERENCE]

### 2.1 Universe Definition

For the 2025 cycle, CRA transitioned from **Assessment Entities** (constructed from GRCAUs) to the **MCA GAU-SAU structure** — aligning compliance assessment with the enterprise-wide common universe used by all other control components. ✅ This was a consent order target state requirement. ✅

| Unit Type | Description | Scope |
|-----------|-------------|-------|
| **GAU (Global Assessment Unit)** | Primary assessment unit; organised by Line of Business and Global Function | Majority of assessments |
| **SAU (Service Assessment Unit)** | Typically legal-entity level | Managed by specific coordinators |

### 2.2 Publication Mapping — 2025 Change

A fundamental change in 2025 was the shift from **anchoring risk** to **obligation-based mapping**. ✅

**Prior approach (2024 and earlier):** Publications were included in an assessment based on the overall "anchoring risk" of the publication in the Regulatory Inventory. A publication anchored to Operational Risk would be excluded from the CRA scope.

**2025 target state:** Publications are mapped based on their **obligation mapping** at the L2 compliance risk level. If any obligation within a Tier 1 or Tier 2 publication maps to a compliance risk, that publication enters the assessment — regardless of its overall anchor. ✅

**Consequence:** The scope of publications in each assessment broadened in 2025. A single publication may appear **multiple times** in one GAU assessment if its obligations map to different L2 risk stripes. ✅

---

## 3. The Three-Pillar Assessment Model

[TYPE: REFERENCE]

The CRA process is built on three sequential pillars: ✅

```
STEP 1: INHERENT RISK
  Measure risk exposure BEFORE controls
  Scale: Tier 1 (highest) → Tier 5 (lowest)
         ↓
STEP 2: CONTROL ENVIRONMENT
  Assess quality of risk mitigations
  Scale: Ineffective | Partially Ineffective | Effective
         ↓
STEP 3: RESIDUAL RISK
  Determine risk AFTER controls
  Scale: Tier 1 (highest) → Tier 5 (lowest)
  Method: MCA GRC Residual Risk Matrix (systemic)
```

**Key definitions:**

- **Inherent Risk:** The level of compliance risk an organisation is exposed to BEFORE controls are applied. Represents raw risk from potential non-compliance with applicable laws, rules, and regulations. ✅
- **Control Environment:** The set of processes, systems, and measures that mitigate inherent risks. Assessed on design adequacy and operational effectiveness. ✅
- **Residual Risk:** The level of risk remaining AFTER controls are applied. Derived from the intersection of Inherent Risk Tier and Control Environment rating via the GRC matrix. ✅

**Risk Taxonomy in Scope:** ✅
- Level 1: Market Practices, Customer/Client Protection, Prudential & Regulatory
- Level 2: Specific risk stripes under each L1 (the primary qualitative assessment level)
- Level 3: Available but optional and not required for 2025 (default: Not Assessed)

---

## 4. Component 1 — Inherent Risk Calculation

[TYPE: REFERENCE]

### 4.1 Calculation Architecture

Inherent Risk is calculated bottom-up from the publication level. ✅ For 2025, all publication-level ratings are **100% systemically calculated** — assessors do not modify or recalculate publication ratings. ✅

```
Publication Tier (Reg Inventory)
    ↓ [+ Significance of Impact from MCA]
Calculated Severity
    ↓ [× Overall Likelihood]
Publication-Level Inherent Risk Rating
    ↓ [qualitative SME assessment by Preparer]
L2 Current Rating (manually assigned)
    ↓ [roll-up]
L1 Rating → Overall GAU Inherent Risk Rating
```

### 4.2 Severity Calculation

**Starting point:** The publication's tier from the Regulatory Inventory. A Tier 1 publication starts at Tier 1 severity; a Tier 2 starts at Tier 2. ✅

**Significance of Impact adjustment:** The system pulls four ratings from the MCA for each publication: ✅
1. Financial / Operational Loss
2. Reputational Impact
3. Business Disruption
4. Conduct Impact

These four ratings are systemically aggregated to produce a single Significance of Impact score, which then adjusts the calculated severity: ✅

| Significance of Impact | Effect on Severity |
|-----------------------|-------------------|
| Very High | Bumps severity UP one tier |
| High | No change |
| Medium | No change |
| Low | Reduces severity DOWN one tier |

Note: A Tier 1 publication cannot be bumped higher; floor/ceiling logic applies. ✅

### 4.3 Likelihood Factors

Three factors determine overall likelihood. Two are fully systemic; one is manually assigned by the Preparer. ✅

| Factor | Source | Assignment | Description |
|--------|--------|------------|-------------|
| **Business Activities** | Offline data kit (SharePoint) | **Manual — Preparer assigns once** | Operational scale and profile of the GAU. Ratings: High / Medium / Low. Extrapolated across all publications in the GAU |
| **Complexity** | PARCUM / SCA Parkham | Systemic | Number of distinct processes + ratio of external dependencies (third parties) per publication |
| **Applicability** | MCA obligation mapping | Systemic | Percentage of a publication's obligations that apply to the specific GAU. Default: Low. >25%: Medium. >75%: High |

**Business Activities Rating Data Kit:** ✅ The offline data kit (on SharePoint) includes: total FTE counts, total number of processes, distribution of MCA business environment ratings. Definitions for High/Medium/Low are also embedded in the CRC tool UI.

**Likelihood Aggregation:** The three factors combine to produce an overall likelihood score, which then modifies the calculated severity. The combined effect moves the publication's inherent risk rating up, down, or leaves it unchanged. ✅

### 4.4 L2 and L1 Roll-Up

After publication-level ratings are systemically generated: ✅

1. The tool displays an **"L2 Rating Based on Publication"** (quantitative summary) for each L2 risk stripe.
2. The Preparer reviews this alongside any supplemental data (listed in Section 4.5) and manually assigns the **"L2 Current Rating"** (qualitative assessment).
3. Every L2 Current Rating requires a **mandatory written rationale** explaining the driver of that rating. The tool will not allow submission without these. ✅
4. L2 ratings roll up to L1 ratings. L1 ratings can also be qualitatively adjusted by the Preparer.
5. L1 ratings roll up to the **overall GAU Inherent Risk rating**. ✅

**L3 Ratings:** L3 risks are available in the tool but are **not required** and default to "Not Assessed." If a Preparer chooses to rate L3s, they contribute to an "L2 Rating Based on L3s" which appears alongside but does not replace the L2 Current Rating. ✅

### 4.5 Additional Inherent Risk Data Points (Qualitative Reference)

The following supplemental data points are compiled and made available on SharePoint for Preparers to reference when qualitatively assessing their L2 ratings: ✅

- Regulatory enforcement actions or alerts
- Number and complexity of applicable publications
- Regulatory change frequency
- Cross-border / multi-jurisdiction exposure
- Product/service complexity
- Customer volume and vulnerability
- Third-party dependency profile
- Prior assessment results and trends

---

## 5. Component 2 — Control Environment Assessment

[TYPE: REFERENCE]

### 5.1 Calculation Architecture

The Control Environment (CE) assessment follows the same bottom-up structure as Inherent Risk. ✅ Publication-level CE ratings are 100% systemically calculated; L2/L1 ratings are qualitatively assessed by the Preparer.

```
MCA Control Design Attributes (preventative/detective, automated/manual, frequency)
    ↓ [systemic decision tree]
Strength of Controls Rating (Strong | Adequate | Weak) per publication
    ↓ [+ Control Effectiveness from ICAPS issues]
Publication-Level Control Environment Rating
    ↓ [qualitative SME assessment]
L2 Current CE Rating (manually assigned + mandatory rationale)
    ↓ [aggregation waterfall]
L1 CE Rating → Overall GAU Control Environment Rating
```

### 5.2 Strength of Controls

Strength of Controls (SOC) is a design-attribute-driven rating sourced from MCA data at the obligation level. ✅ The system evaluates:

| Design Attribute | Assessed |
|-----------------|---------|
| Preventative vs. Detective | ✅ |
| Automated vs. Manual | ✅ |
| Frequency of Control | ✅ |
| Presence or Absence of Controls | ✅ |

The systemic decision tree processes these attributes to produce a rating of **Strong**, **Adequate**, or **Weak** for each publication. ✅

**Exception:** Tier 3, 4, or 5 publications with no obligation mapping receive an SOC rating of **N/A**. ✅

### 5.3 Control Effectiveness

Control Effectiveness (CEff) is driven by open issues from ICAPS, mapped via MCA obligation IDs. ✅ The system applies a **waterfall approach**: it counts the number and severity level of mapped issues and derives a rating based on that count and severity. ✅

**2025 change:** Issues are now mapped based on obligation IDs from SCA PARCUM data — ensuring only open issues relevant to specific compliance risks and GAU-publication pairs are considered. ✅

### 5.4 Issue Tagging — Preparer Capability

Preparers can **add issues** (from ICAPS directly) or **untag issues** (from the systemically mapped list) to better reflect their GAU-specific knowledge. ✅

**Critical limitation:** Adding or untagging issues does **not** change the systemic quantitative calculation. The system's publication-level CE rating is derived solely from the automatically mapped issues. Manually added/removed issues are available for the Preparer's qualitative reasoning at the L2 level. ✅

### 5.5 Aggregation Waterfall (L2 Level)

The aggregation logic from publication to L2 follows a **highest-deficiency basis**: ✅

| Condition | L2 CE Rating |
|-----------|-------------|
| One or more underlying publication CE ratings are Ineffective | L2 = Ineffective |
| No publications Ineffective; one or more are Partially Ineffective | L2 = Partially Ineffective |
| All publications Effective | L2 = Effective |

### 5.6 Control Environment Rating Scale

The CRA uses three CE ratings. **Highly Effective is not used in the CRA** (unlike the standard MCA scale). ✅

| Rating | Description |
|--------|-------------|
| **Effective** | Controls adequately mitigate identified inherent risks |
| **Partially Ineffective** | Some controls in place but material gaps exist |
| **Ineffective** | Controls are absent, inadequate, or fail to mitigate key risks |

### 5.7 Additional Control Environment Data Points

Supplemental data compiled on SharePoint for CE qualitative assessment: ✅
- MCA control ratings and trends
- Internal Audit findings (key input as 3rd-line evidence)
- Compliance testing results
- Regulatory exam findings and MRAs
- Corrective Action Plan (CAP) status from ICAPS
- **Data Compliance Risk concerns** (new in 2025): an offline data kit listing data issues by GAU and L2 risk stripe not yet captured as formal ICAPS issues. Includes an indicative qualitative rating for reference. ✅

---

## 6. Component 3 — Residual Risk Determination

[TYPE: REFERENCE]

### 6.1 Calculation Method

Residual Risk is **100% systemically calculated** using the **MCA GRC Residual Risk Matrix**. ✅ It is not an independent assessment but the direct output of the Inherent Risk and Control Environment inputs via a matrix intersection.

**Assessors cannot directly modify Residual Risk ratings at any level.** ✅ If the calculated residual risk appears incorrect, the Preparer must re-evaluate the qualitative inputs for Inherent Risk or Control Environment — the only path to changing the residual risk outcome.

### 6.2 Rating Visibility

Residual Risk ratings are automatically populated and visible at: ✅
- Publication level
- L2 level
- L1 level
- Overall GAU level

### 6.3 Qualitative Overrides (Legacy / Governance-Gated)

The CRA Study Guide references a qualitative override process for cases where the calculated residual risk does not accurately reflect the true risk profile. ✅ In this process:

1. An Enterprise Compliance analyst (Maker) identifies the discrepancy and drafts justification with evidence.
2. A senior approver or committee (Checker/Approver) reviews and approves the override.
3. If approved, the override is applied and the final rating is changed.

**Note:** The 2025 training transcript describes residual risk as 100% systemic and non-modifiable by the user. The override process described in the Study Guide may operate at a different governance level (above the individual Preparer/Approver workflow). ⚠️ This discrepancy is flagged for clarification with the ICRM Methodology Team.

---

## 7. The Hybrid Execution Methodology

[TYPE: REFERENCE]

The 2025 cycle uses a **Hybrid Approach** — combining systemic quantitative data with qualitative Subject Matter Expertise (SME). ✅ This is the most significant methodology change from 2024.

```
LAYER 1 — SYSTEMIC (Automated, Non-Modifiable)
  Publication-level IR and CE ratings
  100% calculated from upstream data as of data date
  No assessor intervention

LAYER 2 — QUALITATIVE SME (Assessor-Assigned)
  L2 Current Rating (IR and CE) — manually assigned
  L1 Rating — manually assigned (can override the L2 roll-up)
  Overall GAU Rating — manual qualitative override available
  Mandatory rationale required at every L2 for both IR and CE

LAYER 3 — SYSTEMIC AGGREGATION (Automated)
  Residual Risk — 100% systemic via GRC matrix
  No assessor intervention
```

**Purpose of the hybrid approach:** ✅ Quantitative systemic data provides a consistent, authoritative starting point and removes subjectivity from the most granular level. Qualitative SME input at L2 and above allows compliance professionals to incorporate contextual knowledge — regulatory developments, recent enforcement actions, business changes — that automated data systems cannot capture.

---

## 8. User Roles & Responsibilities

[TYPE: REFERENCE]

### 8.1 Role Matrix

| Role | Line of Defence | Primary CRA Actions |
|------|----------------|---------------------|
| **Preparer** | 1st / 2nd | Initiates assessment; assigns Business Activity rating; assigns L2/L1 qualitative ratings; authors mandatory rationales; manages issue tags; completes conclusion; submits |
| **Approver** | 2nd | Reviews Preparer's work; approves (→ Completed) or returns to Preparer (→ Draft) |
| **Business Teams** | 1st | Validates data accuracy; provides control evidence; develops CAPs |
| **Enterprise Compliance & Assurance** | 2nd | Defines methodology; acts as primary Maker/Checker at senior level; approves overrides |
| **ICRM Methodology Team** | 2nd | Administers CRC platform; manages GAU universe; validates data feeds; produces aggregated reports |
| **Technology Team (GFT)** | Technology | Develops and maintains CRA application; runs data extraction; manages DEV/UAT/PROD |
| **Application Manager (Ravi Katiyar)** | Management | Owns strategic roadmap; manages budget; ensures platform meets business needs |
| **Support Manager (Ravi Katiyar)** | Management | Owns support SLA; manages incident response |
| **Senior Management / BRCC / Board** | Executive | Consumes results; sets risk appetite; allocates resources |
| **Internal Audit** | 3rd | Provides audit findings as CE evidence; uses CRA for audit-plan prioritisation |

### 8.2 Entitlement & Access

Access to the CRA Risk Assessment module within CRC is controlled through **City Marketplace** entitlements. ✅ Both Preparers and Approvers must have approved entitlements before the cycle opens. The recommended action is to request access prior to the November 3rd cycle start date. ✅

### 8.3 Maker / Checker Workflow

The CRA enforces a strict maker/checker governance model through the CRC platform: ✅

```
1. Preparer (Maker) starts assessment
2. Preparer assigns Business Activity rating
3. Preparer completes L2/L1 qualitative ratings + mandatory rationales (IR and CE)
4. Preparer completes all mandatory conclusion fields
5. Tool validates: counter must reach ZERO
6. Preparer submits → Status: Pending Approval
7. Approver (Checker) reviews all ratings, rationales, and conclusions
8. Approver: APPROVE → Status: Completed
              OR
   Approver: RETURN → Status: Draft (back to Preparer for revision)
```

---

## 9. Conclusion & Final Documentation Requirements

[TYPE: REFERENCE]

### 9.1 Required Conclusion Fields

Before an assessment can be submitted, the Preparer must complete the following **mandatory** fields (marked with asterisk in the CRC tool): ✅

| Field | Purpose | Mandatory |
|-------|---------|-----------|
| **Business Profile** | Describes GAU scope, organisational changes, business strategy, third-party relationships | ✅ Yes |
| **Inherent Risk Rating Themes** | High-level summary of key IR drivers at the GAU level (e.g., specific business processes) | ✅ Yes |
| **Control Environment Rating Themes** | Summary of CE drivers at the GAU level | ✅ Yes |
| **Residual Risk Commentary** | Overall assessment result summary; year-on-year trends where applicable | ✅ Yes |
| **Monitoring and Testing (Recommendation)** | Information for CIA colleagues to inform their testing scope; highlights L2 risks, publications, third parties | ✅ Yes |
| **Legal Entity Considerations** | Tier 1 or 2 legal entity highlights | Optional |
| **Other Recommendations** | Any additional information not covered elsewhere | Optional |

### 9.2 Third-Party Dashboard Integration

For the 2025 cycle, a new **external third-party dashboard** was introduced to help identify third-party dependencies by GAU. ✅ The Business Profile conclusion field is the recommended location to reference material third-party relationships identified via this dashboard. The dashboard is accessible within the Aggregation tab of the CRC module.

### 9.3 Year-on-Year Trend Note

The 2025 cycle's migration to the GAU-SAU structure means that many GAUs did not exist in their current form in 2024. Year-on-year trend comparisons in Residual Risk Commentary may not be applicable for the first 2025 cycle. ✅ Preparers are advised to note this limitation in their commentary rather than forcing invalid trend comparisons.

---

## 10. Data Management & Governance

[TYPE: REFERENCE]

### 10.1 Single Data Pull Architecture

The most significant process architecture change of the 2024-2025 period was the introduction of the **single data pull** (single snapshot) model. ✅

**Problem addressed:** Prior to 2024, inconsistent data pulling — assessors referencing data from different dates — made it impossible to trace data issues back to their source and created assessment inconsistencies across entities. ✅

**Solution:** Data from three upstream systems (MCA, Regulatory Inventory, ICAPS) is snapshotted at a single data date and stored locally within the CRA platform. ✅ All assessors work from this same snapshot throughout the assessment window.

**2025 Data Date:** October 13, 2025 ✅

**Future direction:** The plan for 2026 is to conduct **multiple snapshots** (September 1, 2026 start, through August 2027), allowing more frequent refreshes while maintaining within-cycle consistency. 💡

### 10.2 Data Validation Responsibility

For assessments that rely on customer, transaction, and product data for Inherent Risk calculations, the underlying data must be **validated for completeness and accuracy by the Business**. ✅ This is not the responsibility of ICRM or GFT.

**Consequence of invalid data:** If data is found to be incomplete, the assessment cannot be considered accurate. The assessment should be paused or re-run after data correction. ✅

### 10.3 Data Classification

| Classification | Scope |
|---------------|-------|
| Internal | All CRA assessment data, risk ratings, control evidence |
| Internal PII | Assessor, approver, and maker/checker identities within the tool |

---

## 11. Integration Points

[TYPE: REFERENCE]

### 11.1 Key Upstream Data Consumers in the Assessment Process

| Upstream System | What CRA Uses | Where in Methodology |
|----------------|---------------|---------------------|
| Regulatory Inventory (CSI-179911) | Publication tiers; obligation mapping for Applicability | Severity calculation; GAU scoping |
| MCA (CSI-176101) | Significance of impact; control design attributes; SOC decision tree inputs | Severity + Likelihood; Strength of Controls |
| SCA PARCUM | Number of processes; external dependency ratio | Complexity likelihood factor |
| ICAPS (CSI-176041) | Open issues mapped to GAU-publication pairs | Control Effectiveness rating |
| Business Activity Warehouse (CSI-153099) | Business-level metrics for data kit reference | Business Activity rating support |
| SharePoint (offline data kits) | FTE counts; process metrics; Data Compliance Concerns | Business Activity rating; CE qualitative reference |

### 11.2 Downstream Consumers of CRA Outputs

| Consumer | What They Use | How |
|----------|--------------|-----|
| CIA — Compliance Independent Assessment | Residual risk ratings; Recommendation field | Annual testing and monitoring plan |
| CBNA-GBRCC | Aggregated GAU/global ratings | Governance committee review |
| Board of Directors | State of Risk report | Annual board reporting |
| GCB Xceptor RPA (CSI-173877) | CRA results for downstream automation | RPA platform consumption |

---

## 12. Constraints & Known Limitations

[TYPE: REFERENCE]

| Constraint | Detail |
|-----------|--------|
| Annual cadence minimum | CRA conducted at least every 12 months; more frequent if significant risk/control environment changes ✅ |
| Data date lock | All assessments use data as of October 13, 2025 — real-time data not available ✅ |
| Residual risk non-modifiable | Cannot be directly changed; must adjust IR or CE inputs ✅ |
| Highly Effective not available | CE rating scale: Effective / Partially Ineffective / Ineffective only ✅ |
| Issue tagging non-quantitative | Adding/untagging issues does not change the systemic calculation ✅ |
| L3 ratings not required | Available but methodology excludes them from 2025 calculations ✅ |
| Business Activity rating once per GAU | One rating applies across all publications in the GAU; can be changed during Draft status only ✅ |
| GAU-SAU transition | Year-on-year trend comparisons may not be valid for 2025 cycle ✅ |

---

## 13. Version Changes — 2025 vs 2024

[TYPE: REFERENCE]

| Area | 2024 (Prior) | 2025 (Current) |
|------|-------------|----------------|
| Assessment universe | Assessment Entities (from GRCAUs) | GAU-SAU structure (MCA-aligned) |
| Publication mapping | Anchoring risk of overall publication | Obligation-level mapping per L2 compliance risk |
| Publication-level ratings | Manually assessed by assessor | 100% systemically calculated |
| L2 assessment method | Publication-by-publication manual assessment | Hybrid: quantitative publication ratings → qualitative L2 |
| Issue mapping | Prior mapping approach | Obligation-IDs from SCA PARCUM |
| Data architecture | Multiple data pulls (no fixed snapshot) | Single data date (October 13, 2025) |
| Third-party visibility | Limited | New external third-party dashboard in Aggregation tab |
| Data Compliance Concerns | Not available | New offline data kit by GAU/L2 risk stripe |

---

## 14. Sources & Cross-References

[TYPE: REFERENCE]

### Primary Sources

| Source Document | Content Sourced |
|----------------|----------------|
| `2025_CRA_Training_Transcript.md` | Hybrid methodology; GAU-SAU; systemic calculations; publication mapping; CE aggregation; conclusion requirements; tool walkthrough |
| `CRA_Study_Guide.md` | Core definitions; Inherent Risk / CE / Residual Risk framework; four enterprise assessments; roles; aggregation; practice problems |
| `CRA_User_and_Roles.md` | Full role responsibilities matrix; maker/checker actions; deliverables per role |
| `CRA_Workflows.md` | Workflow phases; minor workflow steps; maker/checker assignments |
| `notebook-llm-notes.md` | Detailed analysis of each methodology component; upstream integration usage; downstream integration |

### Cross-References

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [CONTEXTUALISED_BY] | CRA-006 System Context Pack | All | System architecture and integration map |
| [PROCEDURED_BY] | CRA-003 Workflow Runbook | All | Step-by-step execution of this methodology |
| [GOVERNED_BY] | RULES-001 Documentation Standards | All | Document format |
| [GOVERNED_BY] | Truth & Verification Standards | Sections 1-4 | Verification label protocol |

---

## 15. Revision History

[TYPE: REFERENCE]

| Version | Date | Change Type | System Version | Description | Reason |
|---------|------|-------------|----------------|-------------|--------|
| 1.0 | 2026-04-12 | Initial | 2025 Cycle | Document created from synthesis of five primary project source documents | Project documentation generation |

---

*TMPL-002 v1.1 — Technical Reference | Document: CRA-002 | Parent: RULES-001 Documentation Standards*
*All claims carry verification labels (✅/⚠️/💡/❓). Sourced from primary project documents. No fabricated data.*
