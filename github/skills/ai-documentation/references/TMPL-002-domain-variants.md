# TMPL-002 Domain Variant Guidance
## Supplementary Structure for Microservices, Data Pipelines, and ML Models

**Source:** TMPL-000 Template Index v1.2 | Phase 3 WP-3.4
**Load when:** Using TMPL-002 for a microservice, data pipeline, or ML model technical reference
**Version:** 1.0 | **Created:** 2026-04-04

---

## Overview

The base TMPL-002 Technical Reference template covers the generic technical reference
structure. When documenting specific domain types, certain sections need additional
fields, different emphasis, or domain-specific subsections. This file defines those
additions — load it alongside TMPL-002, not instead of it.

**How to use this file:**
1. Load TMPL-002 as the base template
2. Identify your domain variant (microservice / data pipeline / ML model)
3. Add the domain-specific sections defined below at the indicated positions
4. All base TMPL-002 sections remain required unless marked optional here

---

## Variant A — Microservice Technical Reference

**Use when:** Documenting a single microservice within a service mesh or distributed system.

### A.1 Additional Frontmatter Fields

Add these fields to the TMPL-002 frontmatter block:

```yaml
# ── MICROSERVICE METADATA ─────────────────────────────────────
service_mesh: ""              # e.g., "Istio 1.20 / AWS App Mesh / none"
api_contract_version: ""      # Semantic version of the API contract
backward_compatible: "yes | no | breaking-change"
service_dependencies: []      # List of services this calls
downstream_consumers: []      # List of services that call this
sla_availability: ""          # e.g., "99.9% / 99.95%"
slo_latency_p99: ""           # e.g., "200ms"
slo_error_rate: ""            # e.g., "<0.1% 5xx per hour"
owns_data: "yes | no"         # Does this service own a data store?
data_store: ""                # If yes: type and name
```

### A.2 Additional Section: Service Mesh & Traffic Management

Insert as Section 4.5 (after existing Section 4.4 Pagination in base template):

```markdown
## 4.5 Service Mesh & Traffic Configuration
[TYPE: REFERENCE]

The [service name] operates within the [mesh name] service mesh.
The following traffic management policies are active.

### 4.5.1 Traffic Policies

| Policy | Configuration | Notes |
|--------|--------------|-------|
| **Retry policy** | [N retries, [N]ms backoff, on: [5xx / timeout]] | |
| **Timeout** | [N]ms default | [Override conditions] |
| **Circuit breaker** | [Threshold: N errors in [N]s → open for [N]s] | |
| **Rate limiting** | [[N] req/s per [caller / client / global]] | |

### 4.5.2 mTLS & Authentication

| Attribute | Value |
|-----------|-------|
| **mTLS mode** | [STRICT / PERMISSIVE / DISABLED] |
| **Service identity** | [SPIFFE URI or service account] |
| **Token validation** | [JWT issuer / API key header / none] |
```

### A.3 Additional Section: API Contract & Versioning

Insert as Section 4.6:

```markdown
## 4.6 API Contract & Versioning
[TYPE: REFERENCE]

### 4.6.1 API Contract Location

| Artifact | Location | Format |
|----------|----------|--------|
| OpenAPI spec | [URL / file path] | OpenAPI 3.1 |
| AsyncAPI spec (if applicable) | [URL / file path] | AsyncAPI 2.x |
| Contract test suite | [Repository path] | [Pact / Spring Contract] |

### 4.6.2 Versioning Policy

| Aspect | Policy |
|--------|--------|
| **Version location** | [URL path: /api/v{N}/ / Header: X-API-Version / Media type] |
| **Breaking change definition** | [What constitutes a breaking change for this service] |
| **Deprecation notice period** | [N months notice before removing a version] |
| **Currently supported versions** | [v1, v2 / v2 only] |
| **Sunset policy** | [Version N sunset date: YYYY-MM-DD] |
```

### A.4 Required Section Enhancement: Constraints & Limits (Section 8)

Add these subsections to the base TMPL-002 Section 8:

```markdown
### 8.N Failure Modes & Degraded Behavior

| Upstream Dependency | Failure Mode | Service Behavior |
|--------------------|-------------|-----------------|
| [Dependency A] | [Timeout / Error / Unavailable] | [Fallback: cached response / circuit open / 503 returned] |
| [Dependency B] | | |

### 8.N+1 SLA & SLO Declarations

| Metric | Target | Measurement Window | Exclusions |
|--------|--------|-------------------|------------|
| Availability | [99.9%] | [Rolling 30 days] | [Planned maintenance, upstream outages] |
| Latency p99 | [200ms] | [Per-hour rolling] | [Batch endpoints] |
| Error rate | [<0.1%] | [Per-hour rolling] | [Client errors (4xx)] |
```

