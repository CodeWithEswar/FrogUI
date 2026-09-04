import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import kotlinx.validation.KotlinApiBuildTask
import kotlinx.validation.KotlinApiCompareTask
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.work.DisableCachingByDefault
import java.util.zip.ZipFile

/** Uses the published release classes, including Kotlin metadata, with AGP built-in Kotlin. */
class FrogApiValidationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val catalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
        val runtime = configurations.create("frogApiValidationRuntime") {
            isCanBeConsumed = false
            isCanBeResolved = true
            isVisible = false
            description = "Build-only Kotlin-aware ABI extraction dependencies."
        }
        listOf("asm", "asm-tree", "kotlin-metadata-jvm").forEach {
            dependencies.add(runtime.name, catalog.findLibrary(it).get())
        }
        val moduleName = name
        extensions.getByType<LibraryAndroidComponentsExtension>().onVariants(
            extensions.getByType<LibraryAndroidComponentsExtension>().selector().withBuildType("release")
        ) { variant ->
            val classes = tasks.register<FrogApiClassesTask>("extractApiClasses") {
                aar.set(variant.artifacts.get(SingleArtifact.AAR))
                outputJar.set(layout.buildDirectory.file("api-input/classes.jar"))
            }
            val rawCandidate = tasks.register<KotlinApiBuildTask>("apiBuild") {
                group = "verification"
                description = "Extracts a candidate Kotlin JVM ABI without changing the reviewed baseline."
                inputJar.set(classes.flatMap { it.outputJar })
                outputApiFile.set(layout.buildDirectory.file("api-raw/$moduleName.api"))
                runtimeClasspath.from(runtime)
            }
            val candidate = tasks.register<FrogNormalizeApiTask>("apiNormalize") {
                inputFile.set(rawCandidate.flatMap { it.outputApiFile })
                outputFile.set(layout.buildDirectory.file("api/$moduleName.api"))
            }
            val baseline = layout.projectDirectory.file("api/$moduleName.api")
            val boundary = tasks.register<FrogApiBoundaryTask>("verifyPublicApiBoundary") {
                group = "verification"
                apiFile.set(candidate.flatMap { it.outputFile })
            }
            val check = tasks.register<KotlinApiCompareTask>("apiCheck") {
                group = "verification"
                description = "Fails on any unreviewed public ABI change; never updates the baseline."
                projectApiFile.set(baseline)
                generatedApiFile.set(candidate.flatMap { it.outputFile })
                dependsOn(boundary)
            }
            tasks.register<FrogApiDumpTask>("apiDump") {
                group = "api maintenance"
                description = "Accepts the candidate ABI. Run explicitly only after reviewing the API diff."
                candidateFile.set(candidate.flatMap { it.outputFile })
                baselineFile.set(baseline)
            }
            tasks.named("check").configure { dependsOn(check) }
        }
    }
}

@CacheableTask
abstract class FrogNormalizeApiTask : DefaultTask() {
    @get:InputFile @get:PathSensitive(PathSensitivity.NONE)
    abstract val inputFile: RegularFileProperty
    @get:OutputFile abstract val outputFile: RegularFileProperty

    @TaskAction fun normalize() {
        val output = outputFile.get().asFile
        output.parentFile.mkdirs()
        output.writeText(inputFile.get().asFile.readText().trimEnd() + "\n")
    }
}

@DisableCachingByDefault(because = "Small verification task with no outputs")
abstract class FrogApiBoundaryTask : DefaultTask() {
    @get:InputFile @get:PathSensitive(PathSensitivity.NONE)
    abstract val apiFile: RegularFileProperty

    @TaskAction fun verify() {
        val forbidden = listOf("androidx/compose/material", "hugeicons/", "coil/", "coil3/",
            "org/commonmark/", "io/github/codewitheswar/frogui/showcase/",
            "io/github/codewitheswar/frogui/registry/", "io/github/codewitheswar/frogui/testing/")
        val violations = apiFile.get().asFile.readLines().filter { line -> forbidden.any(line::contains) }
        check(violations.isEmpty()) { "Implementation/tooling types leak into published API:\n${violations.joinToString("\n")}" }
    }
}

@CacheableTask
abstract class FrogApiClassesTask : DefaultTask() {
    @get:InputFile @get:PathSensitive(PathSensitivity.NONE)
    abstract val aar: RegularFileProperty
    @get:OutputFile abstract val outputJar: RegularFileProperty

    @TaskAction fun extract() {
        val output = outputJar.get().asFile
        output.parentFile.mkdirs()
        ZipFile(aar.get().asFile).use { archive ->
            val entry = requireNotNull(archive.getEntry("classes.jar")) { "Release AAR has no classes.jar" }
            archive.getInputStream(entry).use { input -> output.outputStream().use(input::copyTo) }
        }
    }
}

@DisableCachingByDefault(because = "Explicit maintainer acceptance of a reviewed API baseline")
abstract class FrogApiDumpTask : DefaultTask() {
    @get:InputFile @get:PathSensitive(PathSensitivity.NONE)
    abstract val candidateFile: RegularFileProperty
    @get:OutputFile abstract val baselineFile: RegularFileProperty

    @TaskAction fun accept() {
        val output = baselineFile.get().asFile
        output.parentFile.mkdirs()
        candidateFile.get().asFile.copyTo(output, overwrite = true)
    }
}
