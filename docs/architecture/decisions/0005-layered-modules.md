# ADR 0005: Separate tokens, theme, test support, and app branding

## Status
Accepted — Phase 03.

## Context
Foundation owned token types plus a Material theme bridge and duplicated app branding.
Repeated Android library setup and test theme fixtures now have concrete consumers.

## Decision
Keep token models in foundation; move FrogTheme/defaults and internal CompositionLocals
to theme. Components exposes theme, and theme exposes foundation. Keep one components
module. App owns brand composables/resources. Add one test-support module, allowed
only on test edges, and shared library/publication convention plugins.

## Reason
The separation removes Material and feature UI from foundation, makes public imports
intentional, and centralizes repeated build configuration without empty category modules.

## Consequences
Pre-release consumers change `foundation.theme` imports to `theme`. Raw LocalFrog*
providers become internal; use FrogTheme parameters for overrides. Foundation branding
is no longer a library API; app owns it. No visual component redesign is part of this move.
Test support depends on public theme APIs and never ships in published release graphs.

## Alternatives
Keeping theme in foundation was suitable earlier but preserves the now-visible dependency
mix. Category modules, patterns, brand modules, and benchmarks would add structure
without current responsibilities, so they remain deferred.
