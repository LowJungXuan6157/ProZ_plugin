package com.lowjungxuan.proz.versionchecker.notif

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.EditorNotificationPanel
import com.intellij.ui.EditorNotificationProvider
import com.intellij.ui.HyperlinkLabel
import com.intellij.ui.awt.RelativePoint
import com.intellij.util.ui.UIUtil
import com.jetbrains.lang.dart.DartFileType
import io.flutter.sdk.FlutterSdkUtil
import com.lowjungxuan.proz.versionchecker.dialog.SearchDialog
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle
import com.lowjungxuan.proz.versionchecker.icons.MyIcons
import com.lowjungxuan.proz.versionchecker.util.CacheUtil
import com.lowjungxuan.proz.versionchecker.util.getPubspecYAMLFile
import com.lowjungxuan.proz.versionchecker.window.AllPluginsCheckVersion
import java.util.function.Function
import javax.swing.JComponent

///pubyaml 窗口工具
class PubPluginVersionCheckNotification : EditorNotificationProvider {

    override fun collectNotificationData(
        project: Project,
        file: VirtualFile
    ): Function<in FileEditor, out JComponent?> {
        return Function<FileEditor, JComponent?> {
            project.getPubspecYAMLFile() ?: return@Function null
            if (file.fileType is DartFileType) {
                return@Function null
            }
            val filename: String = file.name
            if (filename != "pubspec.yaml") {
                return@Function null
            }
            if(!FlutterSdkUtil.hasFlutterModules(project)){
                return@Function null
            }
            YamlFileNotificationPanel(it, project)
        }
    }
}

class YamlFileNotificationPanel(private val fileEditor: FileEditor, val project: Project) :
    EditorNotificationPanel(fileEditor, UIUtil.getEditorPaneBackground()) {

    private var checkLabel: HyperlinkLabel = createActionLabel(PluginBundle.get("check.flutter.plugin")) {
        checkNewVersions()
    }

    init {
        icon(MyIcons.dartPluginIcon)
        text(PluginBundle.get("w.t"))

        myLinksPanel.add(checkLabel)

        val searchPluginLabel = createActionLabel(PluginBundle.get("search.pub.plugin")) {
            search()
        }
        myLinksPanel.add(searchPluginLabel)


        //清理缓存
        val cleanCacheBtn = createActionLabel(PluginBundle.get("clean.cache")) {
            CacheUtil.clean()
        }
        myLinksPanel.add(cleanCacheBtn)


        //重新索引
//        val reIndex = createActionLabel("扫描未使用的包") {
//            ApplicationManager.getApplication().run {
//                ScanPackageUtil.doScan(project)
//            }
//
//        }
//        myLinksPanel.add(reIndex)
    }

    private fun checkNewVersions() {
        val file = project.getPubspecYAMLFile()
        file?.containingFile?.let { DaemonCodeAnalyzer.getInstance(project).restart(it) }
        val component = JBPopupFactory.getInstance()
            .createComponentPopupBuilder(AllPluginsCheckVersion(project), fileEditor.component).createPopup()
        component.show(RelativePoint(checkLabel.locationOnScreen))

    }

    private fun search() {
        SearchDialog(project).show()
    }

}