plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics.plugin)
    id("kotlinx-serialization")
}

android {
    namespace = "ru.itis.second_sem"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.itis.second_sem"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = rootProject.extra.get("versionCode") as Int
        versionName = rootProject.extra.get("versionName") as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "OPEN_WEATHER_API_URL", "\"https://api.openweathermap.org/data/2.5/\"")
        buildConfigField("int", "DB_VERSION", "1")

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
    implementation(libs.androidx.navigation.fragment.ktx)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.okhttp)
    implementation(libs.http.logging.interceptor)
    implementation(libs.compiler.compose)
    implementation(libs.gson)
    implementation(libs.room)
    implementation(libs.room.ktx)
    implementation(libs.androidx.fragment)
    implementation(libs.hilt)
    implementation(libs.converter.gson)
    implementation(libs.navigation.compose)
    implementation(libs.serialization)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.remote.config)
    implementation(libs.play.services.cloud.messaging)
    implementation(project(":auth"))
    ksp(libs.hilt.compiler)
    ksp(libs.room.ksp)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(kotlin("script-runtime"))
}