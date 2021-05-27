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

@file:JvmName("InputStreamUtils")

package krautils.io

import java.io.IOException
import java.io.InputStream
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import kotlin.io.path.ExperimentalPathApi

/**
 * Copies the contents of the stream to the given [file].
 *
 * @return the number of bytes read or written.
 *
 * @throws [IOException] if an I/O error occurs
 *
 * @see [Files.copy]
 */
public fun InputStream.copyTo(file: Path, vararg options: CopyOption): Long = Files.copy(this, file, *options)

/**
 * Copies the contents of the stream to the given [file].
 *
 * @return the number of bytes read or written.
 *
 * @throws [IOException] if an I/O error occurs
 *
 * @see [Files.copy]
 */
public fun InputStream.copyTo(file: Path, overwrite: Boolean = false): Long {
    val options = if (overwrite) arrayOf(REPLACE_EXISTING) else emptyArray()
    return Files.copy(this, file, *options)
}