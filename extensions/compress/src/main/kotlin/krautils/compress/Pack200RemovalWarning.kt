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

package krautils.compress

/**
 * The Pack200 compression format was deprecated for removal in Java 11, and was removed in Java 14.
 *
 * Make sure you're using a Java version lower than 14 if you're going to use this compression.
 *
 * See [JEP-367](https://openjdk.java.net/jeps/367) for more information.
 */
@RequiresOptIn
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
public annotation class Pack200RemovalWarning
