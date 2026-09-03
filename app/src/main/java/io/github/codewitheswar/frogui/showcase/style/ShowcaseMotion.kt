package io.github.codewitheswar.frogui.showcase.style

import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import io.github.codewitheswar.frogui.theme.FrogTheme

internal val LocalFrogMotionEnabled = staticCompositionLocalOf { true }

/** One resolver for platform animation preference and the showcase's explicit reduction. */
@Composable
internal fun ProvideShowcaseMotion(reduceMotion: Boolean, content: @Composable () -> Unit) {
    val resolver = LocalContext.current.contentResolver
    fun animationsEnabled() = Settings.Global.getFloat(resolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f) > 0f
    var systemMotion by remember(resolver) { mutableStateOf(animationsEnabled()) }
    DisposableEffect(resolver) {
        val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) { systemMotion = animationsEnabled() }
        }
        resolver.registerContentObserver(Settings.Global.getUriFor(Settings.Global.ANIMATOR_DURATION_SCALE), false, observer)
        onDispose { resolver.unregisterContentObserver(observer) }
    }
    val enabled = systemMotion && !reduceMotion
    CompositionLocalProvider(LocalFrogMotionEnabled provides enabled) {
        FrogTheme(colors = FrogTheme.colors, typography = FrogTheme.typography, shapes = FrogTheme.shapes,
            spacing = FrogTheme.spacing, elevation = FrogTheme.elevation,
            motion = if (enabled) FrogTheme.motion else FrogTheme.motion.copy(fastDurationMillis = 0, normalDurationMillis = 0, largeDurationMillis = 0),
            content = content)
    }
}

internal object ShowcaseMotion {
    val fast: Int @Composable get() = if (LocalFrogMotionEnabled.current) FrogTheme.motion.fastDurationMillis.coerceIn(120, 200) else 0
    val standard: Int @Composable get() = if (LocalFrogMotionEnabled.current) FrogTheme.motion.normalDurationMillis.coerceIn(160, 220) else 0
}
