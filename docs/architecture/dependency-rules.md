# Dependency rules

The [product contract](product-contract.md) and ADRs govern dependency decisions.

## Production boundaries

| Module | Allowed project dependencies | Responsibility |
| --- | --- | --- |
| `:frogui-foundation` | None | Semantic tokens, theme, branding; internal Material bridge allowed. |
| `:frogui-components` | Foundation | Reusable native controls; no app state or tooling runtime. |
| `:frogui-registry` | None | Generated metadata, models, categories, search. |
| `:app` | Foundation, components, registry | Native Showcase navigation, demo state, typed inspector. |

Components exposes foundation through `api` because public defaults and theme types
use it. Registry is independent; components never requires it in production. The
planned patterns layer does not exist and needs a boundary review when introduced.

Foundation/components may declare Kotlin, Compose, and AndroidX Core dependencies.
Registry may declare Kotlin, Compose Runtime (annotations), and the Compose BOM.
Its Android library packaging does not permit framework UI classes or composable
factories in metadata models. Material is internal, not the public semantic API.

Optional integrations need explicit requirements and separate boundaries. Simple
components must not require the whole ecosystem or dictate consumers' app architecture.

## Automated enforcement

`gradle/product-contract.gradle.kts` checks declared dependencies during configuration.
It rejects forbidden project edges, unreviewed modules, and direct external library
dependencies outside the families above. It checks production `api`, `implementation`,
`compileOnly`, and `runtimeOnly` configurations, including variant prefixes.
Configurations whose names contain `test` are excluded. Build-plugin tooling is
outside the runtime policy. Components uses registry only via `testImplementation`
to compare public enums with metadata.

`verifyProductContract` also validates/generates registry data; each module's `check`
depends on it. This is a declaration check, not a transitive dependency audit,
source-import linter, binary API checker, or side-effect detector. Review transitive
and file dependencies, public types, and behavior when changing build configuration.
New dependency families require documented justification and a policy update;
significant architectural deviations require an ADR.
