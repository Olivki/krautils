## jdk17

![Maven Central](https://img.shields.io/maven-central/v/net.ormr.krautils/krautils-jdk17?label=release&style=for-the-badge)

Kotlin extension functions for working with features added from JDK 9 to JDK 17.

This module does not depend on the [core](../../core/README.md) module, but it does complement it.

## Installation

Release

```kotlin
dependencies {
	implementation(group = "net.ormr.krautils", name = "krautils-jdk17", version = "${RELEASE_VERSION}")
}
```

Snapshot

```kotlin
repositories {
	maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
	implementation(group = "net.ormr.krautils", name = "krautils-jdk17", version = "${SNAPSHOT_VERSION}")
}
```