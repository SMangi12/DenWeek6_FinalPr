plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
//    alias(libs.plugins.kotlin.android)

    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.10"

}



kotlin {
    jvmToolchain(24) // ✅ Align with Java 17
}

android {
    namespace = "com.example.deninternship_week5"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.deninternship_week5"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.03.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)



    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose")
    implementation("androidx.activity:activity-compose:1.10.1")
// or latest
    implementation("androidx.compose.ui:ui:1.8.3")
    implementation("androidx.compose.material:material:1.8.3")

    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("androidx.core:core-ktx:1.16.0")



    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.firebase:firebase-auth:22.1.1")
    implementation("com.google.firebase:firebase-database:22.0.0")
    // Firebase Cloud Messaging
    implementation("com.google.firebase:firebase-messaging:25.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2")
    implementation("androidx.navigation:navigation-compose:2.9.3")
    implementation(platform("com.google.firebase:firebase-bom:34.1.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.squareup.okhttp3:okhttp:5.1.0")
// For HTTP requests if needed
    implementation("com.google.code.gson:gson:2.13.1")

// AdMob (Google Mobile Ads)
    implementation ("com.google.android.gms:play-services-ads:24.5.0")




    implementation ("androidx.room:room-runtime:2.7.2")
    annotationProcessor ("androidx.room:room-compiler:2.7.2")

// Lifecycle components
    implementation ("androidx.lifecycle:lifecycle-livedata:2.9.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.9.2")

}