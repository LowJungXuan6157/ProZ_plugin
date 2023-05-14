package com.lowjungxuan.proz.versionchecker.model


data class TextModel (
    val context: String,
    val id: Long,
    val intro: String,
    val isEncryptionText: Boolean,
    val name: String,
    val viewPassword: String
)
