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

  const basicExample = component.examples.find(e => e.id === 'basic') || component.examples[0];
  const variantExamples = component.examples.filter(e => e.id !== basicExample?.id);

  const tocItems: TocItem[] = [
    { id: 'overview', title: 'Overview & Preview', level: 2 },
    { id: 'installation', title: 'Installation', level: 2 },
    { id: 'usage', title: 'Basic Usage', level: 2 },
    ...(variantExamples.length > 0 ? [{ id: 'examples', title: 'Examples', level: 2 }] : []),
    { id: 'api-reference', title: 'API Reference', level: 2 },
    { id: 'accessibility', title: 'Accessibility', level: 2 },
    { id: 'guidance', title: 'Usage Guidance', level: 2 },
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
            Experiment with variants and presentations below. You can toggle the canvas between Light and Dark themes independently of the documentation website.
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
          {basicExample && (
            <CodeBlock
              language="kotlin"
              title={`${component.name}BasicExample.kt`}
              code={basicExample.codeSnippet}
            />
          )}
        </section>

        {/* Section 4: Examples */}
        {variantExamples.length > 0 && (
          <section id="examples" className="space-y-6">
            <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
              Examples
            </h2>
            <p className="text-sm text-zinc-600 dark:text-zinc-400">
              Compiled and verified examples extracted from the native Android Showcase suite:
            </p>

            <div className="space-y-8">
              {variantExamples.map(example => (
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
        )}

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
            {component.id === 'drawer' ? (
              <>
                <p>
                  Use <code className="font-mono text-xs">FrogDrawer</code> to present contextual secondary flows, settings panels, filter sheets, or multi-level navigation drawers without navigating away from the active screen.
                </p>
                <p>
                  Application state remains with the caller via <code className="font-mono text-xs">rememberFrogDrawerState()</code>. Call <code className="font-mono text-xs">state.open()</code> and <code className="font-mono text-xs">state.close()</code> inside a coroutine scope to trigger smooth spring animations.
                </p>
                <h3 className="text-base font-semibold text-zinc-800 dark:text-zinc-200 pt-2">
                  Presentation Modes
                </h3>
                <ul className="list-disc pl-5 space-y-1.5">
                  <li><strong className="text-zinc-800 dark:text-zinc-200">Auto (Default):</strong> Responsive layout adapting automatically based on available width. Renders as a modal bottom sheet on compact screens (&lt; 620dp) and docks as a contextual side panel on tablet/desktop screens (&ge; 620dp).</li>
                  <li><strong className="text-zinc-800 dark:text-zinc-200">Bottom:</strong> Always presents as a bottom sheet with a central drag handle, supporting pointer drag-down dismissal and hardware back press.</li>
                  <li><strong className="text-zinc-800 dark:text-zinc-200">Side:</strong> Always docks as an edge sheet at <code className="font-mono text-xs">Start</code> or <code className="font-mono text-xs">End</code> edge, suitable for persistent desktop side inspectors.</li>
                </ul>
              </>
            ) : (
              <>
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
              </>
            )}
          </div>
        </section>

        {/* Section 8: Design Tokens & Anatomy */}
        <section id="design-tokens" className="space-y-4 pt-6 border-t border-zinc-200 dark:border-zinc-800">
          <h2 className="text-xl font-bold text-zinc-900 dark:text-zinc-100">
            Design Tokens &amp; Anatomy
          </h2>
          <p className="text-sm text-zinc-600 dark:text-zinc-400">
            Structural anatomy and design token specifications governing {component.name}:
          </p>

          {component.id === 'drawer' ? (
            <div className="space-y-6">
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-3">
                <div className="p-3.5 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 space-y-1">
                  <div className="text-[11px] uppercase tracking-wider text-zinc-400 font-semibold">1. Drag Handle</div>
                  <div className="text-xs text-zinc-700 dark:text-zinc-300">48dp &times; 4dp centered pill with 2dp corner radius. Serves as visual affordance for touch swipe dismiss.</div>
                </div>
                <div className="p-3.5 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 space-y-1">
                  <div className="text-[11px] uppercase tracking-wider text-zinc-400 font-semibold">2. Header Slot</div>
                  <div className="text-xs text-zinc-700 dark:text-zinc-300">Contains title text with heading semantics, subtitle, and dismiss icon button.</div>
                </div>
                <div className="p-3.5 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 space-y-1">
                  <div className="text-[11px] uppercase tracking-wider text-zinc-400 font-semibold">3. Content Body</div>
                  <div className="text-xs text-zinc-700 dark:text-zinc-300">Scrollable container with nested scroll interop between drag gestures and inner list scrolling.</div>
                </div>
                <div className="p-3.5 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 space-y-1">
                  <div className="text-[11px] uppercase tracking-wider text-zinc-400 font-semibold">4. Footer Slot</div>
                  <div className="text-xs text-zinc-700 dark:text-zinc-300">Sticky action container with primary action and dismiss buttons docked to the bottom.</div>
                </div>
                <div className="p-3.5 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 space-y-1">
                  <div className="text-[11px] uppercase tracking-wider text-zinc-400 font-semibold">5. Modal Scrim</div>
                  <div className="text-xs text-zinc-700 dark:text-zinc-300">60% black backdrop (<code className="font-mono text-[10px]">scrimColor</code>) that dims background content and handles tap-to-dismiss.</div>
                </div>
                <div className="p-3.5 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 space-y-1">
                  <div className="text-[11px] uppercase tracking-wider text-zinc-400 font-semibold">6. Surface Container</div>
                  <div className="text-xs text-zinc-700 dark:text-zinc-300">Elevated surface with 16dp corner radius and 1dp border separating sheet from background.</div>
                </div>
              </div>

              {/* Token Table */}
              <div className="overflow-x-auto rounded-lg border border-zinc-200 dark:border-zinc-800">
                <table className="min-w-full divide-y divide-zinc-200 dark:divide-zinc-800 text-xs">
                  <thead className="bg-zinc-50 dark:bg-zinc-900/80 text-zinc-500 font-semibold">
                    <tr>
                      <th className="px-4 py-2.5 text-left">Token</th>
                      <th className="px-4 py-2.5 text-left">Value</th>
                      <th className="px-4 py-2.5 text-left">Description</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-zinc-200 dark:divide-zinc-800 font-mono text-[11px]">
                    <tr>
                      <td className="px-4 py-2 text-zinc-900 dark:text-zinc-100 font-semibold">AutoBreakpoint</td>
                      <td className="px-4 py-2 text-zinc-600 dark:text-zinc-400">620.dp</td>
                      <td className="px-4 py-2 text-zinc-500 font-sans">Threshold between Bottom Sheet and Side Drawer modes</td>
                    </tr>
                    <tr>
                      <td className="px-4 py-2 text-zinc-900 dark:text-zinc-100 font-semibold">CornerRadius</td>
                      <td className="px-4 py-2 text-zinc-600 dark:text-zinc-400">16.dp</td>
                      <td className="px-4 py-2 text-zinc-500 font-sans">Top corners (bottom sheet) or side corners (docked panel)</td>
                    </tr>
                    <tr>
                      <td className="px-4 py-2 text-zinc-900 dark:text-zinc-100 font-semibold">AnimationSpec</td>
                      <td className="px-4 py-2 text-zinc-600 dark:text-zinc-400">spring(StiffnessMediumLow)</td>
                      <td className="px-4 py-2 text-zinc-500 font-sans">Natural physics spring curve for open, close, and drag release</td>
                    </tr>
                    <tr>
                      <td className="px-4 py-2 text-zinc-900 dark:text-zinc-100 font-semibold">ScrimColor</td>
                      <td className="px-4 py-2 text-zinc-600 dark:text-zinc-400">Black.copy(alpha = 0.6f)</td>
                      <td className="px-4 py-2 text-zinc-500 font-sans">Backdrop overlay opacity in modal presentation</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          ) : (
            <div className="space-y-6">
              <div className="grid grid-cols-1 sm:grid-cols-3 gap-3">
                <div className="p-3.5 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 space-y-1">
                  <div className="text-[11px] uppercase tracking-wider text-zinc-400 font-semibold">1. Touch Target</div>
                  <div className="text-xs text-zinc-700 dark:text-zinc-300">Minimum 48dp bounding box ensured across all sizes for accessible touch interaction.</div>
                </div>
                <div className="p-3.5 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 space-y-1">
                  <div className="text-[11px] uppercase tracking-wider text-zinc-400 font-semibold">2. Container Surface</div>
                  <div className="text-xs text-zinc-700 dark:text-zinc-300">Rounded pill container with variant-specific surface fill, stroke, and pressed elevation.</div>
                </div>
                <div className="p-3.5 rounded-lg border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900/60 space-y-1">
                  <div className="text-[11px] uppercase tracking-wider text-zinc-400 font-semibold">3. Content Row</div>
                  <div className="text-xs text-zinc-700 dark:text-zinc-300">Horizontal arrangement containing leading icon slot, text label, and trailing icon slot.</div>
                </div>
              </div>

              {/* Token Table */}
              <div className="overflow-x-auto rounded-lg border border-zinc-200 dark:border-zinc-800">
                <table className="min-w-full divide-y divide-zinc-200 dark:divide-zinc-800 text-xs">
                  <thead className="bg-zinc-50 dark:bg-zinc-900/80 text-zinc-500 font-semibold">
                    <tr>
                      <th className="px-4 py-2.5 text-left">Size</th>
                      <th className="px-4 py-2.5 text-left">Height</th>
                      <th className="px-4 py-2.5 text-left">Radius</th>
                      <th className="px-4 py-2.5 text-left">Padding</th>
                      <th className="px-4 py-2.5 text-left">Typography</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-zinc-200 dark:divide-zinc-800 font-mono text-[11px]">
                    <tr>
                      <td className="px-4 py-2 text-zinc-900 dark:text-zinc-100 font-semibold font-sans">Small</td>
                      <td className="px-4 py-2 text-zinc-600 dark:text-zinc-400">32.dp</td>
                      <td className="px-4 py-2 text-zinc-600 dark:text-zinc-400">8.dp</td>
                      <td className="px-4 py-2 text-zinc-600 dark:text-zinc-400">12.dp horizontal</td>
                      <td className="px-4 py-2 text-zinc-500 font-sans">labelMedium (12sp)</td>
                    </tr>
                    <tr>
                      <td className="px-4 py-2 text-zinc-900 dark:text-zinc-100 font-semibold font-sans">Medium</td>
                      <td className="px-4 py-2 text-zinc-600 dark:text-zinc-400">40.dp</td>
                      <td className="px-4 py-2 text-zinc-600 dark:text-zinc-400">10.dp</td>
                      <td className="px-4 py-2 text-zinc-600 dark:text-zinc-400">16.dp horizontal</td>
                      <td className="px-4 py-2 text-zinc-500 font-sans">labelLarge (14sp)</td>
                    </tr>
                    <tr>
                      <td className="px-4 py-2 text-zinc-900 dark:text-zinc-100 font-semibold font-sans">Large</td>
                      <td className="px-4 py-2 text-zinc-600 dark:text-zinc-400">48.dp</td>
                      <td className="px-4 py-2 text-zinc-600 dark:text-zinc-400">12.dp</td>
                      <td className="px-4 py-2 text-zinc-600 dark:text-zinc-400">20.dp horizontal</td>
                      <td className="px-4 py-2 text-zinc-500 font-sans">titleSmall (16sp)</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          )}
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
