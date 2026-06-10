# 040 Governance, Risk & Implementation

```yaml
document_id: "040"
title: "Governance, Risk & Implementation"
parent_document: "000 ICRM AI Layer — Business Case (Master)"
version: "1.2"
status: "Draft — refined from Phase 4; anchored on in-flight CIA build"
methodology_phase: "Business case / pre-pilot"
verification_mode: "HIGH"
confidence_overall: "High — strongest section once Model Risk Management and the missing risks are added"
date: "2026-06-09"
```

> Parent: [000, Section 1]. Cross-refs: [010, Section 8] (model-risk reason), [020, Section 3 & 6] (human-in-the-loop, tiering), [030, Quadrant E] (quality metrics as pilot gates).

---

## 1. Governance & Guardrails

- **Centralized control, delegated ownership** — bot holds no data; relies on each app's MCP; owners decide exactly what is exposed. → ✅ keep as-is; strongest governance asset (zero-trust, data-owner control).
- **Entitlements via CRC** → ⚠️ depends on correct **on-behalf-of identity propagation**. The gate reuses the existing **CRC Entitlement Service** [010, Section 3.5]. Named failure mode: the **confused-deputy problem** — an MCP server may act with its own broad privileges rather than the user's, granting access the user shouldn't have ✅ [Tier 2 — Red Hat]. Mitigation: per-user identity passed to each MCP, OAuth on-behalf-of, least privilege. Starburst's MCP already runs under the caller's identity [✅ Tier 1] — mandate that pattern.
- **Central immutable audit ledger** → ✅ keep; reuses the existing **EEMS Audit Service / centralized CRC repository** [010, Section 3.5]; doubles as model-risk evidence.
- **"RAG prevents hallucinations"** → ⚠️ soften to *reduces*. RAG and fine-tuning do **not** mitigate prompt injection; the recommended approach is defense-in-depth: least-privilege tooling, input/output filtering, human approval for high-risk actions, adversarial testing ✅ [Tier 2 — OWASP]. Pair RAG with faithfulness/groundedness metrics [030, Quadrant E] + human-in-the-loop.

---

## 2. Model Risk Management — NEW (the offensive governance argument)

