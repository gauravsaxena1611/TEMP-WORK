# Application Registry
## Context File for generating-architecture-diagrams skill

**Context ID:** CTX-APPREG  
**Type:** DECLARED (built incrementally)  
**Created:** [DATE]  
**Last Validated:** [DATE]  
**Freshness:** [CURRENT / STALE / UNKNOWN]  

---

## 1. Application Registry

Known applications encountered across diagram sessions. This registry grows
incrementally as new applications are discovered in source documents.

| # | Full Name | Short Name | ID (CAI/CSI) | Domain | Status | First Seen |
|---|-----------|-----------|--------------|--------|--------|-----------|
| 1 | [Full Application Name] | [SHORT] | [CAI-XXXX] | [Domain ID] | [Active/Planned/Deprecated] | [Date first encountered] |
| 2 | [Full Application Name] | [SHORT] | [CAI-XXXX] | [Domain ID] | [Active/Planned/Deprecated] | [Date first encountered] |

---

## 2. Aliases and Acronyms

Systems that are known by multiple names across different documents:

| Primary Name | Aliases | Notes |
|-------------|---------|-------|
| [Primary] | [Alias 1], [Alias 2] | [e.g., "ATPC was previously called ATBC in older docs"] |

---

## 3. Known Integrations

Confirmed integration pairs (updated as diagrams are generated):

| Source System | Target System | Protocol | Data | Direction | Confirmed In |
|--------------|--------------|----------|------|-----------|-------------|
| [System A] | [System B] | [REST/Kafka/etc.] | [Data description] | [→/←/↔] | [Source document] |

---

## CONTEXT METADATA

- **Source:** Built incrementally from diagram source documents
- **Last Validated By:** [Person/role]
- **Refresh Trigger:** New diagram request reveals previously unknown applications

## PASSIVE LEARNING SIGNALS

Claude should propose updates to this registry when:
- A new system name appears in source documents not already in Section 1
- An alias or alternative name is discovered for an existing system
- A new integration is confirmed between known systems
- An application's status changes (e.g., deprecated, replaced)
- A CAI/CSI that was previously TBD is now known

## CREATION NOTES

This context is unique in that it does NOT need to be fully populated on first use.
Start with whatever applications are known from the first diagram request, and
add entries incrementally as subsequent diagrams are generated. Over time, this
becomes a comprehensive application inventory for the project/organization.
