## core

![Maven Central](https://img.shields.io/maven-central/v/moe.kanon.krautils/krautils-core?label=release&style=for-the-badge) ![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/moe.kanon.krautils/krautils-core?label=snapshot&server=https%3A%2F%2Foss.sonatype.org&style=for-the-badge)

Various Kotlin extension functions and data structures for easing Kotlin development.

## Installation

Release

```kotlin
dependencies {
	implementation(group = "moe.kanon.krautils", name = "krautils-core", version = "${RELEASE_VERSION}")
}
```

Snapshot

```kotlin
repositories {
	maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
	implementation(group = "moe.kanon.krautils", name = "krautils-core", version = "${SNAPSHOT_VERSION}")
}
```

## License
The project is licensed under the Apache 2.0 license.
- [AppDirs](./src/main/kotlin/krautils/io/AppDirs.kt) contains code adopted from [kappdirs](https://github.com/erayerdin/kappdirs/blob/master/src/main/kotlin/io/github/erayerdin/kappdirs/appdirs/UnixAppDirs.kt#L79), which is licensed under the Apache 2.0 license.