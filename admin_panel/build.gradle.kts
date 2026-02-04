import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("js") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
}

group = "com.demo.streamflix"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled = true
                }
            }
            distribution {
                outputDirectory = file("$projectDir/dist")
            }
        }
        binaries.executable()
    }
}

dependencies {
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.598")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.598")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.11.3-pre.598")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.20.1-pre.598")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-redux:4.2.1-pre.598")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-pre.598")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    
    // HTTP Client
    implementation("io.ktor:ktor-client-core:2.3.7")
    implementation("io.ktor:ktor-client-js:2.3.7")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
    
    // UI Components
    implementation("org.jetbrains.kotlin-wrappers:kotlin-mui:5.14.18-pre.598")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-mui-icons:5.14.18-pre.598")
    
    // Charts
    implementation("org.jetbrains.kotlin-wrappers:kotlin-charts:0.11.0-pre.598")
}

tasks.named("build") {
    dependsOn("browserProductionWebpack")
}