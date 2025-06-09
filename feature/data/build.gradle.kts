plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.hilt)
    kotlin("kapt")
}


android {
    namespace = "com.openpay.test.feature.data"
    compileSdk = 35
}

dependencies {
    implementation(project(":feature:domain"))
    implementation(project(":network"))
    implementation(libs.retrofit.core)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}
