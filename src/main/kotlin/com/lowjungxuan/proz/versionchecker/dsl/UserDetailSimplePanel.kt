package com.lowjungxuan.proz.versionchecker.dsl

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBLabel
import com.intellij.ui.dsl.builder.RightGap
import com.intellij.ui.dsl.builder.RowLayout
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.ui.JBFont
import com.lowjungxuan.proz.versionchecker.model.user.User
import com.lowjungxuan.proz.versionchecker.widget.AvatarIcon
import javax.swing.BorderFactory

fun userDetailSimplePanel(user: User): DialogPanel {
    lateinit var corePanel: DialogPanel

    val avatarLabel = JBLabel(AvatarIcon(50,50,user.picture))
    val account = JBLabel("@"+user.loginNumber).apply {
        font = JBFont.label()
    }
    val usernameLabel = JBLabel(user.nickName).apply {
        font = JBFont.h3()
    }
    corePanel = panel {
        row {
           cell(avatarLabel).gap(RightGap.SMALL)
            panel {
                row {
                    cell(usernameLabel)
                }
                row {
                    cell(account)
                }
            }
        }.layout(RowLayout.PARENT_GRID)
    }
    corePanel.border = BorderFactory.createEmptyBorder(DslConfig.padding,DslConfig.padding,DslConfig.padding,DslConfig.padding)

    return corePanel
}