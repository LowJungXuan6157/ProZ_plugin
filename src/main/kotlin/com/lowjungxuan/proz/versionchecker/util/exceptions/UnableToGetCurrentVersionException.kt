package com.lowjungxuan.proz.versionchecker.util.exceptions

class UnableToGetCurrentVersionException(dependency: String, e: Throwable) :
    Exception("Cannot get current version number for package: $dependency", e)