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
 * Returns the [end][ClosedRange.endInclusive] of the given [range] if `this` is larger than `range`,
 * or [start][ClosedRange.start] if `this` is smaller than `range`, otherwise returns `this` value.
 */
public infix fun <T : Comparable<T>> T.clampTo(range: ClosedRange<T>): T = when {
    this > range.endInclusive -> range.endInclusive
    this < range.start -> range.start
    else -> this
}