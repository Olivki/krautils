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

@file:OptIn(ExperimentalContracts::class)

package krautils.lang

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Returns the result of applying the value of this instance to [transform] if this is [success][Result.isSuccess],
 * otherwise returns this instance if this is [failure][Result.isFailure].
 *
 * This function will rethrow any [Throwable] exceptions thrown by the `transform` function. See
 * [flatMapCatching] for an alternative that encapsulates any thrown exceptions.
 */
@Suppress("UNCHECKED_CAST")
public inline fun <T, R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return fold({ transform(it) }, { this as Result<R> })
}

/**
 * Returns the result of applying the value of this instance to [transform] if this is [success][Result.isSuccess],
 * otherwise returns this instance if this is [failure][Result.isFailure].
 *
 * This function catches any [Throwable] exceptions thrown by the `transform` function and encapsulates it as a failure.
 * See [flatMap][Result.flatMap] for an alternative that rethrows any thrown exceptions.
 */
@Suppress("UNCHECKED_CAST")
public inline fun <T, R> Result<T>.flatMapCatching(transform: (T) -> Result<R>): Result<R> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return fold({ runCatching { transform(it) }.flatMap { it } }, { this as Result<R> })
}

/**
 * Returns the encapsulated value if [predicate] is `true`, otherwise returns an encapsulated
 * [FailedPredicateException].
 */
public inline fun <T> Result<T>.filter(predicate: (T) -> Boolean): Result<T> {
    contract {
        callsInPlace(predicate, InvocationKind.EXACTLY_ONCE)
    }
    return flatMap { if (predicate(it)) Result.success(it) else Result.failure(FailedPredicateException("filter")) }
}

/**
 * Returns the encapsulated value if [predicate] is `false`, otherwise returns an encapsulated
 * [FailedPredicateException].
 */
public inline fun <T> Result<T>.filterNot(predicate: (T) -> Boolean): Result<T> {
    contract {
        callsInPlace(predicate, InvocationKind.EXACTLY_ONCE)
    }
    return flatMap { if (!predicate(it)) Result.success(it) else Result.failure(FailedPredicateException("filterNot")) }
}

/**
 * Returns the result of the given [predicate] function applied to the encapsulated value if this instance represents
 * [success][Result.isSuccess] or `false` if it is [failure][Result.isFailure].
 */
public inline fun <T> Result<T>.any(predicate: (T) -> Boolean): Boolean {
    contract {
        callsInPlace(predicate, InvocationKind.AT_MOST_ONCE)
    }
    return fold({ predicate(it) }, { false })
}

/**
 * The exception that's returned when a predicate on a [Result] fails.
 */
public class FailedPredicateException @PublishedApi internal constructor(override val message: String) :
    RuntimeException()