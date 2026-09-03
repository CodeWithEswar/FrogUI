import React from 'react';

interface NotFoundPageProps {
  onNavigate: (path: string) => void;
}

export const NotFoundPage: React.FC<NotFoundPageProps> = ({ onNavigate }) => {
  return (
    <div className="py-20 text-center space-y-4 max-w-md mx-auto">
      <div className="inline-flex items-center justify-center w-12 h-12 rounded-xl bg-zinc-100 dark:bg-zinc-800 text-zinc-600 dark:text-zinc-400 font-mono font-bold text-lg">
        404
      </div>
      <h1 className="text-2xl font-bold tracking-tight text-zinc-900 dark:text-zinc-100">
        Page not found
      </h1>
      <p className="text-sm text-zinc-500 dark:text-zinc-400">
        The documentation page you requested does not exist or has been relocated.
      </p>
      <div className="pt-4 flex items-center justify-center gap-3">
        <button
          onClick={() => onNavigate('/')}
          className="px-4 py-2 rounded-lg bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 text-xs font-medium hover:bg-zinc-800 dark:hover:bg-white transition-colors"
        >
          Documentation Home
        </button>
        <button
          onClick={() => onNavigate('/components/button')}
          className="px-4 py-2 rounded-lg border border-zinc-200 dark:border-zinc-800 text-xs font-medium text-zinc-700 dark:text-zinc-300 hover:bg-zinc-100 dark:hover:bg-zinc-800 transition-colors"
        >
          Browse Components
        </button>
      </div>
    </div>
  );
};
