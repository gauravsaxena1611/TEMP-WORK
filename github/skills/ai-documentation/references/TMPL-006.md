# [System Name]: System Context Pack
## [Subtitle — e.g., "Architecture Reference Document" or "Canonical System Definition"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-006: SYSTEM CONTEXT PACK  (v2.0)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: The canonical per-application architecture document.
PRIMARY FEED for the generating-architecture-diagrams skill.
Every YAML field is structured to be machine-parseable by a
downstream diagramming agent.

USE CASES:
  - Onboarding a new system into the architecture corpus
  - Context document before any C4 / DFD / sequence diagram
  - Capturing the 12 architectural principles applied to THIS system
  - CAP/PACELC trade-off documentation
  - Living-context reference that a diagram agent re-reads over time

THIS IS NOT FOR:
  - Container/component detail → use TMPL-007
  - Data flow and entity detail → use TMPL-008
  - Sequence flows → use TMPL-009
  - Deployment topology → use TMPL-010
  - Portfolio multi-system view → use TMPL-011
  - Meeting records → use TMPL-012
  - AI model cards → use TMPL-013
  - Incident post-mortems → use TMPL-014

VERIFICATION REQUIREMENT (Verification Mode = Medium-High minimum):
  All factual claims MUST carry one of:
  ✅ verified  ⚠️ flagged  🚩 outlier  ❌ removed
  ❓ unresolved  💡 inference  🗑️ bogus caught
  Per [013 Truth, Verification & Doc Governance, Section 3.5]

METHODOLOGY PHASE BINDING:
  Set methodology_phase in YAML from the 8-phase flow:
  [030 System Design Methodology, Section 4]

LIVING-CONTEXT SYNC:
  Populate living_context block. Review triggers and drift
  checks from [040 Living Architecture & Drift Control]
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ───────────────────────────────
document_id: "[XXX Short-Title]"
title: "[System Name] — System Context Pack"
version: "1.0"
created: "YYYY-MM-DD"
status: "Draft | Review | Final"
parent_document: ""
template_version_used: "TMPL-006 v2.0"

# ── METHODOLOGY PHASE (from [030 System Design Methodology]) ─
methodology_phase: ""
# Options: Phase-1-Requirements | Phase-2-Constraints
#          Phase-3-Capabilities | Phase-4-HighLevel
#          Phase-5-DetailedDesign | Phase-6-Validation
#          Phase-7-Documentation | Phase-8-Review
design_methodology_ref: "[030 System Design Methodology, Section 4]"

# ── SYSTEM IDENTITY ──────────────────────────────────────────
system:
  name: ""               # Short canonical: "PaymentService"
  full_name: ""          # Human-readable: "Payment Processing Service"
  system_id: ""          # Stable diagram node ID: "SYS-PAY"
  domain: ""             # Business domain: "Finance" | "Identity" | "Logistics"
  capability: ""         # One-sentence business capability
  system_type: ""        # SaaS | microservice | monolith | data-pipeline
                         # | event-stream | batch-processor | mobile-app
  lifecycle_phase: ""    # greenfield | active | sunset | legacy
  criticality: ""        # mission-critical | business-critical | supporting

# ── ACTORS (C4 L1, Swimlane, Sequence feed) ──────────────────
actors:
  - id: ""               # "ACT-001"
    name: ""             # "Customer"
    type: ""             # human | external-system | internal-system | device
    description: ""      # One-sentence role
    trust_level: ""      # trusted | semi-trusted | untrusted | anonymous

# ── EXTERNAL INTEGRATIONS (C4 L1, DFD L0 feed) ──────────────
external_integrations:
  - id: ""               # "INT-001"
    name: ""             # "Stripe Payment Gateway"
    type: ""             # api | event-stream | database | file | webhook
    direction: ""        # inbound | outbound | bidirectional
    protocol: ""         # REST | gRPC | AMQP | SFTP | GraphQL | SOAP
    data_classification: "" # PII | financial | public | internal | confidential
    sla_dependency: ""   # required | optional | degraded-mode-ok
    owner_team: ""

# ── TRUST BOUNDARIES (C4 L1, Security Architecture feed) ─────
trust_boundaries:
  - id: ""               # "TB-001"
    name: ""             # "DMZ" | "Internal Network" | "Public Internet"
    type: ""             # network | auth | service-perimeter | data-classification
    components_inside: []
    controls: []         # mTLS | OAuth2 | VPN | WAF | IAM role | etc.

# ── DATA CLASSIFICATION SUMMARY ──────────────────────────────
data_classifications:
  - type: ""             # PII | PHI | financial | credentials | public | internal
    description: ""
    storage_location: ""
    encryption_at_rest: ""
    encryption_in_transit: ""

