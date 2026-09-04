import React, { useState } from 'react';
import { CodeBlock } from '../components/ui/CodeBlock';
import { Callout } from '../components/ui/Callout';
import { HugeIcon, HugeIconData } from '../components/ui/HugeIcon';
import {
  Layers01Icon,
  Layers02Icon,
  PaletteIcon,
  TextIcon,
  GridViewIcon,
  Motion01Icon,
  RulerIcon,
  SmartPhone01Icon,
  AccessibilityIcon,
  CpuIcon,
  GitBranchIcon,
  CheckmarkSquare01Icon
} from '@hugeicons/core-free-icons';

const colorCategories = [
  {
    category: 'Canvas & Surfaces',
    tokens: [
      { name: 'background', desc: 'Root canvas and window background' },
      { name: 'surface', desc: 'Base content regions, cards, and list items' },
      { name: 'surfaceElevated', desc: 'Raised panels, dialogs, and drawer sheets' },
      { name: 'subtleSurface', desc: 'Quiet background separation and recessed sections' },
      { name: 'muted', desc: 'Supporting regions, disabled tracks, and pill fills' },
      { name: 'mutedForeground', desc: 'Supporting text, captions, and secondary icons' },
    ]
  },
  {
    category: 'Content & Outlines',
    tokens: [
      { name: 'foreground', desc: 'Primary high-contrast text and active iconography' },
      { name: 'border', desc: 'Subtle separators, card outlines, and dividers' },
      { name: 'borderStrong', desc: 'Visible structural outlines and active boundaries' },
      { name: 'focusRing', desc: 'Accessible keyboard navigation and focus rings' },
    ]
  },
  {
    category: 'Actions & Semantic Roles',
    tokens: [
      { name: 'primary', desc: 'High-emphasis action fills and primary badges' },
      { name: 'primaryForeground', desc: 'Content and icons placed on primary actions' },
      { name: 'secondary', desc: 'Lower-emphasis secondary buttons and chips' },
      { name: 'secondaryForeground', desc: 'Content placed on secondary actions' },
      { name: 'destructive', desc: 'Destructive action fills and error states' },
      { name: 'destructiveForeground', desc: 'Content placed on destructive actions (4.83:1 contrast)' },
    ]
  }
];

const typographySpecs = [
  { role: 'display', size: '36sp', line: '44sp', weight: 'Bold (700)', sample: 'Native Compose UI' },
  { role: 'titleLarge', size: '24sp', line: '32sp', weight: 'SemiBold (600)', sample: 'Adaptive Overlays' },
  { role: 'title', size: '20sp', line: '28sp', weight: 'SemiBold (600)', sample: 'Elevated Navigation' },
  { role: 'heading', size: '18sp', line: '24sp', weight: 'SemiBold (600)', sample: 'Enforced Hierarchy' },
  { role: 'subheading', size: '16sp', line: '22sp', weight: 'Medium (500)', sample: 'Caller-Owned Architecture' },
  { role: 'body', size: '14sp', line: '20sp', weight: 'Normal (400)', sample: 'Every dependency must justify its presence in the core hierarchy.' },
  { role: 'bodySmall', size: '12sp', line: '16sp', weight: 'Normal (400)', sample: 'Supporting notes, timestamps, and secondary captions.' },
  { role: 'label', size: '13sp', line: '18sp', weight: 'Medium (500)', sample: 'Button Labels & Tabs' },
  { role: 'caption', size: '11sp', line: '14sp', weight: 'Normal (400)', sample: 'MICRO LABELS & METADATA' },
  { role: 'code', size: '13sp', line: '18sp', weight: 'Mono (400)', sample: 'val colors = FrogThemeDefaults.lightColors()' },
];

const spacingTokens = [
  { name: 'xxs', value: '2dp', px: 2 },
  { name: 'xs', value: '4dp', px: 4 },
  { name: 'sm', value: '6dp', px: 6 },
  { name: 'md', value: '8dp', px: 8 },
  { name: 'lg', value: '12dp', px: 12 },
  { name: 'xl', value: '16dp', px: 16 },
  { name: 'xxl', value: '20dp', px: 20 },
  { name: 'x3l', value: '24dp', px: 24 },
  { name: 'x4l', value: '32dp', px: 32 },
  { name: 'x5l', value: '40dp', px: 40 },
  { name: 'x6l', value: '48dp', px: 48 },
  { name: 'x7l', value: '64dp', px: 64 },
];

