---
name: diagram-c4-context
description: Create a C4 Level 1 System Context diagram
---
Read `#file:.github/skills/generating-architecture-diagrams/SKILL.md` and `#file:.github/skills/generating-architecture-diagrams/references/c4-spec.md`.

Create a C4 L1 System Context diagram for:
System: ${input:system:Application or system name}
Users/actors: ${input:actors:Who uses this system?}
External systems: ${input:external_systems:What external systems does it connect to?}
Brief purpose: ${input:purpose:What does this system do in one sentence?}

Output complete draw.io XML.
