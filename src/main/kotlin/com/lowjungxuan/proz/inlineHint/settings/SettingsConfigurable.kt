package com.lowjungxuan.proz.inlineHint.settings

import com.intellij.openapi.options.Configurable
import com.lowjungxuan.proz.inlineHint.DocumentMarkupModelScanner
import com.lowjungxuan.proz.inlineHint.entities.enums.Listener
import org.overengineer.inlineproblems.settings.SettingsComponent
import javax.swing.JComponent

class SettingsConfigurable : Configurable {
    private var settingsComponent: SettingsComponent = SettingsComponent()
    override fun getDisplayName() = "InlineProblems"
    override fun getPreferredFocusedComponent(): JComponent {
        return settingsComponent.preferredFocusedComponent
    }

    override fun createComponent(): JComponent? {
        settingsComponent = SettingsComponent()
        return settingsComponent.getSettingsPanel()
    }

    override fun isModified(): Boolean {
        val state: SettingsState = SettingsState.instance

        val oldStateEqualsNewState = state.forceProblemsInSameLine == settingsComponent.isForceErrorsInSameLine() &&
                state.drawBoxesAroundErrorLabels == settingsComponent.getDrawBoxesAroundProblemLabels() &&
                state.roundedCornerBoxes == settingsComponent.isRoundedCornerBoxes() &&
                state.useEditorFont == settingsComponent.isUseEditorFont() &&
                state.showOnlyHighestSeverityPerLine == settingsComponent.isShowOnlyHighestSeverityPerLine() &&
                state.inlayFontSizeDelta == settingsComponent.getInlayFontSizeDelta() &&
                state.fillProblemLabels == settingsComponent.isFillProblemLabels() &&
                state.boldProblemLabels == settingsComponent.isBoldProblemLabels() &&
                state.italicProblemLabels == settingsComponent.isItalicProblemLabels() &&
                state.getErrorTextColor() == settingsComponent.getErrorTextColor() &&
                state.getErrorBackgroundColor() == settingsComponent.getErrorLabelBackgroundColor() &&
                state.getErrorHighlightColor() == settingsComponent.getErrorHighlightColor() &&
                state.showErrors == settingsComponent.isShowErrors() &&
                state.highlightErrors == settingsComponent.isHighlightErrors() &&
                state.getWarningTextColor() == settingsComponent.getWarningTextColor() &&
                state.getWarningBackgroundColor() == settingsComponent.getWarningLabelBackgroundColor() &&
                state.getWarningHighlightColor() == settingsComponent.getWarningHighlightColor() &&
                state.showWarnings == settingsComponent.isShowWarnings() &&
                state.highlightWarnings == settingsComponent.isHighlightWarnings() &&
                state.getWeakWarningTextColor() == settingsComponent.getWeakWarningTextColor() &&
                state.getWeakWarningBackgroundColor() == settingsComponent.getWeakWarningLabelBackgroundColor() &&
                state.getWeakWarningHighlightColor() == settingsComponent.getWeakWarningHighlightColor() &&
                state.showWeakWarnings == settingsComponent.isShowWeakWarnings() &&
                state.highlightWeakWarnings == settingsComponent.isHighlightWeakWarnings() &&
                state.getInfoTextColor() == settingsComponent.getInfoTextColor() &&
                state.getInfoBackgroundColor() == settingsComponent.getInfoLabelBackgroundColor() &&
                state.getInfoHighlightColor() == settingsComponent.getInfoHighlightColor() &&
                state.showInfos == settingsComponent.isShowInfos() &&
                state.highlightInfos == settingsComponent.isHighlightInfo() &&
                state.enabledListener == settingsComponent.getEnabledListener() &&
                state.manualScannerDelay == settingsComponent.getManualScannerDelay() &&
                state.problemFilterList == settingsComponent.getProblemFilterList() &&
                state.getAdditionalInfoSeveritiesAsString().equals(settingsComponent.getAdditionalInfoSeverities()) &&
                state.getAdditionalWarningSeveritiesAsString().equals(settingsComponent.getAdditionalWarningSeverities()) &&
                state.getAdditionalWeakWarningSeveritiesAsString().equals(settingsComponent.getAdditionalWeakWarningSeverities()) &&
                state.getAdditionalErrorSeveritiesAsString().equals(settingsComponent.getAdditionalErrorSeverities())
        return !oldStateEqualsNewState
    }

