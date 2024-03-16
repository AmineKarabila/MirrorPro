plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 34
    namespace = "com.example.magicmirror"
    defaultConfig {
        applicationId = "com.example.magicmirror"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3" // Stelle sicher, dass diese Version mit deinem aktuellen Setup kompatibel ist
    }
}

dependencies {
    implementation ("com.google.code.gson:gson:2.8.8")

    implementation ("androidx.compose.foundation:foundation:1.0.0")

    implementation("androidx.navigation:navigation-compose:2.4.0") // Verwenden Sie die aktuellste Version
    implementation("com.google.android.material:material:1.4.0") // Material Design für Android
    implementation("androidx.compose.material:material:1.0.1") // Compose Material Design



    implementation ("com.github.bumptech.glide:glide:4.13.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.13.0")
    implementation("com.github.bumptech.glide:okhttp3-integration:4.13.0")

    implementation("io.coil-kt:coil-compose:2.1.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("androidx.compose.material3:material3:1.0.0-alpha16") // Achte darauf, die neueste verfügbare Version zu verwenden
    implementation("androidx.navigation:navigation-compose:2.5.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.1")
}
