import React from 'react';
import { ComponentProperty } from '../../generated/catalog';

interface ApiTableProps {
  properties: ComponentProperty[];
}

export const ApiTable: React.FC<ApiTableProps> = ({ properties }) => {
  if (!properties || properties.length === 0) {
    return <p className="text-sm text-zinc-500 italic">No public properties documented.</p>;
  }

  return (
    <div className="my-6">
      {/* Desktop Table View */}
      <div className="hidden md:block overflow-x-auto rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 shadow-xs">
        <table className="w-full text-left text-sm border-collapse">
          <thead>
            <tr className="border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50/80 dark:bg-zinc-900/90 text-xs font-semibold text-zinc-600 dark:text-zinc-400">
              <th className="py-3 px-4 font-mono">Property</th>
              <th className="py-3 px-4 font-mono">Type</th>
              <th className="py-3 px-4 font-mono">Default</th>
              <th className="py-3 px-4">Description</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-zinc-200 dark:divide-zinc-800/60">
            {properties.map(prop => (
              <tr key={prop.name} id={prop.name} className="hover:bg-zinc-50/50 dark:hover:bg-zinc-800/30 transition-colors">
                <td className="py-3 px-4 align-top">
                  <a
                    href={`#${prop.name}`}
                    className="font-mono font-medium text-zinc-900 dark:text-zinc-100 hover:underline inline-flex items-center gap-1 group"
                  >
                    <span>{prop.name}</span>
                    <span className="opacity-0 group-hover:opacity-100 text-zinc-400 text-xs">#</span>
                  </a>
                </td>
                <td className="py-3 px-4 align-top font-mono text-xs text-zinc-600 dark:text-zinc-400">
                  <code className="bg-zinc-100 dark:bg-zinc-800 px-1.5 py-0.5 rounded text-zinc-800 dark:text-zinc-200">
                    {prop.type}
                  </code>
                </td>
                <td className="py-3 px-4 align-top font-mono text-xs text-zinc-500 dark:text-zinc-400">
                  <code className="text-[11px] bg-zinc-100/70 dark:bg-zinc-800/70 px-1 py-0.5 rounded">
                    {prop.defaultValue}
                  </code>
                </td>
                <td className="py-3 px-4 align-top text-zinc-600 dark:text-zinc-400 text-xs leading-relaxed">
                  {prop.description}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Mobile Stacked Card View */}
      <div className="md:hidden space-y-4">
        {properties.map(prop => (
          <div
            key={prop.name}
            id={prop.name}
            className="p-4 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 shadow-xs"
          >
            <div className="flex items-center justify-between gap-2 mb-2">
              <a
                href={`#${prop.name}`}
                className="font-mono font-semibold text-sm text-zinc-900 dark:text-zinc-100"
              >
                {prop.name}
              </a>
              <code className="text-xs bg-zinc-100 dark:bg-zinc-800 px-1.5 py-0.5 rounded text-zinc-700 dark:text-zinc-300 font-mono">
                {prop.type}
              </code>
            </div>

            <div className="text-xs text-zinc-500 dark:text-zinc-400 mb-2">
              <span className="font-medium text-zinc-600 dark:text-zinc-300">Default: </span>
              <code className="font-mono bg-zinc-100 dark:bg-zinc-800 px-1 py-0.5 rounded text-[11px]">
                {prop.defaultValue}
              </code>
            </div>

            <p className="text-xs text-zinc-600 dark:text-zinc-400 leading-relaxed">
              {prop.description}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};
