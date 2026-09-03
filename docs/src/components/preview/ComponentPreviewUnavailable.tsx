import React from 'react';

interface ComponentPreviewUnavailableProps {
  componentId: string;
  onOpenShowcase?: () => void;
}

export const ComponentPreviewUnavailable: React.FC<ComponentPreviewUnavailableProps> = ({
  componentId,
  onOpenShowcase
}) => {
  return (
    <div className="py-16 px-6 text-center space-y-4">
      <div className="w-12 h-12 rounded-xl bg-zinc-100 dark:bg-zinc-800/80 border border-zinc-200 dark:border-zinc-700 flex items-center justify-center mx-auto text-zinc-500 dark:text-zinc-400">
        <svg className="w-6 h-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.5} d="M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4" />
        </svg>
      </div>

      <div className="space-y-1.5 max-w-sm mx-auto">
        <h3 className="text-sm font-semibold text-zinc-900 dark:text-zinc-100">
          Representative Web Preview Unavailable
        </h3>
        <p className="text-xs text-zinc-500 dark:text-zinc-400 leading-relaxed">
          A web preview has not been added for <code className="font-mono text-[11px] bg-zinc-100 dark:bg-zinc-800 px-1 py-0.5 rounded text-zinc-800 dark:text-zinc-200">{componentId}</code> yet. Open the Android Showcase application for the canonical interactive Jetpack Compose implementation.
        </p>
      </div>

      {onOpenShowcase && (
        <button
          onClick={onOpenShowcase}
          className="inline-flex items-center gap-1.5 px-3.5 py-1.5 rounded-lg bg-zinc-900 dark:bg-zinc-100 text-white dark:text-zinc-900 text-xs font-medium hover:bg-zinc-800 dark:hover:bg-white transition-colors cursor-pointer"
        >
          <span>Open in Android Showcase</span>
          <svg className="w-3.5 h-3.5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M14 5l7 7m0 0l-7 7m7-7H3" />
          </svg>
        </button>
      )}
    </div>
  );
};
