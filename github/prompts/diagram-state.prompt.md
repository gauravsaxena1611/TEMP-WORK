---
name: diagram-state
description: Create a State Machine diagram showing states, transitions and events
---
Read `#file:.github/skills/generating-architecture-diagrams/SKILL.md` and `#file:.github/skills/generating-architecture-diagrams/references/state-spec.md`.

Create a State diagram for:
Entity: ${input:entity:What entity changes state? e.g. Order, Claim, Application}
States: ${input:states:List all possible states}
Transitions: ${input:transitions:What events trigger transitions between states?}

Output complete draw.io XML.
