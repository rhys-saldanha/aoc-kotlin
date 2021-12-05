plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.hamcrest:hamcrest:2.2")
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

    wrapper {
        gradleVersion = "7.3"
    }
}
