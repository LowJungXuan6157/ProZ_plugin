package com.lowjungxuan.proz.versionchecker.form.socket

import cn.hutool.core.net.url.UrlBuilder
import com.intellij.ui.ColorUtil
import com.intellij.ui.JBColor
import com.intellij.util.ui.UIUtil
import com.lowjungxuan.proz.versionchecker.config.DioxListingUiConfig
import com.lowjungxuan.proz.versionchecker.dsl.requestDetailLayout
import com.lowjungxuan.proz.versionchecker.icons.MyIcons
import java.awt.Component
import javax.swing.*

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
        return  requestDetailLayout(value,isSelected,setting)
    }
}




