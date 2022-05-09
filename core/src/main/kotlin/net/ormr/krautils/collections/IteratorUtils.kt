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

package net.ormr.krautils.collections

private object EmptyIterator : Iterator<Nothing> {
    override fun hasNext(): Boolean = false

    override fun next(): Nothing = throw UnsupportedOperationException("Can't iterate over an empty iterator")
}

private class SingletonIterator<T>(item: T) : AbstractIterator<T>() {
    private var item: T? = item

    override fun computeNext() {
        if (item != null) {
            setNext(item!!)
            item = null
        } else {
            done()
        }
    }
}

private class UnmodifiableIterator<T>(val delegate: Iterator<T>) : Iterator<T> by delegate

/**
 * Returns an iterator that iterates over no elements.
 */
public fun <T> emptyIterator(): Iterator<T> = EmptyIterator

/**
 * Returns an iterator that iterates over no elements.
 */
public fun <T> iteratorOf(): Iterator<T> = emptyIterator()

/**
 * Returns an iterator that only iterates over the given [item].
 */
public fun <T> iteratorOf(item: T): Iterator<T> = SingletonIterator(item)

/**
 * Returns an iterator that iterates over the given [items].
 */
public fun <T> iteratorOf(vararg items: T): Iterator<T> = items.iterator()

/**
 * Returns an unmodifiable view of `this` iterator.
 */
public fun <T> Iterator<T>.asUnmodifiable(): Iterator<T> = UnmodifiableIterator(this)