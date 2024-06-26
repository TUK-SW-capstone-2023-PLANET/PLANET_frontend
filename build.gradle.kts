// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("compileSdk", 34)
        set("targetSdk", 34)
        set("minSdk", 25)
        set("versionName", "1.0")
        set("versionCode", 1)
        set("core-ktxVersion", "1.12.0")
        set("hilt-version", "2.50")
        set("compose_ui_version", "1.7.0-alpha05")
        set("vico-version", "2.0.0-alpha.6")
        set("accompanist_version", "0.28.0")
        set("lifecycle-runtime-ktx", "2.8.0-alpha03")
    }
}
plugins {
    id("com.android.application") version "8.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.12" apply false
    id ("com.google.dagger.hilt.android") version "2.50" apply false
    id("com.android.library") version "8.4.0" apply false
//    id("org.jetbrains.kotlin.kapt") version "1.9.22"
}