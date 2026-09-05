import React from 'react';
import { PreviewTheme } from './types';

interface ComponentPreviewFrameProps {
  theme: PreviewTheme;
  onThemeChange: (theme: PreviewTheme) => void;
  onOpenShowcase?: () => void;
  minHeight?: number;
  children: React.ReactNode;
}

export const ComponentPreviewFrame: React.FC<ComponentPreviewFrameProps> = ({
  theme,
  onThemeChange,
  onOpenShowcase,
  children
}) => {
  const isDark = theme === 'dark';

  return (
    <div className="my-6 w-full rounded-xl border border-zinc-200 dark:border-zinc-800 overflow-hidden shadow-xs">
      {/* Frame Top Header */}
      <div className="flex items-center justify-between gap-3 px-3 sm:px-4 py-2 border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/90 dark:bg-zinc-900/90">
        <div className="flex items-center gap-2 text-xs text-zinc-500 dark:text-zinc-400">
          <span className="w-2 h-2 rounded-full bg-emerald-500/80" />
          <span className="font-medium text-[11px] uppercase tracking-wider">Preview Canvas</span>
        </div>

        {/* Global Canvas Theme Switcher */}
        <div className="flex items-center bg-zinc-200/70 dark:bg-zinc-800/80 p-0.5 rounded-lg">
          <button
            onClick={() => onThemeChange('light')}
            title="Light preview canvas"
            className={`p-1 rounded-md transition-colors cursor-pointer ${
              theme === 'light'
                ? 'bg-white text-zinc-900 shadow-xs'
                : 'text-zinc-500 hover:text-zinc-900 dark:hover:text-zinc-200'
            }`}
          >
            <svg className="w-3.5 h-3.5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
              <circle cx="12" cy="12" r="5" />
              <line x1="12" y1="1" x2="12" y2="3" />
              <line x1="12" y1="21" x2="12" y2="23" />
              <line x1="4.22" y1="4.22" x2="5.64" y2="5.64" />
              <line x1="18.36" y1="18.36" x2="19.78" y2="19.78" />
              <line x1="1" y1="12" x2="3" y2="12" />
              <line x1="21" y1="12" x2="23" y2="12" />
              <line x1="4.22" y1="19.78" x2="5.64" y2="18.36" />
              <line x1="18.36" y1="5.64" x2="19.78" y2="4.22" />
            </svg>
          </button>
          <button
            onClick={() => onThemeChange('dark')}
            title="Dark preview canvas"
            className={`p-1 rounded-md transition-colors cursor-pointer ${
              theme === 'dark'
                ? 'bg-zinc-700 text-zinc-100 shadow-xs'
                : 'text-zinc-400 hover:text-zinc-200'
            }`}
          >
            <svg className="w-3.5 h-3.5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
              <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z" />
            </svg>
          </button>
        </div>
      </div>

      {/* Frame Canvas Content */}
      <div
        className={`relative w-full transition-colors ${
          isDark ? 'bg-zinc-950 text-zinc-100' : 'bg-zinc-100 text-zinc-900'
        }`}
      >
        {children}
      </div>

      {/* Frame Bottom Footer */}
      <div className="px-3.5 sm:px-4 py-2.5 border-t border-zinc-200 dark:border-zinc-800 bg-zinc-50/60 dark:bg-zinc-900/60 flex flex-col sm:flex-row sm:items-center justify-between gap-2 text-xs">
        <span className="text-zinc-500 dark:text-zinc-400 text-[11px] sm:text-xs leading-relaxed">
          Representative preview &middot; Actual component renders in native Compose
        </span>
        {onOpenShowcase && (
          <button
            onClick={onOpenShowcase}
            className="self-start sm:self-auto shrink-0 font-medium text-zinc-900 dark:text-zinc-100 hover:underline inline-flex items-center gap-1.5 text-xs cursor-pointer group"
          >
            <span>Open in Showcase</span>
            <svg className="w-3.5 h-3.5 transition-transform group-hover:translate-x-0.5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
              <path d="M5 12h14M12 5l7 7-7 7" />
            </svg>
          </button>
        )}
      </div>
    </div>
  );
};