# ── SLAs ─────────────────────────────────────────────────────
sla:
  availability_target: ""   # "99.9%"
  rpo: ""                   # "1 hour"
  rto: ""                   # "30 minutes"
  p99_latency_ms: ""        # "200ms"
  throughput: ""            # "500 req/s peak"
  data_retention: ""        # "7 years (regulatory)"

# ── TECH STACK (Container/Component Inventory feed) ──────────
tech_stack:
  languages: []
  frameworks: []
  databases: []
  message_brokers: []
  infra_platform: ""        # AWS | GCP | Azure | hybrid | on-prem
  container_runtime: ""     # Docker/Kubernetes 1.29 | ECS | none
  iac_tool: ""              # Terraform | Pulumi | CloudFormation | none
  observability: []

# ── 12 PRINCIPLES APPLIED (from [011]) ───────────────────────
principles_applied:
  separation_of_concerns: ""     # "strong" | "partial" | "gap" | "N/A"
  modularity: ""
  cohesion_and_coupling: ""
  abstraction_layers: ""
  solid_at_scale: ""
  scalability: ""
  reliability_availability: ""
  maintainability: ""
  security_by_design: ""
  cost_awareness: ""
  evolvability: ""
  tradeoff_analysis: ""

# ── CAP / PACELC (from [011 Section 13]) ─────────────────────
cap_pacelc:
  cap_choice: ""          # CP | AP | CA (single-node only)
  pacelc_choice: ""       # PC/LC | PC/EL | PA/LC | PA/EL
  rationale: ""

# ── FAILURE MODES ─────────────────────────────────────────────
failure_modes:
  - id: ""
    scenario: ""
    impact: ""
    severity: ""           # critical | high | medium | low
    mitigation: ""
    detection: ""

# ── EVOLVABILITY ──────────────────────────────────────────────
evolvability:
  current_pattern: ""      # modular-monolith | microservices | monolith
  target_pattern: ""
  migration_strategy: ""   # strangler-fig | parallel-run | big-bang | N/A
  coupling_risks: []
  extension_points: []

# ── STAKEHOLDER VIEWS (from [050]) ────────────────────────────
stakeholder_views:
  business_view:
    primary_audience: []
    key_message: ""
    diagram_type: ""       # C4-L1-Context-simplified | Business-Flow
  technical_view:
    primary_audience: []
    key_message: ""
    diagram_type: ""       # C4-L2-Container | Sequence | ERD
  security_view:
    primary_audience: []
    key_message: ""
    diagram_type: ""       # C4-L1-with-trust-boundaries | Threat-model

# ── DIAGRAM GENERATION MANIFEST ───────────────────────────────
diagram_manifest:
  - diagram_type: "C4-L1-Context"
    source_sections: ["actors", "external_integrations", "trust_boundaries"]
    priority: "high"
    status: ""             # pending | complete | stale
  - diagram_type: "C4-L2-Container"
    source_sections: ["tech_stack", "trust_boundaries"]
    source_documents: ["TMPL-007"]
    priority: "high"
    status: ""
  - diagram_type: "DFD-L0"
    source_sections: ["external_integrations", "data_classifications"]
    source_documents: ["TMPL-008"]
    priority: "medium"
    status: ""
  - diagram_type: "Deployment"
    source_documents: ["TMPL-010"]
    priority: "medium"
    status: ""

# ── LIVING-CONTEXT SYNC BLOCK (from [040] / [042]) ────────────
living_context:
  last_verified: "YYYY-MM-DD"
  last_verified_by: ""
  drift_check_date: "YYYY-MM-DD"
  drift_status: ""         # clean | minor-drift | significant-drift
  linked_commit: ""        # Git SHA
  linked_adr: []
  next_review_trigger: ""
  automation_pipeline: ""  # Structurizr-DSL | manual | none

# ── AI-OPTIMIZATION FIELDS ────────────────────────────────────
intent: >
  Enable architects and AI diagramming agents to understand
  [system_name] — its business purpose, boundaries, actors,
  integrations, and architectural trade-offs — as a single
  parseable source of truth.

consumption_context:
  - human-reading
  - ai-reasoning
  - rag-retrieval
  - agentic-execution

triggers:
  - "what is [system] and how does it work"
  - "generate architecture diagram for [system]"
  - "[system] integration overview"
  - "[system] security boundary"
  - "[system] CAP trade-off"

negative_triggers:
  - "container or component detail → TMPL-007"
  - "data entities or flows → TMPL-008"
  - "step-by-step runbook → TMPL-003"

