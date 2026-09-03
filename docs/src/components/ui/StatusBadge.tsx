import React from 'react';

interface StatusBadgeProps {
  status: 'experimental' | 'stable' | 'deprecated' | string;
  size?: 'sm' | 'md';
  showLabel?: boolean;
}

export const StatusBadge: React.FC<StatusBadgeProps> = ({
  status,
  size = 'md',
  showLabel = false
}) => {
  const normalized = status.toLowerCase();

  const getStatusConfig = () => {
    switch (normalized) {
      case 'experimental':
        return {
          title: 'Status: Experimental',
          classes: 'bg-amber-500/10 text-amber-600 dark:text-amber-400 border-amber-500/20',
          icon: (
            <svg
              className={size === 'sm' ? 'w-3 h-3' : 'w-3.5 h-3.5'}
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
            >
              <path d="M10 2v7.31L4.41 18.2A2 2 0 0 0 6.11 21h11.78a2 2 0 0 0 1.7-2.8L14 9.31V2" />
              <path d="M8.5 2h7" />
              <path d="M14 9.3a6.5 6.5 0 0 0-4 0" />
            </svg>
          ),
          label: 'Experimental'
        };
      case 'stable':
        return {
          title: 'Status: Stable',
          classes: 'bg-zinc-100 dark:bg-zinc-800 text-zinc-800 dark:text-zinc-200 border-zinc-300 dark:border-zinc-700',
          icon: (
            <svg
              className={size === 'sm' ? 'w-3 h-3' : 'w-3.5 h-3.5'}
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
            >
              <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z" />
              <polyline points="9 12 11 14 15 10" />
            </svg>
          ),
          label: 'Stable'
        };
      case 'deprecated':
        return {
          title: 'Status: Deprecated',
          classes: 'bg-rose-500/10 text-rose-600 dark:text-rose-400 border-rose-500/20',
          icon: (
            <svg
              className={size === 'sm' ? 'w-3 h-3' : 'w-3.5 h-3.5'}
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
            >
              <path d="m21.73 18-8-14a2 2 0 0 0-3.48 0l-8 14A2 2 0 0 0 4 21h16a2 2 0 0 0 1.73-3Z" />
              <line x1="12" y1="9" x2="12" y2="13" />
              <line x1="12" y1="17" x2="12.01" y2="17" />
            </svg>
          ),
          label: 'Deprecated'
        };
      default:
        return {
          title: `Status: ${status}`,
          classes: 'bg-zinc-500/10 text-zinc-500 border-zinc-500/20',
          icon: (
            <svg
              className={size === 'sm' ? 'w-3 h-3' : 'w-3.5 h-3.5'}
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
            >
              <circle cx="12" cy="12" r="10" />
            </svg>
          ),
          label: status
        };
    }
  };

  const config = getStatusConfig();
  const paddingClass = size === 'sm' ? 'p-1 rounded' : 'p-1.5 rounded-lg';

  return (
    <span
      title={config.title}
      aria-label={config.title}
      className={`inline-flex items-center gap-1.5 border ${paddingClass} ${config.classes} transition-colors select-none`}
    >
      {config.icon}
      {showLabel && (
        <span className="text-[11px] font-semibold tracking-wider uppercase font-mono">
          {config.label}
        </span>
      )}
    </span>
  );
};
