import React from 'react';
import { ComponentPreviewMode, ComponentPreviewModeProps } from '../preview/ComponentPreviewMode';

export type ComponentPreviewProps = ComponentPreviewModeProps;

/**
 * ComponentPreview orchestrator.
 * Delegates component preview rendering to the modular ComponentPreviewRegistry.
 */
export const ComponentPreview: React.FC<ComponentPreviewProps> = (props) => {
  return <ComponentPreviewMode {...props} />;
};

export { ComponentPreviewMode };
