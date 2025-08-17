<img src="https://r2cdn.perplexity.ai/pplx-full-logo-primary-dark%402x.png" style="height:64px;margin-right:32px"/>

# give me a elaborated and detailed prompt library for this step and use all the refreence of md files from this step and all previous steps. the idea is to build a detailed prompet library wihth very good prompts that used teh information form templates and md fles to generate best regression tests

Certainly! Here’s a **highly detailed and practical Prompt Library for Step 6: Regression Tests**.
This library provides actionable, copy-paste-ready prompts designed expressly for Copilot/LLM/command-line/automation usage.
Each prompt reference explicitly connects to your `.md` templates and artifacts from Step 6 and all previous steps, ensuring that regression test generation is *systematic, comprehensive, and fully traceable*.

***

# Step 6 – Regression Tests: Prompt Library

| Prompt Name | What It Does / Intended Use | References (MD files/templates) | Where to Use / Section |
| :-- | :-- | :-- | :-- |
| **List Regression Candidates** | Lists all workflows, endpoints, services, and entities that must have regression tests | FunctionalWorkflowTemplate.md<br>DataModelTemplate.md<br>API Test Targets Template (Step 5)<br>IntegrationTestTargetsTemplate.md (Step 4)<br>UnitTestTargetsTemplate.md (Step 3) | 3.1, 4.1 |
| **Build Regression Suite Table** | Auto-generates a regression suite tracking table with test classes, mapped workflow, status, owner, last run, linked defect/feature | RegressionTestSuite.md<br>UnitTestTargetsTemplate.md (Step 3)<br>IntegrationTestTargetsTemplate.md (Step 4)<br>API Test Targets Template.md (Step 5)<br>Defect Log, FunctionalWorkflowTemplate.md | 3.2, 4.1, coverage mapping |
| **Find Missing Regression Tests** | Identifies critical business workflows/entity endpoints still lacking regression test coverage | RegressionTestSuite.md<br>FunctionalWorkflowTemplate.md<br>DataModelTemplate.md<br>Coverage reports from Steps 3–5 | 3.5, 5 |
| **Filter by Priority Prompt** | Filters regression candidates by "High Priority" (critical/bug-prone/goal-driven) | TeamGoalsTemplate.md<br>Defect Log<br>RegressionTestSuite.md | 3.1, 3.2, 4.1, backlog |
| **Collate Past Defects for Regression** | Lists all fixed bugs (from Defect Log) with missing or present regression tests, to force coverage for every fix | Defect Log<br>RegressionTestSuite.md<br>Unit/Integration/API Targets Templates | 3.2, 4.1, 4.3 |
| **Generate Regression Test Scenario** | Produces a full markdown scenario (using Regression Test Scenario Template and references to workflow/entity/API/defect) | Regression Test Scenario Template (Step 6)<br>FunctionalWorkflowTemplate.md<br>DataModelTemplate.md<br>API Test Targets Template (Step 5)<br>IntegrationTestTargetsTemplate.md (Step 4)<br>Defect Log | 4.3, scenario docs |
| **Expand Regression for Feature Change** | Given a new or changed workflow/feature, suggests additional regression tests required | FunctionalWorkflowTemplate.md<br>RegressionTestSuite.md<br>API Test Targets Template<br>IntegrationTestTargetsTemplate.md | 3.6, backlog, maintenance |
| **Update Flaky Test List Prompt** | Automates syncing/changing the "Flaky?" and "Last Run Status" columns after suite run | RegressionTestSuite.md (Step 6) | 3.5, 4.1 |
| **Tag Issue/Change to Test** | For each regression case, links to relevant defect/feature/user story (traceable in table) | RegressionTestSuite.md<br>Defect Log<br>FunctionalWorkflowTemplate.md | 4.1, 4.3, traceability |
| **Summarize Regression Coverage Prompt** | Generates an executive markdown summary: % workflows/entities/endpoints/defect areas covered by regression, highlighting trends/gaps | RegressionTestSuite.md<br>FunctionalWorkflowTemplate.md<br>unit/integration/api-test-coverage.md | 3.5, 4.1, reporting |
| **Generate Automated Run Config Prompt** | Produces a CI runner config or script (e.g., GitHub Actions YAML, Maven/Gradle command) to run only the regression suite | environment-setup.md<br>RegressionTestSuite.md | 3.3, automation |
| **Show Workflow-Linked Regression Map** | For each workflow, shows which unit/integration/api/defect regression tests exist (visual map or table) | FunctionalWorkflowTemplate.md<br>RegressionTestSuite.md<br>all Targets Templates<br>Defect Log | 4.1, 4.3, audit/compliance |


