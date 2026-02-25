import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "me.marthia.app.boomgrad"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "me.marthia.app.boomgrad.guide"
        minSdk = 28
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}

dependencies {

    // core
    implementation(libs.androidx.core.ktx)

    // viewmodel
    implementation(libs.lifecycle.viewmodel.ktx)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.util)
    implementation(libs.compose.animation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.activity)
    implementation(libs.compose.foundation)
    implementation(libs.compose.foundation.layout)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.compose.runtime.saveable)
    implementation(libs.compose.material3)
    implementation(libs.constraintlayout.compose)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.appcompat) // neshan maps depends on this DO NOT DELETE
    implementation(libs.splashscreen)

    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.test.manifest)
    implementation(libs.lifecycle.viewmodel.compose)


    // kotlin serialization
    implementation(libs.kotlin.json)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.client.content)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.json)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // sms retriever
    implementation(libs.gms)
    implementation(libs.gms.phone)

    // koin
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.annotations)
    implementation(libs.koin.androidx.worker)
    ksp(libs.koin.ksp.compiler)


    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Paging
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)
    implementation(libs.paging.common.ktx)

    // WorkManager
    implementation(libs.work.runtime.ktx)

    // timber
    implementation(libs.timber)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    // Navigation
    implementation(libs.navigation.compose)
    implementation(libs.raamcosta.navigation.core)
    ksp(libs.raamcosta.navigation.ksp)
    // V2 only: for bottom sheet destination support, also add
    implementation(libs.raamcosta.navigation.bottomsheet)

    // Neshan Maps SDK
    implementation(libs.mobile.sdk)
    implementation(libs.services.sdk)
    implementation(libs.common.sdk)

    //Play Services
    implementation(libs.gms.gcm)
    implementation(libs.gms.location)

    implementation("com.google.accompanist:accompanist-permissions:0.37.3")
    implementation("androidx.datastore:datastore-preferences:1.1.4")

    implementation(libs.androidx.constraintlayout)

    // test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}