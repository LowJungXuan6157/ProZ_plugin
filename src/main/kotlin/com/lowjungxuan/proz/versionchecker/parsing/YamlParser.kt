package com.lowjungxuan.proz.versionchecker.parsing

import com.lowjungxuan.proz.versionchecker.dto.DependencyDescription
import com.lowjungxuan.proz.versionchecker.util.VersionsRepository
import com.lowjungxuan.proz.versionchecker.util.exceptions.UnableToGetCurrentVersionException
import com.lowjungxuan.proz.versionchecker.util.exceptions.UnableToGetLatestVersionException
import com.lowjungxuan.proz.versionchecker.util.exceptions.UnableToGetPackageNameException
import com.lowjungxuan.proz.versionchecker.util.getDependencies
import io.sentry.Sentry

class YamlParser(
    private val fileContent: String,
    private val versionsRepository: VersionsRepository
) {

    fun inspectFile(): List<DependencyDescription> {
        return getDependencyList()
    }

    private fun getDependencyList(): List<DependencyDescription> {
        val dependencies: List<DependencyDescription> = getAllDependenciesList()
        return getNotMatchingDependenciesList(dependencies)
    }

    private fun getAllDependenciesList(): List<DependencyDescription> {
        return fileContent.getDependencies().mapNotNull {
            try {
                val latestVersion = versionsRepository.getLatestVersion(it.packageName)
                DependencyDescription(it, latestVersion)
            } catch (e: UnableToGetLatestVersionException) {
                Sentry.captureException(e)
                null
            } catch (e: UnableToGetCurrentVersionException) {
                Sentry.captureException(e)
                null
            } catch (e: UnableToGetPackageNameException) {
                Sentry.captureException(e)
                null
            }
        }
    }

    private fun getNotMatchingDependenciesList(dependencies: List<DependencyDescription>): List<DependencyDescription> {
        return dependencies.mapNotNull {
            if (it.latestVersion != it.dependency.currentVersion) it else null
        }
    }
}