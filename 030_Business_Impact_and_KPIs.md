# 030 Business Impact & KPIs

```yaml
document_id: "030"
title: "Business Impact & KPIs"
parent_document: "000 ICRM AI Layer — Business Case (Master)"
version: "1.1"
status: "Draft — refined from Phase 3"
methodology_phase: "Business case / pre-pilot"
verification_mode: "HIGH"
confidence_overall: "High on the reframed structure; all targets remain hypotheses until piloted"
date: "2026-06-09"
```

> Parent: [000, Section 1]. Cross-refs: [010, Section 4] (financial model), [020, Section 6] (capabilities), [040, Section 3] (pilot gates).

---

## 1. Framing — why this section decides 5% vs 95%

MIT's NANDA "GenAI Divide" report found that ~95% of enterprise AI pilots delivered **no measurable P&L impact**, driven by a "learning gap" and poor workflow integration rather than model quality. ⚠️ FLAGGED [Source: MIT NANDA, 2025 — single-origin, not peer-reviewed, publicly contested; directional only].

🚩 **Double-edged (state it, don't hide it):** the same report found vendor-built tools succeeded ~2× internal IT-only builds, and that back-office/compliance is where ROI actually lives. This supports the ICRM use case **and** warns against an IT-only build with poor workflow integration — so the lesson is a strong business-plus-engineering partnership, vendor/foundation-model leverage where sensible, and tight integration into real ICRM workflows.

**Consequence:** every target below is a **hypothesis tested against a measured baseline in the pilot**, not an asserted number.

---

## 2. Measurement Methodology (currently missing — must add)

- **Baseline first.** No "% reduction" claim is credible without a measured "before." Each metric gets a pilot baseline; the target is a *range* against it.
- **Leading vs lagging.** Adoption/usage are leading; time saved and error reduction are lagging. Do not conflate.
- **Attribution discipline.** Before/after (ideally a small control group) so gains are attributable to the tool.

---

## 3. KPI Quadrants

### A. Efficiency & Productivity
| KPI | Original | Refined |
|-----|----------|---------|
| Time-to-information | ">80%" (45→5 min) | ⚠️ Measure baseline; floor on peer-reviewed ~25% faster / ~40% quality [✅ Tier 1, Dell'Acqua], larger as stretch, pilot-validated |
| Manual reporting | "500 hrs/month" | ❌ absolute removed → **% reduction in routine-reporting hours** for onboarded teams, vs baseline |
| Task completion | ">90%" | 🚩 redefine as **correct AND source-verified** (not "answer produced"); scope to **in-scope governed query types** |
| New-hire time-to-productivity | "50%" | ✅ best-evidenced (novices benefit most [Brynjolfsson, Tier 1]); baseline + range + pilot |

### B. Cost Savings & Avoidance
- All dollar figures → ❌ removed; replaced by the **ratio/multiplier model** in [010, Section 4] (combinatorics, build-once-vs-N, recurring multiplier, model-risk multiplier). Do not restate absolutes here.
- **"Productivity recapture = 14 FTEs"** → 🚩 cut or recast. Fragmented minutes across many people do not aggregate into redeployable/removable headcount. Reframe as **"capacity freed (%)"** classified as **soft/contingent**, never hard savings.

### C. Risk Reduction & Governance
- **"100% audit trail" / "100% consistent entitlement"** → reframe from measured KPI to **architectural guarantee by design**; measure **coverage and exceptions**, not a triumphant 100%.
- **Manual data-error reduction** → ✅ keep; needs baseline + method.
- **Proactive insight generation** → ⚠️ aspirational; later phase.

### D. Adoption & Engagement
- MAU/DAU, NPS, breadth → ✅ keep, but add **task-level retention / repeat-use** (MIT: adoption ≠ value; non-integrated tools get abandoned) and a **trust proxy** (% answers accepted unedited). NPS alone is insufficient.

### E. Quality & Reliability — NEW quadrant (the missing piece)
The other quadrants measure speed, cost, posture, and adoption — **not correctness**, which is the metric that matters most for a compliance tool and is the model-risk evidence [040, MRM section]. Built on the standard "RAG Triad" + guardrails:
- **Faithfulness / groundedness** — answer supported by retrieved sources; the primary tool for detecting hallucinations and, for most production teams, the most critical metric in the stack ✅ [Tier 2/3 + arXiv Tier 1].
- **Citation coverage** — claims backed by linked sources (ties to the "every claim cites its source" design).
- **Answer relevancy; retrieval precision/recall** — right answer, right question, right documents.
- **Hallucination rate; human-override / escalation rate; % accepted unedited** — operational guardrails doubling as trust signals.
- Measurable with RAGAS / TruLens / DeepEval ✅ [Tier 2/3]; mirrors Morgan Stanley's pre-deployment eval framework [020, Scenario 4].

---

## 4. Benefits Framework (recast)
- **Hard (defensible):** consolidated run-cost (one support rotation / observability / model-risk cycle vs N); integration combinatorics; one-time build vs N builds.
- **Soft / contingent (label as such):** capacity freed, faster decisions, employee experience, strategic optionality. **Move "FTE recapture" here.**

## 5. Business Value Narrative (ratio form — no absolutes)
- *"We build the shared foundation once instead of N times; point-to-point integration cost grows with the square of our app count, a hub grows linearly."*
- *"We free a measurable share of analyst capacity from routine gathering — validated in pilot — and redirect it to higher-value risk work."*
- *"Every query is logged and entitlement-checked by design, giving a complete, auditable record we don't have today."*

---

## Revision History
| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-06-09 | Initial | Refined Phase 3 into standards-compliant child doc | Convert absolutes to baseline-anchored ratios; cut FTE overclaim; add measurement methodology and the Quality & Reliability quadrant |
| 1.1 | 2026-06-10 | Revision | Reworked the MIT double-edged note to drop the build-on-enterprise-platform conclusion | Stylus framing removed across the set |

---

## Sources & References

### Primary (Tier 1)
| Source | Title | Date | URL |
|--------|-------|------|-----|
| Dell'Acqua et al., *Organization Science* | Jagged Technological Frontier | 2023 | https://pubsonline.informs.org/doi/10.1287/orsc.2025.21838 |
| Brynjolfsson et al., *Science* | Generative AI at Work | 2025 | (Science 381) |
| OpenReview/arXiv | Text-to-SQL Benchmarks for Enterprise Realities | 2025 | https://openreview.net/forum?id=gXkIkSN2Ha |

### Secondary (Tier 2)
| Source | Title | Date | URL |
|--------|-------|------|-----|
| MIT NANDA (via Fortune et al.) | The GenAI Divide: State of AI in Business 2025 | 2025 | https://finance.yahoo.com/news/mit-report-95-generative-ai-105412686.html |
| Comet / Atlan / Evidently | RAG evaluation metrics (RAGAS/TruLens/DeepEval) | 2026 | https://www.comet.com/site/blog/rag-evaluation/ |

### Competitor / Industry-Funded (Tier 3 — flagged)
| Source | Title | Date | Bias note |
|--------|-------|------|-----------|
| Label Your Data / Deepchecks / Kinde | RAG metric guides | 2025–26 | Vendor; corroborated by arXiv |

### Further Reading
- ISO/IEC 42001; NIST AI RMF (measurement/monitoring functions)

---

## Quality Gate
- [x] Correct document ID and naming convention
- [x] Parent document referenced ([000])
- [x] All claims cited / labelled
- [x] Cross-references valid and bidirectional
- [x] Revision history present
- [x] Further reading included
