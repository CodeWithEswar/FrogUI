# Usage guidance

Use `FrogFloatingActionButton` (FAB) for prominent contextual actions that float above the main content canvas. Typical examples include creating a new record, composing a message, scanning a document, or starting a capture. The component emphasizes the single most important action for the current screen context.

Wrap application content in `FrogTheme` from `io.github.codewitheswar.frogui.theme`.
Use composable icon and label slots, standard Compose `Modifier`, presentation forms (`FrogFabPresentation`), and semantic elevation defaults.

## Compose a floating action

Keep application state with the caller. Supply a descriptive action label that explains what happens when activated, rather than describing the visual icon glyph:

```kotlin
@Composable
fun CreateItemFab(onCreate: () -> Unit) {
    FrogFloatingActionButton(
        icon = {
            Icon(
                imageVector = FrogIcons.Add,
                contentDescription = null
            )
        },
        contentDescription = "Create new document",
        onClick = onCreate
    )
}
```

> **Accessibility Rule:** `contentDescription` is mandatory for all presentations of `FrogFloatingActionButton`. Child icon composables passed into the `icon` slot should specify `contentDescription = null` so that assistive technologies announce the action once from the parent button without duplicate or confusing descriptions.

### When to use

- One action deserves persistent visual prominence on the screen.
- The action belongs to the current screen context.
- Floating placement improves discoverability and thumb reach.
- The action remains understandable through the icon and/or label.

### When not to use

- Multiple actions on the screen have equal importance.
- The action belongs naturally in a form footer or modal bottom bar.
- The action is destructive (e.g. Delete, Erase). Use a confirmation dialog with `FrogButton(variant = Destructive)` instead.
- The screen already has an unambiguous primary `FrogButton`.
- Floating placement would cover critical interactive content or text.
- The action is global navigation. Use a Navigation Bar or Navigation Rail instead.

---

## Presentations

`FrogFloatingActionButton` supports three semantic presentation forms:

- **Regular:** Canonical 56dp square visual container with a 24dp centered icon. Designed as the default floating primary action.
- **Small:** Compact 40dp square visual container with a 20dp centered icon. Suitable for secondary floating actions or dense tablet layouts. Preserves a **guaranteed 48dp minimum interactive touch target** for motor accessibility.
- **Extended:** 48dp height container presenting an icon alongside a visible text label. Ideal when an icon alone might be ambiguous or when stronger affordance is required.

```kotlin
// Extended FAB with dynamic label expansion
FrogFloatingActionButton(
    icon = { Icon(FrogIcons.Add, null) },
    label = { Text("Compose message") },
    contentDescription = "Compose message",
    onClick = { },
    presentation = FrogFabPresentation.Extended,
    expanded = true
)
```

---

## Extended expansion & collapsing

Extended FABs can dynamically collapse to an icon-only representation to optimize screen real estate during content consumption:

```kotlin
var isExpanded by remember { mutableStateOf(true) }

FrogFloatingActionButton(
    icon = { Icon(FrogIcons.Add, null) },
    label = { Text("Create task") },
    contentDescription = "Create task",
    onClick = { },
    presentation = FrogFabPresentation.Extended,
    expanded = isExpanded
)
```

When `expanded` transitions between `true` and `false`:
- The container width smoothly animates using `FrogTheme.motion.normalSpec()`.
- The label cleanly appears or disappears without clipping adjacent content.
- If the user has requested reduced motion (`FrogTheme.motion.isReduced`), the layout snaps instantly without spatial animation.

---

## Visibility & presence

Visibility is controlled by the caller through the `visible` boolean property:

```kotlin
// Visibility derived from screen state (e.g. scroll direction)
FrogFloatingActionButton(
    icon = { Icon(FrogIcons.Add, null) },
    contentDescription = "New item",
    onClick = { },
    visible = shouldShowFab
)
```

> **Semantic Removal:** When `visible == false`, the button is smoothly animated out and **completely removed from accessibility semantics and touch interaction**. It cannot receive clicks or keyboard focus while hidden.

---

## Elevation & dark theme

Resting, pressed, and focused elevations are governed by `FrogFabElevation`, resolving by default from `FrogTheme.elevation`:

- **Resting:** `FrogTheme.elevation.medium` (3dp).
- **Pressed:** `FrogTheme.elevation.high` (6dp) with a subtle tonal press overlay.
- **Focused:** Highlights with a 2dp high-contrast `focusRingColor`.
- **Dark Theme:** In dark mode, elevation is supplemented by a 1px subtle surface border (`FrogTheme.colors.border`) to ensure clear boundary definition on deep canvases.
