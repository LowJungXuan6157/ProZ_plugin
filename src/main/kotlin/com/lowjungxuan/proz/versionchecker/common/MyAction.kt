package com.lowjungxuan.proz.versionchecker.common

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.actionSystem.ex.ComboBoxAction
import com.intellij.openapi.project.DumbAwareAction
import com.lowjungxuan.proz.versionchecker.services.ItbugService
import com.lowjungxuan.proz.versionchecker.services.SERVICE
import java.util.function.Supplier
import javax.swing.Icon
import javax.swing.JComponent

abstract class MyAction : AnAction {

    constructor()
    constructor(name: Supplier<String>) : super(name)
    constructor(icon: Icon) : super(icon)

    constructor(name: Supplier<String>, icon: Icon) : super(name, icon)
    constructor(title: String, desc: String, icon: Icon) : super(title, desc, icon)

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}

abstract class MyDumbAwareAction : DumbAwareAction {
    constructor()

    constructor(name: Supplier<String>) : super(name)
    constructor(icon: Icon) : super(icon)

    constructor(title: String, desc: String, icon: Icon) : super(title, desc, icon)

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}

abstract class MyToggleAction : ToggleAction {

    constructor()
    constructor(name: Supplier<String>) : super(name)

    constructor(name: String, desc: String, icon: Icon) : super(name, desc, icon)

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}

abstract class MyComboBoxAction : ComboBoxAction() {
    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

}