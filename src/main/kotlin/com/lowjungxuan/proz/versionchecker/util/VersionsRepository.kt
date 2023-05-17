package com.lowjungxuan.proz.versionchecker.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.lowjungxuan.proz.versionchecker.dto.Response
import com.lowjungxuan.proz.versionchecker.settings.AppSettingsState
import com.lowjungxuan.proz.versionchecker.util.exceptions.UnableToGetLatestVersionException
import java.io.IOException

const val PUB_API_URL = "https://pub.dartlang.org/api/packages/"

class VersionsRepository(
    private val httpClient: DependencyHttpClient,
    private val appSettingsState: AppSettingsState,
) {

    private val dependencyList = mutableListOf<Dependency>()
    private var includePreReleases = false

    private val mapper = ObjectMapper()
        .registerModule(
            KotlinModule.Builder()
                .withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, false)
                .configure(KotlinFeature.NullToEmptyMap, false)
                .configure(KotlinFeature.NullIsSameAsDefault, false)
                .configure(KotlinFeature.SingletonSupport, false)
                .configure(KotlinFeature.StrictNullChecks, false)
                .build()
        )
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun getLatestVersion(packageName: String): String {
        clearDependencyListIfRequired()

        val cachedDependency = dependencyList.find { it.packageName == packageName }
        return if (cachedDependency != null) {
            cachedDependency.version
        } else {
            val dependencyVersion = fetchDependencyVersion(packageName)
            dependencyList.add(Dependency(packageName, dependencyVersion))
            dependencyVersion
        }
    }

    private fun clearDependencyListIfRequired() {
        if (includePreReleases != appSettingsState.includePreRelease) {
            includePreReleases = appSettingsState.includePreRelease
            dependencyList.clear()
        }
    }

    private fun fetchDependencyVersion(packageName: String): String {
        try {
            val jsonResponse = httpClient.getContentAsString(PUB_API_URL + packageName)
            val response = parseResponse(jsonResponse)
            return getLatestVersion(response)
        } catch (e: IOException) {
            throw UnableToGetLatestVersionException(packageName, e)
        }
    }

    private fun getLatestVersion(response: Response): String {
        val includePreReleases = appSettingsState.includePreRelease
        return if (includePreReleases) {
            response.versions.maxByOrNull { it.published }!!
        } else {
            response.latest
        }.version.trim()
    }

    private fun parseResponse(responseString: String): Response {
        return mapper.readValue(responseString)
    }

}

private data class Dependency(
    val packageName: String,
    val version: String
)