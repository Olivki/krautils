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

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.MemberName
import java.io.Closeable
import kotlin.reflect.KProperty1
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

// we need to do this because kotlinpoet doesn't allow us to customize the column limit
// https://github.com/square/kotlinpoet/pull/751
@Suppress("LocalVariableName", "UNCHECKED_CAST", "FunctionName")
fun FileSpec._writeTo(out: Appendable, columnLimit: Int = 120) {
    val DEFAULT_INDENT = "    "

    val NullAppendable = Class.forName("com.squareup.kotlinpoet.NullAppendable").kotlin.objectInstance!! as Appendable

    val writerClass = Class.forName("com.squareup.kotlinpoet.CodeWriter").kotlin
    val writerConst = writerClass.constructors.first()

    val memberImports = (this::class.memberProperties.first { it.name == "memberImports" } as KProperty1<FileSpec, Map<String, Any>>).let {
        it.isAccessible = true
        it.get(this)
    }

    fun CodeWriter(
        out: Appendable,
        indent: String = DEFAULT_INDENT,
        memberImports: Map<String, Any> = emptyMap(),
        importedTypes: Map<String, ClassName> = emptyMap(),
        importedMembers: Map<String, MemberName> = emptyMap(),
        limit: Int = columnLimit
    ) : Closeable = writerConst.call(out, indent, memberImports, importedTypes, importedMembers, limit) as Closeable

    fun emit(codeWriter: Any) = this::class.functions.first { it.name == "emit" }.let {
        it.isAccessible = true
        it.call(this, codeWriter)
        Unit
    }

    val importsCollector = CodeWriter(NullAppendable, DEFAULT_INDENT, memberImports, limit = Integer.MAX_VALUE)

    emit(importsCollector)

    val suggestedTypeImports = importsCollector::class.functions.first { it.name == "suggestedTypeImports" }.let {
        it.call(importsCollector) as Map<String, ClassName>
    }
    val suggestedMemberImports = importsCollector::class.functions.first { it.name == "suggestedMemberImports" }.let {
        it.call(importsCollector) as Map<String, MemberName>
    }

    importsCollector.close()

    CodeWriter(out, DEFAULT_INDENT, memberImports, suggestedTypeImports, suggestedMemberImports).use {
        emit(it)
    }
}