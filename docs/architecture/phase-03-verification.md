# Phase 03 verification record

Verified locally on 2026-09-03 using Windows, the checked-in Gradle wrapper/JDK 21,
Node 24, SDK 36.1, and an Android 15 (API 35) device. This is architecture verification,
not a Stable component certification or a claim of a GitHub-hosted CI run.

## Commands and outcomes

| Command/check | Outcome |
| --- | --- |
| `npm test` | 13 registry/docs tests passed, including negative schema/source/route/version cases. |
| `npm run docs:build` | Generated canonical catalog and search data with `/FrogUI/` routes. |
| `gradlew verifyArchitecture` | Module policy, full schema/docs validation, typed native routes, and enum parity passed. |
| `gradlew testDebugUnitTest` | 14 JVM tests passed across app, components, and registry. |
| `gradlew assembleDebug` | Showcase APK and Android library AARs built. |
| `gradlew :app:assembleDebugAndroidTest :frogui-theme:assembleDebugAndroidTest` | Both test APKs compiled. |
| `gradlew :app:connectedDebugAndroidTest :frogui-theme:connectedDebugAndroidTest` | Three tests passed on Android 15: two app tests and theme nesting/isolation. |
| `gradlew lintDebug` | Zero errors; 29 Showcase warnings remain. Other modules have no lint warnings. |
| `gradlew publishAllPublicationsToBuildRepository` | Foundation, theme, and components staged locally with AAR, sources JAR, POM, and Gradle module metadata. |
| Staged POM inspection | No registry/testing/UI-test dependency in published artifacts; no Material in foundation. |
| Negative dependency checks | Rejected foundation → theme, Material in foundation, and components → testing production edges. Temporary edits restored; valid contract check passed afterward. |
| Documentation/source hygiene | File links resolve; `git diff --check` passes. Generated outputs/dependencies remain ignored. |

The normal build stored Gradle configuration-cache entries successfully. New test
dependencies required an online resolution once; an initial offline cache miss was
resolved before the successful build. CI workflows are configured but have not been
run on GitHub from this local task.

## Remaining boundaries

The published coordinates are still an unpublished development snapshot. No remote
Maven upload, signing, GitHub Release, or Pages deployment occurred. The docs output
is catalog/search data; the public web UI is deferred. Full Button accessibility,
visual/motion coverage, screenshot baselines, and binary API stability remain required
before Stable. Existing app lint warnings are recorded, not suppressed by a baseline.

See [testing strategy](testing-strategy.md), [release flow](release-flow.md), and the
[system overview](system-overview.md) for the maintained architecture contracts.
