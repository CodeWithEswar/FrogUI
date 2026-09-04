import React, { useState } from 'react';

interface ShowcaseModalProps {
  isOpen: boolean;
  onClose: () => void;
  componentName: string;
  deepLinkRoute: string; // e.g. "components/button"
}

export const ShowcaseModal: React.FC<ShowcaseModalProps> = ({
  isOpen,
  onClose,
  componentName,
  deepLinkRoute
}) => {
  const [copiedLink, setCopiedLink] = useState(false);
  const [copiedAdb, setCopiedAdb] = useState(false);
  const [copiedGradle, setCopiedGradle] = useState(false);

  if (!isOpen) return null;

  const deepLinkUri = `frogui://${deepLinkRoute}`;
  const adbCommand = `adb shell am start -a android.intent.action.VIEW -d "${deepLinkUri}"`;
  const gradleCommand = `./gradlew :app:installDebug && adb shell am start -a android.intent.action.VIEW -d "${deepLinkUri}"`;

  const copyToClipboard = (text: string, setFn: (v: boolean) => void) => {
    navigator.clipboard.writeText(text);
    setFn(true);
    setTimeout(() => setFn(false), 2000);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
      {/* Backdrop */}
      <div
        className="fixed inset-0 bg-zinc-950/80 backdrop-blur-xs transition-opacity"
        onClick={onClose}
      />

      {/* Modal Surface - Monochrome Zinc */}
      <div className="relative w-full max-w-lg rounded-2xl border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] shadow-2xl overflow-hidden z-10 animate-in fade-in zoom-in-95 duration-150">
        {/* Header */}
        <div className="flex items-center justify-between px-6 py-4 border-b border-zinc-100 dark:border-zinc-800">
          <div className="flex items-center gap-3">
            <div className="w-8 h-8 rounded-lg bg-[var(--frog-muted)] text-[var(--frog-foreground)] border border-zinc-200 dark:border-zinc-700 flex items-center justify-center">
              <svg className="w-4 h-4" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <polygon points="5 3 19 12 5 21 5 3" />
              </svg>
            </div>
            <div>
              <h3 className="font-bold text-base text-[var(--frog-foreground)]">
                Open in Showcase
              </h3>
              <p className="text-xs text-[var(--frog-muted-foreground)]">
                Launch {componentName} on your device or emulator
              </p>
            </div>
          </div>
          <button
            onClick={onClose}
            className="p-1 rounded-md text-zinc-400 hover:text-zinc-700 dark:hover:text-zinc-200 transition-colors"
          >
            <svg className="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
              <line x1="18" y1="6" x2="6" y2="18" />
              <line x1="6" y1="6" x2="18" y2="18" />
            </svg>
          </button>
        </div>

        {/* Body */}
        <div className="p-6 space-y-5 text-xs text-[var(--frog-muted-foreground)]">
          {/* Direct Device Action - High Contrast Monochrome Primary */}
          <div className="space-y-2">
            <label className="font-semibold text-[var(--frog-foreground)]">
              Direct Native Deep Link
            </label>
            <div className="flex items-center gap-2">
              <a
                href={deepLinkUri}
                className="flex-1 px-4 py-2.5 rounded-lg bg-zinc-900 hover:bg-zinc-800 text-white dark:bg-zinc-100 dark:hover:bg-white dark:text-zinc-950 font-semibold text-center transition-colors flex items-center justify-center gap-2 shadow-xs"
              >
                <svg className="w-4 h-4" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                  <polygon points="5 3 19 12 5 21 5 3" />
                </svg>
                <span>Launch on Android Device</span>
              </a>
              <button
                onClick={() => copyToClipboard(deepLinkUri, setCopiedLink)}
                className="px-3.5 py-2.5 rounded-lg border border-[var(--frog-border)] bg-zinc-50 dark:bg-zinc-800 hover:bg-zinc-100 dark:hover:bg-zinc-700 text-[var(--frog-foreground)] font-medium transition-colors"
              >
                {copiedLink ? '✓ Copied' : 'Copy URI'}
              </button>
            </div>
            <p className="text-[11px] text-[var(--frog-muted-foreground)]">
              URI: <code className="font-mono text-[var(--frog-foreground)]">{deepLinkUri}</code>
            </p>
          </div>

          {/* ADB Terminal Command */}
          <div className="space-y-2 pt-2 border-t border-zinc-100 dark:border-zinc-800">
            <div className="flex items-center justify-between">
              <label className="font-semibold text-[var(--frog-foreground)]">
                Launch via ADB (Terminal)
              </label>
              <button
                onClick={() => copyToClipboard(adbCommand, setCopiedAdb)}
                className="text-[11px] font-medium text-[var(--frog-foreground)] hover:text-[var(--frog-foreground)] hover:underline"
              >
                {copiedAdb ? '✓ Copied' : 'Copy command'}
              </button>
            </div>
            <div className="p-3 rounded-lg bg-zinc-950 border border-zinc-800 font-mono text-[11px] text-zinc-300 overflow-x-auto">
              <code>{adbCommand}</code>
            </div>
          </div>

          {/* Build and run */}
          <div className="space-y-2 pt-2 border-t border-zinc-100 dark:border-zinc-800">
            <div className="flex items-center justify-between">
              <label className="font-semibold text-[var(--frog-foreground)]">
                Install &amp; Launch (Gradle)
              </label>
              <button
                onClick={() => copyToClipboard(gradleCommand, setCopiedGradle)}
                className="text-[11px] font-medium text-[var(--frog-foreground)] hover:text-[var(--frog-foreground)] hover:underline"
              >
                {copiedGradle ? '✓ Copied' : 'Copy command'}
              </button>
            </div>
            <div className="p-3 rounded-lg bg-zinc-950 border border-zinc-800 font-mono text-[11px] text-zinc-300 overflow-x-auto">
              <code>{gradleCommand}</code>
            </div>
          </div>
        </div>

        {/* Footer */}
        <div className="px-6 py-3.5 bg-zinc-50 dark:bg-zinc-900/60 border-t border-zinc-100 dark:border-zinc-800 flex items-center justify-between text-xs">
          <span className="text-zinc-500">Requires FrogUI Showcase app</span>
          <button
            onClick={onClose}
            className="px-3.5 py-1.5 rounded-lg border border-[var(--frog-border)] text-[var(--frog-foreground)] hover:bg-[var(--frog-muted)] transition-colors font-medium cursor-pointer"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  );
};
