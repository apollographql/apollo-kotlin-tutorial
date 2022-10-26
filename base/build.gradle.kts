plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.apollographql.apollo3").version("3.6.2")
}

android {
    namespace = "com.example.rocketreserver.base"
    compileSdk = 32

    defaultConfig {
        minSdk = 23
        targetSdk = 32
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("com.apollographql.apollo3:apollo-runtime")
}

apollo {
    service ("rocketreserver") {
        generateApolloMetadata.set(true)
        packageName.set("com.example.rocketreserver")
    }
}
