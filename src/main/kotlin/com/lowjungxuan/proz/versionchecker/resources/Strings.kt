package com.lowjungxuan.proz.versionchecker.resources

class Strings {

    companion object {
        const val annotationDescription = "Latest available version is:"
        const val updateFixDescription = "Update %s"
        const val updateAllFixDescription =
            "Update\u200B all" // zero-length whitespace to place "Update all" below other fixes
        const val goToPubDevFixDescription = "\u200BSee %s on pub.dev"
        const val fixFamilyName = "Update package"
        const val settingsTitle = "Flutter Pub Version Checker"
        const val settingsIncludePreReleasesTitle = "Include prerelease and preview versions"
        const val settingsIncludePreReleasesTooltip = "Only stable versions are included if not set"
    }
}