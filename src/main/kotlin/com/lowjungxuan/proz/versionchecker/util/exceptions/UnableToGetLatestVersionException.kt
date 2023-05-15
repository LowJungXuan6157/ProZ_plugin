package com.lowjungxuan.proz.versionchecker.util.exceptions

class UnableToGetLatestVersionException(dependency: String, e: Throwable) :
    Exception("Cannot get latest version number for package: $dependency", e)
