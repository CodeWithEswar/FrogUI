import React, { useState } from 'react';
import { ShowcaseModal } from './ShowcaseModal';

interface ComponentPreviewProps {
  componentId?: string;
  showcaseRoute?: string;
}

export const ComponentPreview: React.FC<ComponentPreviewProps> = ({
  componentId = 'button',
  showcaseRoute = 'components/button'
}) => {
  const [previewTheme, setPreviewTheme] = useState<'light' | 'dark'>('dark');
  const [variant, setVariant] = useState<'Primary' | 'Secondary' | 'Outline' | 'Ghost' | 'Destructive'>('Primary');
  const [size, setSize] = useState<'Small' | 'Medium' | 'Large'>('Medium');
  const [loading, setLoading] = useState(false);
  const [disabled, setDisabled] = useState(false);
  const [isShowcaseOpen, setIsShowcaseOpen] = useState(false);

  // Variant styling for representative preview
  const getButtonClasses = () => {
    // Sizes
    const sizeClasses = {
      Small: 'h-8 px-3 text-xs gap-1.5 rounded-md',
      Medium: 'h-10 px-4 text-sm gap-2 rounded-lg',
      Large: 'h-12 px-5 text-base gap-2.5 rounded-xl'
    }[size];

    // Variants based on previewTheme
    if (previewTheme === 'dark') {
      if (disabled) return `${sizeClasses} bg-zinc-800/50 text-zinc-500 cursor-not-allowed border border-transparent`;
      switch (variant) {
        case 'Primary':
          return `${sizeClasses} bg-zinc-100 text-zinc-900 font-medium hover:bg-white active:scale-98 transition-all shadow-xs`;
        case 'Secondary':
          return `${sizeClasses} bg-zinc-800 text-zinc-200 font-medium hover:bg-zinc-700 active:scale-98 transition-all`;
        case 'Outline':
          return `${sizeClasses} border border-zinc-700 bg-transparent text-zinc-200 font-medium hover:bg-zinc-800/60 active:scale-98 transition-all`;
        case 'Ghost':
          return `${sizeClasses} bg-transparent text-zinc-300 font-medium hover:bg-zinc-800/60 active:scale-98 transition-all`;
        case 'Destructive':
          return `${sizeClasses} bg-rose-950/80 text-rose-300 border border-rose-800/60 font-medium hover:bg-rose-900 active:scale-98 transition-all`;
      }
    } else {
      if (disabled) return `${sizeClasses} bg-zinc-200/60 text-zinc-400 cursor-not-allowed border border-transparent`;
      switch (variant) {
        case 'Primary':
          return `${sizeClasses} bg-zinc-900 text-zinc-50 font-medium hover:bg-zinc-850 active:scale-98 transition-all shadow-xs`;
        case 'Secondary':
          return `${sizeClasses} bg-zinc-200 text-zinc-800 font-medium hover:bg-zinc-300 active:scale-98 transition-all`;
        case 'Outline':
          return `${sizeClasses} border border-zinc-300 bg-transparent text-zinc-800 font-medium hover:bg-zinc-100 active:scale-98 transition-all`;
        case 'Ghost':
          return `${sizeClasses} bg-transparent text-zinc-700 font-medium hover:bg-zinc-100 active:scale-98 transition-all`;
        case 'Destructive':
          return `${sizeClasses} bg-rose-50 text-rose-700 border border-rose-200 font-medium hover:bg-rose-100 active:scale-98 transition-all`;
      }
    }
  };

  return (
    <div className="my-6 w-full rounded-xl border border-zinc-200 dark:border-zinc-800 overflow-hidden shadow-xs">
      {/* Controls toolbar */}
      <div className="flex items-center justify-between gap-3 px-3 sm:px-4 py-2.5 border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/90 dark:bg-zinc-900/90 overflow-x-auto scrollbar-none">
        {/* Variant selector */}
        <div className="flex items-center gap-1 shrink-0 text-xs">
          <span className="text-zinc-500 font-medium mr-1 text-[11px] uppercase tracking-wider hidden sm:inline">
            Variant:
          </span>
          {(['Primary', 'Secondary', 'Outline', 'Ghost', 'Destructive'] as const).map(v => (
            <button
              key={v}
              onClick={() => setVariant(v)}
              className={`px-2.5 py-1 rounded-md text-xs font-medium transition-colors ${
                variant === v
                  ? 'bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 shadow-xs'
                  : 'text-zinc-600 dark:text-zinc-400 hover:bg-zinc-200 dark:hover:bg-zinc-800'
              }`}
            >
              {v}
            </button>
          ))}
        </div>

        {/* Right controls: Size & Preview Theme toggle */}
        <div className="flex items-center gap-2 shrink-0 text-xs">
          {/* Size picker */}
          <div className="flex items-center bg-zinc-200/70 dark:bg-zinc-800/80 p-0.5 rounded-lg">
            {(['Small', 'Medium', 'Large'] as const).map(s => (
              <button
                key={s}
                onClick={() => setSize(s)}
                className={`px-2 py-0.5 rounded-md text-[11px] font-medium transition-colors ${
                  size === s
                    ? 'bg-white dark:bg-zinc-700 text-zinc-900 dark:text-zinc-100 shadow-xs'
                    : 'text-zinc-600 dark:text-zinc-400 hover:text-zinc-900 dark:hover:text-zinc-200'
                }`}
              >
                {s[0]}
              </button>
            ))}
          </div>

          {/* Theme switcher */}
          <div className="flex items-center bg-zinc-200/70 dark:bg-zinc-800/80 p-0.5 rounded-lg">
            <button
              onClick={() => setPreviewTheme('light')}
              title="Light preview"
              className={`p-1 rounded-md transition-colors ${
                previewTheme === 'light' ? 'bg-white text-zinc-900 shadow-xs' : 'text-zinc-500 hover:text-zinc-900'
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
              onClick={() => setPreviewTheme('dark')}
              title="Dark preview"
              className={`p-1 rounded-md transition-colors ${
                previewTheme === 'dark' ? 'bg-zinc-700 text-zinc-100 shadow-xs' : 'text-zinc-400 hover:text-zinc-200'
              }`}
            >
              <svg className="w-3.5 h-3.5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z" />
              </svg>
            </button>
          </div>
        </div>
      </div>

      {/* Preview canvas area */}
      <div
        className={`relative h-64 flex items-center justify-center transition-colors ${
          previewTheme === 'dark'
            ? 'bg-zinc-950 text-zinc-100'
            : 'bg-zinc-50 text-zinc-900'
        }`}
      >
        {/* Rendered Component */}
        <button
          disabled={disabled || loading}
          className={`inline-flex items-center justify-center ${getButtonClasses()}`}
        >
          {loading ? (
            <>
              <svg className="animate-spin -ml-1 mr-2 h-4 w-4 text-current" fill="none" viewBox="0 0 24 24">
                <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" />
                <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
              </svg>
              <span>Loading...</span>
            </>
          ) : (
            <span>Continue</span>
          )}
        </button>

        {/* Customized FrogUI Checkboxes */}
        <div className="absolute bottom-3 right-3 flex items-center gap-3 text-xs">
          {/* Loading Checkbox */}
          <label className="flex items-center gap-1.5 cursor-pointer select-none group">
            <div
              className={`w-4 h-4 rounded flex items-center justify-center transition-all duration-150 border ${
                loading
                  ? previewTheme === 'dark'
                    ? 'bg-zinc-100 border-zinc-100 text-zinc-950'
                    : 'bg-zinc-900 border-zinc-900 text-white'
                  : previewTheme === 'dark'
                    ? 'border-zinc-700 bg-zinc-900/70 group-hover:border-zinc-500'
                    : 'border-zinc-300 bg-white group-hover:border-zinc-400'
              }`}
            >
              {loading && (
                <svg className="w-2.5 h-2.5 stroke-[3]" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <polyline points="20 6 9 17 4 12" />
                </svg>
              )}
            </div>
            <input
              type="checkbox"
              checked={loading}
              onChange={e => setLoading(e.target.checked)}
              className="sr-only"
            />
            <span
              className={`text-[11px] font-medium transition-colors ${
                loading
                  ? previewTheme === 'dark' ? 'text-zinc-200' : 'text-zinc-800'
                  : previewTheme === 'dark' ? 'text-zinc-500 group-hover:text-zinc-400' : 'text-zinc-500 group-hover:text-zinc-700'
              }`}
            >
              Loading
            </span>
          </label>

          {/* Disabled Checkbox */}
          <label className="flex items-center gap-1.5 cursor-pointer select-none group">
            <div
              className={`w-4 h-4 rounded flex items-center justify-center transition-all duration-150 border ${
                disabled
                  ? previewTheme === 'dark'
                    ? 'bg-zinc-100 border-zinc-100 text-zinc-950'
                    : 'bg-zinc-900 border-zinc-900 text-white'
                  : previewTheme === 'dark'
                    ? 'border-zinc-700 bg-zinc-900/70 group-hover:border-zinc-500'
                    : 'border-zinc-300 bg-white group-hover:border-zinc-400'
              }`}
            >
              {disabled && (
                <svg className="w-2.5 h-2.5 stroke-[3]" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <polyline points="20 6 9 17 4 12" />
                </svg>
              )}
            </div>
            <input
              type="checkbox"
              checked={disabled}
              onChange={e => setDisabled(e.target.checked)}
              className="sr-only"
            />
            <span
              className={`text-[11px] font-medium transition-colors ${
                disabled
                  ? previewTheme === 'dark' ? 'text-zinc-200' : 'text-zinc-800'
                  : previewTheme === 'dark' ? 'text-zinc-500 group-hover:text-zinc-400' : 'text-zinc-500 group-hover:text-zinc-700'
              }`}
            >
              Disabled
            </span>
          </label>
        </div>
      </div>

      {/* Footer link to native Showcase */}
      <div className="px-4 py-2.5 border-t border-zinc-200 dark:border-zinc-800 bg-zinc-50/60 dark:bg-zinc-900/60 flex items-center justify-between text-xs">
        <span className="text-zinc-500 dark:text-zinc-400 text-[11px] sm:text-xs">
          Representative preview &middot; Actual component renders in native Compose
        </span>
        <button
          onClick={() => setIsShowcaseOpen(true)}
          className="font-medium text-emerald-600 dark:text-emerald-400 hover:underline inline-flex items-center gap-1 cursor-pointer"
        >
          <span>Open in Showcase</span>
          <svg className="w-3.5 h-3.5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
            <path d="M5 12h14M12 5l7 7-7 7" />
          </svg>
        </button>
      </div>

      {/* Interactive Showcase Modal */}
      <ShowcaseModal
        isOpen={isShowcaseOpen}
        onClose={() => setIsShowcaseOpen(false)}
        componentName={componentId.toUpperCase()}
        deepLinkRoute={showcaseRoute}
      />
    </div>
  );
};
