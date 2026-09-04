# FrogButton delivery record

- Component: `FrogButton`
- Registry ID: `button`
- Category: `actions`
- Current status: `Experimental`
- Last reviewed: `2026-09-04`

## Specification

FrogButton triggers one caller-owned action. Use it for explicit actions; use navigation,
selection, or toggle components when those semantics apply. The caller owns action work,
enabled state, and loading state. Primary, Secondary, Outline, Ghost, and Destructive are
semantic variants. Small, Medium, and Large change visual scale while retaining the 48dp
target. Relevant states are default, pressed, focused, disabled, and loading. Touch,
keyboard, D-pad, and accessibility activation use button semantics. Loading preserves
content bounds, announces state, and blocks repeated activation. Button has no unique
window-size behavior and does not own IME behavior.

## API review

`onClick` and composable content express the action. Modifier controls layout; semantic
variant/size and state follow it. Slots accept Compose content without exposing an icon
provider. Immutable colors, shape, border, padding, width, and interaction-source escape
hatches preserve a short default call. Existing leading/trailing slot names remain for
named-argument compatibility. Disabled and loading cannot activate the callback.

## Delivery gates

| Gate | Audit | Evidence |
| --- | --- | --- |
| Specification | COMPLETE | This record defines purpose, ownership, variants, sizes, states, interaction, accessibility, and adaptive scope. |
| Public API | COMPLETE | Canonical FrogButton, semantic models, colors, and Defaults have KDoc and registry-checked signatures. |
| Implementation | COMPLETE | The library component is Compose-native and keeps Material and Hugeicons implementation details out of its API. |
| Theme / states | COMPLETE | Defaults resolve FrogTheme tokens for variants, enabled, disabled, pressed, focused, loading, and custom styling. |
| Accessibility | PARTIAL | Role, loading and disabled state, focus ring, and 48dp target are automated; human TalkBack and physical-input review remains. |
| Compose previews | COMPLETE | ButtonComponentPreviews renders the actual component across variants, sizes, states, dark theme, and customization. |
| Tests | PARTIAL | JVM and Compose tests cover defaults, enum parity, targets, loading, customization, restoration, and generated code; device execution remains. |
| Registry | COMPLETE | button.json contains canonical API, examples, accessibility facts, and schema-v2 lifecycle evidence. |
| Showcase | COMPLETE | ButtonShowcaseDefinition supplies the actual component, typed controls, examples, code, API, and accessibility to ComponentDetailScreen. |
| Web docs | COMPLETE | Shared ComponentDetailPage renders button.md and the isolated registered ButtonPreview. |
| API compatibility | COMPLETE | apiCheck baseline and exact registry signature validation pass with no Phase 10.1 API change. |
| Visual regression | PARTIAL | High-value native captures and pixel assertions exist; deterministic reviewed golden baselines and device capture remain deferred. |

## Promotion decision

FrogButton remains Experimental. Its implementation pipeline is complete, but human
TalkBack/physical-input evidence, connected-device execution, and reviewed deterministic
visual goldens are still required before promotion.
