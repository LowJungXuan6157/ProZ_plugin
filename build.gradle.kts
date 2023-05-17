import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    id("org.jetbrains.intellij") version "1.13.3"
    // gradle-changelog-plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
    id("org.jetbrains.changelog") version "2.0.0"
    // detekt linter - read more: https://detekt.github.io/detekt/gradle.html
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
    // ktlint linter - read more: https://github.com/JLLeitschuh/ktlint-gradle
    id("org.jlleitschuh.gradle.ktlint") version "11.3.2"
}

group = "com.lowjungxuan"
version = "0.0.1"

dependencies {
    implementation("io.sentry:sentry:5.7.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.0-RC3")
    testImplementation(platform("org.junit:junit-bom:5.10.0-M1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.1")
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

detekt {
    config = files("./detekt-config.yml")
    buildUponDefaultConfig = true
}

tasks {
    withType<Detekt> {
        reports {
            html.required.set(true) // observe findings in your browser with structure and code snippets
            xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
            txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
        }
    }
    withType<Detekt> {
        jvmTarget = "1.8"
    }
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
