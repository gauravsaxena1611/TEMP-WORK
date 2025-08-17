<img src="https://r2cdn.perplexity.ai/pplx-full-logo-primary-dark%402x.png" style="height:64px;margin-right:32px"/>

# Dive me the detailed Prompl library for this step 5 with all the reference to md files from this step and all previous steps

Certainly! Here is a **detailed Prompt Library for Step 5: API Tests**, with each prompt clearly described, mapped to the right activity, and referencing the relevant `.md` templates and artifacts from Step 5 _and all earlier steps_. Each prompt comes with its intended use and exact cross-references, making this library ready for direct use and traceability in your playbook.

***

# Step 5 – API Tests: Prompt Library

| Prompt Name | Purpose \& Activity | References (MD files) | Where to Use / Section |
| :-- | :-- | :-- | :-- |
| **List API Endpoints Prompt** | Enumerates all API endpoints, HTTP methods, URL paths, and linked workflows for coverage mapping. | - FunctionalWorkflowTemplate.md (Step 1)<br>- ArchitectureDiagramTemplate.md (Step 1)<br>- ServiceUtilityClassInventoryTemplate.md (Step 1) | Section 3.1 (Identify Endpoints); Template 4.1 |
| **API Test Targets Table Prompt** | Construct a markdown table mapping endpoints to test classes and status (to fill or verify existing targets). | - API Test Targets Template (Step 5)<br>- IntegrationTestTargetsTemplate.md (Step 4)<br>- UnitTestTargetsTemplate.md (Step 3)<br>- FunctionalWorkflowTemplate.md (Step 1) | Section 3.1 (Test Target Table); Template 4.1 |
| **Generate Scenario Prompt** | Produces suites of API test scenarios for each endpoint including happy path, error case, security/auth checks, and edge cases. | - API Test Scenario Template (Step 5)<br>- DataModelTemplate.md (Step 1)<br>- TeamGoalsTemplate.md (Step 1)<br>- FunctionalWorkflowTemplate.md (Step 1) | Section 3.2 (Define Scenarios); Template 4.2 |
| **Negative Test Prompt** | Describes and generates test cases for invalid input, missing/invalid auth, boundary conditions, and error responses. | - API Test Scenario Template (Step 5)<br>- DataModelTemplate.md (Step 1)<br>- ExternalDependencyTemplate.md (Step 1) | Section 3.2 (Define Scenarios); Template 4.2 |
| **Scaffold API Test Class Prompt** | Creates a boilerplate test class (MockMvc/WebTestClient/RestAssured), with imports, setup, and placeholders per endpoint. | - API Test Class Skeleton Example (Step 5)<br>- Environment-setup.md (Step 2)<br>- UnitTestTargetsTemplate.md (Step 3)<br>- IntegrationTestTargetsTemplate.md (Step 4) | Section 3.3 (Implement Tests); Example 4.3 |
| **Generate Security/Authorization Test Prompt** | Generates tests that validate proper enforcement of authN/authZ (tokens, roles, forbidden access, expiry). | - TeamGoalsTemplate.md (Step 1)<br>- API Test Scenario Template (Step 5)<br>- API Test Targets Template (Step 5) | Section 3.2–3.3 (Security Scenarios); Template 4.2/4.1 |
| **Test HTTP Contract Prompt** | Compares actual endpoint responses to the documented OpenAPI/Swagger contract, for schema and status fidelity. | - API Test Scenario Template (Step 5)<br>- DataModelTemplate.md (Step 1)<br>- README.md (if contract link present, Step 1) | Section 3.4 (Validation); Template 4.2 |
| **API Coverage Summary Prompt** | Processes API test execution or coverage reports and outputs a markdown table listing covered/missing endpoints and tested scenarios. | - api-test-coverage.md (Step 5)<br>- API Test Targets Table (Step 5)<br>- integration-test-coverage.md (Step 4)<br>- unit-test-coverage.md (Step 3) | Section 3.4 (Run \& Document), also Section 4.1 |
| **Link to Workflow Prompt** | For a given endpoint/test, insert or update a reference showing which FunctionalWorkflow (business use-case) it satisfies. | - FunctionalWorkflowTemplate.md (Step 1)<br>- API Test Scenario Template (Step 5) | Section 4.2 (Related Artifacts block) |
| **Generate cURL Example Prompt** | Generates sample cURL (or Postman) commands for each API and scenario, usable in documentation or manual validation. | - API Test Scenario Template (Step 5)<br>- DataModelTemplate.md (Step 1)<br>- README.md (Step 1) | Section 4.2 (Request \& Setup); optionally docs for manual/QA |
| **Mock/Stub External Dependency Prompt** | Generates code/config to stub/mock external dependencies (using WireMock, Mockito, etc.) for endpoint testing reliability. | - ExternalDependencyTemplate.md (Step 1)<br>- API Test Targets Table (Step 5)<br>- Environment-setup.md (Step 2) | Section 3.3 (Implement Tests); Example 4.3 |


***

### Example Prompts (for Copy/Paste in Copilot/LLM Chat)

1. **List API Endpoints Prompt:**

```
"List all HTTP API endpoints with methods, URLs, request/response schemas by reading FunctionalWorkflowTemplate.md and ArchitectureDiagramTemplate.md."
```

2. **API Test Targets Table Prompt:**

```
"Generate a markdown table listing all endpoints, mapped test class, and status (based on API Test Targets Template and IntegrationTestTargetsTemplate.md)."
```

3. **Generate Scenario Prompt:**

```
"For endpoint [METHOD] [URL], generate test scenarios and cases for valid, invalid, and security flows referencing DataModelTemplate.md and FunctionalWorkflowTemplate.md."
```

4. **Scaffold API Test Class Prompt:**

```
"Create a MockMvc/WebTestClient test class skeleton for controller [Name], including @WebMvcTest and @MockBean usage."
```

5. **API Coverage Summary Prompt:**

```
"Update the api-test-coverage.md with all endpoints covered by implemented tests and flag missing cases."
```


***

## How This Library Supports Traceability and Validation

- **Every prompt is mapped to a precise template or `.md` artifact**—e.g., scenarios always link to `API Test Scenario Template` and trace back to workflows/data models in Step 1.
- **All generated or curated content is stored in the corresponding `/api-tests/` directory and page sections**, ensuring visibility for validation and future test extension.
- **Reference tables in Step 5 always show which prompt/template feeds which test and which earlier business requirement or technical artifact.**

***

**Want more?**
If you’d like, I can provide fully filled example outputs for these prompts using your actual artifacts, or further expand the table for review/audit purposes.

