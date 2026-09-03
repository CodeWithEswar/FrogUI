import React, { useState } from 'react';
import { getComponentPreview } from './ComponentPreviewRegistry';
import { ComponentPreviewFrame } from './ComponentPreviewFrame';
import { ComponentPreviewUnavailable } from './ComponentPreviewUnavailable';
import { ShowcaseModal } from '../ui/ShowcaseModal';
import { PreviewTheme } from './types';

export interface ComponentPreviewModeProps {
  componentId?: string;
  showcaseRoute?: string;
  defaultTheme?: PreviewTheme;
}

export const ComponentPreviewMode: React.FC<ComponentPreviewModeProps> = ({
  componentId = 'button',
  showcaseRoute = 'components/button',
  defaultTheme = 'dark'
}) => {
  const [theme, setTheme] = useState<PreviewTheme>(defaultTheme);
  const [isShowcaseOpen, setIsShowcaseOpen] = useState(false);

  const previewDefinition = getComponentPreview(componentId);

  return (
    <>
      <ComponentPreviewFrame
        theme={theme}
        onThemeChange={setTheme}
        onOpenShowcase={() => setIsShowcaseOpen(true)}
        minHeight={previewDefinition?.minHeight}
      >
        {previewDefinition ? (
          <previewDefinition.component
            theme={theme}
            isDark={theme === 'dark'}
          />
        ) : (
          <ComponentPreviewUnavailable
            componentId={componentId}
            onOpenShowcase={() => setIsShowcaseOpen(true)}
          />
        )}
      </ComponentPreviewFrame>

      {/* Interactive Showcase Modal */}
      <ShowcaseModal
        isOpen={isShowcaseOpen}
        onClose={() => setIsShowcaseOpen(false)}
        componentName={componentId.toUpperCase()}
        deepLinkRoute={showcaseRoute}
      />
    </>
  );
};
