plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion

        testInstrumentationRunner = "com.example.auth.HiltTestRunner"
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = ConfigData.javaSource
        targetCompatibility = ConfigData.javaTarget
    }
    kotlinOptions {
        jvmTarget = ConfigData.jvmTarget
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
}

dependencies {
    implementation(project(InternalModule.core))

    testImplementation(UnitTest.jUnit4)
    testImplementation(UnitTest.mockito)
    testImplementation(UnitTest.coroutine)
    testImplementation(UnitTest.androidCoreTesting)
    testImplementation(UnitTest.turbineTest)

    androidTestImplementation(AndroidTest.jUnit4)
    androidTestImplementation(AndroidTest.hiltAndroidTest)
    kaptAndroidTest(AndroidTest.hiltTestCompiler)
    androidTestImplementation(AndroidTest.androidTestRunner)
    androidTestImplementation(AndroidTest.androidJunit)
    androidTestImplementation(AndroidTest.mockk)

    androidTestImplementation(platform(Compose.composeBom))
    androidTestImplementation(ComposeTest.uiJunit)
    debugImplementation(ComposeTest.uiTestManifest)

    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)
}
