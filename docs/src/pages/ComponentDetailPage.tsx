import React, { useState } from 'react';
import { ComponentDocPage, release } from '../generated/catalog';
import { ComponentPreview } from '../components/ui/ComponentPreview';
import { CodeBlock } from '../components/ui/CodeBlock';
import { ApiTable } from '../components/ui/ApiTable';
import { Callout } from '../components/ui/Callout';
import { TableOfContents, TocItem } from '../components/layout/TableOfContents';
import { ShowcaseModal } from '../components/ui/ShowcaseModal';
import { StatusBadge } from '../components/ui/StatusBadge';

interface ComponentDetailPageProps {
  component: ComponentDocPage;
  onNavigate: (path: string) => void;
}

export const ComponentDetailPage: React.FC<ComponentDetailPageProps> = ({ component }) => {
  const [isShowcaseOpen, setIsShowcaseOpen] = useState(false);

  const sourceUrl = component.source
    ? `https://github.com/CodeWithEswar/FrogUI/blob/main/${component.source}`
    : 'https://github.com/CodeWithEswar/FrogUI';

  const tocItems: TocItem[] = [
    { id: 'overview', title: 'Overview & Preview', level: 2 },
    { id: 'installation', title: 'Installation', level: 2 },
    { id: 'usage', title: 'Usage Examples', level: 2 },
    { id: 'api-reference', title: 'API Reference', level: 2 },
    { id: 'design-tokens', title: 'Design Tokens & Anatomy', level: 2 }
  ];

  return (
    <div className="w-full xl:pr-72 flex justify-between gap-10">
      {/* Main Content Article */}
      <article className="min-w-0 flex-1 max-w-4xl">
        {/* Page Header */}
        <header className="pb-6 mb-8 border-b border-zinc-200 dark:border-zinc-800">
          <div className="flex flex-wrap items-center justify-between gap-4 mb-3">
            <div className="flex items-center gap-3">
              <h1 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-zinc-900 dark:text-zinc-50">
                {component.displayName}
              </h1>
              <StatusBadge status={component.status} size="md" />
            </div>

            {/* Header Action Buttons */}
            <div className="flex items-center gap-2">
              <button
                onClick={() => setIsShowcaseOpen(true)}
                className="inline-flex items-center gap-1.5 px-3 py-1.5 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900 text-xs font-medium text-zinc-800 dark:text-zinc-200 hover:bg-zinc-100 dark:hover:bg-zinc-800 transition-colors shadow-xs cursor-pointer"
              >
                <svg className="w-3.5 h-3.5 text-zinc-700 dark:text-zinc-300" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                  <polygon points="5 3 19 12 5 21 5 3" />
                </svg>
                <span>Open in Showcase</span>
              </button>
              <a
                href={sourceUrl}
                target="_blank"
                rel="noreferrer"
                className="inline-flex items-center gap-1 px-3 py-1.5 rounded-lg border border-zinc-200 dark:border-zinc-800 text-xs font-medium text-zinc-600 dark:text-zinc-400 hover:text-zinc-900 dark:hover:text-zinc-100 transition-colors"
              >
                <span>Source</span>
                <svg className="w-3 h-3" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                  <path d="M18 13v6a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h6M15 3h6v6M10 14L21 3" />
                </svg>
              </a>
            </div>
          </div>

          <p className="text-base text-zinc-600 dark:text-zinc-400 leading-relaxed">
            {component.description}
          </p>

          <div className="flex flex-wrap items-center gap-4 text-xs text-zinc-400 font-mono">
            <span>Category: <strong className="text-zinc-600 dark:text-zinc-300 capitalize">{component.category}</strong></span>
            <span>&bull;</span>
            <span>Composable: <strong className="text-zinc-600 dark:text-zinc-300">{component.name}</strong></span>
            <span>&bull;</span>
            <span>Since: <strong className="text-zinc-600 dark:text-zinc-300">{component.since}</strong></span>
          </div>
        </header>

        {/* Section 1: Overview & Interactive Preview */}
        <section id="overview" className="space-y-4">
          <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
            Overview &amp; Preview
          </h2>
          <p className="text-sm text-zinc-600 dark:text-zinc-400">
            Experiment with variants and sizes below. You can toggle the canvas between Light and Dark themes independently of the documentation website.
          </p>
          <ComponentPreview
            componentId={component.id}
            showcaseRoute={component.showcase?.route}
          />
        </section>

        {/* Section 2: Installation */}
        <section id="installation" className="space-y-4">
          <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
            Installation
          </h2>
          <p className="text-sm text-zinc-600 dark:text-zinc-400">
            Add the components module to your Gradle dependencies:
          </p>
          <CodeBlock
            language="kotlin"
            title="build.gradle.kts (:app)"
            code={`dependencies {
    implementation("io.github.codewitheswar.frogui:frogui-components:${release.version}")
}`}
          />
        </section>

        {/* Section 3: Basic Usage */}
        <section id="usage" className="space-y-4">
          <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
            Basic Usage
          </h2>
          <p className="text-sm text-zinc-600 dark:text-zinc-400">
            Call <code className="font-mono bg-zinc-100 dark:bg-zinc-800 px-1 py-0.5 rounded text-xs">{component.name}</code> inside a <code className="font-mono text-xs">FrogTheme</code> scope. Application state remains with the caller:
          </p>
          <CodeBlock
            language="kotlin"
            title="ActionExample.kt"
            code={`import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.components.button.FrogButtonVariant
import androidx.compose.material3.Text

@Composable
fun PrimaryAction(onClick: () -> Unit) {
    FrogButton(
        variant = FrogButtonVariant.Primary,
        onClick = onClick
    ) {
        Text("Continue")
    }
}`}
          />
        </section>

        {/* Section 4: Examples */}
        <section id="examples" className="space-y-6">
          <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
            Examples
          </h2>
          <p className="text-sm text-zinc-600 dark:text-zinc-400">
            Compiled and verified examples extracted from the native Android Showcase suite:
          </p>

          <div className="space-y-8">
            {component.examples.map(example => (
              <div key={example.id} className="space-y-2">
                <h3 className="text-base font-semibold text-zinc-800 dark:text-zinc-200">
                  {example.title}
                </h3>
                <p className="text-xs text-zinc-500 dark:text-zinc-400">
                  {example.description}
                </p>
                <CodeBlock
                  language="kotlin"
                  title={`${example.id}.kt`}
                  code={example.codeSnippet}
                />
              </div>
            ))}
          </div>
        </section>

        {/* Section 5: API Reference */}
        <section id="api-reference" className="space-y-4">
          <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
            API Reference
          </h2>
          <p className="text-sm text-zinc-600 dark:text-zinc-400">
            All parameters exposed by <code className="font-mono text-xs">{component.name}</code>:
          </p>
          <ApiTable properties={component.properties} />
        </section>

        {/* Section 6: Accessibility */}
        <section id="accessibility" className="space-y-4">
          <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
            Accessibility
          </h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 my-4">
            <div className="p-4 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60">
              <span className="text-xs text-zinc-400 uppercase tracking-wider font-semibold block mb-1">
                Semantic Role
              </span>
              <code className="text-sm font-mono text-zinc-900 dark:text-zinc-100 font-bold">
                {component.accessibility.role}
              </code>
            </div>
            <div className="p-4 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60">
              <span className="text-xs text-zinc-400 uppercase tracking-wider font-semibold block mb-1">
                Min Touch Target
              </span>
              <code className="text-sm font-mono text-zinc-900 dark:text-zinc-100 font-bold">
                {component.accessibility.minTouchTarget}
              </code>
            </div>
          </div>

          {component.accessibility.talkBackNotes && (
            <Callout type="note" title="TalkBack & Interaction">
              <p>{component.accessibility.talkBackNotes}</p>
            </Callout>
          )}

          <Callout type="important" title="Decorative Icons">
            <p>
              When supplying a decorative icon via <code className="font-mono text-xs">leadingIcon</code> or <code className="font-mono text-xs">trailingIcon</code> alongside visible label text, leave the icon's <code className="font-mono text-xs">contentDescription = null</code> to avoid duplicated announcements in screen readers.
            </p>
          </Callout>
        </section>

        {/* Section 7: Usage Guidance */}
        <section id="guidance" className="space-y-4 pt-6 border-t border-zinc-200 dark:border-zinc-800">
          <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
            Usage Guidance
          </h2>
          <div className="prose prose-zinc dark:prose-invert max-w-none text-sm leading-relaxed text-zinc-600 dark:text-zinc-400 space-y-4">
            <p>
              Use <code className="font-mono text-xs">FrogButton</code> to trigger an action such as saving a form or continuing a workflow. The caller supplies the action callback and owns enabled/loading state.
            </p>
            <p>
              Wrap application content in <code className="font-mono text-xs">FrogTheme</code>. Use composable content and icon slots, native Modifier, semantic variant/size values, and FrogButtonColors for customization.
            </p>
            <h3 className="text-base font-semibold text-zinc-800 dark:text-zinc-200 pt-2">
              Choosing a Variant
            </h3>
            <ul className="list-disc pl-5 space-y-1.5">
              <li><strong className="text-zinc-800 dark:text-zinc-200">Primary:</strong> Gives the main action the most visual emphasis. Recommended once per visual screen.</li>
              <li><strong className="text-zinc-800 dark:text-zinc-200">Secondary:</strong> Tonal Zinc surface for secondary operations (e.g. &ldquo;Cancel&rdquo;, &ldquo;Back&rdquo;).</li>
              <li><strong className="text-zinc-800 dark:text-zinc-200">Outline:</strong> Transparent surface with structural border for lower emphasis.</li>
              <li><strong className="text-zinc-800 dark:text-zinc-200">Ghost:</strong> Borderless button for toolbars and compact surfaces.</li>
              <li><strong className="text-zinc-800 dark:text-zinc-200">Destructive:</strong> High-warning action communicating permanent operations (e.g. &ldquo;Delete repository&rdquo;).</li>
            </ul>
          </div>
        </section>
      </article>

      {/* Right Table of Contents */}
      <TableOfContents items={tocItems} />

      {/* Interactive Showcase Modal */}
      <ShowcaseModal
        isOpen={isShowcaseOpen}
        onClose={() => setIsShowcaseOpen(false)}
        componentName={component.displayName}
        deepLinkRoute={component.showcase?.route || `components/${component.id}`}
      />
    </div>
  );
};
