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

import krautils.KrautilsExperimental
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Thrown if no environment variable could be found for [name].
 */
public class MissingEnvironmentVariableException(public val name: String) :
    NoSuchElementException("Environment variable '$name' is missing!")

/**
 * Returns the environment variable stored under the given [name], or throws a [MissingEnvironmentVariableException] if
 * none is found.
 *
 * @param [name] the name of the environment variable
 *
 * @throws [MissingEnvironmentVariableException] if no environment variable is found for [name]
 *
 * @see [System.getenv]
 */
public fun getEnv(name: String): String = getEnvOrNull(name) ?: throw MissingEnvironmentVariableException(name)

/**
 * Returns the environment variable stored under the given [name], or [default] if none is found.
 *
 * @param [name] the name of the environment variable
 * @param [default] the value to return if [name] is not a known environment variable
 *
 * @see [System.getenv]
 */
public fun getEnvOrDefault(name: String, default: String): String = getEnvOrNull(name) ?: default

/**
 * Returns the environment variable stored under the given [name], or `null` if none is found.
 *
 * @param [name] the name of the environment variable
 *
 * @see [System.getenv]
 */
public fun getEnvOrNull(name: String): String? = System.getenv(name)

// these functions are extension functions on 'Delegates' just for scoping

/**
 * Returns a property delegate for a read-only property that returns the result of invoking [converter] with the
 * value of the environment variable with the given [name].
 *
 * If [default] is *not* `null` then [getEnvOrDefault] will be used to retrieve the variable, otherwise [getEnv]
 * will be used. This means that if no variable exists with `name`, and `default` is `null`, then a
 * [MissingEnvironmentVariableException] will be thrown when retrieving the variable.
 *
 * Note that this delegate *lazily* initializes the environment variable, meaning that it won't be retrieved until
 * the first `get` invocation of the property, and afterwards it will returned the cached value of the variable.
 *
 * @param [name] the name of the environment variable
 * @param [default] the value to return if [name] is not a known environment variable
 * @param [converter] the function to convert the environment variable to a value of type [T]
 */
@KrautilsExperimental
public fun <T : Any> Delegates.env(
    name: String,
    default: String? = null,
    converter: (String) -> T,
): ReadOnlyProperty<Any?, T> = EnvProperty(name, default, converter)

private val TO_STRING_CONVERTER: (String) -> String = { it }

/**
 * Returns a property delegate for a read-only property that returns the value of the environment variable with the
 * given [name].
 *
 * If [default] is *not* `null` then [getEnvOrDefault] will be used to retrieve the variable, otherwise [getEnv]
 * will be used. This means that if no variable exists with `name`, and `default` is `null`, then a
 * [MissingEnvironmentVariableException] will be thrown when retrieving the variable.
 *
 * Note that this delegate *lazily* initializes the environment variable, meaning that it won't be retrieved until
 * the first `get` invocation of the property, and afterwards it will returned the cached value of the variable.
 *
 * @param [name] the name of the environment variable
 * @param [default] the value to return if [name] is not a known environment variable
 */
@KrautilsExperimental
public fun Delegates.env(
    name: String,
    default: String? = null,
): ReadOnlyProperty<Any?, String> = env(name, default, TO_STRING_CONVERTER)

/**
 * Returns a property delegate for a read-only property that returns the result of invoking [converter] with the value
 * of the environment variable with the given [name], or `null` if no such environment variable exists.
 *
 * Note that this delegate *lazily* initializes the environment variable, meaning that it won't be retrieved until
 * the first `get` invocation of the property, and afterwards it will returned the cached value of the variable.
 *
 * @param [name] the name of the environment variable
 */
@KrautilsExperimental
public fun <T> Delegates.nullableEnv(
    name: String,
    converter: (String?) -> T?,
): ReadOnlyProperty<Any?, T?> = NullableEnvProperty(name, converter)

private val NULLABLE_TO_STRING_CONVERTER: (String?) -> String? = { it }

/**
 * Returns a property delegate for a read-only property that returns the value of the environment variable with the
 * given [name], or `null` if no such environment variable exists.
 *
 * Note that this delegate *lazily* initializes the environment variable, meaning that it won't be retrieved until
 * the first `get` invocation of the property, and afterwards it will returned the cached value of the variable.
 *
 * @param [name] the name of the environment variable
 */
@KrautilsExperimental
public fun Delegates.nullableEnv(name: String): ReadOnlyProperty<Any?, String?> =
    nullableEnv(name, NULLABLE_TO_STRING_CONVERTER)

private class EnvProperty<T : Any>(
    val name: String,
    val default: String?,
    val converter: (String) -> T,
) : ReadOnlyProperty<Any?, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (value == null) {
            value = converter(default?.let { getEnvOrDefault(name, it) } ?: getEnv(name))
        }

        return value ?: error("value should never be null here.")
    }
}

private class NullableEnvProperty<T>(
    val name: String,
    val converter: (String?) -> T?,
) : ReadOnlyProperty<Any?, T?> {
    @Suppress("ClassName")
    private object NOT_SET

    private var value: Any? = NOT_SET

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        if (value == NOT_SET) {
            value = converter(getEnvOrNull(name))
        }

        return value as? T
    }
}