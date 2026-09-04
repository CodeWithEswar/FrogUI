# Phase 10.1 per-component delivery audit

## Existing audit

Phase 09 already established one canonical implementation, theme-aware Defaults, shared
Compose previews, lifecycle evidence in registry schema v2, the shared native detail
screen, isolated web previews, API baselines, tests, and documentation for FrogButton and
FrogDrawer. Both components use actual public library composables in the Showcase.

The delivery areas classified as COMPLETE before Phase 10.1 were public API,
implementation, theme/state behavior, Compose previews, registry, Showcase, web docs,
and API compatibility. Accessibility and visual regression were PARTIAL because device
and reviewed golden evidence remains pending. Tests were PARTIAL because Android test
APKs compile but no device was connected. The behavior specification existed across
KDoc, registry, and prose but had no concise per-component review artifact.

The first missing process gate was a canonical component delivery record and an
enforceable relationship between that record and lifecycle promotion.

## Change

Every registered component now requires `docs/components/<id>-delivery.md`. The template
captures identity, behavior specification, API review, twelve ordered delivery gates,
evidence, and the lifecycle decision. FrogButton and FrogDrawer have truthful records;
both remain Experimental.

Registry validation checks record existence, component/ID/category/status parity, exact
gate coverage and order, allowed audit values, nonempty evidence, and promotion rules.
Beta records cannot contain MISSING, OUTDATED, or DUPLICATED gates. Stable records must
mark every gate COMPLETE and still provide the existing stability review.

The contributor guide and pull request template now make the delivery record part of a
normal component change. The record is Markdown consumed only by build tooling; no
runtime registry renderer, reflection, universal props object, database, or alternate
Showcase/docs architecture was added.

## Component decisions

- **FrogButton:** remains Experimental pending connected-device TalkBack/physical-input
  evidence and reviewed deterministic visual goldens.
- **FrogDrawer:** remains Experimental pending connected-device TalkBack, keyboard/tablet
  focus, foldable placement, and reviewed deterministic visual goldens.

## Validation

The Phase 10.1 implementation passed these local gates on September 4, 2026:

- `npm.cmd test`: 26 tests passed, including missing-record, identity-drift, Beta-blocker,
  and incomplete-Stable rejection cases.
- `npm.cmd run registry:validate`: 2 components validated against schema v2.
- `npm.cmd run docs:build`: registry/docs generation, 32-color theme parity, TypeScript,
  and Vite production build passed. The existing large-chunk advisory remains.
- `.\gradlew.bat apiCheck verifyPublicApiBoundary verifyArchitecture testDebugUnitTest
  lintDebug assembleDebug assembleDebugAndroidTest --console=plain`: passed. Existing
  results contain 50 JVM tests with no failures, errors, or skips; lint has no errors and
  35 advisory warnings.
- `git -c core.safecrlf=false diff --check`: passed.

`adb devices` reported no connected device. Android test APK compilation passed, while
device execution, human TalkBack/physical-input review, and deterministic golden review
remain pending and are marked PARTIAL in both component records.
