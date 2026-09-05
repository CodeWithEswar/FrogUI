# FrogFloatingActionButton delivery record

- Component: `FrogFloatingActionButton`
- Registry ID: `fab`
- Category: `actions`
- Current status: `Experimental`
- Last reviewed: `2026-09-04`

## Specification

FrogFloatingActionButton represents a prominent contextual action that floats above the main content surface.
It supports three presentation forms: Regular (56dp standard container, 24dp icon), Small (40dp compact container,
20dp icon, preserving the canonical 48dp minimum interactive touch target), and Extended (48dp height container,
presenting an icon alongside a visible text label with support for smooth expansion and collapsing).
Visibility is caller-owned via the `visible` boolean prop; when false, the button animates out and is completely
suppressed from accessibility semantics and interaction. Interaction states include default, pressed, focused,
and disabled. Elevation provides resting, pressed, and focused surface separation, backed by FrogTheme tokens.
Reduced-motion user preferences are strictly honored via instantaneous state snaps without spatial or scale animations.

## API review

`icon`, `contentDescription`, and `onClick` express the core action. `contentDescription` is mandatory (type `String`)
across all presentations because floating action buttons require an accessible name. Decorative icons inside the slot
must use `contentDescription = null`. Modifier controls outer layout; presentation, label, expanded, enabled, visible,
elevation, colors, shape, and interactionSource follow it. Composable slots for `icon` and `label` accept arbitrary
composables without coupling the library to third-party icon packages.

## Delivery gates

| Gate | Audit | Evidence |
| --- | --- | --- |
| Specification | COMPLETE | This record defines purpose, ownership, Regular/Small/Extended forms, expansion, visibility, accessibility, and touch targets. |
| Public API | COMPLETE | Canonical FrogFloatingActionButton, FrogFabPresentation, FrogFabElevation, FrogFabColors, and Defaults have KDoc and registry-verified signatures. |
| Implementation | COMPLETE | The library component is Compose-native, uses state hoisting, and avoids Material or Hugeicons coupling in its public API. |
| Theme / states | COMPLETE | Defaults resolve FrogTheme tokens for colors, shapes, sizing, elevation, focus ring, press tone, and reduced motion. |
| Accessibility | PARTIAL | Role.Button, mandatory contentDescription, hidden-state semantic exclusion, focus ring, and 48dp target automated; human TalkBack verification pending. |
| Compose previews | COMPLETE | FrogFabPreviews renders the component across Regular, Small, Extended (expanded/collapsed), states, elevation, and dark theme. |
| Tests | PARTIAL | Unit tests verify dimensions, elevation defaults, colors, and presentations; Compose UI test verifies interaction, accessibility, and visibility. |
| Registry | COMPLETE | fab.json contains canonical API properties, examples, accessibility facts, and schema-v2 quality metadata. |
| Showcase | COMPLETE | FabShowcaseDefinition supplies the actual component, typed controls, examples, code, API, and accessibility to ComponentDetailScreen. |
| Web docs | COMPLETE | Shared ComponentDetailPage renders fab.md and the isolated registered FabPreview. |
| API compatibility | COMPLETE | New public types ready for apiDump; baseline validation checks in place. |
| Visual regression | PARTIAL | Previews and inspector previews implemented; golden baseline images remain deferred. |

## Promotion decision

FrogFloatingActionButton is introduced as Experimental. Its implementation pipeline is complete across library,
showcase, registry, and documentation, but human TalkBack verification, physical input validation,
and reviewed visual regression baselines are required before promoting to Beta or Stable.
