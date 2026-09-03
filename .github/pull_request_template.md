## Problem and resulting behavior

Describe the user scenario, component/module affected, and resulting behavior.

## Product contract

- [ ] Read the product contract and checked v1 scope; link an ADR for significant deviations.
- [ ] Application state stays with callers; slots, native Modifier, semantic FrogUI APIs, and local theme tokens remain composable.
- [ ] No accidental Material API leakage, hidden side effects, upward module dependency, or runtime registry rendering.
- [ ] Shared metadata comes from registry JSON; catalog and docs claims match actual implementation.

## Validation

List commands/results and relevant device evidence. Explain any inapplicable checks.
For component changes, include accessibility (TalkBack, labels/states, touch bounds,
focus, contrast), light/dark, increased fonts, adaptive windows, and reduced motion.
For Stable promotion, link the completed component lifecycle evidence record and API
review. Missing evidence means the component stays Experimental or Beta.
