pluginManagement {
    repositories {
        maven(url = "https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        gradlePluginPortal()
    }
    plugins {
        id("org.jetbrains.kotlin.jvm") version "2.0.0"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
