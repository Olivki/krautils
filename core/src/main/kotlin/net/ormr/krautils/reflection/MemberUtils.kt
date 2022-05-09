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

package net.ormr.krautils.reflection

import java.lang.reflect.Member
import java.lang.reflect.Modifier

/**
 * Returns `true` if `this` has the `public` modifier, otherwise `false`.
 */
public val Member.isPublic: Boolean
    get() = Modifier.isPublic(modifiers)

/**
 * Returns `true` if `this` has the `private` modifier, otherwise `false`.
 */
public val Member.isPrivate: Boolean
    get() = Modifier.isPrivate(modifiers)

/**
 * Returns `true` if `this` has the `protected` modifier, otherwise `false`.
 */
public val Member.isProtected: Boolean
    get() = Modifier.isProtected(modifiers)

/**
 * Returns `true` if `this` has the `static` modifier, otherwise `false`.
 */
public val Member.isStatic: Boolean
    get() = Modifier.isStatic(modifiers)

/**
 * Returns `true` if `this` has the `final` modifier, otherwise `false`.
 */
public val Member.isFinal: Boolean
    get() = Modifier.isFinal(modifiers)

/**
 * Returns `true` if `this` does *not* have the `final` modifier, otherwise `false`.
 */
public val Member.isOpen: Boolean
    get() = !Modifier.isFinal(modifiers)

/**
 * Returns `true` if `this` has the `synchronized` modifier, otherwise `false`.
 */
public val Member.isSynchronized: Boolean
    get() = Modifier.isSynchronized(modifiers)

/**
 * Returns `true` if `this` has the `volatile` modifier, otherwise `false`.
 */
public val Member.isVolatile: Boolean
    get() = Modifier.isVolatile(modifiers)

/**
 * Returns `true` if `this` has the `transient` modifier, otherwise `false`.
 */
public val Member.isTransient: Boolean
    get() = Modifier.isTransient(modifiers)

/**
 * Returns `true` if `this` has the `native` modifier, otherwise `false`.
 */
public val Member.isNative: Boolean
    get() = Modifier.isNative(modifiers)

/**
 * Returns `true` if `this` has the `interface` modifier, otherwise `false`.
 */
public val Member.isInterface: Boolean
    get() = Modifier.isInterface(modifiers)

/**
 * Returns `true` if `this` has the `abstract` modifier, otherwise `false`.
 */
public val Member.isAbstract: Boolean
    get() = Modifier.isAbstract(modifiers)

/**
 * Returns `true` if `this` does *not* have the `abstract` modifier, otherwise `false`.
 */
public val Member.isConcrete: Boolean
    get() = !Modifier.isAbstract(modifiers)

/**
 * Returns `true` if `this` has the `strict` modifier, otherwise `false`.
 */
public val Member.isStrict: Boolean
    get() = Modifier.isStrict(modifiers)