---

## Variant B — Data Pipeline Technical Reference

**Use when:** Documenting a batch or streaming data pipeline.

### B.1 Additional Frontmatter Fields

```yaml
# ── DATA PIPELINE METADATA ───────────────────────────────────
pipeline_type: "batch | streaming | micro-batch | hybrid"
trigger_type: "scheduled | event-driven | manual | continuous"
schedule: ""                  # e.g., "Daily at 02:00 UTC / Every 5 minutes"
source_systems: []            # Where data comes from
sink_systems: []              # Where data goes
data_classification: "public | internal | confidential | restricted"
pii_in_scope: "yes | no"      # Does this pipeline process PII?
data_volume_daily: ""         # e.g., "~50GB / ~10M records"
processing_latency_sla: ""    # e.g., "T+4h for batch / <30s for streaming"
owns_output_schema: "yes | no" # Does this pipeline own or inherit the output schema?
```

### B.2 Additional Section: Data Source Specifications

Insert as Section 2.5 (after existing Section 2 Architecture):

```markdown
## 2.5 Data Source Specifications
[TYPE: REFERENCE]

### 2.5.1 Source Inventory

| Source | System | Connection | Format | Volume | Refresh |
|--------|--------|-----------|--------|--------|---------|
| [Source name] | [e.g., PostgreSQL 15 / Kafka topic] | [Connection string placeholder: <SOURCE_CONN>] | [Schema format] | [Daily volume] | [Frequency] |

### 2.5.2 Data Lineage

[Describe end-to-end lineage from raw source to final output. Include
intermediate transformations and any joins or aggregations applied.]

Source: [System A] → [Transform 1: description] → [Transform 2] → Sink: [System B]

### 2.5.3 Source Schema

[Define the schema of input data. If schema is owned by an upstream system,
reference that system's TMPL-002 document.]

| Field | Type | Nullable | Source System | Notes |
|-------|------|----------|--------------|-------|
| `[field_name]` | `[type]` | YES / NO | [Source] | [Transformation applied] |
```

### B.3 Additional Section: Data Quality Rules

Insert as Section 5.5:

```markdown
## 5.5 Data Quality Rules & Validation
[TYPE: REFERENCE]

### 5.5.1 Quality Rules Applied

Each rule is applied at the specified pipeline stage. Records failing
a HARD rule are quarantined; records failing a SOFT rule are flagged.

| Rule ID | Description | Type | Stage | Failure Action |
|---------|-------------|------|-------|---------------|
| DQ-001 | [e.g., `order_id` must be non-null and unique] | HARD | Ingest | Quarantine |
| DQ-002 | [e.g., `amount` must be positive] | HARD | Transform | Quarantine |
| DQ-003 | [e.g., `customer_email` format validates] | SOFT | Enrich | Flag + proceed |

### 5.5.2 Quality SLA

| Metric | Target | Alert Threshold |
|--------|--------|----------------|
| Record pass rate | >99.5% | <99% triggers alert |
| Quarantine rate | <0.5% | >1% triggers investigation |
| Processing latency | [SLA from frontmatter] | [Alert at N% over SLA] |

### 5.5.3 PII Handling
[Only if pii_in_scope: yes]

| PII Field | Handling Method | Retention | Notes |
|-----------|----------------|-----------|-------|
| `[field]` | [Masked / Tokenised / Encrypted / Excluded] | [Duration] | |
```

### B.4 Additional Section: Refresh Cadence & Partitioning

Insert as Section 5.6:

```markdown
## 5.6 Refresh Cadence & Partitioning Strategy
[TYPE: REFERENCE]

### 5.6.1 Schedule

| Run Type | Schedule | Expected Duration | SLA |
|----------|---------|------------------|-----|
| [Full refresh / Daily delta] | [CRON expression or description] | [N minutes] | [T+N hours] |
| [Backfill / Historical] | [On-demand] | [N hours] | [N/A — best effort] |

### 5.6.2 Partitioning

| Dataset | Partition Key | Partition Strategy | Retention |
|---------|-------------|-------------------|-----------|
| [Output dataset] | [e.g., `date` (YYYY-MM-DD)] | [By date / By region / By customer] | [N days / N months] |
```

---

## Variant C — ML Model Technical Reference

