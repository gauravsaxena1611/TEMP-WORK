# AI-Optimization Conventions — Quick Reference
## For use by the ai-documentation skill during document authoring

**Source:** TMPL-000 Template Index v1.1 | PROJ-001 AI-Optimized Documentation Protocol
**Version:** 1.1 | **Updated:** 2026-04-04
**Load when:** Always — this is the primary reference for all AI-optimization conventions

---

## 1. Template Selection — Decision Tree

```
What is the PRIMARY purpose of this document?

├── Synthesizing knowledge FROM external sources  →  TMPL-001
├── Describing HOW a system/API/component WORKS   →  TMPL-002
├── Telling someone HOW TO DO something           →  TMPL-003
├── Context/instructions FOR an AI agent          →  TMPL-004
└── Capturing a DECISION that was made            →  TMPL-005
```

**Hybrid rule:** Split — never merge. Create two documents and link them
bidirectionally. See SKILL.md "Multi-Step Hybrid Document Creation".

---

## 2. Extended Frontmatter — Required Fields

Every document must include these fields beyond RULES-001 baseline:

```yaml
# ── TEMPLATE TRACKING ────────────────────────────────────────
template_version_used: "TMPL-00X v1.1"  # Record active template version

# ── AI-OPTIMIZATION EXTENSION ────────────────────────────────
intent: >              # One sentence: what can someone DO after reading this?
  Enable [who] to [do what] by providing [what knowledge].

consumption_context:   # All that apply:
  - human-reading
  - ai-reasoning
  - rag-retrieval
  - agentic-execution

triggers:              # Min 3 — specific queries that should retrieve this doc
  - ""

negative_triggers:     # Min 2 — what should NOT retrieve this doc
  - ""

volatility:            # evergreen | stable | fast-changing | snapshot | living

research_validated: false
research_validated_date: ""
research_queries_used: []

known_gaps: []         # Open questions or unresolved items from research

review_trigger: ""     # Date OR condition — NEVER leave blank

confidence_overall: "high | medium | low | conditional"
confidence_note: ""
```

**Template field deprecation policy:** When a field is renamed or removed
in a template version update, the old field name is listed in a `deprecated_fields`
comment inside the TMPL file for one major version. Documents using the old field
remain valid for their template version. On any content update to a document,
migrate frontmatter fields to the current template version's schema.

---

## 3. AI Summary Block — Format

Place immediately after frontmatter, before any content. Written LAST.

```markdown
> ## 🤖 AI Summary
> **Core Purpose:** [One sentence]
> **Key Claims/Findings:**
> - [Most important finding/fact with confidence]
> - [Second]
> - [Third]
> **Trust Level:** HIGH / MEDIUM / CONDITIONAL — [reason if conditional]
> **Primary Dependencies:** [Docs this relies on]
> **Research Currency:** [Date validated OR "Not research-dependent"]
> **Do NOT Use This For:** [1-3 explicit exclusions]
> **Review By:** [Date or trigger condition]
```

---

## 4. Section Semantic Tags

Place on the line immediately after every `##` heading:

| Tag | Use When Section Contains |
|-----|--------------------------|
| `[TYPE: REFERENCE]` | Factual, stable, high-precision data — trust and cite |
| `[TYPE: DECISION]` | A choice made — authoritative, historical, immutable |
| `[TYPE: PROCEDURE]` | Step-by-step instructions — execute sequentially |
| `[TYPE: EXPLANATION]` | Background, "why" content — builds understanding |
| `[TYPE: RESEARCH_FINDING]` | Synthesized from sources — check confidence markers |
| `[TYPE: OPEN_QUESTION]` | Unresolved — do not act on, flag for follow-up |

---

## 5. Inline Confidence Markers

Apply to every significant claim an AI agent might act on:

| Marker | Meaning | Format |
|--------|---------|--------|
| ✅ | Verified — Tier 1/2 source confirmed | `✅ [VERIFIED — Source, Date]` |
| ⚠️ | Flagged — used with caveat | `⚠️ [FLAGGED — reason]` |
| 🔬 | Research finding | `🔬 [RESEARCH_FINDING — confidence: HIGH/MEDIUM/LOW]` |
| ❓ | Unresolved | `❓ [UNRESOLVED — status]` |
| 💡 | Logical inference | `💡 [INFERENCE — basis]` |
| 🗑️ | Superseded | `🗑️ [SUPERSEDED — by: doc/date]` |

