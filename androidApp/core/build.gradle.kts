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
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    api(project(KorlinInternalModule.core))

    api(KotlinKtx.ktx)
    api(Material.appCompat)
    api(Material.material)

    api(LifeCycle.viewModel)
    api(Coroutines.coroutine)

    api(platform(Compose.composeBom))
    api(Compose.material)
    api(Compose.foundation)
    api(Compose.activity)
    api(Compose.navigation)
    api(Compose.hiltNavigation)
    api(Compose.coil)
    api(Compose.coilVideo)
    api(Compose.uiToolingPreview)
    api(Compose.lifecycle)
    api(Compose.constraintlayout)

    api(Media3.core)
    api(Media3.ui)
    api(Media3.hls)
    api(Media3.datasourceCronet)

    api(project(DataInternalModule.authDataSource))

    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)

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
}