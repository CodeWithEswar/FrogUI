import java.util.Properties

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

val releaseInfo = Properties().apply {
    rootProject.file("gradle/release.properties").inputStream().use { load(it) }
}
extra["frogVersionCode"] = releaseInfo.getProperty("versionCode")
allprojects {
    group = "io.github.codewitheswar"
    version = releaseInfo.getProperty("version")
}
apply(from = "gradle/product-contract.gradle.kts")
