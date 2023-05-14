package com.lowjungxuan.proz.versionchecker.note.setting

import com.intellij.openapi.project.Project
import com.intellij.ui.dsl.builder.panel
import com.lowjungxuan.proz.versionchecker.common.MyDialogWrapper
import com.lowjungxuan.proz.versionchecker.note.jdbc.SqliteConnectManager
import javax.swing.JComponent


///设置flutter plugin 收藏面板
class InitFlutterPluginSettingDialog( override val project: Project) : MyDialogWrapper(project) {

    init {
        super.init()
        title = "插件收藏"
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row ("状态") {
                button("初始化表") {
                    SqliteConnectManager.createFlutterPluginTable()
                }
            }
        }
    }

}