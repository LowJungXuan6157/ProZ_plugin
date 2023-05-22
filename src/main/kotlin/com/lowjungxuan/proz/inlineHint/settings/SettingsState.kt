package com.lowjungxuan.proz.inlineHint.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.annotations.OptionTag
import com.intellij.util.xmlb.annotations.Transient
import com.lowjungxuan.proz.inlineHint.entities.enums.Listener
import com.lowjungxuan.proz.inlineHint.utils.ColorConverter
import java.awt.Color
import java.util.stream.Collectors

@State(name = "com.lowjungxuan.inlineproblems.settings.SettingsState", storages = [Storage("OverEngineer_InlineProblems.xml")])
class SettingsState : PersistentStateComponent<SettingsState> {
    companion object {
        val instance: SettingsState = ApplicationManager.getApplication().getService(SettingsState::class.java)
    }

    var showErrors = true
    var highlightErrors = false
    var showWarnings = true
    var highlightWarnings = false
    var showWeakWarnings = false
    var highlightWeakWarnings = false
    val showInfos = false
    val highlightInfos = false

    @OptionTag(converter = ColorConverter::class)
    private var errorBackgroundCol = Color(0x654243)

    @OptionTag(converter = ColorConverter::class)
    private var errorTextCol = Color(0xEC5151)

    @OptionTag(converter = ColorConverter::class)
    private var errorHighlightCol = errorBackgroundCol

    @OptionTag(converter = ColorConverter::class)
    private var warningTextCol = Color(0xEC821F)

    @OptionTag(converter = ColorConverter::class)
    private var warningBackgroundCol = Color(0x815125)

    @OptionTag(converter = ColorConverter::class)
    private var warningHighlightCol = warningBackgroundCol

    @OptionTag(converter = ColorConverter::class)
    private var weakWarningTextCol = Color(0xC07937)

    @OptionTag(converter = ColorConverter::class)
    private var weakWarningBackgroundCol = Color(0xA47956)

    @OptionTag(converter = ColorConverter::class)
    private var weakWarningHighlightCol = weakWarningBackgroundCol

    @OptionTag(converter = ColorConverter::class)
    private var infoTextCol = Color(0x3BB1CE)

    @OptionTag(converter = ColorConverter::class)
    private var infoBackgroundCol = Color(0x1E5664)

    @OptionTag(converter = ColorConverter::class)
    private var infoHighlightCol = infoBackgroundCol

    val manualScannerDelay = 200
    val drawBoxesAroundErrorLabels = true
    val roundedCornerBoxes = true
    val forceProblemsInSameLine = true
    val useEditorFont = false
    val inlayFontSizeDelta = 0
    val fillProblemLabels = false
    val boldProblemLabels = false
    val italicProblemLabels = false
    val problemLineLengthOffsetPixels = 50
    var enabledListener: Int = Listener.MARKUP_MODEL_LISTENER
    var problemFilterList = "todo;fixme;open in browser"

    val additionalErrorSeverities: List<Int> = ArrayList()
    val additionalWarningSeverities: List<Int> = ArrayList()
    val additionalWeakWarningSeverities: List<Int> = ArrayList()
    val additionalInfoSeverities: List<Int> = ArrayList()

    val showOnlyHighestSeverityPerLine = false

    // migration booleans
    private var highlightProblemListenerDeprecateMigrationDone = false
    private var filterListMigrationDone01 = false
    fun getAdditionalInfoSeveritiesAsString(): String? {
        return getSeverityListAsString(additionalInfoSeverities)
    }

    fun getAdditionalErrorSeveritiesAsString(): String? {
        return getSeverityListAsString(additionalErrorSeverities)
    }

    fun getAdditionalWarningSeveritiesAsString(): String? {
        return getSeverityListAsString(additionalWarningSeverities)
    }

    fun getAdditionalWeakWarningSeveritiesAsString(): String? {
        return getSeverityListAsString(additionalWeakWarningSeverities)
    }

    private fun getSeverityListAsString(severityList: List<Int>): String? {
        return severityList.stream().map { obj: Int? -> java.lang.String.valueOf(obj) }.collect(Collectors.joining("; "))
    }

    override fun getState(): SettingsState? {
        TODO("Not yet implemented")
    }

    override fun loadState(p0: SettingsState) {
        TODO("Not yet implemented")
    }

    override fun noStateLoaded() {
        migrateState()
    }

    private fun migrateState() {
        // filter list
        if (!filterListMigrationDone01) {
            val newFilterListEntries = listOf("Consider unknown contexts non-blocking")
            for (entry in newFilterListEntries) {
                if (!problemFilterList.contains(entry)) {
                    problemFilterList += ";$entry"
                }
            }
            filterListMigrationDone01 = true
        }

        // listener
        if (!highlightProblemListenerDeprecateMigrationDone) {
            if (enabledListener == Listener.HIGHLIGHT_PROBLEMS_LISTENER) {
                enabledListener = Listener.MARKUP_MODEL_LISTENER
            }
            highlightProblemListenerDeprecateMigrationDone = true
        }
    }

