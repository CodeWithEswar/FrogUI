# Public API design and compatibility

Strong defaults, explicit behavior, caller-owned state, composable extension and a
small public surface are the FrogUI API standard. Kotlin implementation is the
runtime source of truth; registry metadata describes it for native and web docs.
See the [Phase 07 audit](phase-07-api-review.md) for current reference decisions.

## Parameters and ownership

For new APIs, put required semantic values and behavior callbacks first, then
`modifier: Modifier = Modifier` as the first optional parameter. Follow with
semantic configuration, state, meaningful sizing, slots, style and advanced
interaction. Keep primary composable `content` last for trailing-lambda calls.
Existing parameter order and names are compatibility commitments: do not reorder
Button or Drawer merely to match this guide.

| Family | Names and guidance |
| --- | --- |
| Content | `value`, `title`, `items`; strings for simple labels, slots for structured content |
| Behavior | `onClick`, `onValueChange`, `onCheckedChange`, `onExpandedChange`, `onDismissRequest` |
| Layout | Native `Modifier`; semantic `size` or `fullWidth` only when they add component meaning |
| Variants | Finite semantic enum/sealed type; never conflicting visual boolean flags or arbitrary strings |
| State | `enabled`, `loading`, `selected`, `checked`; values and callbacks belong to the caller |
| Slots | `content`, `leadingContent`, `trailingContent`, `supportingContent`, `actions`, `footer` for new APIs |
| Style | Small immutable FrogUI colors/default contracts; Compose `Shape`, `BorderStroke`, `PaddingValues` |
| Interaction | A remembered `MutableInteractionSource` where observation is useful |

Preserve established `leadingIcon`/`trailingIcon` names on Button. Those are
vendor-independent composable slots. Use receiver scopes only when layout semantics
justify them. Avoid speculative overloads, builders, giant style/config objects,
inheritance frameworks, app-specific models and per-edge layout parameters.

Simple controls do not need state objects. Drawer offers a Boolean-controlled
overload and an optional saveable state helper for external open/close triggers.
Dismissal requests never mutate caller state automatically. Current Drawer state
records requested visibility, not animation progress or completion; suspend
`open()`/`close()` return after updating state. Preserve that behavior when evolving
it. Do not invent persistent/modal modes, Drawer sizes or demo-only public props.

## Defaults and style

Resolve semantic defaults from the local `FrogTheme` through component Defaults
objects. Consumers can override individual colors and Compose shape/border/padding
values. Preserve default variants, dimensions, tokens and disabled behavior unless
a behavior change is explicitly reviewed. Button's `fullWidth` remains meaningful:
it fills both the outer target and inner surface; outer `Modifier.fillMaxWidth()`
alone does not change both layers.

Public data-class constructors, `copy`, destructuring, enum values, Defaults
members and theme tokens are API too. Keep their established order and types.
Prefer final immutable data and read-only collections; do not falsely annotate
mutable objects `@Immutable`. Use a state helper's factory/Saver when useful, without
removing an existing public constructor just to enforce a new preference.

## Module and implementation boundaries

Published artifacts are foundation, theme and components. Component signatures
must not expose Material types/default objects, Hugeicons or image-loader types,
Showcase state, Markdown/registry models or app infrastructure. Internal use of
AndroidX/Material primitives is permitted. Compose primitives are interoperability
contracts, not leakage. Existing theme-level `FrogMotion` spec helpers are retained;
new components should consume semantic motion tokens instead of accepting internal
`Transition`/`Animatable` objects. Helpers and composition locals start internal or
private; a public helper requires an actual consumer use case.

## Accessibility and KDoc

Action controls provide role, enabled/loading state, focus treatment and at least
48dp targets within unconstrained parent layouts. Text labels normally supply
semantics; icon-only controls require a meaningful `contentDescription`. Decorative
slots use null descriptions. Loading suppresses activation and retains the action
label; decorative progress should not add duplicate announcements. Caller-provided
slots must preserve their own semantics. Explicit parent constraints can override
minimum sizing; custom colors require contrast review.

Drawer documents native-window versus embedded focus/Back boundaries, logical RTL
edges, insets and dismissal ownership. No automated check certifies human TalkBack
speech. KDoc must explain purpose, ownership, important defaults, slots and meaningful
limitations, with parameter/property docs for public configuration models. Prefer
compiled public-API examples to snippets with app-internal dependencies.

