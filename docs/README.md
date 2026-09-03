# Documentation data pipeline

From the repository root:

```bash
npm ci --ignore-scripts
npm run docs:build
```

This validates registry schemas, source/example references, and documentation routes,
then builds `generated/components.json`, `dist/catalog.json`, and `dist/search.json`.
The catalog combines generated identity/API metadata with Markdown component prose;
the search data retains status and `/FrogUI/` paths. Outputs are ignored by Git.

This is the integration layer for a future public docs UI. No React/Vite/MDX shell
or GitHub Pages website is deployed in Phase 03. See the
[docs boundary](architecture/docs-flow.md), [system overview](architecture/system-overview.md),
and [release flow](architecture/release-flow.md).
