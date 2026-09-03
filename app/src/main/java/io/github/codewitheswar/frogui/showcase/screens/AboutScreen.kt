package io.github.codewitheswar.frogui.showcase.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.BuildConfig
import io.github.codewitheswar.frogui.showcase.markdown.FrogMarkdown
import io.github.codewitheswar.frogui.showcase.code.CodeLanguage
import io.github.codewitheswar.frogui.showcase.code.FrogCodeSnippet
import io.github.codewitheswar.frogui.theme.FrogTheme

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    val assets = LocalContext.current.assets
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("FrogUI ${BuildConfig.VERSION_NAME}", style = FrogTheme.typography.title, color = FrogTheme.colors.foreground)
        FrogMarkdown("""
            Native Android components with caller-owned state, composable slots, and a monochrome token system.

            ## Project
            - Kotlin and Jetpack Compose
            - Android 7.0 (API 24) and later
            - Foundation, theme, components, registry, and native showcase modules

            [Source repository](https://github.com/CodeWithEswar/FrogUI)

            ## Third-party notices
            The showcase uses **Hugeicons** Stroke Rounded (MIT), **Highlights** (Apache 2.0), and **CommonMark Java** with the GFM tables extension (BSD 2-Clause).
        """.trimIndent())
        listOf("hugeicons" to "Hugeicons · MIT", "highlights" to "Highlights · Apache 2.0", "commonmark" to "CommonMark · BSD 2-Clause").forEach { (name, title) ->
            val notice = remember(name, assets) { runCatching { assets.open("licenses/$name.txt").bufferedReader().use { it.readText() } }.getOrDefault("License notice unavailable.") }
            FrogCodeSnippet(notice, language = CodeLanguage.PlainText, filename = title)
        }
    }
}
