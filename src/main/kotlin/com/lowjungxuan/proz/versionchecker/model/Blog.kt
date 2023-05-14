package com.lowjungxuan.proz.versionchecker.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class BlogCategory (
    val createTime: String,
    val id: Long,
    val intro: String,
    val logo: String,
    val name: String
)

