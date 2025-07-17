plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply("$rootDir/plugins/android.gradle")

android {
    namespace = "${Config.NAMESPACE}.features.exchanges"

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    
    implementation(libs.retrofit.gson.converter)

}