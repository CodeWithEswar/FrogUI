import test from 'node:test';
import assert from 'node:assert/strict';
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { loadRegistry, kotlinCatalog, kotlinString } from './registry.mjs';

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '../..');
const recordPath = 'registry/components/button.json';
function fixture() {
  fs.mkdirSync(path.join(root, 'build'), { recursive: true });
  const target = fs.mkdtempSync(path.join(root, 'build/registry-test-'));
  // Fixtures stay in ignored build output. No repository files are mutated.
  for (const source of ['registry', 'gradle/release.properties', 'docs/content',
    'frogui-components/src/main', 'frogui-components/src/test', 'app/src/main', 'app/src/androidTest',
    'docs/src/components/preview']) {
    fs.mkdirSync(path.dirname(path.join(target, source)), { recursive: true });
    fs.cpSync(path.join(root, source), path.join(target, source), { recursive: true });
  }
  return target;
}
function changeRecord(target, mutation) {
  const file = path.join(target, recordPath);
  const record = JSON.parse(fs.readFileSync(file, 'utf8'));
  mutation(record);
  fs.writeFileSync(file, JSON.stringify(record));
}

test('generated native and docs projections use real compiled examples and the canonical version', () => {
  const catalog = loadRegistry(root);
  assert.equal(catalog.release.version, catalog.components[0].since);
  assert.match(catalog.components[0].examples[0].codeSnippet, /fun ButtonPrimaryExample/);
  assert.match(kotlinCatalog(catalog), /showcaseRoute = "components\/button"/);
  assert.equal(kotlinString('$state\n"save"'), '"\\$state\\n\\"save\\""');
});

for (const [name, mutate, expected] of [
  ['unknown metadata keys', c => { c.renderEngine = 'web'; }, /additional properties/],
  ['invalid native route', c => { c.showcase.route = 'components/missing'; }, /Showcase route/],
  ['missing native screen', c => { c.showcase.screen = 'MissingScreen'; }, /Missing Showcase screen/],
  ['missing component implementation', c => { c.name = 'FrogMissing'; }, /Missing Kotlin function/],
  ['invented release version', c => { c.since = '9.9.9'; }, /version must match/],
  ['stable without evidence', c => { c.status = 'stable'; }, /stabilityReview/],
  ['duplicate capabilities', c => { c.variants.push(c.variants[0]); }, /duplicate items/],
  ['missing lifecycle evidence', c => { delete c.quality; }, /required property.*quality/],
  ['invalid lifecycle theme matrix', c => { c.quality.themes = ['Light', 'Dark']; }, /must NOT have fewer than 3 items/],
  ['unsupported schema version', c => { c.schemaVersion = 99; }, /constant/]
]) {
  test(`rejects ${name}`, () => {
    const target = fixture();
    changeRecord(target, mutate);
    assert.throws(() => loadRegistry(target), expected);
  });
}

test('rejects duplicate registry IDs', () => {
  const target = fixture();
  const file = path.join(target, 'registry/index.json');
  const index = JSON.parse(fs.readFileSync(file, 'utf8'));
  index.components.push(index.components[0]);
  fs.writeFileSync(file, JSON.stringify(index));
  assert.throws(() => loadRegistry(target), /Duplicate registry ID/);
});

test('rejects a lifecycle artifact that does not exercise the component', () => {
  const target = fixture();
  const file = path.join(target, 'docs/src/components/preview/previews/button/ButtonPreview.tsx');
  fs.writeFileSync(file, 'export const EmptyPreview = () => null;');
  assert.throws(() => loadRegistry(target), /shared preview contract/);
});

test('rejects a missing docs destination', () => {
  const target = fixture();
  const prose = path.join(target, 'docs/content/components/button.md');
  fs.renameSync(prose, prose + '.unavailable');
  assert.throws(() => loadRegistry(target), /ENOENT/);
});

test('rejects source traversal outside the repository', () => {
  const target = fixture();
  changeRecord(target, c => { c.source = 'frogui-components/src/main/../../../../../../outside.kt'; });
  assert.throws(() => loadRegistry(target), /ENOENT|escapes repository/);
});
