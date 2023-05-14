package com.lowjungxuan.proz.versionchecker.common

import cn.hutool.core.swing.ScreenUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper

abstract class MyDialogWrapper(open val project: Project) : DialogWrapper(project) {


    val width: Int get() = ScreenUtil.getWidth()
    val height: Int get() = ScreenUtil.getHeight()
}