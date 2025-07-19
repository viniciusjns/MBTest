plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

apply("$rootDir/plugins/android.gradle")

android {
    namespace = "${Config.NAMESPACE}.features.exchanges.impl"

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests.apply {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation(project(Modules.CORE))
    implementation(project(Modules.EXCHANGES_PUBLIC))

    // default
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.okhttp)

    // koin
    implementation(libs.koin)
    implementation(libs.koin.compose)

    implementation(libs.androidx.compose.navigation)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.androidx.ui.test.junit4)

    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4.android)
    androidTestImplementation(libs.mockk.android)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}