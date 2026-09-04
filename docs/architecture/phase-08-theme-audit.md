# Phase 08 — theme audit and implementation record

## Audit before implementation

The repository already has one Compose theme runtime and immutable `FrogColors`,
`FrogTypography`, `FrogSpacing`, `FrogShapes`, `FrogElevation` and `FrogMotion` models.
Button and Drawer consume that runtime. No Frog-owned `Native*` token names remain.
The syntax highlighter's Native name describes its implementation, not a legacy theme.

| Group | Decision | Evidence / scope |
| --- | --- | --- |
| Colors | KEEP / REFINE | Preserve semantic roles and zinc palette; review destructive and muted contrast, expand the private Material bridge. |
| Typography | KEEP | Ten named TextStyles, system fonts, scalable sizes and explicit line heights already exist; bridge them to internal Material primitives. |
| Spacing | KEEP | Existing xxs–x7l scale is coherent; migrate shared rhythm only. |
| Shapes | KEEP / REFINE | Keep xs–xl/full; derive Drawer corners from local shapes through Defaults. |
| Elevation | KEEP | Four Dp levels; restrained tonal surfaces and borders remain the primary depth cues. |
| Motion | REFINE | Preserve 120/200/280ms and existing signatures; move system reduction into library runtime, stop decorative loading motion, remove independent clamps. |
| Sizing | CREATE | Touch targets and icon/control sizes are duplicated across Button, icon Button and Showcase. |
| Adaptive | CREATE | Shell uses 600/840 with rounding, Drawer/detail use 620, and the preview labels 480dp Medium. Use one constraint-fed resolver. |

### Literal classification

Audit searches covered `Color(...)`, Black/White, dp/sp literals, rounded shapes,
tween/spring calls in foundation, theme, components and app Kotlin sources.

- **Semantic candidates:** 48dp interaction targets; shared icon/control sizes;
  Drawer header/body gaps; code surface/plain/comment colors; duplicated breakpoints;
  theme-derived motion durations. These should consume the appropriate token/default.
- **Component dimensions:** Button padding/icon gap, border/focus thickness,
  Drawer side/bottom widths, drag threshold, handle geometry and content insets.
  Keep ownership in component Defaults rather than growing global tokens.
- **Intentional local values:** pressed black/white overlays, the Drawer black scrim,
  syntax-category hues, checkerboard and HSV picker colors, icon path strokes,
  Showcase sidebar/inspector widths and tiny navigation indicators. They describe
  local behavior or visualization, not a missing global color/spacing role.
- **Preview/test values:** explicit light/dark backdrop colors, simulated widths,
  screenshot dimensions and custom-color fixtures are deliberate inputs.

The preview and inspector recreated typography/shapes/spacing/elevation/motion.
The Showcase observed system animator preferences, but library consumers did not.
The Foundation explorer lacked semantic color roles, elevation, sizing and adaptive
examples. Web Foundation docs referenced nonexistent typography and touch-target
properties and duplicated incorrect spacing values. Web CSS already had semantic
surface/foreground variables but components often used raw zinc utility classes.

### Contrast findings before changes

Relative luminance calculation: white on light destructive #EF4444 = **3.76:1**;
white on existing dark destructive #DC2626 = **4.83:1**. Light muted foreground
#71717A on surface #FAFAFA = **4.63:1**, but on muted #E4E4E7 = **3.81:1**.
Use the darker existing destructive and zinc foreground steps for these pairs.
Static token contrast does not establish accessibility of arbitrary compositions.

### Deliberately deferred

No token compiler, dynamic-color default, public Material contracts, global state
manager, new spring collection, font download, fold posture/hinge model, web dp-to-px
translation or Showcase-specific public theme tokens. Existing token constructor
and component signatures must remain compatible; accept reviewed additive ABI only.

## Platform references

