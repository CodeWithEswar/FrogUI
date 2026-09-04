import React, { useEffect, useState } from 'react';

export interface TocItem {
  id: string;
  title: string;
  level: number;
}

interface TableOfContentsProps {
  items: TocItem[];
}

export const TableOfContents: React.FC<TableOfContentsProps> = ({ items }) => {
  const [activeId, setActiveId] = useState<string>(() => items[0]?.id || '');

  useEffect(() => {
    if (items.length === 0) return;

    const observer = new IntersectionObserver(
      entries => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            setActiveId(entry.target.id);
          }
        });
      },
      { rootMargin: '-80px 0% -70% 0%' }
    );

    items.forEach(item => {
      const el = document.getElementById(item.id);
      if (el) observer.observe(el);
    });

    return () => observer.disconnect();
  }, [items]);

  if (items.length === 0) return null;

  return (
    <aside className="hidden xl:block fixed top-14 right-0 bottom-0 w-64 overflow-y-auto p-6 border-l border-zinc-200 dark:border-zinc-800/80 bg-zinc-50/90 dark:bg-zinc-950/90 backdrop-blur-sm text-xs z-30">
      <div className="space-y-3">
        <h4 className="font-semibold uppercase tracking-wider text-zinc-400 dark:text-zinc-500 text-[11px]">
          On this page
        </h4>
        <nav aria-label="Table of contents">
          <ul className="relative border-l border-[var(--frog-border)] space-y-1">
            {items.map(item => {
              const isActive = activeId === item.id;
              return (
                <li key={item.id} className="relative">
                  {isActive && (
                    <span
                      className="absolute -left-[1px] top-1 bottom-1 w-[2px] bg-zinc-900 dark:bg-zinc-100 rounded-full"
                      aria-hidden="true"
                    />
                  )}
                  <a
                    href={`#${item.id}`}
                    onClick={() => setActiveId(item.id)}
                    className={`block py-1 transition-colors ${
                      item.level === 3 ? 'pl-6 text-[11px]' : 'pl-4 text-xs'
                    } ${
                      isActive
                        ? 'text-[var(--frog-foreground)] font-semibold'
                        : 'text-[var(--frog-muted-foreground)] hover:text-[var(--frog-foreground)]'
                    }`}
                  >
                    {item.title}
                  </a>
                </li>
              );
            })}
          </ul>
        </nav>
      </div>
    </aside>
  );
};
