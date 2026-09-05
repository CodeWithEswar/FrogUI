import React, { useState } from 'react';
import { ComponentPreviewProps } from '../../types';
import { PreviewCheckbox } from '../../PreviewCheckbox';

export const IconButtonPreview: React.FC<ComponentPreviewProps> = ({ isDark }) => {
  const [variant, setVariant] = useState<'Filled' | 'Tonal' | 'Outline' | 'Ghost'>('Filled');
  const [size, setSize] = useState<'Small' | 'Medium' | 'Large'>('Medium');
  const [icon, setIcon] = useState<'Search' | 'Settings' | 'Close' | 'Reset'>('Search');
  const [badge, setBadge] = useState<'None' | 'Dot' | 'Count'>('None');
  const [loading, setLoading] = useState(false);
  const [disabled, setDisabled] = useState(false);

  const sizeClasses = {
    Small: 'w-8 h-8 rounded-md',
    Medium: 'w-10 h-10 rounded-lg',
    Large: 'w-12 h-12 rounded-xl'
  }[size];

  const iconSizes = {
    Small: 'w-4 h-4',
    Medium: 'w-4.5 h-4.5',
    Large: 'w-5 h-5'
  }[size];

  const getButtonClasses = () => {
    if (isDark) {
      if (disabled) return `${sizeClasses} bg-zinc-800/50 text-zinc-500 cursor-not-allowed border border-transparent`;
      switch (variant) {
        case 'Filled':
          return `${sizeClasses} bg-zinc-100 text-zinc-900 hover:bg-white active:scale-95 transition-all shadow-xs`;
        case 'Tonal':
          return `${sizeClasses} bg-zinc-800 text-zinc-200 hover:bg-zinc-700 active:scale-95 transition-all`;
        case 'Outline':
          return `${sizeClasses} border border-zinc-700 bg-transparent text-zinc-200 hover:bg-zinc-800/60 active:scale-95 transition-all`;
        case 'Ghost':
          return `${sizeClasses} bg-transparent text-zinc-300 hover:bg-zinc-800/60 active:scale-95 transition-all`;
      }
    } else {
      if (disabled) return `${sizeClasses} bg-zinc-200/60 text-zinc-400 cursor-not-allowed border border-transparent`;
      switch (variant) {
        case 'Filled':
          return `${sizeClasses} bg-zinc-900 text-zinc-50 hover:bg-zinc-850 active:scale-95 transition-all shadow-xs`;
        case 'Tonal':
          return `${sizeClasses} bg-zinc-200 text-zinc-800 hover:bg-zinc-300 active:scale-95 transition-all`;
        case 'Outline':
          return `${sizeClasses} border border-zinc-300 bg-transparent text-zinc-800 hover:bg-zinc-100 active:scale-95 transition-all`;
        case 'Ghost':
          return `${sizeClasses} bg-transparent text-zinc-700 hover:bg-zinc-100 active:scale-95 transition-all`;
      }
    }
  };

  const renderIcon = () => {
    switch (icon) {
      case 'Search':
        return (
          <svg className={iconSizes} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <circle cx="11" cy="11" r="8" />
            <line x1="21" y1="21" x2="16.65" y2="16.65" />
          </svg>
        );
      case 'Settings':
        return (
          <svg className={iconSizes} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <circle cx="12" cy="12" r="3" />
            <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z" />
          </svg>
        );
      case 'Close':
        return (
          <svg className={iconSizes} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <line x1="18" y1="6" x2="6" y2="18" />
            <line x1="6" y1="6" x2="18" y2="18" />
          </svg>
        );
      case 'Reset':
        return (
          <svg className={iconSizes} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <polyline points="1 4 1 10 7 10" />
            <path d="M3.51 15a9 9 0 1 0 2.13-9.36L1 10" />
          </svg>
        );
    }
  };

  return (
    <div className="w-full h-full flex flex-col justify-between">
      {/* Variant & Size Selector Header */}
      <div className="flex items-center justify-between gap-2.5 px-3 sm:px-4 py-2.5 border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/90 dark:bg-zinc-900/90">
        {/* Mobile Variant Selector (< sm) */}
        <div className="sm:hidden flex items-center gap-1.5 text-xs min-w-0">
          <label htmlFor="mobile-icon-button-variant" className="text-zinc-500 font-medium text-[11px] uppercase tracking-wider shrink-0">
            Variant:
          </label>
          <div className="relative inline-block">
            <select
              id="mobile-icon-button-variant"
              value={variant}
              onChange={e => setVariant(e.target.value as any)}
              className="appearance-none pl-2.5 pr-7 py-1 rounded-md text-xs font-semibold border border-zinc-300 dark:border-zinc-700 bg-white dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100 shadow-2xs focus:outline-none focus:ring-1 focus:ring-[var(--frog-focus-ring)] cursor-pointer"
            >
              {(['Filled', 'Tonal', 'Outline', 'Ghost'] as const).map(v => (
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
          {(['Filled', 'Tonal', 'Outline', 'Ghost'] as const).map(v => (
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

        {/* Size Segmented Control */}
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

      {/* IconButton Stage Canvas */}
      <div className="relative flex-1 min-h-[220px] flex items-center justify-center py-12 px-6">
        <div className="relative inline-flex items-center justify-center">
          <button
            disabled={disabled || loading}
            aria-label={`${icon} action`}
            className={`inline-flex items-center justify-center cursor-pointer ${getButtonClasses()}`}
          >
            {loading ? (
              <svg className="animate-spin h-4 w-4 text-current" fill="none" viewBox="0 0 24 24">
                <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" />
                <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
              </svg>
            ) : (
              renderIcon()
            )}
          </button>

          {/* Badge Overlay */}
          {badge === 'Dot' && (
            <span className="absolute -top-1 -right-1 w-2.5 h-2.5 rounded-full bg-rose-500 ring-2 ring-white dark:ring-zinc-900" />
          )}
          {badge === 'Count' && (
            <span className="absolute -top-1.5 -right-2 min-w-[18px] h-[18px] px-1 flex items-center justify-center text-[10px] font-bold text-white rounded-full bg-rose-500 ring-2 ring-white dark:ring-zinc-900">
              3
            </span>
          )}
        </div>

        {/* Demo Controls Toolbar: Icon & Badge Selectors */}
        <div className="absolute top-3 left-3 flex items-center gap-2 text-xs">
          <div className="flex items-center gap-1 bg-zinc-200/50 dark:bg-zinc-800/60 p-0.5 rounded-md text-[11px]">
            {(['Search', 'Settings', 'Close', 'Reset'] as const).map(i => (
              <button
                key={i}
                onClick={() => setIcon(i)}
                className={`px-1.5 py-0.5 rounded ${icon === i ? 'bg-white dark:bg-zinc-700 shadow-2xs font-semibold' : 'text-zinc-500'}`}
              >
                {i}
              </button>
            ))}
          </div>
          <div className="flex items-center gap-1 bg-zinc-200/50 dark:bg-zinc-800/60 p-0.5 rounded-md text-[11px]">
            {(['None', 'Dot', 'Count'] as const).map(b => (
              <button
                key={b}
                onClick={() => setBadge(b)}
                className={`px-1.5 py-0.5 rounded ${badge === b ? 'bg-white dark:bg-zinc-700 shadow-2xs font-semibold' : 'text-zinc-500'}`}
              >
                {b}
              </button>
            ))}
          </div>
        </div>

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
