# Compliance Risk Assessment (CRA): Workflow Runbook
## Assessment Lifecycle — Seven-Phase Procedure with Maker/Checker Roles

```yaml
---
# ── RULES-001 STANDARD FIELDS ──────────────────────────────
document_id: "CRA-003 Workflow Runbook"
title: "CRA — Assessment Lifecycle Workflow Runbook"
version: "1.0"
created: "2026-04-12"
status: "Final"
parent_document: "RULES-001 Documentation Standards"
template_version_used: "TMPL-003 v1.0"

# ── AI-OPTIMISATION EXTENSION ──────────────────────────────
intent: >
  Enable Preparers, Approvers, ICRM Methodology Team members, and
  AI agents to execute each phase of the CRA annual cycle — from
  annual planning through action planning and monitoring — with
  precise role-aware step-by-step guidance and maker/checker
  accountability at every stage.

consumption_context:
  - human-reading
  - ai-reasoning
  - agentic-execution

triggers:
  - "CRA workflow steps"
  - "CRA lifecycle phases"
  - "how to run a CRA assessment"
  - "CRA preparer checklist"
  - "CRA maker checker process"
  - "CRA annual planning workflow"
  - "CRA data collection validation"
  - "CRA residual risk determination workflow"

negative_triggers:
  - "how is CRA calculated"               # → CRA-002 Technical Reference
  - "CRA system architecture"             # → CRA-006 System Context Pack
  - "generate CRA architecture diagram"   # → generating-architecture-diagrams

volatility: "annual"
research_validated: false
review_trigger: "Annual CRA cycle methodology update or process change"
confidence_overall: "high"
confidence_note: >
  All content sourced directly from: CRA_Workflows.md, CRA_User_and_Roles.md,
  2025_CRA_Training_Transcript.md, CRA_Study_Guide.md, notes.md.
  Verification labels applied throughout. No fabricated data.

methodology_phase: "Phase-7-Documentation"
---
```

---

> ## 🤖 AI Summary
> **Document:** CRA Assessment Lifecycle Workflow Runbook
> **Core Purpose:** Step-by-step procedural guide for all seven phases of the annual CRA cycle, including who does what at each step (maker/checker roles), platform actions in the CRC tool, and governance gates.
> **Key Procedures:**
> - ✅ 7 major phases: Planning → Data Collection → Risk Assessment → Residual Risk → Socialization → Reporting → Action Planning
> - ✅ Maker/checker documented at every workflow step
> - ✅ CRC tool actions for Preparers and Approvers
> - ✅ 2025-specific: GAU-SAU, hybrid methodology, single data date
> **Trust Level:** HIGH — sourced from primary project workflow documents
> **Do NOT Use This For:** Methodology calculations (→ CRA-002), system architecture (→ CRA-006)

---

## TABLE OF CONTENTS

