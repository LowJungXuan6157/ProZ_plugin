package com.lowjungxuan.proz.versionchecker.util

import com.lowjungxuan.proz.versionchecker.model.PubVersionDataModel
import com.lowjungxuan.proz.versionchecker.services.PubService
import com.lowjungxuan.proz.versionchecker.services.ServiceCreate

object ApiService {
    fun getPluginDetail(name: String): PubVersionDataModel? {
        try {
            return ServiceCreate.create<PubService>().callPluginDetails(name).execute().body()
        } catch (_: Exception) {}
        return null
    }
}