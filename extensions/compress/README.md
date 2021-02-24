## compress

![Maven Central](https://img.shields.io/maven-central/v/moe.kanon.krautils/krautils-compress?label=release&style=for-the-badge) ![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/moe.kanon.krautils/krautils-compress?label=snapshot&server=https%3A%2F%2Foss.sonatype.org&style=for-the-badge)

Kotlin extensions for compressing/decompressing `ByteArray`s and `Path` instances.

Uses the [Apache Commons Compress](https://commons.apache.org/proper/commons-compress/) library and related libraries under the hood.

This module does not depend on the [core](../../core/README.md) module.

## Installation

Release

```kotlin
dependencies {
	implementation(group = "moe.kanon.krautils", name = "krautils-compress", version = "${RELEASE_VERSION}")
}
```

Snapshot

```kotlin
repositories {
	maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
	implementation(group = "moe.kanon.krautils", name = "krautils-compress", version = "${SNAPSHOT_VERSION}")
}
```