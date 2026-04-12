# System Context Pack — TMPL-006
## CTX-SYSPACK | SKILL-ARCHDIAG v5.0

**Source:** [020 Diagramming Frameworks, Section 5] — Phase 0 Diagram Selection Input ✅
**Purpose:** Capture the audience, intent, methodology phase, and scope needed by Phase 0 to propose the correct diagram set.
**Usage:** Fill this template before requesting diagram generation, or answer the Phase 0 questions interactively.

---

## System Identity

```yaml
system_name: [Full System Name]
system_short_name: [SHORT_CODE]
system_id: [CAI/CSI/AppID if available]
domain_owner: [Business Domain or Team]
```

---

## Audience Profile

```yaml
primary_audience: [one of: executive / architect / developer / analyst / dba / devops / operations / mixed]
audience_technical_level: [one of: non-technical / technical / expert]
stakeholder_variant: [one of: Business / Technical / Both]
presentation_context: [one of: slide-deck / reference-diagram / documentation / whiteboard]
```

---

## Architectural Intent

```yaml
intent_question: "[The primary question this diagram must answer — one sentence]"
diagram_category: [one of: structural / behavioral / flow / data / deployment / portfolio]
diagram_types_requested: [list of specific types, or 'auto' to let Phase 0 decide]
```

---

## Methodology Phase

```yaml
methodology_phase: [one of: Phase2-Constraints / Phase3-Capabilities / Phase4-HighLevel / Phase5-Detailed / Phase6-Validation / Phase7-Documentation / Phase8-Review]
phase_label: "[Phase N — Name from [030, Section 5]]"
```

---

## Existing Diagrams in Set (for chaining)

```yaml
existing_diagrams:
  c4_l1_context: [filename or 'not yet created']
  c4_l2_container: [filename or 'not yet created']
  c4_l3_component: [filename or 'not yet created']
  dfd_l0: [filename or 'not yet created']
  swimlane: [filename or 'not yet created']
  sequence: [filename or 'not yet created']
  erd: [filename or 'not yet created']
  deployment: [filename or 'not yet created']
  integration_map: [filename or 'not yet created']
ctx_chainids_file: [filename or 'not yet initialized']
```

---

## Scope Boundaries

```yaml
scope_includes: "[What this diagram set covers]"
scope_excludes: "[What is explicitly out of scope — prevents Over-Promising Completeness B4]"
planned_items: "[Any future-state items to be shown as PLANNED/dashed]"
```

---

## Source Documents Available

```yaml
sources:
  - type: [transcript / design-doc / confluence / ppt / existing-drawio / description]
    title: "[Document title]"
    path_or_link: "[path or URL]"
  - type: ...
```

---

## Instructions for SKILL-ARCHDIAG v5.0

1. This file is read at Phase 0 Step 0.1 to identify audience, intent, phase, and scope
2. If this file does not exist, Phase 0 asks the user the equivalent questions interactively
3. Update this file whenever the scope, audience, or phase changes
4. The `scope_excludes` field prevents Anti-Pattern B4 (Over-Promising Completeness)
5. The `existing_diagrams` fields feed the Chaining Model (CTX-CHAINIDS)
