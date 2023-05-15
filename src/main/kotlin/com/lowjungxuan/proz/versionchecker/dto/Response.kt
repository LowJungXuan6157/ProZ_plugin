package com.lowjungxuan.proz.versionchecker.dto

import java.util.*

data class Response(
    val latest: DependencyResponse,
    val versions: List<DependencyResponse>
)

data class DependencyResponse(
    val version: String,
    val published: Date
)