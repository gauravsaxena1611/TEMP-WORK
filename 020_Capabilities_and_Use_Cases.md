# 020 Capabilities & High-Impact Use Cases

```yaml
document_id: "020"
title: "Capabilities & High-Impact Use Cases"
parent_document: "000 ICRM AI Layer — Business Case (Master)"
version: "1.1"
status: "Draft — refined from Phase 2"
methodology_phase: "Business case / pre-pilot"
verification_mode: "HIGH"
confidence_overall: "High on the human-in-the-loop pattern; Low on any specific % until piloted"
date: "2026-06-09"
```

> Parent: [000, Section 1]. This document elaborates capabilities and use cases.

---

## 1. Evidence Anchor (read before the scenarios)

The strongest peer-reviewed field experiment on GenAI knowledge work (758 consultants, BCG + Harvard/Wharton/MIT) found **both** an upside and a failure mode:

- Inside AI's capability range: ~12% more tasks completed, ~25% faster, ~40% higher quality. ✅ VERIFIED [Source: Dell'Acqua et al., *Organization Science*, 2023]. ⚠️ Minor flag: BCG-collaborated, but preregistered and peer-reviewed.
- Outside that range: ~19 percentage points **worse** with AI, with over-reliance where AI is weakest ("jagged frontier"). ✅ VERIFIED [same].
- Gains concentrate among **novices/lower-skilled** workers. ✅ VERIFIED [Source: Brynjolfsson et al., *Science*, 2025].

💡 **Design consequence:** every scenario must show the human verifying, not a flawless oracle. This is *more* persuasive to a risk-aware audience, and it matches how regulated institutions actually deploy GenAI — as a **copilot, not an adjudicator**. ✅ [Source: Compliance Week, 2026].

---

## 2. Personas

| Persona | Role | Note |
|---------|------|------|
| Sarah | Compliance Officer | Inside-frontier retrieval/summarize use; model use case |
| David | Internal Auditor | Highest-risk: AI must never assert a finding |
| Maria | ICRM Manager | Leadership-facing numbers → governed queries only |
| Tom | New Hire | **Strongest, lowest-risk ROI** — novices benefit most |
| (new) App/Data Owner | Exposes capability via MCP | Adoption dependency; decides what is exposed |
| (new) Second-line / Risk | Consumes the central audit trail | Carries the model-risk story into the human narrative |

---

## 3. The Human-in-the-Loop Interaction Pattern (define once, reuse)

Every multi-step task runs as a chain of checkpoints. For each system the bot touches:

1. **Bot retrieves or drafts** (read-only by default) →
2. **Bot presents the draft with every claim linked to its source** →
3. **Human reviews, edits, and verifies against the linked source** →
4. **Human approves → bot proceeds.** No approval → bot stops or revises.

This maps to the governance tiering banks are standardizing on ✅ [Source: Sardine playbook, 2026 — ⚠️ Tier 3, consistent with SR 11-7]:

| Tier | Example | Control |
|------|---------|---------|
| Retrieval / drafting | knowledge search, draft memo | Light verify-before-use gate |
| Assist a decision | evidence index, KPI pull | Mandatory human-in-the-loop review |
| Act (consequence) | initiate a process, file a record | Hard confirmation gate + full validation + immutable log |

---

## 4. Step-Gated Scenarios

### Scenario 1 — Sarah (compliance investigation)
**Trigger:** alert about a potential trading-policy breach.
1. [BOT→Policy] drafts policy summary + version + source link → [Sarah verifies, narrows scope] → ✓
2. [BOT→CRA] lists associated controls → [Sarah drops one out of scope] → ✓
3. [BOT→iCAPS] (read-only) pulls related historical issues → [Sarah spot-checks two against source] → ✓
4. [BOT] drafts consolidated memo, every claim cited → [Sarah edits, verifies figures, approves, sends] → ✓

