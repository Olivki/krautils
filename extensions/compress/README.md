## compress

![Maven Central](https://img.shields.io/maven-central/v/net.ormr.krautils/krautils-compress?label=release&style=for-the-badge)

Kotlin extensions for compressing/decompressing `ByteArray`s and `Path` instances.

Uses the [Apache Commons Compress](https://commons.apache.org/proper/commons-compress/) library and related libraries under the hood.

This module does not depend on the [core](../../core/README.md) module.

## Installation

Release

```kotlin
dependencies {
	implementation(group = "net.ormr.krautils", name = "krautils-compress", version = "${RELEASE_VERSION}")
}
```

Snapshot

```kotlin
repositories {
	maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
	implementation(group = "net.ormr.krautils", name = "krautils-compress", version = "${SNAPSHOT_VERSION}")
}
```