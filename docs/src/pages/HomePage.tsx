import React from 'react';
import { catalog, release } from '../generated/catalog';
import { CodeBlock } from '../components/ui/CodeBlock';
import { VercelRaycastHero } from '../components/ui/VercelRaycastHero';
import { StatusBadge } from '../components/ui/StatusBadge';
import { HugeIcon, HugeIconData } from '../components/ui/HugeIcon';
import {
  Rocket01Icon,
  ArrowRight01Icon,
  GridViewIcon
} from '@hugeicons/core-free-icons';

interface HomePageProps {
  onNavigate: (path: string) => void;
}

export const HomePage: React.FC<HomePageProps> = ({ onNavigate }) => {
  return (
    <div className="space-y-16 w-full">
      {/* Hero Section - Vercel Style: Top-Left Eyebrow, Left Heading, Bottom-Right Navigation, Immersive Canvas */}
      <section className="relative -mt-4 min-h-[500px] sm:min-h-[560px] p-6 sm:p-10 lg:p-12 overflow-hidden rounded-md bg-[#050507] border border-zinc-900 shadow-2xl flex flex-col justify-between">
        {/* Full-Bleed Interactive Raycasting Frog Canvas */}
        <VercelRaycastHero />

        {/* Top-Left: Small Heading & Ecosystem Status */}
        <div className="relative z-10 flex items-center justify-between w-full">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-md border border-zinc-800 bg-zinc-950/80 backdrop-blur-md text-xs text-zinc-400 shadow-2xs">
            <span className="w-2 h-2 rounded-full bg-emerald-500 animate-pulse" />
            <span>v{release.version} &middot; Open-Source Android UI Ecosystem</span>
          </div>
          <span className="text-xs text-zinc-500 font-mono hidden md:inline">
            Jetpack Compose Suite
          </span>
        </div>

        {/* Center-Left: Bold Vercel-Style Typography & Description */}
        <div className="relative z-10 my-auto max-w-xl space-y-4 py-8">
          <h1 className="text-4xl sm:text-5xl lg:text-6xl font-extrabold tracking-tight text-white leading-[1.06]">
            Composable<br />
            components for<br />
            modern Android.
          </h1>

          <p className="text-sm sm:text-base text-zinc-400 leading-relaxed max-w-md">
            FrogUI provides predictable, customizable, accessible, and ownership-friendly Jetpack Compose UI components. Strong defaults without trapping developers inside proprietary runtimes.
          </p>
        </div>

        {/* Bottom Bar: Bottom-Right Navigation Buttons */}
        <div className="relative z-10 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 pt-6 border-t border-zinc-900/80">
          <div className="text-xs text-zinc-500 font-mono hidden sm:block">
            Compose First &middot; &ge; 48dp Targets &middot; Zinc Scale
          </div>

          <div className="flex flex-wrap items-center gap-3 sm:ml-auto">
            <button
              onClick={() => onNavigate('/docs/introduction')}
              className="inline-flex items-center gap-2 px-5 py-2.5 rounded-md bg-white text-zinc-950 font-semibold text-sm hover:bg-zinc-200 transition-colors shadow-lg cursor-pointer"
            >
              <HugeIcon icon={Rocket01Icon as unknown as HugeIconData} className="w-4 h-4" />
              <span>Get Started</span>
              <HugeIcon icon={ArrowRight01Icon as unknown as HugeIconData} className="w-4 h-4 ml-0.5" />
            </button>
            <button
              onClick={() => onNavigate('/components/button')}
              className="inline-flex items-center gap-2 px-5 py-2.5 rounded-md border border-zinc-800 bg-zinc-950/80 backdrop-blur-md text-zinc-200 font-medium text-sm hover:bg-zinc-900 hover:border-zinc-700 transition-colors cursor-pointer"
            >
              <HugeIcon icon={GridViewIcon as unknown as HugeIconData} className="w-4 h-4 text-zinc-400" />
              <span>Browse Components</span>
            </button>
            <a
              href="https://github.com/CodeWithEswar/FrogUI"
              target="_blank"
              rel="noreferrer"
              className="inline-flex items-center gap-2 px-4 py-2.5 rounded-md border border-zinc-800/80 text-zinc-400 hover:text-white hover:bg-zinc-900 transition-colors text-sm font-medium"
            >
              <svg className="w-4 h-4" viewBox="0 0 24 24" fill="currentColor">
                <path fillRule="evenodd" clipRule="evenodd" d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.53 1.032 1.53 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z"/>
              </svg>
              <span>GitHub</span>
            </a>
          </div>
        </div>
      </section>

      {/* Quick Install Snippet - Full Width */}
      <section className="space-y-4">
        <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
          Quick Setup
        </h2>
        <p className="text-sm text-zinc-600 dark:text-zinc-400">
          Add the components and theme to your Android project:
        </p>
        <CodeBlock
          language="kotlin"
          title="build.gradle.kts"
          code={`dependencies {
    implementation("io.github.codewitheswar.frogui:frogui-components:${release.version}")
    implementation("io.github.codewitheswar.frogui:frogui-foundation:${release.version}")
}`}
        />
      </section>

      {/* Featured Catalog Section */}
      <section className="space-y-6">
        <div className="flex items-center justify-between">
          <div>
            <h2 className="text-2xl font-bold text-zinc-900 dark:text-zinc-100">
              Component Catalog
            </h2>
            <p className="text-sm text-zinc-500">
              Production-ready UI primitives verified in the native showcase app
            </p>
          </div>
          <button
            onClick={() => onNavigate('/components/button')}
            className="text-xs font-semibold text-zinc-900 dark:text-zinc-100 hover:underline inline-flex items-center gap-1 cursor-pointer"
          >
            <span>View all</span>
            <svg className="w-3.5 h-3.5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
              <path d="M5 12h14M12 5l7 7-7 7" />
            </svg>
          </button>
        </div>

        {/* Component Grid Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
          {catalog.map(component => (
            <div
              key={component.id}
              onClick={() => onNavigate(`/components/${component.id}`)}
              className="group cursor-pointer rounded-md border border-zinc-200 dark:border-zinc-800 p-5 bg-white dark:bg-zinc-900/60 hover:border-zinc-400 dark:hover:border-zinc-700 transition-all hover:shadow-md"
            >
              <div className="flex items-center justify-between mb-3">
                <span className="font-bold text-base text-zinc-900 dark:text-zinc-100 group-hover:text-zinc-600 dark:group-hover:text-zinc-300 transition-colors">
                  {component.displayName}
                </span>
                <StatusBadge status={component.status} size="sm" />
              </div>
              <p className="text-xs text-zinc-600 dark:text-zinc-400 leading-relaxed mb-4 line-clamp-2">
                {component.description}
              </p>
              <div className="flex items-center justify-between text-[11px] text-zinc-400 pt-3 border-t border-zinc-100 dark:border-zinc-800/80 font-mono">
                <span>{component.name}</span>
                <span className="text-zinc-500 group-hover:translate-x-0.5 transition-transform">&rarr;</span>
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* Philosophy / Architecture Banner */}
      <section className="rounded-md border border-zinc-200 dark:border-zinc-800 p-8 bg-zinc-50/50 dark:bg-zinc-900/40 space-y-4">
        <h3 className="text-lg font-bold text-zinc-900 dark:text-zinc-100">
          Architecture &amp; Design Philosophy
        </h3>
        <p className="text-sm text-zinc-600 dark:text-zinc-400 leading-relaxed max-w-3xl">
          FrogUI is developed with strict modular separation: <code>frogui-foundation</code> houses semantic design tokens (Zinc-tailored colors, standard elevation, typography scales, squircle and rounded shapes), while <code>frogui-components</code> consumes foundation tokens to deliver accessible, stateful, idiomatically composed UI primitives.
        </p>
        <div className="pt-2 flex flex-wrap gap-4 text-xs font-medium text-zinc-800 dark:text-zinc-200">
          <button
            onClick={() => onNavigate('/foundation/overview')}
            className="hover:underline text-zinc-900 dark:text-zinc-100 cursor-pointer"
          >
            Explore Foundation &rarr;
          </button>
          <button
            onClick={() => onNavigate('/foundation/accessibility')}
            className="hover:underline text-zinc-900 dark:text-zinc-100 cursor-pointer"
          >
            Accessibility Standards &rarr;
          </button>
        </div>
      </section>
    </div>
  );
};
