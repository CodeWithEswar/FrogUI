# FrogUI product contract

Status: Accepted for v1.0. This contract governs modules, public APIs, components,
Showcase, registry, documentation, and releases. It describes required behavior;
it does not certify today's implementation. See [current gaps](#current-implementation).

## Vision and principles

Make native Android UI composition predictable, customizable, accessible,
ownership-friendly, and pleasant for developers. Provide strong defaults without
taking ownership away from the developer. FrogUI is an open-source Kotlin,
Jetpack Compose, AndroidX, and Gradle Kotlin DSL component ecosystem.

Resolve design tradeoffs in this order:

**Predictability → Composability → Ownership → Accessibility → Customization →
Discoverability → Consistency → Performance.**

This ordering guides tradeoffs; it never waives accessibility or other release
requirements. A visually impressive component that fails its contract is incomplete.

## Public API philosophy

- Consumers own application state. Controls accept values and event callbacks;
  callbacks report interaction without silently changing navigation, persistence,
  networking, analytics, or authentication. Implementation state may include press
  interactions, animation progress, transitions, and temporary measurements.
- Follow native Compose conventions: required parameters first, then
  `modifier: Modifier = Modifier` as the first optional parameter for UI elements,
  and the main content slot last. Document where the modifier is applied.
- Prefer slots, semantic variants/sizes, FrogUI Defaults, and theme tokens to
  configuration objects or a parameter for every text/icon/layout detail.
- Own semantic types such as `FrogButtonVariant`, `FrogButtonSize`, and
  `FrogButtonColors`. Material may supply internal behavior, focus, text input,
  semantics, or layout, but component-specific Material colors, defaults, and
  state types must not dictate FrogUI's public API.
- Standard Compose types such as `Modifier`, `Shape`, `Color`, `PaddingValues`,
  and interaction sources are legitimate interoperability tools. Do not invent
  `FrogModifier`, `FrogRow`, `FrogColumn`, `FrogBox`, or `FrogText` without a
  meaningful behavior requirement. FrogUI must mix freely with consumer and
  AndroidX composables.
- Use obvious names (`FrogButton`, `FrogSwitch`), realistic examples (Save,
  Continue, Search, Enable notifications), and useful customization through
  FrogUI colors/defaults, slots, and native modifiers. Branding belongs in product
  identity, not novelty API terminology.
- Before adding a parameter, ask: Is it necessary and understandable? Is it
  idiomatic Compose? Could composition solve this? Does it leak implementation?
  Can consumers customize it? Will it age well? Does it add unnecessary dependencies?
  Would we still want it if the internal implementation changed?

The state-hoisting shape below is illustrative; `FrogSwitch` is not implemented yet:

```kotlin
FrogSwitch(
    checked = notificationsEnabled,
    onCheckedChange = { notificationsEnabled = it }
)
```

Use `FrogTheme { ... }` as the design-system boundary. Semantic colors, typography,
spacing, shapes, elevation, and motion come from the current composition. Support
local overrides and nested themes. No global mutable style manager or hidden
registry state. Raw palette values belong in token definitions, not scattered
component implementations. Light and dark are required from initial implementation.
FrogUI develops its own Android design language; matching shadcn's colors, radius,
typography, dimensions, documentation appearance, or web interactions is not a goal.

## Accessibility contract

Each relevant component documents role, label association, state/selection,
disabled and error semantics, focus behavior, touch target, font scaling, and
contrast. Mark an inapplicable item with a reason, never silently omit it.

- Interactive targets must provide at least a 48dp × 48dp usable target, including
  visually small controls. Verify actual touch/focus bounds and neighboring target
  separation; a size constant or outer layout alone is not proof.
- Test TalkBack on Android for role, label, checked/selected state, enabled state,
  loading, and errors where relevant. Avoid duplicate label/state announcements.
  Decorative icons must not add noise. Localize library-owned spoken strings.
- Test keyboard and directional focus, activation, focus visibility, and dismissal
  where applicable. Disabled/loading controls must follow their documented contract.
- Test default and increased system font sizes (include 2× as a project test case),
  long/localized labels, and light/dark themes. Adapt layout; do not auto-shrink text,
  clip it to conceal overflow, or force every label to one line.
