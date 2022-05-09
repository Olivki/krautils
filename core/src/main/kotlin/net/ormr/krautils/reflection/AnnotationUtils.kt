/*
 * Copyright 2022 Oliver Berg
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

package net.ormr.krautils.reflection

import java.lang.reflect.AnnotatedElement

/**
 * Returns this elements' annotation of type [T] if it is present, otherwise `null`.
 */
public inline fun <reified T : Annotation> AnnotatedElement.getAnnotation(): T? = getAnnotation(T::class.java)

/**
 * Returns this elements' annotation of type [T] if it is directly present, otherwise returns `null`.
 */
public inline fun <reified T : Annotation> AnnotatedElement.getDeclaredAnnotation(): T? =
    getDeclaredAnnotation(T::class.java)

/**
 * Returns `true` if an annotation of type [T] is present on this element, otherwise `false`.
 */
public inline fun <reified T : Annotation> AnnotatedElement.isAnnotationPresent(): Boolean =
    isAnnotationPresent(T::class.java)

/**
 * Returns `true` if an annotation of type [T] is present on this element, otherwise `false`.
 */
public inline fun <reified T : Annotation> AnnotatedElement.isDeclaredAnnotationPresent(): Boolean =
    getDeclaredAnnotation<T>() != null