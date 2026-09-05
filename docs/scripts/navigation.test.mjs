import test from 'node:test';
import assert from 'node:assert/strict';
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { docsCatalog } from '../src/registry/catalog.mjs';
import {
  createDocsNavigation,
  flattenNavigation,
  findNavigationTrail,
  getAdjacentNavigation,
  docsSearchIndex,
  normalizeDocsPath
} from '../src/navigation/docsNavigation.mjs';

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '../..');
const pages = JSON.parse(fs.readFileSync(path.join(root, 'docs/content/pages.json'), 'utf8'));
const metadata = JSON.parse(fs.readFileSync(path.join(root, 'docs/generated/components.json'), 'utf8'));
const catalog = docsCatalog(metadata);
const categories = metadata.categories;

test('documentation navigation creates structured accordion hierarchy without duplicate IDs or routes', () => {
  const sections = createDocsNavigation(catalog, categories, pages);
  assert.equal(sections.length, 4, 'Four primary documentation sections');

  const sectionIds = new Set();
  const allIds = new Set();
  const allRoutes = new Set();

  for (const section of sections) {
    assert.ok(!sectionIds.has(section.id), `Duplicate section ID: ${section.id}`);
    sectionIds.add(section.id);
  }

  const flat = flattenNavigation(sections);
  for (const item of flat) {
    assert.ok(!allIds.has(item.id), `Duplicate navigation ID: ${item.id}`);
    allIds.add(item.id);

    assert.ok(!allRoutes.has(item.href), `Duplicate navigation route: ${item.href}`);
    allRoutes.add(item.href);

    // Verify href resolves
    const normalized = normalizeDocsPath(item.href);
    const isComponent = normalized.startsWith('/components/');
    const isGettingStarted = normalized.startsWith('/docs/');
    const isAuthored = pages.some(p => p.path === normalized);
    assert.ok(
      isComponent || isGettingStarted || isAuthored,
      `Unresolved navigation destination: ${item.href}`
    );
  }
});

test('foundation section defines 10 dedicated child pages including reference Elevation page', () => {
  const sections = createDocsNavigation(catalog, categories, pages);
  const foundationSection = sections.find(s => s.id === 'foundation-section');
  assert.ok(foundationSection, 'Foundation section exists');

  const foundationGroup = foundationSection.nodes.find(n => n.id === 'foundation');
  assert.ok(foundationGroup, 'Foundation accordion group exists');
  assert.equal(foundationGroup.children.length, 10, 'Must have 10 dedicated foundation child pages');

  const expectedFoundationPages = [
    '/foundations',
    '/foundations/colors',
    '/foundations/typography',
    '/foundations/spacing',
    '/foundations/shapes',
    '/foundations/elevation',
    '/foundations/motion',
    '/foundations/sizing',
    '/foundations/adaptive',
    '/foundations/accessibility'
  ];

  for (const expectedPath of expectedFoundationPages) {
    const found = foundationGroup.children.find(child => child.href === expectedPath);
    assert.ok(found, `Expected foundation child page ${expectedPath} in navigation`);
  }

  // Verify spacing and shapes are separated
  const spacingPage = foundationGroup.children.find(c => c.href === '/foundations/spacing');
  const shapesPage = foundationGroup.children.find(c => c.href === '/foundations/shapes');
  assert.ok(spacingPage && shapesPage, 'Spacing and Shapes must be independent routes');
  assert.notEqual(spacingPage.href, shapesPage.href);

  // Verify Elevation reference page
  const elevationPage = foundationGroup.children.find(c => c.href === '/foundations/elevation');
  assert.ok(elevationPage, 'Elevation dedicated page exists');
  assert.equal(elevationPage.title, 'Elevation');
});

