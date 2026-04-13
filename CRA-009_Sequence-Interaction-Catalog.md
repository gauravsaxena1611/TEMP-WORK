# Compliance Risk Assessment (CRA): Sequence & Interaction Catalog
## Critical Business Flows & Swimlane Diagrams — CSI-180236

```yaml
---
# ── RULES-001 STANDARD FIELDS ────────────────────────────────
document_id: "CRA-009 Sequence & Interaction Catalog"
title: "CRA — Sequence & Interaction Catalog"
version: "1.0"
created: "2026-04-12"
status: "Final"
parent_document: "CRA-006 System Context Pack"
template_version_used: "TMPL-009 v2.0"

# ── METHODOLOGY PHASE ─────────────────────────────────────────
methodology_phase: "Phase-5-DetailedDesign"
design_methodology_ref: "Documentation_Standards.md, Section 3"

# ── SYSTEM BACK-REFERENCE ─────────────────────────────────────
system_id: "SYS-CRA-180236"
system_name: "Compliance Risk Assessment (CRA)"
context_pack_ref: "CRA-006 System Context Pack"
container_inventory_ref: "CRA-007 Container & Component Inventory"
data_flow_ref: "CRA-008 Data & Flow Inventory"

# ── PARTICIPANT REGISTER ──────────────────────────────────────
participants:
  - id: "PAR-001"
    name: "Preparer"
    type: "actor"
    source_id: "ACT-001"
    source_doc: "CRA-006"
    stereotype: "<<actor>>"

  - id: "PAR-002"
    name: "Approver"
    type: "actor"
    source_id: "ACT-002"
    source_doc: "CRA-006"
    stereotype: "<<actor>>"

  - id: "PAR-003"
    name: "ICRM Methodology Team"
    type: "actor"
    source_id: "ACT-003"
    source_doc: "CRA-006"
    stereotype: "<<actor>>"

  - id: "PAR-004"
    name: "Technology Team (GFT)"
    type: "actor"
    source_id: "ACT-005"
    source_doc: "CRA-006"
    stereotype: "<<actor>>"

  - id: "PAR-005"
    name: "Business Teams"
    type: "actor"
    source_id: "ACT-008"
    source_doc: "CRA-006"
    stereotype: "<<actor>>"

  - id: "PAR-006"
    name: "CRA UI / Assessment Interface"
    type: "container"
    source_id: "CNT-001"
    source_doc: "CRA-007"
    stereotype: "<<web-app>>"

  - id: "PAR-007"
    name: "CRA Rating Results Service"
    type: "container"
    source_id: "CNT-002"
    source_doc: "CRA-007"
    stereotype: "<<service>>"

  - id: "PAR-008"
    name: "CRA Scoping Service"
    type: "container"
    source_id: "CNT-003"
    source_doc: "CRA-007"
    stereotype: "<<service>>"

  - id: "PAR-009"
    name: "CRA Data Snapshot Store"
    type: "data-store"
    source_id: "CNT-006"
    source_doc: "CRA-007"
    stereotype: "<<database>>"

  - id: "PAR-010"
    name: "CRA Assessment Store"
    type: "data-store"
    source_id: "DS-001"
    source_doc: "CRA-008"
    stereotype: "<<database>>"

  - id: "PAR-011"
    name: "MCA"
    type: "external-system"
    source_id: "INT-002"
    source_doc: "CRA-006"
    stereotype: "<<external>>"

  - id: "PAR-012"
    name: "ICAPS"
    type: "external-system"
    source_id: "INT-004"
    source_doc: "CRA-006"
    stereotype: "<<external>>"

  - id: "PAR-013"
    name: "Regulatory Inventory"
    type: "external-system"
    source_id: "INT-005"
    source_doc: "CRA-006"
    stereotype: "<<external>>"

  - id: "PAR-014"
    name: "CRA Aggregation Service"
    type: "container"
    source_id: "CNT-004"
    source_doc: "CRA-007"
    stereotype: "<<service>>"

  - id: "PAR-015"
    name: "CRA Reporting Service"
    type: "container"
    source_id: "CNT-007"
    source_doc: "CRA-007"
    stereotype: "<<service>>"

  - id: "PAR-016"
    name: "Senior Management / BRCC / Board"
    type: "actor"
    source_id: "ACT-006"
    source_doc: "CRA-006"
    stereotype: "<<actor>>"

# ── INTERACTION FLOWS ─────────────────────────────────────────
flows:
  - id: "SEQ-001"
    name: "Annual Data Snapshot Extract"
    type: "sequence"
    priority: "critical"
    trigger: "GFT runs annual data extraction jobs at the designated data date (October 13)"
    preconditions:
      - "All upstream systems (MCA, Reg Inventory, ICAPS, PARCUM) are available"
      - "Annual cycle is scheduled and confirmed by ICRM Methodology Team"
    postconditions:
      - "CRA Data Snapshot Store populated with reconciled data from all upstream systems"
      - "ICRM Methodology Team confirms all feeds ran successfully"
      - "Data date locked — snapshot is read-only for assessment window"
    happy_path: true
    error_flows: ["SEQ-001E"]
    sla_target: "Completed before cycle open date (November 3)"
    participants_used: ["PAR-004", "PAR-011", "PAR-013", "PAR-012", "PAR-009", "PAR-003"]
    steps:
      - seq: 1
        from_id: "PAR-003"
        to_id: "PAR-004"
        message: "Initiate annual data extraction for data date October 13"
        message_type: "sync-request"
        protocol: "internal"
        data_payload: "Data date: October 13; source systems: MCA, Reg Inventory, ICAPS, PARCUM"
        data_classification: "internal"
        response_message: "Extraction jobs queued"
        response_seq: "1"
        is_async: false
        error_condition: "Source system unavailable"
        error_handling: "GFT escalates to ICRM; affected assessments paused"
        verification: "✅"

      - seq: 2
        from_id: "PAR-004"
        to_id: "PAR-011"
        message: "Extract MCA data: SoI, design attributes, obligation mappings, GAU mapping"
        message_type: "sync-request"
        protocol: "Internal batch extract"
        data_payload: "Publication-level MCA data snapshot"
        data_classification: "internal"
        response_message: "MCA data payload"
        response_seq: "2"
        is_async: false
        error_condition: "MCA data extract fails"
        error_handling: "Retry; escalate to ICRM if persistent"
        verification: "✅"

      - seq: 3
        from_id: "PAR-004"
        to_id: "PAR-013"
        message: "Extract Regulatory Inventory: publication tiers, obligation mappings"
        message_type: "sync-request"
        protocol: "Internal batch extract"
        data_payload: "Publication tier data and obligation-to-L2 mappings"
        data_classification: "internal"
        response_message: "Reg Inventory snapshot payload"
        response_seq: "3"
        is_async: false
        error_condition: "Reg Inventory extract fails"
        error_handling: "Retry; escalate to ICRM"
        verification: "✅"

      - seq: 4
        from_id: "PAR-004"
        to_id: "PAR-012"
        message: "Extract ICAPS open issues: issue ID, severity, obligation IDs, GAU-publication pairs"
        message_type: "sync-request"
        protocol: "Internal batch extract"
        data_payload: "Open issues snapshot as of data date"
        data_classification: "internal"
        response_message: "ICAPS issues snapshot"
        response_seq: "4"
        is_async: false
        error_condition: "ICAPS extract fails"
        error_handling: "Retry; escalate"
        verification: "✅"

      - seq: 5
        from_id: "PAR-004"
        to_id: "PAR-009"
        message: "Load all extracted data into CRA Data Snapshot Store; lock as read-only"
        message_type: "sync-request"
        protocol: "Internal write"
        data_payload: "MCA + Reg Inventory + ICAPS + PARCUM reconciled payloads"
        data_classification: "internal"
        response_message: "Snapshot committed; data date locked"
        response_seq: "5"
        is_async: false
        error_condition: "Load failure"
        error_handling: "Rollback; retry; escalate"
        verification: "✅"

      - seq: 6
        from_id: "PAR-004"
        to_id: "PAR-003"
        message: "Confirm all feeds successful; snapshot committed for data date October 13"
        message_type: "return"
        protocol: "internal"
        data_payload: "Extraction success confirmation"
        data_classification: "internal"
        response_message: ""
        response_seq: ""
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "✅"

    swimlanes:
      - lane_name: "ICRM Methodology Team"
        participant_ids: ["PAR-003"]
      - lane_name: "GFT Technology Team"
        participant_ids: ["PAR-004"]
      - lane_name: "Upstream Systems"
        participant_ids: ["PAR-011", "PAR-013", "PAR-012"]
      - lane_name: "CRA Platform"
        participant_ids: ["PAR-009"]

    verification: "✅"

  - id: "SEQ-001E"
    name: "Annual Data Snapshot — Feed Failure (Error Flow)"
    type: "sequence"
    priority: "critical"
    trigger: "One or more upstream data feed extractions fail at data date"
    preconditions: ["SEQ-001 step 2, 3, or 4 fails"]
    postconditions:
      - "Failed feed identified and reported"
      - "Affected assessments paused until data corrected"
      - "Assessment cycle timeline reviewed"
    happy_path: false
    error_flows: []
    sla_target: "Resolution before cycle open date"
    participants_used: ["PAR-004", "PAR-003"]
    steps:
      - seq: 1
        from_id: "PAR-004"
        to_id: "PAR-003"
        message: "Escalate: [source system] feed failed — snapshot incomplete"
        message_type: "sync-request"
        protocol: "internal"
        data_payload: "Failed system ID; error details"
        data_classification: "internal"
        response_message: "Decision: pause affected assessments or retry"
        response_seq: "1"
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "✅"

    swimlanes:
      - lane_name: "GFT Technology Team"
        participant_ids: ["PAR-004"]
      - lane_name: "ICRM Methodology Team"
        participant_ids: ["PAR-003"]

    verification: "✅"

  - id: "SEQ-002"
    name: "GAU Assessment — Happy Path (Preparer to Completed)"
    type: "both"
    priority: "critical"
    trigger: "Preparer opens CRC tool and starts assessment for their GAU on or after November 3"
    preconditions:
      - "Data snapshot loaded and locked (SEQ-001 complete)"
      - "Preparer has valid City Marketplace entitlement for CRC Risk Assessment module"
      - "GAU universe confirmed by ICRM Methodology Team (Phase 1)"
      - "Business data validated by Business Teams (Phase 2)"
    postconditions:
      - "Assessment status = Completed"
      - "All mandatory fields populated (validate counter = 0)"
      - "Approver has reviewed and approved"
      - "Assessment available for aggregation"
    happy_path: true
    error_flows: ["SEQ-002E"]
    sla_target: "Completed before Q1 close"
    participants_used: ["PAR-001", "PAR-002", "PAR-006", "PAR-007", "PAR-008", "PAR-009", "PAR-010"]
    steps:
      - seq: 1
        from_id: "PAR-001"
        to_id: "PAR-006"
        message: "Navigate to 2025 CRA Cycle dashboard; search for GAU by ID/name; click Start Assessment"
        message_type: "sync-request"
        protocol: "HTTPS"
        data_payload: "GAU identifier"
        data_classification: "internal"
        response_message: "Assessment created; Business Activity prompt displayed"
        response_seq: "1"
        is_async: false
        error_condition: "No entitlement"
        error_handling: "Redirect to City Marketplace entitlement request"
        verification: "✅"

      - seq: 2
        from_id: "PAR-001"
        to_id: "PAR-006"
        message: "Select Business Activity rating (High/Medium/Low) using data kit and SME judgment"
        message_type: "sync-request"
        protocol: "HTTPS"
        data_payload: "Business Activity: High | Medium | Low"
        data_classification: "internal"
        response_message: "Rating applied; publication-level calculations triggered"
        response_seq: "2"
        is_async: false
        error_condition: ""
        error_handling: ""
        alt_fragment: "Preparer can change this during Draft status; change triggers recalculation of all publication ratings"
        verification: "✅"

      - seq: 3
        from_id: "PAR-006"
        to_id: "PAR-007"
        message: "Apply Business Activity across all GAU publications; trigger IR and CE systemic calculations"
        message_type: "sync-request"
        protocol: "Internal"
        data_payload: "Business Activity rating + GAU_id"
        data_classification: "internal"
        response_message: "Publication-level IR tiers and CE ratings calculated"
        response_seq: "3"
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "💡"

      - seq: 4
        from_id: "PAR-007"
        to_id: "PAR-009"
        message: "Read snapshot data: publication tiers, SoI, design attributes, complexity, applicability, issues"
        message_type: "sync-request"
        protocol: "Internal read"
        data_payload: "All snapshot data for this GAU's scoped publications"
        data_classification: "internal"
        response_message: "Snapshot data for GAU publications"
        response_seq: "4"
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "✅"

      - seq: 5
        from_id: "PAR-007"
        to_id: "PAR-006"
        message: "Return calculated publication-level IR tiers and CE ratings for display"
        message_type: "return"
        protocol: "Internal"
        data_payload: "Per-publication: IR tier, SoI, Complexity, Applicability, SOC rating, CEff rating, CE rating, RR tier"
        data_classification: "internal"
        response_message: ""
        response_seq: ""
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "✅"

      - seq: 6
        from_id: "PAR-006"
        to_id: "PAR-001"
        message: "Display assessment: L2 Rating Based on Publication; publication detail; mapped issues"
        message_type: "return"
        protocol: "HTTPS"
        data_payload: "Publication ratings, L2 quantitative summaries, issue lists, supplemental data kit links"
        data_classification: "internal"
        response_message: ""
        response_seq: ""
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "✅"

      - seq: 7
        from_id: "PAR-001"
        to_id: "PAR-006"
        message: "Assign L2 Current IR Rating (dropdown) and enter mandatory rationale for each L2 risk stripe; Save"
        message_type: "sync-request"
        protocol: "HTTPS"
        data_payload: "L2 risk stripe ID, IR current rating (Tier 1-5), rationale text"
        data_classification: "internal"
        response_message: "L1 roll-up recalculated; display updated"
        response_seq: "7"
        is_async: false
        error_condition: "Rationale missing"
        error_handling: "Validation counter increments; submission blocked"
        alt_fragment: "Repeat for each L2 risk stripe; optionally override L1 rating"
        verification: "✅"

      - seq: 8
        from_id: "PAR-001"
        to_id: "PAR-006"
        message: "Assign L2 Current CE Rating and enter mandatory rationale for each L2 risk stripe; Save"
        message_type: "sync-request"
        protocol: "HTTPS"
        data_payload: "L2 risk stripe ID, CE current rating (Effective/Partially Ineffective/Ineffective), rationale text"
        data_classification: "internal"
        response_message: "CE L1 roll-up (highest-deficiency waterfall) recalculated; display updated"
        response_seq: "8"
        is_async: false
        error_condition: "Rationale missing"
        error_handling: "Validation counter increments; submission blocked"
        verification: "✅"

      - seq: 9
        from_id: "PAR-006"
        to_id: "PAR-007"
        message: "Store qualitative ratings; recalculate L1 and overall GAU IR, CE, and RR"
        message_type: "sync-request"
        protocol: "Internal"
        data_payload: "L2 qualitative ratings and rationale"
        data_classification: "internal"
        response_message: "Updated L1 and GAU ratings; Residual Risk tiers (read-only)"
        response_seq: "9"
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "💡"

      - seq: 10
        from_id: "PAR-001"
        to_id: "PAR-006"
        message: "Complete Conclusion section: Business Profile, IR Themes, CE Themes, RR Commentary, Monitoring & Testing Recommendation; Save"
        message_type: "sync-request"
        protocol: "HTTPS"
        data_payload: "Five mandatory text fields; two optional text fields"
        data_classification: "internal"
        response_message: "Fields saved; validate counter updated"
        response_seq: "10"
        is_async: false
        error_condition: "Mandatory field empty"
        error_handling: "Field flagged with asterisk; validate counter retains count"
        verification: "✅"

      - seq: 11
        from_id: "PAR-001"
        to_id: "PAR-006"
        message: "Click Validate; review counter; navigate to any remaining flagged fields"
        message_type: "sync-request"
        protocol: "HTTPS"
        data_payload: "Validation request"
        data_classification: "internal"
        response_message: "Missing field list with direct navigation links; counter value"
        response_seq: "11"
        is_async: false
        error_condition: "Counter > 0"
        error_handling: "Preparer navigates to each flagged item; completes and saves"
        alt_fragment: "loop [until counter = 0]"
        verification: "✅"

      - seq: 12
        from_id: "PAR-001"
        to_id: "PAR-006"
        message: "Click Submit (active only when counter = 0)"
        message_type: "sync-request"
        protocol: "HTTPS"
        data_payload: "Submit action; assessor identity"
        data_classification: "internal-PII"
        response_message: "Assessment status → Pending Approval; Approver notified"
        response_seq: "12"
        is_async: false
        error_condition: "Counter > 0 at submit time"
        error_handling: "Submit button remains inactive; Preparer cannot proceed"
        verification: "✅"

      - seq: 13
        from_id: "PAR-006"
        to_id: "PAR-010"
        message: "Persist submitted assessment with status = Pending Approval; record preparer_id and submitted_at"
        message_type: "sync-request"
        protocol: "Internal write"
        data_payload: "Full assessment record"
        data_classification: "internal-PII"
        response_message: "Committed"
        response_seq: "13"
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "💡"

      - seq: 14
        from_id: "PAR-002"
        to_id: "PAR-006"
        message: "Open assessment in Pending Approval; review all ratings, rationales, and conclusion"
        message_type: "sync-request"
        protocol: "HTTPS"
        data_payload: "Approver review access"
        data_classification: "internal-PII"
        response_message: "Full assessment displayed for review"
        response_seq: "14"
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "✅"

      - seq: 15
        from_id: "PAR-002"
        to_id: "PAR-006"
        message: "Click Approve"
        message_type: "sync-request"
        protocol: "HTTPS"
        data_payload: "Approve action; approver identity"
        data_classification: "internal-PII"
        response_message: "Assessment status → Completed; approver_id and approved_at recorded"
        response_seq: "15"
        is_async: false
        error_condition: "Issues found requiring correction"
        error_handling: "Approver clicks Return; assessment status → Draft; Preparer notified (SEQ-002E)"
        verification: "✅"

    swimlanes:
      - lane_name: "Preparer"
        participant_ids: ["PAR-001"]
      - lane_name: "CRA Platform (UI + Rating Service)"
        participant_ids: ["PAR-006", "PAR-007", "PAR-008"]
      - lane_name: "Data Stores"
        participant_ids: ["PAR-009", "PAR-010"]
      - lane_name: "Approver"
        participant_ids: ["PAR-002"]

    verification: "✅"

  - id: "SEQ-002E"
    name: "GAU Assessment — Approver Returns to Preparer"
    type: "sequence"
    priority: "high"
    trigger: "Approver identifies issues requiring correction in Pending Approval assessment"
    preconditions: ["Assessment status = Pending Approval"]
    postconditions:
      - "Assessment status = Draft"
      - "Preparer notified of required corrections"
    happy_path: false
    error_flows: []
    sla_target: "Within assessment cycle timeline"
    participants_used: ["PAR-001", "PAR-002", "PAR-006", "PAR-010"]
    steps:
      - seq: 1
        from_id: "PAR-002"
        to_id: "PAR-006"
        message: "Click Return; provide correction notes"
        message_type: "sync-request"
        protocol: "HTTPS"
        data_payload: "Return action; correction guidance"
        data_classification: "internal"
        response_message: "Assessment status → Draft"
        response_seq: "1"
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "✅"

      - seq: 2
        from_id: "PAR-006"
        to_id: "PAR-001"
        message: "Notify Preparer: assessment returned; review Approver notes and resubmit"
        message_type: "return"
        protocol: "HTTPS / notification"
        data_payload: "Assessment ID; correction guidance"
        data_classification: "internal"
        response_message: ""
        response_seq: ""
        is_async: true
        error_condition: ""
        error_handling: ""
        verification: "💡"

    swimlanes:
      - lane_name: "Approver"
        participant_ids: ["PAR-002"]
      - lane_name: "CRA Platform"
        participant_ids: ["PAR-006"]
      - lane_name: "Preparer"
        participant_ids: ["PAR-001"]

    verification: "✅"

  - id: "SEQ-003"
    name: "Data Validation by Business Teams"
    type: "swimlane"
    priority: "critical"
    trigger: "ICRM Methodology Team grants Business Teams access to review their GAU data after snapshot load"
    preconditions:
      - "SEQ-001 (Data Snapshot Extract) complete"
      - "Business Teams identified and access granted"
    postconditions:
      - "Business Team manager sign-off on data accuracy"
      - "Any data discrepancies reported and resolved"
      - "Assessment for GAU may proceed"
    happy_path: true
    error_flows: []
    sla_target: "Before assessment window opens (November 3)"
    participants_used: ["PAR-003", "PAR-005", "PAR-004", "PAR-009"]
    steps:
      - seq: 1
        from_id: "PAR-003"
        to_id: "PAR-005"
        message: "Provide access to data for their Assessment Entity; request validation sign-off"
        message_type: "sync-request"
        protocol: "internal"
        data_payload: "Data access grant; validation request"
        data_classification: "internal"
        response_message: "Acknowledgement"
        response_seq: "1"
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "✅"

      - seq: 2
        from_id: "PAR-005"
        to_id: "PAR-009"
        message: "Review data for their GAU: transactional, customer, product data in snapshot"
        message_type: "sync-request"
        protocol: "Internal read access"
        data_payload: "Read request for GAU data"
        data_classification: "internal"
        response_message: "GAU data for review"
        response_seq: "2"
        is_async: false
        error_condition: "Data appears incomplete or inaccurate"
        error_handling: "Business team reports discrepancy to ICRM and GFT (step 3)"
        verification: "✅"

      - seq: 3
        from_id: "PAR-005"
        to_id: "PAR-003"
        message: "Report data discrepancy (if found); provide details of inaccuracy"
        message_type: "sync-request"
        protocol: "internal"
        data_payload: "Discrepancy description; affected data fields"
        data_classification: "internal"
        response_message: "Acknowledgement; GFT investigation initiated"
        response_seq: "3"
        is_async: false
        error_condition: ""
        error_handling: ""
        alt_fragment: "alt [if discrepancy found]; skip step 3 if data accurate"
        verification: "✅"

      - seq: 4
        from_id: "PAR-003"
        to_id: "PAR-004"
        message: "Escalate discrepancy for investigation and correction"
        message_type: "sync-request"
        protocol: "internal"
        data_payload: "Discrepancy details"
        data_classification: "internal"
        response_message: "Correction applied or explanation provided"
        response_seq: "4"
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "✅"

      - seq: 5
        from_id: "PAR-005"
        to_id: "PAR-003"
        message: "Sign off on data accuracy for GAU"
        message_type: "sync-request"
        protocol: "internal"
        data_payload: "Sign-off confirmation; data owner identity"
        data_classification: "internal-PII"
        response_message: "Sign-off recorded; GAU cleared for assessment"
        response_seq: "5"
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "✅"

    swimlanes:
      - lane_name: "ICRM Methodology Team"
        participant_ids: ["PAR-003"]
      - lane_name: "Business Teams"
        participant_ids: ["PAR-005"]
      - lane_name: "GFT Technology Team"
        participant_ids: ["PAR-004"]
      - lane_name: "CRA Snapshot Store"
        participant_ids: ["PAR-009"]

    verification: "✅"

  - id: "SEQ-004"
    name: "Post-Cycle Aggregation and Governance Reporting"
    type: "swimlane"
    priority: "high"
    trigger: "All GAU assessments reach Completed status; calibration sessions concluded"
    preconditions:
      - "All GAU assessments completed and approved"
      - "Calibration sessions held across Lines of Business and risk taxonomy stripes"
      - "Outlier ratings resolved"
    postconditions:
      - "Aggregated risk reports produced at country / regional / global level"
      - "Results presented at CBNA-GBRCC"
      - "State of Risk report updated for Board of Directors"
      - "CIA colleagues have Recommendation field data for testing plan"
    happy_path: true
    error_flows: []
    sla_target: "Q1 close"
    participants_used: ["PAR-003", "PAR-014", "PAR-015", "PAR-016"]
    steps:
      - seq: 1
        from_id: "PAR-003"
        to_id: "PAR-014"
        message: "Trigger aggregation: roll up all completed GAU ratings to country / region / global"
        message_type: "sync-request"
        protocol: "Internal"
        data_payload: "All completed GAU assessment IDs"
        data_classification: "internal"
        response_message: "Aggregated ratings by country/region/global business"
        response_seq: "1"
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "💡"

      - seq: 2
        from_id: "PAR-014"
        to_id: "PAR-015"
        message: "Pass aggregated results for report generation"
        message_type: "async-message"
        protocol: "Internal"
        data_payload: "Aggregated risk ratings + commentary"
        data_classification: "internal"
        response_message: ""
        response_seq: ""
        is_async: true
        error_condition: ""
        error_handling: ""
        verification: "💡"

      - seq: 3
        from_id: "PAR-015"
        to_id: "PAR-003"
        message: "Return draft aggregated reports for review and socialisation"
        message_type: "return"
        protocol: "Internal report"
        data_payload: "Draft reports with aggregated ratings and commentary"
        data_classification: "internal"
        response_message: ""
        response_seq: ""
        is_async: false
        error_condition: ""
        error_handling: ""
        verification: "💡"

      - seq: 4
        from_id: "PAR-003"
        to_id: "PAR-016"
        message: "Present final aggregated CRA results at CBNA-GBRCC and Board; include State of Risk report"
        message_type: "async-message"
        protocol: "Governance presentation"
        data_payload: "Aggregated IR, CE, RR ratings by business/function; key trends and themes"
        data_classification: "internal"
        response_message: "Acknowledgement; strategic directives issued"
        response_seq: "4"
        is_async: true
        error_condition: ""
        error_handling: ""
        verification: "✅"

    swimlanes:
      - lane_name: "ICRM Methodology Team"
        participant_ids: ["PAR-003"]
      - lane_name: "CRA Platform (Aggregation + Reporting)"
        participant_ids: ["PAR-014", "PAR-015"]
      - lane_name: "Senior Management / BRCC / Board"
        participant_ids: ["PAR-016"]

    verification: "✅"

# ── LIVING CONTEXT ────────────────────────────────────────────
living_context:
  last_verified: "2026-04-12"
  last_verified_by: "ai-documentation skill — synthesised from project source documents"
  drift_check_date: "2026-04-12"
  drift_status: "clean"
  linked_commit: ""
  linked_adr: []
  next_review_trigger: "Annual CRA methodology change; workflow modification by ICRM"

# ── AI-OPTIMISATION FIELDS ────────────────────────────────────
intent: >
  Enable the generating-architecture-diagrams skill to produce accurate
  sequence diagrams and swimlane diagrams for all critical CRA business flows
  through a verified, step-by-step interaction catalog sourced from workflow
  and training documentation.

consumption_context:
  - ai-reasoning
  - rag-retrieval
  - agentic-execution
  - human-reading

triggers:
  - "CRA sequence diagram"
  - "CRA swimlane diagram"
  - "CRA assessment workflow interactions"
  - "CRA data snapshot sequence"
  - "CRA preparer approver interaction flow"

confidence_overall: "high"
confidence_note: >
  All flows are ✅ confirmed from primary workflow and training documents.
  Internal platform steps (CNT-002 internal calls) are 💡 inferred from
  methodology detail. Notification mechanism for Approver (step 15) is 💡 inferred.
---
```

