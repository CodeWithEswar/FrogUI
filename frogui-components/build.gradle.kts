plugins {
    id("frogui.android.library")
    id("frogui.publishing")
}
android {
    namespace = "io.github.codewitheswar.frogui.components"
    defaultConfig { consumerProguardFiles("consumer-rules.pro") }
}
dependencies {
    api(project(":frogui-theme"))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    testImplementation(libs.junit)
    testImplementation(project(":frogui-registry"))
    debugImplementation(libs.androidx.compose.ui.tooling)
}
