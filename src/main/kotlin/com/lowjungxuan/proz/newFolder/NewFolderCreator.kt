package com.lowjungxuan.proz.newFolder

import MyDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager
import com.lowjungxuan.proz.utils.toSnakeCase

class NewFolderCreator : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val dialog = MyDialog(project)
        dialog.show()
        if (dialog.isOK) {  // Check if the user clicked OK
            val data = dialog.getDialogData()
            when (data.option) {
                1 -> createGetX(getCurrentDirectory(event)?.createSubdirectory(data.text.toSnakeCase()))
                2 -> createBloc(getCurrentDirectory(event)?.createSubdirectory(data.text.toSnakeCase()))
            }
        }
    }

    private fun getCurrentDirectory(event: AnActionEvent): PsiDirectory? {
        val virtualFile = event.getData(CommonDataKeys.VIRTUAL_FILE)
        return if (virtualFile != null && virtualFile.isDirectory) {
            PsiManager.getInstance(event.project!!).findDirectory(virtualFile)
        } else {
            virtualFile?.parent?.let { PsiManager.getInstance(event.project!!).findDirectory(it) }
        }
    }

    private fun createFile(directory: PsiDirectory, fileName: String, fileContent: String) {
        WriteCommandAction.runWriteCommandAction(directory.project) {
            directory.createFile(fileName).viewProvider.document?.setText(fileContent)
        }
    }

    private fun createGetX(newFolder: PsiDirectory?) {
        newFolder?.let {
            createFile(
                it,
                "${it.name}_page.dart", NewFolderCreatorTemplate.getXPage(it.name)
            )
            createFile(
                it,
                "${it.name}_controller.dart", NewFolderCreatorTemplate.getXController(it.name)
            )
        }
    }

    private fun createBloc(newFolder: PsiDirectory?) {}
}
