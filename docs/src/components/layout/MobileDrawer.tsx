import React, { useEffect, useState } from 'react';
import { navigationSections } from '../../generated/routes';
import { ThemeToggle } from '../ui/ThemeToggle';
import { AppLogo } from '../ui/AppLogo';
import { HugeIcon, HugeIconData } from '../ui/HugeIcon';
import {
  BookOpen01Icon,
  Download01Icon,
  Rocket01Icon,
  Layers01Icon,
  PaletteIcon,
  TextIcon,
  GridViewIcon,
  AccessibilityIcon,
  CursorPointer01Icon,
  SidebarRight01Icon,
  SlidersHorizontalIcon,
  CpuIcon
} from '@hugeicons/core-free-icons';
import { StatusBadge } from '../ui/StatusBadge';

interface MobileDrawerProps {
  isOpen: boolean;
  onClose: () => void;
  currentPath: string;
  onNavigate: (path: string) => void;
  onOpenSearch: () => void;
}

const getNavIcon = (path: string): HugeIconData => {
  if (path.includes('introduction')) return BookOpen01Icon as unknown as HugeIconData;
  if (path.includes('installation')) return Download01Icon as unknown as HugeIconData;
  if (path.includes('quickstart')) return Rocket01Icon as unknown as HugeIconData;
  if (path.includes('technology')) return CpuIcon as unknown as HugeIconData;
  if (path.includes('colors')) return PaletteIcon as unknown as HugeIconData;
  if (path.includes('typography')) return TextIcon as unknown as HugeIconData;
  if (path.includes('spacing')) return GridViewIcon as unknown as HugeIconData;
  if (path.includes('accessibility')) return AccessibilityIcon as unknown as HugeIconData;
  if (path.includes('foundation')) return Layers01Icon as unknown as HugeIconData;
  if (path.includes('button')) return CursorPointer01Icon as unknown as HugeIconData;
  if (path.includes('drawer')) return SidebarRight01Icon as unknown as HugeIconData;
  return SlidersHorizontalIcon as unknown as HugeIconData;
};

export const MobileDrawer: React.FC<MobileDrawerProps> = ({
  isOpen,
  onClose,
  currentPath,
  onNavigate,
  onOpenSearch
}) => {
  const [isRendered, setIsRendered] = useState(false);
  const [isVisible, setIsVisible] = useState(false);

  // Buttery-smooth open and close transition lifecycle
  useEffect(() => {
    let timer: number;
    if (isOpen) {
      setIsRendered(true);
      document.body.style.overflow = 'hidden';
      // Trigger entrance animation on next tick
      timer = window.setTimeout(() => {
        setIsVisible(true);
      }, 15);
    } else {
      setIsVisible(false);
      document.body.style.overflow = '';
      // Wait for exit transition to complete before unmounting
      timer = window.setTimeout(() => {
        setIsRendered(false);
      }, 250);
    }
    return () => {
      clearTimeout(timer);
      document.body.style.overflow = '';
    };
  }, [isOpen]);

  // Close on Escape key
  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      if (e.key === 'Escape' && isOpen) {
        onClose();
      }
    };
    window.addEventListener('keydown', handleKeyDown);
    return () => window.removeEventListener('keydown', handleKeyDown);
  }, [isOpen, onClose]);

  if (!isRendered) return null;

  return (
    <div className="fixed inset-0 z-50 md:hidden flex">
      {/* Smooth fading backdrop */}
      <div
        className={`fixed inset-0 bg-zinc-950/60 backdrop-blur-xs transition-opacity duration-250 ease-out cursor-pointer ${
          isVisible ? 'opacity-100' : 'opacity-0'
        }`}
        onClick={onClose}
        aria-hidden="true"
      />

      {/* Smooth sliding sheet surface */}
      <div
        className={`relative w-4/5 max-w-xs bg-white dark:bg-zinc-900 h-full border-r border-zinc-200 dark:border-zinc-800 p-6 overflow-y-auto flex flex-col justify-between shadow-2xl z-10 transition-transform duration-250 ease-out ${
          isVisible ? 'translate-x-0' : '-translate-x-full'
        }`}
      >
        <div>
          {/* Top header */}
          <div className="flex items-center justify-between pb-4 border-b border-zinc-200 dark:border-zinc-800 mb-6">
            <div className="flex items-center gap-2.5">
              <AppLogo className="w-6 h-6" />
              <span className="font-bold text-lg text-zinc-900 dark:text-zinc-100">FrogUI</span>
            </div>
            <button
              onClick={onClose}
              aria-label="Close menu"
              className="p-1 rounded-md text-zinc-500 hover:text-zinc-900 dark:hover:text-zinc-100 cursor-pointer transition-colors"
            >
              <svg className="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <line x1="18" y1="6" x2="6" y2="18" />
                <line x1="6" y1="6" x2="18" y2="18" />
              </svg>
            </button>
          </div>

          {/* Search Trigger */}
          <button
            onClick={() => {
              onClose();
              onOpenSearch();
            }}
            className="w-full mb-6 flex items-center justify-between px-3 py-2 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-zinc-50 dark:bg-zinc-800/60 text-xs text-zinc-500 dark:text-zinc-400 cursor-pointer hover:border-zinc-300 dark:hover:border-zinc-700 transition-colors"
          >
            <span>Search docs...</span>
            <kbd className="px-1.5 py-0.5 text-[10px] font-mono bg-zinc-200/80 dark:bg-zinc-700 rounded">
              ⌘K
            </kbd>
          </button>

          {/* Navigation Sections */}
          <div className="space-y-6 text-sm">
            {navigationSections.map(section => (
              <div key={section.title} className="space-y-2">
                <h4 className="text-xs font-semibold uppercase tracking-wider text-zinc-400 dark:text-zinc-500 px-2">
                  {section.title}
                </h4>
                <ul className="space-y-1">
                  {section.items.map(item => {
                    const isActive = currentPath === item.path || currentPath.includes(item.path);
                    return (
                      <li key={item.path}>
                        <button
                          onClick={() => {
                            onNavigate(item.path);
                            onClose();
                          }}
                          className={`w-full text-left flex items-center justify-between px-3 py-2 rounded-lg transition-colors cursor-pointer ${
                            isActive
                              ? 'bg-zinc-100 dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100 font-semibold'
                              : 'text-zinc-600 dark:text-zinc-400 hover:bg-zinc-50 dark:hover:bg-zinc-800/40'
                          }`}
                        >
                          <div className="flex items-center gap-2.5 truncate">
                            <HugeIcon
                              icon={getNavIcon(item.path)}
                              className={`w-4 h-4 shrink-0 transition-colors ${
                                isActive
                                  ? 'text-zinc-900 dark:text-zinc-100'
                                  : 'text-zinc-400 dark:text-zinc-500'
                              }`}
                            />
                            <span className="truncate text-xs">{item.title}</span>
                          </div>
                          {item.badge && (
                            <StatusBadge status={item.badge} size="sm" />
                          )}
                        </button>
                      </li>
                    );
                  })}
                </ul>
              </div>
            ))}
          </div>
        </div>

        {/* Bottom footer drawer */}
        <div className="pt-6 border-t border-zinc-200 dark:border-zinc-800 flex items-center justify-between">
          <span className="text-xs text-zinc-500">Theme</span>
          <ThemeToggle />
        </div>
      </div>
    </div>
  );
};
