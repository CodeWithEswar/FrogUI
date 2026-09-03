# ADR 0007: Stage only reusable Maven libraries; deploy docs independently

## Status
Accepted — Phase 03; extends ADR 0004.

## Context
The app, metadata tooling, test support, and docs are useful repository systems but
do not all have external library consumers. Independent version literals already drifted.

## Decision
Publishable modules are foundation, theme, and components. Stage release AARs, sources,
POM, and module metadata in a local build repository using one canonical version source.
Keep signing/Central upload separate. Docs builds consume metadata/prose and their future
Pages artifact is independent of Maven releases.

## Reason
This makes artifacts reviewable without implying a public release, leaking credentials,
or coupling documentation updates to Android binary publication.

## Consequences
CI builds and manual staging workflows exist. Actual signing, Central upload, Pages
website deployment, release notes/tags, and binary API compatibility gates remain
explicit release work. No app/test/tooling artifact is published accidentally.

## Alternatives
Publishing every module exposes internal tooling unnecessarily. A single coupled
Maven/Pages workflow would prevent small documentation-only deployments.
