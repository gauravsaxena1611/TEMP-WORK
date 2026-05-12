# ICRM Policy Management System - High Level Architecture Summary

## Document Information
- **Diagram Type:** C4 Level 2 - Container View
- **System:** ICRM (Integrated Compliance & Risk Management)  
- **Audience:** Technical Architects, Solution Designers, Policy Domain Team Leads
- **Purpose:** Show major technical building blocks and system interactions
- **Date Created:** May 2026
- **Scope:** Policy Management ecosystem across CRC/ERM platforms

---

## System Overview

The ICRM Policy Management system is a comprehensive platform for managing organizational policies, their compliance mappings, attestations, and related change management. It integrates across multiple platforms (CRC and ERM) and connects with downstream business systems.

**Key Scope Items:**
- **CSI 1799.11** - Policy Management and Mapping (Primary domain in CRC)
- **CSI 174592** - Policy Inventory (Central repository)
- **CSI 176041** - ICAPS (Issue Management)
- **CSI 176046** - Regulations Management
- **ERM** - Enterprise Risk Management (future consolidation target)

---

## Architecture Components

### 1. EXTERNAL SYSTEMS & DATA SOURCES

These represent the boundary systems that feed into and consume from the Policy Management system.

**Impound Systems (Connected Risk)**
- External vendor platform for policy authoring
- Hosts the authoritative policy documents (PDFs)
- Manages policy metadata, versioning, and approvals
- Accessible internally via web interface and REST APIs
- Reference: Connected Risk platform

**ES Policy Authoring**
- Policy creation and approval workflows
- Document lifecycle management
- Entitlement and reference data initiation
- Links policy creation back to business requirements

**Reference Data (External)**
- Regulations, training requirements, and entitlements
- Used to establish initial mappings
- Sourced from various organizational systems

**End Users**
- Policy Owners - Define policy content
- Business Partners - Approve and maintain policies
- Admins - System administration and data management
- Analysts - Policy analysis and reporting

---

### 2. CORE POLICY MANAGEMENT (CRC Platform)

This is the central nervous system of the policy management function.

#### User Interface Layer
**Policy Mapping UI**
- Policy management dashboard
- Control mapping interface
- Change management UI
- Attestation and approval interface
- Accessible via HTTPS/API
- Authentication via DSSO/SiteMinder

#### Service Layer

**Policy Mapping Service**
- Maps policies to controls (bidirectional)
- Establishes regulatory mappings
- Links policies to training requirements
- Manages policy versions and history
- Tracks relationships across policy ecosystem

**Change Management Service**
- Detects and tracks all policy changes (metadata, control mapping, regulations, training)
- Notifies affected stakeholders proactively
- Performs impact analysis
- Maintains complete audit trails
- Enforces change workflows (maker-checker)

**Attestation Service**
- Manages control attestations
- Handles waiver dispensations and extensions
- Enforces approval workflows
- Tracks compliance status
- Provides evidence capture

**Workflow Service**
- Implements maker-checker controls
- Routes approvals through proper authorities
- Task assignment and tracking
- Process orchestration across services

#### Data Layer

**Policy Inventory Database (CSI 174592)**
- Master repository of all policies
- Policy metadata (ID, version, status, dates)
- Version history and change tracking
- Status and lifecycle information

**Policy Mapping Database**
- Control-to-policy mappings
- Regulation-to-policy links
- Training-to-policy associations
- Attestation records and history

**CRC Database (REG_MGMT)**
- Central data repository with separate schemas:
  - **Policy Metadata Schema** - All policy-related data
  - **Reference Data Schema** (Shared) - DSMT (Data Source Master Table), entitlements, regulatory data
- Shared across all CRC services
- Change logs and audit records

**ETL Processes**
- Data Quality checks (DQ checks)
- Snapshot creation for point-in-time references
- Integration with external data sources
- Batch job scheduling and execution

---

### 3. INTEGRATIONS & DOWNSTREAM CONSUMERS

These systems consume policy data and provide feedback.

**ICAPS (Issue Management) - CSI 176041**
- Tracks issues and non-compliance findings
- Assigns action items related to policies
- Manages escalations
- Provides feedback on policy effectiveness

**Regulations - CSI 176046**
- Maintains regulatory inventory
- Tracks regulatory changes
- Links regulations to policies
- Monitors regulatory compliance status

**Training - CSI 149359**
- Manages training requirements tied to policies
- Tracks training completion
- Certifications and qualifications
- Training effectiveness metrics

**MCA Mapping (Management Control Assessment)**
- Maps policies to Management Controls
- Links to GAU (Group Assessment Unit) / SAU (Sub-Assessment Unit) data
- Provides control assessment information
- Feeds into compliance assessments

**ERM System (Enterprise Risk Management)**
- Links policies to enterprise risks
- Control-risk relationship management
- Assessment data and risk ratings
- Future consolidation target for policy data (moving from ERM to CRC)

