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

package net.ormr.krautils.lang

import java.math.BigInteger
import kotlin.experimental.and

/**
 * Returns whether or not this [Byte] number is odd.
 */
public fun Byte.isOdd(): Boolean = this and 1 != 0.toByte()

/**
 * Returns whether or not this [Byte] number is even.
 */
public fun Byte.isEven(): Boolean = this and 1 == 0.toByte()

// -- SHORT -- \\
/**
 * Returns whether or not this [Short] number is odd.
 */
public fun Short.isOdd(): Boolean = this and 1 != 0.toShort()

/**
 * Returns whether or not this [Short] number is even.
 */
public fun Short.isEven(): Boolean = this and 1 == 0.toShort()

// -- INT -- \\
/**
 * Returns whether or not this [Int] number is odd.
 */
public fun Int.isOdd(): Boolean = this and 1 != 0

/**
 * Returns whether or not this [Int] number is even.
 */
public fun Int.isEven(): Boolean = this and 1 == 0

// -- LONG -- \\
/**
 * Returns whether or not this [Long] number is odd.
 */
public fun Long.isOdd(): Boolean = this and 1 != 0L

/**
 * Returns whether or not this [Long] number is even.
 */
public fun Long.isEven(): Boolean = this and 1 == 0L

/**
 * Returns the value of this [Byte] number as a [BigInteger].
 */
public fun Byte.toBigInteger(): BigInteger = BigInteger.valueOf(this.toLong())

/**
 * Returns the value of this [Short] number as a [BigInteger].
 */
public fun Short.toBigInteger(): BigInteger = BigInteger.valueOf(this.toLong())

// Plus (+)
/**
 * Adds the value of the given [byte] to this [BigInteger], and returns the result.
 */
public operator fun BigInteger.plus(byte: Byte): BigInteger = this.add(byte.toBigInteger())

/**
 * Adds the value of the given [short] to this [BigInteger], and returns the result.
 */
public operator fun BigInteger.plus(short: Short): BigInteger = this.add(short.toBigInteger())

/**
 * Adds the value of the given [int] to this [BigInteger], and returns the result.
 */
public operator fun BigInteger.plus(int: Int): BigInteger = this.add(int.toBigInteger())

/**
 * Adds the value of the given [long] to this [BigInteger], and returns the result.
 */
public operator fun BigInteger.plus(long: Long): BigInteger = this.add(long.toBigInteger())

// Minus (-)
/**
 * Subtracts the value of the given [byte] from this [BigInteger], and returns the result.
 */
public operator fun BigInteger.minus(byte: Byte): BigInteger = this.min(byte.toBigInteger())

/**
 * Subtracts the value of the given [short] from this [BigInteger], and returns the result.
 */
public operator fun BigInteger.minus(short: Short): BigInteger = this.min(short.toBigInteger())

/**
 * Subtracts the value of the given [int] from this [BigInteger], and returns the result.
 */
public operator fun BigInteger.minus(int: Int): BigInteger = this.min(int.toBigInteger())

/**
 * Subtracts the value of the given [long] from this [BigInteger], and returns the result.
 */
public operator fun BigInteger.minus(long: Long): BigInteger = this.min(long.toBigInteger())

// Times/Multiply (*)
/**
 * Multiplies this [BigInteger] with the value of the given [byte], and returns the result.
 */
public operator fun BigInteger.times(byte: Byte): BigInteger = this.multiply(byte.toBigInteger())

/**
 * Multiplies this [BigInteger] with the value of the given [short], and returns the result.
 */
public operator fun BigInteger.times(short: Short): BigInteger = this.multiply(short.toBigInteger())

/**
 * Multiplies this [BigInteger] with the value of the given [int], and returns the result.
 */
public operator fun BigInteger.times(int: Int): BigInteger = this.multiply(int.toBigInteger())

/**
 * Multiplies this [BigInteger] with the value of the given [long], and returns the result.
 */
public operator fun BigInteger.times(long: Long): BigInteger = this.multiply(long.toBigInteger())

// Divide (/)
/**
 * Divides this [BigInteger] with the value of the given [byte], and returns the result.
 */
public operator fun BigInteger.div(byte: Byte): BigInteger = this.divide(byte.toBigInteger())

/**
 * Divides this [BigInteger] with the value of the given [short], and returns the result.
 */
public operator fun BigInteger.div(short: Short): BigInteger = this.divide(short.toBigInteger())

/**
 * Divides this [BigInteger] with the value of the given [int], and returns the result.
 */
public operator fun BigInteger.div(int: Int): BigInteger = this.divide(int.toBigInteger())

/**
 * Divides this [BigInteger] with the value of the given [long], and returns the result.
 */
public operator fun BigInteger.div(long: Long): BigInteger = this.divide(long.toBigInteger())

// Rem (%)
/**
 * Gets the remainder of dividing this [BigInteger] with the value of the given [byte], and returns the result.
 */
public operator fun BigInteger.rem(byte: Byte): BigInteger = this.remainder(byte.toBigInteger())

/**
 * Gets the remainder of dividing this [BigInteger] with the value of the given [short], and returns the result.
 */
public operator fun BigInteger.rem(short: Short): BigInteger = this.remainder(short.toBigInteger())

/**
 * Gets the remainder of dividing this [BigInteger] with the value of the given [int], and returns the result.
 */
public operator fun BigInteger.rem(int: Int): BigInteger = this.remainder(int.toBigInteger())

/**
 * Gets the remainder of dividing this [BigInteger] with the value of the given [long], and returns the result.
 */
public operator fun BigInteger.rem(long: Long): BigInteger = this.remainder(long.toBigInteger())