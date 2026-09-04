package io.github.codewitheswar.frogui.theme

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.codewitheswar.frogui.foundation.sizing.FrogSizing
import io.github.codewitheswar.frogui.foundation.motion.FrogMotion
import io.github.codewitheswar.frogui.foundation.typography.FrogTypography
import io.github.codewitheswar.frogui.testing.setFrogContent
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class FrogThemeIsolationTest {
    @get:Rule val compose = createComposeRule()

    @Test fun nestedPreviewThemeDoesNotChangeTheSurroundingTheme() {
        var before = false
        var inside = true
        var after = false
        compose.setFrogContent(darkTheme = true) {
            before = FrogTheme.colors.isDark
            FrogTheme(darkTheme = false) { inside = FrogTheme.colors.isDark }
            after = FrogTheme.colors.isDark
        }
        compose.runOnIdle {
            assertEquals(true, before)
            assertEquals(false, inside)
            assertEquals(true, after)
        }
    }

    @Test fun nestedThemeInheritsCustomGroupsAndRestoresTheSurroundingMaterialTheme() {
        val type = FrogTypography().let { it.copy(body = it.body.copy(fontSize = 19.sp)) }
        val colors = FrogThemeDefaults.darkColors().copy(primary = Color.Cyan)
        var values = emptyList<Any>()
        compose.setContent {
            FrogTheme(colors = colors, typography = type) {
                ProvideFrogThemeEnvironment(sizing = FrogSizing(minimumTouchTarget = 56.dp)) {
                    val before = FrogTheme.colors.primary
                    var nested: List<Any> = emptyList()
                    FrogTheme(darkTheme = false) {
                        nested = listOf(FrogTheme.typography, FrogTheme.sizing.minimumTouchTarget,
                            FrogTheme.colors.isDark, MaterialTheme.typography.bodyLarge)
                    }
                    values = listOf(before) + nested + MaterialTheme.colorScheme.primary
                }
            }
        }
        compose.runOnIdle { assertEquals(listOf(Color.Cyan, type, 56.dp, false, type.body, Color.Cyan), values) }
    }

    @Test fun reductionChangesLiveWithoutReplacingTheOriginalMotionProfile() {
        var reduce by mutableStateOf(true)
        var effective = FrogMotion()
        var outer = FrogMotion()
        compose.setContent {
            // Isolate this test from the connected device's animator setting.
            CompositionLocalProvider(LocalFrogSystemReduceMotion provides false) {
                FrogTheme(motion = FrogMotion(normalDurationMillis = 230)) {
                    ProvideFrogThemeEnvironment(reduceMotion = reduce) {
                        FrogTheme(darkTheme = false) { effective = FrogTheme.motion }
                    }
                    outer = FrogTheme.motion
                }
            }
        }
        compose.runOnIdle {
            assertEquals(true, effective.isReduced)
            assertEquals(230, outer.normalDurationMillis)
            reduce = false
        }
        compose.runOnIdle { assertEquals(230, effective.normalDurationMillis) }
    }

    @Test fun systemReductionCannotBeOverriddenByAnExplicitFalsePreference() {
        var reduced = false
        compose.setContent {
            CompositionLocalProvider(LocalFrogSystemReduceMotion provides true) {
                FrogTheme { ProvideFrogThemeEnvironment(reduceMotion = false) { reduced = FrogTheme.reduceMotion } }
            }
        }
        compose.runOnIdle { assertEquals(true, reduced) }
    }
}
