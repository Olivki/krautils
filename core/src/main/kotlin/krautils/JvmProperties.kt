/*
 * Copyright 2020 Oliver Berg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package krautils

/*
 * Unlike environment variables, property variables *can* change what they return during the life-cycle of the JVM
 * instance that is running the program, and therefore it is *generally* not wise to cache the results, However, as the
 * properties we are creating here are the ones that are provided by the actual JVM itself, it *should* be safe to cache
 * them, as they *shouldn't* change during the life-cycle of the JVM, and if they do, it is most likely not from a
 * legal JVM operation. It is also important to keep in mind that we are *not* caching the values on creation but
 * rather we cache them after the first invocation of them, this behaviour may change in a future release if it causes
 * issues.
 */

/**
 * Contains properties of all the JVM properties that are defined by the JVM itself.
 */
public object JvmProperties {
    // TODO: add documentation of what the properties here do?
    // separators
    public val fileSeparator: String by lazy { get("file.separator") }

    public val lineSeparator: String by lazy { get("line.separator") }

    public val pathSeparator: String by lazy { get("path.separator") }

    // operating system
    public val osArch: String by lazy { get("os.arch") }

    public val osName: String by lazy { get("os.name") }

    public val osVersion: String by lazy { get("os.version") }

    // user
    public val userCountry: String by lazy { get("user.country") }

    public val userCountryFormat: String by lazy { get("user.country.format") }

    public val userDir: String by lazy { get("user.dir") }

    public val userHome: String by lazy { get("user.home") }

    public val userLanguage: String by lazy { get("user.language") }

    public val userLanguageFormat: String by lazy { get("user.language.format") }

    public val userName: String by lazy { get("user.name") }

    // io
    public val fileEncoding: String by lazy { get("file.encoding") }

    public val tmpDir: String by lazy { get("java.io.tmpdir") }

    // java specific
    public val javaClassPath: String by lazy { get("java.class.path") }

    public val javaClassVersion: String by lazy { get("java.class.version") }

    public val javaHome: String by lazy { get("java.home") }

    public val javaRuntimeName: String by lazy { get("java.runtime.name") }

    public val javaRuntimeVersion: String by lazy { get("java.runtime.version") }

    public val javaSpecificationName: String by lazy { get("java.specification.name") }

    public val javaSpecificationVendor: String by lazy { get("java.specification.vendor") }

    public val javaSpecificationVersion: String by lazy { get("java.specification.version") }

    public val javaVendor: String by lazy { get("java.vendor") }

    public val javaVendorUrl: String by lazy { get("java.vendor.url") }

    public val javaVendorUrlBug: String by lazy { get("java.vendor.url.bug") }

    public val javaVersion: String by lazy { get("java.version") }

    public val javaVmInfo: String by lazy { get("java.vm.info") }

    public val javaVmName: String by lazy { get("java.vm.name") }

    public val javaVmSpecificationName: String by lazy { get("java.vm.specification.name") }

    public val javaVmSpecificationVendor: String by lazy { get("java.vm.specification.vendor") }

    public val javaVmSpecificationVersion: String by lazy { get("java.vm.specification.version") }

    public val javaVmVersion: String by lazy { get("java.vm.version") }

    /**
     * Returns the system property stored under the given [key], or throws a [NoSuchElementException] if none is
     * found.
     *
     * @throws [IllegalArgumentException] if [key] is empty
     *
     * @see [System.getProperty]
     */
    public operator fun get(key: String): String =
        System.getProperty(key) ?: throw NoSuchElementException("Unknown property '$key'")

    /**
     * Returns the system property stored under the given [key], or `null` if none is found.
     *
     * @throws [IllegalArgumentException] if [key] is empty
     *
     * @see [System.getProperty]
     */
    public fun getOrNull(key: String): String? = System.getProperty(key)

    /**
     * Sets the system property stored under the given [key] to [value].
     *
     * @throws [IllegalArgumentException] if [key] is empty.
     *
     * @see [System.setProperty]
     */
    public operator fun set(key: String, value: String) {
        System.setProperty(key, value)
    }
}