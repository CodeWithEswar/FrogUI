# Usage guidance

Use Drawer to present contextual content, secondary workflows, and property inspectors without navigating away from the current screen destination.
The caller owns the drawer state and supplies the dismiss callback. Dismiss gestures include modal backdrop taps, system back gestures, and downward drag gestures on compact bottom presentations.

`rememberFrogDrawerState()` is optional: use the `visible` overload if the application already owns a Boolean. The helper saves requested visibility. Its suspend `open()` and `close()` functions update state immediately; they do not wait for visual animation to finish. `snapTo()` also changes requested visibility, and does not bypass rendering motion.

Wrap application content in `FrogTheme` from `io.github.codewitheswar.frogui.theme`.
Use composable content, header, preview, and sticky footer slots, adaptive presentation modes, and `FrogDrawerColors` for customization. API signatures, examples, capabilities, and status come directly from the generated registry.

## Compose a drawer

Hoisting state allows external triggers (such as toolbar buttons or menu items) to imperatively launch and dismiss the drawer.

```kotlin
@Composable
fun SettingsDrawer() {
    val drawerState = rememberFrogDrawerState()
    val scope = rememberCoroutineScope()

    FrogButton(
        onClick = { scope.launch { drawerState.open() } }
    ) {
        Text("Configure Settings")
    }

    FrogDrawer(
        state = drawerState,
        onDismissRequest = { scope.launch { drawerState.close() } },
        title = "Settings",
        subtitle = "Manage application preferences"
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("General Preferences", style = FrogTheme.typography.heading)
            Text("Notification and appearance settings.")
        }
    }
}
```

### Choose an adaptive presentation

- **Auto** (default) resolves from `FrogTheme.adaptive`: Compact (below 600dp by default) presents as a bottom sheet; Medium/Expanded use a side panel. Both presentations use a native modal window by default.
- **Bottom** forces bottom sheet presentation with top rounded corners and an interactive drag-to-dismiss handle indicator.
- **Side** places the panel at a logical screen edge (`FrogDrawerSide.End` or `FrogDrawerSide.Start`). Its width is capped by the available space. Start and End mirror in RTL.

On bottom presentation, a downward handle drag exceeding 64dp requests dismissal. The owner closes the state in `onDismissRequest`.

### Preview within a bounded workspace

Wrap content in `FrogOverlayHost(Modifier.width(360.dp).height(360.dp))` to render the same public drawer inside explicit bounds. Auto uses the host's width, and the panel and scrim stay inside those bounds. This is useful for component previews; it does not create a modal window or trap focus across the application. The host's caller handles Back and restores focus to its trigger.

The showcase uses this bounded host in the shared Preview workspace. Its compiled examples open native modal windows. Preview theme, width and background are independent from generated component code. The shared detail tabs are Preview, Code, API and Accessibility; usage guidance and examples live in Preview.

## Customize slots and layout

FrogDrawer provides dedicated structural slots:
- **Header**: Includes title, subtitle, optional `navigationIcon` (e.g. back navigation), and optional trailing `actions`.
- **Preview**: Rendered immediately below the header divider for live preview canvases or status badges.
- **Content**: The main scrollable body. Internal scroll state is managed automatically so headers and footers remain pinned.
- **Footer**: Sticky bottom bar rendered outside the scrollable body, typically housing primary and secondary action buttons.

The showcase's footer and long-content switches configure these slots; they are demonstration options, not extra public Drawer parameters.

### Migrating the Boolean-side overload

The older overload with `side: Boolean` and `onBack` remains callable with a deprecation warning. In the canonical overload, pass `presentation = if (side) FrogDrawerPresentation.Side else FrogDrawerPresentation.Bottom` to preserve its placement. Move its old `actions` lambda into a `Row` in `footer`. Supply the back button through `navigationIcon` and the same callback through `onBackRequest`. The canonical `actions` slot is in the header, so moving old actions there changes behavior.

`FrogDrawerDefaults.AnimationDurationMs` is also deprecated: it never controls the current renderer. Set `FrogTheme` motion tokens to configure transitions. Zero normal duration disables Drawer transitions; the configured normal duration and enter/exit easing otherwise apply directly. There is no mechanical `ReplaceWith` because these migrations require a behavior choice.

```kotlin
FrogDrawer(
    state = drawerState,
    onDismissRequest = { scope.launch { drawerState.close() } },
    title = "Edit Profile",
    footer = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FrogButton(
                onClick = { scope.launch { drawerState.close() } },
                modifier = Modifier.weight(1f),
                variant = FrogButtonVariant.Secondary
            ) {
                Text("Cancel")
            }
            FrogButton(
                onClick = { scope.launch { drawerState.close() } },
                modifier = Modifier.weight(1f),
                variant = FrogButtonVariant.Primary
            ) {
                Text("Save")
            }
        }
    }
) {
    Text("User profile form fields and configurations.")
}
```

## Accessibility

- **Pane Semantics**: Automatically declares `paneTitle = title ?: "Drawer"` so screen readers announce window transitions when opened.
- **Semantic Dismissal**: Exposes the standard accessibility dismiss action to assistive tools.
- **Focus management**: Native modal windows contain focus, and the close control receives initial keyboard focus. Embedded previews use the host's focus boundary; the showcase returns focus to the launch button after dismissal.
- **Touch targets**: The built-in close and compatibility back buttons use `FrogIconButton` with `FrogTheme.sizing.minimumTouchTarget` (48dp by default). Caller-supplied navigation and action slots must preserve their own targets and accessible labels.
- **Nested pages**: `onBackRequest` can return to a parent inspector while Close and outside dismissal discard the entire contextual flow. Without it, Back calls `onDismissRequest`.
- **Motion and insets**: Zero-duration theme motion removes transitions. Native modal content respects safe-drawing and keyboard insets; the body scrolls beneath the header and above the footer.

Drawer remains Experimental. Human TalkBack speech and traversal, physical keyboard/tablet behavior and hinge-aware placement still require release review. Automated semantics and layout checks do not replace those checks.