    @Transient
    fun getErrorTextColor(): Color {
        return errorTextCol
    }

    @Transient
    fun setErrorTextColor(color: Color?) {
        errorTextCol = color!!
    }

    @Transient
    fun getErrorBackgroundColor(): Color {
        return errorBackgroundCol
    }

    @Transient
    fun setErrorBackgroundColor(color: Color?) {
        errorBackgroundCol = color!!
    }

    @Transient
    fun getErrorHighlightColor(): Color {
        return errorHighlightCol
    }

    @Transient
    fun setErrorHighlightColor(color: Color?) {
        errorHighlightCol = color!!
    }

    @Transient
    fun getWarningTextColor(): Color {
        return warningTextCol
    }

    @Transient
    fun setWarningTextColor(color: Color?) {
        warningTextCol = color!!
    }

    @Transient
    fun getWarningBackgroundColor(): Color {
        return warningBackgroundCol
    }

    @Transient
    fun setWarningBackgroundColor(color: Color?) {
        warningBackgroundCol = color!!
    }

    @Transient
    fun getWarningHighlightColor(): Color {
        return warningHighlightCol
    }

    @Transient
    fun setWarningHighlightColor(color: Color?) {
        warningHighlightCol = color!!
    }

    @Transient
    fun getWeakWarningTextColor(): Color {
        return weakWarningTextCol
    }

    @Transient
    fun setWeakWarningTextColor(color: Color?) {
        weakWarningTextCol = color!!
    }

    @Transient
    fun getWeakWarningBackgroundColor(): Color {
        return weakWarningBackgroundCol
    }

    @Transient
    fun setWeakWarningBackgroundColor(color: Color?) {
        weakWarningBackgroundCol = color!!
    }

    @Transient
    fun getWeakWarningHighlightColor(): Color {
        return weakWarningHighlightCol
    }

    @Transient
    fun setWeakWarningHighlightColor(color: Color?) {
        weakWarningHighlightCol = color!!
    }

    @Transient
    fun getInfoTextColor(): Color {
        return infoTextCol
    }

    @Transient
    fun setInfoTextColor(color: Color?) {
        infoTextCol = color!!
    }

    @Transient
    fun getInfoBackgroundColor(): Color {
        return infoBackgroundCol
    }

    @Transient
    fun setInfoBackgroundColor(color: Color?) {
        infoBackgroundCol = color!!
    }

    @Transient
    fun getInfoHighlightColor(): Color {
        return infoHighlightCol
    }

    @Transient
    fun setInfoHighlightColor(color: Color?) {
        infoHighlightCol = color!!
    }

    fun setShowErrors(showErrors: Boolean) {
        this.showErrors = showErrors
    }

    fun setHighlightErrors(highlightErrors: Boolean) {
        this.highlightErrors = highlightErrors
    }

    fun setShowWarnings(showWarnings: Boolean) {
        this.showWarnings = showWarnings
    }

    fun setHighlightWarnings(highlightWarnings: Boolean) {
        this.highlightWarnings = highlightWarnings
    }

    fun setShowWeakWarnings(showWeakWarnings: Boolean) {
        this.showWeakWarnings = showWeakWarnings
    }

    fun setHighlightWeakWarnings(highlightWeakWarnings: Boolean) {
        this.highlightWeakWarnings = highlightWeakWarnings
    }
}

