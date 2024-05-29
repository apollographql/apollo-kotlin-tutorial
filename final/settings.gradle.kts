val javaVersion: String = System.getProperty("java.version")
if (javaVersion.substringBefore(".").toInt() < 11) {
    throw GradleException("Java 11 or higher is required to build this project. You are using Java $javaVersion.")
}

pluginManagement {
    repositories {
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "Rocket Reserver"
include(":app")
