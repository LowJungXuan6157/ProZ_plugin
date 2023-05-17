package com.lowjungxuan.proz.jsontodart.utils

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import java.io.IOException

fun VirtualFile.write(project: Project?, content: String) {
    val runnable = Runnable {
        try {
            setBinaryContent(content.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    WriteCommandAction.runWriteCommandAction(project, runnable)
}