The resolver uses Android's compact/medium/expanded width thresholds and actual
layout constraints. See [Android window size classes](https://developer.android.com/develop/adaptive-apps/guides/use-window-size-classes).
Minimum interaction sizing follows [Compose accessibility defaults](https://developer.android.com/develop/ui/compose/accessibility/api-defaults).

## Kept

All existing token model names, constructor parameters, enum members and component
signatures remain. The six existing foundation groups, monochrome identity, system
font families, component-owned padding/widths, native Drawer implementation and
transactional Token/Custom color editor were preserved. No token compiler or new
global mutable manager was introduced. Previous Phase 06/07 workspace changes remain.

## Changed

- Added `FrogSizing`, `FrogAdaptive` and `FrogWindowSizeClass` with documented immutable
  values, input validation and one exact width resolver.
- Added read-only `FrogTheme.sizing`, `adaptive` and `reduceMotion` accessors, plus the
  optional `ProvideFrogThemeEnvironment` composition provider. The original FrogTheme
  function descriptor and parameter order are unchanged.
- Nested FrogTheme calls inherit omitted non-color groups. Preview and inspector theme
  switches no longer recreate custom typography, spacing, shapes, elevation or motion.
- Moved system animator observation from Showcase into the theme runtime. One root
  observer supplies nested themes; Compose inspection uses deterministic defaults.
- Completed the private Material bridge, including fixed colors, containers, errors,
  inverse colors, all neutral surface roles, typography, shapes and local content color.
- Migrated shared target/glyph sizing, Drawer rhythm, code-surface colors and code text
  style to the actual theme. Component-specific geometry stays local.
- Rebuilt the Foundation explorer and corrected the web theme documentation. Web CSS
  now has all 16 semantic colors in both palettes, checked against Kotlin on every docs
  build. Shared web surfaces/text/borders consume semantic variables.
- Fixed web dark variants to follow the selected `.dark` class rather than continuing
  to follow the system after a user selected Light. Foundation navigation targets the
  corresponding section, including elevation, motion, sizing and adaptive entries.

## Colors

The existing sixteen semantic roles remain. Zinc neutral surfaces retain their light
and dark defaults; primary remains dark-on-light / light-on-dark. Light destructive
now uses the existing #DC2626 palette step, matching dark, with white text at 4.83:1.
Light mutedForeground uses Zinc600 (#52525B) so supporting text also works on the
muted fill. Destructive Button focus now uses the dedicated focusRing token rather
than drawing an outline identical to its fill.

Tests check normal text on background, primary, secondary and destructive fills,
plus muted text on base/elevated/muted surfaces, in both palettes. Focus is checked
against adjacent neutral surfaces at 3:1. Translucent structural borders, disabled
colors and arbitrary custom compositions are not asserted to satisfy these ratios.
The raw palette's Destructive/Success/Warning members remain compatible primitives;
they do not imply extra semantic FrogColors roles.

## Typography

The hierarchy is unchanged: display 32/40, titleLarge 24/32, title 20/28, heading
18/24, subheading 16/22, body 15/22, bodySmall 13/18, label 12/16, caption 11/14,
code 13/18 (font size / line height in sp). System fonts scale with Android settings;
code remains monospace. Code blocks now honor the actual code TextStyle instead of
overriding its line height. The explorer displays every current style's actual size,
line height and weight. Preview/device fixtures cover 1.0, 1.3 and 1.5 font scales;
device execution is still pending, as described below.

## Spacing

Preserved xxs/xs/sm/md/lg/xl/xxl/xxxl/x4l/x5l/x6l/x7l =
2/4/6/8/12/16/20/24/32/40/48/64dp. Drawer shared header/body gaps now consume this
scale. Its 18dp content inset and 10dp footer inset remain Drawer defaults, as do
Button padding and icon-to-label gaps. The explorer renders the complete scale as
actual-width bars, with no separate manually maintained numeric list.

## Shapes

Preserved xs/sm/md/lg/xl = 4/6/10/14/18dp and full. Button still maps Small to sm
and Medium/Large to md. The new composable `FrogDrawerDefaults.shape` derives Bottom
from xl top corners and Side from lg content-facing corners; custom CornerBasedShape
values work too. This intentionally changes default side corners from 16dp to 14dp.
Existing fixed `bottomShape` and `sideShape` helpers retain their old values for
direct callers. Auto is resolved before calling the new shape helper.

## Elevation

Preserved none/low/medium/high = 0/1/3/6dp. Components continue to express depth mainly
with surface tone and borders. The Foundation explorer shows all four actual shadow
values in both nested palettes without inflating the elevations for demonstration.

## Motion

Preserved 120/200/280ms fast/normal/large durations and established easing profiles.
Drawer now uses the configured normal duration and enter/exit curves directly;
Showcase no longer clamps theme durations to an independent range. `FrogMotion.reduced()`
returns a zero-duration copy, preserving easing; `isReduced` identifies all channels
off. The existing spring helper snaps for a fully reduced profile.

`FrogTheme.reduceMotion` combines local preference, the live Android disabled animator
setting and a fully reduced profile. Android reduction cannot be overridden with false.
Button press scale disappears; color/state feedback remains immediate. Button/IconButton
show a static decorative loading arc rather than running an infinite rotation. Drawer
transitions complete immediately. Showcase Settings and one-shot Foundation samples use
this same runtime; the library does not persist user preferences.

## Sizing

`FrogSizing` owns a minimumTouchTarget of 48dp, iconSmall/Medium/Large = 16/18/20dp,
and controlSmall/Medium/Large = 32/40/48dp. Dimensions are finite and positive; targets
can be enlarged, not reduced below 48dp. Explicit parent constraints can still limit
layout. Visual height remains a minimum for text buttons; growing text can exceed it.

Button and IconButton read local sizing. `FrogButtonDefaults.controlHeight(size)` and
`iconSize(size)` expose theme-aware resolutions for consumers' icon slots. Existing
ButtonSize metrics and MinTouchTarget remain canonical reference values, sourced from
FrogSizing, for compatibility. Showcase back buttons remain FrogIconButton with visible
backgrounds; other controls and inspector rows share the target token. Compact selector,
toggle and color-slider geometry from earlier work remains.

## Adaptive

`FrogAdaptive.windowSizeClass(availableWidth)` uses finite Compose dp constraints,
without integer rounding: Compact below 600dp, Medium from 600 to below 840dp, Expanded
from 840dp. Custom immutable thresholds are supported. Unit tests include 360, 390,
412, 599.99, 600, 839.99, 840, 1000 and 1400dp, plus invalid inputs and custom policies.

The shell uses the policy for bottom navigation / rail / sidebar. Detail content
resolves its remaining width after navigation: Medium/Expanded can show a 264dp
persistent inspector, while text scale above 1.3 keeps it modal. Auto Drawer resolves
its own native-window or embedded-host width: Compact Bottom, otherwise Side. This
intentionally aligns the former 620dp transitions with the shared default of 600dp.

Preview requests are 360/600/840dp (Medium previously requested 480dp). Requests are
clamped to actual available width; the preview labels the actual width/class rather
than pretending a phone can contain an unscaled expanded host. The Foundation adaptive
demo embeds the real Auto Drawer. No global app window state is changed by previews.

## FrogTheme usage

```kotlin
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.foundation.adaptive.FrogAdaptive
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.theme.FrogThemeDefaults
import io.github.codewitheswar.frogui.theme.ProvideFrogThemeEnvironment

// Inside a composable:
FrogTheme(colors = FrogThemeDefaults.lightColors().copy(
    primary = Color(0xFF1D4ED8), primaryForeground = Color.White,
)) {
    ProvideFrogThemeEnvironment(
        sizing = FrogTheme.sizing.copy(minimumTouchTarget = 56.dp),
        adaptive = FrogAdaptive(),
        reduceMotion = true,
    ) {
        FrogButton(onClick = {}) { Text("Save changes") }
        FrogTheme(darkTheme = true) {
            // Dark palette; inherited custom sizing and reduced motion.
            FrogButton(onClick = {}) { Text("Nested preview") }
        }
    }
}
```

Pass `colors = FrogTheme.colors` to retain the current custom palette in a nested
theme; omitting colors deliberately selects the requested/system palette. Omitted
non-color groups inherit. Outside a theme, accessors retain safe canonical defaults.
Material types are implementation details, absent from FrogUI's public signatures.

## FrogButton and FrogDrawer

Button's existing variants, colors, shape, padding, border, slots and state signatures
are unchanged. Colors/shapes/typography/motion and live sizing resolve through the
theme; loading and caller-owned interactions keep their existing semantics.

Drawer retains its state and Boolean overloads, native/embedded behavior, dismissal
ownership, RTL handling, insets, footer/preview slots and deprecated compatibility
overload. Color defaults remain semantic; scrim black at 48% is an intentional local
overlay default. Side width 400dp, bottom maximum 600dp and drag threshold 64dp remain
component-owned. Only adaptive resolution, local shapes, shared rhythm and motion
resolution changed.

## Showcase and documentation

The native explorer has Colors, Typography, Spacing, Shapes, Elevation, Motion, Sizing
and Adaptive pages. Semantic colors display current/light/dark values and purpose;
all typography/spacing/shape/elevation values come from the runtime. Theme usage is
available in the explorer, KDoc and the web Foundation page. Five compiled previews
cover light, dark, custom colors, large text and reduced motion.

Both preview canvas and pinned inspector use nested FrogTheme without rebuilding
other groups. The picker includes all sixteen semantic roles, preserves Token versus
Custom identity and resolves current preview colors. Code/Markdown/API/inspector
surfaces consume the shared theme; syntax-category hues remain local code tokens.

Web `/foundation` and `/foundations/theme` render the real theme documentation.
Existing section routes remain, with new elevation/motion/sizing/adaptive navigation.
The small `tools/theme/verify.mjs` check reads the existing Kotlin palette/default
grammar and compares all 32 CSS color values. It fails on unsupported/missing values;
it neither generates runtime code nor puts token objects into the component registry.
Numeric layout remains native to each platform. Source links and runtime-rendered
Android values are authoritative for token definitions.

## Compatibility review

Release-AAR API candidates were compared against copies captured before Phase 08 in
`build/phase08-before-api`. There are **zero removed existing API lines** across the
three published modules. Accepted additions are the two new models and width enum,
motion helpers, three theme accessors, environment provider, two Button defaults
resolvers and one Drawer shape resolver. Existing data-class generated methods remain.

Behavior changes are intentional: inherited nested non-color defaults, improved light
contrast, live system/local reduced motion, direct duration overrides, 600dp adaptive
boundary, theme-derived side corners, theme-aware sizing and private Material mapping.
Consumers depending on the former nested reset can explicitly pass fresh token groups.
Baselines were accepted with `apiDump` as a separate maintainer operation; CI/check
still compare them and never dump automatically.

## Validation

Commands run from the repository root, with `GRADLE_USER_HOME=C:\Users\hp\.gradle`:

```text
.\gradlew.bat :app:compileDebugKotlin :frogui-foundation:apiBuild :frogui-theme:apiBuild :frogui-components:apiBuild --console=plain
.\gradlew.bat testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :frogui-theme:assembleDebugAndroidTest --console=plain
.\gradlew.bat apiDump --console=plain
.\gradlew.bat apiCheck verifyPublicApiBoundary verifyArchitecture testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :frogui-theme:assembleDebugAndroidTest :app:lintDebug :frogui-components:lintDebug :frogui-theme:lintDebug :frogui-foundation:lintDebug --console=plain
.\gradlew.bat :app:assembleDebug :app:assembleDebugAndroidTest :frogui-theme:assembleDebugAndroidTest :frogui-theme:testDebugUnitTest :frogui-theme:lintDebug apiCheck --console=plain
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug --console=plain
npm.cmd test
npm.cmd --prefix docs run build
git diff --check
adb devices -l
```

Results: **49 JVM tests passed** (foundation 5, theme 3, components 11, registry 6,
app 24); **19 Node tests passed**, including negative CSS-drift fixtures, and the
26-icon inventory check passed. All three API checks and public-boundary checks,
architecture verification, app/test APK builds and docs TypeScript/build passed.
Lint: **0 errors, 35 existing warnings** in the aggregate app report; foundation,
theme and components have no issues. Docs retains its existing Vite chunk-size notice.

A combined `apiDump apiCheck` invocation was rejected by Gradle's task-output ordering
validation. It was corrected by running explicit dump first and verification in a
separate invocation, without making checks depend on a baseline-writing task.

The local web Foundation was visually reviewed in desktop and narrow layouts, with
Light/Dark/System selection, semantic swatches and adaptive navigation. The temporary
browser viewport and theme preference were restored after review.

**Device limitation:** ADB lists no device. New native theme isolation/system preference
tests, the 360/390/412/600/840/1000dp Button/Drawer screenshot matrix, static reduced
loading check and large-text Foundation navigation/captures compile but have not run.
Earlier phase screenshots/device results are not evidence for this implementation.
Run these and the existing shared detail/public contract suites once the device is
reconnected; human TalkBack and final native visual review remain pending.

Logs are under `build/phase08-*.log`. Screenshot fixtures write future captures to
the app's `files/showcase-qa` external directory. The debug APK is available under
`app/build/outputs/apk/debug/app-debug.apk`. No changes were committed, published or deployed.
