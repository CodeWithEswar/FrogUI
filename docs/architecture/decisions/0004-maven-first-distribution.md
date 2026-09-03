# ADR 0004: Maven precedes optional source-owned CLI distribution

## Status
Accepted — Phase 02. Governed by the [product contract](../product-contract.md).

## Context
Maven consumption and copying source offer different ownership and upgrade workflows.
Building both now would delay the core API and component quality work.

## Decision
Use Maven as the initial distribution model. Defer `frogui init` and `frogui add`
source installation beyond v1 unless scope is explicitly changed. Publication and
coordinates must be verified before being advertised as available.

## Reason
Standard Android dependencies provide straightforward adoption and upgrades while
the component architecture stabilizes.

## Consequences
Keep component/default/theme dependencies small and understandable so future source
ownership remains possible. Do not couple controls to hidden registries, proprietary
runtimes, or consumer app architecture. This ADR does not implement publishing or CLI
infrastructure; both have separate delivery work.
