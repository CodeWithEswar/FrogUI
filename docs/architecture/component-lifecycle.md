# Component lifecycle and quality gate

Every cataloged FrogUI component has one canonical Android implementation. Compose
previews, the native Showcase, generated examples, registry metadata, and web previews
consume that implementation contract; they do not become alternative Android widgets.

```text
FrogTheme → Defaults → canonical component
                         ├─ Compose previews
                         ├─ behavior and semantics tests
                         └─ registry
                              ├─ shared native detail screen
                              └─ shared web component page
```

## Implementation sequence

1. Write the purpose and identify application-owned state.
2. Review the public composable, callbacks, Modifier position, semantic models, slots,
   style overrides, interaction source, and invalid state combinations.
3. Define applicable variants, sizes, visual states, interactions, accessibility
   semantics, theme mapping, and adaptive behavior.
4. Implement the canonical component and theme-aware Defaults. Keep helpers private or
   internal and keep Showcase, registry rendering, navigation, networking, storage,
   dependency injection, analytics, and icon-provider types out of library modules.
5. Add useful KDoc to the composable, Defaults, and important public models.
6. Add focused Compose previews through `FrogComponentPreview`: baseline, major
   variants or states, light/dark, customization, and adaptive scenarios when relevant.
7. Test public behavior. Unit tests cover real state/resolver logic. Compose tests cover
   semantics, activation/dismissal, focus, targets, restoration, and adaptive layout.
8. Add one registry record with actual source, route, properties, compiled examples,
   accessibility notes, lifecycle matrices, and evidence paths.
9. Add one `ComponentShowcaseDefinition`. Supply component preview/state/controls/code
   and metadata to `ComponentDetailScreen`; do not create a new detail shell.
10. Add explanatory Markdown and an isolated representative web preview registered
    through `ComponentPreviewRegistry`.
11. Run registry, API, architecture, unit, lint, compile, and documentation gates.
    Review device-only accessibility and window evidence separately.

## Public component contract

The common call should be short. Required state and callbacks stay explicit. Prefer
semantic enums to conflicting Boolean combinations and composable slots to provider
specific content types. Style parameters may expose colors, shape, border, padding, or
interaction hooks when they add a useful escape hatch. Demo controls must never appear
as fictional public parameters in generated code.

Components read `FrogTheme`, then resolve component-specific Defaults. Shared concepts
such as focus, motion, target sizing, and window classes come from the theme and
foundation. Component geometry may remain in its Defaults when it has no system-wide
meaning. Custom overrides must retain semantics, targets, focus, and interaction.

## Visual and interaction matrices

Define the meaningful matrix before implementation. Button covers Primary, Secondary,
Outline, Ghost, and Destructive; Small, Medium, and Large; and default, pressed,
focused, disabled, and loading states. Drawer covers Auto, Bottom, and Side; open and
closed; short and scrolling content; Compact, Medium, and Expanded hosts; and light,
dark, and custom themes.

Only applicable interactions belong in a component contract. Button needs click,
keyboard activation, focus, press feedback, disabled behavior, and a 48dp target.
Drawer needs open, dismiss request, system Back, scrim dismissal, focus entry and
restoration boundaries, scrolling, bottom drag threshold, and a non-gesture dismissal
alternative. Text input requirements belong to future input components, not Button.

## Shared Showcase architecture

`ComponentDetailScreen` resolves a `ComponentShowcaseDefinition` from
`ShowcaseRegistry`. The shared shell owns identity, status, detail tabs, preview
workspace, theme/width/background controls, inspector placement, code renderer, API
renderer, accessibility renderer, example framing, and responsive behavior.

Definitions own only component demo state, actual component composition, applicable
controls, code generation, examples, and accessibility facts. Compact inspectors use
the public `FrogDrawer`; the Drawer preview stays inside its own `FrogOverlayHost` so
the inspector and preview do not recursively compete for one modal boundary. Enum,
Boolean, text, and color controls use the shared inspector primitives and
`FrogColorPicker`.

Generated code must compile against the public API, escape user text, omit unchanged
defaults, and exclude preview-only controls. The native Showcase uses
`FrogCodeBlock`, `ComponentApiReference`, the shared accessibility surface, and the
native Markdown renderer for every component.

## Shared web architecture

`ComponentDetailPage` renders identity, representative preview, installation, usage,
compiled examples, the structured API table, accessibility, design tokens, and
verification matrices. Registry data supplies identity/API/discovery/evidence,
Markdown supplies prose, and per-component React modules supply representative visuals.

The rendering chain is fixed:

```text
ComponentPreviewMode → ComponentPreviewRegistry → ButtonPreview / DrawerPreview / …
```

React previews may own local demo state but must identify themselves as representative.
Use the Android Showcase for canonical native interaction.

## Registry quality evidence

Schema version 2 requires every component record to declare:

- `visualStates`, `interactions`, `themes`, and applicable `adaptiveClasses`;
- one Compose preview source using `@Preview` and the canonical component;
- meaningful component unit-test and Android-test sources;
- one isolated web preview using `ComponentPreviewProps`.

The registry validator resolves every path inside the repository, checks canonical
component and Defaults KDoc, confirms tests mention the component, and verifies the web
preview contract. Existing schema checks continue to enforce real source/functions,
Showcase routes, exact public property metadata, compiled example regions, docs parity,
unique values, and truthful versions. These checks establish evidence presence;
reviewers still judge test quality and accessibility claims.

## Lifecycle status

- **Experimental** requires a canonical implementation, basic API, preview, registry
  identity, tests, docs, and truthful known gaps. API changes are expected.
- **Beta** adds a mostly settled API, major state/theme coverage, accessibility baseline,
  Showcase integration, and upgrade guidance.
- **Stable** requires a completed stability review, tracked API baseline, applicable
  interaction/adaptive/device tests, light/dark/custom theme evidence, accessibility
  review, complete shared Showcase and web docs, and no unresolved release blocker.
- **Deprecated** requires KDoc, registry, Showcase, and web migration guidance naming a
  replacement or explaining removal.

No status follows from rendering alone. Screenshot tests are added only for high-value,
deterministic visual matrices; they are not required as empty ceremony.

## Review checklist

- The common API is simple; state ownership, invalid states, slots, and style overrides
  are intentional; implementation helpers stay out of ABI.
- Variants, sizes, enabled/loading/focus/press behavior, theme overrides, contrast, and
  reduced motion are covered where applicable.
- Touch, keyboard, D-pad, Back, drag, scrolling, IME, and adaptive behavior are reviewed
  only where component semantics require them.
- Role, label, state, target size, focus order, font scaling, RTL, and screen-reader
  announcements are truthful and tested at the appropriate layer.
- Compose previews, meaningful tests, registry evidence, shared Showcase definition,
  generated code, examples, API/accessibility metadata, web prose, representative web
  preview, source link, and API compatibility are present.

Record device-dependent TalkBack, keyboard, foldable, or screenshot work as pending.
Do not mark a component Stable to hide missing evidence.
