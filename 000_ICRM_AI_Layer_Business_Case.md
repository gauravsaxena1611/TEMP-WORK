# 000 ICRM Centralized AI Layer — Business Case (Master)

```yaml
document_id: "000"
title: "ICRM Centralized AI Layer — Business Case (Master)"
parent_document: "none — 000-level master"
children: ["010 Value Proposition", "020 Capabilities & Use Cases", "030 Business Impact & KPIs", "040 Governance, Risk & Implementation", "050 Report & Deck Narrative"]
version: "1.4"
status: "Draft — Phases 1–5 consolidated; CIA wedge integrated; Stylus framing removed"
methodology_phase: "Business case / pre-pilot"
verification_mode: "HIGH"
review_trigger: "On completion of Phase 3 (KPIs) or any material change to ICRM AI strategy"
confidence_overall: "High on premises and positioning; Low on financials until derived bottom-up"
date: "2026-06-09"
```

---

## AI / Executive Summary

This is the refined business case for an **ICRM-specific AI platform — a shared conversational UI, orchestration, MCP, and governance layer** — *not* a new standalone app chatbot. The core repositioning is that, on today's trajectory, each ICRM app eventually builds its own conversational AI; the winning proposal is to build that capability **once for the whole portfolio**, domain-aware and centrally governed, rather than N times app by app. The platform is self-contained, with its own Citi-standard interface, and is kept entirely separate from any other Citi product.

The four original reasons (cost/efficiency, integration abstraction, unlocking Elastic, unlocking Starburst) hold up technically and are retained. The strongest *new* argument is **model-risk governance**: a single governed layer is the only practical way GenAI in ICRM becomes validatable at scale under SR 11-7-style scrutiny — which converts governance from a defensive section into the headline reason and the anti–"shadow AI" play.

Financial claims are reframed from absolute dollars/headcount into **defensible ratios and multipliers** anchored to two countable internal inputs. All factual claims carry verification labels; vendor/self-reported figures are flagged and used only directionally.

**The timing argument that anchors the ask:** Phase 1 is already live — the **CIA** application is integrating **Elastic's MCP** and the **CRC Common Services** APIs. The proposal is not "fund a new program" but "**build that work once, reusably, instead of N times**" — a far easier yes, and a rare case of genuine urgency: the cheapest moment to make the foundation reusable is while it is still being poured. *(See [010, Section 3.5].)*

---

## 1. Strategic Spine (one-line each)

0. **The wedge (timing)** — Phase 1 is in flight for CIA (Elastic MCP + CRC Common Services); generalize it now into a reusable platform rather than rebuild N times. *(See [010, Section 3.5].)*
1. **Positioning** — One shared ICRM platform (conversational UI + orchestration + MCP + governance) for the whole portfolio, not N app-private chatbots; self-contained, ICRM-owned. *(See [010, Section 3].)*
2. **Reason 1 — Cost & efficiency** — Build the shared scaffolding (integration, entitlements, MCP, agentic framework, UI, hosting, observability, model-risk sign-off) **once instead of N times**. *(See [010, Section 4].)*
3. **Reason 2 — Integration abstraction** — Reduce M×N point-to-point integration to **M+N**, and enable **bidirectional discovery** between apps that hard integration never allowed. *(See [010, Section 5].)*
4. **Reason 3 — Unlock Elastic** — Conversational, governed access to Elastic via its **official MCP server**, inside cross-system queries. *(See [010, Section 6].)*
5. **Reason 4 — Unlock Starburst** — Conversational analytics via Starburst's **MCP server**, built on **governed data products / parameterized queries**, not fragile free-form text-to-SQL. *(See [010, Section 7].)*
6. **Lead strategic reason — Model-risk governance** — One governed front door makes ICRM GenAI validatable; N shadow chatbots are N unvalidated models. *(See [010, Section 8].)*

---

## 2. Document Map

| Child | Title | Covers | Relationship |
|-------|-------|--------|--------------|
| 010 | Core Value Proposition & Strategic Alignment | Problem, positioning, 4 reasons, financial model (ratio form), new strategic reasons | `elaborates` this master |
| 020 | Capabilities & High-Impact Use Cases | Personas, human-in-the-loop interaction pattern, 4 step-gated scenarios, capability tiering | `elaborates` this master |
| 030 | Business Impact & KPIs | KPI quadrants (incl. new Quality & Reliability), measurement methodology, ratio-form value narrative | `elaborates` this master |
| 040 | Governance, Risk & Implementation | Delegated-ownership governance, Model Risk Management, expanded risk table, phased rollout with gates | `elaborates` this master |
| 050 | Report & Deck Narrative | Pitch sequence, ~14-slide arc, report outline, deck guardrails, eureka trio | `sequences` all children |

The set is complete (000 + 010–050). Illustrations ILL-1 to ILL-10 rendered; ILL-11 (in-flight CIA wedge) added. Next deliverable: the deck build.

---

## 3. Open Questions to Resolve Before the Pitch

- ❓ **N** — the real count of ICRM applications that would plausibly want conversational AI (from the app inventory). Anchors every cost ratio.
- ❓ **Scaffolding share** — common-vs-differentiating effort split, derived by decomposing **one** completed ICRM build. Present as a range until validated.
- ❓ **Pilot deltas** — measured time/quality gains per task. These become the only first-party proof points.
- ❓ **Delivery surface** — confirm the platform's own Citi-standard UI scope and where it lives within ICRM (it is self-contained; no dependency on other Citi products).
- ❓ **Platform ownership & funding** — who owns the reusable platform layer vs the CIA app team; how the generalization workstream is funded without taxing CIA's delivery.
- ❓ **Integration shape per service** — what gets MCP-wrapped vs consumed centrally as a shared CRC API client.

