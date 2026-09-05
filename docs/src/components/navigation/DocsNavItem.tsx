import React from 'react';
import { DocsIcon, DocsNavNode } from '../../navigation';
import { HugeIcon, HugeIconData } from '../ui/HugeIcon';
import {
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

export interface DocsNavItemProps {
  node: DocsNavNode;
  depth: number;
  active: boolean;
  onNavigate: (href: string) => void;
  onItemNavigate?: () => void;
  activeRef?: React.Ref<HTMLAnchorElement>;
}

export const DocsNavItem: React.FC<DocsNavItemProps> = ({
  node,
  depth,
  active,
  onNavigate,
  onItemNavigate,
  activeRef
}) => {
  const icon = node.icon ? iconMap[node.icon] : undefined;

  return (
    <li role="treeitem" aria-selected={active}>
      <a
        ref={active ? activeRef : undefined}
        href={`/FrogUI${node.href || ''}`}
        aria-current={active ? 'page' : undefined}
        onClick={event => {
          event.preventDefault();
          if (!node.href) return;
          onNavigate(node.href);
          onItemNavigate?.();
        }}
        className={`min-h-8 sm:min-h-9 flex items-center gap-2 rounded-md border px-2.5 text-xs transition-colors focus-visible:outline-2 focus-visible:outline-offset-1 focus-visible:outline-[var(--frog-focus-ring)] ${
          active
            ? 'border-[var(--frog-nav-active-border)] bg-[var(--frog-nav-active)] text-[var(--frog-nav-foreground)] font-semibold shadow-xs'
            : 'border-transparent text-[var(--frog-nav-muted)] hover:text-[var(--frog-nav-foreground)] hover:bg-[var(--frog-nav-hover)]'
        }`}
        style={{ paddingLeft: `${8 + depth * 12}px` }}
      >
        {icon && (
          <HugeIcon
            icon={icon}
            size={14}
            className={`shrink-0 ${
              active ? 'text-[var(--frog-nav-foreground)]' : 'text-[var(--frog-nav-muted)]'
            }`}
          />
        )}
        <span className="min-w-0 flex-1 truncate">{node.title}</span>
      </a>
    </li>
  );
};