- Record measured contrast for relevant foreground/background pairs and states.
  Project targets are 4.5:1 for normal text, 3:1 for large text, and 3:1 for essential
  non-text indicators; document applicability and exceptions, including inactive UI.

Semantic UI tests and measured checks supplement manual assistive-technology
testing. Neither a screenshot nor a passing enum/default-value test certifies accessibility.

## Responsive contract

Design for compact, medium, and expanded windows on phones, foldables, tablets,
and large Android screens where applicable. Use available window constraints,
not device labels or an assumed 390dp screen. Verify resize, orientation, insets,
IME, long content, and system back for components that interact with them.

Small primitives can adapt naturally through Compose measurement. Do not add
breakpoint APIs to Checkbox, Switch, or Badge without a need. Navigation, dialogs,
sheets, panes, and form patterns need deliberate composition decisions. Showcase
uses sequential navigation/content/preview/properties on compact windows and
meaningful navigation/preview/properties panes when space permits, rather than
stretching a phone layout. Record medium-window behavior too.

## Motion and testability contract

Motion communicates state or continuity: press, selection, loading, expansion,
overlays, tab indicators, and progress. Avoid gratuitous animation. Significant
motion must respect reduced/disabled system motion expectations and provide clear
state feedback with reduced movement. Core interactions must not depend on large
springs, perpetual decoration, pulsing, or parallax. Verify the behavior on Android;
do not assume a duration token alone proves compliance.

Keep semantics inspectable and state transitions deterministic. Do not introduce
test-only public APIs, reflection-driven invocation, dynamic class loading, or a
JSON UI runtime. Add behavior tests for interactions and state ownership. Useful
Compose previews cover light/dark, major variants, disabled, loading/error where
relevant, and large fonts where valuable; avoid redundant preview proliferation.

## Discoverability contract

Before coding, developers must be able to learn a component's purpose, variants,
sizes, states, properties, disabled/loading behavior, light/dark and tablet behavior,
accessibility, customization, and usage code.

Every stable component needs **Live Preview, Variants, Sizes, States, Interactive
Props, Examples, API, Accessibility, and Usage** in the native Showcase. Use the
actual library component. The preview theme must be independently selectable from
the Showcase theme. `FrogButton` is the reference: prove the API, tokens, states,
motion, accessibility, previews, tests, registry, workbench, inspector, and docs
before replicating its architecture across the catalog.

`registry/components/*.json` owns shared identity, short description, category,
status, versions, routes, variants, sizes, property metadata, and concise examples.
Kotlin owns the actual public API and runtime behavior. The Showcase's metadata is
generated from the registry at build time; future web docs must consume the same
records. Do not hand-author competing metadata lists. Long explanations, migration
advice, design rationale, and verification evidence belong in Markdown.

The website may show clearly labeled screenshots, videos, animations, representative
browser previews, code, and QR/deep links. It must not claim to execute native Compose.
The Android Showcase is the canonical interactive preview and wins if representations
differ. Android and web share identity, metadata, and design intent, not a runtime.

## Distribution direction and ownership

Maven is the first distribution model. Coordinates and release automation remain
planned until publication is configured and verified. Consumers choose their app
architecture, navigation, networking, database, DI, image loader, analytics, and auth.
Optional integrations must explicitly declare additional requirements.

A future optional `frogui init` / `frogui add button` CLI may copy source into an
application, but is not a v1 requirement. Keep component dependencies understandable
and small (component → defaults/theme → Compose/AndroidX), without hidden repository-wide
machinery. Do not build a source installer or weaken v1 quality to anticipate it.

## v1.0 boundaries and progression

Compose is the canonical implementation. Standard Compose hosting in View-based
apps is allowed; parallel XML View components/adapters are out of scope. Android
resource XML (strings, vectors, manifests, platform themes) remains appropriate.

v1 does not require a shadcn visual clone, WebView/React/JavaScript/HTML UI engine
in Android, fake native web previews, 100+ components, enterprise grids,
spreadsheets, diagram/rich-text editors, charting suites, schedulers, complex
calendars, WYSIWYG, source-install CLI, cloud backend, authentication, AI features,
design editor, or drag-and-drop builder. Future web-preview experiments may be
considered separately; v1 cannot depend on them.

