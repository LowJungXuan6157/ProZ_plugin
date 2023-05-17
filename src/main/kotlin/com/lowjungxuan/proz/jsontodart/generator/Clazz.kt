package com.lowjungxuan.proz.jsontodart.generator

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.lowjungxuan.proz.utils.toCamelCase
import com.lowjungxuan.proz.utils.toSnakeCase

abstract class Clazz(
    open val root: MutableList<Clazz>,
    open val name: String,
    open val content: Any?,
    open val children: List<Clazz>?
) {
    companion object {
        operator fun invoke(root: MutableList<Clazz>, name: String, any: Any?): Clazz {
            // 处理空值
            if (any == null || "null" == any.toString())
                return EmptyClazz(root, name, any, null)

            // 处理对象
            if (any is JsonObject)
                return ObjectClazz(root, name, any, json2Clazz(root, any))

            // 处理数组
            if (any is JsonArray) {
                return if (any.size() == 0) {
                    ListClazz(root, name, any, null, null)
                } else {
                    val temp = Clazz(root, name, any[0])
                    ListClazz(root, name, any, null, temp)
                }
            }

            // 处理基本类型
            if (any.isBoolean())
                return BaseClazz(root, "bool", name, any, null)

            if (any.isInt() || any.isLong())
                return BaseClazz(root, "int", name, any, null)

            if (any.isDouble() || any.isFloat())
                return BaseClazz(root, "double", name, any, null)

            // 都不匹配的情况，默认为 String 类型
            return BaseClazz(root, "String", name, any, null)
        }

        fun json2Clazz(root: MutableList<Clazz>, jsonObject: JsonObject): List<Clazz> {
            val list = mutableListOf<Clazz>()
            for (o in jsonObject.entrySet()) {
                val entry = o as Map.Entry<*, *>
                list.add(Clazz(root, entry.key.toString(), entry.value))
            }
            return list
        }

        private fun Any.isInt() = toString().toIntOrNull() != null
        private fun Any.isLong() = toString().toLongOrNull() != null
        private fun Any.isDouble() = toString().toDoubleOrNull() != null
        private fun Any.isFloat() = toString().toFloatOrNull() != null
        private fun Any.isBoolean() = toString().let { it == "true" || it == "false" }
    }

    fun getConstructor() = "${runtimeType()}${if (runtimeType() == "dynamic") " " else "? "}${getCamelName()};"
    fun getParameter() = "this.${getCamelName()},"
    fun getFromJson() = "${getCamelName()} = ${getFromJson(runtimeType())};"
    fun getToJson() = "data['$name'] = ${toJson()};"
    fun getFieldName() = name.toSnakeCase()
    fun getCamelName() = name.toCamelCase()[0].lowercaseChar() + name.toCamelCase().substring(1)
    fun getComment() = "$name : ${content.toString().replace("\n", "")}"

    abstract fun toJson(): String
    abstract fun getFromJson(parent: String): String
    abstract fun runtimeType(): String
    abstract fun map(obj: String): String
}

data class EmptyClazz(
    override val root: MutableList<Clazz>,
    override val name: String,
    override val content: Any?,
    override val children: List<Clazz>?
) : Clazz(root, name, content, children) {
    override fun runtimeType() = "dynamic"
    override fun getFromJson(parent: String) = "json['$name']"
    override fun map(obj: String) = ""
    override fun toJson() = "${getCamelName()}?.toJson()"
}

data class BaseClazz(
    override val root: MutableList<Clazz>,
    val type: String,
    override val name: String,
    override val content: Any?,
    override val children: List<Clazz>?
) : Clazz(root, name, content, children) {

    override fun runtimeType() = type
    override fun getFromJson(parent: String) = "json['$name']"
    override fun map(obj: String): String {
        return when (type) {
            "bool" -> "$obj.toString() == 'true'"
            "int" -> "int.tryParse($obj.toString()) ?? 0"
            "double" -> "double.tryParse($obj.toString()) ?? 0.0"
            else -> "$obj.toString()"
        }
    }

    override fun toJson() = getCamelName()
}

data class ObjectClazz(
    override val root: MutableList<Clazz>,
    override val name: String,
    override val content: Any?,
    override val children: List<Clazz>?
) : Clazz(root, name, content, children) {
    init {
        root.add(this)
    }

    override fun runtimeType() = name.toCamelCase()
    override fun getFromJson(parent: String) = "${runtimeType()}.shared.fromJson(json['$name'])"

    override fun map(obj: String): String {
        return "${runtimeType()}.fromJson($obj)"
    }

    override fun toJson() = "${getCamelName()}?.toJson()"
}

data class ListClazz(
    override val root: MutableList<Clazz>,
    override val name: String,
    override val content: Any?,
    override val children: List<Clazz>?,
    val child: Clazz?
) : Clazz(root, name, content, children) {

    override fun runtimeType() = "List<${child?.runtimeType() ?: "dynamic"}>"

    override fun map(obj: String): String {
        return if (child == null || child is EmptyClazz) "$obj!=null ? []..addAll($obj as List) : null"
        else "$obj!=null ? []..addAll(($obj as List).map((${obj}o) => ${child.map("${obj}o")})) : null"
    }

    override fun getFromJson(parent: String): String {
        return if (child == null || child is EmptyClazz) "json['$name']"
        else "${name.toCamelCase()}.shared.listFromJson(json['$name'])"


    }

    override fun toJson(): String {
        if (child == null || child is BaseClazz) return getCamelName();

        if (child.runtimeType() == "dynamic") {
            return "${getCamelName()}?.map((o) {try{ return o.toJson(); }catch(e){ return o; }}).toList() ?? []"
        }

        return "${getCamelName()}?.map((e) => e.toJson()).toList() ?? []"
    }
}