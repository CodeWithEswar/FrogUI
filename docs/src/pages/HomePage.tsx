import React from 'react';
import { catalog, release } from '../generated/catalog';
import { CodeBlock } from '../components/ui/CodeBlock';

interface HomePageProps {
  onNavigate: (path: string) => void;
}

export const HomePage: React.FC<HomePageProps> = ({ onNavigate }) => {
  return (
    <div className="space-y-16 max-w-4xl">
      {/* Hero Section */}
      <section className="space-y-6 pt-6">
        <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full border border-zinc-200 dark:border-zinc-800 bg-zinc-100/70 dark:bg-zinc-900/70 text-xs text-zinc-600 dark:text-zinc-400">
          <span className="w-2 h-2 rounded-full bg-emerald-500 animate-pulse" />
          <span>v{release.version} &middot; Open-Source Android UI Ecosystem</span>
        </div>

        <h1 className="text-4xl sm:text-5xl font-extrabold tracking-tight text-zinc-900 dark:text-zinc-50 leading-tight">
          Composable components for modern Android.
        </h1>

        <p className="text-lg text-zinc-600 dark:text-zinc-400 leading-relaxed max-w-2xl">
          FrogUI provides predictable, customizable, accessible, and ownership-friendly Jetpack Compose UI components. Strong defaults without trapping developers inside proprietary runtimes.
        </p>

        <div className="flex flex-wrap items-center gap-3 pt-2">
          <button
            onClick={() => onNavigate('/docs/introduction')}
            className="px-5 py-2.5 rounded-lg bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 font-medium text-sm hover:bg-zinc-800 dark:hover:bg-white transition-colors shadow-xs"
          >
            Get Started &rarr;
          </button>
          <button
            onClick={() => onNavigate('/components/button')}
            className="px-5 py-2.5 rounded-lg border border-zinc-300 dark:border-zinc-800 bg-white dark:bg-zinc-900 text-zinc-800 dark:text-zinc-200 font-medium text-sm hover:bg-zinc-100 dark:hover:bg-zinc-800 transition-colors"
          >
            Browse Components
          </button>
          <a
            href="https://github.com/CodeWithEswar/FrogUI"
            target="_blank"
            rel="noreferrer"
            className="px-5 py-2.5 rounded-lg text-zinc-600 dark:text-zinc-400 hover:text-zinc-900 dark:hover:text-zinc-100 font-medium text-sm transition-colors"
          >
            GitHub
          </a>
        </div>
      </section>

      {/* Quick Install Snippet */}
      <section className="space-y-4">
        <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
          Quick Setup
        </h2>
        <p className="text-sm text-zinc-600 dark:text-zinc-400">
          Add the components and theme to your Android project:
        </p>
        <CodeBlock
          language="kotlin"
          title="build.gradle.kts (:app)"
          code={`dependencies {
    // FrogUI Jetpack Compose Components
    implementation("io.github.codewitheswar.frogui:frogui-components:${release.version}")
    implementation("io.github.codewitheswar.frogui:frogui-theme:${release.version}")
}`}
        />
      </section>

      {/* Core Principles */}
      <section className="space-y-6">
        <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
          Engineering Principles
        </h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div className="p-5 rounded-xl border border-zinc-200 dark:border-zinc-800/80 bg-white dark:bg-zinc-900/40 space-y-2">
            <h3 className="font-semibold text-sm text-zinc-900 dark:text-zinc-100">
              Predictable Native Composition
            </h3>
            <p className="text-xs text-zinc-600 dark:text-zinc-400 leading-relaxed">
              No hidden persistence, no secret network calls, and no unexpected side effects. What you pass is what renders.
            </p>
          </div>

          <div className="p-5 rounded-xl border border-zinc-200 dark:border-zinc-800/80 bg-white dark:bg-zinc-900/40 space-y-2">
            <h3 className="font-semibold text-sm text-zinc-900 dark:text-zinc-100">
              Explicit State Hoisting
            </h3>
            <p className="text-xs text-zinc-600 dark:text-zinc-400 leading-relaxed">
              Consumers own application state (<code className="font-mono">checked</code>, <code className="font-mono">onClick</code>). Components manage tactical interactions and animations.
            </p>
          </div>

          <div className="p-5 rounded-xl border border-zinc-200 dark:border-zinc-800/80 bg-white dark:bg-zinc-900/40 space-y-2">
            <h3 className="font-semibold text-sm text-zinc-900 dark:text-zinc-100">
              Accessibility as a Contract
            </h3>
            <p className="text-xs text-zinc-600 dark:text-zinc-400 leading-relaxed">
              48dp touch targets, TalkBack roles, state announcements, font scaling (2x), and tested contrast are mandatory.
            </p>
          </div>

          <div className="p-5 rounded-xl border border-zinc-200 dark:border-zinc-800/80 bg-white dark:bg-zinc-900/40 space-y-2">
            <h3 className="font-semibold text-sm text-zinc-900 dark:text-zinc-100">
              Ownership-Friendly Architecture
            </h3>
            <p className="text-xs text-zinc-600 dark:text-zinc-400 leading-relaxed">
              Mix freely with standard Compose and custom layouts. We never replace Compose primitives with redundant wrappers.
            </p>
          </div>
        </div>
      </section>

      {/* Catalog Grid */}
      <section className="space-y-6">
        <div className="flex items-center justify-between">
          <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
            Components Catalog
          </h2>
          <span className="text-xs text-zinc-500">
            {catalog.length} component(s) registered
          </span>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          {catalog.map(component => (
            <button
              key={component.id}
              onClick={() => onNavigate(component.path)}
              className="w-full text-left p-5 rounded-xl border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/40 hover:border-zinc-400 dark:hover:border-zinc-700 transition-all hover:shadow-xs group"
            >
              <div className="flex items-center justify-between gap-2 mb-2">
                <span className="font-bold text-base text-zinc-900 dark:text-zinc-100 group-hover:underline">
                  {component.displayName}
                </span>
                <span className="text-[10px] uppercase font-semibold px-2 py-0.5 rounded bg-zinc-100 dark:bg-zinc-800 text-zinc-600 dark:text-zinc-400">
                  {component.status}
                </span>
              </div>
              <p className="text-xs text-zinc-500 dark:text-zinc-400 leading-relaxed">
                {component.description}
              </p>
            </button>
          ))}
        </div>
      </section>
    </div>
  );
};
