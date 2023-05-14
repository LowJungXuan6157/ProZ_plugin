package com.lowjungxuan.proz.versionchecker.tools

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.util.indexing.FileBasedIndex
import com.lowjungxuan.proz.versionchecker.fix.NewVersionFix
import com.lowjungxuan.proz.versionchecker.i18n.PluginBundle
import com.lowjungxuan.proz.versionchecker.model.FlutterPluginElementModel
import com.lowjungxuan.proz.versionchecker.util.ApiService
import com.lowjungxuan.proz.versionchecker.util.CacheUtil
import com.lowjungxuan.proz.versionchecker.util.MyPsiElementUtil


/**
 * yaml 版本自动补全
 */
class AutoVersionTool : LocalInspectionTool() {

    /// 访问了文件
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return YamlElementVisitor(holder)
    }

    override fun runForWholeFile(): Boolean {
        return false
    }
}


///检测版本
class YamlElementVisitor(
    private val holder: ProblemsHolder
) : PsiElementVisitor() {



    private val plugins = MyPsiElementUtil.getAllFlutters(holder.project)
    private val pubFile = MyPsiElementUtil.getPubSecpYamlFile(holder.project)


    override fun visitFile(file: PsiFile) {
        super.visitFile(file)

        for (arr in plugins.values) {
            arr.forEach { ele ->
                regProblem(ele)
            }
        }
    }




    /**
     * 问题注册器,并新增快速修复功能更
     */
    private fun regProblem(ele: FlutterPluginElementModel) {

        var cacheModel = CacheUtil.getCatch().getIfPresent(ele.name)
        if(cacheModel == null){
           cacheModel = ApiService.getPluginDetail(ele.name)
        }
        cacheModel?.let { model ->
            CacheUtil.set(ele.name,cacheModel)
            val currentVersionString = ele.element.valueText
            cacheModel.judge(currentVersionString) {
                holder.registerProblem(
                    ele.element.lastChild,
                    "${PluginBundle.get("version.tip.1")}:${it}  (${PluginBundle.get("version.tip.2")}:${cacheModel.lastVersionUpdateTimeString})",
                    ProblemHighlightType.WARNING,
                    NewVersionFix(ele.element, it,model){
                    }
                )
            }
        }
    }


}