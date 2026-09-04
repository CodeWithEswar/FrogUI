import React from 'react';

interface NotFoundPageProps {
  onNavigate: (path: string) => void;
}

export const NotFoundPage: React.FC<NotFoundPageProps> = ({ onNavigate }) => {
  return (
    <div className="py-20 text-center space-y-4 max-w-md mx-auto">
      <div className="inline-flex items-center justify-center w-12 h-12 rounded-xl bg-[var(--frog-muted)] text-[var(--frog-muted-foreground)] font-mono font-bold text-lg">
        404
      </div>
      <h1 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)]">
        Page not found
      </h1>
      <p className="text-sm text-[var(--frog-muted-foreground)]">
        The documentation page you requested does not exist or has been relocated.
      </p>
      <div className="pt-4 flex flex-wrap items-center justify-center gap-3">
        <button
          onClick={() => onNavigate('/')}
          className="px-4 py-2 rounded-lg bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 text-xs font-medium hover:bg-zinc-800 dark:hover:bg-white transition-colors"
        >
          Documentation Home
        </button>
        <button
          onClick={() => onNavigate('/components/button')}
          className="px-4 py-2 rounded-lg border border-[var(--frog-border)] text-xs font-medium text-[var(--frog-foreground)] hover:bg-[var(--frog-muted)] transition-colors"
        >
          Browse Components
        </button>
        <button
          onClick={() => onNavigate('/foundation')}
          className="px-4 py-2 rounded-lg border border-[var(--frog-border)] text-xs font-medium text-[var(--frog-foreground)] hover:bg-[var(--frog-muted)] transition-colors"
        >
          Theme &amp; Foundation
        </button>
      </div>
    </div>
  );
};
