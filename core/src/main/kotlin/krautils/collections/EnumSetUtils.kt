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

@file:Suppress("FunctionName")

package krautils.collections

import java.util.EnumSet

/**
 * Returns an empty [EnumSet] for the given [enum][E].
 *
 * @param [E] the [Enum] type to create the set for
 *
 * @see [EnumSet.noneOf]
 */
public inline fun <reified E : Enum<E>> emptyEnumSet(): EnumSet<E> = EnumSet.noneOf(E::class.java)

/**
 * Returns an empty [EnumSet] for the given [enum][E].
 *
 * @param [E] the [Enum] type to create the set for
 *
 * @see [EnumSet.noneOf]
 */
public inline fun <reified E : Enum<E>> enumSetOf(): EnumSet<E> = emptyEnumSet()

/**
 * Returns an [EnumSet] filled with all the enum constants of the given [enum][E].
 *
 * @param [E] the [Enum] type to extract the constants from
 */
// we use our own implementation here instead of 'EnumSet.allOf' to avoid any unneeded reflection, as we can just
// retrieve the enumValues via Kotlin intrinsics.
public inline fun <reified E : Enum<E>> EnumSet(): EnumSet<E> = emptyEnumSet<E>().apply {
    addAll(enumValues())
}

/**
 * Returns an [EnumSet] for the given [enum][E] that contains the given element.
 *
 * @param [E] the [Enum] type to create the set for
 */
public fun <E : Enum<E>> enumSetOf(e: E): EnumSet<E> = EnumSet.of(e)

/**
 * Returns an [EnumSet] for the given [enum][E] that contains the given elements.
 *
 * @param [E] the [Enum] type to create the set for
 */
public fun <E : Enum<E>> enumSetOf(e1: E, e2: E): EnumSet<E> = EnumSet.of(e1, e2)

/**
 * Returns an [EnumSet] for the given [enum][E] that contains the given elements.
 *
 * @param [E] the [Enum] type to create the set for
 */
public fun <E : Enum<E>> enumSetOf(e1: E, e2: E, e3: E): EnumSet<E> = EnumSet.of(e1, e2, e3)

/**
 * Returns an [EnumSet] for the given [enum][E] that contains the given elements.
 *
 * @param [E] the [Enum] type to create the set for
 */
public fun <E : Enum<E>> enumSetOf(e1: E, e2: E, e3: E, e4: E): EnumSet<E> = EnumSet.of(e1, e2, e3, e4)

/**
 * Returns an [EnumSet] for the given [enum][E] that contains the given elements.
 *
 * @param [E] the [Enum] type to create the set for
 */
public fun <E : Enum<E>> enumSetOf(e1: E, e2: E, e3: E, e4: E, e5: E): EnumSet<E> = EnumSet.of(e1, e2, e3, e4, e5)

/**
 * Returns an [EnumSet] for the given [enum][E] that contains the [initial] element, and [rest].
 *
 * @param [E] the [Enum] type to create the set for
 */
public fun <E : Enum<E>> enumSetOf(initial: E, vararg rest: E): EnumSet<E> = when (rest.size) {
    0 -> enumSetOf(initial)
    1 -> enumSetOf(initial, rest[0])
    2 -> enumSetOf(initial, rest[0], rest[1])
    3 -> enumSetOf(initial, rest[0], rest[1], rest[2])
    4 -> enumSetOf(initial, rest[0], rest[1], rest[2], rest[3])
    else -> EnumSet.of(initial, *rest)
}

/**
 * Returns an [EnumSet] containing the entries of `this` collection.
 *
 * @see [EnumSet.copyOf]
 */
public fun <E : Enum<E>> EnumSet<E>.toEnumSet(): EnumSet<E> = EnumSet.copyOf(this)

/**
 * Returns an [EnumSet] containing the entries of `this` collection.
 *
 * Unlike the [EnumSet.copyOf] function, this will not throw an [IllegalArgumentException] if the collection is empty,
 * and it will instead return an [emptyEnumSet] instead.
 */
public inline fun <reified E : Enum<E>> Collection<E>.toEnumSet(): EnumSet<E> = when (this) {
    is EnumSet<E> -> toEnumSet()
    else -> when (size) {
        0 -> emptyEnumSet()
        else -> emptyEnumSet<E>().apply {
            addAll(this@toEnumSet)
        }
    }
}

/**
 * Returns an [EnumSet] containing the entries of `this` iterable.
 */
public inline fun <reified E : Enum<E>> Iterable<E>.toEnumSet(): EnumSet<E> = when {
    none() -> emptyEnumSet()
    else -> when (this) {
        is EnumSet<E> -> toEnumSet()
        is Collection<E> -> toEnumSet()
        else -> emptyEnumSet<E>().apply {
            addAll(this@toEnumSet)
        }
    }
}

/**
 * Returns an [EnumSet] containing the entries of `this` sequence.
 */
public inline fun <reified E : Enum<E>> Sequence<E>.toEnumSet(): EnumSet<E> = when {
    none() -> emptyEnumSet()
    else -> emptyEnumSet<E>().apply {
        addAll(this@toEnumSet)
    }
}

/**
 * Returns an [EnumSet] containing the entries of `this` array.
 */
public inline fun <reified E : Enum<E>> Array<E>.toEnumSet(): EnumSet<E> = when {
    none() -> emptyEnumSet()
    else -> emptyEnumSet<E>().apply {
        addAll(this@toEnumSet)
    }
}

/**
 * Returns an [EnumSet] that contains all of the elements in `this` range.
 *
 * @see [EnumSet.range]
 */
public fun <E : Enum<E>> ClosedRange<E>.toEnumSet(): EnumSet<E> = EnumSet.range(start, endInclusive)