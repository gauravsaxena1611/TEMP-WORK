# Sequence Diagram Specification
## Reference for generating-architecture-diagrams skill

Based on UML 2.5.1 sequence diagram notation adapted for draw.io XML generation.
Shows ordered interactions between system participants over time.

---

## 1. Purpose

Shows the chronological order of messages exchanged between participants
(systems, services, users) during a specific workflow or use case. Answers:
"In what order do these systems communicate, and what data do they exchange?"

### Audience
Technical stakeholders — developers, integration architects, QA engineers.

### When to Use Over Other Types
- When the **order of interactions** matters more than the structural layout
- When documenting API call chains, request-response flows, or event sequences
- When explaining how a specific use case or workflow executes across systems

---

## 2. Layout

```
  [User]      [Frontend]     [Backend]     [Database]     [External API]
    |              |              |              |               |
    |--- 1. Login -->|              |              |               |
    |              |--- 2. Auth --->|              |               |
    |              |              |--- 3. Query -->|               |
    |              |              |<-- 4. Result --|               |
    |              |              |--- 5. Call ----|-------------->|
    |              |              |<-- 6. Resp ----|--------------|
    |              |<-- 7. Token --|              |               |
    |<-- 8. UI -----|              |              |               |
    |              |              |              |               |
```

Participants (lifelines) arranged horizontally at the top.
Messages flow downward in chronological order.

---

## 3. Element Styles

### Lifeline (Participant)

**Person/Actor:**
- Shape: Rectangle header with dashed vertical line below
- Fill: #08427B, text color: #FFFFFF
- Label: Role name (bold, 13px)

**System/Service:**
- Shape: Rectangle header with dashed vertical line below
- Fill: #438DD5, text color: #FFFFFF
- Label: System name (bold, 13px), technology below (10px)

**Database:**
- Shape: Cylinder header with dashed vertical line below
- Fill: #D5DBDB, text color: #333333
- Label: DB name (bold, 13px)

**External System:**
- Shape: Rectangle header with dashed vertical line below
- Fill: #999999, text color: #FFFFFF
- Label: System name (bold, 13px)

### Lifeline Vertical Line
- Style: `dashed=1;strokeColor=#999999;strokeWidth=1;`
- Extends from bottom of header to bottom of diagram

### Activation Bar (Optional)
- Narrow filled rectangle on the lifeline showing active processing
- Fill: #E8EAF6, Border: #5C6BC0
- Width: 16px, Height: varies by message span

---

## 4. Message Arrow Styles

| Message Type | Arrow Style | Line Style | Usage |
|-------------|-------------|-----------|-------|
| Synchronous Request | Filled arrowhead → | Solid | REST call, method invocation |
| Synchronous Response | Open arrowhead ← | Dashed | Return value, HTTP response |
| Asynchronous Message | Filled arrowhead → | Solid, open arrow | Kafka publish, event fire |
| Self-Message | Loop arrow to self | Solid | Internal processing step |
| Create Message | Filled arrowhead → to new lifeline | Dashed | Object/instance creation |

### Arrow Label Format
```
[Step#]. [Description] ([Protocol])
```

**Examples:**
- `1. POST /api/auth/login (REST)`
- `2. SELECT user WHERE email=? (JDBC)`
- `3. Publish OrderCreated event (Kafka)`
- `4. 200 OK + JWT token (REST)`

### draw.io Style Fragments

**Synchronous Request:**
```
endArrow=block;endFill=1;html=1;strokeWidth=2;strokeColor=#2874A6;edgeStyle=orthogonalEdgeStyle;
```

**Synchronous Response:**
```
endArrow=open;endFill=0;html=1;strokeWidth=1.5;strokeColor=#2874A6;dashed=1;edgeStyle=orthogonalEdgeStyle;
```

**Asynchronous Message:**
```
endArrow=open;endFill=0;html=1;strokeWidth=2;strokeColor=#8E44AD;edgeStyle=orthogonalEdgeStyle;
```

---

## 5. Combined Fragments (Optional)

### Loop Fragment
- Dashed rectangle enclosing repeated messages
- Label: `loop [condition]` in top-left corner
- Fill: none, Border: #666666, dashed

### Alt Fragment (If/Else)
- Dashed rectangle with horizontal divider
- Label: `alt [condition]` and `else` sections
- Fill: none, Border: #666666, dashed

### Opt Fragment (Optional)
- Dashed rectangle enclosing optional messages
- Label: `opt [condition]`

---

## 6. Numbering

Messages are numbered sequentially top-to-bottom:
- Main flow: 1, 2, 3, 4, ...
- Sub-steps: 2.1, 2.2, 2.3 (for internal steps within step 2)
- Response messages: can be unnumbered or use same number as request with "return" label

---

## 7. Sizing Guidelines

| Element | Recommended Size |
|---------|-----------------|
| Canvas | 2000x1500 minimum (scales with participants and messages) |
| Lifeline header | 140px wide, 50px tall |
| Spacing between lifelines | 180-220px horizontal |
| Spacing between messages | 50-70px vertical |
| Arrow label font | 10px |
| Lifeline header font | 13px bold |

---

## 8. Sequence Diagram Quality Checklist

```
☐ All participants shown as lifelines with headers
☐ Messages in correct chronological order (top to bottom)
☐ Every message has a numbered label with description
☐ Protocol/technology noted on each message
☐ Synchronous vs asynchronous distinguished by arrow style
☐ Response messages shown for synchronous calls
☐ Self-messages shown for internal processing (where relevant)
☐ No fabricated interactions — all from sources
☐ TBD markers on messages with unknown details
☐ Diagram title includes workflow/use case name
```

---

## 9. Sources

| Source | Relevance |
|--------|-----------|
| UML 2.5.1 Specification (OMG) | Sequence diagram notation standard |
| draw.io UML shapes library | Shape implementations for draw.io |
| Anthropic Skill Authoring Best Practices | Progressive disclosure pattern |
