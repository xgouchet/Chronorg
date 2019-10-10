package fr.xgouchet.gradle

@Suppress("unused")
object Dependencies {

    object Versions {

        const val AndroidPlugin = "4.1.2"

        // Android Libraries
        const val AndroidSupportLibs = "28.0.0"
        const val AndroidX = "1.0.0"
        const val AndroidXAnnotation = "1.1.0"
        const val AndroidXAppCompat = "1.2.0"
        const val AndroidXArch = "2.0.0"
        const val AndroidXCore = "1.3.2"
        const val AndroidXLifecycle = "2.2.0"
        const val AndroidXLifecycleCompiler = "2.3.0"
        const val AndroidXMedia = "1.2.1"
        const val AndroidXNav = "2.3.4"
        const val AndroidXRoom = "2.2.6"
        const val AndroidXTest = "1.1.0"
        const val MultiDex = "2.0.1"
        const val ConstraintLayout = "2.0.4"
        const val MaterialComponents = "1.3.0"

        // Kotlin
        const val Kotlin = "1.4.31"
        const val KotlinCoroutines = "1.4.3"

        // Architecture
        const val Kodein = "6.3.2"

        // UX

        // Pages
        const val OssLicensesPlugin = "0.9.2"
        const val OssLicensesLibrary = "16.0.0"
        const val AboutPage = "1.3.1"

        const val DataBindingCompiler = "2.3.3"

        const val BuildTimeTracker = "0.11.0"
        const val DependencyVersion = "0.21.0"
        const val Detekt = "1.0.0-RC16"

        const val JodaTime = "2.10.9.1"
        const val Leakcanary = "1.5.4"
        const val Timber = "4.7.1"
        const val Stetho = "1.5.1"

        // Tests
        const val JUnit = "4.12"
        const val Elmyr = "0.8.1"
        const val Mockito = "2.19.0"
        const val MockitoKotlin = "1.6.0"
        const val AssertJ = "3.11.0"
    }

    object Libraries {
        @JvmField
        val AndroidX = arrayOf(
            "androidx.annotation:annotation:${Versions.AndroidXAnnotation}",
            "androidx.appcompat:appcompat:${Versions.AndroidXAppCompat}",
            "androidx.core:core-ktx:${Versions.AndroidXCore}",
            "androidx.media:media:${Versions.AndroidXMedia}"
        )
        @JvmField
        val AndroidUI = arrayOf(
            "androidx.recyclerview:recyclerview:${Versions.AndroidX}",
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.AndroidX}",
            "androidx.cardview:cardview:${Versions.AndroidX}",
            "androidx.constraintlayout:constraintlayout:${Versions.ConstraintLayout}"
        )

        const val MaterialComponents =
            "com.google.android.material:material:${Versions.MaterialComponents}"

        @JvmField
        val AndroidArchComponents = arrayOf(
            "androidx.lifecycle:lifecycle-extensions:${Versions.AndroidXLifecycle}",
            "androidx.room:room-runtime:${Versions.AndroidXRoom}",
            "androidx.room:room-rxjava2:${Versions.AndroidXRoom}",
//                "androidx.room:room-coroutines:${Versions.AndroidXRoom}",
            "androidx.room:room-ktx:${Versions.AndroidXRoom}",
            "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidXNav}",
            "androidx.navigation:navigation-ui-ktx:${Versions.AndroidXNav}"
        )

        const val MultiDex = "androidx.multidex:multidex:${Versions.MultiDex}"

        @JvmField
        val Kotlin = arrayOf(
            "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Kotlin}",
            "org.jetbrains.kotlin:kotlin-reflect:${Versions.Kotlin}",
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KotlinCoroutines}",
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KotlinCoroutines}"
        )

        const val Markdown = "org.jetbrains:markdown:0.1.28"
        const val JodaTime = "net.danlew:android.joda:${Versions.JodaTime}"

        @JvmField
        val Kodein = arrayOf(
            "org.kodein.di:kodein-di-generic-jvm:${Versions.Kodein}",
            "org.kodein.di:kodein-di-framework-android-core:${Versions.Kodein}",
            "org.kodein.di:kodein-di-framework-android-x:${Versions.Kodein}"
        )

        const val OssLicences =
            "com.google.android.gms:play-services-oss-licenses:${Versions.OssLicensesLibrary}"
        const val AboutPage = "com.github.medyo:android-about-page:${Versions.AboutPage}"

        const val Timber = "com.jakewharton.timber:timber:${Versions.Timber}"
        const val Stetho = "com.facebook.stetho:stetho:${Versions.Stetho}"

        @JvmField
        val Testing = arrayOf(
            "junit:junit:${Versions.JUnit}",
            "org.mockito:mockito-core:${Versions.Mockito}",
            "com.nhaarman:mockito-kotlin:${Versions.MockitoKotlin}",
            "com.github.xgouchet:Elmyr:${Versions.Elmyr}",
            "org.assertj:assertj-core:${Versions.AssertJ}",
            "androidx.room:room-testing:${Versions.AndroidXRoom}"
        )

        @JvmField
        val AndroidTesting = arrayOf(
            "androidx.test:runner:${Versions.AndroidXTest}",
            "androidx.test:rules:${Versions.AndroidXTest}",
            "androidx.test.ext:junit:${Versions.AndroidXTest}",
            "androidx.room:room-testing:${Versions.AndroidXArch}"
        )
    }

    object ClassPaths {
        const val AndroidPlugin = "com.android.tools.build:gradle:${Versions.AndroidPlugin}"
        const val OssLicencesPlugin = "com.google.gms:oss-licenses:${Versions.OssLicensesPlugin}"
        const val KotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin}"
    }

    object PluginNamespaces {
        const val Detetk = "io.gitlab.arturbosch"
        const val DependencyVersion = "com.github.ben-manes"
        const val Kotlin = "org.jetbrains.kotlin"
    }

    object Repositories {
        const val Fabric = "https://maven.fabric.io/public"
        const val Jitpack = "https://jitpack.io"
        const val Gradle = "https://plugins.gradle.org/m2/"
        const val Google = "https://maven.google.com"
    }

    object Processors {
        const val DataBinding = "com.android.databinding:compiler:${Versions.DataBindingCompiler}"

        @JvmField
        val AndroidXArch = arrayOf(
            "androidx.lifecycle:lifecycle-compiler:${Versions.AndroidXLifecycleCompiler}",
            "androidx.room:room-compiler:${Versions.AndroidXRoom}"
        )
    }
}