volatility: "fast-changing"
review_trigger: ""
research_validated: false
confidence_overall: "conditional"
---
```

> **AI SUMMARY**
> **Core Purpose:** [One sentence — what this system does and why it exists]
> **Key Design Decisions:** [2–3 most important, each labeled ✅/⚠️/💡]
> **Primary Actors:** [comma-separated list]
> **Critical External Dependencies:** [comma-separated list]
> **CAP/PACELC Stance:** [e.g. AP / PA/EL]
> **Living-Context Status:** Last verified [date] | Drift: [clean/minor/significant]
> **Diagram Generation Ready:** ✅ Yes / ❌ No — [reason if No]

---

# [System Name]: System Context Pack

**Document ID:** [XXX] [System Name] — System Context Pack
**Parent Document:** [Parent Doc ID, Section X]
**Version:** 1.0
**Created:** YYYY-MM-DD
**Status:** Draft

**Cross-References:**

| Relationship | Target | Reason |
|---|---|---|
| [DEPENDS_ON] | [000 Master or Portfolio Register] | Root hierarchy |
| [SEE_ALSO] | [TMPL-007 Container & Component Inventory] | Container/component detail |
| [SEE_ALSO] | [TMPL-008 Data & Flow Inventory] | Data entities and flows |
| [SEE_ALSO] | [TMPL-009 Sequence & Interaction Catalog] | Interaction sequences |
| [SEE_ALSO] | [TMPL-010 Deployment Topology Sheet] | Environment and node detail |
| [DEPENDS_ON] | [011 Core Architectural Principles, Section 2–13] | 12 principles applied in Section 7 |
| [DEPENDS_ON] | [013 Truth, Verification & Doc Governance, Section 3.5] | 7-label system mandatory |
| [APPLIES] | [040 Living Architecture & Drift Control, Section 6] | Living-context sync block |
| [APPLIES] | [050 Stakeholder Communication, Section 4] | Stakeholder view separation |

---

## TABLE OF CONTENTS

1. [System Purpose & Business Capability](#1-system-purpose--business-capability)
2. [Actors & External Integrations](#2-actors--external-integrations)
3. [Trust Boundaries & Security Model](#3-trust-boundaries--security-model)
4. [Data Classification Summary](#4-data-classification-summary)
5. [SLAs & Reliability Targets](#5-slas--reliability-targets)
6. [Tech Stack](#6-tech-stack)
7. [12 Architectural Principles — Applied](#7-12-architectural-principles--applied)
8. [CAP / PACELC Trade-Off Analysis](#8-cap--pacelc-trade-off-analysis)
9. [Failure Modes & Mitigations](#9-failure-modes--mitigations)
10. [Evolvability & Migration Strategy](#10-evolvability--migration-strategy)
11. [Stakeholder Views](#11-stakeholder-views)
12. [Diagram Generation Manifest](#12-diagram-generation-manifest)
13. [Gestalt & Visual Clarity Notes](#13-gestalt--visual-clarity-notes)
14. [Sources & References](#14-sources--references)
15. [Revision History](#15-revision-history)

---

## 1. SYSTEM PURPOSE & BUSINESS CAPABILITY

[TYPE: CONTEXT]

<!-- One paragraph: what this system does, who it serves, what business outcome it enables. -->
<!-- ✅ confirmed facts | 💡 design intent / inference | ⚠️ unverified claims -->

### 1.1 System Description

[2–3 sentences. Business responsibility first, then technical responsibility.]

### 1.2 Scope Boundaries — What This System Is NOT Responsible For

- [Out-of-scope item 1] — Responsibility of [System X]
- [Out-of-scope item 2] — Future roadmap; not yet designed

---

## 2. ACTORS & EXTERNAL INTEGRATIONS

[TYPE: RESEARCH_FINDING]

<!-- Drawn from YAML actors[] and external_integrations[]. Label every claim. -->

### 2.1 Primary Actors

| ID | Actor | Type | Trust Level | Description |
|----|-------|------|-------------|-------------|
| ACT-001 | | | | |

### 2.2 External Integrations

| ID | Integration | Type | Direction | Protocol | Data Class | SLA Dependency |
|----|-------------|------|-----------|----------|-----------|----------------|
| INT-001 | | | | | | |

---

## 3. TRUST BOUNDARIES & SECURITY MODEL

[TYPE: RESEARCH_FINDING]

<!-- Security-by-design means this section must be populated — an empty section is a ⚠️ gap. -->

### 3.1 Trust Boundary Map

[Prose description of how data and requests cross trust boundaries.]

### 3.2 Authentication & Authorization Controls

<!-- ✅ = in production | 💡 = designed not deployed | ⚠️ = known gap -->

[Controls enforcing each boundary, with verification labels on each claim.]

---

## 4. DATA CLASSIFICATION SUMMARY

[TYPE: REFERENCE]

<!-- For detailed entity-level data mapping, see [TMPL-008 Data & Flow Inventory]. -->

| Classification | Description | Storage | At-Rest Encryption | In-Transit |
|---------------|-------------|---------|-------------------|------------|
| | | | | |

---

## 5. SLAs & RELIABILITY TARGETS

[TYPE: RESEARCH_FINDING]

<!-- ✅ = from signed SLA document | 💡 = engineering target, not contractual -->

| Metric | Target | Source | Notes |
|--------|--------|--------|-------|
| Availability | | | |
| RPO | | | |
| RTO | | | |
| p99 Latency | | | |
| Throughput | | | |
| Data Retention | | | |

---

## 6. TECH STACK

[TYPE: REFERENCE]

| Layer | Technology | Version | Notes |
|-------|------------|---------|-------|
| Language | | | |
| Framework | | | |
| Database | | | |
| Message Broker | | | |
| Infrastructure | | | |
| Observability | | | |

---

## 7. 12 ARCHITECTURAL PRINCIPLES — APPLIED

[TYPE: RESEARCH_FINDING]

<!-- Per [011 Core Architectural Principles, Sections 2–13].
     Rating: ✅ strong | ⚠️ partial/gap | ❓ not assessed | 💡 designed/intended -->

| # | Principle | Rating | Evidence / Notes |
|---|-----------|--------|-----------------|
| 1 | Separation of Concerns | | |
| 2 | Modularity | | |
| 3 | Cohesion & Coupling | | |
| 4 | Abstraction Layers | | |
| 5 | SOLID at System Scale | | |
| 6 | Scalability | | |
| 7 | Reliability & Availability | | |
| 8 | Maintainability | | |
| 9 | Security-by-Design | | |
| 10 | Cost-Awareness | | |
| 11 | Evolvability | | |
| 12 | Trade-Off Analysis (CAP/PACELC) | | |

[DEPENDS_ON] → [011 Core Architectural Principles, Section 2–13]

---

## 8. CAP / PACELC TRADE-OFF ANALYSIS

[TYPE: RESEARCH_FINDING]

<!-- This section must state a stance — blank is not acceptable for any production system. -->

**CAP Choice:** [CP | AP | CA] — [✅ confirmed / 💡 designed]
**PACELC Choice:** [PC/LC | PC/EL | PA/LC | PA/EL] — [✅ confirmed / 💡 designed]
**Rationale:** [One paragraph justifying this trade-off for this system's specific use case]
**Implication for Data Stores:** [How the chosen stance constrains or enables database/cache choices]

[DEPENDS_ON] → [011 Core Architectural Principles, Section 13]

---

## 9. FAILURE MODES & MITIGATIONS

[TYPE: RESEARCH_FINDING]

<!-- Minimum 3 failure modes required for production systems.
     ✅ mitigation tested in prod | 💡 designed not tested | ⚠️ known gap -->

| ID | Scenario | Impact | Severity | Mitigation | Detection |
|----|----------|--------|----------|------------|-----------|
| FM-001 | | | | | |
| FM-002 | | | | | |
| FM-003 | | | | | |

---

## 10. EVOLVABILITY & MIGRATION STRATEGY

[TYPE: CONTEXT]

<!-- Per [011 Core Architectural Principles, Section 12 — Evolvability] -->

**Current Pattern:** [modular monolith | microservices | monolith]
**Target Pattern:** [if different]
**Migration Strategy:** [strangler-fig | parallel-run | big-bang | N/A]

**Known Coupling Risks:**
- [Coupling point 1] — ⚠️ impedes [specific type of change]

**Extension Points:**
- [Extension point 1] — 💡 enables [future capability]

---

## 11. STAKEHOLDER VIEWS

[TYPE: CONTEXT]

<!-- Per [050 Stakeholder Communication, Section 4]. Never collapse all views into one. -->

### 11.1 Business View
**Audience:** [Product Manager, C-suite, Business Owner]
**Message:** [Business capability delivered]
**Recommended Diagram:** C4-L1-Context (simplified, no tech labels)

### 11.2 Technical View
**Audience:** [Backend Engineers, DevOps, QA]
**Message:** [Containers, contracts, responsibilities]
**Recommended Diagram:** C4-L2-Container + C4-L3 for focal areas

### 11.3 Security View
**Audience:** [Security Team, CISO, Compliance]
**Message:** [Trust boundaries and data classifications]
**Recommended Diagram:** C4-L1 annotated with trust boundaries

[DEPENDS_ON] → [050 Stakeholder Communication, Section 4]

---

## 12. DIAGRAM GENERATION MANIFEST

[TYPE: REFERENCE]

<!-- Machine-readable by the generating-architecture-diagrams skill. -->

| Diagram Type | Priority | Source Sections | Source Documents | Status |
|-------------|----------|----------------|-----------------|--------|
| C4-L1-Context | High | actors, external_integrations, trust_boundaries | This doc | |
| C4-L2-Container | High | tech_stack, trust_boundaries | TMPL-007 | |
| C4-L3-Component | Medium | per focal container | TMPL-007 | |
| DFD-L0 | Medium | external_integrations, data_classifications | TMPL-008 | |
| DFD-L1 | Medium | per bounded context | TMPL-008 | |
| Swimlane | Medium | per critical flow | TMPL-009 | |
| Sequence | High | per critical interaction | TMPL-009 | |
| ERD | Medium | per data domain | TMPL-008 | |
| Deployment | Medium | per environment | TMPL-010 | |

---

## 13. GESTALT & VISUAL CLARITY NOTES

[TYPE: REFERENCE]

<!-- Per [012 Gestalt & Visual Clarity Standards]. Mandatory when embedded visuals exist. -->

**Color Coding:** [Domain color scheme if applicable]
**WCAG Contrast:** [AA | AAA | not yet verified] ✅/⚠️
**Cognitive Load Target:** [Max containers per diagram] [Max actors shown]
**Typography:** [Font and minimum size for embedded diagrams]

[DEPENDS_ON] → [012 Gestalt & Visual Clarity Standards]

---

## 14. SOURCES & REFERENCES

[TYPE: REFERENCE]

### Project Document References

| Reference | Document | Section | Topic |
|-----------|----------|---------|-------|
| REF-001 | [011 Core Architectural Principles] | 2–13 | 12 principles framework |
| REF-002 | [013 Truth, Verification & Doc Governance] | 3.5 | 7-label verification system |
| REF-003 | [030 System Design Methodology] | 4 | 8-phase methodology binding |
| REF-004 | [040 Living Architecture & Drift Control] | 6 | Living-context sync |
| REF-005 | [050 Stakeholder Communication] | 4 | Stakeholder view separation |
| REF-006 | [012 Gestalt & Visual Clarity Standards] | All | Visual standards for diagrams |

### External Technical Sources

| Source | Title | Date | URL |
|--------|-------|------|-----|
| [Source name] | [Title] | [Date] | [URL] |

---

## 15. REVISION HISTORY

[TYPE: REFERENCE]

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | YYYY-MM-DD | Initial | Document created | [Reason] |

---

## DIAGRAM-READY QUALITY GATE

```
CHECKLIST — must pass before generating-architecture-diagrams skill is invoked

