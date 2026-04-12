# [Model / System Name] — Model Card
## [Subtitle — e.g., "v2.1 — Production Classifier" or "System Card: Customer Support Agent"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-013: AI MODEL & SYSTEM CARD
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: Documenting an AI model or AI-enabled system for
transparency, compliance, and governance purposes.

REGULATORY ALIGNMENT:
  This template satisfies the documentation requirements of:
  - EU AI Act Article 11 + Annex IV (high-risk AI systems)
  - ISO/IEC 42001:2023 AI Management System Standard
  - NIST AI Risk Management Framework (AI RMF 1.0)
  - Mitchell et al. (2019) Model Cards for Model Reporting

USE CASES:
  - Model documentation for ML / AI teams
  - Regulatory technical files (EU AI Act)
  - Procurement due diligence documentation
  - Internal governance and AI risk management
  - AI system transparency reporting

MODEL CARD vs SYSTEM CARD:
  Model Card: Documents a specific trained ML model
    (weights, training data, eval metrics, biases)
  System Card: Documents an AI-enabled system that may
    use one or more models plus infrastructure, rules,
    human oversight, etc.
  This template covers both. Use tmpl_variant in frontmatter.

AUTHORING REQUIREMENTS:
  - Section 3 (Intended Use) is LEGALLY REQUIRED under
    EU AI Act Article 13 for high-risk systems.
  - Section 7 (Evaluation Results) must reference actual
    benchmark runs — do not estimate or fabricate.
  - Section 9 (Regulatory Declarations) must be reviewed
    by a qualified person before finalizing for compliance.
  - Research skill triggers for Sections 3.2, 5, and 6.
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ──────────────────────────────
document_id: "[XXX Model-Name-Card]"
title: "[Model/System Name] — Model Card v[version]"
version: "1.0"
created: "YYYY-MM-DD"
status: "Draft | Review | Final"
parent_document: ""
template_version_used: "TMPL-007 v1.0"

# ── MODEL / SYSTEM IDENTITY ───────────────────────────────────
tmpl_variant: "model-card | system-card"
model_name: ""
model_version: ""             # Semantic version of the model/system
model_type: "classification | generation | regression | detection | recommendation | agent | other"
modality: "text | image | audio | video | tabular | multimodal"
framework: ""                 # e.g., "PyTorch 2.2", "TensorFlow 2.14", "OpenAI API"
base_model: ""                # If fine-tuned: name and version of base model
deployment_context: "production | staging | research | deprecated"
release_date: "YYYY-MM-DD"

# ── REGULATORY METADATA ─────────────────────────────────────
eu_ai_act_risk_category: "unacceptable | high-risk | limited-risk | minimal-risk | not-assessed"
nist_ai_rmf_tier: "1 | 2 | 3 | 4 | not-assessed"
iso_42001_scope: "in-scope | out-of-scope | not-assessed"
requires_conformity_assessment: "yes | no | not-assessed"
data_classification: "public | internal | confidential | restricted"

# ── AI-OPTIMIZATION EXTENSION ────────────────────────────────
intent: >
  Enable [developers / procurers / regulators / auditors] to
  understand what [model/system name] does, what it was trained
  on, how it performs, where it fails, and what oversight is
  required for safe deployment.

consumption_context:
  - human-reading
  - ai-reasoning
  - rag-retrieval

triggers:
  - "[model name] model card"
  - "[model name] documentation"
  - "[model name] evaluation results"
  - "[model name] intended use"
  - "[model name] limitations"
  - "[model name] bias assessment"
  - "[system name] AI system card"
  - "[system name] regulatory documentation"

negative_triggers:
  - "[model name] API reference" # → TMPL-002 Technical Reference
  - "how to deploy [model name]" # → TMPL-003 Procedure

volatility: "stable"
  # stable: changes with model version; increment document version when model is updated
  # Create a new document for each major model version

research_validated: false
  # Research validation not applicable — this captures factual model properties

review_trigger: "When model version increments; when regulatory classification changes; annually for deployed systems"

