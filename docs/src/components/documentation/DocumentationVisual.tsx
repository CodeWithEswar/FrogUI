import React, { useState } from 'react';
import { catalog, categories } from '../../generated/catalog';
import { docsNavigation } from '../../navigation';
import { flattenNavigation, FlattenedNavNode } from '../../navigation';

interface DocumentationVisualProps {
  kind: string;
  onNavigate: (path: string) => void;
}

const tokens = {
  colors: [
    { name: 'background', light: '#FFFFFF', dark: '#09090B', role: 'Base canvas behind all component surfaces' },
    { name: 'foreground', light: '#09090B', dark: '#FAFAFA', role: 'Primary text and icons on neutral surfaces' },
    { name: 'surface', light: '#FAFAFA', dark: '#0C0C0E', role: 'Standard component surface (cards, sheets)' },
    { name: 'surfaceElevated', light: '#FFFFFF', dark: '#111113', role: 'Visually raised contextual surface (drawers, dialogs)' },
    { name: 'subtleSurface', light: '#F4F4F5', dark: '#18181B', role: 'Low-emphasis grouping, tracks and headers' },
    { name: 'muted', light: '#E4E4E7', dark: '#27272A', role: 'Muted fills, hover tracks, and disabled fills' },
    { name: 'mutedForeground', light: '#52525B', dark: '#A1A1AA', role: 'Secondary text and supporting information' },
    { name: 'border', light: '#00000014', dark: '#FFFFFF14', role: 'Subtle 1px boundary between layout regions' },
    { name: 'borderStrong', light: '#00000024', dark: '#FFFFFF24', role: 'Higher-emphasis structural boundary (outlines)' },
    { name: 'primary', light: '#09090B', dark: '#FAFAFA', role: 'Primary action fill' },
    { name: 'primaryForeground', light: '#FFFFFF', dark: '#09090B', role: 'Content on primary action fill' },
    { name: 'secondary', light: '#F4F4F5', dark: '#27272A', role: 'Secondary action fill' },
    { name: 'secondaryForeground', light: '#18181B', dark: '#FAFAFA', role: 'Content on secondary action fill' },
    { name: 'destructive', light: '#DC2626', dark: '#DC2626', role: 'Destructive action fill (4.83:1 contrast)' },
    { name: 'destructiveForeground', light: '#FFFFFF', dark: '#FFFFFF', role: 'Content on destructive action fill' },
    { name: 'focusRing', light: '#52525B', dark: '#A1A1AA', role: 'Visible 2dp keyboard and D-pad focus outline' }
  ],
  typography: [
    { name: 'display', size: '32sp', line: '40sp', weight: 'Bold', tracking: '-0.5sp', sample: 'Build native interfaces with confidence.' },
    { name: 'titleLarge', size: '24sp', line: '32sp', weight: 'SemiBold', tracking: '-0.3sp', sample: 'Adaptive component systems for Compose' },
    { name: 'title', size: '20sp', line: '28sp', weight: 'SemiBold', tracking: '-0.2sp', sample: 'Caller-owned state architecture' },
    { name: 'heading', size: '18sp', line: '24sp', weight: 'SemiBold', tracking: '0sp', sample: 'Delivery evidence and verification' },
    { name: 'subheading', size: '16sp', line: '22sp', weight: 'Medium', tracking: '0sp', sample: 'Focused documentation and guidelines' },
    { name: 'body', size: '15sp', line: '22sp', weight: 'Normal', tracking: '0sp', sample: 'Readable product content follows a predictable typographic hierarchy across all screen sizes.' },
    { name: 'bodySmall', size: '13sp', line: '18sp', weight: 'Normal', tracking: '0sp', sample: 'Supporting detail, metadata, and hints remain legible under system font scaling.' },
    { name: 'label', size: '12sp', line: '16sp', weight: 'Medium', tracking: '0.2sp', sample: 'ACTION BUTTON LABEL' },
    { name: 'caption', size: '11sp', line: '14sp', weight: 'Normal', tracking: '0sp', sample: 'TIMESTAMP · VERSION 0.1.0' },
    { name: 'code', size: '13sp', line: '18sp', weight: 'Normal', tracking: '0sp', sample: 'FrogTheme.typography.code' }
  ],
  spacing: [
    { name: 'xxs', value: 2, role: 'Finest separation between tight inline elements' },
    { name: 'xs', value: 4, role: 'Small inline gap between icon and label' },
    { name: 'sm', value: 6, role: 'Compact content gap within small controls' },
    { name: 'md', value: 8, role: 'Standard content gap and default layout rhythm' },
    { name: 'lg', value: 12, role: 'Control and card group separation' },
    { name: 'xl', value: 16, role: 'Standard container and screen padding' },
    { name: 'xxl', value: 20, role: 'Generous content gap between related sections' },
    { name: 'xxxl', value: 24, role: 'Section separation within a single page' },
    { name: 'x4l', value: 32, role: 'Large section separation on larger screens' },
    { name: 'x5l', value: 40, role: 'Major landmark gap between primary regions' },
    { name: 'x6l', value: 48, role: 'Major region separation on expanded displays' },
    { name: 'x7l', value: 64, role: 'Hero and expansive layout boundary padding' }
  ],
  shapes: [
    { name: 'xs', radius: '4dp', usage: 'Inline badges, tags, subtle tooltips' },
    { name: 'sm', radius: '6dp', usage: 'Compact controls, small buttons, chips' },
    { name: 'md', radius: '10dp', usage: 'Standard buttons, cards, input containers' },
    { name: 'lg', radius: '14dp', usage: 'Side panels, modal overlays, docked sheets' },
    { name: 'xl', radius: '18dp', usage: 'Bottom sheet top corners, primary dialogs' },
    { name: 'full', radius: 'CircleShape', usage: 'Pill buttons, circular avatars, status dots' }
  ],
  elevation: [
    { name: 'none', value: 0, role: 'Flat Canvas', surface: 'Base background, insets, inline list items' },
    { name: 'low', value: 1, role: 'Raised Container', surface: 'Cards, toolbars, segmented controls' },
    { name: 'medium', value: 3, role: 'Floating Overlay', surface: 'Popovers, dropdown menus, context tooltips' },
    { name: 'high', value: 6, role: 'Modal Priority Sheet', surface: 'Dialogs, drawers, priority alerts' }
  ],
  sizing: {
    touchTarget: { name: 'minimumTouchTarget', value: 48, role: 'Enforced interactive envelope (at least 48dp)' },
    controls: [
      { name: 'controlSmall', value: 32, role: 'Compact visual control height' },
      { name: 'controlMedium', value: 40, role: 'Standard visual control height' },
      { name: 'controlLarge', value: 48, role: 'Prominent visual control height' }
    ],
    icons: [
      { name: 'iconSmall', value: 16, role: 'Compact glyph size' },
      { name: 'iconMedium', value: 18, role: 'Standard control glyph size' },
      { name: 'iconLarge', value: 20, role: 'Prominent / navigation glyph size' }
    ]
  },
  adaptive: [
    { name: 'Compact', range: '< 600dp', pattern: 'Single-column stack; bottom modal sheets for contextual inspectors' },
    { name: 'Medium', range: '600–839dp', pattern: 'Navigation rail + content; optional contextual side panel' },
    { name: 'Expanded', range: '≥ 840dp', pattern: 'Persistent sidebar navigation + multi-pane content + side inspector' }
  ]
};

const IndexVisual: React.FC<{ prefix: string; onNavigate: (path: string) => void }> = ({ prefix, onNavigate }) => {
  const items: FlattenedNavNode[] = flattenNavigation(docsNavigation).filter((item: FlattenedNavNode) => item.href.startsWith(prefix) && item.href !== prefix);
  return (
    <div className="divide-y divide-[var(--frog-border)] border-y border-[var(--frog-border)]">
      {items.map((item: FlattenedNavNode) => (
        <button
          key={item.href}
          type="button"
          onClick={() => onNavigate(item.href)}
          className="group w-full py-3.5 flex items-center gap-4 text-left hover:bg-[var(--frog-subtle-surface)] focus-visible:outline-2 focus-visible:outline-[var(--frog-focus-ring)] px-2 transition-colors cursor-pointer"
        >
          <span className="min-w-0 flex-1">
            <span className="block text-sm font-semibold text-[var(--frog-foreground)] group-hover:underline">
              {item.title}
            </span>
            <span className="block mt-0.5 text-xs text-[var(--frog-muted-foreground)]">
              {item.description}
            </span>
          </span>
          <span aria-hidden="true" className="text-[var(--frog-muted-foreground)] group-hover:translate-x-0.5 transition-transform text-xs">
            →
          </span>
        </button>
      ))}
    </div>
  );
};

