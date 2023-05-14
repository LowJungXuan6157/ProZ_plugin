package com.lowjungxuan.proz.versionchecker.util

import java.awt.Dimension
import java.awt.Toolkit

object ScreenUtil {

    private fun getScreenSize(): Dimension {
        return Toolkit.getDefaultToolkit().screenSize
    }



}