package com.lowjungxuan.proz.jsontodart

import com.intellij.json.JsonLanguage
import com.intellij.lang.Language
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.lowjungxuan.proz.jsontodart.ui.GeneratorDialog
import com.lowjungxuan.proz.jsontodart.utils.DartFileType


class DartJsonNewFileAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = CommonDataKeys.PROJECT.getData(event.dataContext)
        val input = PsiFileFactory.getInstance(project)
            .createFileFromText(Language.findLanguageByID(JsonLanguage.INSTANCE.id)!!, "")
        val virtualFile = CommonDataKeys.VIRTUAL_FILE.getData(event.dataContext)
        if (virtualFile == null || project == null) {
            Messages.showMessageDialog(
                project,
                "The virtual file or the project cannot be null!",
                "Error", Messages.getErrorIcon()
            )
            return
        }
        val directory = PsiManager.getInstance(project).findDirectory(virtualFile)
        if (directory == null) {
            Messages.showMessageDialog(
                project,
                "Please select a folder you want to save this file",
                "Directory Not Found", Messages.getErrorIcon()
            )
            return
        }
        GeneratorDialog(project, input) { fileName, code, options, base->
            WriteCommandAction.runWriteCommandAction(directory.project) {
                try {
                    val file = PsiFileFactory.getInstance(directory.project)
                        .createFileFromText("${fileName.trim('`')}.dart", DartFileType(), code)
                    directory.add(file)
                    if(options != 1){
                        val baseFile = PsiFileFactory.getInstance(directory.project)
                            .createFileFromText("base_response.dart", DartFileType(), base)
                        directory.add(baseFile)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
