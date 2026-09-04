# Registry flow and schema contract

`registry/components/<id>.json` is the canonical shared descriptive record.
`registry/index.json` owns taxonomy and ordered ID/file references. Schema version 2
is explicitly declared and validated using pinned Ajv with the draft-07 component,
example, and index schemas. It requires lifecycle evidence for
visual states, interactions, themes, adaptive classes, previews, and tests. Breaking
field or meaning changes require another schema version and migration.

Records own identity, short description, category/status, introduction version,
variants/sizes/tags, property metadata, docs path, native route/screen reference,
accessibility notes, example descriptors, and concise lifecycle evidence paths. Kotlin owns API/behavior. Long prose
and review evidence stay in Markdown. Examples reference marked regions of real
compiled Showcase Kotlin; generated snippets are never the only implementation.

## Build-time data flow

```text
registry/index.json + components/*.json + compiled example source
    → tools/registry/registry.mjs (shared schema/invariant validation)
    → frogui-registry/build/generated/registry/kotlin/.../GeneratedComponentRegistry.kt
    → FrogComponentRegistry → typed Showcase adapter and in-memory search

    → docs/generated/components.json
    → docs/src/registry/catalog.mjs + docs/content/components/<id>.md
    → docs/dist/catalog.json and docs/dist/search.json
```

Android generation runs before registry compilation. Both targets use the same
validator. Install the locked build dependencies with `npm ci --ignore-scripts`.
Generated outputs are ignored; author JSON and native examples, never generated Kotlin.

## Verification

`npm run registry:validate` checks complete schemas, IDs, categories, index coverage,
version agreement, repository-contained source paths, named public Kotlin functions and KDoc,
named Showcase screens/routes, docs content destinations, example regions, and unique
capabilities/properties/examples. It also verifies a KDoc'd Defaults object, Compose
preview coverage, unit/Android test sources, and a web preview that uses the shared
preview contract. Stable entries also require nonempty properties/
examples, accessibility metadata, and `docs/components/<id>-review.md`.

Each component also has a validator-enforced delivery record at
`docs/components/<id>-delivery.md`. It records specification, API, implementation,
theme/state, accessibility, preview, test, registry, Showcase, web docs, compatibility,
and visual-regression evidence. Identity and status must match the registry. Beta cannot
contain a missing, outdated, or duplicated gate; Stable requires all gates complete and
the separate stability review.

`npm test` exercises rejection cases. `verifyArchitecture` additionally runs native
destination coverage and Button enum parity tests. Source-name checks are limited
sanity checks; Kotlin compilation proves examples compile. Public default/parameter
parity, reflection/forks, and stability evidence quality still need human/API review.
An evidence path is a prerequisite, never automatic accessibility certification.

Every cataloged component needs a real destination. No placeholder Beta components
or silently borrowed Button previews. IconButton source is not yet catalog-ready.
Stable completeness is governed by the [lifecycle](component-lifecycle.md).

`gradle/release.properties` owns current development/release version, app version code,
and publication status. The current unpublished introduction line must match it;
released-history metadata will need an explicit release ledger when first publishing.
No generated version or `since` field claims a Maven upload occurred.