**Reporting**
- Tableau dashboards and analytics
- Executive reporting and KPIs
- Compliance metrics and trends
- Historical trend analysis

**Audit & Compliance**
- Change audit trails
- Approval record documentation
- Compliance proof and evidence
- Regulatory compliance reports

#### Data Integration Layer
**Connection Methods:**
- REST APIs (Synchronous requests)
- Kafka event streams (Asynchronous messaging)
- ETL batch jobs (Scheduled data transfer)
- Message queues (Reliable delivery)

---

## Key Data Flows

### Inbound Flows
1. **Policy Feed** - Impound Systems → Policy Inventory DB (daily via SFTP/REST API)
2. **Reference Data** - External systems → CRC Reference Data (periodic sync)
3. **MCA Data** - ICRM Proxy → Data Lake → ETL → CRC DB (nightly jobs)

### Internal Flows
1. **Policy to Mapping** - Policy metadata triggers mapping service
2. **Change Detection** - Automated daily change detection
3. **Change Notification** - Policy Mapping Service → End Users (email, workflow)
4. **Attestation** - Users → Attestation Service → Audit Trail

### Outbound Flows
1. **API Publishing** - Policy data available via REST APIs
2. **Event Stream** - Changes published to Kafka topics
3. **ETL Export** - Policy data exported to data lake and reporting systems
4. **Reporting** - Data fed to Tableau for dashboards

---

## Technology Stack

**Infrastructure:**
- Kubernetes Services Platform (ECS Cluster - Web Tier, DB Tier)
- Apache HTTP Server (Load balanced)
- Oracle Database backend

**Integration:**
- REST APIs (HTTPS)
- Apache Kafka (Event streaming)
- ETL batch processes

**Data Management:**
- Oracle Database (CRC_DB with multiple schemas)
- Data Lake (for historical data)
- Elasticsearch (indexing for search)

**Monitoring & Operations:**
- App Dynamics
- Kibana (Logging)
- OpenShift (Container orchestration)

---

## Service Dependencies

### Critical Dependencies
- **Impound Systems** - Policy source of truth
- **CRC Database** - Central data repository
- **Authentication Services (DSSO/SiteMinder)** - User access control
- **Workflow Service** - Process orchestration
- **ETL Infrastructure** - Data integration

### Integration Dependencies
- **ICAPS** - Issue tracking feedback loop
- **Training System** - Training requirement validation
- **ERM System** - Risk data integration
- **Regulations System** - Regulatory compliance tracking

---

## Future State Considerations

1. **BWD Consolidation** - Enterprise Risk Management (BWD) database will be migrated from ERM to CRC in 2H 2026
   - Will unify all policy-related systems under single domain (CSI 1799.11)
   - Budget already approved
   - Activity to commence in second half of year

2. **Shared Services Model** - Potential services architecture:
   - Attestation as a Service (AttSVC)
   - Workflow as a Service (already implemented)
   - Change Management as a Service (planned)

3. **API-First Approach**
   - Move from manual uploads to RESTful APIs where applicable
   - 2026 initiative for external system integrations

---

## Compliance & Governance

- **Attestation Model** - All policy controls require periodic attestation
- **Waiver Management** - Exceptions tracked and reviewed
- **Change Management** - No change proceeds without notification and impact analysis
- **Maker-Checker** - All updates subject to approval workflows
- **Audit Trail** - Complete tracking of all changes with who, what, when, why

---

## Access & Security

- **Authentication** - DSSO/SiteMinder based
- **Load Balancing** - SSL/TLS encrypted connections
- **Database Credentials** - Stored in Secrets Management Service
- **Monitoring & Troubleshooting** - AppDynamics and Kibana integration
- **Container Governance** - OpenShift container orchestration with AppDynamics controller

---

## Related Documentation

- **Policy Technical Component & Dataflow Architecture** - Detailed technical component diagram
- **Policy Component Diagram** - Kubernetes deployment view
- **Policy Process Model** - Business process flows
- **Policy Management Target State Integration** - Future state vision
- **Policy Inventory & Data Flow Diagram** - ETL and data lineage

---

## Architecture Governance

**Diagram Standard:** C4 Model - Container Level (L2)  
**Methodology Phase:** Phase 4 (Design & Architecture)  
**Review Cycle:** Quarterly with stakeholder updates  
**Update Triggers:** Major infrastructure changes, new system integrations, technology upgrades

---

## Diagram Legend

| Element | Color | Meaning |
|---------|-------|---------|
| Orange Boxes | External Systems | Vendor/external data sources |
| Blue Boxes | Core Services | ICRM-owned services in CRC |
| Green Boxes | Downstream Systems | Consumer systems receiving policy data |
| Orange Cylinders | Databases | Data storage and persistence |
| Yellow Boxes | ETL/Integration | Data movement and transformation |
| Arrows | Data Flows | Direction and nature of data movement |

---

**Document Status:** Ready for Review and Distribution  
**Next Review Date:** August 2026 (post-BWD consolidation initiation)
