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

import java.io.IOException
import java.io.OutputStream
import java.math.BigInteger
import java.nio.file.*
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.SignStyle
import java.time.temporal.ChronoField
import java.util.regex.PatternSyntaxException
import kotlin.io.path.*
import kotlin.streams.asSequence

/**
 * The default glob value used for any parameters that require a glob value.
 */
private const val DEFAULT_GLOB = "*"

private val DATE_DIRECTORY_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
    .appendValue(ChronoField.YEAR_OF_ERA, 4, 19, SignStyle.EXCEEDS_PAD)
    .appendLiteral('/')
    .appendValue(ChronoField.MONTH_OF_YEAR, 2)
    .appendLiteral('/')
    .appendValue(ChronoField.DAY_OF_MONTH, 2)
    .toFormatter()

private val DATE_TIME_DIRECTORY_FORMATTER: DateTimeFormatter = DateTimeFormatterBuilder()
    .append(DATE_DIRECTORY_FORMATTER)
    .appendLiteral('/')
    .appendValue(ChronoField.HOUR_OF_DAY, 2)
    .appendLiteral('/')
    .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
    .appendLiteral('/')
    .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
    .toFormatter()

/**
 * Returns the extension of the file, or `null` if either the file represents a directory or no extension could be
 * found.
 *
 * Note that this resolves the extensions of a file in a very naive way: The extension is assumed to be the contents
 * after the *last* occurrence of the `.` character in the [name][Path.name] of the file. Meaning that this may return
 * an incorrect extension depending on how the extension is formed, but for the vast majority of file extensions, it
 * should be correct.
 */
public val Path.extension: String?
    get() = if (hasExtension(this)) name.substringAfterLast('.') else null

/**
 * Returns the [name][Path.name] of the file with its [extension][Path.extension] removed, or `name` if the file has
 * no `extension`.
 */
public val Path.simpleName: String
    get() = if (hasExtension(this)) name.substringBeforeLast('.') else name

private fun hasExtension(path: Path): Boolean = when {
    path.isDirectory() -> false
    else -> path.name.let { '.' in it && !it.startsWith('.') && it.lastIndexOf('.') != it.lastIndex }
}

// TODO: add params to documentation
/**
 * Constructs a new [DirectoryStream] for the file and returns it as a result.
 *
 * @throws [PatternSyntaxException] if the `glob` pattern is invalid
 * @throws [NotDirectoryException] if the file is not a directory
 * @throws [IOException] if an I/O error occurs
 *
 * @see [Files.newDirectoryStream]
 */
public fun Path.directoryStream(glob: String = DEFAULT_GLOB): DirectoryStream<Path> =
    Files.newDirectoryStream(this, glob)

// TODO: add params to documentation
/**
 * Walks the files tree.
 *
 * @throws [IllegalArgumentException] if [maxDepth] is negative
 * @throws [IOException] if an I/O error occurs
 *
 * @see [Files.walkFileTree]
 */
public fun Path.walkFileTree(
    options: Set<FileVisitOption> = emptySet(),
    maxDepth: Int = Int.MAX_VALUE,
    visitor: FileVisitor<Path>,
): Path = Files.walkFileTree(this, options, maxDepth, visitor)

// TODO: this documentation sounds dodgy
/**
 * Invokes the given [block] with a sequence of all the files found in the file.
 *
 * @throws [IllegalArgumentException] if [maxDepth] is negative
 * @throws [IOException] if an I/O error occurs
 *
 * @see [Files.walk]
 */
public inline fun <T> Path.walk(
    maxDepth: Int = Int.MAX_VALUE,
    vararg options: FileVisitOption,
    block: (Sequence<Path>) -> T,
): T = Files.walk(this, maxDepth, *options).use { block(it.asSequence()) }

/**
 * Calculates the size of the directory and returns the size as a result.
 *
 * This will ignore symbolic links when calculating the size.
 *
 * @throws [NotDirectoryException] if the file is not a directory
 */
public fun Path.directorySize(): BigInteger {
    if (!isDirectory()) {
        throw NotDirectoryException(toString())
    }

    var size = BigInteger.ZERO

    walkFileTree(visitor = object : SimpleFileVisitor<Path>() {
        override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
            size += file?.fileSize()?.toBigInteger() ?: return FileVisitResult.CONTINUE
            return FileVisitResult.CONTINUE
        }

        override fun visitFileFailed(file: Path?, exc: IOException?): FileVisitResult = FileVisitResult.CONTINUE
    })

    return size
}

/**
 * Creates *(if not already existing)* a series of directories reflecting the given [date], up to the day unit, under
 * the directory.
 *
 * If `date` is that of `2018-05-25` then the following directories will be created; `./2018/05/25/`
 *
 * @param [date] the date to create the directories from
 *
 * @return the last directory in the chain of the newly created directories
 *
 * @throws [NoSuchFileException] if the file does not have an existing file on the `file-system`
 * @throws [NotDirectoryException] if the file is not a directory
 *
 * @see [Files.createDirectories]
 */
public fun Path.createDateDirectories(date: LocalDate = LocalDate.now()): Path {
    if (!isDirectory()) {
        throw NotDirectoryException(toString())
    }

    return resolve(date.format(DATE_DIRECTORY_FORMATTER)).createDirectories()
}

/**
 * Creates *(if not already existing)* a series of directories reflecting the given [date], up to the day unit, under
 * the directory.
 *
 * If `date` is that of `2018-05-25T20:16:03` then the following directories will be created; `./2018/05/25/20/16/03/`
 *
 * @param [date] the date to create the directories from
 *
 * @return the last directory in the chain of the newly created directories
 *
 * @throws [NoSuchFileException] if the file does not have an existing file on the `file-system`
 * @throws [NotDirectoryException] if the file is not a directory
 *
 * @see [Files.createDirectories]
 */
public fun Path.createDateTimeDirectories(date: LocalDate = LocalDate.now()): Path {
    if (!isDirectory()) {
        throw NotDirectoryException(toString())
    }

    return resolve(date.format(DATE_TIME_DIRECTORY_FORMATTER)).createDirectories()
}

/**
 * Renames the file to the given [name].
 *
 * @see [Path.moveTo]
 */
public fun Path.renameTo(name: String, vararg options: CopyOption): Path = moveTo(resolveSibling(name), *options)

/**
 * Renames the file to the given [name].
 *
 * @see [Path.moveTo]
 */
public fun Path.renameTo(name: String, overwrite: Boolean = false): Path {
    val options = if (overwrite) arrayOf(REPLACE_EXISTING) else emptyArray()
    return moveTo(resolveSibling(name), *options)
}

/**
 * Copies the bytes of the file to the given [outputStream].
 *
 * @return the number of bytes read or written
 *
 * @throws [IOException] if an I/O error occurs
 *
 * @see [Files.copy]
 */
public fun Path.copyTo(outputStream: OutputStream): Long = Files.copy(this, outputStream)

/**
 * Updates the [lastModifiedTime][Path.getLastModifiedTime] of the file to the current time, or creates a new empty
 * file if the file doesn't already exist.
 *
 * @throws [IOException] if an I/O error occurs
 */
public fun Path.touch(): Path {
    if (exists()) {
        setLastModifiedTime(FileTime.fromMillis(System.currentTimeMillis()))
    } else {
        createFile()
    }

    return this
}