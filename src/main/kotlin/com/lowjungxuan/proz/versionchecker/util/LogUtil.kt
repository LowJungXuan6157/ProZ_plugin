package com.lowjungxuan.proz.versionchecker.util

import com.intellij.openapi.diagnostic.Logger
import com.lowjungxuan.proz.versionchecker.annotator.PubPackagesAnnotator

private val LOG = Logger.getInstance(PubPackagesAnnotator::class.java)

fun printMessage(message: String) {
    println(message)
    LOG.info(message)
}
