package io.github.codewitheswar.frogui.components.button

import androidx.compose.foundation.Canvas
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.theme.FrogTheme

/** Loading is announced by the action's state; its visual indicator has no duplicate semantics. */
@Composable
internal fun FrogButtonProgress(color: Color, modifier: Modifier) {
    if (FrogTheme.reduceMotion) {
        Canvas(modifier.clearAndSetSemantics {}) {
            val stroke = 2.dp.toPx()
            drawArc(color, startAngle = -90f, sweepAngle = 270f, useCenter = false,
                topLeft = androidx.compose.ui.geometry.Offset(stroke / 2, stroke / 2),
                size = androidx.compose.ui.geometry.Size(size.width - stroke, size.height - stroke),
                style = Stroke(stroke, cap = StrokeCap.Round))
        }
    } else {
        CircularProgressIndicator(modifier = modifier.clearAndSetSemantics {}, color = color,
            strokeWidth = 2.dp, strokeCap = StrokeCap.Round)
    }
}
