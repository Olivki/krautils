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

package krautils.collections

/**
 * Creates a new generic [Array] of the specified [size] filled with the specified [default] value.
 */
public inline fun <reified T : Any> createArray(size: Int, default: T): Array<T> = Array(size) { default }

// TODO: implement an actual proper way of doing this rather than just having it be a wrapper

/**
 * Returns a [Map] where keys are elements from the given collection and values are produced by the [valueSelector]
 * function applied to each element.
 *
 * If any two elements are equal, the last one gets added to the map.
 *
 * The returned map preserves the entry iteration order of the original collection.
 */
public inline fun <K, V> Array<K>.associateWith(valueSelector: (K) -> V): Map<K, V> =
    asIterable().associateWith(valueSelector)

/**
 * Populates and returns the [destination] mutable map with key-value pairs for each element of the given collection,
 * where key is the element itself and value is provided by the [valueSelector] function applied to that key.
 *
 * If any two elements are equal, the last one overwrites the former value in the map.
 */
public inline fun <K, V, M : MutableMap<in K, in V>> Array<K>.associateWithTo(
    destination: M,
    valueSelector: (K) -> V,
): M = asIterable().associateWithTo(destination, valueSelector)

// -- MAPPING FUNCTIONS -- \\
// mapTo
/**
 * Applies the given [transform] function to each element of the original array and appends the results to the
 * given [destination].
 */
