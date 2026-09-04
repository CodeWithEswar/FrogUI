# Phase 07 public API review

## Existing API Audit

Audit completed before changing public declarations. The entry working tree already
contained the shared Button/Drawer detail migration; this review uses that surface
as its starting point, not the older Git HEAD or a claimed published release.

| API | Current state | Keep | Refine | Breaking risk |
| --- | --- | --- | --- | --- |
| `FrogButton` | One caller-controlled content-slot API, semantic variant/size, loading, full width, interaction source | Signature, names/order, defaults and icon slots | KDoc; minimum target width for tiny content | No descriptor change planned; intentional bounds correction |
| `FrogIconButton` | Explicit label, same color/size defaults; spinner adds child progress semantics; pressed overlay unused | Signature, label ownership, sizes | KDoc; pressed color and decorative spinner semantics | Behavioral bug fixes |
| Button models/Defaults | Immutable colors, finite enums, remembered default source; selected color overrides | Public constructors, copy/default methods and full-width meaning | Parameter/property documentation | No rename/reorder planned |
| `FrogDrawer` | State and Boolean overloads share one renderer; legacy Boolean-side adapter remains public | State/visible APIs, slots, presentation, logical side, callbacks | Deprecate legacy adapter with migration; complete ownership/embedded KDoc | Warning only; retain old callable descriptor |
| `FrogDrawerState` / Saver | Saveable requested visibility, internal setters; suspend methods update immediately | Constructor, helper, operations and Saved value | Correct animation-completion claims; restoration test | Preserve timing and signatures |
| Drawer Defaults/colors/enums | Immutable colors; 400/600dp widths; 64dp drag threshold; unused public 220ms constant | Useful defaults and finite modes | Deprecate unused duration constant, document theme motion | Warning only; constant retained |
| `FrogOverlayHost` | Public bounded embedding used by shared workspace; local flag internal | Real reusable embedding contract and signature | Explicit caller Back/focus ownership | No change planned |
| `FrogTheme` / Defaults | Public local-token access and provider; Material bridge internal | All parameters and color factories | KDoc | No change planned |
| Foundation | `FrogColors`, palette, typography, shapes, spacing, elevation and motion models | Constructors, properties, default values, spec helpers | Document model contracts and review obligations | Avoid gratuitous constructor/spec removals |
| Registry/testing | Public tooling models and fixture are separate nonpublished modules | Existing tool boundaries | Verify no published signature leakage | Outside published ABI baseline |
| API tooling | No selected validator, `.api` baselines or compatibility CI gate | Existing Gradle/AGP versions and module boundaries | Add Kotlin-aware release-AAR ABI checks | First baseline, not historical compatibility proof |

No public experimental annotation, sealed component mode, extension API or custom
builder framework exists in the three published modules. Implementation layouts,
composition locals and state setters are already private/internal. `FrogIconButton`
and `FrogOverlayHost` are intentional public companion APIs, not separate catalog
entries. No source moves or visibility removals are justified merely by lack of a
registry card. They remain covered by the components ABI baseline.

## Standards Established

[API design](api-design.md) now defines parameter families, state ownership,
semantic enums, scoped slots, immutable defaults, module boundaries, meaningful
KDoc, experimental lifecycle, source/binary/behavior review and deprecation. The
contributor guide and PR template link the proposal and review checklist. The
standards preserve established signatures instead of imposing new naming churn.

## FrogButton

Kept the single content-slot signature, variant/size enums, `leadingIcon` and
`trailingIcon`, remembered non-null interaction source, constructors/defaults and
`fullWidth`. Full width has actual meaning because both surface and target expand.
Completed ownership/loading/slot/default KDoc. Tiny content or zero padding now
retains minimum target width as well as height. IconButton now applies its configured
pressed overlay and hides decorative progress semantics while retaining its label.
These are semantic bug fixes; the public ABI and parameter names/order are unchanged.

## FrogDrawer

Kept state-driven and Boolean-driven canonical overloads, presentation/side enums,
all slots and dismiss callbacks. Added warning-level deprecation to the legacy
Boolean-side overload and unused `AnimationDurationMs` constant, preserving both
declarations. Migration documents the legacy Bottom default, footer actions and
separate navigation/Back callbacks. No inaccurate automatic replacement is offered.
Updated consumer previews to the canonical overload. KDoc now distinguishes native
modal and bounded embedded behavior, inset handling, focus and caller-owned dismissal.

## Props Architecture

New APIs use required semantic values/callbacks, Modifier, semantic configuration,
state/layout, slots, style and advanced interaction, with content last. Existing
parameter order remains intact. No Button state object, Drawer size enum, behavior
mode, giant config, animation parameter or speculative component was added. Drawer
inspector footer/long-content controls are explicitly labelled demo content.

## Defaults

Retained `FrogButtonDefaults`, `FrogDrawerDefaults`, their immutable colors models,
Button size/variant enums, `FrogTheme`/`FrogThemeDefaults`, and the foundation color,
palette, typography, shape, spacing, elevation and motion contracts. Added useful
parameter/property KDoc. Existing public constructor/copy/destructuring and motion
interop methods remain available. The unused Drawer duration constant is deprecated,
not silently repurposed or removed. No consumer defaults were reordered or renamed.

## State

Button remains caller-controlled. Drawer's optional helper remains saveable and
supports external open/close triggers; the Boolean overload remains an alternative.
State KDoc now accurately says that open/close/snap update requested visibility
immediately and do not await or bypass a rendered transition. A JVM test restores
saved visibility into an independent owner and verifies later mutations are isolated.

## Accessibility

