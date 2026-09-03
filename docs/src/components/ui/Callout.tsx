import React from 'react';

export type CalloutType = 'note' | 'tip' | 'important' | 'warning';

interface CalloutProps {
  type?: CalloutType;
  title?: string;
  children: React.ReactNode;
}

export const Callout: React.FC<CalloutProps> = ({
  type = 'note',
  title,
  children
}) => {
  const styles = {
    note: {
      border: 'border-zinc-300 dark:border-zinc-700',
      bg: 'bg-zinc-100/60 dark:bg-zinc-900/60',
      titleColor: 'text-zinc-900 dark:text-zinc-100',
      icon: (
        <svg className="w-4 h-4 text-zinc-600 dark:text-zinc-400" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
          <circle cx="12" cy="12" r="10" />
          <line x1="12" y1="16" x2="12" y2="12" />
          <line x1="12" y1="8" x2="12.01" y2="8" />
        </svg>
      )
    },
    tip: {
      border: 'border-emerald-300/80 dark:border-emerald-800/80',
      bg: 'bg-emerald-50/40 dark:bg-emerald-950/20',
      titleColor: 'text-emerald-900 dark:text-emerald-300',
      icon: (
        <svg className="w-4 h-4 text-emerald-600 dark:text-emerald-400" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
          <path d="M12 2v4M12 18v4M4.93 4.93l2.83 2.83M16.24 16.24l2.83 2.83M2 12h4M18 12h4M4.93 19.07l2.83-2.83M16.24 7.76l2.83-2.83" />
        </svg>
      )
    },
    important: {
      border: 'border-zinc-400 dark:border-zinc-600',
      bg: 'bg-zinc-100 dark:bg-zinc-900/90',
      titleColor: 'text-zinc-900 dark:text-zinc-100',
      icon: (
        <svg className="w-4 h-4 text-zinc-900 dark:text-zinc-100" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
          <circle cx="12" cy="12" r="10" />
          <line x1="12" y1="8" x2="12" y2="12" />
          <line x1="12" y1="16" x2="12.01" y2="16" />
        </svg>
      )
    },
    warning: {
      border: 'border-amber-300/80 dark:border-amber-800/80',
      bg: 'bg-amber-50/40 dark:bg-amber-950/20',
      titleColor: 'text-amber-900 dark:text-amber-300',
      icon: (
        <svg className="w-4 h-4 text-amber-600 dark:text-amber-400" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
          <path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z" />
          <line x1="12" y1="9" x2="12" y2="13" />
          <line x1="12" y1="17" x2="12.01" y2="17" />
        </svg>
      )
    }
  }[type];

  const displayTitle = title || type.charAt(0).toUpperCase() + type.slice(1);

  return (
    <div className={`my-5 rounded-lg border ${styles.border} ${styles.bg} p-4 text-sm leading-relaxed`}>
      <div className={`flex items-center gap-2 font-medium mb-1.5 ${styles.titleColor}`}>
        {styles.icon}
        <span>{displayTitle}</span>
      </div>
      <div className="text-zinc-700 dark:text-zinc-300 pl-6">
        {children}
      </div>
    </div>
  );
};
