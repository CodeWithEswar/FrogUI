import groovy.json.JsonSlurper

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "io.github.codewitheswar.frogui.registry"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    testImplementation(libs.junit)
}

val generatedRegistry = layout.buildDirectory.dir("generated/registry/kotlin")
val generateComponentRegistry = tasks.register("generateComponentRegistry") {
    val repositoryRoot = rootProject.layout.projectDirectory.asFile
    val registryOutputDirectory = generatedRegistry
    group = "build setup"
    description = "Validates canonical metadata and generates data-only Kotlin catalog records."
    inputs.dir(rootProject.layout.projectDirectory.dir("registry"))
    inputs.dir(rootProject.layout.projectDirectory.dir("frogui-components/src/main"))
    inputs.dir(rootProject.layout.projectDirectory.dir("docs"))
    outputs.dir(generatedRegistry)

    doLast {
        fun readObject(file: java.io.File): Map<*, *> =
            JsonSlurper().parse(file) as? Map<*, *>
                ?: error("Expected JSON object: $file")

        fun repositoryFile(path: String): java.io.File {
            val file = repositoryRoot.resolve(path).canonicalFile
            require(file.toPath().startsWith(repositoryRoot.canonicalFile.toPath())) {
                "Registry path escapes repository: $path"
            }
            require(file.isFile) { "Missing registry reference: $path" }
            return file
        }

        fun string(record: Map<*, *>, key: String): String =
            (record[key] as? String)?.takeIf { it.isNotBlank() }
                ?: error("Missing/non-string '$key' in ${record["id"] ?: record}")

        fun records(record: Map<*, *>, key: String): List<Map<*, *>> =
            (record[key] as? List<*>)?.map {
                it as? Map<*, *> ?: error("$key requires object entries")
            } ?: error("Missing/non-array '$key'")

        fun strings(record: Map<*, *>, key: String): List<String> =
            (record[key] as? List<*>)?.map {
                it as? String ?: error("$key requires string entries")
            }?.also { require(it.distinct().size == it.size) { "Duplicate $key" } }
                ?: error("Missing/non-array '$key'")

        // Escape data as Kotlin literals, including '$'; JSON is never executable source.
        fun quote(value: String): String = buildString {
            append('"')
            value.forEach { char ->
                append(when (char) {
                    '\\' -> "\\\\"
                    '"' -> "\\\""
                    '$' -> "\\$"
                    '\n' -> "\\n"
                    '\r' -> "\\r"
                    '\t' -> "\\t"
                    else -> if (char.code < 32) "\\u%04x".format(char.code) else char.toString()
                })
            }
            append('"')
        }
        fun stringList(values: List<String>) = values.joinToString(", ", "listOf(", ")", transform = ::quote)

        val categories = mapOf(
            "actions" to "Actions", "inputs" to "Inputs", "data-display" to "DataDisplay",
            "feedback" to "Feedback", "navigation" to "Navigation", "overlays" to "Overlays",
            "layout" to "Layout"
        )
        val statuses = mapOf("experimental" to "Experimental", "beta" to "Beta",
            "stable" to "Stable", "deprecated" to "Deprecated")
        val index = readObject(repositoryFile("registry/index.json"))
        val entries = records(index, "components")
        val ids = entries.map { string(it, "id") }
        require(ids.distinct().size == ids.size) { "Duplicate registry IDs" }
        val files = entries.map { string(it, "file") }
        require(files.toSet() == repositoryRoot.resolve("registry/components").listFiles()
            .orEmpty().filter { it.extension == "json" }.map { "components/${it.name}" }.toSet()) {
            "registry/index.json must list every component record exactly once"
        }
        val output = entries.map { entry ->
            val id = string(entry, "id")
            require(id.matches(Regex("[a-z0-9]+(-[a-z0-9]+)*"))) { "Invalid ID: $id" }
            require(string(entry, "file") == "components/$id.json") { "Non-canonical file for $id" }
            val record = readObject(repositoryFile("registry/${string(entry, "file")}"))
            require(string(record, "id") == id) { "Index ID mismatch: $id" }
            val name = string(record, "name")
            require(name.matches(Regex("Frog[A-Z][a-zA-Z0-9]*"))) { "Invalid component name: $name" }
            val source = string(record, "source")
            require(source.startsWith("frogui-components/src/main/") && source.endsWith(".kt"))
            require(Regex("\\bfun\\s+${Regex.escape(name)}\\s*\\(").containsMatchIn(repositoryFile(source).readText())) {
                "$id must reference its actual Kotlin component"
            }
            val category = categories[string(record, "category")] ?: error("Invalid category: $id")
            val status = statuses[string(record, "status")] ?: error("Invalid status: $id")
            require(string(record, "docs") == "/components/$id") { "Non-canonical docs route: $id" }
            require(string(record, "since").matches(Regex("[0-9]+\\.[0-9]+\\.[0-9]+")))
            val properties = records(record, "properties")
            val examples = records(record, "examples")
            require(properties.map { string(it, "name") }.distinct().size == properties.size)
            require(examples.map { string(it, "id") }.distinct().size == examples.size)
            if (status == "Stable") {
                require(properties.isNotEmpty() && examples.isNotEmpty() && record["accessibility"] is Map<*, *>) {
                    "Stable $id needs properties, examples, and accessibility metadata"
                }
                val review = string(record, "stabilityReview")
                require(review == "docs/components/$id-review.md") { "Non-canonical stability review: $id" }
                require(repositoryFile(review).readText().isNotBlank()) { "Stable $id needs review evidence" }
            }
            val propertyCode = properties.joinToString(",\n") {
                "        ComponentPropertyMetadata(" + listOf("name", "type", "defaultValue", "description")
                    .joinToString(", ") { key -> quote(string(it, key)) } + ")"
            }
            val exampleCode = examples.joinToString(",\n") {
                "        ComponentExampleMetadata(" + listOf("id", "title", "description", "codeSnippet")
                    .joinToString(", ") { key -> quote(string(it, key)) } + ")"
            }
            """
                FrogComponentMetadata(
                    id = ${quote(id)},
                    name = ${quote(name)},
                    displayName = ${quote(string(record, "displayName"))},
                    description = ${quote(string(record, "description"))},
                    category = FrogComponentCategory.$category,
                    status = FrogComponentStatus.$status,
                    since = ${quote(string(record, "since"))},
                    docsPath = ${quote(string(record, "docs").removePrefix("/"))},
                    variants = ${stringList(strings(record, "variants"))},
                    sizes = ${stringList(strings(record, "sizes"))},
                    properties = listOf(
                $propertyCode
                    ),
                    examples = listOf(
                $exampleCode
                    )
                )
            """.trimIndent()
        }
        val target = registryOutputDirectory.get().file(
            "io/github/codewitheswar/frogui/registry/GeneratedComponentRegistry.kt"
        ).asFile
        target.parentFile.mkdirs()
        target.writeText("""
            // Generated from registry/components/*.json. Do not edit.
            package io.github.codewitheswar.frogui.registry

            internal val generatedComponents: List<FrogComponentMetadata> = listOf(
            ${output.joinToString(",\n")}
            )
        """.trimIndent() + "\n")
    }
}

androidComponents.onVariants { variant ->
    variant.sources.kotlin?.addStaticSourceDirectory(generatedRegistry.get().asFile.absolutePath)
}
tasks.named("preBuild").configure { dependsOn(generateComponentRegistry) }
