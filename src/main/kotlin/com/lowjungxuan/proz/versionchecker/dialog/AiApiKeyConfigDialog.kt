package com.lowjungxuan.proz.versionchecker.dialog

import com.intellij.openapi.project.Project
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.lowjungxuan.proz.versionchecker.common.MyDialogWrapper
import com.lowjungxuan.proz.versionchecker.util.CredentialUtil
import javax.swing.JComponent

///配置open ai 的 key
class AiApiKeyConfigDialog(override val project: Project) : MyDialogWrapper(project) {
    private var apiKey = CredentialUtil.openApiKey


    init {
        super.init()
        title = "Configure apikey"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row("ApiKey") {
                passwordField().bindText({ apiKey }, { apiKey = it }).align(Align.FILL)
            }
            row {
                comment("The plugin will not collect your data, it will be securely saved locally")
            }
        }
    }

    override fun isOKActionEnabled(): Boolean {
        return apiKey.isNotEmpty()
    }

    override fun doOKAction() {
        super.doOKAction()
        CredentialUtil.setOpenAiKey(apiKey)
    }
}