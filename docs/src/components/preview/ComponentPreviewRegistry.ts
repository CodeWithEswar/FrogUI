import { ButtonPreview } from './previews/button/ButtonPreview';
import { DrawerPreview } from './previews/drawer/DrawerPreview';
import { FabPreview } from './previews/fab/FabPreview';
import { IconButtonPreview } from './previews/icon-button/IconButtonPreview';
import { TextFieldPreview } from './previews/text-field/TextFieldPreview';
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
  },
  fab: {
    id: 'fab',
    displayName: 'Floating Action Button',
    component: FabPreview,
    previewMode: 'canvas'
  },
  'icon-button': {
    id: 'icon-button',
    displayName: 'Icon Button',
    component: IconButtonPreview,
    previewMode: 'canvas'
  },
  'text-field': {
    id: 'text-field',
    displayName: 'Text Field',
    component: TextFieldPreview,
    previewMode: 'canvas'
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
