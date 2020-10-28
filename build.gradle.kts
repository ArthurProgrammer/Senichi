plugins {
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.serialization") version "1.4.10"
}

group = "com.hechfx.project"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

buildscript {
    repositories { jcenter() }

    dependencies {
        val kotlinVersion = "1.4.10"
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization", version = kotlinVersion))
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("net.dv8tion:JDA:4.2.0_211")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.9")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    jar {
        manifest {
            attributes(mapOf("Main-Class" to "com.hechfx.project.Senichi"))
        }

        from(sourceSets.main.get().output)

        dependsOn(configurations.runtimeClasspath)

        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        })
    }
}