**Plaintext fallback** — for toolchains where emoji do not parse correctly,
use these ASCII-safe alternatives in document body text:
`[VERIFIED]`, `[FLAGGED]`, `[RESEARCH_FINDING]`, `[UNRESOLVED]`,
`[INFERENCE]`, `[SUPERSEDED]`

The plaintext forms are functionally equivalent. Use consistently within
a single document — do not mix emoji and plaintext markers.

---

## 6. Relationship Types for Cross-References

Format: `[RELATIONSHIP_TYPE] → [Document ID, Section X.X] — [one-line reason]`

| Type | Meaning |
|------|---------|
| `[DEPENDS_ON]` | This doc's validity relies on that one being current |
| `[SUPERSEDES]` | This replaces or updates that document |
| `[APPLIES]` | This is an application of a principle defined there |
| `[CONTRADICTS]` | Known conflict — explained in this section |
| `[EXTENDS]` | This adds depth to that document's content |
| `[VALIDATED_BY]` | This claim is backed by that external source |
| `[IMPLEMENTS]` | This is the concrete implementation of that specification |
| `[SEE_ALSO]` | Related but not dependent |

---

## 7. Chunking-Aware Authoring Rules

The chunking rules below are calibrated for AI retrieval systems that
operate in **tokens**, not words. The 2026 research consensus establishes
the optimal retrieval chunk at 256–512 tokens, with 400–512 tokens being
the production sweet spot for structured technical documentation. At
approximately 0.75 tokens per English word, this translates to a target
section length of **300–500 words** between `##` headings.
✅ `[VERIFIED — FloTorch Benchmark 2025–2026, Vectara/NAACL 2025]`

**The primary rule is semantic completeness, not word count.** A section
should contain exactly one complete idea, concept, or query — neither
split across headings nor bundled with unrelated ideas. Use word count
as a guideline; semantic self-containment is the hard requirement.

### 7.1 Core Chunking Rules

1. **Target 300–500 words per `##` section** — below 200 words produces
   under-context chunks; above 600 words risks context cliff degradation
2. **Open every section with a context anchor sentence** — explicitly name
   the document subject, system, or product being discussed. Do this even
   when the heading makes it obvious — chunks are retrieved without heading context
3. **Every section must be self-contained** — a reader who receives only
   this section (no surrounding context) must understand what it is about
   and act on it correctly
4. **Tables and code blocks always preceded by one context sentence** —
   never let a table or code block be the first element in a section
5. **Bullet lists always preceded by one intro sentence** — no floating lists
6. **Technical identifiers wrapped in backticks**: `field_name`, `TABLE_NAME`, `/api/path`
7. **Paragraphs 3–5 sentences maximum** — hard limit; split if longer
8. **No cross-section pronouns** — write "the authentication step" not "this step";
   name the subject explicitly every time it appears

### 7.2 Context Anchor Sentence Pattern

The context anchor sentence is the first sentence of every section. It
must name the subject of the section regardless of the heading.

```
HEADING: ## Configuration Options
WRONG OPENER: "The following options are available:"
CORRECT OPENER: "The AI Documentation Skill supports the following 
                 configuration options in doc-standards.ctx.md:"

HEADING: ## API Rate Limits  
WRONG OPENER: "Limits are applied per endpoint."
CORRECT OPENER: "The Payments API enforces per-endpoint rate limits 
                 to protect service stability."
```

### 7.3 Cross-Boundary Bridge Pattern

For concepts that logically span multiple sections, use bridge sentences
to maintain retrieval coherence:

- **End of first section** — "For the implementation steps that follow
  from this configuration, see Section 4.2 (Deployment Procedure)."
- **Start of second section** — "The deployment procedure below implements
  the configuration defined in Section 3.1 (Configuration Options)."

### 7.4 Contextual Retrieval Optimization

When a document will be indexed in a vector database, each section chunk
should be embedded with a prepended context header. This header is used
for embedding only and does not appear in the human-readable document.
✅ `[VERIFIED — Anthropic Contextual Retrieval Research, 2024]`

```
Embedding context header format:
[Document: {document_id} — {document_title}]
[Section: {section_heading}]
[Summary: {one-sentence section summary}]
```

