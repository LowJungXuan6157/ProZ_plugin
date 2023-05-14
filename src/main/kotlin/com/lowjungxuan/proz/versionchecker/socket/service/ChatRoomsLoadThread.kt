package com.lowjungxuan.proz.versionchecker.socket.service

import com.intellij.openapi.components.service

class ChatRoomsLoadThread : Runnable {
    override fun run() {
        service<AppService>().loadRooms()
    }
}