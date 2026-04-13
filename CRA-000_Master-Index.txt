# CRA Documentation Suite: Master Index
## Compliance Risk Assessment (CSI-180236) — Project Documentation

```yaml
---
document_id: "CRA-000 Master Index"
title: "CRA Documentation Suite — Master Index"
version: "1.0"
created: "2026-04-12"
status: "Final"
parent_document: "RULES-001 Documentation Standards"
template_version_used: "TMPL-000 (Master per RULES-001)"
---
```

---

## PROJECT OVERVIEW

| Attribute | Value |
|-----------|-------|
| **Application** | Compliance Risk Assessment (CRA) |
| **CSI ID** | CSI-180236 |
| **Platform** | Citi Risk & Controls (CRC) |
| **Documentation Suite Version** | 1.0 |
| **Generated** | 2026-04-12 |
| **Source Materials** | 7 primary project documents |
| **Governing Standards** | RULES-001 Documentation Standards; Truth & Verification Standards |

---

## DOCUMENT INDEX

| Doc ID | Title | Template | Status | Description |
|--------|-------|----------|--------|-------------|
| **CRA-000** | Master Index | RULES-001 000-level | Final | This document — navigation hub for all CRA documentation |
| **CRA-002** | Technical Reference — Methodology & Calculations | TMPL-002 | Final | CRA assessment methodology; inherent risk calculation; control environment; residual risk; 2025 hybrid approach; user roles |
| **CRA-003** | Workflow Runbook — Assessment Lifecycle | TMPL-003 | Final | Step-by-step procedure for all 7 CRA phases; maker/checker roles; CRC tool Preparer and Approver execution guides |
| **CRA-006** | System Context Pack — Architecture Reference | TMPL-006 | Final | Canonical architecture document; 8 actors; 15 integrations; 3 trust boundaries; CAP/PACELC; failure modes; diagram manifest |
| **CRA-007** | Container & Component Inventory | TMPL-007 | **Pending** | CRC microservices detail — UI, Rating Results, Scoping, Aggregation |
| **CRA-008** | Data & Flow Inventory | TMPL-008 | **Pending** | Entity model; data stores; L0/L1 data flows; PII classification |
| **CRA-009** | Sequence & Interaction Catalog | TMPL-009 | **Pending** | Swimlane diagrams for assessment lifecycle; calibration flow; reporting flow |
| **CRA-010** | Deployment Topology Sheet | TMPL-010 | **Pending** | COB/PROD server layout; container environments; DEV/UAT/PROD |

---

## SOURCE MATERIALS USED

| Source Document | Key Content Extracted |
|----------------|----------------------|
| `CRA_Dependencies.md` | 13 upstream integrations; 1 downstream consumer; infrastructure; data governance |
| `CRA_Study_Guide.md` | Methodology framework; business purpose; risk scales; four enterprise assessments |
| `CRA_User_and_Roles.md` | Role responsibilities matrix; maker/checker deliverables |
| `CRA_Workflows.md` | Seven lifecycle phases; minor workflows; role-step assignments |
| `notes.md` | Microservices architecture; snapshot rationale; third-party dashboard; 2025 timeline |
| `2025_CRA_Training_Transcript.md` | 2025 GAU-SAU migration; hybrid methodology; systemic calculations; tool walkthrough |
| `notebook-llm-notes.md` | Component analysis; integration detail; downstream usage |

---

## KEY DECISIONS LOG

| Date | Decision | Rationale | Impact |
|------|----------|-----------|--------|
| 2024 | Single data pull / snapshot architecture adopted | Mixed-date assessments caused data traceability failures | All assessors use same Oct-13 snapshot; local storage required |
| 2025 | GAU-SAU universe replaces Assessment Entities | Consent order target state; align with enterprise common universe | Broader publication scope per GAU; cross-team consistency |
| 2025 | Obligation-based publication mapping replaces anchoring risk | More precise compliance risk scoping | Publications appear multiple times in one GAU where multi-L2 obligation mapping exists |
| 2025 | 100% systemic publication-level ratings | Eliminate assessor subjectivity at most granular level | Preparer workload focuses on qualitative L2/L1; faster calculation |
| 2026 (planned) | Multiple snapshots per year | October-13-only snapshot becomes stale over 5-6 month window | Multiple data dates; cycle start September 2026 |

---

## VERIFICATION STANDARDS APPLIED

All documents in this suite apply the Truth & Verification Standards (attached to this project). Every factual claim carries one of seven labels:

| Label | Meaning |
|-------|---------|
| ✅ | Verified — traced to primary source document |
| ⚠️ | Flagged — used with caveat or partial confirmation |
| 💡 | Inference — logical derivation from verified facts |
| ❓ | Unresolved — cannot be confirmed from available sources |
| 🚩 | Outlier — contradicts mainstream; critically analysed |
| ❌ | Excluded — did not meet verification standards |
| 🗑️ | Bogus caught — false or unverifiable claim removed |

No fabricated data, statistics, or claims appear in any document in this suite.

---

## CROSS-PROJECT REFERENCES

| Reference | Document | Purpose |
|-----------|----------|---------|
| RULES-001 Documentation Standards | Project file | Governs all document format, naming, revision tracking |
| Truth & Verification Standards | Project file | Governs all claim verification and labelling |
| ARCH-001 Skill Architecture | Project file | Living Context Framework — architectural reference |
| ARCH-002 Context Template Generator | Project file | Template generation reference |

---

## DOCUMENT HIERARCHY

```
CRA-000 Master Index (this document)
├── CRA-002 Technical Reference — Methodology & Calculations (TMPL-002) ✅
├── CRA-003 Workflow Runbook — Assessment Lifecycle (TMPL-003) ✅
├── CRA-006 System Context Pack — Architecture (TMPL-006) ✅
├── CRA-007 Container & Component Inventory (TMPL-007) [Pending]
├── CRA-008 Data & Flow Inventory (TMPL-008) [Pending]
├── CRA-009 Sequence & Interaction Catalog (TMPL-009) [Pending]
└── CRA-010 Deployment Topology Sheet (TMPL-010) [Pending]
```

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-04-12 | Initial | Master index created for CRA documentation suite | Project documentation generation from all provided resources |

---

*Document: CRA-000 | Parent: RULES-001 Documentation Standards | Generated: 2026-04-12*
