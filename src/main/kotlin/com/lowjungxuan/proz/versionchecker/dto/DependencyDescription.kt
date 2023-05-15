package com.lowjungxuan.proz.versionchecker.dto

data class DependencyDescription(
    val dependency: Dependency,
    val latestVersion: String
)