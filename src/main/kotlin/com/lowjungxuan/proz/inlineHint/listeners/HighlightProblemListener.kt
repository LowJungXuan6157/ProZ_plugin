package com.lowjungxuan.proz.inlineHint.listeners

import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.codeInsight.daemon.impl.HighlightInfoFilter
import com.intellij.psi.PsiFile

class HighlightProblemListener : HighlightInfoFilter {
    override fun accept(highlightInfo: HighlightInfo, file: PsiFile?): Boolean {
        TODO("Not yet implemented")
    }

    companion object {
        val NAME: String = "HighlightProblemListener (Deprecated)"
    }
}