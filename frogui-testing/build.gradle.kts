plugins { id("frogui.android.library") }
android { namespace = "io.github.codewitheswar.frogui.testing" }
dependencies {
    api(project(":frogui-theme"))
    api(libs.androidx.compose.ui.test.junit4)
}
