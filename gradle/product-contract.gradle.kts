// Keep this policy synchronized with docs/architecture/dependency-rules.md.
val allowedProjects = mapOf(
    ":frogui-foundation" to emptySet<String>(),
    ":frogui-components" to setOf(":frogui-foundation"),
    ":frogui-registry" to emptySet<String>(),
    ":app" to setOf(":frogui-foundation", ":frogui-components", ":frogui-registry")
)

subprojects {
    val modulePath = path
    val allowed = allowedProjects[modulePath]
        ?: throw GradleException("$modulePath needs a documented product-contract module boundary")
    configurations.configureEach {
        // Test harness dependencies do not ship in the library's production runtime.
        val isProductionDeclaration = listOf("api", "implementation", "compileOnly", "runtimeOnly")
            .any { name.endsWith(it, ignoreCase = true) }
        if (isProductionDeclaration && !name.contains("test", ignoreCase = true)) {
            dependencies.all {
                when (this) {
                    is ProjectDependency -> require(path in allowed) {
                        "$modulePath may not depend on $path ($name). See dependency-rules.md."
                    }
                    is ExternalModuleDependency -> if (modulePath != ":app") {
                        val permitted = group == "org.jetbrains.kotlin" ||
                            if (modulePath == ":frogui-registry") {
                                group == "androidx.compose.runtime" ||
                                    (group == "androidx.compose" && name == "compose-bom")
                            } else {
                                group?.startsWith("androidx.compose.") == true ||
                                    (group == "androidx.compose" && name == "compose-bom") ||
                                    group == "androidx.core"
                            }
                        require(permitted) {
                            "$modulePath dependency $group:$name requires a documented boundary review."
                        }
                    }
                }
            }
        }
    }
}

val verifyProductContract = tasks.register("verifyProductContract") {
    group = "verification"
    description = "Checks configured module boundaries and canonical registry invariants."
    dependsOn(":frogui-registry:generateComponentRegistry")
}

subprojects {
    tasks.matching { it.name == "check" }.configureEach {
        dependsOn(verifyProductContract)
    }
}