confidence_overall: "high"
confidence_note: "Direct model knowledge — all evaluation metrics must reference actual benchmark runs"
---
```

---

> ## 🤖 AI Summary
> **Model/System:** [Name] v[version] — [Type]
> **Intended Use:** [One sentence — what this model/system is designed to do and for whom]
> **Must NOT Be Used For:** [Most important prohibited use — see Section 3.2 for full list]
> **EU AI Act Classification:** [Risk category]
> **Known Limitations:** [The single most important limitation that users must know]
> **Human Oversight Required:** [YES — [what kind] / NO]
> **Evaluation Currency:** [Date of most recent benchmark run]
> **Version Validity:** This card covers [model name] v[version] only — do not apply to other versions

---

## TABLE OF CONTENTS

1. [Model / System Identity](#1-model--system-identity)
2. [Development & Provenance](#2-development--provenance)
3. [Intended Use & Out-of-Scope Use](#3-intended-use--out-of-scope-use)
4. [Training Data](#4-training-data)
5. [Evaluation Results](#5-evaluation-results)
6. [Known Limitations & Failure Modes](#6-known-limitations--failure-modes)
7. [Bias & Fairness Assessment](#7-bias--fairness-assessment)
8. [Privacy & Security Considerations](#8-privacy--security-considerations)
9. [Human Oversight & Governance](#9-human-oversight--governance)
10. [Regulatory Declarations](#10-regulatory-declarations)
11. [Version History & Deprecation Plan](#11-version-history--deprecation-plan)
12. [Contact & Accountability](#12-contact--accountability)
13. [Sources & References](#13-sources--references)
14. [Revision History](#14-revision-history)

---

## 1. Model / System Identity
[TYPE: REFERENCE]

<!--
CHUNKING NOTE: Most frequently retrieved section.
Must be independently complete — an auditor reading only
this section must understand what the model/system is.
-->

The [model/system name] is a [type] model/system designed to [purpose].
It [brief technical description in one sentence].

### 1.1 Technical Identity

| Attribute | Value |
|-----------|-------|
| **Name** | [Model/System name] |
| **Version** | [Semantic version — e.g., 2.1.0] |
| **Type** | [Classification / Generation / Agent / etc.] |
| **Modality** | [Text / Image / Audio / Multimodal] |
| **Framework** | [e.g., PyTorch 2.2 / TensorFlow 2.14 / OpenAI API gpt-4o] |
| **Base Model** | [If fine-tuned: base model name and version / N/A] |
| **Parameters** | [Number of parameters / N/A for API-based systems] |
| **Context Window** | [N tokens / N/A] |
| **Deployment Context** | [Production / Staging / Research] |
| **Release Date** | [YYYY-MM-DD] |
| **Languages Supported** | [e.g., English, French, Spanish / Multilingual] |

### 1.2 What This Model/System Does

**Primary function:** [Clear, non-technical description of what the model/system does]

**What it takes as input:** [Input description — e.g., "Customer support tickets in plain text, up to 2,000 characters"]

**What it produces as output:** [Output description — e.g., "A classification label (BILLING / TECHNICAL / GENERAL) with a confidence score 0–1"]

**Where it is deployed:** [Deployment environment — e.g., "Customer support triage pipeline, processing ~5,000 tickets/day"]

---

## 2. Development & Provenance
[TYPE: REFERENCE]

### 2.1 Development Team & Organization

| Attribute | Value |
|-----------|-------|
| **Developed By** | [Team / Organization name] |
| **Development Period** | [YYYY-MM-DD — YYYY-MM-DD] |
| **Primary Contact** | [Name / Role — or team alias] |
| **Repository** | [URL — if accessible / Internal — see Section 12] |

### 2.2 Model Architecture

[For model cards: describe the architecture at the level of detail appropriate for the audience. For API-based systems (e.g., GPT-4, Claude) note the provider and version.]

| Component | Description |
|-----------|-------------|
| **Architecture type** | [e.g., Transformer decoder / CNN / Ensemble] |
| **Key architectural choices** | [What makes this architecture appropriate for the task] |
| **Fine-tuning approach** | [Full fine-tuning / LoRA / RLHF / Prompt engineering / N/A] |
| **Training compute** | [GPU hours / TPU hours / N/A for API systems] |

### 2.3 Relationship to Prior Versions

| Version | Date | Key Changes | Breaking Changes |
|---------|------|-------------|----------------|
| [Current version] | [Date] | [What changed] | [Yes/No — describe] |
| [Previous version] | [Date] | [Changes] | |

---

## 3. Intended Use & Out-of-Scope Use
[TYPE: REFERENCE]

<!--
EU AI Act Article 13 REQUIREMENT: This section must clearly state
the intended purpose, user groups, and conditions of deployment.
For high-risk AI systems, this section is LEGALLY REQUIRED to be
provided to deployers and users.
Research trigger: Run research skill against EU AI Act Article 13
requirements before finalizing this section for compliance use.
-->

### 3.1 Intended Use

The [model/system name] is designed for the following uses only:

**Primary use case:** [Specific, bounded description of the primary use]

**Intended users:** [Who should use this model — specific roles, contexts, or organizations]

**Intended deployment environment:** [Where this model should be deployed — e.g., "Internal customer support teams; not for public-facing deployment without additional safety layers"]

**Performance conditions:** [Under what conditions the model performs as evaluated — e.g., "English-language customer tickets of 50–2,000 characters; tickets from B2B SaaS customers"]

### 3.2 Out-of-Scope Use (Prohibited)

The following uses are explicitly prohibited. Deploying this model for these purposes
may produce unreliable outputs or cause harm:

| Prohibited Use | Reason | Risk |
|----------------|--------|------|
| [Use 1 — e.g., "Medical diagnosis or triage"] | [Why prohibited — e.g., "Not trained on medical data; no clinical validation"] | [e.g., "Patient safety"] |
| [Use 2 — e.g., "Legal advice or contract interpretation"] | [Reason] | [Risk] |
| [Use 3 — e.g., "Real-time safety-critical decisions without human review"] | [Reason] | [Risk] |
| [Use 4 — e.g., "Processing of children's data"] | [Reason — e.g., "Not evaluated for COPPA/GDPR-K compliance"] | [Risk] |

### 3.3 Consequential Use Warnings

[If the model is used to inform high-stakes decisions — employment, credit, healthcare, law enforcement — state the specific warnings required by applicable regulations.]

⚠️ `[FLAGGED — this model should NOT be used as the sole decision-maker for [specific consequential use]. Human review is required for all final decisions in [domain].]`

---

## 4. Training Data
[TYPE: REFERENCE]

<!--
IMPORTANT: Describe training data at a level that allows
assessment of potential biases and coverage gaps.
Do not disclose proprietary details if under NDA, but
describe the nature and characteristics of the data.
-->

### 4.1 Data Summary

| Attribute | Value |
|-----------|-------|
| **Dataset name(s)** | [Public dataset names / "Internal — see Section 4.3"] |
| **Total size** | [N examples / N tokens / N hours of audio] |
| **Date range** | [YYYY-MM to YYYY-MM] |
| **Languages** | [List] |
| **Geographic coverage** | [Regions represented] |
| **Data source types** | [e.g., Web text, Customer support logs, Academic papers] |

### 4.2 Data Collection & Preprocessing

**How data was collected:** [Collection method — web scraping, human annotation, licensed datasets, internal logs, etc.]

**Preprocessing steps:** [Key preprocessing — deduplication, filtering, tokenization, etc.]

**Filtering criteria applied:** [What was excluded and why — e.g., "Content flagged by NSFW classifier; duplicates; non-target-language content"]

**Human annotation:** [YES — describe annotation process, annotator demographics, IAA scores / NO]

### 4.3 Known Data Limitations & Gaps

The training data has the following known limitations that may affect model performance:

| Limitation | Affected Groups / Domains | Potential Impact |
|-----------|--------------------------|----------------|
| [e.g., "Underrepresented non-English speakers"] | [Specific groups] | [Expected performance degradation] |
| [e.g., "Data predates 2023 — no coverage of recent events"] | [Time-sensitive domains] | [Staleness risk] |
| [e.g., "Primarily B2B enterprise data"] | [Consumer use cases] | [Performance degradation on consumer queries] |

### 4.4 Data Rights & Licensing

| Dataset | License | Commercial Use Permitted | Restrictions |
|---------|---------|------------------------|-------------|
| [Dataset name] | [License — e.g., CC-BY 4.0, Apache 2.0, Proprietary] | YES / NO | [Any restrictions] |

---

## 5. Evaluation Results
[TYPE: RESEARCH_FINDING]

<!--
CRITICAL: All metrics here must reference actual benchmark runs.
Do not estimate or interpolate. If a metric was not measured,
state "Not measured" — do not omit the field.
Research trigger: Run verification skill on all cited benchmarks.
-->

### 5.1 Primary Evaluation Metrics

All metrics in this section are from evaluation runs completed on [evaluation date].
Results are on the held-out test set unless otherwise noted.

| Metric | Value | Dataset | Date | Notes |
|--------|-------|---------|------|-------|
| [e.g., Accuracy] | [e.g., 94.2%] | [Dataset name — not training data] | [YYYY-MM-DD] | [Confidence interval if available] |
| [e.g., F1-score] | | | | |
| [e.g., Precision] | | | | |
| [e.g., Recall] | | | | |
| [e.g., Latency p50] | [e.g., 120ms] | Production trace | [Date] | [Hardware specs] |
| [e.g., Latency p99] | | | | |

✅ `[VERIFIED — all metrics from evaluation run [run ID] on [date]]`

### 5.2 Benchmark Comparisons

| Benchmark | This Model | Baseline / Prior Version | Industry Reference |
|-----------|-----------|------------------------|-------------------|
| [Benchmark name] | [Score] | [Score] | [Score — source] |

### 5.3 Evaluation Limitations

The evaluation above does not measure:
- [Limitation 1 — e.g., "Performance on non-English inputs — not evaluated"]
- [Limitation 2 — e.g., "Adversarial robustness — not systematically tested"]
- [Limitation 3 — e.g., "Long-tail domain performance — insufficient test data"]

❓ `[UNRESOLVED — [specific metric] not measured; recommend adding to next evaluation cycle]`

---

## 6. Known Limitations & Failure Modes
[TYPE: REFERENCE]

<!--
This section is critical for safe deployment. Omitting known failure
modes creates liability. Be specific — vague limitations provide
no actionable guidance to deployers.
-->

The [model/system name] has the following known limitations.
Deployers must account for these before production use.

### 6.1 Technical Limitations

| Limitation | Conditions | Observed Failure Rate | Mitigation |
|-----------|-----------|----------------------|------------|
| [e.g., "Hallucinates confident answers on rare topics"] | [When input is outside training distribution] | [e.g., ~8% on OOD queries] | [Human review for low-confidence outputs] |
| [e.g., "Degrades significantly on inputs >1,500 tokens"] | [Long inputs] | [N/A — truncation applied] | [Apply input length validation] |
| [e.g., "Struggles with negation in conditional sentences"] | [Complex logical conditions] | [~12% error rate on negation tests] | [Post-process outputs for this pattern] |

### 6.2 Known Failure Scenarios

These are documented cases where the model produces incorrect or harmful output:

| Scenario | Description | Severity | Status |
|----------|-------------|----------|--------|
| [Failure scenario 1] | [What happens and why] | HIGH / MEDIUM / LOW | [Mitigated / Open / Accepted] |
| [Failure scenario 2] | | | |

### 6.3 Performance Boundaries

**The model performs as evaluated when:**
- [Boundary condition 1 — e.g., "Input is in English"]
- [Boundary condition 2 — e.g., "Input length is between 50 and 2,000 tokens"]
- [Boundary condition 3 — e.g., "Input domain matches the training distribution"]

**Performance degrades when:**
- [Degradation condition 1 — e.g., "Input contains domain-specific jargon not in training data"]
- [Degradation condition 2 — e.g., "Input is adversarially crafted to manipulate output"]

---

## 7. Bias & Fairness Assessment
[TYPE: RESEARCH_FINDING]

<!--
Required for responsible AI disclosure and EU AI Act compliance.
If a formal bias audit was not conducted, state that explicitly —
do not omit this section. An incomplete assessment is better than
a missing one, provided the incompleteness is disclosed.
-->

### 7.1 Groups & Attributes Assessed

| Attribute | Groups Tested | Assessment Method | Result |
|-----------|-------------|-------------------|--------|
| [e.g., Gender] | [Male / Female / Non-binary] | [e.g., Counterfactual evaluation] | [e.g., 2% accuracy gap Female vs Male] |
| [e.g., Race/Ethnicity] | [Groups tested] | [Method] | [Result] |
| [e.g., Age] | [Groups tested] | [Method] | [Result] |
| [e.g., Language variety] | [Dialects/accents tested] | [Method] | [Result] |

### 7.2 Bias Assessment Limitations

[Describe what was NOT assessed and why:]
- [e.g., "Intersectional bias (e.g., race × gender) was not assessed — insufficient labeled data"]
- [e.g., "Non-Western cultural bias not assessed — test data was primarily North American and European"]

⚠️ `[FLAGGED — bias assessment is incomplete. The following groups were not evaluated: [list]. These gaps should be addressed before deployment in contexts affecting these groups.]`

### 7.3 Bias Mitigation Steps Taken

| Bias Found | Mitigation Applied | Effectiveness |
|-----------|-------------------|--------------|
| [Bias 1] | [Step taken — e.g., "Resampled training data to balance class representation"] | [e.g., "Reduced gap from 8% to 2%"] |
| [Bias 2] | | |

---

## 8. Privacy & Security Considerations
[TYPE: REFERENCE]

### 8.1 Data Privacy

| Attribute | Status |
|-----------|--------|
| **PII in training data** | [YES — [type and how handled] / NO] |
| **PII in model outputs** | [YES — [mitigations] / NO / POTENTIAL — see below] |
| **GDPR applicable** | [YES / NO / Requires assessment] |
| **Right to be forgotten** | [Supported via [method] / Not supported / N/A] |
| **Data retention (inference logs)** | [Duration — e.g., "30 days / No logs retained"] |

### 8.2 Security Vulnerabilities

| Vulnerability Type | Risk Level | Status | Mitigation |
|-------------------|-----------|--------|------------|
| Prompt injection / jailbreak | [HIGH / MEDIUM / LOW] | [Assessed / Not assessed] | [Mitigation if any] |
| Training data extraction | [Risk level] | [Status] | [Mitigation] |
| Adversarial inputs | [Risk level] | [Status] | [Mitigation] |
| Model inversion | [Risk level] | [Status] | [Mitigation] |

### 8.3 Sensitive Content Handling

[Does the model process or produce sensitive content? How is this managed?]

---

## 9. Human Oversight & Governance
[TYPE: REFERENCE]

<!--
Required by EU AI Act and NIST AI RMF.
Be specific — "humans review outputs" is insufficient.
Describe WHO reviews WHAT under WHAT conditions.
-->

### 9.1 Human-in-the-Loop Requirements

| Decision Type | Human Review Required | Review Trigger | Reviewer Role |
|--------------|----------------------|----------------|--------------|
| [e.g., "Outputs affecting user accounts"] | YES — mandatory | Every instance | [Role — e.g., "Customer success manager"] |
| [e.g., "High-confidence routine classifications"] | NO — sample review | [e.g., "5% random sample daily"] | [Role] |
| [e.g., "Low-confidence outputs (score <0.7)"] | YES — mandatory | Confidence threshold | [Role] |

### 9.2 Monitoring & Alerting

| What Is Monitored | Metric | Alert Threshold | Response |
|------------------|--------|----------------|---------|
| [e.g., "Output confidence distribution"] | [Metric] | [Threshold] | [Action] |
| [e.g., "Prediction drift from baseline"] | [Metric] | [Threshold] | [Action] |

### 9.3 Incident Response

When a model output causes or contributes to a harmful incident:

1. [Step 1 — e.g., "Halt model serving immediately"]
2. [Step 2 — e.g., "Notify [role] within [N hours]"]
3. [Step 3 — e.g., "Preserve inference logs for the incident window"]
4. [Step 4 — e.g., "Conduct post-mortem using TMPL-008"]

---

## 10. Regulatory Declarations
[TYPE: REFERENCE]

<!--
⚠️ This section must be reviewed by a qualified person (legal,
compliance, or regulatory expert) before finalizing for any
compliance purpose. Claude cannot provide legal advice.
The classifications below are based on publicly available
regulatory guidance and should be verified.
-->

### 10.1 EU AI Act Classification

| Criterion | Assessment | Notes |
|-----------|-----------|-------|
| **Risk Category** | [Unacceptable / High-risk / Limited-risk / Minimal-risk] | [Basis for classification] |
| **Annex III applies** | [YES — [which provision] / NO] | |
| **Annex IV technical file required** | [YES / NO] | |
| **Conformity assessment required** | [YES / NO] | |
| **CE marking required** | [YES / NO] | |
| **Fundamental rights impact assessment** | [Completed YYYY-MM-DD / Required / Not applicable] | |

⚠️ `[FLAGGED — EU AI Act classification above is based on self-assessment. Independent legal review required before regulatory submission.]`

### 10.2 NIST AI RMF Alignment

| RMF Function | Status | Evidence |
|-------------|--------|---------|
| GOVERN | [Implemented / Partial / Not implemented] | [Policy / Process reference] |
| MAP | [Status] | [Evidence] |
| MEASURE | [Status] | [Evidence] |
| MANAGE | [Status] | [Evidence] |

### 10.3 Other Applicable Frameworks

| Framework | Applicable | Compliance Status |
|-----------|-----------|------------------|
| ISO/IEC 42001:2023 | [YES / NO] | [Certified / In progress / Not assessed] |
| GDPR | [YES / NO] | [Status] |
| [Other — e.g., HKMA, FCA, FDA] | [YES / NO] | [Status] |

---

## 11. Version History & Deprecation Plan
[TYPE: REFERENCE]

### 11.1 Model Version History

| Version | Release Date | Key Changes | Performance Change | Status |
|---------|-------------|-------------|-------------------|--------|
| [Current] | [Date] | [Changes] | [Delta vs prior] | Active |
| [Prior] | [Date] | [Changes] | [Baseline] | [Deprecated / Retired] |

### 11.2 Deprecation Plan

| Milestone | Date | Action |
|-----------|------|--------|
| Deprecation announcement | [YYYY-MM-DD] | [Communication channels] |
| End of new deployments | [YYYY-MM-DD] | [Migration path for new use cases] |
| End of support | [YYYY-MM-DD] | [What happens to existing integrations] |
| End of life | [YYYY-MM-DD] | [Model removed from serving infrastructure] |

**Migration path:** [Where users should migrate to when this version is deprecated]

---

## 12. Contact & Accountability
[TYPE: REFERENCE]

| Role | Name / Team | Contact |
|------|-------------|---------|
| **Model Owner** | [Name / Team] | [Email / Slack / alias] |
| **Technical Lead** | [Name] | [Contact] |
| **Compliance Contact** | [Name / Legal team] | [Contact] |
| **Incident Response** | [On-call rotation / team alias] | [PagerDuty / Slack / etc.] |
| **Model Card Maintainer** | [Name — who updates this document] | [Contact] |

**Feedback / Issue Reporting:** [How users and deployers can report issues with this model]

---

## 13. Sources & References
[TYPE: REFERENCE]

### 13.1 Regulatory Sources

| Framework | Document | Version / Date | URL |
|-----------|----------|---------------|-----|
| EU AI Act | Regulation (EU) 2024/1689 | June 2024 | [EUR-Lex URL] |
| NIST AI RMF | AI Risk Management Framework | Version 1.0, January 2023 | [NIST URL] |
| ISO/IEC 42001 | AI Management Systems Standard | 2023 | [ISO URL — paywalled] |
| Model Cards | Mitchell et al., "Model Cards for Model Reporting" | 2019 | [ACM DL URL] |

### 13.2 Evaluation & Research References

| Source | Title | Date | Used For |
|--------|-------|------|---------|
| [Benchmark paper/repo] | [Title] | [Date] | [Which evaluation metric] |
| [Bias evaluation methodology] | [Title] | [Date] | [Section 7] |

### 13.3 Document Cross-References

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DEPENDS_ON] | [TMPL-002 — Technical Reference for the serving infrastructure] | All | System architecture for this model |
| [SEE_ALSO] | [TMPL-005 — Decision Record for model selection] | All | Why this model was chosen |
| [VALIDATED_BY] | [Internal evaluation run record] | Section 5 | Evaluation result provenance |

---

## 14. Revision History

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | [Date] | Initial | Model card created | [Model release / compliance requirement] |

### Change Detail Guidelines

For significant updates (new evaluation results, bias findings, regulatory changes):
use the expanded Change Detail block format from RULES-001 Section 4.3.

---

*Template: TMPL-007 AI Model & System Card v1.0 | Parent: TMPL-000 Template Index*
*Regulatory content (Section 10) requires qualified legal/compliance review before use for compliance purposes*
