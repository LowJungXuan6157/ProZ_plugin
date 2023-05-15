package com.lowjungxuan.proz.versionchecker.settings

import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.FormBuilder
import com.lowjungxuan.proz.versionchecker.resources.Strings
import javax.swing.JComponent
import javax.swing.JPanel


class AppSettingsComponent {
    val panel: JPanel
    private val includePreReleasesCheckBox = JBCheckBox(Strings.settingsIncludePreReleasesTitle)

    init {
        panel = FormBuilder.createFormBuilder()
            .addComponent(includePreReleasesCheckBox, 1)
            .addTooltip(Strings.settingsIncludePreReleasesTooltip)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    val preferredFocusedComponent: JComponent
        get() = includePreReleasesCheckBox

    var includePreReleases: Boolean
        get() = includePreReleasesCheckBox.isSelected
        set(newStatus) {
            includePreReleasesCheckBox.isSelected = newStatus
        }
}