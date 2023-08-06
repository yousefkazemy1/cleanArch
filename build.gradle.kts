buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Kotlin.gradlePlugin)
        classpath(Kotlin.serialization)
        classpath(Gradle.tools)
        classpath(Hilt.hiltPlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.21")
    }
}