package io.github.codewitheswar.frogui.theme

import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import io.github.codewitheswar.frogui.foundation.adaptive.FrogAdaptive
import io.github.codewitheswar.frogui.foundation.sizing.FrogSizing

internal val LocalFrogSizing = staticCompositionLocalOf { FrogSizing() }
internal val LocalFrogAdaptive = staticCompositionLocalOf { FrogAdaptive() }
internal val LocalFrogReduceMotion = staticCompositionLocalOf { false }
internal val LocalFrogSystemReduceMotion = staticCompositionLocalOf<Boolean?> { null }

/**
 * Locally overrides shared sizing, adaptive policy and motion preference. Use inside
 * [FrogTheme]; nested themes inherit this environment. This additive provider preserves
 * the original FrogTheme call signature and keeps rare overrides out of that parameter list.
 * It does not persist preferences or mutate window state. Component state is unaffected.
 *
 * @param sizing Shared visual/interactive dimensions. Touch targets cannot be smaller than 48dp.
 * @param adaptive Width thresholds, resolved by each host against its actual constraints.
 * @param reduceMotion Stops decorative motion while retaining immediate state feedback.
 * Android's disabled animator preference always takes precedence over false here.
 * @param content Subtree receiving the environment; siblings retain their own environment.
 */
@Composable
fun ProvideFrogThemeEnvironment(
    sizing: FrogSizing = FrogTheme.sizing,
    adaptive: FrogAdaptive = FrogTheme.adaptive,
    reduceMotion: Boolean = FrogTheme.reduceMotion,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalFrogSizing provides sizing,
        LocalFrogAdaptive provides adaptive,
        LocalFrogReduceMotion provides reduceMotion,
        content = content,
    )
}

/** Observe once per root theme; nested themes share the live preference through composition. */
@Composable
internal fun systemReduceMotion(): Boolean {
    LocalFrogSystemReduceMotion.current?.let { return it }
    if (LocalInspectionMode.current) return false
    val resolver = LocalContext.current.contentResolver
    fun read() = Settings.Global.getFloat(resolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f) <= 0f
    var reduced by remember(resolver) { mutableStateOf(read()) }
    DisposableEffect(resolver) {
        val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) { reduced = read() }
        }
        resolver.registerContentObserver(Settings.Global.getUriFor(Settings.Global.ANIMATOR_DURATION_SCALE), false, observer)
        reduced = read()
        onDispose { resolver.unregisterContentObserver(observer) }
    }
    return reduced
}
