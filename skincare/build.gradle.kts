plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.zrcoding.skincare"

    compileSdk = 33

    defaultConfig {
        applicationId = "com.zrcoding.compose"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        compileSdkPreview = "UpsideDownCake"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isDebuggable = true
        }
    }
    flavorDimensions += "type"
    productFlavors {
        create("mock") {
            dimension = "type"
        }
        create("real") {
            dimension = "type"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    // project dependencies
    implementation(project(":ui-kit"))
    implementation(project(":common"))
    implementation(project(":data"))

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)

    // Activity
    implementation(libs.androidx.activity)
    // LifeCycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.extensions)

    // Navigation
    implementation(libs.androidx.navigation)
    implementation(libs.dagger.hilt.navigation)

    // ViewPager
    implementation(libs.google.accompanist.pager)
    implementation(libs.google.accompanist.pager.indicators)

    // di: hilt
    implementation(libs.google.dagger.hilt.android)
    kapt(libs.google.dagger.hilt.android.compiler)


    // Mockk framework
    testImplementation(libs.io.mockk)
    // Coroutines framework
    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}