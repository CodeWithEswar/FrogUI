export type DocsIcon =
  | 'book'
  | 'download'
  | 'rocket'
  | 'layers'
  | 'component'
  | 'action'
  | 'overlay'
  | 'architecture';

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

export interface FlattenedNavNode extends DocsNavNode {
  href: string;
  trail: DocsNavNode[];
}

export interface AdjacentNavigation {
  previous?: FlattenedNavNode;
  next?: FlattenedNavNode;
}

export interface DocsSearchEntry {
  id: string;
  name: string;
  displayName: string;
  description: string;
  category: string;
  status: string;
  path: string;
  tags: string[];
}