This pattern resolves the retrieval failure mode where a technically
correct chunk fails search because it does not contain the product or
feature name in its text.

### 7.5 Context Cliff Awareness

Full-document retrieval is not the intended use case for documents
in this system. A TMPL-002 technical reference may be 5,000–10,000 tokens,
which exceeds the ~2,500 token threshold where LLM response quality
measurably degrades regardless of chunk quality.
⚠️ `[FLAGGED — 2,500 token threshold is an estimate pending empirical testing]`

Design documents to be retrieved section-by-section, not as a whole.
Each section must be independently useful.

---

## 8. Research Trigger Rules — Precise Criteria

The rules below replace general judgment with specific, testable criteria.
Apply the checklist for TMPL-002 and TMPL-004 before deciding whether
to invoke the research-orchestrator skill.

| Template | Trigger | Rule |
|----------|---------|------|
| TMPL-001 | **ALWAYS** | Run research before writing any content |
| TMPL-002 | **CONDITIONAL** | See checklist below |
| TMPL-003 | **NEVER** | Procedure is authoritative — no research needed |
| TMPL-004 | **CONDITIONAL** | See checklist below |
| TMPL-005 | **NEVER** | Decision is a historical fact — not research-derived |

**TMPL-002 Research Trigger Checklist** — trigger research if ANY of these are true:

```
[ ] Document contains a section titled "Best Practices", "Recommendations",
    "Guidance", "Constraints", "Limitations", or similar normative heading
[ ] Document uses "should", "must", "recommended", "avoid", or "never"
    outside of a code block or quoted configuration
[ ] Document describes performance characteristics, capacity limits,
    throughput figures, or SLA/SLO values
[ ] Document references external specifications, RFCs, standards,
    or third-party system behaviour as factual claims
```

**TMPL-004 Research Trigger Checklist** — trigger research if ANY of these are true:

```
[ ] Agent design references a named pattern (ReAct, chain-of-thought,
    tool use, RAG, MRKL) without citing a source
[ ] Document specifies token budgets, context window limits, or
    performance expectations for the agent
[ ] Document describes multi-agent coordination or orchestration
    patterns (orchestrator-subagent, parallel agents)
[ ] Document specifies evaluation or quality criteria for agent output
    (e.g., "accuracy should exceed 90%")
```

---

## 9. Volatility Definitions

| Value | Meaning | Review Cadence |
|-------|---------|----------------|
| `evergreen` | Foundational — changes rarely | 2+ years |
| `stable` | Changes with major versions or system changes | 6–12 months |
| `fast-changing` | Technology-specific — field moves fast | 3–6 months |
| `snapshot` | Point-in-time capture — no expectation of currency | N/A |
| `living` | Updated by agent/human on an ongoing basis | After each session |

---

## 10. Sensitive Content Rules

The following rules apply to all documents in this system without exception.
A document containing prohibited content will fail the Pre-Publish Checklist.

**Never document the following directly in document body text:**

- Credentials of any kind: passwords, API keys, tokens, certificates,
  passphrases, OAuth secrets, private keys
- Personal Identifiable Information (PII): real names in examples, email
  addresses, user IDs, phone numbers, physical addresses
- Connection strings containing usernames or passwords
- Internal infrastructure details: private hostnames, private IP addresses,
  internal service URLs not intended for public documentation
- Production-specific values: actual configuration values from production
  environments (use placeholder syntax instead)

**Approved placeholder syntax:**

```
API_KEY=<YOUR_API_KEY>
DB_URL=<PRODUCTION_DB_URL>
USER_ID=<EXAMPLE_USER_ID>
AUTH_TOKEN=<BEARER_TOKEN>
HOST=<INTERNAL_SERVICE_HOST>
```

**For AI agent context documents (TMPL-004):** Do not include live credentials
even as illustrative examples. Reference secrets manager paths instead:
`DB_PASSWORD → retrieve from secrets/prod/db_password`.

**For regulated industries:** Additionally exclude algorithm training data
details, model threshold values, proprietary data assets, and any information
subject to regulatory disclosure restrictions. Set `data_classification:
confidential` or `restricted` in the CTX-DOCSTD context.

---

## 11. Pre-Publish Checklist — Summary

