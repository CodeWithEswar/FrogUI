plugins { id("frogui.android.library") }
android { namespace = "io.github.codewitheswar.frogui.registry" }
dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    testImplementation(libs.junit)
}

val generatedRegistry = layout.buildDirectory.dir("generated/registry/kotlin")
val generateComponentRegistry = tasks.register<Exec>("generateComponentRegistry") {
    group = "build setup"
    description = "Validates registry schemas and generates typed Kotlin metadata."
    workingDir(rootProject.layout.projectDirectory)
    inputs.files(rootProject.fileTree("registry"), rootProject.fileTree("tools/registry"))
    inputs.files(rootProject.fileTree("app/src/main"), rootProject.fileTree("frogui-components/src/main"))
    inputs.files(rootProject.fileTree("docs/content"), rootProject.fileTree("docs/components"))
    inputs.files(rootProject.file("gradle/release.properties"), rootProject.file("package-lock.json"))
    outputs.dir(generatedRegistry)
    commandLine("node", "tools/registry/generate.mjs", "--android", generatedRegistry.get().asFile.absolutePath)
}
androidComponents.onVariants { variant ->
    variant.sources.kotlin?.addStaticSourceDirectory(generatedRegistry.get().asFile.absolutePath)
}
tasks.named("preBuild").configure { dependsOn(generateComponentRegistry) }
