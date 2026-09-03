# ADR 0002: The registry contains metadata, not UI implementations

## Status
Accepted — Phase 02. Governed by the [product contract](../product-contract.md).

## Context
Separate lists in Kotlin, Showcase, and web docs already drifted in status, properties,
and available components. Discovery needs shared identity without a second UI engine.

## Decision
Author shared metadata in `registry/components/*.json`. Generate native catalog records
at build time and use the same records for future web docs. Kotlin APIs remain
authoritative for behavior. Registry models contain data and search helpers, not
composable factories, runtime JSON rendering, reflection invocation, or global UI state.
Long prose, migration guidance, and review evidence stay in documentation.

## Reason
One metadata source prevents conflicting catalogs while direct Kotlin composition
preserves native performance, testability, and understandable component dependencies.

## Consequences
Generation and validation are build concerns. Neither foundation nor components may
depend on registry at runtime. Inspector controls use explicitly typed Kotlin code.
Metadata generation does not validate behavior or replace API review. Source-owned
tooling can later consume metadata without becoming a v1 dependency.
