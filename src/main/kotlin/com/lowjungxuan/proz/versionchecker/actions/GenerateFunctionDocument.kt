package com.lowjungxuan.proz.versionchecker.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.lang.dart.DartLanguage
import com.jetbrains.lang.dart.psi.impl.DartComponentNameImpl
import com.jetbrains.lang.dart.psi.impl.DartFormalParameterListImpl
import com.jetbrains.lang.dart.psi.impl.DartNormalFormalParameterImpl
import com.lowjungxuan.proz.versionchecker.common.MyAction

///生成方法的函数文档操作
class GenerateFunctionDocument : MyAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val data = e.getData(CommonDataKeys.PSI_ELEMENT)
        if (data is DartComponentNameImpl) {
            val nextSibling = data.nextSibling
            if (nextSibling is DartFormalParameterListImpl) {
                val psis = PsiTreeUtil.collectElements(
                    nextSibling
                ) { element -> element is DartNormalFormalParameterImpl }
                val sb = StringBuilder()
                sb.append("///\n")
                psis.forEach {
                    val name = it.firstChild.lastChild.text
                    sb.append("/// [$name] - \n")
                }.takeIf { psis.isNotEmpty() }
                e.project?.let {
                    val psiFile = e.getData(CommonDataKeys.PSI_FILE)
                    val document = e.getData(CommonDataKeys.EDITOR)?.document
                    createDartDocPsiElement(it, data.parent, sb.toString(), psiFile, document)
                }
            }
        }
    }

    private fun createDartDocPsiElement(
        project: Project,
        element: PsiElement,
        text: String,
        file: PsiFile?,
        doc: Document?
    ) {
        println(text)
        val psiFile = PsiFileFactory.getInstance(project).createFileFromText(DartLanguage.INSTANCE, text)
        WriteCommandAction.runWriteCommandAction(project) {
            element.addBefore(psiFile.navigationElement, element.originalElement)
            doc?.let {
                PsiDocumentManager.getInstance(project).doPostponedOperationsAndUnblockDocument(doc)
                file?.let {
                    CodeStyleManager.getInstance(project).reformat(file)
                }
            }
        }

    }
}