```
STRUCTURE
[ ] Extended frontmatter complete — no placeholder values
[ ] template_version_used field populated (e.g., "TMPL-001 v1.1")
[ ] AI Summary block present and complete
[ ] Every ## section has [TYPE: ...] tag
[ ] Headings target 300–500 words apart — no long un-headed blocks
[ ] document_id follows project numbering (CTX-DOCSTD)
[ ] parent_document field is filled

CONTENT
[ ] Significant claims have confidence markers
[ ] Every section opens with a context anchor sentence naming the subject
[ ] No floating tables or lists
[ ] Technical identifiers in backticks
[ ] Paragraphs 3–5 sentences max
[ ] No cross-section pronouns — subject named explicitly

SECURITY
[ ] No credentials, passwords, API keys, or tokens in document body
[ ] No real PII — placeholder syntax used (<PLACEHOLDER>)
[ ] No production-specific values — placeholders used
[ ] Regulated industry: disclosure-restricted information excluded

REFERENCES
[ ] Typed cross-references (relationship declared)
[ ] 3+ triggers, 2+ negative_triggers
[ ] review_trigger set — never blank

VERIFICATION
[ ] research_validated date set or marked N/A
[ ] confidence_overall is honest
[ ] Verification Record where applicable

HIERARCHY
[ ] Parent document updated
[ ] Bidirectional references confirmed
[ ] Revision History v1.0 entry present
```

---

## 12. RAG Lifecycle — Superseded Documents

This section defines what happens to a document's vector index presence
when it is superseded. Failure to follow this protocol allows stale,
outdated content to contaminate retrieval results.

### 12.1 Supersession Actions (All Required)

When a document's `status` is set to `Superseded`, three actions are mandatory:

**Action 1 — In the document itself:**
```yaml
status: "Superseded"
superseded_by: "[New Document ID]"
```
Add this warning block immediately after the frontmatter in the document body:
```markdown
> ⚠️ SUPERSEDED: This document has been replaced by [Document ID].
> Do not use for new work. See [Document ID] for current guidance.
```

**Action 2 — In the replacement document:**
```yaml
supersedes: "[Superseded Document ID]"
```
Add a `[SUPERSEDES] →` cross-reference in the relevant section.

**Action 3 — In the vector index:**
Apply a metadata filter tag `status: superseded` to the superseded
document's chunks immediately upon supersession. This excludes them from
default retrieval queries without permanently deleting the source record.
Remove chunks from the active index entirely after a 30-day grace period.

### 12.2 Grace Period Rationale

The 30-day grace period allows agents and workflows that have cached
references to the superseded document to naturally expire. During the
grace period, the document is retrievable only when explicitly filtered
for `status: superseded` — it does not appear in default search results.

### 12.3 Source File Retention

Never delete source Markdown files for superseded documents. The source
file is the authoritative record of what was decided or documented.
Archive to a `_superseded/` folder or apply a `_SUPERSEDED` filename prefix.
Only the index entries are removed; the source is retained indefinitely.

### 12.4 Cross-Reference Hygiene

After supersession, update all documents that contain `[APPLIES] →` or
`[DEPENDS_ON] →` references to the superseded document:
- Either point to the new replacement document
- Or add a note: `⚠️ [FLAGGED — references superseded document; see [New ID]]`

---

## 13. Negative Space Conventions — What NOT to Write

The following anti-patterns reduce RAG retrievability, inflate token count
without adding information, and degrade LLM accuracy on retrieval. Every
document produced by this system must be checked against this list before
publication. Anti-patterns found during authoring should be corrected immediately.
🔬 `[RESEARCH_FINDING — confidence: HIGH — GEO research, 2025–2026; kapa.ai production study, 2026]`

### 13.1 Anti-Pattern Reference Table

