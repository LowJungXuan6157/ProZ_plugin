package com.lowjungxuan.proz.versionchecker.model

data class PubSearchResult (
    val packages: List<Package>,
    val next: String
)

data class Package (
    val `package`: String
)
