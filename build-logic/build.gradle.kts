plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "io.gremstudio"
version = "1.0"

repositories {
    mavenCentral()
}


publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            groupId = group as String?
        }
    }
}
