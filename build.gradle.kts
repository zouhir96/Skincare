// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val depHilt by extra("2.47")
    val depKotlin by extra("1.9.0")

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$depHilt")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$depKotlin")
    }
    repositories {
        mavenCentral()
    }
}
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}