import React, { useState } from 'react';
import { ComponentPreviewProps } from '../../types';
import { PreviewCheckbox } from '../../PreviewCheckbox';

export const FabPreview: React.FC<ComponentPreviewProps> = ({ isDark }) => {
  const [presentation, setPresentation] = useState<'Regular' | 'Small' | 'Extended'>('Regular');
  const [expanded, setExpanded] = useState(true);
  const [visible, setVisible] = useState(true);
  const [disabled, setDisabled] = useState(false);
  const [icon, setIcon] = useState<'Add' | 'Edit' | 'Camera' | 'Scan' | 'Compose'>('Add');
  const [label, setLabel] = useState('Create');

  const renderIcon = (iconSizeClass: string) => {
    switch (icon) {
      case 'Add':
        return (
          <svg className={iconSizeClass} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round">
            <line x1="12" y1="5" x2="12" y2="19" />
            <line x1="5" y1="12" x2="19" y2="12" />
          </svg>
        );
      case 'Edit':
        return (
          <svg className={iconSizeClass} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round">
            <path d="M12 20h9" />
            <path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z" />
          </svg>
        );
      case 'Camera':
        return (
          <svg className={iconSizeClass} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round">
            <path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z" />
            <circle cx="12" cy="13" r="4" />
          </svg>
        );
      case 'Scan':
        return (
          <svg className={iconSizeClass} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round">
            <path d="M3 7V5a2 2 0 0 1 2-2h2" />
            <path d="M17 3h2a2 2 0 0 1 2 2v2" />
            <path d="M21 17v2a2 2 0 0 1-2 2h-2" />
            <path d="M7 21H5a2 2 0 0 1-2-2v-2" />
            <line x1="7" y1="12" x2="17" y2="12" />
          </svg>
        );
      case 'Compose':
        return (
          <svg className={iconSizeClass} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round">
            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z" />
            <line x1="9" y1="10" x2="15" y2="10" />
          </svg>
        );
    }
  };

  const getContainerDimensions = () => {
    switch (presentation) {
      case 'Regular':
        return 'w-14 h-14 rounded-2xl'; // 56dp
      case 'Small':
        return 'w-10 h-10 rounded-xl'; // 40dp (inside min 48dp envelope)
      case 'Extended':
        return expanded
          ? 'h-12 px-4 min-w-[120px] rounded-2xl gap-2.5'
          : 'w-12 h-12 rounded-2xl justify-center'; // 48dp
    }
  };

  const getIconDimensions = () => {
    switch (presentation) {
      case 'Regular':
        return 'w-6 h-6'; // 24dp
      case 'Small':
        return 'w-5 h-5'; // 20dp
      case 'Extended':
        return 'w-5 h-5'; // 20dp
    }
  };

  const getThemeClasses = () => {
    if (disabled) {
      return isDark
        ? 'bg-zinc-800/60 text-zinc-500 cursor-not-allowed border border-zinc-700/30'
        : 'bg-zinc-200/60 text-zinc-400 cursor-not-allowed border border-zinc-300/40';
    }

    if (isDark) {
      return 'bg-zinc-100 text-zinc-900 hover:bg-white active:scale-95 shadow-lg shadow-black/40 hover:shadow-xl border border-white/10';
    } else {
      return 'bg-zinc-900 text-zinc-50 hover:bg-zinc-800 active:scale-95 shadow-md shadow-zinc-900/25 hover:shadow-lg border border-zinc-800';
    }
  };

  return (
    <div className="w-full h-full flex flex-col justify-between">
      {/* Header Controls: Presentation Selector */}
      <div className="flex items-center justify-between gap-2.5 px-3 sm:px-4 py-2.5 border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/90 dark:bg-zinc-900/90">
        {/* Mobile Presentation Selector (< sm) */}
        <div className="sm:hidden flex items-center gap-1.5 text-xs min-w-0">
          <label htmlFor="mobile-fab-presentation" className="text-zinc-500 font-medium text-[11px] uppercase tracking-wider shrink-0">
            Form:
          </label>
          <div className="relative inline-block">
            <select
              id="mobile-fab-presentation"
              value={presentation}
              onChange={e => setPresentation(e.target.value as any)}
              className="appearance-none pl-2.5 pr-7 py-1 rounded-md text-xs font-semibold border border-zinc-300 dark:border-zinc-700 bg-white dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100 shadow-2xs focus:outline-none focus:ring-1 focus:ring-[var(--frog-focus-ring)] cursor-pointer"
            >
              {(['Regular', 'Small', 'Extended'] as const).map(p => (
                <option key={p} value={p} className="bg-white dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100">
                  {p}
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

        {/* Desktop Presentation Pills (>= sm) */}
        <div className="hidden sm:flex items-center gap-1 shrink-0 text-xs">
          <span className="text-zinc-500 font-medium mr-1 text-[11px] uppercase tracking-wider">
            Presentation:
          </span>
          {(['Regular', 'Small', 'Extended'] as const).map(p => (
            <button
              key={p}
              onClick={() => setPresentation(p)}
              className={`px-2.5 py-1 rounded-md text-xs font-medium transition-colors cursor-pointer ${
                presentation === p
                  ? 'bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 shadow-xs font-semibold'
                  : 'text-zinc-600 dark:text-zinc-400 hover:bg-zinc-200 dark:hover:bg-zinc-800'
              }`}
            >
              {p}
            </button>
          ))}
        </div>

        {/* Extended Expansion Switch (Only when Extended) */}
        {presentation === 'Extended' && (
          <div className="flex items-center gap-1.5 text-xs bg-zinc-200/60 dark:bg-zinc-800/70 px-2 py-0.5 rounded-lg border border-zinc-300/40 dark:border-zinc-700/40">
            <PreviewCheckbox
              label="Expanded"
              checked={expanded}
              onChange={setExpanded}
              isDark={isDark}
            />
          </div>
        )}
      </div>

      {/* FAB Stage Canvas */}
      <div className="relative flex-1 min-h-[240px] flex items-center justify-center py-12 px-6">
        {visible ? (
          <div className="relative inline-flex items-center justify-center p-2">
            {/* Small Touch-Target Envelope Indicator for Small FAB */}
            {presentation === 'Small' && (
              <div
                className="absolute inset-0 min-w-[48px] min-h-[48px] border border-dashed border-zinc-400/30 dark:border-zinc-600/30 rounded-full pointer-events-none"
                title="Minimum 48dp accessible touch target envelope"
              />
            )}

            <button
              disabled={disabled}
              aria-label={presentation === 'Extended' && expanded ? label : `${icon} action`}
              className={`inline-flex items-center justify-center cursor-pointer transition-all duration-200 motion-reduce:transition-none select-none ${getContainerDimensions()} ${getThemeClasses()}`}
            >
              {renderIcon(getIconDimensions())}
              {presentation === 'Extended' && expanded && (
                <span className="text-sm font-semibold tracking-wide whitespace-nowrap overflow-hidden text-ellipsis">
                  {label}
                </span>
              )}
            </button>
          </div>
        ) : (
          /* Hidden Placeholder State */
          <div className="flex flex-col items-center justify-center gap-2 p-4 rounded-xl border border-dashed border-zinc-300 dark:border-zinc-700 text-zinc-400 dark:text-zinc-500 select-none">
            <svg className="w-6 h-6 stroke-1" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24" />
              <line x1="1" y1="1" x2="23" y2="23" />
            </svg>
            <span className="text-xs font-medium">Component hidden (visible = false)</span>
          </div>
        )}

        {/* Demo Controls Toolbar: Icon Picker */}
        <div className="absolute top-3 left-3 flex items-center gap-2 text-xs">
          <div className="flex items-center gap-1 bg-zinc-200/50 dark:bg-zinc-800/60 p-0.5 rounded-md text-[11px]">
            {(['Add', 'Edit', 'Camera', 'Scan', 'Compose'] as const).map(i => (
              <button
                key={i}
                onClick={() => setIcon(i)}
                className={`px-1.5 py-0.5 rounded transition-colors ${
                  icon === i
                    ? 'bg-white dark:bg-zinc-700 text-zinc-900 dark:text-zinc-100 shadow-2xs font-semibold'
                    : 'text-zinc-500 hover:text-zinc-800 dark:hover:text-zinc-300'
                }`}
              >
                {i}
              </button>
            ))}
          </div>

          {presentation === 'Extended' && (
            <input
              type="text"
              value={label}
              onChange={e => setLabel(e.target.value)}
              placeholder="Label"
              aria-label="Extended FAB label"
              className="px-2 py-0.5 text-xs rounded border border-zinc-300 dark:border-zinc-700 bg-white dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100 max-w-[90px] focus:outline-none focus:ring-1 focus:ring-[var(--frog-focus-ring)]"
            />
          )}
        </div>

        {/* Visible & Disabled Toggles */}
        <div className="absolute bottom-3 right-3 flex items-center gap-3 text-xs z-10 bg-zinc-900/40 dark:bg-zinc-950/60 backdrop-blur-xs px-2.5 py-1 rounded-md border border-zinc-700/20 sm:border-transparent sm:bg-transparent sm:backdrop-blur-none">
          <PreviewCheckbox
            label="Visible"
            checked={visible}
            onChange={setVisible}
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
