/*
 * Copyright 2022 Oliver Berg
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

@file:Suppress("NOTHING_TO_INLINE")

package net.ormr.krautils.jdk17.reflection

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodHandles.Lookup
import java.lang.invoke.MethodHandles.lookup
import java.lang.invoke.VarHandle
import java.lang.reflect.Field

/**
 * Returns a [MethodHandle] pointing to `this` field.
 *
 * @see [MethodHandles.Lookup.unreflectVarHandle]
 */
public inline fun Field.toVarHandle(lookup: Lookup = lookup()): VarHandle = lookup.unreflectVarHandle(this)

/**
 * Returns a [VarHandle] giving access to a non-static field with the given [name] and [type] in `this` class.
 *
 * @see [MethodHandles.Lookup.findVarHandle]
 */
public inline fun <reified T : Any> Class<*>.findVarHandle(
    name: String,
    type: Class<T> = T::class.java,
    lookup: Lookup = lookup(),
): VarHandle = lookup.findVarHandle(this, name, type)


/**
 * Returns a [VarHandle] giving access to a static field with the given [name] and [type] in `this` class.
 *
 * @see [MethodHandles.Lookup.findStaticVarHandle]
 */
public inline fun <reified T : Any> Class<*>.findStaticVarHandle(
    name: String,
    type: Class<T> = T::class.java,
    lookup: Lookup = lookup(),
): VarHandle = lookup.findStaticVarHandle(this, name, type)