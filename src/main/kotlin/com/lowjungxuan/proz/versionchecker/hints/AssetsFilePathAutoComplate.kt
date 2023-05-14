package com.lowjungxuan.proz.versionchecker.hints

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.util.elementType
import com.intellij.util.ProcessingContext
import com.jetbrains.lang.dart.DartLanguage
import com.jetbrains.lang.dart.DartTokenTypes
import com.lowjungxuan.proz.versionchecker.icons.MyIcons
import com.lowjungxuan.proz.versionchecker.services.AppStateModel
import com.lowjungxuan.proz.versionchecker.services.PluginStateService
import com.lowjungxuan.proz.versionchecker.util.MyFileUtil
import com.lowjungxuan.proz.versionchecker.util.fileNameWith

/**
 * 资源文件路径自动补全
 */
class AssetsFilePathAutoComplete : CompletionContributor() {

    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement().withLanguage(DartLanguage.INSTANCE),
            AssetsFilePathAutoCompleteProvider()
        )
    }


}


/**
 * 资产文件自动补全
 */
class AssetsFilePathAutoCompleteProvider : CompletionProvider<CompletionParameters>() {


    private var setting = PluginStateService.getInstance().state ?: AppStateModel()
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {

        val file = parameters.originalFile
        file.findElementAt(parameters.offset)?.apply {
            if (prevSibling.elementType == DartTokenTypes.REGULAR_STRING_PART) {
                val strEle = prevSibling.text
                if (strEle.length >= setting.assetCompilationTriggerLen && (strEle.startsWith(setting.assetCompilationTriggerString))) {
                    doHandle(parameters, result)
                }
            }
        }
    }


    private fun doHandle(parameters: CompletionParameters, result: CompletionResultSet) {
        parameters.editor.project?.apply {
            MyFileUtil.onFolderEachWithProject(this, setting.assetScanFolderName) { virtualFile ->
                val withIcon =
                    LookupElementBuilder.create(virtualFile.fileNameWith("assets")).withIcon(MyIcons.diandianLogoIcon)
                result.addElement(withIcon)
            }
        }
    }

}
