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
val yarn_mappings: String by project
val midnightlib_version: String by project

// val archives_base_name: String

base {
    // archives_base_name = String by project
    archivesName.set(project.property("archives_base_name") as String)
}

repositories {
    mavenCentral()

    maven(url = "https://api.modrinth.com/maven") {
        name = "Modrinth Maven"
    }

    maven("https://maven.wispforest.io") {
        name = "WispForest Maven"
    }

    maven(url = "https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1") {
        name = "DevAuth Maven"
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")
    // mappings(loom.officialMojangMappings())
    mappings("net.fabricmc:yarn:${yarn_mappings}")
    modImplementation("net.fabricmc:fabric-loader:$loader_version")

    // Fabric API
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabric_version")
    //implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.11.0+kotlin.2.0.0")

    // owo-config
    modImplementation("io.wispforest:owo-lib:${properties["owo_version"]}")
    annotationProcessor("io.wispforest:owo-lib:${properties["owo_version"]}")

    // DevAuth
    modRuntimeOnly("me.djtheredstoner:DevAuth-fabric:${properties["devauth_version"]}")
}

tasks.withType<ProcessResources>() {
    inputs.property("mod_version", mod_version)
    inputs.property("minecraft_version", minecraft_version)
    inputs.property("loader_version", loader_version)
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "mod_version" to mod_version,
            "minecraft_version" to minecraft_version,
            "loader_version" to loader_version,
            "version" to mod_version
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