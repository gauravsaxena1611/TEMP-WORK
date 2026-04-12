# Entity Relationship Diagram (ERD) Specification
## Reference for generating-architecture-diagrams skill

Based on Peter Chen's ER Model (1976) with Crow's Foot notation for
cardinality. Adapted for draw.io XML generation.

---

## 1. Purpose

Shows entities (tables), their attributes, and the relationships between them.
Answers: "What data does this system store, and how are the tables related?"

### Audience
Database administrators, backend developers, data architects, data modelers.

### When to Use Over Other Types
- When documenting database schema design
- When planning new tables or relationships
- When communicating data model to stakeholders
- When analyzing existing database structure from migration files or schemas

---

## 2. ERD Detail Levels

### Conceptual ERD
- Entities and relationships only — no attributes, no data types
- High-level view for business stakeholders

### Logical ERD
- Entities, attributes (including primary/foreign keys), and relationships with cardinality
- Technology-neutral (no data types specific to a DBMS)

### Physical ERD
- Full detail: entities, attributes, data types, constraints, indexes
- Tied to a specific database technology (PostgreSQL, MySQL, Oracle, etc.)

**Default to Logical ERD** unless user specifies otherwise.

---

## 3. Element Styles

### Entity (Table)
- Shape: Rectangle with header section
- Header Fill: #DAE8FC, Border: #6C8EBF (strong entities)
- Header Fill: #FCF3CF, Border: #B7950B (weak entities — depend on another for existence)
- Body Fill: #FFFFFF
- Label: Table name in header (bold, 14px), attributes listed in body (11px)

```
draw.io style: shape=table;startSize=30;container=1;collapsible=0;childLayout=tableLayout;fixedRows=1;rowLines=1;fontStyle=1;align=center;resizeLast=1;fillColor=#DAE8FC;strokeColor=#6C8EBF;fontSize=14;
```

### Attribute Row
- Primary Key (PK): Bold, underlined, with key icon or `PK` prefix
- Foreign Key (FK): Italic, with `FK` prefix
- Regular: Normal weight
- Nullable: Suffix with `(NULL)` or omit for NOT NULL convention

### Display Format (per row in entity)
```
PK  id              BIGINT
FK  customer_id     BIGINT
    order_date      TIMESTAMP
    total_amount    DECIMAL(10,2)
    status          VARCHAR(20)
```

---

## 4. Relationship Styles (Crow's Foot Notation)

| Cardinality | Symbol | Meaning | draw.io endArrow |
|-------------|--------|---------|-----------------|
| One (mandatory) | `——\|` | Exactly one | `ERone` |
| One (optional) | `——O\|` | Zero or one | `ERzeroToOne` |
| Many (mandatory) | `——<` | One or more | `ERoneToMany` |
| Many (optional) | `——O<` | Zero or more | `ERzeroToMany` |
| Many-to-Many | `>——<` | Many on both sides | Usually decomposed via junction table |

### Relationship Line Style
```
endArrow=ERzeroToMany;startArrow=ERmandOne;html=1;strokeWidth=1.5;strokeColor=#666666;edgeStyle=orthogonalEdgeStyle;rounded=0;fontSize=10;
```

### Relationship Label
Every relationship MUST have a verb phrase describing the association:
- `Customer PLACES Order`
- `Order CONTAINS OrderItem`
- `Employee MANAGES Department`

Label positioned at midpoint of the relationship line, font size 10px.

---

## 5. Layout Guidelines

- Place central/core entities in the middle of the diagram
- Group related entities into clusters
- Place lookup/reference tables along the edges
- Minimize crossing relationship lines
- Use left-to-right or top-to-bottom reading direction for primary relationships
- Junction tables (for M:N) placed between the two related entities

---

## 6. Sizing Guidelines

| Element | Recommended Size |
|---------|-----------------|
| Canvas | 2500x2000 minimum (scales with entity count) |
| Entity box | 200px wide minimum, height varies by attribute count |
| Row height per attribute | 25px |
| Header height | 30px |
| Spacing between entities | Minimum 100px |
| Font: entity name | 14px bold |
| Font: attributes | 11px regular |
| Font: relationship label | 10px italic |

---

## 7. ERD Quality Checklist

```
☐ ERD level stated in diagram title (Conceptual, Logical, or Physical)
☐ All entities have a primary key identified
☐ All foreign keys reference valid parent entities
☐ Cardinality notation correct on both ends of every relationship
☐ Every relationship has a verb phrase label
☐ Weak entities visually distinguished (if applicable)
☐ No orphaned entities (every entity has at least one relationship)
☐ Junction tables present for many-to-many relationships
☐ Data types present for Physical ERD level
☐ No assumed entities or relationships — all from sources
☐ TBD markers on entities or attributes with unknown details
☐ Database technology noted in title (for Physical ERD)
```

---

## 8. Sources

| Source | Relevance |
|--------|-----------|
| Peter Chen, The Entity-Relationship Model (1976) | ER notation standard |
| Crow's Foot Notation (Gordon Everest, 1976) | Cardinality notation |
| draw.io ERD shapes library | Shape implementations for draw.io |
| ISO/IEC 11179 (Metadata Registries) | Data element naming conventions |
