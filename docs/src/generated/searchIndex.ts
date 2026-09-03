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
  }
];
