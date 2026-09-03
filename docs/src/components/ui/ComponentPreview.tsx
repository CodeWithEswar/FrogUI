import React, { useState } from 'react';

interface ComponentPreviewProps {
  componentId?: string;
  showcaseRoute?: string;
}

export const ComponentPreview: React.FC<ComponentPreviewProps> = ({
  showcaseRoute = 'components/button'
}) => {
  const [previewTheme, setPreviewTheme] = useState<'light' | 'dark'>('dark');
  const [variant, setVariant] = useState<'Primary' | 'Secondary' | 'Outline' | 'Ghost' | 'Destructive'>('Primary');
  const [size, setSize] = useState<'Small' | 'Medium' | 'Large'>('Medium');
  const [loading, setLoading] = useState(false);
  const [disabled, setDisabled] = useState(false);

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
          return `${sizeClasses} bg-zinc-150 bg-zinc-200 text-zinc-800 font-medium hover:bg-zinc-300 active:scale-98 transition-all`;
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
      <div className="flex flex-wrap items-center justify-between gap-3 px-4 py-3 border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/90 dark:bg-zinc-900/90">
        {/* Variant selector */}
        <div className="flex items-center gap-1.5 overflow-x-auto text-xs">
          <span className="text-zinc-500 font-medium mr-1 text-[11px] uppercase tracking-wider">Variant:</span>
          {(['Primary', 'Secondary', 'Outline', 'Ghost', 'Destructive'] as const).map(v => (
            <button
              key={v}
              onClick={() => setVariant(v)}
              className={`px-2.5 py-1 rounded-md text-xs font-medium transition-colors ${
                variant === v
                  ? 'bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900'
                  : 'text-zinc-600 dark:text-zinc-400 hover:bg-zinc-200 dark:hover:bg-zinc-800'
              }`}
            >
              {v}
            </button>
          ))}
        </div>

        {/* Right controls: Size & Preview Theme toggle */}
        <div className="flex items-center gap-2 text-xs">
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

      {/* Interactive canvas */}
      <div
        className={`relative min-h-[220px] flex items-center justify-center p-8 transition-colors ${
          previewTheme === 'dark' ? 'bg-zinc-950 text-zinc-100' : 'bg-white text-zinc-900'
        }`}
      >
        <button
          disabled={disabled || loading}
          className={`inline-flex items-center justify-center select-none ${getButtonClasses()}`}
        >
          {loading ? (
            <>
              <svg className="animate-spin -ml-0.5 w-4 h-4 text-current" fill="none" viewBox="0 0 24 24">
                <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" />
                <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
              </svg>
              <span>Loading...</span>
            </>
          ) : (
            <span>Continue</span>
          )}
        </button>

        {/* State modifiers in canvas corner */}
        <div className="absolute bottom-3 right-3 flex items-center gap-2 text-xs">
          <label className="flex items-center gap-1.5 cursor-pointer text-zinc-500 hover:text-zinc-300 select-none">
            <input
              type="checkbox"
              checked={loading}
              onChange={e => setLoading(e.target.checked)}
              className="rounded text-zinc-800 dark:text-zinc-200 focus:ring-0"
            />
            <span className="text-[11px]">Loading</span>
          </label>
          <label className="flex items-center gap-1.5 cursor-pointer text-zinc-500 hover:text-zinc-300 select-none">
            <input
              type="checkbox"
              checked={disabled}
              onChange={e => setDisabled(e.target.checked)}
              className="rounded text-zinc-800 dark:text-zinc-200 focus:ring-0"
            />
            <span className="text-[11px]">Disabled</span>
          </label>
        </div>
      </div>

      {/* Footer link to native Showcase */}
      <div className="px-4 py-2.5 border-t border-zinc-200 dark:border-zinc-800 bg-zinc-50/60 dark:bg-zinc-900/60 flex items-center justify-between text-xs">
        <span className="text-zinc-500 dark:text-zinc-400">
          Representative preview · Actual component renders in native Compose
        </span>
        <a
          href={`frogui://${showcaseRoute}`}
          className="font-medium text-zinc-900 dark:text-zinc-100 hover:underline inline-flex items-center gap-1"
        >
          <span>Open in Showcase</span>
          <svg className="w-3.5 h-3.5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
            <path d="M5 12h14M12 5l7 7-7 7" />
          </svg>
        </a>
      </div>
    </div>
  );
};
