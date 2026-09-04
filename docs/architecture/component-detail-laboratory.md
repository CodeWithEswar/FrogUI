# Component detail laboratory

The Button detail and Playground share one native Compose workspace. This phase
adds a contextual drawer, token-aware colors, structured API exploration, and a
live inspector using the published `FrogButton` implementation.

The subsequent [shared detail system](shared-component-detail-system.md) extracts
this workspace for both Button and Drawer. This document retains the Button color
and interaction contract; the shared-system report describes the current shell.

## Ownership and data flow

- `ButtonDemoState` owns committed variant, size, content, state, shape and color
  overrides. Its saver preserves these values across configuration changes.
- `ComponentDetailState` owns the shared typed page stack. Properties, preview settings, a color
  property, and an API property all use one `FrogDrawer` window. Back pops the page;
  Close/outside dismissal clears it. Color drafts are saved separately from commits.
- `FrogColorValue.Token` retains semantic identity (and optional alpha), while
  `Custom` stores ARGB. Both resolve against the preview theme and generate Kotlin.
- A color editor operates on a draft. Apply commits; dismissal discards it. Reset
  drafts the current variant default. Changing variant clears color overrides;
  resetting colors leaves label, size and interaction state unchanged.
- `ButtonLivePreview` composes the actual library button. A fixed preview region
  inside the drawer makes all control changes visible. Disabled-color properties
  preview the disabled state without changing the committed enabled setting.
- API names, types, defaults, descriptions, version, status and examples come from
  the canonical registry. Showcase-owned guidance adds categories, explanations,
  values, and examples without parsing source at runtime.

## Layout and interactions

Phones retain compact horizontally scrolling variant/size controls and a Customize
action. At Medium available detail width (600dp by default, and normal text scale), a 264dp persistent
inspector appears beside the preview. This threshold uses content width after app
navigation, so it also works in a split window. Larger font scales use the drawer.

`FrogDrawer` uses Compose's native Dialog window with a custom surface rather than
a Material sheet or AlertDialog. Compact layouts use a content-driven bottom panel
capped at 90% of inset-adjusted height. Expanded layouts use a 400dp side panel.
The header, live preview, and actions stay visible while the body scrolls. Each new
page resets body scroll so navigation does not open halfway down the next panel.
The phone handle supports downward drag dismissal. Native Back, close, outside tap,
pane title and dismiss semantics are supported. Keyboard focus enters the close
control; the native modal window restores focus to the underlying window on exit.

The color picker has separate token/custom modes. Tokens show their resolved value
and theme expression. Custom mode includes a 120dp saturation/value plane, compact
4dp slider tracks with 16dp round thumbs, accessible adjustment actions, alpha,
and validated hex input. The controls keep 48dp interaction height. Standard paste
works in the native hex field; RGB and alpha-first ARGB are accepted. There are no
fake recent colors or third-party picker dependencies.

Preview theme, constrained width, canvas/light/dark/checkerboard background, and
alignment are independent from exported component state. Reduced motion disables
drawer transitions and component press transforms through theme motion tokens.
Toolbar and drawer Back actions use `FrogIconButton`, a Hugeicons back glyph, and a
subtle tonal background. The complete 48dp area is clickable and focusable.

## Library corrections

`FrogButton` now has `fullWidth`, filling both its visible surface and touch target.
Loading overlays a centered spinner while keeping the label and slots measured,
preserving width and action semantics. The outer 48dp target owns the single click
action. Custom color defaults accept selected overrides, and the border resolves
from supplied colors rather than resolving the variant again. Pressed overlays
and focus rings remain theme-driven. The existing composable `leadingIcon` and
`trailingIcon` slot names are retained; Hugeicons stays in the showcase.

The component remains Experimental. These are intentional API/behavior changes to
an unpublished snapshot, documented in the canonical registry and generated docs.

## Sources and scope of claims

The drawer uses the native platform window behavior described in the
[Compose Dialog documentation](https://developer.android.com/develop/ui/compose/components/dialog).
The custom content and adaptive layout are implemented locally.

Contrast uses alpha compositing over the selected opaque canvas followed by
relative luminance and `(lighter + 0.05) / (darker + 0.05)`. Feedback uses the
unrounded 4.5:1 normal-text threshold from the
[W3C contrast explanation](https://www.w3.org/WAI/WCAG22/Understanding/contrast-minimum.html).
It is informational, does not override user colors, and does not certify the whole
component. Transparent backgrounds and disabled states are explained separately.

## Verification artifacts

- `build/detail-verification.log`: architecture, registry/docs, unit tests and lint.
- `build/detail-build.log`: current app and Android test APK build.
- `build/detail-device-suite.log`: full native device test output.
- `build/showcase-qa/detail-*.png`: focused phone/tablet/drawer render captures.
- `app/build/reports/tests/testDebugUnitTest/index.html`: JVM results.
- `app/build/reports/lint-results-debug.html`: lint findings.

The tests cover hex/alpha parsing, token serialization and code generation, variant
reset, contrast endpoints, drawer navigation/restoration, Apply/Cancel/Reset, invalid
input, code copying, loading bounds/semantics, and pixel verification that custom
container color and full width affect the actual button surface. Window tests cover
light/dark phone, tablet, landscape, and large-text layouts. Captures are review
artifacts rather than pixel-golden comparisons or claims about multiple devices.

Human TalkBack speech/traversal, physical tablet/Chromebook input, a minimum-API
device, three-button navigation and hinge-specific foldable placement remain manual
release checks. The app has no posture/hinge integration yet; it adapts to the
available window size. No fake fullscreen, recent-color storage, or external-link
actions were added.
