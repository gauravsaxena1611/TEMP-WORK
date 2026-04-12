# [Document Title]
## [Subtitle — Research Synthesis on: Topic]

<!--
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TMPL-001: RESEARCH & KNOWLEDGE SYNTHESIS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
USE FOR: Industry research, best practice synthesis, technology
evaluation, competitive analysis, literature reviews, market
research, standards documentation.

AUTHORING WORKFLOW:
  1. Run Research Skill BEFORE filling this template
  2. Let research findings determine which sections exist
  3. Apply verification skill to all claims
  4. Embed confidence markers as you write
  5. Set review_trigger — this document type expires

RESEARCH SKILL INTEGRATION:
  - Minimum 5 searches across independent sources
  - 90/10 outlier analysis mandatory
  - Industry-funded sources flagged
  - All claims traced to Tier 1 or Tier 2 sources
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
-->

```yaml
---
# ── RULES-001 STANDARD FIELDS ──────────────────────────────
document_id: "[XXX Short-Title]"
title: ""
version: "1.0"
created: "YYYY-MM-DD"
status: "Draft | Review | Final | Living"
parent_document: ""

# ── AI-OPTIMIZATION EXTENSION ───────────────────────────────
intent: >
  Enable [who] to [do what] by providing [synthesized knowledge
  on what topic from what sources]. Frame as an action outcome.

consumption_context:
  - human-reading
  - ai-reasoning
  - rag-retrieval

triggers:
  - "[Topic] best practices"
  - "how should we approach [topic]"
  - "what does research say about [topic]"
  - "industry standard for [topic]"
  - "[specific question this document answers]"

negative_triggers:
  - "[similar topic this document does NOT cover]"
  - "[decision about this topic — use TMPL-005 instead]"
  - "[implementation steps — use TMPL-003 instead]"

volatility: "stable | fast-changing"
  # stable: annual review sufficient
  # fast-changing: 6-month review, major technology domains

template_version_used: "TMPL-001 v1.1"  # Record which template version was active when this doc was written

research_validated: true
research_validated_date: "YYYY-MM-DD"
research_queries_used:
  - "[query 1 used in research skill]"
  - "[query 2]"
  - "[query 3]"

review_trigger: "YYYY-MM-DD OR [condition, e.g., 'when X releases']"

confidence_overall: "high | medium | low | conditional"
confidence_note: ""
---
```

---

> ## 🤖 AI Summary
> **Core Purpose:** [One sentence — what decision or action this research enables]
> **Key Findings:**
> - [Most important finding — with confidence level]
> - [Second finding]
> - [Third finding — include any significant outlier position]
> **Trust Level:** [HIGH / MEDIUM / CONDITIONAL]
> **Research Sources:** [N] sources reviewed, [N] Tier 1/2 used
> **Research Currency:** [Date validated]
> **Do NOT Use This For:** [What this should not drive — e.g., "specific implementation decisions without reading TMPL-002"]
> **Review By:** [Date or trigger condition]

---

## TABLE OF CONTENTS

