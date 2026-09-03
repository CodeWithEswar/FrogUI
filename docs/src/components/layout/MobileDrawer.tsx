import React, { useEffect } from 'react';
import { navigationSections } from '../../generated/routes';
import { ThemeToggle } from '../ui/ThemeToggle';
import { AppLogo } from '../ui/AppLogo';

interface MobileDrawerProps {
  isOpen: boolean;
  onClose: () => void;
  currentPath: string;
  onNavigate: (path: string) => void;
  onOpenSearch: () => void;
}

export const MobileDrawer: React.FC<MobileDrawerProps> = ({
  isOpen,
  onClose,
  currentPath,
  onNavigate,
  onOpenSearch
}) => {
  useEffect(() => {
    if (isOpen) {
      document.body.style.overflow = 'hidden';
    } else {
      document.body.style.overflow = '';
    }
    return () => {
      document.body.style.overflow = '';
    };
  }, [isOpen]);

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 md:hidden flex">
      {/* Backdrop */}
      <div
        className="fixed inset-0 bg-zinc-950/60 backdrop-blur-xs transition-opacity"
        onClick={onClose}
      />

      {/* Drawer surface */}
      <div className="relative w-4/5 max-w-xs bg-white dark:bg-zinc-900 h-full border-r border-zinc-200 dark:border-zinc-800 p-6 overflow-y-auto flex flex-col justify-between shadow-2xl animate-in slide-in-from-left duration-200">
        <div>
          {/* Top header */}
          <div className="flex items-center justify-between pb-4 border-b border-zinc-200 dark:border-zinc-800 mb-6">
            <div className="flex items-center gap-2">
              <AppLogo className="w-6 h-6" />
              <span className="font-bold text-lg text-zinc-900 dark:text-zinc-100">FrogUI</span>
            </div>
            <button
              onClick={onClose}
              className="p-1 rounded-md text-zinc-500 hover:text-zinc-900 dark:hover:text-zinc-100"
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
            className="w-full mb-6 flex items-center justify-between px-3 py-2 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-zinc-50 dark:bg-zinc-800/60 text-xs text-zinc-500 dark:text-zinc-400"
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
                          className={`w-full text-left flex items-center justify-between px-3 py-2 rounded-md transition-colors ${
                            isActive
                              ? 'bg-zinc-100 dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100 font-medium'
                              : 'text-zinc-600 dark:text-zinc-400 hover:bg-zinc-50 dark:hover:bg-zinc-800/40'
                          }`}
                        >
                          <span>{item.title}</span>
                          {item.badge && (
                            <span className="text-[10px] font-medium px-1.5 py-0.5 rounded bg-amber-500/10 text-amber-600 dark:text-amber-400 border border-amber-500/20">
                              {item.badge}
                            </span>
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

        {/* Bottom controls */}
        <div className="pt-6 border-t border-zinc-200 dark:border-zinc-800 flex items-center justify-between">
          <span className="text-xs text-zinc-500">Theme</span>
          <ThemeToggle />
        </div>
      </div>
    </div>
  );
};
