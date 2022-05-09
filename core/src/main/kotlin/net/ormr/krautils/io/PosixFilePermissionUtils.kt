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

package net.ormr.krautils.io

import java.nio.file.attribute.FileAttribute
import java.nio.file.attribute.PosixFilePermission
import java.nio.file.attribute.PosixFilePermissions

/**
 * Returns a [FileAttribute] containing the set of permissions.
 *
 * @see [PosixFilePermissions.asFileAttribute]
 */
public fun Set<PosixFilePermission>.asFileAttribute(): FileAttribute<Set<PosixFilePermission>> =
    PosixFilePermissions.asFileAttribute(this)

/**
 * Returns a [FileAttribute] containing the singular permission.
 *
 * @see [PosixFilePermissions.asFileAttribute]
 */
public fun PosixFilePermission.asFileAttribute(): FileAttribute<Set<PosixFilePermission>> =
    PosixFilePermissions.asFileAttribute(setOf(this))

/**
 * Returns the [String] representation of the set of permissions.
 *
 * The returned string can be parsed by the [PosixFilePermissions.fromString] function.
 *
 * If the set contains `null` or elements that are not of type [PosixFilePermission] then these elements are ignored.
 *
 * @see [PosixFilePermissions.toString]
 */
public fun Set<PosixFilePermission>.encodeToString(): String = PosixFilePermissions.toString(this)