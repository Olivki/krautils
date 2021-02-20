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

@file:JvmName("AppDirs")

package krautils.io

import krautils.JvmProperties
import krautils.JvmProperties.userHome
import krautils.KrautilsExperimental
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.createDirectories

// inspired by https://github.com/harawata/appdirs and https://github.com/erayerdin/kappdirs

@ExperimentalPathApi
@KrautilsExperimental
private sealed class AppDirsFactory {
    companion object {
        fun create(): AppDirsFactory {
            val os = JvmProperties.osName.toLowerCase()
            return when {
                os.startsWith("windows") -> Windows
                os.startsWith("mac os x") -> MacOsX
                // assume it's some *nix based system
                else -> Unix
            }
        }
    }

    abstract fun getUserDataDir(name: String, roaming: Boolean): Path

    abstract fun getUserConfigDir(name: String, roaming: Boolean): Path

    abstract fun getUserCacheDir(name: String): Path

    abstract fun getUserLogDir(name: String): Path

    abstract fun getSiteDataDir(name: String, local: Boolean): Path

    abstract fun getSiteConfigDir(name: String): Path

    private object MacOsX : AppDirsFactory() {
        override fun getUserDataDir(name: String, roaming: Boolean): Path =
            Path(userHome, "Library", "Application Support", name)

        override fun getUserConfigDir(name: String, roaming: Boolean): Path = getUserDataDir(name, roaming)

        override fun getUserCacheDir(name: String): Path = Path(userHome, "Library", "Caches", name)

        override fun getUserLogDir(name: String): Path = Path(userHome, "Library", "Logs", name)

        override fun getSiteDataDir(name: String, local: Boolean): Path =
            Path("/", "Library", "Application Support", name)

        override fun getSiteConfigDir(name: String): Path = getSiteDataDir(name, false)
    }

    private object Unix : AppDirsFactory() {
        override fun getUserDataDir(name: String, roaming: Boolean): Path = Path(userHome, ".local", "share", name)

        override fun getUserConfigDir(name: String, roaming: Boolean): Path = Path(userHome, ".config", name)

        override fun getUserCacheDir(name: String): Path = Path(userHome, ".cache", name)

        override fun getUserLogDir(name: String): Path = Path(userHome, ".cache", name, "logs")

        override fun getSiteDataDir(name: String, local: Boolean): Path = when {
            local -> Path("/", "usr", "local", "share", name)
            else -> Path("/", "usr", "share", name)
        }

        override fun getSiteConfigDir(name: String): Path = Path("/", "etc", name)
    }

    private object Windows : AppDirsFactory() {
        // lazy so that we don't encounter any exceptions when this instance is created
        private val appData by lazy { getEnv("APPDATA") }
        private val localAppData by lazy { getEnv("LOCALAPPDATA") }
        private val programData by lazy { getEnv("PROGRAMDATA") }

        private fun getEnv(name: String): String =
            System.getenv(name) ?: throw NoSuchElementException("Could not find environment variable '$name'")

        override fun getUserDataDir(name: String, roaming: Boolean): Path =
            Path(if (roaming) appData else localAppData, name)

        override fun getUserConfigDir(name: String, roaming: Boolean): Path = getUserDataDir(name, roaming)

        override fun getUserCacheDir(name: String): Path = Path(localAppData, name, "Cache")

        override fun getUserLogDir(name: String): Path = Path(localAppData, name, "Logs")

        override fun getSiteDataDir(name: String, local: Boolean): Path = Path(programData, name)

        override fun getSiteConfigDir(name: String): Path = getSiteDataDir(name, false)
    }
}

@ExperimentalPathApi
@KrautilsExperimental
private val factory: AppDirsFactory by lazy { AppDirsFactory.create() }

// TODO: improve the documentation

/**
 * Returns a newly created [Path] instance pointing to a `directory` made using the given [name], the directory is
 * located in the current operating systems user data directory.
 *
 * @param [name] the name of the application to create the directory for
 * @param [roaming] whether or not the directory should be created in the `roaming` directory *(only used on Windows)*
 *
 * @return the newly created directory
 */
@ExperimentalPathApi
@KrautilsExperimental
public fun createDataDirectory(name: String, roaming: Boolean = true): Path =
    factory.getUserDataDir(name, roaming).createDirectories()

/**
 * Returns a newly created [Path] instance pointing to a `directory` made using the given [name], the directory is
 * located in the current operating systems user config directory.
 *
 * @param [name] the name of the application to create the directory for
 * @param [roaming] whether or not the directory should be created in the `roaming` directory *(only used on Windows)*
 *
 * @return the newly created directory
 */
@ExperimentalPathApi
@KrautilsExperimental
public fun createConfigDirectory(name: String, roaming: Boolean = true): Path =
    factory.getUserConfigDir(name, roaming).createDirectories()

/**
 * Returns a newly created [Path] instance pointing to a `directory` made using the given [name], the directory is
 * located in the current operating systems user cache directory.
 *
 * @param [name] the name of the application to create the directory for
 *
 * @return the newly created directory
 */
@ExperimentalPathApi
@KrautilsExperimental
public fun createCacheDirectory(name: String): Path = factory.getUserCacheDir(name).createDirectories()

/**
 * Returns a newly created [Path] instance pointing to a `directory` made using the given [name], the directory is
 * located in the current operating systems user log directory.
 *
 * @param [name] the name of the application to create the directory for
 *
 * @return the newly created directory
 */
@ExperimentalPathApi
@KrautilsExperimental
public fun createLogDirectory(name: String): Path = factory.getUserLogDir(name).createDirectories()