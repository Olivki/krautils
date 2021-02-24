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

import java.util.EnumMap

/**
 * Returns an empty [EnumMap] for the given [enum][K].
 *
 * @param [K] the [Enum] type to create the map for
 */
public inline fun <reified K : Enum<K>, V> emptyEnumMap(): EnumMap<K, V> = EnumMap(K::class.java)

/**
 * Returns an empty [EnumMap] for the given [enum][K].
 *
 * @param [K] the [Enum] type to create the map for
 */
public inline fun <reified K : Enum<K>, V> enumMapOf(): EnumMap<K, V> = emptyEnumMap()

/**
 * Returns an [EnumMap] that contains the given [entries].
 *
 * @param [K] the [Enum] type to create the map for
 */
public inline fun <reified K : Enum<K>, V> enumMapOf(
    vararg entries: Pair<K, V>,
): EnumMap<K, V> = emptyEnumMap<K, V>().apply {
    putAll(entries)
}

/**
 * Returns an [EnumMap] which is populated by all the enum constants of the given [enum][K].
 *
 * The `key` is the enum constant and the value is the result of invoking the [init] function with the enum constant.
 *
 * @param [K] the [Enum] type to extract the constants from
 */
public inline fun <reified K : Enum<K>, V> EnumMap(init: (K) -> V): EnumMap<K, V> = emptyEnumMap<K, V>().apply {
    for (key in enumValues<K>()) {
        put(key, init(key))
    }
}

/**
 * Returns an [EnumMap] that contains all the entries of `this` map.
 *
 * Unlike the `EnumMap` constructor that accepts a `Map` instance, this will *not* throw an [IllegalArgumentException]
 * if the map is empty, it will instead return an [emptyEnumMap].
 */
public inline fun <reified K : Enum<K>, V> Map<K, V>.toEnumMap(): EnumMap<K, V> = when {
    isEmpty() -> emptyEnumMap()
    else -> when (this) {
        is EnumMap -> EnumMap(this)
        else -> EnumMap(this)
    }
}