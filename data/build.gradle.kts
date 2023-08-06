plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.data"
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
    api(KotlinKtx.ktx)
    api(Network.retrofit)
    api(Network.retrofitConverterJson)
    api(Database.roomDatabase)
    kapt(Database.roomDatabaseAnnotationProcesssor)

    api(Coroutines.coroutine)

    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)

    testImplementation(UnitTest.jUnit4)
}