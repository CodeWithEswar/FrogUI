package io.github.codewitheswar.frogui.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.navigation.*
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry
import io.github.codewitheswar.frogui.showcase.code.FrogCodeBlock
import io.github.codewitheswar.frogui.showcase.components.button.ButtonDemoState
import io.github.codewitheswar.frogui.showcase.components.button.PropertyInspector
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.markdown.FrogApiTable
import io.github.codewitheswar.frogui.showcase.markdown.FrogMarkdown
import io.github.codewitheswar.frogui.showcase.style.*
import io.github.codewitheswar.frogui.theme.FrogTheme

@Preview(name = "Toolbar and dock · light", widthDp = 390)
@Composable
private fun ShellChromeLight() = ChromePreview(false)
@Preview(name = "Toolbar and dock · dark compact", widthDp = 360)
@Composable
private fun ShellChromeDark() = ChromePreview(true)
@Composable
private fun ChromePreview(dark: Boolean) {
    FrogTheme(darkTheme = dark) {
        CompositionLocalProvider(LocalFrogMotionEnabled provides false) {
            Column(Modifier.background(FrogTheme.colors.background)) {
                FrogShowcaseTopBar("Button", subtitle = "Actions · Experimental", navigationIcon = { ShowcaseBackButton(onClick = {}) })
                Spacer(Modifier.height(24.dp))
                FrogShowcaseBottomBar(showcaseDestinations, "components", {})
            }
        }
    }
}
@Preview(name = "Rail", widthDp = 108, heightDp = 700)
@Composable
private fun RailPreview() { FrogTheme(darkTheme = false) { FrogNavigationRail("components", {}) } }
@Preview(name = "Sidebar", widthDp = 208, heightDp = 700)
@Composable
private fun SidebarPreview() { FrogTheme(darkTheme = true) { FrogNavigationSidebar("home", {}) } }
@Preview(name = "Code light", widthDp = 390)
@Composable
private fun CodeLight() { FrogTheme(darkTheme = false) { FrogCodeBlock("val count = 42\n// Native Compose\nText(\"Continue\")") } }
@Preview(name = "Code dark", widthDp = 390)
@Composable
private fun CodeDark() { FrogTheme(darkTheme = true) { FrogCodeBlock("val count = 42\n// Native Compose\nText(\"Continue\")") } }
@Preview(name = "Markdown headings and fence", widthDp = 390)
@Composable
private fun MarkdownPreview() { FrogTheme(darkTheme = true) { Box(Modifier.background(FrogTheme.colors.background).padding(16.dp)) { FrogMarkdown("# Usage\n\n## Compose content\n\nUse **FrogTheme** with `FrogButton`.\n\n```kotlin\nval enabled = true\n```") } } }
@Preview(name = "API phone", widthDp = 360)
@Composable
private fun ApiPhone() { FrogTheme(darkTheme = false) { FrogApiTable(FrogComponentRegistry.Button.properties.take(3), Modifier.padding(16.dp)) } }
@Preview(name = "API tablet", widthDp = 840)
@Composable
private fun ApiTablet() { FrogTheme(darkTheme = true) { FrogApiTable(FrogComponentRegistry.Button.properties.take(3), Modifier.padding(16.dp)) } }
@Preview(name = "Properties · dark", widthDp = 360, heightDp = 780)
@Composable
private fun PropertiesDark() { FrogTheme(darkTheme = true) { PropertyInspector(ButtonDemoState(), {}, Modifier.padding(16.dp)) } }
@Preview(name = "Properties · light", widthDp = 360, heightDp = 780)
@Composable
private fun PropertiesLight() { FrogTheme(darkTheme = false) { PropertyInspector(ButtonDemoState(), {}, Modifier.padding(16.dp)) } }
