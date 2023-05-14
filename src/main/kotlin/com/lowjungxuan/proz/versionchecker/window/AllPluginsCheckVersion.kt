package com.lowjungxuan.proz.versionchecker.window

import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.lowjungxuan.proz.versionchecker.model.FlutterPluginElementModel
import com.lowjungxuan.proz.versionchecker.model.FlutterPluginType
import com.lowjungxuan.proz.versionchecker.model.getElementVersion
import com.lowjungxuan.proz.versionchecker.model.isLastVersion
import com.lowjungxuan.proz.versionchecker.services.PubService
import com.lowjungxuan.proz.versionchecker.services.ServiceCreate
import com.lowjungxuan.proz.versionchecker.util.MyPsiElementUtil
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.*

///检测版本小窗口
class AllPluginsCheckVersion(val project: Project) : JPanel(BorderLayout()) {

    private var topTipLabel = JBLabel("check")
    private var bottomTipLabel = JBLabel("loading...")

    //插件列表
    private var plugins: MutableMap<FlutterPluginType, List<FlutterPluginElementModel>> =
        MyPsiElementUtil.getAllFlutters(project)

    //展示列表组件
    private val listView = JBList<FlutterPluginElementModel>()


    init {
        add(topTipLabel, BorderLayout.NORTH)
        add(JBScrollPane(listView), BorderLayout.CENTER)
        add(bottomTipLabel, BorderLayout.SOUTH)
        listView.model = PluginListModel(emptyList())
        listView.cellRenderer = PluginListCellRender()
        initRequest()
    }

    /**
     * 开始检测插件新版本
     * 需要访问网络
     */
    @OptIn(DelicateCoroutinesApi::class)
    private fun initRequest() {
        topTipLabel.text = "loading..."
        GlobalScope.launch {
            for (item in plugins.values) {
                bottomTipLabel.text = "all size:${item.size}"
                item.forEach { model ->
                    bottomTipLabel.text = "checking: ${model.name}"
                    val r = ServiceCreate.create<PubService>().callPluginDetails(model.name).execute()
                    if (r.isSuccessful) {

                        val rModel = r.body()
                        if (rModel != null) {
                            model.pubData = rModel
                            if (!model.isLastVersion()) {
                                val oldList = (listView.model as PluginListModel).list
                                val l = oldList.toMutableList()
                                l.add(model)
                                listView.model = PluginListModel(l)
                            }

                        }
                    }
                }
                bottomTipLabel.text = "Done"
            }
        }
    }


}


class PluginListModel(val list: List<FlutterPluginElementModel>) : AbstractListModel<FlutterPluginElementModel>() {
    override fun getSize(): Int {
        return list.size
    }

    override fun getElementAt(index: Int): FlutterPluginElementModel {
        return list[index]
    }
}

class PluginListCellRender : ListCellRenderer<FlutterPluginElementModel> {
    override fun getListCellRendererComponent(
        list: JList<out FlutterPluginElementModel>?,
        value: FlutterPluginElementModel?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        val box = Box.createVerticalBox()
        val titleLabel = JBLabel("${value!!.getElementVersion()} --> ${value.pubData?.latest?.version}")
        val padding = JPanel()
        padding.border = BorderFactory.createTitledBorder(value.name)
        box.border = BorderFactory.createEmptyBorder(0, 0, 10, 0)
        padding.add(titleLabel)
        box.add(padding)
        return box
    }

}