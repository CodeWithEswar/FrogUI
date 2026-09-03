import React from 'react';
import { navigationSections } from '../../generated/routes';

interface SidebarProps {
  currentPath: string;
  onNavigate: (path: string) => void;
}

export const Sidebar: React.FC<SidebarProps> = ({ currentPath, onNavigate }) => {
  return (
    <aside className="w-64 shrink-0 hidden md:block sticky top-14 self-start h-[calc(100vh-3.5rem)] overflow-y-auto py-6 pr-6 border-r border-zinc-200 dark:border-zinc-800/80 text-sm">
      <div className="space-y-8">
        {navigationSections.map(section => (
          <div key={section.title} className="space-y-2">
            <h4 className="text-xs font-semibold uppercase tracking-wider text-zinc-400 dark:text-zinc-500 px-3">
              {section.title}
            </h4>
            <ul className="space-y-1">
              {section.items.map(item => {
                // Check if currentPath matches (accounting for /FrogUI base path or relative route)
                const isActive =
                  currentPath === item.path ||
                  currentPath === `/FrogUI${item.path}` ||
                  (item.path === '/components/button' && currentPath.includes('/components/button'));

                return (
                  <li key={item.path}>
                    <button
                      onClick={() => onNavigate(item.path)}
                      className={`w-full text-left flex items-center justify-between px-3 py-1.5 rounded-md transition-colors ${
                        isActive
                          ? 'bg-zinc-100 dark:bg-zinc-800/90 text-zinc-900 dark:text-zinc-100 font-medium'
                          : 'text-zinc-600 dark:text-zinc-400 hover:text-zinc-900 dark:hover:text-zinc-100 hover:bg-zinc-50 dark:hover:bg-zinc-900/50'
                      }`}
                    >
                      <span className="truncate">{item.title}</span>
                      {item.badge && (
                        <span className="text-[10px] font-medium px-1.5 py-0.2 rounded bg-amber-500/10 text-amber-600 dark:text-amber-400 border border-amber-500/20">
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
    </aside>
  );
};
