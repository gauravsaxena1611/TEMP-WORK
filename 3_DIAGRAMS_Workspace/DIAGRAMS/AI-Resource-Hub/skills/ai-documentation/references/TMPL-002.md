# [System / Component Name]: Technical Reference
## [Subtitle — e.g., "API Specification" or "Architecture Reference"]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-002: TECHNICAL REFERENCE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: API documentation, system architecture specs, database
schemas, configuration references, integration contracts, data
models, infrastructure specs, deployment architecture, system
component descriptions.

AUTHORING WORKFLOW:
  1. Identify the exact system/component version this covers
  2. Research skill runs ONLY for best-practice sections (e.g.,
     "Security Considerations") — NOT for factual system description
  3. Verification skill runs on all factual claims (versions,
     behaviors, constraints) that come from external sources
  4. Direct system knowledge (from code, config, tests) needs
     no external verification — mark as [SOURCE: direct]

KEY PRINCIPLE:
  This document describes what IS, not what SHOULD BE.
  Recommendations belong in TMPL-001 (research) or
  TMPL-005 (decision). This template is factual + precise.
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ──────────────────────────────
document_id: "[XXX Short-Title]"
title: ""
version: "1.0"
created: "YYYY-MM-DD"
status: "Draft | Review | Final"
parent_document: ""

# ── SYSTEM VERSION LOCK ──────────────────────────────────────
system_name: ""
system_version: ""         # Exact version this doc covers: "3.2.4"
applies_from: "YYYY-MM-DD" # When this version became effective
supersedes_version: ""     # Previous version this replaces, if any

# ── AI-OPTIMIZATION EXTENSION ───────────────────────────────
intent: >
  Enable [developers / agents / operators] to [integrate with /
  deploy / configure / query] [system name] by providing a
  precise, complete technical specification for version [X].

consumption_context:
  - human-reading
  - ai-reasoning
  - rag-retrieval
  - agentic-execution    # AI can generate code / configs from this

triggers:
  - "how does [system] work"
  - "[system] API reference"
  - "[system] configuration options"
  - "[system] [endpoint/schema/component] specification"
  - "what does [system] [verb]"

negative_triggers:
  - "how should I design [system]"   # → TMPL-001 or TMPL-005
  - "step by step [task]"            # → TMPL-003
  - "what is the best approach"      # → TMPL-001

volatility: "stable"
  # stable: changes only with system version changes
  # Increment document version whenever system_version changes

template_version_used: "TMPL-002 v1.1"  # Record which template version was active when this doc was written

research_validated: false  # Technical specs are direct, not research-driven
research_validated_date: ""
research_queries_used: []

review_trigger: "When system_version is incremented"

confidence_overall: "high"
confidence_note: "Direct system knowledge — verified against source code/config"
---
```

---

> ## 🤖 AI Summary
> **System:** [Name] v[Version]
> **Core Purpose:** [One sentence — what this system/component does]
> **Key Specifications:**
> - [Most important technical fact — e.g., "REST API, JSON, Bearer JWT auth"]
> - [Second critical spec — e.g., "PostgreSQL 15, Flyway migrations"]
> - [Third spec — e.g., "Rate limit: 1000 req/min per API key"]
> **Trust Level:** HIGH — direct system knowledge, version-locked
> **Version Validity:** [System version range this covers]
> **Do NOT Use This For:** Design decisions, best practice guidance, future state

---

## TABLE OF CONTENTS

1. [System Overview](#1-system-overview)
2. [Architecture](#2-architecture)
3. [Data Model](#3-data-model)
4. [API / Interface Specification](#4-api--interface-specification)
5. [Configuration Reference](#5-configuration-reference)
6. [Security Model](#6-security-model)
7. [Integration Points](#7-integration-points)
8. [Constraints & Limits](#8-constraints--limits)
9. [Error Reference](#9-error-reference)
10. [Version History & Migration Notes](#10-version-history--migration-notes)
11. [Sources & Cross-References](#11-sources--cross-references)
12. [Revision History](#12-revision-history)

<!--
SECTION SELECTION GUIDE:
Not every section applies to every technical reference.
Remove sections that do not apply. Do NOT leave them with
"N/A" content — remove the section entirely and note
in the TOC what was excluded and why.

MINIMUM REQUIRED SECTIONS:
  1 (Overview), last section (Cross-References), Revision History
-->

---

## 1. System Overview
[TYPE: REFERENCE]

<!--
CHUNKING NOTE: This section is retrieved most frequently.
Keep it dense and complete — 150-300 words.
An agent reading only this section must understand
what the system does, what it connects to, and its key constraints.
-->

### 1.1 Purpose & Responsibility

[2-3 sentences describing what this system/component does.
Be precise about boundaries — what it handles and what it
delegates to other systems.]

`[system-name]` is responsible for: [list of responsibilities]
`[system-name]` does NOT handle: [list of non-responsibilities — important for RAG precision]

### 1.2 Technical Identity

| Attribute | Value |
|-----------|-------|
| **System Name** | `[name]` |
| **Version** | `[version]` |
| **Type** | [REST API / gRPC service / batch processor / library / etc.] |
| **Language** | [Java 17 / Python 3.11 / etc.] |
| **Framework** | [Spring Boot 3.2.4 / FastAPI 0.110 / etc.] |
| **Deployment Target** | [Kubernetes / Lambda / Docker / etc.] |
| **Primary Database** | [PostgreSQL 15 / DynamoDB / etc.] |
| **Message Broker** | [Kafka / RabbitMQ / None] |
| **Source Repository** | `[repo URL or path]` |

### 1.3 System Boundaries

```
[ASCII or text diagram showing system position]
Example:
  [Client] → [This System] → [Database]
                           → [External Service A]
                           → [Message Queue]