//package org.overengineer.inlineproblems.settings
//
//import com.intellij.openapi.application.ApplicationManager
//import com.intellij.openapi.components.PersistentStateComponent
//import com.intellij.openapi.components.State
//import com.intellij.openapi.components.Storage
//import com.intellij.util.xmlb.XmlSerializerUtil
//import com.intellij.util.xmlb.annotations.OptionTag
//import com.intellij.util.xmlb.annotations.Transient
//import lombok.Getter
//import lombok.Setter
//import org.overengineer.inlineproblems.entities.enums.Listener
//import org.overengineer.inlineproblems.utils.ColorConverter
//import java.awt.Color
//import java.util.stream.Collectors
//
//@Getter
//@Setter
//@State(name = "org.overengineer.inlineproblems.settings.SettingsState", storages = [Storage("OverEngineer_InlineProblems.xml")])
//class SettingsState : PersistentStateComponent<SettingsState?> {
//    private val showErrors = true
//    private val highlightErrors = false
//    private val showWarnings = true
//    private val highlightWarnings = false
//    private val showWeakWarnings = false
//    private val highlightWeakWarnings = false
//    private val showInfos = false
//    private val highlightInfos = false
//
//    /**
//     * Colors renamed from '<NAME>Color' to '<NAME>Col' to solve
//     * the compatibility issue with old version persisted xml setting file.
//     *
//     * @see [Github discussion](https://github.com/0verEngineer/InlineProblems/pull/10)
//    </NAME></NAME> */
//    @get:Transient
//    @set:Transient
//    @OptionTag(converter = ColorConverter::class)
//    var errorBackgroundColor = Color(0x654243)
//
//    //<editor-fold desc="Handwritten Colors getter/setter to compatible with external callers.">
//    @get:Transient
//    @set:Transient
//    @OptionTag(converter = ColorConverter::class)
//    var errorTextColor = Color(0xEC5151)
//
//    @get:Transient
//    @set:Transient
//    @OptionTag(converter = ColorConverter::class)
//    var errorHighlightColor = errorBackgroundColor
//
//    @get:Transient
//    @set:Transient
//    @OptionTag(converter = ColorConverter::class)
//    var warningTextColor = Color(0xEC821F)
//
//    @get:Transient
//    @set:Transient
//    @OptionTag(converter = ColorConverter::class)
//    var warningBackgroundColor = Color(0x815125)
//
//    @get:Transient
//    @set:Transient
//    @OptionTag(converter = ColorConverter::class)
//    var warningHighlightColor = warningBackgroundColor
//
//    @get:Transient
//    @set:Transient
//    @OptionTag(converter = ColorConverter::class)
//    var weakWarningTextColor = Color(0xC07937)
//
//    @get:Transient
//    @set:Transient
//    @OptionTag(converter = ColorConverter::class)
//    var weakWarningBackgroundColor = Color(0xA47956)
//
//    @get:Transient
//    @set:Transient
//    @OptionTag(converter = ColorConverter::class)
//    var weakWarningHighlightColor = weakWarningBackgroundColor
//
//    @get:Transient
//    @set:Transient
//    @OptionTag(converter = ColorConverter::class)
//    var infoTextColor = Color(0x3BB1CE)
//
//    @get:Transient
//    @set:Transient
//    @OptionTag(converter = ColorConverter::class)
//    var infoBackgroundColor = Color(0x1E5664)
//
//    //</editor-fold>
//    @get:Transient
//    @set:Transient
//    @OptionTag(converter = ColorConverter::class)
//    var infoHighlightColor = infoBackgroundColor
//    private val manualScannerDelay = 200
//    private val drawBoxesAroundErrorLabels = true
//    private val roundedCornerBoxes = true
//    private val forceProblemsInSameLine = true
//    private val useEditorFont = false
//    private val inlayFontSizeDelta = 0
//    private val fillProblemLabels = false
//    private val boldProblemLabels = false
//    private val italicProblemLabels = false
//    private val problemLineLengthOffsetPixels = 50
//    private var enabledListener: Int = Listener.MARKUP_MODEL_LISTENER
//    private var problemFilterList = "todo;fixme;open in browser"
//    private val additionalErrorSeverities: List<Int> = ArrayList()
//    private val additionalWarningSeverities: List<Int> = ArrayList()
//    private val additionalWeakWarningSeverities: List<Int> = ArrayList()
//    private val additionalInfoSeverities: List<Int> = ArrayList()
//    private val showOnlyHighestSeverityPerLine = false
//
//    // migration booleans
//    private var highlightProblemListenerDeprecateMigrationDone = false
//    private var filterListMigrationDone01 = false
//    override fun getState(): SettingsState? {
//        return this
//    }
//
//    override fun loadState(state: SettingsState) {
//        XmlSerializerUtil.copyBean(state, this)
//        migrateState()
//    }
//
//    override fun noStateLoaded() {
//        migrateState()
//    }
//
//    private fun migrateState() {
//        // filter list
//        if (!filterListMigrationDone01) {
//            val newFilterListEntries = listOf("Consider unknown contexts non-blocking")
//            for (entry in newFilterListEntries) {
//                if (!problemFilterList.contains(entry)) {
//                    problemFilterList += ";$entry"
//                }
//            }
//            filterListMigrationDone01 = true
//        }
//
//        // listener
//        if (!highlightProblemListenerDeprecateMigrationDone) {
//            if (enabledListener == Listener.HIGHLIGHT_PROBLEMS_LISTENER) {
//                enabledListener = Listener.MARKUP_MODEL_LISTENER
//            }
//            highlightProblemListenerDeprecateMigrationDone = true
//        }
//    }
//
//    val additionalInfoSeveritiesAsString: String
//        get() = getSeverityListAsString(additionalInfoSeverities)
//    val additionalErrorSeveritiesAsString: String
//        get() = getSeverityListAsString(additionalErrorSeverities)
//    val additionalWarningSeveritiesAsString: String
//        get() = getSeverityListAsString(additionalWarningSeverities)
//    val additionalWeakWarningSeveritiesAsString: String
//        get() = getSeverityListAsString(additionalWeakWarningSeverities)
//
//    private fun getSeverityListAsString(severityList: List<Int>): String {
//        return severityList.stream().map { obj: Int? -> java.lang.String.valueOf(obj) }.collect(Collectors.joining("; "))
//    }
//
//    companion object {
//        val instance: SettingsState
//            get() = ApplicationManager.getApplication().getService(SettingsState::class.java)
//    }
//}
