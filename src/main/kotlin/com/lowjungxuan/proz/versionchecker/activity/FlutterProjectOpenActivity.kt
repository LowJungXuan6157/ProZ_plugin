package com.lowjungxuan.proz.versionchecker.activity

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileEvent
import com.lowjungxuan.proz.versionchecker.config.GenerateAssetsClassConfig
import com.lowjungxuan.proz.versionchecker.util.CacheUtil
import com.lowjungxuan.proz.versionchecker.util.MyDartPsiElementUtil

/**
 * 梁典典
 * 当项目打开的时候,会执行这个类的runActivity方法
 * 在这里启动一个子线程去检测项目中的pubspec.yaml文件.并执行检测新版本
 */
class FlutterProjectOpenActivity : StartupActivity, Disposable {

    /**
     * 项目在idea中打开时执行函数
     *
     */


    private fun checkAndAutoGenFile(projectPath: String, file: VirtualFile, project: Project) {
        var filePath = file.canonicalPath
        filePath = filePath?.replace("$projectPath/", "")
        if (filePath != null) {
            if (filePath.indexOf("assets") == 0) {
                MyDartPsiElementUtil.autoGenerateAssetsDartClassFile(project, "assets", true)
            }
        }
    }

    override fun dispose() {

    }

    fun execute(project: Project) {
        ///监听assets资源目录更改事件
        ApplicationManager.getApplication().messageBus.connect(this).subscribe(VirtualFileManager.VFS_CHANGES, object :
            BulkFileListener {
            override fun after(events: MutableList<out VFileEvent>) {
                val projectPath = project.basePath
                val setting = GenerateAssetsClassConfig.getGenerateAssetsSetting()
                if (!setting.autoListenFileChange) {
                    return
                }
                if (projectPath != null) {
                    events.forEach {
                        it.file?.apply {
                            checkAndAutoGenFile(projectPath, this, project)
                        }
                    }
                }

                super.after(events)
            }
        })

        cleanPubPluginsCache()
    }



    ///清理插件的数据缓存
    private fun cleanPubPluginsCache() {
        CacheUtil.clean()
    }

    override fun runActivity(p0: Project) {
        execute(p0)
    }

}