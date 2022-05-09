/*
 * Copyright 2021-2022 Oliver Berg
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

import net.ormr.krautils.KrautilsExperimental
import java.nio.file.Path
import kotlin.io.path.toPath

/**
 * Returns a path to the resource with the given [name], or `null` if none is found.
 *
 * @see [ClassLoader.getResource]
 */
@KrautilsExperimental
public fun Class<*>.getResourceAsPath(name: String): Path? = this.getResource(name)?.toURI()?.toPath()

/**
 * Returns a path to the resource with the given [name], or `null` if none is found.
 *
 * @see [ClassLoader.getResource]
 */
@KrautilsExperimental
public fun ClassLoader.getResourceAsPath(name: String): Path? = this.getResource(name)?.toURI()?.toPath()