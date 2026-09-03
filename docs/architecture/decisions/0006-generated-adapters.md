# ADR 0006: One validator generates native and docs metadata adapters

## Status
Accepted — Phase 03; extends ADR 0002.

## Context
Phase 02's inline Gradle generator validated only a subset of fields, and docs had no
consumer. Native route strings and hand-authored snippets could drift.

## Decision
Use versioned draft-07 schemas and pinned build-time Ajv validation for both outputs.
Generate Kotlin metadata and docs JSON from canonical records. Reference marked regions
of compiled native examples. App owns the exhaustive typed destination dispatch;
docs owns a data-only catalog/search projection and Markdown content.

## Reason
Shared validation catches metadata/route/source drift without introducing Android
binaries to web builds or a JSON renderer to Android.

## Consequences
Node and locked npm build dependencies are required for generation, never for Android
runtime. Generated output stays in ignored build directories. Routes and examples
are validated at build time. Kotlin compilation and typed-route tests supplement
source-reference checks; behavior/accessibility still require review and testing.

## Alternatives
Independent Gradle/TypeScript validators would duplicate schema rules. Runtime reflection
or binary extraction violates ownership and performance boundaries. A docs UI framework
is deferred until it has a real visual/content responsibility.
