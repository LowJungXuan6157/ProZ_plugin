package com.lowjungxuan.proz.versionchecker.dsl

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import com.lowjungxuan.proz.versionchecker.document.Helper
import com.lowjungxuan.proz.versionchecker.document.MyMarkdownDocRenderObject

///markdown渲染弹窗
fun docPanel(markdownText: String, project: Project, covert: Boolean = true): DialogPanel {
    val mk = if (covert) mkToHtml(markdownText,project) else markdownText
    val p = panel {
        row {
            label("<html>${mk}</html>")
        }
    }
    return p.addBorder()
}

fun mkToHtml(mkText: String, project: Project): String =
    Helper.markdown2Html(MyMarkdownDocRenderObject(mkText, project))