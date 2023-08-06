import org.gradle.api.JavaVersion

object ConfigData {
    const val compileSdkVersion = 33
    const val minSdkVersion = 21
    const val targetSdkVersion = 33
    const val versionCode = 1
    const val versionName = "1.0"

    val javaSource = JavaVersion.VERSION_1_8
    val javaTarget = JavaVersion.VERSION_1_8
    const val jvmTarget = "1.8"
}