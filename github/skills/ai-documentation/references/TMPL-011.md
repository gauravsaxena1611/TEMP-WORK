# [Portfolio / Product Suite Name]: Portfolio Register
## [Subtitle — e.g., "Enterprise System Inventory & Integration Map — v1.0"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-011: PORTFOLIO REGISTER  (v2.0)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: The master index for a portfolio of multiple systems.
Captures system inventory, cross-system integrations, shared
services, governance, technical debt, and the portfolio-level
APM (Application Portfolio Management) view.

The primary feed for:
  - Portfolio-level Integration Map diagrams
  - System Landscape diagrams (C4 L1 across all systems)
  - Enterprise Architecture overview

AUTHORING ORDER:
  1. Each system in the portfolio should have its own TMPL-006
     (System Context Pack). This document REFERENCES those.
  2. This is the 000-level (master) document for the portfolio.
     Individual TMPL-006s are its children.

THIS IS NOT FOR:
  - Individual system detail → use TMPL-006 for each system
  - Container detail for a system → TMPL-007
  - Data flow within a system → TMPL-008
  - Sequence flows → TMPL-009
  - Deployment of a single system → TMPL-010

ALIGNS WITH:
  [033 Portfolio & Multi-System Architecture] — enterprise
  framework patterns (TOGAF, Zachman, APM, DDD bounded contexts,
  integration patterns) applied at portfolio scope.

VERIFICATION REQUIREMENT (Verification Mode = High):
  ✅ verified  ⚠️ flagged  🚩 outlier  ❌ removed
  ❓ unresolved  💡 inference  🗑️ bogus caught

METHODOLOGY PHASE BINDING (from [030]):
  Phase-3-Capabilities (capability map) or
  Phase-4-HighLevel (integration map).
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ────────────────────────────────
document_id: "000-Portfolio"     # Always 000-level — this IS the master
title: "[Portfolio Name] — Portfolio Register"
version: "1.0"
created: "YYYY-MM-DD"
status: "Draft | Review | Final"
parent_document: "None — this is the root master document"
template_version_used: "TMPL-011 v2.0"

# ── METHODOLOGY PHASE ─────────────────────────────────────────
methodology_phase: "Phase-3-Capabilities"
design_methodology_ref: "[030 System Design Methodology, Section 4]"

# ── PORTFOLIO IDENTITY ────────────────────────────────────────
portfolio:
  name: ""              # "ACME Platform" | "E-Commerce Suite"
  description: ""       # One-sentence: what this portfolio of systems does
  owner: ""             # CTO | Chief Architect | Platform Lead
  domain: ""            # Primary business domain or line of business
  strategy_doc: ""      # Link to business/technology strategy document

# ── SYSTEM INVENTORY ──────────────────────────────────────────
# One entry per system in the portfolio.
# Each system should have its own TMPL-006 context pack.
systems:
  - id: ""              # "SYS-PAY" — must match system.system_id in TMPL-006
    name: ""            # "Payment Service"
    domain: ""          # Business domain
    criticality: ""     # mission-critical | business-critical | supporting
    lifecycle_phase: "" # greenfield | active | sunset | legacy
    owner_team: ""
    context_pack_ref: "" # Document ID of this system's TMPL-006
    tech_summary: ""    # One line: primary language/framework
    infra_platform: ""  # AWS | GCP | Azure | on-prem | hybrid
    apm_classification: ""  # TIME: Tolerate | Invest | Migrate | Eliminate
                            # Or Gartner 4Q: Value | Cost | Pioneer | Commodity
    technical_debt_score: "" # high | medium | low | not-assessed
    verification: ""    # ✅ | 💡 | ⚠️

# ── APM CLASSIFICATION (Application Portfolio Management) ─────
# From [033 Portfolio & Multi-System Architecture, Section 2]
# Using TIME model: Tolerate / Invest / Migrate / Eliminate
apm:
  model: ""             # "TIME" | "Gartner-4Q" | "custom"
  last_assessment: "YYYY-MM-DD"
  classification_criteria: ""  # Brief: what criteria were used
  tolerate:   []        # System IDs — keep, no new investment
  invest:     []        # System IDs — strategic, increase investment
  migrate:    []        # System IDs — move to better platform/pattern
  eliminate:  []        # System IDs — decommission with timeline
  verification: ""      # ✅ from ARB review | 💡 draft

