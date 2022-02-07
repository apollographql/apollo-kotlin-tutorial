rootProject.name="apollo-android-tutorial"
include(":app")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://s01.oss.sonatype.org/service/local/repositories/comapollographql-1004/content/")
    }
}
