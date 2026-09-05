import React, { useState } from 'react';
import { ComponentPreviewProps } from '../../types';
import { PreviewCheckbox } from '../../PreviewCheckbox';

export const ButtonPreview: React.FC<ComponentPreviewProps> = ({ isDark }) => {
  const [variant, setVariant] = useState<'Primary' | 'Secondary' | 'Outline' | 'Ghost' | 'Destructive'>('Primary');
  const [size, setSize] = useState<'Small' | 'Medium' | 'Large'>('Medium');
  const [loading, setLoading] = useState(false);
  const [disabled, setDisabled] = useState(false);

  const getButtonClasses = () => {
    const sizeClasses = {
      Small: 'h-8 px-3 text-xs gap-1.5 rounded-md',
      Medium: 'h-10 px-4 text-sm gap-2 rounded-lg',
      Large: 'h-12 px-5 text-base gap-2.5 rounded-xl'
    }[size];

    if (isDark) {
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
    <div className="w-full h-full flex flex-col justify-between">
      {/* Button Variant & Size Selector Header */}
      <div className="flex items-center justify-between gap-2.5 px-3 sm:px-4 py-2.5 border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/90 dark:bg-zinc-900/90">
        {/* Mobile Variant Selector (< sm) */}
        <div className="sm:hidden flex items-center gap-1.5 text-xs min-w-0">
          <label htmlFor="mobile-button-variant" className="text-zinc-500 font-medium text-[11px] uppercase tracking-wider shrink-0">
            Variant:
          </label>
          <div className="relative inline-block">
            <select
              id="mobile-button-variant"
              value={variant}
              onChange={e => setVariant(e.target.value as any)}
              className="appearance-none pl-2.5 pr-7 py-1 rounded-md text-xs font-semibold border border-zinc-300 dark:border-zinc-700 bg-white dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100 shadow-2xs focus:outline-none focus:ring-1 focus:ring-[var(--frog-focus-ring)] cursor-pointer"
            >
              {(['Primary', 'Secondary', 'Outline', 'Ghost', 'Destructive'] as const).map(v => (
                <option key={v} value={v} className="bg-white dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100">
                  {v}
                </option>
              ))}
            </select>
            <svg
              className="w-3.5 h-3.5 text-zinc-400 dark:text-zinc-500 absolute right-2 top-1/2 -translate-y-1/2 pointer-events-none"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
            >
              <polyline points="6 9 12 15 18 9" />
            </svg>
          </div>
        </div>

        {/* Desktop Variant Pills (>= sm) */}
        <div className="hidden sm:flex items-center gap-1 shrink-0 text-xs">
          <span className="text-zinc-500 font-medium mr-1 text-[11px] uppercase tracking-wider">
            Variant:
          </span>
          {(['Primary', 'Secondary', 'Outline', 'Ghost', 'Destructive'] as const).map(v => (
            <button
              key={v}
              onClick={() => setVariant(v)}
              className={`px-2.5 py-1 rounded-md text-xs font-medium transition-colors cursor-pointer ${
                variant === v
                  ? 'bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 shadow-xs'
                  : 'text-zinc-600 dark:text-zinc-400 hover:bg-zinc-200 dark:hover:bg-zinc-800'
              }`}
            >
              {v}
            </button>
          ))}
        </div>

        {/* Size Segmented Control (Both Mobile & Desktop) */}
        <div className="flex items-center gap-1 shrink-0 text-xs bg-zinc-200/70 dark:bg-zinc-800/80 p-0.5 rounded-lg border border-zinc-300/40 dark:border-zinc-700/40">
          {(['Small', 'Medium', 'Large'] as const).map(s => (
            <button
              key={s}
              onClick={() => setSize(s)}
              aria-label={`${s} size`}
              className={`px-2 py-0.5 rounded-md text-[11px] font-medium transition-colors cursor-pointer ${
                size === s
                  ? 'bg-white dark:bg-zinc-700 text-zinc-900 dark:text-zinc-100 shadow-xs font-semibold'
                  : 'text-zinc-600 dark:text-zinc-400 hover:text-zinc-900 dark:hover:text-zinc-200'
              }`}
            >
              {s[0]}
            </button>
          ))}
        </div>
      </div>

      {/* Button Stage Canvas */}
      <div className="relative flex-1 min-h-[220px] flex items-center justify-center py-12 px-6">
        <button
          disabled={disabled || loading}
          className={`inline-flex items-center justify-center cursor-pointer ${getButtonClasses()}`}
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

        {/* Loading & Disabled Toggles */}
        <div className="absolute bottom-3 right-3 flex items-center gap-3 text-xs z-10 bg-zinc-900/40 dark:bg-zinc-950/60 backdrop-blur-xs px-2.5 py-1 rounded-md border border-zinc-700/20 sm:border-transparent sm:bg-transparent sm:backdrop-blur-none">
          <PreviewCheckbox
            label="Loading"
            checked={loading}
            onChange={setLoading}
            isDark={isDark}
          />
          <PreviewCheckbox
            label="Disabled"
            checked={disabled}
            onChange={setDisabled}
            isDark={isDark}
          />
        </div>
      </div>
    </div>
  );
};
