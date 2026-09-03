import React, { useState, useEffect } from 'react';
import { codeToHtml } from 'shiki';

interface CodeBlockProps {
  code: string;
  language?: string;
  title?: string;
}

const highlightCache = new Map<string, string>();

export const CodeBlock: React.FC<CodeBlockProps> = ({
  code,
  language = 'kotlin',
  title
}) => {
  const [copied, setCopied] = useState(false);
  const [highlightedHtml, setHighlightedHtml] = useState<string>(() => {
    const key = `${language}:${code}`;
    return highlightCache.get(key) || '';
  });

  useEffect(() => {
    const key = `${language}:${code}`;
    if (highlightCache.has(key)) {
      setHighlightedHtml(highlightCache.get(key)!);
      return;
    }

    let isMounted = true;
    codeToHtml(code.trim(), {
      lang: language,
      themes: {
        light: 'github-light',
        dark: 'github-dark'
      }
    }).then(html => {
      highlightCache.set(key, html);
      if (isMounted) {
        setHighlightedHtml(html);
      }
    }).catch(() => {
      // Fallback if language unsupported
      if (isMounted) {
        setHighlightedHtml('');
      }
    });

    return () => {
      isMounted = false;
    };
  }, [code, language]);

  const handleCopy = async () => {
    try {
      await navigator.clipboard.writeText(code.trim());
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    } catch {
      // Ignore clipboard error
    }
  };

  return (
    <div className="my-5 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/70 overflow-hidden shadow-xs">
      {/* Header bar */}
      <div className="flex items-center justify-between px-4 py-2 border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/80 dark:bg-zinc-900/90 text-xs text-zinc-500 dark:text-zinc-400">
        <div className="flex items-center space-x-2 font-mono">
          {title ? (
            <span className="font-medium text-zinc-700 dark:text-zinc-300">{title}</span>
          ) : (
            <span className="uppercase text-[10px] tracking-wider text-zinc-400 dark:text-zinc-500 font-semibold">
              {language}
            </span>
          )}
        </div>

        <button
          onClick={handleCopy}
          aria-label="Copy code"
          className="inline-flex items-center gap-1 px-2 py-1 rounded text-[11px] font-medium transition-colors hover:bg-zinc-200 dark:hover:bg-zinc-800 text-zinc-600 dark:text-zinc-400 hover:text-zinc-900 dark:hover:text-zinc-200"
        >
          {copied ? (
            <>
              <svg className="w-3.5 h-3.5 text-emerald-600 dark:text-emerald-400" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5">
                <polyline points="20 6 9 17 4 12" />
              </svg>
              <span className="text-emerald-600 dark:text-emerald-400">Copied!</span>
            </>
          ) : (
            <>
              <svg className="w-3.5 h-3.5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                <rect x="9" y="9" width="13" height="13" rx="2" ry="2" />
                <path d="M5 15H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h9a2 2 0 0 1 2 2v1" />
              </svg>
              <span>Copy</span>
            </>
          )}
        </button>
      </div>

      {/* Code body */}
      <div className="p-4 overflow-x-auto text-[13px] font-mono leading-relaxed">
        {highlightedHtml ? (
          <div
            className="[&_pre]:!bg-transparent [&_pre]:!p-0 [&_code]:font-mono"
            dangerouslySetInnerHTML={{ __html: highlightedHtml }}
          />
        ) : (
          <pre className="text-zinc-800 dark:text-zinc-200 whitespace-pre">
            <code>{code.trim()}</code>
          </pre>
        )}
      </div>
    </div>
  );
};
