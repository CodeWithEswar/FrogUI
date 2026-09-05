import React, { useEffect, useRef, useState } from 'react';
import { Cancel01Icon, Search01Icon } from '@hugeicons/core-free-icons';
import { ThemeToggle } from '../ui/ThemeToggle';
import { AppLogo } from '../ui/AppLogo';
import { HugeIcon, HugeIconData } from '../ui/HugeIcon';
import { DocsNavigationTree } from '../navigation/DocsNavigationTree';
import { docsNavigation } from '../../navigation';

interface MobileDrawerProps {
  isOpen: boolean;
  onClose: () => void;
  currentPath: string;
  onNavigate: (path: string) => void;
  onOpenSearch: () => void;
}

export const MobileDrawer: React.FC<MobileDrawerProps> = ({ isOpen, onClose, currentPath, onNavigate, onOpenSearch }) => {
  const [isRendered, setIsRendered] = useState(false);
  const [isVisible, setIsVisible] = useState(false);
  const panelRef = useRef<HTMLDivElement>(null);
  const closeRef = useRef<HTMLButtonElement>(null);

  useEffect(() => {
    let timer: number;
    if (isOpen) {
      setIsRendered(true);
      document.body.style.overflow = 'hidden';
      timer = window.setTimeout(() => {
        setIsVisible(true);
        closeRef.current?.focus();
      }, 15);
    } else {
      setIsVisible(false);
      document.body.style.overflow = '';
      timer = window.setTimeout(() => setIsRendered(false), 200);
    }
    return () => {
      window.clearTimeout(timer);
      document.body.style.overflow = '';
    };
  }, [isOpen]);

  useEffect(() => {
    const handleKeyDown = (event: KeyboardEvent) => {
      if (!isOpen) return;
      if (event.key === 'Escape') {
        event.preventDefault();
        onClose();
        window.setTimeout(() => document.querySelector<HTMLButtonElement>('[aria-label="Open mobile navigation"]')?.focus(), 0);
        return;
      }
      if (event.key !== 'Tab' || !panelRef.current) return;
      const focusable = [...panelRef.current.querySelectorAll<HTMLElement>('a[href], button:not([disabled]), [tabindex]:not([tabindex="-1"])')];
      if (!focusable.length) return;
      const first = focusable[0];
      const last = focusable.at(-1)!;
      if (event.shiftKey && document.activeElement === first) { event.preventDefault(); last.focus(); }
      if (!event.shiftKey && document.activeElement === last) { event.preventDefault(); first.focus(); }
    };
    window.addEventListener('keydown', handleKeyDown);
    return () => window.removeEventListener('keydown', handleKeyDown);
  }, [isOpen, onClose]);

  if (!isRendered) return null;

  return (
    <div className="fixed inset-0 z-50 md:hidden flex">
      <button
        type="button"
        className={`fixed inset-0 bg-zinc-950/60 backdrop-blur-xs transition-opacity duration-200 ${isVisible ? 'opacity-100' : 'opacity-0'}`}
        onClick={onClose}
        aria-label="Close navigation"
        tabIndex={-1}
      />
      <div
        ref={panelRef}
        role="dialog"
        aria-modal="true"
        aria-label="Documentation navigation"
        className={`relative w-[86%] max-w-[19rem] h-full border-r border-[var(--frog-nav-border)] bg-[var(--frog-sidebar-background)] shadow-2xl z-10 transition-transform duration-200 ease-out flex flex-col ${isVisible ? 'translate-x-0' : '-translate-x-full'}`}
      >
        <div className="h-14 px-4 flex items-center justify-between border-b border-[var(--frog-nav-border)]">
          <div className="flex items-center gap-2.5">
            <AppLogo className="w-6 h-6" />
            <div>
              <p className="font-semibold text-sm text-[var(--frog-nav-foreground)]">FrogUI Docs</p>
              <p className="text-[10px] text-[var(--frog-nav-section)]">Compose component system</p>
            </div>
          </div>
          <button ref={closeRef} type="button" onClick={onClose} aria-label="Close menu" className="w-8 h-8 grid place-items-center rounded-md border border-[var(--frog-nav-border)] bg-[var(--frog-nav-hover)] text-[var(--frog-nav-muted)] hover:text-[var(--frog-nav-foreground)] focus-visible:outline-2 focus-visible:outline-[var(--frog-focus-ring)]">
            <HugeIcon icon={Cancel01Icon as unknown as HugeIconData} size={16} />
          </button>
        </div>

        <div className="px-3 pt-3">
          <button
            type="button"
            onClick={() => { onClose(); onOpenSearch(); }}
            className="w-full min-h-9 flex items-center gap-2 px-3 rounded-md border border-[var(--frog-nav-border)] bg-[var(--frog-nav-hover)] text-xs text-[var(--frog-nav-muted)] hover:text-[var(--frog-nav-foreground)] focus-visible:outline-2 focus-visible:outline-[var(--frog-focus-ring)]"
          >
            <HugeIcon icon={Search01Icon as unknown as HugeIconData} size={15} />
            <span className="flex-1 text-left">Search documentation</span>
            <kbd className="text-[9px] font-mono">⌘K</kbd>
          </button>
        </div>

        <div className="min-h-0 flex-1 overflow-y-auto px-3 py-4">
          <DocsNavigationTree sections={docsNavigation} currentPath={currentPath} onNavigate={onNavigate} onItemNavigate={onClose} />
        </div>

        <div className="px-4 py-3 border-t border-[var(--frog-nav-border)] flex items-center justify-between">
          <span className="text-[10px] uppercase tracking-wider text-[var(--frog-nav-section)]">Theme</span>
          <ThemeToggle />
        </div>
      </div>
    </div>
  );
};
