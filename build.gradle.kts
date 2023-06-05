// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val depHilt by extra("2.44")
    val depKotlin by extra("1.8.20")

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$depHilt")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$depKotlin")
    }
    repositories {
        mavenCentral()
    }
}
plugins {
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}