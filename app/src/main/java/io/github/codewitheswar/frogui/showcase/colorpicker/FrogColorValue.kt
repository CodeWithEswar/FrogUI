package io.github.codewitheswar.frogui.showcase.colorpicker

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import io.github.codewitheswar.frogui.foundation.color.FrogColors
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

internal enum class FrogColorToken(val label: String, val member: String) {
    Background("Background", "background"), Surface("Surface", "surface"), SurfaceElevated("Surface Elevated", "surfaceElevated"),
    Foreground("Foreground", "foreground"), Primary("Primary", "primary"), PrimaryForeground("Primary Foreground", "primaryForeground"),
    Secondary("Secondary", "secondary"), SecondaryForeground("Secondary Foreground", "secondaryForeground"), Muted("Muted", "muted"),
    MutedForeground("Muted Foreground", "mutedForeground"), Border("Border", "border"), BorderStrong("Border Strong", "borderStrong"),
    Destructive("Destructive", "destructive"), DestructiveForeground("Destructive Foreground", "destructiveForeground"),
    SubtleSurface("Subtle Surface", "subtleSurface"), FocusRing("Focus Ring", "focusRing");

    fun resolve(colors: FrogColors): Color = when (this) {
        Background -> colors.background; Surface -> colors.surface; SurfaceElevated -> colors.surfaceElevated
        Foreground -> colors.foreground; Primary -> colors.primary; PrimaryForeground -> colors.primaryForeground
        Secondary -> colors.secondary; SecondaryForeground -> colors.secondaryForeground; Muted -> colors.muted
        MutedForeground -> colors.mutedForeground; Border -> colors.border; BorderStrong -> colors.borderStrong
        Destructive -> colors.destructive; DestructiveForeground -> colors.destructiveForeground
        SubtleSurface -> colors.subtleSurface; FocusRing -> colors.focusRing
    }

    val purpose: String get() = when (this) {
        Background -> "Root canvas behind content."
        Surface -> "Base cards and content regions."
        SurfaceElevated -> "Raised panels, menus and overlays."
        SubtleSurface -> "Quiet separation within a surface."
        Foreground -> "Primary text and icons on surfaces."
        Primary -> "High-emphasis actions."
        PrimaryForeground -> "Text and icons on primary actions."
        Secondary -> "Lower-emphasis action containers."
        SecondaryForeground -> "Text and icons on secondary actions."
        Muted -> "Quiet controls and supporting regions."
        MutedForeground -> "Supporting text on neutral surfaces."
        Border -> "Subtle structural separators, not focus indicators."
        BorderStrong -> "Stronger outlines between adjacent regions."
        Destructive -> "Actions that remove or destroy content."
        DestructiveForeground -> "Text and icons on destructive actions."
        FocusRing -> "Visible keyboard and accessibility focus."
    }
}

/** Token identity survives theme changes, saving, and Kotlin generation. */
@Immutable
internal sealed interface FrogColorValue {
    data class Token(val token: FrogColorToken, val alpha: Float? = null) : FrogColorValue
    data class Custom(val argb: Long) : FrogColorValue { init { require(argb in 0..0xFFFFFFFFL) } }

    fun resolve(colors: FrogColors): Color = when (this) {
        is Token -> token.resolve(colors).let { if (alpha == null) it else it.copy(alpha = alpha) }
        is Custom -> Color(argb)
    }
    fun code(): String = when (this) {
        is Token -> "FrogTheme.colors.${token.member}" + (alpha?.let { ".copy(alpha = ${it}f)" } ?: "")
        is Custom -> "Color(0x${argb.toString(16).uppercase(Locale.ROOT).padStart(8, '0')})"
    }
    fun encode(): String = when (this) {
        is Token -> "token:${token.name}:${alpha ?: ""}"
        is Custom -> "custom:$argb"
    }
    companion object {
        fun decode(encoded: String): FrogColorValue? = runCatching {
            val parts = encoded.split(':')
            when (parts[0]) {
                "token" -> Token(FrogColorToken.valueOf(parts[1]), parts.getOrNull(2)?.toFloatOrNull())
                "custom" -> Custom(parts[1].toLong())
                else -> null
            }
        }.getOrNull()
    }
}

internal fun parseHexColor(input: String): FrogColorValue.Custom? {
    val hex = input.trim().removePrefix("#")
    if (hex.length != 6 && hex.length != 8 || hex.any { it !in '0'..'9' && it.lowercaseChar() !in 'a'..'f' }) return null
    return hex.toLongOrNull(16)?.let { FrogColorValue.Custom(if (hex.length == 6) it or 0xFF000000L else it) }
}

internal fun Color.hex(): String {
    val argb = toArgb().toLong() and 0xFFFFFFFFL
    val digits = if (alpha >= 1f) argb and 0xFFFFFF else argb
    return "#" + digits.toString(16).uppercase(Locale.ROOT).padStart(if (alpha >= 1f) 6 else 8, '0')
}

/** Composite alpha before applying WCAG relative-luminance contrast. Canvas must be opaque. */
internal fun colorContrast(content: Color, container: Color, canvas: Color): Double {
    require(canvas.alpha == 1f)
    val background = container.compositeOver(canvas)
    val foreground = content.compositeOver(background)
    val a = foreground.luminance().toDouble()
    val b = background.luminance().toDouble()
    return (max(a, b) + .05) / (min(a, b) + .05)
}
