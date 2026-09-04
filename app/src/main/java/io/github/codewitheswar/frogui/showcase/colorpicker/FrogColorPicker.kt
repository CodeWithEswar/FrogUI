package io.github.codewitheswar.frogui.showcase.colorpicker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.foundation.color.FrogColors
import io.github.codewitheswar.frogui.showcase.code.FrogInlineCode
import io.github.codewitheswar.frogui.showcase.inspector.FrogEnumSelector
import io.github.codewitheswar.frogui.showcase.inspector.FrogInspectorRow
import io.github.codewitheswar.frogui.theme.FrogTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/** Generic draft editor: transaction ownership and component previews stay with the caller. */
@Composable
internal fun FrogColorPicker(value: FrogColorValue, onValueChange: (FrogColorValue) -> Unit, modifier: Modifier = Modifier,
    allowAlpha: Boolean = true, availableTokens: List<FrogColorToken> = FrogColorToken.entries,
    tokenColors: FrogColors = FrogTheme.colors, onValidityChange: (Boolean) -> Unit = {}) {
    val resolved = value.resolve(tokenColors)
    var mode by rememberSaveable { mutableStateOf(if (value is FrogColorValue.Token) "Theme token" else "Custom") }
    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        FrogEnumSelector("Color source", listOf("Theme token", "Custom"), mode) {
            mode = it
            onValidityChange(true)
            if (it == "Custom") onValueChange(FrogColorValue.Custom(resolved.toArgb().toLong() and 0xFFFFFFFFL))
            else if (value !is FrogColorValue.Token) onValueChange(FrogColorValue.Token(availableTokens.first()))
        }
        if (mode == "Theme token") {
            Text("Tokens follow the preview theme.", style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground)
            availableTokens.forEach { token ->
                FrogInspectorRow(token.label, "${token.resolve(tokenColors).hex()} · ${if ((value as? FrogColorValue.Token)?.token == token) "Selected" else "Theme"}",
                    { onValueChange(FrogColorValue.Token(token)); onValidityChange(true) },
                    description = "FrogTheme.colors.${token.member}", leading = { ColorSwatch(token.resolve(tokenColors)) },
                    modifier = Modifier.semantics { selected = (value as? FrogColorValue.Token)?.token == token })
            }
        } else {
            CustomColorControls(resolved, allowAlpha, { onValueChange(FrogColorValue.Custom(it.toArgb().toLong() and 0xFFFFFFFFL)) }, onValidityChange)
        }
        Text("Generated value", style = FrogTheme.typography.label, color = FrogTheme.colors.mutedForeground)
        FrogInlineCode(value.code())
    }
}

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
private fun CustomColorControls(color: Color, allowAlpha: Boolean, onChange: (Color) -> Unit, onValidityChange: (Boolean) -> Unit) {
    val hsv = remember(color) { FloatArray(3).also { android.graphics.Color.colorToHSV(color.toArgb(), it) } }
    var hex by rememberSaveable { mutableStateOf(color.hex()) }
    var valid by rememberSaveable { mutableStateOf(true) }
    // Hue survives achromatic values; dragging the plane must not reset it to red.
    var hue by rememberSaveable { mutableFloatStateOf(hsv[0]) }
    LaunchedEffect(color) { if (hsv[1] > .001f && hsv[2] > .001f) hue = hsv[0] }
    fun update(h: Float = hue, s: Float = hsv[1], v: Float = hsv[2], a: Float = color.alpha) {
        hue = h
        valid = true
        onValidityChange(true)
        onChange(Color.hsv(h.coerceIn(0f, 360f), s.coerceIn(0f, 1f), v.coerceIn(0f, 1f), if (allowAlpha) a.coerceIn(0f, 1f) else 1f))
    }
    val updatePlane by rememberUpdatedState<(Offset, Float, Float) -> Unit>({ point, width, height -> update(s = point.x / width, v = 1f - point.y / height) })
    Canvas(Modifier.fillMaxWidth().height(120.dp).clip(FrogTheme.shapes.sm)
        .pointerInput(Unit) { detectTapGestures { updatePlane(it, size.width.toFloat(), size.height.toFloat()) } }
        .pointerInput(Unit) { detectDragGestures(onDragStart = { updatePlane(it, size.width.toFloat(), size.height.toFloat()) }) { change, _ -> change.consume(); updatePlane(change.position, size.width.toFloat(), size.height.toFloat()) } }
        .semantics { contentDescription = "Saturation and brightness plane. Use the sliders below for precise adjustment." }) {
        drawRect(Brush.horizontalGradient(listOf(Color.White, Color.hsv(hue, 1f, 1f))))
        drawRect(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
        val point = Offset(hsv[1] * size.width, (1f - hsv[2]) * size.height)
        drawCircle(Color.Black, 7.dp.toPx(), point, style = Stroke(3.dp.toPx()))
        drawCircle(Color.White, 7.dp.toPx(), point, style = Stroke(1.5.dp.toPx()))
    }
    Column {
        ColorSlider("Hue", hue, 0f..360f, "${hue.roundToInt()} degrees", { update(h = it) },
            listOf(Color.Red, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta, Color.Red))
        ColorSlider("Saturation", hsv[1], 0f..1f, "${(hsv[1] * 100).roundToInt()} percent", { update(s = it) })
        ColorSlider("Brightness", hsv[2], 0f..1f, "${(hsv[2] * 100).roundToInt()} percent", { update(v = it) })
        if (allowAlpha) ColorSlider("Alpha", color.alpha, 0f..1f, "${(color.alpha * 100).roundToInt()} percent", { update(a = it) }, listOf(color.copy(alpha = 0f), color.copy(alpha = 1f)), true)
    }
    LaunchedEffect(color) { if (valid && parseHexColor(hex)?.argb != (color.toArgb().toLong() and 0xFFFFFFFFL)) hex = color.hex() }
    val bring = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()
    OutlinedTextField(hex, { input ->
        hex = input
        val parsed = parseHexColor(input)
        valid = parsed != null && (allowAlpha || (parsed.argb shr 24) == 255L)
        onValidityChange(valid)
        if (valid) onChange(Color(parsed!!.argb))
    }, Modifier.fillMaxWidth().bringIntoViewRequester(bring).onFocusChanged { if (it.isFocused) scope.launch { bring.bringIntoView() } },
        label = { Text("Hex color") }, singleLine = true, isError = !valid,
        supportingText = { Text(if (valid) if (allowAlpha) "#RRGGBB or #AARRGGBB (alpha first)" else "#RRGGBB" else "Enter 6 or 8 hexadecimal digits${if (!allowAlpha) " with full opacity" else ""}.") },
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters, autoCorrectEnabled = false, keyboardType = KeyboardType.Ascii),
        textStyle = FrogTheme.typography.code, shape = FrogTheme.shapes.sm)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ColorSlider(label: String, value: Float, range: ClosedFloatingPointRange<Float>, description: String, onChange: (Float) -> Unit,
    gradient: List<Color>? = null, checker: Boolean = false) {
    Row(Modifier.fillMaxWidth().heightIn(min = FrogTheme.sizing.minimumTouchTarget), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(label, Modifier.width(78.dp), style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.foreground)
        Slider(value, onChange, Modifier.weight(1f).semantics { contentDescription = label; stateDescription = description }, valueRange = range,
            thumb = { Box(Modifier.size(16.dp).background(FrogTheme.colors.foreground, CircleShape).border(2.dp, FrogTheme.colors.surfaceElevated, CircleShape)) },
            track = {
                Box(Modifier.fillMaxWidth().height(4.dp).clip(FrogTheme.shapes.sm)
                    .then(if (checker) Modifier.checkerboard() else Modifier)
                    .then(if (gradient != null) Modifier.background(Brush.horizontalGradient(gradient)) else Modifier.background(FrogTheme.colors.muted))) {
                    if (gradient == null) Box(Modifier.fillMaxWidth(((value - range.start) / (range.endInclusive - range.start)).coerceIn(0f, 1f)).height(4.dp).background(FrogTheme.colors.foreground))
                }
            })
        Text(description.replace(" degrees", "°").replace(" percent", "%"), Modifier.widthIn(min = 36.dp), style = FrogTheme.typography.bodySmall, color = FrogTheme.colors.mutedForeground)
    }
}
