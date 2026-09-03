import React from 'react';
import { release } from '../../generated/catalog';
import { AppLogo } from '../ui/AppLogo';

export const Footer: React.FC = () => {
  return (
    <footer className="mt-20 border-t border-zinc-200 dark:border-zinc-800/80 py-8 text-xs text-zinc-500 dark:text-zinc-400">
      <div className="w-full px-4 sm:px-6 lg:px-8 xl:pr-72 flex flex-col sm:flex-row items-center justify-between gap-4">
        <div className="flex items-center gap-2">
          <AppLogo className="w-4 h-4" />
          <span className="font-semibold text-zinc-800 dark:text-zinc-200">FrogUI</span>
          <span>&middot;</span>
          <span>Open-source Jetpack Compose Ecosystem</span>
          <span>&middot;</span>
          <span className="font-mono">v{release.version}</span>
        </div>

        <div className="flex items-center space-x-5">
          <a
            href="https://github.com/CodeWithEswar/FrogUI/blob/main/LICENSE"
            target="_blank"
            rel="noreferrer"
            className="hover:text-zinc-900 dark:hover:text-zinc-100 transition-colors"
          >
            Apache 2.0
          </a>
          <a
            href="https://github.com/CodeWithEswar/FrogUI/blob/main/CONTRIBUTING.md"
            target="_blank"
            rel="noreferrer"
            className="hover:text-zinc-900 dark:hover:text-zinc-100 transition-colors"
          >
            Contributing
          </a>
          <a
            href="https://github.com/CodeWithEswar/FrogUI/blob/main/SECURITY.md"
            target="_blank"
            rel="noreferrer"
            className="hover:text-zinc-900 dark:hover:text-zinc-100 transition-colors"
          >
            Security
          </a>
          <a
            href="https://github.com/CodeWithEswar/FrogUI"
            target="_blank"
            rel="noreferrer"
            className="hover:text-zinc-900 dark:hover:text-zinc-100 transition-colors"
          >
            GitHub
          </a>
        </div>
      </div>
    </footer>
  );
};
