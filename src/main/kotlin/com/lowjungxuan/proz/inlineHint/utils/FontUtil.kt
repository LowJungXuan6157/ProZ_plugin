package com.lowjungxuan.proz.inlineHint.utils

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.EditorFontType
import com.intellij.util.ui.UIUtil
import java.awt.Font


object FontUtil {
    fun getActiveFont(editor: Editor): Font {
        val fontType = EditorFontType.PLAIN
        val toolTipFont = UIUtil.getToolTipFont()
        return UIUtil.getFontWithFallback(
            toolTipFont.fontName,
            fontType.ordinal,
            12
        )
    }
}