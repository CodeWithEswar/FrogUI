import React from 'react';
import { catalog, release } from '../generated/catalog';
import { CodeBlock } from '../components/ui/CodeBlock';
import { AppLogo } from '../components/ui/AppLogo';
import { VercelRaycastHero } from '../components/ui/VercelRaycastHero';
import { StatusBadge } from '../components/ui/StatusBadge';

interface HomePageProps {
  onNavigate: (path: string) => void;
}

export const HomePage: React.FC<HomePageProps> = ({ onNavigate }) => {
  return (
    <div className="space-y-16 w-full">
      {/* Hero Section with Vercel-Style Radiant Point Light & Dynamic Raycast Shadows */}
      <section className="relative -mt-4 pt-10 pb-14 lg:pt-16 lg:pb-20 px-6 sm:px-10 overflow-hidden rounded-3xl bg-[#050507] border border-zinc-900 shadow-2xl min-h-[460px] flex items-center">
        {/* Interactive / Autonomous Raymarch Canvas */}
        <VercelRaycastHero />

        <div className="relative z-10 w-full flex flex-col lg:flex-row items-start lg:items-center justify-between gap-12">
          {/* Left Column: Heading & CTA Actions */}
          <div className="space-y-6 max-w-xl">
            <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full border border-zinc-800 bg-zinc-950/80 backdrop-blur-md text-xs text-zinc-400 shadow-2xs">
              <span className="w-2 h-2 rounded-full bg-emerald-500 animate-pulse" />
              <span>v{release.version} &middot; Open-Source Android UI Ecosystem</span>
            </div>

            <h1 className="text-4xl sm:text-5xl lg:text-6xl font-extrabold tracking-tight text-white leading-[1.08]">
              Composable components for modern Android.
            </h1>

            <p className="text-base sm:text-lg text-zinc-400 leading-relaxed max-w-lg">
              FrogUI provides predictable, customizable, accessible, and ownership-friendly Jetpack Compose UI components. Strong defaults without trapping developers inside proprietary runtimes.
            </p>

            <div className="flex flex-wrap items-center gap-3 pt-2">
              <button
                onClick={() => onNavigate('/docs/introduction')}
                className="px-5 py-2.5 rounded-full bg-white text-zinc-950 font-semibold text-sm hover:bg-zinc-200 transition-colors shadow-lg cursor-pointer"
              >
                Get Started &rarr;
              </button>
              <button
                onClick={() => onNavigate('/components/button')}
                className="px-5 py-2.5 rounded-full border border-zinc-800 bg-zinc-950/80 backdrop-blur-md text-zinc-200 font-medium text-sm hover:bg-zinc-900 hover:border-zinc-700 transition-colors cursor-pointer"
              >
                Browse Components
              </button>
              <a
                href="https://github.com/CodeWithEswar/FrogUI"
                target="_blank"
                rel="noreferrer"
                className="px-4 py-2.5 rounded-full text-zinc-400 hover:text-white font-medium text-sm transition-colors"
              >
                GitHub
              </a>
            </div>
          </div>

          {/* Right Column: Values / Showcase Badge with Frosted Glass */}
          <div className="w-full lg:w-76 shrink-0 p-6 rounded-2xl border border-zinc-800/80 bg-zinc-950/70 backdrop-blur-xl shadow-2xl space-y-5">
            <div className="flex items-center gap-3.5">
              <AppLogo className="w-12 h-12" />
              <div>
                <div className="font-bold text-base text-white">FrogUI Native</div>
                <div className="text-xs text-zinc-400">Jetpack Compose Suite</div>
              </div>
            </div>

            <div className="space-y-2.5 pt-3 border-t border-zinc-800/80 text-xs text-zinc-400 font-mono">
              <div className="flex items-center justify-between">
                <span>Compose First</span>
                <span className="text-white font-bold">100%</span>
              </div>
              <div className="flex items-center justify-between">
                <span>Touch Targets</span>
                <span className="text-zinc-300">&ge; 48dp</span>
              </div>
              <div className="flex items-center justify-between">
                <span>Theme System</span>
                <span className="text-zinc-300">Zinc Scale</span>
              </div>
              <div className="flex items-center justify-between">
                <span>Module Coupling</span>
                <span className="text-white font-bold">Acyclic</span>
              </div>
            </div>
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
              className="group cursor-pointer rounded-xl border border-zinc-200 dark:border-zinc-800 p-5 bg-white dark:bg-zinc-900/60 hover:border-zinc-400 dark:hover:border-zinc-700 transition-all hover:shadow-md"
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
      <section className="rounded-2xl border border-zinc-200 dark:border-zinc-800 p-8 bg-zinc-50/50 dark:bg-zinc-900/40 space-y-4">
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
