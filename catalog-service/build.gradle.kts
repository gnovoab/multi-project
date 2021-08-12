import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

println("${project.name} starting config phase...")

//PLugins
plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot") version "2.5.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("io.gitlab.arturbosch.detekt").version("1.18.0-RC3")
    jacoco
}

//Jar Settings
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    archiveFileName.set("catalog-service.jar")
}


// detek settings

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    config = files("$projectDir/codequality/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior

    reports {
        html.enabled = true // observe findings in your browser with structure and code snippets
        xml.enabled = true // checkstyle like format mainly for integrations like Jenkins
        txt.enabled = true // similar to the console output, contains issue signature to manually edit baseline files
        sarif.enabled = true // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with Github Code Scanning
    }
}


//Code coverage
tasks.jacocoTestReport {
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.required.set(true)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}



dependencies {

    //Actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    //Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    //JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    //Logging
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.10")

    //Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    //Metadata
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    //Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")

    // swagger for API visualization
    implementation("org.springdoc:springdoc-openapi-ui:1.5.10")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.5.10")

    //DB
    runtimeOnly("com.h2database:h2")
}



tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

println("${project.name} config phase completed")


