# Showcase verification — 2026-09-03

## Completed checks

| Check | Result |
| --- | --- |
| Native app/debug test APK builds | Passed with the pinned dependencies and current module graph. |
| `verifyArchitecture` | Passed; registry, docs, dependency contract, and required unit suites remain valid. |
| App JVM suite | 14 tests passed, including 10 new navigation/parser/highlighter/contrast cases. |
| Node registry/docs tests and icon generation check | 13 tests passed; 23 semantic Hugeicons aliases match the pinned official source. |
| Android suite on RMX3312, Android 15 / API 35 | 19 tests passed, including the real activity/IME check. |
| Property selector follow-up | Targeted device regression verifies selection semantics and unchanged option bounds for Ghost/Small. |
| Android lint | Zero errors; 31 warnings for existing SDK/dependency update advisories, unused brand resources, and vector paths. Two dependency-version advisories reflect newly pinned parser artifacts. |
| Native render matrix | 50 captures: Home, preview, code, API, and docs in 10 configurations. |

The matrix includes 360×800, 390×844, 412×915, 600×900, 840×1050, 1000×800,
900×500 landscape, 360 dp at 200% font size, 840 dp at 160%, and 390 dp RTL.
Both themes are included. The window harness consumes physical device insets,
which do not scale with forced window sizes; real edge-to-edge activity checks
cover platform behavior separately. These are native render captures, not pixel
goldens or tests on ten different physical devices.

Device interactions verified navigation role/selection, minimum dock/back targets,
keyboard focus and Enter activation, toolbar back, nested dock visibility,
appearance changes, saved navigation/search restoration, exact clipboard source,
inline copy success, Markdown headings/fence actions, and search with the real IME.
The keyboard hides the dock and dismissing it restores the selected destination.
Code text token contrast is at least 4.5:1 in both palettes.

Visual review led to two refinements: a two-row dock for large text and a property
selector redesign with stable dimensions, clear borders/checks, and horizontal
scrolling for Variant and Size. The API header now shares the body column spacing.

## Evidence

Local, ignored build outputs:

- `build/showcase-verification.log`: passing 19-test Gradle device run and lint.
- `build/showcase-instrumentation-final.log`: earlier passing 18-test native run.
- `build/properties-test.log`: focused property selection regression.
- `build/showcase-final-build.log`: architecture/build/unit/lint gate.
- `app/build/reports/androidTests/connected/debug/index.html`: Gradle device report.
- `app/build/reports/lint-results-debug.html`: lint report.
- `build/showcase-qa/`: render captures, selector before/after captures, and HTML contact sheet.

The targeted property-selector test runs after the full suite; the full suite was
not repeated for that local styling adjustment. APK assembly and that focused
interaction check cover the follow-up.

## Remaining manual coverage

TalkBack speech/traversal has not been audited by a human. Three-button navigation,
physical tablet/Chromebook input, and an API 24 device still need manual coverage.
The current device uses gesture navigation. No screenshot-golden baseline or
performance benchmark is claimed. This upgrade does not promote Button from
Experimental to Stable.
