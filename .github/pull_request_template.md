## Problem and resulting behavior

Describe the user scenario, component/module affected, and resulting behavior.

## Product contract

- [ ] Read the product contract and checked v1 scope; link an ADR for significant deviations.
- [ ] Application state stays with callers; slots, native Modifier, semantic FrogUI APIs, and local theme tokens remain composable.
- [ ] No accidental Material API leakage, hidden side effects, upward module dependency, or runtime registry rendering.
- [ ] Shared metadata comes from registry JSON; catalog and docs claims match actual implementation.
- [ ] Reviewed API names/order/defaults and the candidate `.api` diff; classified source, binary and behavior impact. Linked migration guidance for deprecations or breaking changes.
- [ ] Ran `apiCheck`; any baseline update was explicitly reviewed, with no automatic `apiDump` step in CI.

## Component delivery

- [ ] Audited the component from specification through visual regression and resumed at
  the first incomplete gate instead of recreating healthy infrastructure.
- [ ] Added or updated `docs/components/<id>-delivery.md` from the canonical template;
  identity, status, gate classifications, evidence, and promotion decision are truthful.
- [ ] Showcase renders the canonical component through `ComponentDetailScreen`; web docs
  use the shared page and an isolated registered preview.
- [ ] Playground controls are typed/shared, generated Kotlin uses only public API, and
  demo-only state is not represented as a public property.

## Validation

List commands/results and relevant device evidence. Explain any inapplicable checks.
For component changes, include accessibility (TalkBack, labels/states, touch bounds,
focus, contrast), light/dark, increased fonts, adaptive windows, and reduced motion.
For Stable promotion, link the completed component lifecycle evidence record and API
review. Missing evidence means the component stays Experimental or Beta.
