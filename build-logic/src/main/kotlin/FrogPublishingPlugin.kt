import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create

/** Local, reviewable publication output. Remote upload/signing is deliberately separate. */
class FrogPublishingPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        require(name in setOf("frogui-foundation", "frogui-theme", "frogui-components")) {
            "Only reusable production libraries may apply frogui.publishing"
        }
        val artifactName = name
        pluginManager.apply("maven-publish")
        extensions.configure<LibraryExtension> {
            publishing { singleVariant("release") { withSourcesJar() } }
        }
        extensions.configure<PublishingExtension> {
            repositories {
                maven {
                    name = "build"
                    url = rootProject.layout.buildDirectory.dir("maven").get().asFile.toURI()
                }
            }
        }
        afterEvaluate {
            extensions.configure<PublishingExtension> {
                publications {
                    create<MavenPublication>("release") {
                        from(components.getByName("release"))
                        artifactId = artifactName
                        pom {
                            name.set(artifactName)
                            description.set("Native Android Compose components and design tokens for FrogUI.")
                            url.set("https://github.com/codewitheswar/FrogUI")
                            licenses { license { name.set("Apache License 2.0"); url.set("https://www.apache.org/licenses/LICENSE-2.0") } }
                            developers { developer { id.set("codewitheswar"); name.set("FrogUI maintainers") } }
                            scm {
                                url.set("https://github.com/codewitheswar/FrogUI")
                                connection.set("scm:git:https://github.com/codewitheswar/FrogUI.git")
                            }
                        }
                    }
                }
            }
        }
    }
}
