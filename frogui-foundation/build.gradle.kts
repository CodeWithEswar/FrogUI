plugins {
    id("frogui.android.library")
    id("frogui.publishing")
}
android { namespace = "io.github.codewitheswar.frogui.foundation" }
dependencies {
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.ui.text)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.animation.core)
    testImplementation(libs.junit)
}
