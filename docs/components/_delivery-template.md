# Component delivery record template

- Component: `FrogComponent`
- Registry ID: `component`
- Category: `category`
- Current status: `Experimental`
- Last reviewed: `YYYY-MM-DD`

## Specification

Document purpose, when to use it, when to choose another component, caller-owned state,
semantic variants, sizes, states, primary interaction, accessibility semantics,
adaptive behavior, gesture/IME/keyboard behavior, and default behavior.

## API review

Record required content and callbacks, Modifier placement, configuration models, slots,
style overrides, interaction hooks, invalid combinations, and compatibility impact.

## Delivery gates

Use only `COMPLETE`, `PARTIAL`, `MISSING`, `OUTDATED`, or `DUPLICATED`. Evidence must
name real source, tests, generated artifacts, validation, or a concrete remaining gap.

| Gate | Audit | Evidence |
| --- | --- | --- |
| Specification | MISSING | Replace with evidence. |
| Public API | MISSING | Replace with evidence. |
| Implementation | MISSING | Replace with evidence. |
| Theme / states | MISSING | Replace with evidence. |
| Accessibility | MISSING | Replace with evidence. |
| Compose previews | MISSING | Replace with evidence. |
| Tests | MISSING | Replace with evidence. |
| Registry | MISSING | Replace with evidence. |
| Showcase | MISSING | Replace with evidence. |
| Web docs | MISSING | Replace with evidence. |
| API compatibility | MISSING | Replace with evidence. |
| Visual regression | MISSING | Replace with evidence. |

## Promotion decision

State the selected lifecycle status, the evidence supporting it, and every unresolved
promotion blocker. Experimental records may contain any truthful state. Beta records
cannot contain MISSING, OUTDATED, or DUPLICATED gates. Stable requires every gate to be
COMPLETE plus the registry stability review.
