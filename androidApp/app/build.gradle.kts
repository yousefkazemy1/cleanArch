plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.example.cleanarchproject"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "com.example.cleanarchproject.HiltTestRunner"
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
    implementation(project(InternalModule.profile))
    implementation(project(InternalModule.auth))

    implementation(project(DataInternalModule.contentDataSource))

    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)

    testImplementation(UnitTest.jUnit4)
    testImplementation(UnitTest.mockk)
    testImplementation(UnitTest.coroutine)
    testImplementation(UnitTest.androidCoreTesting)
    testImplementation(UnitTest.turbineTest)

    androidTestImplementation(AndroidTest.jUnit4)
    androidTestImplementation(AndroidTest.androidTestRunner)
    androidTestImplementation(AndroidTest.androidJunit)
    androidTestImplementation(AndroidTest.mockk)
    androidTestImplementation(AndroidTest.hiltAndroidTest)
    kaptAndroidTest(AndroidTest.hiltTestCompiler)
    androidTestImplementation(platform(Compose.composeBom))
    androidTestImplementation(ComposeTest.uiJunit)
    debugImplementation(ComposeTest.uiTestManifest)
}