package com.lowjungxuan.proz.versionchecker.services.cache

import com.intellij.openapi.components.service
import com.lowjungxuan.proz.versionchecker.socket.service.AppService

/**
 * 在idea启动的时候,实现自动登录的功能
 */
class UserRunStartService: Runnable {

    override fun run() {
       service<AppService>().login()
    }
}