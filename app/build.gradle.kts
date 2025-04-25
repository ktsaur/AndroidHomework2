plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "ru.itis.second_sem"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.itis.second_sem"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        defaultConfig {
            buildConfigField("String", "OPEN_WEATHER_API_URL", "\"https://api.openweathermap.org/data/2.5/\"")
        }
        defaultConfig {
            buildConfigField("String", "OPEN_WEATHER_API_KEY", "\"d9990220ce61da50cdc4fa13b0ae4a84\"")
        }
    }

    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isShrinkResources = false
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.retrofit)
    implementation(libs.glide)
    implementation(libs.viewbindingpropertydelegate.noreflection)
    implementation(libs.bundles.compose.base)
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.androidx.material3.android)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.okhttp)
    implementation(libs.http.logging.interceptor)
    implementation("androidx.compose.compiler:compiler:1.5.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation(libs.androidx.fragment)
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.converter.gson)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(kotlin("script-runtime"))
}