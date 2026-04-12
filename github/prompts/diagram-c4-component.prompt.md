---
name: diagram-c4-component
description: Create a C4 Level 3 Component diagram — internals of one container
---
Read `#file:.github/skills/generating-architecture-diagrams/SKILL.md` and `#file:.github/skills/generating-architecture-diagrams/references/c4-spec.md`.

Create a C4 L3 Component diagram for:
Container: ${input:container:Which container are we zooming into?}
Components: ${input:components:List the main components inside this container}
Key interactions: ${input:interactions:How do they interact with each other?}

Output complete draw.io XML.
