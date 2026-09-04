// AUTO-GENERATED navigation structure. DO NOT EDIT MANUALLY.
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
      { title: 'Theme Runtime', path: '/foundation/theme' },
      { title: 'Colors', path: '/foundation/colors' },
      { title: 'Typography', path: '/foundation/typography' },
      { title: 'Spacing', path: '/foundation/spacing' },
      { title: 'Shapes', path: '/foundation/shapes' },
      { title: 'Elevation', path: '/foundation/elevation' },
      { title: 'Motion', path: '/foundation/motion' },
      { title: 'Sizing', path: '/foundation/sizing' },
      { title: 'Adaptive', path: '/foundation/adaptive' },
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
