import React from 'react';

export interface PreviewCheckboxProps {
  label: string;
  checked: boolean;
  onChange: (checked: boolean) => void;
  isDark: boolean;
}

export const PreviewCheckbox: React.FC<PreviewCheckboxProps> = ({
  label,
  checked,
  onChange,
  isDark
}) => (
  <label className="flex items-center gap-1.5 cursor-pointer select-none group">
    <div
      className={`w-3.5 h-3.5 rounded flex items-center justify-center transition-all duration-150 border ${
        checked
          ? isDark
            ? 'bg-zinc-100 border-zinc-100 text-zinc-950'
            : 'bg-zinc-900 border-zinc-900 text-white'
          : isDark
            ? 'border-zinc-700 bg-zinc-900/70 group-hover:border-zinc-500'
            : 'border-zinc-300 bg-white group-hover:border-zinc-400'
      }`}
    >
      {checked && (
        <svg className="w-2 h-2 stroke-[3]" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <polyline points="20 6 9 17 4 12" />
        </svg>
      )}
    </div>
    <input
      type="checkbox"
      checked={checked}
      onChange={e => onChange(e.target.checked)}
      className="sr-only"
    />
    <span
      className={`text-[11px] font-medium transition-colors ${
        checked
          ? isDark ? 'text-zinc-200' : 'text-zinc-800'
          : isDark ? 'text-zinc-500 group-hover:text-zinc-400' : 'text-zinc-500 group-hover:text-zinc-700'
      }`}
    >
      {label}
    </span>
  </label>
);
