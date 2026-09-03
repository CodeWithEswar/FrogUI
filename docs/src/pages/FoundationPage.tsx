import React from 'react';
import { CodeBlock } from '../components/ui/CodeBlock';
import { Callout } from '../components/ui/Callout';

export const FoundationPage: React.FC = () => {
  return (
    <article className="max-w-3xl space-y-12">
      <header className="space-y-3 pb-6 border-b border-zinc-200 dark:border-zinc-800">
        <h1 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-zinc-900 dark:text-zinc-50">
          Design Foundation
        </h1>
        <p className="text-base text-zinc-600 dark:text-zinc-400 leading-relaxed">
          The core design tokens powering FrogUI: monochrome Zinc color scale, typographic hierarchy, adaptive spacing, tactile shapes, and physics-based motion.
        </p>
      </header>

      {/* Colors Section */}
      <section className="space-y-4">
        <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
          Color Tokens
        </h2>
        <p className="text-sm text-zinc-600 dark:text-zinc-400">
          FrogUI uses a tailored 11-step Zinc scale (Zinc 950 to Zinc 50) paired with semantic status colors (Success, Warning, Error, Info).
        </p>

        {/* Color Palette Grid */}
        <div className="grid grid-cols-2 sm:grid-cols-4 gap-2.5 pt-2 text-xs">
          <div className="p-3 rounded-lg bg-zinc-950 text-white font-mono flex flex-col justify-between h-20 shadow-xs">
            <span>Zinc 950</span>
            <span className="text-[10px] text-zinc-400">#09090b</span>
          </div>
          <div className="p-3 rounded-lg bg-zinc-900 text-white font-mono flex flex-col justify-between h-20 shadow-xs">
            <span>Zinc 900</span>
            <span className="text-[10px] text-zinc-400">#18181b</span>
          </div>
          <div className="p-3 rounded-lg bg-zinc-800 text-white font-mono flex flex-col justify-between h-20 shadow-xs">
            <span>Zinc 800</span>
            <span className="text-[10px] text-zinc-400">#27272a</span>
          </div>
          <div className="p-3 rounded-lg bg-zinc-700 text-white font-mono flex flex-col justify-between h-20 shadow-xs">
            <span>Zinc 700</span>
            <span className="text-[10px] text-zinc-300">#3f3f46</span>
          </div>
          <div className="p-3 rounded-lg bg-zinc-500 text-white font-mono flex flex-col justify-between h-20 shadow-xs">
            <span>Zinc 500</span>
            <span className="text-[10px] text-zinc-200">#71717a</span>
          </div>
          <div className="p-3 rounded-lg bg-zinc-300 text-zinc-900 font-mono flex flex-col justify-between h-20 shadow-xs">
            <span>Zinc 300</span>
            <span className="text-[10px] text-zinc-600">#d4d4d8</span>
          </div>
          <div className="p-3 rounded-lg bg-zinc-100 text-zinc-900 font-mono flex flex-col justify-between h-20 shadow-xs border border-zinc-200">
            <span>Zinc 100</span>
            <span className="text-[10px] text-zinc-500">#f4f4f5</span>
          </div>
          <div className="p-3 rounded-lg bg-zinc-50 text-zinc-900 font-mono flex flex-col justify-between h-20 shadow-xs border border-zinc-200">
            <span>Zinc 50</span>
            <span className="text-[10px] text-zinc-500">#fafafa</span>
          </div>
        </div>

        <CodeBlock
          language="kotlin"
          title="Consuming Theme Colors in Compose"
          code={`// Access semantic tokens from LocalFrogColors
val surface = FrogTheme.colors.surface
val foreground = FrogTheme.colors.foreground
val border = FrogTheme.colors.border`}
        />
      </section>

      {/* Typography Section */}
      <section className="space-y-4">
        <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
          Typography Hierarchy
        </h2>
        <p className="text-sm text-zinc-600 dark:text-zinc-400">
          From high-impact display titles to dense technical monospace code styles:
        </p>

        <div className="space-y-3 p-5 rounded-xl border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/40 divide-y divide-zinc-100 dark:divide-zinc-800">
          <div className="pb-3">
            <span className="text-2xl font-bold tracking-tight text-zinc-900 dark:text-zinc-100 block">
              Display (32sp / Bold)
            </span>
            <span className="text-xs font-mono text-zinc-400">FrogTheme.typography.display</span>
          </div>
          <div className="py-3">
            <span className="text-xl font-bold text-zinc-900 dark:text-zinc-100 block">
              Title (24sp / Bold)
            </span>
            <span className="text-xs font-mono text-zinc-400">FrogTheme.typography.titleLarge</span>
          </div>
          <div className="py-3">
            <span className="text-base font-semibold text-zinc-900 dark:text-zinc-100 block">
              Headline (18sp / SemiBold)
            </span>
            <span className="text-xs font-mono text-zinc-400">FrogTheme.typography.headline</span>
          </div>
          <div className="py-3">
            <span className="text-sm text-zinc-700 dark:text-zinc-300 block">
              Body Regular (14sp / Normal)
            </span>
            <span className="text-xs font-mono text-zinc-400">FrogTheme.typography.bodyMedium</span>
          </div>
          <div className="pt-3">
            <span className="text-xs font-mono text-zinc-800 dark:text-zinc-200 block">
              Monospace Code (13sp / Medium)
            </span>
            <span className="text-xs font-mono text-zinc-400">FrogTheme.typography.code</span>
          </div>
        </div>
      </section>

      {/* Spacing & Shapes */}
      <section className="space-y-4">
        <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
          Spacing &amp; Shapes
        </h2>
        <p className="text-sm text-zinc-600 dark:text-zinc-400">
          Predictable 8-point geometric grid with micro-steps for compact mobile UIs:
        </p>

        <div className="grid grid-cols-2 sm:grid-cols-4 gap-3 text-xs font-mono">
          <div className="p-3 rounded-lg border border-zinc-200 dark:border-zinc-800">
            <div className="text-zinc-400">xxs</div>
            <div className="text-base font-bold text-zinc-900 dark:text-zinc-100">2dp</div>
          </div>
          <div className="p-3 rounded-lg border border-zinc-200 dark:border-zinc-800">
            <div className="text-zinc-400">xs</div>
            <div className="text-base font-bold text-zinc-900 dark:text-zinc-100">4dp</div>
          </div>
          <div className="p-3 rounded-lg border border-zinc-200 dark:border-zinc-800">
            <div className="text-zinc-400">sm</div>
            <div className="text-base font-bold text-zinc-900 dark:text-zinc-100">8dp</div>
          </div>
          <div className="p-3 rounded-lg border border-zinc-200 dark:border-zinc-800">
            <div className="text-zinc-400">md</div>
            <div className="text-base font-bold text-zinc-900 dark:text-zinc-100">16dp</div>
          </div>
        </div>

        <Callout type="tip" title="Touch Target Sizing">
          <p>
            Interactive components enforce a minimum 48dp &times; 48dp touch surface regardless of visual padding (<code className="font-mono text-xs">FrogSpacing.touchTargetMinimum</code>).
          </p>
        </Callout>
      </section>
    </article>
  );
};
