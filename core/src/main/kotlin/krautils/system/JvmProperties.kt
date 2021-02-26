/*
 * Copyright 2021 Oliver Berg
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

package krautils.system

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
    public val fileSeparator: String by lazy { getProperty("file.separator") }

    public val lineSeparator: String by lazy { getProperty("line.separator") }

    public val pathSeparator: String by lazy { getProperty("path.separator") }

    // operating system
    public val osArch: String by lazy { getProperty("os.arch") }

    public val osName: String by lazy { getProperty("os.name") }

    public val osVersion: String by lazy { getProperty("os.version") }

    // user
    public val userCountry: String by lazy { getProperty("user.country") }

    public val userCountryFormat: String by lazy { getProperty("user.country.format") }

    public val userDir: String by lazy { getProperty("user.dir") }

    public val userHome: String by lazy { getProperty("user.home") }

    public val userLanguage: String by lazy { getProperty("user.language") }

    public val userLanguageFormat: String by lazy { getProperty("user.language.format") }

    public val userName: String by lazy { getProperty("user.name") }

    // io
    public val fileEncoding: String by lazy { getProperty("file.encoding") }

    public val tmpDir: String by lazy { getProperty("java.io.tmpdir") }

    // java specific
    public val javaClassPath: String by lazy { getProperty("java.class.path") }

    public val javaClassVersion: String by lazy { getProperty("java.class.version") }

    public val javaHome: String by lazy { getProperty("java.home") }

    public val javaRuntimeName: String by lazy { getProperty("java.runtime.name") }

    public val javaRuntimeVersion: String by lazy { getProperty("java.runtime.version") }

    public val javaSpecificationName: String by lazy { getProperty("java.specification.name") }

    public val javaSpecificationVendor: String by lazy { getProperty("java.specification.vendor") }

    public val javaSpecificationVersion: String by lazy { getProperty("java.specification.version") }

    public val javaVendor: String by lazy { getProperty("java.vendor") }

    public val javaVendorUrl: String by lazy { getProperty("java.vendor.url") }

    public val javaVendorUrlBug: String by lazy { getProperty("java.vendor.url.bug") }

    public val javaVersion: String by lazy { getProperty("java.version") }

    public val javaVmInfo: String by lazy { getProperty("java.vm.info") }

    public val javaVmName: String by lazy { getProperty("java.vm.name") }

    public val javaVmSpecificationName: String by lazy { getProperty("java.vm.specification.name") }

    public val javaVmSpecificationVendor: String by lazy { getProperty("java.vm.specification.vendor") }

    public val javaVmSpecificationVersion: String by lazy { getProperty("java.vm.specification.version") }

    public val javaVmVersion: String by lazy { getProperty("java.vm.version") }
}