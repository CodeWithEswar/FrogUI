import React, { useEffect } from 'react';
import { ArrowLeft01Icon, ArrowRight01Icon } from '@hugeicons/core-free-icons';
import { DocumentationPageDefinition } from '../content/types';
import { TableOfContents } from '../components/layout/TableOfContents';
import { CodeBlock } from '../components/ui/CodeBlock';
import { Callout } from '../components/ui/Callout';
import { HugeIcon, HugeIconData } from '../components/ui/HugeIcon';
import { DocumentationVisual } from '../components/documentation/DocumentationVisual';
import { docsNavigation, findNavigationTrail, getAdjacentNavigation, DocsNavNode } from '../navigation';

interface DocumentationPageProps {
  page: DocumentationPageDefinition;
  onNavigate: (path: string) => void;
}

export const DocumentationPage: React.FC<DocumentationPageProps> = ({ page, onNavigate }) => {
  const trail = findNavigationTrail(docsNavigation, page.path);
  const adjacent = getAdjacentNavigation(docsNavigation, page.path);

  useEffect(() => {
    document.title = `${page.title} — FrogUI`;
    document.querySelector<HTMLMetaElement>('meta[name="description"]')?.setAttribute('content', page.description);
    if (window.location.hash) {
      window.requestAnimationFrame(() => document.getElementById(window.location.hash.slice(1))?.scrollIntoView());
    }
  }, [page]);

  return (
    <>
      <article className="max-w-4xl mx-auto xl:mr-64 xl:ml-auto pb-12">
        <nav aria-label="Breadcrumb" className="mb-6 flex flex-wrap items-center gap-1.5 text-[11px] text-[var(--frog-muted-foreground)]">
          <button type="button" onClick={() => onNavigate('/')} className="hover:text-[var(--frog-foreground)]">Docs</button>
          {trail.map((item: DocsNavNode, index: number) => <React.Fragment key={`${item.id}-${index}`}><span aria-hidden="true">/</span>{item.href && index < trail.length - 1 ? <button type="button" onClick={() => onNavigate(item.href!)} className="hover:text-[var(--frog-foreground)]">{item.title}</button> : <span aria-current={index === trail.length - 1 ? 'page' : undefined} className={index === trail.length - 1 ? 'text-[var(--frog-foreground)]' : ''}>{item.title}</span>}</React.Fragment>)}
        </nav>

        <header className="pb-8 border-b border-[var(--frog-border)]">
          <p className="text-[10px] font-semibold uppercase tracking-[0.16em] text-[var(--frog-muted-foreground)]">{page.eyebrow}</p>
          <h1 className="mt-2 text-3xl sm:text-4xl font-bold tracking-tight text-[var(--frog-foreground)]">{page.title}</h1>
          <p className="mt-3 max-w-2xl text-base sm:text-lg leading-relaxed text-[var(--frog-muted-foreground)]">{page.description}</p>
          <p className="mt-4 max-w-3xl text-sm leading-7 text-[var(--frog-muted-foreground)]">{page.intro}</p>
        </header>

        <div className="divide-y divide-[var(--frog-border)]">
          {page.sections.map(section => (
            <section key={section.id} id={section.id} className="py-8 space-y-4">
              <h2 className="text-xl font-semibold tracking-tight text-[var(--frog-foreground)]">{section.title}</h2>
              {section.paragraphs.map(paragraph => <p key={paragraph} className="text-sm leading-7 text-[var(--frog-muted-foreground)]">{paragraph}</p>)}
              {section.bullets && <ul className="space-y-2 text-sm leading-6 text-[var(--frog-muted-foreground)]">{section.bullets.map(item => <li key={item} className="flex gap-2"><span aria-hidden="true" className="text-[var(--frog-foreground)]">•</span><span>{item}</span></li>)}</ul>}
              {section.visual && <DocumentationVisual kind={section.visual} onNavigate={onNavigate} />}
              {section.code && <CodeBlock language={section.code.language} title={section.code.title} code={section.code.value} />}
              {section.callout && <Callout type={section.callout.type} title={section.callout.title}><p>{section.callout.value}</p></Callout>}
            </section>
          ))}
        </div>

        <nav aria-label="Page navigation" className="mt-8 pt-6 border-t border-[var(--frog-border)] grid gap-3 sm:grid-cols-2">
          {adjacent.previous ? <button type="button" onClick={() => onNavigate(adjacent.previous!.href)} className="group min-h-20 p-4 rounded-lg border border-[var(--frog-border)] text-left hover:border-[var(--frog-border-strong)] hover:bg-[var(--frog-subtle-surface)]"><span className="flex items-center gap-1 text-[10px] uppercase tracking-wider text-[var(--frog-muted-foreground)]"><HugeIcon icon={ArrowLeft01Icon as unknown as HugeIconData} size={12}/>Previous</span><span className="block mt-1 text-sm font-semibold">{adjacent.previous.title}</span></button> : <span />}
          {adjacent.next && <button type="button" onClick={() => onNavigate(adjacent.next!.href)} className="group min-h-20 p-4 rounded-lg border border-[var(--frog-border)] text-right hover:border-[var(--frog-border-strong)] hover:bg-[var(--frog-subtle-surface)]"><span className="flex items-center justify-end gap-1 text-[10px] uppercase tracking-wider text-[var(--frog-muted-foreground)]">Next<HugeIcon icon={ArrowRight01Icon as unknown as HugeIconData} size={12}/></span><span className="block mt-1 text-sm font-semibold">{adjacent.next.title}</span></button>}
        </nav>
      </article>
      <TableOfContents items={page.sections.map(section => ({ id: section.id, title: section.title, level: 2 }))} />
    </>
  );
};