KDoc explains role, label ownership, disabled/loading behavior, minimum targets,
custom-style obligations, focus and overlay boundaries. The Button target-width
and IconButton progress/pressed-color corrections preserve a simple default action
contract. Three new public-API Compose tests cover these behaviors and Drawer
restoration/caller-owned dismissal; they compile but await a connected device.
Existing prior-phase device results are not claimed as execution of these new tests.

## Public Visibility

No existing public declarations were moved internal: audit found no justified
breaking removal. Rendering helpers, composition locals, state setters and Showcase
models were already private/internal or outside published modules. Obsolete public
contracts were deprecated instead. ABI extraction filters Kotlin internal visibility,
and a new boundary check rejects published Material, Showcase, registry, testing,
Hugeicons, CommonMark and Coil references. Intentional public IconButton and overlay
host companion APIs are included in the baseline even without separate catalog cards.

## Registry / Showcase

Kotlin stays authoritative. A build-time lexical signature check now compares the
canonical first overload's property names, types, defaults and order with registry
JSON. Tests reject drift and cover nested generics, lambda defaults and scoped slots.
This is a guard for the current grammar, not a full Kotlin compiler or runtime parser.
Drawer metadata/prose now describe requests rather than animation progress or
automatic closure. Both components retain the shared API renderer and generated-code
architecture; token expressions/default omission and vendor-independent icon slots
are preserved. The docs catalog was regenerated. A compiled consumer fixture covers
named/positional calls, scoped slots and the retained deprecated overload.

The wider JVM run exposed a stale registry test that still expected only Button;
it now expects the two implemented reference components while rejecting placeholders.
The missing-route Android assertion was aligned with the shared error message.

## Compatibility

JetBrains Binary Compatibility Validator 0.18.1 extracts Kotlin-aware JVM signatures
from the actual release AARs. A small build-logic adapter uses AGP's public artifact
API because this project uses built-in Kotlin instead of the older `kotlin-android`
plugin expected by BCV's automatic integration. ASM and Kotlin metadata dependencies
are build-only; generated consumer POMs contain none of them.

First reviewed baselines:

- `frogui-foundation/api/frogui-foundation.api`
- `frogui-theme/api/frogui-theme.api`
- `frogui-components/api/frogui-components.api`

Each final baseline exactly matches the extraction saved before Phase 07 behavior
changes. All three `apiCheck` and `verifyPublicApiBoundary` tasks pass. `apiBuild`
writes candidates; the explicitly reviewed `apiDump` created these initial files.
CI and library `check` use `apiCheck` and never update baselines. `.api` files have
fixed LF line endings. A negative fixture removed FrogButton's method from a copy:
the configured comparison task failed and displayed that exact missing signature.
Real baselines were untouched by the negative test.

This establishes future regression protection and proves no signature drift within
this phase. It does not claim comparison against a previously published artifact;
the product is still an unpublished snapshot. The earlier shared-detail migration
is part of this phase's entry surface, not retrospectively certified here.

## Validation

Executed on Windows with `GRADLE_USER_HOME=C:\Users\hp\.gradle`:

```powershell
.\gradlew.bat apiBuild --console=plain
.\gradlew.bat apiBuild testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest --console=plain
.\gradlew.bat apiDump --console=plain
.\gradlew.bat apiCheck verifyArchitecture testDebugUnitTest :app:assembleDebugAndroidTest :app:lintDebug generatePomFileForReleasePublication --console=plain
.\gradlew.bat -I build/phase07-api-mismatch.init.gradle :frogui-components:apiMismatchFixture --no-configuration-cache --console=plain
npm.cmd test
npm.cmd run docs:build
git -c core.safecrlf=false diff --check
```

- 41 JVM tests pass: app 24, components 11, registry 6.
- 17 Node tests and the pinned 26-icon inventory check pass.
- Release ABI extraction, all three compatibility/boundary checks, architecture
  checks, app/test APK builds and consumer fixture compilation pass.
- Lint: zero errors, 35 warnings. These concern SDK/dependency versions and existing
  resources/vectors; four version notices come from the pinned build-only ABI tooling.
- Docs build and whitespace check pass. The existing Vite large-chunk notice remains.
- Negative ABI fixture fails as intended on the missing FrogButton signature.
- POM review confirms no ABI-tooling dependencies are published.
- `adb devices -l` returned no devices. New public-API tests and the outstanding
  shared-detail device/window suite were not executed or visually certified.

Evidence: `build/phase07-api-before.log`, `build/phase07-before-api`,
`build/phase07-build.log`, `build/phase07-api-dump.log`,
`build/phase07-verification.log`, `build/phase07-api-negative.log`,
`build/phase07-tools-tests.log`, `build/phase07-docs-build.log`, and module reports.
CI configuration was updated locally; no hosted run or publication was performed.

## Breaking Changes

None in public signatures, named arguments, parameter order, constructors or defaults
during Phase 07. The two warning-level deprecations can affect consumers that promote
warnings to errors; their replacement guidance is documented. Minimum target width,
IconButton pressed feedback and decorative progress semantics are intentional behavior
corrections. Existing shared-detail changes remain separate prior-phase work.

## Deferred

Reconnect an Android device for the new Compose behaviors and pending shared-detail
responsive/visual tests. Human TalkBack speech/traversal, physical keyboard/tablet,
minimum API and hinge/posture review remain release checks. No component was promoted
to Stable. A consistent Kotlin opt-in annotation policy, further companion catalog
entries, richer persistent-drawer behavior and future component proposals are deferred
until their consumer requirements justify API changes. No signature was changed to
manufacture uniformity with hypothetical components.
