buildscript {
    val compose_version by extra("1.1.0-beta01")
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.1.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

