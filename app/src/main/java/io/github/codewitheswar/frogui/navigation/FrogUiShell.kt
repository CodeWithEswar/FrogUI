package io.github.codewitheswar.frogui.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.foundation.branding.FrogUiLogo
import io.github.codewitheswar.frogui.foundation.branding.FrogUiLogoVariant
import io.github.codewitheswar.frogui.foundation.theme.FrogTheme
import io.github.codewitheswar.frogui.showcase.screens.AboutScreen
import io.github.codewitheswar.frogui.showcase.screens.ComponentDetailScreen
import io.github.codewitheswar.frogui.showcase.screens.ComponentsScreen
import io.github.codewitheswar.frogui.showcase.screens.FoundationScreen
import io.github.codewitheswar.frogui.showcase.screens.HomeScreen

/**
 * Adaptive navigation shell for the FrogUI Showcase.
 * Automatically adapts between compact phone navigation and expanded tablet rail navigation.
 */
@Composable
fun FrogUiShell(
    darkTheme: Boolean,
    onToggleTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentDestination by remember { mutableStateOf<FrogUiDestination>(FrogUiDestination.Home) }

    val colors = FrogTheme.colors

    BoxWithConstraints(modifier = modifier.fillMaxSize().background(colors.background)) {
        val isTablet = maxWidth >= 840.dp

        if (isTablet) {
            // Tablet Layout: Persistent Navigation Rail + Content Area
            Row(modifier = Modifier.fillMaxSize()) {
                ShowcaseNavigationRail(
                    currentDestination = currentDestination,
                    onSelectDestination = { currentDestination = it },
                    darkTheme = darkTheme,
                    onToggleTheme = onToggleTheme
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    ShellContent(
                        currentDestination = currentDestination,
                        onNavigate = { currentDestination = it },
                        onBack = { currentDestination = FrogUiDestination.Components }
                    )
                }
            }
        } else {
            // Phone Layout: Top Bar + Content + Bottom Bar
            val showBottomBar = currentDestination !is FrogUiDestination.ComponentDetail

            Scaffold(
                topBar = {
                    if (currentDestination !is FrogUiDestination.ComponentDetail) {
                        ShowcaseTopBar(
                            currentDestination = currentDestination,
                            darkTheme = darkTheme,
                            onToggleTheme = onToggleTheme
                        )
                    }
                },
                bottomBar = {
                    if (showBottomBar) {
                        ShowcaseBottomBar(
                            currentDestination = currentDestination,
                            onSelectDestination = { currentDestination = it }
                        )
                    }
                },
                containerColor = colors.background
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    ShellContent(
                        currentDestination = currentDestination,
                        onNavigate = { currentDestination = it },
                        onBack = { currentDestination = FrogUiDestination.Components }
                    )
                }
            }
        }
    }
}

@Composable
private fun ShellContent(
    currentDestination: FrogUiDestination,
    onNavigate: (FrogUiDestination) -> Unit,
    onBack: () -> Unit
) {
    Crossfade(targetState = currentDestination, label = "destination_crossfade") { dest ->
        when (dest) {
            is FrogUiDestination.Home -> HomeScreen(
                onNavigateToComponents = { onNavigate(FrogUiDestination.Components) },
                onNavigateToFoundation = { onNavigate(FrogUiDestination.Foundation) },
                onNavigateToButtonDetail = { onNavigate(FrogUiDestination.ComponentDetail("button")) }
            )

            is FrogUiDestination.Components -> ComponentsScreen(
                onSelectComponent = { componentId ->
                    onNavigate(FrogUiDestination.ComponentDetail(componentId))
                }
            )

            is FrogUiDestination.Foundation -> FoundationScreen()

            is FrogUiDestination.About -> AboutScreen()

            is FrogUiDestination.ComponentDetail -> ComponentDetailScreen(
                componentId = dest.componentId,
                onBack = onBack
            )
        }
    }
}

