# [System Name]: Sequence & Interaction Catalog
## [Subtitle — e.g., "Critical Business Flows & Interaction Sequences — v1.0"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-009: SEQUENCE & INTERACTION CATALOG  (v2.0)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: Ordered catalog of all critical business flows expressed
as interaction sequences. The primary feed for Sequence Diagrams
and Swimlane Diagrams in the generating-architecture-diagrams skill.

AUTHORING ORDER:
  1. TMPL-006 (System Context Pack) — provides actors and
     external integrations used as sequence participants.
  2. TMPL-007 (Container & Component Inventory) — containers and
     components are the internal participants in sequences.
  3. TMPL-008 (Data & Flow Inventory) — data stores referenced
     in interaction steps.
  4. This document catalogs the TIME-ORDERED interactions.

THIS IS NOT FOR:
  - System-level context → TMPL-006
  - Container/component structure → TMPL-007
  - Data entity model → TMPL-008
  - Deployment infrastructure → TMPL-010
  - Portfolio integration flows → TMPL-011

DIAGRAM SKILL FEED:
  flows[].participants → Sequence diagram lifelines
  flows[].steps[]      → Sequence diagram messages
  flows[].swimlanes    → Swimlane diagram lanes

VERIFICATION REQUIREMENT (Verification Mode = Medium-High):
  ✅ verified  ⚠️ flagged  🚩 outlier  ❌ removed
  ❓ unresolved  💡 inference  🗑️ bogus caught

METHODOLOGY PHASE BINDING (from [030]):
  Typically Phase-5-DetailedDesign or Phase-6-Validation.
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ────────────────────────────────
document_id: "[XXX Short-Title]-Sequences"
title: "[System Name] — Sequence & Interaction Catalog"
version: "1.0"
created: "YYYY-MM-DD"
status: "Draft | Review | Final"
parent_document: "[TMPL-006 System Context Pack for this system]"
template_version_used: "TMPL-009 v2.0"

# ── METHODOLOGY PHASE ─────────────────────────────────────────
methodology_phase: "Phase-5-DetailedDesign"
design_methodology_ref: "[030 System Design Methodology, Section 4]"

# ── SYSTEM BACK-REFERENCE ─────────────────────────────────────
system_id: ""
system_name: ""
context_pack_ref: ""
container_inventory_ref: ""
data_flow_ref: ""

# ── PARTICIPANT REGISTER ──────────────────────────────────────
# All participants that appear across sequences. Sourced from:
#   actors[] in TMPL-006 (external)
#   containers[] in TMPL-007 (internal)
# consumed by: all sequence diagram lifelines
participants:
  - id: ""              # "PAR-001" — stable reference across all flows
    name: ""            # "Customer Browser"
    type: ""            # actor | container | component | external-system | data-store
    source_id: ""       # Original ID from TMPL-006 or TMPL-007
    source_doc: ""      # "TMPL-006" | "TMPL-007"
    stereotype: ""      # "<<actor>>" | "<<service>>" | "<<database>>" | "<<queue>>"

# ── INTERACTION FLOWS ─────────────────────────────────────────
# Each flow = one critical business scenario.
# consumed by: Sequence diagram OR Swimlane diagram (or both)
flows:
  - id: ""              # "SEQ-001"
    name: ""            # "User Checkout — Happy Path"
    type: ""            # sequence | swimlane | both
    priority: ""        # critical | high | medium | low
    trigger: ""         # "User clicks 'Place Order'" | "Nightly batch job at 02:00"
    preconditions: []   # ["User is authenticated", "Cart is non-empty"]
    postconditions: []  # ["Order record created", "Confirmation email queued"]
    happy_path: true    # true = primary success scenario
    error_flows: []     # List of SEQ IDs for error/alternate paths
    sla_target: ""      # "p99 < 3 seconds end-to-end"
    participants_used: []  # List of PAR IDs active in this flow

    # Ordered interaction steps
    steps:
      - seq: 1          # Step number — MUST be sequential integers
        from_id: ""     # PAR ID
        to_id: ""       # PAR ID
        message: ""     # "POST /orders {payload}"
        message_type: "" # sync-request | async-message | return | self-call
                         # | create | destroy | note
        protocol: ""    # REST | gRPC | SQL | Kafka | AMQP | internal | event
        data_payload: "" # Brief: "Order JSON: order_id, items[], customer_id"
        data_classification: "" # PII | financial | public | internal
        response_message: "" # For sync-request: "200 OK {order_id}"
        response_seq: "" # Which step this is a response to (for sync calls)
        is_async: false
        error_condition: "" # What can go wrong here
        error_handling: ""  # How the error is handled
        alt_fragment: ""    # "alt [if payment fails]" — UML alt/opt/loop labels
        verification: ""    # ✅ | 💡 | ⚠️

    # For swimlane generation
    swimlanes:
      - lane_name: ""   # "Customer" | "Order Service" | "Payment Gateway"
        participant_ids: []  # PAR IDs belonging to this lane

    verification: ""    # Overall flow verification: ✅ | 💡 | ⚠️

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
  Enable the generating-architecture-diagrams skill to produce
  accurate sequence and swimlane diagrams for [system_name] by
  providing a structured, ordered catalog of all critical
  business flows and their step-by-step interactions.

