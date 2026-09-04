# Showcase and native example flow

The app's catalog/search reads generated `FrogComponentRegistry` data locally.
`ShowcaseRegistry` adapts records to explicit `ComponentDemo` destinations.
`ComponentDetailScreen` resolves a typed `ComponentShowcaseFactory` and renders the
single shared `ComponentDetailLayout`. Button and Drawer supply definitions with
component-owned state, compiled previews, inspectors and code generators.
Tests require exactly one destination per catalog ID and matching route strings.
Unknown IDs render an explicit missing-component state.

Button's state, inspector, definition, and examples live in
`app/src/main/java/io/github/codewitheswar/frogui/showcase/components/button`.
They import the same public Button/theme APIs available to consumers, with no
cross-module internal access, reflection, friend configuration, or source copies.
The app uses custom Compose shell controls, Hugeicons, and themed AndroidX form
controls in the inspector. Phone navigation has Home, Components, Playground, and
Foundation. Settings/About sit outside the dock. Medium windows use a rail and
expanded windows use a sidebar; detail routes have a back stack and hide the dock.

`ButtonExamples.kt` contains the compiled native preset functions shown by the screen.
Marked regions also generate docs/native code snippets, and app compilation checks
those examples against actual public APIs. The interactive code generator escapes
consumer label text as Kotlin literals; it is an illustrative usage view, not a
runtime source executor.

Drawer follows the same contract in `showcase/components/drawer`. Both use one
header, preview workspace, inspector host, four-tab strip, documentation layout,
code renderer, API renderer, accessibility renderer and example presentation.
`frogui://components/button` and `frogui://components/drawer` enter those same
routes on cold or warm launch. Unknown IDs use the shared missing-component state.
See [shared detail system](shared-component-detail-system.md) for ownership and QA.

Preview themes nest inside the app theme via public FrogTheme parameters. The new
test fixture exercises nested theme isolation. Catalog, preview, inspector, and
examples have no network/backend dependency and remain usable offline. External
documentation links may require internet.

Canonical prose under `docs/content` is packaged as app assets. CommonMark parses
it into showcase-owned models and Compose renders it natively. Code fences reuse
the same code block, highlighter, selection, and clipboard controls as the live
snippet. API tables consume typed registry properties and switch to stacked rows
on narrow content panes. See [showcase upgrade](showcase-upgrade.md) for dependency
decisions, motion behavior, and verification details.

App branding now has one owner in `showcase/branding` and app resources. The unused
legacy app theme and duplicate foundation brand implementation/resources are removed.
Application ID and launcher resource ownership remain with app.
