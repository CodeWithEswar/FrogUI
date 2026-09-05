# Usage guidance

Use `FrogTextField` as the canonical general-purpose editable text input for FrogUI forms, dialogs, and text entry screens. Typical use cases include name, email, titles, search queries, usernames, short notes, IDs, and free-form input.

Wrap application content in `FrogTheme` from `io.github.codewitheswar.frogui.theme`.
The field preserves Compose state hoisting: the application owns `value` and `onValueChange`, while `FrogTextField` handles presentation, interaction, and accessibility.

## Compose a text field

Keep state management with the caller. Declare a mutable state and bind it directly to the field:

```kotlin
@Composable
fun UserEmailField() {
    var email by rememberSaveable { mutableStateOf("") }

    FrogTextField(
        value = email,
        onValueChange = { email = it },
        label = "Work email",
        placeholder = "name@company.com",
        helperText = "We will send your verification link here",
        modifier = Modifier.fillMaxWidth()
    )
}
```

> **Accessibility Rule:** A placeholder is not a label. Important fields must provide a persistent or floating `label` so that users and assistive technologies can identify the field at all times, even after typing begins.

### When to use

- The user needs to enter or edit single-line or multiline text.
- A visible label identifies the input field.
- Form validation may apply with contextual helper or error feedback.
- Configurable software keyboard options (IME) and action buttons are required.
- Standard accessible editable text semantics are needed.

### When not to use

- Obscured password entry. Use a dedicated password component when available.
- PIN or numeric OTP codes with distinct digit boxes.
- Large document-style rich text editing.
- Discrete choices (checkboxes, radio buttons, switches, or dropdown selectors).
- Continuous numeric ranges (use sliders or steppers).

---

## Visual variants

`FrogTextField` supports three semantic visual presentations:

| Variant | Container style | Best suited for |
|---|---|---|
| **Filled** | Subtle container surface with bottom indicator | Strong field grouping, dense forms, or high visual hierarchy |
| **Outline** | Explicit surrounding border with transparent background | Clear individual field boundaries across varied surface backgrounds |
| **Underline** | Minimal surface with bottom-edge border accent | Clean, lightweight interfaces with established structural rhythm |

```kotlin
// Outline variant
FrogTextField(
    value = name,
    onValueChange = { name = it },
    label = "Full name",
    variant = FrogTextFieldVariant.Outline
)

// Underline variant
FrogTextField(
    value = title,
    onValueChange = { title = it },
    label = "Project title",
    variant = FrogTextFieldVariant.Underline
)
```

All three variants share identical semantic behavior for typing, selection, focus, keyboard handling, labels, placeholders, supporting text, and accessibility.

---

## Label vs placeholder

- **`label`** identifies the field. It floats to a compact top position when the field is focused or contains text, remaining continuously readable.
- **`placeholder`** displays an example or hint (e.g. `name@example.com`). It appears only when `value` is empty and does not replace the label.

```kotlin
FrogTextField(
    value = username,
    onValueChange = { username = it },
    label = "Username",                     // Identifies the field
    placeholder = "e.g. alex_dev",          // Hint when empty
    helperText = "Lowercase letters only"   // Guidance
)
```

---

## Supporting text & error handling

Supporting text renders below the field container with aligned geometry:

- **`helperText`** provides persistent formatting hints or guidance.
- **`errorText`** provides the authoritative validation error message.

> **Precedence Rule:** When `errorText != null`, the error message replaces `helperText`. Stacking competing messages is avoided.

```kotlin
var email by rememberSaveable { mutableStateOf("invalid-email") }
val isValid = email.contains("@") && email.contains(".")

FrogTextField(
    value = email,
    onValueChange = { email = it },
    label = "Email address",
    errorText = if (!isValid) "Enter a valid email address" else null,
    helperText = "We will send your login link here"
)
```

### Error accessibility

Errors are never communicated by color alone. When `errorText` is present:
1. The border or underline transitions to `FrogTheme.colors.error`.
2. The error message is rendered in error-toned supporting text.
3. The field exposes `SemanticsProperties.Error`, ensuring screen readers announce the validation failure when navigating to or interacting with the field without repetitive announcement spam.

---

## Enabled vs read-only

`enabled = false` and `readOnly = true` represent fundamentally different states:

| State | Editable | Focusable / Selectable | Visual presentation |
|---|---|---|---|
| **Enabled** | Yes | Yes | High contrast, interactive affordance |
| **Read-Only** | No | Yes | Normal readable contrast, non-interactive affordance |
| **Disabled** | No | No | Muted contrast, clicks and focus suppressed |

```kotlin
// Read-only: value is visible and selectable, but cannot be changed
FrogTextField(
    value = "org_2026_prod",
    onValueChange = {},
    label = "Organization ID",
    readOnly = true,
    helperText = "Generated at workspace creation; cannot be edited"
)

// Disabled: field is completely inactive
FrogTextField(
    value = "Legacy plan",
    onValueChange = {},
    label = "Subscription tier",
    enabled = false
)
```

---

## Leading & trailing content

Use generic composable slots to compose leading indicators or trailing actions:

```kotlin
FrogTextField(
    value = search,
    onValueChange = { search = it },
    label = "Search",
    leading = {
        Icon(FrogIcons.Search, contentDescription = null)
    },
    trailing = if (search.isNotEmpty()) {
        {
            FrogIconButton(
                icon = { Icon(FrogIcons.Close, contentDescription = null) },
                contentDescription = "Clear search",
                onClick = { search = "" },
                variant = FrogIconButtonVariant.Ghost,
                size = FrogIconButtonSize.Small
            )
        }
    } else null
)
```

- **Leading icons** should specify `contentDescription = null` when purely decorative so they do not duplicate the field label.
- **Interactive trailing controls** should use `FrogIconButton` to preserve independent touch targets and semantic actions.

---

## Keyboard configuration & multiline

Configure software keyboard behavior via Compose `KeyboardOptions` and `KeyboardActions`:

```kotlin
FrogTextField(
    value = email,
    onValueChange = { email = it },
    label = "Email",
    singleLine = true,
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next
    ),
    keyboardActions = KeyboardActions(
        onNext = { focusManager.moveFocus(FocusDirection.Down) }
    )
)
```

For multiline inputs, set `singleLine = false` and specify `maxLines`:

```kotlin
FrogTextField(
    value = notes,
    onValueChange = { notes = it },
    label = "Release notes",
    singleLine = false,
    maxLines = 5,
    modifier = Modifier.fillMaxWidth()
)
```

---

## Sizing & touch targets

- Standard fields maintain a minimum container height of **56dp** (`FrogTextFieldDefaults.MinHeight`), providing generous touch targets and comfortable vertical padding for floating labels.
- Compact layouts can utilize **48dp** (`FrogTextFieldDefaults.CompactMinHeight`), which strictly fulfills the canonical 48dp minimum touch target for motor accessibility.
- Slot icons default to **20dp** (`FrogTextFieldDefaults.IconSize`).

---

## Reduced motion & dark theme

- When reduced motion is requested (`FrogTheme.motion.isReduced`), label transitions and border color shifts snap immediately without animated tweens.
- In dark mode, container surfaces and borders utilize deep semantic tokens (`surfaceVariant`, `border`, `borderFocus`) to ensure crisp visual contrast against dark backgrounds.