const ComponentIndex: React.FC<{ onNavigate: (path: string) => void }> = ({ onNavigate }) => (
  <div className="space-y-5">
    {categories.map(category => {
      const items = catalog.filter(component => component.category === category.id);
      if (!items.length) return null;
      return (
        <section key={category.id}>
          <h3 className="mb-2 text-[10px] font-semibold uppercase tracking-wider text-[var(--frog-muted-foreground)]">
            {category.displayName}
          </h3>
          <div className="grid gap-2 sm:grid-cols-2">
            {items.map(component => (
              <button
                key={component.id}
                onClick={() => onNavigate(component.path)}
                className="p-4 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface)] text-left hover:border-[var(--frog-border-strong)] transition-all cursor-pointer"
              >
                <span className="font-semibold text-sm text-[var(--frog-foreground)]">{component.displayName}</span>
                <span className="block mt-1 text-xs text-[var(--frog-muted-foreground)]">{component.description}</span>
              </button>
            ))}
          </div>
        </section>
      );
    })}
  </div>
);

const ColorVisual = () => (
  <div className="grid gap-3 sm:grid-cols-2">
    {tokens.colors.map(color => (
      <div key={color.name} className="p-3.5 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] space-y-2.5">
        <div className="flex gap-2">
          <div className="flex-1 rounded-md border border-black/10 overflow-hidden shadow-2xs">
            <div className="h-9 w-full" style={{ background: color.light }} />
            <div className="px-2 py-1 bg-white text-[10px] font-mono text-zinc-800 flex justify-between">
              <span>Light</span>
              <span>{color.light}</span>
            </div>
          </div>
          <div className="flex-1 rounded-md border border-white/10 overflow-hidden shadow-2xs">
            <div className="h-9 w-full" style={{ background: color.dark }} />
            <div className="px-2 py-1 bg-zinc-950 text-[10px] font-mono text-zinc-300 flex justify-between">
              <span>Dark</span>
              <span>{color.dark}</span>
            </div>
          </div>
        </div>
        <div>
          <div className="flex items-center justify-between">
            <code className="text-xs font-semibold text-[var(--frog-foreground)]">{color.name}</code>
            <span className="text-[10px] font-mono text-[var(--frog-muted-foreground)]">FrogColors.{color.name}</span>
          </div>
          <p className="mt-1 text-[11px] leading-relaxed text-[var(--frog-muted-foreground)]">{color.role}</p>
        </div>
      </div>
    ))}
  </div>
);

const TypographyVisual = () => (
  <div className="divide-y divide-[var(--frog-border)] rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface)] overflow-hidden">
    {tokens.typography.map(item => (
      <div key={item.name} className="p-4 sm:flex sm:items-start sm:gap-6">
        <div className="w-44 shrink-0 mb-2 sm:mb-0">
          <div className="flex items-center gap-1.5">
            <code className="text-xs font-bold text-[var(--frog-foreground)]">{item.name}</code>
          </div>
          <div className="mt-1 flex flex-wrap gap-1 text-[10px] font-mono text-[var(--frog-muted-foreground)]">
            <span className="px-1.5 py-0.5 rounded bg-[var(--frog-subtle-surface)] border border-[var(--frog-border)]">{item.size} / {item.line}</span>
            <span className="px-1.5 py-0.5 rounded bg-[var(--frog-subtle-surface)] border border-[var(--frog-border)]">{item.weight}</span>
            {item.tracking !== '0sp' && (
              <span className="px-1.5 py-0.5 rounded bg-[var(--frog-subtle-surface)] border border-[var(--frog-border)]">{item.tracking}</span>
            )}
          </div>
        </div>
        <p
          className={`min-w-0 flex-1 text-[var(--frog-foreground)] ${item.name === 'code' ? 'font-mono' : ''}`}
          style={{
            fontSize: item.name === 'display' ? '28px' : item.name === 'titleLarge' ? '22px' : item.name === 'title' ? '19px' : item.name === 'heading' ? '17px' : item.name === 'subheading' ? '15px' : item.name === 'body' ? '14.5px' : item.name === 'bodySmall' ? '13px' : item.name === 'label' ? '12px' : item.name === 'caption' ? '11px' : '13px',
            lineHeight: 1.3,
            fontWeight: item.weight === 'Bold' ? 700 : item.weight === 'SemiBold' ? 600 : item.weight === 'Medium' ? 500 : 400,
            letterSpacing: item.tracking === '-0.5sp' ? '-0.03em' : item.tracking === '0.2sp' ? '0.04em' : 'normal'
          }}
        >
          {item.sample}
        </p>
      </div>
    ))}
  </div>
);

const SpacingVisual = () => (
  <div className="grid gap-2.5 sm:grid-cols-2">
    {tokens.spacing.map(item => (
      <div key={item.name} className="p-3 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface)] flex flex-col justify-between gap-2">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-2">
            <code className="text-xs font-semibold text-[var(--frog-foreground)]">{item.name}</code>
            <span className="text-[11px] font-mono text-[var(--frog-muted-foreground)]">FrogSpacing.{item.name}</span>
          </div>
          <span className="px-1.5 py-0.5 rounded text-[10px] font-mono font-bold bg-[var(--frog-subtle-surface)] text-[var(--frog-foreground)] border border-[var(--frog-border)]">
            {item.value}dp
          </span>
        </div>
        <div className="flex items-center gap-3">
          <div
            className="h-2 rounded-full bg-[var(--frog-foreground)] transition-all"
            style={{ width: `${Math.min(item.value * 2.5, 160)}px` }}
          />
          <span className="text-[10px] text-[var(--frog-muted-foreground)] truncate">{item.role}</span>
        </div>
      </div>
    ))}
  </div>
);

const ShapesVisual = () => (
  <div className="grid grid-cols-2 sm:grid-cols-3 gap-3">
    {tokens.shapes.map(item => (
      <div key={item.name} className="p-4 text-center rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] space-y-2">
        <div
          className="mx-auto h-16 w-16 border-2 border-[var(--frog-border-strong)] bg-[var(--frog-surface)] shadow-2xs flex items-center justify-center text-[10px] font-mono text-[var(--frog-muted-foreground)]"
          style={{ borderRadius: item.radius === 'CircleShape' ? '9999px' : item.radius }}
        >
          {item.radius === 'CircleShape' ? 'full' : item.radius}
        </div>
        <div>
          <code className="block text-xs font-bold text-[var(--frog-foreground)]">FrogShapes.{item.name}</code>
          <span className="block mt-0.5 text-[10px] text-[var(--frog-muted-foreground)]">{item.usage}</span>
        </div>
      </div>
    ))}
  </div>
);

const ElevationVisual = () => (
  <div className="grid gap-3.5 sm:grid-cols-2 lg:grid-cols-4">
    {tokens.elevation.map(item => (
      <div
        key={item.name}
        className="p-4 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] flex flex-col justify-between transition-all"
        style={{
          boxShadow:
            item.value === 0
              ? 'none'
              : `0 ${item.value * 2}px ${item.value * 4}px rgba(0,0,0,0.06), 0 ${item.value}px ${item.value * 2}px rgba(0,0,0,0.04)`
        }}
      >
        <div>
          <div className="flex items-center justify-between">
            <code className="text-xs font-semibold uppercase tracking-wider text-[var(--frog-foreground)]">
              {item.name}
            </code>
            <span className="px-1.5 py-0.5 rounded text-[10px] font-mono font-semibold bg-[var(--frog-subtle-surface)] text-[var(--frog-muted-foreground)] border border-[var(--frog-border)]">
              {item.value}dp
            </span>
          </div>
          <p className="mt-2 text-xs font-medium text-[var(--frog-foreground)]">{item.role}</p>
          <p className="mt-1 text-[11px] leading-relaxed text-[var(--frog-muted-foreground)]">
            {item.surface}
          </p>
        </div>
        <div className="mt-4 pt-3 border-t border-[var(--frog-border)] flex items-center justify-between text-[10px] font-mono text-[var(--frog-muted-foreground)]">
          <span>FrogElevation.{item.name}</span>
        </div>
      </div>
    ))}
  </div>
);