**Time:** manual ≈ most of a morning across three systems + collation; with bot + review ≈ **30–40 min** (⚠️ illustrative — measure in pilot). **Real-world basis:** banks run GenAI compliance copilots that draft SAR/case narratives **with human review** and explicit review-and-approval procedures + audit trails. ✅ [Source: Compliance Week, 2026 — Tier 2; 200OK, 2025 — Tier 3].

### Scenario 2 — David (auditor evidence-gathering)
**Trigger:** RegW audit prep for the ABC Business Unit. *(Rewritten so the bot never asserts a finding.)*
1. [BOT gathers] latest CRA, policy exceptions (6 mo), training data — each item a **link to system-of-record**, not a claim → [David reviews] → ✓
2. [BOT drafts evidence index] noting an *apparent* training gap (labelled "to verify") → [David independently checks against the LMS source; confirms] → ✓
3. [BOT drafts workpaper section] with citations → [David applies professional skepticism, edits, **owns the conclusion**] → ✓
4. Every bot query logged → **the AI's own work is auditable**; conclusion human-owned and reproducible.

**Time:** manual ≈ 2+ days (emails, waiting, collation); with bot ≈ a few hours, **mostly David's verification** (⚠️ illustrative). **Real-world basis:** the IIA limits GenAI to drafting/summarizing that is edited and reviewed; risk-function copilots draft control updates **for human approval**. ✅ [Source: IIA, 2025 — Tier 2; HBR sponsored, 2025 — ⚠️ Tier 3].

### Scenario 3 — Maria (manager reporting to leadership)
**Trigger:** leadership meeting in an hour; needs Q2 KPIs. *(Highest-stakes numbers in the deck.)*
1. [BOT runs **governed, pre-approved KPI queries**] — parameterized data products, **not free-form text-to-SQL** → [Maria reviews numbers + the query definitions] → ✓
2. [BOT drafts trend chart + summary] → [Maria sanity-checks vs last quarter, edits framing] → ✓
3. [BOT prepares slide-ready output] → [Maria approves; she owns what she presents] → ✓
4. **Team-level metrics only** — avoid individual-performance surveillance (⚠️ privacy / works-council optic, esp. EMEA).

**Time:** manual ≈ an hour+ of analyst export/pivot, error-prone; with bot ≈ **15–25 min** (⚠️ illustrative). **Why governed queries:** enterprise NL-to-SQL accuracy is too low for leadership-facing numbers ✅ [Source: OpenReview/arXiv, 2025]; reliability comes from validated data products ✅ [Source: Starburst, 2026]. *(See [010, Section 7].)*

### Scenario 4 — Tom (new hire) — the safest, best-evidenced win
**Trigger:** *"What's RegW? How do I escalate an issue? Who owns CRA?"*
1. [BOT answers from grounded ICRM knowledge, with citations] → [Tom clicks through to source policy to confirm] — light gate, retrieval only.
2. For *"how do I start process X,"* bot explains + links; any actual initiation routes to the normal human approval path.

**Time:** manual ≈ interrupting senior colleagues, waiting; with bot ≈ instant self-service. **Real-world basis:** Morgan Stanley's internal assistant over 100,000+ documents is RAG-grounded, eval-tested before deployment, with human-in-the-loop oversight ⚠️ [Source: OpenAI/Morgan Stanley case study — industry self-report, directional] — a comparable RAG-over-internal-docs pattern. Novices benefit most ✅ [Brynjolfsson, 2025].

---

## 5. Time Framing (standing rule)

Scenario times are realistic figures (~15–40 min) against much larger manual baselines, **labelled illustrative, to be validated in the pilot.** The defensible spine is the *ratio* (manual half-day/multi-day → tens of minutes) plus the peer-reviewed ~25% faster / ~40% quality anchor. Vendor percentages (adoption, "−60%") are flagged and held in reserve as "consistent with industry reports," never asserted as fact.

---

## 6. Core Capability Breakdown (risk-honest framing)

