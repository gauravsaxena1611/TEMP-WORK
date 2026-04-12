---
name: diagram-deployment
description: Create a Deployment Topology diagram showing infrastructure, environments and nodes
---
Read `#file:.github/skills/generating-architecture-diagrams/SKILL.md` and `#file:.github/skills/generating-architecture-diagrams/references/deployment-spec.md`.

Create a Deployment diagram for:
System: ${input:system:Application or system name}
Environments: ${input:environments:e.g. DEV, SIT, UAT, PROD}
Infrastructure: ${input:infra:Cloud/on-prem? Key nodes, servers, load balancers?}

Output complete draw.io XML.
