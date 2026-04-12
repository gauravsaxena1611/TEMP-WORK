---
name: diagram-sequence
description: Create a Sequence diagram showing interactions between systems or components over time
---
Read `#file:.github/skills/generating-architecture-diagrams/SKILL.md` and `#file:.github/skills/generating-architecture-diagrams/references/sequence-spec.md`.

Create a Sequence diagram for:
Flow/scenario: ${input:scenario:What flow or use case are we showing? e.g. user login, payment processing}
Participants: ${input:participants:Systems, services, or actors involved}
Include error flows: ${input:error_flows:yes/no}

Output complete draw.io XML.
