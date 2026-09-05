export type DocsIcon = 'book' | 'download' | 'rocket' | 'layers' | 'component' | 'action' | 'overlay' | 'architecture';

export interface DocsNavNode {
  id: string;
  title: string;
  href?: string;
  description?: string;
  icon?: DocsIcon;
  children?: DocsNavNode[];
}

export interface DocsNavSection {
  id: string;
  label: string;
  nodes: DocsNavNode[];
}

export interface FlatDocsNavItem extends DocsNavNode {
  href: string;
  trail: DocsNavNode[];
}

export interface DocsSearchItem {
  id: string;
  name: string;
  displayName: string;
  description: string;
  category: string;
  status: string;
  path: string;
  tags: string[];
}

interface ComponentLike { id: string; displayName: string; description: string; category: string; path: string; }
interface CategoryLike { id: string; displayName: string; description: string; }
interface PageLike { path: string; title: string; description: string; }

export function normalizeDocsPath(path: string): string;
export function createDocsNavigation(catalog: ComponentLike[], categories: CategoryLike[], pages: PageLike[]): DocsNavSection[];
export function flattenNavigation(sections: DocsNavSection[]): FlatDocsNavItem[];
export function findNavigationTrail(sections: DocsNavSection[], currentPath: string): DocsNavNode[];
export function getAdjacentNavigation(sections: DocsNavSection[], currentPath: string): { previous?: FlatDocsNavItem; next?: FlatDocsNavItem };
export function docsSearchIndex(sections: DocsNavSection[]): DocsSearchItem[];
