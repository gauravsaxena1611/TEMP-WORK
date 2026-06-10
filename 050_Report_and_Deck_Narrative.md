# 050 Report & Deck Narrative

```yaml
document_id: "050"
title: "Report & Deck Narrative"
parent_document: "000 ICRM AI Layer — Business Case (Master)"
version: "1.1"
status: "Draft — Phase 5; Stylus framing removed"
methodology_phase: "Business case / pre-pilot"
verification_mode: "MEDIUM — structuring previously-verified content"
confidence_overall: "High — structure over already-validated children; no new external claims"
date: "2026-06-09"
```

> Parent: [000, Section 1]. This document sequences the validated material in [010]–[040] into a director-level pitch. It introduces no new claims; all evidence lives in the children.

---

## 1. Narrative Spine

A director needs four beats in order: **this costs us today → here's the simple idea → it pays off and we can prove it → and it's safe and low-risk to start.**

The single most important structural move: **answer "why a central platform, not a chatbot per app?" before they think it** — early (slide 4), not in Q&A. Everything else builds on that. [See 010, Section 3.]

---

## 2. Refined Elevator Pitch (repositioned)

> *"ICRM has the data and the applications — what we lack is one place to ask across all of it, safely, with every answer sourced and every access governed. Today each app would build its own chatbot to get there. I'm proposing we build that capability once, for all of ICRM, instead of ten teams each rebuilding it."*

⚠️ The original "universal translator" line reads as "a new chatbot" — the trap. This version leads with the gap and pre-frames the platform-vs-N-chatbots answer. [010, Section 3.]

---

## 3. Deck Arc (≈14 slides + appendix)

| # | Slide | Key message | Feeds from | Illustration |
|---|-------|-------------|-----------|--------------|
| 1 | Title + thesis | "Ask ICRM anything — safely." | — | — |
| 2 | Cost of the status quo | Every app rebuilds the same foundation; brittle integrations; locked data; siloed risk view | [010 §1] | — |
| 3 | Why now | GenAI moving into production across the firm and industry; efficiency mandate; data-as-asset; CIA build in flight | [010 §2, §3.5] | — |
| 4 | **One platform, not N chatbots** | On today's path each app builds its own chatbot; we build it once — domain-aware, governed, cross-app. Self-contained, ICRM-owned | [010 §3] | — |
| 5 | The idea in one picture | Hub-and-spoke / layered architecture | [000] | ILL-1 / ILL-2 |
| 6 | Reason 1 — Build once, not N times | Point-to-point cost grows with the square of app count; a hub grows linearly. Ratios, no dollars | [010 §4] | ILL-1 / ILL-3 |
| 7 | Reason 2 — Dynamic interoperability | M+N, and **bidirectional discovery** (Policy↔MCA) hard integration never allowed | [010 §5] | ILL-2 |
| 8 | Reasons 3 & 4 — Unlock Elastic & Starburst | Conversational, governed access via official MCP servers; **governed query templates**, not fragile free-form SQL | [010 §6–7] | — |
| 9 | A day in the life | One human-in-the-loop scenario (Sarah), step-gated: draft → verify → approve | [020 §4] | ILL-4 / ILL-6 |
| 10 | The impact | Ratio/multiplier value + KPI dashboard incl. the **quality quadrant**; framed as pilot-validated | [030] | ILL-7 |
| 11 | **Governance is the point** | Delegated ownership + audit ledger + the **model-risk multiplier** — centralization is the only way to govern ICRM GenAI at scale | [040 §1–2] | ILL-9 |
| 12 | We've thought about the risks | Risk matrix incl. prompt injection, tool poisoning, concentration — with mitigations | [040 §3] | ILL-8 |
| 13 | A low-risk path | Phased roadmap, **retrieval-first** pilot, gates between phases | [040 §4] | ILL-10 |
| 14 | The ask | Approve a tightly-scoped, retrieval-only pilot — narrow scope, domain-specific, governed by design, generalizing the in-flight CIA build | [040 §4] | — |
| A | Appendix | Open questions (N, scaffolding share, baselines, regimes), full KPI list, sources | all | — |

---

## 4. Report Outline (written companion)

Maps ~1:1 to the existing doc set, so this is a thin narrative layer over the children:

1. Executive Summary — from [000]
2. Problem & Strategic Alignment — [010 §1–3]
3. The Four Reasons — [010 §4–8]
4. Capabilities & Use Cases — [020]
5. Business Impact & KPIs — [030]
6. Governance, Risk & Implementation — [040]
7. Appendices — open questions, full KPI list, sources

---

## 5. Deck Guardrails (carried from validation passes)

- ❌ No hard dollar / headcount figures, and **no "14 FTEs"** — ratios only. [030, Quadrant B.]
- ⚠️ Don't headline the contested MIT "95%" stat; use it only as the *rationale for a tight pilot*, if at all. [030, Section 1.]
- ⚠️ Don't gate the pilot on NPS — gate on quality + model-risk sign-off. [040, Section 4.]

---

## 6. The "Eureka" Trio (if the director remembers only three things)

1. It's **not a new chatbot** — it's one shared ICRM platform built once, instead of a chatbot per app.
2. Point-to-point integration cost grows with the **square** of our app count; a hub doesn't.
3. A central platform is the **only way ICRM GenAI ever passes model-risk review at scale.**

---

## Revision History
| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-06-09 | Initial | Phase 5 narrative + slide blueprint folded into child doc | Complete the doc set; provide pitch sequence over validated children |
| 1.1 | 2026-06-10 | Revision | Slide 4 reframed to "one platform, not N chatbots"; pitch, elevator line, eureka trio and sources de-Stylused | Keep Stylus entirely separate — analogy only |

---

## Sources & References

*Structural document — evidence resides in the cross-referenced children. Key anchors repeated for traceability:*

### Primary / Secondary (carried)
| Source | Used for | Tier |
|--------|----------|------|
| Dell'Acqua / Brynjolfsson (via [020], [030]) | productivity magnitudes | 1 |
| SR 11-7 / Moody's / OWASP (via [040]) | governance, risk | 1/2 |

### Flagged (carried)
| Source | Used for | Note |
|--------|----------|------|
| MIT NANDA (via [030 §1]) | pilot rationale only | single-origin, contested — not a headline stat |

### Further Reading
- See [010], [020], [030], [040] reference sections for full citations.

---

## Quality Gate
- [x] Correct document ID and naming convention
- [x] Parent document referenced ([000])
- [x] All claims cross-referenced to source children / labelled
- [x] Cross-references valid and bidirectional
- [x] Revision history present
- [x] Further reading included
