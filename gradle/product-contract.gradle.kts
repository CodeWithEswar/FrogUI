// Keep in sync with docs/architecture/dependency-rules.md.
val productionEdges = mapOf(
    ":frogui-foundation" to emptySet<String>(),
    ":frogui-theme" to setOf(":frogui-foundation"),
    ":frogui-components" to setOf(":frogui-foundation", ":frogui-theme"),
    ":frogui-registry" to emptySet<String>(),
    ":frogui-testing" to setOf(":frogui-theme"),
    ":app" to setOf(":frogui-foundation", ":frogui-theme", ":frogui-components", ":frogui-registry")
)
val testEdges = mapOf(
    ":frogui-foundation" to emptySet<String>(),
    ":frogui-theme" to setOf(":frogui-testing"),
    ":frogui-components" to setOf(":frogui-registry", ":frogui-testing"),
    ":frogui-registry" to emptySet<String>(),
    ":frogui-testing" to emptySet<String>(),
    ":app" to setOf(":frogui-foundation", ":frogui-theme", ":frogui-components", ":frogui-registry", ":frogui-testing")
)
subprojects {
    val modulePath = path
    require(modulePath in productionEdges) { "$modulePath needs an explicit module boundary" }
    configurations.configureEach {
        val configurationName = name
        val isTest = name.contains("test", ignoreCase = true)
        val isDeclaration = listOf("api", "implementation", "compileOnly", "runtimeOnly").any { name.endsWith(it, true) }
        if (isDeclaration) dependencies.all {
            when (this) {
                is ProjectDependency -> {
                    val allowed = if (isTest) testEdges.getValue(modulePath) else productionEdges.getValue(modulePath)
                    require(path in allowed) { "$modulePath may not depend on $path in $configurationName" }
                }
                is ExternalModuleDependency -> if (!isTest && modulePath != ":app") {
                    val compose = group?.startsWith("androidx.compose.") == true ||
                        (group == "androidx.compose" && name == "compose-bom")
                    val permitted = group == "org.jetbrains.kotlin" || when (modulePath) {
                        ":frogui-registry" -> group == "androidx.compose.runtime" || (group == "androidx.compose" && name == "compose-bom")
                        ":frogui-foundation" -> compose && group?.startsWith("androidx.compose.material") != true
                        else -> compose || group == "androidx.core"
                    }
                    require(permitted) { "$modulePath dependency $group:$name requires a documented boundary review" }
                    require(modulePath == ":frogui-testing" || !name.startsWith("ui-test") ||
                        (name == "ui-test-manifest" && configurationName.startsWith("debug", true))) {
                        "Test harness dependency must not ship in $modulePath: $group:$name"
                    }
                }
                is FileCollectionDependency -> error("Raw file dependencies need a reviewed module/artifact boundary: $modulePath")
            }
        }
    }
}

val validateRegistry = tasks.register<Exec>("validateRegistry") {
    group = "verification"
    workingDir(rootDir)
    commandLine("node", "tools/registry/generate.mjs", "--validate")
}
val verifyDocs = tasks.register<Exec>("verifyDocs") {
    group = "verification"
    workingDir(rootDir)
    commandLine("node", "docs/scripts/build.mjs", "--generate")
}
val verifyProductContract = tasks.register("verifyProductContract") {
    group = "verification"
    description = "Validates declared module edges, schemas, source references, and documentation routes."
    dependsOn(validateRegistry, verifyDocs)
}
val verifyArchitecture = tasks.register("verifyArchitecture") {
    group = "verification"
    description = "Also verifies native route coverage using typed Showcase tests."
    dependsOn(verifyProductContract, ":app:testDebugUnitTest", ":frogui-components:testDebugUnitTest")
}
subprojects {
    tasks.matching { it.name == "check" }.configureEach { dependsOn(verifyProductContract) }
}
