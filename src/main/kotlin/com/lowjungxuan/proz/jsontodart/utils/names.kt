package com.lowjungxuan.proz.jsontodart.utils

/**
 * Create valid Dart file name for class (snake_case)
 */
fun createFileName(className: String): String {
    var fileName = ""
    var prevChar: Char? = null
    className.toCharArray().forEach { char ->
        if (prevChar?.isLowerCase() == true && char.isUpperCase()) {
            fileName += "_" + Character.toLowerCase(char)
        } else {
            fileName += Character.toLowerCase(char)
        }
        prevChar = char
    }
    return fileName
}