1. [Workflow Overview & Governance](#1-workflow-overview--governance)
2. [Phase 1 — Annual Planning & Scoping](#2-phase-1--annual-planning--scoping)
3. [Phase 2 — Data Collection & Validation](#3-phase-2--data-collection--validation)
4. [Phase 3 — Risk & Control Assessment](#4-phase-3--risk--control-assessment)
5. [Phase 4 — Residual Risk Determination](#5-phase-4--residual-risk-determination)
6. [Phase 5 — Results Review & Socialization](#6-phase-5--results-review--socialization)
7. [Phase 6 — Final Reporting & Aggregation](#7-phase-6--final-reporting--aggregation)
8. [Phase 7 — Action Planning & Monitoring](#8-phase-7--action-planning--monitoring)
9. [CRC Tool: Preparer Step-by-Step Execution](#9-crc-tool-preparer-step-by-step-execution)
10. [CRC Tool: Approver Step-by-Step Execution](#10-crc-tool-approver-step-by-step-execution)
11. [Escalation & Exception Handling](#11-escalation--exception-handling)
12. [Sources & Cross-References](#12-sources--cross-references)
13. [Revision History](#13-revision-history)

---

## 1. Workflow Overview & Governance

[TYPE: CONTEXT]

The CRA operates on an annual cycle running from **November through Q1 of the following year**. ✅ The cycle is mandatory for all applicable Citi business units. The 2025 cycle opened on **November 3, 2025**. ✅

### 1.1 Seven-Phase Lifecycle

```
Phase 1: Annual Planning & Scoping
  └─ Define universe, methodology, schedule

Phase 2: Data Collection & Validation
  └─ Extract and validate upstream data against single snapshot

Phase 3: Risk & Control Assessment
  └─ Inherent Risk + Control Environment assessment (hybrid)

Phase 4: Residual Risk Determination
  └─ Systemic GRC matrix calculation + override process

Phase 5: Results Review & Socialization
  └─ Calibration sessions; stakeholder review of draft results

Phase 6: Final Reporting & Aggregation
  └─ Governance committee reporting; board-level reports

Phase 7: Action Planning & Monitoring
  └─ Compliance testing plans; corrective action plans (CAPs)
```

### 1.2 Governance Principles

**Maker/Checker Model:** Every workflow phase has a defined Maker (who produces the output) and a Checker or Approver (who validates it). ✅ No phase can advance without the Checker's confirmation.

**Non-negotiable gates:** ✅
- Data validation sign-off (Business Teams) before assessment
- Validate counter = 0 before submission
- Approver review before Completed status

### 1.3 Role Quick Reference

| Role | Phase Involvement | Maker / Checker |
|------|-----------------|----------------|
| ICRM Methodology Team | Phases 1, 2, 6 | Maker for planning & reports; Checker for data |
| Enterprise Compliance & Assurance | Phases 1, 2, 3, 4, 5 | Maker for assessment; Checker for approval |
| Technology Team (GFT) | Phases 2, ongoing support | Maker for data extraction |
| Business Teams | Phases 2, 3, 7 | Maker for data validation; evidence provider |
| Preparer | Phase 3, 4 | Maker — assessment execution |
| Approver | Phase 3, 4 | Checker — assessment review and completion |
| Senior Management / BRCC | Phase 6 | Consumer / strategic decision-maker |
| Internal Audit | Phase 3 | Evidence contributor |

---

## 2. Phase 1 — Annual Planning & Scoping

[TYPE: PROCEDURE]

**Purpose:** Define the "what, who, and when" for the upcoming CRA cycle. Establish a comprehensive, relevant, and properly scoped assessment before any data collection or assessment work begins. ✅

---

### Workflow 1.1 — Review ICRM Assessment Entity Universe

**Goal:** Ensure the GAU-SAU universe is current and reflects the organisation's actual structure.

| Step | Action | Actor |
|------|--------|-------|
| 1 | Review existing list of GAUs and SAUs | ICRM Methodology Team |
| 2 | Add newly formed business units; remove decommissioned ones | ICRM Methodology Team |
| 3 | Reflect organisational structure changes in the universe | ICRM Methodology Team |
| 4 | Consult Business Teams to confirm GAU mapping accuracy | Business Teams (contributors) |
| 5 | Propose updated universe for approval | ICRM Methodology Team (**Maker**) |
| 6 | Review and approve the universe for their areas | Enterprise Compliance + ICRM Product/Function Teams (**Checker**) |

**Deliverable:** Updated and approved GAU-SAU hierarchy in the CRA application. ✅

---

### Workflow 1.2 — Review & Update Methodology

**Goal:** Ensure the methodology reflects current regulations, industry practices, and lessons learned.

| Step | Action | Actor |
|------|--------|-------|
| 1 | Review methodology for measuring inherent risk and control effectiveness | Enterprise Compliance |
| 2 | Incorporate changes based on new regulations, industry practices, lessons learned | Enterprise Compliance |
| 3 | Update official methodology documents | ICRM Methodology Team |
| 4 | Propose methodology changes | Enterprise Compliance (**Maker**) |
| 5 | Review and approve through senior governance forum (e.g., methodology council) | Senior ICRM governance forum (**Checker**) |

**Deliverable:** Approved 2025 methodology document (published to SharePoint by October 31). ✅

---

### Workflow 1.3 — Establish Annual Schedule

**Goal:** Create and communicate the detailed cycle timeline.

| Step | Action | Actor |
|------|--------|-------|
| 1 | Create detailed timeline from data collection to final reporting | ICRM Methodology Team (**Maker**) |
| 2 | Review and approve the schedule | Enterprise Compliance leadership (**Checker**) |
| 3 | Communicate the schedule to all stakeholders | ICRM Methodology Team |

**Deliverable:** Official CRA timeline communicated to all participants. ✅
**2025 Key Date:** Tool open: November 3, 2025. All materials available by October 31, 2025. ✅

---

## 3. Phase 2 — Data Collection & Validation

[TYPE: PROCEDURE]

**Purpose:** Gather accurate and complete data, which is the quantitative foundation of a credible inherent risk and control environment assessment. ✅ The integrity of every downstream calculation depends on this phase.

---

### Workflow 2.1 — Data Extraction

**Goal:** Extract and load upstream system data into the CRA platform staging area.

| Step | Action | Actor |
|------|--------|-------|
| 1 | Run processes to extract data from source systems (MCA, Reg Inventory, ICAPS, PARCUM) | Technology Team (GFT) (**Maker** — automated and manual) |
| 2 | Load data into CRA platform staging area | Technology Team (GFT) |
| 3 | Verify that all data feeds have run successfully | ICRM Methodology Team (**Checker**) |
| 4 | Confirm data date lock (October 13, 2025 for 2025 cycle) | ICRM Methodology Team |

**Data date:** October 13, 2025 ✅ — single snapshot, non-negotiable.

**Deliverable:** Raw upstream data loaded into CRA platform, validated as complete. ✅

---

### Workflow 2.2 — Data Validation by Business

**Goal:** Ensure Business Teams confirm the accuracy of data representing their entities.

| Step | Action | Actor |
|------|--------|-------|
| 1 | Provide Business Teams access to review their Assessment Entity data | ICRM Methodology Team |
| 2 | Review the completeness and accuracy of data for their GAU | Business Team (**Maker**) |
| 3 | Report any discrepancies to ICRM Methodology and Technology Teams | Business Team |
| 4 | Investigate and correct reported discrepancies | Technology Team (GFT) + ICRM Methodology Team |
| 5 | Sign off on data accuracy | Business Team manager or designated data owner (**Checker**) |

**Deliverable:** Business sign-off on data accuracy for each GAU. ✅

**⚠️ Critical note:** No CRA assessment can be considered accurate until the underlying data is validated. If data is found incomplete, the assessment must be paused or re-run after correction. ✅

---

## 4. Phase 3 — Risk & Control Assessment

[TYPE: PROCEDURE]

**Purpose:** Perform the core analytical assessment of inherent risk and control environment within the CRC tool using the 2025 hybrid methodology. ✅

---

### Workflow 3.1 — Inherent Risk Assessment

**Goal:** Determine the Inherent Risk tier for each GAU across all L2/L1/Overall levels.

| Step | Action | Actor |
|------|--------|-------|
| 1 | Open GAU assessment in CRC tool (Status: Not Started → Started) | Preparer |
| 2 | Select **Business Activity rating** (High / Medium / Low) using data kit and SME knowledge | Preparer (**Maker**) |
| 3 | Review system-generated publication-level IR ratings (auto-calculated from Reg Inventory + MCA + PARCUM) | Preparer |
| 4 | Review "L2 Rating Based on Publication" (quantitative summary) for each L2 risk stripe | Preparer |
| 5 | Reference supplemental data (SharePoint data kit, enforcement actions, third-party dashboard) | Preparer |
| 6 | Manually assign **L2 Current Rating** (qualitative) for each L2 risk stripe | Preparer (**Maker**) |
| 7 | Enter mandatory written **rationale** for every L2 rating assigned | Preparer (**Maker**) |
| 8 | Review system-calculated L1 roll-up ratings | Preparer |
| 9 | Optionally adjust L1 rating with qualitative override + mandatory rationale | Preparer |
| 10 | Review overall GAU IR rating | Preparer |
| 11 | Save all entries (triggers recalculation of downstream ratings) | Preparer |
| 12 | Review the Inherent Risk ratings for accuracy and consistency | Senior Compliance analyst (**Checker**) |

**Optional — L3 Assessment:** L3 risks default to "Not Assessed." Preparer may voluntarily rate L3s; they contribute to an "L2 Rating Based on L3s" display but are not required by the 2025 methodology. ✅

**Deliverable:** Populated Inherent Risk ratings at L2, L1, and overall GAU levels with mandatory rationales. ✅

---

### Workflow 3.2 — Control Environment Assessment

**Goal:** Determine the Control Environment rating for each GAU across all L2/L1/Overall levels.

| Step | Action | Actor |
|------|--------|-------|
| 1 | Navigate to Control Environment section of the assessment in CRC tool | Preparer |
| 2 | Review system-generated Strength of Controls ratings per publication (Strong / Adequate / Weak — from MCA design attributes decision tree) | Preparer |
| 3 | Review Control Effectiveness ratings per publication (from ICAPS issues waterfall) | Preparer |
| 4 | Review mapped issues list for each L2 | Preparer |
| 5 | Optionally add issues (search ICAPS) or untag irrelevant system-mapped issues (note: does NOT affect systemic CE calculation) | Preparer |
| 6 | Review supplemental CE data (Data Compliance Concerns data kit; audit findings; MCA ratings; testing results) | Preparer |
| 7 | Manually assign **L2 Current CE Rating** (Effective / Partially Ineffective / Ineffective) | Preparer (**Maker**) |
| 8 | Enter mandatory written **rationale** for every L2 CE rating | Preparer (**Maker**) |
| 9 | Review system-calculated L1 CE roll-up (highest-deficiency waterfall) | Preparer |
| 10 | Optionally adjust L1 CE rating with qualitative override + mandatory rationale | Preparer |
| 11 | Review overall GAU CE rating | Preparer |
| 12 | Save all entries | Preparer |
| 13 | Review CE ratings for accuracy and adequacy of control evidence | Senior Compliance analyst (**Checker**) |

**Deliverable:** Populated Control Environment ratings at L2, L1, and overall GAU levels with mandatory rationales. ✅

---

## 5. Phase 4 — Residual Risk Determination

[TYPE: PROCEDURE]

**Purpose:** Calculate the final risk rating after considering controls and document the overall conclusion. ✅

---

### Workflow 4.1 — Automated Residual Risk Calculation

| Step | Action | Actor |
|------|--------|-------|
| 1 | CRA platform automatically calculates Residual Risk Tier at publication, L2, L1, and GAU levels | CRA Application (**Maker** — systemic, non-modifiable) |
| 2 | Review calculated residual risk ratings at all levels | Preparer |
| 3 | If residual risk appears inaccurate: re-evaluate L2/L1 IR or CE qualitative inputs (only path to changing residual risk) | Preparer |

**Key principle:** Residual Risk is 100% systemic. Assessors cannot directly adjust it. ✅

---

### Workflow 4.2 — Complete Conclusion (Residual Risk Section)

| Step | Action | Mandatory |
|------|--------|-----------|
| 1 | Write **Business Profile** — scope of GAU, org changes, strategy, third-party relationships | ✅ Yes |
| 2 | Write **Inherent Risk Rating Themes** — key drivers at GAU level | ✅ Yes |
| 3 | Write **Control Environment Rating Themes** — key CE drivers at GAU level | ✅ Yes |
| 4 | Write **Residual Risk Commentary** — overall result; year-on-year trends if applicable | ✅ Yes |
| 5 | Write **Monitoring & Testing (Recommendation)** — specific information for CIA testing/monitoring team | ✅ Yes |
| 6 | Write **Legal Entity Considerations** — Tier 1/2 legal entity highlights | Optional |
| 7 | Write **Other Recommendations** — any additional information | Optional |

---

### Workflow 4.3 — Validation & Submission

| Step | Action | Actor |
|------|--------|-------|
| 1 | Navigate to Validate section in CRC tool | Preparer |
| 2 | Click through each flagged missing item; the tool navigates directly to the missing field | Preparer |
| 3 | Complete all outstanding mandatory fields until validate counter = 0 | Preparer |
| 4 | Click Save (Submit button becomes active only when counter = 0) | Preparer |
| 5 | Click Submit → Status changes to **Pending Approval** | Preparer (**Maker** — submission complete) |

**⚠️ Important:** Save frequently during assessment work. The CRC tool recalculates downstream ratings on each Save. Looking at the screen without saving will not reflect the current calculation state. ✅

---

### Workflow 4.4 — Qualitative Override (Governance-Gated)

For cases where the calculated residual risk does not accurately reflect the true risk profile: ✅

| Step | Action | Actor |
|------|--------|-------|
| 1 | Identify discrepancy between calculated and appropriate residual risk | Enterprise Compliance analyst |
| 2 | Draft justification for the override with supporting evidence | Enterprise Compliance analyst (**Maker**) |
| 3 | Submit proposed override and justification to designated senior approver or committee | Enterprise Compliance analyst |
| 4 | Review and approve or reject the override | Senior manager or governance committee (**Checker/Approver**) |
| 5 | If approved: apply override; document final Residual Risk rating | Enterprise Compliance |

---

## 6. Phase 5 — Results Review & Socialization

[TYPE: PROCEDURE]

**Purpose:** Share draft results with stakeholders, identify outliers, ensure consistent methodology application, and prepare for aggregation. ✅

---

### Workflow 5.1 — Calibration Sessions

**Goal:** Identify methodology inconsistencies and prepare for aggregated reporting.

| Step | Action | Actor |
|------|--------|-------|
| 1 | Conduct calibration sessions across Lines of Business and Functions | Enterprise Compliance + ICRM |
| 2 | Conduct calibration across risk taxonomy stripes | Enterprise Compliance |
| 3 | Conduct calibration across product Chief Compliance Officers | Enterprise Compliance |
| 4 | Identify outliers — ratings that appear inconsistent with methodology | Enterprise Compliance |
| 5 | Resolve outliers (adjust ratings with appropriate rationale or document justification for unusual ratings) | Enterprise Compliance |

**Deliverable:** Calibrated, consistent ratings ready for aggregation. ✅

---

### Workflow 5.2 — Stakeholder Socialization of Draft Results

| Step | Action | Actor |
|------|--------|-------|
| 1 | Generate draft aggregated reports at country, regional, and global business levels | ICRM Methodology Team (**Maker**) |
| 2 | Distribute draft reports to ICRM partners and Business stakeholders | ICRM Methodology Team |
| 3 | Review draft reports for accuracy and contextual appropriateness | Enterprise Compliance + Business stakeholders (**Checker**) |
| 4 | Provide feedback and corrections | Business stakeholders |
| 5 | Incorporate feedback into final reports | ICRM Methodology Team |

---

## 7. Phase 6 — Final Reporting & Aggregation

[TYPE: PROCEDURE]

**Purpose:** Consolidate results for senior management and governance bodies. ✅

---

### Workflow 6.1 — Results Aggregation

| Step | Action | Actor |
|------|--------|-------|
| 1 | Use CRA platform aggregation engine to roll up individual GAU ratings to country/region/global | ICRM Methodology Team |
| 2 | Apply risk-weighted average aggregation logic (inherent risks and controls) | CRA Application (systemic) |
| 3 | Prepare final reports with aggregated ratings and commentary | ICRM Methodology Team (**Maker**) |
| 4 | Review final reports for accuracy | Enterprise Compliance + Business stakeholders (**Checker**) |

---

### Workflow 6.2 — Governance Reporting

| Step | Action | Audience |
|------|--------|---------|
| 1 | Present results to **CBNA-GBRCC** | Senior Management |
| 2 | Include results in **State of Risk report** for Board of Directors | Board |
| 3 | Socialize at all BRCC and control function meetings across Lines of Business and Global Functions | All Lines of Business |

**Deliverables:** Final CRA reports; board-level presentations; BRCC presentations. ✅

---

## 8. Phase 7 — Action Planning & Monitoring

[TYPE: PROCEDURE]

**Purpose:** Use CRA results to drive concrete risk reduction activities and compliance assurance planning. ✅

---

### Workflow 7.1 — Compliance Monitoring & Testing Plan Development

| Step | Action | Actor |
|------|--------|-------|
| 1 | Use CRA residual risk results as primary input for annual Compliance Monitoring and Testing plans | Compliance Monitoring / Testing Teams (**Maker**) |
| 2 | Reference Recommendation fields from individual CRA conclusions for specific testing scope | Compliance Monitoring / Testing Teams |
| 3 | Review and approve monitoring and testing plans | Enterprise Compliance + Senior business management (**Checker**) |

---

### Workflow 7.2 — Corrective Action Plan (CAP) Development

| Step | Action | Actor |
|------|--------|-------|
| 1 | Identify business units with high residual risk or Ineffective / Partially Ineffective control environments | ICRM + Enterprise Compliance |
| 2 | Require formal CAPs from identified business units | Enterprise Compliance |
| 3 | Develop and document CAPs for identified control weaknesses | Business Teams (**Maker**) |
| 4 | Track CAPs in the designated system (ICAPS) | Business Teams |
| 5 | Review and approve CAPs | Enterprise Compliance + Senior business management (**Checker**) |
| 6 | Monitor CAP execution and closure | ICRM Methodology Team |

**Deliverable:** Formal, tracked CAPs for all identified high-risk or control-deficient business units. ✅

---

## 9. CRC Tool: Preparer Step-by-Step Execution

[TYPE: PROCEDURE]

This section provides a click-level guide for Preparers executing their assessment in the CRC module. ✅

### 9.1 Access & Entitlement
- Request entitlement: City Marketplace → search "City Risk and Controls" → look for "Risk Assessment" → request access before November 3rd ✅
- Navigate to: Assessments → Independent Challenge → Compliance Risk Assessment

### 9.2 Starting the Assessment
1. Locate your GAU by ID or name in the 2025 CRA Cycle dashboard search bar
2. Confirm status: **Not Started**
3. Click **Start Assessment**
4. A prompt appears requesting **Business Activity rating**

### 9.3 Business Activity Rating
1. Review provided data kit on SharePoint (FTE counts, process counts, MCA business environment distribution)
2. Read definitions embedded in the CRC UI (High / Medium / Low)
3. Select your rating — this is applied across all publications in the GAU
4. **Note:** You can change this rating while the assessment is in Draft status; any change recalculates all publication-level ratings

### 9.4 Inherent Risk Assessment

```
For each L2 risk stripe:
1. View "L2 Rating Based on Publication" — the systemic quantitative summary
2. Optional: View "L2 Rating Based on L3s" if you chose to rate L3s
3. Click the L2 dropdown → select your Current L2 Rating
4. Enter mandatory rationale in the text box that appears
5. Click Save (triggers recalculation)
6. Repeat for all L2 risk stripes within each L1

After all L2s are rated:
7. Review auto-populated L1 rating
8. Optionally adjust L1 rating with dropdown + mandatory rationale
9. Click Save
10. Review overall GAU inherent risk rating
11. Optionally adjust with qualitative override + rationale
```

### 9.5 Navigating Publication Detail
- Click any publication to view: Calculated Severity, Significance of Impact scores, Likelihood factors (Business Activity, Complexity, Applicability)
- Click on individual obligations to view Reg Inventory detail
- Use the navigation bar to filter by L2/L1 or by publication

### 9.6 Control Environment Assessment
1. Navigate to **Control Environment** section via the navigation bar
2. For each L2: view system-generated CE ratings (Strength of Controls + Control Effectiveness) per publication
3. Click **Mapped Issues** link to review system-mapped issues from ICAPS
4. Optionally: click **Add Issue** (search ICAPS) or untag irrelevant issues
5. Reference Data Compliance Concerns data kit from SharePoint
6. Select L2 Current CE Rating from dropdown → enter mandatory rationale → Save
7. Repeat for all L2 stripes; review L1 CE roll-up; optionally adjust

### 9.7 Residual Risk Section (Conclusion)
1. Navigate to **Residual Risk** via the left navigation bar
2. Review the systemic residual risk ratings at all levels (non-modifiable)
3. Complete all mandatory conclusion fields (marked with asterisk *)
4. Use the Validate section to identify any remaining missing fields
5. Navigate directly to missing fields by clicking the flagged item in Validate

### 9.8 Submission
1. Ensure all mandatory fields complete (Validate counter = 0)
2. Click **Save**
3. Click **Submit** (only active when counter = 0)
4. Status changes to **Pending Approval**

### 9.9 Supporting Features
- **Attachments:** Upload supporting documents in the Attachments section to support the assessment
- **Workflow History:** View timestamped log of all actions (who did what and when)
- **Third-Party Dashboard:** Access from Aggregation tab — filter by GRC level, third-party name, or GAU

---

## 10. CRC Tool: Approver Step-by-Step Execution

[TYPE: PROCEDURE]

### 10.1 Accessing Pending Assessments
1. Navigate to the 2025 CRA Cycle dashboard in CRC
2. Filter by Status: **Pending Approval**
3. Locate the assessment(s) assigned for your review

### 10.2 Review Checklist
For each assessment:
- [ ] Business Activity rating is appropriate for the GAU's scale and profile
- [ ] All L2 IR ratings are assigned and supported by adequate rationale
- [ ] L1 IR ratings are consistent with the underlying L2 assessments
- [ ] All L2 CE ratings are assigned with adequate rationale and evidence reference
- [ ] Mapped issues are reasonably addressed in CE rationale
- [ ] Conclusion section is fully complete (all mandatory fields)
- [ ] Business Profile describes the GAU scope accurately
- [ ] Recommendation field provides actionable information for CIA colleagues

### 10.3 Approver Decision
| Decision | Action | Outcome |
|----------|--------|---------|
| **Approve** | Click Approve | Status → **Completed** |
| **Return** | Click Return with explanation | Status → **Draft** (Preparer notified) |

---

## 11. Escalation & Exception Handling

[TYPE: PROCEDURE]

| Situation | Action | Escalation Path |
|-----------|--------|----------------|
| Data feed failed at snapshot date | Pause affected assessments; report to ICRM Methodology Team and GFT | ICRM Methodology Team → GFT Support Manager |
| Business Team cannot validate data by deadline | Escalate data quality issue; document impact; may require timeline extension | Enterprise Compliance → ICRM Methodology Team |
| Application outage during active window | Log support ticket; GFT SLA-based response | GFT Support Manager (SLA enforcement) |
| Preparer and Approver disagree on rating | Escalate to Enterprise Compliance senior manager | Enterprise Compliance leadership |
| Calculated residual risk appears inappropriate | Initiate qualitative override process (Workflow 4.4) | Enterprise Compliance analyst → Senior manager / committee |
| Missing entitlement prevents access | Request via City Marketplace before November 3rd | City Marketplace entitlement team |

---

## 12. Sources & Cross-References

[TYPE: REFERENCE]

### Primary Sources

| Source Document | Content Sourced |
|----------------|----------------|
| `CRA_Workflows.md` | All seven major phases; minor workflows; maker/checker role assignments |
| `CRA_User_and_Roles.md` | Role responsibilities; workflow interaction points; deliverables per role |
| `2025_CRA_Training_Transcript.md` | CRC tool walkthrough; Preparer/Approver execution; tool-specific steps; validation counter |
| `CRA_Study_Guide.md` | Phase definitions; assessment principles; data governance rules |
| `notes.md` | Tool opening date; meeting notes on process; snapshot architecture |

### Cross-References

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [BASED_ON] | CRA-002 Technical Reference | All | Methodology that this procedure executes |
| [CONTEXTUALISED_BY] | CRA-006 System Context Pack | Sections 3-5 | Actor roles and integration context |
| [GOVERNED_BY] | RULES-001 Documentation Standards | All | Document format |
| [GOVERNED_BY] | Truth & Verification Standards | Sections 1-4 | Verification label protocol |

---

## 13. Revision History

[TYPE: REFERENCE]

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-12 | Initial | Document created from synthesis of five primary project source documents | Project documentation generation from all provided resources |

---

*TMPL-003 v1.0 — Procedure & Workflow Runbook | Document: CRA-003 | Parent: RULES-001 Documentation Standards*
*All procedural steps sourced from primary project documents. Verification labels applied throughout. No fabricated steps.*
