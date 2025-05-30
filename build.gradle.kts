// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Firebase plugin classpath - commented out for now
        // classpath("com.google.gms:google-services:4.4.1")
    }
}