plugins {
    id("fabric-loom") version "1.6-SNAPSHOT"
    id("maven-publish")
    id("org.jetbrains.kotlin.jvm") version "2.0.0"
    //kotlin("jvm") version "2.0.0"
}

val mod_version: String by project
val maven_group: String by project
val minecraft_version: String by project
val loader_version: String by project
val fabric_version: String by project

// val archives_base_name: String

base {
    // archives_base_name = String by project
    archivesName.set(project.property("archives_base_name") as String)
}

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:$loader_version")

    // Fabric API
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabric_version")
    //implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.11.0+kotlin.2.0.0")
}

tasks.withType<ProcessResources>() {
    inputs.property("version", mod_version)
    inputs.property("minecraft_version", minecraft_version)
    inputs.property("loader_version", loader_version)
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "mod_version" to mod_version,
            "minecraft_version" to minecraft_version,
            "loader_version" to loader_version
        )
    }
}

var targetJavaVersion = 21
tasks.withType<JavaCompile>().configureEach {
    this.options.encoding = "UTF-8"
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible()) {
        this.options.release = targetJavaVersion
    }
}

java {
    var javaVersion = JavaVersion.toVersion(targetJavaVersion)
    if (JavaVersion.current() < javaVersion) {
        toolchain {
            languageVersion = JavaLanguageVersion.of(targetJavaVersion)
        }
    }

    withSourcesJar()
}


tasks.jar {
    val archives_base_name: String by project
    from("LICENSE") {
        rename { "${it}_${archives_base_name}"}
    }
}

publishing {
    val archives_base_name: String by project
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = archives_base_name
        }
    }
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}