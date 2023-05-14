package com.lowjungxuan.proz.versionchecker.actions.jobs

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.lowjungxuan.proz.versionchecker.common.MyAction
import com.lowjungxuan.proz.versionchecker.services.ItbugService
import com.lowjungxuan.proz.versionchecker.services.JSONResult
import com.lowjungxuan.proz.versionchecker.services.SERVICE
import com.lowjungxuan.proz.versionchecker.services.params.AddCityApiModel
import com.lowjungxuan.proz.versionchecker.util.toast
import com.lowjungxuan.proz.versionchecker.util.toastWithError
import com.lowjungxuan.proz.versionchecker.widget.WidgetUtil

/**
 * 添加城市操作
 */
class JobsCityAddAction: MyAction() {

    override fun actionPerformed(e: AnActionEvent) {

        WidgetUtil.getTextEditorPopup("请输入城市名","例如:广州",{it.showInFocusCenter()}){
            addNewCity(it,e.project!!)
        }
    }

    /**
     * 请求api
     */
    private fun addNewCity(name: String,project: Project) {
        SERVICE.create<ItbugService>().addNewJobsCity(AddCityApiModel(name = name))
            .enqueue(object : Callback<JSONResult<Any>> {
                override fun onResponse(call: Call<JSONResult<Any>>, response: Response<JSONResult<Any>>) {
                    project.toast(response.body()?.message ?: response.message())
                }
                override fun onFailure(call: Call<JSONResult<Any>>, t: Throwable) {
                    project.toastWithError("添加失败")
                }
            })
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = e.project!=null
        super.update(e)
    }

}