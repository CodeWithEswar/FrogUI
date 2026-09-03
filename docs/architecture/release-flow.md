# Release and CI flow

## Canonical version and publication boundary

`gradle/release.properties` owns version, app version code, and publication status.
The current version is an unpublished development snapshot. Gradle coordinates,
Showcase version labels, and generated docs/registry release metadata read this source.
Record actual releases and coordinate release notes, tag, registry, and Maven version
before changing publication claims.

Only foundation, theme, and components apply `frogui.publishing`. The convention
produces release AAR, sources JAR, POM (license/developer/SCM), and module metadata
in the ignored local `build/maven` repository:

```bash
./gradlew publishAllPublicationsToBuildRepository
```

This is local staging, not Maven Central. App, registry tooling, testing, docs, and
build logic are not published. A future signed release adds credentials from a
secret store, Central upload, API/consumer verification, and release coordination.
No signing keys, tokens, remote publishing repository, or source-install CLI is configured.

## CI workflows

- `android-ci.yml`: library/build changes → schema/route checks, JVM tests, Lint,
  debug assembly, Android test APK compilation. Device execution is a separate gate.
- `registry-docs.yml`: relevant metadata/docs/source changes → Node tests and docs
  catalog/search build, without Gradle or Android binaries.
- `release.yml`: manual local Maven staging and artifact upload; no signing or
  remote release. This does not publish a version to consumers.

These workflow files are configured; local execution does not imply a successful
GitHub-hosted run. Path filters avoid expensive Android jobs for prose-only edits.

Pages deployment is independent of Maven. See [docs flow](docs-flow.md) for the
artifact/deploy boundary; actual web UI and deployment are deferred.

## Release gates

Before stable publication: complete component evidence, binary/API compatibility
baseline, actual device/assistive-tech tests, consumer installation/POM checks,
signed artifacts, matching release notes/tag/version, and an honest documentation
surface. Pre-1.0 breaking changes need migration notes. After 1.0, removing stable
APIs requires a major release and at least one minor release of deprecation guidance.
