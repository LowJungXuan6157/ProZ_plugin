package com.lowjungxuan.proz.jsontodart.generator

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.intellij.util.containers.isNullOrEmpty
import com.lowjungxuan.proz.jsontodart.utils.Settings
import com.lowjungxuan.proz.utils.toCamelCase

class ClazzGenerator(val settings: Settings?) {

    fun generate(fileName: String, className: String, json: String) = try {
        JsonParser().parse(json).let {
            when (it) {
                is JsonObject -> it.asJsonObject
                is JsonArray -> it.asJsonArray[0].asJsonObject
                else -> null
            }
        }.let { obj ->
            mutableListOf<Clazz>().let {
                Clazz(it, className, obj) to it
            }
        }.let { (clazz, clazzes) ->
            val sb = StringBuilder()

            sb.append("import 'package:pro_z/pro_z.dart';\n\n")

            clazzes.reversed().forEach {
                sb.append(printClazz(it == clazz, it))
                sb.append("\n\n")
            }
            sb.toString()
        }
    } catch (jsonParseException: JsonParseException) {
        jsonParseException.printStackTrace()
        "error: not supported json"
    } catch (illegalStateException: IllegalStateException) {
        illegalStateException.printStackTrace()

        if (illegalStateException.message?.startsWith("Not a JSON Object") == true) {
            "error: not supported json"
        } else {
            "error: unknown"
        }
    }

    private fun printClazz(keepName: Boolean, clazz: Clazz): String {
        val sb = StringBuilder()

        val className = if (keepName)
            clazz.name.toCamelCase()
        else
            clazz.getClassName().toCamelCase()


        // comments
//        if (settings?.generateComments == true) {
//            clazz.children?.map {
//                "// ${it.getComment()}\n"
//            }?.forEach {
//                sb.append(it)
//            }
//            sb.append("\n")
//        }

        // class
        sb.append("class $className extends Serializable {\n")

        // constructor
        if (!clazz.children.isNullOrEmpty()) {
            clazz.children!!.map {
                "  ${it.getStatement()};//${it.getClassName()}\n"
            }.forEach {
                sb.append(it)
            }
        }
        sb.append("\n")
        if (!clazz.children.isNullOrEmpty()) {
            sb.append("  $className({\n")
            clazz.children!!.map {
                "    this.${it.getCamelName()},\n"
            }.forEach {
                sb.append(it)
            }
            sb.append("  });\n")
        }
        sb.append("\n")
        // json
        if (!clazz.children.isNullOrEmpty()) {
            sb.append("  $className.fromJson(Map<String, dynamic> json) {\n")
            clazz.children!!.map {
                "    ${it.getCamelName()} = json['${it.getFieldName()}'];\n"
            }.forEach {
                sb.append(it)
            }
            sb.append("  }\n")
        }
        sb.append("\n")
        if (!clazz.children.isNullOrEmpty()) {
            sb.append("  Map<String, dynamic> toJson() {\n")
            sb.append("    final Map<String, dynamic> data = new Map<String, dynamic>();\n")
            clazz.children!!.map {
                //    data['id'] = this.id;
                "    data['${it.getFieldName()}'] = this.${it.getCamelName()};\n"
            }.forEach {
                sb.append(it)
            }
            sb.append("    return data;\n")
            sb.append("  }\n")
        }
        sb.append("\n")
        sb.append("  @override\n")
        sb.append("  fromJson(Map<String, dynamic>? json) {\n")
        sb.append("    if (json == null) return null;\n")
        sb.append("    return $className.fromJson(json)\n")
        sb.append("  }\n")
        sb.append("\n")
        sb.append("  @override\n")
        sb.append("  List<$className> listFromJson(List? json) {\n")
        sb.append("    if (json == null) return [];\n")
        sb.append("    List<$className> list = [];\n")
        sb.append("    for (var item in json) {\n")
        sb.append("      list.add($className.fromJson(item));\n")
        sb.append("    }\n")
        sb.append("    return list;\n")
        sb.append("  }\n")
        sb.append("}")
        return sb.toString()
    }
}