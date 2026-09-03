import React, { useState, useEffect } from 'react';
import { Header } from './Header';
import { Sidebar } from './Sidebar';
import { MobileDrawer } from './MobileDrawer';
import { SearchModal } from '../ui/SearchModal';
import { Footer } from './Footer';

interface ShellProps {
  currentPath: string;
  onNavigate: (path: string) => void;
  children: React.ReactNode;
}

export const Shell: React.FC<ShellProps> = ({ currentPath, onNavigate, children }) => {
  const [isSearchOpen, setIsSearchOpen] = useState(false);
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  // Global Ctrl+K / Cmd+K listener
  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      if ((e.metaKey || e.ctrlKey) && e.key === 'k') {
        e.preventDefault();
        setIsSearchOpen(true);
      }
    };
    window.addEventListener('keydown', handleKeyDown);
    return () => window.removeEventListener('keydown', handleKeyDown);
  }, []);

  return (
    <div className="min-h-screen flex flex-col bg-zinc-50 dark:bg-zinc-950 text-zinc-900 dark:text-zinc-100">
      {/* Top Header */}
      <Header
        currentPath={currentPath}
        onNavigate={onNavigate}
        onOpenSearch={() => setIsSearchOpen(true)}
        onToggleMobileMenu={() => setIsMobileMenuOpen(prev => !prev)}
      />

      {/* Main Container - Full Width Layout */}
      <div className="flex-1 w-full px-4 sm:px-6 lg:px-8 xl:px-12 flex items-start">
        {/* Left Sidebar - Fixed sticky panel */}
        <Sidebar currentPath={currentPath} onNavigate={onNavigate} />

        {/* Page Content Container */}
        <main className="flex-1 min-w-0 py-8 md:px-8 lg:px-10">
          {children}
        </main>
      </div>

      {/* Footer */}
      <Footer />

      {/* Modals & Drawers */}
      <SearchModal
        isOpen={isSearchOpen}
        onClose={() => setIsSearchOpen(false)}
        onNavigate={onNavigate}
      />

      <MobileDrawer
        isOpen={isMobileMenuOpen}
        onClose={() => setIsMobileMenuOpen(false)}
        currentPath={currentPath}
        onNavigate={onNavigate}
        onOpenSearch={() => setIsSearchOpen(true)}
      />
    </div>
  );
};
