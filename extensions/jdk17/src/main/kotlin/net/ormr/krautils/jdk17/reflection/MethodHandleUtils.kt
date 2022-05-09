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

package net.ormr.krautils.jdk17.reflection

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles

/**
 * @see [MethodHandles.tryFinally]
 */
public fun MethodHandle.tryFinally(cleanup: MethodHandle): MethodHandle =
    MethodHandles.tryFinally(this, cleanup)

/**
 * @see [MethodHandles.dropArgumentsToMatch]
 */
public fun MethodHandle.dropArgumentsToMatch(skip: Int, newTypes: List<Class<*>>, pos: Int): MethodHandle =
    MethodHandles.dropArgumentsToMatch(this, skip, newTypes, pos)

/**
 * @see [MethodHandles.dropReturn]
 */
public fun MethodHandle.dropReturn(): MethodHandle = MethodHandles.dropReturn(this)

/**
 * @see [MethodHandles.foldArguments]
 */
public fun MethodHandle.foldArguments(combiner: MethodHandle, pos: Int): MethodHandle =
    MethodHandles.foldArguments(this, pos, combiner)