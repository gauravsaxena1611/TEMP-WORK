# Organization Domain Groups
## Context File for generating-architecture-diagrams skill

**Context ID:** CTX-DOMAINS  
**Type:** DECLARED  
**Created:** [DATE]  
**Last Validated:** [DATE]  
**Freshness:** [CURRENT / STALE / UNKNOWN]  

---

## 1. Domain Classification

How this organization classifies its systems into domains for architectural diagrams:

| Domain ID | Domain Name | Description | Color Key |
|-----------|-------------|-------------|-----------|
| [DOM-1] | [Name] | [What systems belong here] | [Reference to CTX-PALETTE Section 1] |
| [DOM-2] | [Name] | [What systems belong here] | |
| [DOM-3] | [Name] | [What systems belong here] | |
| EXT | External | Third-party vendors and external services | Dashed borders |

---

## 2. Domain Rules

Rules for classifying a system into a domain:

| Rule | Description |
|------|-------------|
| [Rule 1] | [e.g., "Systems owned by ICRM/CRC team go in Domain 1"] |
| [Rule 2] | [e.g., "Enterprise-shared services go in Domain 2"] |
| [Rule 3] | [e.g., "Any system outside the corporate network is External"] |

---

## 3. Known Domain Assignments

Systems whose domain is already confirmed:

| System Name | Short Name | Domain ID | Confirmed By |
|-------------|-----------|-----------|--------------|
| [System] | [SHORT] | [DOM-X] | [Source or person] |
| [System] | [SHORT] | [DOM-X] | [Source or person] |

---

## 4. Gateway / Boundary Rules

| Boundary Type | When Applied | Visual Treatment |
|---------------|-------------|-----------------|
| Firewall/Gateway | [e.g., "Between External and any internal domain"] | [e.g., "Dashed box with #F5B7B1 fill"] |
| DMZ | [e.g., "Web-facing services"] | [e.g., "Separate container"] |

---

## CONTEXT METADATA

- **Source:** [How domains were defined — org chart, architecture board decision, etc.]
- **Last Validated By:** [Person/role]
- **Refresh Trigger:** Organizational restructuring, domain ownership changes

## CREATION QUESTIONS

When creating this context file for a new project, ask:
1. How does your organization group its systems into domains? (e.g., by business unit, technology, team)
2. What are the main domain categories? (List 3-6 domains)
3. How do you classify external vs internal systems?
4. Are there any boundary rules (firewalls, DMZ, gateways) between domains?
5. Can you list 5-10 known systems and their domain assignments?
