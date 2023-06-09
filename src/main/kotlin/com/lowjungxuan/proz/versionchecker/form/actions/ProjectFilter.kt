package com.lowjungxuan.proz.versionchecker.form.actions

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Condition
import com.intellij.util.ModalityUiUtil
import com.lowjungxuan.proz.versionchecker.bus.ProjectListChangeBus
import com.lowjungxuan.proz.versionchecker.common.MyAction
import com.lowjungxuan.proz.versionchecker.common.MyComboBoxAction
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle
import com.lowjungxuan.proz.versionchecker.icons.MyIcons
import com.lowjungxuan.proz.versionchecker.socket.service.AppService
import com.lowjungxuan.proz.versionchecker.util.projectClosed
import java.util.*
import javax.swing.JComponent


/**
 * 过滤项目
 * 因为可能会多开多个项目,所以要支持过滤
 * 当然socket也根据项目分离Request请求
 */
class ProjectFilter : MyComboBoxAction(), DumbAware {


    private val actions = mutableListOf<ProjectAnAction>()
    private var projectNames = emptyList<String>()
    private var ideaProject: MutableList<Project> = Collections.synchronizedList(mutableListOf())
    private val appService = service<AppService>()

    private fun createDefaultGroup(): DefaultActionGroup {
        val group = DefaultActionGroup()
        group.addAll(actions)
        return group
    }

    override fun createPopupActionGroup(button: JComponent, dataContext: DataContext): DefaultActionGroup {
        return createDefaultGroup()
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.project?.apply {
            if (!ideaProject.contains(this)) {
                ideaProject.add(this)
                val changeNameRunnable = Runnable {}
                appService.addListening(changeNameRunnable)
                this.projectClosed {
                    appService.removeListening(changeNameRunnable)
                }
                ProjectListChangeBus.lisening {
                    projectNames = it
                    changeProjectNameAction()
                }

            }
        }
        doUpdate(e)
    }

    override fun createPopupActionGroup(p0: JComponent?): DefaultActionGroup {
        return createDefaultGroup()
    }


    private fun changeProjectNameAction() {
        actions.clear()
        actions.addAll(projectNames.map { ProjectAnAction(it) })
    }

    private fun doUpdate(e: AnActionEvent) {
        ModalityUiUtil.invokeLaterIfNeeded(ModalityState.defaultModalityState()) {
            updateSelect(e)
        }
    }


    //更新选中
    private fun updateSelect(e: AnActionEvent) {
        if (actions.isEmpty()) {
            e.presentation.isEnabled = false
            e.presentation.text = PluginBundle.get("empty")
            e.presentation.icon = MyIcons.flutter
        } else {
            e.presentation.isEnabled = true
        }

        val appName = appService.currentSelectName.get()
        if (appName != null) {
            changeText(e.presentation, appName)
        }
        if (appName == null && actions.size == 1) {
            appService.changeCurrentSelectFlutterProjectName(actions[0].getText())
        }

        //主要是解决新开项目后选项会被禁用的问题
        if (appName != null && actions.isEmpty()) {
            projectNames = appService.projectNames
            changeProjectNameAction()
        }
    }

    private fun changeText(presentation: Presentation, name: String) {
        presentation.text = name
        presentation.icon = MyIcons.flutter
    }

    override fun getPreselectCondition(): Condition<AnAction> {
        return Condition<AnAction> { t -> t is ProjectAnAction && t.getText() == appService.currentSelectName.get() }
    }


}

/**
 * 项目选择操作
 */
class ProjectAnAction(private val projectName: String) : MyAction({ projectName }, MyIcons.flutter) {
    override fun actionPerformed(e: AnActionEvent) {
        service<AppService>().changeCurrentSelectFlutterProjectName(projectName)
    }

    fun getText() = projectName
}