const MotionVisual = () => {
  const [run, setRun] = useState(0);
  const [reduced, setReduced] = useState(false);

  return (
    <div className="p-5 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] space-y-4">
      <div className="flex flex-wrap items-center justify-between gap-3">
        <div>
          <h4 className="text-xs font-bold text-[var(--frog-foreground)]">Interactive Motion Simulation</h4>
          <p className="text-[11px] text-[var(--frog-muted-foreground)]">
            Test FastOutSlowInEasing transitions and reduced-motion instant snapping.
          </p>
        </div>
        <div className="flex items-center gap-3">
          <label className="flex items-center gap-2 text-xs text-[var(--frog-foreground)] cursor-pointer select-none group">
            <input
              type="checkbox"
              checked={reduced}
              onChange={e => setReduced(e.target.checked)}
              className="sr-only peer"
            />
            <div
              className={`w-4 h-4 rounded flex items-center justify-center transition-all duration-150 border ${
                reduced
                  ? 'bg-[var(--frog-foreground)] border-[var(--frog-foreground)] text-[var(--frog-background)]'
                  : 'border-[var(--frog-border-strong)] bg-[var(--frog-surface)] group-hover:border-zinc-400 dark:group-hover:border-zinc-500'
              } peer-focus-visible:ring-2 peer-focus-visible:ring-offset-2 peer-focus-visible:ring-[var(--frog-focus-ring)] peer-focus-visible:ring-offset-[var(--frog-surface-elevated)]`}
            >
              {reduced && (
                <svg className="w-2.5 h-2.5 stroke-[3]" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                  <polyline points="20 6 9 17 4 12" />
                </svg>
              )}
            </div>
            <span className="font-medium text-xs text-[var(--frog-muted-foreground)] group-hover:text-[var(--frog-foreground)] transition-colors">
              Simulate Reduce Motion
            </span>
          </label>
          <button
            type="button"
            onClick={() => setRun(v => v + 1)}
            className="px-3 py-1.5 rounded-md bg-[var(--frog-foreground)] text-[var(--frog-background)] text-xs font-semibold hover:opacity-90 transition-opacity cursor-pointer"
          >
            Play Transitions
          </button>
        </div>
      </div>

      <div className="grid gap-3 sm:grid-cols-3 pt-2">
        {[
          { name: 'fastDurationMillis', label: 'Fast', ms: 120, usage: 'Button feedback, micro-interactions' },
          { name: 'normalDurationMillis', label: 'Normal', ms: 200, usage: 'State toggles, expanding content' },
          { name: 'largeDurationMillis', label: 'Large', ms: 280, usage: 'Sheet translations, drawer slides' }
        ].map(spec => (
          <div key={spec.name} className="p-3.5 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface)] space-y-2.5">
            <div className="flex justify-between text-xs">
              <code className="font-semibold text-[var(--frog-foreground)]">{spec.label}</code>
              <span className="font-mono text-[var(--frog-muted-foreground)]">{reduced ? '0ms (Snapped)' : `${spec.ms}ms`}</span>
            </div>
            <div className="h-3 w-full bg-[var(--frog-subtle-surface)] rounded-full overflow-hidden p-0.5 border border-[var(--frog-border)]">
              <div
                key={`${run}-${spec.ms}-${reduced}`}
                className="h-full rounded-full bg-[var(--frog-foreground)]"
                style={{
                  width: '100%',
                  transition: reduced ? 'none' : `width ${spec.ms}ms cubic-bezier(0.4, 0.0, 0.2, 1.0)`,
                  animation: reduced ? 'none' : `motionDemo ${spec.ms}ms cubic-bezier(0.4, 0.0, 0.2, 1.0)`
                }}
              />
            </div>
            <p className="text-[10px] text-[var(--frog-muted-foreground)] leading-tight">{spec.usage}</p>
          </div>
        ))}
      </div>
      <style>{`
        @keyframes motionDemo {
          0% { transform: scaleX(0.1); transform-origin: left; }
          100% { transform: scaleX(1); transform-origin: left; }
        }
      `}</style>
    </div>
  );
};

const SizingVisual = () => (
  <div className="space-y-4">
    <div className="p-4 rounded-xl border border-emerald-500/30 bg-emerald-500/5 space-y-2">
      <div className="flex items-center justify-between">
        <span className="text-xs font-bold text-emerald-600 dark:text-emerald-400">
          Mandatory Interaction Envelope: {tokens.sizing.touchTarget.value}dp
        </span>
        <code className="text-[10px] font-mono text-emerald-600 dark:text-emerald-400">
          FrogSizing.{tokens.sizing.touchTarget.name}
        </code>
      </div>
      <p className="text-xs text-[var(--frog-muted-foreground)] leading-relaxed">
        FrogUI enforces at least 48dp on all interactive targets via a runtime invariant (require ≥ 48dp). Visual controls can be 32dp or 40dp, but the clickable bounds must expand to 48dp.
      </p>
    </div>

    <div className="grid gap-3 sm:grid-cols-2">
      <div className="p-4 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface)] space-y-3">
        <h4 className="text-xs font-semibold text-[var(--frog-foreground)]">Control Heights (Visual)</h4>
        <div className="space-y-2">
          {tokens.sizing.controls.map(item => (
            <div key={item.name} className="flex items-center justify-between p-2.5 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)]">
              <div className="flex items-center gap-3">
                <div
                  className="w-8 rounded bg-[var(--frog-subtle-surface)] border border-[var(--frog-border-strong)] flex items-center justify-center font-mono text-[9px]"
                  style={{ height: `${item.value * 0.8}px` }}
                >
                  {item.value}
                </div>
                <div>
                  <code className="text-xs font-medium text-[var(--frog-foreground)]">{item.name}</code>
                  <span className="block text-[10px] text-[var(--frog-muted-foreground)]">{item.role}</span>
                </div>
              </div>
              <span className="text-xs font-mono font-bold text-[var(--frog-foreground)]">{item.value}dp</span>
            </div>
          ))}
        </div>
      </div>

      <div className="p-4 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface)] space-y-3">
        <h4 className="text-xs font-semibold text-[var(--frog-foreground)]">Decorative Icon Sizes</h4>
        <div className="space-y-2">
          {tokens.sizing.icons.map(item => (
            <div key={item.name} className="flex items-center justify-between p-2.5 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)]">
              <div className="flex items-center gap-3">
                <div
                  className="rounded bg-[var(--frog-subtle-surface)] border border-[var(--frog-border-strong)] flex items-center justify-center"
                  style={{ width: `${item.value + 4}px`, height: `${item.value + 4}px` }}
                >
                  <span className="rounded-full bg-[var(--frog-foreground)]" style={{ width: `${item.value / 3}px`, height: `${item.value / 3}px` }} />
                </div>
                <div>
                  <code className="text-xs font-medium text-[var(--frog-foreground)]">{item.name}</code>
                  <span className="block text-[10px] text-[var(--frog-muted-foreground)]">{item.role}</span>
                </div>
              </div>
              <span className="text-xs font-mono font-bold text-[var(--frog-foreground)]">{item.value}dp</span>
            </div>
          ))}
        </div>
      </div>
    </div>
  </div>
);