    override fun apply() {
        val state: SettingsState = SettingsState.instance

        val listenerChanged = state.enabledListener != settingsComponent.getEnabledListener()
        val manualScannerDelayChanged = state.manualScannerDelay != settingsComponent.getManualScannerDelay()

        state.setShowErrors(settingsComponent.isShowErrors())
        state.setHighlightErrors(settingsComponent.isHighlightErrors())
        state.setErrorTextColor(settingsComponent.getErrorTextColor())
        state.setErrorBackgroundColor(settingsComponent.getErrorLabelBackgroundColor())
        state.setErrorHighlightColor(settingsComponent.getErrorHighlightColor())

        state.setShowWarnings(settingsComponent.isShowWarnings())
        state.setHighlightWarnings(settingsComponent.isHighlightWarnings())
        state.setWarningTextColor(settingsComponent.getWarningTextColor())
        state.setWarningBackgroundColor(settingsComponent.getWarningLabelBackgroundColor())
        state.setWarningHighlightColor(settingsComponent.getWarningHighlightColor())

        state.setShowWeakWarnings(settingsComponent.isShowWeakWarnings())
        state.setHighlightWeakWarnings(settingsComponent.isHighlightWeakWarnings())
        state.setWeakWarningTextColor(settingsComponent.getWeakWarningTextColor())
        state.setWeakWarningBackgroundColor(settingsComponent.getWeakWarningLabelBackgroundColor())
        state.setWeakWarningHighlightColor(settingsComponent.getWeakWarningHighlightColor())

        state.setShowInfos(settingsComponent.isShowInfos())
        state.setHighlightInfos(settingsComponent.isHighlightInfo())
        state.setInfoTextColor(settingsComponent.getInfoTextColor())
        state.setInfoBackgroundColor(settingsComponent.getInfoLabelBackgroundColor())
        state.setInfoHighlightColor(settingsComponent.getInfoHighlightColor())

        state.setForceProblemsInSameLine(settingsComponent.isForceErrorsInSameLine())
        state.setDrawBoxesAroundErrorLabels(settingsComponent.getDrawBoxesAroundProblemLabels())
        state.setRoundedCornerBoxes(settingsComponent.isRoundedCornerBoxes())
        state.setUseEditorFont(settingsComponent.isUseEditorFont())
        state.setShowOnlyHighestSeverityPerLine(settingsComponent.isShowOnlyHighestSeverityPerLine())
        state.setInlayFontSizeDelta(settingsComponent.getInlayFontSizeDelta())
        state.setFillProblemLabels(settingsComponent.isFillProblemLabels())
        state.setBoldProblemLabels(settingsComponent.isBoldProblemLabels())
        state.setItalicProblemLabels(settingsComponent.isItalicProblemLabels())

        state.setEnabledListener(settingsComponent.getEnabledListener())
        state.setManualScannerDelay(settingsComponent.getManualScannerDelay())
        state.setProblemFilterList(settingsComponent.getProblemFilterList())

        state.setAdditionalInfoSeverities(settingsComponent.additionalInfoSeveritiesList)
        state.setAdditionalWarningSeverities(settingsComponent.additionalWarningSeveritiesList)
        state.setAdditionalWeakWarningSeverities(settingsComponent.additionalWeakWarningSeveritiesList)
        state.setAdditionalErrorSeverities(settingsComponent.additionalErrorSeveritiesList)

        if (manualScannerDelayChanged && state.enabledListener == Listener.MANUAL_SCANNING) {
            DocumentMarkupModelScanner.instance.setDelayMilliseconds(state.getManualScannerDelay())
        }

        listenerManager.resetAndRescan()

        if (listenerChanged) {
            listenerManager.changeListener()
        }
    }

    override fun reset() {
        super.reset()
    }

    override fun disposeUIResources() {
//        settingsComponent = null
    }
}