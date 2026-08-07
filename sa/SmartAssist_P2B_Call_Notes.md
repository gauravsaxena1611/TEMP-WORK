# SmartAssist Phase 1 vs Phase 2 — P2B Positioning Call Notes

**Date:** 2026-08-07
**Topic:** How to present Phase 2 (agentic) vs Phase 1 (generative) to reviewers; P2B / ARB approval status
**Participants:** Speaker 1 (lead — will contact Cal and send the summary email) · Gaurav (Speaker 2 — solution/delivery). Others referenced: Seenu, Vaibhav, Vinod, Ashok, Cal, Lynn, Jessie, Hakeem, Shaji.

---

## 1. Purpose of the call

Reviewers ("these people") want to understand, at a system level, **what Phase 2 improves or adds to the existing system**. Their underlying assumption is that Phase 2 is built *on top of* Phase 1. The team's position is the opposite: **Phase 1 and Phase 2 are independent** in architecture, code, delivery mechanism, and usage. The call was about how to present this clearly without inviting excessive follow-up questions, plus a status check on the stalled P2B / ARB approval.

## 2. Phase 1 vs Phase 2 — the core distinction

| Aspect | Phase 1 (last year) | Phase 2 (this year) |
|---|---|---|
| Nature | Plain-vanilla **Generative AI** solution (agents did not exist in the market yet) | **Agentic AI** solution |
| Architecture | Simple, few layers — user → CIA screen → REST service picks up obligation & publication text → LLM processes → returns to UI | Complex agentic architecture — MCP servers, MCP tools, agents developed/onboarded within ARB using ADK |
| Delivery / interface | Integrated **directly into the CIA workflow** (an "AI button" on the workflow itself; input → output) | Delivered via a **separate chatbot**, a median layer between the user and the CIA workflow |
| Capabilities | **One** capability: extraction of actionable components — summaries generated from an obligation | **6–7 capabilities** (extension of scope) |
| Module | Independent module | Separate module altogether |

**Independence is the key message:** nothing in architecture or code is shared, and usages are very different. Phase 2 is *not* built on top of Phase 1 — described as "zameen–aasman ka farak" (a world of difference).

## 3. What is shared / carried over

Only two overlapping components from last year:

- **Reg service** — used to extract publication and obligation data (a common service everyone uses).
- **Prompts** — possibly one or two prompts from last year's prompt-engineering effort, and even those were tweaked to suit the agentic solution.

Everything else is new / built on ADK. Reuse of prior deployment architecture is effectively zero.

## 4. Platform / tooling context (Phase 2)

- **ARB** — Citi-introduced platform; a Citi-defined, protected boundary/compartment where agents are deployed. Heavy guardrails and a **kill switch** that triggers on foul or biased prompts/commands. Citi-owned infrastructure. (ARB became a required step and was a temporary roadblock.)
- **ADK** — Google's Agent Development Kit; the development environment, subscribed via Google. Every agent must be onboarded/developed within ARB using ADK.

## 5. Phase 1 scope going forward

- Phase 1 service is **not being decommissioned**; it stays as-is in its own module.
- It is **not in Phase 2 scope** and there is no ARB requirement to migrate it into the agentic solution.
- Merging Phase 1 into Phase 2 would only be considered later in the year **if time permits** or if business directs it (e.g., wanting last year's capability inside the chatbot). No current plan to touch it.

## 6. Decisions

1. Add **two slides** to the P2B deck:
   - **Slide A — Phased delivery:** Phase 1 architecture diagram vs Phase 2 architecture diagram.
   - **Slide B — Capabilities:** what was delivered in Phase 1 (single actionable-component capability, integrated into the CIA workflow) vs what Phase 2 targets (6–7 capabilities via chatbot) — framed to make clear the two are separate, not stacked.
2. Position deliberately and simply to **clarify concerns without opening ground for more questions**.
3. Produce a **combined P2B deck** (Phase 1 + Phase 2); review it tomorrow.

## 7. Action items

| # | Owner | Action |
|---|---|---|
| 1 | Gaurav | Share resources: Confluence page, Phase 1 P2B deck; prepare the two slides (Phase 1 & Phase 2 diagrams + capabilities comparison) and place them appropriately in the combined deck. |
| 2 | Gaurav | **Brief Seenu** (solution architect for both phases) so he can articulate the architecture differences/commonalities and be prepared to take the lead if such questions come up in the review. (Vaibhav also spans both phases; Vinod was last year's SA.) |
| 3 | Gaurav | Follow up **today** with the Agentic AI Forum on the request to reverse the **conditional approval → full approval** (Agent Flow now ready; screenshots/evidence already sent; no reply yet). |
| 4 | Speaker 1 | Draft and send the **summary email** to reviewers capturing what was discussed; **CC Gaurav** to confirm. |
| 5 | Speaker 1 | Connect with **Cal today** to get the **P2B approved**. |
| 6 | Both | **Review the combined P2B deck tomorrow.** |

## 8. Risks & open issues

- **P2B not approved / project going red.** P2B has been open for 2+ months (now month 8); 7th flagged as the last date. Cal created the ARB and P2B/P2D subtasks **without first approving P2B**, so PTS planned execution cannot be closed. PMO (Jessie) may need to raise a **project-level risk**, escalating to Hakeem and Shaji — which would put the project on the organisation's radar despite the work being complete. Open question raised: why split into separate P2B ARB and P2D ARB rather than the traditional/larger ARB route.
- **Conditional approval from Agentic AI Forum.** Issued because the team was not yet onboarded into Agent Flow. Agent Flow is now ready; the Agent Flow / Agent Tech matrix items the forum wanted are complete. Awaiting the forum's reply to grant full approval (to be submitted to ARB instead of the conditional one).
- **Environments / deployment.** Deployed and tested in SIT and UAT — working seamlessly. Not yet in PROD; **PROD deployment happens only at month-end**.
- **ARB scheduling.** Lynn will schedule the review **next week** (this-week request was declined). Gaurav has messaged Jessie.

## 9. Suggested next step

Speaker 1 to circulate the summary email + updated combined P2B deck; team to review tomorrow and hold the reviewer session next week, with Seenu (and/or Vaibhav) available to field architecture questions.

---

| Version | Date | Change Type | Description | Reason |
|---|---|---|---|---|
| 1.0 | 2026-08-07 | Initial | Notes generated from call transcript | Capture discussion, decisions, actions, risks |
