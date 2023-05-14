package com.lowjungxuan.proz.versionchecker.actions

import cn.hutool.core.util.URLUtil
import com.intellij.openapi.actionSystem.AnActionEvent
import com.lowjungxuan.proz.versionchecker.common.MyAction
import com.lowjungxuan.proz.versionchecker.document.copyTextToClipboard
import com.lowjungxuan.proz.versionchecker.util.toast


///复制路径
class ApiCopyPathAction: MyAction({"Copy Path"}) {
    override fun actionPerformed(e: AnActionEvent) {
        val url = e.api()!!.url
        val path = URLUtil.getPath(url)

        path.copyTextToClipboard().apply {
            e.apiListProject()?.toast("Copy succeeded!")
        }
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = e.api()!=null
        super.update(e)
    }

}