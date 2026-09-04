import assert from 'node:assert/strict';
import fs from 'node:fs';
import path from 'node:path';
import Ajv from 'ajv';
import { verifyPropertyMetadata } from './kotlin-signature.mjs';

export function repositoryFile(root, relative) {
  assert(!path.isAbsolute(relative), `Absolute registry reference: ${relative}`);
  const base = fs.realpathSync(root);
  const file = fs.realpathSync(path.resolve(root, relative));
  assert(file.startsWith(base + path.sep), `Reference escapes repository: ${relative}`);
  assert(fs.statSync(file).isFile(), `Expected file: ${relative}`);
  return file;
}

const readJson = (root, file) => JSON.parse(fs.readFileSync(repositoryFile(root, file), 'utf8').replace(/^\uFEFF/, ''));
const unique = (values, label) => assert.equal(new Set(values).size, values.length, `Duplicate ${label}`);

function verifyLifecycleArtifacts(root, component, source) {
  const quality = component.quality;
  const preview = fs.readFileSync(repositoryFile(root, quality.composePreviews), 'utf8');
  assert(preview.includes('@Preview'), `Compose preview file has no @Preview: ${component.id}`);
  assert(preview.includes(component.name), `Compose previews must render ${component.name}`);

  for (const file of [...quality.unitTests, ...quality.androidTests]) {
    const test = fs.readFileSync(repositoryFile(root, file), 'utf8');
    assert(test.includes(component.name), `Lifecycle test must exercise ${component.name}: ${file}`);
  }

  const webPreview = fs.readFileSync(repositoryFile(root, quality.webPreview), 'utf8');
  assert(webPreview.includes('ComponentPreviewProps'), `Web preview must use the shared preview contract: ${component.id}`);
  assert.match(webPreview, /export\s+(?:const|function)\s+\w+Preview\b/, `Web preview must export a component preview: ${component.id}`);

  const escapedName = component.name.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
  assert(new RegExp(`/\\*\\*[\\s\\S]*?\\*/\\s*@Composable\\s+(?:public\\s+)?fun\\s+${escapedName}\\s*\\(`).test(source),
    `Canonical component requires useful KDoc immediately before ${component.name}`);
  const sourceDirectory = path.dirname(component.source);
  const defaultsName = `${component.name}Defaults`;
  const defaults = fs.readFileSync(repositoryFile(root, `${sourceDirectory}/${defaultsName}.kt`), 'utf8');
  assert(new RegExp(`/\\*\\*[\\s\\S]*?\\*/\\s*(?:public\\s+)?object\\s+${defaultsName}\\b`).test(defaults),
    `Defaults require KDoc: ${defaultsName}`);
}

export function readRelease(root) {
  const text = fs.readFileSync(repositoryFile(root, 'gradle/release.properties'), 'utf8');
  const values = Object.fromEntries(text.split(/\r?\n/).filter(line => line && !line.startsWith('#')).map(line => {
    const offset = line.indexOf('=');
    assert(offset > 0, 'Invalid release property');
    return [line.slice(0, offset), line.slice(offset + 1)];
  }));
  assert.match(values.version, /^\d+\.\d+\.\d+(?:-[0-9A-Za-z.-]+)?$/);
  assert.match(values.versionCode, /^[1-9]\d*$/);
  assert(['true', 'false'].includes(values.published));
  return { version: values.version, versionCode: Number(values.versionCode), published: values.published === 'true' };
}

export function extractExample(root, example) {
  assert(example.source.startsWith('app/src/main/') && example.source.endsWith('.kt'), 'Examples must use compiled Showcase Kotlin sources');
  const text = fs.readFileSync(repositoryFile(root, example.source), 'utf8');
  const start = `// example:${example.region}:start`;
  const end = `// example:${example.region}:end`;
  assert.equal(text.split(start).length, 2, `Missing/duplicate example start: ${example.id}`);
  assert.equal(text.split(end).length, 2, `Missing/duplicate example end: ${example.id}`);
  assert(text.indexOf(end) > text.indexOf(start), `Reversed example region: ${example.id}`);
  const lines = text.slice(text.indexOf(start) + start.length, text.indexOf(end)).trimEnd().split(/\r?\n/).slice(1);
  const indentation = Math.min(...lines.filter(line => line.trim()).map(line => line.match(/^ */)[0].length));
  const codeSnippet = lines.map(line => line.slice(indentation)).join('\n');
  assert(codeSnippet.trim(), `Empty example: ${example.id}`);
  return { id: example.id, title: example.title, description: example.description, codeSnippet };
}

