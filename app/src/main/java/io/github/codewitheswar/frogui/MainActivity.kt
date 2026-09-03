package io.github.codewitheswar.frogui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.codewitheswar.frogui.foundation.theme.FrogTheme
import io.github.codewitheswar.frogui.navigation.FrogUiShell

/**
 * Main application entry point hosting the FrogUI Showcase and Component Workbench.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkTheme by remember { mutableStateOf(true) }

            FrogTheme(darkTheme = darkTheme) {
                FrogUiShell(
                    darkTheme = darkTheme,
                    onToggleTheme = { darkTheme = !darkTheme }
                )
            }
        }
    }
}