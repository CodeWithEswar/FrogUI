# ADR 0003: Android Showcase is the canonical interactive preview

## Status
Accepted — Phase 02. Governed by the [product contract](../product-contract.md).

## Context
Browser approximations cannot establish native Compose semantics, touch, focus,
IME, system back, motion, or adaptive behavior.

## Decision
Showcase imports actual FrogUI components and provides their native workbench.
Preview themes are independently selectable from the app theme. Web docs consume
shared metadata and may use labeled representative previews, screenshots, videos,
code, and QR/deep links; they must not claim those execute native Compose.

## Reason
Developers need an honest discovery surface and a real environment for accessibility
and interaction testing. If representations differ, native Android behavior wins.

## Consequences
Stable components require a complete native discovery contract. Android does not embed
a website for UI sharing. Web preview research may happen later, but v1 does not depend
on it. Missing workbench implementations must not be presented using another component.
