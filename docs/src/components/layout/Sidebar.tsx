import React from 'react';
import { DocsNavigationTree } from '../navigation/DocsNavigationTree';
import { docsNavigation } from '../../navigation';

interface SidebarProps {
  currentPath: string;
  onNavigate: (path: string) => void;
}

export const Sidebar: React.FC<SidebarProps> = ({ currentPath, onNavigate }) => (
  <aside className="fixed top-14 left-0 bottom-0 w-60 shrink-0 hidden md:flex flex-col border-r border-[var(--frog-nav-border)] bg-[var(--frog-sidebar-background)] z-30">
    <div className="px-4 pt-5 pb-3">
      <p className="text-xs font-semibold text-[var(--frog-nav-foreground)]">Documentation</p>
      <p className="mt-0.5 text-[10px] text-[var(--frog-nav-section)]">Android · Compose · Kotlin</p>
    </div>
    <div className="min-h-0 flex-1 overflow-y-auto px-3 pb-6">
      <DocsNavigationTree sections={docsNavigation} currentPath={currentPath} onNavigate={onNavigate} />
    </div>
  </aside>
);
