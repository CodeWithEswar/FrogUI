# Usage guidance

Use `FrogIconButton` for compact, icon-only actions such as searching, closing modals, navigating back, opening settings, or toggling favorites. The visible content is primarily an icon, making accessible naming through `contentDescription` strictly mandatory.

Wrap application content in `FrogTheme` from `io.github.codewitheswar.frogui.theme`.
Use composable icon and badge slots, native `Modifier`, semantic variant/size values, and `FrogIconButtonColors` for customization.

## Compose an icon action

Keep application state with the caller. Supply a descriptive action label that explains what happens when activated, rather than describing what the icon looks like:

```kotlin
@Composable
fun SearchAction(onSearch: () -> Unit) {
    FrogIconButton(
        icon = {
            Icon(
                imageVector = FrogIcons.Search,
                contentDescription = null
            )
        },
        contentDescription = "Search items",
        onClick = onSearch
    )
}
```

> **Accessibility Rule:** `contentDescription` is mandatory for `FrogIconButton`. Child icon composables passed into the `icon` slot should specify `contentDescription = null` so that assistive technologies announce the action once from the parent button.

### When to use

- The action is compact and contextual (e.g. toolbars, card headers, inline table rows).
- The icon conveys an unambiguous, familiar concept.
- The control has a clear, actionable `contentDescription`.

### When not to use

- The action requires explanatory text to be understood. Use `FrogButton` instead.
- The icon is purely decorative or non-interactive. Use a standard `Icon` with `contentDescription = null`. Do not use `FrogIconButton` for decorative visuals.
- The icon meaning is ambiguous or unfamiliar to your users.

---

## Semantic variants

FrogIconButton supports four semantic emphasis levels:

- **Filled:** Highest visual emphasis with a solid surface and high-contrast content. Ideal for standalone floating actions or primary tool actions.
- **Tonal:** Medium visual emphasis on a muted surface container. Great for secondary actions in content cards.
- **Outline:** Medium/low visual emphasis with a defined structural border and transparent background.
- **Ghost:** Lowest visual emphasis with a transparent background. Ideal for navigation bars, app bars, and high-density toolbars.

```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    FrogIconButton(
        icon = { Icon(FrogIcons.Search, null) },
        contentDescription = "Search",
        onClick = { },
        variant = FrogIconButtonVariant.Filled
    )
    FrogIconButton(
        icon = { Icon(FrogIcons.Settings, null) },
        contentDescription = "Settings",
        onClick = { },
        variant = FrogIconButtonVariant.Tonal
    )
    FrogIconButton(
        icon = { Icon(FrogIcons.Close, null) },
        contentDescription = "Close",
        onClick = { },
        variant = FrogIconButtonVariant.Outline
    )
    FrogIconButton(
        icon = { Icon(FrogIcons.Reset, null) },
        contentDescription = "Reset",
        onClick = { },
        variant = FrogIconButtonVariant.Ghost
    )
}
```

---

## Visual sizing vs. touch targets

Visual container size and interactive touch target size are distinct:

| Size | Visual Container | Recommended Icon Size | Interactive Touch Target |
| :--- | :--- | :--- | :--- |
| **Small** | 32dp × 32dp | 16dp | 48dp × 48dp |
| **Medium** | 40dp × 40dp | 18dp | 48dp × 48dp |
| **Large** | 48dp × 48dp | 20dp | 48dp × 48dp |

Even for `Small` icon buttons, `FrogIconButton` enforces a minimum 48dp interactive bounds via `FrogTheme.sizing.minimumTouchTarget` so motor accessibility is never compromised.

---

## Badge overlay

Support optional badges such as unread notification dots or numerical count badges via the `badge` slot:

```kotlin
FrogIconButton(
    icon = { Icon(FrogIcons.Info, null) },
    contentDescription = "Notifications, 3 unread",
    onClick = { },
    badge = {
        Box(
            modifier = Modifier
                .defaultMinSize(minWidth = 16.dp, minHeight = 16.dp)
                .background(FrogTheme.colors.destructive, CircleShape)
                .padding(horizontal = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("3", style = FrogTheme.typography.bodySmall, color = Color.White)
        }
    }
)
```

- Badges are placed at `Alignment.TopEnd` relative to the visual container.
- Badges are treated as overlays and do not alter the button's layout dimensions or minimum touch target.
- If a badge conveys critical state (e.g. count), enrich the button's `contentDescription` accordingly.

---

## Loading state

When `loading = true`:

- The icon is replaced by a centered progress indicator.
- Button bounds and container dimensions remain perfectly stable (no layout shifts).
- Interaction is blocked; clicks will not fire.
- Screen readers receive the loading state without duplicate announcements.

```kotlin
FrogIconButton(
    icon = { Icon(FrogIcons.Reset, null) },
    contentDescription = "Syncing data",
    onClick = { },
    loading = isSyncing
)
```

---

## Accessibility checklist

1. **Mandatory label:** `contentDescription` is required. Avoid generic labels like "Button" or image descriptions like "Magnifying glass". Describe the action: `"Search"`, `"Close window"`, `"Add to cart"`.
2. **Decorative child icons:** Ensure icon vectors within the slot pass `contentDescription = null` to prevent duplicate announcements.
3. **Minimum touch target:** All sizes retain a minimum 48dp target.
4. **Focus ring:** Keyboard and D-pad navigation display a visible focus ring using `FrogTheme.colors.focusRing`.
5. **RTL support:** Badges and layout positions respect layout direction via `Start`/`End` semantics.
