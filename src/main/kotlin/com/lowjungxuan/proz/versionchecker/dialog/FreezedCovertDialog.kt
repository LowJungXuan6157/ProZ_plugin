package com.lowjungxuan.proz.versionchecker.dialog

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.LanguageTextField
import com.intellij.ui.components.JBScrollPane
import com.jetbrains.lang.dart.DartLanguage
import com.lowjungxuan.proz.versionchecker.model.FreezedCovertModel
import com.lowjungxuan.proz.versionchecker.model.getPropertiesString
import com.lowjungxuan.proz.versionchecker.util.MyDartPsiElementUtil
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel

class FreezedCovertDialog(val project: Project, val model: FreezedCovertModel) : DialogWrapper(project) {

    data class GenProperties(
        var genFromJson: Boolean = false,
        var genPartOf: Boolean = false,
        var funsToExt: Boolean = false,
        var className: String = ""
    )

    val setting = GenProperties().apply {
        className = model.className
    }

    private val editView = LanguageTextField(
        DartLanguage.INSTANCE,
        project,
        "",
        false
    ).apply {
        minimumSize = Dimension(400,400)
    }

    init {
        super.init()
        title = "模型转Freezed对象(${model.className})"
        generateFreezedModel()
    }

    override fun createCenterPanel(): JComponent {


        return object : JPanel(BorderLayout()) {
            init {
                add(JBScrollPane(editView), BorderLayout.CENTER)
            }
        }
    }

    private fun generateFreezedModel() {
        val genFreezedClass =
            MyDartPsiElementUtil.genFreezedClass(project, model.className, model.getPropertiesString())
        editView.text = genFreezedClass.text
    }


}