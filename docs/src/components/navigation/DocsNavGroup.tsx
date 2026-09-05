import React from 'react';
import { DocsIcon, DocsNavNode } from '../../navigation';
import { HugeIcon, HugeIconData } from '../ui/HugeIcon';
import {
  ArrowDown01Icon,
  BookOpen01Icon,
  ComponentIcon,
  CpuIcon,
  CursorPointer01Icon,
  Download01Icon,
  Layers01Icon,
  Rocket01Icon,
  SidebarRight01Icon
} from '@hugeicons/core-free-icons';

const iconMap: Record<DocsIcon, HugeIconData> = {
  book: BookOpen01Icon as unknown as HugeIconData,
  download: Download01Icon as unknown as HugeIconData,
  rocket: Rocket01Icon as unknown as HugeIconData,
  layers: Layers01Icon as unknown as HugeIconData,
  component: ComponentIcon as unknown as HugeIconData,
  action: CursorPointer01Icon as unknown as HugeIconData,
  overlay: SidebarRight01Icon as unknown as HugeIconData,
  architecture: CpuIcon as unknown as HugeIconData
};

export interface DocsNavGroupProps {
  node: DocsNavNode;
  depth: number;
  isOpen: boolean;
  isActiveGroup: boolean;
  onToggle: () => void;
  children: React.ReactNode;
}

export const DocsNavGroup: React.FC<DocsNavGroupProps> = ({
  node,
  depth,
  isOpen,
  isActiveGroup,
  onToggle,
  children
}) => {
  const regionId = `docs-nav-${node.id}`;
  const icon = node.icon ? iconMap[node.icon] : undefined;

  return (
    <li role="treeitem" aria-expanded={isOpen}>
      <button
        type="button"
        aria-expanded={isOpen}
        aria-controls={regionId}
        onClick={onToggle}
        className={`group w-full min-h-8 sm:min-h-9 flex items-center gap-2 rounded-md px-2.5 text-left text-xs transition-colors focus-visible:outline-2 focus-visible:outline-offset-1 focus-visible:outline-[var(--frog-focus-ring)] ${
          isActiveGroup
            ? 'text-[var(--frog-nav-foreground)] bg-[var(--frog-nav-group-active)] font-medium'
            : 'text-[var(--frog-nav-muted)] hover:text-[var(--frog-nav-foreground)] hover:bg-[var(--frog-nav-hover)] font-medium'
        }`}
        style={{ paddingLeft: `${8 + depth * 12}px` }}
      >
        {icon && (
          <HugeIcon
            icon={icon}
            size={14}
            className={`shrink-0 ${
              isActiveGroup ? 'text-[var(--frog-nav-foreground)]' : 'text-[var(--frog-nav-muted)]'
            }`}
          />
        )}
        <span className="min-w-0 flex-1 truncate">{node.title}</span>
        <HugeIcon
          icon={ArrowDown01Icon as unknown as HugeIconData}
          size={14}
          className={`shrink-0 text-[var(--frog-nav-muted)] transition-transform duration-200 ease-out ${
            isOpen ? 'rotate-0' : '-rotate-90'
          }`}
        />
      </button>
      {isOpen && (
        <ul
          id={regionId}
          role="group"
          className={`mt-0.5 space-y-0.5 ${
            depth === 0 ? 'border-l border-[var(--frog-nav-border)] ml-3.5 pl-1' : 'ml-2.5 pl-1'
          }`}
        >
          {children}
        </ul>
      )}
    </li>
  );
};