| # | Capability | Verdict | Framing |
|---|-----------|---------|---------|
| 1 | Unified NL interface | ✅ | The platform's own Citi-standard conversational UI — built once, shared across ICRM [010, Section 3] |
| 2 | Cross-system orchestration | ⚠️ | Most valuable + hardest; multi-MCP accuracy degrades → scope to a curated set of high-value queries first |
| 3 | Conversational data analysis | ⚠️ | Governed/parameterized core now; free-form exploration phased later |
| 4 | Proactive intelligence & summarization | 🚩 | Highest hallucination surface; phase it; grounding + citations required |
| 5 | Entitlement enforcement | ✅ | Correct in principle (Starburst runs under caller identity); ⚠️ name the on-behalf-of / "confused deputy" identity-propagation challenge |
| 6 | Workflow triggering | 🚩 | Actions have consequences → human-in-the-loop confirmation + ARC governance; Tier-"act" controls |

---

## 7. Two Design Principles to Promise

- **A "what the chatbot does NOT do" boundary** per scenario — stating limits *raises* credibility with a risk audience.
- **Graceful unknown:** the bot says *"I can't confirm that — here's where to verify,"* rather than guessing. The single most trust-building behavior; directly answers the hallucination fear.

---

## Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-06-09 | Initial | Refined Phase 2 into standards-compliant child doc | Add human-in-the-loop step-gates to all scenarios; rewrite auditor scenario; add Tom; ground in peer-reviewed + real deployments; tier capabilities |
| 1.1 | 2026-06-10 | Revision | Removed Stylus/Citi Assist references; unified NL interface is the platform's own Citi-standard UI | Keep Stylus entirely separate — analogy only, no integration |

---

## Sources & References

### Primary (Tier 1)
| Source | Title | Date | URL |
|--------|-------|------|-----|
| Dell'Acqua et al., *Organization Science* | Navigating the Jagged Technological Frontier | 2023 | https://pubsonline.informs.org/doi/10.1287/orsc.2025.21838 |
| Brynjolfsson et al., *Science* | Generative AI at Work | 2025 | (Science 381) |
| OpenReview/arXiv | Text-to-SQL Benchmarks for Enterprise Realities | 2025 | https://openreview.net/forum?id=gXkIkSN2Ha |
| Starburst | MCP server (read-only, caller identity) | 2026 | https://docs.starburst.io/latest/starburst-ai/mcp-server.html |

### Secondary (Tier 2)
| Source | Title | Date | URL |
|--------|-------|------|-----|
| The IIA | Transforming Audit Through AI | 2025 | https://www.theiia.org/en/content/articles/global-best-practices/2025/transforming-audit-through-ai/ |
| Compliance Week | GenAI as copilot not adjudicator | 2026 | https://www.complianceweek.com/opinion/.../36431.article |
| (removed) | Citi Stylus reference no longer used | — | — |
| Harvard Forum / corpgov | Audit committee oversight of AI | 2025 | https://corpgov.law.harvard.edu/2025/07/12/oversight-in-the-ai-era-understanding-the-audit-committees-role/ |

### Competitor / Industry-Funded (Tier 3 — flagged, directional only)
| Source | Title | Date | Bias note |
|--------|-------|------|-----------|
| OpenAI | Morgan Stanley case study | 2024–25 | Vendor + client self-report |
| Sardine | Agentic AI banking compliance (tiered model) | 2026 | Vendor; framework useful |
| SymphonyAI / StackAI / Intellivon / 200OK | AML/compliance GenAI claims | 2025–26 | Vendor marketing |
| HBR (sponsored, Provectus/AWS) | Transforming Risk Management with GenAI | 2025 | Sponsored |

### Further Reading
- COSO GenAI governance taxonomy (2026); NIST AI RMF; ISO/IEC 42001

---

## Quality Gate

- [x] Correct document ID and naming convention
- [x] Parent document referenced ([000])
- [x] All claims cited / labelled
- [x] Cross-references valid and bidirectional (master lists this child)
- [x] Revision history present
- [x] Further reading included
