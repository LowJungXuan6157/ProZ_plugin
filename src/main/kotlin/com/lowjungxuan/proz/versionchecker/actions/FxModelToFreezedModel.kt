package com.lowjungxuan.proz.versionchecker.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.lowjungxuan.proz.versionchecker.common.MyAction
import com.lowjungxuan.proz.versionchecker.dialog.FreezedCovertDialog
import com.lowjungxuan.proz.versionchecker.services.impl.ModelToFreezedModelServiceImpl
import com.lowjungxuan.proz.versionchecker.util.DartPsiElementUtil
import com.lowjungxuan.proz.versionchecker.util.getDartClassDefinition

/**
 * 模型转freezed
 */
class FxModelToFreezedModel : MyAction() {

    private val toFreezedService = ModelToFreezedModelServiceImpl()
    override fun actionPerformed(e: AnActionEvent) {
        val model = toFreezedService.anActionEventToFreezedCovertModel(e)
        FreezedCovertDialog(e.project!!, model).show()
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = e.getDartClassDefinition() != null && DartPsiElementUtil.getClassProperties(e.getDartClassDefinition()!!).isNotEmpty()
        super.update(e)
    }


}
