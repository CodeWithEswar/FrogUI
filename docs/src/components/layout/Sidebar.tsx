import React from 'react';
import { navigationSections } from '../../generated/routes';
import { HugeIcon, HugeIconData } from '../ui/HugeIcon';
import {
  BookOpen01Icon,
  Download01Icon,
  Rocket01Icon,
  Layers01Icon,
  Layers02Icon,
  PaletteIcon,
  TextIcon,
  GridViewIcon,
  Motion01Icon,
  RulerIcon,
  SmartPhone01Icon,
  AccessibilityIcon,
  CursorPointer01Icon,
  SidebarRight01Icon,
  ComponentIcon,
  CpuIcon
} from '@hugeicons/core-free-icons';
import { StatusBadge } from '../ui/StatusBadge';

interface SidebarProps {
  currentPath: string;
  onNavigate: (path: string) => void;
}

// Map path to semantic official Hugeicon
const getNavIcon = (path: string): HugeIconData => {
  // Getting Started
  if (path.includes('introduction')) return BookOpen01Icon as unknown as HugeIconData;
  if (path.includes('installation')) return Download01Icon as unknown as HugeIconData;
  if (path.includes('quick-start') || path.includes('quickstart')) return Rocket01Icon as unknown as HugeIconData;

  // Architecture
  if (path.includes('technology') || path.includes('architecture')) return CpuIcon as unknown as HugeIconData;

  // Foundation
  if (path.includes('colors')) return PaletteIcon as unknown as HugeIconData;
  if (path.includes('typography')) return TextIcon as unknown as HugeIconData;
  if (path.includes('spacing')) return GridViewIcon as unknown as HugeIconData;
  if (path.includes('elevation')) return Layers02Icon as unknown as HugeIconData;
  if (path.includes('motion')) return Motion01Icon as unknown as HugeIconData;
  if (path.includes('sizing')) return RulerIcon as unknown as HugeIconData;
  if (path.includes('adaptive')) return SmartPhone01Icon as unknown as HugeIconData;
  if (path.includes('accessibility')) return AccessibilityIcon as unknown as HugeIconData;
  if (path === '/foundation' || path.endsWith('/foundation')) return Layers01Icon as unknown as HugeIconData;

  // Components
  if (path.includes('button')) return CursorPointer01Icon as unknown as HugeIconData;
  if (path.includes('drawer')) return SidebarRight01Icon as unknown as HugeIconData;

  return ComponentIcon as unknown as HugeIconData;
};

export const Sidebar: React.FC<SidebarProps> = ({ currentPath, onNavigate }) => {
  return (
    <aside className="fixed top-14 left-0 bottom-0 w-64 shrink-0 hidden md:block overflow-y-auto py-6 px-4 border-r border-[var(--frog-border)] bg-[var(--frog-background)] text-sm z-30">
      <div className="space-y-7">
        {navigationSections.map(section => (
          <div key={section.title} className="space-y-2">
            <h4 className="text-[11px] font-semibold uppercase tracking-wider text-zinc-400 dark:text-zinc-500 px-3">
              {section.title}
            </h4>
            <ul className="space-y-1">
              {section.items.map(item => {
                const isActive =
                  currentPath === item.path ||
                  currentPath === `/FrogUI${item.path}` ||
                  (item.path === '/components/button' && currentPath.includes('/components/button')) ||
                  (item.path === '/components/drawer' && currentPath.includes('/components/drawer'));

                return (
                  <li key={item.path}>
                    <button
                      onClick={() => onNavigate(item.path)}
                      className={`w-full text-left flex items-center justify-between px-3 py-1.5 rounded-lg transition-colors cursor-pointer ${
                        isActive
                          ? 'bg-zinc-200/80 dark:bg-zinc-800/90 text-[var(--frog-foreground)] font-semibold shadow-2xs'
                          : 'text-[var(--frog-muted-foreground)] hover:text-[var(--frog-foreground)] hover:bg-zinc-100 dark:hover:bg-zinc-900/60'
                      }`}
                    >
                      <div className="flex items-center gap-2.5 truncate">
                        <HugeIcon
                          icon={getNavIcon(item.path)}
                          className={`w-4 h-4 shrink-0 transition-colors ${
                            isActive
                              ? 'text-[var(--frog-foreground)]'
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
    </aside>
  );
};
