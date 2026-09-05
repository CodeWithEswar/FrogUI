import React, { useEffect, useMemo, useRef, useState } from 'react';
import {
  DocsNavNode,
  DocsNavSection as DocsNavSectionType,
  findNavigationTrail,
  normalizeDocsPath
} from '../../navigation';
import { DocsNavSection } from './DocsNavSection';
import { DocsNavGroup } from './DocsNavGroup';
import { DocsNavItem } from './DocsNavItem';

interface DocsNavigationTreeProps {
  sections: DocsNavSectionType[];
  currentPath: string;
  onNavigate: (path: string) => void;
  onItemNavigate?: () => void;
}

export const DocsNavigationTree: React.FC<DocsNavigationTreeProps> = ({
  sections,
  currentPath,
  onNavigate,
  onItemNavigate
}) => {
  const trail = useMemo(() => findNavigationTrail(sections, currentPath), [sections, currentPath]);
  const activeIds = useMemo(() => new Set(trail.map(node => node.id)), [trail]);

  // Expand parent groups in the current active trail by default
  const [expanded, setExpanded] = useState<Set<string>>(() => {
    return new Set(trail.filter(node => (node.children?.length ?? 0) > 0).map(node => node.id));
  });

  const activeRef = useRef<HTMLAnchorElement>(null);

  // Auto-expand ancestry when route changes, while preserving previously opened groups
  useEffect(() => {
    setExpanded(current => {
      const next = new Set(current);
      trail
        .filter(node => (node.children?.length ?? 0) > 0)
        .forEach(node => next.add(node.id));
      return next;
    });
  }, [trail]);

  // Keep active item in view smoothly
  useEffect(() => {
    const element = activeRef.current;
    if (!element) return;
    const reduced = window.matchMedia('(prefers-reduced-motion: reduce)').matches;
    element.scrollIntoView({ block: 'nearest', behavior: reduced ? 'auto' : 'smooth' });
  }, [currentPath]);

  const handleToggle = (nodeId: string) => {
    setExpanded(current => {
      const next = new Set(current);
      if (next.has(nodeId)) {
        next.delete(nodeId);
      } else {
        next.add(nodeId);
      }
      return next;
    });
  };

  const renderNode = (node: DocsNavNode, depth: number): React.ReactNode => {
    const hasChildren = Boolean(node.children?.length);
    if (hasChildren) {
      const isOpen = expanded.has(node.id);
      const isActiveGroup = activeIds.has(node.id);

      return (
        <DocsNavGroup
          key={node.id}
          node={node}
          depth={depth}
          isOpen={isOpen}
          isActiveGroup={isActiveGroup}
          onToggle={() => handleToggle(node.id)}
        >
          {node.children?.map(child => renderNode(child, depth + 1))}
        </DocsNavGroup>
      );
    }

    const isActive = node.href
      ? normalizeDocsPath(node.href) === normalizeDocsPath(currentPath)
      : false;

    return (
      <DocsNavItem
        key={node.id}
        node={node}
        depth={depth}
        active={isActive}
        onNavigate={onNavigate}
        onItemNavigate={onItemNavigate}
        activeRef={isActive ? activeRef : undefined}
      />
    );
  };

  return (
    <nav aria-label="Documentation" className="space-y-4">
      {sections.map(section => (
        <DocsNavSection key={section.id} section={section}>
          {section.nodes.map(node => renderNode(node, 0))}
        </DocsNavSection>
      ))}
    </nav>
  );
};
