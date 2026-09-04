package io.github.codewitheswar.frogui.showcase.detail

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.registry.FrogComponentMetadata
import io.github.codewitheswar.frogui.showcase.canvas.*
import io.github.codewitheswar.frogui.theme.FrogTheme

internal enum class PreviewContentMode { Standard, Overlay }
internal data class PreviewCapabilities(
    val theme: Boolean = true, val width: Boolean = true, val alignment: Boolean = true,
    val background: Boolean = true, val inspector: Boolean = true,
    val contentMode: PreviewContentMode = PreviewContentMode.Standard,
    val minHeight: Dp = 192.dp,
)

internal sealed interface ComponentDrawerPage {
    data object Inspector : ComponentDrawerPage
    data object Preview : ComponentDrawerPage
    data class Api(val name: String) : ComponentDrawerPage
    data class Custom(val id: String) : ComponentDrawerPage
}

/** Shared UI state deliberately knows nothing about button or drawer properties. */
@Stable
internal class ComponentDetailState(initialDark: Boolean) {
    var tab by mutableIntStateOf(0)
    var previewDark by mutableStateOf(initialDark)
    var width by mutableStateOf(PreviewWidthMode.Fit)
    var background by mutableStateOf(PreviewBackground.Canvas)
    var alignment by mutableStateOf(PreviewAlignment.Center)
    var pages by mutableStateOf<List<ComponentDrawerPage>>(emptyList())
    val currentPage get() = pages.lastOrNull()
    fun open(page: ComponentDrawerPage) { pages = listOf(page) }
    fun push(page: ComponentDrawerPage) { if (currentPage != page) pages = pages + page }
    fun back() { pages = pages.dropLast(1) }
    fun dismiss() { pages = emptyList() }
    companion object {
        val saver = listSaver<ComponentDetailState, Any>(save = { state ->
            listOf(state.tab, state.previewDark, state.width.name, state.background.name, state.alignment.name) + state.pages.map { page -> when (page) {
                ComponentDrawerPage.Inspector -> "inspector"; ComponentDrawerPage.Preview -> "preview"
                is ComponentDrawerPage.Api -> "api:${page.name}"; is ComponentDrawerPage.Custom -> "custom:${page.id}"
            } }
        }, restore = { values -> ComponentDetailState(values[1] as Boolean).apply {
            tab = (values[0] as Int).coerceIn(0, 3)
            width = PreviewWidthMode.valueOf(values[2] as String)
            background = PreviewBackground.valueOf(values[3] as String)
            alignment = PreviewAlignment.valueOf(values[4] as String)
            pages = values.drop(5).mapNotNull { value -> val route = value as String; when {
                route == "inspector" -> ComponentDrawerPage.Inspector
                route == "preview" -> ComponentDrawerPage.Preview
                route.startsWith("api:") -> ComponentDrawerPage.Api(route.substringAfter(':'))
                route.startsWith("custom:") -> ComponentDrawerPage.Custom(route.substringAfter(':'))
                else -> null
            } }
        } })
    }
}

@Composable
internal fun rememberComponentDetailState(): ComponentDetailState {
    val dark = FrogTheme.colors.isDark
    return rememberSaveable(saver = ComponentDetailState.saver) { ComponentDetailState(dark) }
}

internal data class ComponentDrawerContent(
    val title: String,
    val subtitle: String? = null,
    val preview: (@Composable () -> Unit)? = null,
    val actions: (@Composable RowScope.() -> Unit)? = null,
    val content: @Composable ColumnScope.() -> Unit,
)

/** Small composable contract: shared presentation, component-owned state and behavior. */
internal data class ComponentShowcaseDefinition(
    val metadata: FrogComponentMetadata,
    val preview: @Composable () -> Unit,
    val inspector: @Composable () -> Unit,
    val onReset: () -> Unit,
    val generatedCode: String,
    val api: List<ComponentApiProperty>,
    val accessibility: ComponentAccessibilityInfo,
    val previewContent: @Composable () -> Unit,
    val capabilities: PreviewCapabilities = PreviewCapabilities(),
    val quickControls: (@Composable () -> Unit)? = null,
    val previewStatus: String? = null,
    val inspectorPreview: (@Composable () -> Unit)? = null,
    val codeNote: String? = null,
    val customDrawer: (@Composable (String) -> ComponentDrawerContent?)? = null,
)

internal fun interface ComponentShowcaseFactory {
    @Composable fun create(metadata: FrogComponentMetadata, state: ComponentDetailState): ComponentShowcaseDefinition
}
