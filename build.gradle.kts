import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
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

    // DI
    implementation("io.insert-koin:koin-core:${property("koinVersion")}")

    // Logging
    implementation("org.slf4j:slf4j-api:${property("slf4jVersion")}")
    implementation("ch.qos.logback:logback-classic:${property("logbackVersion")}")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "17"

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set("com.ttymonkey.deliverysimulation.Main")
}

tasks.shadowJar {
    archiveBaseName.set("delivery-simulation")
    archiveVersion.set("1.0.0")
    archiveClassifier.set("")
    manifest {
        attributes["Main-Class"] = "com.ttymonkey.deliverysimulation.Main"
    }
    mergeServiceFiles()
}
