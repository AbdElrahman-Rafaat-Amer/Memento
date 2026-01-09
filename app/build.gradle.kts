import org.gradle.internal.extensions.stdlib.capitalized
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
}

var canSignWithKeystore = false
val keystoreProperties =
    Properties().apply {
        val keystorePropertiesFile = rootProject.file("./keystore.properties")
        canSignWithKeystore = keystorePropertiesFile.exists()
        if (canSignWithKeystore) {
            load(rootProject.file("./keystore.properties").inputStream())
        }
    }

android {
    namespace = "com.abdelrahman.raafat.memento"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.abdelrahman.raafat.memento"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = getStoreFile()
            storePassword = getProperty("KEYSTORE_PASSWORD")
            keyAlias = getProperty("KEY_ALIAS")
            keyPassword = getProperty("KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
            resValue("string", "app_name", "Memo-Debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    buildFeatures {
        compose = true
    }

    applicationVariants.all {
        outputs.all {
            val outputFileName = getOutputFileName(buildType.name.capitalized())
            val output = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            output.outputFileName = "$outputFileName.apk"
        }
    }
}

fun getStoreFile(): File =
    if (canSignWithKeystore) {
        rootProject.file(keystoreProperties.getProperty("KEYSTORE_FILE_PATH"))
    } else {
        file(System.getenv("KEYSTORE_FILE_PATH"))
    }

fun getProperty(propertyKey: String): String =
    if (canSignWithKeystore) {
        keystoreProperties.getProperty(propertyKey)
    } else {
        System.getenv(propertyKey)
    }

tasks.configureEach {
    if (name.startsWith("bundle")) {
        doLast {
            val buildType = name.removePrefix("bundle").capitalized()
            val outputFileName = getOutputFileName(buildType.capitalized())

            // Find the generated AAB file
            val bundleDir =
                layout.buildDirectory
                    .dir("outputs/bundle/${buildType.lowercase()}")
                    .get()
                    .asFile

            bundleDir.listFiles()?.forEach { file ->
                if (file.extension == "aab") {
                    val newName = "$outputFileName.aab"
                    val newFile = File(file.parentFile, newName)
                    file.renameTo(newFile)
                }
            }
        }
    }
}

fun getOutputFileName(buildType: String): String {
    val appName = "Memento"
    val versionName = android.defaultConfig.versionName
    val versionCode = android.defaultConfig.versionCode
    val outputFileName = "${appName}_${buildType}_$versionName($versionCode)"
    return outputFileName
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.lottie.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.material3)
    implementation(libs.compose.material.icons)

    // Navigation
    implementation(libs.androidx.navigation.compose)
}
