package io.github.codewitheswar.frogui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.SystemBarStyle
import androidx.core.content.edit
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import io.github.codewitheswar.frogui.navigation.ShowcaseAppearance
import io.github.codewitheswar.frogui.showcase.style.ProvideShowcaseMotion
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.codewitheswar.frogui.theme.FrogTheme
import io.github.codewitheswar.frogui.navigation.FrogUiShell

/**
 * Main application entry point hosting the FrogUI Showcase and Component Workbench.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val preferences = remember { getSharedPreferences("showcase", MODE_PRIVATE) }
            var appearance by remember { mutableStateOf(ShowcaseAppearance.entries.firstOrNull { it.name == preferences.getString("appearance", null) } ?: ShowcaseAppearance.System) }
            var reduceMotion by remember { mutableStateOf(preferences.getBoolean("reduceMotion", false)) }
            val darkTheme = when (appearance) { ShowcaseAppearance.System -> isSystemInDarkTheme(); ShowcaseAppearance.Light -> false; ShowcaseAppearance.Dark -> true }
            SideEffect {
                enableEdgeToEdge(statusBarStyle = SystemBarStyle.auto(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT) { darkTheme })
            }
            FrogTheme(darkTheme = darkTheme) {
                ProvideShowcaseMotion(reduceMotion) {
                    FrogUiShell(appearance, { appearance = it; preferences.edit { putString("appearance", it.name) } },
                        reduceMotion, { reduceMotion = it; preferences.edit { putBoolean("reduceMotion", it) } })
                }
            }
        }
    }
}
