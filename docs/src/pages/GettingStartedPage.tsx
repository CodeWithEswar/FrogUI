import React from 'react';
import { CodeBlock } from '../components/ui/CodeBlock';
import { Callout } from '../components/ui/Callout';
import { release } from '../generated/catalog';

interface GettingStartedPageProps {
  section?: 'introduction' | 'installation' | 'quick-start';
  onNavigate: (path: string) => void;
}

const modules = [
  ['frogui-foundation', 'Semantic colors, typography, spacing, shapes, elevation, motion, sizing, and adaptive values.'],
  ['frogui-theme', 'FrogTheme runtime, Compose locals, Material bridge, dark mode, and reduced-motion resolution.'],
  ['frogui-components', 'Public components such as FrogButton and FrogDrawer. Depends on theme and foundation.'],
  ['app', 'Native showcase and component laboratory. It is an example application, not a library dependency.']
];

export const GettingStartedPage: React.FC<GettingStartedPageProps> = ({ section = 'introduction', onNavigate }) => {
  const title = section === 'installation' ? 'Installation' : section === 'quick-start' ? 'Quick Start' : 'Introduction';
  const description = section === 'installation'
    ? 'Add the modules your app needs and confirm the release status before resolving dependencies.'
    : section === 'quick-start'
      ? 'Build a themed action and a caller-owned adaptive overlay with the current public API.'
      : 'Learn what FrogUI owns, what your application owns, and how the modules fit together.';

  return (
    <article className="w-full space-y-10 pb-16">
      <header className="space-y-3 pb-6 border-b border-[var(--frog-border)]">
        <div className="text-xs font-mono text-[var(--frog-muted-foreground)]">FrogUI {release.version}</div>
        <h1 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-[var(--frog-foreground)]">{title}</h1>
        <p className="text-base text-[var(--frog-muted-foreground)] leading-relaxed max-w-3xl">{description}</p>
      </header>

      {section === 'introduction' && (
        <div className="space-y-8 text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          <section className="space-y-4">
            <h2 className="text-xl font-bold text-[var(--frog-foreground)]">What FrogUI provides</h2>
            <p>FrogUI is a native Jetpack Compose component system. It provides semantic design tokens, a theme runtime, and reusable components with explicit state and accessibility contracts. Your application continues to own navigation, dependency injection, persistence, networking, and business state.</p>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
              {[
                ['Predictable APIs', 'Required state and callbacks are visible at the call site; components do not persist application data.'],
                ['Composable content', 'Slots accept normal Compose content, modifiers, icons, headers, previews, and footers.'],
                ['Accessible defaults', 'Controls preserve 48dp targets, semantic roles, focus treatment, contrast, and reduced motion.'],
                ['Theme ownership', 'Semantic defaults come from FrogTheme and can be replaced locally without forking a component.']
              ].map(([heading, body]) => (
                <div key={heading} className="p-4 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)]">
                  <h3 className="font-semibold text-[var(--frog-foreground)] mb-1">{heading}</h3>
                  <p className="text-xs">{body}</p>
                </div>
              ))}
            </div>
          </section>

          <section className="space-y-4">
            <h2 className="text-xl font-bold text-[var(--frog-foreground)]">Module map</h2>
            <div className="overflow-x-auto rounded-lg border border-[var(--frog-border)]">
              <table className="min-w-full text-left text-xs">
                <thead className="bg-[var(--frog-muted)] text-[var(--frog-foreground)]"><tr><th className="px-4 py-3">Module</th><th className="px-4 py-3">Responsibility</th></tr></thead>
                <tbody className="divide-y divide-[var(--frog-border)]">
                  {modules.map(([name, purpose]) => <tr key={name}><td className="px-4 py-3 font-mono text-[var(--frog-foreground)] whitespace-nowrap">{name}</td><td className="px-4 py-3">{purpose}</td></tr>)}
                </tbody>
              </table>
            </div>
          </section>

          <Callout type="note" title="Current stability">
            <p>Version <code className="font-mono text-xs">{release.version}</code> is {release.published ? 'marked published' : 'marked unpublished'} in the registry. Button and Drawer are experimental, so pin the version and review API changes before upgrading.</p>
          </Callout>

          <section className="space-y-3">
            <h2 className="text-xl font-bold text-[var(--frog-foreground)]">Compatibility contract</h2>
            <ul className="list-disc pl-5 space-y-2">
              <li>FrogUI targets native Android with Jetpack Compose and a minimum SDK of 24.</li>
              <li>Public API baselines are checked in, and compatibility changes must be reviewed explicitly.</li>
              <li>Registry metadata drives component navigation, examples, search, and API tables in these docs.</li>
              <li>Compose-in-View interop works through normal Android Compose hosting; FrogUI does not ship separate XML widgets.</li>
            </ul>
          </section>

          <button onClick={() => onNavigate('/docs/installation')} className="px-4 py-2 rounded-lg bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 font-medium text-xs cursor-pointer">Continue to Installation &rarr;</button>
        </div>
      )}

      {section === 'installation' && (
        <div className="space-y-8 text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          <section className="space-y-4">
            <h2 className="text-xl font-bold text-[var(--frog-foreground)]">1. Configure repositories</h2>
            <p>FrogUI uses Google&apos;s repository for Android and Compose dependencies and Maven Central for published artifacts.</p>
            <CodeBlock language="kotlin" title="settings.gradle.kts" code={`dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}`} />
          </section>

          <section className="space-y-4">
            <h2 className="text-xl font-bold text-[var(--frog-foreground)]">2. Add library modules</h2>
            <p>Add components and theme for a normal application integration. Gradle resolves the foundation dependency transitively; add it directly only when your code imports foundation tokens.</p>
            <CodeBlock language="kotlin" title="app/build.gradle.kts" code={`dependencies {
    implementation("io.github.codewitheswar.frogui:frogui-theme:${release.version}")
    implementation("io.github.codewitheswar.frogui:frogui-components:${release.version}")

    // Optional when importing foundation tokens directly
    // implementation("io.github.codewitheswar.frogui:frogui-foundation:${release.version}")
}`} />
          </section>

          <Callout type={release.published ? 'tip' : 'warning'} title={release.published ? 'Published release' : 'Snapshot availability'}>
            <p>Repository metadata marks <code className="font-mono text-xs">{release.version}</code> as {release.published ? 'published' : 'unpublished'}. If the artifact is unavailable from Maven Central, build the repository locally or consume the modules through a source checkout until a release is published.</p>
          </Callout>

          <section className="space-y-4">
            <h2 className="text-xl font-bold text-[var(--frog-foreground)]">3. Verify the integration</h2>
            <p>Sync Gradle, wrap one Compose subtree in <code className="font-mono text-xs">FrogTheme</code>, and compile. Repository contributors can run the complete architecture and compatibility gate locally:</p>
            <CodeBlock language="shell" title="Terminal" code={`./gradlew verifyArchitecture
npm test
npm --prefix docs run build`} />
          </section>

          <button onClick={() => onNavigate('/docs/quick-start')} className="px-4 py-2 rounded-lg bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 font-medium text-xs cursor-pointer">Next: Quick Start &rarr;</button>
        </div>
      )}

      {section === 'quick-start' && (
        <div className="space-y-8 text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          <section className="space-y-4">
            <h2 className="text-xl font-bold text-[var(--frog-foreground)]">1. Provide the theme</h2>
            <p><code className="font-mono text-xs">FrogTheme</code> supplies colors, typography, shapes, elevation, spacing, motion, sizing, and adaptive rules. Nested themes inherit token groups you do not replace.</p>
            <CodeBlock language="kotlin" title="MainActivity.kt" code={`setContent {
    FrogTheme {
        AppScreen()
    }
}`} />
          </section>

          <section className="space-y-4">
            <h2 className="text-xl font-bold text-[var(--frog-foreground)]">2. Add an action</h2>
            <p>The caller owns the action and loading state. A loading button suppresses activation while keeping stable label semantics.</p>
            <CodeBlock language="kotlin" title="SaveAction.kt" code={`var saving by remember { mutableStateOf(false) }

FrogButton(
    onClick = { saving = true },
    loading = saving,
    variant = FrogButtonVariant.Primary,
    fullWidth = true
) {
    Text("Save changes")
}`} />
          </section>

          <section className="space-y-4">
            <h2 className="text-xl font-bold text-[var(--frog-foreground)]">3. Add an adaptive Drawer</h2>
            <p>The state helper stores requested visibility. Dismiss events ask the caller to update that state. In Auto presentation, Compact space uses Bottom and Medium or Expanded space uses Side.</p>
            <CodeBlock language="kotlin" title="FilterDrawer.kt" code={`val drawerState = rememberFrogDrawerState()
val scope = rememberCoroutineScope()

FrogButton(onClick = { scope.launch { drawerState.open() } }) { Text("Filters") }

FrogDrawer(
    state = drawerState,
    onDismissRequest = { scope.launch { drawerState.close() } },
    title = "Filters",
    presentation = FrogDrawerPresentation.Auto,
    footer = {
        FrogButton(onClick = { scope.launch { drawerState.close() } }, fullWidth = true) {
            Text("Apply filters")
        }
    }
) {
    FilterControls()
}`} />
          </section>

          <Callout type="tip" title="Respect system motion settings">
            <p>Use <code className="font-mono text-xs">ProvideFrogThemeEnvironment</code> at the Android boundary when you need system animator state resolved into <code className="font-mono text-xs">FrogTheme.reduceMotion</code>. Components then switch to immediate or static feedback automatically.</p>
          </Callout>

          <div className="flex flex-wrap gap-3">
            <button onClick={() => onNavigate('/components/button')} className="px-4 py-2 rounded-lg bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 font-medium text-xs cursor-pointer">Read Button docs &rarr;</button>
            <button onClick={() => onNavigate('/components/drawer')} className="px-4 py-2 rounded-lg border border-[var(--frog-border)] text-[var(--frog-foreground)] font-medium text-xs cursor-pointer">Read Drawer docs &rarr;</button>
          </div>
        </div>
      )}
    </article>
  );
};
