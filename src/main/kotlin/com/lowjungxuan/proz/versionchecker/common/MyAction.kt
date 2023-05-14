package com.lowjungxuan.proz.versionchecker.common

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.actionSystem.ex.ComboBoxAction
import com.intellij.openapi.project.DumbAwareAction
import java.util.function.Supplier
import javax.swing.Icon

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

abstract class MyToggleAction(name: Supplier<String>) : ToggleAction(name) {

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }
}

abstract class MyComboBoxAction : ComboBoxAction() {
    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

}