const shapeTokens = [
  { name: 'xs', radius: '4dp', desc: 'Badges, micro tags' },
  { name: 'sm', radius: '6dp', desc: 'Small buttons, chips' },
  { name: 'md', radius: '8dp', desc: 'Standard buttons, inputs' },
  { name: 'lg', radius: '12dp', desc: 'Cards, side drawers' },
  { name: 'xl', radius: '16dp', desc: 'Dialogs, bottom sheets' },
  { name: 'full', radius: '9999dp', desc: 'Pills, circular avatars' },
];

const elevationTokens = [
  { name: 'none', value: '0dp', desc: 'Resting directly on parent canvas; border provides quiet separation.' },
  { name: 'low', value: '1dp', desc: 'Subtle raised state for cards and list item groups.' },
  { name: 'medium', value: '2dp', desc: 'Interactive hover, active panels, and floating bars.' },
  { name: 'high', value: '4dp', desc: 'Floating modal dialogs, drawers, and overlay sheets.' },
];

const motionProfiles = [
  { name: 'fast', duration: '120ms', easing: 'FastOutSlowInEasing', desc: 'Micro-interactions, button press scale, toggle switch snaps.' },
  { name: 'normal', duration: '200ms', easing: 'CubicBezier(0.2, 0, 0, 1)', desc: 'Standard transitions, tab indicator slides, fade in/out.' },
  { name: 'large', duration: '280ms', easing: 'FastOutSlowInEasing', desc: 'Sheet expansion, drawer slide-in, full modal reveals.' },
];

const sizingGroups = [
  {
    group: 'Touch Targets',
    items: [
      { name: 'minimumTouchTarget', value: '48dp', desc: 'WCAG 2.5.5 accessible interaction target reserved on all interactive controls.' }
    ]
  },
  {
    group: 'Control Heights',
    items: [
      { name: 'controlSmall', value: '32dp', desc: 'Compact actions (reserves 48dp touch target internally)' },
      { name: 'controlMedium', value: '40dp', desc: 'Standard action and form control height' },
      { name: 'controlLarge', value: '48dp', desc: 'Hero actions and prominent call-to-actions' },
    ]
  },
  {
    group: 'Icon Sizing',
    items: [
      { name: 'iconSmall', value: '16dp', desc: 'Inlined with captions or compact controls' },
      { name: 'iconMedium', value: '20dp', desc: 'Standard button and list item icons' },
      { name: 'iconLarge', value: '24dp', desc: 'Standalone icon buttons and app bar actions' },
    ]
  }
];

const adaptiveBreakpoints = [
  { name: 'Compact', range: '< 600dp', devices: 'Phones (Portrait)', layout: 'Bottom navigation, stacked full-width content, Bottom Drawer' },
  { name: 'Medium', range: '600dp – 839dp', devices: 'Tablets (Portrait), Foldables', layout: 'Navigation rail, dual-pane cards, Side Auto Drawer' },
  { name: 'Expanded', range: '840dp+', devices: 'Tablets (Landscape), Desktops', layout: 'Permanent sidebar, multi-column dashboard, docked panels' },
];

const accessibilityPillars = [
  { title: '48dp Touch Targets', desc: 'Every interactive element reserves at least 48x48dp hit area, even when visual bounds are compact (32dp).' },
  { title: 'Contrast Guarantees', desc: 'Body text meets WCAG AA 4.5:1, large headings meet 3:1, and destructive white-on-red meets 4.83:1.' },
  { title: 'Reduced Motion', desc: 'Honors Android system animation scale and LocalFrogMotionEnabled, immediately collapsing spring durations to 0ms.' },
  { title: 'Assistive Tech (TalkBack)', desc: 'Full paneTitle semantics on drawers, explicit button roles, and state descriptions without screen reader noise.' },
];

const cssToken = (name: string) => `--frog-${name.replace(/[A-Z]/g, c => `-${c.toLowerCase()}`)}`;

