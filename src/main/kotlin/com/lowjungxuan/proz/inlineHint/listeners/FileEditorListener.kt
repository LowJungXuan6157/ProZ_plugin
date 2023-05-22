package com.lowjungxuan.proz.inlineHint.listeners

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.fileEditor.ex.FileEditorWithProvider
import com.intellij.openapi.vfs.VirtualFile
import com.lowjungxuan.proz.inlineHint.settings.SettingsState

class FileEditorListener : FileEditorManagerListener {
//    var settingsState: SettingsState = SettingsState.

    override fun fileOpenedSync(source: FileEditorManager, file: VirtualFile, editorsWithProviders: MutableList<FileEditorWithProvider>) {
        super.fileOpenedSync(source, file, editorsWithProviders)
    }
}