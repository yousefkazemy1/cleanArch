plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = ConfigData.javaTarget
    targetCompatibility = ConfigData.javaTarget
}

dependencies {
    api(project(KorlinInternalModule.core))
    testImplementation(UnitTest.jUnit4)
    testImplementation(UnitTest.mockk)
    testImplementation(UnitTest.coroutine)
    testImplementation (UnitTest.kotlinTestJunit)
}