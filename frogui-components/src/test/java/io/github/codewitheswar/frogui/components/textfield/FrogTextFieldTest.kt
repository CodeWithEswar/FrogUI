package io.github.codewitheswar.frogui.components.textfield

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FrogTextFieldTest {

    @Test
    fun defaultDimensionsMatchSpecifications() {
        assertEquals(56.dp, FrogTextFieldDefaults.minHeight(FrogTextFieldVariant.Filled))
        assertEquals(56.dp, FrogTextFieldDefaults.minHeight(FrogTextFieldVariant.Outline))
        assertEquals(48.dp, FrogTextFieldDefaults.minHeight(FrogTextFieldVariant.Underline))

        // Accessible touch target guarantee
        assertTrue(FrogTextFieldDefaults.minHeight(FrogTextFieldVariant.Filled) >= 48.dp)
        assertTrue(FrogTextFieldDefaults.minHeight(FrogTextFieldVariant.Outline) >= 48.dp)
        assertTrue(FrogTextFieldDefaults.minHeight(FrogTextFieldVariant.Underline) >= 48.dp)

        assertEquals(20.dp, FrogTextFieldDefaults.IconSize)
        assertEquals(1.dp, FrogTextFieldDefaults.indicatorThickness(focused = false))
        assertEquals(2.dp, FrogTextFieldDefaults.indicatorThickness(focused = true))
    }

    @Test
    fun colorsModelIntegrity() {
        val colors = FrogTextFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Gray,
            readOnlyTextColor = Color.DarkGray,
            containerColor = Color.White,
            focusedContainerColor = Color.LightGray,
            disabledContainerColor = Color.Transparent,
            cursorColor = Color.Blue,
            indicatorColor = Color.DarkGray,
            focusedIndicatorColor = Color.Blue,
            disabledIndicatorColor = Color.LightGray,
            errorIndicatorColor = Color.Red,
            labelColor = Color.Gray,
            focusedLabelColor = Color.Black,
            disabledLabelColor = Color.LightGray,
            errorLabelColor = Color.Red,
            placeholderColor = Color.Gray,
            disabledPlaceholderColor = Color.LightGray,
            helperTextColor = Color.DarkGray,
            errorTextColor = Color.Red,
            leadingIconColor = Color.DarkGray,
            trailingIconColor = Color.DarkGray,
            disabledLeadingIconColor = Color.LightGray,
            disabledTrailingIconColor = Color.LightGray,
        )

        assertEquals(Color.Black, colors.textColor)
        assertEquals(Color.Gray, colors.disabledTextColor)
        assertEquals(Color.DarkGray, colors.readOnlyTextColor)
        assertEquals(Color.White, colors.containerColor)
        assertEquals(Color.LightGray, colors.focusedContainerColor)
        assertEquals(Color.Red, colors.errorIndicatorColor)
        assertEquals(Color.Red, colors.errorTextColor)
        assertEquals(Color.Blue, colors.cursorColor)
    }

    @Test
    fun variantsCoverAllForms() {
        val variants = FrogTextFieldVariant.entries
        assertEquals(3, variants.size)
        assertTrue(variants.contains(FrogTextFieldVariant.Filled))
        assertTrue(variants.contains(FrogTextFieldVariant.Outline))
        assertTrue(variants.contains(FrogTextFieldVariant.Underline))
    }

    @Test
    fun contentPaddingAdaptsToSlotsAndVariants() {
        val defaultPadding = FrogTextFieldDefaults.contentPadding(FrogTextFieldVariant.Filled, hasLeading = false, hasTrailing = false)
        val leadingPadding = FrogTextFieldDefaults.contentPadding(FrogTextFieldVariant.Filled, hasLeading = true, hasTrailing = false)
        val trailingPadding = FrogTextFieldDefaults.contentPadding(FrogTextFieldVariant.Filled, hasLeading = false, hasTrailing = true)
        val underlinePadding = FrogTextFieldDefaults.contentPadding(FrogTextFieldVariant.Underline, hasLeading = false, hasTrailing = false)

        // Underline uses 0dp horizontal start/end to align with text baseline
        assertEquals(underlinePadding, FrogTextFieldDefaults.contentPadding(FrogTextFieldVariant.Underline))
    }
}
