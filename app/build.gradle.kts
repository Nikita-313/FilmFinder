plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.cinetech.filmfinder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cinetech.filmfinder"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val API_KEY: String = project.findProperty("API_KEY") as String? ?: ""
        buildConfigField("String","API_KEY", API_KEY)
    }

    buildTypes {
        release {
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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.coil)

    implementation(libs.retrofit2.converter.gson)
    implementation(libs.retrofit2)

    implementation(libs.recyclerview)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.dagger)
    implementation(project(":data"))
    implementation(project(":domain"))
    kapt(libs.dagger.compiler)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(libs.fragment.ktx)
    implementation(libs.fragment)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}