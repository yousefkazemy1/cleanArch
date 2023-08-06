plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.authdatasource"
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = ConfigData.javaTarget
        targetCompatibility = ConfigData.javaTarget
    }
    kotlinOptions {
        jvmTarget = ConfigData.jvmTarget
    }
}

dependencies {
    api(project(DataInternalModule.data))
    api(project(KorlinInternalModule.authDomain))

    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)

    testImplementation(UnitTest.jUnit4)
    testImplementation(UnitTest.mockito)
    testImplementation(UnitTest.coroutine)
    testImplementation(UnitTest.okhttpMockWebServer)

    androidTestImplementation(AndroidTest.jUnit4)
    androidTestImplementation(AndroidTest.androidJunit)
    androidTestImplementation(AndroidTest.espresso)
}