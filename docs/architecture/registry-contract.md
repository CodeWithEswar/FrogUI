# FrogUI Architecture — Component Registry Contract

## 1. Registry Purpose

The FrogUI Component Registry serves as the single machine-readable contract linking Kotlin source code, the Android Showcase application, GitHub Pages documentation, and future automation tooling.

```text
    Kotlin Implementation (:frogui-components)
                         │
                         ▼
        Canonical Registry (registry/components/*.json)
                         │
        ┌────────────────┼────────────────┐
        ▼                ▼                ▼
  Showcase App     GitHub Pages      Future Tooling
  (Navigation &    Documentation     (CLI / Scaffolding)
   Inspector)       (Web Pages)
```

The registry describes components; it does not replace the authoritativeness of the Kotlin language compiler.

---

## 2. Component Identification Rules

Every component must possess a permanent, unique identifier adhering to these rules:

1. **Format**: Lowercase, kebab-case (`^[a-z0-9]+(-[a-z0-9]+)*$`).
2. **Stability**: Never changed once published.
3. **No Generated UUIDs**: Must be readable and intuitive (`button`, `card`, `text-field`, `dialog`).
4. **Universal Mapping**:
   * Registry ID: `button`
   * Android Package: `io.github.codewitheswar.frogui.components.button`
   * Primary Composable: `FrogButton`
   * Showcase Route: `components/button`
   * Documentation URL: `/components/button`
   * Future CLI Command: `frogui add button`

---

## 3. Schema Structure & Core Fields

The registry enforces strict conformance against `registry/schema/component.schema.json`:

```json
{
  "$schema": "../schema/component.schema.json",
  "id": "button",
  "name": "FrogButton",
  "displayName": "Button",
  "description": "A versatile, accessible action button with 5 visual variants, 3 sizes, tactile press physics, loading state, and slot APIs.",
  "category": "actions",
  "status": "stable",
  "since": "1.0.0",
  "docs": "/components/button",
  "source": "frogui-components/src/main/java/io/github/codewitheswar/frogui/components/button/FrogButton.kt",
  "variants": ["Primary", "Secondary", "Outline", "Ghost", "Destructive"],
  "sizes": ["Small", "Medium", "Large"],
  "accessibility": {
    "role": "Role.Button",
    "minTouchTarget": "48dp",
    "talkBackNotes": "Provides Role.Button; declares stateDescription = 'Loading' during background operations."
  },
  "properties": [...],
  "examples": [...]
}
```

---

## 4. Prevention of Component Drift

The primary engineering objective of the registry is the total elimination of component drift:

* **Variant Drift**: The library must never support 5 variants while documentation lists 6 and the showcase displays 4.
* **Property Renaming**: When a Kotlin parameter name changes, automated CI checks will flag any mismatch with the registry schema.
* **Orphan Components**: Components removed or deprecated from Kotlin source must have their registry status updated accordingly.

---

## 5. Automated Validation Rules

The following automated rules are enforced by unit tests and CI pipelines:

1. **Schema Compliance**: Every component JSON must pass draft-07 validation against `component.schema.json`.
2. **ID Uniqueness**: No two components may share an ID in `registry/index.json`.
3. **Source Verification**: The `source` file path must exist on disk in the repository.
4. **Category Conformance**: Must match one of the 7 official categories: `actions`, `inputs`, `data-display`, `feedback`, `navigation`, `overlays`, `layout`.
5. **Status Conformance**: Must match one of the 4 lifecycle statuses: `experimental`, `beta`, `stable`, `deprecated`.
6. **Non-Empty Contracts**: Stable components must provide complete `properties` and `examples` arrays.
