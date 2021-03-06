import name.remal.gradle_plugins.dsl.extensions.convention
import name.remal.gradle_plugins.dsl.extensions.get
import name.remal.gradle_plugins.plugins.publish.ossrh.RepositoryHandlerOssrhExtension

plugins {
    kotlin("jvm") version "1.5.10"
    id("name.remal.maven-publish-ossrh") version "1.3.1"
    id("name.remal.check-dependency-updates") version "1.3.1"
    `maven-publish`
}

val kotestVersion: String by project

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
        testImplementation(group = "io.kotest", name = "kotest-runner-junit5", version = kotestVersion)
        testImplementation(group = "io.kotest", name = "kotest-assertions-core", version = kotestVersion)
        testImplementation(group = "io.kotest", name = "kotest-property", version = kotestVersion)
    }

    kotlin {
        explicitApi()
    }

    tasks {
        compileKotlin {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = freeCompilerArgs + listOf(
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
                url.set("https://github.com/Olivki/krautils")

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
                    connection.set("scm:git:git://github.com/Olivki/krautils.git")
                    developerConnection.set("scm:git:ssh://github.com/Olivki/krautils.git")
                    url.set("https://github.com/Olivki/krautils")
                }
            }
        }

        publishing.repositories.convention[RepositoryHandlerOssrhExtension::class.java].ossrh()
    }
}