test('trail and ancestry auto-expansion correctly resolves parent nodes', () => {
  const sections = createDocsNavigation(catalog, categories, pages);

  // Elevation trail should contain ['foundation', 'foundations-elevation']
  const elevationTrail = findNavigationTrail(sections, '/foundations/elevation');
  assert.equal(elevationTrail.length, 2, 'Elevation trail has parent group and child page');
  assert.equal(elevationTrail[0].id, 'foundation');
  assert.equal(elevationTrail[1].href, '/foundations/elevation');

  // Architecture API design trail
  const apiTrail = findNavigationTrail(sections, '/architecture/api-design');
  assert.equal(apiTrail.length, 2);
  assert.equal(apiTrail[0].id, 'architecture');
  assert.equal(apiTrail[1].href, '/architecture/api-design');

  // Root component catalog trail
  const componentsTrail = findNavigationTrail(sections, '/components');
  assert.equal(componentsTrail.length, 2);
  assert.equal(componentsTrail[0].id, 'components');
  assert.equal(componentsTrail[1].id, 'components-overview');
  assert.notEqual(componentsTrail[0].id, componentsTrail[1].id, 'Trail IDs must never collide');

  // Root architecture trail
  const archTrail = findNavigationTrail(sections, '/architecture');
  assert.equal(archTrail.length, 2);
  assert.equal(archTrail[0].id, 'architecture');
  assert.equal(archTrail[1].id, 'architecture-overview');
  assert.notEqual(archTrail[0].id, archTrail[1].id, 'Trail IDs must never collide');

  // Nested component trail: components -> components-actions -> component-button
  const buttonTrail = findNavigationTrail(sections, '/components/button');
  assert.equal(buttonTrail.length, 3);
  assert.equal(buttonTrail[0].id, 'components');
  assert.equal(buttonTrail[1].id, 'components-actions');
  assert.equal(buttonTrail[2].title, 'Button');

  // Global check: no trail across all flattened routes contains duplicate IDs
  const flatNodes = flattenNavigation(sections);
  for (const item of flatNodes) {
    const idSet = new Set(item.trail.map(n => n.id));
    assert.equal(idSet.size, item.trail.length, `Trail for ${item.href} contains duplicate IDs`);
  }
});

test('adjacent navigation orders previous and next routes correctly for Elevation reference page', () => {
  const sections = createDocsNavigation(catalog, categories, pages);
  const adjacent = getAdjacentNavigation(sections, '/foundations/elevation');

  assert.ok(adjacent.previous, 'Elevation has previous item');
  assert.equal(adjacent.previous.href, '/foundations/shapes', 'Previous item must be Shapes');

  assert.ok(adjacent.next, 'Elevation has next item');
  assert.equal(adjacent.next.href, '/foundations/motion', 'Next item must be Motion');
});

test('path normalization correctly maps legacy routes and project base paths', () => {
  assert.equal(normalizeDocsPath('/FrogUI/foundations/elevation'), '/foundations/elevation');
  assert.equal(normalizeDocsPath('/foundation'), '/foundations');
  assert.equal(normalizeDocsPath('/foundation/'), '/foundations');
  assert.equal(normalizeDocsPath('/foundation/colors'), '/foundations/colors');
  assert.equal(normalizeDocsPath('/FrogUI/foundation/elevation'), '/foundations/elevation');
  assert.equal(normalizeDocsPath('/docs/technology'), '/architecture/technology-foundation');
  assert.equal(normalizeDocsPath('/architecture/technology'), '/architecture/technology-foundation');
});

test('search index incorporates all dedicated doc pages with tags and categories', () => {
  const sections = createDocsNavigation(catalog, categories, pages);
  const searchItems = docsSearchIndex(sections);

  const elevationSearch = searchItems.find(item => item.path === '/foundations/elevation');
  assert.ok(elevationSearch, 'Elevation indexed in docs search');
  assert.equal(elevationSearch.displayName, 'Elevation');
  assert.equal(elevationSearch.category, 'Foundation');
  assert.ok(elevationSearch.tags.includes('Foundation'));

  const motionSearch = searchItems.find(item => item.path === '/foundations/motion');
  assert.ok(motionSearch, 'Motion indexed in docs search');
});
