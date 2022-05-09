/*
 * Copyright 2021-2022 Oliver Berg
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

@file:JvmMultifileClass
@file:JvmName("ArrayUtils")

package net.ormr.krautils.collections

// AUTO GENERATED, DO NOT EDIT

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the given
 * [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapTo(destination: BooleanArray, transform: (T) -> Boolean): BooleanArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * the given [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapIndexedTo(
    destination: BooleanArray,
    transform: (i: Int, e: T) -> Boolean
): BooleanArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(i, e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a newly
 * created [BooleanArray].
 */
public inline fun <T> Array<T>.mapToBooleanArray(transform: (T) -> Boolean): BooleanArray =
    mapTo(BooleanArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * a newly created [BooleanArray].
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 */
public inline fun <T> Array<T>.mapIndexedToBooleanArray(transform: (i: Int, e: T) -> Boolean): BooleanArray =
    mapIndexedTo(BooleanArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the given
 * [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapTo(destination: CharArray, transform: (T) -> Char): CharArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * the given [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapIndexedTo(destination: CharArray, transform: (i: Int, e: T) -> Char): CharArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(i, e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a newly
 * created [CharArray].
 */
public inline fun <T> Array<T>.mapToCharArray(transform: (T) -> Char): CharArray =
    mapTo(CharArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * a newly created [CharArray].
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 */
public inline fun <T> Array<T>.mapIndexedToCharArray(transform: (i: Int, e: T) -> Char): CharArray =
    mapIndexedTo(CharArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the given
 * [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapTo(destination: ByteArray, transform: (T) -> Byte): ByteArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * the given [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapIndexedTo(destination: ByteArray, transform: (i: Int, e: T) -> Byte): ByteArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(i, e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a newly
 * created [ByteArray].
 */
public inline fun <T> Array<T>.mapToByteArray(transform: (T) -> Byte): ByteArray =
    mapTo(ByteArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * a newly created [ByteArray].
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 */
public inline fun <T> Array<T>.mapIndexedToByteArray(transform: (i: Int, e: T) -> Byte): ByteArray =
    mapIndexedTo(ByteArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the given
 * [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapTo(destination: ShortArray, transform: (T) -> Short): ShortArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * the given [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapIndexedTo(destination: ShortArray, transform: (i: Int, e: T) -> Short): ShortArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(i, e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a newly
 * created [ShortArray].
 */
public inline fun <T> Array<T>.mapToShortArray(transform: (T) -> Short): ShortArray =
    mapTo(ShortArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * a newly created [ShortArray].
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 */
public inline fun <T> Array<T>.mapIndexedToShortArray(transform: (i: Int, e: T) -> Short): ShortArray =
    mapIndexedTo(ShortArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the given
 * [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapTo(destination: IntArray, transform: (T) -> Int): IntArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * the given [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapIndexedTo(destination: IntArray, transform: (i: Int, e: T) -> Int): IntArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(i, e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a newly
 * created [IntArray].
 */
public inline fun <T> Array<T>.mapToIntArray(transform: (T) -> Int): IntArray = mapTo(IntArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * a newly created [IntArray].
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 */
public inline fun <T> Array<T>.mapIndexedToIntArray(transform: (i: Int, e: T) -> Int): IntArray =
    mapIndexedTo(IntArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the given
 * [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapTo(destination: LongArray, transform: (T) -> Long): LongArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * the given [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapIndexedTo(destination: LongArray, transform: (i: Int, e: T) -> Long): LongArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(i, e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a newly
 * created [LongArray].
 */
public inline fun <T> Array<T>.mapToLongArray(transform: (T) -> Long): LongArray =
    mapTo(LongArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * a newly created [LongArray].
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 */
public inline fun <T> Array<T>.mapIndexedToLongArray(transform: (i: Int, e: T) -> Long): LongArray =
    mapIndexedTo(LongArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the given
 * [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapTo(destination: FloatArray, transform: (T) -> Float): FloatArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * the given [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapIndexedTo(destination: FloatArray, transform: (i: Int, e: T) -> Float): FloatArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(i, e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a newly
 * created [FloatArray].
 */
public inline fun <T> Array<T>.mapToFloatArray(transform: (T) -> Float): FloatArray =
    mapTo(FloatArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * a newly created [FloatArray].
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 */
public inline fun <T> Array<T>.mapIndexedToFloatArray(transform: (i: Int, e: T) -> Float): FloatArray =
    mapIndexedTo(FloatArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the given
 * [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapTo(destination: DoubleArray, transform: (T) -> Double): DoubleArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * the given [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
public inline fun <T> Array<T>.mapIndexedTo(
    destination: DoubleArray,
    transform: (i: Int, e: T) -> Double
): DoubleArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(i, e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a newly
 * created [DoubleArray].
 */
public inline fun <T> Array<T>.mapToDoubleArray(transform: (T) -> Double): DoubleArray =
    mapTo(DoubleArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * a newly created [DoubleArray].
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 */
public inline fun <T> Array<T>.mapIndexedToDoubleArray(transform: (i: Int, e: T) -> Double): DoubleArray =
    mapIndexedTo(DoubleArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the given
 * [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapTo(destination: UByteArray, transform: (T) -> UByte): UByteArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * the given [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapIndexedTo(destination: UByteArray, transform: (i: Int, e: T) -> UByte): UByteArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(i, e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a newly
 * created [UByteArray].
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapToUByteArray(transform: (T) -> UByte): UByteArray =
    mapTo(UByteArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * a newly created [UByteArray].
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapIndexedToUByteArray(transform: (i: Int, e: T) -> UByte): UByteArray =
    mapIndexedTo(UByteArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the given
 * [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapTo(destination: UShortArray, transform: (T) -> UShort): UShortArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * the given [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapIndexedTo(
    destination: UShortArray,
    transform: (i: Int, e: T) -> UShort
): UShortArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(i, e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a newly
 * created [UShortArray].
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapToUShortArray(transform: (T) -> UShort): UShortArray =
    mapTo(UShortArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * a newly created [UShortArray].
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapIndexedToUShortArray(transform: (i: Int, e: T) -> UShort): UShortArray =
    mapIndexedTo(UShortArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the given
 * [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapTo(destination: UIntArray, transform: (T) -> UInt): UIntArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * the given [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapIndexedTo(destination: UIntArray, transform: (i: Int, e: T) -> UInt): UIntArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(i, e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a newly
 * created [UIntArray].
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapToUIntArray(transform: (T) -> UInt): UIntArray =
    mapTo(UIntArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * a newly created [UIntArray].
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapIndexedToUIntArray(transform: (i: Int, e: T) -> UInt): UIntArray =
    mapIndexedTo(UIntArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the given
 * [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapTo(destination: ULongArray, transform: (T) -> ULong): ULongArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * the given [destination].
 *
 * The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 *
 * @throws IllegalArgumentException if [destination] does not have the same size as `this` array
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapIndexedTo(destination: ULongArray, transform: (i: Int, e: T) -> ULong): ULongArray {
    require(this.size == destination.size) { "Destination is either larger or smaller than origin array." }
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(i, e)
    }
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a newly
 * created [ULongArray].
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapToULongArray(transform: (T) -> ULong): ULongArray =
    mapTo(ULongArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the results to
 * a newly created [ULongArray].
 *
 * @param transform function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element
 */
@ExperimentalUnsignedTypes
public inline fun <T> Array<T>.mapIndexedToULongArray(transform: (i: Int, e: T) -> ULong): ULongArray =
    mapIndexedTo(ULongArray(this.size), transform)

