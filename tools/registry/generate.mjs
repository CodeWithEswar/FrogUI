import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { loadRegistry, kotlinCatalog } from './registry.mjs';

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '../..');
const catalog = loadRegistry(root);
const write = (file, content) => {
  fs.mkdirSync(path.dirname(file), { recursive: true });
  fs.writeFileSync(file, content);
};
const args = process.argv.slice(2);
if (args[0] === '--android') {
  if (!args[1]) throw new Error('Missing Android output directory');
  const target = path.resolve(args[1]);
  if (!target.startsWith(path.join(root, 'frogui-registry/build') + path.sep)) throw new Error('Android output must stay in registry build directory');
  write(path.join(target, 'io/github/codewitheswar/frogui/registry/GeneratedComponentRegistry.kt'), kotlinCatalog(catalog));
} else if (args[0] === '--docs') {
  write(path.join(root, 'docs/generated/components.json'), JSON.stringify(catalog, null, 2) + '\n');
} else if (args[0] !== '--validate') {
  throw new Error('Use --validate, --docs, or --android <build-directory>');
}
console.log(`Validated ${catalog.components.length} component(s), schema v${catalog.schemaVersion}, ${catalog.release.version}.`);
