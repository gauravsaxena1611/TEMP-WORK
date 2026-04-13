# Compliance Risk Assessment (CRA): Deployment Topology Sheet
## Infrastructure & Environment Map — CSI-180236

```yaml
---
# ── RULES-001 STANDARD FIELDS ────────────────────────────────
document_id: "CRA-010 Deployment Topology Sheet"
title: "CRA — Deployment Topology Sheet"
version: "1.0"
created: "2026-04-12"
status: "Final"
parent_document: "CRA-006 System Context Pack"
template_version_used: "TMPL-010 v2.0"

# ── METHODOLOGY PHASE ─────────────────────────────────────────
methodology_phase: "Phase-5-DetailedDesign"
design_methodology_ref: "Documentation_Standards.md, Section 3"

# ── SYSTEM BACK-REFERENCE ─────────────────────────────────────
system_id: "SYS-CRA-180236"
system_name: "Compliance Risk Assessment (CRA)"
context_pack_ref: "CRA-006 System Context Pack"
container_inventory_ref: "CRA-007 Container & Component Inventory"
infra_platform: "Internal CTI (Citi Technology Infrastructure) — on-premise"
iac_tool: "❓ Not specified"
iac_repo: "❓ Not specified"

# ── ENVIRONMENTS ──────────────────────────────────────────────
environments:
  - id: "ENV-PROD"
    name: "Production"
    type: "production"
    cloud_provider: "on-prem"
    region: "Citi CTI data centre — location not disclosed"
    account_id: "<CTI-PROD-ACCOUNT>"
    compliance_scope: ["Internal data classification", "Internal PII handling standards", "GRC Risk Taxonomy compliance"]
    iac_state_ref: "❓ Not specified"
    verification: "✅"

  - id: "ENV-UAT"
    name: "User Acceptance Testing"
    type: "uat"
    cloud_provider: "on-prem"
    region: "Citi CTI data centre — location not disclosed"
    account_id: "<CTI-UAT-ACCOUNT>"
    compliance_scope: ["Internal data classification"]
    iac_state_ref: "❓ Not specified"
    verification: "✅"

  - id: "ENV-DEV"
    name: "Development"
    type: "dev"
    cloud_provider: "on-prem"
    region: "Citi CTI data centre — location not disclosed"
    account_id: "<CTI-DEV-ACCOUNT>"
    compliance_scope: ["Internal data classification"]
    iac_state_ref: "❓ Not specified"
    verification: "✅"

  - id: "ENV-COB"
    name: "Continuity of Business (COB)"
    type: "dr"
    cloud_provider: "on-prem"
    region: "Citi CTI data centre — location not disclosed"
    account_id: "<CTI-COB-ACCOUNT>"
    compliance_scope: ["Internal data classification", "Business continuity standards"]
    iac_state_ref: "❓ Not specified"
    verification: "✅"

# ── NETWORKS ──────────────────────────────────────────────────
networks:
  - id: "NET-001"
    name: "CTI Internal Production Network"
    environment_id: "ENV-PROD"
    type: "vlan"
    cidr: "<INTERNAL-CIDR-PROD>"
    internet_facing: false
    egress_allowed: false
    ingress_rules: ["Internal CTI IAM-controlled access only"]
    egress_rules: ["Internal-to-internal; no public egress"]
    verification: "💡"

  - id: "NET-002"
    name: "CTI Internal UAT Network"
    environment_id: "ENV-UAT"
    type: "vlan"
    cidr: "<INTERNAL-CIDR-UAT>"
    internet_facing: false
    egress_allowed: false
    ingress_rules: ["Internal CTI access; UAT tester entitlements"]
    egress_rules: ["Internal only"]
    verification: "💡"

  - id: "NET-003"
    name: "CTI Internal COB Network"
    environment_id: "ENV-COB"
    type: "vlan"
    cidr: "<INTERNAL-CIDR-COB>"
    internet_facing: false
    egress_allowed: false
    ingress_rules: ["COB activation required; CTI-managed"]
    egress_rules: ["Internal only"]
    verification: "💡"

# ── DEPLOYMENT NODES ──────────────────────────────────────────
nodes:
  - id: "NOD-001"
    name: "CLMWAMLAI01P"
    environment_id: "ENV-PROD"
    network_id: "NET-001"
    type: "bare-metal"
    technology: "X86 Server — on-premise CTI"
    size_or_config: "❓ Not specified"
    auto_scaling: "none — dedicated on-premise server"
    high_availability: "❓ Not specified; COB server provides DR capability"
    managed_by: "CTI (Citi Technology Infrastructure)"
    monitoring: ["❓ Not specified"]
    verification: "✅"

  - id: "NOD-002"
    name: "CLSWAMLAI01C"
    environment_id: "ENV-COB"
    network_id: "NET-003"
    type: "bare-metal"
    technology: "X86 Server — on-premise CTI (COB)"
    size_or_config: "❓ Not specified"
    auto_scaling: "none — COB standby server"
    high_availability: "Active-passive COB configuration"
    managed_by: "CTI (Citi Technology Infrastructure)"
    monitoring: ["❓ Not specified"]
    verification: "✅"

  - id: "NOD-003"
    name: "CTO-GFT-CRA-180236 (PROD)"
    environment_id: "ENV-PROD"
    network_id: "NET-001"
    type: "k8s-cluster"
    technology: "On-premise container project — container runtime not disclosed"
    size_or_config: "❓ Not specified"
    auto_scaling: "❓ Not specified"
    high_availability: "❓ Not specified"
    managed_by: "GFT (Technology Team)"
    monitoring: ["❓ Not specified"]
    verification: "✅"

  - id: "NOD-004"
    name: "CTO-GFT-CRA-180236 (UAT)"
    environment_id: "ENV-UAT"
    network_id: "NET-002"
    type: "k8s-cluster"
    technology: "On-premise container project — container runtime not disclosed"
    size_or_config: "❓ Not specified"
    auto_scaling: "❓ Not specified"
    high_availability: "Not required for UAT"
    managed_by: "GFT (Technology Team)"
    monitoring: ["❓ Not specified"]
    verification: "✅"

  - id: "NOD-005"
    name: "CTO-GFT-LT-CRA-180236 (UAT Load Test)"
    environment_id: "ENV-UAT"
    network_id: "NET-002"
    type: "k8s-cluster"
    technology: "On-premise container project — load test environment"
    size_or_config: "❓ Not specified"
    auto_scaling: "❓ Sized for load testing scenarios"
    high_availability: "Not required for load test"
    managed_by: "GFT (Technology Team)"
    monitoring: ["❓ Not specified"]
    verification: "✅"

  - id: "NOD-006"
    name: "CTO-GFT-CRA-180236 (DEV)"
    environment_id: "ENV-DEV"
    network_id: "❓ Internal CTI DEV network"
    type: "k8s-cluster"
    technology: "On-premise container project — development environment"
    size_or_config: "❓ Not specified"
    auto_scaling: "❓ Not specified"
    high_availability: "Not required for DEV"
    managed_by: "GFT (Technology Team)"
    monitoring: ["❓ Not specified"]
    verification: "✅"

# ── CONTAINER PLACEMENTS ──────────────────────────────────────
container_placements:
  - container_id: "CNT-001"
    node_id: "NOD-003"
    replicas: "❓ Not specified"
    resource_limits: "❓ Not specified"
    resource_requests: "❓ Not specified"
    port_mapping: "443:❓"
    config_source: "❓ Not specified"
    secret_source: "❓ Not specified"
    image_registry: "❓ Internal CTI registry"
    image_tag_strategy: "❓ Not specified"
    verification: "💡"

  - container_id: "CNT-002"
    node_id: "NOD-003"
    replicas: "❓ Not specified"
    resource_limits: "❓ Not specified"
    resource_requests: "❓ Not specified"
    port_mapping: "❓ Internal"
    config_source: "❓ Not specified"
    secret_source: "❓ Not specified"
    image_registry: "❓ Internal CTI registry"
    image_tag_strategy: "❓ Not specified"
    verification: "💡"

  - container_id: "CNT-003"
    node_id: "NOD-003"
    replicas: "❓ Not specified"
    resource_limits: "❓ Not specified"
    resource_requests: "❓ Not specified"
    port_mapping: "❓ Internal"
    config_source: "❓ Not specified"
    secret_source: "❓ Not specified"
    image_registry: "❓ Internal CTI registry"
    image_tag_strategy: "❓ Not specified"
    verification: "💡"

  - container_id: "CNT-004"
    node_id: "NOD-003"
    replicas: "❓ Not specified"
    resource_limits: "❓ Not specified"
    resource_requests: "❓ Not specified"
    port_mapping: "❓ Internal"
    config_source: "❓ Not specified"
    secret_source: "❓ Not specified"
    image_registry: "❓ Internal CTI registry"
    image_tag_strategy: "❓ Not specified"
    verification: "💡"

  - container_id: "CNT-005"
    node_id: "NOD-003"
    replicas: "❓ Not specified"
    resource_limits: "❓ Not specified"
    resource_requests: "❓ Not specified"
    port_mapping: "443:❓"
    config_source: "❓ Not specified"
    secret_source: "❓ Not specified"
    image_registry: "❓ Internal CTI registry"
    image_tag_strategy: "❓ Not specified"
    verification: "💡"

  - container_id: "CNT-006"
    node_id: "NOD-001"
    replicas: "1 — single dedicated server (CLMWAMLAI01P)"
    resource_limits: "❓ Not specified"
    resource_requests: "❓ Not specified"
    port_mapping: "❓ Internal database port"
    config_source: "❓ Not specified"
    secret_source: "❓ Internal CTI secret management"
    image_registry: "N/A — on-premise server"
    image_tag_strategy: "N/A"
    verification: "✅"

  - container_id: "CNT-007"
    node_id: "NOD-003"
    replicas: "❓ Not specified"
    resource_limits: "❓ Not specified"
    resource_requests: "❓ Not specified"
    port_mapping: "❓ Internal"
    config_source: "❓ Not specified"
    secret_source: "❓ Not specified"
    image_registry: "❓ Internal CTI registry"
    image_tag_strategy: "❓ Not specified"
    verification: "💡"

# ── CI/CD PIPELINE ────────────────────────────────────────────
cicd:
  pipeline_tool: "❓ Not specified in source materials"
  source_repo: "❓ Internal Citi repository"
  build_tool: "❓ Not specified"
  test_stages:
    - "DEV environment — development and unit testing"
    - "UAT environment — user acceptance testing (CTO-GFT-CRA-180236 UAT)"
    - "UAT Load Test — performance validation (CTO-GFT-LT-CRA-180236)"
    - "PROD promotion — following UAT sign-off"
  deployment_strategy: "❓ Not specified; blue-green vs rolling not disclosed"
  rollback_strategy: "❓ Not specified"
  approval_gates:
    - "UAT sign-off by ICRM Methodology Team before PROD promotion"
    - "GFT Application Manager approval for major releases"
  verification: "💡"

# ── DR / BCP ─────────────────────────────────────────────────
disaster_recovery:
  rpo: "❓ Not specified — medium business criticality"
  rto: "❓ Not specified — GFT incident SLA governs"
  dr_strategy: "COB (Continuity of Business) — CLSWAMLAI01C standby server (ENV-COB)"
  dr_activation: "❓ Activation procedure not described in source materials"
  last_dr_test: "❓ Not specified"
  verification: "✅"

# ── LIVING CONTEXT ────────────────────────────────────────────
living_context:
  last_verified: "2026-04-12"
  last_verified_by: "ai-documentation skill — synthesised from project source documents"
  drift_check_date: "2026-04-12"
  drift_status: "clean"
  linked_commit: ""
  linked_adr: []
  next_review_trigger: "Infrastructure change; new environment addition; annual CTI review"

# ── AI-OPTIMISATION FIELDS ────────────────────────────────────
intent: >
  Enable the generating-architecture-diagrams skill to produce accurate
  deployment diagrams for CRA (CSI-180236) by providing a structured mapping
  of all environments, infrastructure nodes, and container placements.

consumption_context:
  - ai-reasoning
  - rag-retrieval
  - agentic-execution
  - human-reading

triggers:
  - "CRA deployment diagram"
  - "CRA infrastructure environments"
  - "CRA server topology"
  - "CRA DEV UAT PROD environments"
  - "CRA deployment topology"

confidence_overall: "medium"
confidence_note: >
  Environment names and node hostnames (CLMWAMLAI01P, CLSWAMLAI01C,
  CTO-GFT-CRA-180236) are ✅ confirmed from CRA_Dependencies.md.
  Container-to-node placement and all ❓ fields are inferred or unavailable.
  Technology stack details, resource configs, CI/CD tooling, and network
  CIDRs are not disclosed in source materials.
---
```

