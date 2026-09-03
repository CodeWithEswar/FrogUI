import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { execFileSync } from 'node:child_process';
import { docsCatalog, searchIndex } from '../src/registry/catalog.mjs';

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '../..');
if (process.argv.includes('--generate')) {
  execFileSync(process.execPath, ['tools/registry/generate.mjs', '--docs'], { cwd: root, stdio: 'inherit' });
}
const metadata = JSON.parse(fs.readFileSync(path.join(root, 'docs/generated/components.json'), 'utf8'));
const catalog = docsCatalog(metadata);
const pages = catalog.map(component => {
  const original = metadata.components.find(c => c.id === component.id) || {};
  return {
    ...component,
    source: original.source,
    showcase: original.showcase,
    prose: fs.readFileSync(path.join(root, 'docs/content/components', component.id + '.md'), 'utf8')
  };
});

// 1. Ensure docs/dist exists and write JSON targets for catalog tests and API consumers
const distOutput = path.join(root, 'docs/dist');
fs.mkdirSync(distOutput, { recursive: true });
for (const [name, data] of Object.entries({
  'catalog.json': { schemaVersion: metadata.schemaVersion, release: metadata.release, pages },
  'search.json': searchIndex(catalog)
})) {
  fs.writeFileSync(path.join(distOutput, name), JSON.stringify(data, null, 2) + '\n');
}

// 2. Generate typed TypeScript modules for the Vite React frontend
const srcGenerated = path.join(root, 'docs/src/generated');
fs.mkdirSync(srcGenerated, { recursive: true });

const catalogTs = `// AUTO-GENERATED from registry and documentation content. DO NOT EDIT MANUALLY.
export interface ComponentProperty {
  name: string;
  type: string;
  defaultValue: string;
  description: string;
}

export interface ComponentExample {
  id: string;
  title: string;
  description: string;
  codeSnippet: string;
}

export interface ComponentAccessibility {
  role: string;
  minTouchTarget: string;
  talkBackNotes?: string;
}

export interface ComponentShowcase {
  route: string;
  source: string;
  screen: string;
}

export interface ComponentDocPage {
  id: string;
  name: string;
  displayName: string;
  description: string;
  category: string;
  status: 'stable' | 'beta' | 'experimental' | 'deprecated';
  since: string;
  path: string;
  variants: string[];
  sizes: string[];
  properties: ComponentProperty[];
  examples: ComponentExample[];
  tags: string[];
  accessibility: ComponentAccessibility;
  source?: string;
  showcase?: ComponentShowcase;
  prose: string;
}

export interface CategoryInfo {
  id: string;
  displayName: string;
  description: string;
}

export interface ReleaseInfo {
  version: string;
  versionCode: number;
  published: boolean;
}

export const release: ReleaseInfo = ${JSON.stringify(metadata.release, null, 2)};

export const categories: CategoryInfo[] = ${JSON.stringify(metadata.categories, null, 2)};

export const catalog: ComponentDocPage[] = ${JSON.stringify(pages, null, 2)};

export function getComponentById(id: string): ComponentDocPage | undefined {
  return catalog.find(c => c.id === id);
}

export function getComponentsByCategory(categoryId: string): ComponentDocPage[] {
  return catalog.filter(c => c.category === categoryId);
}
`;

const searchIndexTs = `// AUTO-GENERATED from registry and documentation content. DO NOT EDIT MANUALLY.
export interface SearchItem {
  id: string;
  name: string;
  displayName: string;
  description: string;
  category: string;
  status: string;
  path: string;
  tags?: string[];
}

export const searchIndex: SearchItem[] = ${JSON.stringify(searchIndex(catalog), null, 2)};
`;

const routesTs = `// AUTO-GENERATED navigation structure. DO NOT EDIT MANUALLY.
export interface NavItem {
  title: string;
  path: string;
  badge?: string;
}

export interface NavSection {
  title: string;
  items: NavItem[];
}

import { catalog, categories } from './catalog';

export const navigationSections: NavSection[] = [
  {
    title: 'Getting Started',
    items: [
      { title: 'Introduction', path: '/docs/introduction' },
      { title: 'Installation', path: '/docs/installation' },
      { title: 'Quick Start', path: '/docs/quick-start' },
    ]
  },
  {
    title: 'Foundation',
    items: [
      { title: 'Overview', path: '/foundation' },
      { title: 'Colors & Theme', path: '/foundation/colors' },
      { title: 'Typography', path: '/foundation/typography' },
      { title: 'Spacing & Shapes', path: '/foundation/spacing' },
      { title: 'Accessibility', path: '/foundation/accessibility' },
    ]
  },
  {
    title: 'Architecture',
    items: [
      { title: 'Technology Foundation', path: '/docs/technology' },
    ]
  },
  ...categories.map(category => {
    const components = catalog.filter(c => c.category === category.id);
    return {
      title: category.displayName,
      items: components.map(c => ({
        title: c.displayName,
        path: c.path,
        badge: c.status !== 'stable' ? c.status.charAt(0).toUpperCase() + c.status.slice(1) : undefined
      }))
    };
  }).filter(section => section.items.length > 0)
];
`;

fs.writeFileSync(path.join(srcGenerated, 'catalog.ts'), catalogTs);
fs.writeFileSync(path.join(srcGenerated, 'searchIndex.ts'), searchIndexTs);
fs.writeFileSync(path.join(srcGenerated, 'routes.ts'), routesTs);

console.log(`Built documentation catalog (${pages.length} component(s)), search index, and TypeScript routes.`);
