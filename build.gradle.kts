plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}
dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src/main/kotlin")
            java.srcDirs("src/test/kotlin")
        }
    }

    wrapper {
        gradleVersion = "7.3"
    }
    
}