YAML FIELDS
[ ] system.system_id populated (diagram node ID)
[ ] actors[] has min 1 entry with id, name, type, trust_level
[ ] external_integrations[] has min 1 entry with id, name, protocol, direction
[ ] trust_boundaries[] has min 1 entry
[ ] data_classifications[] has min 1 entry
[ ] tech_stack.infra_platform populated
[ ] cap_pacelc.cap_choice populated (not blank)
[ ] diagram_manifest[] has C4-L1-Context entry

LINKED DOCUMENTS
[ ] TMPL-007 exists or flagged "not yet authored"
[ ] TMPL-008 exists or flagged "not yet authored"
[ ] TMPL-009 exists or flagged "not yet authored"
[ ] TMPL-010 exists or flagged "not yet authored"

VERIFICATION
[ ] Every factual claim in Sections 2–10 carries a label (✅/⚠️/💡/❓)
[ ] SLA targets in Section 5 are sourced (not free-floating numbers)
[ ] No blank entries in actors, external_integrations, trust_boundaries
[ ] failure_modes[] has min 3 entries for production systems

LIVING CONTEXT
[ ] living_context.last_verified populated
[ ] living_context.drift_status populated
[ ] living_context.next_review_trigger is not blank

STANDARD QUALITY GATE (RULES-001 / [013 Section 4.6])
[ ] Correct document ID and naming convention
[ ] Parent document referenced
[ ] Cross-references use format [DOC ID, Section X.X]
[ ] Bidirectional references added
[ ] Revision history present with v1.0 entry
```

---

*TMPL-006 v2.0 — System Context Pack. Governed by [RULES-001] and [013 Truth, Verification & Documentation Governance]. Verification labels mandatory per [013, Section 3.5].*
