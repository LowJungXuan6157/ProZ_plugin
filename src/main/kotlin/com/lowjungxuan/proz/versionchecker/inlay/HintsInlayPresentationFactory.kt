package com.lowjungxuan.proz.versionchecker.inlay

import com.intellij.codeInsight.hints.presentation.InlayPresentation
import com.intellij.codeInsight.hints.presentation.PresentationFactory
import java.awt.Image
import java.awt.Point
import java.awt.event.MouseEvent
import java.io.File
import javax.swing.Icon
import javax.swing.ImageIcon


/**
 * 悬浮提示的工厂类
 * 提供了一些的悬浮提示工具函数
 */
typealias InlayPresentationClickHandle = (MouseEvent, Point) -> Unit


class HintsInlayPresentationFactory(private val factory: PresentationFactory) {

    private fun InlayPresentation.clickHandle(handle: InlayPresentationClickHandle?): InlayPresentation {
        return factory.mouseHandling(this, clickListener = handle, null)
    }

    fun simpleText(text: String, tip: String?, handle: InlayPresentationClickHandle?): InlayPresentation {
        return text(text).bg().addTip(tip ?: text).clickHandle(handle)
    }

    // 展示一个文本
    private fun text(text: String?): InlayPresentation = factory.smallText(text ?: "?")

    // 添加提示文本
    private fun InlayPresentation.addTip(text: String): InlayPresentation = factory.withTooltip(text, this)

    // 添加一个背景颜色
    private fun InlayPresentation.bg(): InlayPresentation = factory.roundWithBackgroundAndSmallInset(this)


    fun getImageWithPath(path: String, basePath: String): InlayPresentation? {
        return try {
            if (!File(path).exists()) {
                return null
            }
            val i = ImageIcon(path).image.getScaledInstance(16, 16, Image.SCALE_SMOOTH)
            val imageIcon: Icon = ImageIcon(i)
            return factory.icon(imageIcon).addTip(basePath)
        } catch (e: Exception) {
            println("$path 读取图片失败:$e")
            null
        }
    }

}