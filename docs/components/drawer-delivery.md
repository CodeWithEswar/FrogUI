# FrogDrawer delivery record

- Component: `FrogDrawer`
- Registry ID: `drawer`
- Category: `overlays`
- Current status: `Experimental`
- Last reviewed: `2026-09-04`

## Specification

FrogDrawer presents contextual content while preserving the current task. Use it for a
temporary inspector, filter, or secondary workflow; use destination navigation or a
persistent pane when content should replace or permanently accompany the task. The caller
owns open/dismiss intent through FrogDrawerState or the Boolean overload. Auto, Bottom,
and Side are presentation semantics rather than visual variants. Relevant states include
closed, open, and dismissing with short or scrolling content. Auto resolves from FrogUI
window classes. Bottom supports drag dismissal; Back, scrim, close, and accessibility
dismiss provide non-gesture paths. Modal focus, logical Start/End placement, keyboard
insets, large text, and RTL are part of the contract.

## API review

State and dismissal callbacks are explicit. Modifier controls the panel, presentation and
side are semantic, and header/content/footer slots remain composable. Colors and optional
shape are theme-aware escape hatches. The deprecated Boolean-side overload supplies a
migration path. Demo controls such as long content and footer visibility never enter the
public signature or generated Kotlin.

## Delivery gates

| Gate | Audit | Evidence |
| --- | --- | --- |
| Specification | COMPLETE | This record defines purpose, ownership, presentation, states, adaptive behavior, dismissal, focus, IME, and RTL. |
| Public API | COMPLETE | Canonical state and Boolean overloads, presentation models, colors, shape, Defaults, and KDoc are registry/API checked. |
| Implementation | COMPLETE | The actual Compose drawer uses one modal/embedded implementation with private layout, scrim, handle, and transition helpers. |
| Theme / states | COMPLETE | Light, dark, custom colors, custom shape, reduced motion, closed/open transitions, and adaptive presentations use FrogUI contracts. |
| Accessibility | PARTIAL | Pane/dismiss semantics, close target, Back, focus entry, and Showcase restoration are implemented; human TalkBack and device focus review remains. |
| Compose previews | COMPLETE | DrawerDetailPreviews renders actual compact bottom, expanded side, and custom-theme drawers through FrogComponentPreview. |
| Tests | PARTIAL | State, defaults, restoration, adaptive layout, bounded overlay, dismissal, inspector, generated code, font scale, and RTL have tests; device execution remains. |
| Registry | COMPLETE | drawer.json contains canonical API, examples, accessibility facts, adaptive classes, and schema-v2 lifecycle evidence. |
| Showcase | COMPLETE | DrawerShowcaseDefinition uses ComponentDetailScreen, FrogOverlayHost, typed shared controls, real preview, examples, code, API, and accessibility. |
| Web docs | COMPLETE | Shared ComponentDetailPage renders drawer.md and the isolated registered DrawerPreview. |
| API compatibility | COMPLETE | The reviewed optional Shape ABI update is in the baseline and apiCheck passes; Phase 10.1 adds no API change. |
| Visual regression | PARTIAL | Window/theme/font/RTL captures exist for review; deterministic goldens and connected-device capture remain deferred. |

## Promotion decision

FrogDrawer remains Experimental. Connected-device TalkBack, keyboard/tablet focus,
foldable placement, and reviewed deterministic visual baselines remain promotion blockers.
