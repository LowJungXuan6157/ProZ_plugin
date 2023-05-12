package com.lowjungxuan.proz

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager

class GetX : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return

        // Show input dialog to get folder name
        val folderName = Messages.showInputDialog(project, "Enter folder name", "New GetX Page", MyIcons.GetXDialog)
        if (!folderName.isNullOrEmpty()) {
            // Get current directory
            val currentDir = getCurrentDirectory(event)

            // Create new folder
            val newFolder = currentDir?.createSubdirectory(folderName.toString())

            // Create new files
            newFolder?.let {
                createFile(
                    it,
                    "${folderName}Page.dart",
                    GetXTemplate.page.replace("${'$'}{folderName}", folderName.toString())
                )
                createFile(
                    it,
                    "${folderName}Controller.dart",
                    GetXTemplate.controller.replace("${'$'}{folderName}", folderName.toString())
                )
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
}