Progression is guidance, not a promised count: foundation → Button reference →
IconButton → TextField/TextArea/SearchField → Checkbox/Radio/Switch →
Card/Badge/Avatar/Chip → Divider/ListItem → Progress/Skeleton → Alert/Snackbar →
Dialog/BottomSheet → Tabs/SegmentedControl → TopBar/navigation →
EmptyState/ErrorState/LoadingState. A smaller consistent stable set wins over breadth.

Success means developers can install, apply FrogTheme, discover and inspect components,
understand APIs, preview states, customize, test accessibility, and build phone/tablet
interfaces without fighting the framework.

## Decision framework and enforcement

For each proposed feature ask:

1. Does it improve reusable Android components?
2. Does it stabilize or demonstrate public APIs?
3. Does it improve accessibility, responsiveness, or testability?
4. Is it necessary for discovery or documentation?
5. Can v1 succeed without it?

If 5 is yes and 1–4 are weak, defer it. If a request conflicts with these boundaries,
identify the conflict and treat it as post-v1 unless project requirements explicitly
change. Record significant changes in an ADR with Context, Decision, Reason,
Consequences, and Status; update affected contracts and checks in the same change.
Do not create ADRs for trivial choices.

Foundational decisions: [Compose](decisions/0001-compose-only-v1.md),
[metadata registry](decisions/0002-registry-metadata.md),
[native Showcase](decisions/0003-native-showcase.md),
[Maven first](decisions/0004-maven-first-distribution.md).

`./gradlew verifyProductContract` checks declared module/dependency boundaries and
registry schemas, source references, and docs destinations. Android builds generate
the Kotlin catalog; `verifyArchitecture` also checks typed native routes. Module `check` tasks depend
on it. See [dependency rules](dependency-rules.md) and [registry contract](registry-contract.md)
for exactly what is automated. API, device behavior, and scope decisions require
review using the [component lifecycle](component-lifecycle.md) and PR template.
Automation does not certify those judgments.

## Current implementation

Phase 03 establishes layered modules and generated adapters, not a component release. Button is **Experimental**;
IconButton exists in source but has no dedicated catalog/workbench contract yet.
Unimplemented components belong on the roadmap, not in a Beta catalog. No component
currently has evidence sufficient for Stable.

Before Button becomes the reference for expansion, complete and record:

- API review, customization review, and full metadata/API parity, including defaults.
- Semantic interaction tests, manual TalkBack and keyboard focus evidence,
  actual hit-target verification, and contrast measurements. Generic loading strings
  now belong to components resources; translation and announcement behavior need review.
- Light/dark, increased font sizes, compact/medium/expanded behavior, and motion
  with system animation reduction/disablement. Existing scaling/indicator code is
  not proof. Move component-specific raw overlay colors to semantic tokens as needed.
- Compose previews, complete accessibility/usage docs, and all discovery sections.
- Binary API compatibility tooling, full public docs UI, screenshot baselines, and
  signed Maven/Pages releases in their appropriate phases. Full schema validation,
  docs catalog/search data builds, CI workflow definitions, and local Maven staging
  now exist; they do not certify Stable or imply public publication.

## Contributor answer check

| Question | Binding answer |
| --- | --- |
| What and why is FrogUI? | Native Android components with predictable composition and developer ownership. |
| Compose-first? XML in v1? | Compose canonical; normal interop yes, parallel XML components no. |
| Is Material the public design API? | No. FrogUI owns semantic APIs; Material is an implementation tool. |
| Who owns application state? | The consumer, via values and callbacks. |
| Accessibility or dark mode optional? | Neither; both are component requirements. |
| Tablet behavior considered? | Yes, including medium/expanded window composition where applicable. |
| Native previews? Does the website run Compose? | Actual interaction in Android Showcase; website representations are labeled. |
| What does the registry own? | Shared metadata, never runtime rendering. |
| Is CLI required? Every component? | No. Maven first; quality and Button reference before catalog breadth. |
| What is Stable? | All applicable lifecycle requirements have review/test/device evidence. Otherwise Experimental or Beta. |

## Platform references

Native conventions follow the [Compose API guidelines](https://android.googlesource.com/platform/frameworks/support/+/androidx-main/compose/docs/compose-api-guidelines.md)
and [state-hoisting guidance](https://developer.android.com/develop/ui/compose/state-hoisting).
Accessibility practice follows [Android accessibility guidance](https://developer.android.com/guide/topics/ui/accessibility/apps).
