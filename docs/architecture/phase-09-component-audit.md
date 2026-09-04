# Phase 09 component implementation audit

## Existing architecture audit

The Phase 06–08 baseline already supplied a shared Android detail screen, registry-backed
API metadata and compiled examples, a bounded Drawer preview host, reusable inspector,
code, API, accessibility and Markdown renderers, per-component Showcase definitions,
and the web `ComponentPreviewMode → ComponentPreviewRegistry → preview module` chain.
Button and Drawer already used the same detail chrome and generic web page.

Classification:

- **KEEP:** canonical Button/Drawer implementations, theme/defaults, shared detail shell,
  preview workspace, inspector primitives, native code/API/accessibility renderers,
  generic web component page, preview registry, API baselines, and route generation.
- **REFINE:** Drawer style escape hatches, Button preview matrix, Drawer web visual parity,
  lifecycle evidence, and contributor guidance.
- **REUSE:** `FrogColorPicker`, `FrogOverlayHost`, `FrogComponentPreview`, generated
  registry projections, compiled example regions, and public API validation.
- **MIGRATE:** registry records from schema v1 to v2 lifecycle evidence.
- **REMOVE DUPLICATION:** no additional per-component detail, API, accessibility, code,
  or preview-shell implementations were introduced.
- **DEFER:** device TalkBack speech/traversal, physical keyboard/tablet runs, foldable
  hinge placement, and screenshot baselines pending a connected deterministic device.

## Changes

The mandatory standard is documented in `component-lifecycle.md` and linked from
contributor guidance. Registry schema v2 records meaningful visual, interaction, theme,
adaptive, preview, and test evidence. Validation checks those paths, canonical KDoc,
Defaults KDoc, Compose preview use, component test references, and the shared web preview
contract.

Drawer now exposes an optional `Shape?` override while preserving presentation-aware
theme shapes when null. Its shared inspector adds shape and all five Drawer color roles
through the existing color picker transaction. Draft changes update the actual Drawer
preview and generated Kotlin; Apply commits and Cancel/Back/dismiss retains prior values.
Button gains compact canonical previews for default, variants, sizes/states, and custom
dark styling through the shared preview scaffold.

The web quality matrix is rendered from registry data. Drawer representative visuals now
match the canonical 48% scrim, 32×3 handle, 200ms normal motion, accessible close labels,
and snapshot version.

## Reference component status

**FrogButton:** kept as the primary API/theme/interaction reference; changed only to add
the missing canonical preview matrix and lifecycle evidence. Public API is unchanged.

**FrogDrawer:** kept on the shared detail architecture; changed to add custom shape and
shared color editing, corresponding API metadata/docs/previews, and lifecycle evidence.
It remains Experimental because manual accessibility/device review is pending.

## Validation

Validation results are recorded after the Phase 09 implementation run. Required gates:

```powershell
npm.cmd test
npm.cmd run docs:build
.\gradlew.bat apiCheck verifyPublicApiBoundary verifyArchitecture testDebugUnitTest lintDebug assembleDebug assembleDebugAndroidTest --console=plain
git -c core.safecrlf=false diff --check
```

API changes are reviewed and accepted with `apiDump` only after the candidate diff shows
the intended Drawer `shape` parameter and no unrelated public surface.
