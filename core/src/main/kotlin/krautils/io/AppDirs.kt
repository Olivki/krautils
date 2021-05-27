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

package krautils.io

import krautils.KrautilsExperimental
import krautils.system.JvmProperties
import krautils.system.JvmProperties.userHome
import krautils.system.getEnv
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectories

// inspired by https://github.com/harawata/appdirs and https://github.com/erayerdin/kappdirs

// TODO: documentation
@KrautilsExperimental
public sealed class AppDirs {
    /**
     * A [AppDirs] implementation that delegates to [MacOsX], [Unix] or [Windows] depending on the current
     * [osName][JvmProperties.osName].
     */
    @KrautilsExperimental
    public companion object Default : AppDirs() {
        internal val instance: AppDirs by lazy {
            val os = JvmProperties.osName.lowercase()
            when {
                os.startsWith("windows") -> Windows
                os.startsWith("mac os x") -> MacOsX
                // assume it's some *nix based system
                else -> Unix
            }
        }

        override fun getUserDataDirectory(roaming: Boolean): Path = instance.getUserDataDirectory(roaming)

        override fun getUserConfigDirectory(roaming: Boolean): Path = instance.getUserConfigDirectory(roaming)

        override fun getUserCacheDirectory(): Path = instance.getUserCacheDirectory()

        override fun getUserLogDirectory(): Path = instance.getUserLogDirectory()

        override fun getSiteDataDirectory(local: Boolean): Path = instance.getSiteDataDirectory(local)

        override fun getSiteConfigDirectory(): Path = instance.getSiteConfigDirectory()
    }

    /**
     * Returns the user data directory of the operating system.
     *
     * @param [roaming] whether or not the directory should be created in the `roaming` directory *(only used
     * on Windows systems)*
     */
    @KrautilsExperimental
    public abstract fun getUserDataDirectory(roaming: Boolean): Path

    /**
     * Returns the user config directory of the operating system.
     *
     * @param [roaming] whether or not the directory should be created in the `roaming` directory *(only used
     * on Windows systems)*
     */
    @KrautilsExperimental
    public abstract fun getUserConfigDirectory(roaming: Boolean): Path

    /**
     * Returns the user cache directory of the operating system.
     */
    @KrautilsExperimental
    public abstract fun getUserCacheDirectory(): Path

    /**
     * Returns the user log directory of the operating system.
     */
    @KrautilsExperimental
    public abstract fun getUserLogDirectory(): Path

    /**
     * Returns the site data directory of the operating system.
     */
    @KrautilsExperimental
    public abstract fun getSiteDataDirectory(local: Boolean): Path

    /**
     * Returns the site config directory of the operating system.
     */
    @KrautilsExperimental
    public abstract fun getSiteConfigDirectory(): Path

    /**
     * Implementation for MacOsX systems.
     */
    @KrautilsExperimental
    public object MacOsX : AppDirs() {
        override fun getUserDataDirectory(roaming: Boolean): Path =
            Path(userHome, "Library", "Application Support")

        override fun getUserConfigDirectory(roaming: Boolean): Path = getUserDataDirectory(roaming)

        override fun getUserCacheDirectory(): Path = Path(userHome, "Library", "Caches")

        override fun getUserLogDirectory(): Path = Path(userHome, "Library", "Logs")

        override fun getSiteDataDirectory(local: Boolean): Path =
            Path("/", "Library", "Application Support")

        override fun getSiteConfigDirectory(): Path = getSiteDataDirectory(false)
    }

    /**
     * Implementation for *nix based systems.
     */
    @KrautilsExperimental
    public object Unix : AppDirs() {
        override fun getUserDataDirectory(roaming: Boolean): Path = Path(userHome, ".local", "share")

        override fun getUserConfigDirectory(roaming: Boolean): Path = Path(userHome, ".config")

        override fun getUserCacheDirectory(): Path = Path(userHome, ".cache")

        override fun getUserLogDirectory(): Path = Path(userHome, ".cache", "logs")

        override fun getSiteDataDirectory(local: Boolean): Path = when {
            local -> Path("/", "usr", "local", "share")
            else -> Path("/", "usr", "share")
        }

        override fun getSiteConfigDirectory(): Path = Path("/", "etc")
    }

    /**
     * Implementation for Windows systems.
     */
    @KrautilsExperimental
    public object Windows : AppDirs() {
        // lazy so that we don't encounter any exceptions when this instance is created
        private val appData by lazy { getEnv("APPDATA") }
        private val localAppData by lazy { getEnv("LOCALAPPDATA") }
        private val programData by lazy { getEnv("PROGRAMDATA") }

        override fun getUserDataDirectory(roaming: Boolean): Path = Path(if (roaming) appData else localAppData)

        override fun getUserConfigDirectory(roaming: Boolean): Path = getUserDataDirectory(roaming)

        override fun getUserCacheDirectory(): Path = Path(localAppData)

        override fun getUserLogDirectory(): Path = Path(localAppData)

        override fun getSiteDataDirectory(local: Boolean): Path = Path(programData)

        override fun getSiteConfigDirectory(): Path = getSiteDataDirectory(false)
    }
}

// TODO: documentation

@KrautilsExperimental
public fun AppDirs.createUserDataDirectory(name: String, roaming: Boolean): Path =
    getUserDataDirectory(roaming).resolve(name).createDirectories()

@KrautilsExperimental
public fun AppDirs.createUserConfigDirectory(name: String, roaming: Boolean): Path =
    getUserConfigDirectory(roaming).resolve(name).createDirectories()

@KrautilsExperimental
public fun AppDirs.createUserCacheDirectory(name: String): Path = when (this) {
    is AppDirs.Default -> instance.createUserCacheDirectory(name)
    AppDirs.MacOsX, AppDirs.Unix -> getUserCacheDirectory().resolve(name).createDirectories()
    AppDirs.Windows -> getUserCacheDirectory().resolve(name).resolve("Cache").createDirectories()
}

@KrautilsExperimental
public fun AppDirs.createUserLogDirectory(name: String): Path = when (this) {
    is AppDirs.Default -> instance.createUserLogDirectory(name)
    AppDirs.MacOsX, AppDirs.Unix -> getUserLogDirectory().resolve(name).createDirectories()
    AppDirs.Windows -> getUserLogDirectory().resolve(name).resolve("Logs").createDirectories()
}

@KrautilsExperimental
public fun AppDirs.createSiteDataDirectory(name: String, local: Boolean): Path =
    getSiteDataDirectory(local).resolve(name).createDirectories()

@KrautilsExperimental
public fun AppDirs.createSiteConfigDirectory(name: String): Path =
    getSiteConfigDirectory().resolve(name).createDirectories()