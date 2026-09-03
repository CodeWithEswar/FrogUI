import React from 'react';
import { ThemeToggle } from '../ui/ThemeToggle';
import { release } from '../../generated/catalog';

interface HeaderProps {
  onOpenSearch: () => void;
  onToggleMobileMenu: () => void;
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
    <header className="sticky top-0 z-40 w-full border-b border-zinc-200 dark:border-zinc-800/80 bg-white/80 dark:bg-zinc-950/80 backdrop-blur-md">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-14 flex items-center justify-between gap-4">
        {/* Left: Mobile hamburger & FrogUI Brand Logo */}
        <div className="flex items-center gap-3">
          <button
            onClick={onToggleMobileMenu}
            aria-label="Open mobile navigation"
            className="md:hidden p-2 rounded-lg text-zinc-600 dark:text-zinc-400 hover:bg-zinc-100 dark:hover:bg-zinc-900"
          >
            <svg className="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
              <line x1="3" y1="12" x2="21" y2="12" />
              <line x1="3" y1="6" x2="21" y2="6" />
              <line x1="3" y1="18" x2="21" y2="18" />
            </svg>
          </button>

          <a
            href="/FrogUI/"
            onClick={e => {
              e.preventDefault();
              onNavigate('/');
            }}
            className="flex items-center gap-2.5 group"
          >
            {/* FrogUI Monochrome Badge Mark */}
            <div className="w-7 h-7 rounded-lg bg-zinc-900 dark:bg-zinc-100 flex items-center justify-center text-white dark:text-zinc-900 shadow-xs transition-transform group-hover:scale-105">
              <svg className="w-4 h-4" viewBox="0 0 24 24" fill="currentColor">
                <path d="M12 3a9 9 0 0 0-9 9c0 4.97 4.03 9 9 9s9-4.03 9-9a9 9 0 0 0-9-9zm-3.5 6a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3zm7 0a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3zm-3.5 8c-2.33 0-4.31-1.46-5.11-3.5h10.22c-.8 2.04-2.78 3.5-5.11 3.5z"/>
              </svg>
            </div>
            <div className="flex items-baseline gap-1.5">
              <span className="font-bold text-base tracking-tight text-zinc-900 dark:text-zinc-100">
                FrogUI
              </span>
              <span className="text-[10px] font-mono text-zinc-400 dark:text-zinc-500">
                v{release.version}
              </span>
            </div>
          </a>

          {/* Desktop Nav Links */}
          <nav className="hidden md:flex items-center gap-1 ml-6 text-sm">
            <button
              onClick={() => onNavigate('/docs/introduction')}
              className={`px-3 py-1.5 rounded-md font-medium transition-colors ${
                currentPath.startsWith('/docs')
                  ? 'text-zinc-900 dark:text-zinc-100 bg-zinc-100 dark:bg-zinc-900'
                  : 'text-zinc-600 dark:text-zinc-400 hover:text-zinc-900 dark:hover:text-zinc-100'
              }`}
            >
              Docs
            </button>
            <button
              onClick={() => onNavigate('/components/button')}
              className={`px-3 py-1.5 rounded-md font-medium transition-colors ${
                currentPath.startsWith('/components')
                  ? 'text-zinc-900 dark:text-zinc-100 bg-zinc-100 dark:bg-zinc-900'
                  : 'text-zinc-600 dark:text-zinc-400 hover:text-zinc-900 dark:hover:text-zinc-100'
              }`}
            >
              Components
            </button>
            <button
              onClick={() => onNavigate('/foundation')}
              className={`px-3 py-1.5 rounded-md font-medium transition-colors ${
                currentPath.startsWith('/foundation')
                  ? 'text-zinc-900 dark:text-zinc-100 bg-zinc-100 dark:bg-zinc-900'
                  : 'text-zinc-600 dark:text-zinc-400 hover:text-zinc-900 dark:hover:text-zinc-100'
              }`}
            >
              Foundation
            </button>
          </nav>
        </div>

        {/* Right: Search, Theme Toggle, GitHub */}
        <div className="flex items-center gap-2 sm:gap-3">
          {/* Search Trigger Button */}
          <button
            onClick={onOpenSearch}
            className="flex items-center gap-2 px-2.5 py-1.5 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-zinc-50 dark:bg-zinc-900/60 text-xs text-zinc-500 dark:text-zinc-400 hover:border-zinc-300 dark:hover:border-zinc-700 transition-colors w-36 sm:w-56 justify-between"
          >
            <div className="flex items-center gap-2">
              <svg className="w-3.5 h-3.5 text-zinc-400" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <circle cx="11" cy="11" r="8" />
                <line x1="21" y1="21" x2="16.65" y2="16.65" />
              </svg>
              <span>Search docs...</span>
            </div>
            <kbd className="hidden sm:inline-block px-1.5 py-0.5 text-[10px] font-mono bg-zinc-200/60 dark:bg-zinc-800 rounded border border-zinc-300/60 dark:border-zinc-700">
              ⌘K
            </kbd>
          </button>

          {/* Theme switcher */}
          <ThemeToggle />

          {/* GitHub Link */}
          <a
            href="https://github.com/CodeWithEswar/FrogUI"
            target="_blank"
            rel="noreferrer"
            aria-label="FrogUI GitHub Repository"
            className="p-2 rounded-lg border border-zinc-200 dark:border-zinc-800 text-zinc-600 dark:text-zinc-400 hover:text-zinc-900 dark:hover:text-zinc-100 hover:bg-zinc-100 dark:hover:bg-zinc-900 transition-colors"
          >
            <svg className="w-4 h-4" viewBox="0 0 24 24" fill="currentColor">
              <path fillRule="evenodd" clipRule="evenodd" d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.53 1.032 1.53 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z"/>
            </svg>
          </a>
        </div>
      </div>
    </header>
  );
};