const AdaptiveVisual = () => (
  <div className="grid gap-3 sm:grid-cols-3">
    {tokens.adaptive.map((item, index) => (
      <div key={item.name} className="p-4 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] flex flex-col justify-between space-y-3">
        <div>
          <div className="flex items-center justify-between mb-2">
            <code className="text-xs font-bold text-[var(--frog-foreground)]">{item.name}</code>
            <span className="px-1.5 py-0.5 rounded text-[10px] font-mono font-semibold bg-[var(--frog-subtle-surface)] text-[var(--frog-foreground)] border border-[var(--frog-border)]">
              {item.range}
            </span>
          </div>

          <div className="h-24 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface)] p-2 mb-3 flex gap-1.5 items-stretch">
            {index === 0 && (
              <div className="w-full flex flex-col gap-1">
                <div className="h-4 rounded bg-[var(--frog-subtle-surface)] w-full" />
                <div className="flex-1 rounded bg-[var(--frog-surface-elevated)] border border-[var(--frog-border)] flex items-center justify-center text-[9px] text-[var(--frog-muted-foreground)]">
                  Stacked Content
                </div>
                <div className="h-6 rounded bg-[var(--frog-foreground)]/10 border-t border-[var(--frog-border-strong)] flex items-center justify-center text-[8px] text-[var(--frog-foreground)]">
                  Bottom Sheet
                </div>
              </div>
            )}
            {index === 1 && (
              <>
                <div className="w-4 rounded bg-[var(--frog-subtle-surface)] flex items-center justify-center text-[8px] text-[var(--frog-muted-foreground)]" />
                <div className="flex-1 rounded bg-[var(--frog-surface-elevated)] border border-[var(--frog-border)] flex items-center justify-center text-[9px] text-[var(--frog-muted-foreground)]">
                  Main Content
                </div>
                <div className="w-10 rounded bg-[var(--frog-subtle-surface)] border-l border-[var(--frog-border)] flex items-center justify-center text-[8px] text-[var(--frog-muted-foreground)]">
                  Side
                </div>
              </>
            )}
            {index === 2 && (
              <>
                <div className="w-12 rounded bg-[var(--frog-subtle-surface)] flex items-center justify-center text-[8px] text-[var(--frog-muted-foreground)]">
                  Nav
                </div>
                <div className="flex-1 rounded bg-[var(--frog-surface-elevated)] border border-[var(--frog-border)] flex items-center justify-center text-[9px] text-[var(--frog-muted-foreground)]">
                  Primary Pane
                </div>
                <div className="w-16 rounded bg-[var(--frog-subtle-surface)] border-l border-[var(--frog-border)] flex items-center justify-center text-[8px] text-[var(--frog-muted-foreground)]">
                  Inspector
                </div>
              </>
            )}
          </div>

          <p className="text-xs text-[var(--frog-muted-foreground)] leading-relaxed">{item.pattern}</p>
        </div>
        <div className="pt-2 border-t border-[var(--frog-border)] text-[10px] font-mono text-[var(--frog-muted-foreground)]">
          FrogWindowSizeClass.{item.name}
        </div>
      </div>
    ))}
  </div>
);

const AccessibilityVisual = () => (
  <div className="grid gap-2.5 sm:grid-cols-2">
    {[
      { title: 'Semantic Roles & States', desc: 'Components announce role (Button, Switch, Pane) and active states (enabled, loading, selected).' },
      { title: 'Enforced 48dp Touch Targets', desc: 'Every interactive control guarantees ≥ 48dp clickable envelope via runtime contract.' },
      { title: 'Visible 2dp Focus Rings', desc: 'High-contrast focus ring with offset for keyboard, D-pad, and TV remote navigation.' },
      { title: 'TalkBack 7-Step Protocol', desc: 'Explicit label traversal without redundant announcements; verified across light and dark modes.' },
      { title: 'Font Scaling Up to 2.0x', desc: 'Layouts flex and wrap without text clipping, overlap, or touch-target shrinkage.' },
      { title: 'Non-Color Contrast & Cues', desc: 'All states provide icon and text semantics; errors never rely on red color alone.' },
      { title: 'Bi-directional (RTL) Layout', desc: 'Directional icon mirroring, slot alignment, and drawer docking respect LayoutDirection.' },
      { title: 'System Reduced Motion', desc: 'Live listener for Settings.Global.ANIMATOR_DURATION_SCALE snaps animations instantly.' }
    ].map(item => (
      <div key={item.title} className="p-3.5 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] space-y-1">
        <div className="flex items-center gap-2">
          <span className="w-4 h-4 rounded-full bg-emerald-500/10 text-emerald-600 dark:text-emerald-400 border border-emerald-500/20 flex items-center justify-center text-[10px] font-bold">
            ✓
          </span>
          <h4 className="text-xs font-semibold text-[var(--frog-foreground)]">{item.title}</h4>
        </div>
        <p className="text-[11px] leading-relaxed text-[var(--frog-muted-foreground)] pl-6">{item.desc}</p>
      </div>
    ))}
  </div>
);
const IntroPrinciplesVisual = () => (
  <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
    {[
      { title: 'Compose-Native', desc: 'Built directly as Kotlin composables, not a WebView, JavaScript runtime, or legacy XML wrapper.' },
      { title: 'Ownership-Friendly', desc: 'Use FrogUI where it helps, mix with standard Compose, and customize without losing control of application UI.' },
      { title: 'Accessible by Default', desc: '48dp touch targets, TalkBack roles, state descriptions, visible focus, and reduced motion are baseline requirements.' },
      { title: 'Adaptive Behavior', desc: 'Components automatically respond to available window constraints from compact phones to expanded tablet panes.' }
    ].map(item => (
      <div key={item.title} className="p-4 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)]">
        <h4 className="font-semibold text-xs text-[var(--frog-foreground)] mb-1">{item.title}</h4>
        <p className="text-xs leading-relaxed text-[var(--frog-muted-foreground)]">{item.desc}</p>
      </div>
    ))}
  </div>
);

