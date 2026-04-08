# Setup Guide — Generating Architecture Diagrams

**Version:** 3.0  
**Last Updated:** March 29, 2026  

---

## Quick Start (2 Minutes)

### Claude.ai Projects
1. Download `generating-architecture-diagrams.zip`
2. Go to **Claude.ai → Project → Settings → Skills → Upload**
3. Upload the ZIP
4. Start a conversation: *"Create a high level architecture diagram for [your app]"*

### Claude Code
```bash
cp -r generating-architecture-diagrams ~/.claude/skills/
# Or per-project:
cp -r generating-architecture-diagrams .claude/skills/
```

### VS Code (Claude Model)
Copy the folder into `.claude/skills/` in your workspace root.

---

## What Happens on First Use

When you first ask Claude to generate a diagram, the skill will:

1. **Load the appropriate reference specs** based on diagram type
2. **Check for context files** (CTX-PALETTE, CTX-DOMAINS, CTX-APPREG)
3. Since this is first use, **no context files exist yet** — that's fine:
   - Colors: Default palette from `references/color-palette.md` is used
   - Domains: Generic Internal/External grouping is used
   - App Registry: Started fresh from your source documents
4. **Ask you** for application name, source material, and diagram type
5. **Generate the diagram** following all standards

### Optional: Customize on First Use

After your first diagram, Claude may ask:
- *"Would you like to customize the color palette for your organization?"*
- *"How does your organization classify system domains?"*

If you provide answers, context files are created for future sessions.

---

## Living Context Architecture

This skill uses the Living Context pattern [ARCH-001]. Organization-specific
data lives in context files, not in the skill itself. This means:

- **Same skill works across different organizations** — just different contexts
- **Colors, domains, and app names persist** across conversation sessions
- **Claude learns incrementally** — each diagram session can discover new apps

### Context Files

| Context | What It Stores | Created When |
|---------|---------------|-------------|
| CTX-PALETTE | Your organization's color scheme | User provides custom colors |
| CTX-DOMAINS | How your org groups systems | User explains domain structure |
| CTX-APPREG | Known applications and IDs | Built incrementally per diagram |

### Where Context Files Live

| Platform | Location |
|----------|----------|
| Claude Code | `.claude/contexts/` in project directory |
| Claude.ai Projects | Project Knowledge Files |

---

## Supported Diagram Types

| Type | Trigger Examples | Best For |
|------|-----------------|---------|
| **HLLFD** | "architecture diagram", "high level flow" | Full system view with data flows |
| **C4 Context** | "system context diagram", "C4 level 1" | Executive/business stakeholder view |
| **C4 Container** | "container diagram", "what's inside" | Technical stakeholder view |
| **C4 Component** | "component diagram", "zoom into [service]" | Developer implementation view |
| **Sequence** | "sequence diagram", "message flow" | Time-ordered interactions |
| **DFD** | "data flow diagram", "how data moves" | Data pipeline / compliance view |
| **ERD** | "entity relationship", "database diagram" | Database schema design |
| **Deployment** | "infrastructure diagram", "cloud architecture" | DevOps / platform view |
| **Integration Map** | "integration map", "interface diagram" | Enterprise integration landscape |

---

## Source Material Tips

The better your source material, the better the diagram. Here's what works best:

| Source Type | Quality | Tips |
|-------------|---------|------|
| Design documents | ⭐⭐⭐⭐⭐ | Most complete — systems, integrations, protocols all documented |
| Architecture review transcripts | ⭐⭐⭐⭐ | Rich detail but may need cleanup — Claude extracts facts from conversation |
| Confluence pages | ⭐⭐⭐⭐ | Good structure — paste content or upload export |
| PowerPoint decks | ⭐⭐⭐ | Often high-level — good for C4 Context, may need supplement for HLLFD |
| Meeting notes | ⭐⭐⭐ | Variable quality — works well with follow-up questions |
| Verbal description | ⭐⭐ | Least complete — expect more TBD markers and gap reports |

### Multi-Source Is Best

Provide 2+ sources when possible. Claude cross-validates across documents
and produces higher-confidence diagrams with fewer TBD markers.

---

## Iterating on Diagrams

### Via Conversation
```
"Add a Kafka integration between ATPC and the Reporting Platform"
"Remove System X — it was decommissioned"
"Change the arrow between A and B from REST to gRPC"
```

### Via File Upload
Upload a modified `.drawio` file and say:
```
"Here's the updated diagram — I added System X manually. 
Now add the database connections and apply color standards."
```

### Version Tracking
Each iteration produces a new file with incremented version:
- `ATPC-HLLFD.drawio` → `ATPC-HLLFD-v2.drawio` → `ATPC-HLLFD-v3.drawio`

---

## Troubleshooting

| Issue | Solution |
|-------|---------|
| Skill doesn't trigger | Check description is on single line in YAML; verify skill is uploaded correctly |
| Diagram won't open in draw.io | Check XML well-formedness; try opening in diagrams.net online |
| Colors look wrong | Check if CTX-PALETTE context overrides are in place; compare with `references/color-palette.md` |
| Too many TBD markers | Provide richer source material or answer Claude's follow-up questions |
| Wrong diagram type generated | Be explicit: "Create a C4 Container diagram, not an HLLFD" |
| Skill triggers for non-diagram requests | Use negative triggers in description to exclude; check for keyword overlap with other skills |

---

## Companion Skills

| Skill | How It Helps |
|-------|-------------|
| **verification** | Run before diagramming to validate source document claims |
| **research-orchestrator** | Gather comprehensive source material before diagramming |
| **skills-updater** | Keep context files fresh across sessions |

---

## Further Reading

- **C4 Model**: c4model.com — Official C4 diagram specification
- **draw.io AI Guide**: drawio.com/doc/faq/ai-drawio-generation — Official AI generation rules
- **UML 2.5.1**: omg.org/spec/UML — Sequence and deployment diagram notation
- **ARCH-001**: Living Context Framework — How context files work
- **ARCH-002**: Context Template Generator — Template specifications