---

## 4. Illustration Catalog (deferred — to be produced after content lock)

| ID | Illustration | Supports |
|----|--------------|----------|
| ILL-1 | Spaghetti (point-to-point) vs hub-and-spoke, with quadratic-vs-linear curve | [010, Section 5] |
| ILL-2 | Layer diagram: data & systems → MCP layer → orchestration & governance → ICRM conversational UI | [010, Section 3] |
| ILL-3 | "Build once vs N times" iceberg / effort-mix split | [010, Section 4] |
| ILL-4 | Human-in-the-loop step-gate flow (draft → review → approve → next) | [020, Section 3] |
| ILL-5 | Governance tiering (retrieval / assist / act) mapped to controls | [020, Section 6] |
| ILL-6 | Persona before/after journey (Sarah) | [020, Section 4] |
| ILL-7 | KPI dashboard incl. Quality & Reliability quadrant (target vs baseline) | [030, Section 3] |
| ILL-8 | Risk matrix (likelihood × impact), with new rows pre/post mitigation | [040, Section 3] |
| ILL-9 | Governance/MRM model: user → entitlement check → MCP → source, with audit ledger | [040, Sections 1–2] |
| ILL-10 | Implementation roadmap with phase gates (retrieval → assist → act) | [040, Section 4] |
| ILL-11 | "CIA today → ICRM platform" — the fork in the road (generalize now vs rebuild N times) | [010, Section 3.5] |

---

## Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-06-09 | Initial | Master created to index refined Phase 1–2 children | Consolidate validated brainstorm into standards-compliant draft |
| 1.1 | 2026-06-09 | Update | Linked children 030 and 040; updated document map and illustration catalog (ILL-7 to ILL-10) | Phases 3–4 folded into child docs |
| 1.2 | 2026-06-09 | Update | Linked child 050; set status to full-set consolidated | Phase 5 folded; doc set complete |
| 1.3 | 2026-06-09 | Update | Integrated in-flight CIA wedge (exec summary, spine point 0, open questions, ILL-11); 010 and 040 updated to v1.1 | New input: Phase 1 live for CIA on Elastic MCP + CRC Common Services |
| 1.4 | 2026-06-10 | Revision | Removed all Stylus framing across the set; repositioned as one shared ICRM platform vs N app chatbots; dropped Stylus sources and ILL-2 surface label | Stylus was an explanatory analogy only — no integration; keep entirely separate |

---

## Sources & References

*Consolidated; child documents carry the subset relevant to each.*

### Primary Research (Tier 1)
| Source | Title | Date | URL |
|--------|-------|------|-----|
| Dell'Acqua et al., *Organization Science* | Navigating the Jagged Technological Frontier | 2023/2025 | https://pubsonline.informs.org/doi/10.1287/orsc.2025.21838 |
| Brynjolfsson et al., *Science* | Generative AI at Work (support agents) | 2025 | (Science 381) |
| OpenReview / arXiv | Text-to-SQL Benchmarks for Enterprise Realities (BIRD-Ent / Spider-Ent) | 2025 | https://openreview.net/forum?id=gXkIkSN2Ha |
| Elastic | Elasticsearch MCP server (official docs / GitHub) | 2026 | https://www.elastic.co/docs/solutions/search/mcp |
| Starburst | Starburst MCP server (Enterprise docs) | 2026 | https://docs.starburst.io/latest/starburst-ai/mcp-server.html |
| Federal Reserve / OCC | SR 11-7 Model Risk Management (principles applied to GenAI) | 2011– | (supervisory guidance) |

### Secondary Research (Tier 2)
| Source | Title | Date | URL |
|--------|-------|------|-----|
| The IIA | Transforming Audit Through AI / AI Auditing Framework | 2025 | https://www.theiia.org/en/content/articles/global-best-practices/2025/transforming-audit-through-ai/ |
| Compliance Week | Responsibly embedding GenAI into AML surveillance | 2026 | https://www.complianceweek.com/opinion/.../36431.article |
| Moody's | Model Risk Management in the Age of AI | 2025 | https://www.moodys.com/web/en/us/insights/resources/model-risk-management-in-the-age-of-ai.pdf |

### Competitor / Industry-Funded (Tier 3 — flagged, directional only)
| Source | Title | Date | Bias note |
|--------|-------|------|-----------|
| OpenAI | Morgan Stanley case study (98% adoption, 20→80%) | 2024–25 | Vendor + client self-report |
| SymphonyAI | AML investigation time reduction ("days→hours", "60%") | 2025–26 | Vendor marketing |
| Sardine | Agentic AI banking compliance playbook (tiered model) | 2026 | Vendor; framework useful, claims unverified |
| HBR (sponsored, Provectus/AWS) | Transforming Risk Management with GenAI | 2025 | Sponsored content |

### Further Reading
- COSO — audit-ready guidance for governing generative AI (2026)
- NIST AI RMF 1.0; ISO/IEC 42001
- Gartner — MCP / iPaaS adoption (cited secondhand; primary not obtained → not used as fact)

---

## Quality Gate (this document)

- [x] Correct document ID and naming convention
- [x] Parent document referenced (N/A — this is the master)
- [x] All claims cited with sources / labelled
- [x] Cross-references use correct format and point to real sections
- [x] Bidirectional references added (children reference 000)
- [x] Revision history table present and current
- [x] Further reading section included
