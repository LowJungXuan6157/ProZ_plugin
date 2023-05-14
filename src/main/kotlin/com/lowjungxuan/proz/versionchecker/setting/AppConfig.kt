package com.lowjungxuan.proz.versionchecker.setting

import com.intellij.openapi.Disposable
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBTabbedPane
import com.lowjungxuan.proz.versionchecker.config.DioxListeningSetting
import com.lowjungxuan.proz.versionchecker.config.DioxListingUiConfig
import com.lowjungxuan.proz.versionchecker.config.GenerateAssetsClassConfig
import com.lowjungxuan.proz.versionchecker.dsl.settingPanel
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle
import com.lowjungxuan.proz.versionchecker.services.AppStateModel
import com.lowjungxuan.proz.versionchecker.services.PluginStateService
import javax.swing.JComponent

class AppConfig : Configurable, Disposable {

    var model = PluginStateService.getInstance().state ?: AppStateModel()

    private var dioSetting = DioxListingUiConfig.getInstance().state ?: DioxListeningSetting()

    private val generaAssetsSettingPanel = GenerateAssetsClassConfig.getGenerateAssetsSetting()

    private var generaAssetsSettingPanelModelIs = false

    private var generateSettingPanel =
        GeneraAssetsSettingPanel(settingModel = generaAssetsSettingPanel, parentDisposable = this@AppConfig) {
            generaAssetsSettingPanelModelIs = it
        }

    override fun createComponent(): JComponent {
        return JBTabbedPane().apply {
            add(PluginBundle.get("basic"), panel)
            add(PluginBundle.get("assets.gen"), generateSettingPanel)
        }
    }

    val dialog: DialogPanel = settingPanel(model, dioSetting, this) {
        model = it
    }

    private val panel: JComponent get() = dialog

    override fun isModified(): Boolean {
        return dialog.isModified() || generaAssetsSettingPanelModelIs
    }

    override fun apply() {
        dialog.apply()
        generateSettingPanel.doApply()
        PluginStateService.getInstance().loadState(model)
        DioxListingUiConfig.getInstance().loadState(dioSetting)
        GenerateAssetsClassConfig.getInstance().loadState(generaAssetsSettingPanel)
    }

    override fun getDisplayName(): String {
        return PluginBundle.get("setting")
    }

    override fun reset() {
        dialog.reset()
        super.reset()
    }

    override fun dispose() {

    }
}