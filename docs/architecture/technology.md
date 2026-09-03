# Technology Foundation, Dependency Boundaries & Engineering Standards

This document specifies the technology decisions, dependency boundaries, and engineering standards for **FrogUI**.

---

## 1. Core Technology Decisions

| Concern | Canonical Technology | Boundary & Rationale |
| :--- | :--- | :--- |
| **Android Language** | Kotlin 2.2.10 | Kotlin-first. Explicit nullability, Compose stability annotations (`@Immutable`, `@Stable`), slot APIs, named parameters, and state hoisting. |
| **UI Runtime** | Jetpack Compose | Compose-first. No parallel XML layouts or custom `View` hierarchies in v1. Standard Android Compose interoperability supported via `ComposeView` where consumers require it. |
| **Build System** | Gradle Kotlin DSL | Centralized dependency catalog in `gradle/libs.versions.toml`. No hardcoded strings in module builds. |
| **Asynchronous State** | Kotlin Coroutines & Flow | Used strictly where asynchronous behavior genuinely exists (e.g. `FrogDrawerState.open()` / `close()` suspend functions). No async complexity forced onto synchronous components. |
| **Media & Images** | Optional Adapter Layer | Coil remains an optional integration adapter. Core reusable modules contain **zero** network, HTTP, or image decoding dependencies. |
| **Showcase Icons** | Hugeicons Free 4.3.0 | Consistent iconography across the Showcase application (toolbars, bottom bar, tabs, inspectors). |
| **Component Icons** | Composable Slots | Reusable library components (`FrogButton`, `FrogDrawer`) accept composable icon slots (`leadingIcon`, `navigationIcon`, etc.), remaining completely icon-pack independent. |
| **Registry System** | JSON Schema (v1) | Machine-readable contract in `registry/` validated via AJV; generates typed Kotlin (`frogui-registry`) and TypeScript (`docs/src/generated/`) models. |
| **Web Documentation** | React 19 + Vite 6 + TypeScript + Tailwind CSS 4 | Fast, modern static documentation platform deployed directly to GitHub Pages. |
| **Syntax Highlighting** | Shiki 3.1.0 | High-fidelity build-time and client-side syntax highlighting across Kotlin, Gradle DSL, JSON, and TypeScript. |
| **Distribution** | Maven Central + GitHub Releases | Standard release publications with sources JAR, verified POM metadata, and local reviewable staging (`build/maven`). |

---

## 2. Module Dependency Direction & Governance

The FrogUI repository enforces a strict, acyclic dependency hierarchy verified via `gradle/product-contract.gradle.kts`:

```
frogui-foundation (Zero project dependencies; primitive tokens & models)
       │
       ▼
  frogui-theme   (Semantic token resolution, FrogTheme, CompositionLocals)
       │
       ▼
frogui-components (Reusable Compose components: FrogButton, FrogDrawer)
       │
       ▼
      app        (FrogUI Showcase application & laboratories)
```

### Prohibited Dependencies in Reusable Modules
1. **No Networking:** No Retrofit, OkHttp, Ktor, or HTTP clients.
2. **No Dependency Injection:** No Hilt, Dagger, or Koin in library components.
3. **No Database / Storage:** No Room, SQLite, or DataStore.
4. **No Third-Party Icon Sets:** Components take slots, not icon dependencies.
5. **No Architectural Inversion:** Lower modules never depend on higher modules or Showcase code.

---

## 3. Showcase Icon & Motion Standards

- **Restrained Motion:** Bottom navigation applies subtle Compose-native transitions (`animateFloatAsState`, `animateDpAsState`, `animateColorAsState`).
- **Scale:** Selected destination scales smoothly from `1.0x` to `1.06x`.
- **Active Indicator:** Smooth horizontal expansion from `0dp` to `16dp` with rounded corners.
- **Accessibility:** Queries `LocalFrogMotionEnabled` to respect reduced-motion settings, disabling scale animations when reduced motion is requested.

---

## 4. Verification & Automated Quality Gates

All technology boundaries and engineering standards are validated through automated CI workflows:
- `./gradlew verifyArchitecture`: Validates dependency contract, schema compliance, docs generation, and test suites.
- `npm test`: Executes 13 automated node test suites across icon sets and registry projections.
- `npm run docs:typecheck` & `npm run docs:build`: Typechecks and bundles the documentation platform with 0 errors.
- `./gradlew testDebugUnitTest`: Validates state machines and component behavior across all modules.
