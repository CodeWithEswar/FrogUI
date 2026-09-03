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
  const [activeId, setActiveId] = useState<string>('');

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
    <div className="w-56 shrink-0 hidden xl:block pl-6 py-6 text-xs">
      <div className="sticky top-20 space-y-3">
        <h4 className="font-semibold uppercase tracking-wider text-zinc-400 dark:text-zinc-500 text-[11px]">
          On this page
        </h4>
        <ul className="space-y-2 border-l border-zinc-200 dark:border-zinc-800">
          {items.map(item => {
            const isActive = activeId === item.id;
            return (
              <li key={item.id} className={item.level === 3 ? 'pl-4' : 'pl-3'}>
                <a
                  href={`#${item.id}`}
                  className={`block transition-colors -ml-px border-l-2 py-0.5 ${
                    isActive
                      ? 'border-zinc-900 dark:border-zinc-100 text-zinc-900 dark:text-zinc-100 font-medium'
                      : 'border-transparent text-zinc-500 dark:text-zinc-400 hover:text-zinc-900 dark:hover:text-zinc-200'
                  }`}
                >
                  {item.title}
                </a>
              </li>
            );
          })}
        </ul>
      </div>
    </div>
  );
};
