package com.lowjungxuan.proz.versionchecker.common

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.JSONReader
import com.alibaba.fastjson2.JSONWriter
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBScrollPane
import com.lowjungxuan.proz.versionchecker.dialog.FreezedClassesGenerateDialog
import com.lowjungxuan.proz.versionchecker.services.impl.ModelToFreezedModelServiceImpl
import com.lowjungxuan.proz.versionchecker.util.toastWithError
import javax.swing.JComponent

fun String.getVirtualFile(): VirtualFile? {
    return LocalFileSystem.getInstance().findFileByPath(this)
}

fun Project.jsonToFreezedRun(jsonText: String) {
    try {

        val jsonObject = JSON.parseObject(jsonText, JSONReader.Feature.SupportArrayToBean)
        val jsonObjectToFreezedCovertModelList =
            ModelToFreezedModelServiceImpl().jsonObjectToFreezedCovertModelList(jsonObject)
        FreezedClassesGenerateDialog(this, jsonObjectToFreezedCovertModelList).show()
    } catch (e: Exception) {
        println("json to freezed error:$e")
        toastWithError("$e")
        e.printStackTrace()
    }
}
