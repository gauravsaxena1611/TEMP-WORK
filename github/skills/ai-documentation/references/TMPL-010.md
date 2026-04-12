# [System Name]: Deployment Topology Sheet
## [Subtitle — e.g., "Infrastructure & Environment Map — v1.0"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-010: DEPLOYMENT TOPOLOGY SHEET  (v2.0)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: Structured mapping of all environments, infrastructure
nodes, networks, and zones for a single system. The primary feed
for Deployment Diagrams in the generating-architecture-diagrams
skill.

AUTHORING ORDER:
  1. TMPL-006 (System Context Pack) — provides tech_stack
     and trust_boundaries used here.
  2. TMPL-007 (Container & Component Inventory) — containers
     are mapped to deployment nodes in this document.
  3. This document records WHERE containers run and HOW
     the infrastructure is organized.

THIS IS NOT FOR:
  - What the containers are → TMPL-007
  - Data flows → TMPL-008
  - Business interaction sequences → TMPL-009
  - Portfolio infrastructure map → TMPL-011

DIAGRAM SKILL FEED:
  environments[] → top-level deployment boundaries
  nodes[]        → deployment nodes inside environments
  networks[]     → network segments / VPCs / VLANs
  container_placements[] → which container runs on which node

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
document_id: "[XXX Short-Title]-Deployment"
title: "[System Name] — Deployment Topology Sheet"
version: "1.0"
created: "YYYY-MM-DD"
status: "Draft | Review | Final"
parent_document: "[TMPL-006 System Context Pack for this system]"
template_version_used: "TMPL-010 v2.0"

# ── METHODOLOGY PHASE ─────────────────────────────────────────
methodology_phase: "Phase-5-DetailedDesign"
design_methodology_ref: "[030 System Design Methodology, Section 4]"

# ── SYSTEM BACK-REFERENCE ─────────────────────────────────────
system_id: ""
system_name: ""
context_pack_ref: ""
container_inventory_ref: ""
infra_platform: ""        # Must match tech_stack.infra_platform in TMPL-006
iac_tool: ""              # Must match tech_stack.iac_tool in TMPL-006
iac_repo: ""              # Link to IaC repository / folder

# ── ENVIRONMENTS ──────────────────────────────────────────────
# Top-level deployment boundaries. Minimum: production.
# consumed by: outermost deployment diagram boxes
environments:
  - id: ""              # "ENV-PROD"
    name: ""            # "Production"
    type: ""            # production | staging | uat | qa | dev | sandbox | dr
    cloud_provider: ""  # AWS | GCP | Azure | on-prem | hybrid
    region: ""          # "us-east-1" | "canadacentral" | "europe-west1"
    account_id: ""      # Cloud account ID (use placeholder if sensitive)
    compliance_scope: [] # "PCI-DSS" | "SOC2" | "HIPAA" | "PIPEDA" | "GDPR"
    iac_state_ref: ""   # Terraform state file or stack name
    verification: ""    # ✅ | 💡 | ⚠️

# ── NETWORKS (VPCs, VLANs, subnets) ──────────────────────────
# consumed by: network zone boxes in deployment diagram
networks:
  - id: ""              # "NET-001"
    name: ""            # "prod-vpc" | "private-subnet" | "public-subnet"
    environment_id: ""  # References environments[].id
    type: ""            # vpc | vnet | vlan | subnet | peering | vpn-tunnel
    cidr: ""            # "10.0.0.0/16" — use placeholder if sensitive
    internet_facing: false
    egress_allowed: false
    ingress_rules: []   # Brief: ["443 from 0.0.0.0/0", "22 from VPN only"]
    egress_rules: []
    verification: ""    # ✅ | 💡 | ⚠️

