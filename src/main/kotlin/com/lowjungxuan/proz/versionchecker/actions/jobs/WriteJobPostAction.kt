package com.lowjungxuan.proz.versionchecker.actions.jobs

import com.intellij.openapi.actionSystem.AnActionEvent
import com.lowjungxuan.proz.versionchecker.common.MyAction
import com.lowjungxuan.proz.versionchecker.dialog.jobs.AddJobsDialog

class WriteJobPostAction: MyAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let{
            AddJobsDialog(it).show()
        }
    }
}