import test from 'node:test';
import assert from 'node:assert/strict';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { loadRegistry } from '../../tools/registry/registry.mjs';
import { docsCatalog, searchIndex } from '../src/registry/catalog.mjs';

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '../..');
test('documentation routes support the GitHub Pages project base and keep lifecycle status', () => {
  const source = loadRegistry(root);
  const catalog = docsCatalog(source);
  assert.equal(catalog[0].path, '/FrogUI/components/button');
  assert.equal(catalog[0].status, source.components[0].status);
  assert.equal(searchIndex(catalog)[0].path, catalog[0].path);
  assert.equal(searchIndex(catalog)[0].status, catalog[0].status);
  assert.equal(Object.hasOwn(catalog[0], 'source'), false);
  assert.throws(() => docsCatalog(source, 'FrogUI'), /base path/);
});
