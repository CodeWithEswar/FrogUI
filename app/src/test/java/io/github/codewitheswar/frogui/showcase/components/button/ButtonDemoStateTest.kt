package io.github.codewitheswar.frogui.showcase.components.button

import org.junit.Assert.assertTrue
import org.junit.Test

class ButtonDemoStateTest {
    @Test fun usageGeneratorEscapesConsumerTextAsAKotlinLiteral() {
        val code = ButtonDemoState(buttonText = "Save \"draft\"\n\$value\\path").toCodeSnippet()
        assertTrue(code.contains("Text(\"Save \\\"draft\\\"\\n\\\$value\\\\path\")"))
    }
}
