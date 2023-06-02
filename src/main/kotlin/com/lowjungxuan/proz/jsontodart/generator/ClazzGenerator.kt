package com.lowjungxuan.proz.jsontodart.generator

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.lowjungxuan.proz.utils.toCamelCase

class ClazzGenerator {

    fun generate(className: String, json: String, option: Int) = try {
        JsonParser.parseString(json).let {
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
            if (option == 1) {
                sb.append("import 'package:pro_z/pro_z.dart';\n\n")
            } else {
                sb.append("import 'base_response.dart';\n\n")
            }
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

    fun generateBase(): String {
        val sb = StringBuilder()
        sb.append("abstract class Serializable {\n")
        sb.append("    fromJson(Map<String, dynamic>? json);\n")
        sb.append("\n")
        sb.append("    Map<String, dynamic> toJson();\n")
        sb.append("\n")
        sb.append("    listFromJson(List? json);\n")
        sb.append("}\n")
        sb.append("class BaseResponse<T extends Serializable> {\n")
        sb.append("    bool? success;\n")
        sb.append("    String? message;\n")
        sb.append("    T? data;\n")
        sb.append("    List<T>? datas;\n")
        sb.append("    BaseResponse({\n")
        sb.append("        this.message,\n")
        sb.append("        this.success,\n")
        sb.append("        this.data,\n")
        sb.append("        this.datas,\n")
        sb.append("    });\n")
        sb.append("    factory BaseResponse.fromJson(T item, Map<String, dynamic> json) {\n")
        sb.append("    var jsonData = json['data'];\n")
        sb.append("    if (jsonData is List && jsonData.isEmpty) jsonData = [];\n")
        sb.append("    final success = json['success'];\n")
        sb.append("    T? mItem;\n")
        sb.append("    List<T>? mItemList;\n")
        sb.append("    if (success) {\n")
        sb.append("        if (jsonData is List) {\n")
        sb.append("            mItemList = item.listFromJson(jsonData);\n")
        sb.append("        } else {\n")
        sb.append("            mItem = item.fromJson(jsonData);\n")
        sb.append("        }\n")
        sb.append("    }\n")
        sb.append("    return BaseResponse<T>(\n")
        sb.append("        success: json['success'],\n")
        sb.append("        message: json.containsKey('message') ? json['message'] : '',\n")
        sb.append("    data: mItem,\n")
        sb.append("    datas: mItemList,\n")
        sb.append("    );\n")
        sb.append("  }\n")
        sb.append("}\n")
        return sb.toString()
    }//

    private fun printClazz(keepName: Boolean, clazz: Clazz): String {
        val sb = StringBuilder()

        val className = if (keepName)
            clazz.name.toCamelCase()
        else
            clazz.runtimeType().toCamelCase()

        // class
        sb.append("class $className extends Serializable {\n")

        // constructor
        if (!clazz.children.isNullOrEmpty()) {
            clazz.children!!.map {
                "  ${it.getConstructor()}\n"
            }.forEach {
                sb.append(it)
            }
        }
        sb.append("\n")

        // parameter
        if (!clazz.children.isNullOrEmpty()) {
            sb.append("  $className({\n")
            clazz.children!!.map {
                "    ${it.getParameter()}\n"
            }.forEach {
                sb.append(it)
            }
            sb.append("  });\n")
        }
        sb.append("\n")

        // fromJson(map)
        if (!clazz.children.isNullOrEmpty()) {
            sb.append("  $className.fromJson(Map<String, dynamic> json) {\n")
            clazz.children!!.map {
                "    ${it.getFromJson()}\n"
            }.forEach {
                sb.append(it)
            }
            sb.append("  }\n")
        }
        sb.append("\n")

        // toJson()
        if (!clazz.children.isNullOrEmpty()) {
            sb.append("  @override\n")
            sb.append("  Map<String, dynamic> toJson() {\n")
            sb.append("    final Map<String, dynamic> data = <String, dynamic>{};\n")
            clazz.children!!.map {
                "    ${it.getToJson()}\n"
            }.forEach {
                sb.append(it)
            }
            sb.append("    return data;\n")
            sb.append("  }\n")
        }

        sb.append("\n")
        sb.append("  static final shared = $className();\n")
        sb.append("\n")
        sb.append("  @override\n")
        sb.append("  fromJson(Map<String, dynamic>? json) {\n")
        sb.append("    if (json == null) return null;\n")
        sb.append("    return $className.fromJson(json);\n")
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