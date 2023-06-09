package com.lowjungxuan.proz.versionchecker.actions

import com.alibaba.fastjson2.JSON
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKey
import com.intellij.openapi.project.Project
import com.lowjungxuan.proz.versionchecker.form.components.ApiListPanel
import com.lowjungxuan.proz.versionchecker.form.socket.Request


fun Request.getDataJson() : Any {
    if(data is Map<*, *>) {
        return data
    }
    if(data is String && JSON.isValid(data)) {
        return  JSON.parse(data)
    }
    return data ?: ""
}

fun AnActionEvent.api(): Request? {
    return getData(DataKey.create(ApiListPanel.SELECT_KEY))
}

 fun AnActionEvent.apiListProject() : Project? {
    return getData(DataKey.create(ApiListPanel.PROJECT_KEY))
}