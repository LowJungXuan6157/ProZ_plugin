package com.lowjungxuan.proz.utils

import java.util.*

fun String.toCamelCase(): String {
    var s = this
    if (this.contains("_") || this.contains("-")) {
        s = s.replace("_", " ")
        s = s.replace("-", " ")
        s = s.split(" ").joinToString("") {
            it.replaceFirstChar { it1 ->
                if (it1.isLowerCase())
                    it1.titlecase(Locale.getDefault())
                else
                    it1.toString()
            }
        }
    } else {
        s = s.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
    return s
}


fun String.digitsToWords(): String {
    val numberWords = mapOf(
        '0' to "zero",
        '1' to "one",
        '2' to "two",
        '3' to "three",
        '4' to "four",
        '5' to "five",
        '6' to "six",
        '7' to "seven",
        '8' to "eight",
        '9' to "nine"
    )
    return this.map { ch -> numberWords[ch] ?: ch.toString() }.joinToString("")
}

fun String.toSnakeCase(): String {
    return this.replace("(?<!_)(?<=.)([A-Z])".toRegex(), "_$1").lowercase(Locale.getDefault())
}
