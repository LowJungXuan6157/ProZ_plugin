package com.lowjungxuan.proz.versionchecker.util

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import com.lowjungxuan.proz.versionchecker.model.PubVersionDataModel
import java.util.concurrent.TimeUnit


object CacheUtil {

    private val cache: Cache<String, PubVersionDataModel> = CacheBuilder.newBuilder()
        .initialCapacity(1)
        .maximumSize(1000)
        .expireAfterWrite(1, TimeUnit.DAYS)
        .build()


    fun getCatch(): Cache<String, PubVersionDataModel> {
        return cache
    }

    fun set(name: String, model: PubVersionDataModel) {
        cache.put(name, model)
    }


    fun remove(name: String) {
        cache.invalidate(name)
    }

    fun clean() {
        cache.invalidateAll()
    }
}