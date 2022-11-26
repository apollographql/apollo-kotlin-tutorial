plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.apollographql.apollo3").version("3.7.1")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.rocketreserver"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
//    compileOptions {
//        // Flag to enable support for the new language APIs
//        isCoreLibraryDesugaringEnabled = true
//    }
    namespace = "com.example.rocketreserver"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("io.coil-kt:coil:1.4.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.security:security-crypto:1.1.0-alpha04")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")

    implementation("com.apollographql.apollo3:apollo-runtime")
    implementation("com.apollographql.apollo3:apollo-http-cache:3.7.1")

//    implementation("com.apollographql.apollo3:apollo-normalized-cache:3.7.1") // for memory cache
//    implementation("com.apollographql.apollo3:apollo-normalized-cache-sqlite:3.7.1") // for SQL cache

//    // define a BOM and its version
//    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
//
//    // define any required OkHttp artifacts without version
//    implementation("com.squareup.okhttp3:okhttp")
//    implementation("com.squareup.okhttp3:logging-interceptor")
}

apollo {
    packageName.set("com.example.rocketreserver")
}
