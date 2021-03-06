import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("kapt") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.jpa") version "1.6.10"

    id("com.adarshr.test-logger") version "3.1.0"
    id("io.gitlab.arturbosch.detekt") version("1.19.0")

    jacoco
}

group = "xyz.l7ssha"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.passay:passay:1.6.1")
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    implementation("org.flywaydb:flyway-core")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")

    testImplementation("com.h2database:h2")
    testImplementation("com.icegreen:greenmail-junit5:1.6.5")

    implementation("org.springdoc:springdoc-openapi-ui:1.6.6")

    implementation("org.openapitools:jackson-databind-nullable:0.2.2")

    implementation("io.github.perplexhub:rsql-querydsl-spring-boot-starter:5.0.19")
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config = files("$projectDir/config/detekt.yml")
    baseline = file("$projectDir/config/baseline.xml")
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xemit-jvm-type-annotations", "-Xjvm-default=all")
        jvmTarget = "11"
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "11"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "11"
}
