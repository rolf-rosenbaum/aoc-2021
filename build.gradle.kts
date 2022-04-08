import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
}

repositories {
    mavenCentral()
}
dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    implementation(kotlin("stdlib-jdk8"))
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
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}