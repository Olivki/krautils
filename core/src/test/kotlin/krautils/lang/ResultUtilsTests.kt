/*
 * Copyright 2021 Oliver Berg
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

import io.kotest.assertions.throwables.shouldNotThrowUnit
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.result.shouldBeFailureOfType
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.types.shouldBeSameInstanceAs

class ResultMapCatchingTests : FunSpec({
    context("Invoking Result.flatMap") {
        context("on success") {
            val success = Result.success("success")

            test("with itself should return the same instance") {
                success.flatMap { success } shouldBeSuccess "success"
            }

            test("with a new Result instance should return the new instance") {
                success.flatMap { Result.success("$it again") } shouldBeSuccess "success again"
            }

            test("with a throwing transformer should rethrow exception") {
                shouldThrow<DummyException> {
                    success.flatMap<String, String> { throw DummyException() }
                }
            }

            test("with failure should return failure") {
                success.flatMap { Result.failure<String>(DummyException()) }.shouldBeFailureOfType<DummyException>()
            }
        }

        context("on failure") {
            val exception = DummyException()
            val failure = Result.failure<String>(exception)

            test("with success should return the failure") {
                val flatMappedFailure = failure.flatMap { Result.success("hello") }
                flatMappedFailure.shouldBeFailureOfType<DummyException>()
                flatMappedFailure.exceptionOrNull() shouldBeSameInstanceAs exception
            }

            test("with failure should return original failure") {
                val flatMappedFailure = failure.flatMap { Result.failure<String>(DummyException()) }
                flatMappedFailure.shouldBeFailureOfType<DummyException>()
                flatMappedFailure.exceptionOrNull() shouldBeSameInstanceAs exception
            }
        }
    }
})

class ResultFlatMapCatchingTests : FunSpec({
    context("Invoking Result.flatMapCatching") {
        context("on success") {
            val success = Result.success("success")

            test("with itself should return the same instance") {
                success.flatMapCatching { success } shouldBeSuccess "success"
            }

            test("with a new Result instance should return the new instance") {
                success.flatMapCatching { Result.success("$it again") } shouldBeSuccess "success again"
            }

            test("with a throwing transformer should return thrown exception encapsulated") {
                shouldNotThrowUnit<DummyException> {
                    val exception = DummyException()
                    val result: Result<String> = Result.success("throwing").flatMapCatching { throw exception }
                    result.shouldBeFailureOfType<DummyException>()
                    result.exceptionOrNull() shouldBeSameInstanceAs exception
                }
            }

            test("with failure should return failure") {
                success.flatMapCatching { Result.failure<String>(DummyException()) }.shouldBeFailureOfType<DummyException>()
            }
        }

        context("on failure") {
            val exception = DummyException()
            val failure = Result.failure<String>(exception)

            test("with success should return the failure") {
                val flatMappedFailure = failure.flatMapCatching { Result.success("hello") }
                flatMappedFailure.shouldBeFailureOfType<DummyException>()
                flatMappedFailure.exceptionOrNull() shouldBeSameInstanceAs exception
            }

            test("with failure should return original failure") {
                val flatMappedFailure = failure.flatMapCatching { Result.failure<String>(DummyException()) }
                flatMappedFailure.shouldBeFailureOfType<DummyException>()
                flatMappedFailure.exceptionOrNull() shouldBeSameInstanceAs exception
            }
        }
    }
})

class ResultFilterTests : FunSpec({
    context("Invoking Result.filter") {
        context("on success") {
            val resultValue = DummyClass()
            val result = Result.success(resultValue)

            test("with true predicate should return success") {
                result.filter { true } shouldBeSuccess resultValue
                result.filter { it === resultValue } shouldBeSuccess resultValue
            }

            test("with false predicate should return failure") {
                result.filter { false }.shouldBeFailureOfType<FailedPredicateException>()
                result.filter { it !== resultValue }.shouldBeFailureOfType<FailedPredicateException>()
            }
        }

        context("on failure") {
            val resultValue = DummyException()
            val result = Result.failure<String>(resultValue)

            test("with true predicate should return original failure") {
                val filterResult = result.filter { true }
                filterResult.shouldBeFailureOfType<DummyException>()
                filterResult.exceptionOrNull() shouldBeSameInstanceAs resultValue
            }

            test("with false predicate should return original failure") {
                val filterResult = result.filter { false }
                filterResult.shouldBeFailureOfType<DummyException>()
                filterResult.exceptionOrNull() shouldBeSameInstanceAs resultValue
            }
        }
    }
})

class ResultFilterNotTests : FunSpec({
    context("Invoking Result.filterNot") {
        context("on success") {
            val resultValue = DummyClass()
            val result = Result.success(resultValue)

            test("with true predicate should return failure") {
                result.filterNot { true }.shouldBeFailureOfType<FailedPredicateException>()
                result.filterNot { it == resultValue }.shouldBeFailureOfType<FailedPredicateException>()
            }

            test("with false predicate should return success") {
                result.filterNot { false } shouldBeSuccess resultValue
                result.filterNot { it !== resultValue } shouldBeSuccess resultValue
            }
        }

        context("on failure") {
            val resultValue = DummyException()
            val result = Result.failure<String>(resultValue)

            test("with true predicate should return original failure") {
                val filterResult = result.filterNot { true }
                filterResult.shouldBeFailureOfType<DummyException>()
                filterResult.exceptionOrNull() shouldBeSameInstanceAs resultValue
            }

            test("with false predicate should return original failure") {
                val filterResult = result.filterNot { false }
                filterResult.shouldBeFailureOfType<DummyException>()
                filterResult.exceptionOrNull() shouldBeSameInstanceAs resultValue
            }
        }
    }
})

class ResultAnyTests : FunSpec({
    context("Invoking Result.any") {
        context("on success") {
            val resultValue = DummyClass()
            val result = Result.success(resultValue)

            test("with true predicate should return true") {
                result.any { true }.shouldBeTrue()
                result.any { it === resultValue }.shouldBeTrue()
            }

            test("with false predicate should return false") {
                result.any { false }.shouldBeFalse()
                result.any { it !== resultValue }.shouldBeFalse()
            }
        }

        context("on failure") {
            val resultValue = DummyException()
            val result = Result.failure<String>(resultValue)

            test("with true predicate should return false") {
                result.any { true }.shouldBeFalse()
            }

            test("with false predicate should return false") {
                result.any { false }.shouldBeFalse()
            }
        }
    }
})

private class DummyClass

private class DummyException : RuntimeException()