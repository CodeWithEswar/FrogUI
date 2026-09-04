# Shared component detail system

## Audit

Button previously owned its detail header, preview toolbar, tab routing, contextual
page stack, API presentation, examples and accessibility layout in `ButtonScreen`.
Drawer had a separate `DrawerScreen` with its own preview card, controls, code/API
presentation and accessibility content. Their layout and behavior could diverge.
The existing Button inspector, color transactions and native code/Markdown
renderers were retained as the reference behavior.

## Shared Components Created

The app's `showcase/detail` package now owns:

- `ComponentShowcaseDefinition`, `ComponentShowcaseFactory`, `PreviewCapabilities`,
  `ComponentDetailState`, `ComponentDrawerPage` and `ComponentDrawerContent`.
- `ComponentDetailHeader`, `ComponentStatusBadge` and `ComponentDetailLayout`.
- `ComponentPreviewWorkspace`, `ComponentInspectorHost` and preview settings.
- `ComponentDocSection`, `ComponentCodeContent`, `ComponentApiReference`,
  `ComponentApiPropertyDetail`, `ComponentAccessibilityContent`,
  `ComponentExampleSection` and `ComponentLongFormDocs`.
- Typed API categories, enum-value guidance and accessibility facts.

`ComponentDetailScreen` resolves a definition from `ShowcaseRegistry`; it contains
no component-specific layout switch. The existing `ComponentPreviewCanvas`,
`FrogShowcaseTabs`, `FrogCodeBlock`, `FrogCodeSnippet`, `FrogInlineCode`,
`FrogApiTable`, `FrogMarkdown` and `FrogMarkdownDocument` remain canonical renderers.
`FrogInspectorText` joins the shared compact enum, boolean and section controls.

## Button Migration

`buttonShowcaseDefinition` supplies Button state, the real public `FrogButton`
preview, property inspector, code, examples, API guidance and accessibility facts.
Button-specific state, semantic color values, live drafts, contrast calculations,
Apply/Cancel/Reset and variant defaults remain in the Button/color-picker packages.
The common shell owns its tabs, contextual window and documentation presentation.
The inspector retains a pinned live button so size, variant, full width and color
changes affect the visible component. Disabled-color editing previews that state.

## Drawer Migration

`drawerShowcaseDefinition` uses the same shell with `DrawerDemoState` and the public
`FrogDrawer`, `rememberFrogDrawerState`, presentation and side enums. Its inspector
controls presentation, logical edge, title, subtitle, footer and long content.
Examples remain compiled public-API calls. The old independent Drawer screen is
removed. Registry metadata no longer advertises nonexistent size options, and the
state-based overload correctly marks `state` as required.
The registry's existing native-function reference now accepts lowercase factory
names as well as uppercase screen names, while still validating the actual Kotlin
function and its source path.

## Responsive Behavior

Layout decisions use available content width after app navigation and insets.
At Compact detail width (below 600dp by default), or above 1.3 font scale, properties open in a contextual drawer.
Wider content uses a 264dp persistent inspector beside the scrolling detail body.
Main tabs fit their available width where practical; narrow panes and large text
use horizontal scrolling with the selected tab brought into view. Tab changes
return to the documentation boundary instead of retaining an unrelated offset.
Examples become side by side from 680dp; long-form documentation is capped at 760dp.

## Inspector

One `ComponentInspectorHost` resolves properties, preview settings, API details
and component-defined contextual pages. Its compatibility adapter delegates to
the public `FrogDrawer`; it contains no second modal implementation. Compact hosts
use Bottom and wider hosts use Side. Back pops nested pages; Close and outside
dismissal clear the contextual stack. Color changes commit only through Apply.
Toolbar and contextual Back controls use `FrogIconButton`, a Hugeicons back glyph
and a tonal background. Compact choices and toggles preserve 48dp interaction
targets while keeping their visible controls small.

## Preview

Capabilities enable theme, width, alignment, background and inspector controls.
Both definitions share toolbar/canvas styling. Theme is independent of the app;
width presets change actual Compose constraints and clamp to available space.
Button uses standard content alignment. Drawer uses Overlay mode and the public
`FrogOverlayHost`, which clips the actual panel and scrim to explicit bounds.
Auto presentation resolves from that width. Embedded preview Back and trigger
focus restoration belong to the host; compiled examples demonstrate native modal
windows. There is no screenshot scaling or simulated drawer body.

## Code

