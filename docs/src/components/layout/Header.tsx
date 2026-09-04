import React from 'react';
import { release } from '../../generated/catalog';
import { ThemeToggle } from '../ui/ThemeToggle';
import { AppLogo } from '../ui/AppLogo';

interface HeaderProps {
  onToggleMobileMenu: () => void;
  onOpenSearch: () => void;
  onNavigate: (path: string) => void;
  currentPath: string;
}

export const Header: React.FC<HeaderProps> = ({
  onOpenSearch,
  onToggleMobileMenu,
  onNavigate,
  currentPath
}) => {
  return (
    <header className="fixed top-0 inset-x-0 h-14 z-40 border-b border-[var(--frog-border)] bg-[var(--frog-background)]">
      <div className="w-full h-14 flex items-center">
        {/* Left Column: w-auto on mobile, w-64 on desktop to align with Sidebar */}
        <div className="w-auto md:w-64 shrink-0 px-3 sm:px-4 h-full flex items-center justify-between border-r border-transparent md:border-[var(--frog-border)]">
          <div className="flex items-center gap-2.5 sm:gap-3">
            {/* Mobile hamburger */}
            <button
              onClick={onToggleMobileMenu}
              aria-label="Open mobile navigation"
              className="md:hidden p-1.5 rounded-lg text-[var(--frog-muted-foreground)] hover:bg-[var(--frog-subtle-surface)] cursor-pointer"
            >
              <svg className="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <line x1="3" y1="12" x2="21" y2="12" />
                <line x1="3" y1="6" x2="21" y2="6" />
                <line x1="3" y1="18" x2="21" y2="18" />
              </svg>
            </button>

            {/* Brand Logo & Name */}
            <a
              href="/FrogUI/"
              onClick={e => {
                e.preventDefault();
                onNavigate('/');
              }}
              className="flex items-center gap-2 group cursor-pointer"
            >
              <AppLogo className="w-6 h-6 sm:w-7 sm:h-7" />
              <div className="flex items-baseline gap-1.5">
                <span className="font-bold text-base tracking-tight text-[var(--frog-foreground)]">
                  FrogUI
                </span>
                <span className="hidden sm:inline text-[10px] font-mono text-zinc-400 dark:text-zinc-500 whitespace-nowrap">
                  v{release.version}
                </span>
              </div>
            </a>
          </div>
        </div>

        {/* Center & Right Column */}
        <div className="flex-1 px-3 sm:px-6 lg:px-8 xl:px-10 h-full flex items-center justify-between gap-2 sm:gap-4 min-w-0">
          {/* Top Navigation Links */}
          <nav className="hidden md:flex items-center gap-1 text-sm">
            <button
              onClick={() => onNavigate('/docs/introduction')}
              className={`px-3 py-1.5 rounded-lg text-xs font-medium transition-colors cursor-pointer ${
                currentPath.startsWith('/docs')
                  ? 'text-[var(--frog-foreground)] bg-[var(--frog-subtle-surface)] font-semibold'
                  : 'text-[var(--frog-muted-foreground)] hover:text-[var(--frog-foreground)] hover:bg-zinc-100/60 dark:hover:bg-zinc-900/60'
              }`}
            >
              Docs
            </button>
            <button
              onClick={() => onNavigate('/components/button')}
              className={`px-3 py-1.5 rounded-lg text-xs font-medium transition-colors cursor-pointer ${
                currentPath.startsWith('/components')
                  ? 'text-[var(--frog-foreground)] bg-[var(--frog-subtle-surface)] font-semibold'
                  : 'text-[var(--frog-muted-foreground)] hover:text-[var(--frog-foreground)] hover:bg-zinc-100/60 dark:hover:bg-zinc-900/60'
              }`}
            >
              Components
            </button>
            <button
              onClick={() => onNavigate('/foundation')}
              className={`px-3 py-1.5 rounded-lg text-xs font-medium transition-colors cursor-pointer ${
                currentPath.startsWith('/foundation')
                  ? 'text-[var(--frog-foreground)] bg-[var(--frog-subtle-surface)] font-semibold'
                  : 'text-[var(--frog-muted-foreground)] hover:text-[var(--frog-foreground)] hover:bg-zinc-100/60 dark:hover:bg-zinc-900/60'
              }`}
            >
              Foundation
            </button>
          </nav>

          {/* Right Action Icons: Search, Theme Toggle, GitHub */}
          <div className="flex items-center gap-1.5 sm:gap-3 ml-auto">
            {/* Mobile Search Icon Button */}
            <button
              onClick={onOpenSearch}
              aria-label="Search docs"
              className="sm:hidden p-2 rounded-lg border border-[var(--frog-border)] bg-zinc-50 dark:bg-zinc-900/60 text-[var(--frog-muted-foreground)] hover:border-zinc-300 dark:hover:border-zinc-700 cursor-pointer"
            >
              <svg className="w-4 h-4 text-zinc-400" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <circle cx="11" cy="11" r="8" />
                <line x1="21" y1="21" x2="16.65" y2="16.65" />
              </svg>
            </button>

            {/* Desktop Search Bar with Cmd+K */}
            <button
              onClick={onOpenSearch}
              className="hidden sm:flex items-center gap-2 px-2.5 py-1.5 rounded-lg border border-[var(--frog-border)] bg-zinc-50 dark:bg-zinc-900/60 text-xs text-[var(--frog-muted-foreground)] hover:border-zinc-300 dark:hover:border-zinc-700 transition-colors w-40 lg:w-56 justify-between cursor-pointer"
            >
              <div className="flex items-center gap-2">
                <svg className="w-3.5 h-3.5 text-zinc-400" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                  <circle cx="11" cy="11" r="8" />
                  <line x1="21" y1="21" x2="16.65" y2="16.65" />
                </svg>
                <span>Search docs...</span>
              </div>
              <kbd className="hidden lg:inline-block px-1.5 py-0.5 text-[10px] font-mono bg-zinc-200/60 dark:bg-zinc-800 rounded border border-zinc-300/60 dark:border-zinc-700">
                ⌘K
              </kbd>
            </button>

            {/* Theme Toggle */}
            <ThemeToggle />

            {/* GitHub Repo */}
            <a
              href="https://github.com/CodeWithEswar/FrogUI"
              target="_blank"
              rel="noreferrer"
              aria-label="FrogUI GitHub Repository"
              className="p-2 rounded-lg border border-[var(--frog-border)] text-[var(--frog-muted-foreground)] hover:text-[var(--frog-foreground)] hover:bg-[var(--frog-subtle-surface)] transition-colors"
            >
              <svg className="w-4 h-4" viewBox="0 0 24 24" fill="currentColor">
                <path fillRule="evenodd" clipRule="evenodd" d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.53 1.032 1.53 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z"/>
              </svg>
            </a>
          </div>
        </div>
      </div>
    </header>
  );
};
