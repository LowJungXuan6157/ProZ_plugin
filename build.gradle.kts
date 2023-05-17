plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    id("org.jetbrains.intellij") version "1.13.3"
}

group = "com.lowjungxuan"
version = "0.0.1"

dependencies {
    implementation("io.sentry:sentry:5.7.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}

repositories {
    mavenCentral()
}

intellij {
    pluginName.set("ProZ")
//    intellij idea
    version.set("2022.2.5")
    type.set("IC") // Target IDE Platform
//    android studio
//    version.set("2022.2.1.18")
//    type.set("AI")
    updateSinceUntilBuild.set(true)
    downloadSources.set(true)

    plugins.set(listOf("org.jetbrains.kotlin", "org.jetbrains.plugins.yaml", "com.intellij.java"))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("232.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
