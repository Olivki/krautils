import name.remal.gradle_plugins.dsl.extensions.convention
import name.remal.gradle_plugins.dsl.extensions.get
import name.remal.gradle_plugins.dsl.extensions.testImplementation
import name.remal.gradle_plugins.dsl.extensions.testRuntimeOnly
import name.remal.gradle_plugins.plugins.publish.ossrh.RepositoryHandlerOssrhExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
    id("name.remal.maven-publish-ossrh") version "1.2.2"
    id("name.remal.check-dependency-updates") version "1.2.2"
    `maven-publish`
}

val gitUrl = "github.com/Olivki/krautils"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "name.remal.maven-publish-ossrh")
    apply(plugin = "maven-publish")

    project.group = "moe.kanon.krautils"

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation(kotlin("test-junit5"))
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
    }

    kotlin {
        explicitApi()
    }

    tasks {
        withType<KotlinCompile>() {
            with(kotlinOptions) {
                jvmTarget = "1.8"
                freeCompilerArgs = listOf(
                    "-Xuse-experimental=kotlin.Experimental",
                    "-Xuse-experimental=kotlin.contracts.ExperimentalContracts",
                    "-Xjvm-default=all"
                )
            }
        }

        test {
            useJUnitPlatform()
        }
    }

    afterEvaluate {
        publishing.publications.withType<MavenPublication> {
            pom {
                name.set("${project.group}:${project.name}")
                description.set(project.description)
                url.set(gitUrl)

                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("Olivki")
                        name.set("Oliver Berg")
                        email.set("oliver@berg.moe")
                    }
                }

                scm {
                    connection.set("scm:git:git://${gitUrl}.git")
                    developerConnection.set("scm:git:ssh://${gitUrl}.git")
                    url.set("https://$gitUrl")
                }
            }
        }

        publishing.repositories.convention[RepositoryHandlerOssrhExtension::class.java].ossrh()
    }
}