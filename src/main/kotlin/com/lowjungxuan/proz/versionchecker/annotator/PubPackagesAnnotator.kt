package com.lowjungxuan.proz.versionchecker.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.ExternalAnnotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.application.runReadAction
import com.intellij.psi.PsiFile
import com.lowjungxuan.proz.versionchecker.dto.DependencyDescription
import com.lowjungxuan.proz.versionchecker.parsing.YamlParser
import com.lowjungxuan.proz.versionchecker.quickfix.GoToPubDevQuickFix
import com.lowjungxuan.proz.versionchecker.quickfix.UpdateAllDependenciesQuickFix
import com.lowjungxuan.proz.versionchecker.quickfix.UpdateDependencyQuickFix
import com.lowjungxuan.proz.versionchecker.resources.Strings
import com.lowjungxuan.proz.versionchecker.settings.AppSettingsState
import com.lowjungxuan.proz.versionchecker.util.DependencyHttpClient
import com.lowjungxuan.proz.versionchecker.util.VersionsRepository

class PubPackagesAnnotator : ExternalAnnotator<PubPackagesAnnotator.Info, PubPackagesAnnotator.Result>() {

    data class Info(val file: PsiFile)
    data class Result(val annotations: List<DependencyDescription>)

    override fun collectInformation(file: PsiFile): Info =
        runReadAction { Info(file) }

    override fun doAnnotate(collectedInfo: Info?): Result? {
        if (collectedInfo == null) return null

        val httpClient = DependencyHttpClient()
        val appSettingsState = AppSettingsState.instance
        val versionsRepository = VersionsRepository(httpClient, appSettingsState)
        val yamlParser = YamlParser(collectedInfo.file.text, versionsRepository)
        val annotations = yamlParser.inspectFile()
        return if (annotations.isNotEmpty()) Result(annotations) else null
    }

    override fun apply(file: PsiFile, annotationResult: Result?, holder: AnnotationHolder) {
        if (annotationResult == null) return

        annotationResult.annotations.forEach {
            val psiElement = file.findElementAt(it.dependency.index)!!
            holder.newAnnotation(HighlightSeverity.WARNING, "${Strings.annotationDescription} ${it.latestVersion}")
                .range(psiElement)
                .newFix(UpdateDependencyQuickFix(it.dependency.packageName, it.latestVersion, psiElement))
                .registerFix()
                .newFix(UpdateAllDependenciesQuickFix(annotationResult.annotations))
                .registerFix()
                .newFix(GoToPubDevQuickFix(it.dependency.packageName))
                .registerFix()
                .create()
        }
    }
}