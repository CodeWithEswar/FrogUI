# Dependency rules and graph validation

The [product contract](product-contract.md) and [layering ADR](decisions/0005-layered-modules.md)
govern this acyclic graph. Arrows below mean “depends on.”

| Module | Allowed production project edges | Allowed test project edges |
| --- | --- | --- |
| foundation | None | None |
| theme | foundation | testing |
| components | foundation, theme | registry, testing |
| registry | None | None |
| testing | theme | None |
| app | foundation, theme, components, registry | those libraries plus testing |

Actual components exposes theme with `api`; theme exposes foundation. Foundation's
public token types use Compose Runtime, graphics/text, shapes, and animation core.
It has no Material or Android Core dependency. Theme/components may use Material
internally. Registry uses only Kotlin/Compose Runtime and the BOM. Testing is an
internal Android test harness; it is not published or allowed in consumer release dependencies.

`gradle/product-contract.gradle.kts` checks project edges on production and test
`api`, `implementation`, `compileOnly`, and `runtimeOnly` configurations, including
variant prefixes. It rejects unlisted modules/edges, raw file dependencies, direct
external dependencies outside the allowed families, Material in foundation, and
test UI dependencies in production libraries (except debug-only test manifests).
Build-plugin tooling configurations are separate.

Run `./gradlew verifyArchitecture` for dependency checks plus registry/docs and
typed Showcase route tests. Module `check` also depends on `verifyProductContract`.
The graph cannot gain an upward edge or test-support runtime edge without changing
this explicit policy. Review transitive dependencies and public types: this is
not a transitive security audit, Kotlin semantic analyzer, or binary API validator.

Resource ownership: app owns branding/launcher/platform theme assets; components
owns generic spoken-state strings; foundation owns token models; theme owns resolvers.
There are no copied library component sources in app. Test utilities may depend on
public themes but cannot hold production state. Optional integrations need their own
reviewed module boundary; services/backends are not part of the core UI ecosystem.
