# Changelog — Generating Architecture Diagrams

All notable changes to this skill are documented in this file.
Format follows [Keep a Changelog](https://keepachangelog.com/).

---

## [3.0.0] — 2026-03-29

### Added
- **6 new diagram types**: C4 Component (Level 3), Sequence Diagram, Data Flow Diagram (DFD), Entity Relationship Diagram (ERD), Deployment Diagram, Integration/Interface Map
- **Living Context Architecture** (ARCH-001/002): Required Contexts section, 3 context dependencies (CTX-PALETTE, CTX-DOMAINS, CTX-APPREG), context-templates/ directory with 3 templates
- **50+ trigger phrases** across 5 categories: creation, diagram nouns, technology, context, modification
- **Post-Execution observation section** for passive learning of application registry and domain data
- **New reference specs**: `sequence-spec.md`, `data-flow-spec.md`, `erd-spec.md`, `deployment-spec.md`, `integration-spec.md`
- **Audience-based decision routing** (D12): executive → C4 Context, technical → HLLFD, developer → C4 Component
- **Documentation Standards compliance**: Document ID, parent reference, version header, Sources table with tier ratings
- **Comprehensive Sources & References table** with verification tier labels

### Changed
- **Description rewritten** as single line (fixed multi-line YAML `>` anti-pattern per PATTERN-001)
- **Color palette externalized** to CTX-PALETTE context — no longer ICRM-specific in skill kernel
- **Domain grouping externalized** to CTX-DOMAINS context — portable across organizations
- **C4 spec expanded** from 2 levels (Context, Container) to 3 levels (added Component)
- **color-palette.md** renamed conceptually from "ICRM Domain Color Palette" to "Default Domain Color Palette" with override documentation
- **Decision Points** expanded from D1-D7 to D1-D12
- **Edge Cases** expanded with 3 new scenarios (mixed diagram request, unnamed technologies, conflicting domain classifications)
- **Error Handling** expanded with 2 new error types (reference spec missing, diagram too complex)

### Fixed
- YAML frontmatter description no longer uses multi-line `>` syntax (was causing Prettier formatting issues and potential trigger failures)
- Color palette no longer hardcodes organization-specific ICRM values in the skill kernel (was violating ARCH-001 separation of concerns)

### Governance
- Aligned with: ARCH-001 v1.0, ARCH-002 v1.0, PROMPT-001 v2.0, RULES-001 v1.0
- Research conducted: Anthropic skill authoring best practices (Mar 2026), UML 2.5.1, C4 Model, Tom DeMarco DFD, Peter Chen ERD, TOGAF 10, ISO/IEC/IEEE 42010:2022

---

## [2.0.0] — 2026-03-27

### Changed
- Refactored from single-file to progressive disclosure architecture
- SKILL.md reduced from 716 to ~400 lines
- Color palette, XML rules, patterns, numbering, HLLFD spec, and C4 spec extracted to references/
- Templates bundled in templates/ directory
- Test cases added
- Aligned to PROMPT-001 framework

---

## [1.0.0] — 2026-03-27

### Added
- Initial skill creation
- Full skill content in single SKILL.md
- HLLFD, C4 Context, C4 Container support
- ICRM domain color palette
- draw.io XML generation rules
- Workflow numbering standards
