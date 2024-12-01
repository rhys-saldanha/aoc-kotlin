plugins {
    kotlin("jvm") version "2.1.0"
    id("se.patrikerdes.use-latest-versions") version "0.2.18"
    id("com.github.ben-manes.versions") version "0.51.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.hamcrest:hamcrest:3.0")
    testImplementation("org.assertj:assertj-core:3.26.3")
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
        test {
            java.srcDirs("test")
        }
    }

    test {
        useJUnitPlatform()
    }
}
