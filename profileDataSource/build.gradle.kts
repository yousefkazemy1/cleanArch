plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.profiledatasource"
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
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
    api(project(KorlinInternalModule.profileDomain))

    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)

    testImplementation(UnitTest.jUnit5)
    testImplementation(UnitTest.mockito)
    testImplementation(UnitTest.coroutine)
    testImplementation(UnitTest.okhttpMockWebServer)
}