import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "u.ways"
version = "SNAPSHOT"

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

plugins {
    application
    kotlin("jvm") version "1.4.31"
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("io.mockk:mockk:1.10.2")
    testImplementation("com.natpryce:hamkrest:1.8.0.1")
    testImplementation("org.amshove.kluent:kluent:1.63")

    testImplementation("org.assertj:assertj-core:3.12.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.2")
    testImplementation("com.natpryce:hamkrest:1.7.0.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

application {
    mainClass.set("u.ways.Main")
}

tasks.getByName<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "u.ways.Main"
    }

    from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = "6.6"
    distributionType = Wrapper.DistributionType.ALL
}