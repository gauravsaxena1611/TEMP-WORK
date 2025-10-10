# Fair Lending EUC Remediation Project - High Level Architecture

## Overview

This architecture diagram (HLD OFL.drawio) shows the high-level system design for remediating high-risk End-User Computing (EUC) processes used in fair lending analytics at Citi. It documents the Phase 1 CRC application (APP ID: 176042 — City Risk & Controls) that centralizes previously unmonitored SAS/Python EUCs into a controlled, auditable environment.

## Section 1: Input Data Sources (Left Side)

### 1.1 User Groups

Three distinct teams interact with this system:

- **Fair Lending Analytics (FLAM) Team**: Runs only the BISG process to generate probability reports, then exits the workflow
- **Risk Model Team (L1 OFL)**: Uses BISG output to perform subsequent DIA and SMDA analyses
- **Analytics Team (L1 OFL)**: Similar to Risk Model team, performs the complete analytical workflow

### 1.2 Data Sources (Phase 2 - Future State)

The diagram shows three critical data inputs, all marked for Phase 2 automation:

**Census/Government Data**
- Source: U.S. Census Bureau and Social Security Administration
- Purpose: Provides lookup information for the BISG algorithm
- Challenge: No official System of Record (SOR) exists within Citi Bank
- Current approach: On-demand extracts loaded into CRC database
- Used to calculate demographic probabilities

**Prospects Data**
- Source: AXIOM system (identified)
- Contains: Information about individuals who are not current Citi customers
- Status: Part of Use Case 16 initiative
- Integration method: To be determined in Phase 2

**Existing Customer Data (ECM)**
- Source: Enterprise Data Warehouse (EDW) - provides 90% data coverage
- Contains: System of Record information for current Citi account holders
- Status: Automation planned for Phase 2

### 1.3 Phase 1 Workaround

The yellow box indicates that during Phase 1, users will manually upload Excel files for model training and testing purposes. This data is explicitly not for regulatory use, only for internal analysis and algorithm refinement.


## Section 2: Processing - CRC Application (Center)

The processing section is organized into four architecture layers and includes the CRC application identifier (APP ID: 176042 — CRC):

### 2.1 Presentation Layer

Angular UI (CRC portal)
- Integrated with the CRC portal
- Request submission (Submit BISG / Submit DIA / Submit Change in AIR/SMD)
- Manual file upload in Phase 1 (Excel uploads) or system selection in Phase 2
- Downloadable Angular reports (default: non-PII)

### 2.2 Application Layer (Java services)

Main Java services shown in the diagram:

- Process Service — file upload APIs, execution APIs and orchestration (invokes Python algorithms)
- Execution File Service — creates/manages temporary execution files
- IDOC Service — two-way interaction with IDOC storage (persist/retrieve files)
- File Reader Service — validation, cleanup and parsing of uploaded files

API calls are used between the Angular UI and Java services (edge label: "API Calls"). The Process Service invokes the Python BISG engine (diagram label: "2. Invoke Python BISG").

### 2.3 Processing Layer (Python algorithms)

Three analytical engines replace the high-risk EUCs and run as Python algorithms called by the Java services:

- BISG (Bayesian Improved Surname Geocoding)
	- EUC ID: 101299
	- Purpose: Compute probability distributions for protected classes using surname + geography
	- Input: business PII (names, geo)
	- Lookup: Census/Government data (cached in CRC DB)
	- Output: BISG probability report (non-PII by default)

- DIA (Disparate Impact Analysis)
	- EUC ID: 6554
	- Purpose: Detect disparate impact using BISG output plus score/age
	- Inputs: BISG output + additional fields (score, age)
	- Output: DIA report (impact found / not found)

- Change in AIR / SMD (Standardized Mean Difference)
	- EUC ID: 1080
	- Purpose: Conditional "what-if" analyses to test alternative score thresholds and mitigation scenarios
	- Trigger: Runs when DIA identifies bias
	- Output: Recommended score/threshold changes

Execution flow (diagram numbers):
1. User submits BISG request (file upload or system selection)
2. Process Service invokes Python BISG
3. BISG output feeds DIA
4. If bias is found, DIA triggers Change in AIR/SMD
5. Algorithms generate reports for users (Angular reports and downloadable files)

### 2.4 Data and Storage Layer

CRC Database
- Schema: ICRM_CP_DATA (stores confidential PII, assessment data, workflow records, control mappings and a cached copy of Census data)
- FID-based isolation used to logically separate data
- Diagram note: database-level encryption is not required (security confirmation recorded)

IDOC Storage (Amazon S3)
- Stores user uploads, system-generated reports and temporary processing files
- Uses S3 encryption for files at rest

## Section 3: Output and Reporting (Right Side)

### 3.1 FLAM Team Output

- Receives BISG Probability Report
- Report is non-PII by default
- Download capability provided
- FLAM team workflow ends here (does not proceed to DIA or SMDA)

### 3.2 Risk Model and Analytics Team Output

- Receives DIA Report showing bias analysis results
- Receives Change in SMDA/AIR Report with mitigation recommendations
- All reports downloadable
- Non-PII by default with option for PII reports if needed

### 3.3 Sequential Workflow

The diagram shows the numbered workflow sequence:
1. FLAM team submits BISG request and runs algorithm
2. FLAM team downloads BISG output
3. Risk/Analytics teams upload BISG output along with additional data for DIA
4. If bias is found, teams run Change in SMDA analysis
5. Teams download final analytical reports

## Section 4: Data Flow and Connections

### 4.1 User Interaction Flow (Blue Arrows)