export function loadRegistry(root) {
  const ajv = new Ajv({ allErrors: true, strict: true });
  for (const name of ['example', 'component', 'registry']) {
    ajv.addSchema(readJson(root, `registry/schema/${name}.schema.json`), name);
  }
  const validate = (name, value) => {
    assert(ajv.validate(name, value), `${name} schema: ${ajv.errorsText(ajv.errors)}`);
  };
  const index = readJson(root, 'registry/index.json');
  validate('registry', index);
  unique(index.components.map(item => item.id), 'registry ID');
  unique(index.categories.map(item => item.id), 'category ID');
  const listed = index.components.map(item => item.file).sort();
  const actual = fs.readdirSync(path.join(root, 'registry/components')).filter(file => file.endsWith('.json')).map(file => `components/${file}`).sort();
  assert.deepEqual(listed, actual, 'Index must cover every component record exactly once');
  const release = readRelease(root);
  const components = index.components.map(entry => {
    assert.equal(entry.file, `components/${entry.id}.json`, 'Non-canonical registry file');
    const component = readJson(root, `registry/${entry.file}`);
    validate('component', component);
    assert.equal(component.schemaVersion, index.schemaVersion, 'Index/component schema version mismatch');
    assert.equal(component.id, entry.id, 'Index/component ID mismatch');
    assert(index.categories.some(category => category.id === component.category), 'Unknown category');
    assert.equal(component.docs, `/components/${component.id}`, 'Non-canonical docs route');
    assert.equal(component.showcase.route, `components/${component.id}`, 'Non-canonical Showcase route');
    assert(component.source.startsWith('frogui-components/src/main/') && component.source.endsWith('.kt'), 'Component must reference library Kotlin source');
    const source = fs.readFileSync(repositoryFile(root, component.source), 'utf8');
    assert(new RegExp(`\\bfun\\s+${component.name}\\s*\\(`).test(source), `Missing Kotlin function: ${component.name}`);
    assert(!new RegExp(`\\binternal\\s+fun\\s+${component.name}\\s*\\(`).test(source), 'Catalog requires public components');
    verifyPropertyMetadata(source, component);
    verifyLifecycleArtifacts(root, component, source);
    assert(component.showcase.source.startsWith('app/src/main/') && component.showcase.source.endsWith('.kt'), 'Showcase route must reference app Kotlin');
    const demo = fs.readFileSync(repositoryFile(root, component.showcase.source), 'utf8');
    assert(new RegExp(`\\bfun\\s+${component.showcase.screen}\\s*\\(`).test(demo), `Missing Showcase screen: ${component.id}`);
    const prosePath = `docs/content${component.docs}.md`;
    assert(fs.readFileSync(repositoryFile(root, prosePath), 'utf8').trim(), `Missing docs content: ${component.id}`);
    unique(component.properties.map(item => item.name), 'property');
    unique(component.examples.map(item => item.id), 'example');
    if (component.status === 'stable') {
      assert.equal(component.stabilityReview, `docs/components/${component.id}-review.md`);
      assert(fs.readFileSync(repositoryFile(root, component.stabilityReview), 'utf8').trim(), 'Empty stability review');
    }
    // On the initial unpublished line, introduction metadata must name the actual build version.
    if (!release.published) assert.equal(component.since, release.version, 'Unpublished introduction version must match release.properties');
    return { ...component, examples: component.examples.map(example => extractExample(root, example)) };
  });
  unique(components.map(component => component.docs), 'docs route');
  unique(components.map(component => component.showcase.route), 'Showcase route');
  const docsIds = fs.readdirSync(path.join(root, 'docs/content/components')).filter(file => file.endsWith('.md')).map(file => file.slice(0, -3)).sort();
  assert.deepEqual(docsIds, components.map(component => component.id).sort(), 'Component docs and registry must agree');
  return { schemaVersion: index.schemaVersion, release, categories: index.categories, components };
}

// Kotlin string literals, never executable metadata; escape string interpolation too.
export const kotlinString = value => JSON.stringify(value).replaceAll('$', '\\$');

export function kotlinCatalog(catalog) {
  const categories = { actions: 'Actions', inputs: 'Inputs', 'data-display': 'DataDisplay', feedback: 'Feedback', navigation: 'Navigation', overlays: 'Overlays', layout: 'Layout' };
  const q = kotlinString;
  const list = values => `listOf(${values.map(q).join(', ')})`;
  const records = catalog.components.map(component => `    FrogComponentMetadata(
        id = ${q(component.id)},
        name = ${q(component.name)},
        displayName = ${q(component.displayName)},
        description = ${q(component.description)},
        category = FrogComponentCategory.${categories[component.category]},
        status = FrogComponentStatus.${component.status[0].toUpperCase() + component.status.slice(1)},
        since = ${q(component.since)},
        docsPath = ${q(component.docs.slice(1))},
        showcaseRoute = ${q(component.showcase.route)},
        variants = ${list(component.variants)},
        sizes = ${list(component.sizes)},
        properties = listOf(
${component.properties.map(item => `            ComponentPropertyMetadata(${['name', 'type', 'defaultValue', 'description'].map(key => q(item[key])).join(', ')})`).join(',\n')}
        ),
        examples = listOf(
${component.examples.map(item => `            ComponentExampleMetadata(${['id', 'title', 'description', 'codeSnippet'].map(key => q(item[key])).join(', ')})`).join(',\n')}
        )
    )`).join(',\n');
  return `// Generated from canonical registry metadata. Do not edit.\npackage io.github.codewitheswar.frogui.registry\n\ninternal val generatedComponents: List<FrogComponentMetadata> = listOf(\n${records}\n)\n`;
}