# ── DEPLOYMENT NODES ──────────────────────────────────────────
# A "node" = any hardware or execution environment that hosts software.
# consumed by: inner deployment diagram boxes
nodes:
  - id: ""              # "NOD-001"
    name: ""            # "api-eks-cluster" | "orders-db-rds" | "cdn-cloudfront"
    environment_id: ""  # References environments[].id
    network_id: ""      # References networks[].id
    type: ""            # k8s-cluster | ecs-cluster | ec2-instance | rds-instance
                        # | lambda | s3-bucket | cdn | load-balancer | api-gateway
                        # | vm | bare-metal | managed-service | serverless
    technology: ""      # "EKS 1.29" | "RDS PostgreSQL 15 Multi-AZ" | "CloudFront"
    size_or_config: ""  # "3x t3.xlarge nodes" | "db.r6g.large, 200GB gp3"
    auto_scaling: ""    # "HPA: 2–20 pods" | "RDS read replicas on demand" | "none"
    high_availability: "" # "Multi-AZ active-passive" | "active-active 3 AZs" | "none"
    managed_by: ""      # "Platform Team" | "AWS-managed" | "DevOps"
    monitoring: []      # ["CloudWatch", "Datadog agent"]
    verification: ""    # ✅ | 💡 | ⚠️

# ── CONTAINER PLACEMENTS ──────────────────────────────────────
# Maps containers (TMPL-007) to deployment nodes (above).
# This is the JOIN between logical architecture and physical topology.
container_placements:
  - container_id: ""    # References containers[].id in TMPL-007
    node_id: ""         # References nodes[].id above
    replicas: ""        # "3" | "1 (single-instance)" | "auto"
    resource_limits: "" # "CPU: 500m, Mem: 512Mi"
    resource_requests: "" # "CPU: 100m, Mem: 256Mi"
    port_mapping: ""    # "8080:80" — container_port:host_port
    config_source: ""   # "AWS Parameter Store /prod/service/" | "ConfigMap"
    secret_source: ""   # "AWS Secrets Manager" | "Vault" | "K8s Secret"
    image_registry: ""  # "ECR: 123456789.dkr.ecr.us-east-1.amazonaws.com"
    image_tag_strategy: "" # "semver tags" | "latest (⚠️ not recommended)"
    verification: ""    # ✅ | 💡 | ⚠️

# ── CI/CD PIPELINE ────────────────────────────────────────────
cicd:
  platform: ""          # "GitHub Actions" | "GitLab CI" | "Jenkins" | "AWS CodePipeline"
  pipeline_stages: []   # ["build", "unit-test", "integration-test", "push-image", "deploy-staging", "deploy-prod"]
  deploy_strategy: ""   # "rolling" | "blue-green" | "canary" | "recreate"
  rollback_strategy: "" # "Helm rollback" | "previous task definition" | "manual"
  environment_promotion: "" # "dev → staging → prod" | "PR → staging → prod"
  iac_drift_check: ""   # "terraform plan in CI" | "none"
  verification: ""      # ✅ | 💡 | ⚠️

# ── DISASTER RECOVERY ─────────────────────────────────────────
disaster_recovery:
  dr_strategy: ""       # "active-passive" | "active-active" | "backup-restore" | "none"
  dr_region: ""         # Secondary region for DR
  rpo_minutes: ""       # Must match or improve on sla.rpo in TMPL-006
  rto_minutes: ""       # Must match or improve on sla.rto in TMPL-006
  failover_mechanism: "" # "Route53 health check failover" | "manual DNS change"
  last_dr_test: "YYYY-MM-DD"
  verification: ""      # ✅ | 💡 | ⚠️

# ── LIVING-CONTEXT SYNC BLOCK (from [040] / [042]) ────────────
living_context:
  last_verified: "YYYY-MM-DD"
  last_verified_by: ""
  drift_check_date: "YYYY-MM-DD"
  drift_status: ""
  linked_commit: ""
  linked_iac_pr: ""     # Last IaC PR that changed this topology
  linked_adr: []
  next_review_trigger: ""
  automation_source: "" # "terraformer import" | "manual" | "IaC annotation"

# ── AI-OPTIMIZATION FIELDS ────────────────────────────────────
intent: >
  Enable the generating-architecture-diagrams skill to produce
  accurate deployment diagrams for [system_name] by providing
  a complete, structured inventory of all environments, nodes,
  networks, container placements, and CI/CD configuration.

