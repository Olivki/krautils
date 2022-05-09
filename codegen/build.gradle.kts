import java.nio.file.Path

plugins {
    application
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(group = "com.squareup", name = "kotlinpoet", version = "1.8.0")
    implementation(group = "com.github.ajalt.clikt", name = "clikt", version = "3.1.0")
}

application {
    mainClass.set("net.ormr.krautils.codegen.MainKt")
}

kotlin {
    explicitApi = null
}

val coreSourceDirectory: Path by lazy {
    project(":core").kotlin
        .sourceSets
        .main
        .get()
        .kotlin
        .sourceDirectories
        .single { it.name.endsWith("kotlin") }
        .toPath()
}

tasks {
    register<JavaExec>("generateArrayExtensions") {
        setup("code generation [core]")
        args(coreSourceDirectory.toString(), "array_extensions")
    }
}

fun JavaExec.setup(group: String) {
    this.group = group
    mainClass.set(application.mainClass.get())
    classpath = sourceSets.main.get().runtimeClasspath
}