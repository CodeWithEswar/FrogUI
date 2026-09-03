# Contributing to FrogUI Documentation

FrogUI documentation is generated automatically from component registry records, Showcase example source files, and Markdown prose.

## Authoring Component Documentation

To document a new component or update an existing one:

1. **Update Registry Record** (`registry/components/<id>.json`):
   - Define `id`, `name`, `displayName`, `description`, `category`, `status`, `since`, and `showcase`.
   - List public parameters in `properties`.
   - Reference compiled Showcase examples in `examples` using `source` and `region`.

2. **Add Showcase Example Snippets**:
   - In `app/src/main/java/.../<Component>Examples.kt`, tag examples with:
     ```kotlin
     // example:<region>:start
     @Composable
     fun ComponentExample() { ... }
     // example:<region>:end
     ```

3. **Author Markdown Prose** (`docs/content/components/<id>.md`):
   - Explain usage guidance, variant selection rationale, layout tips, and accessibility considerations.
   - Do NOT duplicate property tables or version badges; the docs platform renders those automatically from registry metadata.

4. **Verify Locally**:
   ```bash
   # From repository root:
   npm test
   npm run docs:build
   ```

5. **Local Development Server**:
   ```bash
   npm run docs:dev
   ```
   Open `http://localhost:5173/FrogUI/` to preview changes live.

## What Not to Edit

Do NOT manually edit files under `docs/src/generated/`:
- `docs/src/generated/catalog.ts`
- `docs/src/generated/searchIndex.ts`
- `docs/src/generated/routes.ts`

These files are auto-generated from `registry/components/*.json` and `docs/content/` during `npm run docs:build`.
