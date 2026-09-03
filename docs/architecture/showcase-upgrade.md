# Native showcase upgrade

## Scope and ownership

The upgrade stays in `app`. Foundation/theme/component/registry APIs keep their
Phase 03 ownership. `FrogIcons`, code rendering, Markdown models, navigation,
preferences, and showcase motion are internal app implementations. No library
requires an icon vendor type, browser, Markdown engine, or syntax dependency.

The initial audit found a default Material bottom bar/rail, one 840 dp breakpoint,
local navigation without restoration, a plain text code viewer, no native Markdown
parser, and Material icons spread across the app. These surfaces now share a
custom top bar, phone dock, rail/sidebar, focus treatment, and semantic theme tokens.

## Dependencies evaluated 2026-09-03

| Dependency | Decision and evidence | Native input size |
| --- | --- | --- |
| Official `@hugeicons/core-free-icons` 4.3.0 | MIT Stroke Rounded geometry, pinned in npm tooling only. Official packages do not list an Android Compose renderer. Generate the 23 semantic aliases used by the app into lazy native ImageVectors. The community Compose adapter would add a JitPack repository and the full pack, so it was not selected. | Generated Kotlin source: 25,830 bytes; no npm runtime or full pack in APK. |
| `dev.snipme:highlights` 1.1.0 | Apache 2.0 native Kotlin/JVM engine; Kotlin 2.2-compatible release; source inspected for source-offset output and dependencies. Supports Kotlin, Kotlin DSL through the Kotlin grammar, and Shell. Pure JVM artifact adds no Android manifest/minSdk or browser. Isolated behind `CodeHighlighter`; tested on the app's Kotlin/Compose toolchain. | JVM JAR: 120,318 bytes. |
| `org.commonmark:commonmark` 0.29.0 and GFM tables | BSD 2-Clause AST parser. Java 11 and Android API 19 supported by upstream; app baseline is API 24. Core has no runtime dependencies. Compose consumes a mapped document model, not HTML. 0.x APIs can change between minor versions, so both artifacts are pinned and wrapped. | Core JAR: 216,091 bytes; tables JAR: 23,057 bytes. |

Highlights brings Kotlin serialization and coroutines; the resolved graph reuses
serialization 1.7.3 and coroutines 1.9.0 already present in the app graph. JAR/source
sizes describe inputs, not an APK delta. The debug APK also includes Compose tooling.
Material Icons Extended was removed from the app and version catalog.

Maintenance checked against upstream commits: Highlights `4c0caa8` (2026-08-09),
CommonMark `b89e72f` (2026-08-07), Hugeicons `b2462ec` (2026-08-27). Licenses are
packaged in `app/src/main/assets/licenses` and readable in Settings → About.

Sources: [Hugeicons packages/license](https://github.com/hugeicons/hugeicons),
[Highlights source/API](https://github.com/SnipMeDev/Highlights),
[CommonMark Android support/API](https://github.com/commonmark/commonmark-java).

## Rendering and state

- Width is rounded to logical dp before the 600/840 dp shell breakpoints to avoid
  floating-point boundary flicker. Layout uses available window width, not a model
  or device name. Detail workspaces split at 760 dp of usable content width and
  stack again for large text. API mode gives the table the whole content pane.
- A saved back stack preserves nested Settings/About history. A saved-state holder
  retains each destination's scroll/query/workbench state. Appearance and the
  explicit reduce-motion preference persist in app SharedPreferences.
- The motion provider observes Android animator duration scale. A disabled platform
  scale or the app preference makes all showcase transitions immediate. Icon scale
  and one-dp offset are disabled too. Other durations derive from FrogTheme motion.
- Insets come from safe drawing, status/navigation bars, and IME. Compact detail
  pages hide the dock; the dock also yields to the IME. Controls use 48 dp minimum
  targets, native selected/role semantics, visible focus outlines, and pressed tone.
  At 150% font scale and above, the phone dock uses two rows to keep full labels
  readable. Property variants and sizes each use one horizontally scrollable row,
  with compact 36dp surfaces, content-aware widths, and 48dp touch targets.
  A reserved check slot marks the selected variant; size choices use a solid selected
  surface. Labels stay on one line at larger font scales. Selection does not resize
  the options.
- Code tokenization is independent of colors and layout, cached per source/language,
  and dispatched away from the UI thread. Blocks support selection, line numbers,
  horizontal scroll, full-source copy, and expansion above 14 lines. Text remains
  available immediately. A 100,000-character analysis limit falls back to plain
  text without losing the source. Copy success is announced inline for 1.8 seconds.
- Highlights does not support JSON/XML/Markdown, so those three use a bounded
  character scanner, with escaped strings/comment delimiters handled explicitly.
  It provides lexical categories, not validation or compiler-level semantic analysis.
  No WebView, JavaScript runtime, Tree-sitter native ABI, or compiler is bundled.
- Markdown is parsed once per source, with safe original-text fallback. Headings,
  paragraphs, emphasis, inline code, fences, nested lists, quotes, dividers, web
  links, and GFM tables have native renderers. Arbitrary HTML remains inert text;
  only ordinary HTTP(S) URLs without credentials are actionable. Images currently
  render alt text; no existing component docs need image loading.

## Development and verification

Run `npm ci --ignore-scripts`, `npm run icons:generate` when changing the semantic
icon map, and `npm test` to validate both generated icons and the registry/docs.
The generated file is checked in, so an Android build does not require npm access.

`ShowcasePreviews.kt` includes light/dark chrome and code, rail/sidebar, Markdown,
and phone/tablet API previews. Unit tests cover navigation history, breakpoints,
tokenization, document parsing, safe links, and 4.5:1 code text contrast.
Android interaction tests exercise role/selected semantics, keyboard activation,
toolbar back, clipboard, theme switching, and Markdown fence actions. Window tests
use Compose DeviceConfigurationOverride; they do not alter device-wide settings.
Native PNGs are review captures, not golden-image comparisons.

The completion record and remaining manual checks are in
[showcase verification](showcase-verification.md).