***

## Example Prompts (Ready for Copilot/LLM, in Markdown)


***

### 1. **List Regression Candidates**

> “Using `FunctionalWorkflowTemplate.md`, `DataModelTemplate.md`, `API Test Targets Template`, and `IntegrationTestTargetsTemplate.md`, generate a list of all workflows, endpoints, and core entities that must have an automated regression test to prevent breakage.”

***

### 2. **Build Regression Suite Table**

> “From `RegressionTestSuite.md`, `UnitTestTargetsTemplate.md`, `IntegrationTestTargetsTemplate.md`, and `API Test Targets Template.md`, produce a markdown table showing: Test Name/Class, Type (unit/integration/API/defect), covered workflow, linked entity/API, status, owner, last run result, defect/feature link, and flaky flag.”

***

### 3. **Find Missing Regression Tests**

> “Compare `FunctionalWorkflowTemplate.md` and all `/coverage.md` reports (unit, integration, api) to `RegressionTestSuite.md`. List any workflow, endpoint, or entity not currently covered by an automated regression test. Mark as gaps requiring immediate coverage.”

***

### 4. **Filter by Priority Prompt**

> “From the regression table and `TeamGoalsTemplate.md`, filter and list all ‘High Priority’ regression tests—those tied to major business goals, key entity flows, or recurring bugs.”

***

### 5. **Collate Past Defects for Regression**

> “Using `Defect Log` and `RegressionTestSuite.md`, produce a table showing all fixed issues/bugs, if/where they have non-regression test coverage, and flag any gaps.”

***

### 6. **Generate Regression Test Scenario**

> “For the regression candidate `[Name/Workflow/Entity/Endpoint]`, use the Regression Test Scenario Template and gather details from `FunctionalWorkflowTemplate.md`, `DataModelTemplate.md`, `API Test Targets Template`, `IntegrationTestTargetsTemplate.md`, and `Defect Log` to generate a detailed regression test scenario documentation block.”

***

### 7. **Expand Regression for Feature Change**

> “When `[Feature/Workflow]` changes (new story or business rule), use `FunctionalWorkflowTemplate.md` and `RegressionTestSuite.md` to suggest and document what new regression test cases must be added or updated.”

***

### 8. **Update Flaky Test List Prompt**

> “After the latest regression suite run, update the Flaky? and Last Run Status columns in `RegressionTestSuite.md` for any tests that failed intermittently or didn’t pass in CI.”

***

### 9. **Tag Issue/Change to Test**

> “For each row in `RegressionTestSuite.md`, link to the relevant issue, change request, or user story in the ‘Issue/Change Ref’ column by referencing the Defect Log or FunctionalWorkflowTemplate.md.”

***

### 10. **Summarize Regression Coverage Prompt**

> “Summarize, in markdown, the overall regression coverage across all workflows, core entities, and endpoints. Use `RegressionTestSuite.md`, `FunctionalWorkflowTemplate.md`, and `/coverage.md` files to highlight % coverage by workflow, by entity, and by endpoint, including trend or red flag gap areas.”

***

### 11. **Generate Automated Run Config Prompt**

> “Using `environment-setup.md` and `RegressionTestSuite.md`, generate a script or CI runner config that executes only the current regression suite (e.g., a GitHub Actions YAML that triggers the correct test tags/classes).”

***

### 12. **Show Workflow-Linked Regression Map**

> “For each business workflow listed in `FunctionalWorkflowTemplate.md`, show the linked regression tests in `RegressionTestSuite.md` (unit, integration, API, or defect), including scenario coverage and audit/compliance notes, in a table or process diagram.”

***

## How to Use This Library

- **Paste a prompt in Copilot Chat, your LLM interface, or your test documentation workflow.**
- **Always specify or attach the referenced `.md` or `.yaml` content for best results and traceability.**
- All prompt outputs map directly to your regression suite documentation and coverage traceability requirements.

***

**With these prompts, your team and tooling can keep the regression suite authoritative, auditable, and perfectly mapped to everything that matters—workflows, endpoints, business logic, past bugs, and current team/coverage priorities.**