# ── CROSS-SYSTEM INTEGRATIONS ─────────────────────────────────
# All integration points BETWEEN systems in this portfolio.
# consumed by: Integration Map diagram, System Landscape diagram
integrations:
  - id: ""              # "INT-001"
    from_system_id: ""  # System ID
    to_system_id: ""    # System ID
    name: ""            # "Orders → Payments"
    pattern: ""         # point-to-point | api-gateway | event-bus | esb
                        # | file-transfer | batch | shared-database (⚠️)
    protocol: ""        # REST | gRPC | Kafka | AMQP | SFTP | GraphQL
    direction: ""       # sync | async | bidirectional
    data_description: "" # Brief: "Order payment request JSON"
    data_classification: "" # PII | financial | public | internal
    coupling_level: ""  # tight | loose | decoupled
    owner_team: ""
    sla_contract: ""    # "p99 < 200ms" | "at-least-once" | "none"
    verification: ""    # ✅ | 💡 | ⚠️

# ── SHARED SERVICES ───────────────────────────────────────────
# Services consumed by 2+ systems in the portfolio.
# From [033 Portfolio & Multi-System Architecture, Section 5]
shared_services:
  - id: ""              # "SVC-001"
    name: ""            # "Identity & Access Management"
    system_id: ""       # Which system provides this service
    consumers: []       # List of system IDs consuming this service
    type: ""            # iam | logging | monitoring | config | notification
                        # | data-catalog | api-gateway | cdn | secret-mgmt
    governance_model: "" # "platform-owned" | "federated" | "vendor-managed"
    verification: ""    # ✅ | 💡 | ⚠️

# ── BOUNDED CONTEXTS (DDD) ────────────────────────────────────
# From [033 Portfolio & Multi-System Architecture, Section 3]
# Domain-Driven Design context boundaries at portfolio scale.
bounded_contexts:
  - id: ""              # "BC-001"
    name: ""            # "Order Management"
    systems_in_context: []   # System IDs in this context
    upstream_contexts: []    # BC IDs this context depends on
    downstream_contexts: []  # BC IDs that depend on this context
    integration_pattern: ""  # "partnership" | "customer-supplier"
                             # | "conformist" | "anticorruption-layer"
                             # | "open-host-service" | "published-language"
    canonical_data_model: "" # Link to MDM / canonical model document

# ── PORTFOLIO TECHNICAL DEBT SUMMARY ──────────────────────────
# From [033 Portfolio & Multi-System Architecture, Section 6]
technical_debt:
  portfolio_debt_level: ""  # high | medium | low | not-assessed
  highest_debt_systems: []  # System IDs with technical_debt_score: high
  debt_categories:
    architecture_debt: ""   # "3 monoliths blocking microservice migration"
    security_debt: ""       # "2 systems without mTLS"
    dependency_debt: ""     # "4 systems on EOL frameworks"
    test_coverage_debt: ""  # "3 systems below 60% coverage"
  remediation_plan_ref: ""  # Link to technical debt remediation roadmap

# ── GOVERNANCE ────────────────────────────────────────────────
governance:
  arb_exists: false     # Architecture Review Board exists?
  arb_cadence: ""       # "bi-weekly" | "monthly" | "none"
  adr_process: ""       # "ADRs required for all cross-system changes"
  golden_paths: []      # Mandated patterns: ["microservices on EKS", "Kafka for async"]
  prohibited_patterns: [] # ["shared database between services", "synchronous saga"]
  review_trigger: ""    # When portfolio governance is next reviewed

# ── LIVING-CONTEXT SYNC BLOCK (from [040] / [042]) ────────────
living_context:
  last_verified: "YYYY-MM-DD"
  last_verified_by: ""
  drift_check_date: "YYYY-MM-DD"
  drift_status: ""      # clean | minor-drift | significant-drift
  context_packs_verified: []  # List of TMPL-006 IDs confirmed current
  context_packs_stale: []     # List of TMPL-006 IDs needing refresh
  next_review_trigger: ""

# ── AI-OPTIMIZATION FIELDS ────────────────────────────────────
intent: >
  Enable architects and AI diagramming agents to understand the
  full portfolio — its systems, integrations, shared services,
  bounded contexts, and governance — as a single parseable
  master index that drives portfolio-level diagram generation.

consumption_context:
  - ai-reasoning
  - rag-retrieval
  - agentic-execution
  - human-reading

triggers:
  - "portfolio diagram"
  - "system landscape diagram"
  - "integration map across all systems"
  - "list all systems in the platform"
  - "APM classification for portfolio"
  - "portfolio technical debt"
  - "which systems are being sunset"

negative_triggers:
  - "single system detail → TMPL-006"
  - "container structure for a system → TMPL-007"
  - "deployment topology for a system → TMPL-010"

