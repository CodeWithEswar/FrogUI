# FrogUI Documentation Platform Pipeline

## System Overview

FrogUI's public documentation at `https://codewitheswar.github.io/FrogUI/` is an **auto-generated, registry-aware developer platform**, not an independently maintained marketing site.

```text
┌──────────────────────────────────────────────────────────────┐
│                       FrogUI Repository                      │
└──────────────────────────────┬───────────────────────────────┘
                               │
                               ▼
                    Android Component Source
                               │
                               ▼
                   Registry Metadata + Examples
                               │
                               ▼
                     Registry Validation
                               │
                    ┌──────────┴───────────┐
                    │                      │
                    ▼                      ▼
            Showcase Metadata       Docs Generator
                    │                      │
                    ▼                      ▼
           Native Showcase App       Generated Catalog
                                           │
                                           ▼
                                   React + Vite + MDX
                                           │
                                           ▼
                                     Static Build
                                           │
                                           ▼
                                  GitHub Pages Artifact
                                           │
                                           ▼
                                  Public FrogUI Docs
```

## Architectural Tenets

1. **Single Source of Truth**:
   - `registry/components/*.json` owns component identity, categories, status, variants, sizes, properties, and example regions.
   - Example Kotlin snippets are directly extracted from compiled Showcase source (`app/src/main/.../ButtonExamples.kt`) via region markers (`// example:<region>:start` / `// example:<region>:end`).
   - Markdown prose lives in `docs/content/components/<id>.md`.
   - Build scripts assemble these sources into typed TypeScript modules (`docs/src/generated/catalog.ts`, `searchIndex.ts`, `routes.ts`) and JSON distributions.

2. **Native Interaction Bridge**:
   - The documentation website displays representative visual previews. It does NOT pretend that browser React rendering is live Jetpack Compose execution.
   - Every component page provides deep links to the native Showcase (`frogui://components/<id>`) and direct links to canonical GitHub sources.

3. **Static GitHub Pages Deployment**:
   - Built with Vite using `base: "/FrogUI/"`.
   - Includes `404.html` SPA fallback routing script to guarantee that direct navigation, bookmarking, and page reloads on deep links (e.g. `/FrogUI/components/button`) function cleanly without 404 errors.

4. **Syntax Highlighting & Styling**:
   - **Shiki** provides syntax highlighting for Kotlin, JSON, Bash, and Gradle snippets.
   - Dual-theme support (`github-light` / `github-dark`) synchronized with the docs theme.
   - Styled with Tailwind CSS v4 using FrogUI's monochromatic Zinc design language.

## Build and Developer Commands

From the repository root:

| Command | Action |
| :--- | :--- |
| `npm run docs:dev` | Starts local Vite documentation dev server with hot module reloading. |
| `npm run docs:typecheck` | Validates TypeScript types across the documentation platform. |
| `npm run docs:build` | Validates registry, generates TypeScript catalog, and builds the production bundle into `docs/dist/`. |
| `npm test` | Runs registry validation, icon generation checks, and documentation route tests. |
| `./gradlew verifyProductContract` | Validates Android module boundaries and documentation route contracts via Gradle. |