consumption_context:
  - ai-reasoning
  - rag-retrieval
  - agentic-execution
  - human-reading

triggers:
  - "sequence diagram for [system]"
  - "swimlane diagram for [flow]"
  - "how does [flow name] work end to end"
  - "[system] interaction flow"
  - "what happens when [trigger]"

negative_triggers:
  - "container structure → TMPL-007"
  - "data entity model → TMPL-008"
  - "deployment topology → TMPL-010"

volatility: "fast-changing"
review_trigger: ""
research_validated: false
confidence_overall: "conditional"
---
```

> **AI SUMMARY**
> **Core Purpose:** Ordered catalog of [N] critical business flows for [system_name], structured for Sequence and Swimlane diagram generation.
> **Critical Flows:** [comma-separated list of SEQ IDs with priority=critical]
> **Total Flows:** [N] | **Total Steps:** [N across all flows]
> **Living-Context Status:** Last verified [date] | Drift: [status]
> **Diagram Generation Ready:** ✅ Yes / ❌ No

---

# [System Name]: Sequence & Interaction Catalog

**Document ID:** [XXX]-Sequences
**Parent Document:** [TMPL-006 Document ID, Section 12]
**Version:** 1.0
**Created:** YYYY-MM-DD
**Status:** Draft

**Cross-References:**

| Relationship | Target | Reason |
|---|---|---|
| [DEPENDS_ON] | [TMPL-006 System Context Pack] | Actors and external integrations as participants |
| [DEPENDS_ON] | [TMPL-007 Container & Component Inventory] | Containers and components as internal participants |
| [SEE_ALSO] | [TMPL-008 Data & Flow Inventory] | Data entities referenced in step payloads |
| [DEPENDS_ON] | [013 Truth, Verification & Doc Governance, Section 3.5] | 7-label verification |
| [APPLIES] | [040 Living Architecture & Drift Control] | Living-context sync |
| [SEE_ALSO] | [024 Swimlane & Workflow Diagrams] | Swimlane notation standard |
| [SEE_ALSO] | [025 Sequence, ERD & Deployment Diagrams] | Sequence diagram notation standard |

---

## TABLE OF CONTENTS

1. [Participant Register](#1-participant-register)
2. [Flow Index](#2-flow-index)
3. [Critical Flows — Detail](#3-critical-flows--detail)
4. [Error & Alternate Flows](#4-error--alternate-flows)
5. [Diagram Generation Notes](#5-diagram-generation-notes)
6. [Sources & References](#6-sources--references)
7. [Revision History](#7-revision-history)

---

## 1. PARTICIPANT REGISTER

[TYPE: REFERENCE]

<!-- All entities that appear as lifelines across all sequence diagrams.
     Source from participants[] in YAML. Pre-defining here keeps diagram labels consistent.
     ✅ = confirmed participant | 💡 = designed / inferred -->

| ID | Participant | Type | Source Document | Stereotype | Verification |
|----|-------------|------|-----------------|-----------|-------------|
| PAR-001 | | | | | |

---

## 2. FLOW INDEX

[TYPE: REFERENCE]

<!-- One-line summary of every flow. Diagram agents use this to select which flow to render. -->

| ID | Flow Name | Type | Priority | Trigger | Participants | Verification |
|----|-----------|------|----------|---------|-------------|-------------|
| SEQ-001 | | sequence | | | | |

---

## 3. CRITICAL FLOWS — DETAIL

[TYPE: RESEARCH_FINDING]

<!-- One subsection per flow with priority = critical or high.
     Steps must be numbered sequentially. Every step carries a verification label. -->

### 3.1 SEQ-001 — [Flow Name]

**Type:** Sequence | Swimlane | Both
**Priority:** Critical | High
**Trigger:** [What initiates this flow]
**Preconditions:** [List]
**Postconditions:** [List]
**SLA Target:** [e.g., "p99 < 3s end-to-end"] ✅/💡
**Participants:** [List of PAR IDs]

#### Step Table

| Seq | From | To | Message | Type | Protocol | Data | Error Handling | Verification |
|-----|------|----|---------|------|----------|------|---------------|-------------|
| 1 | | | | | | | | |
| 2 | | | | | | | | |

#### Swimlane Lanes (if applicable)

| Lane | Participants |
|------|-------------|
| [Lane name] | [PAR IDs] |

#### Notes

[Any notes about this flow that affect diagram generation — e.g., UML `alt` fragments, `loop` conditions, `opt` blocks.]

---

## 4. ERROR & ALTERNATE FLOWS

[TYPE: RESEARCH_FINDING]

<!-- Error flows are referenced by their happy-path counterpart (flows[].error_flows[]).
     Each error flow follows the same step-table format as Section 3. -->

### 4.1 SEQ-001-ERR — [Error Flow Name]

**Related Happy Path:** SEQ-001
**Error Condition:** [What went wrong]
**Entry Point:** Step [N] of SEQ-001

| Seq | From | To | Message | Type | Protocol | Verification |
|-----|------|----|---------|------|----------|-------------|
| 1 | | | | | | |

---

## 5. DIAGRAM GENERATION NOTES

[TYPE: REFERENCE]

**Sequence Diagram Generation:**
- Source: `flows[]` where `type: sequence` or `type: both`
- Each `steps[]` entry is one sequence diagram arrow
- `message_type: return` generates dashed return arrows
- `alt_fragment` generates UML `alt`/`opt`/`loop` blocks
- `is_async: true` generates async (open arrowhead) messages

**Swimlane Diagram Generation:**
- Source: `flows[]` where `type: swimlane` or `type: both`
- Each `swimlanes[].lane_name` becomes one swim lane column/row
- Steps are placed in the lane of the `from_id` participant
- Arrows crossing lanes represent handoffs

**Participant Labeling:**
- Use `participants[].name` for lifeline labels
- Apply `participants[].stereotype` in guillemets: `<<service>>`

---

## DIAGRAM-READY QUALITY GATE

```
[ ] participants[] has min 1 entry; covers all actors and containers in any flow
[ ] flows[] has min 1 entry with priority = critical or high
[ ] Every flow has at least 3 steps
[ ] Every step has from_id, to_id, message, and verification label
[ ] SLA targets on critical flows are sourced (✅) not free-floating (💡 acceptable)
[ ] Error flows defined for all critical flows
[ ] living_context.last_verified populated
[ ] Parent TMPL-006 document ID in parent_document field
[ ] Bidirectional reference added in TMPL-006 Section 12
[ ] Revision history present
```

---

## 6. SOURCES & REFERENCES

[TYPE: REFERENCE]

| Reference | Document | Section | Topic |
|-----------|----------|---------|-------|
| REF-001 | [TMPL-006 System Context Pack] | actors[], external_integrations[] | External participant definitions |
| REF-002 | [TMPL-007 Container & Component Inventory] | containers[], components[] | Internal participant definitions |
| REF-003 | [TMPL-008 Data & Flow Inventory] | entities[] | Data payloads in steps |
| REF-004 | [024 Swimlane & Workflow Diagrams] | All | Swimlane notation standard |
| REF-005 | [025 Sequence, ERD & Deployment Diagrams] | Sequence section | Sequence diagram notation |
| REF-006 | [013 Truth, Verification & Doc Governance] | 3.5 | 7-label verification |
| REF-007 | [040 Living Architecture & Drift Control] | 6 | Living-context sync |

---

## 7. REVISION HISTORY

[TYPE: REFERENCE]

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | YYYY-MM-DD | Initial | Document created | [Reason] |

---

*TMPL-009 v2.0 — Sequence & Interaction Catalog. Governed by [RULES-001] and [013]. Verification labels mandatory per [013, Section 3.5]. Parent: [TMPL-006].*
