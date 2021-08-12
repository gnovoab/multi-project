import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


println("${project.name} starting config phase...")

//PLugins
plugins {
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
}

allprojects{

    apply(plugin = "kotlin")

    java.sourceCompatibility = JavaVersion.VERSION_1_8

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    repositories {
        mavenCentral()
    }


    tasks.getByName<Jar>("jar") {
        enabled = false
    }

}

println("${project.name} config phase completed")
