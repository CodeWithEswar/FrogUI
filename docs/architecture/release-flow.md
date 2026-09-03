# FrogUI Architecture — Release Flow & Publishing Strategy

## 1. Quality Gates & Validation Flow

Every proposed change in FrogUI must clear an automated multi-stage pipeline before merging into `main`:

```text
    Pull Request
         │
         ▼
    Compilation (./gradlew assembleDebug)
         │
         ▼
    Unit Tests (./gradlew testDebugUnitTest)
         │
         ▼
    Static Analysis & Lint (./gradlew lintDebug)
         │
         ▼
    Registry Schema Validation (JSON Schema conformance)
         │
         ▼
    Binary API Check (Public surface compatibility)
         │
         ▼
    Documentation Build (Static verification)
         │
         ▼
       MERGE
```

---

## 2. Versioning Strategy

FrogUI adheres strictly to [Semantic Versioning 2.0.0](https://semver.org/):

```text
MAJOR . MINOR . PATCH
```

* **MAJOR**: Incompatible API changes, removal of deprecated components, breaking theme changes.
* **MINOR**: New components, new variants, backward-compatible feature additions.
* **PATCH**: Bug fixes, internal performance improvements, documentation corrections.

### Pre-1.0.0 vs Post-1.0.0 Guarantees
* **Pre-1.0.0 (`0.x.y`)**: Rapid evolution; breaking changes documented in release notes.
* **Post-1.0.0 (`1.0.0+`)**: Stable components require a full deprecation cycle (minimum 1 minor version) before API removal.

---

## 3. Publication Architecture

When release pipelines are activated in future phases, FrogUI will publish clean Maven artifacts:

```text
io.github.codewitheswar:frogui-foundation:<version>
io.github.codewitheswar:frogui-components:<version>
io.github.codewitheswar:frogui-registry:<version>
```

Consumer setup will be straightforward:

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

// build.gradle.kts
dependencies {
    implementation("io.github.codewitheswar:frogui-components:1.0.0")
}
```

> **Note**: As dictated by Phase 01 principles, no artifacts are published during this foundational stage. All release coordinates remain local project references until publishing infrastructure is formally approved.
