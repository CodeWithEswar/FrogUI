# FrogUI Architecture — Component Lifecycle & Delivery Workflow

## 1. Lifecycle Overview

In FrogUI, implementing a Composable function is only the initial step of engineering a component. A component is not considered complete until it is integrated into the design foundation, verified for accessibility, registered in the machine-readable catalog, exposed in the Showcase workbench, documented, and tested.

---

## 2. Canonical Lifecycle Statuses

Every component declares a lifecycle status in both Kotlin (`FrogComponentStatus`) and the machine-readable registry (`status`):

```text
┌────────────────┐      ┌────────────┐      ┌────────────┐      ┌──────────────┐
│  Experimental  │ ───► │    Beta    │ ───► │   Stable   │ ───► │  Deprecated  │
└────────────────┘      └────────────┘      └────────────┘      └──────────────┘
```

| Status | Definition | API Stability | Quality Gate |
| :--- | :--- | :--- | :--- |
| **Experimental** | Active exploration or proof of concept. | May break without deprecation. | Basic layout compiles; initial API drafted. |
| **Beta** | Feature-complete and under real-world dogfooding. | Minor changes require migration notice. | Unit tests pass; full token integration; Showcase screen active. |
| **Stable** | Production-ready reference component. | Strict semantic versioning compatibility. | 100% accessible (48dp touch target, TalkBack); two-pane workbench; complete docs; lint clean. |
| **Deprecated** | Scheduled for removal in future major release. | Preserved with `@Deprecated` annotations. | Replacement component specified in registry and documentation. |

---

## 3. The 15-Step Component Delivery Workflow

To prevent architectural drift and maintain uniform quality, every component must strictly follow these 15 steps:

### Phase A: Architecture & Design
1. **Define Component Purpose**: Document user scenarios, developer pain points, and why the component belongs in FrogUI.
2. **Design Public API Contract**: Define clear function signatures, default arguments, and `@Composable` slot parameters (`leadingIcon`, `trailingIcon`, `content`).

### Phase B: Implementation & Foundation Integration
3. **Implement Pure Composable**: Write the component in `:frogui-components`.
4. **Integrate Foundation Tokens**: Bind colors, typography, spacing, shapes, and motion exclusively through `FrogTheme`. Avoid hardcoded DP values or raw hex colors.
5. **Implement Accessibility Semantics**: Ensure 48dp minimum touch target boundaries (`FrogButtonDefaults.MinTouchTarget`), `Role` declarations, TalkBack announcements, and `stateDescription`.
6. **Implement Visual States**: Handle enabled, disabled, pressed, focused, and loading states cleanly.
7. **Create Compose Previews**: Add `@Preview` functions covering light and dark themes, multi-locale text, and font scale variations.

### Phase C: Verification & Tooling Integration
8. **Write Unit Tests**: Verify size measurements, variant counts, defaults, and interaction contracts in `:frogui-components/src/test`.
9. **Register Canonical Metadata**: Add entry in `registry/components/<id>.json` and register in `FrogComponentRegistry.kt` with matching properties, examples, and taxonomy.
10. **Build Native Showcase Screen**: Implement responsive preview in `:app` using `ComponentPreviewCanvas`.
11. **Add Property Inspector Controls**: Connect interactive toggles for variant, size, enabled, loading, and slot states.
12. **Add Realistic Preset Examples**: Provide tested usage patterns representing real production scenarios.

### Phase D: Documentation & Release
13. **Generate Public Documentation**: Document usage, parameter tables, and accessibility guidance in `docs/`.
14. **Validate API & Metadata Consistency**: Run automated checks verifying zero drift between Kotlin source, registry JSON, and documentation.
15. **Promote Component Status**: Move component status from `Experimental` to `Beta` or `Stable` via PR review.