public inline fun <T, R> Array<T>.mapTo(destination: Array<R>, transform: (T) -> R): Array<R> {
    require(this.size <= destination.size) { "this.size <= target.size" }
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the
 * given [destination].
 */
public inline fun <T> Array<T>.mapTo(destination: BooleanArray, transform: (T) -> Boolean): BooleanArray {
    require(this.size <= destination.size) { "this.size <= target.size" }
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the
 * given [destination].
 */
public inline fun <T> Array<T>.mapTo(destination: CharArray, transform: (T) -> Char): CharArray {
    require(this.size <= destination.size) { "this.size <= target.size" }
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the
 * given [destination].
 */
public inline fun <T> Array<T>.mapTo(destination: ByteArray, transform: (T) -> Byte): ByteArray {
    require(this.size <= destination.size) { "this.size <= target.size" }
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the
 * given [destination].
 */
public inline fun <T> Array<T>.mapTo(destination: ShortArray, transform: (T) -> Short): ShortArray {
    require(this.size <= destination.size) { "this.size <= target.size" }
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the
 * given [destination].
 */
public inline fun <T> Array<T>.mapTo(destination: IntArray, transform: (T) -> Int): IntArray {
    require(this.size <= destination.size) { "this.size <= target.size" }
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the
 * given [destination].
 */
public inline fun <T> Array<T>.mapTo(destination: LongArray, transform: (T) -> Long): LongArray {
    require(this.size <= destination.size) { "this.size <= target.size" }
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the
 * given [destination].
 */
public inline fun <T> Array<T>.mapTo(destination: FloatArray, transform: (T) -> Float): FloatArray {
    require(this.size <= destination.size) { "this.size <= target.size" }
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

/**
 * Applies the given [transform] function to each element of the original array and appends the results to the
 * given [destination].
 */
public inline fun <T> Array<T>.mapTo(destination: DoubleArray, transform: (T) -> Double): DoubleArray {
    require(this.size <= destination.size) { "this.size <= target.size" }
    for ((i, e) in withIndex()) destination[i] = transform(e)
    return destination
}

// map...Array
/**
 * Applies the given [transform] function to each element of the original array and appends the results to a
 * newly created [Array].
 */
public inline fun <T, reified R> Array<T>.mapToTypedArray(transform: (T) -> R): Array<R> =
    mapTo(createArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a
 * newly created [BooleanArray].
 */
public inline fun <T> Array<T>.mapToBooleanArray(transform: (T) -> Boolean): BooleanArray =
    mapTo(BooleanArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a
 * newly created [CharArray].
 */
public inline fun <T> Array<T>.mapToCharArray(transform: (T) -> Char): CharArray =
    mapTo(CharArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a
 * newly created [ByteArray].
 */
public inline fun <T> Array<T>.mapToByteArray(transform: (T) -> Byte): ByteArray =
    mapTo(ByteArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a
 * newly created [ShortArray].
 */
public inline fun <T> Array<T>.mapToShortArray(transform: (T) -> Short): ShortArray =
    mapTo(ShortArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a
 * newly created [IntArray].
 */
public inline fun <T> Array<T>.mapToIntArray(transform: (T) -> Int): IntArray = mapTo(IntArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a
 * newly created [LongArray].
 */
public inline fun <T> Array<T>.mapToLongArray(transform: (T) -> Long): LongArray =
    mapTo(LongArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a
 * newly created [FloatArray].
 */
public inline fun <T> Array<T>.mapToFloatArray(transform: (T) -> Float): FloatArray =
    mapTo(FloatArray(this.size), transform)

/**
 * Applies the given [transform] function to each element of the original array and appends the results to a
 * newly created [DoubleArray].
 */
public inline fun <T> Array<T>.mapToDoubleArray(transform: (T) -> Double): DoubleArray =
    mapTo(DoubleArray(this.size), transform)

// mapIndexedTo
/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T, R> Array<T>.mapIndexedTo(destination: Array<R>, transform: (i: Int, e: T) -> R): Array<R> {
    require(this.size <= destination.size) { "this.size <= target.size" }

    var i = 0
    for (element in this) {
        destination[i++] = transform(i, element)
    }

    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedTo(
    destination: BooleanArray,
    transform: (i: Int, e: T) -> Boolean,
): BooleanArray {
    require(this.size <= destination.size) { "this.size <= target.size" }

    var i = 0
    for (element in this) {
        destination[i++] = transform(i, element)
    }

    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedTo(destination: CharArray, transform: (i: Int, e: T) -> Char): CharArray {
    require(this.size <= destination.size) { "this.size <= target.size" }

    var i = 0
    for (element in this) {
        destination[i++] = transform(i, element)
    }

    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedTo(destination: ByteArray, transform: (i: Int, e: T) -> Byte): ByteArray {
    require(this.size <= destination.size) { "this.size <= target.size" }

    var i = 0
    for (element in this) {
        destination[i++] = transform(i, element)
    }

    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedTo(destination: ShortArray, transform: (i: Int, e: T) -> Short): ShortArray {
    require(this.size <= destination.size) { "this.size <= target.size" }

    var i = 0
    for (element in this) {
        destination[i++] = transform(i, element)
    }

    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedTo(destination: IntArray, transform: (i: Int, e: T) -> Int): IntArray {
    require(this.size <= destination.size) { "this.size <= target.size" }

    var i = 0
    for (element in this) {
        destination[i++] = transform(i, element)
    }

    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedTo(destination: LongArray, transform: (i: Int, e: T) -> Long): LongArray {
    require(this.size <= destination.size) { "this.size <= target.size" }

    var i = 0
    for (element in this) {
        destination[i++] = transform(i, element)
    }

    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedTo(destination: FloatArray, transform: (i: Int, e: T) -> Float): FloatArray {
    require(this.size <= destination.size) { "this.size <= target.size" }

    var i = 0
    for (element in this) {
        destination[i++] = transform(i, element)
    }

    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to the given [destination].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedTo(
    destination: DoubleArray,
    transform: (i: Int, e: T) -> Double,
): DoubleArray {
    require(this.size <= destination.size) { "this.size <= target.size" }

    var i = 0
    for (element in this) {
        destination[i++] = transform(i, element)
    }

    return destination
}

// mapIndexed...Array
/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to a newly created [Array].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T, reified R> Array<T>.mapIndexedToTypedArray(transform: (i: Int, e: T) -> R): Array<R> =
    mapIndexedTo(createArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to a newly created [BooleanArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedToBooleanArray(transform: (i: Int, e: T) -> Boolean): BooleanArray =
    mapIndexedTo(BooleanArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to a newly created [CharArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedToCharArray(transform: (i: Int, e: T) -> Char): CharArray =
    mapIndexedTo(CharArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to a newly created [ByteArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedToByteArray(transform: (i: Int, e: T) -> Byte): ByteArray =
    mapIndexedTo(ByteArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to a newly created [ShortArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedToShortArray(transform: (i: Int, e: T) -> Short): ShortArray =
    mapIndexedTo(ShortArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to a newly created [IntArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedToIntArray(transform: (i: Int, e: T) -> Int): IntArray =
    mapIndexedTo(IntArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to a newly created [LongArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedToLongArray(transform: (i: Int, e: T) -> Long): LongArray =
    mapIndexedTo(LongArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to a newly created [FloatArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedToFloatArray(transform: (i: Int, e: T) -> Float): FloatArray =
    mapIndexedTo(FloatArray(this.size), transform)

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to a newly created [DoubleArray].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T> Array<T>.mapIndexedToDoubleArray(transform: (i: Int, e: T) -> Double): DoubleArray =
    mapIndexedTo(DoubleArray(this.size), transform)

@PublishedApi
@Suppress("UNCHECKED_CAST")
internal inline fun <reified T> createArray(size: Int): Array<T> = arrayOfNulls<T>(size) as Array<T>