| Anti-Pattern | Why It Hurts RAG | Correct Alternative |
|--------------|-----------------|---------------------|
| **Vague superlatives without evidence** — "cutting-edge", "robust", "seamlessly", "best-in-class" | Adds tokens with zero information density; blocks precise retrieval | State the specific capability: "processes 10,000 records/second" instead of "high-performance" |
| **Inter-section pronouns** — "As mentioned above…", "this step", "the following", "it" without a named referent | Breaks when chunk is retrieved in isolation — the "above" is gone | Name the subject every time: "The authentication step" not "this step"; "the database schema" not "it" |
| **Sourceless normative claims** — "Best practices recommend…", "Studies show…", "Experts agree…" | Forces LLM to generate a fabricated source or hedge; degrades answer quality | Cite the source: "The OWASP Top 10 recommends…" or downgrade: "One approach is…" |
| **Vague cross-references** — "For more information, see the documentation" | Provides no retrievable pointer; agent cannot follow the reference | Use precise cross-reference format: "[TMPL-002, Section 4.3]" or full document ID |
| **Floating bullet lists** — lists without a preceding introductory sentence | Bullets retrieved without context don't explain what they're listing | Always precede lists with: "The [system] supports the following configuration options:" |
| **Hedging chains** — "may potentially sometimes be considered possibly…" | Strips the actual claim from the sentence; LLM extracts nothing actionable | Make the claim, then qualify once: "This applies in most cases; exceptions include X." |
| **Question headings** — "## What is the Configuration?" | Question-form headings embed poorly; declarative headings retrieve better | Use declarative: "## Configuration Reference" or "## How to Configure [System Name]" |
| **Dense paragraphs >5 sentences** | Exceeds the attention density that LLMs maintain across a paragraph | Hard limit: 3–5 sentences per paragraph; split or use a list if content is longer |
| **Passive voice obscuring the actor** — "It was determined that…", "Errors are thrown when…" | Removes the agent/subject from the sentence; reduces answer precision | Active voice: "The validator throws an error when…", "The team decided that…" |
| **Assumed context from document title** — sections that begin with "The following table shows:" without naming what system/feature | Title is stripped from the chunk; the "following table" has no subject | Context anchor required: "The Payments API supports the following rate limit configurations:" |
| **Version-ambiguous claims** — "The API supports X" without specifying version | Retrieval returns a claim that may apply to a different system version | "The Payments API v2.1 supports X" or use `system_version` frontmatter to bind |
| **Circular definitions** — "The [X] processes [X] requests" | Defines nothing; LLM cannot generate an accurate explanation | Add a real predicate: "The Payment Processor validates, routes, and records each payment request" |

### 13.2 Authoring Self-Check

Before publishing any section, ask:
1. Could this section be understood by an agent that received ONLY this section?
2. Does every normative claim ("should", "must", "recommended") have a source?
3. Does the opening sentence name the subject explicitly?
4. Is every cross-reference a specific document ID + section number?
5. Could any sentence be deleted without losing information? If yes, delete it.

---

## 14. Session Handoff Pattern

When a documentation task spans multiple Claude conversations, this pattern
ensures the agent in the new session can resume without losing context.

### 14.1 End-of-Session Handoff (Agent Produces)

At the close of any session where work is incomplete, the agent produces a
handoff block and saves it to the `CTX-DOCSTD` context file under
`pending_observations`:

```markdown
## SESSION HANDOFF — [YYYY-MM-DD]

**Work Completed This Session:**
- [Document ID]: [What was done — e.g., "TMPL-001 research synthesis drafted, Sections 1–6 complete"]
- [Document ID]: [Status]

**Work In Progress:**
- [Document ID]: [Current state — e.g., "Section 7 Open Questions not yet written"]
- [Specific task]: [What remains — e.g., "Verification pass on Section 3 claims outstanding"]

**Decisions Made This Session:**
- [Decision summary] — [rationale in one sentence] — See [Document ID if documented]

**Blockers / Open Questions for Next Session:**
- [Question 1 — must be answered before work can continue]
- [Question 2 — enhances quality but not blocking]

**Files Modified This Session:**
- [Filename/path]: [Nature of change]

**Next Session Should Start By:**
[Single specific action that resumes work correctly — e.g., "Load TMPL-001 and complete Section 7 Open Questions, then run Phase 6 checklist"]
```

### 14.2 Start-of-Session Resume (Agent Reads)

At the start of a new session, before any work begins, the agent:

1. Reads CTX-DOCSTD `pending_observations` for the most recent SESSION HANDOFF block
2. Reads the in-progress documents listed in "Work In Progress"
3. Confirms with the user: "Resuming from [date] session. Work in progress on [docs]. Should I continue with [Next Session Should Start By]?"
4. Waits for explicit user confirmation before executing

### 14.3 Clean Session Start (No Prior Handoff)

When no SESSION HANDOFF block exists in CTX-DOCSTD, the agent runs the
Context Setup wizard to establish project context from scratch.

---

## 15. Context Window Management

When generating a long document that risks exceeding the Claude context
window, the agent applies these strategies to maintain coherence.