consumption_context:
  - ai-reasoning
  - rag-retrieval
  - agentic-execution
  - human-reading

triggers:
  - "deployment diagram for [system]"
  - "infrastructure map for [system]"
  - "where does [container] run"
  - "[system] environment topology"
  - "[system] DR strategy"
  - "[system] CI/CD pipeline"

negative_triggers:
  - "what containers exist → TMPL-007"
  - "data flows → TMPL-008"
  - "business sequences → TMPL-009"

volatility: "fast-changing"
review_trigger: ""
research_validated: false
confidence_overall: "conditional"
---
```

> **AI SUMMARY**
> **Core Purpose:** Deployment topology for [system_name] — [N] environments, [N] nodes, [N] container placements across [infra_platform].
> **Environments:** [prod / staging / dev / DR]
> **HA Strategy:** [Multi-AZ / active-active / etc.]
> **DR RTO/RPO:** [from DR section]
> **Living-Context Status:** Last verified [date] | Drift: [status]
> **Diagram Generation Ready:** ✅ Yes / ❌ No

---

# [System Name]: Deployment Topology Sheet

**Document ID:** [XXX]-Deployment
**Parent Document:** [TMPL-006 Document ID, Section 12]
**Version:** 1.0
**Created:** YYYY-MM-DD
**Status:** Draft

**Cross-References:**

| Relationship | Target | Reason |
|---|---|---|
| [DEPENDS_ON] | [TMPL-006 System Context Pack] | tech_stack, trust_boundaries, SLAs |
| [DEPENDS_ON] | [TMPL-007 Container & Component Inventory] | containers[] mapped to nodes here |
| [DEPENDS_ON] | [013 Truth, Verification & Doc Governance, Section 3.5] | 7-label verification |
| [APPLIES] | [040 Living Architecture & Drift Control] | Living-context sync; IaC drift check |
| [SEE_ALSO] | [025 Sequence, ERD & Deployment Diagrams] | Deployment diagram notation standard |
| [DEPENDS_ON] | [011 Core Architectural Principles, Section 7] | Reliability & availability principle |

---

## TABLE OF CONTENTS

1. [Environments](#1-environments)
2. [Networks & Zones](#2-networks--zones)
3. [Deployment Nodes](#3-deployment-nodes)
4. [Container Placements](#4-container-placements)
5. [CI/CD Pipeline](#5-cicd-pipeline)
6. [Disaster Recovery](#6-disaster-recovery)
7. [Diagram Generation Notes](#7-diagram-generation-notes)
8. [Sources & References](#8-sources--references)
9. [Revision History](#9-revision-history)

---

## 1. ENVIRONMENTS

[TYPE: REFERENCE]

<!-- ✅ = confirmed provisioned | 💡 = planned | ⚠️ = provisioned but config uncertain -->

| ID | Environment | Type | Platform | Region | Compliance Scope | IaC State | Verification |
|----|-------------|------|----------|--------|-----------------|-----------|-------------|
| ENV-PROD | Production | production | | | | | |
| ENV-STG | Staging | staging | | | | | |

---

## 2. NETWORKS & ZONES

[TYPE: REFERENCE]

<!-- Each row is one network segment. Internet-facing networks are ⚠️ flagged if unprotected. -->

| ID | Network | Environment | Type | CIDR | Internet-Facing | Ingress Rules | Verification |
|----|---------|-------------|------|------|----------------|--------------|-------------|
| NET-001 | | | | | | | |

### 2.1 Network Architecture Notes

[Prose: describe the network topology. Highlight trust boundaries from TMPL-006 and how they map to network segments. Note any flat networks without segmentation as ⚠️.]

---

## 3. DEPLOYMENT NODES

[TYPE: REFERENCE]

<!-- One row per infrastructure node. ✅ = provisioned and confirmed | 💡 = planned -->

| ID | Node | Environment | Network | Type | Technology | HA | Scaling | Verification |
|----|------|-------------|---------|------|------------|----|---------|-----------  |
| NOD-001 | | | | | | | | |

---

## 4. CONTAINER PLACEMENTS

[TYPE: REFERENCE]

<!-- JOIN between TMPL-007 containers and deployment nodes.
     This table is the critical link for deployment diagram generation. -->

| Container ID | Container Name | Node ID | Replicas | Resource Limits | Image Strategy | Config Source | Verification |
|-------------|---------------|---------|----------|----------------|----------------|---------------|-------------|
| CNT-001 | | | | | | | |

---

## 5. CI/CD PIPELINE

[TYPE: REFERENCE]

<!-- ✅ = pipeline active | 💡 = planned | ⚠️ = pipeline exists but incomplete -->

**Platform:** [CI/CD platform] [✅/💡/⚠️]
**Stages:** [Ordered list of pipeline stages]
**Deploy Strategy:** [rolling | blue-green | canary | recreate] [✅/💡]
**Rollback:** [Mechanism] [✅/💡/⚠️]
**IaC Drift Check:** [terraform plan in CI | none] [✅/⚠️]

---

## 6. DISASTER RECOVERY

[TYPE: RESEARCH_FINDING]

<!-- ✅ = DR tested | 💡 = designed not tested | ⚠️ = DR gap or not defined -->

**DR Strategy:** [active-passive | active-active | backup-restore | none] [✅/💡/⚠️]
**DR Region:** [Region] [✅/💡]
**RPO:** [minutes] — must meet SLA commitment in TMPL-006 [✅/⚠️]
**RTO:** [minutes] — must meet SLA commitment in TMPL-006 [✅/⚠️]
**Failover Mechanism:** [Description] [✅/💡]
**Last DR Test:** [Date] [✅ tested | ❓ never tested]

---

## 7. DIAGRAM GENERATION NOTES

[TYPE: REFERENCE]

**Deployment Diagram Generation:**
- Top-level boxes: `environments[]`
- Inner zone boxes: `networks[]` grouped by `environment_id`
- Node boxes: `nodes[]` placed inside their `network_id` box
- Container icons: placed inside node boxes per `container_placements[]`
- Use `container_placements[].replicas` for replica count annotations
- Trust boundaries from TMPL-006 should overlay network zone boundaries

---

## DIAGRAM-READY QUALITY GATE

```
[ ] environments[] has min 1 entry (production mandatory)
[ ] nodes[] has min 1 entry per environment
[ ] networks[] has entries matching trust_boundaries in TMPL-006
[ ] container_placements[] covers ALL containers from TMPL-007
[ ] cicd.deploy_strategy is not blank
[ ] disaster_recovery.dr_strategy is not blank (even if "none")
[ ] DR RTO/RPO ≤ SLA commitments in TMPL-006
[ ] Every node and placement carries a verification label (✅/💡/⚠️)
[ ] living_context.last_verified populated
[ ] living_context.linked_iac_pr populated (or "not applicable")
[ ] Parent TMPL-006 document ID in parent_document field
[ ] Bidirectional reference added in TMPL-006 Section 12
[ ] Revision history present
```

---

## 8. SOURCES & REFERENCES

[TYPE: REFERENCE]

| Reference | Document | Section | Topic |
|-----------|----------|---------|-------|
| REF-001 | [TMPL-006 System Context Pack] | tech_stack, trust_boundaries, sla | Infra platform, trust zones, SLA targets |
| REF-002 | [TMPL-007 Container & Component Inventory] | containers[] | Containers mapped to nodes |
| REF-003 | [025 Sequence, ERD & Deployment Diagrams] | Deployment section | Deployment diagram notation |
| REF-004 | [011 Core Architectural Principles] | 7 | Reliability & availability — HA patterns |
| REF-005 | [013 Truth, Verification & Doc Governance] | 3.5 | 7-label verification |
| REF-006 | [040 Living Architecture & Drift Control] | 6 | Living-context sync |

---

## 9. REVISION HISTORY

[TYPE: REFERENCE]

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | YYYY-MM-DD | Initial | Document created | [Reason] |

---

*TMPL-010 v2.0 — Deployment Topology Sheet. Governed by [RULES-001] and [013]. Verification labels mandatory. Parent: [TMPL-006].*
