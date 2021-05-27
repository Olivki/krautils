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

@file:JvmName("BufferedImageUtils")

package krautils.io

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.file.OpenOption
import java.nio.file.Path
import java.nio.file.StandardOpenOption.CREATE
import java.nio.file.StandardOpenOption.READ
import java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
import java.nio.file.StandardOpenOption.WRITE
import javax.imageio.ImageIO
import javax.imageio.stream.ImageInputStream
import javax.imageio.stream.ImageOutputStream
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.inputStream
import kotlin.io.path.outputStream

/**
 * The informal name of the default format used for the "writing" convience functions.
 */
private const val DEFAULT_FORMAT = "png"

/**
 * Returns a newly created [BufferedImage], which will have the given [width] and [height], containing only the given
 * [color].
 */
@JvmOverloads
public fun createBufferedImage(width: Int, height: Int, color: Color = Color.BLACK): BufferedImage =
    BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB).apply {
        createGraphics().apply {
            setColor(color)
            fillRect(0, 0, width, height)
            dispose()
        }
    }

/**
 * Constructs a new [ImageInputStream] for the file and returns it as a result.
 *
 * @throws [IOException] if an I/O error occurs
 *
 * @see [Path.inputStream]
 * @see [ImageIO.createImageInputStream]
 */
@JvmOverloads
@JvmName("newImageInputStream")
public fun Path.imageInputStream(vararg options: OpenOption = arrayOf(READ)): ImageInputStream =
    inputStream(*options).use { ImageIO.createImageInputStream(it) }
        ?: throw IllegalStateException("Failed to create a ImageInputStream")

/**
 * Constructs a new [ImageOutputStream] for the file and returns it as a result.
 *
 * @throws [IOException] if an I/O error occurs
 *
 * @see [Path.outputStream]
 * @see [ImageIO.createImageInputStream]
 */
@JvmOverloads
@JvmName("newImageOutputStream")
public fun Path.imageOutputStream(
    vararg options: OpenOption = arrayOf(CREATE, WRITE, TRUNCATE_EXISTING),
): ImageOutputStream = outputStream(*options).use { ImageIO.createImageOutputStream(it) }
    ?: throw IllegalStateException("Failed to create a ImageOutputStream")

/**
 * Writes the image to the given [file], using the given [format] and [options].
 *
 * @throws [IOException] if an I/O error occurs
 *
 * @see [Path.outputStream]
 * @see [ImageIO.write]
 */
@JvmOverloads
public fun BufferedImage.writeTo(
    file: Path,
    format: String = file.extension?.lowercase() ?: DEFAULT_FORMAT,
    vararg options: OpenOption = arrayOf(CREATE, WRITE, TRUNCATE_EXISTING),
): Boolean = file.outputStream(*options).use { ImageIO.write(this, format, it) }

/**
 * Writes the contents of the image to the given [target] using the given [format].
 *
 * @throws [IOException] if an I/O error occurs
 *
 * @see [ImageIO.write]
 */
@JvmOverloads
public fun BufferedImage.writeTo(target: OutputStream, format: String = DEFAULT_FORMAT): Boolean =
    ImageIO.write(this, format, target)

/**
 * Constructs a new [ByteArray] from the contents of the image, using the given [format] and returns it as a result.
 *
 * @throws [IOException] if an I/O error occurs
 *
 * @see [ImageIO.write]
 */
@JvmOverloads
public fun BufferedImage.toByteArray(format: String = DEFAULT_FORMAT): ByteArray {
    val out = ByteArrayOutputStream()
    writeTo(out, format)
    return out.toByteArray()
}