### 15.1 Signals That Context Management Is Needed

The agent shifts to context-managed authoring when ANY of the following:
- The document being drafted is a TMPL-002 Technical Reference (typically 5,000–10,000 tokens)
- The document requires research results AND a full draft in the same session
- The conversation already contains >40,000 tokens before drafting begins

### 15.2 Section-by-Section Strategy

Rather than drafting the full document in one pass, the agent:

1. **Draft frontmatter and AI Summary first** — these are small and anchor all later work
2. **Draft one `##` section at a time** — complete each section before moving to the next
3. **After every 3 sections, produce a "section checkpoint"** summarizing what is written:
   ```
   CHECKPOINT — [N] sections complete
   Written: [Section headings]
   Remaining: [Section headings not yet written]
   Current document token estimate: ~[N] tokens
   ```
4. **Save the in-progress document to file** after each section if file tools are available
5. **If context window is approaching limit**: finalize what is written, save to file, produce SESSION HANDOFF block, and let the user resume in a new conversation

### 15.3 Cross-Section Reference Management

When references between sections are needed (e.g., Section 5 refers to
Section 3), the agent uses placeholder notation during drafting:

```
[REF-SECTION-3.2] — Placeholder: will resolve to actual section content after full draft
```

Before the Phase 6 checklist, the agent replaces all `[REF-SECTION-X.X]`
placeholders with the correct cross-reference text.


---

## 16. Relationship Type Vocabulary — Expanded & Research-Validated

The relationship types below represent the complete controlled vocabulary
for cross-references in this project. Each type is mapped to its closest
equivalent in established ontology standards (SKOS, Dublin Core Terms, PROV-O).
✅ `[VERIFIED — W3C SKOS 2009; Dublin Core Metadata Terms 2020; PROV-O W3C Recommendation 2013]`

### 16.1 Complete Relationship Vocabulary

Format for all cross-references: `[TYPE] → [Document ID, Section X.X] — [one-line reason]`

| Type | Meaning | Use When | SKOS / DC Mapping |
|------|---------|----------|------------------|
| `[DEPENDS_ON]` | This document's validity relies on the referenced document being current. | Document uses facts, schemas, or decisions from another as its foundation | `skos:related` (dependency) |
| `[SUPERSEDES]` | This document formally replaces the referenced document. | A newer version or decision replaces an older one | `dct:replaces` |
| `[SUPERSEDED_BY]` | The referenced document replaces this one. Add to the OLD document when superseding it. | Marking a document as having been replaced | `dct:isReplacedBy` |
| `[EXTENDS]` | This document adds depth or scope to the referenced document without replacing it. | Supplementary guidance, domain variant docs, addendums | `skos:broaderTransitive` |
| `[IMPLEMENTS]` | This document is a concrete realization of a specification or decision in the referenced document. | A procedure implements a decision; a tech ref implements an architecture decision | `skos:narrower` |
| `[INSTANTIATES]` | This document is a specific filled-in instance of a template defined in the referenced document. | A populated example instantiates a template | `skos:narrowerInstantive` |
| `[VALIDATED_BY]` | A claim or the entire document is backed by evidence in the referenced source. | Research findings, benchmark results, regulatory citations | `prov:wasDerivedFrom` |
| `[CONTRADICTS]` | This document contains claims in tension with the referenced document. Both exist; the conflict is documented. | When two documents disagree on a fact or recommendation | *(project-defined)* |
| `[APPLIES]` | This document applies a principle or decision from the referenced document to a specific context. | Procedure that applies a decision record; system that follows a standard | `skos:scopeNote` |
| `[PRECEDES]` | This document represents an earlier state or version than the referenced document. | Historical version of a decision; earlier phase of a project | `dct:isVersionOf` |
| `[SCOPES]` | The referenced document defines the boundary within which this document's content applies. | Domain constraints document that bounds a technical reference | `skos:scopeNote` |
| `[SEE_ALSO]` | Related but not formally dependent. The connection is informational. | Adjacent topics, related templates, further reading | `skos:related` |

### 16.2 Relationship Type Selection Guide

When choosing a type, ask these questions in order:

