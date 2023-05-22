package org.overengineer.inlineproblems.settings

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.ColorPanel
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.lowjungxuan.proz.inlineHint.DocumentMarkupModelScanner
import com.lowjungxuan.proz.inlineHint.listeners.HighlightProblemListener
import com.lowjungxuan.proz.inlineHint.listeners.MarkupModelProblemListener
import com.lowjungxuan.proz.inlineHint.settings.SettingsState
//import lombok.Getter
//import org.overengineer.inlineproblems.DocumentMarkupModelScanner
//import org.overengineer.inlineproblems.listeners.MarkupModelProblemListener
import java.awt.Color
import java.text.NumberFormat
import java.util.*
import java.util.stream.Collectors
import javax.swing.*
import javax.swing.text.NumberFormatter

class SettingsComponent {
    private val showErrors = JBCheckBox("Show errors")
    private val highlightErrors = JBCheckBox("Highlight errors")
    private val showWarnings = JBCheckBox("Show warnings")
    private val highlightWarnings = JBCheckBox("Highlight warnings")
    private val showWeakWarnings = JBCheckBox("Show weak warnings")
    private val highlightWeakWarnings = JBCheckBox("Highlight weak warnings")
    private val showInfos = JBCheckBox("Show infos")
    private val highlightInfo = JBCheckBox("Highlight infos")
    private val errorTextColor = ColorPanel()
    private val errorLabelBackgroundColor = ColorPanel()
    private val errorHighlightColor = ColorPanel()
    private val warningTextColor = ColorPanel()
    private val warningLabelBackgroundColor = ColorPanel()
    private val warningHighlightColor = ColorPanel()
    private val weakWarningTextColor = ColorPanel()
    private val weakWarningLabelBackgroundColor = ColorPanel()
    private val weakWarningHighlightColor = ColorPanel()
    private val infoTextColor = ColorPanel()
    private val infoLabelBackgroundColor = ColorPanel()
    private val infoHighlightColor = ColorPanel()
    private val forceErrorsInSameLine = JBCheckBox("Force problems in the same line even if they are to long to fit")
    private val drawBoxesAroundProblemLabels = JBCheckBox("Draw boxes around problem labels")
    private val roundedCornerBoxes = JBCheckBox("Rounded corners")
    private val useEditorFont = JBCheckBox("Use editor font instead of tooltip font")
    private val showOnlyHighestSeverityPerLine = JBCheckBox("Show only the problem with the highest severity per line")
    private val inlayFontSizeDeltaText: JFormattedTextField
    private val manualScannerDelay: JFormattedTextField
    private val fillProblemLabels = JBCheckBox("Fill problem label background")
    private val boldProblemLabels = JBCheckBox("Bold problem labels")
    private val italicProblemLabels = JBCheckBox("Italic problem labels")
    private val problemFilterList = JBTextField("Problem text beginning filter")
    private val availableListeners = arrayOf(HighlightProblemListener.NAME, MarkupModelProblemListener.NAME, DocumentMarkupModelScanner.NAME)
    private val enabledListener: JComboBox<String> = ComboBox(availableListeners)
    private val additionalInfoSeverities = JBTextField()
    private val additionalWarningSeverities = JBTextField()
    private val additionalWeakWarningSeverities = JBTextField()
    private val additionalErrorSeverities = JBTextField()

    private val settingsPanel: JPanel

