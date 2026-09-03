# Contributing to FrogUI

Start with the [product contract](docs/architecture/product-contract.md) and
[architecture decisions](docs/architecture/system-overview.md#flows-and-decisions).
Provide strong defaults without taking ownership away from application developers.

## Before implementing

- Check v1 scope: reusable Compose components, accessibility, adaptive behavior,
  testability, and discovery. Parallel XML components, embedded web runtimes,
  enterprise widgets, and a source-install CLI are outside current v1 scope.
- Hoist application state. Use callbacks, slots, native Modifier, FrogUI semantic
  types, local tokens, and useful escape hatches. Material may remain internal.
- Review module dependencies and public API leakage. Do not dictate consumers'
  navigation, storage, networking, DI, image loading, analytics, or authentication.
- Showcase uses actual components. Label web representations honestly. Author shared
  metadata in registry JSON; never edit generated native catalogs.
- Use professional API names and a native FrogUI design language rather than copying
  shadcn's visual system or web interactions.

Significant deviations need an ADR with Context, Decision, Reason, Consequences,
and Status. Identify conflicting requests and defer them unless requirements
explicitly change. Update affected docs and checks in the same change.

## Component delivery

Follow the [component lifecycle](docs/architecture/component-lifecycle.md). Complete
FrogButton as the reference before replicating its architecture. Stable requires
reviewed API, device/test, theme, accessibility, adaptive, motion, Showcase, registry,
and documentation evidence. Record inapplicable items with reasons.

Use realistic examples and meaningful behavior tests. Size constants and enum
counts do not prove touch bounds, TalkBack, focus, or interaction behavior.

## Development and verification

Use the checked-in wrapper and version catalog. The daemon requests JDK 21
(`gradle/gradle-daemon-jvm.properties`); Android modules compile against SDK 36.1
with minimum SDK 24. Follow build files when installing tooling.

```bash
npm ci --ignore-scripts
npm test
npm run docs:build
./gradlew verifyArchitecture
./gradlew verifyProductContract
./gradlew testDebugUnitTest
./gradlew lintDebug
./gradlew assembleDebug
./gradlew check
```

Use `gradlew.bat` on Windows. Generation runs before registry compilation; module
`check` tasks run contract verification. See the [registry contract](docs/architecture/registry-contract.md)
for exact coverage and manual requirements. Node 22+ is a build-time requirement
(CI uses the version in `.node-version`). CI workflows, full schema validation,
docs data builds, and local Maven staging are configured. Binary API baselines,
web UI/Pages deployment, and signed remote publication remain deferred. Report local
and hosted results separately; a workflow file is not proof of a hosted run.

Only foundation, theme, and components are publishable. `frogui-testing` belongs on
test dependencies only. See the [migration notes](docs/architecture/decisions/0005-layered-modules.md)
for the pre-release theme import change and app-owned branding. Keep new package
boundaries aligned with the [module graph](docs/architecture/system-overview.md).

## Pull requests

Use a focused branch and follow `.editorconfig`. Describe the problem and resulting
behavior using the PR template, with command results and relevant device evidence.
Stable promotions require a completed review record. Synchronize docs and metadata
with implementation and known gaps.

Use conventional commit subjects such as `feat(components): ...`,
`fix(foundation): ...`, `docs(architecture): ...`, `test(registry): ...`, and
`chore(build): ...`. Commit-message validation is a convention, not an automated gate.