In a bank, this platform is a **model under model-risk management**; the risk function will require that framing.
- **Model inventory + tiering**, independent **validation** (output reliability, factual accuracy, prompt-injection/red-team testing, output variance, drift), **ongoing monitoring** — SR 11-7 pillars applied to GenAI, which breaks traditional assumptions through opacity, nondeterminism, and third-party reliance ✅ [Tier 1/2 — Moody's; SR 11-7].
- **Third-party/vendor model risk** — external foundation models accessed through the firm's approved, governed channel; vendor opacity and model change are explicit MRM concerns.
- **Framework alignment** — NIST AI RMF / ISO/IEC 42001 (EU AI Act / DORA where in scope) ❓ confirm applicable regimes for ICRM jurisdictions.
- 💡 **Offensive framing:** centralization makes all of this tractable **once** vs N times — the model-risk multiplier [010, Section 4]. Converts governance from defensive to the headline reason.

---

## 3. Risk Identification & Mitigation

### Existing rows — keep, strengthen mitigations
| Risk | Strengthened mitigation |
|------|------------------------|
| Model hallucination | + faithfulness/groundedness eval, citations, human verification |
| Unauthorized data access | + confused-deputy / on-behalf-of identity |
| Poor adoption | + MIT "learning gap" lesson, champions, workflow integration |
| Scope creep | + formal onboarding gate; **+ run platform generalization as a separate workstream so CIA's committed delivery is not pulled off-schedule** |

### New rows — add (the credibility-makers)
| Risk | Why it matters | Mitigation |
|------|----------------|------------|
| **Prompt injection (direct + indirect)** 🚩 | OWASP's #1 LLM risk two editions running; LLMs read instructions and data in one channel, so attacker content in a retrieved document can be read as an instruction ✅ [Tier 2 — OWASP]. Bot reads many systems = large indirect-injection surface. | Treat retrieved content as untrusted; input/output filtering; isolate untrusted inputs; no high-risk action without human approval; red-teaming |
| **Excessive agency** (OWASP LLM06) 🚩 | Over-broad functionality/permissions/autonomy in workflow triggering | Least functionality + least privilege; human confirmation gate on every action |
| **MCP tool poisoning / supply chain** 🚩 | Tool descriptions load into model context and can carry hidden instructions; a 2025 incident exfiltrated message history via a poisoned tool description ✅ [Tier 2/3 — Checkmarx/Red Hat] | Internal vetted MCP registry only; tool allowlisting/integrity checks; no arbitrary third-party MCP servers |
| **Concentration / single point of failure** ⚠️ | Central front door concentrates attack surface + blast radius | HA/resilience; blast-radius containment; per-app isolation; data stays in source systems |
| **Data quality / readiness** ⚠️ | MIT: data quality is the missing link | Data-readiness assessment as a pilot prerequisite; governed data products |

⚠️ MCP exposure note: research found hundreds of publicly exposed MCP servers, many lacking basic auth/encryption — the dominant issue is misconfiguration, not the protocol [Tier 3]. Mitigation posture: internal-only, authenticated, least-privilege, vetted registry.

---

## 4. Phased Implementation (crawl-walk-run — refined)

- **Phase 1 — Pilot, anchored on the in-flight CIA build (Months 1–6):** Phase 1 is already live — **CIA is integrating Elastic's MCP and the CRC Common Services APIs** [010, Section 3.5]. The pilot is therefore *not* a greenfield project: it is a **separate generalization workstream** that extracts and hardens what CIA builds into a reusable platform pattern, **without pulling scope onto CIA's committed delivery**. Scope: CIA's CIA + Elastic capability as the first vertical spoke, **retrieval-only — no actions/workflow triggering** (lowest-risk tier), 20–30 users, starting with Tom/knowledge + Sarah read-only [020]. Add a **data-readiness check** and an **eval/red-team pass before go-live** (Morgan Stanley pattern).
  - **Success criteria** → ⚠️ NOT ">80% NPS". Gate on **Quality & Reliability metrics** [030, Quadrant E] (faithfulness, citation coverage, override rate), **time/quality deltas vs baseline**, entitlement-enforcement validation (via the CRC Entitlement Service), and a **passed model-risk review**. Plus a platform criterion: **the CRC + Elastic wiring is demonstrably reusable by a second app** (the proof the generalization worked). NPS = secondary signal.
- **Phase 2 — Expansion (Months 7–18):** 3–5 apps + Starburst, 200+ users. ⚠️ "Simple workflows" = **actions**, moving from "assist" to "act" → require model-risk validation + human-confirmation gates **before** any action capability; sequence after retrieval is proven.
- **Phase 3 — Scale (Months 19+):** self-service onboarding toolkit; standard for new apps. ⚠️ ">80% apps integrated" is aspirational (large enterprises average ~9 months to scale [MIT, directional]); tie to onboarding maturity. "Predictive insights / anomaly detection" → ⚠️ exploratory, high model-risk.
- **Add explicit phase gates** — do not expand scope/autonomy until governance + quality thresholds are met ("earn trust before expanding authority").

---

## Revision History
| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-06-09 | Initial | Refined Phase 4 into standards-compliant child doc | Add Model Risk Management section; add prompt injection / excessive agency / tool poisoning / concentration / data-quality risk rows; fix pilot to gate on quality + MRM sign-off; sequence retrieval before actions |
| 1.1 | 2026-06-09 | Update | Anchored Phase 1 on the in-flight CIA build as a separate generalization workstream; named CRC Entitlement/EEMS Audit services; standardized GRCT→CRC | Reflect live CIA/Elastic-MCP/CRC reality; protect CIA delivery; add reusability success criterion |
| 1.2 | 2026-06-10 | Revision | Third-party model-risk now references the firm's approved governed channel, not a named product | Stylus framing removed across the set |

---

## Sources & References

### Primary (Tier 1)
| Source | Title | Date | URL |
|--------|-------|------|-----|
| Federal Reserve/OCC | SR 11-7 (applied to GenAI) | 2011– | (supervisory guidance) |
| Starburst | MCP server — caller identity, read-only | 2026 | https://docs.starburst.io/latest/starburst-ai/mcp-server.html |

### Secondary (Tier 2)
| Source | Title | Date | URL |
|--------|-------|------|-----|
| OWASP | Top 10 for LLM Applications 2025 (LLM01 Prompt Injection, LLM06 Excessive Agency) | 2025 | https://genai.owasp.org/llm-top-10/ |
| Red Hat | MCP security risks & controls (confused deputy) | 2026 | https://www.redhat.com/en/blog/model-context-protocol-mcp-understanding-security-risks-and-controls |
| Moody's | Model Risk Management in the Age of AI | 2025 | https://www.moodys.com/.../model-risk-management-in-the-age-of-ai.pdf |

### Competitor / Industry-Funded (Tier 3 — flagged)
| Source | Title | Date | Bias note |
|--------|-------|------|-----------|
| Checkmarx | MCP security risks / 11 emerging risks (tool poisoning) | 2025 | Vendor; corroborated by Red Hat/OWASP |
| MIT NANDA (via press) | GenAI Divide (scale time, internal-build failure) | 2025 | Single-origin, contested; directional |
| OpenAI | Morgan Stanley eval framework | 2024–25 | Vendor + client self-report |

### Further Reading
- NIST AI RMF 1.0; ISO/IEC 42001; EU AI Act; DORA; COSO GenAI governance (2026)

---

## Quality Gate
- [x] Correct document ID and naming convention
- [x] Parent document referenced ([000])
- [x] All claims cited / labelled
- [x] Cross-references valid and bidirectional
- [x] Revision history present
- [x] Further reading included
