plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    kotlin("kapt")
}


android {
    namespace = "com.openpay.test.feature.data"
    compileSdk = 35

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(project(":feature:domain"))
    implementation(project(":network"))
    implementation(libs.retrofit.core)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlin.serialization)

    testImplementation(libs.junit)
    testImplementation(libs.mockK)
}
