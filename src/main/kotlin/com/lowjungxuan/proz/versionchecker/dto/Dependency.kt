package com.lowjungxuan.proz.versionchecker.dto

data class Dependency(
    val packageName: String,
    val currentVersion: String,
    val index: Int
)