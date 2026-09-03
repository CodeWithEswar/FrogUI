# FrogUI system overview

FrogUI is an open-source native Android component ecosystem built with Kotlin,
Jetpack Compose, AndroidX, and Gradle Kotlin DSL. Read the binding
[product contract](product-contract.md) before making API or scope decisions.

## Product surfaces

| Surface | Owns | Boundary |
| --- | --- | --- |
| Foundation/components | Native APIs, tokens, rendering, interactions, semantics | No Showcase, registry, website, or consumer app architecture dependency. |
| Android Showcase | Discovery, typed inspector, actual component previews | Canonical native interaction; independently selectable preview theme. |
| Registry JSON / native registry | Shared metadata / generated data projection | No rendering engine or competing metadata authoring. |
| Documentation | Architecture and future usage guidance | Future website uses registry metadata and labeled representations. |

The website is planned. The repository currently contains architecture Markdown,
not a configured GitHub Pages build or browser Compose runtime.

## Repository map

```text
frogui-foundation/   Semantic tokens, theme, branding
frogui-components/   FrogButton and FrogIconButton source; depends on foundation
frogui-registry/     Data models, generated catalog, search; Compose Runtime annotations
app/                Native Showcase, preview canvas, inspector, screens
registry/           Canonical component JSON, index references, schema
docs/architecture/  Product contract, lifecycle, boundaries, registry/release policy, ADRs
gradle/             Version catalog, wrapper, product-contract checks
```

Button is the Experimental reference under development. IconButton awaits its own
catalog/workbench contract. Other components are roadmap items, not implemented Beta
controls. The catalog no longer advertises them as usable components.

Kotlin defines behavior and public signatures. Registry describes the contract and
generates native discovery data; future docs consume the same metadata. Showcase calls
actual composables. Native behavior wins when a web representation differs.

Components depends on foundation. App depends on foundation, components, and registry.
Registry is independent of the UI library. A patterns module is future work.
See [dependency rules](dependency-rules.md) for enforcement and limitations.

## Architecture decisions

- [0001: Compose-only v1](decisions/0001-compose-only-v1.md)
- [0002: Metadata registry](decisions/0002-registry-metadata.md)
- [0003: Canonical native Showcase](decisions/0003-native-showcase.md)
- [0004: Maven-first distribution](decisions/0004-maven-first-distribution.md)

Use the [component lifecycle](component-lifecycle.md), [registry contract](registry-contract.md),
and [release flow](release-flow.md) for implementation and release reviews.
