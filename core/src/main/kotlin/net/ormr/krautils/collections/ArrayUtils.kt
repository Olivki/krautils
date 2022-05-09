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

@file:JvmMultifileClass
@file:JvmName("ArrayUtils")

package net.ormr.krautils.collections

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
    for ((i, e) in this.withIndex()) {
        destination[i] = transform(e)
    }
    return destination
}

// map...Array
/**
 * Applies the given [transform] function to each element of the original array and appends the results to a
 * newly created [Array].
 */
public inline fun <T, reified R> Array<T>.mapToTypedArray(transform: (T) -> R): Array<R> =
    mapTo(createArray(this.size), transform)

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

    for ((i, e) in this.withIndex()) {
        destination[i] = transform(i, e)
    }

    return destination
}

/**
 * Applies the given [transform] function to each element and its index in the original array and appends the
 * results to a newly created [Array].
 *
 * @param [transform] function that takes the index of an element and the element itself and returns the result of the
 * transform applied to the element.
 */
public inline fun <T, reified R> Array<T>.mapIndexedToTypedArray(transform: (i: Int, e: T) -> R): Array<R> =
    mapIndexedTo(createArray(this.size), transform)

@PublishedApi
@Suppress("UNCHECKED_CAST")
internal inline fun <reified T> createArray(size: Int): Array<T> = arrayOfNulls<T>(size) as Array<T>