```

---

## 2. Architecture
[TYPE: REFERENCE]

<!--
INCLUDE: Only what is relevant to consumers of this reference.
An API consumer may need authentication architecture.
An operator may need deployment architecture.
A developer may need internal module architecture.
Decide who reads this document and include accordingly.
-->

### 2.1 High-Level Architecture

[1-2 sentence narrative of the architecture pattern.
Example: "Layered REST API following hexagonal architecture.
External requests enter through the Controller layer, pass
through Service orchestration, and interact with the
Repository layer for persistence."]

**Architecture Pattern:** [Hexagonal / MVC / Event-driven / etc.]

### 2.2 Component Structure

| Component | Responsibility | Technology |
|-----------|---------------|------------|
| `[ComponentName]` | [What it does] | [Technology] |
| `[ComponentName]` | [What it does] | [Technology] |

### 2.3 Data Flow

```
[Request/Data flow in sequential order]
Example:
  1. HTTP Request → AuthFilter (JWT validation)
  2. → OrderController (request mapping, @Valid)
  3. → OrderService (business logic, @Transactional)
  4. → OrderRepository (persistence, Spring Data JPA)
  5. → PostgreSQL (storage)
  Response follows reverse path
```

### 2.4 Key Design Decisions

<!--
IMPORTANT: Do NOT include the rationale for decisions here.
That belongs in TMPL-005 (Decision Records).
Here, only state WHAT was decided, so consumers know the constraints.
-->

| Decision | What Was Decided | Decision Record |
|----------|-----------------|-----------------|
| [Topic] | [What applies] | [DECISION → Doc ID, Section X] |
| [Topic] | [What applies] | [DECISION → Doc ID, Section X] |

---

## 3. Data Model
[TYPE: REFERENCE]

<!--
INCLUDE IF: System manages persistent data.
SKIP IF: Stateless service with no data ownership.

PRECISION REQUIREMENT: This section must exactly match
the actual database schema. Version-lock is critical.
Mark the source of truth for schema.
-->

### 3.1 Schema Source of Truth

The authoritative schema is maintained in: `[migration directory / schema file path]`
Migration tool: [Flyway / Liquibase / Manual]
Last migration applied: `[version identifier]`

### 3.2 Entities

#### `[table_name]`

| Column | Type | Nullable | Default | Constraints | Description |
|--------|------|----------|---------|-------------|-------------|
| `[column]` | `[type]` | YES / NO | `[default]` | [PK / FK / UNIQUE / CHECK] | [Purpose] |

**Indexes:**
- `[index_name]` ON (`[columns]`) — [Purpose]

**Business Notes:**
- [Any domain context needed to use this table correctly]
- [Soft delete strategy if applicable]
- [Audit fields if applicable]

#### `[next_table_name]` — repeat pattern

### 3.3 Relationships

| From | Cardinality | To | Description |
|------|------------|-----|-------------|
| `[table.column]` | N:1 / 1:N / N:N | `[table.column]` | [What the relationship represents] |

### 3.4 Enums & Constrained Values

| Table.Column | Allowed Values | Semantics |
|-------------|----------------|-----------|
| `[table.column]` | `VALUE_1, VALUE_2, VALUE_3` | [What each value means] |

---

## 4. API / Interface Specification
[TYPE: REFERENCE]

<!--
PRECISION REQUIREMENT: Every field, every header, every status code.
Ambiguity here causes integration bugs. Be exhaustive.

FORMAT NOTE: Use tables for structured data — highest LLM
extraction accuracy for structured comparisons.
Always wrap paths, methods, field names in backticks.
-->

### 4.1 API Identity

| Attribute | Value |
|-----------|-------|
| **Base URL (Production)** | `[URL]` |
| **Base URL (Staging)** | `[URL]` |
| **API Version Path** | `/api/v[N]/` |
| **Protocol** | HTTPS only |
| **Format** | JSON (`application/json`) |
| **Authentication** | [Bearer JWT / API Key / OAuth 2.0] |
| **Rate Limit** | [N] requests/[period] per [API key / user / IP] |

### 4.2 Authentication

**Method:** [Describe authentication mechanism precisely]

```http
Authorization: Bearer [JWT_TOKEN]
```

**Token acquisition:**
1. [Step 1]
2. [Step 2]

**Token validity:** [Duration]
**Refresh mechanism:** [How to refresh]
**Error on invalid token:** `401 Unauthorized` — see Section 9.1

### 4.3 Endpoints

#### [Resource Group — e.g., Orders]

##### `POST [/resource]`

**Purpose:** [What this endpoint does in one sentence]

**Request:**

| Field | Type | Required | Validation | Notes |
|-------|------|----------|------------|-------|
| `[field]` | `[type]` | YES / NO | [Rules] | [Context] |

```json
// Example request body
{
  "[field]": "[example value]",
  "[field]": [example_value]
}
```

**Response — `201 Created`:**

| Field | Type | Nullable | Notes |
|-------|------|----------|-------|
| `[field]` | `[type]` | YES / NO | [Context] |

```json
// Example response body
{
  "[field]": "[example value]"
}
```

**Error Responses:** `400` [validation failure], `401` [auth], `409` [conflict]
See Section 9 for error response schema.

---

##### `GET [/resource/{id}]`

**Purpose:** [What this endpoint retrieves]

**Path Parameters:**

| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | `UUID` | [Description] |

**Query Parameters:**

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `[param]` | `[type]` | NO | `[default]` | [Description] |

**Response — `200 OK`:** [Schema reference or inline]

**Error Responses:** `404` [not found], `401` [auth]

---

<!--
REPEAT ENDPOINT PATTERN FOR:
GET /resource (list/search)
PUT /resource/{id} (update)
DELETE /resource/{id} (delete)
Any custom actions

Pagination pattern (if applicable):
-->

### 4.4 Pagination

| Attribute | Value |
|-----------|-------|
| **Style** | [Page-based / Cursor-based / Offset] |
| **Default Page Size** | [N] |
| **Maximum Page Size** | [N] |
| **Request Parameters** | `page=[N]&size=[N]&sort=[field],[asc/desc]` |
| **Response Fields** | `content`, `totalElements`, `totalPages`, `number`, `size` |

### 4.5 Custom Headers

| Header | Direction | Required | Purpose |
|--------|-----------|----------|---------|
| `[X-Header-Name]` | Request / Response / Both | YES / NO | [Purpose] |

---

## 5. Configuration Reference
[TYPE: REFERENCE]

<!--
INCLUDE IF: System is configurable via environment, files, or flags.
Every property that affects behavior belongs here.
Distinguish between required and optional properties.
Mark which properties are secret / should not be logged.
-->

### 5.1 Environment Variables

| Variable | Required | Default | Description | Secret? |
|----------|----------|---------|-------------|---------|
| `[VAR_NAME]` | YES / NO | `[default]` | [What it configures] | YES / NO |

### 5.2 Application Configuration

File: `[path/to/config.yml]`

```yaml
# [Section name]
[property]:
  [sub-property]: [type] # [Description. Default: value. Range: min-max]
  [sub-property]: [type] # [Description]

