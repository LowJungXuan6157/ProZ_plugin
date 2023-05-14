package com.lowjungxuan.proz.versionchecker.form

import com.intellij.openapi.components.service
import com.lowjungxuan.proz.versionchecker.socket.service.AppService
import kotlin.reflect.KProperty

///项目列表委托类
class MyProjectProviders {
    operator fun setValue(original: Any?,properties: KProperty<*>, value: List<String>){
        service<AppService>().projectNames = value
    }

    operator fun getValue(original: Any?, properties: KProperty<*>) : List<String> {
        return service<AppService>().projectNames
    }
}