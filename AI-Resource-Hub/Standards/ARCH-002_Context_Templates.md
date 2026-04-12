# Context Template Generator
## Ready-to-Use Templates, Scan Instructions & Generation Workflows for Living Context Files

**Document ID:** ARCH-002 Context Template Generator  
**Parent Document:** ARCH-001 Skill Architecture & Living Context Framework  
**Version:** 2.0  
**Created:** March 28, 2026  
**Status:** Active
**Updated:** 2026-04-05  

---

## PURPOSE

**This document covers:**
- Complete, ready-to-use templates for every standard context file type defined in the Living Context Framework
- Exact scan instructions for SCANNABLE contexts — what files to read, what to extract, how to structure findings
- Exact question sets for DECLARED and INFERRABLE contexts — what to ask users on first use and on refresh
- The generation workflow — step-by-step process Claude follows when creating context files for a new project
- Domain-specific templates optimized for Java/Spring Boot backend development
- A generic template for creating custom context types not covered by the standard set
- Integration guide for the Skill Creator Pro prompt (PROMPT-001)

**This document does NOT cover:**
- The architectural principles behind the Living Context system — See [ARCH-001, Sections 1-3]
- The Context Resolution Lifecycle — See [ARCH-001, Section 5]
- The Passive Learning mechanism — See [ARCH-001, Section 6]
- The Skills Updater meta-skill specification — See [ARCH-001, Section 7]

---

## TABLE OF CONTENTS

