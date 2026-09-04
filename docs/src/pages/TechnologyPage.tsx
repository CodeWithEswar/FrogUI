import { CodeBlock } from '../components/ui/CodeBlock';
import { Callout } from '../components/ui/Callout';
import { HugeIcon, HugeIconData } from '../components/ui/HugeIcon';
import {
  Layers01Icon,
  CpuIcon,
  Shield01Icon,
  Rocket01Icon,
  CheckmarkCircle01Icon,
  Cancel01Icon,
  GitBranchIcon,
  Download01Icon
} from '@hugeicons/core-free-icons';

interface TechnologyPageProps {
  onNavigate?: (path: string) => void;
}

export const TechnologyPage: React.FC<TechnologyPageProps> = ({ onNavigate: _onNavigate }) => {
  return (
    <article className="w-full space-y-12 pb-16">
      {/* Header */}
      <header className="space-y-4 pb-8 border-b border-[var(--frog-border)]">
        <div className="inline-flex items-center gap-2 px-2.5 py-1 rounded-md text-xs font-medium bg-zinc-100 dark:bg-zinc-800/80 text-zinc-800 dark:text-zinc-200 border border-zinc-200 dark:border-zinc-700/80">
          <HugeIcon icon={Shield01Icon as unknown as HugeIconData} size={14} className="text-zinc-500 dark:text-zinc-400" />
          Phase 05 · Enforceable Architecture Contract
        </div>
        <h1 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-[var(--frog-foreground)]">
          Technology Foundation & Architecture
        </h1>
        <p className="text-base text-[var(--frog-muted-foreground)] leading-relaxed max-w-3xl">
          FrogUI is a native Android component system, not an application framework. Every dependency must justify its presence, every public type must justify its stability cost, and developers always retain total ownership of their Android UI.
        </p>
      </header>

      {/* Core Architectural Pillars */}
      <section className="space-y-6">
        <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2">
          <HugeIcon icon={Layers01Icon as unknown as HugeIconData} size={22} className="text-zinc-500" />
          Core Technology Contract
        </h2>
        <div className="overflow-x-auto rounded-xl border border-[var(--frog-border)] bg-zinc-50/50 dark:bg-zinc-900/30">
          <table className="w-full text-left text-sm">
            <thead className="border-b border-[var(--frog-border)] bg-zinc-100/50 dark:bg-zinc-900/60 text-xs font-semibold text-[var(--frog-muted-foreground)] uppercase tracking-wider">
              <tr>
                <th className="py-3.5 px-4">Concern</th>
                <th className="py-3.5 px-4">Decision</th>
                <th className="py-3.5 px-4">Architectural Rationale</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-zinc-200 dark:divide-zinc-800/60 font-mono text-xs">
              <tr className="hover:bg-zinc-100/30 dark:hover:bg-zinc-800/20">
                <td className="py-3 px-4 font-semibold text-zinc-900 dark:text-zinc-200 font-sans">Android Language</td>
                <td className="py-3 px-4 text-zinc-900 dark:text-zinc-100 font-semibold font-mono">Kotlin 2.2.10</td>
                <td className="py-3 px-4 text-[var(--frog-muted-foreground)] font-sans">Strict null safety, Compose compiler integration, idiomatic slot and DSL APIs.</td>
              </tr>
              <tr className="hover:bg-zinc-100/30 dark:hover:bg-zinc-800/20">
                <td className="py-3 px-4 font-semibold text-zinc-900 dark:text-zinc-200 font-sans">UI Runtime</td>
                <td className="py-3 px-4 text-zinc-900 dark:text-zinc-100 font-semibold font-mono">Jetpack Compose</td>
                <td className="py-3 px-4 text-[var(--frog-muted-foreground)] font-sans">Compose-first v1. No parallel XML layouts or custom Views.</td>
              </tr>
              <tr className="hover:bg-zinc-100/30 dark:hover:bg-zinc-800/20">
                <td className="py-3 px-4 font-semibold text-zinc-900 dark:text-zinc-200 font-sans">Build System</td>
                <td className="py-3 px-4 text-zinc-900 dark:text-zinc-100 font-semibold font-mono">Gradle Kotlin DSL</td>
                <td className="py-3 px-4 text-[var(--frog-muted-foreground)] font-sans">Type-safe configuration with centralized Version Catalog (<code className="text-[var(--frog-foreground)]">libs.versions.toml</code>).</td>
              </tr>
              <tr className="hover:bg-zinc-100/30 dark:hover:bg-zinc-800/20">
                <td className="py-3 px-4 font-semibold text-zinc-900 dark:text-zinc-200 font-sans">Asynchronous State</td>
                <td className="py-3 px-4 text-zinc-900 dark:text-zinc-100 font-semibold font-mono">Coroutines & Flow</td>
                <td className="py-3 px-4 text-[var(--frog-muted-foreground)] font-sans">Only where genuinely asynchronous behavior exists (e.g. drawer suspend transitions).</td>
              </tr>
              <tr className="hover:bg-zinc-100/30 dark:hover:bg-zinc-800/20">
                <td className="py-3 px-4 font-semibold text-zinc-900 dark:text-zinc-200 font-sans">Images</td>
                <td className="py-3 px-4 text-zinc-900 dark:text-zinc-100 font-semibold font-mono">Optional Coil Adapter</td>
                <td className="py-3 px-4 text-[var(--frog-muted-foreground)] font-sans">Coil is never forced into core. Network/image dependencies remain optional.</td>
              </tr>
              <tr className="hover:bg-zinc-100/30 dark:hover:bg-zinc-800/20">
                <td className="py-3 px-4 font-semibold text-zinc-900 dark:text-zinc-200 font-sans">Showcase Icons</td>
                <td className="py-3 px-4 text-zinc-900 dark:text-zinc-100 font-semibold font-mono">Hugeicons Free 4.3.0</td>
                <td className="py-3 px-4 text-[var(--frog-muted-foreground)] font-sans">Consistent, modern iconography across toolbar, navigation, and inspectors.</td>
              </tr>
              <tr className="hover:bg-zinc-100/30 dark:hover:bg-zinc-800/20">
                <td className="py-3 px-4 font-semibold text-zinc-900 dark:text-zinc-200 font-sans">Component Icons</td>
                <td className="py-3 px-4 text-zinc-900 dark:text-zinc-100 font-semibold font-mono">Composable Slots</td>
                <td className="py-3 px-4 text-[var(--frog-muted-foreground)] font-sans">Public components do not hardcode Hugeicons; callers supply any vector or slot.</td>
              </tr>
              <tr className="hover:bg-zinc-100/30 dark:hover:bg-zinc-800/20">
                <td className="py-3 px-4 font-semibold text-zinc-900 dark:text-zinc-200 font-sans">Web Documentation</td>
                <td className="py-3 px-4 text-zinc-900 dark:text-zinc-100 font-semibold font-mono">React + Vite + MDX</td>
                <td className="py-3 px-4 text-[var(--frog-muted-foreground)] font-sans">Fast static developer portal deployed to GitHub Pages with Tailwind CSS 4.</td>
              </tr>
              <tr className="hover:bg-zinc-100/30 dark:hover:bg-zinc-800/20">
                <td className="py-3 px-4 font-semibold text-zinc-900 dark:text-zinc-200 font-sans">Code Highlighting</td>
                <td className="py-3 px-4 text-zinc-900 dark:text-zinc-100 font-semibold font-mono">Shiki</td>
                <td className="py-3 px-4 text-[var(--frog-muted-foreground)] font-sans">Build-time syntax highlighting for Kotlin, Gradle DSL, JSON, and TypeScript.</td>
              </tr>
              <tr className="hover:bg-zinc-100/30 dark:hover:bg-zinc-800/20">
                <td className="py-3 px-4 font-semibold text-zinc-900 dark:text-zinc-200 font-sans">Registry System</td>
                <td className="py-3 px-4 text-zinc-900 dark:text-zinc-100 font-semibold font-mono">JSON Schema (v1)</td>
                <td className="py-3 px-4 text-[var(--frog-muted-foreground)] font-sans">Canonical source of truth driving both typed Kotlin and TypeScript projections.</td>
              </tr>
              <tr className="hover:bg-zinc-100/30 dark:hover:bg-zinc-800/20">
                <td className="py-3 px-4 font-semibold text-zinc-900 dark:text-zinc-200 font-sans">Distribution</td>
                <td className="py-3 px-4 text-zinc-900 dark:text-zinc-100 font-semibold font-mono">Maven Central</td>
                <td className="py-3 px-4 text-[var(--frog-muted-foreground)] font-sans">Standard Android artifacts with source JARs, reviewable local staging, and POM metadata.</td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>

      {/* Dependency Boundaries */}
      <section className="space-y-6">
        <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2">
          <HugeIcon icon={GitBranchIcon as unknown as HugeIconData} size={22} className="text-zinc-500" />
          Enforced Module Hierarchy
        </h2>
        <p className="text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          The dependency graph is strictly acyclic and enforced by <code className="text-[var(--frog-foreground)]">gradle/product-contract.gradle.kts</code> during every build and CI run. Lower-level modules never depend on higher-level consumers.
        </p>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div className="p-5 rounded-xl border border-[var(--frog-border)] bg-zinc-50/50 dark:bg-zinc-900/40 space-y-3">
            <div className="flex items-center gap-2 font-semibold text-sm text-[var(--frog-foreground)]">
              <HugeIcon icon={CheckmarkCircle01Icon as unknown as HugeIconData} size={18} className="text-zinc-500 dark:text-zinc-400" />
              Permitted Production Edges
            </div>
            <ul className="text-xs space-y-2 text-[var(--frog-muted-foreground)] font-mono">
              <li>• <span className="text-zinc-900 dark:text-zinc-100 font-bold">frogui-foundation</span>: Zero project dependencies. Low-level tokens only.</li>
              <li>• <span className="text-zinc-900 dark:text-zinc-100 font-bold">frogui-theme</span>: Depends only on <code className="text-[var(--frog-foreground)]">frogui-foundation</code>.</li>
              <li>• <span className="text-zinc-900 dark:text-zinc-100 font-bold">frogui-components</span>: Depends on <code className="text-[var(--frog-foreground)]">frogui-theme</code>.</li>
              <li>• <span className="text-zinc-900 dark:text-zinc-100 font-bold">frogui-registry</span>: Zero production dependencies.</li>
              <li>• <span className="text-zinc-900 dark:text-zinc-100 font-bold">app (Showcase)</span>: Consumes all modules for interactive laboratories.</li>
            </ul>
          </div>

          <div className="p-5 rounded-xl border border-red-500/20 bg-red-500/5 space-y-3">
            <div className="flex items-center gap-2 font-semibold text-sm text-red-600 dark:text-red-400">
              <HugeIcon icon={Cancel01Icon as unknown as HugeIconData} size={18} className="text-red-500" />
              Forbidden Dependency Creep
            </div>
            <ul className="text-xs space-y-2 text-[var(--frog-muted-foreground)]">
              <li>❌ <strong className="text-zinc-900 dark:text-zinc-200">No Networking:</strong> Retrofit, OkHttp, or Ktor in core modules.</li>
              <li>❌ <strong className="text-zinc-900 dark:text-zinc-200">No Dependency Injection:</strong> Hilt or Koin in reusable components.</li>
              <li>❌ <strong className="text-zinc-900 dark:text-zinc-200">No Database / Storage:</strong> Room, SQLite, or DataStore in core.</li>
              <li>❌ <strong className="text-zinc-900 dark:text-zinc-200">No Hardcoded Icon Packs:</strong> Components take slots, not icon libraries.</li>
              <li>❌ <strong className="text-zinc-900 dark:text-zinc-200">No Upward Leaks:</strong> Foundation or components never import app.</li>
            </ul>
          </div>
        </div>

        <CodeBlock
          language="kotlin"
          title="Architecture Graph Enforcement (product-contract.gradle.kts)"
          code={`// Keep in sync with docs/architecture/dependency-rules.md
val productionEdges = mapOf(
    ":frogui-foundation" to emptySet<String>(),
    ":frogui-theme" to setOf(":frogui-foundation"),
    ":frogui-components" to setOf(":frogui-foundation", ":frogui-theme"),
    ":frogui-registry" to emptySet<String>(),
    ":frogui-testing" to setOf(":frogui-theme"),
    ":app" to setOf(":frogui-foundation", ":frogui-theme", ":frogui-components", ":frogui-registry")
)`}
        />
      </section>

      {/* Kotlin & Compose API Philosophy */}
      <section className="space-y-6">
        <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2">
          <HugeIcon icon={CpuIcon as unknown as HugeIconData} size={22} className="text-zinc-500" />
          Kotlin & Compose API Philosophy
        </h2>

        <div className="space-y-4 text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          <p>
            FrogUI APIs are strictly Kotlin-first. We reject Java-style builder patterns, heavy reflection, and stateful monoliths in favor of idiomatic Jetpack Compose patterns:
          </p>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 pt-2">
            <div className="p-4 rounded-lg border border-[var(--frog-border)] space-y-2">
              <h4 className="font-semibold text-[var(--frog-foreground)]">Slot-Based Architecture</h4>
              <p className="text-xs">Composable lambda slots (<code className="text-[var(--frog-foreground)]">content</code>, <code className="text-[var(--frog-foreground)]">leadingIcon</code>, <code className="text-[var(--frog-foreground)]">footer</code>) give callers total freedom over content hierarchy.</p>
            </div>
            <div className="p-4 rounded-lg border border-[var(--frog-border)] space-y-2">
              <h4 className="font-semibold text-[var(--frog-foreground)]">State Hoisting</h4>
              <p className="text-xs">Consumers own their data and business logic. Components accept state values and dispatch actions via callbacks.</p>
            </div>
            <div className="p-4 rounded-lg border border-[var(--frog-border)] space-y-2">
              <h4 className="font-semibold text-[var(--frog-foreground)]">Stability Annotations</h4>
              <p className="text-xs">Data tokens are marked <code className="text-[var(--frog-foreground)]">@Immutable</code> and controllers <code className="text-[var(--frog-foreground)]">@Stable</code> to guarantee zero unnecessary recompositions.</p>
            </div>
            <div className="p-4 rounded-lg border border-[var(--frog-border)] space-y-2">
              <h4 className="font-semibold text-[var(--frog-foreground)]">Meaningful Nullability</h4>
              <p className="text-xs">Nullable parameters communicate true optionality (e.g. <code className="text-[var(--frog-foreground)]">subtitle: String? = null</code>), never laziness around defaults.</p>
            </div>
          </div>
        </div>

        <CodeBlock
          language="kotlin"
          title="Canonical Component Example: FrogDrawer.kt"
          code={`@Composable
fun FrogDrawer(
    state: FrogDrawerState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    presentation: FrogDrawerPresentation = FrogDrawerPresentation.Auto,
    side: FrogDrawerSide = FrogDrawerSide.End,
    title: String? = null,
    subtitle: String? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    preview: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
    colors: FrogDrawerColors = FrogDrawerDefaults.colors(),
    onBackRequest: (() -> Unit)? = null,
    closeIcon: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
)`}
        />
      </section>

      {/* Showcase Icon Motion */}
      <section className="space-y-6">
        <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2">
          <HugeIcon icon={Rocket01Icon as unknown as HugeIconData} size={22} className="text-zinc-500" />
          Hugeicons Showcase & Motion Standards
        </h2>
        <p className="text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          Within the Showcase application, <strong>Hugeicons</strong> provides the canonical iconography across toolbars, tabs, inspectors, and navigation. The bottom navigation applies restrained, professional Compose transitions:
        </p>
        <ul className="list-disc pl-5 text-sm space-y-2 text-[var(--frog-muted-foreground)]">
          <li><strong>Icon Scale:</strong> Subtle expansion from <code className="text-[var(--frog-foreground)]">1.0x</code> to <code className="text-[var(--frog-foreground)]">1.06x</code> on selection.</li>
          <li><strong>Active Indicator:</strong> Smooth horizontal expansion from <code className="text-[var(--frog-foreground)]">0dp</code> to <code className="text-[var(--frog-foreground)]">16dp</code> below the active destination.</li>
          <li><strong>Reduced Motion Support:</strong> Reads <code className="text-[var(--frog-foreground)]">FrogTheme.reduceMotion</code>. <code className="text-[var(--frog-foreground)]">ProvideFrogThemeEnvironment</code> resolves the Android animator setting at the app boundary.</li>
        </ul>
      </section>

      {/* CI/CD & Verification */}
      <section className="space-y-6">
        <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2">
          <HugeIcon icon={Download01Icon as unknown as HugeIconData} size={22} className="text-zinc-500" />
          Automated Quality Gates & Verification
        </h2>
        <div className="p-4 rounded-xl border border-[var(--frog-border)] bg-zinc-50/50 dark:bg-zinc-900/40 space-y-3 text-xs font-mono">
          <div className="flex items-center justify-between text-[var(--frog-foreground)] font-semibold text-sm font-sans">
            <span>Automated CI Workflows</span>
            <span className="text-xs font-mono px-2 py-0.5 rounded bg-zinc-200/60 dark:bg-zinc-800 border border-zinc-300 dark:border-zinc-700 text-zinc-700 dark:text-zinc-300">Local gates verified</span>
          </div>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-2 text-[var(--frog-muted-foreground)]">
            <div>• <strong className="text-[var(--frog-foreground)]">android-ci.yml:</strong> Architecture, compilation, and Android unit tests.</div>
            <div>• <strong className="text-[var(--frog-foreground)]">registry-docs.yml:</strong> Schema validation, docs typecheck, and build.</div>
            <div>• <strong className="text-[var(--frog-foreground)]">docs-deploy.yml:</strong> GitHub Pages deployment to <code className="text-[var(--frog-foreground)]">/FrogUI/</code>.</div>
            <div>• <strong className="text-[var(--frog-foreground)]">release.yml:</strong> Staging Maven publications with verified POM metadata.</div>
          </div>
        </div>

        <Callout type="note" title="Local Verification Command">
          <p>
            Run <code className="text-[var(--frog-foreground)] font-bold">./gradlew verifyArchitecture</code> to execute the full architecture verification suite, including registry validation, documentation asset generation, product contract boundary checks, and unit tests.
          </p>
        </Callout>
        <p className="text-xs text-[var(--frog-muted-foreground)] leading-relaxed">
          JVM, registry, API, architecture, lint, APK compilation, and documentation checks run without a device. Connected Android tests remain a separate release step because they require an emulator or physical device for TalkBack, focus traversal, font scaling, and gesture verification.
        </p>
      </section>
    </article>
  );
};