@Composable
private fun ShowcaseTopBar(
    currentDestination: FrogUiDestination,
    darkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val colors = FrogTheme.colors

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(colors.background)
            .border(
                1.dp,
                colors.border,
                androidx.compose.ui.graphics.RectangleShape
            )
            .padding(horizontal = FrogTheme.spacing.md, vertical = FrogTheme.spacing.xs),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(FrogTheme.spacing.sm)
        ) {
            FrogUiLogo(
                size = 32.dp,
                variant = FrogUiLogoVariant.Auto,
                contentDescription = "FrogUI"
            )

            Text(
                text = currentDestination.title,
                style = FrogTheme.typography.title,
                color = colors.foreground
            )
        }

        IconButton(onClick = onToggleTheme) {
            Icon(
                imageVector = if (darkTheme) Icons.Rounded.LightMode else Icons.Rounded.DarkMode,
                contentDescription = "Toggle theme",
                tint = colors.foreground
            )
        }
    }
}

@Composable
private fun ShowcaseBottomBar(
    currentDestination: FrogUiDestination,
    onSelectDestination: (FrogUiDestination) -> Unit
) {
    val colors = FrogTheme.colors

    NavigationBar(
        containerColor = colors.surfaceElevated,
        tonalElevation = 0.dp,
        modifier = Modifier.border(1.dp, colors.border, androidx.compose.ui.graphics.RectangleShape)
    ) {
        val navItems = listOf(
            NavItem(FrogUiDestination.Home, "Home", Icons.Rounded.Home),
            NavItem(FrogUiDestination.Components, "Components", Icons.Rounded.Category),
            NavItem(FrogUiDestination.Foundation, "Foundation", Icons.Rounded.Palette),
            NavItem(FrogUiDestination.About, "About", Icons.Rounded.Info)
        )

        navItems.forEach { item ->
            val isSelected = currentDestination == item.destination

            NavigationBarItem(
                selected = isSelected,
                onClick = { onSelectDestination(item.destination) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, style = FrogTheme.typography.caption) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colors.primaryForeground,
                    selectedTextColor = colors.foreground,
                    indicatorColor = colors.primary,
                    unselectedIconColor = colors.mutedForeground,
                    unselectedTextColor = colors.mutedForeground
                )
            )
        }
    }
}

private data class NavItem(
    val destination: FrogUiDestination,
    val label: String,
    val icon: ImageVector
)

@Composable
private fun ShowcaseNavigationRail(
    currentDestination: FrogUiDestination,
    onSelectDestination: (FrogUiDestination) -> Unit,
    darkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val colors = FrogTheme.colors

    NavigationRail(
        containerColor = colors.surfaceElevated,
        header = {
            Column(
                modifier = Modifier.padding(top = FrogTheme.spacing.lg),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FrogUiLogo(
                    size = 40.dp,
                    variant = FrogUiLogoVariant.Auto,
                    contentDescription = "FrogUI"
                )
            }
        },
        modifier = Modifier
            .border(1.dp, colors.border, androidx.compose.ui.graphics.RectangleShape)
            .statusBarsPadding()
    ) {
        val navItems = listOf(
            NavItem(FrogUiDestination.Home, "Home", Icons.Rounded.Home),
            NavItem(FrogUiDestination.Components, "Components", Icons.Rounded.Category),
            NavItem(FrogUiDestination.Foundation, "Foundation", Icons.Rounded.Palette),
            NavItem(FrogUiDestination.About, "About", Icons.Rounded.Info)
        )

        Spacer(modifier = Modifier.height(FrogTheme.spacing.lg))

        navItems.forEach { item ->
            val isSelected = currentDestination == item.destination

            NavigationRailItem(
                selected = isSelected,
                onClick = { onSelectDestination(item.destination) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, style = FrogTheme.typography.caption) },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = colors.primaryForeground,
                    selectedTextColor = colors.foreground,
                    indicatorColor = colors.primary,
                    unselectedIconColor = colors.mutedForeground,
                    unselectedTextColor = colors.mutedForeground
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = onToggleTheme,
            modifier = Modifier.padding(bottom = FrogTheme.spacing.md)
        ) {
            Icon(
                imageVector = if (darkTheme) Icons.Rounded.LightMode else Icons.Rounded.DarkMode,
                contentDescription = "Toggle theme",
                tint = colors.foreground
            )
        }
    }
}
