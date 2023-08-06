plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = ConfigData.javaTarget
    targetCompatibility = ConfigData.javaTarget
}
dependencies {
    testImplementation(UnitTest.jUnit5)
    testImplementation(UnitTest.mockk)

    api(Coroutines.coroutineCore)
    implementation(Common.gson)
}

tasks.test {
    useJUnitPlatform()
}
