import React from 'react';
import { catalog, release } from '../generated/catalog';
import { VercelRaycastHero } from '../components/ui/VercelRaycastHero';
import { StatusBadge } from '../components/ui/StatusBadge';
import { HugeIcon, HugeIconData } from '../components/ui/HugeIcon';
import {
  Rocket01Icon,
  ArrowRight01Icon,
  GridViewIcon,
  CodeIcon,
  Tick02Icon,
  AccessibilityIcon,
  SmartPhone01Icon
} from '@hugeicons/core-free-icons';

interface HomePageProps {
  onNavigate: (path: string) => void;
}

export const HomePage: React.FC<HomePageProps> = ({ onNavigate }) => {
  return (
    <div className="space-y-20 w-full pb-16">
      {/* 1. Hero Section - Developer-Tool Density, Monochrome Aesthetics & Interactive Canvas */}
      <section className="relative -mt-4 min-h-[540px] sm:min-h-[580px] p-6 sm:p-10 lg:p-12 overflow-hidden rounded-xl bg-[#050507] border border-zinc-900 shadow-2xl flex flex-col justify-between">
        {/* Full-Bleed Interactive Raycasting Frog Canvas */}
        <VercelRaycastHero />

        {/* Top-Left: Eyebrow & Status */}
        <div className="relative z-10 flex flex-wrap items-center justify-between gap-3 w-full">
          <div className="inline-flex items-center gap-2 px-3 py-1 rounded-md border border-zinc-800 bg-zinc-950/80 backdrop-blur-md text-[11px] font-mono text-zinc-300 shadow-2xs">
            <span className="w-2 h-2 rounded-full bg-emerald-500 animate-pulse" />
            <span>OPEN-SOURCE &middot; KOTLIN &middot; JETPACK COMPOSE</span>
          </div>
          <span className="text-xs text-zinc-500 font-mono">
            v{release.version} &middot; {release.published ? 'Published' : 'Development Snapshot'}
          </span>
        </div>

        {/* Center: Split Headline + Code Preview on Desktop */}
        <div className="relative z-10 my-auto grid grid-cols-1 lg:grid-cols-12 gap-8 items-center py-6 sm:py-8">
          <div className="lg:col-span-7 space-y-4 max-w-xl">
            <h1 className="text-4xl sm:text-5xl lg:text-6xl font-extrabold tracking-tight text-white leading-[1.06]">
              Composable<br />
              components for<br />
              modern Android.
            </h1>
            <p className="text-sm sm:text-base text-zinc-400 leading-relaxed max-w-lg">
              FrogUI is an open-source Jetpack Compose component system for building polished, accessible, and adaptive Android interfaces. Start with carefully designed defaults, customize through semantic tokens and composable APIs, and keep ownership of the UI you ship.
            </p>

            <div className="flex flex-wrap items-center gap-3 pt-2">
              <button
                type="button"
                onClick={() => onNavigate('/docs/quick-start')}
                className="inline-flex items-center gap-2 px-5 py-2.5 rounded-lg bg-white text-zinc-950 font-semibold text-sm hover:bg-zinc-200 transition-colors shadow-lg cursor-pointer focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-white"
              >
                <HugeIcon icon={Rocket01Icon as unknown as HugeIconData} size={16} />
                <span>Get Started</span>
                <HugeIcon icon={ArrowRight01Icon as unknown as HugeIconData} size={14} className="ml-0.5" />
              </button>
              <button
                type="button"
                onClick={() => onNavigate('/components')}
                className="inline-flex items-center gap-2 px-5 py-2.5 rounded-lg border border-zinc-800 bg-zinc-950/80 backdrop-blur-md text-zinc-200 font-medium text-sm hover:bg-zinc-900 hover:border-zinc-700 transition-colors cursor-pointer focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-zinc-400"
              >
                <HugeIcon icon={GridViewIcon as unknown as HugeIconData} size={16} className="text-zinc-400" />
                <span>Browse Components</span>
              </button>
              <a
                href="https://github.com/CodeWithEswar/FrogUI"
                target="_blank"
                rel="noreferrer"
                className="inline-flex items-center gap-2 px-4 py-2.5 rounded-lg border border-zinc-800/80 text-zinc-400 hover:text-white hover:bg-zinc-900 transition-colors text-sm font-medium"
              >
                <svg className="w-4 h-4" viewBox="0 0 24 24" fill="currentColor">
                  <path fillRule="evenodd" clipRule="evenodd" d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.53 1.032 1.53 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z"/>
                </svg>
                <span>GitHub &nearr;</span>
              </a>
            </div>
          </div>

          {/* Desktop Real Kotlin Code Preview */}
          <div className="lg:col-span-5 w-full">
            <div className="rounded-xl border border-zinc-800/80 bg-zinc-950/90 backdrop-blur-md shadow-2xl overflow-hidden">
              <div className="flex items-center justify-between px-4 py-2.5 border-b border-zinc-800/80 bg-zinc-900/40 text-[11px] font-mono text-zinc-400">
                <span>WelcomeAction.kt</span>
                <span className="text-zinc-500">Kotlin &middot; Compose</span>
              </div>
              <div className="p-4 font-mono text-xs leading-relaxed text-zinc-300">
                <p className="text-purple-400">FrogTheme &#123;</p>
                <p className="pl-4 text-zinc-300">
                  <span className="text-blue-400">FrogButton</span>(
                </p>
                <p className="pl-8 text-zinc-400">
                  onClick = &#123; <span className="text-zinc-500">/* Handle action */</span> &#125;,
                </p>
                <p className="pl-8 text-zinc-400">
                  variant = FrogButtonVariant.<span className="text-amber-300">Primary</span>
                </p>
                <p className="pl-4 text-zinc-300">) &#123;</p>
                <p className="pl-8 text-emerald-300">Text(&quot;Continue&quot;)</p>
                <p className="pl-4 text-zinc-300">&#125;</p>
                <p className="text-purple-400">&#125;</p>
              </div>
            </div>
          </div>
        </div>

        {/* Bottom Bar: Engineering Metrics */}
        <div className="relative z-10 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 pt-6 border-t border-zinc-900/80 text-xs text-zinc-500 font-mono">
          <div>Compose-Native &middot; &ge; 48dp Targets &middot; Monochrome Zinc Palette</div>
          <div className="text-zinc-400">minSdk 24 &middot; Kotlin 2.2+ &middot; Compose BOM 2026.02</div>
        </div>
      </section>

      {/* 2. What is FrogUI? - Precise Definition */}
      <section className="space-y-4 max-w-4xl">
        <p className="text-[10px] font-semibold uppercase tracking-[0.16em] text-[var(--frog-muted-foreground)]">System Overview</p>
        <h2 className="text-2xl sm:text-3xl font-bold tracking-tight text-[var(--frog-foreground)]">What is FrogUI?</h2>
        <div className="space-y-3 text-sm sm:text-base leading-relaxed text-[var(--frog-muted-foreground)]">
          <p>
            FrogUI is a reusable Android UI system built natively with Kotlin and Jetpack Compose. It provides production-oriented components, design foundations, adaptive behavior, accessibility conventions, examples, and developer documentation while preserving the normal Compose programming model.
          </p>
          <p className="font-medium text-[var(--frog-foreground)]">
            FrogUI does not replace Jetpack Compose. It builds on Compose and gives applications a consistent set of reusable UI primitives and components.
          </p>
        </div>
      </section>

      {/* 3. Core Principles - Designed around Compose */}
      <section className="space-y-6">
        <div className="space-y-2">
          <p className="text-[10px] font-semibold uppercase tracking-[0.16em] text-[var(--frog-muted-foreground)]">Engineering Values</p>
          <h2 className="text-2xl sm:text-3xl font-bold tracking-tight text-[var(--frog-foreground)]">
            Designed around the way Compose works.
          </h2>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {[
            {
              icon: CodeIcon,
              title: 'Compose-Native',
              desc: 'Built directly as Kotlin composables rather than a WebView, JavaScript runtime, or legacy XML component wrapper.'
            },
            {
              icon: Tick02Icon,
              title: 'Ownership-Friendly',
              desc: 'Use FrogUI where it helps, combine it with standard Compose, and customize without surrendering control of your application UI.'
            },
            {
              icon: AccessibilityIcon,
              title: 'Accessible by Default',
              desc: 'Semantics, focus behavior, 48dp touch targets, font scaling, contrast, and interaction states are treated as mandatory requirements.'
            },
            {
              icon: SmartPhone01Icon,
              title: 'Adaptive Behavior',
              desc: 'Components and documentation account for compact phones, medium screens, and expanded tablet layouts where adaptation is meaningful.'
            }
          ].map(principle => (
            <div
              key={principle.title}
              className="p-5 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] space-y-2.5"
            >
              <div className="w-8 h-8 rounded-md border border-[var(--frog-border)] bg-[var(--frog-subtle-surface)] flex items-center justify-center text-[var(--frog-foreground)]">
                <HugeIcon icon={principle.icon as unknown as HugeIconData} size={16} />
              </div>
              <h3 className="font-semibold text-sm text-[var(--frog-foreground)]">{principle.title}</h3>
              <p className="text-xs leading-relaxed text-[var(--frog-muted-foreground)]">{principle.desc}</p>
            </div>
          ))}
        </div>
      </section>

      {/* 4. What You Get - Technical Capabilities */}
      <section className="space-y-6">
        <div className="space-y-2">
          <p className="text-[10px] font-semibold uppercase tracking-[0.16em] text-[var(--frog-muted-foreground)]">Technical Capabilities</p>
          <h2 className="text-2xl sm:text-3xl font-bold tracking-tight text-[var(--frog-foreground)]">What you get</h2>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
          {[
            { title: 'Components', desc: 'Reusable Compose controls with semantic variant APIs, explicit callbacks, and composable slots.' },
            { title: 'Foundation', desc: 'Nine shared token systems: colors, typography, spacing, shapes, elevation, motion, sizing, and adaptive rules.' },
            { title: 'Native Showcase', desc: 'Android showcase app for interactive on-device state testing, theme matrix toggles, and TalkBack verification.' },
            { title: 'Developer Documentation', desc: 'Comprehensive usage guides, compiled API signatures, accessibility evidence, and architecture contracts.' },
            { title: 'Customization', desc: 'Theme-level defaults through FrogTheme plus component-level escape hatches via Defaults and Modifier.' },
            { title: 'Rigorous Testing', desc: 'Built-in accessibility semantics, focus rings, and automated CI compatibility checks.' }
          ].map(cap => (
            <div key={cap.title} className="p-4 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface)]">
              <h3 className="font-semibold text-sm text-[var(--frog-foreground)] mb-1">{cap.title}</h3>
              <p className="text-xs leading-relaxed text-[var(--frog-muted-foreground)]">{cap.desc}</p>
            </div>
          ))}
        </div>
      </section>

      {/* 5. Compact Architecture Snapshot */}
      <section className="rounded-xl border border-[var(--frog-border)] p-6 sm:p-8 bg-[var(--frog-surface-elevated)] space-y-5">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <div>
            <h3 className="text-lg font-bold text-[var(--frog-foreground)]">Architecture Snapshot</h3>
            <p className="text-xs sm:text-sm text-[var(--frog-muted-foreground)] mt-1">
              Strict acyclic dependency direction guarantees that library primitives never import consumer or showcase code.
            </p>
          </div>
          <button
            type="button"
            onClick={() => onNavigate('/architecture')}
            className="shrink-0 text-xs font-semibold text-[var(--frog-foreground)] hover:underline inline-flex items-center gap-1 cursor-pointer"
          >
            <span>Architecture deep-dive</span>
            <HugeIcon icon={ArrowRight01Icon as unknown as HugeIconData} size={12} />
          </button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 pt-2">
          <div className="p-4 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-subtle-surface)] font-mono text-xs space-y-1.5 text-[var(--frog-foreground)]">
            <span className="text-[10px] font-semibold uppercase tracking-wider text-[var(--frog-muted-foreground)] font-sans">Runtime Dataflow</span>
            <p className="pt-1">FrogTheme</p>
            <p className="text-[var(--frog-muted-foreground)]">&darr; Foundation tokens (colors, type, spacing)</p>
            <p className="text-[var(--frog-muted-foreground)]">&darr; Component Defaults (FrogButtonDefaults)</p>
            <p className="text-[var(--frog-muted-foreground)]">&darr; FrogUI Components (FrogButton, FrogDrawer)</p>
            <p className="font-bold text-[var(--frog-foreground)]">&darr; Application UI</p>
          </div>

          <div className="p-4 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-subtle-surface)] font-mono text-xs space-y-1.5 text-[var(--frog-foreground)]">
            <span className="text-[10px] font-semibold uppercase tracking-wider text-[var(--frog-muted-foreground)] font-sans">Build &amp; Evidence Pipeline</span>
            <p className="pt-1">Kotlin Implementation</p>
            <p className="text-[var(--frog-muted-foreground)]">&darr; Registry Metadata (schema v2)</p>
            <p className="text-[var(--frog-muted-foreground)]">&darr; Binary API validation (apiCheck)</p>
            <div className="flex gap-4 pt-1 text-[var(--frog-muted-foreground)]">
              <span>&swarrow; Native Showcase</span>
              <span>&searrow; Web Documentation</span>
            </div>
          </div>
        </div>
      </section>

      {/* 6. Available Component Preview */}
      <section className="space-y-6">
        <div className="flex items-center justify-between">
          <div>
            <p className="text-[10px] font-semibold uppercase tracking-[0.16em] text-[var(--frog-muted-foreground)]">Component Catalog</p>
            <h2 className="text-2xl sm:text-3xl font-bold tracking-tight text-[var(--frog-foreground)]">Available components</h2>
            <p className="text-xs text-[var(--frog-muted-foreground)] mt-1">
              Production primitives verified across light/dark themes and adaptive window classes
            </p>
          </div>
          <button
            type="button"
            onClick={() => onNavigate('/components')}
            className="text-xs font-semibold text-[var(--frog-foreground)] hover:underline inline-flex items-center gap-1 cursor-pointer"
          >
            <span>Explore all ({catalog.length})</span>
            <HugeIcon icon={ArrowRight01Icon as unknown as HugeIconData} size={12} />
          </button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {catalog.map(component => (
            <button
              key={component.id}
              type="button"
              onClick={() => onNavigate(component.path)}
              className="group text-left p-5 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] hover:border-zinc-400 dark:hover:border-zinc-700 transition-all hover:shadow-xs cursor-pointer focus-visible:outline-2 focus-visible:outline-[var(--frog-focus-ring)]"
            >
              <div className="flex items-center justify-between mb-2.5">
                <span className="font-bold text-base text-[var(--frog-foreground)] group-hover:text-zinc-600 dark:group-hover:text-zinc-300 transition-colors">
                  {component.displayName}
                </span>
                <StatusBadge status={component.status} size="sm" />
              </div>
              <p className="text-xs text-[var(--frog-muted-foreground)] leading-relaxed mb-4 line-clamp-2">
                {component.description}
              </p>
              <div className="flex items-center justify-between text-[11px] text-[var(--frog-muted-foreground)] pt-3 border-t border-[var(--frog-border)] font-mono">
                <span>{component.name}</span>
                <span className="font-semibold text-[var(--frog-foreground)] group-hover:translate-x-0.5 transition-transform inline-flex items-center gap-0.5">
                  View docs &rarr;
                </span>
              </div>
            </button>
          ))}
        </div>
      </section>

      {/* 7. Start Building Onboarding Grid */}
      <section className="space-y-6">
        <div className="space-y-2">
          <p className="text-[10px] font-semibold uppercase tracking-[0.16em] text-[var(--frog-muted-foreground)]">Developer Journey</p>
          <h2 className="text-2xl sm:text-3xl font-bold tracking-tight text-[var(--frog-foreground)]">Start building with FrogUI</h2>
          <p className="text-xs sm:text-sm text-[var(--frog-muted-foreground)] max-w-2xl leading-relaxed">
            Follow the guided path from conceptual understanding to your first screen, or jump directly to token reference.
          </p>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {[
            {
              step: '01',
              title: 'Understand the system',
              desc: 'Learn the core philosophy, Compose relationships, and non-goals.',
              path: '/docs/introduction',
              action: 'Introduction'
            },
            {
              step: '02',
              title: 'Add FrogUI',
              desc: 'Review toolchain requirements, dependencies, and theme configuration.',
              path: '/docs/installation',
              action: 'Installation'
            },
            {
              step: '03',
              title: 'Build your first screen',
              desc: 'Use FrogTheme, FrogButton, state hoisting, and semantic variants.',
              path: '/docs/quick-start',
              action: 'Quick Start'
            },
            {
              step: '04',
              title: 'Explore design tokens',
              desc: 'Inspect semantic colors, typography scale, 12-step spacing, and elevation.',
              path: '/foundations',
              action: 'Foundation Overview'
            }
          ].map(item => (
            <button
              key={item.path}
              type="button"
              onClick={() => onNavigate(item.path)}
              className="group text-left p-5 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] hover:border-zinc-400 dark:hover:border-zinc-700 hover:shadow-xs transition-all cursor-pointer focus-visible:outline-2 focus-visible:outline-[var(--frog-focus-ring)] flex flex-col justify-between"
            >
              <div>
                <span className="text-[11px] font-mono font-semibold text-[var(--frog-muted-foreground)] block mb-2">{item.step}</span>
                <h3 className="text-sm font-semibold text-[var(--frog-foreground)] mb-1">{item.title}</h3>
                <p className="text-xs leading-relaxed text-[var(--frog-muted-foreground)]">{item.desc}</p>
              </div>
              <span className="block mt-4 pt-3 border-t border-[var(--frog-border)] text-xs font-semibold text-[var(--frog-foreground)] group-hover:translate-x-0.5 transition-transform">
                {item.action} &rarr;
              </span>
            </button>
          ))}
        </div>
      </section>

      {/* 8. Truthful Release Status Notice */}
      <section className="rounded-xl border border-[var(--frog-border)] p-6 bg-[var(--frog-surface)] space-y-3">
        <div className="flex items-center gap-2">
          <span className="px-2 py-0.5 rounded text-[10px] font-mono font-semibold bg-amber-500/10 text-amber-600 dark:text-amber-400 border border-amber-500/20">
            SNAPSHOT &middot; v{release.version}
          </span>
          <span className="text-xs font-semibold text-[var(--frog-foreground)]">Development Distribution</span>
        </div>
        <p className="text-xs sm:text-sm text-[var(--frog-muted-foreground)] leading-relaxed max-w-3xl">
          FrogUI is actively maintained as an open-source project. Version <code className="font-mono text-[var(--frog-foreground)]">{release.version}</code> is currently an unpublished development snapshot. You can test and contribute by cloning the repository and consuming the modules locally via composite builds.
        </p>
        <div className="pt-1 flex flex-wrap gap-4 text-xs font-medium text-[var(--frog-foreground)]">
          <button
            type="button"
            onClick={() => onNavigate('/docs/installation')}
            className="hover:underline cursor-pointer"
          >
            Installation details &rarr;
          </button>
          <a
            href="https://github.com/CodeWithEswar/FrogUI"
            target="_blank"
            rel="noreferrer"
            className="hover:underline"
          >
            GitHub repository &nearr;
          </a>
        </div>
      </section>
    </div>
  );
};
