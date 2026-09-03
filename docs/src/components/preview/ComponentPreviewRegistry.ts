import { ButtonPreview } from './previews/button/ButtonPreview';
import { DrawerPreview } from './previews/drawer/DrawerPreview';
import { ComponentPreviewDefinition } from './types';

export const componentPreviewRegistry: Record<string, ComponentPreviewDefinition> = {
  button: {
    id: 'button',
    displayName: 'Button',
    component: ButtonPreview,
    previewMode: 'canvas'
  },
  drawer: {
    id: 'drawer',
    displayName: 'Drawer',
    component: DrawerPreview,
    previewMode: 'overlay',
    minHeight: 420
  }
};

export function getComponentPreview(id: string): ComponentPreviewDefinition | undefined {
  if (!id) return undefined;
  return componentPreviewRegistry[id.toLowerCase().trim()];
}

export function hasComponentPreview(id: string): boolean {
  if (!id) return false;
  return Object.prototype.hasOwnProperty.call(componentPreviewRegistry, id.toLowerCase().trim());
}
