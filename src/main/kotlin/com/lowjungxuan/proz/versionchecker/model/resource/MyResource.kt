package com.lowjungxuan.proz.versionchecker.model.resource

import com.lowjungxuan.proz.versionchecker.model.user.User

data class MyResource (
    val authority: Long,
    val clickCount: Long,
    val content: String,
    val createDate: String,
    val description: String,
    val id: Long,
    val label: String,
    val links: String,
    val thumbnailImage: String,
    val title: String,
    val type: String,
    val user: User,
    var category: ResourceCategory
)
