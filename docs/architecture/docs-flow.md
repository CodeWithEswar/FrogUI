# Documentation integration boundary

Docs consumes generated registry JSON, example text extracted from compiled native
sources, and Markdown prose. It never loads AARs, APKs, DEX, Compose binaries, or
Android rendering engines. The docs consumer in `docs/src/registry/catalog.mjs`
projects names, status, descriptions, capabilities, properties, examples, and search
fields without depending on Android packages.

`npm run docs:build` validates source contracts, generates `docs/generated/components.json`,
and combines it with `docs/content/components/<id>.md` into `docs/dist/catalog.json`
and `search.json`. Missing or orphan component prose and invalid routes fail validation.
Status and version metadata travel with the data; deprecated entries retain their status.

The checked adapter emits `/FrogUI/components/<id>` URLs, ready for the GitHub Pages
project base path. **This phase builds docs data, not a public documentation website.**
The React/Vite/MDX shell, representative preview assets, and actual Pages deployment
are deferred to the docs UI phase. Long prose can migrate to MDX when embedded UI is useful.

When that shell exists, the independent Pages pipeline is:
main change → registry validation/generation → docs build → route/base-path checks →
Pages artifact upload → deployment in the GitHub Pages environment. Use separate
read-only build and scoped deployment permissions. Never deploy PR artifacts as trusted
main output. Docs deployment must not require Maven publication.

The current `registry-docs.yml` workflow validates and uploads metadata artifacts only.
No website has been deployed by this phase. Native interaction remains canonical in
Showcase; future browser representations must be labeled accurately.

References: [GitHub custom Pages workflows](https://docs.github.com/en/pages/getting-started-with-github-pages/using-custom-workflows-with-github-pages),
[Ajv schema support](https://ajv.js.org/json-schema.html).