1. Does this document's validity **depend** on the other? → `[DEPENDS_ON]`
2. Does this document **replace** the other? → `[SUPERSEDES]`
3. Does the other document **replace** this one? → `[SUPERSEDED_BY]`
4. Does this document **add to** the other without replacing it? → `[EXTENDS]`
5. Is this document a **concrete realization** of an abstract specification? → `[IMPLEMENTS]`
6. Is this document a **filled-in instance** of a template? → `[INSTANTIATES]`
7. Does the other document **prove or support** a claim in this one? → `[VALIDATED_BY]`
8. Do these documents **disagree** on something? → `[CONTRADICTS]`
9. Does this document **apply a rule** from the other to a specific case? → `[APPLIES]`
10. Is the other document an **earlier version or state**? → `[PRECEDES]`
11. Does the other document **bound or limit** the scope of this one? → `[SCOPES]`
12. Are they just **related** but none of the above apply? → `[SEE_ALSO]`

---

## 17. Hybrid Search Optimization

Production RAG systems in 2026 predominantly use hybrid search — combining
keyword-based retrieval (BM25) with semantic vector search. Documents in
this system should be optimized for both retrieval methods.
🔬 `[RESEARCH_FINDING — confidence: HIGH — 2025–2026 production RAG engineering reports]`

### 17.1 Why Hybrid Search

Pure semantic search excels at concept-level matching but struggles with exact
term recall. Pure BM25 excels at exact terms but misses semantic equivalents.
Hybrid search combines both. For technical documentation, a 60–70% semantic
plus 30–40% BM25 weight balance is recommended as a starting point.
⚠️ `[FLAGGED — optimal weighting is corpus-dependent; test on your specific data]`

### 17.2 Optimizing for BM25 (Keyword Retrieval)

These authoring rules improve BM25 recall for technical documentation:

**Technical identifiers must appear verbatim in section text**, not only in headings.
A section titled "## Authentication" that never uses "authentication" in its body
will score poorly on BM25 for authentication queries.

**Abbreviations must be expanded on first use in each section.** Because chunks are
retrieved independently, expansions in the introduction do not help BM25 in later sections.

```
WRONG: "The API uses JWT. Tokens expire after 24 hours."
RIGHT: "The Payments API uses JSON Web Tokens (JWT) for authentication.
        JWT tokens expire 24 hours after issuance."
```

**Product names, version numbers, and error codes must appear exactly** as users
would type them. If users search for "ORA-00942", that exact string must appear in the chunk.

### 17.3 Optimizing for Semantic (Vector) Retrieval

**Sections must express complete conceptual units.** The 300–500 word target (Section 7)
is calibrated for this — incomplete sections have embeddings that don't fully represent
the concept.

**Abstract concepts must be explicitly defined.** "The event loop processes callbacks"
is harder to retrieve for "how does concurrency work in Node" than an explicit definition
that names the mechanism and the model.

**Use the vocabulary your readers will use in queries.** If users ask "how do I set
the password?", the section should contain both "password" and the actual field name
— not just the technical identifier.

### 17.4 Chunk Metadata for Hybrid Systems

When indexing in a hybrid search system, store these metadata fields alongside each chunk:

```json
{
  "document_id": "EX-002 AI-Doc-Skill-Technical-Reference",
  "template_type": "TMPL-002",
  "section_heading": "3.1 Phase 2 Research Routing Logic",
  "status": "active",
  "volatility": "stable",
  "confidence_overall": "high",
  "created": "2026-04-04"
}
```

Apply `status: active` as a default filter on all queries to exclude superseded content.

### 17.5 Embedding Model Chunk Size Guidance

The 300–500 word target is calibrated for general-purpose embedding models.

| Model Family | Optimal Chunk (approx.) | Notes |
|-------------|------------------------|-------|
| OpenAI text-embedding-3-large | 300–500 words | Matches our guidance |
| Cohere embed-v4 | 300–500 words | Similar range |
| BAAI/bge-m3 | 250–400 words | Slightly smaller |
| Custom fine-tuned | Test empirically | No general guidance |

⚠️ `[FLAGGED — validate on your specific corpus before committing to a chunk size]`

---

*Source: TMPL-000 Template Index v1.2 — PROJ-001 AI-Optimized Documentation Protocol*
*Phase 1: chunking, triggers, security, RAG lifecycle*
*Phase 2: negative space (§13), session handoff (§14), context window (§15)*
*Phase 3: expanded relationship vocabulary (§16), hybrid search optimization (§17)*