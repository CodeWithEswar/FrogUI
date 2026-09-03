import React from 'react';
import { navigationSections } from '../../generated/routes';
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

interface SidebarProps {
  currentPath: string;
  onNavigate: (path: string) => void;
}

// Map path to semantic official Hugeicon
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

export const Sidebar: React.FC<SidebarProps> = ({ currentPath, onNavigate }) => {
  return (
    <aside className="fixed top-14 left-0 bottom-0 w-64 shrink-0 hidden md:block overflow-y-auto py-6 px-4 border-r border-zinc-200 dark:border-zinc-800/80 bg-zinc-50/90 dark:bg-zinc-950/90 backdrop-blur-sm text-sm z-30">
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
                          ? 'bg-zinc-200/80 dark:bg-zinc-800/90 text-zinc-900 dark:text-zinc-100 font-semibold shadow-2xs'
                          : 'text-zinc-600 dark:text-zinc-400 hover:text-zinc-900 dark:hover:text-zinc-100 hover:bg-zinc-100 dark:hover:bg-zinc-900/60'
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
    </aside>
  );
};
