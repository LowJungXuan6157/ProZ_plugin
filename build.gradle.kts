plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.21"
    id("org.jetbrains.intellij") version "1.13.3"
}

group = "com.lowjungxuan"
version = "0.0.1"

repositories {
    mavenCentral()
    google()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

intellij {
//  IntelliJ Idea
//  version.set("2022.2.5")
//  type.set("IC")

    /// Android Studio
    version.set("2022.2.1.18")
    type.set("AI")
    plugins.set(
        listOf(
            "yaml",
            "Dart:222.4560",
            "io.flutter:73.0.2",
            "org.intellij.plugins.markdown",
            "terminal", "java"
        )
    )
}

kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0") {
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "org.slf4j")
        exclude(group = "org.jetbrains.kotlin")
    }
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    {
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "org.slf4j")
        exclude(group = "org.jetbrains.kotlin")
    }
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.5")
    {
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "org.slf4j")
        exclude(group = "org.jetbrains.kotlin")
    }
    implementation("cn.hutool:hutool-all:5.8.15") {
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "org.slf4j")
        exclude(group = "org.jetbrains.kotlin")
    }
    implementation("org.smartboot.socket:aio-core:1.6.3") {
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "org.slf4j")
        exclude(group = "org.jetbrains.kotlin")
    }
    implementation("com.alibaba.fastjson2:fastjson2:2.0.25")
    {
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "org.slf4j")
        exclude(group = "org.jetbrains.kotlin")
    }
    implementation("com.alibaba.fastjson2:fastjson2-kotlin:2.0.25")
    {
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "org.slf4j")
        exclude(group = "org.jetbrains.kotlin")
    }
    implementation("org.apache.commons:commons-lang3:3.12.0")
    {
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "org.slf4j")
        exclude(group = "org.jetbrains.kotlin")
    }
    implementation("com.google.code.gson:gson:2.10.1") {
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "org.slf4j")
        exclude(group = "org.jetbrains.kotlin")
    }
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2") {
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "org.slf4j")
        exclude(group = "org.jetbrains.kotlin")
    }
    implementation("org.xerial:sqlite-jdbc:3.40.1.0") {
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "org.slf4j")
        exclude(group = "org.jetbrains.kotlin")
    }
    implementation("com.aallam.openai:openai-client:3.2.0") {
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "org.slf4j")
        exclude(group = "org.jetbrains.kotlin")
    }
    implementation("io.ktor:ktor-client-cio-jvm:2.2.4") {
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "org.slf4j")
        exclude(group = "org.jetbrains.kotlin")
    }
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
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        compilerOptions.languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_9)
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

    runIde {
        autoReloadPlugins.set(true)
        jvmArgs = listOf("-XX:+AllowEnhancedClassRedefinition")
    }

    buildSearchableOptions {
        enabled = false
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}
configurations.all {
    exclude(group = "io.ktor", module = "kotlinx-coroutines-jdk8")
    exclude(group = "io.ktor", module = "kotlinx-coroutines-core")
    exclude(group = "com.aallam.openai", module = "kotlinx-coroutines-jdk8")
    exclude(group = "com.aallam.openai", module = "kotlinx-coroutines-core")
}