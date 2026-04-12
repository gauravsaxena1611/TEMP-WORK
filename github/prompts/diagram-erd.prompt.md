---
name: diagram-erd
description: Create an Entity Relationship Diagram (ERD) with Crow's Foot notation
---
Read `#file:.github/skills/generating-architecture-diagrams/SKILL.md` and `#file:.github/skills/generating-architecture-diagrams/references/erd-spec.md`.

Create an ERD for:
System/domain: ${input:system:Application or domain name}
Entities: ${input:entities:List the main entities/tables e.g. Customer, Order, Product, Payment}
Key relationships: ${input:relationships:Describe relationships e.g. Customer has many Orders}

Use Crow's Foot notation. Output complete draw.io XML.
