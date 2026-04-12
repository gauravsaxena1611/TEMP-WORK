---
name: diagram-dfd
description: Create a Data Flow Diagram (DFD) showing how data moves and transforms through the system
---
Read `#file:.github/skills/generating-architecture-diagrams/SKILL.md` and `#file:.github/skills/generating-architecture-diagrams/references/data-flow-spec.md`.

Create a DFD for:
System: ${input:system:Application or system name}
Level: ${input:level:Level 0 (overview) or Level 1 (detailed)?}
Data flows to show: ${input:flows:What data enters, transforms, and exits? e.g. customer order → validation → DB}

Output complete draw.io XML.
