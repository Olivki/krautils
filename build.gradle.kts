plugins {
    id("me.him188.maven-central-publish") version "1.0.0-dev-3"
    kotlin("jvm") version "1.6.21"
}

val kotestVersion: String by project

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "me.him188.maven-central-publish")
    apply(plugin = "kotlin")

    project.group = "moe.kanon.krautils"

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation(group = "io.kotest", name = "kotest-runner-junit5", version = kotestVersion)
        testImplementation(group = "io.kotest", name = "kotest-assertions-core", version = kotestVersion)
        testImplementation(group = "io.kotest", name = "kotest-property", version = kotestVersion)
    }

    mavenCentralPublish {
        useCentralS01()
        singleDevGithubProject("Olivki", "krautils")
        licenseApacheV2()
    }

    kotlin {
        explicitApi()
    }

    tasks {
        compileKotlin {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-opt-in=kotlin.contracts.ExperimentalContracts",
                    "-Xjvm-default=all",
                )
            }
        }

        test {
            useJUnitPlatform()
        }
    }
}