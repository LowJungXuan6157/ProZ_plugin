package com.lowjungxuan.proz.versionchecker.fix

import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import org.jetbrains.yaml.YAMLElementGenerator
import org.jetbrains.yaml.psi.impl.YAMLKeyValueImpl
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle
import com.lowjungxuan.proz.versionchecker.model.PubVersionDataModel
import com.lowjungxuan.proz.versionchecker.util.CacheUtil


/**
 * 存在新版本的快速修复
 */
class NewVersionFix(
    psiElement: PsiElement,
    private val newVersion: String,
    private val pubVersionDataModel: PubVersionDataModel,
    val invokeCallback: () -> Unit
) : LocalQuickFixOnPsiElement(psiElement) {

    override fun getFamilyName(): String {
        return " ${PluginBundle.get("replace.with")}:$newVersion"
    }

    override fun getText(): String {
        return familyName
    }

    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
        val pluginName = (startElement as YAMLKeyValueImpl).keyText
        val newElement = YAMLElementGenerator.getInstance(project).createYamlKeyValue(pluginName, newVersion)
        startElement.replace(newElement)
        CacheUtil.remove(pubVersionDataModel.name)
        invokeCallback()
    }

}