const ModuleMapVisual = () => {
  const modules = [
    { name: 'frogui-foundation', role: 'Tokens', desc: 'Semantic colors, typography scale, 12-step spacing, shapes, elevation, motion, sizing, and adaptive policies.' },
    { name: 'frogui-theme', role: 'Runtime', desc: 'FrogTheme provider, CompositionLocals, Material bridge, dark mode, and system reduced-motion resolver.' },
    { name: 'frogui-components', role: 'Library', desc: 'Public UI primitives (FrogButton, FrogDrawer) consuming theme tokens with explicit state and accessibility.' },
    { name: 'app', role: 'Showcase', desc: 'Native Android showcase and laboratory for on-device testing and visual inspection. Not a consumer dependency.' }
  ];
  return (
    <div className="overflow-x-auto rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface)]">
      <table className="min-w-full text-left text-xs">
        <thead className="bg-[var(--frog-subtle-surface)] text-[var(--frog-foreground)] border-b border-[var(--frog-border)]">
          <tr>
            <th className="px-4 py-2.5 font-semibold">Module</th>
            <th className="px-4 py-2.5 font-semibold">Role</th>
            <th className="px-4 py-2.5 font-semibold">Responsibility</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-[var(--frog-border)] text-[var(--frog-muted-foreground)]">
          {modules.map(mod => (
            <tr key={mod.name}>
              <td className="px-4 py-3 font-mono font-medium text-[var(--frog-foreground)] whitespace-nowrap">{mod.name}</td>
              <td className="px-4 py-3 whitespace-nowrap"><span className="px-1.5 py-0.5 rounded text-[10px] font-semibold bg-[var(--frog-subtle-surface)] text-[var(--frog-foreground)] border border-[var(--frog-border)]">{mod.role}</span></td>
              <td className="px-4 py-3 leading-relaxed">{mod.desc}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

const InstallationRequirementsVisual = () => {
  const requirements = [
    { name: 'UI Toolkit', value: 'Jetpack Compose', notes: 'Native declarative UI' },
    { name: 'Language', value: 'Kotlin 2.0+ (Tested 2.2.10)', notes: 'Compose Compiler Plugin' },
    { name: 'Min Android SDK', value: 'API 24 (Android 7.0)', notes: 'defaultConfig minSdk = 24' },
    { name: 'Compile / Target SDK', value: 'API 36', notes: 'Modern Android toolchain' },
    { name: 'Java / JDK', value: 'Java 11+', notes: 'JVM target compatibility' },
    { name: 'Android Gradle Plugin', value: 'AGP 9.0+ (Tested 9.3.2)', notes: 'Build tools' },
    { name: 'Compose BOM', value: '2024.x – 2026.x', notes: 'Uses Compose BOM platform' }
  ];
  return (
    <div className="overflow-x-auto rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface)]">
      <table className="min-w-full text-left text-xs">
        <thead className="bg-[var(--frog-subtle-surface)] text-[var(--frog-foreground)] border-b border-[var(--frog-border)]">
          <tr>
            <th className="px-4 py-2.5 font-semibold">Requirement</th>
            <th className="px-4 py-2.5 font-semibold">Target / Verified Value</th>
            <th className="px-4 py-2.5 font-semibold">Notes</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-[var(--frog-border)] text-[var(--frog-muted-foreground)]">
          {requirements.map(req => (
            <tr key={req.name}>
              <td className="px-4 py-2.5 font-medium text-[var(--frog-foreground)] whitespace-nowrap">{req.name}</td>
              <td className="px-4 py-2.5 font-mono text-xs whitespace-nowrap text-[var(--frog-foreground)] font-semibold">{req.value}</td>
              <td className="px-4 py-2.5 text-[11px]">{req.notes}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

const ArchSystemMapVisual = () => {
  return (
    <div className="space-y-4 rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface)] p-5">
      <div className="flex items-center justify-between pb-3 border-b border-[var(--frog-border)]">
        <div>
          <span className="text-xs font-semibold uppercase tracking-wider text-[var(--frog-muted-foreground)]">System Map</span>
          <h4 className="text-sm font-bold text-[var(--frog-foreground)]">FrogUI Ecosystem Architecture</h4>
        </div>
        <span className="text-[10px] font-mono px-2 py-0.5 rounded bg-[var(--frog-subtle-surface)] text-[var(--frog-foreground)] border border-[var(--frog-border)]">
          Single-Direction Dependency
        </span>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {/* Android Library */}
        <div className="rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] p-4 space-y-2.5">
          <div className="flex items-center justify-between">
            <h5 className="text-xs font-bold text-[var(--frog-foreground)] flex items-center gap-1.5">
              <span className="w-2 h-2 rounded-full bg-emerald-500" />
              Android Library Stack
            </h5>
            <span className="text-[10px] font-mono px-1.5 py-0.5 rounded bg-emerald-500/10 text-emerald-600 dark:text-emerald-400 border border-emerald-500/20">
              Published AARs
            </span>
          </div>
          <p className="text-[11px] text-[var(--frog-muted-foreground)] leading-relaxed">
            The authoritative runtime UI implementation. Written in Kotlin and Jetpack Compose.
          </p>
          <div className="space-y-1.5 pt-1 text-xs font-mono">
            <div className="p-2 rounded bg-[var(--frog-subtle-surface)] border border-[var(--frog-border)] flex items-center justify-between">
              <span>:frogui-components</span>
              <span className="text-[10px] font-sans text-[var(--frog-muted-foreground)]">Actions & Overlays</span>
            </div>
            <div className="text-center text-[var(--frog-muted-foreground)] text-[10px]">▲ depends on</div>
            <div className="p-2 rounded bg-[var(--frog-subtle-surface)] border border-[var(--frog-border)] flex items-center justify-between">
              <span>:frogui-theme</span>
              <span className="text-[10px] font-sans text-[var(--frog-muted-foreground)]">FrogTheme & Locals</span>
            </div>
            <div className="text-center text-[var(--frog-muted-foreground)] text-[10px]">▲ depends on</div>
            <div className="p-2 rounded bg-[var(--frog-subtle-surface)] border border-[var(--frog-border)] flex items-center justify-between">
              <span>:frogui-foundation</span>
              <span className="text-[10px] font-sans text-[var(--frog-muted-foreground)]">Tokens & Primitives</span>
            </div>
          </div>
        </div>

        {/* Native Showcase */}
        <div className="rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] p-4 space-y-2.5">
          <div className="flex items-center justify-between">
            <h5 className="text-xs font-bold text-[var(--frog-foreground)] flex items-center gap-1.5">
              <span className="w-2 h-2 rounded-full bg-blue-500" />
              Native Showcase
            </h5>
            <span className="text-[10px] font-mono px-1.5 py-0.5 rounded bg-blue-500/10 text-blue-600 dark:text-blue-400 border border-blue-500/20">
              :app (Internal)
            </span>
          </div>
          <p className="text-[11px] text-[var(--frog-muted-foreground)] leading-relaxed">
            On-device interactive laboratory and component inspection environment.
          </p>
          <div className="p-2.5 rounded bg-[var(--frog-subtle-surface)] border border-[var(--frog-border)] space-y-1 text-xs">
            <div className="font-semibold text-[var(--frog-foreground)]">ComponentDetailScreen</div>
            <p className="text-[11px] text-[var(--frog-muted-foreground)] leading-relaxed">
              Unified laboratory shell: live preview canvas, property inspectors, state toggles, TalkBack labels, and generated code export.
            </p>
          </div>
          <div className="text-[10px] text-[var(--frog-muted-foreground)] border-t border-[var(--frog-border)] pt-2">
            Consumes: :frogui-components, :frogui-theme, :frogui-foundation, :frogui-registry
          </div>
        </div>

        {/* Registry & Metadata */}
        <div className="rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] p-4 space-y-2.5">
          <div className="flex items-center justify-between">
            <h5 className="text-xs font-bold text-[var(--frog-foreground)] flex items-center gap-1.5">
              <span className="w-2 h-2 rounded-full bg-amber-500" />
              Registry & Discovery
            </h5>
            <span className="text-[10px] font-mono px-1.5 py-0.5 rounded bg-amber-500/10 text-amber-600 dark:text-amber-400 border border-amber-500/20">
              registry/ + :frogui-registry
            </span>
          </div>
          <p className="text-[11px] text-[var(--frog-muted-foreground)] leading-relaxed">
            Machine-readable JSON schema (Draft-07) and Ajv validation. Never executes at runtime.
          </p>
          <div className="p-2 rounded bg-[var(--frog-subtle-surface)] border border-[var(--frog-border)] space-y-1 text-xs font-mono">
            <div className="text-[var(--frog-foreground)]">registry/components/*.json</div>
            <div className="text-[10px] text-[var(--frog-muted-foreground)] font-sans">
              → Generated Kotlin catalog for Showcase
            </div>
            <div className="text-[10px] text-[var(--frog-muted-foreground)] font-sans">
              → Generated TypeScript catalog for Web Docs
            </div>
          </div>
        </div>

        {/* Web Documentation */}
        <div className="rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] p-4 space-y-2.5">
          <div className="flex items-center justify-between">
            <h5 className="text-xs font-bold text-[var(--frog-foreground)] flex items-center gap-1.5">
              <span className="w-2 h-2 rounded-full bg-purple-500" />
              Web Documentation
            </h5>
            <span className="text-[10px] font-mono px-1.5 py-0.5 rounded bg-purple-500/10 text-purple-600 dark:text-purple-400 border border-purple-500/20">
              docs/ (GitHub Pages)
            </span>
          </div>
          <p className="text-[11px] text-[var(--frog-muted-foreground)] leading-relaxed">
            Static documentation platform built with React 19, TypeScript, Vite 6, and Tailwind CSS 4.
          </p>
          <div className="p-2.5 rounded bg-[var(--frog-subtle-surface)] border border-[var(--frog-border)] space-y-1 text-xs">
            <div className="font-semibold text-[var(--frog-foreground)]">Web Docs ≠ Android Runtime</div>
            <p className="text-[11px] text-[var(--frog-muted-foreground)] leading-relaxed">
              Provides API references, architecture guides, design tokens, and isolated representative previews. Does not execute Compose.
            </p>
          </div>
        </div>
      </div>

      {/* Build Logic */}
      <div className="p-3 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-subtle-surface)] flex flex-wrap items-center justify-between gap-2 text-xs">
        <span className="font-semibold text-[var(--frog-foreground)]">Build Logic & CI Automation:</span>
        <span className="text-[11px] text-[var(--frog-muted-foreground)] font-mono">
          build-logic (frogui.android.library · frogui.publishing · frogui.api.validation) | GitHub Actions CI
        </span>
      </div>
    </div>
  );
};

const ArchDependenciesVisual = () => {
  const allowed = [
    { from: ':frogui-foundation', to: 'None', desc: 'Zero project dependencies; primitive tokens and models only.' },
    { from: ':frogui-theme', to: ':frogui-foundation', desc: 'Consumes foundation tokens; provides FrogTheme & CompositionLocals.' },
    { from: ':frogui-components', to: ':frogui-foundation, :frogui-theme', desc: 'Consumes tokens and theme; exports public Compose components.' },
    { from: ':frogui-registry', to: 'None', desc: 'Generated immutable metadata; depends only on Compose runtime.' },
    { from: ':app (Showcase)', to: 'All 4 modules above', desc: 'Offline catalog, component laboratory, inspector, and examples.' }
  ];

  const prohibited = [
    { edge: 'Library → Showcase (:app)', reason: 'Showcase code, state, and assets must never leak into reusable library AARs.' },
    { edge: ':frogui-foundation → Material3', reason: 'Foundation is pure Compose runtime and graphics; no Material dependency allowed.' },
    { edge: 'Reusable Library → :frogui-testing', reason: 'Test harnesses are restricted to test configurations and never ship to consumers.' },
    { edge: ':frogui-components → Hugeicons / Coil', reason: 'Core components use composable slots; no icon-pack or image loader lock-in.' },
    { edge: 'Core → Networking / DI / Database', reason: 'Zero HTTP clients, Retrofit, Ktor, Hilt, Room, or DataStore in UI libraries.' }
  ];

  return (
    <div className="space-y-4">
      <div className="overflow-x-auto rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface)]">
        <table className="min-w-full text-left text-xs">
          <thead className="bg-[var(--frog-subtle-surface)] text-[var(--frog-foreground)] border-b border-[var(--frog-border)]">
            <tr>
              <th className="px-4 py-2.5 font-semibold">Module</th>
              <th className="px-4 py-2.5 font-semibold">Allowed Production Edges</th>
              <th className="px-4 py-2.5 font-semibold">Architectural Role</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-[var(--frog-border)] text-[var(--frog-muted-foreground)]">
            {allowed.map(row => (
              <tr key={row.from}>
                <td className="px-4 py-2.5 font-mono font-medium text-[var(--frog-foreground)] whitespace-nowrap">{row.from}</td>
                <td className="px-4 py-2.5 font-mono text-[11px] text-emerald-600 dark:text-emerald-400 whitespace-nowrap">{row.to}</td>
                <td className="px-4 py-2.5 text-[11px] leading-relaxed">{row.desc}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="p-4 rounded-lg border border-red-500/20 bg-red-500/5 space-y-2">
        <h5 className="text-xs font-bold text-red-600 dark:text-red-400 flex items-center gap-1.5">
          <span className="w-1.5 h-1.5 rounded-full bg-red-500" />
          Strictly Prohibited Reverse & Leaked Dependencies
        </h5>
        <div className="grid gap-1.5 text-xs">
          {prohibited.map(item => (
            <div key={item.edge} className="flex flex-col sm:flex-row sm:items-baseline gap-1 text-[11px]">
              <span className="font-mono font-semibold text-red-600 dark:text-red-400 min-w-56">{item.edge}</span>
              <span className="text-[var(--frog-muted-foreground)]">{item.reason}</span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

const ArchSourceOfTruthVisual = () => {
  const sources = [
    { concern: 'Runtime Implementation', source: 'Kotlin Library (:frogui-components)', authority: 'Jetpack Compose source code', consumer: 'Android Applications & Showcase' },
    { concern: 'Public API Contract & ABI', source: 'Kotlin Declarations & BCV .api', authority: 'Binary Compatibility Validator', consumer: 'Gradle apiCheck & release builds' },
    { concern: 'Theme Runtime & Tokens', source: 'FrogTheme & CompositionLocals', authority: ':frogui-theme / :frogui-foundation', consumer: 'Components & custom application UI' },
    { concern: 'Component Identity & Status', source: 'registry/components/<id>.json', authority: 'JSON Schema (Draft-07) + Ajv', consumer: 'Showcase routes, search, web docs' },
    { concern: 'Interactive Verification', source: 'Android Showcase (:app)', authority: 'Native device Compose execution', consumer: 'Developers, QA, TalkBack testers' },
    { concern: 'Long-Form Guidance', source: 'docs/src & docs/content', authority: 'Web documentation platform', consumer: 'Developers, contributors, maintainers' },
    { concern: 'Release Version', source: 'gradle/release.properties', authority: 'Root Gradle build properties', consumer: 'Publishing scripts, catalog, POMs' }
  ];

  return (
    <div className="overflow-x-auto rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface)]">
      <table className="min-w-full text-left text-xs">
        <thead className="bg-[var(--frog-subtle-surface)] text-[var(--frog-foreground)] border-b border-[var(--frog-border)]">
          <tr>
            <th className="px-4 py-2.5 font-semibold">Architectural Concern</th>
            <th className="px-4 py-2.5 font-semibold">Canonical Source of Truth</th>
            <th className="px-4 py-2.5 font-semibold">Enforcement / Consumer</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-[var(--frog-border)] text-[var(--frog-muted-foreground)]">
          {sources.map(s => (
            <tr key={s.concern}>
              <td className="px-4 py-2.5 font-medium text-[var(--frog-foreground)] whitespace-nowrap">{s.concern}</td>
              <td className="px-4 py-2.5 font-mono text-[11px] text-[var(--frog-foreground)] whitespace-nowrap">{s.source}</td>
              <td className="px-4 py-2.5 text-[11px]">{s.consumer}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

const ArchTechMatrixVisual = () => {
  const techs = [
    { area: 'Language', tech: 'Kotlin 2.2.10', why: 'First-class Compose compiler integration, explicit nullability, and @Immutable / @Stable models.' },
    { area: 'UI Toolkit', tech: 'Jetpack Compose', why: 'Declarative native UI with Modifier, state hoisting, slot APIs, and accessibility semantics. Zero legacy XML in v1.' },
    { area: 'Build System', tech: 'Gradle Kotlin DSL', why: 'Type-safe build configuration with convention plugins in build-logic for library, publishing, and ABI validation.' },
    { area: 'Version Catalog', tech: 'gradle/libs.versions.toml', why: 'Centralized dependency versions, AGP 9.3.2, compileSdk 36, minSdk 24, Compose BOM 2026.02.01.' },
    { area: 'Async State', tech: 'Coroutines & Flow', why: 'Applied strictly where async genuinely exists (FrogDrawerState.open()/close() suspend functions); no async bloat on sync components.' },
    { area: 'Metadata Registry', tech: 'JSON Schema + Ajv 8.20', why: 'Machine-readable cross-tool contracts generating typed Kotlin and TypeScript discovery data without runtime reflection.' },
    { area: 'Web Docs', tech: 'React 19 + Vite 6 + Tailwind 4', why: 'Fast, responsive static documentation deployed directly to GitHub Pages without Compose web runtime overhead.' },
    { area: 'Syntax Highlighting', tech: 'Shiki 3.1.0 (Web) / Highlights (Native)', why: 'Showcase renders native Compose code with Highlights; Web Docs renders Shiki. Zero runtime code sharing.' },
    { area: 'API Compatibility', tech: 'Binary Compatibility Validator 0.18.1', why: 'Release AAR ABI extraction and baseline verification; apiCheck fails on unreviewed public surface changes.' },
    { area: 'Continuous Integration', tech: 'GitHub Actions (5 workflows)', why: 'Automated verification across android-ci, docs-ci, docs-deploy, registry-docs, and manual release staging.' }
  ];

  return (
    <div className="overflow-x-auto rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface)]">
      <table className="min-w-full text-left text-xs">
        <thead className="bg-[var(--frog-subtle-surface)] text-[var(--frog-foreground)] border-b border-[var(--frog-border)]">
          <tr>
            <th className="px-4 py-2.5 font-semibold">Area</th>
            <th className="px-4 py-2.5 font-semibold">Canonical Technology</th>
            <th className="px-4 py-2.5 font-semibold">Boundary & Rationale</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-[var(--frog-border)] text-[var(--frog-muted-foreground)]">
          {techs.map(t => (
            <tr key={t.area}>
              <td className="px-4 py-2.5 font-medium text-[var(--frog-foreground)] whitespace-nowrap">{t.area}</td>
              <td className="px-4 py-2.5 font-mono text-[11px] text-[var(--frog-foreground)] whitespace-nowrap">{t.tech}</td>
              <td className="px-4 py-2.5 text-[11px] leading-relaxed">{t.why}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

const ArchRepoTreeVisual = () => {
  const tree = [
    { path: 'frogui-foundation/', role: 'Android Library', desc: 'Primitive tokens: colors, typography scale, 12-step spacing, shapes, elevation, motion, sizing, adaptive breakpoints.' },
    { path: 'frogui-theme/', role: 'Android Library', desc: 'Theme runtime: FrogTheme provider, CompositionLocals, system reduced motion listener, Material bridge.' },
    { path: 'frogui-components/', role: 'Android Library', desc: 'Public components: FrogButton, FrogDrawer, semantic variants, Defaults objects, slot contracts.' },
    { path: 'frogui-registry/', role: 'Android Module', desc: 'Generated immutable catalog models and route descriptors for native Showcase consumption.' },
    { path: 'frogui-testing/', role: 'Test Only', desc: 'Internal test harness fixture for theme and compose test execution. Never published.' },
    { path: 'app/', role: 'Application', desc: 'Native Android Showcase: ComponentDetailScreen laboratory, property inspectors, and compiled examples.' },
    { path: 'build-logic/', role: 'Convention Plugins', desc: 'frogui.android.library, frogui.publishing, and frogui.api.validation Gradle plugins.' },
    { path: 'registry/', role: 'Metadata Contract', desc: 'schema/ (Draft-07 schemas) and components/ (JSON component records: button.json, drawer.json).' },
    { path: 'docs/', role: 'Web Documentation', desc: 'React 19, TypeScript, Vite 6, Tailwind 4 documentation platform deployed to GitHub Pages.' },
    { path: 'tools/', role: 'Tooling', desc: 'Node.js generation and validation scripts: tools/registry/, tools/icons/, tools/theme/.' },
    { path: '.github/workflows/', role: 'CI / CD', desc: 'android-ci.yml, docs-ci.yml, docs-deploy.yml, registry-docs.yml, release.yml.' }
  ];

  return (
    <div className="overflow-x-auto rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface)]">
      <table className="min-w-full text-left text-xs">
        <thead className="bg-[var(--frog-subtle-surface)] text-[var(--frog-foreground)] border-b border-[var(--frog-border)]">
          <tr>
            <th className="px-4 py-2.5 font-semibold">Directory / Module</th>
            <th className="px-4 py-2.5 font-semibold">Classification</th>
            <th className="px-4 py-2.5 font-semibold">Responsibility</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-[var(--frog-border)] text-[var(--frog-muted-foreground)]">
          {tree.map(item => (
            <tr key={item.path}>
              <td className="px-4 py-2.5 font-mono font-semibold text-[var(--frog-foreground)] whitespace-nowrap">{item.path}</td>
              <td className="px-4 py-2.5 whitespace-nowrap">
                <span className="px-1.5 py-0.5 rounded text-[10px] font-mono bg-[var(--frog-subtle-surface)] text-[var(--frog-foreground)] border border-[var(--frog-border)]">
                  {item.role}
                </span>
              </td>
              <td className="px-4 py-2.5 text-[11px] leading-relaxed">{item.desc}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

const ArchApiOrderVisual = () => {
  const slots = [
    { index: '1', name: 'Required Content / Value', example: 'value: String, title: String', desc: 'Essential data displayed by the component.' },
    { index: '2', name: 'Required Behavior Callback', example: 'onClick: () -> Unit, onCheckedChange: (Boolean) -> Unit', desc: 'Explicit user action intent callback.' },
    { index: '3', name: 'Modifier', example: 'modifier: Modifier = Modifier', desc: 'First optional parameter for layout and ornamentation.' },
    { index: '4', name: 'Semantic Variant & Size', example: 'variant: FrogButtonVariant = Primary, size: FrogButtonSize = Medium', desc: 'High-level design intent enums, not boolean explosion.' },
    { index: '5', name: 'Caller-Owned State', example: 'enabled: Boolean = true, loading: Boolean = false', desc: 'Component visual states owned entirely by the caller.' },
    { index: '6', name: 'Layout Parameters', example: 'fullWidth: Boolean = false, presentation: FrogDrawerPresentation', desc: 'Component-specific layout constraints and presentation modes.' },
    { index: '7', name: 'Composable Slots', example: 'leadingIcon: (@Composable () -> Unit)? = null', desc: 'Composable extension slots; vendor and icon-pack independent.' },
    { index: '8', name: 'Theme Defaults & Style', example: 'colors: FrogButtonColors = FrogButtonDefaults.colors()', desc: 'Theme-aware token contracts with granular override options.' },
    { index: '9', name: 'Interaction Observation', example: 'interactionSource: MutableInteractionSource? = null', desc: 'Advanced observation hook for press, focus, and hover state.' }
  ];

  return (
    <div className="space-y-2 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface)] p-4">
      <div className="text-xs font-bold text-[var(--frog-foreground)] mb-2">Canonical Parameter Ordering Standard</div>
      <div className="grid gap-2">
        {slots.map(s => (
          <div key={s.index} className="flex items-baseline gap-3 p-2 rounded bg-[var(--frog-surface-elevated)] border border-[var(--frog-border)] text-xs">
            <span className="w-5 h-5 rounded-full bg-[var(--frog-subtle-surface)] text-[var(--frog-foreground)] border border-[var(--frog-border)] flex items-center justify-center text-[10px] font-mono font-bold shrink-0">
              {s.index}
            </span>
            <div className="min-w-0 flex-1">
              <div className="flex flex-wrap items-baseline gap-2">
                <span className="font-semibold text-[var(--frog-foreground)]">{s.name}</span>
                <code className="text-[10px] font-mono text-[var(--frog-muted-foreground)]">{s.example}</code>
              </div>
              <p className="text-[11px] text-[var(--frog-muted-foreground)] mt-0.5">{s.desc}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

const ArchLifecycleVisual = () => {
  const gates = [
    { num: '01', title: 'Specification', desc: 'Purpose, state ownership, variants, invalid states, a11y, and adaptive behavior defined.' },
    { num: '02', title: 'Public API', desc: 'Composable signature, parameter order, slots, and theme-aware Defaults designed and reviewed.' },
    { num: '03', title: 'Implementation', desc: 'One canonical Compose component implemented; zero Material, Hugeicons, or demo leaks.' },
    { num: '04', title: 'Theme / States', desc: 'Defaults resolve FrogTheme tokens for enabled, disabled, pressed, focused, and loading.' },
    { num: '05', title: 'Accessibility', desc: 'Semantics role, 48dp minimum touch target, visible focus ring, TalkBack annotations.' },
    { num: '06', title: 'Compose Previews', desc: 'FrogComponentPreview covers variants, sizes, states, dark theme, and customization.' },
    { num: '07', title: 'Automated Tests', desc: 'JVM unit and Compose tests cover activation, dismissal, restoration, and semantics.' },
    { num: '08', title: 'Registry Metadata', desc: 'JSON record created with schema v2 evidence, properties, examples, and routes.' },
    { num: '09', title: 'Showcase Integration', desc: 'ComponentShowcaseDefinition supplies component, typed controls, and inspector to detail screen.' },
    { num: '10', title: 'Web Documentation', desc: 'Shared ComponentDetailPage renders prose, API table, accessibility facts, and preview.' },
    { num: '11', title: 'API Compatibility', desc: 'Binary ABI extracted, verified against version-controlled baseline via apiCheck.' },
    { num: '12', title: 'Visual Regression', desc: 'High-value native screen captures and deterministic pixel assertions reviewed.' }
  ];

  return (
    <div className="space-y-4">
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-2.5">
        {gates.map(g => (
          <div key={g.num} className="p-3 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] space-y-1">
            <div className="flex items-center gap-2">
              <span className="text-[10px] font-mono font-bold px-1.5 py-0.5 rounded bg-[var(--frog-subtle-surface)] text-[var(--frog-foreground)] border border-[var(--frog-border)]">
                {g.num}
              </span>
              <h5 className="text-xs font-semibold text-[var(--frog-foreground)]">{g.title}</h5>
            </div>
            <p className="text-[11px] text-[var(--frog-muted-foreground)] leading-relaxed pl-7">{g.desc}</p>
          </div>
        ))}
      </div>

      <div className="p-3 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-subtle-surface)] flex flex-wrap items-center justify-between gap-2 text-xs">
        <span className="font-semibold text-[var(--frog-foreground)]">Promotion Criteria:</span>
        <div className="flex flex-wrap gap-2 text-[10px] font-mono">
          <span className="px-2 py-0.5 rounded bg-amber-500/10 text-amber-600 dark:text-amber-400 border border-amber-500/20">Experimental: Partial gates permitted</span>
          <span className="px-2 py-0.5 rounded bg-blue-500/10 text-blue-600 dark:text-blue-400 border border-blue-500/20">Beta: No missing or outdated gates</span>
          <span className="px-2 py-0.5 rounded bg-emerald-500/10 text-emerald-600 dark:text-emerald-400 border border-emerald-500/20">Stable: All 12 gates complete + stability review</span>
        </div>
      </div>
    </div>
  );
};

const ArchRegistryFlowVisual = () => {
  return (
    <div className="rounded-xl border border-[var(--frog-border)] bg-[var(--frog-surface)] p-5 space-y-4">
      <div className="text-xs font-semibold uppercase tracking-wider text-[var(--frog-muted-foreground)]">Registry Pipeline</div>
      <div className="space-y-3 font-mono text-xs">
        <div className="p-3 rounded-lg bg-[var(--frog-surface-elevated)] border border-[var(--frog-border)] space-y-1">
          <div className="text-[var(--frog-foreground)] font-bold">1. Authoritative Source Records</div>
          <div className="text-[11px] text-[var(--frog-muted-foreground)] font-sans">
            • <code>registry/schema/*.schema.json</code> (Draft-07 schemas for component, example, and index)<br />
            • <code>registry/components/button.json, drawer.json</code> (Canonical metadata, properties, examples, evidence)<br />
            • <code>app/.../showcase/components/.../*.kt</code> (Real compiled Showcase example regions)
          </div>
        </div>

        <div className="text-center text-[var(--frog-muted-foreground)] text-xs">▼ validated by tools/registry/registry.mjs (Ajv)</div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
          <div className="p-3 rounded-lg bg-[var(--frog-surface-elevated)] border border-[var(--frog-border)] space-y-1">
            <div className="text-[var(--frog-foreground)] font-bold">2a. Native Android Projection</div>
            <div className="text-[11px] text-[var(--frog-muted-foreground)] font-sans">
              <code>GeneratedComponentRegistry.kt</code><br />
              Generated inside <code>frogui-registry/build/</code> during build. Consumed by Showcase for route registration, search, and detail screens.
            </div>
          </div>
          <div className="p-3 rounded-lg bg-[var(--frog-surface-elevated)] border border-[var(--frog-border)] space-y-1">
            <div className="text-[var(--frog-foreground)] font-bold">2b. Web Documentation Projection</div>
            <div className="text-[11px] text-[var(--frog-muted-foreground)] font-sans">
              <code>docs/dist/catalog.json</code> & <code>search.json</code><br />
              Generated via <code>docs/scripts/build.mjs</code>. Powers web navigation, API property tables, status badges, and search dialog.
            </div>
          </div>
        </div>
      </div>
      <div className="p-2.5 rounded bg-[var(--frog-subtle-surface)] border border-[var(--frog-border)] text-[11px] text-[var(--frog-muted-foreground)]">
        <strong>Rule:</strong> Generated files in <code>build/</code> or <code>src/generated/</code> are ignored by git and never edited by hand.
      </div>
    </div>
  );
};

const ArchReleaseFlowVisual = () => {
  const steps = [
    { step: '1', name: 'Pull Request Gate', desc: 'Compiles Android code, runs testDebugUnitTest, lintDebug, validateRegistry, and builds docs static bundle.' },
    { step: '2', name: 'Architecture & API Check', desc: 'Executes verifyArchitecture and apiCheck. Compares release AAR ABI with version-controlled baseline.' },
    { step: '3', name: 'Merge & Versioning', desc: 'Merges to main. gradle/release.properties defines version (0.1.0-SNAPSHOT), versionCode, and published status.' },
    { step: '4', name: 'Local Maven Staging', desc: 'publishAllPublicationsToBuildRepository packages release AAR, sources JAR, and POM to build/maven.' },
    { step: '5', name: 'GitHub Pages Deployment', desc: 'docs-deploy.yml builds static Vite bundle, verifies 404/base-path integrity, and deploys to GitHub Pages.' },
    { step: '6', name: 'Remote Publishing (Planned)', desc: 'Signed release to Maven Central with credentials from GitHub Secrets. Kept separate from staging.' }
  ];

  return (
    <div className="space-y-3">
      {steps.map(s => (
        <div key={s.step} className="flex items-baseline gap-3 p-3 rounded-lg border border-[var(--frog-border)] bg-[var(--frog-surface-elevated)] text-xs">
          <span className="w-6 h-6 rounded-full bg-[var(--frog-subtle-surface)] text-[var(--frog-foreground)] border border-[var(--frog-border)] flex items-center justify-center text-[10px] font-mono font-bold shrink-0">
            {s.step}
          </span>
          <div className="min-w-0 flex-1">
            <h5 className="font-semibold text-[var(--frog-foreground)]">{s.name}</h5>
            <p className="text-[11px] text-[var(--frog-muted-foreground)] mt-0.5 leading-relaxed">{s.desc}</p>
          </div>
        </div>
      ))}
    </div>
  );
};

const ModulesVisual = () => <ArchRepoTreeVisual />;
const PipelineVisual = () => <ArchLifecycleVisual />;

export const DocumentationVisual: React.FC<DocumentationVisualProps> = ({ kind, onNavigate }) => {
  const visuals: Record<string, React.ReactNode> = {
    'foundation-index': <IndexVisual prefix="/foundations" onNavigate={onNavigate} />,
    'architecture-index': <IndexVisual prefix="/architecture" onNavigate={onNavigate} />,
    'component-index': <ComponentIndex onNavigate={onNavigate} />,
    'intro-principles': <IntroPrinciplesVisual />,
    'module-map': <ModuleMapVisual />,
    'installation-requirements': <InstallationRequirementsVisual />,
    colors: <ColorVisual />, typography: <TypographyVisual />, spacing: <SpacingVisual />, shapes: <ShapesVisual />,
    elevation: <ElevationVisual />, motion: <MotionVisual />, sizing: <SizingVisual />, adaptive: <AdaptiveVisual />,
    accessibility: <AccessibilityVisual />, modules: <ModulesVisual />, pipeline: <PipelineVisual />,
    'arch-system-map': <ArchSystemMapVisual />,
    'arch-dependencies': <ArchDependenciesVisual />,
    'arch-source-of-truth': <ArchSourceOfTruthVisual />,
    'arch-tech-matrix': <ArchTechMatrixVisual />,
    'arch-repo-tree': <ArchRepoTreeVisual />,
    'arch-api-order': <ArchApiOrderVisual />,
    'arch-lifecycle': <ArchLifecycleVisual />,
    'arch-registry-flow': <ArchRegistryFlowVisual />,
    'arch-release-flow': <ArchReleaseFlowVisual />
  };
  return <div className="mt-5">{visuals[kind] ?? null}</div>;
};

