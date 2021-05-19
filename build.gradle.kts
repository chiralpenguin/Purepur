import xyz.jpenilla.toothpick.gitCmd
import xyz.jpenilla.toothpick.toothpick

plugins {
    `java-library`
    `maven-publish`
    id("xyz.jpenilla.toothpick") version "1.0.0-SNAPSHOT"
}

toothpick {
    forkName = "Purepur"
    groupId = "com.purityvanilla.purepur"
    forkUrl = "https://github.com/chiralpenguin/Purepur"
    val versionTag = System.getenv("BUILD_NUMBER")
        ?: "\"${gitCmd("rev-parse", "--short", "HEAD").output}\""
    forkVersion = "git-$forkName-$versionTag"

    minecraftVersion = "1.16.5"
    nmsPackage = "1_16_R3"
    nmsRevision = "R0.1-SNAPSHOT"

    upstream = "Paper"
    upstreamBranch = "origin/master"

    server {
        project = project(":$forkNameLowercase-server")
        patchesDir = rootProject.projectDir.resolve("patches/server")
    }
    api {
        project = project(":$forkNameLowercase-api")
        patchesDir = rootProject.projectDir.resolve("patches/api")
    }
}

subprojects {
    repositories {
        maven("https://nexus.velocitypowered.com/repository/velocity-artifacts-snapshots/")
    }

    java {
        sourceCompatibility = JavaVersion.toVersion(8)
        targetCompatibility = JavaVersion.toVersion(8)
    }

    publishing.repositories.maven {
        url = uri("https://repo.pl3x.net/snapshots")
        credentials(PasswordCredentials::class)
    }
}
