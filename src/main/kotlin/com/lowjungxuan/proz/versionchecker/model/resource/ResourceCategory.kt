package com.lowjungxuan.proz.versionchecker.model.resource

import com.alibaba.fastjson2.JSONObject
import com.intellij.util.xmlb.Converter
import java.io.Serializable

data class ResourceCategory(
    val description: String,
    val id: Int,
    val level: Int,
    val logo: String,
    val name: String,
    val type: String
) : Serializable


