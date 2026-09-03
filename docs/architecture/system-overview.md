# FrogUI Architecture — System Overview

## 1. Executive Summary

FrogUI is an open-source, production-grade Android UI ecosystem and developer platform. It is engineered to solve a systemic problem in mobile component ecosystems: **component drift**, where the published library, the showcase demo app, the public documentation, and machine tooling gradually diverge.

FrogUI solves this by enforcing an uncompromising architecture:

> **One component has one canonical implementation, one public API contract, one registry identity, one native Showcase destination, and one documentation destination.**

---

## 2. Product Surfaces

FrogUI consists of four primary product surfaces coordinated by a single release and validation system:

```text
                                  FrogUI
                                     │
      ┌──────────────────┬───────────┴───────────┬──────────────────┐
      ▼                  ▼                       ▼                  ▼
 Android Library    Showcase App        Component Registry      Documentation
(:frogui-components)   (:app)               (registry/)            (docs/)
 Canonical runtime   Real-device dogfood    Machine-readable     GitHub Pages
   implementation    & component workbench      contract       public developer guide
```

### 2.1 The Android Library (`:frogui-foundation`, `:frogui-components`)
* **Role**: The single source of truth for runtime behavior, visual rendering, accessibility semantics, and public Kotlin APIs.
* **Guarantee**: Consumed by external Android applications. Must have zero dependency on showcase-specific state, demo hacks, or tooling.

### 2.2 The Native Showcase Application (`:app`)
* **Role**: The component laboratory and dogfooding application running on real Android devices.
* **Guarantee**: Renders the **exact production components** imported from the library modules. Never creates visual mockup duplicates or alternative demo components.

### 2.3 The Component Registry (`registry/`, `:frogui-registry`)
* **Role**: The machine-readable bridge connecting Kotlin runtime code to documentation, showcase discovery, and future CLI tooling.
* **Guarantee**: Describes the component's public contract, properties, examples, and taxonomy. Never contains alternative runtime implementations.

### 2.4 The Documentation Website (`docs/`)
* **Role**: The public GitHub Pages documentation hosted at `https://codewitheswar.github.io/FrogUI/`.
* **Guarantee**: Directly reflects registry metadata and Kotlin source contracts without manual divergence.

---

## 3. Canonical Ownership Hierarchy

The ecosystem maintains a strict top-down ownership model:

```text
    REAL IMPLEMENTATION
    (:frogui-components)
             │
             ▼
     PUBLIC API CONTRACT
     (Kotlin Function / Types)
             │
             ▼
     COMPONENT REGISTRY
     (registry/*.json & :frogui-registry)
             │
     ┌───────┴───────┐
     ▼               ▼
SHOWCASE APP   DOCUMENTATION
   (:app)         (docs/)
     │               │
     └───────┬───────┘
             ▼
      FUTURE TOOLING
```

The relationship is never inverted:
* The runtime library never depends on showcase UI.
* The runtime library never depends on web documentation.
* The showcase imports and exercises the library.
* Documentation presents the library.

---

## 4. Repository Monorepo Layout

```text
FrogUI/
│
├── app/                        # Native Showcase Application & Component Laboratory
│   ├── src/main/java/.../
│   │   ├── navigation/         # Adaptive phone/tablet navigation shell
│   │   ├── showcase/canvas/    # ComponentPreviewCanvas with isolated theme switching
│   │   ├── showcase/inspector/ # Real-time PropertyInspector & code snippet generator
│   │   └── showcase/screens/   # Home, Components, Detail Workbench, Foundation, About
│   └── src/test/.../           # Showcase unit tests
│
├── frogui-foundation/          # Design-system Foundation Tokens
│   ├── src/main/java/.../
│   │   ├── color/              # Strict monochrome Zinc palette & semantic colors
│   │   ├── typography/         # Accessible typography scale
│   │   ├── spacing/            # Intentional spatial rhythm tokens
│   │   ├── shape/              # Structural corner radius scale
│   │   ├── elevation/          # Restrained tonal elevation tokens
│   │   ├── motion/             # Physics-based spring animations
│   │   ├── branding/           # Vector-first cubic Bézier brand composables
│   │   └── theme/              # FrogTheme and CompositionLocals
│   └── src/test/.../           # Foundation unit tests
│
├── frogui-components/          # Pure Jetpack Compose UI Components
│   ├── src/main/java/.../
│   │   └── button/             # FrogButton & FrogIconButton reference implementation
│   └── src/test/.../           # Component sizing, state, and accessibility unit tests
│
├── frogui-registry/            # Kotlin Registry Contracts & Validation
│   ├── src/main/java/.../
│   │   └── registry/           # FrogComponentMetadata, properties, search, and categories
│   └── src/test/.../           # Registry integrity & schema conformance tests
│
├── registry/                   # Machine-Readable Public Registry Source
│   ├── schema/
│   │   └── component.schema.json # JSON Schema (draft-07) specification
│   ├── components/
│   │   └── button.json         # Canonical metadata for FrogButton
│   └── index.json              # Public registry manifest & taxonomy
│
├── docs/                       # Public Documentation Source
│   └── architecture/           # Enforceable architecture specifications
│
├── gradle/                     # Gradle Build Configuration
│   └── libs.versions.toml      # Centralized Version Catalog
│
├── .editorconfig               # Repository-wide code formatting standard
├── CONTRIBUTING.md             # Contribution guidelines & 15-step workflow
├── CODE_OF_CONDUCT.md          # Contributor Covenant v2.1
├── SECURITY.md                 # Vulnerability disclosure & SLA policy
└── README.md                   # Truthful public repository overview
```
