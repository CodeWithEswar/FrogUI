package io.github.codewitheswar.frogui.navigation

import org.junit.Assert.*
import org.junit.Test

class ComponentDeepLinkTest {
    @Test fun knownAndUnknownIdsShareTheComponentRouteGrammar() {
        assertEquals("button", componentIdFromDeepLink("frogui://components/button"))
        assertEquals("drawer", componentIdFromDeepLink("frogui://components/drawer"))
        assertEquals("future-component", componentIdFromDeepLink("frogui://components/future-component"))
    }
    @Test fun malformedOrUnrelatedLinksAreIgnored() {
        listOf(null, "", "https://components/button", "frogui://other/button", "frogui://components/button/extra", "frogui://components/../button", "frogui://components/button?x=1", "frogui://user@components/button").forEach { assertNull(componentIdFromDeepLink(it)) }
    }
}
