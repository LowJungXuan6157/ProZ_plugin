package com.lowjungxuan.proz.versionchecker.dialog

import cn.hutool.core.swing.ScreenUtil
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.ui.DialogPanel
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTabbedPane
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.indexing.FileBasedIndex
import com.jetbrains.lang.dart.DartLanguage
import org.jetbrains.plugins.terminal.TerminalView
import com.lowjungxuan.proz.versionchecker.common.MyDialogWrapper
import com.lowjungxuan.proz.versionchecker.common.getVirtualFile
import com.lowjungxuan.proz.versionchecker.config.JsonToFreezedSettingModelConfig
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle
import com.lowjungxuan.proz.versionchecker.model.FreezedCovertModel
import com.lowjungxuan.proz.versionchecker.services.DEFAULT_CLASS_NAME
import com.lowjungxuan.proz.versionchecker.util.toast
import com.lowjungxuan.proz.versionchecker.util.toastWithError
import com.lowjungxuan.proz.versionchecker.widget.FreezedCovertModelWidget
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel


class FreezedClassesGenerateDialog(
    override val project: Project,
    private val freezedClasses: MutableList<FreezedCovertModel>
) :
    MyDialogWrapper(project) {

    private val settingInstance = JsonToFreezedSettingModelConfig.getInstance(project)
    private val setting = settingInstance.state
    private val tabView = JBTabbedPane()
    private var fileName: String = DEFAULT_CLASS_NAME
    private var filePath: String = setting.generateToPath
    private val widgets: MutableList<FreezedCovertModelWidget> = mutableListOf()
    private lateinit var settingPanel: DialogPanel
    private val filePathLabel = JBLabel(filePath)

    init {
        super.init()
        initTabView()
        title = PluginBundle.get("freezed.title")
        setOKButtonText(PluginBundle.get("freezed.btn.ok"))
        setCancelButtonText(PluginBundle.get("cancel"))
    }


    private fun initTabView() {
        freezedClasses.forEach {
            val widget = FreezedCovertModelWidget(it, project)
            widgets.add(widget)
            tabView.add(it.className, widget)
        }
    }

    override fun createCenterPanel(): JComponent {
        return object : JPanel(BorderLayout()) {

            init {
                add(tabView, BorderLayout.CENTER)
                add(getGlobalSettingPanel(), BorderLayout.SOUTH)
            }
        }
    }


    /**
     * 存储设置
     */
    fun getGlobalSettingPanel(): DialogPanel {

        settingPanel = panel {
            group(PluginBundle.get("global.settings")) {
                row(PluginBundle.get("save.to.directory")) {
                    textFieldWithBrowseButton(
                        fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor()
                            .withRoots(ProjectRootManager.getInstance(project).contentRoots.first()),
                        project = project
                    ).bindText({
                        filePath
                    }, {
                        filePath = it
                        filePathLabel.text = it
                    })
                }
                row(PluginBundle.get("file.name")) {
                    textField().bindText({ fileName }, {
                        fileName = it
                    })
                }
                row {
                    checkBox("${PluginBundle.get("automatic.operation.command")} flutter pub run build_runner build").bindSelected(
                        { setting.autoRunDartBuilder },
                        { v ->
                            settingInstance.changeState { it.copy(autoRunDartBuilder = v) }
                        })
                }
            }
        }
        return settingPanel
    }


    override fun doOKAction() {
        settingPanel.apply()

        ///保存此路径
        settingInstance.changeState { it.copy(generateToPath = filePath) }

        val psiFile =
            PsiFileFactory.getInstance(project)
                .createFileFromText("$fileName.dart", DartLanguage.INSTANCE, generateFileText())
        val virtualFile = filePath.getVirtualFile()
        if (virtualFile == null) {
            project.toastWithError(PluginBundle.get("unable.to.find.directory"))
        } else {
            val findDirectory = PsiManager.getInstance(project).findDirectory(virtualFile)
            if (findDirectory == null) {
                project.toastWithError(PluginBundle.get("unable.to.find.directory"))
            }
            findDirectory?.let { pd ->
                runWriteAction {
                    pd.add(psiFile)
                }
                project.toast(PluginBundle.get("build.succeeded"))
                if (setting.autoRunDartBuilder) {
                    TerminalView.getInstance(project)
                        .createLocalShellWidget(project.basePath, "freezed gen")
                        .executeCommand("flutter pub run build_runner build")
                }
                FileBasedIndex.getInstance().requestReindex(virtualFile)
                super.doOKAction()
            }
        }
    }

    override fun doCancelAction() {
        settingPanel.apply()
        ///保存路径
        settingInstance.changeState { it.copy(generateToPath = filePath) }
        super.doCancelAction()
    }

    private fun generateFileText(): String {
        val sb = StringBuilder()
        sb.appendLine("import 'package:freezed_annotation/freezed_annotation.dart';")
        sb.appendLine()
        sb.appendLine("part '$fileName.freezed.dart';")
        sb.appendLine("part '$fileName.g.dart';")
        sb.appendLine()
        widgets.forEach {
            sb.appendLine()
            sb.appendLine(it.code)
            sb.appendLine()
        }
        return sb.toString()
    }


    override fun getPreferredSize(): Dimension {
        return ScreenUtil.dimension
    }
}