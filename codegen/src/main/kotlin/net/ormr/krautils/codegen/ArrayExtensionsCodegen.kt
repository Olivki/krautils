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

package net.ormr.krautils.codegen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import net.ormr.krautils.codegen.utils.*
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.writeLines
import kotlin.reflect.KClass

fun main() {
    ArrayExtensionsCodegen.generate(Path("""H:\Programming\JVM\Kotlin\Projects\Tools\krautils\core\src\main\kotlin"""))
}

object ArrayExtensionsCodegen {
    @OptIn(ExperimentalUnsignedTypes::class)
    fun generate(directory: Path) {
        val types: List<Triple<KClass<*>, KClass<*>, Boolean>> = listOf(
            Triple(Boolean::class, BooleanArray::class, false),
            Triple(Char::class, CharArray::class, false),
            Triple(Byte::class, ByteArray::class, false),
            Triple(Short::class, ShortArray::class, false),
            Triple(Int::class, IntArray::class, false),
            Triple(Long::class, LongArray::class, false),
            Triple(Float::class, FloatArray::class, false),
            Triple(Double::class, DoubleArray::class, false),
            Triple(UByte::class, UByteArray::class, true),
            Triple(UShort::class, UShortArray::class, true),
            Triple(UInt::class, UIntArray::class, true),
            Triple(ULong::class, ULongArray::class, true),
        )
        val file = file("krautils.collections", "ArrayUtilsGenerated") {
            annotation<JvmMultifileClass>()
            annotation<JvmName> { addMember("\"ArrayUtils\"") }

            indent("    ")

            for ((typeClass, arrayClass, isExperimental) in types) {
                genericArrayToPrimitiveArray(typeClass, arrayClass, isExperimental)
            }
        }

        // some additional cleaning needs to be done on the output we receive from kotlin poet, missing some features
        // for allowing proper insertion of multi-line comments at logical places..
        val content = buildString {
            appendLine(COPYRIGHT_HEADER)
            file._writeTo(this, 120)
        }.split("\n")

        val toWrite = content.shank(
            "",
            "// AUTO GENERATED, DO NOT EDIT",
            startIndex = content.indexOfLast { it.startsWith("import") } + 1
        )
        val filePath = Path(file.packageName.replace('.', '/')).resolve("${file.name}.kt")
        directory.resolve(filePath).writeLines(toWrite)
    }

    private fun FileSpec.Builder.genericArrayToPrimitiveArray(
        typeClass: KClass<*>,
        arrayClass: KClass<*>,
        isExperimental: Boolean,
    ) {
        val type = typeClass.asTypeName()
        val arrayType = arrayClass.asTypeName()
        val requireName = MemberName("kotlin", "require")
        val experimentalAnnotation by lazy { ClassName("kotlin", "ExperimentalUnsignedTypes") }
        val inputType = type("T")
        val receiver = ARRAY.parameterizedBy(inputType)

        func("mapTo") {
            if (isExperimental) addAnnotation(experimentalAnnotation)
            addKdoc(
                """
                    Applies the given [transform] function to each element of the original array and appends the results to the given [destination].
                    
                    The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.
                    
                    @throws IllegalArgumentException if [destination] does not have the same size as `this` array
                """.trimIndent()
            )
            addModifiers(KModifier.PUBLIC, KModifier.INLINE)
            addTypeVariable(inputType)
            receiver(receiver)
            param("destination", arrayType)
            param("transform", lambda(inputType, returns = type))
            returns(arrayType)
            addStatement("%M(this.size·==·destination.size)·{·\"Destination·is·either·larger·or·smaller·than·origin·array.\"·}", requireName)
            addStatement("for ((i, e) in this.withIndex()) {")
            addStatement("    destination[i] = transform(e)")
            addStatement("}")
            addStatement("return destination")
        }

        func("mapIndexedTo") {
            if (isExperimental) addAnnotation(experimentalAnnotation)
            addKdoc(
                """
                    Applies the given [transform] function to each element and its index in the original array and appends the results to the given [destination].

                    The given `destination` array must have the same size as `this` array, or a [IllegalArgumentException] is thrown.

                    @param transform function that takes the index of an element and the element itself and returns the result of the transform applied to the element
                    
                    @throws IllegalArgumentException if [destination] does not have the same size as `this` array
                """.trimIndent()
            )
            addModifiers(KModifier.PUBLIC, KModifier.INLINE)
            addTypeVariable(inputType)
            receiver(receiver)
            param("destination", arrayType)
            param("transform", lambda(ParameterSpec("i", INT), ParameterSpec("e", inputType), returns = type))
            returns(arrayType)
            addStatement("%M(this.size·==·destination.size)·{·\"Destination·is·either·larger·or·smaller·than·origin·array.\"·}", requireName)
            addStatement("for ((i, e) in this.withIndex()) {")
            addStatement("    destination[i] = transform(i, e)")
            addStatement("}")
            addStatement("return destination")
        }

        func("mapTo${arrayType.simpleName}") {
            if (isExperimental) addAnnotation(experimentalAnnotation)
            addKdoc(
                """
                    Applies the given [transform] function to each element of the original array and appends the results to a newly created [${arrayType.simpleName}].
                """.trimIndent()
            )
            addModifiers(KModifier.PUBLIC, KModifier.INLINE)
            addTypeVariable(inputType)
            receiver(receiver)
            param("transform", lambda(inputType, returns = type))
            returns(arrayType)
            addStatement("return mapTo(${arrayType.simpleName}(this.size),·transform)", arrayType)
        }

        func("mapIndexedTo${arrayType.simpleName}") {
            if (isExperimental) addAnnotation(experimentalAnnotation)
            addKdoc(
                """
                    Applies the given [transform] function to each element and its index in the original array and appends the results to a newly created [${arrayType.simpleName}].
 
                    @param transform function that takes the index of an element and the element itself and returns the result of the transform applied to the element
                """.trimIndent()
            )
            addModifiers(KModifier.PUBLIC, KModifier.INLINE)
            addTypeVariable(inputType)
            receiver(receiver)
            param("transform", lambda(ParameterSpec("i", INT), ParameterSpec("e", inputType), returns = type))
            returns(arrayType)
            addStatement("return mapIndexedTo(${arrayType.simpleName}(this.size),·transform)", arrayType)
        }
    }
}