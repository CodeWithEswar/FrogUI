# Testing strategy

Use the cheapest test that verifies the actual contract. Required stable evidence
is in the [component lifecycle](component-lifecycle.md).

| Layer | Existing checks | Purpose |
| --- | --- | --- |
| Registry/build | Node tests and full draft-07 validation | Schema, source/example/version/route drift; invalid metadata fails. |
| Library/registry | JVM unit tests | Metadata/search, public Button enum parity, existing defaults checks. |
| Showcase | JVM tests | Typed routes, navigation history/breakpoints, literal escaping, highlighting, Markdown parsing/safe links, and code contrast. |
| Android integration | Theme isolation, shell and documentation interaction tests | Theme nesting, selected roles, keyboard activation, copying, back navigation, restoration, IME, and native layout captures. |
| Docs adapter | Node test and data build | Shared status, search, /FrogUI/ routes, no Android binary input. |
| Dependency graph | Gradle declaration guard | Allowed production/test edges, no Material in foundation or shipped test harness. |

`frogui-testing` contains one reusable `setFrogContent` fixture used by theme and app
Android tests. It is test-only at consumer dependency sites, carries no production
state, and is not published. Add matchers or golden helpers only once a real shared
case needs them; do not add public component test hooks for implementation convenience.

Run `npm test`, `npm run docs:build`, `./gradlew verifyArchitecture testDebugUnitTest`,
and Android Lint/assembly as relevant. Compile Android tests with
`:app:assembleDebugAndroidTest :frogui-theme:assembleDebugAndroidTest`.
With an Android device/emulator, run
`:app:connectedDebugAndroidTest :frogui-theme:connectedDebugAndroidTest`.
Record whether device tests were actually run, not merely compiled.

Showcase window tests capture native PNGs for visual review at 360, 390, 412, 600,
840, and 1000 dp, landscape, large text, and RTL. Pixel-golden baselines remain
deferred until the reference component's visual contract is ready.
Cover light/dark, major variants, disabled/loading/error
states, and scaling risks without multiplying redundant goldens. Benchmarks are
deferred until startup, scrolling, or recomposition measurements answer a real question.
API/binary validation must precede stable releases; a source-name regex does not
replace it. TalkBack, contrast, touch bounds, motion, and complete accessibility
evidence remain reference-component work.
