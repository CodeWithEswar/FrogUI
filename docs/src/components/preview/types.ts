import React from 'react';

export type PreviewTheme = 'light' | 'dark';
export type PreviewWidth = 'fit' | 'compact' | 'medium' | 'expanded';

export interface ComponentPreviewProps {
  theme: PreviewTheme;
  isDark: boolean;
  width?: PreviewWidth;
}

export interface ComponentPreviewDefinition {
  id: string;
  displayName: string;
  component: React.ComponentType<ComponentPreviewProps>;
  defaultTheme?: PreviewTheme;
  minHeight?: number;
  preferredWidth?: PreviewWidth;
  previewMode?: 'canvas' | 'overlay';
}
