/*
 * Copyright 2020-2022 Oliver Berg
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

package net.ormr.krautils.io

import net.ormr.krautils.KrautilsExperimental
import net.ormr.krautils.io.AppDirs.Default.instance
import net.ormr.krautils.system.JvmProperties
import net.ormr.krautils.system.JvmProperties.userHome
import net.ormr.krautils.system.getEnv
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists
import kotlin.io.path.useLines

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

        override fun getDownloadsDirectory(): Path = instance.getDownloadsDirectory()

        override fun getDesktopDirectory(): Path = instance.getDesktopDirectory()

        override fun getDocumentsDirectory(): Path = instance.getDocumentsDirectory()

        override fun getMusicDirectory(): Path = instance.getMusicDirectory()

        override fun getPicturesDirectory(): Path = instance.getPicturesDirectory()

        override fun getVideosDirectory(): Path = instance.getVideosDirectory()

        override fun getUserDataDirectory(roaming: Boolean): Path = instance.getUserDataDirectory(roaming)

        override fun getUserConfigDirectory(roaming: Boolean): Path = instance.getUserConfigDirectory(roaming)

        override fun getUserCacheDirectory(): Path = instance.getUserCacheDirectory()

        override fun getUserLogDirectory(): Path = instance.getUserLogDirectory()

        override fun getSiteDataDirectory(local: Boolean): Path = instance.getSiteDataDirectory(local)

        override fun getSiteConfigDirectory(): Path = instance.getSiteConfigDirectory()
    }

    /**
     * Returns the downloads directory of the current user.
     */
    @KrautilsExperimental
    public abstract fun getDownloadsDirectory(): Path

    /**
     * Returns the desktop directory of the current user.
     */
    @KrautilsExperimental
    public abstract fun getDesktopDirectory(): Path

    /**
     * Returns the documents directory of the current user.
     */
    @KrautilsExperimental
    public abstract fun getDocumentsDirectory(): Path

    /**
     * Returns the music directory of the current user.
     */
    @KrautilsExperimental
    public abstract fun getMusicDirectory(): Path

    /**
     * Returns the pictures directory of the current user.
     */
    @KrautilsExperimental
    public abstract fun getPicturesDirectory(): Path

    /**
     * Returns the videos directory of the current user.
     */
    @KrautilsExperimental
    public abstract fun getVideosDirectory(): Path

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
        // according to this SO answer https://stackoverflow.com/questions/567874/how-do-i-find-the-users-documents-folder-with-java-in-os-x#comment379566_567902
        // the localization should only be for displaying it, and doesn't actually change the "physical" files name
        // so this code should work, however, the apple page he links to doesn't exist anymore, so I'm not 100% certain

        override fun getDownloadsDirectory(): Path = Path(userHome, "Downloads")

        override fun getDesktopDirectory(): Path = Path(userHome, "Desktop")

        override fun getDocumentsDirectory(): Path = Path(userHome, "Documents")

        override fun getMusicDirectory(): Path = Path(userHome, "Music")

        override fun getPicturesDirectory(): Path = Path(userHome, "Pictures")

        override fun getVideosDirectory(): Path = Path(userHome, "Videos")

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
        override fun getDownloadsDirectory(): Path = XdgUserDirs.XDG_DOWNLOAD_DIR.path

        override fun getDesktopDirectory(): Path = XdgUserDirs.XDG_DESKTOP_DIR.path

        override fun getDocumentsDirectory(): Path = XdgUserDirs.XDG_DOCUMENTS_DIR.path

        override fun getMusicDirectory(): Path = XdgUserDirs.XDG_MUSIC_DIR.path

        override fun getPicturesDirectory(): Path = XdgUserDirs.XDG_PICTURES_DIR.path

        override fun getVideosDirectory(): Path = XdgUserDirs.XDG_VIDEOS_DIR.path

        override fun getUserDataDirectory(roaming: Boolean): Path = Path(userHome, ".local", "share")

        override fun getUserConfigDirectory(roaming: Boolean): Path = Path(userHome, ".config")

        override fun getUserCacheDirectory(): Path = Path(userHome, ".cache")

        override fun getUserLogDirectory(): Path = Path(userHome, ".cache", "logs")

        override fun getSiteDataDirectory(local: Boolean): Path = when {
            local -> Path("/", "usr", "local", "share")
            else -> Path("/", "usr", "share")
        }

        override fun getSiteConfigDirectory(): Path = Path("/", "etc")

        // based on https://github.com/erayerdin/kappdirs/blob/master/src/main/kotlin/io/github/erayerdin/kappdirs/appdirs/UnixAppDirs.kt#L79
        // the functionality is the same, it's just been made more "idiomatic"
        private enum class XdgUserDirs(defaultPath: Path) {
            XDG_DOWNLOAD_DIR(Path(userHome, "Downloads")),
            XDG_DESKTOP_DIR(Path(userHome, "Desktop")),
            XDG_DOCUMENTS_DIR(Path(userHome, "Documents")),
            XDG_MUSIC_DIR(Path(userHome, "Music")),
            XDG_PICTURES_DIR(Path(userHome, "Pictures")),
            XDG_VIDEOS_DIR(Path(userHome, "Videos"));

            private companion object {
                val userDirConfig: Path by lazy { Path(userHome, ".config", "user-dirs.dirs") }
            }

            val path: Path by lazy {
                return@lazy when {
                    userDirConfig.notExists() -> defaultPath
                    else -> userDirConfig.useLines { lines ->
                        lines.map(String::trim)
                            .filter { it.startsWith(name) }
                            .map(this::parsePath)
                            .lastOrNull() ?: defaultPath
                    }
                }
            }

            private fun parsePath(text: String): Path = text.split('=')[1]
                .drop(1)
                .dropLast(1)
                .splitToSequence(JvmProperties.fileSeparator)
                .toMutableList()
                .also { it.remove("\$HOME") }
                .fold(Path(userHome)) { parent, path -> parent.resolve(path) }
        }
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

        override fun getDownloadsDirectory(): Path = Path(userHome, "Downloads")

        override fun getDesktopDirectory(): Path = Path(userHome, "Desktop")

        override fun getDocumentsDirectory(): Path = Path(userHome, "My Documents")

        override fun getMusicDirectory(): Path = Path(userHome, "My Music")

        override fun getPicturesDirectory(): Path = Path(userHome, "My Pictures")

        override fun getVideosDirectory(): Path = Path(userHome, "My Videos")

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
public fun AppDirs.createDownloadsDirectory(name: String): Path =
    getDownloadsDirectory().resolve(name).createDirectories()

@KrautilsExperimental
public fun AppDirs.createDesktopDirectory(name: String): Path =
    getDesktopDirectory().resolve(name).createDirectories()

@KrautilsExperimental
public fun AppDirs.createDocumentsDirectory(name: String): Path =
    getDocumentsDirectory().resolve(name).createDirectories()

@KrautilsExperimental
public fun AppDirs.createPicturesDirectory(name: String): Path =
    getPicturesDirectory().resolve(name).createDirectories()

@KrautilsExperimental
public fun AppDirs.createVideosDirectory(name: String): Path =
    getVideosDirectory().resolve(name).createDirectories()

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