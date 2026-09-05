package io.github.codewitheswar.frogui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.registry.FrogComponentRegistry
import io.github.codewitheswar.frogui.components.button.FrogIconButton
import io.github.codewitheswar.frogui.components.button.FrogIconButtonSize
import io.github.codewitheswar.frogui.components.button.FrogIconButtonVariant
import io.github.codewitheswar.frogui.showcase.branding.FrogUiLogo
import io.github.codewitheswar.frogui.showcase.icons.FrogIcons
import io.github.codewitheswar.frogui.showcase.screens.*
import io.github.codewitheswar.frogui.showcase.style.*
import io.github.codewitheswar.frogui.theme.FrogTheme

internal enum class ShowcaseAppearance { System, Light, Dark }

/** Window-based layout with persistent top-level state and a separate detail workspace. */
@Composable
internal fun FrogUiShell(appearance: ShowcaseAppearance, onAppearanceChange: (ShowcaseAppearance) -> Unit,
    reduceMotion: Boolean, onReduceMotionChange: (Boolean) -> Unit, modifier: Modifier = Modifier, incomingLink: ComponentDeepLink? = null) {
    var backStack by rememberSaveable { mutableStateOf(listOf("home")) }
    LaunchedEffect(incomingLink) { incomingLink?.let { backStack = listOf("components", "components/${it.componentId}") } }
    val route = backStack.last()
    var appearanceMenu by remember { mutableStateOf(false) }
    val destination = FrogUiDestination.fromRoute(route)
    val detail = destination as? FrogUiDestination.ComponentDetail
    val nested = detail != null || destination == FrogUiDestination.Settings || destination == FrogUiDestination.About
    val metadata = detail?.let { FrogComponentRegistry.findById(it.componentId) }
    val stateHolder = rememberSaveableStateHolder()
    val navigate: (String) -> Unit = { next ->
        backStack = navigateShowcase(backStack, next)
    }
    val back: () -> Unit = { if (backStack.size > 1) backStack = backStack.dropLast(1) }
    BackHandler(nested, onBack = back)
    val colors = FrogTheme.colors
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    BoxWithConstraints(modifier.fillMaxSize().background(colors.background)
        .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)).imePadding()) {
        val window = showcaseWindow(maxWidth, FrogTheme.adaptive)
        val compact = window == ShowcaseWindow.Compact
        Row(Modifier.fillMaxSize()) {
            if (!compact) {
                val selected = if (detail != null) "components" else route
                if (window == ShowcaseWindow.Expanded) FrogNavigationSidebar(selected, navigate)
                else FrogNavigationRail(selected, navigate)
            }
            Column(Modifier.weight(1f).fillMaxHeight()) {
                FrogShowcaseTopBar(
                    title = if (detail != null) metadata?.displayName ?: "Component not found" else if (nested) destination.title else "FrogUI",
                    subtitle = if (detail != null) metadata?.let { "${it.category.displayName} · ${it.status.label}" } else if (!nested) destination.title else null,
                    modifier = Modifier.statusBarsPadding(),
                    navigationIcon = if (nested) ({ ShowcaseBackButton(onClick = back) })
                        else if (compact) ({ FrogUiLogo(size = 28.dp, modifier = Modifier.padding(start = 12.dp, end = 4.dp), contentDescription = null) }) else null,
                    actions = {
                        Box {
                            FrogIconButton(
                                icon = {
                                    Icon(
                                        imageVector = when (appearance) {
                                            ShowcaseAppearance.System -> FrogIcons.System
                                            ShowcaseAppearance.Light -> FrogIcons.Light
                                            ShowcaseAppearance.Dark -> FrogIcons.Dark
                                        },
                                        contentDescription = null
                                    )
                                },
                                contentDescription = "Appearance",
                                onClick = { appearanceMenu = true },
                                variant = FrogIconButtonVariant.Ghost,
                                size = FrogIconButtonSize.Medium
                            )
                            DropdownMenu(appearanceMenu, onDismissRequest = { appearanceMenu = false }, containerColor = colors.surfaceElevated) {
                                ShowcaseAppearance.entries.forEach { option -> DropdownMenuItem(
                                    text = { Text(option.name) }, onClick = { onAppearanceChange(option); appearanceMenu = false },
                                    trailingIcon = if (appearance == option) ({ Icon(FrogIcons.Check, "Selected") }) else null,
                                ) }
                            }
                        }
                        if (!nested && compact) {
                            FrogIconButton(
                                icon = { Icon(FrogIcons.Settings, contentDescription = null) },
                                contentDescription = "Settings",
                                onClick = { navigate("settings") },
                                variant = FrogIconButtonVariant.Ghost,
                                size = FrogIconButtonSize.Medium
                            )
                        }
                    },
                )
                Box(Modifier.weight(1f).fillMaxWidth().then(if (compact && !nested && !imeVisible) Modifier else Modifier.navigationBarsPadding())) {
                    Crossfade(route, animationSpec = tween(ShowcaseMotion.standard), label = "destination") { contentRoute ->
                        stateHolder.SaveableStateProvider(contentRoute) {
                            when (val page = FrogUiDestination.fromRoute(contentRoute)) {
                                FrogUiDestination.Home -> HomeScreen({ navigate("components") }, { navigate("foundation") }, { navigate("components/button") })
                                FrogUiDestination.Components -> ComponentsScreen({ navigate("components/$it") })
                                FrogUiDestination.Playground -> ComponentDetailScreen("button", back)
                                FrogUiDestination.Foundation -> FoundationScreen()
                                FrogUiDestination.Settings -> SettingsScreen(appearance, onAppearanceChange, reduceMotion, onReduceMotionChange, { navigate("about") })
                                FrogUiDestination.About -> AboutScreen()
                                is FrogUiDestination.ComponentDetail -> ComponentDetailScreen(page.componentId, back)
                            }
                        }
                    }
                }
                if (compact && !nested && !imeVisible) FrogShowcaseBottomBar(showcaseDestinations, route, navigate)
            }
        }
    }
}

internal fun navigateShowcase(stack: List<String>, next: String): List<String> = when {
    stack.lastOrNull() == next -> stack
    next in listOf("home", "components", "playground", "foundation") -> listOf(next)
    else -> stack + next
}
