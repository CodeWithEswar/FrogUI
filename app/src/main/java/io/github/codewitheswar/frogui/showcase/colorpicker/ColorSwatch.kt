package io.github.codewitheswar.frogui.showcase.colorpicker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme
import kotlin.math.ceil

internal fun Modifier.checkerboard() = drawBehind {
    val cell = 8.dp.toPx()
    for (y in 0 until ceil(size.height / cell).toInt()) for (x in 0 until ceil(size.width / cell).toInt()) {
        drawRect(if ((x + y) % 2 == 0) Color(0xFFE4E4E7) else Color(0xFFA1A1AA), Offset(x * cell, y * cell), Size(cell, cell))
    }
}

@Composable
internal fun ColorSwatch(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier.size(28.dp).clip(FrogTheme.shapes.sm).checkerboard().border(1.dp, FrogTheme.colors.borderStrong, FrogTheme.shapes.sm)) { drawRect(color) }
}
