---
name: diagram-c4-container
description: Create a C4 Level 2 Container diagram showing deployable units
---
Read `#file:.github/skills/generating-architecture-diagrams/SKILL.md` and `#file:.github/skills/generating-architecture-diagrams/references/c4-spec.md`.

Create a C4 L2 Container diagram for:
System: ${input:system:Application or system name}
Containers: ${input:containers:List the main containers — APIs, DBs, UIs, services, queues}
Tech stack: ${input:tech:Technologies used e.g. Spring Boot, Oracle, React}

Output complete draw.io XML.
