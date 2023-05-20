package com.lowjungxuan.proz.jsontodart

import com.intellij.json.JsonLanguage
import com.intellij.lang.Language
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiUtilBase
import com.lowjungxuan.proz.jsontodart.ui.GeneratorDialog
import com.lowjungxuan.proz.jsontodart.ui.Model
import com.lowjungxuan.proz.jsontodart.utils.DartFileType
import com.lowjungxuan.proz.jsontodart.utils.write
import com.lowjungxuan.proz.utils.toCamelCase


class DartJsonGenerateAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT) as Project
        val input = PsiFileFactory.getInstance(project)
            .createFileFromText(Language.findLanguageByID(JsonLanguage.INSTANCE.id)!!, "")
        val output =
            PsiUtilBase.getPsiFileInEditor(event.getData(PlatformDataKeys.EDITOR) as Editor, project)!!.virtualFile
        val className = output.nameWithoutExtension.toCamelCase()
            .split("_")
            .reduce { acc, s -> "$acc${s.toCamelCase()}" }

        GeneratorDialog(project, input, Model(className, output.nameWithoutExtension)) { _, code ->
            output.write(project, code)
        }
    }
}

class DartJsonNewFileAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT) as Project
        val input = PsiFileFactory.getInstance(project)
            .createFileFromText(Language.findLanguageByID(JsonLanguage.INSTANCE.id)!!, "")
//
//        val directory = when (val navigatable = LangDataKeys.NAVIGATABLE.getData(event.dataContext)) {
//            is PsiDirectory -> navigatable
//            is PsiFile -> navigatable.containingDirectory
//            else -> {
//                val root = ModuleRootManager.getInstance(LangDataKeys.MODULE.getData(event.dataContext) ?: return)
//                root.sourceRoots
//                    .asSequence()
//                    .mapNotNull {
//                        PsiManager.getInstance(project).findDirectory(it)
//                    }.firstOrNull()
//            }
//        } ?: return
        val project1 = CommonDataKeys.PROJECT.getData(event.dataContext)
        val virtualFile = CommonDataKeys.VIRTUAL_FILE.getData(event.dataContext)
        if (virtualFile == null || project1 == null) {
            return
        }
        val directory = PsiManager.getInstance(project1).findDirectory(virtualFile)

        GeneratorDialog(project, input) { fileName, code ->
//            val output = PsiFileFactory.getInstance(project).createFileFromText("${fileName.trim('`')}.dart", DartFileType(), code)
//            directory.add(output)
//            getCurrentDirectory(event)?.createFile(it, "${fileName.trim('`')}.dart", code)
//            getCurrentDirectory(event)?.createSubdirectory("data.text.toSnakeCase()")?.let {
//                createFile(it, "${fileName.trim('`')}.dart", code)
//            }
            if (directory != null) {
                WriteCommandAction.runWriteCommandAction(directory.project) {
                    try {
//                        val content = "This is a template content" // this can be replaced with your template content
                        val file = PsiFileFactory.getInstance(directory.project)
                            .createFileFromText("${fileName.trim('`')}.dart", DartFileType(), code)
                        directory.add(file)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun createFile(directory: PsiDirectory) {
        WriteCommandAction.runWriteCommandAction(directory.project) {
            try {
                val content = "This is a template content" // this can be replaced with your template content
                val file = PsiFileFactory.getInstance(directory.project)
                    .createFileFromText("NewFile.txt", PlainTextFileType.INSTANCE, content)
                directory.add(file)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
