package com.lowjungxuan.proz.versionchecker.linemark

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer
import com.intellij.codeInsight.daemon.GutterIconNavigationHandler
import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.icons.AllIcons
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.util.BaseListPopupStep
import com.intellij.psi.PsiElement
import com.intellij.ui.awt.RelativePoint
import com.lowjungxuan.proz.versionchecker.note.jdbc.FlutterCollectService
import com.lowjungxuan.proz.versionchecker.note.jdbc.SqliteConnectManager
import org.jetbrains.yaml.psi.impl.YAMLKeyValueImpl
import com.lowjungxuan.proz.versionchecker.actions.PUB_URL
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle
import com.lowjungxuan.proz.versionchecker.icons.MyIcons
import com.lowjungxuan.proz.versionchecker.util.getPluginName
import com.lowjungxuan.proz.versionchecker.util.isDartPluginElement
import com.lowjungxuan.proz.versionchecker.util.toast
import java.awt.event.MouseEvent
import javax.swing.Icon



class PluginDartIconLineMark : LineMarkerProvider {

    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<PsiElement>? {
        if (element.isDartPluginElement() && element is YAMLKeyValueImpl) {
            val isExits = FlutterCollectService.exits(element.getPluginName()) //是否已经收藏了
            return LineMarkerInfo(
                element.firstChild, element.firstChild.textRange,
                if(isExits)  MyIcons.collect else  MyIcons.dartPackageIcon, { element.text }, PluginDartIconLineMarkNavHandler(element),
                GutterIconRenderer.Alignment.LEFT
            ) { "111" }
        }
        return null
    }
}

///处理点击
class PluginDartIconLineMarkNavHandler(val element: PsiElement) : GutterIconNavigationHandler<PsiElement> {
    override fun navigate(e: MouseEvent?, elt: PsiElement?) {
        if ((e != null) && (e.clickCount == 1)) {
            JBPopupFactory.getInstance().createListPopup(PluginDartIconActioinMenuList(element = element))
                .show(RelativePoint(e.locationOnScreen))
        }
    }
}

data class PluginDartIconActionMenuItem(val title: String, val type: String, val icon: Icon)
class PluginDartIconActioinMenuList(val element: PsiElement) : BaseListPopupStep<PluginDartIconActionMenuItem>() {

    private val isExites = FlutterCollectService.exits(element.getPluginName()) //是否已经收藏

    private val menus
        get() = listOf(
            PluginDartIconActionMenuItem(
                title = "${PluginBundle.get("nav.to")} pub.dev",
                type = "navToPub",
                icon = AllIcons.Toolwindows.WebToolWindow
            ),
            PluginDartIconActionMenuItem(
                PluginBundle.get("plugin.collect"),
                "collect",
                if (isExites) MyIcons.collect else MyIcons.collectUn
            )
        )

    init {
        super.init(element.text, menus, menus.map { it.icon })
    }


    override fun getTextFor(value: PluginDartIconActionMenuItem?): String {
        return value?.title ?: "未知选项"
    }

    override fun onChosen(selectedValue: PluginDartIconActionMenuItem?, finalChoice: Boolean): PopupStep<*>? {
        when (selectedValue?.type) {
            menus[0].type -> {
                BrowserUtil.browse("$PUB_URL${element.getPluginName()}")
            }

            menus[1].type -> {
                addToCollect()
            }
        }
        return super.onChosen(selectedValue, finalChoice)
    }


    ///添加收藏
    private fun addToCollect() {
        if (isExites) {
            removeCollect()
            return
        }
        SqliteConnectManager.createFlutterPluginTable()
        FlutterCollectService.add(element.getPluginName())
        /// update the icon
        DaemonCodeAnalyzer.getInstance(element.project).restart()
    }


    ///移除收藏
    private fun removeCollect() {
        val success = FlutterCollectService.remove(element.getPluginName())
        if (success) {
            element.project.toast("Delete succeeded")

            /// update the icon
            DaemonCodeAnalyzer.getInstance(element.project).restart()
        } else {
            element.project.toast("Delete failed")
        }



    }
}