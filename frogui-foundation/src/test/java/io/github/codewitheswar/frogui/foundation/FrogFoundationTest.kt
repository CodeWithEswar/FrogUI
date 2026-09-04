package io.github.codewitheswar.frogui.foundation

import androidx.compose.animation.core.SnapSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.codewitheswar.frogui.foundation.adaptive.*
import io.github.codewitheswar.frogui.foundation.motion.FrogMotion
import io.github.codewitheswar.frogui.foundation.sizing.FrogSizing
import org.junit.Assert.*
import org.junit.Test

class FrogFoundationTest {
    @Test fun widthsResolveWithoutRoundingAcrossPhoneTabletAndDesktop() {
        val policy = FrogAdaptive()
        listOf(360f, 390f, 412f, 599.99f).forEach { assertEquals(FrogWindowSizeClass.Compact, policy.windowSizeClass(it.dp)) }
        listOf(600f, 839.99f).forEach { assertEquals(FrogWindowSizeClass.Medium, policy.windowSizeClass(it.dp)) }
        listOf(840f, 1000f, 1400f).forEach { assertEquals(FrogWindowSizeClass.Expanded, policy.windowSizeClass(it.dp)) }
    }

    @Test fun nestedPolicyCanChangeThresholdsWithoutMutatingTheOuterPolicy() {
        val outer = FrogAdaptive()
        val nested = outer.copy(mediumMinWidth = 700.dp, expandedMinWidth = 1000.dp)
        assertEquals(FrogWindowSizeClass.Medium, outer.windowSizeClass(650.dp))
        assertEquals(FrogWindowSizeClass.Compact, nested.windowSizeClass(650.dp))
        assertEquals(FrogWindowSizeClass.Medium, nested.windowSizeClass(999.99.dp))
        assertEquals(FrogWindowSizeClass.Expanded, nested.windowSizeClass(1000.dp))
    }

    @Test fun invalidAdaptiveInputsFailInsteadOfChoosingAnAccidentalLayout() {
        listOf(Dp.Unspecified, Dp.Infinity, (-1).dp).forEach { width ->
            assertThrows(IllegalArgumentException::class.java) { FrogAdaptive().windowSizeClass(width) }
        }
        assertThrows(IllegalArgumentException::class.java) { FrogAdaptive(840.dp, 600.dp) }
        assertThrows(IllegalArgumentException::class.java) { FrogAdaptive(600.dp, 600.dp) }
    }

    @Test fun sizingCanEnlargeTargetsWithoutEnlargingCompactVisuals() {
        val original = FrogSizing()
        val roomy = original.copy(minimumTouchTarget = 56.dp)
        assertEquals(original.controlSmall, roomy.controlSmall)
        assertTrue(roomy.minimumTouchTarget > original.minimumTouchTarget)
        assertThrows(IllegalArgumentException::class.java) { original.copy(minimumTouchTarget = 32.dp) }
        assertThrows(IllegalArgumentException::class.java) { original.copy(iconSmall = Dp.Unspecified) }
    }

    @Test fun reductionPreservesEasingAndDisablesSpringAsWellAsDurations() {
        val original = FrogMotion(fastDurationMillis = 160, normalDurationMillis = 230, largeDurationMillis = 310)
        val reduced = original.reduced()
        assertTrue(reduced.isReduced)
        assertFalse(original.isReduced)
        assertSame(original.enterEasing, reduced.enterEasing)
        assertTrue(reduced.responsiveSpring<Float>() is SnapSpec)
        assertTrue(original.responsiveSpring<Float>() is SpringSpec)
        assertEquals(reduced, reduced.reduced())
    }
}
