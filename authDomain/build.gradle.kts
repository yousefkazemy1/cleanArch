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
    testImplementation(UnitTest.jUnit5)
    testImplementation(UnitTest.mockito)
    testImplementation(UnitTest.coroutine)
}

tasks.test {
    useJUnitPlatform()
}
