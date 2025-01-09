plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.dagger.hilt.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    kotlin("plugin.serialization") version "1.9.0"
}

buildscript {
    dependencies {
        classpath ("com.google.gms:google-services:4.4.2") // Certifique-se que está atualizado
    }
}