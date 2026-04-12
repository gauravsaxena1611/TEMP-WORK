# Documentation Standards & Project Management Rules
## Universal Guidelines for Structured Project Documentation

**Document ID:** RULES-001  
**Version:** 1.0  
**Created:** January 19, 2026  
**Purpose:** Establish consistent documentation standards for all projects

---

## TABLE OF CONTENTS

1. [Overview & Philosophy](#1-overview--philosophy)
2. [Document Naming Convention](#2-document-naming-convention)
3. [Document Hierarchy Structure](#3-document-hierarchy-structure)
4. [Revision Tracking System](#4-revision-tracking-system)
5. [Cross-Referencing Standards](#5-cross-referencing-standards)
6. [Research & Citation Requirements](#6-research--citation-requirements)
7. [Document Templates](#7-document-templates)
8. [Quality Checklist](#8-quality-checklist)

---

## 1. OVERVIEW & PHILOSOPHY

### 1.1 Core Principles

| Principle | Description |
|-----------|-------------|
| **Modularity** | Break large documents into logical, standalone units |
| **Traceability** | Track all changes with dates, reasons, and context |
| **Interconnectedness** | Documents reference each other with specific pointers |
| **Research-Backed** | Include sources, references, and further reading links |
| **Clarity** | Use clear, short titles and consistent numbering |
| **Hierarchy** | Always maintain parent-child document relationships |

### 1.2 Why These Rules Matter

1. **Individual documents can be shared** without losing context
2. **Research can be conducted** on specific topics independently
3. **Changes are traceable** — we know what changed, when, and why
4. **Knowledge compounds** — findings in one document inform others
5. **Onboarding is easier** — new team members understand the structure
6. **Future projects benefit** — consistent standards across all work

---

## 2. DOCUMENT NAMING CONVENTION

### 2.1 Numbering System

```
FORMAT: [XXX] [Title]: [Subtitle]

Where XXX = Three-digit number indicating hierarchy level

000-009 = Master/Parent documents (project-level)
010-099 = First-level child documents
0X1-0X9 = Sub-children of X (e.g., 011, 012 are children of 010)
100-199 = Second major section (if needed)
```

### 2.2 Numbering Examples

```
PROJECT: Backstage Hair Salon Website

000 Step1 Master - Foundation & Validation
├── 010 Personas - Customer Profiles
│   ├── 011 Persona - Sophisticated Sarah
│   ├── 012 Persona - Busy Professional Priya
│   ├── 013 Persona - Modest Maryam
│   └── etc.
├── 020 Seasons - Business Calendar
├── 030 Website Structure - Page Hierarchy
├── 040 Industry Research - Market Data
└── 050 Validation - Structure Testing

100 Step2 Master - Keyword Research
├── 110 Keywords - Service Categories
├── 120 Keywords - Competitor Analysis
└── 130 Keywords - Intent Classification

200 Step3 Master - Content Strategy
└── etc.
```

### 2.3 Title Format Rules

| Rule | Good Example | Bad Example |
|------|--------------|-------------|
| Keep titles short (3-6 words) | "010 Personas - Customer Profiles" | "010 Complete Customer Persona Profiles Document" |
| Use Title Case | "020 Seasons - Business Calendar" | "020 seasons - business calendar" |
| Include document type | "011 Persona - Sophisticated Sarah" | "011 Sarah" |
| Avoid special characters | "030 Website Structure" | "030 Website Structure (Final v2)" |

### 2.4 File Naming for Storage

```
Format: [Number]_[ShortTitle].md

Examples:
000_Step1_Master.md
010_Personas_Overview.md
011_Persona_Sarah.md
020_Seasons_Calendar.md
```

---

## 3. DOCUMENT HIERARCHY STRUCTURE

### 3.1 The Master-Child Model

Every project MUST have:
- **ONE Master Document** (000 level) — overview, links to all children
- **Multiple Child Documents** (010, 020, etc.) — detailed topic coverage
- **Optional Sub-Children** (011, 012, etc.) — when topics need further breakdown

### 3.2 When to Break Documents Apart

**CREATE A SEPARATE DOCUMENT WHEN:**
- Topic can stand alone (e.g., each persona)
- Document exceeds 1,500 words on a single sub-topic
- Content might be shared independently
- Topic requires its own research/references
- Multiple team members might work on it

**KEEP TOGETHER WHEN:**
- Topics are tightly interdependent
- Separating would lose critical context
- Content is under 500 words
- It's a simple list or reference table

### 3.3 Master Document Requirements

Every Master Document (000 level) MUST contain:

```
1. PROJECT OVERVIEW
   - Project name and description
   - Current status
   - Key stakeholders

2. DOCUMENT INDEX
   - Complete list of all child documents
   - Brief description of each
   - Status of each (Draft/Review/Final)

3. KEY DECISIONS LOG
   - Major decisions made
   - Date and rationale

4. CROSS-PROJECT REFERENCES
   - Links to related projects (if any)

5. REVISION HISTORY
   - Full change log for the master
```

### 3.4 Child Document Requirements

Every Child Document (010+ level) MUST contain:

```
1. HEADER SECTION
   - Document ID and Title
   - Parent document reference
   - Version and date
   - Status (Draft/Review/Final)

2. PURPOSE STATEMENT
   - What this document covers
   - What it does NOT cover (scope boundaries)

3. CONTENT BODY
   - Main content with clear sections
   - Numbered sections for easy reference

4. REFERENCES SECTION
   - Cross-references to other project documents
   - External research sources

5. REVISION HISTORY
   - Change log for this document
```

---

## 4. REVISION TRACKING SYSTEM

### 4.1 Revision History Format

Every document MUST end with a Revision History section:

```markdown
---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-01-19 | Initial | Document created | Initial research and drafting |
| 1.1 | 2026-01-22 | Update | Added Section 3.2 | New research findings from [source] |
| 1.2 | 2026-01-25 | Revision | Revised pricing in 2.4 | Client feedback - prices were outdated |
| 2.0 | 2026-02-01 | Major | Complete restructure | Strategy pivot based on competitor analysis |
```

### 4.2 Change Type Definitions

| Change Type | When to Use | Version Impact |
|-------------|-------------|----------------|
| **Initial** | First creation of document | 1.0 |
| **Update** | Adding new content | +0.1 (1.0 → 1.1) |
| **Revision** | Modifying existing content | +0.1 (1.1 → 1.2) |
| **Correction** | Fixing errors | +0.1 |
| **Major** | Significant restructure or strategy change | +1.0 (1.x → 2.0) |
| **Archive** | Document deprecated/replaced | Move to archive |

### 4.3 Detailed Change Documentation

For significant changes, include expanded context:

```markdown
### Change Detail: Version 1.2 → 1.3

**Date:** 2026-01-25  
**Section Changed:** Section 2.4 (Pricing Strategy)  

**Initial Thought:**  
Pricing was set at $150-250 based on competitor analysis from December 2025.

**New Finding:**  
Market research in January 2026 revealed competitor Salon X increased prices by 15%. 
Customer surveys indicated willingness to pay premium for specialized services.
(See: 040 Industry Research, Section 3.2)

**Change Made:**  
Updated pricing range from $150-250 to $175-300.
Added premium tier for specialized color correction.

**Impact on Other Documents:**  
- 010 Personas: Updated Sarah's expected spend (Section 2.1)
- 030 Website Structure: Added premium service page (Section 4.3)
```

---

## 5. CROSS-REFERENCING STANDARDS

### 5.1 Internal Reference Format

When referencing another document in the same project:

```markdown
FORMAT: [Document ID, Section X.X]

EXAMPLES:
- For detailed persona analysis, see [010 Personas, Section 2.1]
- Pricing aligns with market research findings [040 Industry Research, Section 3.2]
- This supports the website structure defined in [030 Website Structure, Section 4.3]
```

### 5.2 Reference Link Block

For multiple references, use a reference block:

```markdown
---
**RELATED DOCUMENTS:**
- [010 Personas, Section 2.1] — Sarah persona details
- [020 Seasons, Section 3.4] — Bridal season timing
- [040 Industry Research, Section 2.2] — Market size data
---
```

### 5.3 Cross-Reference Table

For documents with many references, include a reference table:

```markdown
## DOCUMENT REFERENCES

| Reference ID | Document | Section | Topic |
|--------------|----------|---------|-------|
| REF-001 | 010 Personas | 2.1 | Sarah demographics |
| REF-002 | 020 Seasons | 3.4 | Bridal peak months |
| REF-003 | 040 Industry Research | 2.2 | GTA market size |
```

### 5.4 Bidirectional References

When Document A references Document B, Document B should also reference Document A:

```
Document A (010 Personas):
"Sarah typically books during bridal season [020 Seasons, Section 3.4]"

Document B (020 Seasons):
"Bridal season attracts Sophisticated Sarah persona [010 Personas, Section 2.1]"
```

---

## 6. RESEARCH & CITATION REQUIREMENTS

### 6.1 Research Expectations

Every document should include:
- **Primary research** — surveys, interviews, direct data
- **Secondary research** — industry reports, published studies
- **Competitive research** — competitor analysis
- **Further reading** — resources for deeper exploration

### 6.2 Citation Format

```markdown
**Inline Citation:**
The salon market is valued at $247 billion globally [Source: Fortune Business Insights, 2024]

**Footnote Style:**
The salon market is valued at $247 billion globally.¹

¹ Fortune Business Insights, "Salon Services Market Size," 2024
```

### 6.3 Source Categories

Organize sources by type:

```markdown
## SOURCES & REFERENCES

### Industry Reports
| Source | Title | Date | URL |
|--------|-------|------|-----|
| Fortune Business Insights | Salon Services Market Size | 2024 | [link] |
| GM Insights | Salon Service Market | 2024 | [link] |

### Government/Census Data
| Source | Title | Date | URL |
|--------|-------|------|-----|
| Statistics Canada | Oakville Population | 2024 | [link] |
| Town of Oakville | Growth Analysis | 2024 | [link] |

### News & Articles
| Source | Title | Date | URL |
|--------|-------|------|-----|
| CBC News | Hijab-friendly salons | 2023 | [link] |

### Competitor Analysis
| Competitor | Website | Notes |
|------------|---------|-------|
| Salon X | salonx.com | Pricing, services |
```

### 6.4 Further Reading Section

Every document should end with a "Further Reading" section:

```markdown
## FURTHER READING & RESEARCH POINTERS

### For Deeper Research on This Topic:
1. **[Topic 1]** — Search terms: "keyword 1", "keyword 2"
   - Recommended source: [Source name]
   
2. **[Topic 2]** — Industry association: [Association name]
   - Annual report: [Report name]

### Related External Resources:
- [Resource 1 with brief description]
- [Resource 2 with brief description]

### Questions for Future Research:
- [ ] Question 1 that needs investigation
- [ ] Question 2 that needs investigation
```

---

## 7. DOCUMENT TEMPLATES

### 7.1 Master Document Template (000 Level)

```markdown
# [Project Name]: [Phase] Master Document
## [Brief Subtitle]

**Document ID:** 000 [Phase] Master  
**Version:** 1.0  
**Created:** [Date]  
**Status:** Draft | Review | Final  

---

## 1. PROJECT OVERVIEW

### 1.1 Project Description
[2-3 sentences describing the project]

### 1.2 Current Status
[Current phase and progress]

### 1.3 Key Stakeholders
- [Name/Role 1]
- [Name/Role 2]

---

## 2. DOCUMENT INDEX

| Doc ID | Title | Description | Status |
|--------|-------|-------------|--------|
| 010 | [Title] | [Brief description] | Draft |
| 020 | [Title] | [Brief description] | Draft |

---

## 3. KEY DECISIONS LOG

| Date | Decision | Rationale | Impact |
|------|----------|-----------|--------|
| [Date] | [Decision] | [Why] | [What it affects] |

---

## 4. PROJECT TIMELINE

[Key milestones and dates]

---

## 5. REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | [Date] | Initial | Document created | [Reason] |

---

## FURTHER READING

[Resources and research pointers]
```

### 7.2 Child Document Template (010+ Level)

```markdown
# [Topic]: [Subtitle]

**Document ID:** [XXX] [Topic]  
**Parent Document:** [000 Master Reference]  
**Version:** 1.0  
**Created:** [Date]  
**Status:** Draft | Review | Final  

---

## PURPOSE

**This document covers:**
- [Scope item 1]
- [Scope item 2]

**This document does NOT cover:**
- [Out of scope item 1] — See [XXX Document] instead

---

## TABLE OF CONTENTS

1. [Section 1]
2. [Section 2]
3. [Section 3]

---

## 1. [SECTION 1 TITLE]

### 1.1 [Subsection]

[Content]

**Related:** [XXX Document, Section X.X]

### 1.2 [Subsection]

[Content]

---

## 2. [SECTION 2 TITLE]

[Continue pattern]

---

## SOURCES & REFERENCES

### Research Sources
| Source | Title | Date | URL |
|--------|-------|------|-----|
| [Source] | [Title] | [Date] | [Link] |

### Document References
| Reference | Document | Section | Topic |
|-----------|----------|---------|-------|
| REF-001 | [Doc ID] | [Section] | [Topic] |

---

## FURTHER READING

### For Deeper Research:
1. [Topic with search terms or resources]

### Questions for Future Research:
- [ ] [Question 1]

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | [Date] | Initial | Document created | [Reason] |
```

### 7.3 Persona Document Template

```markdown
# Persona: [Persona Name]
## [One-line descriptor]

**Document ID:** 01X Persona - [Name]  
**Parent Document:** 010 Personas Overview  
**Version:** 1.0  
**Created:** [Date]  

---

## 1. PERSONA SNAPSHOT

| Attribute | Value |
|-----------|-------|
| **Name** | [Persona Name] |
| **Age Range** | [Age] |
| **Quote** | "[Their key statement]" |
| **Primary Need** | [What they want] |
| **Pain Point** | [Their frustration] |

---

## 2. DEMOGRAPHICS

### 2.1 Basic Demographics
[Details]

### 2.2 Psychographics
[Values, attitudes, lifestyle]

---

## 3. BEHAVIOR PATTERNS

### 3.1 Search Behavior
[How they search, what terms]

### 3.2 Decision Process
[How they decide, timeline]

### 3.3 Trust Factors
[What makes them trust/choose]

---

## 4. NEEDS & PAIN POINTS

### 4.1 Primary Needs
[List]

### 4.2 Pain Points
[List with context]

### 4.3 Objections
[What stops them]

---

## 5. SERVICE PREFERENCES

### 5.1 Primary Services
[Services they seek]

### 5.2 Visit Frequency
[How often]

### 5.3 Average Spend
[Typical spend]

---

## 6. WEBSITE REQUIREMENTS

### 6.1 Entry Points
[How they'll find/enter the site]

### 6.2 Required Pages/Content
[What pages they need]

### 6.3 Content to Create
[Blog posts, guides for this persona]

---

## SOURCES & REFERENCES

[Research backing this persona]

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
```

---

## 8. QUALITY CHECKLIST

### 8.1 Before Finalizing Any Document

**Structure Checks:**
- [ ] Document has correct ID number
- [ ] Title follows naming convention
- [ ] Parent document is referenced
- [ ] Table of contents matches actual sections
- [ ] All sections are numbered correctly

**Content Checks:**
- [ ] Purpose statement is clear
- [ ] Scope boundaries are defined
- [ ] All claims have sources cited
- [ ] Cross-references use correct format
- [ ] Further reading section included

**Revision Tracking:**
- [ ] Revision history table exists
- [ ] Current version is correct
- [ ] All changes are documented

**Cross-Reference Checks:**
- [ ] All referenced documents exist
- [ ] Section numbers in references are accurate
- [ ] Bidirectional references created where needed

### 8.2 Project-Level Checks

**Before Moving to Next Phase:**
- [ ] Master document index is complete
- [ ] All child documents link back to master
- [ ] Key decisions are logged
- [ ] All research sources are catalogued
- [ ] Revision histories are up to date

---

## APPLICATION TO CURRENT PROJECT

### Backstage Hair Salon Website Project

**Recommended Document Structure:**

```
000 Step1 Master - Foundation & Validation
├── 010 Personas Overview - Customer Profiles Summary
│   ├── 011 Persona - Sophisticated Sarah
│   ├── 012 Persona - Busy Professional Priya
│   ├── 013 Persona - Modest Maryam
│   ├── 014 Persona - Senior Margaret
│   ├── 015 Persona - Student Sophie
│   ├── 016 Persona - Bridal Bianca
│   └── 017 Persona - New Parent Nadia
├── 020 Seasons - Business Calendar
├── 030 Website Structure - Page Hierarchy
├── 040 Industry Research - Market Data
├── 050 Validation - Structure Testing
└── 060 Decisions - Approval Tracker

100 Step2 Master - Keyword Research
[To be created after Step 1 approval]
```

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | 2026-01-19 | Initial | Document created | Establishing project documentation standards |

---

## FURTHER READING

### Documentation Best Practices:
1. **Technical Writing Standards** — Search: "documentation best practices", "technical writing guidelines"
2. **Knowledge Management** — Resources: Atlassian Confluence guides, Notion templates
3. **Academic Citation** — Chicago Manual of Style, APA guidelines

### Questions for Future Refinement:
- [ ] Should we add version control integration (Git)?
- [ ] Should we create document templates in Word/Google Docs format?
- [ ] Should we add collaboration/approval workflow rules?

---

**END OF DOCUMENT**

*This document should be included in every project folder as the governing standard for documentation.*
