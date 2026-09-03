# ADR 0001: Compose is the canonical v1 UI implementation

## Status
Accepted — Phase 02. Governed by the [product contract](../product-contract.md).

## Context
Maintaining parallel Compose and XML View components would split behavior, testing,
documentation, and API stabilization effort.

## Decision
Implement v1 UI with Kotlin and Jetpack Compose. Standard Compose/View interoperability
is allowed. Do not ship a parallel XML component catalog or a web/JavaScript UI runtime
inside Android. Resource XML for vectors, strings, and platform integration is allowed.

## Reason
A native, focused runtime supports touch, TalkBack, focus, IME, system back, insets,
and adaptive windows while leaving application architecture to consumers.

## Consequences
Consumers can host Compose in existing View applications. Dedicated View equivalents
are deferred beyond v1. New runtime support requires an explicit scope change and ADR.
Dependency checks and API review protect this boundary; Material may remain internal.
