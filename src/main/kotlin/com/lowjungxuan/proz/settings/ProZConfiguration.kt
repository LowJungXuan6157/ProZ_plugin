package com.lowjungxuan.proz.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.options.Configurable
import java.awt.BorderLayout
import javax.swing.*

class ProZConfiguration : Configurable {

    private val checkboxes = listOf(
        JCheckBox("GetX"),
        JCheckBox("Bloc")
    )

    override fun createComponent(): JComponent {
        val settings = getSettings()
        val panel = JPanel(BorderLayout()) // Change layout to BorderLayout
        val wrappingPanel = JPanel()
        wrappingPanel.layout = BoxLayout(wrappingPanel, BoxLayout.Y_AXIS) // Set layout for wrappingPanel
        wrappingPanel.border = BorderFactory.createTitledBorder("Code Wrapping (⌥ + ⏎)")
        for (checkbox in checkboxes) {
            checkbox.isSelected = checkbox.text in settings.enabledCategories
            wrappingPanel.add(checkbox)
        }
        panel.add(wrappingPanel, BorderLayout.NORTH) // Add wrappingPanel to the north
        return panel
    }


    override fun isModified(): Boolean {
        val settings = getSettings()
        for (checkbox in checkboxes) {
            if (checkbox.isSelected != (checkbox.text in settings.enabledCategories)) {
                return true
            }
        }
        return false
    }

    override fun apply() {
        val settings = getSettings()
        settings.enabledCategories.clear()
        for (checkbox in checkboxes) {
            if (checkbox.isSelected) {
                settings.enabledCategories.add(checkbox.text)
            }
        }
        println(settings.enabledCategories)
    }

    override fun getDisplayName(): String {
        return "ProZ"
    }

    private fun getSettings(): ProZSetting.State {
        val settings = ApplicationManager.getApplication().getService(ProZSetting::class.java)
        return settings.state
    }
}