1. [How This Document Works](#1-how-this-document-works)
2. [The Generation Workflow](#2-the-generation-workflow)
3. [Template: Context Manifest](#3-template-context-manifest)
4. [Template: Project Standards (INFERRABLE)](#4-template-project-standards-inferrable)
5. [Template: Database Schema (SCANNABLE)](#5-template-database-schema-scannable)
6. [Template: API Contract (SCANNABLE)](#6-template-api-contract-scannable)
7. [Template: Business Rules (DECLARED)](#7-template-business-rules-declared)
8. [Template: Environment Configuration (DECLARED)](#8-template-environment-configuration-declared)
9. [Template: Pending Observations](#9-template-pending-observations)
10. [Template: Custom Context (Generic)](#10-template-custom-context-generic)
11. [Scan Instructions Reference](#11-scan-instructions-reference)
12. [Question Bank Reference](#12-question-bank-reference)
13. [Integration with Skill Creator Pro](#13-integration-with-skill-creator-pro)
14. [Project Brain Templates — Overview](#14-project-brain-templates--overview)
15. [Template: CLAUDE.md — Agent Entry Point](#15-template-claudemd--agent-entry-point) [TMPL-004A]
16. [Template: Contracts.md — Architectural Constitution](#16-template-contractsmd--architectural-constitution) [TMPL-002 + TMPL-005]
17. [Template: Work Audit.md — Project Provenance Log](#17-template-work-auditmd--project-provenance-log) [TMPL-004C]
18. [Template: Application Understanding.md](#18-template-application-understandingmd) [TMPL-002]
19. [Template: Application Workflows.md](#19-template-application-workflowsmd) [TMPL-003]
20. [Template: Application Features.md](#20-template-application-featuresmd) [TMPL-002]
21. [Template: Data Models.md](#21-template-data-modelsmd) [TMPL-002]
22. [Template: Users.md](#22-template-usersmd) [TMPL-002]
23. [Template: context/api-contract.md](#23-template-contextapi-contractmd) [TMPL-002]
24. [Template: context/environment-config.md](#24-template-contextenvironment-configmd) [TMPL-002]
25. [Template: context/context-manifest.md](#25-template-contextcontext-manifestmd) [TMPL-002]
26. [Template: features/<n>/Feature Understanding.md](#26-template-featuresnfeature-understandingmd) [TMPL-002]
27. [Template: features/<n>/Feature Work Audit.md](#27-template-featuresnfeature-work-auditmd) [TMPL-004C]
28. [Template: features/<n>/Tests Scenarios.md](#28-template-featuresntest-scenariosmd) [TMPL-003]
29. [Template: features/<n>/Test Data Setup.md](#29-template-featuresntest-data-setupmd) [TMPL-003]
30. [Template: archives/INGESTION-LOG.md](#30-template-archivesINGESTION-LOGmd) [TMPL-002]
31. [Q22–Q34: Contracts.md Question Bank](#31-q22q34-contractsmd-question-bank)

---

## 1. HOW THIS DOCUMENT WORKS

### 1.1 Document Role

This document is an operational reference — not a theoretical framework. It contains the actual templates and instructions that Claude uses when creating context files. Every template in this document is designed to be copied, populated with project-specific data, and saved as a `.ctx.md` file.

When a skill is invoked for the first time on a project and context files need to be created, Claude reads the relevant template from this document, follows the scan instructions or asks the specified questions, and produces a populated context file.

### 1.2 Template Structure

Each template section follows this pattern:

1. **Template Purpose** — what this context file captures and which skills typically need it
2. **Ready-to-Use Template** — the complete markdown template with placeholders
3. **Scan Instructions** (for SCANNABLE types) — exactly what files to read and how to extract information
4. **Question Set** (for DECLARED/INFERRABLE types) — the exact questions to ask the user
5. **Populated Example** — a realistic filled-in example showing what the completed file looks like
6. **Refresh Protocol** — how to validate this context on subsequent uses
7. **Passive Learning Signals** — what observations to watch for that might indicate this context has changed

### 1.3 Naming Conventions

All context files use the `.ctx.md` extension as defined in [ARCH-001, Section 4.1]:

```
Standard files:
  context-manifest.ctx.md
  project-standards.ctx.md
  db-schema.ctx.md
  api-contract.ctx.md
  business-rules.ctx.md
  environment-config.ctx.md
  pending-observations.ctx.md

Custom files:
  [descriptive-name].ctx.md
```

---

## 2. THE GENERATION WORKFLOW

### 2.1 When Context Generation Triggers

Context generation runs when a skill declares a required context (in its `## Required Contexts` section) and the corresponding `.ctx.md` file does not exist for the current project.

### 2.2 Full Generation Sequence

When one or more context files are missing, Claude follows this workflow:

```
Step 1: INVENTORY
  Read the skill's ## Required Contexts table
  List all required context IDs
  Check which .ctx.md files exist
  Identify MISSING contexts

Step 2: SORT BY TYPE
  Group missing contexts:
    SCANNABLE  → Will attempt to scan project files
    INFERRABLE → Will scan code patterns + ask user
    DECLARED   → Will ask user directly

Step 3: SCAN (for SCANNABLE contexts)
  For each SCANNABLE context:
    a. Identify source files per Scan Instructions (Section 11)
    b. In Claude Code: read files directly
       In Claude.ai: ask user to upload files or provide info
    c. Extract and structure data per the template
    d. Present findings to user for confirmation
    e. Save confirmed context file

Step 4: INFER (for INFERRABLE contexts)
  For each INFERRABLE context:
    a. Identify representative source files to scan
    b. In Claude Code: read 5-10 files per category
       In Claude.ai: ask user to describe patterns or paste examples
    c. Identify patterns with evidence counts
    d. Present findings: "I observed X in Y out of Z files"
    e. Ask user to confirm, correct, or supplement
    f. Save confirmed context file

Step 5: ASK (for DECLARED contexts)
  Batch ALL questions from ALL DECLARED contexts
  Present as a single numbered list
  Collect all answers
  Structure into context files
  Read back for user verification
  Save confirmed context files

Step 6: CREATE MANIFEST
  If context-manifest.ctx.md does not exist:
    Create it with all context files indexed
  If it exists:
    Update it with newly created contexts

Step 7: CONFIRM
  Present summary:
    "Created [N] context files for this project:
     ✅ project-standards.ctx.md (INFERRABLE — confirmed)
     ✅ db-schema.ctx.md (SCANNABLE — confirmed)
     ✅ business-rules.ctx.md (DECLARED — confirmed)
     ...
     Ready to proceed with the skill workflow."
```

### 2.3 Efficiency Rules

**Batch questions:** All DECLARED context questions across all missing contexts are presented in a single interaction. Claude never asks one question at a time across multiple rounds.

**Confirm before saving:** Claude always presents the generated content to the user before saving. No context file is created silently.

**Fail gracefully:** If a SCANNABLE source file cannot be found (e.g., no OpenAPI spec exists), Claude downgrades that context to DECLARED and asks the user directly. It never skips a required context.

**Minimal viable context:** If the user wants to proceed quickly, Claude can create a minimal context with just the essential fields filled in. Missing sections are marked as `[TO BE COMPLETED]` and flagged as incomplete in the manifest.

---

## 3. TEMPLATE: CONTEXT MANIFEST

### 3.1 Purpose

The context manifest is the index file for all context files in a project. It is the first file the Skills Updater meta-skill reads. Every project has exactly one manifest.

### 3.2 Template

```markdown
# Context Manifest

## Project Information

| Field | Value |
|-------|-------|
| **Project Name** | {{PROJECT_NAME}} |
| **Project Type** | {{PROJECT_TYPE}} |
| **Primary Language** | {{LANGUAGE}} |
| **Framework** | {{FRAMEWORK}} |
| **Build Tool** | {{BUILD_TOOL}} |
| **Created** | {{DATE}} |
| **Last Full Review** | {{DATE}} |

## Context Files Index

| Context ID | File | Type | Last Verified | Status |
|------------|------|------|---------------|--------|
| {{CTX_ID}} | {{FILENAME}} | {{TYPE}} | {{DATE}} | {{STATUS}} |

## Pending Observations Summary

{{COUNT}} observations pending review — see pending-observations.ctx.md

## Staleness Thresholds

| Type | Flag as Stale After |
|------|---------------------|
| SCANNABLE | Source file modified since last scan |
| INFERRABLE | 14 days without re-confirmation OR 3+ contradicting observations |
| DECLARED | 30 days without re-confirmation |

## Installed Skills

| Skill Name | Required Contexts | Last Used |
|------------|-------------------|-----------|
| {{SKILL_NAME}} | {{CTX_IDS}} | {{DATE}} |

## Change Log

| Date | What Changed | Confirmed By |
|------|-------------|--------------|
| {{DATE}} | Manifest created | {{USER}} |
```

### 3.3 Generation Notes

The manifest is always the last context file created during the generation workflow. It is populated automatically from the other context files that were created or already exist. It does not require user input beyond what was already collected.

---

## 4. TEMPLATE: PROJECT STANDARDS (INFERRABLE)

### 4.1 Purpose

Captures the team's coding conventions, architectural patterns, naming standards, and development practices. This is the most commonly referenced context file — nearly every skill that produces or analyzes code depends on it.

### 4.2 Template

```markdown
# Context: Project Standards

## Context Metadata

| Field | Value |
|-------|-------|
| **Context ID** | CTX-STD |
| **Type** | INFERRABLE |
| **Source** | Codebase pattern analysis + user confirmation |
| **Created** | {{DATE}} |
| **Last Verified** | {{DATE}} |
| **Last Updated** | {{DATE}} |
| **Verified By** | User confirmation |
| **Confidence** | {{HIGH/MEDIUM/LOW}} |
| **Skills That Use This** | {{SKILL_LIST}} |

## Framework & Language

| Attribute | Value | Source |
|-----------|-------|--------|
| Language | {{e.g., Java 17}} | pom.xml / build.gradle |
| Framework | {{e.g., Spring Boot 3.2.4}} | pom.xml / build.gradle |
| Build Tool | {{e.g., Maven 3.9.x}} | Project structure |
| Package Structure | {{e.g., com.company.project}} | Source directory |

## Controller Layer Standards

| Convention | Pattern | Evidence |
|------------|---------|----------|
| Controller Annotation | {{e.g., @RestController}} | Scanned: {{X}}/{{Y}} controllers |
| Endpoint Mapping | {{e.g., @GetMapping, @PostMapping}} | Scanned: {{X}}/{{Y}} endpoints |
| URL Naming | {{e.g., kebab-case: /order-items}} | Scanned: {{X}}/{{Y}} paths |
| Response Wrapping | {{e.g., ResponseEntity<T>}} | Scanned: {{X}}/{{Y}} methods |
| Validation | {{e.g., @Valid on @RequestBody}} | Scanned: {{X}}/{{Y}} POST/PUT methods |

## Service Layer Standards

| Convention | Pattern | Evidence |
|------------|---------|----------|
| Service Annotation | {{e.g., @Service}} | Scanned: {{X}}/{{Y}} services |
| Transaction Management | {{e.g., @Transactional on service methods}} | Scanned: {{X}}/{{Y}} write methods |
| Interface Usage | {{e.g., No interfaces for services / Interface per service}} | Scanned: {{X}}/{{Y}} services |

## Data Access Layer Standards

| Convention | Pattern | Evidence |
|------------|---------|----------|
| Repository Pattern | {{e.g., Spring Data JPA @Repository}} | Scanned: {{X}}/{{Y}} repositories |
| Query Method Style | {{e.g., Derived queries / @Query JPQL}} | Scanned: {{X}}/{{Y}} query methods |
| Entity Annotations | {{e.g., @Entity with @Table(name=...)}} | Scanned: {{X}}/{{Y}} entities |

## DTO & Model Standards

| Convention | Pattern | Evidence |
|------------|---------|----------|
| DTO Naming | {{e.g., {Entity}Response, {Entity}Request}} | Scanned: {{X}}/{{Y}} DTOs |
| DTO Type | {{e.g., Java record / POJO with Lombok}} | Scanned: {{X}}/{{Y}} DTOs |
| Mapping Approach | {{e.g., MapStruct / manual mapping}} | Scanned: {{X}}/{{Y}} mappers |
| Validation | {{e.g., Jakarta Bean Validation on Request DTOs}} | Scanned: {{X}}/{{Y}} request DTOs |

## Exception Handling Standards

| Convention | Pattern | Evidence |
|------------|---------|----------|
| Global Handler | {{e.g., @ControllerAdvice}} | Scanned: exists/not found |
| Error Response Format | {{e.g., ProblemDetail RFC 7807 / Custom ErrorResponse}} | Scanned from handler |
| Custom Exceptions | {{e.g., {Entity}NotFoundException extends RuntimeException}} | Scanned: {{X}} custom exceptions |

## Logging Standards

| Convention | Pattern | Evidence |
|------------|---------|----------|
| Framework | {{e.g., SLF4J with Logback}} | pom.xml / build.gradle |
| Format | {{e.g., Structured JSON / Pattern layout}} | logback.xml / application.yml |
| Logger Declaration | {{e.g., @Slf4j Lombok / LoggerFactory.getLogger()}} | Scanned: {{X}}/{{Y}} classes |

## Testing Standards

| Convention | Pattern | Evidence |
|------------|---------|----------|
| Test Framework | {{e.g., JUnit 5 + Mockito}} | pom.xml / build.gradle |
| Integration Tests | {{e.g., @SpringBootTest + TestContainers}} | Scanned: {{X}}/{{Y}} test classes |
| API Tests | {{e.g., MockMvc / RestAssured / WebTestClient}} | Scanned: {{X}}/{{Y}} controller tests |
| Naming Convention | {{e.g., should_ReturnX_When_Y / given_when_then}} | Scanned: {{X}}/{{Y}} test methods |
| Assertion Style | {{e.g., AssertJ / JUnit Assertions}} | Scanned: {{X}}/{{Y}} test methods |

## Code Style & Formatting

| Convention | Pattern | Evidence |
|------------|---------|----------|
| Formatter | {{e.g., IntelliJ defaults / Checkstyle / Spotless}} | Config files |
| Import Order | {{e.g., java.*, javax.*, org.*, com.*}} | Observed |
| Access Modifiers | {{e.g., Package-private default / Everything public}} | Observed |
| Null Handling | {{e.g., Optional<T> return / @Nullable annotations}} | Scanned: {{X}}/{{Y}} methods |

## Known Exceptions & Legacy Patterns

| Pattern | Where Found | Status |
|---------|-------------|--------|
| {{e.g., @RequestMapping used}} | {{e.g., LegacyUserController}} | Legacy — do not replicate |
| {{e.g., UserDto naming}} | {{e.g., legacy module}} | Legacy — new DTOs use {Entity}Response |

## Observations Log

| Date | Observation | Status |
|------|-------------|--------|

## Change Log

| Date | What Changed | How Discovered | Confirmed By |
|------|-------------|----------------|--------------|
| {{DATE}} | Initial creation | Codebase scan + user confirmation | User |
```

### 4.3 Scan Instructions (Claude Code)

```
SCAN PROTOCOL FOR PROJECT STANDARDS

Step 1: Identify project structure
  Read: pom.xml OR build.gradle → Extract Java version, Spring Boot version, dependencies
  Read: src/main/java/ directory structure → Extract package naming, module organization

Step 2: Scan Controllers (minimum 5 files or all if fewer)
  Pattern: src/main/java/**/controller/**/*.java
  OR:      src/main/java/**/*Controller.java
  Extract: class annotations, method annotations, return types, parameter annotations
  Count: occurrences of each pattern for evidence

Step 3: Scan Services (minimum 5 files or all if fewer)
  Pattern: src/main/java/**/service/**/*.java
  OR:      src/main/java/**/*Service.java
  Extract: class annotations, transaction annotations, interface usage

Step 4: Scan DTOs/Models (minimum 5 files or all if fewer)
  Pattern: src/main/java/**/dto/**/*.java
  OR:      src/main/java/**/model/**/*.java
  Extract: naming patterns, class type (record/class/Lombok), validation annotations

Step 5: Scan Exception handling
  Pattern: src/main/java/**/*Exception*.java
  AND:     src/main/java/**/*Advice*.java OR *ExceptionHandler*.java
  Extract: global handler presence, error response format, custom exception hierarchy

Step 6: Scan Tests (minimum 5 files or all if fewer)
  Pattern: src/test/java/**/*Test.java
  Extract: test framework, assertion style, naming convention, integration test patterns

Step 7: Scan Configuration
  Read: src/main/resources/application.yml OR application.properties
  Read: logback.xml OR logback-spring.xml (if exists)
  Read: checkstyle.xml OR .editorconfig (if exists)
  Extract: logging framework, format, any code style configuration
```

### 4.4 Question Set (User Confirmation)

After scanning, present findings and ask:

```
CONFIRMATION QUESTIONS FOR PROJECT STANDARDS

I scanned your codebase and identified these patterns.
Please confirm, correct, or add anything I missed.

1. FRAMEWORK: I found {{Framework}} {{Version}} with {{Build Tool}}.
   Correct? [yes/no/correct to: ___]

2. CONTROLLER PATTERNS: I observed {{X}} out of {{Y}} controllers using
   {{annotation pattern}}. Is this your team's standard?
   [yes / no, we're migrating to ___ / it varies by ___]

3. DTO NAMING: I found {{pattern}} in {{X}} out of {{Y}} DTOs.
   Is this intentional? Are the exceptions ({{list}}) legacy?
   [yes, exceptions are legacy / no, the standard is actually ___]

4. EXCEPTION HANDLING: I found {{pattern}}.
   Is this the current approach? Any planned changes?
   [yes / migrating to ___ / additional rules: ___]

5. TESTING: I observed {{framework}} with {{assertion style}}.
   Any conventions I should know about test structure or naming?
   [what I found is correct / also: ___]

6. ANYTHING MISSING? Are there conventions your team follows
   that wouldn't be visible in code? (e.g., PR processes,
   documentation requirements, deployment rules)
   [describe or skip]
```

### 4.5 Refresh Protocol

**On subsequent use (INFERRABLE type):**
1. Check Observations Log for pending observations
2. If observations exist → Present: "Since last use, I noticed these patterns that may differ from your current standards: [observations]. Review now?"
3. If no observations and last verified < 14 days → Proceed without re-confirmation
4. If no observations and last verified >= 14 days → Show summary of current standards, ask: "Are these still accurate?"

### 4.6 Passive Learning Signals

Watch for these signals during any interaction (not just skill invocations):

| Signal | Context Section Affected | Threshold |
|--------|--------------------------|-----------|
| User uses a different annotation than recorded | Controller/Service/Data layer standards | 3+ occurrences |
| User uses different DTO naming pattern | DTO & Model Standards | 3+ occurrences |
| User mentions "we changed" or "we don't do that anymore" | Any section | Immediate (1 occurrence) |
| User introduces a new library (e.g., switches from Mockito to MockK) | Testing Standards | Immediate |
| User corrects Claude's generated code style | Any section | Immediate |

---

## 5. TEMPLATE: DATABASE SCHEMA (SCANNABLE)

### 5.1 Purpose

Captures the current database structure — tables, columns, data types, constraints, relationships, and enums. Essential for skills that generate test data, SQL queries, migration plans, or validate data flow.

### 5.2 Template

```markdown
# Context: Database Schema

## Context Metadata

| Field | Value |
|-------|-------|
| **Context ID** | CTX-DB |
| **Type** | SCANNABLE |
| **Source** | {{e.g., src/main/resources/db/migration/}} |
| **Migration Tool** | {{Flyway / Liquibase / Manual SQL}} |
| **Database Type** | {{PostgreSQL / MySQL / H2 / Oracle}} |
| **Created** | {{DATE}} |
| **Last Verified** | {{DATE}} |
| **Last Updated** | {{DATE}} |
| **Verified By** | Auto-scan + user confirmation |
| **Confidence** | HIGH |
| **Skills That Use This** | {{SKILL_LIST}} |

## Tables

### {{table_name}}

| Column | Type | Nullable | Default | Constraints |
|--------|------|----------|---------|-------------|
| {{column}} | {{type}} | {{YES/NO}} | {{default}} | {{PK / FK → table.col / UNIQUE / CHECK}} |

**Indexes:**
- {{index_name}} ON ({{columns}}) {{UNIQUE/BTREE/etc.}}

**Business Notes:**
- {{Any business context for this table, e.g., "Soft-deleted via deleted_at"}}

### {{next_table_name}}
[Repeat structure]

## Enums & Constrained Values

| Table.Column | Allowed Values | Notes |
|-------------|----------------|-------|
| {{table.column}} | {{value1, value2, value3}} | {{Business meaning}} |

## Foreign Key Relationships

| From Table | From Column | To Table | To Column | On Delete |
|-----------|-------------|----------|-----------|-----------|
| {{table}} | {{column}} | {{table}} | {{column}} | {{CASCADE/RESTRICT/SET NULL}} |

## Key Business Constraints

| Constraint | Table(s) | Description |
|-----------|----------|-------------|
| {{name}} | {{table}} | {{e.g., quantity must be > 0}} |

## Migration History Summary

| Version | Date | Description |
|---------|------|-------------|
| {{V1__}} | {{date}} | {{Initial schema}} |
| {{V2__}} | {{date}} | {{Added orders table}} |
| {{latest}} | {{date}} | {{Latest migration description}} |

## Observations Log

| Date | Observation | Status |
|------|-------------|--------|

## Change Log

| Date | What Changed | How Discovered | Confirmed By |
|------|-------------|----------------|--------------|
| {{DATE}} | Initial creation | Migration file scan | User |
```

### 5.3 Scan Instructions (Claude Code)

```
SCAN PROTOCOL FOR DATABASE SCHEMA

Step 1: Identify migration tool and location
  Check pom.xml / build.gradle for:
    - org.flywaydb:flyway-core → Flyway
    - org.liquibase:liquibase-core → Liquibase
  
  Check application.yml / application.properties for:
    - spring.flyway.locations (default: classpath:db/migration)
    - spring.liquibase.change-log (default: classpath:db/changelog)

  Default scan locations:
    Flyway:    src/main/resources/db/migration/
    Liquibase: src/main/resources/db/changelog/

Step 2: Read migration files in version order
  Flyway naming: V{version}__{description}.sql
    Sort by version number (V1, V2, V3...)
    Read each file sequentially
    
  Liquibase naming: Read master changelog, follow includes
    Parse XML/YAML/SQL changesets in order

Step 3: For each migration file, extract:
  - CREATE TABLE statements → table names, columns, types, constraints
  - ALTER TABLE statements → schema modifications
  - CREATE INDEX statements → index definitions
  - INSERT INTO with enum-like data → constrained value sets
  - CHECK constraints → business rules encoded in DB
  - FOREIGN KEY definitions → relationships

Step 4: Build cumulative schema
  Apply migrations sequentially to build the current state
  Track: which migration introduced each table/column
  Note: any columns that were later ALTERed or DROPped

Step 5: Cross-reference with JPA entities (if applicable)
  Scan: src/main/java/**/*Entity.java or @Entity annotated classes
  Compare: entity field names and types against migration-derived schema
  Note: any discrepancies (field exists in entity but not in migration, or vice versa)

Step 6: Extract enums and constrained values
  From migration CHECK constraints
  From JPA @Enumerated fields in entity classes
  From any seed data INSERT statements

Step 7: Present findings to user for confirmation
  Show: table count, column summary, relationship map
  Ask: "Are there any tables or constraints I missed, or any
        business context for specific tables you'd like to add?"
```

### 5.4 Refresh Protocol

**On subsequent use (SCANNABLE type):**
1. Re-read the migration directory
2. Check if any new migration files exist since `Last Verified` date
3. If new migrations found → Parse them, present changes: "I found {{N}} new migration(s) since last scan: {{descriptions}}. Should I update the schema context?"
4. If no new migrations → Mark as CURRENT, update `Last Verified`

### 5.5 Passive Learning Signals

| Signal | Action | Threshold |
|--------|--------|-----------|
| User mentions a new table or column | Log observation | Immediate |
| User writes SQL referencing a table not in context | Log observation | Immediate |
| User discusses a schema change or migration | Log observation | Immediate |
| New migration file detected in project | Auto-flag for update | Immediate |

---

## 6. TEMPLATE: API CONTRACT (SCANNABLE)

### 6.1 Purpose

Captures the REST API structure — endpoints, HTTP methods, request/response payloads, authentication mechanism, and error responses. Essential for skills that generate test scenarios, test data, API documentation, or implementation plans.

### 6.2 Template

```markdown
# Context: API Contract

## Context Metadata

| Field | Value |
|-------|-------|
| **Context ID** | CTX-API |
| **Type** | SCANNABLE |
| **Source** | {{e.g., src/main/resources/openapi/api.yaml OR controller scan}} |
| **Spec Format** | {{OpenAPI 3.x / Swagger 2.x / Controller-inferred}} |
| **Created** | {{DATE}} |
| **Last Verified** | {{DATE}} |
| **Last Updated** | {{DATE}} |
| **Verified By** | {{Auto-scan / User confirmation}} |
| **Confidence** | {{HIGH/MEDIUM}} |
| **Skills That Use This** | {{SKILL_LIST}} |

## API Overview

| Attribute | Value |
|-----------|-------|
| Base Path | {{e.g., /api/v2}} |
| Authentication | {{e.g., Bearer JWT via Authorization header}} |
| Content Type | {{e.g., application/json}} |
| API Versioning | {{e.g., URL path /v2/ / Header / None}} |

## Endpoints

### {{Resource Group, e.g., Orders}}

| Method | Path | Summary | Auth Required | Request Body | Response |
|--------|------|---------|---------------|--------------|----------|
| {{POST}} | {{/orders}} | {{Create order}} | {{Yes}} | {{CreateOrderRequest}} | {{201: OrderResponse}} |
| {{GET}} | {{/orders/{id}}} | {{Get by ID}} | {{Yes}} | {{—}} | {{200: OrderResponse, 404: ErrorResponse}} |
| {{PUT}} | {{/orders/{id}}} | {{Update order}} | {{Yes}} | {{UpdateOrderRequest}} | {{200: OrderResponse}} |
| {{DELETE}} | {{/orders/{id}}} | {{Delete order}} | {{Yes}} | {{—}} | {{204: No Content}} |
| {{GET}} | {{/orders}} | {{Search orders}} | {{Yes}} | {{—}} | {{200: Page<OrderResponse>}} |

**Query Parameters for GET /orders:**

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| {{customerId}} | {{UUID}} | {{No}} | {{Filter by customer}} |
| {{status}} | {{String}} | {{No}} | {{Filter by status}} |
| {{page}} | {{int}} | {{No}} | {{Page number, default 0}} |
| {{size}} | {{int}} | {{No}} | {{Page size, default 20}} |

### {{Next Resource Group}}
[Repeat structure]

## Request/Response Schemas

### {{CreateOrderRequest}}

| Field | Type | Required | Validation | Notes |
|-------|------|----------|------------|-------|
| {{customerId}} | {{UUID}} | {{Yes}} | {{Must exist in customers table}} | |
| {{items}} | {{List<OrderItemRequest>}} | {{Yes}} | {{Min 1, Max 50}} | |
| {{notes}} | {{String}} | {{No}} | {{Max 500 chars}} | |

### {{OrderResponse}}

| Field | Type | Nullable | Notes |
|-------|------|----------|-------|
| {{id}} | {{UUID}} | {{No}} | {{System generated}} |
| {{customerId}} | {{UUID}} | {{No}} | |
| {{totalAmount}} | {{BigDecimal}} | {{No}} | {{Calculated server-side}} |
| {{status}} | {{String}} | {{No}} | {{Enum: DRAFT, CONFIRMED, SHIPPED, DELIVERED, CANCELLED}} |
| {{createdAt}} | {{ISO 8601}} | {{No}} | |

### {{ErrorResponse}}

| Field | Type | Notes |
|-------|------|-------|
| {{timestamp}} | {{ISO 8601}} | |
| {{status}} | {{int}} | {{HTTP status code}} |
| {{error}} | {{String}} | {{Error category}} |
| {{message}} | {{String}} | {{Human-readable message}} |
| {{path}} | {{String}} | {{Request path}} |

## Custom Headers

| Header | Direction | Required | Purpose |
|--------|-----------|----------|---------|
| {{X-Correlation-Id}} | {{Request/Response}} | {{No}} | {{Distributed tracing}} |
| {{X-Request-Id}} | {{Response}} | {{Always}} | {{Request tracking}} |

## Pagination Pattern

| Attribute | Value |
|-----------|-------|
| Style | {{Spring Page / Cursor-based / Offset}} |
| Default Page Size | {{20}} |
| Max Page Size | {{100}} |
| Response Wrapper | {{Page<T> with totalElements, totalPages, content}} |

## Observations Log

| Date | Observation | Status |
|------|-------------|--------|

## Change Log

| Date | What Changed | How Discovered | Confirmed By |
|------|-------------|----------------|--------------|
| {{DATE}} | Initial creation | {{OpenAPI scan / Controller scan}} | User |
```

### 6.3 Scan Instructions (Claude Code)

```
SCAN PROTOCOL FOR API CONTRACT

Strategy A: OpenAPI/Swagger Spec Exists
  Check for:
    src/main/resources/openapi/*.yaml
    src/main/resources/openapi/*.json
    src/main/resources/swagger/*.yaml
    src/main/resources/static/swagger*.json
    src/main/resources/api-docs*.yaml
  
  If found:
    Parse the spec file
    Extract: paths, methods, parameters, schemas, security definitions
    Map to template structure

Strategy B: No Spec — Infer from Controllers
  Scan: src/main/java/**/*Controller.java
  For each controller:
    Extract: class-level @RequestMapping base path
    Extract: method-level annotations (@GetMapping, @PostMapping, etc.)
    Extract: method parameters (@PathVariable, @RequestParam, @RequestBody)
    Extract: return types (ResponseEntity<T>)
    Extract: @Valid annotations for validation
  
  Scan: src/main/java/**/dto/**/*.java OR *Request.java, *Response.java
  For each DTO:
    Extract: field names, types, validation annotations
    Map: which endpoints use which DTOs

Strategy C: Hybrid
  If partial spec exists, supplement with controller scan
  Note any discrepancies between spec and actual controllers

Cross-reference:
  Check for custom exception handlers → extract error response format
  Check for security configuration → extract authentication method
  Check for pagination configuration → extract pagination pattern
```

### 6.4 Refresh Protocol

**On subsequent use (SCANNABLE type):**
1. Re-scan using the same strategy (A, B, or C) as initial creation
2. Compare endpoint count, payload fields, and response schemas
3. If changes found → Present diff: "Found {{N}} changes: [new endpoints / modified schemas / removed endpoints]. Update?"
4. If no changes → Mark as CURRENT

### 6.5 Passive Learning Signals

| Signal | Action | Threshold |
|--------|--------|-----------|
| User creates a new controller or endpoint | Log observation | Immediate |
| User modifies a DTO (adds/removes/renames fields) | Log observation | Immediate |
| User mentions a new API version or endpoint | Log observation | Immediate |
| User discusses changing authentication method | Log observation | Immediate |

---

## 7. TEMPLATE: BUSINESS RULES (DECLARED)

### 7.1 Purpose

Captures domain-specific business logic, constraints, validation rules, and workflow rules that are not codified in code or configuration. These rules come from requirements documents, stakeholder conversations, and team knowledge.

### 7.2 Template

```markdown
# Context: Business Rules

## Context Metadata

| Field | Value |
|-------|-------|
| **Context ID** | CTX-BIZ |
| **Type** | DECLARED |
| **Source** | User-provided |
| **Created** | {{DATE}} |
| **Last Verified** | {{DATE}} |
| **Last Updated** | {{DATE}} |
| **Verified By** | User confirmation |
| **Confidence** | {{HIGH/MEDIUM}} — user-stated, not code-verified |
| **Skills That Use This** | {{SKILL_LIST}} |

## Domain Entities & Their Rules

### {{Entity Name, e.g., Order}}

**Lifecycle / Status Transitions:**
```
{{e.g.,
DRAFT → CONFIRMED → SHIPPED → DELIVERED
DRAFT → CANCELLED
CONFIRMED → CANCELLED
SHIPPED (cannot be cancelled)
}}
```

**Validation Rules:**

| Rule ID | Rule | Applies When | Exception |
|---------|------|-------------|-----------|
| BIZ-{{NNN}} | {{e.g., Order total must be > $0}} | {{Always}} | {{None}} |
| BIZ-{{NNN}} | {{e.g., Max 50 items per order}} | {{Order creation}} | {{ENTERPRISE customers: 200}} |
| BIZ-{{NNN}} | {{e.g., Cannot modify after SHIPPED}} | {{Order update}} | {{Admin override}} |

**Computed Fields:**

| Field | Calculation | When Computed |
|-------|-------------|---------------|
| {{totalAmount}} | {{Sum of item.quantity * item.unitPrice}} | {{On save}} |
| {{tax}} | {{totalAmount * taxRate based on region}} | {{On save}} |

### {{Next Entity}}
[Repeat structure]

## Cross-Entity Rules

| Rule ID | Rule | Entities Involved |
|---------|------|-------------------|
| BIZ-{{NNN}} | {{e.g., Customer must be ACTIVE to place orders}} | Customer, Order |
| BIZ-{{NNN}} | {{e.g., Refund cannot exceed original order total}} | Order, Refund |

## Authorization Rules

| Action | Required Role/Condition | Notes |
|--------|------------------------|-------|
| {{Create order}} | {{Any authenticated user}} | |
| {{Cancel order}} | {{Order owner OR Admin}} | {{Within 24 hours only for non-Admin}} |
| {{View all orders}} | {{Admin only}} | |

## Time-Based Rules

| Rule | Timeframe | Notes |
|------|-----------|-------|
| {{Refund window}} | {{30 days from delivery}} | {{Configurable per merchant}} |
| {{Abandoned cart cleanup}} | {{24 hours}} | {{Sends reminder email at 12 hours}} |

## Observations Log

| Date | Observation | Status |
|------|-------------|--------|

## Change Log

| Date | What Changed | How Discovered | Confirmed By |
|------|-------------|----------------|--------------|
| {{DATE}} | Initial creation | User interview | User |
```

### 7.3 Question Set (User Interview)

```
QUESTIONS FOR BUSINESS RULES CONTEXT

Please answer the following about your project's business rules.
Skip any that don't apply — I'll mark them as N/A.

CORE DOMAIN:
1. What are the main domain entities in your system?
   (e.g., Order, Customer, Product, Payment, etc.)

2. For each key entity, what are the valid status values
   and allowed transitions?
   (e.g., Order: DRAFT → CONFIRMED → SHIPPED → DELIVERED)

3. What validation rules apply during creation/update of
   each entity? Include any limits, minimums, maximums.
   (e.g., "Max 50 items per order", "Amount must be > $0")

4. Are there any rules that differ by customer type, role,
   or tier? (e.g., "Enterprise customers have higher limits")

CROSS-ENTITY RULES:
5. Are there rules that span multiple entities?
   (e.g., "Customer must be active to place orders",
    "Refund cannot exceed original total")

AUTHORIZATION:
6. What actions require specific roles or permissions?
   (e.g., "Only admins can cancel shipped orders")

TIME-BASED:
7. Are there any time-sensitive rules?
   (e.g., refund windows, expiration periods, auto-cancellation)

COMPUTED VALUES:
8. Are there fields calculated by the system rather than
   provided by the user?
   (e.g., totals, taxes, discount calculations)

ANYTHING ELSE:
9. Any business rules that are important but don't fit the
   categories above?
```

### 7.4 Refresh Protocol

**On subsequent use (DECLARED type):**
1. Check Observations Log for pending observations
2. If observations exist → Present them with current rule values
3. If last verified >= 30 days → Show all current rules and ask: "Are these still accurate? Any rules changed, added, or removed?"
4. For stale contexts (60+ days) → Ask specific questions per rule rather than blanket confirmation

### 7.5 Passive Learning Signals

| Signal | Action | Threshold |
|--------|--------|-----------|
| User mentions a new business rule or constraint | Log observation | Immediate |
| User says "we changed" or "that rule was removed" | Log observation | Immediate |
| User implements validation logic that contradicts stored rules | Log observation | 2+ occurrences |
| User discusses a new entity or status transition | Log observation | Immediate |

---

## 8. TEMPLATE: ENVIRONMENT CONFIGURATION (DECLARED)

### 8.1 Purpose

Captures test environment details — database connections, service URLs, authentication setup, and environment-specific settings needed for test execution, deployment, and integration testing.

### 8.2 Template

```markdown
# Context: Environment Configuration

## Context Metadata

| Field | Value |
|-------|-------|
| **Context ID** | CTX-ENV |
| **Type** | DECLARED |
| **Source** | User-provided |
| **Created** | {{DATE}} |
| **Last Verified** | {{DATE}} |
| **Last Updated** | {{DATE}} |
| **Verified By** | User confirmation |
| **Confidence** | {{HIGH/MEDIUM}} |
| **Skills That Use This** | {{SKILL_LIST}} |

## Local Development Environment

| Attribute | Value |
|-----------|-------|
| Application Port | {{e.g., 8080}} |
| Application Profile | {{e.g., local / dev}} |
| Hot Reload | {{e.g., spring-boot-devtools enabled}} |

## Database (Test/Local)

| Attribute | Value |
|-----------|-------|
| DB Type | {{e.g., H2 in-memory / PostgreSQL via Docker / TestContainers}} |
| JDBC URL | {{e.g., jdbc:h2:mem:testdb / jdbc:postgresql://localhost:5432/testdb}} |
| Schema Setup | {{e.g., Flyway auto-migration on startup}} |
| Test Data | {{e.g., data.sql / @Sql annotations / programmatic setup}} |

## Authentication (Test)

| Attribute | Value |
|-----------|-------|
| Auth Method | {{e.g., JWT Bearer token}} |
| Test User Setup | {{e.g., Static test token in test/resources/ / @WithMockUser}} |
| Test Roles Available | {{e.g., USER, ADMIN, MANAGER}} |

## External Service Dependencies

| Service | Test Strategy | URL/Config |
|---------|---------------|------------|
| {{e.g., Payment Gateway}} | {{WireMock / MockServer / Test sandbox}} | {{e.g., localhost:8089}} |
| {{e.g., Email Service}} | {{Mocked / GreenMail}} | {{e.g., localhost:3025}} |
| {{e.g., File Storage}} | {{Local filesystem / MinIO}} | {{e.g., /tmp/test-storage}} |

## CI/CD Environment

| Attribute | Value |
|-----------|-------|
| CI Platform | {{e.g., GitHub Actions / Jenkins / GitLab CI}} |
| Test Stage | {{e.g., mvn test in CI, mvn verify for integration}} |
| Test Database in CI | {{e.g., PostgreSQL service container / H2}} |

## Useful Commands

| Task | Command |
|------|---------|
| Run unit tests | {{e.g., mvn test}} |
| Run integration tests | {{e.g., mvn verify -P integration-test}} |
| Start local app | {{e.g., mvn spring-boot:run -Dspring.profiles.active=local}} |
| Build without tests | {{e.g., mvn package -DskipTests}} |
| Run specific test class | {{e.g., mvn test -Dtest=OrderServiceTest}} |

## Observations Log

| Date | Observation | Status |
|------|-------------|--------|

## Change Log

| Date | What Changed | How Discovered | Confirmed By |
|------|-------------|----------------|--------------|
| {{DATE}} | Initial creation | User interview | User |
```

### 8.3 Question Set

```
QUESTIONS FOR ENVIRONMENT CONFIGURATION

1. LOCAL DEV: What port does your app run on locally?
   What Spring profile do you use for local development?

2. TEST DATABASE: What database do you use for tests?
   (H2 in-memory / PostgreSQL via Docker / TestContainers / other)
   How is test data set up? (data.sql / @Sql / programmatic)

3. AUTHENTICATION: How does auth work in your tests?
   (Static test tokens / @WithMockUser / disabled for tests)
   What test roles/users are available?

4. EXTERNAL SERVICES: Does your app depend on external services?
   (Payment gateways, email, file storage, other APIs)
   How do you mock them in tests? (WireMock / MockServer / other)

5. CI/CD: What CI platform do you use?
   What commands run your test suite?

6. USEFUL COMMANDS: What are the key Maven/Gradle commands
   your team uses? (test, verify, build, run)
```

### 8.4 Refresh Protocol

**On subsequent use:** Show current values, ask: "Are these still accurate? Any environment changes?" This context rarely changes — 30-day refresh threshold is appropriate.

---

## 9. TEMPLATE: PENDING OBSERVATIONS

### 9.1 Purpose

A shared queue for observations detected by the Passive Learning mechanism. This file is written to by any interaction and read/processed during skill invocations or Skills Updater runs.

### 9.2 Template

```markdown
# Pending Observations

## Summary
- Total pending: {{N}}
- Oldest pending: {{DATE}}
- Contexts affected: {{list of CTX-IDs}}

## Observations

### OBS-{{NNN}}

| Field | Value |
|-------|-------|
| **Date** | {{DATE}} |
| **Context Affected** | {{CTX-ID (filename)}} |
| **Section** | {{Section within the context file}} |
| **Current Value** | {{What the context currently says}} |
| **Observed** | {{What was observed that differs}} |
| **Frequency** | {{Number of occurrences}} |
| **Confidence** | {{HIGH / MEDIUM / LOW}} |
| **Signal Type** | {{Explicit correction / Repeated pattern / Source file change / New information}} |

**Recommendation:** {{Suggested change}}

---

### OBS-{{NNN}}
[Repeat structure]
```

### 9.3 Management Rules

- Maximum 30 entries. If exceeded, flag for immediate Skills Updater run
- Processed observations (APPLIED or REJECTED) are removed and their outcome recorded in the affected context file's Change Log
- Observations older than 60 days without processing are auto-flagged as HIGH priority

---

## 10. TEMPLATE: CUSTOM CONTEXT (GENERIC)

### 10.1 Purpose

When a skill requires project-specific context that does not fit any standard template, use this generic template. Common use cases include: project-specific domain terminology, third-party integration details, regulatory compliance requirements, team-specific workflow rules.

### 10.2 Template

```markdown
# Context: {{Descriptive Name}}

## Context Metadata

| Field | Value |
|-------|-------|
| **Context ID** | CTX-{{CODE}} |
| **Type** | {{SCANNABLE / INFERRABLE / DECLARED}} |
| **Source** | {{Source description}} |
| **Created** | {{DATE}} |
| **Last Verified** | {{DATE}} |
| **Last Updated** | {{DATE}} |
| **Verified By** | {{Method}} |
| **Confidence** | {{HIGH / MEDIUM / LOW}} |
| **Skills That Use This** | {{SKILL_LIST}} |

## Content

### {{Section 1}}
{{Structured content}}

### {{Section 2}}
{{Structured content}}

## Observations Log

| Date | Observation | Status |
|------|-------------|--------|

## Change Log

| Date | What Changed | How Discovered | Confirmed By |
|------|-------------|----------------|--------------|
| {{DATE}} | Initial creation | {{Method}} | {{Who}} |
```

### 10.3 Custom Context Creation Checklist

When creating a custom context type:
- [ ] Assign a unique CTX-ID that does not conflict with standard IDs
- [ ] Determine the correct Type (SCANNABLE/INFERRABLE/DECLARED)
- [ ] Define scan instructions (for SCANNABLE) or questions (for DECLARED/INFERRABLE)
- [ ] Define refresh protocol — how will freshness be checked?
- [ ] Define passive learning signals — what observations should Claude watch for?
- [ ] Add to context manifest
- [ ] Ensure the creating skill's `## Required Contexts` table references this context

---

## 11. SCAN INSTRUCTIONS REFERENCE

### 11.1 Java/Spring Boot Scan Map

This reference shows where to find each type of information in a standard Java/Spring Boot project.

| Information Needed | Where to Look | How to Extract |
|-------------------|---------------|----------------|
| Java version | pom.xml: `<java.version>` or `<maven.compiler.source>` | Parse XML |
| Spring Boot version | pom.xml: `<parent><version>` for spring-boot-starter-parent | Parse XML |
| Dependencies | pom.xml: `<dependencies>` section | Parse XML, list all artifactIds |
| DB migration files | `src/main/resources/db/migration/` (Flyway) or `src/main/resources/db/changelog/` (Liquibase) | List directory, read SQL/XML files |
| OpenAPI spec | `src/main/resources/openapi/` or `src/main/resources/static/` | Find *.yaml or *.json files |
| Application config | `src/main/resources/application.yml` or `application.properties` | Parse YAML/properties |
| Controller classes | `src/main/java/**/*Controller.java` | Read class annotations, method signatures |
| Service classes | `src/main/java/**/*Service.java` | Read class annotations, method signatures |
| Entity classes | `src/main/java/**/*Entity.java` or `@Entity` annotated | Read field definitions, annotations |
| DTO classes | `src/main/java/**/dto/**/*.java` or `*Request.java`, `*Response.java` | Read field definitions, naming patterns |
| Exception handlers | `src/main/java/**/*Advice.java` or `*ExceptionHandler.java` | Read handler methods, error response format |
| Test classes | `src/test/java/**/*Test.java` | Read test framework, assertion imports, naming |
| Logging config | `src/main/resources/logback*.xml` | Parse XML for appender patterns |
| Security config | `src/main/java/**/*SecurityConfig.java` or `*WebSecurityConfig.java` | Read security chain configuration |

### 11.2 Scan Priority Order

When scanning a new project, scan in this order for efficiency (each scan informs the next):

1. **pom.xml / build.gradle** → Framework, version, dependencies (informs everything else)
2. **Application config** → Database type, profiles, external service URLs
3. **Migration files** → Database schema (informs entity validation)
4. **Entity classes** → Domain model (cross-reference with migrations)
5. **Controllers** → API surface (informs test scenario generation)
6. **DTOs** → Request/Response structure (informs test data generation)
7. **Services** → Business logic patterns
8. **Tests** → Test patterns and conventions
9. **Exception handlers** → Error response format
10. **Logging/Security config** → Operational patterns

---

## 12. QUESTION BANK REFERENCE

### 12.1 Master Question Bank

This is the consolidated list of all questions across all context types. During context generation, Claude selects and batches only the questions relevant to the missing contexts.

**Framework & Structure (answers populate CTX-STD):**
1. What Java version and Spring Boot version are you using?
2. Do you use Maven or Gradle?
3. What is your base package structure?

**Database (answers populate CTX-DB):**
4. What database do you use in production? (PostgreSQL, MySQL, Oracle, etc.)
5. Do you use Flyway or Liquibase for migrations?
6. Where are your migration files located?

**API (answers populate CTX-API):**
7. Do you have an OpenAPI/Swagger specification file?
8. What is your API base path? (e.g., /api/v2)
9. What authentication method does your API use?
10. Do you use pagination? What style?

**Business Rules (answers populate CTX-BIZ):**
11. What are the main domain entities in your system?
12. What are the valid status transitions for key entities?
13. What validation rules apply during entity creation/update?
14. Are there rules that differ by customer type or role?
15. Are there cross-entity rules?
16. Are there time-sensitive rules (expiration, windows)?

**Environment (answers populate CTX-ENV):**
17. What database do you use for tests?
18. How does authentication work in your tests?
19. What external services does your app depend on, and how are they mocked?
20. What CI platform do you use?
21. What are the key build/test commands?

### 12.2 Batching Rules

- If ALL contexts are missing (first use): Ask questions 1-21 in a single batch, grouped by section
- If only specific contexts are missing: Ask only the questions for those contexts
- If contexts exist but are stale: Show current values and ask confirmation questions instead of full questions

---

## 13. INTEGRATION WITH SKILL CREATOR PRO

### 13.1 How This Document Integrates with PROMPT-001

When creating a new skill using the Skill Creator Pro workflow [PROMPT-001], the new Phase 2.5 (Context Dependency Analysis) as defined in [ARCH-001, Section 8.1.1] uses this document as follows:

**During Phase 2.5, Step 3 (Define Required Contexts Table):**
- For each identified context dependency, reference this document to determine if a standard template exists
- If a standard template exists (Sections 4-8) → Use that template. Do not create a custom one
- If no standard template exists → Use the Generic template (Section 10) and create a custom context type

**During Phase 2.5, Step 4 (Define Context Creation Questions):**
- For SCANNABLE contexts → Copy the relevant Scan Instructions from Section 11
- For DECLARED contexts → Copy the relevant questions from the Question Bank (Section 12)
- For INFERRABLE contexts → Copy the relevant scan protocol + confirmation questions

**During Phase 5 (Asset & Script Generation):**
- For each required context, generate a context template file in `context-templates/`
- The template file is a copy of the relevant template from this document with placeholders intact
- This template file ships with the skill package and is used when context files are created on first use

### 13.2 Updated Skill Folder Structure

```
skill-name/
├── SKILL.md                          # Workflow + ## Required Contexts
├── scripts/                          # Deterministic processing
├── references/                       # Stable reference docs
├── assets/                           # Templates, static files
└── context-templates/                # Context file templates
    ├── README.md                     # List of templates and their purpose
    ├── project-standards.ctx.template.md     # From ARCH-002, Section 4
    ├── db-schema.ctx.template.md            # From ARCH-002, Section 5
    ├── api-contract.ctx.template.md         # From ARCH-002, Section 6
    ├── business-rules.ctx.template.md       # From ARCH-002, Section 7
    └── environment-config.ctx.template.md   # From ARCH-002, Section 8
```

Only include templates for contexts this skill actually requires. Not every skill needs all five standard contexts.

---

## RELATED DOCUMENTS

| Reference | Document | Section | Topic |
|-----------|----------|---------|-------|
| REF-001 | ARCH-001 Skill Architecture | Section 2 | Skill Kernel + Living Context Model |
| REF-002 | ARCH-001 Skill Architecture | Section 3 | Context File Type System (SCANNABLE/INFERRABLE/DECLARED) |
| REF-003 | ARCH-001 Skill Architecture | Section 4 | Context File Format Standard |
| REF-004 | ARCH-001 Skill Architecture | Section 5 | Context Resolution Lifecycle |
| REF-005 | ARCH-001 Skill Architecture | Section 6 | Passive Learning Mechanism |
| REF-006 | ARCH-001 Skill Architecture | Section 8 | Integration with Skill Creator Pro |
| REF-007 | PROMPT-001 Skill Creator Pro | Phase 2.5 | Context Dependency Analysis phase |
| REF-008 | RULES-001 Documentation Standards | All | Document formatting, naming, revision tracking |
| REF-009 | Truth & Verification Standards | Section 2 | Verification mode selection for context content |

---

## SOURCES & REFERENCES

### Anthropic Official Documentation

| Source | Title | Date | Relevance |
|--------|-------|------|-----------|
| Anthropic Platform Docs | Skill Authoring Best Practices | Current | Concise skills, progressive disclosure, description writing |
| Anthropic Engineering | Equipping Agents for the Real World with Agent Skills | Oct 2025 | Skill structure, progressive disclosure, bundled resources |
| Anthropic | Claude Code Skills Documentation | Current | Skill invocation, frontmatter, context modes |
| agentskills.io | Agent Skills Open Standard | Dec 2025 | Cross-platform skill format specification |

### Community & Industry Sources

| Source | Title | Date | Relevance |
|--------|-------|------|-----------|
| Piotr Minkowski | Claude Code Template for Spring Boot | Mar 2026 | Java/Spring Boot skill structure, metadata tags, scan patterns |
| decebals/claude-code-java (GitHub) | Reusable AI Development Infrastructure for Java Projects | Current | 18 Java-specific skills, testing strategy, design principles |
| MindStudio | Self-Learning Claude Code Skill with Learnings.md | Mar 2026 | Self-updating context patterns, append-only learning files |
| Matthew Groff | Implementing CLAUDE.md and Agent Skills | 2026 | Agent guides, living documents, PR feedback as context signals |

### Technical References

| Source | Title | Date | Relevance |
|--------|-------|------|-----------|
| Baeldung | Database Migrations with Flyway | Jan 2026 | Flyway migration structure, naming conventions, Spring Boot integration |
| JetBrains Blog | How to Use Flyway for Database Migrations in Spring Boot | Dec 2025 | Migration file locations, versioning, schema history |
| Spring Projects (GitHub) | Spring Boot 4.0 Migration Guide | Current | Starter POM changes, modularization, dependency updates |

---

## FURTHER READING

### For Deeper Research:
1. **OpenAPI Specification** — swagger.io/specification — Understanding OpenAPI 3.x for API contract extraction
2. **Flyway Documentation** — documentation.red-gate.com/flyway — Migration file naming, versioning, and configuration
3. **Liquibase Documentation** — docs.liquibase.com — Changelog format, changeset structure
4. **Spring Boot Testing Guide** — docs.spring.io — @SpringBootTest, TestContainers, MockMvc patterns

### Questions for Future Research:
- [ ] Should we add templates for frontend contexts (React components, CSS conventions)?
- [ ] Can scan instructions be converted to executable scripts for faster extraction?
- [ ] Should context templates be versioned independently of skills?
- [ ] How should context templates handle multi-module Maven/Gradle projects?
- [ ] Can we auto-detect the migration tool and OpenAPI spec location without user input?

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-03-28 | Initial | Document created with 7 standard templates, scan instructions, question bank, and integration guide | Deliverable 2 of the Living Context Framework — operational templates for context generation |
| 2.0 | 2026-04-05 | Major | Added §§11–28: 16 Project Brain document templates (CLAUDE.md, Contracts.md, Work Audit.md, Application Understanding/Workflows/Features, Data Models, Users, context/, feature-level docs, INGESTION-LOG.md). Added Q22–Q34 to question bank. Added docs/ naming conventions. Deprecated pending-observations template. | Java Backend Skill Pack v3.0.0 upgrade |

---



---

## 14. PROJECT BRAIN TEMPLATES — OVERVIEW

This section and sections 15–30 contain the complete templates for every document in the `docs/` Project Brain. All templates comply with the ai-documentation skill's conventions.

**Minimum compliance for every docs/ file:**
- Extended YAML frontmatter (document_id, title, version, status, parent_document, template_version_used)
- 🤖 AI Summary block immediately after frontmatter
- [TYPE: ...] tag on every ## section
- Revision History at document foot — every row must include the Work-ID
- Cross-references using format: `[RELATIONSHIP] → [Document, Section] — reason`

**Template coverage matrix:**

| Template | Created by | Used by |
|----------|-----------|---------|
| CLAUDE.md [TMPL-004A] | project-understanding | All agents |
| Contracts.md [TMPL-002 + TMPL-005] | project-understanding | All skills (MANDATORY read) |
| Work Audit.md [TMPL-004C] | project-understanding | All skills (append) |
| Application Understanding.md [TMPL-002] | project-understanding | code-analyzer, implementation-planner |
| Application Workflows.md [TMPL-003] | project-understanding | implementation-planner |
| Application Features.md [TMPL-002] | project-understanding | implementation-planner (update) |
| Data Models.md [TMPL-002] | project-understanding | test-data-generator, code-analyzer |
| Users.md [TMPL-002] | project-understanding | test-scenario-generator |
| context/api-contract.md [TMPL-002] | project-understanding | test-scenario-generator, test-data-generator, bug-analyzer |
| context/environment-config.md [TMPL-002] | project-understanding | test-data-generator, test-script-generator |
| context/context-manifest.md [TMPL-002] | project-understanding | All skills (freshness checks) |
| Feature Understanding.md [TMPL-002] | implementation-planner | test-scenario-generator |
| Feature Work Audit.md [TMPL-004C] | implementation-planner | All skills (feature-scoped append) |
| Tests Scenarios.md [TMPL-003] | test-scenario-generator | test-data-generator, test-script-generator |
| Test Data Setup.md [TMPL-003] | test-data-generator | test-script-generator |
| INGESTION-LOG.md [TMPL-002] | project-understanding | project-understanding (UNPROCESSED scan) |

**docs/ naming conventions:**

Files in the Project Brain use plain `.md` naming (not `.ctx.md`). Feature folder names use lowercase-hyphenated convention: `order-management`, `payment-processing`.

---

## 15. TEMPLATE: CLAUDE.md — AGENT ENTRY POINT [TMPL-004A]

**Purpose:** First file any agent reads. Orients agents to the project.
**TMPL type:** TMPL-004A — agent reads only, never writes.
**Location:** Project root (not inside docs/).
**Created by:** project-understanding.

The full template is included in `project-understanding/context-templates/CLAUDE.md.template.md`.

**Key sections:**
- What This Project Does [TYPE: CONTEXT]
- Before You Write Any Code [TYPE: PROCEDURE] — mandatory 4-step checklist
- Where to Find Things [TYPE: REFERENCE] — table of docs/ → purpose
- What You MUST NOT Do [TYPE: CONSTRAINT] — hard stops
- How to Log Your Work [TYPE: PROCEDURE] — Work-ID schema
- When to Stop and Ask [TYPE: CONSTRAINT] — escalation conditions
- Revision History

---

## 16. TEMPLATE: Contracts.md — ARCHITECTURAL CONSTITUTION [TMPL-002 + TMPL-005]

**Purpose:** Defines the "taste" of the project — architectural decisions and hard stops.
**TMPL type:** TMPL-002 (Technical Reference) wrapping inline TMPL-005 (Decision Record) blocks.
**Amendment policy:** Human approval required always. Agents propose via Work Audit.md §6.2 only.
**Created by:** project-understanding (seeded from Q22–34 + code scan).

The full template is included in `project-understanding/context-templates/Contracts.md.template.md`.

**ADR sections (each follows TMPL-005 format: Decision / Rationale / Rule / Anti-pattern / Applies to):**
- §1 Inviolable Rules (Hard Stops)
- §2 DTO & Data Transfer Policy [ADR-001]
- §3 Transaction Management [ADR-002]
- §4 Error Handling Conventions [ADR-003]
- §5 Logging Conventions [ADR-004]
- §6 Security Enforcement [ADR-005]
- §7 Design Patterns in Use [ADR-006]
- §8 Anti-Patterns (Forbidden) [ADR-007]
- §9 Test Conventions [ADR-008]
- §10 Audit Fields [ADR-009]
- §11 Technology Constraints [ADR-010]
- §12 Configuration Management [ADR-011]

---

## 17. TEMPLATE: Work Audit.md — PROJECT PROVENANCE LOG [TMPL-004C]

**Purpose:** Cross-session living memory. Provenance trail for all work.
**TMPL type:** TMPL-004C (Living Context Document).
**Agent write sections:** §3.2, §6.2, §7 ONLY.
**Replaces:** pending-observations.ctx.md.
**Created by:** project-understanding.

The full template is included in `project-understanding/context-templates/Work-Audit.md.template.md`.

**Key sections:**
- §1 Document Identity [TYPE: REFERENCE] — human-authored, agent must not modify
- §2 Stable Project Knowledge [TYPE: REFERENCE] — project facts, governing standards
- §3.1 Current Phase [TYPE: REFERENCE] — milestones (human-authored)
- §3.2 Recent Changes — **AGENT-WRITABLE**, reverse-chrono; what skills read at pre-flight
- §4 Behavioral Configuration [TYPE: CONSTRAINT] — standing constraints, escalation threshold
- §5 Context Dependencies [TYPE: REFERENCE] — all docs/ files + staleness dates
- §6.1 Validated Learnings — human-confirmed observations
- §6.2 Pending Observations — **AGENT-WRITABLE** (append-only); max 20 before human review
- §6.3 Rejected Observations
- §7 Session Log — **AGENT-WRITABLE** (append-only); one WRK-ID row per skill run
- §8 Open Questions
- §9 Revision History

**Work-ID schema:** `WRK-YYYY-MM-DD-NNN`

---

## 18. TEMPLATE: Application Understanding.md [TMPL-002]

**Purpose:** Business overview + domain rules. Absorbs CTX-BIZ content.
**Created by:** project-understanding. Updated by: code-analyzer.

**Key sections:**
- §1 Business Overview [TYPE: CONTEXT]
- §2 Technical Architecture [TYPE: REFERENCE]
- §3 Domain Model [TYPE: REFERENCE]
- §4 Business Rules Summary [TYPE: REFERENCE] — authoritative source for business rules
- §5 Integration Points [TYPE: REFERENCE]
- Cross-References [TYPE: REFERENCE]
- Revision History

---

## 19. TEMPLATE: Application Workflows.md [TMPL-003]

**Purpose:** End-to-end process flows from user intent to backend logic.
**Created by:** project-understanding.

**Key sections:**
- §1 Workflow Overview [TYPE: CONTEXT]
- §2–N [Workflow Name] [TYPE: PROCEDURE] — one section per major workflow
- Error & Exception Flows [TYPE: PROCEDURE]
- Revision History

---

## 20. TEMPLATE: Application Features.md [TMPL-002]

**Purpose:** Feature registry — one entry per feature with status, folder link, Work-ID.
**Created by:** project-understanding. Updated by: implementation-planner.

**Feature Registry table:**
```markdown
| Feature | Status | Feature Folder | Work-ID (created) | Owner |
|---------|--------|---------------|-------------------|-------|
| Order Management | Active | docs/features/order-management/ | WRK-2026-04-05-001 | — |
```

---

## 21. TEMPLATE: Data Models.md [TMPL-002]

**Purpose:** Entities, DTOs, schema, audit fields. Absorbs CTX-DB.
**Created by:** project-understanding. Updated by: code-analyzer.
**Critical field:** Audit Fields — lists @CreatedDate etc. These are NEVER included in INSERT statements.

**Key sections:**
- §1 Entity Overview [TYPE: REFERENCE] — entity list + relationships
- §2 Entity Definitions [TYPE: REFERENCE] — per entity: table, columns, audit fields, soft delete
- §3 DTO Catalog [TYPE: REFERENCE]
- §4 Schema Conventions [TYPE: REFERENCE] — migration tool, naming, soft delete pattern
- §5 Migration History Summary [TYPE: REFERENCE]
- Revision History

**Split rule:** When this file exceeds ~1,500 words, split by domain: `Data Models - Core.md`, `Data Models - Orders.md` etc.

---

## 22. TEMPLATE: Users.md [TMPL-002]

**Purpose:** User roles, permissions, and behavioral rules.
**Created by:** project-understanding.

**Key sections:**
- §1 User Role Overview [TYPE: REFERENCE]
- §2 Role Definitions [TYPE: REFERENCE] — per role: who, can do, cannot do
- §3 Permission Matrix [TYPE: REFERENCE] — endpoints × roles
- §4 Behavioral Notes [TYPE: REFERENCE] — edge cases, test implications
- Revision History

---

## 23. TEMPLATE: context/api-contract.md [TMPL-002]

**Purpose:** Compact API surface reference. Absorbs CTX-API.
**Created by:** project-understanding. Updated by: project-understanding Mode B.
**Staleness threshold:** 7 days.

The full template is included in `project-understanding/context-templates/context-api-contract.md.template.md`.

**Key sections:**
- §1 API Overview [TYPE: REFERENCE] — base path, auth, versioning, pagination
- §2 Endpoint Catalog [TYPE: REFERENCE] — grouped by resource
- §3 Common Patterns [TYPE: REFERENCE] — error format, pagination params, date format
- §4 Changelog [TYPE: REFERENCE]
- Revision History

---

## 24. TEMPLATE: context/environment-config.md [TMPL-002]

**Purpose:** Test and CI environment configuration. Absorbs CTX-ENV.
**Critical field:** `test_data_base_date` — used by test-data-generator for all timestamp values.
**Created by:** project-understanding.

The full template is included in `project-understanding/context-templates/context-environment-config.md.template.md`.

**Key sections:**
- §1 Test Database [TYPE: REFERENCE] — type, mode, test_data_base_date
- §2 Authentication in Tests [TYPE: REFERENCE] — @WithMockUser / static token
- §3 External Service Mocks [TYPE: REFERENCE]
- §4 CI Environment [TYPE: REFERENCE]
- §5 Build Commands [TYPE: REFERENCE]
- Revision History

---

## 25. TEMPLATE: context/context-manifest.md [TMPL-002]

**Purpose:** Machine-readable freshness index for all docs/ files. Absorbs CTX-MANIFEST.
**Created by:** project-understanding. Updated by: all skills (per run).

The full template is included in `project-understanding/context-templates/context-context-manifest.md.template.md`.

**Context File Registry table:**
```markdown
| File | Last Updated | Updated By (Work-ID) | Staleness Threshold | Status |
|------|-------------|----------------------|---------------------|--------|
| docs/Contracts.md | <DATE> | WRK-2026-04-05-001 | 30 days | ✅ Current |
```

**Staleness thresholds:**
- api-contract.md: 7 days
- Work Audit.md: living (no expiry)
- Contracts.md, Data Models.md: 30 days
- environment-config.md: 90 days

---

## 26. TEMPLATE: features/<n>/Feature Understanding.md [TMPL-002]

**Purpose:** Complete understanding of one feature — business flow, users, DB tables, rules.
**Created by:** implementation-planner.
**Read by:** test-scenario-generator for richer context.

The full template is included in `project-understanding/context-templates/Feature-Understanding.md.template.md`.

**Key sections:**
- §1 Feature Overview [TYPE: CONTEXT]
- §2 Business Flows [TYPE: PROCEDURE]
- §3 Domain Entities Involved [TYPE: REFERENCE]
- §4 Business Rules Specific to This Feature [TYPE: REFERENCE]
- §5 API Surface [TYPE: REFERENCE]
- §6 Integration Dependencies [TYPE: REFERENCE]
- Revision History

---

## 27. TEMPLATE: features/<n>/Feature Work Audit.md [TMPL-004C]

**Purpose:** Feature-scoped living context. Same structure as Work Audit.md but scoped to one feature.
**Created by:** implementation-planner.
**Same Work-IDs as docs/Work Audit.md** — both get the same entry for feature-scoped work.

The full template is included in `project-understanding/context-templates/Feature-Work-Audit.md.template.md`.

Structure is identical to Work Audit.md (§17 above) with these differences:
- `context_domain`: set to the feature name
- §2 Stable Knowledge covers feature-specific facts
- §7 Session Log contains only entries for work on this feature

---

## 28. TEMPLATE: features/<n>/Tests Scenarios.md [TMPL-003]

**Purpose:** Structured TSC document — test scenario catalog for one feature.
**Created by:** test-scenario-generator.
**Consumed by:** test-data-generator, test-script-generator.

**Frontmatter:**
```yaml
document_id: "tsc-<feature-name>-001"
title: "<FEATURE_NAME> — Test Scenarios"
template_version_used: "TMPL-003 v1.1"
```

**Key sections:**
- Scenario Catalog [TYPE: REFERENCE] — summary table: TSC-ID, Endpoint, Category, Priority
- TSC-NNN — [Scenario Name] [TYPE: PROCEDURE] — per scenario: Preconditions, Request Payload, Expected Response, Expected DB State, Data Requirements, Cleanup Steps, Notes

---

## 29. TEMPLATE: features/<n>/Test Data Setup.md [TMPL-003]

**Purpose:** SQL setup/verify/cleanup scripts and JSON payloads per test scenario.
**Created by:** test-data-generator.
**Consumed by:** test-script-generator.

**Frontmatter:**
```yaml
document_id: "tda-<feature-name>-001"
title: "<FEATURE_NAME> — Test Data Setup"
template_version_used: "TMPL-003 v1.1"
```

**Per-scenario structure (grouped by TSC-ID):**
- Setup SQL [TYPE: PROCEDURE]
- JSON Request Payload [TYPE: PROCEDURE]
- Verification SQL [TYPE: PROCEDURE]
- Cleanup SQL [TYPE: PROCEDURE]

---

## 30. TEMPLATE: archives/INGESTION-LOG.md [TMPL-002]

**Purpose:** Tracks every file in docs/archives/ — processing status, Work-ID, docs updated.
**Created by:** project-understanding (Mode A or C).

The full template is included in `project-understanding/context-templates/INGESTION-LOG.md.template.md`.

**Ingestion Log table:**
```markdown
| Work-ID | Received Date | Archive File | Source Type | Processed By | Docs Updated | Status |
|---------|--------------|--------------|-------------|--------------|--------------|--------|
```

**Status codes:** ✅ PROCESSED | ⏳ UNPROCESSED | ⚠️ PARTIAL | 🗄️ SUPERSEDED

**File naming convention:** `[YYYY-MM-DD]_[source-type]_[description].[ext]`

Source types: `transcript`, `confluence`, `spec`, `email`, `design`, `vendor`, `handover`, `payload`, `wsdl`, `postman`, `schema`

---

## 31. Q22–Q34: CONTRACTS.MD QUESTION BANK

Add these questions to the question bank (Section 12) as a second batch presented by project-understanding during Mode A or Mode C. Pre-fill answers where code scan evidence is clear (>80% consistency). Ask only for unanswered or ambiguous items.

```
ARCHITECTURAL CONTRACTS — to seed docs/Contracts.md:
(Skip any that don't apply — mark as INCOMPLETE)

22. DTO policy: Do you use dedicated DTOs for all request/response objects,
    or do you expose entity objects directly in some cases?

23. Transaction management: Where does @Transactional live — service layer,
    repository layer, or both? Any readOnly conventions?

24. Logging framework and conventions: Which framework (SLF4J/Logback, Log4j2)?
    What gets logged at INFO vs DEBUG vs WARN? Any MDC fields in use?

25. Error handling strategy: Do you use a GlobalExceptionHandler (@ControllerAdvice)?
    What is your exception class hierarchy?

26. Pagination: Are all list endpoints paginated? Which library? (Spring Pageable, custom?)
    What is the default page size?

27. DTO mapping approach: MapStruct, ModelMapper, or manual mapping?
    Any naming conventions for mapper classes?

28. Soft delete or hard delete? If soft delete, how is it implemented?
    (@SQLRestriction, @Where, custom flag?)

29. Design patterns: Are any patterns mandated? (Repository, Factory, Strategy,
    Decorator?) Where are they applied in the project?

30. Audit fields: Which Spring Data audit annotations are in use?
    (@CreatedDate, @LastModifiedDate, @CreatedBy, @LastModifiedBy, @Version?)
    These must be excluded from test INSERT statements.

31. Forbidden patterns: Are there any patterns currently in the codebase that
    you want to stop replicating? (e.g., business logic in controllers, direct
    entity exposure?)

32. Technology constraints: Are any library versions pinned? Any banned libraries?
    Any server or platform version constraints?

33. Security enforcement at controller layer: @PreAuthorize on service methods,
    security filter chain, or custom annotations? How is auth handled in tests?

34. Configuration management: Spring profiles? Vault? Environment variables for
    secrets? How are environment-specific configs managed?
```

**Batching rule for Q22–34:** Present as a second batch after Q1–21. Pre-fill any item where code scan produced clear evidence. Only ask for items that are unanswered or where evidence was ambiguous (below 80% consistency).

---

**END OF DOCUMENT**

*This document is the operational companion to ARCH-001 v2.0. It provides the templates and instructions Claude uses when creating and maintaining the Project Brain and legacy context files. Every skill built under the Living Context Framework references these templates.*
