import fr.xgouchet.gradle.AndroidBuild
import fr.xgouchet.gradle.Dependencies
import fr.xgouchet.gradle.dependencyUpdateConfig
import fr.xgouchet.gradle.implementation
import fr.xgouchet.gradle.kapt

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id ("kotlin-parcelize")
    id("com.github.ben-manes.versions")
    // id("io.gitlab.arturbosch.detekt")
    // id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdkVersion(AndroidBuild.TargetSdk)
    buildToolsVersion(AndroidBuild.BuildTools)

    defaultConfig {
        minSdkVersion(AndroidBuild.MinSdk)
        targetSdkVersion(AndroidBuild.TargetSdk)
        // versionCode = AndroidConfig.VERSION.code
        // versionName = AndroidConfig.VERSION.name
        multiDexEnabled = true

        // applicationId "fr.xgouchet.chronorg"
        //
        // versionCode 1
        // versionName "1.0"
        vectorDrawables.useSupportLibrary = true

        // testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

        // javaCompileOptions {
        //     annotationProcessorOptions {
        //         arguments = ["room.schemaLocation": "$projectDir/schemas".toString()]
        //     }
        // }
    }

    // buildTypes {
    //     release {
    //         minifyEnabled false
    //         proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    //     }
    //     debug {
    //         applicationIdSuffix ".debug"
    //         versionNameSuffix "Δ"
    //     }
    // }

    // Used for testing Room migration
    // sourceSets {
    //     androidTest.assets.srcDirs += files("$projectDir/schemas".toString())
    // }

    testOptions {
        // unitTests.returnDefaultValues = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude("META-INF/*")
    }
}

dependencies {

    implementation(Dependencies.Libraries.Kotlin)
    implementation(Dependencies.Libraries.MultiDex)

    implementation(Dependencies.Libraries.AndroidX)
    implementation(Dependencies.Libraries.AndroidUI)
    implementation(Dependencies.Libraries.MaterialComponents)
    implementation(Dependencies.Libraries.AndroidArchComponents)

    implementation(Dependencies.Libraries.AboutPage)
    implementation(Dependencies.Libraries.Timber)
    implementation(Dependencies.Libraries.Stetho)
    implementation(Dependencies.Libraries.Kodein)

    kapt(Dependencies.Processors.AndroidXArch)

    // TIME
    implementation(Dependencies.Libraries.JodaTime)

}

dependencyUpdateConfig()

kapt {
    correctErrorTypes = true
}

// configurations {
//     compile.exclude group : 'com.android.support'
// }
