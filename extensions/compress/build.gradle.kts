description = "Kotlin extensions for the Java Apache Commons Compress library."
version = "0.0.1-SNAPSHOT"

dependencies {
    api(group = "org.apache.commons", name = "commons-compress", version = "1.20")
    implementation(group = "com.github.luben", name = "zstd-jni", version = "1.4.4-7")
    implementation(group = "org.tukaani", name = "xz", version = "1.8")
}