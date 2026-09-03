# FrogUI Architecture — Dependency Rules & Isolation Guarantees

## 1. Dependency Graph

FrogUI enforces a strict acyclic dependency direction. Lower layers have zero visibility into higher layers:

```text
    ┌──────────────────────┐
    │  frogui-foundation   │  <-- Design tokens, themes, branding
    └──────────────────────┘
               ▲
               │ (implementation)
    ┌──────────────────────┐
    │  frogui-components   │  <-- Pure Compose UI components
    └──────────────────────┘
               ▲
               │ (implementation)
    ┌──────────────────────┐
    │   frogui-patterns    │  <-- Higher-level compositions (planned)
    └──────────────────────┘
               ▲
               │ (implementation)
    ┌──────────────────────┐
    │         app          │  <-- Showcase application & workbench
    └──────────────────────┘
```

The registry module (`:frogui-registry`) provides metadata models and search helpers:
* It depends only on basic Compose primitives (e.g. `@Immutable`).
* It does not introduce runtime coupling between components.

---

## 2. Forbidden Edges

The following dependency directions are strictly forbidden by architectural policy:

```text
❌ foundation  ──► components    (Foundation must remain component-agnostic)
❌ components  ──► app           (Library must never depend on the showcase)
❌ foundation  ──► app           (Foundation must never depend on the showcase)
❌ components  ──► docs          (Library has no knowledge of web documentation)
❌ library     ──► showcase      (No cyclic or reverse coupling allowed)
```

Any pull request introducing circular or upward dependencies will be rejected by CI.

---

## 3. Module Boundaries & Responsibilities

### `:frogui-foundation`
* **Allowed**: Kotlin stdlib, Compose UI, Compose Runtime, Compose Graphics, VectorDrawable XMLs.
* **Forbidden**: Components (`FrogButton`, `FrogCard`), navigation libraries, network libraries.

### `:frogui-components`
* **Allowed**: `:frogui-foundation`, Compose UI, Compose Material3 (for accessibility primitives), Compose Foundation.
* **Forbidden**: Showcase state (`ButtonDemoState`), Showcase UI (`ComponentPreviewCanvas`), navigation controllers.

### `:frogui-registry`
* **Allowed**: Kotlin stdlib, Compose Runtime (`@Immutable`).
* **Forbidden**: Android framework classes, UI layout composables, showcase state.

### `:app`
* **Allowed**: `:frogui-foundation`, `:frogui-components`, `:frogui-registry`, Compose Material3, Navigation Compose, Activity Compose.
* **Role**: Orchestrates screens, hosts the interactive workbench, and exercises library components on real devices.
