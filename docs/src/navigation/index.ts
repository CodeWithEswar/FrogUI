import pages from '../../content/pages.json';
import { catalog, categories } from '../generated/catalog';
import { createDocsNavigation } from './docsNavigation';
import { DocumentationPageDefinition } from '../content/types';

export const documentationPages = pages as DocumentationPageDefinition[];
export const docsNavigation = createDocsNavigation(catalog, categories, documentationPages);

export * from './types';
export * from './docsNavigation';