---

> ## 🤖 AI Summary
> **System:** CRA CSI-180236 — Sequence & Interaction Catalog
> **Core Purpose:** Ordered catalog of all critical CRA business flows for sequence and swimlane diagram generation.
> **Flow Count:** 5 flows (2 critical, 2 high, 1 error path)
> **Critical Flows:** SEQ-001 (Annual Data Snapshot), SEQ-002 (GAU Assessment Happy Path)
> **Participant Count:** 16 participants (8 actors, 5 containers, 3 external systems)
> **Living-Context Status:** Last verified 2026-04-12 | Drift: clean

---

## TABLE OF CONTENTS

1. [Flow Catalog Summary](#1-flow-catalog-summary)
2. [SEQ-001 — Annual Data Snapshot Extract](#2-seq-001--annual-data-snapshot-extract)
3. [SEQ-002 — GAU Assessment Happy Path](#3-seq-002--gau-assessment-happy-path)
4. [SEQ-002E — Approver Returns Assessment](#4-seq-002e--approver-returns-assessment)
5. [SEQ-003 — Data Validation by Business Teams](#5-seq-003--data-validation-by-business-teams)
6. [SEQ-004 — Post-Cycle Aggregation and Governance Reporting](#6-seq-004--post-cycle-aggregation-and-governance-reporting)
7. [Participant Register](#7-participant-register)
8. [Diagram Generation Notes](#8-diagram-generation-notes)
9. [Sources & Cross-References](#9-sources--cross-references)
10. [Revision History](#10-revision-history)

---

## 1. Flow Catalog Summary

[TYPE: REFERENCE]

| ID | Flow Name | Type | Priority | Steps | Verification |
|----|-----------|------|----------|-------|-------------|
| SEQ-001 | Annual Data Snapshot Extract | Sequence | Critical | 6 | ✅ |
| SEQ-001E | Annual Data Snapshot — Feed Failure | Sequence | Critical | 1 | ✅ |
| SEQ-002 | GAU Assessment — Happy Path | Both (Sequence + Swimlane) | Critical | 15 | ✅ |
| SEQ-002E | GAU Assessment — Approver Returns | Sequence | High | 2 | ✅ |
| SEQ-003 | Data Validation by Business Teams | Swimlane | Critical | 5 | ✅ |
| SEQ-004 | Post-Cycle Aggregation and Governance Reporting | Swimlane | High | 4 | ✅ |

---

## 2. SEQ-001 — Annual Data Snapshot Extract

[TYPE: PROCEDURE]

**Trigger:** GFT runs annual data extraction jobs at the designated data date (October 13 for 2025 cycle). ✅
**SLA Target:** Completed before cycle open date (November 3). ✅

This is the most foundational flow in the CRA system. All assessment calculations depend on the successful completion of this flow. ✅ The single-data-pull architecture means this flow runs once and its output is locked for the entire assessment window — making its correct execution critical to the integrity of all downstream assessments.

| Step | From | To | Message | Error Handling |
|------|------|----|---------|---------------|
| 1 | ICRM Methodology Team | GFT | Initiate annual extraction for data date | Upstream unavailability → escalate, pause assessments |
| 2 | GFT | MCA | Extract SoI, design attributes, obligation mappings | Retry; escalate if persistent |
| 3 | GFT | Reg Inventory | Extract publication tiers and obligation mappings | Retry; escalate |
| 4 | GFT | ICAPS | Extract open issues with obligation IDs | Retry; escalate |
| 5 | GFT | CRA Snapshot Store | Load and lock all extracted data | Rollback; retry; escalate |
| 6 | GFT | ICRM Methodology Team | Confirm extraction success | — |

**Swimlane groups:** ICRM Methodology Team | GFT Technology Team | Upstream Systems | CRA Platform ✅

---

## 3. SEQ-002 — GAU Assessment Happy Path

[TYPE: PROCEDURE]

**Trigger:** Preparer starts assessment in CRC tool on or after November 3. ✅
**SLA Target:** Completed by Q1 close. ✅

This is the primary user-facing flow of the CRA annual cycle. It covers the complete journey from assessment initiation through final approval. The 15-step flow embeds two critical governance gates: the Validate counter (Step 11) which must reach zero before submission, and the Approver review (Steps 14-15) which finalises the maker/checker cycle. ✅

| Step | From | To | Message | Key Gate |
|------|------|----|---------|---------|
| 1 | Preparer | CRA UI | Navigate, search GAU, Start Assessment | Entitlement check |
| 2 | Preparer | CRA UI | Select Business Activity rating | Triggers all publication recalculation |
| 3 | CRA UI | Rating Results Service | Apply Business Activity; trigger systemic calculations | — |
| 4 | Rating Results Service | Snapshot Store | Read all GAU publication data | Read-only data date lock |
| 5-6 | Rating Results Service → UI → Preparer | Display | Publication IR/CE/RR ratings displayed | — |
| 7 | Preparer | CRA UI | Assign L2 IR Current Rating + mandatory rationale; Save | Rationale missing → counter ↑ |
| 8 | Preparer | CRA UI | Assign L2 CE Current Rating + mandatory rationale; Save | Rationale missing → counter ↑ |
| 9 | CRA UI | Rating Results Service | Store qualitative ratings; recalculate L1/GAU | — |
| 10 | Preparer | CRA UI | Complete all 5 mandatory conclusion fields; Save | Empty field → counter ↑ |
| 11 | Preparer | CRA UI | Click Validate; complete remaining fields | **Counter must = 0** |
| 12 | Preparer | CRA UI | Click Submit | Counter must = 0; button inactive otherwise |
| 13 | CRA UI | Assessment Store | Persist submitted assessment; status = Pending Approval | — |
| 14 | Approver | CRA UI | Open and review full assessment | — |
| 15 | Approver | CRA UI | Click Approve | **Status → Completed; maker/checker complete** |

**Swimlane groups:** Preparer | CRA Platform (UI + Rating Service) | Data Stores | Approver ✅

---

## 4. SEQ-002E — Approver Returns Assessment

[TYPE: PROCEDURE]

**Trigger:** Approver identifies issues requiring correction. ✅

| Step | From | To | Message |
|------|------|----|---------|
| 1 | Approver | CRA UI | Click Return; provide correction notes → status = Draft |
| 2 | CRA UI | Preparer | Notify: assessment returned; review and resubmit |

---

## 5. SEQ-003 — Data Validation by Business Teams

[TYPE: PROCEDURE]

**Trigger:** ICRM Methodology Team grants Business Teams access after snapshot load. ✅
**SLA Target:** Before November 3 cycle open. ✅

This swimlane flow is a mandatory governance gate. No GAU assessment can be considered valid without Business Team sign-off on data accuracy. ✅

| Step | From | To | Message | Gate |
|------|------|----|---------|------|
| 1 | ICRM | Business Teams | Grant access; request validation sign-off | — |
| 2 | Business Teams | Snapshot Store | Review GAU data for completeness and accuracy | Discrepancy → step 3 |
| 3 (if discrepancy) | Business Teams | ICRM | Report discrepancy | — |
| 4 (if discrepancy) | ICRM | GFT | Escalate for investigation and correction | — |
| 5 | Business Teams | ICRM | Sign off on data accuracy | **GAU cleared for assessment** |

**Swimlane groups:** ICRM Methodology Team | Business Teams | GFT Technology Team | CRA Snapshot Store ✅

---

## 6. SEQ-004 — Post-Cycle Aggregation and Governance Reporting

[TYPE: PROCEDURE]

**Trigger:** All GAU assessments completed; calibration sessions concluded. ✅

| Step | From | To | Message |
|------|------|----|---------|
| 1 | ICRM | Aggregation Service | Trigger aggregation to country / region / global |
| 2 | Aggregation Service | Reporting Service | Pass aggregated results for report generation |
| 3 | Reporting Service | ICRM | Return draft aggregated reports |
| 4 | ICRM | Senior Mgmt / BRCC / Board | Present results at CBNA-GBRCC; State of Risk report |

**Swimlane groups:** ICRM Methodology Team | CRA Platform (Aggregation + Reporting) | Senior Management / BRCC / Board ✅

---

## 7. Participant Register

[TYPE: REFERENCE]

| ID | Participant | Type | Source | Stereotype |
|----|------------|------|--------|-----------|
| PAR-001 | Preparer | actor | CRA-006 ACT-001 | <<actor>> |
| PAR-002 | Approver | actor | CRA-006 ACT-002 | <<actor>> |
| PAR-003 | ICRM Methodology Team | actor | CRA-006 ACT-003 | <<actor>> |
| PAR-004 | Technology Team (GFT) | actor | CRA-006 ACT-005 | <<actor>> |
| PAR-005 | Business Teams | actor | CRA-006 ACT-008 | <<actor>> |
| PAR-006 | CRA UI | container | CRA-007 CNT-001 | <<web-app>> |
| PAR-007 | CRA Rating Results Service | container | CRA-007 CNT-002 | <<service>> |
| PAR-008 | CRA Scoping Service | container | CRA-007 CNT-003 | <<service>> |
| PAR-009 | CRA Data Snapshot Store | data-store | CRA-007 CNT-006 | <<database>> |
| PAR-010 | CRA Assessment Store | data-store | CRA-008 DS-001 | <<database>> |
| PAR-011 | MCA | external-system | CRA-006 INT-002 | <<external>> |
| PAR-012 | ICAPS | external-system | CRA-006 INT-004 | <<external>> |
| PAR-013 | Regulatory Inventory | external-system | CRA-006 INT-005 | <<external>> |
| PAR-014 | CRA Aggregation Service | container | CRA-007 CNT-004 | <<service>> |
| PAR-015 | CRA Reporting Service | container | CRA-007 CNT-007 | <<service>> |
| PAR-016 | Senior Mgmt / BRCC / Board | actor | CRA-006 ACT-006 | <<actor>> |

---

## 8. Diagram Generation Notes

[TYPE: REFERENCE]

**Sequence Diagram Generation (SEQ-001, SEQ-002, SEQ-002E):**
- Lifelines from `participants_used[]` for the specific flow
- Messages from `steps[]` in `seq` order — sequential integer ordering is strict
- Render `alt_fragment` steps with UML `alt` / `opt` / `loop` boxes
- `is_async: true` steps render as dashed arrows
- `is_async: false` steps render as solid synchronous arrows with return message
- Label error conditions as `note` annotations on the relevant step

**Swimlane Diagram Generation (SEQ-002, SEQ-003, SEQ-004):**
- Lanes from `swimlanes[].lane_name`; participants grouped per `participant_ids`
- Vertical lanes; time flows top to bottom
- Cross-lane arrows for each step in `steps[]` sequence
- Colour-code by lane using domain colour scheme from CRA-006 stakeholder views

**Priority Ordering for Diagram Generation:**
1. SEQ-002 (GAU Assessment — Happy Path) — swimlane + sequence; most complex, most business value
2. SEQ-001 (Data Snapshot Extract) — sequence; critical infrastructure flow
3. SEQ-003 (Business Data Validation) — swimlane; governance gate flow
4. SEQ-004 (Aggregation + Reporting) — swimlane; governance output flow
5. SEQ-002E and SEQ-001E — error flows; generate as alt fragments within parent flows or separate

---

## DIAGRAM-READY QUALITY GATE

```
[✅] participants[] — 16 participants defined with source references
[✅] flows[] — 5 flows with min 1 priority: critical entry
[✅] Every flow has trigger, preconditions, postconditions
[✅] Every step has from_id, to_id, message, verification label
[✅] Error flows documented (SEQ-001E, SEQ-002E)
[✅] Swimlanes defined for SEQ-002, SEQ-003, SEQ-004
[✅] living_context.last_verified populated
[✅] living_context.drift_status populated
[✅] Parent CRA-006 referenced; data_flow_ref CRA-008 referenced
```

---

## 9. Sources & Cross-References

[TYPE: REFERENCE]

| Source | Content Sourced |
|--------|----------------|
| `CRA_Workflows.md` | All seven lifecycle phases; minor workflow steps; maker/checker assignments per step |
| `2025_CRA_Training_Transcript.md` | CRC tool walkthrough; specific UI steps; Business Activity assignment; validate counter; submit flow |
| `CRA_User_and_Roles.md` | Actor interactions; deliverables per workflow stage |
| `notes.md` | Data snapshot architecture; single data pull rationale; GFT team roles |

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DEPENDS_ON] | CRA-006 System Context Pack | actors[], external_integrations[] | PAR-* actor and external system source IDs |
| [DEPENDS_ON] | CRA-007 Container & Component Inventory | containers[] | PAR-* container participant source IDs |
| [DEPENDS_ON] | CRA-008 Data & Flow Inventory | data_stores[], flows[] | Data store participants and flow context |
| [GOVERNED_BY] | RULES-001 Documentation Standards | All | Document format |
| [GOVERNED_BY] | Truth & Verification Standards | Sections 1-4 | Verification labels |

---

## 10. Revision History

[TYPE: REFERENCE]

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-12 | Initial | Document created — 5 flows, 16 participants, all sourced from primary workflow and training documents | Project documentation generation |

---

*TMPL-009 v2.0 — Sequence & Interaction Catalog | Document: CRA-009 | Parent: CRA-006 System Context Pack*
*All flow steps carry verification labels. ✅ = confirmed from workflow/training sources. 💡 = inferred from methodology detail.*
