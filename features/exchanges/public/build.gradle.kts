plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
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
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

}