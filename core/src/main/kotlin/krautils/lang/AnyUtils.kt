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

package krautils.lang

/**
 * Returns the same hash code for the given object as would be returned by the default [Any.hashCode] function,
 * regardless of whether or not the class of [value] overrides `hashCode`, or `0` if `value` is `null`.
 *
 * @see System.identityHashCode
 */
public fun identityHashOf(value: Any?): Int = value?.let(System::identityHashCode) ?: 0

/**
 * Returns the hash of [value], or `0` if `value` is `null`.
 *
 * If `value` is an `Array` then this returns [Array.contentDeepHashCode], if it's a `Iterable` or a `Sequence` then it
 * will calculate the hashcode from all the hashcodes of the elements contained within it and return that.
 */
public fun hashOf(value: Any?): Int = when (value) {
    null -> 0
    is Array<*> -> value.contentDeepHashCode()
    is Iterable<*> -> value.fold(1) { hash, element -> 31 * hash + hashOf(element) }
    is Sequence<*> -> value.fold(1) { hash, element -> 31 * hash + hashOf(element) }
    else -> value.hashCode()
}

/**
 * Attempts to always return a human-readable `String` representation of [value].
 */
public fun stringOf(value: Any?): String = when (value) {
    null -> "null"
    is Array<*> -> value.contentDeepToString()
    is Iterable<*> -> "[${value.joinToString(transform = ::stringOf)}]"
    is Sequence<*> -> "[${value.joinToString(transform = ::stringOf)}]"
    is Map<*, *> -> "{${value.entries.joinToString { (key, value) -> "${stringOf(key)}: ${stringOf(value)}" }}}"
    else -> value.toString()
}