**Use when:** Documenting an ML model from a systems/engineering perspective
(how to call it, how it's served, what its contracts are). For transparency and
governance documentation, use TMPL-007 (Model Card) instead.

**TMPL-002 vs TMPL-007 for ML:**
- TMPL-002: How engineers integrate with and operate the model (API, serving, config)
- TMPL-007: What the model is, what it was trained on, its limitations, regulatory status

### C.1 Additional Frontmatter Fields

```yaml
# ── ML MODEL SERVING METADATA ────────────────────────────────
model_name: ""                # Same as in TMPL-007 if paired
model_version: ""             # Exact version this reference covers
model_card_ref: ""            # Document ID of paired TMPL-007 model card
serving_framework: ""         # e.g., "TorchServe 0.9 / Triton 23.12 / SageMaker"
inference_mode: "real-time | batch | both"
inference_latency_p99: ""     # e.g., "150ms"
throughput: ""                # e.g., "500 req/s per replica"
hardware_requirements: ""     # e.g., "1x A100 GPU / CPU-only"
model_registry: ""            # Where model artifacts are stored
artifact_location: ""         # Path or URI to model artifacts
```

### C.2 Additional Section: Inference API

Insert as replacement or supplement to TMPL-002 Section 4 (API Specification):

```markdown
## 4.N Inference API Specification
[TYPE: REFERENCE]

### 4.N.1 Prediction Endpoint

**Endpoint:** `POST /v{N}/predict`

**Request schema:**

| Field | Type | Required | Description | Constraints |
|-------|------|----------|-------------|------------|
| `input` | `[type]` | YES | [What the model takes as input] | [Max length / size] |
| `parameters` | `object` | NO | [Optional inference parameters] | |
| `parameters.temperature` | `float` | NO | [Sampling temperature if applicable] | 0.0 – 2.0 |
| `parameters.max_tokens` | `int` | NO | [Max output tokens if applicable] | 1 – [max] |

```json
// Example request
{
  "input": "<EXAMPLE_INPUT>",
  "parameters": {
    "temperature": 0.7
  }
}
```

**Response schema:**

| Field | Type | Description |
|-------|------|-------------|
| `prediction` | `[type]` | [Model output — e.g., "Classification label"] |
| `confidence` | `float` | [Confidence score 0–1, if applicable] |
| `model_version` | `string` | [Version that produced this prediction] |
| `latency_ms` | `int` | [Inference time in milliseconds] |

### 4.N.2 Batch Inference Endpoint (if applicable)

**Endpoint:** `POST /v{N}/predict/batch`

[Batch-specific schema and behavior]

### 4.N.3 Model Version Pinning

To use a specific model version (not the latest), pass:

```
Header: X-Model-Version: <MODEL_VERSION>
```

Supported version pinning window: [N days back from latest]

### 4.N.4 Feature Input Specification

| Feature Name | Type | Required | Normalization | Out-of-Distribution Handling |
|-------------|------|----------|--------------|----------------------------|
| `[feature]` | `[type]` | YES / NO | [Standardization method] | [Clipped / Flagged / Rejected] |
```

### C.3 Additional Section: Model Monitoring & Retraining

Insert as Section 8.N:

```markdown
## 8.N Model Monitoring & Retraining
[TYPE: REFERENCE]

### 8.N.1 Drift Monitoring

| Metric | Baseline | Alert Threshold | Response |
|--------|---------|----------------|---------|
| Prediction distribution drift | [Baseline distribution] | [KL divergence > 0.1] | [Investigate data shift] |
| Feature distribution drift | [Baseline] | [Threshold] | [Response] |
| Output confidence distribution | [Baseline] | [Mean confidence < 0.7] | [Trigger review] |
| [Business metric correlated with model] | [Baseline] | [Threshold] | [Response] |

### 8.N.2 Retraining Triggers

The model is retrained when ANY of the following conditions are met:

| Trigger | Threshold | Validation Before Deploy |
|---------|-----------|------------------------|
| Scheduled retraining | [e.g., "Quarterly"] | [Evaluation gate: must beat baseline by N%] |
| Drift alert fires | [Sustained > N hours] | [A/B test for N days] |
| Business metric degrades | [Threshold] | [Evaluation gate] |
| New training data available | [Volume threshold] | [Evaluation gate] |

### 8.N.3 Model Registry

| Attribute | Value |
|-----------|-------|
| **Registry location** | [MLflow / SageMaker Model Registry / Weights & Biases] |
| **Artifact storage** | [S3 path / GCS path — use placeholder: <MODEL_REGISTRY_PATH>] |
| **Versioning scheme** | [Semantic / Timestamp-based / Experiment ID] |
| **Rollback procedure** | [How to roll back to a prior version — or reference TMPL-003] |
```

---

*Source: TMPL-000 Domain Variant Guidance v1.0 — Phase 3 WP-3.4*
*Use alongside TMPL-002, not instead of it.*
