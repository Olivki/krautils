/*
 * Copyright 2022-2022 Oliver Berg
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

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.iterator.shouldHaveNext
import io.kotest.matchers.iterator.shouldNotHaveNext
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldNotBeInstanceOf
import net.ormr.krautils.collections.asUnmodifiable
import net.ormr.krautils.collections.emptyIterator
import net.ormr.krautils.collections.iteratorOf

class IteratorUtilsTests : ShouldSpec({
    context("Invoking emptyIterator()") {
        should("return an empty iterator") {
            val iterator = emptyIterator<Unit>()
            iterator.shouldNotHaveNext()
            val exception = shouldThrow<UnsupportedOperationException> { iterator.next() }
            exception shouldHaveMessage  "Can't iterate over an empty iterator"
        }
    }

    context("Invoking iteratorOf()") {
        should("return an empty iterator") {
            val iterator = iteratorOf<Unit>()
            iterator.shouldNotHaveNext()
            val exception = shouldThrow<UnsupportedOperationException> { iterator.next() }
            exception shouldHaveMessage "Can't iterate over an empty iterator"
        }
    }

    context("Invoking iteratorOf(item)") {
        should("return a singleton iterator") {
            val item = Unit
            val iterator = iteratorOf(item)
            iterator.shouldHaveNext()
            iterator.next() shouldBe item
            iterator.shouldNotHaveNext()
            // the 'NoSuchElementException' is defined by the underlying 'AbstractIterator' and may change at some
            // point, but that should be unlikely.
            shouldThrow<NoSuchElementException> { iterator.next() }
        }
    }

    context("Invoking iteratorOf(...items)") {
        should("return an iterator for the items") {
            val itemOne = 1
            val itemTwo = 2
            val itemThree = 3
            val iterator = iteratorOf(itemOne, itemTwo, itemThree)
            iterator.shouldHaveNext() // itemOne
            iterator.next() shouldBe itemOne
            iterator.shouldHaveNext() // itemTwo
            iterator.next() shouldBe itemTwo
            iterator.shouldHaveNext() // itemThree
            iterator.next() shouldBe itemThree
            iterator.shouldNotHaveNext()
        }
    }

    // TODO: 'asUnmodifiable' is not the easiest to test, as we can't actually just check if it's an instance of
    //       'MutableIterator' as that type doesn't actually exist on the JVM side, so figure out a way to properly
    //       test this, for now it's disabled. The way we create the unmodifiable view is also dependent on compiler
    //       intrinsics, so it's a bit wishy washy to test, as it depends on behavior that's not publicly defined and
    //       may be changed.
    xcontext("Invoking iterator.asUnmodifiable()") {
        should("return an unmodifiable view of the iterator") {
            val originalIterator = mutableListOf("hello").iterator()
            originalIterator.shouldBeInstanceOf<MutableIterator<*>>()

            val unmodifiableIterator = originalIterator.asUnmodifiable()
            unmodifiableIterator.shouldNotBeInstanceOf<MutableIterator<*>>()

            unmodifiableIterator.shouldHaveNext()
            unmodifiableIterator.next() shouldBe "hello"
            unmodifiableIterator.shouldNotHaveNext()
        }
    }
})