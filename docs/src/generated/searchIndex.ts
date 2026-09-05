// AUTO-GENERATED from registry and documentation content. DO NOT EDIT MANUALLY.
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

export const searchIndex: SearchItem[] = [
  {
    "id": "button",
    "name": "FrogButton",
    "displayName": "Button",
    "description": "Triggers an action with semantic variants, sizes, loading feedback, and composable content slots.",
    "category": "actions",
    "status": "experimental",
    "path": "/FrogUI/components/button",
    "tags": [
      "action",
      "submit",
      "loading"
    ]
  },
  {
    "id": "drawer",
    "name": "FrogDrawer",
    "displayName": "Drawer",
    "description": "Adaptive contextual overlay presented as a modal bottom sheet or docked side panel without navigating away from the current screen destination.",
    "category": "overlays",
    "status": "experimental",
    "path": "/FrogUI/components/drawer",
    "tags": [
      "overlay",
      "sheet",
      "side-panel",
      "inspector",
      "modal",
      "dialog"
    ]
  },
  {
    "id": "fab",
    "name": "FrogFloatingActionButton",
    "displayName": "Floating Action Button",
    "description": "A prominent floating contextual action with regular, small, and extended presentations, accessible naming, and reduced-motion fallbacks.",
    "category": "actions",
    "status": "experimental",
    "path": "/FrogUI/components/fab",
    "tags": [
      "action",
      "fab",
      "floating",
      "extended",
      "speeddial"
    ]
  },
  {
    "id": "icon-button",
    "name": "FrogIconButton",
    "displayName": "Icon Button",
    "description": "Compact icon-only action control with semantic variants, accessible labeling, loading support, and optional badge content.",
    "category": "actions",
    "status": "experimental",
    "path": "/FrogUI/components/icon-button",
    "tags": [
      "action",
      "icon",
      "iconbutton",
      "badge",
      "toolbar"
    ]
  },
  {
    "id": "text-field",
    "name": "FrogTextField",
    "displayName": "Text Field",
    "description": "A state-hoisted text input with filled, outline, and underline presentations, supporting content, slots, and accessible error handling.",
    "category": "inputs",
    "status": "experimental",
    "path": "/FrogUI/components/text-field",
    "tags": [
      "input",
      "form",
      "text-field",
      "entry",
      "editable"
    ]
  }
];
