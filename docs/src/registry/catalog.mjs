// This consumer receives JSON metadata only; no Android modules or binary tooling.
export function docsCatalog(generated, basePath = '/FrogUI/') {
  if (!/^\/[A-Za-z0-9/_-]*\/$/.test(basePath)) throw new Error('Expected an absolute directory base path');
  return generated.components.map(component => ({
    id: component.id,
    name: component.name,
    displayName: component.displayName,
    description: component.description,
    category: component.category,
    status: component.status,
    since: component.since,
    path: basePath.replace(/\/$/, '') + component.docs,
    variants: component.variants,
    sizes: component.sizes,
    properties: component.properties,
    examples: component.examples,
    tags: component.tags,
    accessibility: component.accessibility,
    quality: component.quality
  }));
}

export function searchIndex(catalog) {
  return catalog.map(({ id, name, displayName, description, category, status, path, tags }) =>
    ({ id, name, displayName, description, category, status, path, tags }));
}
