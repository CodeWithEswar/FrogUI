const BASE_PATH = '/FrogUI';

export function normalizeDocsPath(path) {
  const pathname = (path || '/').split('#')[0].split('?')[0];
  let logical = pathname.startsWith(BASE_PATH) ? pathname.slice(BASE_PATH.length) : pathname;
  if (!logical.startsWith('/')) logical = `/${logical}`;
  logical = logical.replace(/\/$/, '') || '/';
  if (logical === '/foundation') return '/foundations';
  if (logical.startsWith('/foundation/')) return logical.replace('/foundation/', '/foundations/');
  if (logical === '/docs/technology' || logical === '/architecture/technology') {
    return '/architecture/technology-foundation';
  }
  return logical;
}

const authoredNode = (pages, path, icon) => {
  const page = pages.find(item => item.path === path);
  if (!page) throw new Error(`Missing authored docs page: ${path}`);
  const slug = path.slice(1).replaceAll('/', '-');
  const id = slug === 'components' ? 'components-overview' : slug === 'architecture' ? 'architecture-overview' : slug;
  return { id, title: page.title, href: path, description: page.description, icon };
};

export function createDocsNavigation(catalog, categories, pages) {
  const foundationPaths = [
    '/foundations', '/foundations/colors', '/foundations/typography', '/foundations/spacing',
    '/foundations/shapes', '/foundations/elevation', '/foundations/motion', '/foundations/sizing',
    '/foundations/adaptive', '/foundations/accessibility'
  ];
  const architecturePaths = [
    '/architecture', '/architecture/technology-foundation', '/architecture/repository',
    '/architecture/api-design', '/architecture/component-standard', '/architecture/registry',
    '/architecture/release'
  ];
  const componentCategories = categories.map(category => ({
    id: `components-${category.id}`,
    title: category.displayName,
    description: category.description,
    icon: category.id === 'overlays' ? 'overlay' : 'component',
    children: catalog.filter(component => component.category === category.id).map(component => ({
      id: `component-${component.id}`,
      title: component.displayName,
      href: normalizeDocsPath(component.path || component.docs),
      description: component.description,
      icon: component.id === 'button' ? 'action' : component.id === 'drawer' ? 'overlay' : 'component'
    }))
  })).filter(category => category.children.length > 0);

  return [
    {
      id: 'getting-started-section',
      label: 'Getting started',
      nodes: [{
        id: 'getting-started', title: 'Getting Started', icon: 'book', children: [
          authoredNode(pages, '/docs/introduction', 'book'),
          authoredNode(pages, '/docs/installation', 'download'),
          authoredNode(pages, '/docs/quick-start', 'rocket')
        ]
      }]
    },
    {
      id: 'foundation-section',
      label: 'Foundation',
      nodes: [{
        id: 'foundation', title: 'Foundation', icon: 'layers',
        children: foundationPaths.map((path, index) => authoredNode(pages, path, index === 0 ? 'layers' : undefined))
      }]
    },
    {
      id: 'components-section',
      label: 'Components',
      nodes: [{
        id: 'components', title: 'Components', icon: 'component',
        children: [authoredNode(pages, '/components', 'component'), ...componentCategories]
      }]
    },
    {
      id: 'architecture-section',
      label: 'Architecture',
      nodes: [{
        id: 'architecture', title: 'Architecture', icon: 'architecture',
        children: architecturePaths.map((path, index) => authoredNode(pages, path, index === 0 ? 'architecture' : undefined))
      }]
    }
  ];
}

export function flattenNavigation(sections) {
  const flat = [];
  const visit = (node, ancestors) => {
    const trail = [...ancestors, node];
    if (node.href) flat.push({ ...node, trail });
    for (const child of node.children || []) visit(child, trail);
  };
  for (const section of sections) for (const node of section.nodes) visit(node, []);
  return flat;
}

export function findNavigationTrail(sections, currentPath) {
  const target = normalizeDocsPath(currentPath);
  return flattenNavigation(sections).find(item => normalizeDocsPath(item.href) === target)?.trail || [];
}

export function getAdjacentNavigation(sections, currentPath) {
  const flat = flattenNavigation(sections);
  const target = normalizeDocsPath(currentPath);
  const index = flat.findIndex(item => normalizeDocsPath(item.href) === target);
  return { previous: index > 0 ? flat[index - 1] : undefined, next: index >= 0 ? flat[index + 1] : undefined };
}

export function docsSearchIndex(sections) {
  return flattenNavigation(sections).map(item => ({
    id: `docs:${item.id}`,
    name: item.title,
    displayName: item.title,
    description: item.description || `Open ${item.title} documentation.`,
    category: item.trail[0]?.title || 'Documentation',
    status: 'stable',
    path: item.href,
    tags: item.trail.map(node => node.title)
  }));
}
