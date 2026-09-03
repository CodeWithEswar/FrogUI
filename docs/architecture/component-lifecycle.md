# Component lifecycle and delivery

The [product contract](product-contract.md) defines quality. Prove FrogButton as the
reference before expanding the catalog; a composable alone is not a complete product.

## Status

| Status | Meaning | Gate |
| --- | --- | --- |
| Experimental | Implementation exists; API/behavior still developing. | Honest known gaps and canonical metadata for cataloged components. |
| Beta | Feature-complete contract under consumer validation. | Reviewed draft API, tokens/states, tests, workbench, docs; stabilization gaps explicit. |
| Stable | All applicable requirements below have reviewed evidence. | API review plus device, test, discovery, and documentation evidence. |
| Deprecated | Maintained during migration before removal. | Replacement/migration guidance, annotations, release policy. |

Roadmap entries have no implementation status. Button is Experimental. No current
component has evidence sufficient for Stable. Version strings do not prove maturity.

## Delivery workflow

1. Define purpose, realistic scenarios, and v1 scope.
2. Review values/callbacks, slots, Modifier placement, semantic types, escape hatches.
3. Implement native composition with small, explicit dependencies.
4. Integrate FrogTheme tokens, light/dark, and local overrides.
5. Define labels, roles, states, selection/errors, focus, and usable touch bounds.
6. Implement applicable enabled, disabled, pressed, focused, loading/error states.
7. Add useful Compose previews for themes, major states, and scaling risks.
8. Test interactions, ownership, semantics, and relevant layout behavior.
9. Author registry JSON, generate native data, compare capabilities with public APIs.
10. Build the actual component's Showcase destination with isolated preview theme.
11. Connect typed inspector controls and adaptive preview/property composition.
12. Add realistic examples and accurate usage code.
13. Document what, why, when, how, customization, states, and accessibility.
14. Run available checks and review API/metadata/docs parity; record manual checks.
15. Promote only to the maturity demonstrated by evidence and maintainer review.

## Stable evidence record

Create `docs/components/<id>-review.md` and reference it with `stabilityReview` in
JSON. Include API revision/commit, reviewer, date, test commands/results, Android/
device or emulator configuration, and evidence links. Each row needs results and
evidence or a justified not-applicable decision. Missing evidence blocks Stable.

| Requirement | Evidence |
| --- | --- |
| Public API | Necessity/naming, Compose conventions, slots, ownership, customization, Material leakage, dependencies, compatibility. |
| Theme | Light/dark and custom/nested themes across major variants/states; measured contrast. |
| Font scaling | Default/increased system fonts including 2×, long/localized content, no clipped/auto-shrunk essential text. |
| Accessibility | TalkBack role/label/state/selection/error announcements, no duplication, actual touch/focus bounds, localized spoken strings. |
| Interaction | Click/toggle/input contracts, caller-owned state, disabled/loading behavior, visible press/focus feedback. |
| Focus/native integration | Keyboard/directional focus; system back, IME, insets, dismissal where relevant. |
| Adaptive | Compact/medium/expanded and resizing, or justified natural primitive measurement. |
| Motion | Purposeful transitions, reduced/disabled motion with clear feedback. |
| Tests/previews | Behavior/semantics tests and useful theme/state/font previews; enum tests alone are insufficient. |
| Showcase | Actual component; Live Preview, Variants, Sizes, States, Interactive Props, Examples, API, Accessibility, Usage; isolated preview theme. |
| Registry | Accurate identity, category, status, version, routes, variants/sizes/properties. |
| Documentation | Real usage/customization/state/accessibility guidance, honest web representations, migration guidance where needed. |

Re-evaluate affected evidence after API/behavior changes. Keep incomplete components
Experimental or Beta instead of waiving requirements to increase a stable count.