export const FoundationPage: React.FC<{ section?: string }> = ({ section }) => {
  const [copiedToken, setCopiedToken] = useState<string | null>(null);

  React.useEffect(() => {
    if (!section || section === 'foundation') return;
    const targetId = section === 'shapes' ? 'spacing' : section;
    const frame = requestAnimationFrame(() => {
      const el = document.getElementById(targetId) || document.getElementById('spacing-shapes-elevation');
      if (el) {
        el.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }
    });
    return () => cancelAnimationFrame(frame);
  }, [section]);

  const copyToClipboard = (tokenStr: string) => {
    navigator.clipboard.writeText(tokenStr).then(() => {
      setCopiedToken(tokenStr);
      setTimeout(() => setCopiedToken(null), 2000);
    });
  };

  return (
    <article className="foundation-docs w-full space-y-12 pb-16">
      {/* Header */}
      <header className="space-y-4 pb-8 border-b border-[var(--frog-border)]">
        <div className="inline-flex items-center gap-2 px-2.5 py-1 rounded-md text-xs font-medium bg-zinc-100 dark:bg-zinc-800/80 text-zinc-800 dark:text-zinc-200 border border-zinc-200 dark:border-zinc-700/80">
          <HugeIcon icon={Layers01Icon as unknown as HugeIconData} size={14} className="text-zinc-500 dark:text-zinc-400" />
          Design Foundations & Tokens
        </div>
        <h1 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-[var(--frog-foreground)]">
          Theme & Foundations
        </h1>
        <p className="text-base text-[var(--frog-muted-foreground)] leading-relaxed max-w-3xl">
          One local Jetpack Compose runtime for FrogUI components. Semantic tokens establish the design language; component Defaults translate them into each control's behavior.
        </p>

        {/* Quick-Jump Foundation Pills */}
        <div className="flex flex-wrap gap-1.5 pt-2">
          {[
            { label: 'Theme Runtime', id: 'theme' },
            { label: 'Semantic Colors', id: 'colors' },
            { label: 'Typography', id: 'typography' },
            { label: 'Spacing & Shapes', id: 'spacing' },
            { label: 'Elevation', id: 'elevation' },
            { label: 'Motion', id: 'motion' },
            { label: 'Sizing', id: 'sizing' },
            { label: 'Adaptive', id: 'adaptive' },
            { label: 'Accessibility', id: 'accessibility' },
            { label: 'Interop', id: 'interop' },
          ].map(pill => (
            <a
              key={pill.id}
              href={`#${pill.id}`}
              className="text-xs px-2.5 py-1 rounded-md border border-[var(--frog-border)] bg-[var(--frog-surface)] hover:bg-zinc-100 dark:hover:bg-zinc-800/60 text-[var(--frog-muted-foreground)] hover:text-[var(--frog-foreground)] transition-colors"
            >
              {pill.label}
            </a>
          ))}
        </div>
      </header>

      {/* 1. Start with FrogTheme */}
      <section id="theme" className="space-y-6">
        <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2.5">
          <HugeIcon icon={CpuIcon as unknown as HugeIconData} size={22} className="text-zinc-400" />
          Start with FrogTheme
        </h2>
        <p className="text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          The default palette follows the system Android appearance. Pass <code className="text-[var(--frog-foreground)]">darkTheme</code> for a forced light or dark subtree, or pass custom <code className="text-[var(--frog-foreground)]">colors</code> for a brand palette. Its internal flag drives the private Material 3 bridge. System bars and preferences remain app responsibilities.
        </p>

        <CodeBlock
          language="kotlin"
          title="Canonical Usage: Theme.kt"
          code={`import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.theme.FrogThemeDefaults

// Inside a Composable hierarchy:
val customColors = FrogThemeDefaults.lightColors().copy(
    primary = Color(0xFF18181B),
    primaryForeground = Color.White,
)

FrogTheme(colors = customColors) {
    FrogButton(onClick = { /* Save action */ }) {
        Text("Save changes")
    }
}`}
        />

        <p className="text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          Nested themes inherit omitted typography, spacing, shapes, elevation, motion, sizing, and adaptive policies. Overrides affect only the nested subtree without resetting component state.
        </p>

        <CodeBlock
          language="kotlin"
          title="Nested Theme Inheritance"
          code={`FrogTheme(darkTheme = true) {
    // Outer subtree uses dark colors
    FrogTheme(darkTheme = false) {
        // Preview subtree inherits typography and sizing while using light colors
        FrogButton(onClick = {}) { Text("Subtree Preview") }
    }
}`}
        />
      </section>

      {/* 2. Semantic Colors */}
      <section id="colors" className="space-y-6">
        <div className="flex items-center justify-between flex-wrap gap-2">
          <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2.5">
            <HugeIcon icon={PaletteIcon as unknown as HugeIconData} size={22} className="text-zinc-400" />
            Semantic Colors
          </h2>
          <span className="text-xs font-mono px-2 py-0.5 rounded bg-zinc-100 dark:bg-zinc-800 border border-[var(--frog-border)] text-[var(--frog-muted-foreground)]">
            16 Semantic Roles
          </span>
        </div>
        <p className="text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          These swatches reflect the active documentation theme. In the Android Foundation explorer, light and dark values render side-by-side. CSS color values are strictly verified against canonical Kotlin defaults during CI.
        </p>

        <div className="space-y-6">
          {colorCategories.map(group => (
            <div key={group.category} className="space-y-3">
              <h3 className="text-xs font-semibold uppercase tracking-wider text-zinc-400 dark:text-zinc-500">
                {group.category}
              </h3>
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-3">
                {group.tokens.map(token => {
                  const varName = cssToken(token.name);
                  const isCopied = copiedToken === varName;
                  return (
                    <div
                      key={token.name}
                      onClick={() => copyToClipboard(varName)}
                      className="group p-3.5 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface)] hover:border-zinc-400 dark:hover:border-zinc-600 transition-all cursor-pointer flex gap-3.5 items-start"
                      title="Click to copy CSS token variable"
                    >
                      <div
                        className="w-10 h-10 shrink-0 rounded-lg border border-black/10 dark:border-white/10 shadow-2xs relative overflow-hidden"
                        style={{ backgroundColor: `var(${varName})` }}
                      >
                        <div className="absolute inset-0 bg-linear-to-b from-white/10 to-transparent pointer-events-none" />
                      </div>
                      <div className="min-w-0 flex-1 space-y-0.5">
                        <div className="flex items-center justify-between gap-1">
                          <code className="text-xs font-bold text-[var(--frog-foreground)] truncate font-mono">
                            {token.name}
                          </code>
                          <span className="text-[10px] text-zinc-400 opacity-0 group-hover:opacity-100 transition-opacity">
                            {isCopied ? 'Copied!' : 'Copy'}
                          </span>
                        </div>
                        <p className="text-xs text-[var(--frog-muted-foreground)] leading-tight line-clamp-2">
                          {token.desc}
                        </p>
                        <div className="pt-1 text-[10px] font-mono text-zinc-400 dark:text-zinc-500 truncate">
                          {varName}
                        </div>
                      </div>
                    </div>
                  );
                })}
              </div>
            </div>
          ))}
        </div>

        <Callout type="note" title="Accessibility & Contrast Guarantees">
          <p>
            Canonical destructive fill guarantees WCAG AA white-text contrast at <strong>4.83:1</strong>. Keyboard focus uses a separate dedicated <code className="text-[var(--frog-foreground)] font-mono">focusRing</code> token to ensure accessibility across all surfaces.
          </p>
        </Callout>
      </section>

      {/* 3. Typography */}
      <section id="typography" className="space-y-6">
        <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2.5">
          <HugeIcon icon={TextIcon as unknown as HugeIconData} size={22} className="text-zinc-400" />
          Typography
        </h2>
        <p className="text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          <code className="text-[var(--frog-foreground)]">FrogTypography</code> exposes 10 structured text styles. Explicit <code className="text-[var(--frog-foreground)]">sp</code> line heights honor Android font scaling; <code className="text-[var(--frog-foreground)]">code</code> uses monospace. Avoid fixed text container heights when verifying 1.3× and 1.5× large text accessibility.
        </p>

        {/* Visual Typography Specimen Table */}
        <div className="rounded-xl border border-[var(--frog-border)] overflow-hidden bg-[var(--frog-surface)] divide-y divide-[var(--frog-border)]">
          {typographySpecs.map(t => (
            <div key={t.role} className="p-4 flex flex-col md:flex-row md:items-center justify-between gap-3 hover:bg-zinc-50/50 dark:hover:bg-zinc-900/40 transition-colors">
              <div className="space-y-1 md:w-1/3 shrink-0">
                <div className="flex items-center gap-2">
                  <span className="font-mono text-xs font-bold text-[var(--frog-foreground)]">{t.role}</span>
                  <span className="text-[11px] px-1.5 py-0.5 rounded bg-zinc-100 dark:bg-zinc-800 text-zinc-500 font-mono">
                    {t.size} / {t.line}
                  </span>
                </div>
                <div className="text-xs text-[var(--frog-muted-foreground)]">{t.weight}</div>
              </div>
              <div className="min-w-0 flex-1">
                <p className="text-[var(--frog-foreground)] truncate" style={{ fontSize: `clamp(12px, ${t.size}, 28px)` }}>
                  {t.sample}
                </p>
              </div>
            </div>
          ))}
        </div>

        <CodeBlock
          language="kotlin"
          title="Local Typography Overrides"
          code={`val customTypography = FrogTheme.typography.copy(
    body = FrogTheme.typography.body.copy(fontSize = 15.sp, lineHeight = 22.sp),
    heading = FrogTheme.typography.heading.copy(letterSpacing = (-0.2).sp)
)

FrogTheme(typography = customTypography) {
    Text("A readable paragraph using customized body tokens.", style = FrogTheme.typography.body)
}`}
        />
      </section>

      {/* 4. Spacing & Shapes */}
      <section id="spacing" className="space-y-6">
        <div id="spacing-shapes-elevation" className="space-y-6">
          <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2.5">
            <HugeIcon icon={GridViewIcon as unknown as HugeIconData} size={22} className="text-zinc-400" />
            Spacing & Shapes
          </h2>
          <p className="text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
            Keep the established 12-step spacing scale for layout rhythm. Button padding and Drawer insets remain component-owned. Shapes provide cohesive corner radius standards across all interactive controls.
          </p>

          {/* Spacing Ruler */}
          <div className="space-y-3">
            <h3 className="text-xs font-semibold uppercase tracking-wider text-zinc-400 dark:text-zinc-500">
              Spacing Scale (xxs — x7l)
            </h3>
            <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-3">
              {spacingTokens.map(s => (
                <div key={s.name} className="p-3 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface)] space-y-2">
                  <div className="flex items-center justify-between text-xs font-mono">
                    <span className="font-bold text-[var(--frog-foreground)]">{s.name}</span>
                    <span className="text-zinc-400">{s.value}</span>
                  </div>
                  <div className="h-2 rounded bg-zinc-200 dark:bg-zinc-800 overflow-hidden">
                    <div className="h-full bg-zinc-500 dark:bg-zinc-400 rounded" style={{ width: `${Math.min(100, (s.px / 64) * 100)}%` }} />
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Shapes Gallery */}
          <div id="shapes" className="space-y-3 pt-4">
            <h3 className="text-xs font-semibold uppercase tracking-wider text-zinc-400 dark:text-zinc-500">
              Shape Corner Radii
            </h3>
            <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-6 gap-3">
              {shapeTokens.map(shape => (
                <div key={shape.name} className="p-3 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface)] text-center space-y-2">
                  <div
                    className="w-12 h-12 mx-auto border-2 border-zinc-400 dark:border-zinc-500 bg-zinc-100 dark:bg-zinc-800"
                    style={{ borderRadius: shape.radius === '9999dp' ? '9999px' : `${parseFloat(shape.radius) * 1.5}px` }}
                  />
                  <div className="font-mono text-xs font-bold text-[var(--frog-foreground)]">{shape.name}</div>
                  <div className="text-[11px] font-mono text-zinc-400">{shape.radius}</div>
                  <div className="text-[10px] text-[var(--frog-muted-foreground)] leading-tight">{shape.desc}</div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </section>

      {/* 5. Elevation */}
      <section id="elevation" className="space-y-6">
        <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2.5">
          <HugeIcon icon={Layers02Icon as unknown as HugeIconData} size={22} className="text-zinc-400" />
          Elevation & Depth
        </h2>
        <p className="text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          FrogUI avoids heavy simulated drop shadows. Depth is communicated primarily through subtle structural borders (<code className="text-[var(--frog-foreground)]">border</code> and <code className="text-[var(--frog-foreground)]">borderStrong</code>) and tonal surface elevation (<code className="text-[var(--frog-foreground)]">surface</code> vs <code className="text-[var(--frog-foreground)]">surfaceElevated</code>).
        </p>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {elevationTokens.map(e => (
            <div
              key={e.name}
              className="p-5 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] space-y-3"
            >
              <div className="flex items-center justify-between text-xs font-mono">
                <span className="font-bold text-[var(--frog-foreground)] uppercase">{e.name}</span>
                <span className="px-2 py-0.5 rounded bg-zinc-100 dark:bg-zinc-800 text-zinc-400">{e.value}</span>
              </div>
              <p className="text-xs text-[var(--frog-muted-foreground)] leading-relaxed">{e.desc}</p>
            </div>
          ))}
        </div>
      </section>

      {/* 6. Motion & Environment */}
      <section id="motion" className="space-y-6">
        <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2.5">
          <HugeIcon icon={Motion01Icon as unknown as HugeIconData} size={22} className="text-zinc-400" />
          Motion & Environment
        </h2>
        <p className="text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          Fast, normal, and large transitions use the standardized 120/200/280ms duration curve with semantic deceleration easing. <code className="text-[var(--frog-foreground)]">FrogTheme</code> automatically observes Android's disabled animator setting.
        </p>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          {motionProfiles.map(m => (
            <div key={m.name} className="p-4 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface)] space-y-2">
              <div className="flex items-center justify-between font-mono text-xs">
                <span className="font-bold text-[var(--frog-foreground)] uppercase">{m.name}</span>
                <span className="text-zinc-400 font-bold">{m.duration}</span>
              </div>
              <div className="text-[11px] font-mono text-zinc-500">{m.easing}</div>
              <p className="text-xs text-[var(--frog-muted-foreground)] leading-relaxed">{m.desc}</p>
            </div>
          ))}
        </div>

        <CodeBlock
          language="kotlin"
          title="Environment Provider & Reduced Motion Override"
          code={`import io.github.codewitheswar.frogui.theme.ProvideFrogThemeEnvironment

FrogTheme {
    ProvideFrogThemeEnvironment(
        sizing = FrogTheme.sizing.copy(minimumTouchTarget = 56.dp),
        reduceMotion = true, // Snaps transitions and disables decorative animation
    ) {
        FrogButton(onClick = {}, loading = true) {
            Text("Saving changes")
        }
    }
}`}
        />
      </section>

      {/* 7. Sizing */}
      <section id="sizing" className="space-y-6">
        <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2.5">
          <HugeIcon icon={RulerIcon as unknown as HugeIconData} size={22} className="text-zinc-400" />
          Sizing Tokens
        </h2>
        <p className="text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          <code className="text-[var(--frog-foreground)]">FrogSizing</code> separates interaction target bounds from visual control heights and icon scales. Compact 32dp buttons still reserve a 48dp touch region for motor accessibility.
        </p>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          {sizingGroups.map(group => (
            <div key={group.group} className="p-4 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface)] space-y-3">
              <h3 className="text-xs font-semibold uppercase tracking-wider text-zinc-400 dark:text-zinc-500">
                {group.group}
              </h3>
              <div className="space-y-2.5">
                {group.items.map(item => (
                  <div key={item.name} className="space-y-0.5">
                    <div className="flex items-center justify-between text-xs font-mono">
                      <span className="font-semibold text-[var(--frog-foreground)]">{item.name}</span>
                      <span className="px-1.5 py-0.5 rounded bg-zinc-100 dark:bg-zinc-800 text-zinc-400">{item.value}</span>
                    </div>
                    <p className="text-[11px] text-[var(--frog-muted-foreground)] leading-relaxed">{item.desc}</p>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* 8. Adaptive Composition */}
      <section id="adaptive" className="space-y-6">
        <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2.5">
          <HugeIcon icon={SmartPhone01Icon as unknown as HugeIconData} size={22} className="text-zinc-400" />
          Adaptive Composition
        </h2>
        <p className="text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          <code className="text-[var(--frog-foreground)]">FrogTheme.adaptive</code> classifies layout constraints into three canonical window size classes, ensuring components like <code className="text-[var(--frog-foreground)]">FrogDrawer</code> present as bottom sheets on Compact displays and side docked panels on Medium/Expanded viewports.
        </p>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          {adaptiveBreakpoints.map(bp => (
            <div key={bp.name} className="p-4 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface)] space-y-2.5">
              <div className="flex items-center justify-between font-mono text-xs">
                <span className="font-bold text-[var(--frog-foreground)]">{bp.name}</span>
                <span className="px-1.5 py-0.5 rounded bg-zinc-100 dark:bg-zinc-800 text-zinc-400">{bp.range}</span>
              </div>
              <div className="text-xs font-semibold text-zinc-700 dark:text-zinc-300">{bp.devices}</div>
              <p className="text-xs text-[var(--frog-muted-foreground)] leading-relaxed">{bp.layout}</p>
            </div>
          ))}
        </div>

        <CodeBlock
          language="kotlin"
          title="Adaptive Resolution with BoxWithConstraints"
          code={`BoxWithConstraints {
    val widthClass = FrogTheme.adaptive.windowSizeClass(maxWidth)
    when (widthClass) {
        FrogWindowWidthClass.Compact -> CompactBottomNavigation()
        FrogWindowWidthClass.Medium -> NavigationRailLayout()
        FrogWindowWidthClass.Expanded -> PermanentSidebarLayout()
    }
}`}
        />
      </section>

      {/* 9. Accessibility */}
      <section id="accessibility" className="space-y-6">
        <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2.5">
          <HugeIcon icon={AccessibilityIcon as unknown as HugeIconData} size={22} className="text-zinc-400" />
          Accessibility Guarantees
        </h2>
        <p className="text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          FrogUI enforces baseline accessibility directly in component composables and token definitions:
        </p>

        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          {accessibilityPillars.map(pillar => (
            <div key={pillar.title} className="p-4 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface)] space-y-2">
              <div className="flex items-center gap-2 font-semibold text-sm text-[var(--frog-foreground)]">
                <HugeIcon icon={CheckmarkSquare01Icon as unknown as HugeIconData} size={16} className="text-zinc-500 dark:text-zinc-400" />
                {pillar.title}
              </div>
              <p className="text-xs text-[var(--frog-muted-foreground)] leading-relaxed">{pillar.desc}</p>
            </div>
          ))}
        </div>
      </section>

      {/* 10. Material Interoperability */}
      <section id="interop" className="space-y-6">
        <h2 className="text-2xl font-bold tracking-tight text-[var(--frog-foreground)] flex items-center gap-2.5">
          <HugeIcon icon={GitBranchIcon as unknown as HugeIconData} size={22} className="text-zinc-400" />
          Material Interoperability
        </h2>
        <p className="text-sm text-[var(--frog-muted-foreground)] leading-relaxed">
          <code className="text-[var(--frog-foreground)]">FrogTheme</code> privately maps colors, typography, and shapes for any internal Material primitives. When custom compositions end, nested themes restore the surrounding theme cleanly without leaks.
        </p>
        <p className="text-xs text-[var(--frog-muted-foreground)]">
          Canonical source definitions:{' '}
          <a
            className="text-[var(--frog-foreground)] underline font-medium hover:text-zinc-400 transition-colors"
            href="https://github.com/CodeWithEswar/FrogUI/tree/main/frogui-foundation/src/main/java/io/github/codewitheswar/frogui/foundation"
            target="_blank"
            rel="noopener noreferrer"
          >
            frogui-foundation
          </a>{' '}
          and{' '}
          <a
            className="text-[var(--frog-foreground)] underline font-medium hover:text-zinc-400 transition-colors"
            href="https://github.com/CodeWithEswar/FrogUI/tree/main/frogui-theme/src/main/kotlin/io/github/codewitheswar/frogui/theme"
            target="_blank"
            rel="noopener noreferrer"
          >
            frogui-theme
          </a>.
        </p>
      </section>
    </article>
  );
};
