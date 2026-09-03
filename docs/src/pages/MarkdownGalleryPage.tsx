import React from 'react';
import { CodeBlock } from '../components/ui/CodeBlock';
import { Callout } from '../components/ui/Callout';

export const MarkdownGalleryPage: React.FC = () => {
  return (
    <article className="max-w-3xl space-y-10">
      <header className="space-y-3 pb-6 border-b border-zinc-200 dark:border-zinc-800">
        <div className="inline-flex items-center px-2 py-0.5 rounded text-[10px] uppercase font-mono font-semibold bg-zinc-200 dark:bg-zinc-800 text-zinc-600 dark:text-zinc-400">
          Internal Dev Surface
        </div>
        <h1 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-zinc-900 dark:text-zinc-50">
          Markdown &amp; MDX Component Gallery
        </h1>
        <p className="text-base text-zinc-600 dark:text-zinc-400 leading-relaxed">
          Visual QA test bench for all custom documentation renderers, typography, callouts, and Shiki code blocks.
        </p>
      </header>

      {/* Headings */}
      <section className="space-y-4">
        <h2 id="heading-2" className="text-2xl font-bold tracking-tight text-zinc-900 dark:text-zinc-100 group flex items-center gap-2">
          <span>Heading Level 2</span>
          <a href="#heading-2" className="opacity-0 group-hover:opacity-100 text-zinc-400 text-base">#</a>
        </h2>
        <p className="text-sm text-zinc-600 dark:text-zinc-400 leading-relaxed">
          Standard paragraph demonstrating comfortable line length (65–80 characters) and high-legibility typography across both Light and Dark themes.
        </p>

        <h3 id="heading-3" className="text-lg font-semibold text-zinc-800 dark:text-zinc-200 group flex items-center gap-2">
          <span>Heading Level 3</span>
          <a href="#heading-3" className="opacity-0 group-hover:opacity-100 text-zinc-400 text-base">#</a>
        </h3>
        <p className="text-sm text-zinc-600 dark:text-zinc-400 leading-relaxed">
          Supporting subsection with <code className="font-mono text-xs bg-zinc-100 dark:bg-zinc-800 px-1 py-0.5 rounded text-zinc-800 dark:text-zinc-200">inline code</code> and <strong>bold text</strong>.
        </p>
      </section>

      {/* Callouts */}
      <section className="space-y-4">
        <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
          Callout Variations
        </h2>
        <Callout type="note" title="Architectural Note">
          <p>State is hoisted to callers. Components manage tactile interactions without hidden side effects.</p>
        </Callout>

        <Callout type="tip" title="Performance Tip">
          <p>Use remember for expensive lambda allocations or complex state machines.</p>
        </Callout>

        <Callout type="important" title="Accessibility Mandate">
          <p>Never bypass 48dp minimum accessible touch targets on phone and tablet viewports.</p>
        </Callout>

        <Callout type="warning" title="Experimental Warning">
          <p>This API is subject to change before the 1.0.0 stability review.</p>
        </Callout>
      </section>

      {/* Code Blocks */}
      <section className="space-y-4">
        <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
          Shiki Code Blocks
        </h2>

        <CodeBlock
          language="kotlin"
          title="FrogButtonExample.kt"
          code={`@Composable
fun SaveButton(loading: Boolean, onSave: () -> Unit) {
    FrogButton(
        variant = FrogButtonVariant.Primary,
        loading = loading,
        onClick = onSave
    ) {
        Text("Save Changes")
    }
}`}
        />

        <CodeBlock
          language="bash"
          title="Terminal"
          code={`./gradlew verifyProductContract check lintDebug`}
        />
      </section>

      {/* Tables */}
      <section className="space-y-4">
        <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
          Data Table
        </h2>
        <div className="overflow-x-auto rounded-lg border border-zinc-200 dark:border-zinc-800">
          <table className="w-full text-left text-sm border-collapse">
            <thead>
              <tr className="border-b border-zinc-200 dark:border-zinc-800 bg-zinc-50 dark:bg-zinc-900 text-xs font-semibold text-zinc-600 dark:text-zinc-400">
                <th className="py-2.5 px-4 font-mono">Module</th>
                <th className="py-2.5 px-4">Role</th>
                <th className="py-2.5 px-4">Status</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-zinc-200 dark:divide-zinc-800">
              <tr>
                <td className="py-2 px-4 font-mono text-xs">:frogui-foundation</td>
                <td className="py-2 px-4 text-xs">Tokens, palette, branding</td>
                <td className="py-2 px-4 text-xs font-semibold text-emerald-600">Stable</td>
              </tr>
              <tr>
                <td className="py-2 px-4 font-mono text-xs">:frogui-components</td>
                <td className="py-2 px-4 text-xs">Composable UI catalog</td>
                <td className="py-2 px-4 text-xs font-semibold text-amber-600">Experimental</td>
              </tr>
              <tr>
                <td className="py-2 px-4 font-mono text-xs">:frogui-registry</td>
                <td className="py-2 px-4 text-xs">Metadata contracts</td>
                <td className="py-2 px-4 text-xs font-semibold text-emerald-600">Stable</td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
    </article>
  );
};
