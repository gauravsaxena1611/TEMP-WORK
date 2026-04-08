# Workflow Numbering Standards
## Reference for generating-architecture-diagrams skill

---

## 1. Core Principles

- Each workflow uses a **unique numbering format** — never mix styles within one workflow
- Numbers appear on **both arrows and notes** — always synchronized
- Sequence follows **left → right** or **top → bottom** reading direction
- Sub-steps use hierarchical notation
- Parallel steps use branching notation

---

## 2. Numbering Formats by Workflow Type

### Workflow 1 — Numeric (Main Business Flow)
**Used for**: Primary happy path, most critical business workflow
```
Format:    1 → 2 → 3 → 4 → 5
Sub-steps: 1.1 → 1.2 → 1.3
Parallel:  3a → 3b → 3c (merge at 4)
```

### Workflow 2 — Uppercase Alphabetical (Update Flow)
**Used for**: Secondary workflow, update/modify processes, alternate paths
```
Format:    A → B → C → D
Sub-steps: A.1 → A.2
Parallel:  C-a → C-b
```

### Workflow 3 — Roman Numerals (Batch Flow)
**Used for**: Batch processing, schedulers, background jobs
```
Format:    I → II → III → IV
Sub-steps: II.i → II.ii
```

### Workflow 4 — Lowercase Alphabetical (Manual Upload)
**Used for**: Admin workflows, manual operations, file upload flows
```
Format:    a → b → c → d
Sub-steps: b.1 → b.2
```

### Workflow 5 — E-Prefix (Error/Exception)
**Used for**: Error handling, exception flows, retry logic
```
Format:    E1 → E2 → E3
```

### Workflow 6 — K-Prefix (Kafka/Messaging)
**Used for**: Asynchronous messaging, Kafka, MQ, event-driven flows
```
Format:    K1 → K2 → K3
```

### Workflow 7 — D-Prefix (Database Operations)
**Used for**: Direct DB calls, stored procedure execution
```
Format:    D1 → D2 → D3
```

---

## 3. Sub-Step Rules

**Allowed formats:**
- `1.1`, `1.2`, `1.3`
- `A.1`, `A.2`
- `II.i`, `II.ii`
- `b.1`, `b.2`

**NOT allowed:**
- `1a` (ambiguous — is this sub-step or parallel?)
- `1-A` (mixing numbering styles)
- `1_1` (underscore notation)

---

## 4. Parallel Processing Notation

Use letter suffix for parallel branches that execute simultaneously:
```
3a (Branch A)
3b (Branch B)    →  merge at 4
3c (Branch C)
```

---

## 5. Conditional Flow Notation

Use decision suffix with outcomes:
```
2? Yes → 3
2? No  → E1
```

---

## 6. Async Event Trigger Notation

For Kafka or event-driven flows, show publish and consume separately:
```
2 → K1 (publish event to topic)
K2 (consumer receives from topic)
K3 (consumer processes message)
```

---

## 7. DB Interaction Notation

For direct database operations within a workflow:
```
4 → D1 (read from DB)
D2 (write to DB)
```

---

## 8. Arrow Label Format

Every arrow MUST have a label in this format:
```
[Step#]. [Action/Data Description]
```

**Examples:**
- `1. TPM User initiates relationship onboarding`
- `3. Pull Relationship & Control Data`
- `B. Pulls Entitlement Data`
- `K1. Publish assessment status event`
- `D1. Persist integration data`
- `E1. Log error and notify admin`

---

## 9. Workflow Notes Section Format

Group notes by workflow. Each step gets one concise sentence:

```
Workflow 1 — Create Assessment
1. TPM User initiates relationship onboarding in Aravo (External SaaS)
2. Aravo sends notification to ATPC via integration layer
3. ATPC pulls relationship and control data from Aravo
4. ATPC creates assessment record in Application DB
5. Assessment appears in Business Submission User's task list

Workflow A — User Review Cycle
A. Business Submission User opens assessment and completes questionnaire
B. Backend pulls entitlement data from EEMS
C. Supervisor (BAO) attests to submission accuracy
D. TPRO validates compliance controls
E. PFICRM user reviews and completes assessment

Workflow I — Batch Data Sync
I. Control Poller triggers on schedule
II. Pulls latest control data from Aravo TPM Integration
III. Persists data to Integration DB
```

---

## 10. Limits

| Guideline | Recommendation |
|-----------|---------------|
| Workflows per diagram | 3 ideal, 5 maximum |
| More than 5 workflows | Split into multiple diagrams |
| Steps per workflow | 3–10 recommended |
| More than 10 steps | Consider sub-diagrams or decomposition |

---

## 11. Color Alignment

Each workflow type has a recommended arrow color (see color-palette.md Section 6):

| Workflow | Arrow Color | Hex |
|----------|-------------|-----|
| Create (1,2,3) | Blue | #2874A6 |
| Update (A,B,C) | Dark Blue | #1A5276 |
| Batch (I,II,III) | Green | #229954 |
| Manual (a,b,c) | Orange | #E67E22 |
| Kafka (K1,K2) | Purple | #8E44AD |
| DB (D1,D2) | Gray | #7B7D7D |
| Error (E1,E2) | Red | #C0392B |
