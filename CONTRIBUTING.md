# Contributing to FrogUI

Thank you for your interest in contributing to FrogUI! We are building a professional, open-source native Android UI component ecosystem and developer-tooling platform.

---

## 1. Core Architectural Principles

Before writing code, please understand the non-negotiable architectural tenets of this repository:

1. **One Component, One Truth**:
   * One canonical runtime implementation (`frogui-components`).
   * One public API contract (`FrogButton(...)`).
   * One machine-readable registry identity (`button` in `registry/components/button.json`).
   * One native Showcase workbench destination (`components/button`).
   * One documentation destination (`components/button`).
2. **Strict Dependency Hierarchy**:
   * `frogui-foundation` ← `frogui-components` ← `frogui-patterns` ← `app`.
   * The runtime library must never depend on `app` or showcase-specific state.
   * `frogui-foundation` must never depend on `frogui-components`.
3. **No Alternative Implementations**:
   * The Showcase application and documentation must always render and reference the actual library components imported from the library modules. Never create "demo-only" duplicates.
4. **No Mascots or Cartoon Branding**:
   * FrogUI adheres strictly to a monochrome Zinc developer-tool design system.

---

## 2. Component Delivery Workflow

Every new or modified component must progress through the standard 15-step delivery lifecycle:

```text
01. Define component purpose & user scenarios
02. Design stable public API & slot contracts
03. Implement component in frogui-components
04. Integrate semantic tokens from frogui-foundation
05. Implement accessibility (touch targets >= 48dp, TalkBack semantics, Role)
06. Implement visual states (enabled, disabled, pressed, focused, loading)
07. Add Compose @Preview definitions
08. Add unit tests verifying sizing, variants, and behavior
09. Register canonical metadata in registry/components/<id>.json & frogui-registry
10. Build Showcase workbench destination in app
11. Add real-time PropertyInspector controls
12. Add realistic preset examples
13. Write comprehensive documentation in docs/
14. Validate API, registry, and schema consistency
15. Assign component lifecycle status (Experimental -> Beta -> Stable)
```

---

## 3. Development Environment

* **JDK**: OpenJDK 17 or higher
* **Android Studio**: Android Studio Ladybug (2024.2.1+) or newer
* **Android SDK**: API 34+ (target SDK 34, minimum SDK 24)

### Building and Testing

Run the following Gradle commands to ensure your changes comply with quality gates:

```bash
# Run unit test suite across all modules
./gradlew testDebugUnitTest

# Run Android Lint across all modules
./gradlew lintDebug

# Assemble library AARs and showcase APK
./gradlew assembleDebug

# Run all verification checks
./gradlew check
```

---

## 4. Conventional Commits

We enforce the [Conventional Commits](https://www.conventionalcommits.org/) specification for a clean, automated changelog and Git history:

* `feat(scope)`: A new feature or component (e.g. `feat(components): implement FrogButton`)
* `fix(scope)`: A bug fix (e.g. `fix(foundation): correct focus ring contrast in light theme`)
* `docs(scope)`: Documentation changes (e.g. `docs(architecture): add registry contract`)
* `test(scope)`: Adding or refactoring tests (e.g. `test(registry): add schema validation test`)
* `chore(scope)`: Build configuration, dependencies, or tooling updates (e.g. `chore(deps): bump AGP`)

---

## 5. Pull Request Guidelines

1. Fork the repository and create a feature branch from `main`.
2. Follow `.editorconfig` formatting rules (4 spaces for Kotlin, 2 spaces for JSON/XML).
3. Ensure `./gradlew check` and `./gradlew lintDebug` pass with 0 errors.
4. Include unit tests covering new component behaviors or registry changes.
5. Submit your PR with a clear description referencing the component ID and architecture role.
