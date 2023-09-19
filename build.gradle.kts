import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "com.ttymonkey"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.vertx:vertx-core:${property("vertxVersion")}")
    implementation("io.vertx:vertx-web:${property("vertxVersion")}")
    implementation("io.vertx:vertx-lang-kotlin:${property("vertxVersion")}")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:${property("vertxVersion")}")
    testImplementation("io.vertx:vertx-junit5:4.2.1")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${property("jacksonVersion")}")

    // testing
    testImplementation("org.junit.jupiter:junit-jupiter:${property("junitJupiterVersion")}")
    testImplementation("org.assertj:assertj-core:${property("assertJVersion")}")
    testImplementation("io.mockk:mockk:${property("mockkVersion")}")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "17"

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}
