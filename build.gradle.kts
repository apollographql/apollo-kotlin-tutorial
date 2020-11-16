buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter {
            content {
                includeGroup("org.jetbrains.trove4j")
            }
        }
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