1. [Research Context & Scope](#1-research-context--scope)
2. [Executive Summary of Findings](#2-executive-summary-of-findings)
3. [Primary Findings](#3-primary-findings)
4. [Outlier & Minority Positions](#4-outlier--minority-positions)
5. [Comparative Analysis](#5-comparative-analysis)
6. [Synthesis & Recommendations](#6-synthesis--recommendations)
7. [Open Questions & Gaps](#7-open-questions--gaps)
8. [Verification Record](#8-verification-record)
9. [Sources & References](#9-sources--references)
10. [Further Research Pointers](#10-further-research-pointers)
11. [Revision History](#11-revision-history)

---

## 1. Research Context & Scope
[TYPE: EXPLANATION]

<!--
WHY THIS SECTION EXISTS:
Establishes what question this research answers and what it does not.
Critical for AI retrieval — agents need to know if this is the right
document before reading the full content.

CHUNKING NOTE: This section should be 100-200 words. It is often
retrieved alone as a "is this relevant?" check before the full doc.
-->

### 1.1 Research Question

[State the primary question this research answers. Be specific.
Example: "What are the current industry-recommended approaches for
structuring Markdown documentation for LLM consumption?" Not:
"What is good documentation?"]

### 1.2 Scope Boundaries

**This research covers:**
- [Specific scope item 1]
- [Specific scope item 2]
- [Time period / version range if applicable]

**This research does NOT cover:**
- [Explicit exclusion 1] — See [RELATIONSHIP] → [Document if exists]
- [Explicit exclusion 2]

### 1.3 Research Method

| Parameter | Value |
|-----------|-------|
| Sources Reviewed | [N total] |
| Tier 1 Sources Used | [N] |
| Tier 2 Sources Used | [N] |
| Sources Excluded | [N — with reason summary] |
| Industry-Funded Sources | [N — flagged where present] |
| Outlier Positions Identified | [N] |
| Research Date | [Date] |
| Research Tool | Research Orchestrator Skill |

---

## 2. Executive Summary of Findings
[TYPE: RESEARCH_FINDING]

<!--
WHY THIS SECTION EXISTS:
Highest-priority RAG chunk. An agent reading this section alone
should be able to answer the research question at a useful level.
Write this section last — after all findings are complete.

CHUNKING NOTE: 200-300 words maximum. Dense, assertion-forward.
No supporting narrative — that lives in Section 3.
-->

[3-5 paragraph summary of the most important findings.
Each paragraph = one major finding cluster.
Every significant assertion carries a confidence marker.]

The dominant industry position is [summary]. 🔬 `[RESEARCH_FINDING — confidence: HIGH — N sources in agreement]`

[Second major finding paragraph.] ✅ `[VERIFIED — Tier 1, Source Name, Date]`

[Third finding — include if a significant minority position exists.]
⚠️ `[FLAGGED — minority position, see Section 4 for full analysis]`

---

## 3. Primary Findings
[TYPE: RESEARCH_FINDING]

<!--
WHY THIS SECTION EXISTS:
The substantive content of the research. Each subsection = one
finding cluster. Structure each finding with: the claim, the
evidence, and the confidence assessment.

AUTHORING RULES:
- Lead with the claim, not the evidence
- Evidence follows the claim
- Source citations inline
- Confidence marker on every claim an agent might act on
- 150-200 words per subsection max (chunking)
-->

### 3.1 [Finding Cluster 1 — Descriptive Heading]

**Core claim:** [State the finding as a single sentence.]
🔬 `[RESEARCH_FINDING — confidence: HIGH | MEDIUM | LOW]`

[Supporting paragraph — 2-4 sentences explaining the finding.
Reference sources inline as "According to [Source]" — do not quote
directly unless under 15 words and necessary for precision.]

**Supporting evidence:**
- [Evidence point 1] — Source: [Tier 1/2 name, date]
- [Evidence point 2] — Source: [Tier 1/2 name, date]
- [Evidence point 3] — Source: [Tier 1/2 name, date]

**Confidence assessment:** [Why this finding is rated HIGH/MEDIUM/LOW.
Example: "Three independent Tier 1 sources in agreement, one Tier 2
corroborating, no contradicting Tier 1 evidence found."]

---

### 3.2 [Finding Cluster 2]

**Core claim:** [Finding as one sentence.]
🔬 `[RESEARCH_FINDING — confidence: HIGH | MEDIUM | LOW]`

[Supporting paragraph.]

**Supporting evidence:**
- [Evidence point] — Source: [Name, date]

**Confidence assessment:** [Reason for rating.]

---

### 3.3 [Finding Cluster 3 — repeat pattern]

<!--
Add as many 3.N subsections as findings warrant.
Do not force findings into fewer sections to appear concise.
Each finding cluster should be independently retrievable.
-->

---

### 3.N [Industry-Funded / Biased Source Note]

<!--
INCLUDE THIS SUBSECTION ONLY IF:
Industry-funded sources were used or encountered in research.
Required by Truth & Verification Standards, Section 5.
-->

During this research, [N] industry-funded sources were identified
on [specific claim area]. ⚠️ `[FLAGGED — industry-funded bias risk]`

| Source | Funder | Claim Made | Independent Corroboration |
|--------|--------|------------|--------------------------|
| [Source] | [Company] | [Claim] | [Yes/No/Partial — source if yes] |

Where independent corroboration exists, findings are included with
the flag above. Where no independent corroboration was found, the
claim is excluded from Section 2 and 6.

---

## 4. Outlier & Minority Positions
[TYPE: RESEARCH_FINDING]

<!--
WHY THIS SECTION EXISTS:
Mandatory per Truth & Verification Standards — the 90/10 rule.
The ~10% of sources that contradict the mainstream must be analyzed,
not dismissed. Agents reading only the mainstream can be misled.

AUTHORING RULES:
- Every outlier gets the structured analysis below
- Do not dismiss outliers — analyze them
- Verdict must be one of: CREDIBLE / DATED / WEAK / CONTRADICTS TIER 1 / BOGUS
-->

### 4.1 [Outlier Position 1]

🚩 **OUTLIER DETECTED**

| Field | Value |
|-------|-------|
| **Source** | [Name, URL, Date] |
| **Tier** | [1 / 2 / 3 / 4] |
| **Claim** | [What this source says differently] |
| **Mainstream Position** | [What the ~90% of sources say] |

**Critical Analysis:**
- **Methodology:** [How was this researched?]
- **Sample scope:** [Size, geography, time period]
- **Publication:** [Peer-reviewed / editorial / self-published]
- **Funding:** [Disclosed / industry / independent]
- **Plausibility:** [Does it hold under cross-reference?]

**Verdict:** `[CREDIBLE OUTLIER — adds nuance, included with flag]`
OR `[WEAK OUTLIER — methodology flawed, excluded]`
OR `[DATED OUTLIER — superseded by more recent evidence]`

---

### 4.2 [Outlier Position 2 — repeat structure if needed]

<!--
If no outlier positions were found in research, state explicitly:
"No significant outlier positions were identified in this research.
All sources reviewed were in agreement on the primary findings
within ±10% variance."

Do NOT skip this section — its absence must be declared.
-->

---

## 5. Comparative Analysis
[TYPE: REFERENCE]

<!--
WHY THIS SECTION EXISTS:
Side-by-side comparison of approaches, tools, frameworks, or positions.
This is the highest-value section for AI agents making decisions between options.
Structure as tables wherever possible — highest LLM extraction accuracy.

INCLUDE ONLY IF: Research revealed multiple viable approaches to compare.
SKIP IF: Research is about a single approach with no meaningful alternatives.
-->

### 5.1 [Comparison Dimension — e.g., "Approaches Compared"]

| Attribute | [Option A] | [Option B] | [Option C] |
|-----------|-----------|-----------|-----------|
| [Criterion 1] | | | |
| [Criterion 2] | | | |
| [Criterion 3] | | | |
| [Criterion 4] | | | |
| **Best For** | | | |
| **Avoid When** | | | |
| **Confidence** | | | |

**Table notes:** [Any caveats on the comparison — e.g., "Option B data from 2024 industry report, may be outdated for v3.x comparisons"]

✅ `[VERIFIED — Table data sourced from Tier 1/2 sources as marked]`

---

### 5.2 [Second Comparison Dimension if needed]

---

## 6. Synthesis & Recommendations
[TYPE: RESEARCH_FINDING]

<!--
WHY THIS SECTION EXISTS:
Where findings become actionable. This is what an AI agent will
read when it needs to make a recommendation or take an action
based on this research. Be explicit, not hedged.

AUTHORING RULES:
- Recommendations must follow from evidence in Sections 3-5
- Do not introduce new claims here not supported above
- Distinguish between strong recommendations (high confidence) and
  conditional recommendations (medium/low confidence)
- Include "when NOT to follow this recommendation"
-->

### 6.1 Primary Recommendation

**Recommendation:** [Clear, direct statement of what to do.]
🔬 `[RESEARCH_FINDING — confidence: HIGH | MEDIUM | CONDITIONAL]`

**Rationale:** [2-3 sentences connecting this recommendation to
the evidence in Section 3. Reference specific finding subsections.]

**When this applies:** [Conditions under which this recommendation holds]

**When NOT to follow this:** [Important exceptions or counter-conditions]

---

### 6.2 Secondary Recommendations

| Priority | Recommendation | Confidence | Condition |
|----------|---------------|------------|-----------|
| 1 | [Recommendation] | HIGH / MEDIUM / LOW | [When applies] |
| 2 | [Recommendation] | HIGH / MEDIUM / LOW | [When applies] |
| 3 | [Recommendation] | HIGH / MEDIUM / LOW | [When applies] |

---

### 6.3 What This Research Does NOT Resolve

[Honest statement of what questions this research raises but does
not answer. Agents need to know the limits of this document before
acting on it.]

- [Unresolved question 1] ❓ `[UNRESOLVED — insufficient independent evidence]`
- [Unresolved question 2] ❓ `[UNRESOLVED — conflicting Tier 1 sources]`

---

## 7. Open Questions & Gaps
[TYPE: OPEN_QUESTION]

<!--
WHY THIS SECTION EXISTS:
Signals to human authors and AI agents what follow-up research
is needed. Prevents this document from being over-relied on
for questions it cannot answer.
-->

### 7.1 Questions Requiring Further Research

- [ ] [Question 1 — what would need to be researched and why it matters]
- [ ] [Question 2]
- [ ] [Question 3]

### 7.2 Known Information Gaps

| Gap | Impact | Recommended Action |
|-----|--------|-------------------|
| [Description of missing information] | [How this affects the recommendations] | [What research would fill it] |

### 7.3 Expiry Conditions

This document should be re-researched when:
- [Specific trigger 1 — e.g., "A new major version of X is released"]
- [Specific trigger 2 — e.g., "More than 12 months have passed"]
- [Specific trigger 3 — e.g., "A contradicting Tier 1 study is published"]

---

## 8. Verification Record
[TYPE: REFERENCE]

<!--
WHY THIS SECTION EXISTS:
Required by Truth & Verification Standards, Section 9.
Provides the audit trail for all claims in this document.
AI agents can use this to assess overall document trustworthiness
without re-running verification.
-->

**Verification Mode:** MAXIMUM (Research & Knowledge Synthesis)
**Verification Date:** [Date]
**Verification Tool:** Truth & Verification Standards + Verification Skill

| Category | Count |
|----------|-------|
| ✅ Claims Verified (Tier 1/2) | [N] |
| ⚠️ Claims Flagged (used with caveat) | [N] |
| 🚩 Outliers Analyzed | [N] |
| ❌ Claims Excluded (unverifiable) | [N] |
| 🗑️ Bogus Claims Caught | [N] |
| ❓ Open / Unresolved | [N] |

**Overall Confidence:** [HIGH / MEDIUM / LOW]
**Gaps Disclosed:** [YES / NO — description if yes]

**Excluded Claims Log:**
- ❌ [Claim] — Reason: [No primary source / Circular citation / Tier 4]

---

## 9. Sources & References
[TYPE: REFERENCE]

<!--
FORMATTING RULES (Truth & Verification Standards):
- Organize by tier, then domain
- Every source used in the document appears here
- Sources not cited in text but reviewed appear in Section 9.2
- Wikipedia entries are not listed as sources — only their primary sources
- URLs included where available
-->

### 9.1 Primary Sources Used (Tier 1 & 2)

| Tier | Source | Title | Date | Used For |
|------|--------|-------|------|----------|
| 1 | [Author / Organization] | [Title] | [Date] | [Which finding] |
| 1 | | | | |
| 2 | | | | |

### 9.2 Sources Reviewed but Not Used

| Source | Tier | Exclusion Reason |
|--------|------|-----------------|
| [Source] | [3 / 4] | [Circular citation / Outdated / Tier 4] |

### 9.3 Document Cross-References

| Relationship | Document | Section | Reason |
|-------------|----------|---------|--------|
| [DEPENDS_ON] | [Doc ID] | [Section] | [Why this dependency exists] |
| [VALIDATED_BY] | [External source] | | [What it validates] |
| [EXTENDS] | [Doc ID] | [Section] | [How this extends it] |
| [SEE_ALSO] | [Doc ID] | | [Related but not dependent] |

---

## 10. Further Research Pointers
[TYPE: EXPLANATION]

<!--
WHY THIS SECTION EXISTS:
Enables humans and AI agents to go deeper without starting from scratch.
These are curated starting points, not a bibliography dump.
-->

### 10.1 For Deeper Research on Key Findings

1. **[Finding area 1]**
   - Recommended search: "[specific query]"
   - Best source types: [peer-reviewed / industry reports / official docs]
   - Entry point: [specific URL or publication if known]

2. **[Finding area 2]**
   - Recommended search: "[specific query]"
   - Best source types: [type]

### 10.2 Adjacent Topics Worth Investigating

- [ ] [Topic 1] — [Why it's adjacent and what question it would answer]
- [ ] [Topic 2]

### 10.3 Research Currency Watchlist

Monitor these sources for updates that would trigger re-research:
- [Source/Organization] — [What they publish that affects this topic]
- [Source/Organization]

---

## REVISION HISTORY

| Version | Date | Change Type | Description | Reason |
|---------|------|-------------|-------------|--------|
| 1.0 | [Date] | Initial | Document created | [Research question that triggered this] |

---

**RELATED DOCUMENTS**

| Relationship | Document | Reason |
|-------------|----------|--------|
| [DEPENDS_ON] | [Doc ID] | [Reason] |
| [SUPERSEDES] | [Doc ID if applicable] | [What changed] |

---

*Template: TMPL-001 Research & Knowledge Synthesis v1.0 | Parent: TMPL-000 Template Index*
