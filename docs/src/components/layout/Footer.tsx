import React from 'react';
import { release } from '../../generated/catalog';
import { AppLogo } from '../ui/AppLogo';

export const Footer: React.FC = () => {
  return (
    <footer className="mt-20 border-t border-[var(--frog-border)] py-8 text-xs text-[var(--frog-muted-foreground)]">
      <div className="w-full px-4 sm:px-6 lg:px-8 xl:pr-72 flex flex-col md:flex-row items-center justify-between gap-4 text-center md:text-left">
        {/* Brand & Ecosystem Meta */}
        <div className="flex flex-wrap items-center justify-center md:justify-start gap-x-2.5 gap-y-1.5">
          <div className="inline-flex items-center gap-2">
            <AppLogo className="w-4 h-4 shrink-0" />
            <span className="font-semibold text-[var(--frog-foreground)]">FrogUI</span>
          </div>
          <span className="text-[var(--frog-muted-foreground)] hidden sm:inline">&middot;</span>
          <span className="text-[var(--frog-muted-foreground)]">Open-source Jetpack Compose Ecosystem</span>
          <span className="text-[var(--frog-muted-foreground)] hidden sm:inline">&middot;</span>
          <span className="whitespace-nowrap font-mono text-[11px] px-1.5 py-0.5 rounded bg-[var(--frog-surface-elevated)] border border-[var(--frog-border)] text-[var(--frog-muted-foreground)]">
            v{release.version}
          </span>
        </div>

        {/* Links */}
        <div className="flex flex-wrap items-center justify-center gap-x-5 gap-y-2 text-xs">
          <a
            href="https://github.com/CodeWithEswar/FrogUI/blob/main/LICENSE"
            target="_blank"
            rel="noreferrer"
            className="hover:text-[var(--frog-foreground)] transition-colors py-0.5"
          >
            Apache 2.0
          </a>
          <a
            href="https://github.com/CodeWithEswar/FrogUI/blob/main/CONTRIBUTING.md"
            target="_blank"
            rel="noreferrer"
            className="hover:text-[var(--frog-foreground)] transition-colors py-0.5"
          >
            Contributing
          </a>
          <a
            href="https://github.com/CodeWithEswar/FrogUI/blob/main/SECURITY.md"
            target="_blank"
            rel="noreferrer"
            className="hover:text-[var(--frog-foreground)] transition-colors py-0.5"
          >
            Security
          </a>
          <a
            href="https://github.com/CodeWithEswar/FrogUI"
            target="_blank"
            rel="noreferrer"
            className="hover:text-[var(--frog-foreground)] transition-colors py-0.5"
          >
            GitHub
          </a>
        </div>
      </div>
    </footer>
  );
};
