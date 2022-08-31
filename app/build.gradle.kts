import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.apollographql.apollo3").version("3.5.0")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.example.rocketreserver"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}



tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


dependencies {
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("io.coil-kt:coil:2.2.0")
    implementation("io.coil-kt:coil-compose:2.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.1")
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.security:security-crypto:1.1.0-alpha03")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_version"]}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")


    implementation("com.apollographql.apollo3:apollo-runtime:3.5.0")
    implementation("com.apollographql.apollo3:apollo-normalized-cache:3.5.0")

    //Compose
    // Integration with activities
    implementation ("androidx.activity:activity-compose:1.5.1")
    // Compose Material Design
    implementation ("androidx.compose.material:material:1.2.1")
    // Animations
    implementation ("androidx.compose.animation:animation:1.2.1")
    // Tooling support (Previews, etc.)
    implementation ("androidx.compose.ui:ui-tooling:1.2.1")
    // Integration with ViewModels
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    // UI Tests
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.2.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra["compose_version"]}")

}

apollo {
    packageName.set("com.example.rocketreserver")
}
