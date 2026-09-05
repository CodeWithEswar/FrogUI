import React from 'react';
import { DocsNavSection as DocsNavSectionType } from '../../navigation';

export interface DocsNavSectionProps {
  section: DocsNavSectionType;
  children: React.ReactNode;
}

export const DocsNavSection: React.FC<DocsNavSectionProps> = ({ section, children }) => {
  return (
    <section aria-labelledby={`${section.id}-label`} className="mb-4">
      <h2
        id={`${section.id}-label`}
        className="px-2.5 mb-1.5 text-[10px] font-semibold uppercase tracking-[0.14em] text-[var(--frog-nav-section)] select-none"
      >
        {section.label}
      </h2>
      <ul role="tree" className="space-y-0.5">
        {children}
      </ul>
    </section>
  );
};
