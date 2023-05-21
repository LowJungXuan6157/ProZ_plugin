package com.lowjungxuan.proz.inlineHint.listeners

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

class ProjectCloseListener : ProjectManagerListener {
    override fun projectClosing(project: Project) {
        super.projectClosing(project)
    }
}