volatility: "stable"    # Changes with major portfolio decisions, not sprint changes
review_trigger: ""
research_validated: false
confidence_overall: "conditional"
---
```

> **AI SUMMARY**
> **Core Purpose:** Master portfolio register for [portfolio_name] — [N] systems, [N] integrations, [N] shared services.
> **Portfolio Domain:** [domain]
> **APM Summary:** Invest: [N] | Migrate: [N] | Tolerate: [N] | Eliminate: [N]
> **Portfolio Debt Level:** [high/medium/low]
> **Living-Context Status:** Last verified [date] | Context packs stale: [N]
> **Diagram Generation Ready:** ✅ Yes / ❌ No

---

# [Portfolio Name]: Portfolio Register

**Document ID:** 000-Portfolio
**Parent Document:** None — Root Master Document
**Version:** 1.0
**Created:** YYYY-MM-DD
**Status:** Draft

**Cross-References:**

| Relationship | Target | Reason |
|---|---|---|
| [SEE_ALSO] | [TMPL-006 — per system] | Each system in systems[] has a TMPL-006 context pack |
| [DEPENDS_ON] | [033 Portfolio & Multi-System Architecture, Section 2–6] | APM, DDD, integration patterns, technical debt frameworks |
| [DEPENDS_ON] | [011 Core Architectural Principles, Section 3] | Cohesion/coupling — portfolio-level loose coupling mandate |
| [DEPENDS_ON] | [013 Truth, Verification & Doc Governance, Section 3.5] | 7-label verification mandatory |
| [APPLIES] | [040 Living Architecture & Drift Control] | Living-context sync; context pack freshness tracking |
| [APPLIES] | [050 Stakeholder Communication] | Portfolio view serves executive and business audiences |

---

## TABLE OF CONTENTS

1. [Portfolio Overview](#1-portfolio-overview)
2. [System Inventory](#2-system-inventory)
3. [APM Classification](#3-apm-classification)
4. [Cross-System Integrations](#4-cross-system-integrations)
5. [Shared Services](#5-shared-services)
6. [Bounded Contexts](#6-bounded-contexts)
7. [Portfolio Technical Debt](#7-portfolio-technical-debt)
8. [Governance](#8-governance)
9. [Diagram Generation Manifest](#9-diagram-generation-manifest)
10. [Context Pack Freshness Tracker](#10-context-pack-freshness-tracker)
11. [Sources & References](#11-sources--references)
12. [Revision History](#12-revision-history)

---

## 1. PORTFOLIO OVERVIEW

[TYPE: CONTEXT]

<!-- One paragraph: what this portfolio is, who it serves, and the key business outcome it enables. -->
<!-- ✅ confirmed business facts | 💡 design intent | ⚠️ unverified claims -->

### 1.1 Portfolio Description

[2–3 sentences. Describe the portfolio's business purpose, its major capability groupings, and the organization that owns it.]

### 1.2 What This Portfolio Does NOT Cover

- [Out of scope system 1] — governed separately under [Other Portfolio]

---

## 2. SYSTEM INVENTORY

[TYPE: REFERENCE]

<!-- One row per system. Source from systems[] in YAML.
     ✅ = system confirmed active | 💡 = planned | ⚠️ = unverified status -->

| ID | System | Domain | Criticality | Lifecycle | APM | Tech Debt | Context Pack | Verification |
|----|--------|--------|-------------|-----------|-----|-----------|-------------|-------------|
| SYS-001 | | | | | | | | |

---

## 3. APM CLASSIFICATION

[TYPE: RESEARCH_FINDING]

<!-- From [033 Portfolio & Multi-System Architecture, Section 2].
     APM decisions require ARB approval — note source for each classification.
     ✅ = ARB-approved classification | 💡 = draft / under review -->

**APM Model Used:** [TIME | Gartner-4Q | custom]
**Last Assessment Date:** [Date] [✅/⚠️]

| Quadrant | Systems | Rationale |
|----------|---------|-----------|
| Invest | | |
| Migrate | | |
| Tolerate | | |
| Eliminate | | |

[DEPENDS_ON] → [033 Portfolio & Multi-System Architecture, Section 2]

---

## 4. CROSS-SYSTEM INTEGRATIONS

[TYPE: RESEARCH_FINDING]

<!-- One row per integration BETWEEN systems in this portfolio.
     ✅ = integration confirmed in prod | 💡 = designed | ⚠️ = known but unverified -->

| ID | From System | To System | Pattern | Protocol | Direction | Coupling | SLA | Verification |
|----|------------|-----------|---------|----------|-----------|---------|-----|-------------|
| INT-001 | | | | | | | | |

### 4.1 Integration Architecture Notes

[Prose: describe the overall integration strategy. Is there an ESB, an API gateway, an event mesh? Are there any point-to-point integrations that should be ⚠️ flagged for migration?]

[DEPENDS_ON] → [033 Portfolio & Multi-System Architecture, Section 3 — Integration Patterns]

---

## 5. SHARED SERVICES

[TYPE: REFERENCE]

<!-- Services consumed by 2+ systems. ✅ = in production | 💡 = planned | ⚠️ = informal/undocumented -->

| ID | Service | Provider System | Consumers | Type | Governance | Verification |
|----|---------|----------------|-----------|------|-----------|-------------|
| SVC-001 | | | | | | |

---

## 6. BOUNDED CONTEXTS

[TYPE: RESEARCH_FINDING]

<!-- From [033 Portfolio & Multi-System Architecture, Section 4 — DDD].
     💡 = context boundaries designed but not formally agreed -->

| ID | Context | Systems | Upstream | Downstream | Integration Pattern | Verification |
|----|---------|---------|----------|-----------|-------------------|-------------|
| BC-001 | | | | | | |

[DEPENDS_ON] → [033 Portfolio & Multi-System Architecture, Section 4]

---

## 7. PORTFOLIO TECHNICAL DEBT

[TYPE: RESEARCH_FINDING]

<!-- ✅ = quantified from actual code/infra analysis | 💡 = estimated -->

**Portfolio Debt Level:** [high | medium | low] [✅/💡/⚠️]

| Category | Description | Affected Systems | Severity | Verification |
|----------|-------------|-----------------|----------|-------------|
| Architecture Debt | | | | |
| Security Debt | | | | |
| Dependency Debt | | | | |
| Test Coverage Debt | | | | |

---

## 8. GOVERNANCE

[TYPE: REFERENCE]

<!-- ✅ = active governance | 💡 = planned | ⚠️ = governance gap -->

**ARB:** [Exists / Does not exist] [✅/⚠️]
**ADR Process:** [Description] [✅/💡/⚠️]

**Golden Paths (mandated patterns):**
- [Pattern 1] [✅ enforced | 💡 advisory]

**Prohibited Patterns:**
- [Anti-pattern 1] [✅ blocked in CI | 💡 advisory only ⚠️]

---

## 9. DIAGRAM GENERATION MANIFEST

[TYPE: REFERENCE]

<!-- Machine-readable instructions for the generating-architecture-diagrams skill. -->

| Diagram Type | Priority | Source Sections | Source Documents | Status |
|-------------|----------|----------------|-----------------|--------|
| System Landscape (C4 L1 Portfolio) | High | systems[], integrations[] | This doc + all TMPL-006s | |
| Integration Map | High | integrations[], shared_services[] | This doc | |
| Bounded Context Map | Medium | bounded_contexts[] | This doc | |
| APM Quadrant View | Low | apm[] | This doc | |
| Portfolio Deployment Overview | Low | systems[].infra_platform | All TMPL-010s | |

---

## 10. CONTEXT PACK FRESHNESS TRACKER

[TYPE: REFERENCE]

<!-- Track which TMPL-006 context packs are current vs. stale.
     A stale context pack = portfolio diagram may be inaccurate. -->

| System ID | Context Pack Doc ID | Last Verified | Drift Status | Action Needed |
|-----------|-------------------|--------------|-------------|---------------|
| SYS-001 | | | | |

---

## DIAGRAM-READY QUALITY GATE

```
[ ] systems[] has min 2 entries (1 system = not a portfolio)
[ ] Every system entry has context_pack_ref (or flagged "not yet authored")
[ ] integrations[] covers all known cross-system integrations
[ ] shared_services[] populated (or explicitly "none")
[ ] apm.model and apm.last_assessment populated
[ ] bounded_contexts[] has min 1 entry
[ ] Every integration and system carries a verification label (✅/💡/⚠️)
[ ] governance section populated (even if governance does not exist — note ⚠️)
[ ] living_context.context_packs_stale is an empty list OR action plan exists
[ ] living_context.last_verified populated
[ ] Revision history present with v1.0 entry
```

---

## 11. SOURCES & REFERENCES

[TYPE: REFERENCE]

### Project Document References

| Reference | Document | Section | Topic |
|-----------|----------|---------|-------|
| REF-001 | [033 Portfolio & Multi-System Architecture] | 2–6 | APM, DDD, integration patterns, technical debt |
| REF-002 | [011 Core Architectural Principles] | 3, 11, 12 | Coupling, evolvability, trade-off analysis |
| REF-003 | [013 Truth, Verification & Doc Governance] | 3.5 | 7-label verification |
| REF-004 | [040 Living Architecture & Drift Control] | 6 | Living-context sync; context pack freshness |
| REF-005 | [050 Stakeholder Communication] | 4 | Portfolio view for executive audience |

### Per-System Child Documents

| System | TMPL-006 | TMPL-007 | TMPL-008 | TMPL-009 | TMPL-010 |
|--------|---------|---------|---------|---------|---------|
| [System name] | [Doc ID] | [Doc ID] | [Doc ID] | [Doc ID] | [Doc ID] |

---

## 12. REVISION HISTORY

[TYPE: REFERENCE]

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | YYYY-MM-DD | Initial | Document created | [Reason] |

---

*TMPL-011 v2.0 — Portfolio Register. Governed by [RULES-001] and [013]. Verification labels mandatory. This is the 000-level master document for the portfolio.*
