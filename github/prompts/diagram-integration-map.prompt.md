---
name: diagram-integration-map
description: Create an Integration Map showing upstream/downstream system connections
---
Read `#file:.github/skills/generating-architecture-diagrams/SKILL.md` and `#file:.github/skills/generating-architecture-diagrams/references/integration-spec.md`.

Create an Integration Map for:
Central system: ${input:system:The main application}
Upstream systems: ${input:upstream:Systems that send data TO this application}
Downstream systems: ${input:downstream:Systems this application sends data TO}
Integration types: ${input:integration_types:e.g. REST, SOAP, MQ, DB link, file transfer}

Output complete draw.io XML.
