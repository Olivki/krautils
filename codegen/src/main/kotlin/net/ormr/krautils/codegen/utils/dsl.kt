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

package net.ormr.krautils.codegen.utils

import com.squareup.kotlinpoet.*
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.util.Types
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.javaType

// originally taken from https://github.com/friedrich-goetze/kotlinpoet/blob/e0a01fe9c2f8a32b83ae9a74c7a1557815bb8fc3/src/main/java/com/squareup/kotlinpoet/Shortcuts.kt
// modified to make use of kotlin features to reduce some unneeded code typing

// -- UTILITY FUNCTIONS -- \\
// file spec
inline fun <reified A : Annotation> FileSpec.Builder.annotation() = addAnnotation(A::class)

// fun spec
inline fun <reified T> FunSpec.Builder.returns() = returns(T::class)

inline fun <reified T> FunSpec.Builder.returnsNullable() = returns(T::class.asTypeName().copy(nullable = true))

inline fun <reified A : Annotation> FunSpec.Builder.annotation() = addAnnotation(A::class)

inline fun <reified T> FunSpec.Builder.addParameter(name: String, vararg modifiers: KModifier) =
    addParameter(name, T::class, *modifiers)

// type spec
inline fun <reified A : Annotation> TypeSpec.Builder.annotation() = addAnnotation(A::class)

inline fun <reified T> TypeSpec.Builder.implements() = addSuperinterface(T::class)

fun TypeSpec.Builder.implements(superinterface: TypeName, delegate: CodeBlock = CodeBlock.builder().build()) =
    addSuperinterface(superinterface, delegate)

fun TypeSpec.Builder.implements(name: String) = addSuperinterface(ClassName.bestGuess(name))

fun TypeSpec.Builder.implements(name: ClassName) = addSuperinterface(name)

inline fun <reified T> TypeSpec.Builder.addProperty(name: String, vararg modifiers: KModifier) =
    addProperty(name, T::class, *modifiers)

inline fun <reified T> TypeSpec.Builder.addMutableProperty(name: String, vararg modifiers: KModifier) =
    addProperty(PropertySpec.builder(name, T::class, *modifiers).mutable(true).build())

// -- DSL FUNCTIONS -- \\
@DslMarker annotation class UtilScope

/*
 * Shortcuts for FileSpec
 */
inline fun file(packageName: String, fileName: String, block: FileSpec.Builder.() -> Unit) =
    FileSpec.builder(packageName, fileName).apply(block).build()

/*
 * Shortcuts for adding TypeSpec to FileSpec
 */
inline fun FileSpec.Builder.`class`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.classBuilder(name).apply(block).build())

inline fun FileSpec.Builder.`class`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.classBuilder(className).apply(block).build())

inline fun FileSpec.Builder.expectClass(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.expectClassBuilder(name).apply(block).build())

inline fun FileSpec.Builder.expectClass(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.expectClassBuilder(className).apply(block).build())

inline fun FileSpec.Builder.`object`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder =
    addType(TypeSpec.objectBuilder(name).apply(block).build())

inline fun FileSpec.Builder.`object`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.objectBuilder(className).apply(block).build())

inline fun FileSpec.Builder.`interface`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.interfaceBuilder(name).apply(block).build())

inline fun FileSpec.Builder.`interface`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.interfaceBuilder(className).apply(block).build())

inline fun FileSpec.Builder.`enum`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder =
    addType(TypeSpec.enumBuilder(name).apply(block).build())

inline fun FileSpec.Builder.`enum`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.enumBuilder(className).apply(block).build())

inline fun FileSpec.Builder.annotation(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.annotationBuilder(name).apply(block).build())

inline fun FileSpec.Builder.annotation(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addType(TypeSpec.annotationBuilder(className).apply(block).build())

inline fun <reified A : Annotation> FileSpec.Builder.annotation(
    block: AnnotationSpec.Builder.() -> Unit = {}
): FileSpec.Builder = addAnnotation(AnnotationSpec.builder(A::class).apply(block).build())

/*
 * Shortcuts for adding TypeSpec to TypeSpec
 */
inline fun TypeSpec.Builder.`class`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder =
    addType(TypeSpec.classBuilder(name).apply(block).build())

inline fun TypeSpec.Builder.`class`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.classBuilder(className).apply(block).build())

inline fun TypeSpec.Builder.expectClass(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.expectClassBuilder(name).apply(block).build())

inline fun TypeSpec.Builder.expectClass(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.expectClassBuilder(className).apply(block).build())

inline fun TypeSpec.Builder.`object`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder =
    addType(TypeSpec.objectBuilder(name).apply(block).build())

inline fun TypeSpec.Builder.`object`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.objectBuilder(className).apply(block).build())

