### Architecture Assessment: Automating Data Ingestion via A-Hub Integration

#### 1\. Strategic Context and Assessment Objectives

The transition from manual End User Applications (EUAs) to automated data ingestion is a critical mandate for the OFL Project Phase 2\. Currently, the reliance on manual file handling creates operational bottlenecks and introduces significant data governance risks. This assessment serves as the primary blueprint for reconciling robust data security with operational efficiency, ensuring that the architecture supports a governed, systemic flow of information from internal and external sources. By migrating to a systemic ingestion model, we aim to move beyond the fragmented "manual upload" state and establish a resilient pipeline that satisfies internal governance standards and EUA remediation mandates.The primary objectives of this assessment are:

* **Automating External/Internal Data Ingestion:**  Establishing a systemic connection to replace manual file uploads, leveraging A-Hub as the centralized entry point for data.  
* **Decommissioning Legacy EUAs:**  Systematically retiring legacy SAS codes, standalone Python scripts, and the BG downstream process to reduce technical debt and align with institutional standards.  
* **Eliminating PII Data Exposure:**  Removing the risk of Sensitive Personal Identifiable Information (PII) landing on local user machines by ensuring data remains within controlled, systemic environments throughout its lifecycle.While this automation streamlines the transfer and tracking of data, it is important to note that users will continue to perform necessary "data preparation" to create the required input datasets within the governed A-Hub environment prior to ingestion.

#### 2\. Analysis of the Current State: Manual Local Data Handling

The current "Manual Upload" process represents a significant strategic risk, particularly regarding compliance and data privacy. Under the existing model, users must download sensitive data from various repositories—including the Enterprise Data Warehouse (EDW)—to their local machines before re-uploading the files into the CRC User Interface (UI). This bypasses centralized controls and creates a high probability of sensitive data residing in unencrypted or unmonitored local storage.**Current State Process Flow:**

1. **Extraction:**  Users log into source systems (e.g., EDW) using individual credentials to retrieve data.  
2. **Local Storage:**  Extracted data is downloaded directly to the user’s local machine or a local repository.  
3. **Manual Intervention:**  Users perform data preparation and formatting on their local machines to meet CRC input requirements.  
4. **UI Upload:**  The user manually uploads the final file into the CRC UI to initiate the analytics job.This model suffers from critical vulnerabilities, most notably the lack of automated data lineage and the risk of confidential PI data exposure. Without a direct system-to-system connection, the organization cannot verify the integrity of the data from the source to the landing zone, which is a primary driver for the strategic pivot toward A-Hub integration.

#### 3\. Integration Model I: Centralized A-Hub Repository

Model I proposes a simplified, consolidated landing zone where A-Hub acts as a proxy for multi-source data. This model is designed to provide a single, known location for CRC to retrieve all necessary input files.**Technical Mechanics:**  In this model, a dedicated CRC directory is created within the A-Hub environment. Users, after completing their data preparation, place their files into this centralized path. The CRC system then utilizes a systemic connection (SFTP or API) to retrieve files from this single repository to run necessary jobs.**The Challenge of Access Control:**  While operationally straightforward, Model I presents a significant challenge regarding entitlement boundaries. A centralized repository often necessitates universal access for all tool users to the shared directory. This undermines the principle of "Least Privilege," as any user with access to the CRC directory could potentially view data intended for other teams or models. Maintaining strict entitlement boundaries is difficult in a shared-folder architecture, necessitating an alternative that respects individual user permissions.

#### 4\. Integration Model II: Personalized Entitlement-Based Connection

Model II prioritizes security and entitlement integrity by maintaining individual user permissions through the ingestion process. This ensures that users can only access the specific data folders and files they are already authorized to view within the internal A-Hub system.**Technical Execution:**  This model utilizes individual user credentials and "Python secure transfer" (PI SFTP) commands. The technical execution mirrors the user experience found in SAS Studio: when a user logs in to the CRC interface, they are presented with a directory browsing window that reflects their specific A-Hub file structure. Data is transferred systemically from A-Hub directly to the Python server environment, ensuring that confidential PI data never touches a local machine.

