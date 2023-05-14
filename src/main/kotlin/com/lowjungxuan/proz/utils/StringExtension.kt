package com.lowjungxuan.proz.utils

import java.util.*

fun String.toCamelCase(): String {
    return if (this.contains("_")) {
        this.split("_").joinToString("") {
            it.replaceFirstChar { it1 ->
                if (it1.isLowerCase())
                    it1.titlecase(Locale.getDefault())
                else
                    it1.toString()
            }
        }
    } else {
        this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}

fun String.toSnakeCase(): String {
    return this.replace("(?<!_)(?<=.)([A-Z])".toRegex(), "_$1").lowercase(Locale.getDefault())
}