inline fun TypeSpec.Builder.`interface`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.interfaceBuilder(name).apply(block).build())

inline fun TypeSpec.Builder.`interface`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.interfaceBuilder(className).apply(block).build())

inline fun TypeSpec.Builder.`enum`(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.enumBuilder(name).apply(block).build())

inline fun TypeSpec.Builder.`enum`(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.enumBuilder(className).apply(block).build())

inline fun TypeSpec.Builder.annotation(
    name: String,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.annotationBuilder(name).apply(block).build())

inline fun TypeSpec.Builder.annotation(
    className: ClassName,
    block: TypeSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addType(TypeSpec.annotationBuilder(className).apply(block).build())

inline fun <reified A : Annotation> TypeSpec.Builder.annotation(
    block: AnnotationSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addAnnotation(AnnotationSpec.builder(A::class).apply(block).build())

/*
 * Shortcuts for adding PropertySpec to FileSpec
 */
inline fun FileSpec.Builder.value(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): FileSpec.Builder = addProperty(PropertySpec.builder(name, type, *modifiers).apply(block).build())

@OptIn(ExperimentalStdlibApi::class)
inline fun FileSpec.Builder.value(
    name: String,
    type: KType,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): FileSpec.Builder = addProperty(PropertySpec.builder(name, type.javaType, *modifiers).apply(block).build())

inline fun <reified T> FileSpec.Builder.value(
    name: String,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): FileSpec.Builder = addProperty(PropertySpec.builder(name, T::class, *modifiers).apply(block).build())

/*
 * Shortcuts for adding PropertySpec to TypeSpec
 */
inline fun TypeSpec.Builder.value(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): TypeSpec.Builder = addProperty(PropertySpec.builder(name, type, *modifiers).apply(block).build())

@OptIn(ExperimentalStdlibApi::class)
inline fun TypeSpec.Builder.value(
    name: String,
    type: KType,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): TypeSpec.Builder = addProperty(PropertySpec.builder(name, type.javaType, *modifiers).apply(block).build())

inline fun <reified T> TypeSpec.Builder.value(
    name: String,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): TypeSpec.Builder = addProperty(PropertySpec.builder(name, T::class, *modifiers).apply(block).build())

inline fun TypeSpec.Builder.variable(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): TypeSpec.Builder = addProperty(PropertySpec.builder(name, type, *modifiers).mutable(true).apply(block).build())

@OptIn(ExperimentalStdlibApi::class)
inline fun TypeSpec.Builder.variable(
    name: String,
    type: KType,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): TypeSpec.Builder =
    addProperty(PropertySpec.builder(name, type.javaType, *modifiers).mutable(true).apply(block).build())

inline fun <reified T> TypeSpec.Builder.variable(
    name: String,
    vararg modifiers: KModifier,
    block: (PropertySpec.Builder.() -> Unit) = {}
): TypeSpec.Builder = addProperty(PropertySpec.builder(name, T::class, *modifiers).mutable(true).apply(block).build())

/*
 * Shortcuts for adding FunctionSpec to FileSpec
 */
inline fun FileSpec.Builder.func(name: String, block: FunSpec.Builder.() -> Unit = {}): FileSpec.Builder =
    addFunction(FunSpec.builder(name).apply(block).build())

/*
 * Shortcuts for adding FunctionSpec to TypeSpec
 */
inline fun TypeSpec.Builder.func(name: String, block: FunSpec.Builder.() -> Unit = {}): TypeSpec.Builder =
    addFunction(FunSpec.builder(name).apply(block).build())

@UtilScope
inline fun TypeSpec.Builder.primaryConstructor(block: FunSpec.Builder.() -> Unit = {}): TypeSpec.Builder =
    primaryConstructor(FunSpec.constructorBuilder().apply(block).build())

@UtilScope
inline fun TypeSpec.Builder.nonPrimaryConstructor(block: FunSpec.Builder.() -> Unit = {}): TypeSpec.Builder =
    addFunction(FunSpec.constructorBuilder().apply(block).build())

inline fun TypeSpec.Builder.overriding(
    method: ExecutableElement,
    block: FunSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addFunction(FunSpec.overriding(method).apply(block).build())

inline fun TypeSpec.Builder.overriding(
    method: ExecutableElement,
    enclosing: DeclaredType,
    types: Types,
    block: FunSpec.Builder.() -> Unit = {}
): TypeSpec.Builder = addFunction(FunSpec.overriding(method, enclosing, types).apply(block).build())

/*
 * Shortcuts for using Function Spec as setter or getter
 */
inline fun PropertySpec.Builder.setter(block: FunSpec.Builder.() -> Unit = {}): PropertySpec.Builder =
    setter(FunSpec.setterBuilder().apply(block).build())

inline fun PropertySpec.Builder.getter(block: FunSpec.Builder.() -> Unit = {}): PropertySpec.Builder =
    getter(FunSpec.getterBuilder().apply(block).build())


inline fun <reified A : Annotation> FunSpec.Builder.annotation(
    block: AnnotationSpec.Builder.() -> Unit = {}
): FunSpec.Builder = addAnnotation(AnnotationSpec.builder(A::class).apply(block).build())

/*
 * Shortcuts for fun spec
 */
inline fun FunSpec.Builder.param(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    block: ParameterSpec.Builder.() -> Unit = {}
): FunSpec.Builder = addParameter(ParameterSpec.builder(name, type, *modifiers).apply(block).build())

@OptIn(ExperimentalStdlibApi::class)
inline fun FunSpec.Builder.param(
    name: String,
    type: KType,
    vararg modifiers: KModifier,
    block: ParameterSpec.Builder.() -> Unit = {}
): FunSpec.Builder = addParameter(ParameterSpec.builder(name, type.javaType, *modifiers).apply(block).build())

inline fun <reified T> FunSpec.Builder.param(
    name: String,
    vararg modifiers: KModifier,
    block: ParameterSpec.Builder.() -> Unit = {}
): FunSpec.Builder = addParameter(ParameterSpec.builder(name, T::class, *modifiers).apply(block).build())

/*
 * Shortcuts for creating type names
 */
fun clazz(fqName: String) = ClassName.bestGuess(fqName)

fun clazz(`package`: String, name: String, vararg nested: String) = ClassName(`package`, name, *nested)

inline fun <reified Receiver, reified Return> receiverLambda(vararg params: ParameterSpec) =
    LambdaTypeName.get(Receiver::class.asTypeName(), parameters = params, returnType = Return::class.asTypeName())

inline fun <reified Return> lambda(vararg params: ParameterSpec) =
    LambdaTypeName.get(parameters = params, returnType = Return::class.asTypeName())

fun lambda(vararg params: ParameterSpec, returns: TypeName = type("Unit")) =
    LambdaTypeName.get(parameters = params, returnType = returns)

fun receiverLambda(receiverType: TypeName, vararg params: ParameterSpec, returns: TypeName) =
    LambdaTypeName.get(receiverType, parameters = params, returnType = returns)

// vararg typenames
inline fun <reified Receiver, reified Return> receiverLambda(vararg params: TypeName) =
    LambdaTypeName.get(Receiver::class.asTypeName(), parameters = params, returnType = Return::class.asTypeName())

inline fun <reified Return> lambda(vararg params: TypeName) =
    LambdaTypeName.get(parameters = params, returnType = Return::class.asTypeName())

fun lambda(vararg params: TypeName, returns: TypeName = type("Unit")) =
    LambdaTypeName.get(parameters = params, returnType = returns)

fun receiverLambda(receiverType: TypeName, vararg params: TypeName, returns: TypeName = type("Unit")) =
    LambdaTypeName.get(receiverType, parameters = params, returnType = returns)

// typevar
fun type(name: String, variance: KModifier? = null) = TypeVariableName(name, variance)

fun type(name: String, vararg bounds: TypeName, variance: KModifier? = null) =
    TypeVariableName(name, *bounds, variance = variance)

fun type(name: String, vararg bounds: KClass<*>, variance: KModifier? = null) =
    TypeVariableName(name, *bounds, variance = variance)

@OptIn(ExperimentalStdlibApi::class)
fun type(name: String, vararg bounds: KType, variance: KModifier? = null) =
    TypeVariableName(name, *(bounds.map { it.javaType }.toTypedArray()), variance = variance)

// data class constructor
/**
 * Adds the FunSpec created from the specified [scope] as the primary-constructor of `this` type-spec, and creates
 * `val` properties for each parameter specified.
 */
inline fun TypeSpec.Builder.primaryDataConstructor(scope: FunSpec.Builder.() -> Unit): TypeSpec.Builder {
    val constructor = FunSpec.constructorBuilder().apply(scope)
    primaryConstructor(constructor.build())
    for (param in constructor.parameters) value(param.name, param.type) { initializer(param.name) }
    return this
}

/**
 * Adds the FunSpec created from the specified [scope] as the primary-constructor of `this` type-spec, and creates
 * `val` properties for each parameter specified and applies the specified [variableScope] to each created property.
 */
inline fun TypeSpec.Builder.primaryDataConstructor(
    scope: FunSpec.Builder.() -> Unit,
    variableScope: PropertySpec.Builder.() -> Unit
): TypeSpec.Builder {
    val constructor = FunSpec.constructorBuilder().apply(scope)
    primaryConstructor(constructor.build())
    for (param in constructor.parameters) value(param.name, param.type) {
        initializer(param.name)
        apply(variableScope)
    }
    return this
}