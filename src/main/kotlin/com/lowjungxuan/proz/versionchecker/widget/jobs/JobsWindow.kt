package com.lowjungxuan.proz.versionchecker.widget.jobs

import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBPanel
import java.awt.GridBagConstraints
import java.awt.GridBagLayout

class JobsWindow(val project: Project) : JBPanel<JobsWindow>(GridBagLayout()) {

    private val cityList = CityListView(project) {
        postListWidget.changeListWithCategoryId(it.id.toLong())
    }
    private val postListWidget = PostListWidget(project)
    private val commentListWidget = CommentListWidget()


    init {
        GridBagConstraints().apply {
            fill = GridBagConstraints.BOTH
            gridx = 0
            weighty = 1.0
            weightx = 0.1
            gridwidth = 1
            add(cityList, this)
            gridx = 1
            weightx = 0.7
            add(postListWidget, this)
            gridx = 2
            weightx = 0.2
            add(commentListWidget, this)
        }
    }


}