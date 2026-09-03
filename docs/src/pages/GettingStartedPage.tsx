import React from 'react';
import { CodeBlock } from '../components/ui/CodeBlock';
import { Callout } from '../components/ui/Callout';
import { release } from '../generated/catalog';

interface GettingStartedPageProps {
  section?: 'introduction' | 'installation' | 'quick-start';
  onNavigate: (path: string) => void;
}

export const GettingStartedPage: React.FC<GettingStartedPageProps> = ({
  section = 'introduction',
  onNavigate
}) => {
  return (
    <article className="max-w-3xl space-y-10">
      <header className="space-y-3 pb-6 border-b border-zinc-200 dark:border-zinc-800">
        <h1 className="text-3xl sm:text-4xl font-extrabold tracking-tight text-zinc-900 dark:text-zinc-50">
          {section === 'installation'
            ? 'Installation'
            : section === 'quick-start'
            ? 'Quick Start'
            : 'Introduction'}
        </h1>
        <p className="text-base text-zinc-600 dark:text-zinc-400 leading-relaxed">
          {section === 'installation'
            ? 'Add FrogUI components and theme tokens to your Android Gradle configuration.'
            : section === 'quick-start'
            ? 'A quick guide to building your first UI using FrogUI and Jetpack Compose.'
            : 'Predictable, customizable, accessible, ownership-friendly Jetpack Compose UI ecosystem.'}
        </p>
      </header>

      {/* Introduction Content */}
      {(section === 'introduction' || !section) && (
        <div className="space-y-6 text-sm text-zinc-600 dark:text-zinc-400 leading-relaxed">
          <p>
            FrogUI is not a monolithic application framework or a generic demo app. It is an open-source, production-grade Android UI ecosystem designed to make native Compose development fast, predictable, and maintainable.
          </p>

          <h2 className="text-lg font-bold text-zinc-900 dark:text-zinc-100 pt-4">
            Core Tenets
          </h2>

          <ul className="list-disc pl-5 space-y-2">
            <li>
              <strong className="text-zinc-800 dark:text-zinc-200">Predictability:</strong> No surprise side effects, hidden persistence, or magical global states.
            </li>
            <li>
              <strong className="text-zinc-800 dark:text-zinc-200">Composability:</strong> Slots and idiomatic Compose parameters rather than rigid configuration bags.
            </li>
            <li>
              <strong className="text-zinc-800 dark:text-zinc-200">Developer Ownership:</strong> Applications own their navigation, DI, state, and architecture.
            </li>
            <li>
              <strong className="text-zinc-800 dark:text-zinc-200">Accessibility:</strong> 48dp touch targets, TalkBack support, font scaling (2x), and tested contrast are mandatory.
            </li>
          </ul>

          <Callout type="note" title="Compose First">
            <p>
              FrogUI v1.0 targets Jetpack Compose natively. Standard Compose-in-View interop is fully supported, but parallel legacy XML View adapters are not maintained.
            </p>
          </Callout>

          <div className="pt-4 flex gap-3">
            <button
              onClick={() => onNavigate('/docs/installation')}
              className="px-4 py-2 rounded-lg bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 font-medium text-xs hover:bg-zinc-800 dark:hover:bg-white transition-colors"
            >
              Continue to Installation &rarr;
            </button>
          </div>
        </div>
      )}

      {/* Installation Content */}
      {section === 'installation' && (
        <div className="space-y-6 text-sm text-zinc-600 dark:text-zinc-400 leading-relaxed">
          <p>
            FrogUI is divided into focused modules so you only import what you need.
          </p>

          <h2 className="text-lg font-bold text-zinc-900 dark:text-zinc-100">
            1. Gradle Dependencies
          </h2>

          <p>
            Add the following modules to your app&apos;s <code className="font-mono text-xs">build.gradle.kts</code>:
          </p>

          <CodeBlock
            language="kotlin"
            title="app/build.gradle.kts"
            code={`dependencies {
    // Design tokens and Theme engine
    implementation("io.github.codewitheswar.frogui:frogui-theme:${release.version}")

    // Reusable Jetpack Compose Components
    implementation("io.github.codewitheswar.frogui:frogui-components:${release.version}")
}`}
          />

          <Callout type="tip" title="Repository Setup">
            <p>
              Ensure you have <code className="font-mono text-xs">mavenCentral()</code> configured in your root <code className="font-mono text-xs">settings.gradle.kts</code>.
            </p>
          </Callout>

          <div className="pt-4 flex gap-3">
            <button
              onClick={() => onNavigate('/docs/quick-start')}
              className="px-4 py-2 rounded-lg bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 font-medium text-xs hover:bg-zinc-800 dark:hover:bg-white transition-colors"
            >
              Next: Quick Start &rarr;
            </button>
          </div>
        </div>
      )}

      {/* Quick Start Content */}
      {section === 'quick-start' && (
        <div className="space-y-6 text-sm text-zinc-600 dark:text-zinc-400 leading-relaxed">
          <p>
            Wrap your Compose hierarchy in <code className="font-mono text-xs">FrogTheme</code> to provide semantic colors, typography, shapes, and motion tokens.
          </p>

          <CodeBlock
            language="kotlin"
            title="MainActivity.kt"
            code={`package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.components.button.FrogButton
import io.github.codewitheswar.frogui.components.button.FrogButtonVariant

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrogTheme {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    var count by remember { mutableStateOf(0) }

                    FrogButton(
                        variant = FrogButtonVariant.Primary,
                        onClick = { count++ }
                    ) {
                        Text("Clicked $count times")
                    }
                }
            }
        }
    }
}`}
          />

          <Callout type="note" title="Local Theme Tokens">
            <p>
              Inside <code className="font-mono text-xs">FrogTheme</code>, you can access design tokens directly via <code className="font-mono text-xs">FrogTheme.colors</code>, <code className="font-mono text-xs">FrogTheme.typography</code>, and <code className="font-mono text-xs">FrogTheme.spacing</code>.
            </p>
          </Callout>

          <div className="pt-4 flex gap-3">
            <button
              onClick={() => onNavigate('/components/button')}
              className="px-4 py-2 rounded-lg bg-zinc-900 text-white dark:bg-zinc-100 dark:text-zinc-900 font-medium text-xs hover:bg-zinc-800 dark:hover:bg-white transition-colors"
            >
              Explore Components &rarr;
            </button>
          </div>
        </div>
      )}
    </article>
  );
};
