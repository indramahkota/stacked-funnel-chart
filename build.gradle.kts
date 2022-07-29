// We want to use new APIs
@file:Suppress("UnstableApiUsage")

// Add plugin to classpath with id
plugins {
    // plugin id: com.android.application
    id("com.android.application") version "7.4.0-alpha08" apply false
    // plugin id: com.android.library
    id("com.android.library") version "7.4.0-alpha08" apply false
    // plugin id: kotlin-android
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
