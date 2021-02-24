## scalr

![Maven Central](https://img.shields.io/maven-central/v/moe.kanon.krautils/krautils-scalr?style=for-the-badge) ![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/moe.kanon.krautils/krautils-scalr?server=https%3A%2F%2Foss.sonatype.org&style=for-the-badge)

Kotlin extension functions for working with the Java [imgscalr](https://github.com/rkalla/imgscalr) library.

This module does not depend on the [core](../../core/README.md) module.

## Installation

Release

```kotlin
dependencies {
	implementation(group = "moe.kanon.krautils", name = "krautils-scalr", version = "${RELEASE_VERSION}")
}
```

Snapshot

```kotlin
repositories {
	maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
	implementation(group = "moe.kanon.krautils", name = "krautils-scalr", version = "${SNAPSHOT_VERSION}")
}
```