# [Next section]
```

### 5.3 Feature Flags

| Flag | Type | Default | Effect When Enabled |
|------|------|---------|---------------------|
| `[flag-name]` | Boolean | false | [What changes] |

---

## 6. Security Model
[TYPE: REFERENCE]

<!--
PRECISION REQUIREMENT: Security specifications must be exact.
Vague security documentation leads to integration vulnerabilities.
If best-practice research informed security decisions, reference
the TMPL-001 or TMPL-005 document where those decisions were made.
Do NOT re-derive security reasoning here — only describe what IS.
-->

### 6.1 Authentication & Authorization

| Aspect | Implementation |
|--------|---------------|
| **Authentication Method** | [JWT / API Key / OAuth 2.0 / SAML] |
| **Token Issuer** | `[Issuer URL]` |
| **Token Validation** | [How tokens are validated] |
| **Authorization Model** | [RBAC / ABAC / Scope-based] |

### 6.2 Roles & Permissions

| Role | Permissions | Notes |
|------|-------------|-------|
| `[ROLE_NAME]` | [List of allowed actions] | [Context] |

### 6.3 Data Protection

| Data Type | At Rest | In Transit | Notes |
|-----------|---------|------------|-------|
| [PII fields] | [Encryption method] | TLS 1.3 | |
| [Sensitive fields] | [Method] | TLS 1.3 | |

### 6.4 Sensitive Content Rules for This Document
[TYPE: REFERENCE]

This section defines what sensitive content must never appear in this
technical reference document, regardless of how convenient it would be
to include. These rules apply to all technical reference documents in
this system.
✅ `[VERIFIED — TMPL-000 conventions, Section 10, v1.1]`

**Never document the following directly in this document's body text:**
- Credentials of any kind: passwords, API keys, bearer tokens, OAuth
  secrets, private certificates, or passphrases — even as examples
- Real Personal Identifiable Information (PII): actual user names, email
  addresses, user IDs, phone numbers, or physical addresses in code samples
- Production-environment connection strings containing usernames or passwords
- Private hostnames, internal IP addresses, or internal service URLs not
  intended for public documentation
- Actual production configuration values (use placeholder syntax below)

**Approved placeholder syntax for all examples and code blocks:**

```
# Configuration examples always use placeholders:
API_KEY=<YOUR_API_KEY>
DB_URL=postgresql://<DB_USER>:<DB_PASSWORD>@<DB_HOST>:5432/<DB_NAME>
AUTH_TOKEN=<BEARER_TOKEN>
WEBHOOK_SECRET=<WEBHOOK_SECRET>
USER_ID=<EXAMPLE_USER_ID>
```

**For agent context references:** When an AI agent needs to retrieve
a credential at runtime, reference the secrets manager path, never the
value: `DB_PASSWORD → retrieve from secrets/prod/db_password`.

---

## 7. Integration Points
[TYPE: REFERENCE]

<!--
INCLUDE: All external systems this component depends on or exposes to.
This is critical for AI agents doing impact analysis.
-->

### 7.1 Upstream Dependencies

Systems this component CALLS:

| System | Purpose | Protocol | Timeout | Failure Behavior |
|--------|---------|----------|---------|-----------------|
| `[System Name]` | [Why it's called] | REST / gRPC / Event | [N]ms | [Fallback / circuit breaker] |

### 7.2 Downstream Consumers

Systems that CALL this component:

| System | What It Uses | Integration Pattern |
|--------|-------------|---------------------|
| `[System Name]` | [Endpoints / events consumed] | [Sync REST / Async event] |

### 7.3 Events Published

| Event Name | Trigger | Schema | Consumers |
|------------|---------|--------|-----------|
| `[event.name]` | [When published] | [Schema reference] | [Who consumes] |

### 7.4 Events Consumed

| Event Name | Source | Action Taken |
|------------|--------|-------------|
| `[event.name]` | `[source system]` | [What happens] |

---

## 8. Constraints & Limits
[TYPE: REFERENCE]

<!--
WHY THIS SECTION EXISTS:
Hard limits that AI agents and developers must know before
designing integrations. Non-negotiable technical constraints.
Separate from business rules (which belong in TMPL-004 or
context files).
-->

### 8.1 Technical Limits

| Constraint | Limit | Behavior When Exceeded |
|-----------|-------|----------------------|
| Rate limit | [N] req/[period] | `429 Too Many Requests` |
| Request body size | [N] MB | `413 Payload Too Large` |
| Response timeout | [N] seconds | `504 Gateway Timeout` |
| [Field name] max length | [N] chars | `400 Bad Request` |
| [Concurrent connections] | [N] | [Behavior] |

### 8.2 Known Limitations

<!--
Honest statement of known limitations, quirks, or gotchas.
Agents need this to avoid generating incorrect integrations.
-->

- [Limitation 1 — e.g., "Bulk endpoints limited to 100 items per request"]
- [Limitation 2 — e.g., "Sort is not supported on computed fields"]
- [Limitation 3]

---

## 9. Error Reference
[TYPE: REFERENCE]

<!--
Complete, precise error catalog.
This is a high-frequency retrieval section for agents generating
error handling code or troubleshooting integrations.
-->

### 9.1 Standard Error Response Schema

```json
{
  "timestamp": "ISO 8601",
  "status": "integer — HTTP status code",
  "error": "string — error category",
  "message": "string — human-readable description",
  "path": "string — request path",
  "traceId": "string — for log correlation"
}
```

### 9.2 Error Catalog

| Status | Code / Type | Message Pattern | Cause | Resolution |
|--------|-------------|----------------|-------|------------|
| `400` | `VALIDATION_FAILED` | `Field [x] failed: [reason]` | Invalid request payload | Fix request per schema |
| `401` | `UNAUTHORIZED` | `Invalid or expired token` | Missing/bad auth | Re-authenticate |
| `403` | `FORBIDDEN` | `Insufficient permissions` | Role mismatch | Check role assignment |
| `404` | `NOT_FOUND` | `[Resource] [id] not found` | ID doesn't exist | Verify ID |
| `409` | `CONFLICT` | `[Resource] already exists` | Duplicate creation | Check before create |
| `422` | `BUSINESS_RULE_VIOLATION` | [varies by rule] | Domain constraint | See business rules |
| `429` | `RATE_LIMIT_EXCEEDED` | `Retry after [N] seconds` | Rate limit hit | Back off, use Retry-After header |
| `500` | `INTERNAL_ERROR` | `Internal server error` | Server-side failure | Log traceId, contact support |
| `503` | `SERVICE_UNAVAILABLE` | `Service temporarily unavailable` | Downstream failure | Retry with backoff |

---

## 10. Version History & Migration Notes
[TYPE: REFERENCE]

<!--
WHY THIS SECTION EXISTS:
Agents generating integration code need to know what changed between
versions to avoid using deprecated patterns.
Keep BRIEF — detailed changelogs belong in CHANGELOG.md.
Only include breaking changes and significant additions here.
-->

### 10.1 Current Version Changes

**v[current version] — [Date]**
- [Breaking change or significant addition 1]
- [Breaking change or significant addition 2]

### 10.2 Migration Notes (From Previous Version)

| Old Pattern | New Pattern | Breaking? |
|-------------|-------------|-----------|
| `[old endpoint/field/behavior]` | `[new equivalent]` | YES / NO |

### 10.3 Deprecation Schedule

| Deprecated Feature | Deprecated In | Removal In | Replacement |
|-------------------|--------------|------------|-------------|
| `[feature/endpoint]` | v[N] | v[N+1] | `[replacement]` |

---

## 11. Sources & Cross-References
[TYPE: REFERENCE]

### 11.1 Source of Truth for this Document

| Content Area | Source | Location |
|-------------|--------|----------|
| Schema | Migration files | `[path]` |
| API endpoints | Controller source | `[path]` |
| Configuration | application.yml | `[path]` |
| Security config | SecurityConfig.java | `[path]` |

### 11.2 Document Cross-References

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DEPENDS_ON] | [Doc ID] | [Section] | [Why] |
| [IMPLEMENTS] | [Doc ID — design/decision] | [Section] | [What this implements] |
| [SEE_ALSO] | [Doc ID] | | [Related reference] |
| [VALIDATED_BY] | [External spec/standard] | | [What it validates] |

---

## 12. Revision History

| Version | Date | Change Type | System Version | Description | Reason |
|---------|------|-------------|----------------|-------------|--------|
| 1.0 | [Date] | Initial | [System v] | Document created | [Reason] |

---

*Template: TMPL-002 Technical Reference v1.0 | Parent: TMPL-000 Template Index*
