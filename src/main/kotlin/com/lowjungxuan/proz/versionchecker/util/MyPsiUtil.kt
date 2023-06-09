package com.lowjungxuan.proz.versionchecker.util

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.project.stateStore
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.PsiFileFactoryImpl
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.childrenOfType
import com.intellij.testFramework.LightVirtualFile
import com.intellij.util.IncorrectOperationException
import com.jetbrains.lang.dart.DartFileType
import com.lowjungxuan.proz.versionchecker.constance.igFlutterPlugin
import com.lowjungxuan.proz.versionchecker.model.FlutterPluginElementModel
import com.lowjungxuan.proz.versionchecker.model.FlutterPluginType
import org.jetbrains.yaml.YAMLElementGenerator
import org.jetbrains.yaml.YAMLLanguage
import org.jetbrains.yaml.YAMLUtil
import org.jetbrains.yaml.psi.YAMLFile
import org.jetbrains.yaml.psi.impl.YAMLBlockMappingImpl
import org.jetbrains.yaml.psi.impl.YAMLKeyValueImpl
import java.io.File


/**
 * PSI 操作相关类
 */
class MyPsiElementUtil {

    companion object {


        /**
         * 插入节点到pubspec文件
         */
        fun insertPluginToPubspecFile(
            project: Project,
            pluginName: String,
            version: String = "any",
            type: FlutterPluginType = FlutterPluginType.Dependencies
        ) {
            val psiFile = getPubSecpYamlFile(project)
            if (psiFile != null) {
                val qualifiedKeyInFile = YAMLUtil.getQualifiedKeyInFile(psiFile as YAMLFile, type.type)
                val insetVersion = "^$version"
                val blockElement = YAMLElementGenerator.getInstance(project)
                    .createYamlKeyValue(pluginName, insetVersion)
                val eolElement = YAMLElementGenerator.getInstance(project).createEol()
                WriteCommandAction.runWriteCommandAction(project) {
                    try {
                        qualifiedKeyInFile?.add(eolElement)
                        qualifiedKeyInFile?.add(blockElement)
                    } catch (e: IncorrectOperationException) {
                        project.toastWithError("add to file error: $e")
                    }
                }
            }
        }


        /**
         * 获取插件名字
         *
         * 例子:
         * flutter_launcher_icons: ^0.9.2
         * 返回 flutter_launcher_icons
         */
        fun getPluginNameWithPsi(psiElement: PsiElement?): String {
            if (psiElement is YAMLKeyValueImpl) {
                return psiElement.keyText
            }
            if (psiElement is LeafPsiElement) {
                psiElement.text
            }
            return psiElement?.text ?: ""
        }

        /**
         * 获取项目pubspec.yaml 文件
         */
        fun getPubSecpYamlFile(project: Project): PsiFile? {
            val pubspecYamlFile =
                LocalFileSystem.getInstance()
                    .findFileByIoFile(File("${project.stateStore.projectBasePath}/pubspec.yaml"))
            if (pubspecYamlFile != null) {
                return PsiManager.getInstance(project).findFile(pubspecYamlFile)
            }
            return null
        }

        /**
         * 获取项目的所有插件
         */
        fun getAllPlugins(project: Project, key: String = "dependencies"): List<String> {
            val yamlFile = project.getPubspecYAMLFile()
            yamlFile?.let { file ->
                val deps = YAMLUtil.getQualifiedKeyInFile(file, key)
                if (deps != null) {

                    return deps.children.first().children.map { (it as YAMLKeyValueImpl).keyText }
                }
            }
            return emptyList()
        }


        /**
         * 获取项目插件列表
         */
        fun getAllFlutters(project: Project): MutableMap<FlutterPluginType, List<FlutterPluginElementModel>> {
            val yamlFile = project.getPubspecYAMLFile()
            val map = mutableMapOf<FlutterPluginType, List<FlutterPluginElementModel>>()
            yamlFile?.let { yaml ->
                val coreElement = yaml.firstChild.firstChild
                val coreElementChildrens = coreElement.childrenOfType<YAMLKeyValueImpl>()
                if (coreElementChildrens.isNotEmpty()) {
                    FlutterPluginType.values().forEach { type ->
                        val l = coreElementChildrens.filter { it.keyText == type.type }.toList()
                        if (l.isNotEmpty()) {
                            val pluginDevs = l.first()
                            if (pluginDevs.childrenOfType<YAMLBlockMappingImpl>().isNotEmpty()) {
                                val mapping = pluginDevs.childrenOfType<YAMLBlockMappingImpl>().first()
                                var psis = mapping.childrenOfType<YAMLKeyValueImpl>()
                                val igs = igFlutterPlugin.igPlugins
                                psis = psis.filter { p -> !igs.contains(p.keyText) }
                                map[type] = psis.map { psi ->
                                    FlutterPluginElementModel(
                                        name = psi.keyText,
                                        type = type,
                                        element = psi
                                    )
                                }
                            }
                        }
                    }
                }
            }
            return map
        }


    }


}


/**
 * 判断此节点是否为flutter插件
 */
fun PsiElement.isDartPluginElement(): Boolean {

    if (this is YAMLKeyValueImpl) {
        val allPluginsMap = MyPsiElementUtil.getAllFlutters(project)
        val allPlugins = allPluginsMap.values.toList()
        var isin = false
        allPlugins.forEach { e1 ->
            try {
                e1.first { it.name == this.keyText }
                isin = true
            } catch (_: Exception) {
            }
        }
        return isin
    }
    return false
}


fun PsiElement.getPluginName(): String {
    return MyPsiElementUtil.getPluginNameWithPsi(this)
}

/**
 * 获取项目下的pubspec.yaml文件的yamlfile对象
 */
fun Project.getPubspecYAMLFile(): YAMLFile? {
    return MyPsiElementUtil.getPubSecpYamlFile(this) as? YAMLFile
}