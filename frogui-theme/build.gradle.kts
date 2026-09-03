plugins {
    id("frogui.android.library")
    id("frogui.publishing")
}
android { namespace = "io.github.codewitheswar.frogui.theme" }
dependencies {
    api(project(":frogui-foundation"))
    implementation(libs.androidx.compose.material3)
    androidTestImplementation(project(":frogui-testing"))
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
