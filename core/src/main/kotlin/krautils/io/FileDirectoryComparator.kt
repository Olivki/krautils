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

package krautils.io

import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile

// TODO: fix this shitty documentation
/**
 * Compares whether the [Path] instances are a regular file, or a directory.
 */
@ExperimentalPathApi
public object FileDirectoryComparator : Comparator<Path> {
    override fun compare(o1: Path, o2: Path): Int = when {
        o1.isDirectory() && o2.isRegularFile() -> -1
        o1.isDirectory() && o2.isDirectory() -> 0
        o1.isRegularFile() && o2.isRegularFile() -> 0
        else -> 1
    }
}