##### Model Comparison: Centralized vs. Personalized

Dimension,Model I (Centralized),Model II (Personalized)  
Security & Entitlements,Universal access risk; fails to maintain individual boundaries.,"Maintains ""Least Privilege""; mirrors individual A-Hub permissions."  
Implementation Complexity,Low; requires a single dedicated directory and systemic ID.,High; requires M2M encryption and individual credential pass-through.  
User Experience,Restricted to a specific shared folder.,Seamless; users browse their own existing A-Hub directories.  
To support these secure connections, the infrastructure must support robust Machine-to-Machine (M2M) communication. This remains a primary architectural requirement that must be validated with the A-Hub product team.

#### 5\. Technical Dependencies and Security Architecture

Infrastructure-level security is the non-negotiable foundation for this integration. A primary architectural constraint is the network boundary: the CRC system resides on an  **outside network system** , while A-Hub is an  **internal system** . Bridging this gap requires rigorous Network Access (NAN) reviews and verified encryption protocols.**Technical Prerequisites:**

* **Machine-to-Machine (M2M) Encryption:**  System-to-system communication must be fully encrypted. Validating M2M support is a potential showstopper that requires immediate huddles with the A-Hub Product Manager to ensure out-of-the-box compatibility.  
* **SFTP Connection Configurations:**  Establishment of secure SFTP tunnels between the outside CRC Python servers and the internal A-Hub environment.  
* **Secure Staging Environment:**  
* **Sonic S3 (Preferred):**  This is the recommended staging solution as it offers "Encryption as a Service" via encrypted bucket storage, providing a significant security upgrade.  
* **IDDS (Legacy):**  While currently used for manual storage, leveraging IDDS for automated A-Hub data would require a new Privacy Impact Assessment (PIA) to ensure compliance with updated data privacy standards.

#### 6\. EUA Decommissioning and Remediation Impact

Retiring EUAs is a strategic priority to reduce technical debt and satisfy internal governance requirements. By establishing A-Hub as the "starting point" for data, we replace unmonitored user-led scripts with a systemic, audited process.**Targeted for Full Retirement:**

* Legacy SAS codes.  
* Standalone Python scripts.  
* BG (Downstream processes).  
* Python-based estimations.**Data Lineage and System of Record:**  In this architecture, A-Hub serves as the  **System of Record**  for the input file (though not the Source of Record for the raw data). This allows the system to establish compliant, end-to-end lineage. The audit trail will track data from the A-Hub landing path, through the CRC staging tables (Sonic S3), to the final report generation. This replaces the "black box" of local machine processing with a transparent, governed workflow.

#### 7\. Structured Recommendation and Implementation Roadmap

A phased validation approach is necessary to address the technical complexities of M2M encryption and the internal/outside network conflict.**Next Steps Action Plan:**

*   **A-Hub Technical Huddle:**  Meet with the A-Hub Product Team to verify individual entitlement management and the "SAS Studio-like" directory browsing capability.  
*   **Network (NAN) Review:**  Conduct a formal infrastructure review to approve connectivity between the CRC outside-network system and the A-Hub internal network.  
*   **M2M Encryption Validation:**  Confirm the CRC/A-Hub interface can support M2M encryption protocols; escalate to product management if out-of-the-box support is absent.  
*   **Privacy Impact Assessment (PIA):**  Finalize the PIA for A-Hub data storage, specifically validating the use of Sonic S3 for encrypted bucket storage.**Architect’s Verdict:**   **Model II (Personalized Connection)**  is the recommended architecture. Despite the higher implementation complexity and the need for rigorous NAN reviews, Model II is the only approach that maintains entitlement integrity and enforces "Least Privilege." This model successfully transforms the ingestion process from a high-risk manual task into a compliant, automated, and governed systemic flow. Successful implementation is contingent upon resolving the M2M and network boundary challenges during the initial validation phase.

