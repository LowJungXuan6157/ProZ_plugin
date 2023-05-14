package com.lowjungxuan.proz.versionchecker.form.socket

import com.lowjungxuan.proz.versionchecker.config.DioxListingUiConfig
import com.lowjungxuan.proz.versionchecker.dsl.requestDetailLayout
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.ListCellRenderer

///渲染请求列表
class MyCustomItemRender : ListCellRenderer<Request> {

    private val setting = DioxListingUiConfig.setting
    override fun getListCellRendererComponent(
        list: JList<out Request>?,
        value: Request?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        if (value == null) return JLabel("未知请求")
        return requestDetailLayout(value, isSelected, setting)
    }
}




