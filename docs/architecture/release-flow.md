# Release direction and quality gates

Follow the [product contract](product-contract.md), [component lifecycle](component-lifecycle.md),
and [Maven-first ADR](decisions/0004-maven-first-distribution.md).

## Available local checks

```bash
./gradlew verifyProductContract
./gradlew testDebugUnitTest
./gradlew lintDebug
./gradlew assembleDebug
./gradlew check
```

Contract checks cover declared dependency boundaries and registry validation/generation.
Unit tests cover catalog behavior and Button variant/size parity. Android Lint and
assembly are available through AGP. Report actual results; none alone certifies
accessibility or API stability. Device/Compose semantics tests remain reference-component work.

## Gates still to establish

There is no configured CI pipeline, binary API baseline/check, full JSON Schema
validation, web documentation build, or Maven publication yet. Implement and run
these before claiming an automated release pipeline. Publication also needs API
compatibility review, component stability evidence, consumer installation verification,
artifact metadata/license checks, and matching docs/Showcase versions.

Maven is initial distribution. Intended artifact names include `frogui-foundation`
and `frogui-components`; registry is for tooling, not required by component consumers.
Coordinates become authoritative only after publication is configured and verified.
Use local project dependencies today; a published `1.0.0` artifact is not implied.
Source-install CLI remains optional post-v1 work.

## Versioning

Use semantic versioning: major for incompatible public changes, minor for compatible
features, patch for fixes. Pre-1.0 breaking changes need explicit release notes.
After 1.0, stable API removal requires a major release and at least one minor release
of deprecation/migration guidance beforehand. Record experimental API changes clearly;
an experimental component label never licenses silently breaking a stable API.