## Experimental APIs and deprecation

The current unpublished `0.1.0-SNAPSHOT` uses canonical lifecycle metadata and KDoc
to identify Experimental components. No Kotlin opt-in annotation policy exists yet;
this phase does not impose a new source-level opt-in requirement on all consumers.
A future annotation policy must explicitly define scope, warning/error level and
migration, then mark declarations consistently. Package names alone are not a policy.
Stable requires reviewed signatures and behavior, KDoc, compatibility baselines,
tests, accessibility, registry and documentation evidence.

Retain obsolete public helpers through warning-level `@Deprecated` with accurate
migration guidance. Add `ReplaceWith` only when it faithfully preserves behavior.
Do not use error/hidden deprecations or remove published declarations casually.
After 1.0, stable removals require a major release and at least one minor release
of migration guidance. Pre-1.0 changes still need release notes and review.

## Compatibility workflow

Review every change as intentional compatible, intentional breaking,
experimental-only or accidental. Named parameters and defaults are Kotlin source
API. Adding a defaulted parameter changes JVM descriptors and default bridges and
can break already compiled clients; it is not automatically binary compatible.
Enums can break exhaustive `when` consumers, data-class additions affect generated
methods, and new abstract interface members affect implementers.

For the deprecated Boolean-side Drawer overload, preserve placement with
`presentation = if (side) FrogDrawerPresentation.Side else FrogDrawerPresentation.Bottom`.
Move its `actions` into a Row in `footer`, and its `onBack` into a navigation button
plus `onBackRequest`. Canonical `actions` belongs in the header. The deprecated
`AnimationDurationMs` constant remains available but is not used by the renderer;
configure theme motion instead. These need contextual migration, so no misleading
automatic `ReplaceWith` is supplied.

The build extracts Kotlin-aware JVM ABI from each publishable release AAR using
JetBrains Binary Compatibility Validator. `apiBuild` writes candidates under the
module's build directory; `apiCheck` compares them with version-controlled `.api`
files; `apiDump` is an explicit maintainer operation to accept a reviewed candidate.
`check` and CI run `apiCheck`, never `apiDump`. Keep Experimental declarations in
the baseline too. Source names/default expressions and behavior require separate
review, compiled consumer calls, registry validation and interaction tests.

`verifyPublicApiBoundary` also rejects published ABI references to Material,
Showcase, registry, testing, Hugeicons, CommonMark or Coil implementation types.
Its dependencies are build-only and do not enter consumer POMs/AARs.

Registry validation includes a build-time lexical check of the first canonical
Kotlin overload's parameter names, types, default expressions and order. It supports
the current declaration grammar and reports unsupported signatures instead of
silently accepting them. Kotlin compilation remains authoritative; the check is
neither a full Kotlin parser nor runtime reflection. Secondary overloads and companion
APIs remain documented in KDoc/prose and covered by compiled consumer calls and ABI.

```text
./gradlew apiBuild
review build/api/*.api against api/*.api in each published module
classify changes; update migration notes, code examples and metadata
./gradlew apiDump                  # only after that review
./gradlew apiCheck check
```

Tooling references: [BCV](https://github.com/Kotlin/binary-compatibility-validator)
and [AGP built-in Kotlin](https://developer.android.com/build/migrate-to-built-in-kotlin).
The adapter uses release AAR artifacts because the selected BCV plugin's automatic
Android integration expects the older standalone Kotlin Android plugin.

## Proposal and review checklist

Before implementation, record:

```text
Component / purpose:
Required content and callbacks:
Caller state / justified state helper:
Variants / sizes / invalid combinations:
Slots / receiver scopes:
Style defaults and overrides:
Accessibility and advanced interaction:
Expected public models / constructors:
Compatibility and lifecycle status:
```

Before merge, verify common-case simplicity, ownership, parameter names/order,
semantic variants, slot sufficiency, Material/vendor boundaries, KDoc, API dump
diff, source/behavior compatibility, canonical registry properties/types/defaults,
shared Showcase inspectors/code/API/accessibility, and web metadata. Inspector
controls may expose a subset of API; clearly identify demo content controls.
