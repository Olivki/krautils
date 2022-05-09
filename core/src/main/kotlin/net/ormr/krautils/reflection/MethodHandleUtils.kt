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

package net.ormr.krautils.reflection

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodHandles.Lookup
import java.lang.invoke.MethodHandles.lookup
import java.lang.invoke.MethodType
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Returns a [MethodHandle] that always returns the given [value].
 *
 * @see [MethodHandles.constant]
 */
public inline fun <reified T : Any> constantMethodHandle(
    value: T,
    type: Class<T> = T::class.java
): MethodHandle = MethodHandles.constant(type, value)

/**
 * Returns a [MethodHandle] pointing to `this` method.
 *
 * @see [MethodHandles.Lookup.unreflect]
 */
public inline fun Method.toMethodHandle(lookup: Lookup = lookup()): MethodHandle = lookup.unreflect(this)

/**
 * Returns a [MethodHandle] pointing to `this` method.
 *
 * @see [MethodHandles.Lookup.unreflectSpecial]
 */
public inline fun Method.toSpecialMethodHandle(
    caller: Class<*> = declaringClass,
    lookup: Lookup = lookup(),
): MethodHandle = lookup.unreflectSpecial(this, caller)

/**
 * Returns a [MethodHandle] pointing to `this` constructor.
 *
 * @see [MethodHandles.Lookup.unreflectConstructor]
 */
public inline fun Constructor<*>.toMethodHandle(lookup: Lookup = lookup()): MethodHandle =
    lookup.unreflectConstructor(this)

/**
 * Returns a [MethodHandle] for a static method.
 *
 * @see [MethodHandles.Lookup.findStatic]
 */
public inline fun Class<*>.findStaticMethod(
    name: String,
    type: MethodType,
    lookup: Lookup = lookup(),
): MethodHandle = lookup.findStatic(this, name, type)

/**
 * Returns a [MethodHandle] for a virtual method.
 *
 * @see [MethodHandles.Lookup.findVirtual]
 */
public inline fun Class<*>.findVirtualMethod(
    name: String,
    type: MethodType,
    lookup: Lookup = lookup(),
): MethodHandle = lookup.findVirtual(this, name, type)

/**
 * Returns an early-bound [MethodHandle] for a virtual method.
 *
 * @see [MethodHandles.Lookup.findSpecial]
 */
public inline fun Class<*>.findSpecialMethod(
    name: String,
    type: MethodType,
    caller: Class<*> = this,
    lookup: Lookup = lookup(),
): MethodHandle = lookup.findSpecial(this, name, type, caller)

/**
 * Returns a [MethodHandle] for a constructor.
 *
 * @see [MethodHandles.Lookup.findConstructor]
 */
public inline fun Class<*>.findConstructor(
    type: MethodType,
    lookup: Lookup = lookup(),
): MethodHandle = lookup.findConstructor(this, type)

/**
 * Returns a [MethodHandle] giving read access to a non-static field.
 *
 * @see [MethodHandles.Lookup.findGetter]
 */
public inline fun Class<*>.createGetterMethod(
    name: String,
    type: Class<*>,
    lookup: Lookup = lookup(),
): MethodHandle = lookup.findGetter(this, name, type)

/**
 * Returns a [MethodHandle] giving read access to a static field.
 *
 * @see [MethodHandles.Lookup.findStaticGetter]
 */
public inline fun Class<*>.createStaticGetterMethod(
    name: String,
    type: Class<*>,
    lookup: Lookup = lookup(),
): MethodHandle = lookup.findStaticGetter(this, name, type)

/**
 * Returns a [MethodHandle] giving write access to a non-static field.
 *
 * @see [MethodHandles.Lookup.findSetter]
 */
public inline fun Class<*>.createSetterMethod(
    name: String,
    type: Class<*>,
    lookup: Lookup = lookup(),
): MethodHandle = lookup.findSetter(this, name, type)

/**
 * Returns a [MethodHandle] giving read access to a static field.
 *
 * @see [MethodHandles.Lookup.findStaticSetter]
 */
public inline fun Class<*>.createStaticSetterMethod(
    name: String,
    type: Class<*>,
    lookup: Lookup = lookup(),
): MethodHandle = lookup.findStaticSetter(this, name, type)

/**
 * Returns a [MethodHandle] giving read access to `this` field.
 *
 * @see [MethodHandles.Lookup.unreflectGetter]
 */
public inline fun Field.toGetterMethodHandle(lookup: Lookup = lookup()): MethodHandle = lookup.unreflectGetter(this)

/**
 * Returns a [MethodHandle] giving write access to `this` field
 *
 * @see [MethodHandles.Lookup.unreflectSetter]
 */
public inline fun Field.toSetterMethodHandle(lookup: Lookup = lookup()): MethodHandle = lookup.unreflectSetter(this)

/**
 * @see [MethodHandles.guardWithTest]
 */
public fun MethodHandle.guardWithTest(test: MethodHandle, fallback: MethodHandle): MethodHandle =
    MethodHandles.guardWithTest(test, this, fallback)

/**
 * @see [MethodHandles.catchException]
 */
public inline fun <reified T : Exception> MethodHandle.catchException(
    type: Class<T> = T::class.java,
    handler: MethodHandle,
): MethodHandle = MethodHandles.catchException(this, type, handler)

/**
 * @see [MethodHandles.explicitCastArguments]
 */
public fun MethodHandle.explicitCastArguments(newType: MethodType): MethodHandle =
    MethodHandles.explicitCastArguments(this, newType)

/**
 * @see [MethodHandles.permuteArguments]
 */
public fun MethodHandle.permuteArguments(newType: MethodType, vararg reorder: Int): MethodHandle =
    MethodHandles.permuteArguments(this, newType, *reorder)

/**
 * @see [MethodHandles.insertArguments]
 */
public fun MethodHandle.insertArguments(pos: Int, vararg values: Any?): MethodHandle =
    MethodHandles.insertArguments(this, pos, *values)

/**
 * @see [MethodHandles.dropArguments]
 */
public fun MethodHandle.dropArguments(pos: Int, valueTypes: List<Class<*>>): MethodHandle =
    MethodHandles.dropArguments(this, pos, valueTypes)

/**
 * @see [MethodHandles.dropArguments]
 */
public fun MethodHandle.dropArguments(pos: Int, vararg valueTypes: Class<*>): MethodHandle =
    MethodHandles.dropArguments(this, pos, valueTypes.asList())

/**
 * @see [MethodHandles.filterArguments]
 */
public fun MethodHandle.filterArguments(pos: Int, vararg filters: MethodHandle): MethodHandle =
    MethodHandles.filterArguments(this, pos, *filters)

/**
 * @see [MethodHandles.collectArguments]
 */
public fun MethodHandle.collectArguments(pos: Int, filter: MethodHandle): MethodHandle =
    MethodHandles.collectArguments(this, pos, filter)

/**
 * @see [MethodHandles.filterReturnValue]
 */
public fun MethodHandle.filterReturnValue(filter: MethodHandle): MethodHandle =
    MethodHandles.filterReturnValue(this, filter)

/**
 * @see [MethodHandles.foldArguments]
 */
public fun MethodHandle.foldArguments(combiner: MethodHandle): MethodHandle =
    MethodHandles.foldArguments(this, combiner)