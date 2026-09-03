# Component registry contract

See [ADR 0002](decisions/0002-registry-metadata.md) and the
[product contract](product-contract.md). The registry describes UI; it never renders it.

## Ownership and identity

`registry/components/<id>.json` owns shared identity, display name, short description,
category, status, version, docs/source paths, variants, sizes, properties, and concise
examples. `registry/index.json` holds taxonomy and ordered `{ id, file }` references;
it does not copy descriptions/status. `component.schema.json` documents the shape.

Kotlin owns actual APIs and behavior. Long usage guidance, migration advice,
rationale, and verification evidence belong in Markdown, not registry JSON.

IDs are permanent, unique lowercase kebab-case. For Button, the record is
`components/button.json`, public API `FrogButton`, native route/`docsPath`
`components/button`, and web route `/components/button` (website adds deployment
base path). A future `frogui add button` command is not currently available.

`since` currently denotes the intended first release, not Maven publication. Preserve
it as the actual introduction version when released. Catalog only implemented
components with a workbench that renders that component. IconButton exists in source
but awaits its own discovery contract; the current catalog contains Button.

## Native generation

`:frogui-registry:generateComponentRegistry` reads JSON at build time and writes
`GeneratedComponentRegistry.kt` in ignored `build/generated/registry/kotlin`.
`preBuild` depends on generation. `FrogComponentRegistry` exposes generated data
records and search helpers. Edit JSON, never generated output.

Inspector behavior remains typed Kotlin calling actual composables. No runtime JSON
parsing, reflection invocation, or dynamic UI engine is introduced. Future web docs
must read the same component records. Button unit tests compare generated variant/
size lists to public Kotlin enum entries.

## Existing checks and limits

Run `./gradlew verifyProductContract` and `./gradlew testDebugUnitTest`.

Generation checks JSON parsing, required projected fields, unique IDs, index/file
coverage, canonical IDs/paths, source existence and named Kotlin function, allowed
categories/statuses, version shape, docs routes, unique properties/examples and
variant/size entries. Repository references cannot escape the checkout.

Stable records additionally require properties, examples, accessibility metadata,
and an existing nonempty `docs/components/<id>-review.md` evidence record. Its
existence is a prerequisite, not certification; reviewers assess completeness.

Full draft-07 validation is not implemented by this generator. Function-name lookup
is a limited source sanity check, not Kotlin signature parsing. Parameter/default
parity, Showcase destinations, and behavior need review. Binary API checks and a
website build are future gates; do not claim they passed CI before implementation.

Promotion follows the [lifecycle evidence requirements](component-lifecycle.md).
Showcase status is generated from JSON and cannot be independently promoted.
