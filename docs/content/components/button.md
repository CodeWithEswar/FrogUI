# Usage guidance

Use Button to trigger an action such as saving a form or continuing a workflow.
The caller supplies the action callback and owns enabled/loading state. Loading
suppresses activation; it does not start network work or retain application state.

Wrap application content in `FrogTheme` from `io.github.codewitheswar.frogui.theme`.
Use composable content and icon slots, native Modifier, semantic variant/size values,
and FrogButtonColors for customization. API signatures, examples, capabilities, and
status come from the generated registry rather than this prose.

## Compose an action

Keep application state with the caller. Pass a callback to the button and use its
content slot for the visible label.

```kotlin
@Composable
fun ContinueAction(enabled: Boolean, onContinue: () -> Unit) {
    FrogButton(enabled = enabled, onClick = onContinue) {
        Text("Continue")
    }
}
```

### Choose a variant

- **Primary** gives the main action the most emphasis.
- **Secondary**, **Outline**, and **Ghost** support quieter actions.
- **Destructive** identifies an action that needs destructive intent.

> Loading blocks activation. The caller remains responsible for starting work,
> handling its result, and updating `loading`.

## Customize colors and layout

Use `fullWidth = true` to expand both the visible surface and its touch target.
Small, Medium, and Large use one shared size model for padding, icon size, spacing,
and minimum surface height. Prefer semantic shapes; override `shape` only when
the surrounding design calls for it.

```kotlin
FrogButton(
    variant = FrogButtonVariant.Outline,
    fullWidth = true,
    colors = FrogButtonDefaults.colors(
        variant = FrogButtonVariant.Outline,
        containerColor = FrogTheme.colors.surfaceElevated,
        contentColor = FrogTheme.colors.foreground
    ),
    onClick = { /* Continue */ }
) {
    Text("Continue")
}
```

The color defaults accept selected overrides, including border and disabled
colors. The default border uses the supplied `FrogButtonColors`. Keep the same
variant in the button and its color defaults. Semantic tokens follow the theme;
a custom literal such as `Color(0x8018181B)` keeps its ARGB value and alpha.

## Use the component laboratory

- Quick controls change variant and size immediately.
- **Customize** opens grouped appearance, content, state, and color controls.
- Wider layouts keep the inspector beside the preview.
- Color rows open the same drawer in either theme-token or custom mode.
- Drag the saturation/brightness plane, use the accessible sliders, or enter
  `#RRGGBB` / `#AARRGGBB` (alpha first). Invalid input disables Apply.
- A color draft updates the miniature button and live preview. **Apply** commits;
  **Cancel**, Back, the close action, and outside dismissal leave committed colors
  unchanged. Back returns to the parent inspector when opened from Customize.
- **Reset** in a color editor drafts the current variant default. **Reset colors**
  restores all colors without changing size, label, enabled, or loading state.
- Changing variant restores its defaults. Generated Kotlin updates immediately,
  preserving semantic token expressions.
- Preview theme, width, background, and alignment are independent of exported code.
- Tap an API property for its type, default, guidance, values, and a copyable example.

## Accessibility

- **Role and target:** one Button action with a minimum 48dp touch target. Surface
  dimensions may be smaller for Small and Medium.
- **Enabled:** disabling suppresses activation and exposes disabled semantics.
- **Loading:** the visible label and icon slots are replaced by a centered spinner,
  while retaining their measured bounds and accessible label. The state is Loading;
  the spinner itself does not repeatedly announce progress. Activation is blocked.
- **Focus:** keyboard focus draws a visible semantic focus ring. Use a shared
  `MutableInteractionSource` when observing interactions.
- **Label and icons:** use clear action text; decorative slots have a null content
  description. Do not nest clickable controls inside the content.
- **Contrast:** custom colors may reduce readability. The picker composites alpha
  over the chosen opaque preview canvas, then calculates relative-luminance text
  contrast. Its 4.5:1 normal-text feedback is informational, not a certification.
  Transparent backdrops can change the result; inactive controls are exempt from
  the minimum text contrast requirement. See the
  [W3C contrast explanation](https://www.w3.org/WAI/WCAG22/Understanding/contrast-minimum.html).
- **Text scaling:** content can grow vertically; inspector choices scroll rather
  than truncate. Sliders expose labels, values, and adjustment actions.
- **Motion:** the showcase respects its reduced-motion preference and the system
  animator setting. Consumer apps can provide zero-duration `FrogMotion` tokens.

Automated semantics and interaction tests provide focused evidence. Human TalkBack
speech/traversal, physical tablet input, and minimum-API review remain release checks
before this experimental component can be considered Stable.

## Native preview

Use the Android Showcase for real interaction. Web documentation presents metadata,
usage code, and documentation; it does not execute native Compose.
