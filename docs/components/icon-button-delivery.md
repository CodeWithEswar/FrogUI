# FrogIconButton delivery record

- Component: `FrogIconButton`
- Registry ID: `icon-button`
- Category: `actions`
- Current status: `Experimental`
- Last reviewed: `2026-09-04`

## Specification

FrogIconButton triggers one caller-owned action whose primary visual content is an icon.
Use it for compact actions whose meaning is readily apparent or clarified by adjacent context.
The caller owns action logic, enabled state, loading state, and the mandatory accessible description.
Filled, Tonal, Outline, and Ghost are semantic variants. Small (32dp), Medium (40dp), and Large (48dp)
change visual container scale while retaining the canonical 48dp minimum interactive touch target.
States include default, pressed, focused, disabled, and loading. Touch, keyboard, D-pad, and TalkBack
activation use button semantics. When loading, the icon is replaced by a centered progress indicator,
activation is blocked, and container size remains stable. An optional badge slot is anchored at TopEnd
as an overlay without mutating the minimum touch target or button layout.

## API review

`icon`, `contentDescription`, and `onClick` express the action. `contentDescription` is mandatory
(type `String`) because actionable icon controls must expose a screen reader label. Decorative icons
must not use FrogIconButton. Modifier controls layout; semantic variant and size follow it.
The composable badge slot accepts custom overlays without coupling the library to a concrete Badge type.
Immutable colors, shape, and interaction source allow customization while preserving a concise default call.

## Delivery gates

| Gate | Audit | Evidence |
| --- | --- | --- |
| Specification | COMPLETE | This record defines purpose, ownership, variants, sizes, states, interaction, accessibility, badge layout, and touch targets. |
| Public API | COMPLETE | Canonical FrogIconButton, semantic models, colors, and Defaults have KDoc and registry-checked signatures. |
| Implementation | COMPLETE | The library component is Compose-native and keeps Material and Hugeicons implementation details out of its public API. |
| Theme / states | COMPLETE | Defaults resolve FrogTheme tokens for variants, enabled, disabled, pressed, focused, loading, and custom styling. |
| Accessibility | PARTIAL | Role.Button, mandatory contentDescription, loading/disabled semantics, focus ring, and 48dp target automated; human TalkBack review pending. |
| Compose previews | COMPLETE | IconButtonComponentPreviews renders the actual component across variants, sizes, states, badges, and dark theme. |
| Tests | PARTIAL | Unit tests verify defaults, borders, touch targets, and variants; screenshot and physical input review pending. |
| Registry | COMPLETE | icon-button.json contains canonical API, examples, accessibility facts, and schema-v2 lifecycle evidence. |
| Showcase | COMPLETE | IconButtonShowcaseDefinition supplies the actual component, typed controls, examples, code, API, and accessibility to ComponentDetailScreen. |
| Web docs | COMPLETE | Shared ComponentDetailPage renders icon-button.md and the isolated registered IconButtonPreview. |
| API compatibility | COMPLETE | Deprecated legacy signature maintained for binary compatibility; new canonical API ready for apiDump. |
| Visual regression | PARTIAL | Component previews and inspector previews implemented; golden baseline images remain deferred. |

## Promotion decision

FrogIconButton is introduced as Experimental. Its implementation pipeline is complete across library,
showcase, registry, and documentation, but human TalkBack verification, physical input validation,
and reviewed visual regression baselines are required before promoting to Beta or Stable.
