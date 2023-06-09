package com.lowjungxuan.proz.versionchecker.dsl

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.ui.JBFont
import com.intellij.util.ui.UIUtil
import com.lowjungxuan.proz.versionchecker.config.DioxListeningSetting
import com.lowjungxuan.proz.versionchecker.form.socket.Request
import java.net.URI
import javax.swing.BorderFactory


fun String.formatUrl(setting: DioxListeningSetting): String{
    if(setting.showHost.not()){
       return URI(this).path
    }
    return this
}

/**
 * 新版的请求UI
 */
fun requestDetailLayout(request: Request, isSelected: Boolean,setting: DioxListeningSetting): DialogPanel {

    val color = UIUtil.getLabelDisabledForeground()
    val p = panel {
        row {
            label(request.url!!.formatUrl(setting)).component.apply {
                foreground = if(isSelected) UIUtil.getListSelectionForeground(false) else UIUtil.getLabelForeground()
            }
        }
        row {
            label(request.statusCode!!.toString())
                .visible(setting.showStatusCode).component.apply {
                font = JBFont.small()
                foreground = if(request.statusCode == 200) UIUtil.getLabelInfoForeground() else color
            }
            label(request.method!!).visible(setting.showMethod).component.apply {
                font = JBFont.small()
                foreground = color
            }
            label(request.timestamp!!.toString()+"ms").visible(setting.showTimestamp).component.apply {
                font = JBFont.small()
                foreground = color
            }
            label(request.createDate).visible(setting.showDate).component.apply {
                font = JBFont.small()
                foreground = color
            }
            label(request.projectName?:"").component.apply {
                font = JBFont.small()
                foreground = color
            }
        }.visible(setting.showStatusCode || setting.showMethod || setting.showTimestamp || setting.showDate)
    }
    p.background = if(isSelected)  UIUtil.getListBackground(true,false)  else UIUtil.getPanelBackground()
    return p.withBorder(BorderFactory.createEmptyBorder(0,12,0,12))
}