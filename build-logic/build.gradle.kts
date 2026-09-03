plugins { `kotlin-dsl` }

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.compose.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("frogAndroidLibrary") {
            id = "frogui.android.library"
            implementationClass = "FrogAndroidLibraryPlugin"
        }
        register("frogPublishing") {
            id = "frogui.publishing"
            implementationClass = "FrogPublishingPlugin"
        }
    }
}
