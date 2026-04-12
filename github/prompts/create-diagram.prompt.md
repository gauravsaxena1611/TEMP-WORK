---
name: create-diagram
description: Create an architecture diagram using the generating-architecture-diagrams skill
---

Read `.github/skills/generating-architecture-diagrams/SKILL.md` and follow its instructions.

Diagram type: ${input:diagram_type:e.g. C4 Context, HLLFD, Sequence, Component}
System/scope: ${input:system_or_scope:e.g. payments service, customer onboarding flow}

Additional context: ${input:additional_context:Any notes, actors, systems to include}

Output the complete draw.io XML that I can copy into draw.io or diagrams.net.
