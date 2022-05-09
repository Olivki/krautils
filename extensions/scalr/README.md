## scalr

![Maven Central](https://img.shields.io/maven-central/v/net.ormr.krautils/krautils-scalr?label=release&style=for-the-badge)

Kotlin extension functions for working with the Java [imgscalr](https://github.com/rkalla/imgscalr) library.

This module does not depend on the [core](../../core/README.md) module.

## Installation

Release

```kotlin
dependencies {
	implementation(group = "net.ormr.krautils", name = "krautils-scalr", version = "${RELEASE_VERSION}")
}
```

Snapshot

```kotlin
repositories {
	maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
	implementation(group = "net.ormr.krautils", name = "krautils-scalr", version = "${SNAPSHOT_VERSION}")
}
```