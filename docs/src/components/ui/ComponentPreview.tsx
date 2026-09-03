import React, { useState, useRef } from 'react';
import { ShowcaseModal } from './ShowcaseModal';

interface ComponentPreviewProps {
  componentId?: string;
  showcaseRoute?: string;
}

export const ComponentPreview: React.FC<ComponentPreviewProps> = ({
  componentId = 'button',
  showcaseRoute = 'components/button'
}) => {
  const [isShowcaseOpen, setIsShowcaseOpen] = useState(false);

  return (
    <div className="my-6 w-full rounded-xl border border-zinc-200 dark:border-zinc-800 overflow-hidden shadow-xs">
      {componentId === 'drawer' ? (
        <DrawerPreview />
      ) : (
        <ButtonPreview />
      )}

      {/* Footer link to native Showcase */}
      <div className="px-4 py-2.5 border-t border-zinc-200 dark:border-zinc-800 bg-zinc-50/60 dark:bg-zinc-900/60 flex items-center justify-between text-xs">
        <span className="text-zinc-500 dark:text-zinc-400 text-[11px] sm:text-xs">
          Representative preview &middot; Actual component renders in native Compose
        </span>
        <button
          onClick={() => setIsShowcaseOpen(true)}
          className="font-medium text-zinc-900 dark:text-zinc-100 hover:underline inline-flex items-center gap-1 cursor-pointer"
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

/* =========================================================================
   1. BUTTON PREVIEW WORKBENCH
   ========================================================================= */
const ButtonPreview: React.FC = () => {
  const [previewTheme, setPreviewTheme] = useState<'light' | 'dark'>('dark');
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
    <div>
      {/* Controls toolbar */}
      <div className="flex items-center justify-between gap-3 px-3 sm:px-4 py-2.5 border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/90 dark:bg-zinc-900/90 overflow-x-auto scrollbar-none">
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

        <div className="flex items-center gap-2 shrink-0 text-xs">
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

          <ThemeToggleControl theme={previewTheme} onToggle={setPreviewTheme} />
        </div>
      </div>

      {/* Canvas */}
      <div
        className={`relative h-64 flex items-center justify-center transition-colors ${
          previewTheme === 'dark' ? 'bg-zinc-950 text-zinc-100' : 'bg-zinc-50 text-zinc-900'
        }`}
      >
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

        <div className="absolute bottom-3 right-3 flex items-center gap-3 text-xs">
          <CheckboxControl label="Loading" checked={loading} onChange={setLoading} dark={previewTheme === 'dark'} />
          <CheckboxControl label="Disabled" checked={disabled} onChange={setDisabled} dark={previewTheme === 'dark'} />
        </div>
      </div>
    </div>
  );
};

/* =========================================================================
   2. DRAWER PREVIEW WORKBENCH WITH REAL DRAG-TO-DISMISS
   ========================================================================= */
const DrawerPreview: React.FC = () => {
  const [previewTheme, setPreviewTheme] = useState<'light' | 'dark'>('dark');
  const [isOpen, setIsOpen] = useState(true);
  const [presentation, setPresentation] = useState<'bottom' | 'side-end' | 'side-start'>('bottom');
  const [hasHeader, setHasHeader] = useState(true);
  const [hasFooter, setHasFooter] = useState(true);

  // Gesture State
  const [dragOffset, setDragOffset] = useState(0);
  const [isDragging, setIsDragging] = useState(false);
  const startPos = useRef({ x: 0, y: 0 });

  const isDark = previewTheme === 'dark';

  /* Theme-specific explicit styles for deterministic contrast */
  const canvasBg = isDark ? 'bg-zinc-950 text-zinc-100' : 'bg-zinc-100 text-zinc-900';
  const drawerContainer = isDark
    ? 'bg-zinc-900 border-zinc-800 text-zinc-100 shadow-2xl'
    : 'bg-white border-zinc-200 text-zinc-900 shadow-xl';
  const dragHandleBg = isDark ? 'bg-zinc-700' : 'bg-zinc-300';
  const headerBorder = isDark ? 'border-zinc-800' : 'border-zinc-200';
  const titleColor = isDark ? 'text-zinc-100' : 'text-zinc-900';
  const subtitleColor = isDark ? 'text-zinc-400' : 'text-zinc-500';
  const closeBtn = isDark ? 'text-zinc-400 hover:text-zinc-100 hover:bg-zinc-800' : 'text-zinc-500 hover:text-zinc-900 hover:bg-zinc-100';

  const cardContainer = isDark
    ? 'bg-zinc-800/60 border-zinc-700/60'
    : 'bg-zinc-50 border-zinc-200';
  const cardTitle = isDark ? 'text-zinc-100' : 'text-zinc-900';
  const cardSubtitle = isDark ? 'text-zinc-400' : 'text-zinc-500';
  const badgeStyle = isDark
    ? 'bg-zinc-800 text-zinc-300 border-zinc-700'
    : 'bg-zinc-200/70 text-zinc-700 border-zinc-300';
  const checkmarkColor = isDark ? 'text-zinc-100' : 'text-zinc-900';

  const footerStyle = isDark
    ? 'border-zinc-800 bg-zinc-900'
    : 'border-zinc-200 bg-zinc-50/90';
  const dismissBtn = isDark
    ? 'text-zinc-400 hover:text-zinc-100 hover:bg-zinc-800'
    : 'text-zinc-600 hover:text-zinc-900 hover:bg-zinc-100';
  const primaryBtn = isDark
    ? 'bg-zinc-100 text-zinc-950 hover:bg-white active:scale-98'
    : 'bg-zinc-900 text-zinc-50 hover:bg-zinc-800 active:scale-98';

  /* Pointer Gesture Handlers */
  const onPointerDown = (e: React.PointerEvent) => {
    (e.currentTarget as HTMLElement).setPointerCapture(e.pointerId);
    setIsDragging(true);
    startPos.current = { x: e.clientX, y: e.clientY };
  };

  const onPointerMove = (e: React.PointerEvent) => {
    if (!isDragging) return;
    if (presentation === 'bottom') {
      const deltaY = e.clientY - startPos.current.y;
      setDragOffset(Math.max(0, deltaY));
    } else if (presentation === 'side-end') {
      const deltaX = e.clientX - startPos.current.x;
      setDragOffset(Math.max(0, deltaX));
    } else if (presentation === 'side-start') {
      const deltaX = startPos.current.x - e.clientX;
      setDragOffset(Math.max(0, deltaX));
    }
  };

  const onPointerUp = (e: React.PointerEvent) => {
    if (!isDragging) return;
    try {
      (e.currentTarget as HTMLElement).releasePointerCapture(e.pointerId);
    } catch {}
    setIsDragging(false);
    if (dragOffset > 50) {
      setIsOpen(false);
    }
    setDragOffset(0);
  };

  /* Dynamic CSS Transforms during gestures */
  const bottomTransform = isOpen
    ? isDragging
      ? `translateY(${dragOffset}px)`
      : 'translateY(0)'
    : 'translateY(100%)';

  const sideEndTransform = isOpen
    ? isDragging
      ? `translateX(${dragOffset}px)`
      : 'translateX(0)'
    : 'translateX(100%)';

  const sideStartTransform = isOpen
    ? isDragging
      ? `translateX(-${dragOffset}px)`
      : 'translateX(0)'
    : 'translateX(-100%)';

  const scrimOpacity = isOpen ? Math.max(0, 1 - dragOffset / 220) : 0;

  return (
    <div>
      {/* Controls toolbar */}
      <div className="flex items-center justify-between gap-3 px-3 sm:px-4 py-2.5 border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/90 dark:bg-zinc-900/90 overflow-x-auto scrollbar-none">
        {/* Presentation modes */}
        <div className="flex items-center gap-1 shrink-0 text-xs">
          <span className="text-zinc-500 font-medium mr-1 text-[11px] uppercase tracking-wider hidden sm:inline">
            Mode:
          </span>
          <button
            onClick={() => { setPresentation('bottom'); setIsOpen(true); setDragOffset(0); }}
            className={`px-2.5 py-1 rounded-md text-xs font-medium transition-colors ${
              presentation === 'bottom'
                ? 'bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 shadow-xs'
                : 'text-zinc-600 dark:text-zinc-400 hover:bg-zinc-200 dark:hover:bg-zinc-800'
            }`}
          >
            Bottom Sheet
          </button>
          <button
            onClick={() => { setPresentation('side-end'); setIsOpen(true); setDragOffset(0); }}
            className={`px-2.5 py-1 rounded-md text-xs font-medium transition-colors ${
              presentation === 'side-end'
                ? 'bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 shadow-xs'
                : 'text-zinc-600 dark:text-zinc-400 hover:bg-zinc-200 dark:hover:bg-zinc-800'
            }`}
          >
            Side (End)
          </button>
          <button
            onClick={() => { setPresentation('side-start'); setIsOpen(true); setDragOffset(0); }}
            className={`px-2.5 py-1 rounded-md text-xs font-medium transition-colors ${
              presentation === 'side-start'
                ? 'bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 shadow-xs'
                : 'text-zinc-600 dark:text-zinc-400 hover:bg-zinc-200 dark:hover:bg-zinc-800'
            }`}
          >
            Side (Start)
          </button>
        </div>

        {/* Right controls: State toggle, Header/Footer toggles & Theme switcher */}
        <div className="flex items-center gap-2 shrink-0 text-xs">
          <button
            onClick={() => { setIsOpen(!isOpen); setDragOffset(0); }}
            className={`px-2.5 py-1 rounded-md text-xs font-medium border transition-colors ${
              isOpen
                ? 'border-zinc-400 dark:border-zinc-600 bg-zinc-200/70 dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100 font-medium'
                : 'border-zinc-300 dark:border-zinc-700 text-zinc-600 dark:text-zinc-400 hover:bg-zinc-100 dark:hover:bg-zinc-800'
            }`}
          >
            {isOpen ? 'Close Drawer' : 'Open Drawer'}
          </button>

          <ThemeToggleControl theme={previewTheme} onToggle={setPreviewTheme} />
        </div>
      </div>

      {/* Interactive Device / Canvas Stage */}
      <div
        className={`relative h-96 sm:h-[420px] w-full overflow-hidden select-none transition-colors ${canvasBg}`}
      >
        {/* Background Canvas: Simulated App Destination */}
        <div className="absolute inset-0 p-6 flex flex-col justify-between opacity-30 filter blur-[0.5px]">
          <div className="flex items-center justify-between pb-4 border-b border-zinc-500/20">
            <div className="flex items-center gap-3">
              <div className="w-8 h-8 rounded-lg bg-zinc-500/30 flex items-center justify-center font-bold text-xs">F</div>
              <div>
                <div className="w-24 h-3 rounded bg-zinc-500/30 mb-1.5" />
                <div className="w-16 h-2 rounded bg-zinc-500/20" />
              </div>
            </div>
            <div className="w-7 h-7 rounded-full bg-zinc-500/20" />
          </div>

          <div className="space-y-3 py-6">
            <div className="w-3/4 h-5 rounded bg-zinc-500/30" />
            <div className="w-full h-3 rounded bg-zinc-500/20" />
            <div className="w-5/6 h-3 rounded bg-zinc-500/20" />
            <div className="grid grid-cols-2 gap-3 pt-4">
              <div className="h-20 rounded-xl bg-zinc-500/10 border border-zinc-500/10 p-3" />
              <div className="h-20 rounded-xl bg-zinc-500/10 border border-zinc-500/10 p-3" />
            </div>
          </div>

          <div className="h-10 rounded-lg bg-zinc-500/20 w-full" />
        </div>

        {/* Center Trigger when closed */}
        {!isOpen && (
          <div className="absolute inset-0 flex flex-col items-center justify-center gap-3 z-10">
            <div className="text-center space-y-1">
              <p className={`text-sm font-semibold ${titleColor}`}>Drawer is Closed</p>
              <p className={`text-xs ${subtitleColor}`}>Click below or use controls to open the {presentation === 'bottom' ? 'bottom sheet' : 'side drawer'}</p>
            </div>
            <button
              onClick={() => { setIsOpen(true); setDragOffset(0); }}
              className={`px-4 py-2 rounded-lg text-xs font-semibold shadow-lg transition-transform active:scale-95 cursor-pointer ${primaryBtn}`}
            >
              Open FrogDrawer
            </button>
          </div>
        )}

        {/* Modal Scrim Backdrop */}
        <div
          onClick={() => { setIsOpen(false); setDragOffset(0); }}
          style={{ opacity: scrimOpacity }}
          className={`absolute inset-0 bg-black/60 z-20 cursor-pointer ${
            isDragging ? 'transition-none' : 'transition-opacity duration-300'
          } ${isOpen ? 'pointer-events-auto' : 'pointer-events-none'}`}
        />

        {/* Drawer Component Container */}
        {presentation === 'bottom' ? (
          /* Bottom Sheet Presentation */
          <div
            style={{ transform: bottomTransform }}
            className={`absolute bottom-0 inset-x-0 mx-auto max-w-lg z-30 flex flex-col ${
              isDragging ? 'transition-none' : 'transition-transform duration-300 ease-out'
            } ${drawerContainer} border-t border-x rounded-t-2xl max-h-[85%] ${
              isOpen ? 'pointer-events-auto' : 'pointer-events-none'
            }`}
          >
            {/* Drag Handle (Interactive Drag Target) */}
            <div
              onPointerDown={onPointerDown}
              onPointerMove={onPointerMove}
              onPointerUp={onPointerUp}
              onPointerCancel={onPointerUp}
              className="pt-3 pb-2 flex justify-center cursor-grab active:cursor-grabbing touch-none select-none group"
              title="Drag down to dismiss"
            >
              <div className={`w-12 h-1.5 rounded-full transition-transform group-hover:scale-x-110 ${dragHandleBg}`} />
            </div>

            {/* Header Slot (Also draggable) */}
            {hasHeader && (
              <div
                onPointerDown={onPointerDown}
                onPointerMove={onPointerMove}
                onPointerUp={onPointerUp}
                onPointerCancel={onPointerUp}
                className={`px-5 py-3 flex items-center justify-between border-b cursor-grab active:cursor-grabbing touch-none select-none ${headerBorder}`}
              >
                <div>
                  <h3 className={`text-sm font-bold tracking-tight ${titleColor}`}>Quick Actions &amp; Settings</h3>
                  <p className={`text-[11px] ${subtitleColor}`}>Drag down handle or header to dismiss</p>
                </div>
                <button
                  onClick={(e) => { e.stopPropagation(); setIsOpen(false); setDragOffset(0); }}
                  className={`w-7 h-7 rounded-md flex items-center justify-center transition-colors cursor-pointer ${closeBtn}`}
                >
                  ✕
                </button>
              </div>
            )}

            {/* Content Slot */}
            <div className="p-5 space-y-3 overflow-y-auto text-xs">
              <div className={`p-3 rounded-xl border flex items-center justify-between ${cardContainer}`}>
                <div>
                  <div className={`font-semibold ${cardTitle}`}>Presentation Mode</div>
                  <div className={`text-[11px] ${cardSubtitle}`}>Current layout: Modal Bottom Sheet</div>
                </div>
                <span className={`px-2 py-0.5 rounded text-[10px] font-semibold border ${badgeStyle}`}>
                  Adaptive
                </span>
              </div>

              <div className={`p-3 rounded-xl border flex items-center justify-between ${cardContainer}`}>
                <div>
                  <div className={`font-semibold ${cardTitle}`}>Accessibility Pane Title</div>
                  <div className={`text-[11px] ${cardSubtitle}`}>TalkBack paneTitle semantics announced</div>
                </div>
                <span className={checkmarkColor}>✓</span>
              </div>
            </div>

            {/* Footer Slot */}
            {hasFooter && (
              <div className={`p-4 border-t flex items-center justify-end gap-2 ${footerStyle}`}>
                <button
                  onClick={() => { setIsOpen(false); setDragOffset(0); }}
                  className={`px-3 py-1.5 rounded-lg text-xs font-medium transition-colors cursor-pointer ${dismissBtn}`}
                >
                  Dismiss
                </button>
                <button
                  onClick={() => { setIsOpen(false); setDragOffset(0); }}
                  className={`px-4 py-1.5 rounded-lg text-xs font-semibold shadow-xs transition-colors cursor-pointer ${primaryBtn}`}
                >
                  Apply Settings
                </button>
              </div>
            )}
          </div>
        ) : (
          /* Side Drawer Presentation */
          <div
            style={{
              transform: presentation === 'side-end' ? sideEndTransform : sideStartTransform
            }}
            className={`absolute top-0 bottom-0 ${
              presentation === 'side-end' ? 'right-0 rounded-l-2xl border-l' : 'left-0 rounded-r-2xl border-r'
            } w-72 sm:w-80 z-30 flex flex-col ${
              isDragging ? 'transition-none' : 'transition-transform duration-300 ease-out'
            } ${drawerContainer} border-y ${
              isOpen ? 'pointer-events-auto' : 'pointer-events-none'
            }`}
          >
            {/* Header (Interactive Drag Target) */}
            {hasHeader && (
              <div
                onPointerDown={onPointerDown}
                onPointerMove={onPointerMove}
                onPointerUp={onPointerUp}
                onPointerCancel={onPointerUp}
                className={`px-5 py-4 flex items-center justify-between border-b cursor-grab active:cursor-grabbing touch-none select-none ${headerBorder}`}
              >
                <div>
                  <h3 className={`text-sm font-bold tracking-tight ${titleColor}`}>Navigation Drawer</h3>
                  <p className={`text-[11px] ${subtitleColor}`}>Drag {presentation === 'side-end' ? 'right' : 'left'} to dismiss</p>
                </div>
                <button
                  onClick={(e) => { e.stopPropagation(); setIsOpen(false); setDragOffset(0); }}
                  className={`w-7 h-7 rounded-md flex items-center justify-center transition-colors cursor-pointer ${closeBtn}`}
                >
                  ✕
                </button>
              </div>
            )}

            {/* Nav Destination items */}
            <div className="p-4 space-y-1 overflow-y-auto text-xs flex-1">
              {[
                { name: 'Components Laboratory', active: true, count: '2' },
                { name: 'Design Tokens', active: false },
                { name: 'Playground', active: false },
                { name: 'Architecture & Rules', active: false }
              ].map(item => (
                <div
                  key={item.name}
                  className={`px-3 py-2 rounded-lg flex items-center justify-between cursor-pointer transition-colors ${
                    item.active
                      ? isDark
                        ? 'bg-zinc-800 text-zinc-100 font-semibold'
                        : 'bg-zinc-200/80 text-zinc-900 font-semibold'
                      : isDark
                        ? 'text-zinc-400 hover:text-zinc-100 hover:bg-zinc-800/40'
                        : 'text-zinc-600 hover:text-zinc-900 hover:bg-zinc-100'
                  }`}
                >
                  <span>{item.name}</span>
                  {item.count && (
                    <span className={`px-1.5 py-0.5 rounded text-[10px] font-bold ${
                      isDark ? 'bg-zinc-700 text-zinc-200' : 'bg-zinc-300/80 text-zinc-800'
                    }`}>
                      {item.count}
                    </span>
                  )}
                </div>
              ))}
            </div>

            {/* Footer */}
            {hasFooter && (
              <div className={`p-4 border-t flex items-center justify-between text-xs ${footerStyle}`}>
                <span className={`text-[11px] font-mono ${subtitleColor}`}>FrogUI v0.1.0</span>
                <button
                  onClick={() => { setIsOpen(false); setDragOffset(0); }}
                  className={`px-3 py-1 rounded text-[11px] font-medium transition-colors cursor-pointer ${
                    isDark ? 'bg-zinc-800 hover:bg-zinc-700 text-zinc-200' : 'bg-zinc-200 hover:bg-zinc-300 text-zinc-800'
                  }`}
                >
                  Close
                </button>
              </div>
            )}
          </div>
        )}

        {/* Configuration Checkboxes */}
        <div className="absolute bottom-3 right-3 flex items-center gap-3 text-xs z-10 bg-zinc-900/90 text-zinc-200 backdrop-blur-xs px-2.5 py-1.5 rounded-lg border border-zinc-800 shadow-md">
          <CheckboxControl label="Header" checked={hasHeader} onChange={setHasHeader} dark={true} />
          <CheckboxControl label="Footer" checked={hasFooter} onChange={setHasFooter} dark={true} />
        </div>
      </div>
    </div>
  );
};

/* =========================================================================
   HELPER CONTROLS
   ========================================================================= */
const ThemeToggleControl: React.FC<{
  theme: 'light' | 'dark';
  onToggle: (theme: 'light' | 'dark') => void;
}> = ({ theme, onToggle }) => (
  <div className="flex items-center bg-zinc-200/70 dark:bg-zinc-800/80 p-0.5 rounded-lg">
    <button
      onClick={() => onToggle('light')}
      title="Light preview"
      className={`p-1 rounded-md transition-colors ${
        theme === 'light' ? 'bg-white text-zinc-900 shadow-xs' : 'text-zinc-500 hover:text-zinc-900'
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
      onClick={() => onToggle('dark')}
      title="Dark preview"
      className={`p-1 rounded-md transition-colors ${
        theme === 'dark' ? 'bg-zinc-700 text-zinc-100 shadow-xs' : 'text-zinc-400 hover:text-zinc-200'
      }`}
    >
      <svg className="w-3.5 h-3.5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
        <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z" />
      </svg>
    </button>
  </div>
);

const CheckboxControl: React.FC<{
  label: string;
  checked: boolean;
  onChange: (val: boolean) => void;
  dark?: boolean;
}> = ({ label, checked, onChange, dark = true }) => (
  <label className="flex items-center gap-1.5 cursor-pointer select-none group">
    <div
      className={`w-3.5 h-3.5 rounded flex items-center justify-center transition-all duration-150 border ${
        checked
          ? dark
            ? 'bg-zinc-100 border-zinc-100 text-zinc-950'
            : 'bg-zinc-900 border-zinc-900 text-white'
          : dark
            ? 'border-zinc-700 bg-zinc-900/70 group-hover:border-zinc-500'
            : 'border-zinc-300 bg-white group-hover:border-zinc-400'
      }`}
    >
      {checked && (
        <svg className="w-2 h-2 stroke-[3]" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <polyline points="20 6 9 17 4 12" />
        </svg>
      )}
    </div>
    <input
      type="checkbox"
      checked={checked}
      onChange={e => onChange(e.target.checked)}
      className="sr-only"
    />
    <span
      className={`text-[11px] font-medium transition-colors ${
        checked
          ? dark ? 'text-zinc-200' : 'text-zinc-800'
          : dark ? 'text-zinc-500 group-hover:text-zinc-400' : 'text-zinc-500 group-hover:text-zinc-700'
      }`}
    >
      {label}
    </span>
  </label>
);
