object KotlinKtx {
    val ktx = "androidx.core:core-ktx:${Versions.ktx}"
}

object LifeCycle {
    val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
}

object Material {
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val material = "com.google.android.material:material:${Versions.material}"
}

object Compose {
    val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    val material = "androidx.compose.material:material"
    val foundation = "androidx.compose.foundation:foundation"
    val activity = "androidx.activity:activity-compose:${Versions.composeActivity}"
    val navigation = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
    val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigation}"
    val coil = "io.coil-kt:coil-compose:${Versions.coil}"
    val coilVideo = "io.coil-kt:coil-video:${Versions.coil}"
    val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeUiTooling}"
    val lifecycle = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.composeLifecycle}"
    val constraintlayout = "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraintLayout}"
}

object Hilt {
    val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltPlugin}"

//    val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
//    val hiltViewModelCompiler = "androidx.hilt:hilt-compiler:1.0.0"
}

object Coroutines {
    val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
    val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"
}

object Network {
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitConverterJson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
}

object Database {
    val roomDatabase = "androidx.room:room-runtime:${Versions.room}"
    val roomDatabaseAnnotationProcesssor = "androidx.room:room-compiler:${Versions.room}"
}

object InternalModule {
    val core = ":androidApp:core"
    val profile = ":androidApp:profile"
    val auth = ":androidApp:auth"
}

object KorlinInternalModule {
    val core = ":core"
    val authDomain = ":authDomain"
    val contentDomain = ":contentDomain"
    val profileDomain = ":profileDomain"
}

object DataInternalModule {
    val data = ":data"
    val authDataSource = ":authDataSource"
    val contentDataSource = ":contentDataSource"
    val profileDataSource = ":profileDataSource"
}

object UnitTest {
    val jUnit5 = "org.junit.jupiter:junit-jupiter:${Versions.jUnit}"
    val jUnit4 = "junit:junit:${Versions.jUnit4}"
    val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineTest}"
    val androidCoreTesting = "androidx.arch.core:core-testing:${Versions.androidCoreTesting}"
    val okhttpMockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okhttpMockWebServer}"
    val turbineTest = "app.cash.turbine:turbine:${Versions.turbineTest}"
    val mockk = "io.mockk:mockk:${Versions.mockk}"
    val kotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlinTestJunit}"
}

object AndroidTest {
    val jUnit4 = "junit:junit:${Versions.jUnit4}"
    val hiltAndroidTest = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    val hiltTestCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltPlugin}"
    val androidTestRunner = "androidx.test:runner:${Versions.androidTestRunner}"
    val androidJunit = "androidx.test.ext:junit:${Versions.androidJunit}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val mockk = "io.mockk:mockk-android:${Versions.mockkAndroid}"
}

object ComposeTest {
    val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
    val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    val uiJunit = "androidx.compose.ui:ui-test-junit4"
    val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeUiTooling}"
    val uiTestManifest = "androidx.compose.ui:ui-test-manifest"
}

object ExoPlayer {
    val core = "com.google.android.exoplayer:exoplayer-core:${Versions.exoPlayer}"
    val ui = "com.google.android.exoplayer:exoplayer-ui:${Versions.exoPlayer}"
}

object Media3 {
    val core = "androidx.media3:media3-exoplayer:${Versions.media3Version}"
    val ui = "androidx.media3:media3-ui:${Versions.media3Version}"
    val hls = "androidx.media3:media3-exoplayer-hls:${Versions.media3Version}"
    val datasourceCronet = "androidx.media3:media3-datasource-cronet:${Versions.media3Version}"
}

object Common {
    val gson = "com.google.code.gson:gson:${Versions.gson}"
}