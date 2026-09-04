import React, { useState, useRef } from 'react';
import { ComponentPreviewProps } from '../../types';
import { PreviewCheckbox } from '../../PreviewCheckbox';

export const DrawerPreview: React.FC<ComponentPreviewProps> = ({ isDark }) => {
  const [isOpen, setIsOpen] = useState(true);
  const [presentation, setPresentation] = useState<'bottom' | 'side-end' | 'side-start'>('bottom');
  const [hasHeader, setHasHeader] = useState(true);
  const [hasFooter, setHasFooter] = useState(true);

  // Pointer Gesture State for drag-to-dismiss
  const [dragOffset, setDragOffset] = useState(0);
  const [isDragging, setIsDragging] = useState(false);
  const startPos = useRef({ x: 0, y: 0 });

  /* Explicit theme-derived styling tokens for clean, deterministic rendering */
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
    <div className="w-full h-full flex flex-col justify-between">
      {/* Presentation Mode & Open/Close Bar */}
      <div className="flex items-center justify-between gap-3 px-3 sm:px-4 py-2.5 border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/90 dark:bg-zinc-900/90 overflow-x-auto scrollbar-none">
        <div className="flex items-center gap-1 shrink-0 text-xs">
          <span className="text-zinc-500 font-medium mr-1 text-[11px] uppercase tracking-wider hidden sm:inline">
            Mode:
          </span>
          <button
            onClick={() => { setPresentation('bottom'); setIsOpen(true); setDragOffset(0); }}
            className={`px-2.5 py-1 rounded-md text-xs font-medium transition-colors cursor-pointer ${
              presentation === 'bottom'
                ? 'bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 shadow-xs'
                : 'text-zinc-600 dark:text-zinc-400 hover:bg-zinc-200 dark:hover:bg-zinc-800'
            }`}
          >
            Bottom Sheet
          </button>
          <button
            onClick={() => { setPresentation('side-end'); setIsOpen(true); setDragOffset(0); }}
            className={`px-2.5 py-1 rounded-md text-xs font-medium transition-colors cursor-pointer ${
              presentation === 'side-end'
                ? 'bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 shadow-xs'
                : 'text-zinc-600 dark:text-zinc-400 hover:bg-zinc-200 dark:hover:bg-zinc-800'
            }`}
          >
            Side (End)
          </button>
          <button
            onClick={() => { setPresentation('side-start'); setIsOpen(true); setDragOffset(0); }}
            className={`px-2.5 py-1 rounded-md text-xs font-medium transition-colors cursor-pointer ${
              presentation === 'side-start'
                ? 'bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 shadow-xs'
                : 'text-zinc-600 dark:text-zinc-400 hover:bg-zinc-200 dark:hover:bg-zinc-800'
            }`}
          >
            Side (Start)
          </button>
        </div>

        <button
          onClick={() => { setIsOpen(!isOpen); setDragOffset(0); }}
          className={`px-2.5 py-1 rounded-md text-xs font-medium border transition-colors cursor-pointer ${
            isOpen
              ? 'border-zinc-400 dark:border-zinc-600 bg-zinc-200/70 dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100 font-medium'
              : 'border-zinc-300 dark:border-zinc-700 text-zinc-600 dark:text-zinc-400 hover:bg-zinc-100 dark:hover:bg-zinc-800'
          }`}
        >
          {isOpen ? 'Close Drawer' : 'Open Drawer'}
        </button>
      </div>

      {/* Interactive Device / Canvas Stage */}
      <div className="relative h-96 sm:h-[420px] w-full overflow-hidden select-none">
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
          className={`absolute inset-0 bg-black/[0.48] z-20 cursor-pointer ${
            isDragging ? 'transition-none' : 'transition-opacity duration-200'
          } ${isOpen ? 'pointer-events-auto' : 'pointer-events-none'}`}
        />

        {/* Drawer Component Container */}
        {presentation === 'bottom' ? (
          /* Bottom Sheet Presentation */
          <div
            style={{ transform: bottomTransform }}
            className={`absolute bottom-0 inset-x-0 mx-auto max-w-lg z-30 flex flex-col ${
              isDragging ? 'transition-none' : 'transition-transform duration-200 ease-out'
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
              <div className={`w-8 h-[3px] rounded-full transition-transform group-hover:scale-x-110 ${dragHandleBg}`} />
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
                  aria-label="Close drawer"
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
              isDragging ? 'transition-none' : 'transition-transform duration-200 ease-out'
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
                  aria-label="Close drawer"
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
                <span className={`text-[11px] font-mono ${subtitleColor}`}>FrogUI v0.1.0-SNAPSHOT</span>
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
        <div className="absolute bottom-3 right-3 flex items-center gap-3 text-xs z-10">
          <PreviewCheckbox
            label="Header"
            checked={hasHeader}
            onChange={setHasHeader}
            isDark={isDark}
          />
          <PreviewCheckbox
            label="Footer"
            checked={hasFooter}
            onChange={setHasFooter}
            isDark={isDark}
          />
        </div>
      </div>
    </div>
  );
};
