package com.lowjungxuan.proz.versionchecker.util

import com.intellij.openapi.fileEditor.ex.FileEditorProviderManager
import com.intellij.openapi.project.Project
import com.intellij.testFramework.LightVirtualFile
import org.intellij.plugins.markdown.lang.MarkdownFileType
import org.intellij.plugins.markdown.ui.preview.MarkdownEditorWithPreview
import org.intellij.plugins.markdown.ui.preview.MarkdownSplitEditorProvider


object SwingUtil {

    fun getMkEditor(project: Project, initText: String = ""): MarkdownEditorWithPreview {
        val vF = LightVirtualFile("D", MarkdownFileType.INSTANCE, initText)
        val mkEdit =
            FileEditorProviderManager.getInstance().getProviders(project, vF).first() as MarkdownSplitEditorProvider
        val edit = mkEdit.createEditor(project, vF)
        return edit as MarkdownEditorWithPreview
    }

}