    init {
        val settingsState: SettingsState = SettingsState.instance
        showErrors.isSelected = settingsState.showErrors
        highlightErrors.isSelected = settingsState.highlightErrors
        errorTextColor.selectedColor = settingsState.getErrorTextColor()
        errorLabelBackgroundColor.selectedColor = settingsState.getErrorBackgroundColor()
        errorHighlightColor.selectedColor = settingsState.getErrorHighlightColor()

        showWarnings.isSelected = settingsState.showWarnings
        highlightWarnings.isSelected = settingsState.highlightWarnings
        warningTextColor.selectedColor = settingsState.getWarningTextColor()
        warningLabelBackgroundColor.selectedColor = settingsState.getWarningBackgroundColor()
        warningHighlightColor.selectedColor = settingsState.getWarningHighlightColor()

        showWeakWarnings.isSelected = settingsState.showWeakWarnings
        highlightWeakWarnings.isSelected = settingsState.highlightWeakWarnings
        weakWarningTextColor.selectedColor = settingsState.getWeakWarningTextColor()
        weakWarningLabelBackgroundColor.selectedColor = settingsState.getWeakWarningBackgroundColor()
        weakWarningHighlightColor.selectedColor = settingsState.getWeakWarningHighlightColor()

        showInfos.isSelected = settingsState.showInfos
        highlightInfo.isSelected = settingsState.highlightInfos
        infoTextColor.selectedColor = settingsState.getInfoTextColor()
        infoLabelBackgroundColor.selectedColor = settingsState.getInfoBackgroundColor()
        infoHighlightColor.selectedColor = settingsState.getInfoHighlightColor()

        forceErrorsInSameLine.isSelected = settingsState.forceProblemsInSameLine
        drawBoxesAroundProblemLabels.isSelected = settingsState.drawBoxesAroundErrorLabels
        roundedCornerBoxes.isSelected = settingsState.roundedCornerBoxes
        useEditorFont.isSelected = settingsState.useEditorFont
        val intFormat = NumberFormat.getIntegerInstance()
        intFormat.isGroupingUsed = false
        val numberFormatter = NumberFormatter(intFormat)
        numberFormatter.valueClass = Int::class.java // Optional, ensures we always get a int value
        inlayFontSizeDeltaText = JFormattedTextField(numberFormatter)
        inlayFontSizeDeltaText.text = settingsState.inlayFontSizeDelta.toString()
        manualScannerDelay = JFormattedTextField(numberFormatter)
        manualScannerDelay.text = settingsState.manualScannerDelay.toString()
        fillProblemLabels.isSelected = settingsState.fillProblemLabels
        boldProblemLabels.isSelected = settingsState.boldProblemLabels
        italicProblemLabels.isSelected = settingsState.italicProblemLabels
        additionalInfoSeverities.text = settingsState.getAdditionalInfoSeveritiesAsString()
        additionalWeakWarningSeverities.text = settingsState.getAdditionalWeakWarningSeveritiesAsString()
        additionalWarningSeverities.text = settingsState.getAdditionalWarningSeveritiesAsString()
        additionalErrorSeverities.text = settingsState.getAdditionalErrorSeveritiesAsString()
        problemFilterList.text = settingsState.problemFilterList
        enabledListener.selectedItem = Optional.of(settingsState.enabledListener)
        val enabledListenerDimension = enabledListener.preferredSize
        enabledListenerDimension.width += 100
        enabledListener.preferredSize = enabledListenerDimension
        settingsPanel = FormBuilder.createFormBuilder()
            .addComponent(JBLabel("Box / Label"))
            .addComponent(drawBoxesAroundProblemLabels, 0)
            .addComponent(roundedCornerBoxes, 0)
            .addComponent(fillProblemLabels, 0)
            .addComponent(boldProblemLabels, 0)
            .addComponent(italicProblemLabels, 0)
            .addSeparator()
            .addComponent(JBLabel("General"))
            .addLabeledComponent(JBLabel("Enabled problem listener"), enabledListener)
            .addTooltip("- MarkupModelListener: Called after addition of a RangeHighlighter to a file. Faster on large files, slower on small ones")
            .addTooltip("- ManualScanner: Scans the DocumentMarkupModel for all highlighters at a fixed delay")
            .addTooltip("- HighlightProblemListener (DEPRECATED): Faster on small to medium sized files, slower on large ones.")
            .addTooltip("   Called way to often and can cause freezes. If you want similar performance use the ManualScanner instead")
            .addLabeledComponent(JBLabel("ManualScanner delay in milliseconds"), manualScannerDelay)
            .addTooltip("Delay between manual scans, only used when ManualScanner is enabled")
            .addComponent(forceErrorsInSameLine, 0)
            .addComponent(useEditorFont, 0)
            .addComponent(showOnlyHighestSeverityPerLine, 0)
            .addLabeledComponent(JBLabel("Inlay size delta"), inlayFontSizeDeltaText)
            .addTooltip("Used to have smaller font size for the inlays, should be smaller than editor font size")
            .addLabeledComponent(JLabel("Problem filter list"), problemFilterList)
            .addTooltip("Semicolon separated list of problem text beginnings that will not be handled")
            .addSeparator()
            .addComponent(JBLabel("Colors"))
            .addComponent(showErrors)
            .addComponent(highlightErrors)
            .addLabeledComponent(JLabel("Error text color:"), errorTextColor)
            .addLabeledComponent(JLabel("Error label border color:"), errorLabelBackgroundColor)
            .addLabeledComponent(JLabel("Error line highlight color:"), errorHighlightColor)
            .addLabeledComponent(JLabel("Additional severities:"), additionalErrorSeverities)
            .addTooltip("Semicolon separated list of additional error severities e.g. '10, 100'")
            .addSeparator()
            .addComponent(showWarnings)
            .addComponent(highlightWarnings)
            .addLabeledComponent(JLabel("Warning text color:"), warningTextColor)
            .addLabeledComponent(JLabel("Warning label border color:"), warningLabelBackgroundColor)
            .addLabeledComponent(JLabel("Warning line highlight color:"), warningHighlightColor)
            .addLabeledComponent(JLabel("Additional severities:"), additionalWarningSeverities)
            .addTooltip("Semicolon separated list of additional warning severities e.g. '10, 100'")
            .addSeparator()
            .addComponent(showWeakWarnings)
            .addComponent(highlightWeakWarnings)
            .addLabeledComponent(JLabel("Weak warning text color:"), weakWarningTextColor)
            .addLabeledComponent(JLabel("Weak warning label border color:"), weakWarningLabelBackgroundColor)
            .addLabeledComponent(JLabel("Weak warning line highlight color:"), weakWarningHighlightColor)
            .addLabeledComponent(JLabel("Additional severities:"), additionalWeakWarningSeverities)
            .addTooltip("Semicolon separated list of additional weak-warning severities e.g. '10, 100'")
            .addSeparator()
            .addComponent(showInfos)
            .addComponent(highlightInfo)
            .addLabeledComponent(JLabel("Info text color:"), infoTextColor)
            .addLabeledComponent(JLabel("Info label border color:"), infoLabelBackgroundColor)
            .addLabeledComponent(JLabel("Info line highlight color:"), infoHighlightColor)
            .addLabeledComponent(JLabel("Additional severities:"), additionalInfoSeverities)
            .addTooltip("Semicolon separated list of additional info severities e.g. '10, 100'")
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    val preferredFocusedComponent: JComponent
        get() = settingsPanel

    fun isForceErrorsInSameLine(): Boolean {
        return forceErrorsInSameLine.isSelected
    }

    fun setForceErrorsInSameLine(isSelected: Boolean) {
        forceErrorsInSameLine.isSelected = isSelected
    }

    fun getDrawBoxesAroundProblemLabels(): Boolean {
        return drawBoxesAroundProblemLabels.isSelected
    }

    fun setDrawBoxesAroundProblemLabels(isSelected: Boolean) {
        drawBoxesAroundProblemLabels.isSelected = isSelected
    }

    fun isRoundedCornerBoxes(): Boolean {
        return roundedCornerBoxes.isSelected
    }

    fun setRoundedCornerBoxes(isSelected: Boolean) {
        roundedCornerBoxes.isSelected = isSelected
    }

    fun isUseEditorFont(): Boolean {
        return useEditorFont.isSelected
    }

    fun isShowOnlyHighestSeverityPerLine(): Boolean {
        return showOnlyHighestSeverityPerLine.isSelected
    }

    fun getInlayFontSizeDelta(): Int {
        var value = 0
        // Convert the String
        try {
            value = inlayFontSizeDeltaText.text.toInt()
        } catch (ignored: NumberFormatException) {
        }

        if (value < 0) {
            value = 0
        }
        return value
    }


    fun setUseEditorFont(isSelected: Boolean) {
        useEditorFont.isSelected = isSelected
    }

    fun setShowOnlyHighestSeverityPerLine(isSelected: Boolean) {
        showOnlyHighestSeverityPerLine.isSelected = isSelected
    }

    fun isFillProblemLabels(): Boolean {
        return fillProblemLabels.isSelected
    }

    fun setFillProblemLabels(isSelected: Boolean) {
        fillProblemLabels.isSelected = isSelected
    }

    fun isBoldProblemLabels(): Boolean {
        return boldProblemLabels.isSelected
    }

    fun setBoldProblemLabels(isSelected: Boolean) {
        boldProblemLabels.isSelected = isSelected
    }

    fun isItalicProblemLabels(): Boolean {
        return italicProblemLabels.isSelected
    }

    fun setItalicProblemLabels(isSelected: Boolean) {
        italicProblemLabels.isSelected = isSelected
    }

    fun isShowErrors(): Boolean {
        return showErrors.isSelected
    }

    fun setShowErrors(isSelected: Boolean) {
        showErrors.isSelected = isSelected
    }

    fun isHighlightErrors(): Boolean {
        return highlightErrors.isSelected
    }

    fun setHighlightErrors(isSelected: Boolean) {
        highlightErrors.isSelected = isSelected
    }

    fun isShowWarnings(): Boolean {
        return showWarnings.isSelected
    }

    fun setShowWarnings(isSelected: Boolean) {
        showWarnings.isSelected = isSelected
    }

    fun isHighlightWarnings(): Boolean {
        return highlightWarnings.isSelected
    }

    fun setHighlightWarnings(isSelected: Boolean) {
        highlightWarnings.isSelected = isSelected
    }

    fun isShowWeakWarnings(): Boolean {
        return showWeakWarnings.isSelected
    }

    fun setShowWeakWarnings(isSelected: Boolean) {
        showWeakWarnings.isSelected = isSelected
    }

    fun isHighlightWeakWarnings(): Boolean {
        return highlightWeakWarnings.isSelected
    }

    fun setHighlightWeakWarnings(isSelected: Boolean) {
        highlightWeakWarnings.isSelected = isSelected
    }

    fun isShowInfos(): Boolean {
        return showInfos.isSelected
    }

    fun setShowInfos(isSelected: Boolean) {
        showInfos.isSelected = isSelected
    }

    fun isHighlightInfo(): Boolean {
        return highlightInfo.isSelected
    }

    fun setHighlightInfo(isSelected: Boolean) {
        highlightInfo.isSelected = isSelected
    }

    fun getErrorTextColor(): Color? {
        return errorTextColor.selectedColor
    }

    fun setErrorTextColor(color: Color?) {
        errorTextColor.selectedColor = color
    }

    fun getErrorLabelBackgroundColor(): Color? {
        return errorLabelBackgroundColor.selectedColor
    }

    fun setErrorLabelBackgroundColor(color: Color?) {
        errorLabelBackgroundColor.selectedColor = color
    }

    fun getWarningTextColor(): Color? {
        return warningTextColor.selectedColor
    }

    fun setWarningTextColor(color: Color?) {
        warningTextColor.selectedColor = color
    }

    fun getWarningLabelBackgroundColor(): Color? {
        return warningLabelBackgroundColor.selectedColor
    }

    fun setWarningLabelBackgroundColor(color: Color?) {
        warningLabelBackgroundColor.selectedColor = color
    }

    fun getWeakWarningTextColor(): Color? {
        return weakWarningTextColor.selectedColor
    }

    fun setWeakWarningTextColor(color: Color?) {
        weakWarningTextColor.selectedColor = color
    }

    fun getWeakWarningLabelBackgroundColor(): Color? {
        return weakWarningLabelBackgroundColor.selectedColor
    }

    fun setWeakWarningLabelBackgroundColor(color: Color?) {
        weakWarningLabelBackgroundColor.selectedColor = color
    }

    fun getInfoTextColor(): Color? {
        return infoTextColor.selectedColor
    }

    fun setInfoTextColor(color: Color?) {
        infoTextColor.selectedColor = color
    }

    fun getInfoLabelBackgroundColor(): Color? {
        return infoLabelBackgroundColor.selectedColor
    }

    fun setInfoLabelBackgroundColor(color: Color?) {
        infoLabelBackgroundColor.selectedColor = color
    }

    fun getErrorHighlightColor(): Color? {
        return errorHighlightColor.selectedColor
    }

    fun setErrorHighlightColor(color: Color?) {
        errorHighlightColor.selectedColor = color
    }

    fun getWarningHighlightColor(): Color? {
        return warningHighlightColor.selectedColor
    }

    fun setWarningHighlightColor(color: Color?) {
        warningHighlightColor.selectedColor = color
    }

    fun getWeakWarningHighlightColor(): Color? {
        return weakWarningHighlightColor.selectedColor
    }

    fun setWeakWarningHighlightColor(color: Color?) {
        weakWarningHighlightColor.selectedColor = color
    }

    fun getInfoHighlightColor(): Color? {
        return infoHighlightColor.selectedColor
    }

    fun setInfoHighlightColor(color: Color?) {
        infoHighlightColor.selectedColor = color
    }

    fun getProblemFilterList(): String {
        return problemFilterList.text
    }

    fun setProblemFilterList(newText: String?) {
        problemFilterList.text = newText
    }

    fun getAdditionalInfoSeverities(): String {
        return additionalInfoSeverities.text
    }

    val additionalInfoSeveritiesList: List<Int>
        get() = getSeverityIntegerList(additionalInfoSeverities.text)

    fun setAdditionalInfoSeverities(newText: String?) {
        additionalInfoSeverities.text = newText
    }

    fun getAdditionalWarningSeverities(): String {
        return additionalWarningSeverities.text
    }

    val additionalWarningSeveritiesList: List<Int>
        get() = getSeverityIntegerList(additionalWarningSeverities.text)

    fun setAdditionalWarningSeverities(newText: String?) {
        additionalWarningSeverities.text = newText
    }

    fun getAdditionalErrorSeverities(): String {
        return additionalErrorSeverities.text
    }

    val additionalErrorSeveritiesList: List<Int>
        get() = getSeverityIntegerList(additionalErrorSeverities.text)

    fun setAdditionalErrorSeverities(newText: String?) {
        additionalErrorSeverities.text = newText
    }

    fun getAdditionalWeakWarningSeverities(): String {
        return additionalWeakWarningSeverities.text
    }

    val additionalWeakWarningSeveritiesList: List<Int>
        get() = getSeverityIntegerList(additionalWeakWarningSeverities.text)

    fun setAdditionalWeakWarningSeverities(newText: String?) {
        additionalWeakWarningSeverities.text = newText
    }

    fun getEnabledListener(): Int {
        return enabledListener.selectedIndex
    }

    fun setEnabledListener(index: Int) {
        enabledListener.selectedIndex = index
    }

    private fun getSeverityIntegerList(text: String): List<Int> {
        return Arrays.stream(text.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            .map { obj: String -> obj.trim { it <= ' ' } }
            .filter { s: String -> !s.isEmpty() }
            .filter { s: String ->
                try {
                    s.toInt()
                    return@filter true
                } catch (e: NumberFormatException) {
                    return@filter false
                }
            }
            .map { s: String -> s.toInt() }
            .collect(Collectors.toList())
    }

    fun getManualScannerDelay(): Int {
        return try {
            Math.max(manualScannerDelay.text.toInt(), 10)
        } catch (e: NumberFormatException) {
            100
        }
    }

    fun setManualScannerDelay(delay: Int) {
        manualScannerDelay.text = Integer.toString(Math.max(10, delay))
    }

    fun getSettingsPanel(): JComponent? = settingsPanel
}
