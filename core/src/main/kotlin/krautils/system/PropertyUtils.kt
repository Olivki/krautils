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

package krautils.system

/**
 * Thrown if no system property could be found for [key].
 */
public class MissingPropertyException(public val key: String) :
    NoSuchElementException("System property '$key' is missing!")

/**
 * Returns the system property stored under the given [key], or throws a [MissingPropertyException] if none is found.
 *
 * @throws [IllegalArgumentException] if [key] is empty
 * @throws [MissingPropertyException] if no property is found for [key]
 *
 * @see [System.getProperty]
 */
public fun getProperty(key: String): String = getPropertyOrNull(key) ?: throw MissingPropertyException(key)

/**
 * Returns the system property stored under the given [key], or [default] if none is found.
 *
 * @throws [IllegalArgumentException] if [key] is empty
 * @throws [MissingPropertyException] if no property is found for [key]
 *
 * @see [System.getProperty]
 */
public fun getPropertyOrDefault(key: String, default: String): String = System.getProperty(key, default)

/**
 * Returns the system property stored under the given [key], or `null` if none is found.
 *
 * @throws [IllegalArgumentException] if [key] is empty
 *
 * @see [System.getProperty]
 */
public fun getPropertyOrNull(key: String): String? = System.getProperty(key)

/**
 * Sets the system property stored under the given [key] to [value].
 *
 * @throws [IllegalArgumentException] if [key] is empty.
 *
 * @see [System.setProperty]
 */
public fun setProperty(key: String, value: String) {
    System.setProperty(key, value)
}