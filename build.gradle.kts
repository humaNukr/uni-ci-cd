plugins {
    id("java")
    id("com.diffplug.spotless") version "6.25.0"
}

group = "ua.edu.kma"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:6.1.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.1.0")

    testImplementation("org.junit.platform:junit-platform-suite:6.1.0")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

spotless {
    java {
        palantirJavaFormat()
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
}
