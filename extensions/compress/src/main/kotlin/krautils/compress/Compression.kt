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

package krautils.compress

import org.apache.commons.compress.compressors.CompressorInputStream
import org.apache.commons.compress.compressors.CompressorOutputStream
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream
import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream
import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorInputStream
import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorOutputStream
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorInputStream
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorOutputStream
import org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream
import org.apache.commons.compress.compressors.lzma.LZMACompressorOutputStream
import org.apache.commons.compress.compressors.pack200.Pack200CompressorInputStream
import org.apache.commons.compress.compressors.pack200.Pack200CompressorOutputStream
import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorInputStream
import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorOutputStream
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorInputStream
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorOutputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

// TODO: documentation
// TODO: add functions for writing compressed files and reading

// ar, cpio, Unix dump, tar, zip, gzip, XZ, Pack200, bzip2, 7z, arj, lzma, snappy, DEFLATE, lz4, Brotli, Zstandard,
// DEFLATE64 and Z files.
public sealed class Compression {
    public abstract fun compressBytes(bytes: ByteArray): ByteArray

    public abstract fun decompressBytes(bytes: ByteArray): ByteArray

    // TODO: public abstract fun readCompressedFile(file: Path): ByteArray
    // TODO: public abstract fun writeCompressedFile(bytes: ByteArray, file: Path, options: Array<out OpenOptions>)

    public object BZip2 : Compression() {
        override fun compressBytes(bytes: ByteArray): ByteArray = bytes.compressWith(::BZip2CompressorOutputStream)

        override fun decompressBytes(bytes: ByteArray): ByteArray = bytes.decompressWith(::BZip2CompressorInputStream)
    }

    public object Deflate : Compression() {
        override fun compressBytes(bytes: ByteArray): ByteArray = bytes.compressWith(::DeflateCompressorOutputStream)

        override fun decompressBytes(bytes: ByteArray): ByteArray = bytes.decompressWith(::DeflateCompressorInputStream)
    }

    public object Gzip : Compression() {
        override fun compressBytes(bytes: ByteArray): ByteArray = bytes.compressWith(::GzipCompressorOutputStream)

        override fun decompressBytes(bytes: ByteArray): ByteArray = bytes.decompressWith(::GzipCompressorInputStream)
    }

    public object LZ4Block : Compression() {
        override fun compressBytes(bytes: ByteArray): ByteArray = bytes.compressWith(::BlockLZ4CompressorOutputStream)

        override fun decompressBytes(bytes: ByteArray): ByteArray =
            bytes.decompressWith(::BlockLZ4CompressorInputStream)
    }

    public object LZ4Framed : Compression() {
        override fun compressBytes(bytes: ByteArray): ByteArray = bytes.compressWith(::FramedLZ4CompressorOutputStream)

        override fun decompressBytes(bytes: ByteArray): ByteArray =
            bytes.decompressWith(::FramedLZ4CompressorInputStream)
    }

    public object Lzma : Compression() {
        override fun compressBytes(bytes: ByteArray): ByteArray = bytes.compressWith(::LZMACompressorOutputStream)

        override fun decompressBytes(bytes: ByteArray): ByteArray = bytes.decompressWith(::LZMACompressorInputStream)
    }

    @Pack200RemovalWarning
    public object Pack200 : Compression() {
        @Pack200RemovalWarning
        override fun compressBytes(bytes: ByteArray): ByteArray = bytes.compressWith(::Pack200CompressorOutputStream)

        @Pack200RemovalWarning
        override fun decompressBytes(bytes: ByteArray): ByteArray = bytes.decompressWith(::Pack200CompressorInputStream)
    }

    public object SnappyFramed : Compression() {
        override fun compressBytes(bytes: ByteArray): ByteArray =
            bytes.compressWith(::FramedSnappyCompressorOutputStream)

        override fun decompressBytes(bytes: ByteArray): ByteArray =
            bytes.decompressWith(::FramedSnappyCompressorInputStream)
    }

    public object XZ : Compression() {
        override fun compressBytes(bytes: ByteArray): ByteArray = bytes.compressWith(::XZCompressorOutputStream)

        override fun decompressBytes(bytes: ByteArray): ByteArray = bytes.decompressWith(::XZCompressorInputStream)
    }

    public object Zstd : Compression() {
        override fun compressBytes(bytes: ByteArray): ByteArray = bytes.compressWith(::ZstdCompressorOutputStream)

        override fun decompressBytes(bytes: ByteArray): ByteArray = bytes.decompressWith(::ZstdCompressorInputStream)
    }
}

public fun ByteArray.compress(compression: Compression): ByteArray = compression.compressBytes(this)

public fun ByteArray.decompress(compression: Compression): ByteArray = compression.decompressBytes(this)

internal inline fun <T : CompressorOutputStream> ByteArray.compressWith(factory: (OutputStream) -> T): ByteArray {
    val copiedArray = this.copyOf()
    return ByteArrayOutputStream(copiedArray.size).use { bytesOut ->
        factory(bytesOut).use { compressOut ->
            compressOut.write(copiedArray)
        }
        bytesOut.toByteArray()
    }
}

internal inline fun <T : CompressorInputStream> ByteArray.decompressWith(factory: (InputStream) -> T): ByteArray =
    factory(this.copyOf().inputStream()).readBytes()