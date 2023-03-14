pluginManagement {
    repositories {
        maven {
            url = uri("https://s01.oss.sonatype.org/service/local/repositories/comapollographql-1056/content/")
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
            url = uri("https://s01.oss.sonatype.org/service/local/repositories/comapollographql-1056/content/")
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "Rocket Reserver"
include(":app")
