package io.github.codewitheswar.frogui.showcase.components.drawer

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerColors
import io.github.codewitheswar.frogui.components.overlays.drawer.FrogDrawerDefaults
import io.github.codewitheswar.frogui.showcase.colorpicker.FrogColorToken
import io.github.codewitheswar.frogui.showcase.colorpicker.FrogColorValue
import io.github.codewitheswar.frogui.showcase.colorpicker.hex
import io.github.codewitheswar.frogui.theme.FrogTheme

internal enum class DrawerShapePreset(val label: String) {
    Theme("Theme default"), Square("Square"), Rounded("Extra rounded");

    val code: String? get() = when (this) {
        Theme -> null
        Square -> "RectangleShape"
        Rounded -> "RoundedCornerShape(28.dp)"
    }

    val shape: Shape? get() = when (this) {
        Theme -> null
        Square -> RectangleShape
        Rounded -> RoundedCornerShape(28.dp)
    }
}

internal enum class DrawerColorProperty(val label: String, val parameter: String) {
    Container("Container color", "containerColor"),
    Content("Content color", "contentColor"),
    Border("Border color", "borderColor"),
    Handle("Handle color", "handleColor"),
    Scrim("Scrim color", "scrimColor");
}

internal fun defaultDrawerColor(property: DrawerColorProperty): FrogColorValue = when (property) {
    DrawerColorProperty.Container -> FrogColorValue.Token(FrogColorToken.SurfaceElevated)
    DrawerColorProperty.Content -> FrogColorValue.Token(FrogColorToken.Foreground)
    DrawerColorProperty.Border -> FrogColorValue.Token(FrogColorToken.BorderStrong)
    DrawerColorProperty.Handle -> FrogColorValue.Token(FrogColorToken.BorderStrong)
    DrawerColorProperty.Scrim -> FrogColorValue.Custom(0x7A000000)
}

internal fun DrawerDemoState.colorValue(property: DrawerColorProperty): FrogColorValue =
    colorOverrides[property] ?: defaultDrawerColor(property)

@Composable
internal fun DrawerDemoState.resolvedColors(): FrogDrawerColors = FrogDrawerDefaults.colors(
    containerColor = colorValue(DrawerColorProperty.Container).resolve(FrogTheme.colors),
    contentColor = colorValue(DrawerColorProperty.Content).resolve(FrogTheme.colors),
    borderColor = colorValue(DrawerColorProperty.Border).resolve(FrogTheme.colors),
    handleColor = colorValue(DrawerColorProperty.Handle).resolve(FrogTheme.colors),
    scrimColor = colorValue(DrawerColorProperty.Scrim).resolve(FrogTheme.colors),
)

internal fun DrawerDemoState.withColor(property: DrawerColorProperty, value: FrogColorValue?): DrawerDemoState {
    val next = colorOverrides.toMutableMap()
    if (value == null || value == defaultDrawerColor(property)) next.remove(property) else next[property] = value
    return copy(colorOverrides = next)
}

internal fun DrawerDemoState.resetColors(): DrawerDemoState = copy(colorOverrides = emptyMap())

internal fun FrogColorValue.readableValue(colors: io.github.codewitheswar.frogui.foundation.color.FrogColors): String = when (this) {
    is FrogColorValue.Token -> token.label + if (alpha != null) " · ${(alpha * 100).toInt()}%" else ""
    is FrogColorValue.Custom -> if (argb == 0L) "Transparent" else resolve(colors).hex()
}