Every code tab and example uses the shared selectable, highlighted, copyable and
expandable code renderer. Each definition generates code from its own current
state. Preview-only controls are excluded. Button icon examples take consumer
`ImageVector` parameters instead of exporting showcase-only icon symbols. Drawer
generates public state, launch, dismiss, presentation and slot calls. User text is
escaped as Kotlin literals. These snippets are usage examples, not runtime source
execution; compiled preset functions provide public API compilation coverage.

## API

Names, types, defaults and descriptions come from canonical generated registry
metadata. Component definitions add categories, explanations, values and examples.
One renderer presents the signature, grouped responsive tables/stacked rows and
clickable property details in the shared inspector. Values use inline code; longer
examples and guidance reuse the code and Markdown renderers. No source parsing or
duplicate signature database runs in the app.

## Accessibility

One renderer presents typed facts and the canonical Accessibility Markdown section.
Claims describe actual behavior and distinguish guidance from pending manual review.
There are no success badges implying completed certification. Drawer exposes pane
and dismiss semantics, a focusable close button and logical RTL edges. Its native
window supports safe-drawing/IME insets; embedded previews state their narrower
focus boundary. Public Drawer content inherits its configured content color.
Zero-duration FrogTheme motion disables panel transitions and button transforms.

## Removed Duplication

- Deleted `ButtonScreen.kt`, `DrawerScreen.kt` and `ButtonDrawerState.kt`.
- Removed Button-owned API/reference renderers and separate Drawer code/API/a11y UI.
- Replaced per-component example framing, headings and tab/page routing.
- Replaced native screen dispatch with one factory mapping and shared layout.
- Integrated usage docs into Preview; the four core tabs are Preview, Code, API
  and Accessibility for both components.

Catalog/search, Playground and `frogui://components/{id}` use the same definitions.
Deep links support cold and warm intents. Unknown IDs show a truthful shared error.
The existing route saveable-state holder keeps each destination's state isolated.

## Validation

Commands and evidence for this migration:

```powershell
$env:GRADLE_USER_HOME = 'C:\Users\hp\.gradle'
.\gradlew.bat verifyArchitecture :app:testDebugUnitTest :frogui-components:testDebugUnitTest :app:assembleDebug :app:assembleDebugAndroidTest :app:lintDebug --console=plain
npm.cmd test
npm.cmd run docs:build
git -c core.safecrlf=false diff --check
```

The attempted final device run was:

```powershell
$adb = 'C:\Users\hp\AppData\Local\Android\Sdk\platform-tools\adb.exe'
& $adb install -r app/build/outputs/apk/debug/app-debug.apk
& $adb install -r app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
& $adb shell am instrument -w -r -e class io.github.codewitheswar.frogui.showcase.SharedComponentDetailTest,io.github.codewitheswar.frogui.showcase.SharedDetailWindowTest io.github.codewitheswar.frogui.test/androidx.test.runner.AndroidJUnitRunner
```

- App JVM tests: 24 passing. Component JVM tests: 9 passing.
- Registry/docs tooling: 15 tests passing; icon inventory and docs build passing.
- Architecture, app APK and instrumentation APK compilation pass. Lint reports
  zero errors and 31 existing SDK/dependency/resource warnings. Results are recorded
  in `build/shared-detail-verification.log` and the app lint report; all three
  Compose warnings identified during this migration were resolved.
- Seven Button interaction regressions passed after the shared-shell migration:
  `build/shared-button-regression.log`. This covers color transactions, restoration,
  code/API interactions, loading bounds and whole-button color/full-width behavior.
- New device tests compile: five shared Drawer interaction/routing tests and eleven
  responsive scenarios. The latter cover 360/390/412/600/840/1000dp, landscape,
  light/dark outer themes, opposite preview themes and font scales 1/1.3/1.5.
  Existing Button window tests also include larger text. These latest tests have
  not yet run: the connected Android device disappeared from ADB and no emulator
  is configured. `build/shared-device-regression.log` records that failed launch.
- Earlier `build/showcase-qa` captures are prior-phase evidence; they are not
  presented as fresh screenshots of the final Drawer migration.

## Remaining

Reconnect the Android device to run the final interaction/window suite, review fresh
captures and verify cold/warm deep links through Android intent dispatch. This is
the outstanding validation step for the shared migration. Human TalkBack speech
and traversal, physical keyboard/tablet input, minimum-API behavior, three-button
navigation and foldable hinge placement remain manual release checks. Window-size
adaptation is implemented; hinge/posture-specific placement is not.

Button and Drawer remain Experimental. No known separate component-detail shell
remains; further visual findings require the final device review. The docs build
still reports its existing large JavaScript chunk warning.
