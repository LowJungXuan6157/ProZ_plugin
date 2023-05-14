package com.lowjungxuan.proz.versionchecker.model.chat

import com.lowjungxuan.proz.versionchecker.model.resource.ResourceCategory
import com.lowjungxuan.proz.versionchecker.model.user.User
import java.io.Serializable

/**
 * idea聊天消息模型
 */
data class IdeaMessage(
    val atUser: User?,
    val code: String,
    val codeType: String,
    val content: String,
    val createTime: String,
    val id: Int,
    val room: ResourceCategory,
    val roomName: String,
    val sendTime: String,
    val user: User
): Serializable