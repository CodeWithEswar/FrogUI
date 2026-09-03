# Usage guidance

Use Drawer to present contextual content, secondary workflows, and property inspectors without navigating away from the current screen destination.
The caller owns the drawer state and supplies the dismiss callback. Dismiss gestures include modal backdrop taps, system back gestures, and downward drag gestures on compact bottom presentations.

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

- **Auto** (default) dynamically presents as a modal bottom sheet on compact mobile screens (< 620dp) and docks as a contextual side panel on tablet and desktop surfaces (>= 620dp).
- **Bottom** forces bottom sheet presentation with top rounded corners and an interactive drag-to-dismiss handle indicator.
- **Side** docks to a specified screen edge (`FrogDrawerSide.End` or `FrogDrawerSide.Start`) as a fixed-width inspector surface.

> On bottom presentation, downward drags exceeding the 64dp velocity threshold trigger dismissal automatically.

## Customize slots and layout

FrogDrawer provides dedicated structural slots:
- **Header**: Includes title, subtitle, optional `navigationIcon` (e.g. back navigation), and optional trailing `actions`.
- **Preview**: Rendered immediately below the header divider for live preview canvases or status badges.
- **Content**: The main scrollable body. Internal scroll state is managed automatically so headers and footers remain pinned.
- **Footer**: Sticky bottom bar rendered outside the scrollable body, typically housing primary and secondary action buttons.

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

## Accessibility and Semantics

- **Pane Semantics**: Automatically declares `paneTitle = title ?: "Drawer"` so screen readers announce window transitions when opened.
- **Semantic Dismissal**: Exposes the standard accessibility dismiss action to assistive tools.
- **Focus Management**: Traps focus within the modal window upon entrance and focuses the primary close control.
- **Touch Target Compliance**: Close controls and navigation icons strictly satisfy the 48dp minimum accessible touch target requirement.