Shows how users from all three teams submit requests to the Angular UI. The label indicates "Submit BISG Request (File Upload or System Selection)" representing the dual input methods during Phase 1 (manual) and Phase 2 (automated).

### 4.2 Service Communication (Green Arrows)

Bidirectional arrows between Angular UI and Java services represent API calls and responses for orchestrating business logic.

### 4.3 Algorithm Processing Flow (Orange Arrows)

Shows the invocation of Python algorithms by Java services and the sequential dependency between algorithms:
- Java service invokes Python BISG
- BISG output feeds into DIA
- DIA results (if bias found) trigger SMDA

### 4.4 File Storage Operations (Purple Arrows)

Bidirectional connection between IDOC Service and IDOC Storage for file persistence and retrieval operations.

### 4.5 Database Operations (Green Arrows)

Shows BISG algorithm reading Census data from CRC database and writing results back.

### 4.6 Report Generation (Cyan Arrows)

Shows the flow from algorithms to output sections, indicating report generation and delivery to users.

### 4.7 Phase 2 Future Automation (Gray Dashed Arrows)

Dashed lines from data sources to CRC database represent future automated data feeds:
- Census data: On-demand extracts
 - Prospects data: Automated feed from AXIOM
- ECM data: EDW integration

## Section 5: External Storage Components

### 5.1 CRC Database (Cylinder at Bottom)

Physical representation of the database showing:
- ICRM_CP_DATA schema designation
- Assessment data storage
- Review workflows
- Control mappings
- Census data cache
- FID-based access control mechanism

### 5.2 IDOC Storage (Cylinder at Bottom)

Physical representation of file storage showing:
- Amazon S3 infrastructure
- Encrypted file storage
- User uploads
- Generated reports
- Temporary files

## Section 6: Legend and Key Information

### 6.1 Color-Coded Flow Legend

- **Blue arrows**: User interaction flow
- **Green arrows**: Service communication and database operations
- **Orange arrows**: Algorithm processing flow
- **Purple arrows**: File storage operations
- **Cyan arrows**: Report generation
- **Gray dashed arrows**: Phase 2 future automation

### 6.2 EUC Remediation Details

Lists the specific EUC IDs being replaced:
- 101299: BISG
- 6554: DIA
- 1080: Change in SMDA

### 6.3 Project Timeline

- **Phase 1**: April 2026 - Core functionality replication
- **Phase 2**: Post-April 2026 - Data automation tracked via Corrective Action Plan (CAP)

## Section 7: Key Project Information (Notes Box)

### 7.1 Business Context

- Office of Fair Lending compliance requirement
- Regulated by Consumer Financial Protection Bureau (CFPB)
- Purpose: Prevent bias in marketing campaigns
- Ensures product offers reach all protected classes equitably

### 7.2 Problem Statement

- High-risk EUCs lacked tracking and audit trails
- Manual processes with no monitoring
- Unmonitored SAS and Python codes
- Risk of unintentional discrimination

### 7.3 Solution Approach

- Centralized CRC application replaces scattered EUCs
- Automated audit trail for compliance
- Controlled, monitored environment
- FID-based data isolation for security

### 7.4 User Group Distinctions

- FLAM Team: Executes BISG process only
- Risk Model Team: Executes full analytical workflow
- Analytics Team: Executes full analytical workflow

### 7.5 Data Security Implementation

- Confidential PII data classification
- ICRM_CP_DATA schema for sensitive data
- FID-based table isolation within shared schema
- S3 encryption for files
- Database encryption not required (security team confirmed)

### 7.6 Phase 1 Scope

- Replicate existing EUC functionality
- Support manual file upload for model training and testing
- Build Angular UI integrated with CRC portal
- Implement Java services and Python algorithms

### 7.7 Phase 2 Scope (Tracked via CAP)

- Automate data sourcing from Systems of Record
- EDW integration providing 90% data coverage
 - AXIOM integration for prospects data
- Alignment with Use Case 16 initiative
- Evaluation of NDM, API, or Kafka integration options

### 7.8 Data Sourcing Challenges

- Business users often don't know original data sources
- No official SOR identified for Census data within bank
- Requires commitment from multiple source system teams
- Many data inputs currently marked "To Be Determined" (TBD)

## Section 8: Regulatory Context

### 8.1 CFPB Oversight

The Consumer Financial Protection Bureau monitors banks to ensure fair lending practices. Protected classes include:
- Asian-American
- African-American
- White American
- Hispanic/Latino
- Other demographic groups

### 8.2 System Purpose

- Internal compliance tool (not direct regulatory reporting)
- Validates that marketing campaigns are bias-free
- Analyzes campaigns before public launch
- Provides defensible audit trail for regulatory examination

## Critical Understanding Points

1. **Sequential Dependency**: The three algorithms must execute in order - BISG produces the demographic probabilities that DIA needs to detect bias, and SMDA only runs if DIA finds issues.

2. **Phased Delivery Strategy**: Phase 1 delivers immediate risk remediation by replacing unmonitored EUCs with controlled application. Phase 2 tackles the complex data automation challenge separately via a formal CAP.

3. **User Workflow Divergence**: FLAM team completes their work after BISG, while Risk Model and Analytics teams continue through the full workflow.

4. **Data Isolation Strategy**: Even though multiple applications share the ICRM_CP_DATA schema, FID-based access control ensures complete logical separation.

5. **Compliance vs. Efficiency Trade-off**: Phase 1 prioritizes compliance (eliminating EUC risk) over efficiency (manual uploads), with automation coming in Phase 2.

This architecture represents a pragmatic approach to remediating high-risk processes while acknowledging and formally tracking the complexities of enterprise data integration.