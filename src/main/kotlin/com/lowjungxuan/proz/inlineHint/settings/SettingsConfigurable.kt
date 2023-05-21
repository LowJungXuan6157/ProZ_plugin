package com.lowjungxuan.proz.inlineHint.settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class SettingsConfigurable : Configurable {
    override fun getDisplayName() = "InlineProblems"
    override fun getPreferredFocusedComponent(): JComponent? {
        return super.getPreferredFocusedComponent()
    }

    override fun createComponent(): JComponent? {
        TODO("Not yet implemented")
    }

    override fun isModified(): Boolean {
        TODO("Not yet implemented")
    }

    override fun apply() {
        TODO("Not yet implemented")
    }

    override fun reset() {
        super.reset()
    }

    override fun disposeUIResources() {
        super.disposeUIResources()
    }
}