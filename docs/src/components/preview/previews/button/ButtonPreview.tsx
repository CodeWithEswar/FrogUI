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
      <div className="flex items-center justify-between gap-3 px-3 sm:px-4 py-2.5 border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/90 dark:bg-zinc-900/90 overflow-x-auto scrollbar-none">
        <div className="flex items-center gap-1 shrink-0 text-xs">
          <span className="text-zinc-500 font-medium mr-1 text-[11px] uppercase tracking-wider hidden sm:inline">
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

        <div className="flex items-center gap-1.5 shrink-0 text-xs bg-zinc-200/70 dark:bg-zinc-800/80 p-0.5 rounded-lg">
          {(['Small', 'Medium', 'Large'] as const).map(s => (
            <button
              key={s}
              onClick={() => setSize(s)}
              className={`px-2 py-0.5 rounded-md text-[11px] font-medium transition-colors cursor-pointer ${
                size === s
                  ? 'bg-white dark:bg-zinc-700 text-zinc-900 dark:text-zinc-100 shadow-xs'
                  : 'text-zinc-600 dark:text-zinc-400 hover:text-zinc-900 dark:hover:text-zinc-200'
              }`}
            >
              {s[0]}
            </button>
          ))}
        </div>
      </div>

      {/* Button Stage Canvas */}
      <div className="relative flex-1 min-h-[200px] flex items-center justify-center p-6">
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
        <div className="absolute bottom-3 right-3 flex items-center gap-3 text-xs z-10">
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
