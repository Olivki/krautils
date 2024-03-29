plugins {
    java
}

description = "Kotlin extensions for JDK 17"
version = "0.1.0"

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}