---

> ## 🤖 AI Summary
> **System:** CRA CSI-180236 — Deployment Topology Sheet
> **Core Purpose:** Infrastructure and environment map for CRA across four deployment environments (PROD, UAT, UAT Load Test, DEV, COB).
> **Environments:** 4 (Production, UAT, DEV, COB/DR) ✅
> **Named Nodes:** 6 (2 X86 servers confirmed; 4 container projects confirmed)
> **Confidence:** Environment and node names ✅ confirmed; resource configs and CI/CD ❓ not disclosed
> **DR Strategy:** COB standby — CLSWAMLAI01C ✅
> **Living-Context Status:** Last verified 2026-04-12 | Drift: clean

---

## TABLE OF CONTENTS

1. [Environment Overview](#1-environment-overview)
2. [Network Architecture](#2-network-architecture)
3. [Deployment Nodes](#3-deployment-nodes)
4. [Container Placements](#4-container-placements)
5. [CI/CD Pipeline](#5-cicd-pipeline)
6. [Disaster Recovery & Business Continuity](#6-disaster-recovery--business-continuity)
7. [Diagram Generation Notes](#7-diagram-generation-notes)
8. [Sources & Cross-References](#8-sources--cross-references)
9. [Revision History](#9-revision-history)

---

## 1. Environment Overview

[TYPE: REFERENCE]

The CRA application (CSI-180236) is deployed across four environments, all hosted on Citi's internal CTI (Citi Technology Infrastructure) on-premise infrastructure. ✅ No cloud provider is used; all infrastructure is Internal-CTI managed. ✅

| ID | Environment | Type | Purpose | Verified |
|----|------------|------|---------|---------|
| ENV-PROD | Production | production | Live annual assessment cycle serving all Preparers, Approvers, and ICRM users | ✅ |
| ENV-UAT | User Acceptance Testing | uat | Pre-production validation; tool demonstrations; new feature testing before PROD promotion | ✅ |
| ENV-UAT (LT) | UAT Load Test | uat | Performance and load testing environment (separate container project) | ✅ |
| ENV-DEV | Development | dev | GFT development and build environment | ✅ |
| ENV-COB | Continuity of Business | dr | Standby DR environment; COB server CLSWAMLAI01C | ✅ |

**Classification note:** All environments are Internal. No environment is internet-facing. ✅ All access is via internal CTI network with entitlement-based controls. ✅

**Training environment note:** The 2025 CRA training was conducted on a UAT environment instance showing "test data" throughout — this is confirmed as standard practice for training demonstrations. ✅ The production environment uses live data and is available from November 3, 2025.

---

## 2. Network Architecture

[TYPE: REFERENCE]

All CRA network segments are internal-only. No public-facing endpoints exist. ✅ The diagram below represents the logical network boundary structure.

```
Citi Internal Network (CTI-managed)
├── PROD network (NET-001)
│   ├── CLMWAMLAI01P (X86 — NOD-001) ← CRA Data Snapshot Store (CNT-006)
│   └── CTO-GFT-CRA-180236/PROD (Container — NOD-003) ← All CRA microservices
│
├── UAT network (NET-002)
│   ├── CTO-GFT-CRA-180236/UAT (Container — NOD-004)
│   └── CTO-GFT-LT-CRA-180236/UAT (Container — NOD-005) ← Load test only
│
├── DEV network (❓ designation)
│   └── CTO-GFT-CRA-180236/DEV (Container — NOD-006)
│
└── COB network (NET-003)
    └── CLSWAMLAI01C (X86 — NOD-002) ← COB standby
```

**Network characteristics:** ✅ Internal-only; no egress to public internet; access controlled by CTI IAM and City Marketplace entitlements.

| ID | Network | Environment | Type | Internet Facing | Verification |
|----|---------|-------------|------|----------------|-------------|
| NET-001 | CTI Internal PROD Network | ENV-PROD | Internal VLAN | No | 💡 |
| NET-002 | CTI Internal UAT Network | ENV-UAT | Internal VLAN | No | 💡 |
| NET-003 | CTI Internal COB Network | ENV-COB | Internal VLAN | No | 💡 |

---

## 3. Deployment Nodes

[TYPE: REFERENCE]

Six deployment nodes are confirmed from CRA_Dependencies.md. ✅ Two are dedicated X86 bare-metal servers; four are on-premise container projects managed by GFT.

| ID | Node Name | Environment | Type | Purpose | Managed By | Verification |
|----|-----------|-------------|------|---------|------------|-------------|
| NOD-001 | `CLMWAMLAI01P` | PROD | X86 Server (bare-metal) | Primary production server — hosts CRA Data Snapshot Store | CTI | ✅ |
| NOD-002 | `CLSWAMLAI01C` | COB | X86 Server (bare-metal) | COB standby server | CTI | ✅ |
| NOD-003 | `CTO-GFT-CRA-180236` (PROD) | PROD | Container Project | Hosts all CRA microservices in production | GFT | ✅ |
| NOD-004 | `CTO-GFT-CRA-180236` (UAT) | UAT | Container Project | UAT environment for testing and training demonstrations | GFT | ✅ |
| NOD-005 | `CTO-GFT-LT-CRA-180236` (UAT LT) | UAT | Container Project | Load and performance testing environment | GFT | ✅ |
| NOD-006 | `CTO-GFT-CRA-180236` (DEV) | DEV | Container Project | GFT development environment | GFT | ✅ |

### 3.1 Node Detail Notes

**NOD-001 — CLMWAMLAI01P (PROD):** ✅ The primary production X86 server confirmed in CRA_Dependencies.md. Hosts the CRA Data Snapshot Store (CNT-006) — the on-premise local store containing the annual data snapshot. This server is the physical home of the read-only snapshot that all production assessment calculations read from.

**NOD-002 — CLSWAMLAI01C (COB):** ✅ The Continuity of Business standby X86 server. Provides DR capability for the production environment. Exact activation procedure not documented in source materials. ❓

**NOD-003 — CTO-GFT-CRA-180236 (PROD Container Project):** ✅ The production container project hosting all CRA microservices — UI (CNT-001), Rating Results (CNT-002), Scoping (CNT-003), Aggregation (CNT-004), Third-Party Dashboard (CNT-005), and Reporting (CNT-007). Internal container runtime not disclosed. ❓

**NOD-004 / NOD-005 — UAT Container Projects:** ✅ The UAT environment is explicitly used for training demonstrations (confirmed in 2025 training transcript). Participants were advised that data seen in UAT shows "test data" rather than live assessment data. ✅ The separate Load Test container project (NOD-005) indicates capacity testing is performed in an isolated UAT environment.

---

## 4. Container Placements

[TYPE: REFERENCE]

The container placement model places the Data Snapshot Store (CNT-006) on the dedicated X86 server (NOD-001), separating it physically from the containerised microservices. All application microservices run on the container project (NOD-003) in PROD. ✅ Detailed resource configurations are not disclosed in source materials. ❓

| Container | Container Name | Node (PROD) | Node (UAT) | Node (DEV) | Verification |
|-----------|---------------|-------------|------------|------------|-------------|
| CNT-001 | CRA UI | NOD-003 | NOD-004 | NOD-006 | 💡 |
| CNT-002 | CRA Rating Results | NOD-003 | NOD-004 | NOD-006 | 💡 |
| CNT-003 | CRA Scoping | NOD-003 | NOD-004 | NOD-006 | 💡 |
| CNT-004 | CRA Aggregation | NOD-003 | NOD-004 | NOD-006 | 💡 |
| CNT-005 | Third-Party Dashboard | NOD-003 | NOD-004 | NOD-006 | 💡 |
| CNT-006 | CRA Data Snapshot Store | **NOD-001** (X86) | NOD-004 (UAT instance) | NOD-006 (dev instance) | ✅ |
| CNT-007 | CRA Reporting Service | NOD-003 | NOD-004 | NOD-006 | 💡 |

**Key placement observation:** CNT-006 (Data Snapshot Store) is the only container placed on the dedicated X86 bare-metal server (NOD-001). ✅ This physically separates the critical annual data snapshot from the containerised microservices, consistent with the importance of protecting data integrity in the single-snapshot architecture.

---

## 5. CI/CD Pipeline

[TYPE: REFERENCE]

The CRA follows a standard DEV → UAT → PROD promotion pipeline with a dedicated load test stage. ✅ Specific tooling (CI platform, build tool, deployment strategy) is not disclosed. ❓

```
Development:
  GFT develops and builds on DEV environment (NOD-006)
    ↓
UAT Validation:
  Deploy to UAT (NOD-004) for ICRM and user testing
    ↓
Load Testing (if applicable):
  Performance validation on UAT LT (NOD-005)
    ↓
Production Promotion:
  Deploy to PROD (NOD-003) — requires UAT sign-off
    GFT Application Manager: strategic approval
    ICRM Methodology Team: functional sign-off
```

| Stage | Environment | Node | Gate |
|-------|------------|------|------|
| Development | DEV | NOD-006 | Unit / integration tests |
| UAT | UAT | NOD-004 | ICRM functional sign-off |
| Load Test | UAT LT | NOD-005 | Performance criteria ❓ |
| Production | PROD | NOD-003 | GFT + ICRM dual approval 💡 |

---

## 6. Disaster Recovery & Business Continuity

[TYPE: REFERENCE]

| Attribute | Value | Verification |
|-----------|-------|-------------|
| DR Strategy | COB (Continuity of Business) — CLSWAMLAI01C standby server | ✅ |
| PROD Server | `CLMWAMLAI01P` | ✅ |
| COB Server | `CLSWAMLAI01C` | ✅ |
| RPO | ❓ Not specified — medium business criticality | ❓ |
| RTO | ❓ Not specified — GFT incident SLA governs | ❓ |
| DR Activation | ❓ Procedure not described in source materials | ❓ |
| Last DR Test | ❓ Not specified | ❓ |
| Business Criticality | Medium | ✅ |

**DR architecture note:** ✅ The presence of a dedicated COB X86 server (CLSWAMLAI01C) alongside the production server (CLMWAMLAI01P) indicates an active-passive standby architecture. The COB server mirrors the production X86 role. Given that the CRA operates on an annual batch cycle rather than real-time continuous processing, the COB architecture is appropriate for this criticality level.

**Annual cycle resilience:** The single-data-pull snapshot architecture provides an inherent resilience benefit during the assessment window: even if upstream systems (MCA, Reg Inventory, ICAPS) experience outages, the local snapshot in DS-002 ensures assessments can continue uninterrupted. ✅

---

## 7. Diagram Generation Notes

[TYPE: REFERENCE]

**Deployment Diagram Generation:**
- Top-level boxes: ENV-PROD, ENV-UAT, ENV-COB, ENV-DEV
- Within ENV-PROD:
  - Inner box 1: `CLMWAMLAI01P` (X86 Server) containing `CRA Data Snapshot Store (CNT-006)`
  - Inner box 2: `CTO-GFT-CRA-180236/PROD` (Container Project) containing all other microservices (CNT-001 through CNT-005, CNT-007)
- Within ENV-UAT:
  - Inner box 1: `CTO-GFT-CRA-180236/UAT` (Container Project) — assessment and training
  - Inner box 2: `CTO-GFT-LT-CRA-180236/UAT LT` (Container Project) — load test
- Within ENV-COB:
  - Inner box: `CLSWAMLAI01C` (X86 Server — COB Standby) — mirroring PROD
- Within ENV-DEV:
  - Inner box: `CTO-GFT-CRA-180236/DEV` (Container Project)

**Verification colouring:** ✅ nodes in solid borders; 💡 nodes in dashed borders; ❓ fields shown with placeholder notation.

**Network zones:** Draw all environments inside a single "Citi CTI Internal Network" outer boundary with "No public internet access" label. ✅

**DR relationship:** Dashed arrow from `CLMWAMLAI01P` to `CLSWAMLAI01C` labelled "COB Standby (active-passive)". ✅

---

## DIAGRAM-READY QUALITY GATE

```
[✅] environments[] has production entry — ENV-PROD confirmed
[✅] nodes[] — 6 nodes defined; all reference environment_id
[✅] container_placements[] covers ALL 7 TMPL-007 containers
[⚠️] DR RTO/RPO — ❓ not specified; cannot verify against TMPL-006 SLA
[✅] living_context.last_verified populated
[✅] living_context.drift_status populated
[✅] Parent CRA-006 referenced; container_inventory_ref CRA-007 set
[⚠️] Network CIDRs — placeholders used; internal network details not disclosed
[⚠️] Resource limits, CI/CD tooling, image registries — ❓ not disclosed
[✅] COB DR strategy documented with confirmed server names
```

---

## 8. Sources & Cross-References

[TYPE: REFERENCE]

| Source | Content Sourced |
|--------|----------------|
| `CRA_Dependencies.md` | All 6 named infrastructure resources (X86 servers + container projects); environment types (DEV, UAT, PROD, COB); internal CTI hosting confirmation |
| `2025_CRA_Training_Transcript.md` | UAT environment usage for training; "test data" confirmation for non-PROD |
| `CRA_Study_Guide.md` | Lifecycle status: Production; Hosting Model: Internal-CTI; Business Criticality: Medium |

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DEPENDS_ON] | CRA-006 System Context Pack | tech_stack, trust_boundaries | Platform, hosting model, infrastructure; trust boundary references |
| [DEPENDS_ON] | CRA-007 Container & Component Inventory | containers[] | All container IDs mapped to nodes in container_placements[] |
| [GOVERNED_BY] | RULES-001 Documentation Standards | All | Document format |
| [GOVERNED_BY] | Truth & Verification Standards | Sections 1-4 | Verification labels |

---

## 9. Revision History

[TYPE: REFERENCE]

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-12 | Initial | Document created — all 6 named infrastructure resources confirmed from CRA_Dependencies.md; resource configs and CI/CD ❓ not disclosed | Project documentation generation |

---

*TMPL-010 v2.0 — Deployment Topology Sheet | Document: CRA-010 | Parent: CRA-006 System Context Pack*
*Infrastructure node names ✅ confirmed from CRA_Dependencies.md. Internal resource configurations ❓ not available in source materials.*
