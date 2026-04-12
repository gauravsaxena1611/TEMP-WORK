---
name: diagram-swimlane
description: Create a Swimlane or BPMN process diagram showing who does what across actors
---
Read `#file:.github/skills/generating-architecture-diagrams/SKILL.md` and `#file:.github/skills/generating-architecture-diagrams/references/swimlane-spec.md`.

Create a Swimlane diagram for:
Process: ${input:process:What business process are we documenting?}
Lanes/actors: ${input:actors:Who are the actors? Each gets a lane e.g. Customer, Backend, Payment Gateway}
Key steps: ${input:steps:Describe the main steps or paste notes}

Output complete draw.io XML.
