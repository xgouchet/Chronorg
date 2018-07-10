package fr.xgouchet.chronorg

object Dependencies {

    object Versions {
        // Commons
        const val Kotlin = "1.2.41"
        const val AndroidBuildTools = "3.2.0-alpha12"
        const val AndroidSupportLib = "27.1.1"
        const val AndroidNavigation = "1.0.0-alpha02"
        const val AndroidConstraintLayout = "1.0.2"
        const val AndroidX = "1.0.0-beta01"

        //
        const val RxJava = "2.1.8"
        const val RxAndroid = "2.0.1"
        const val Klaxon = "3.0.1"

        // Debug
        const val Timber = "4.6.0"
        const val Stetho = "1.5.0"
        const val DependencyVersions = "0.17.0"

        // Tests
        const val JUnit4 = "4.12"
        const val Spek = "1.1.5"
        const val JUnitPlatform = "1.0.0"
        const val MockitoKotlin = "1.5.0"
        const val Mockito = "2.18.3"
        const val Elmyr = "0.6.2"
        const val AssertK = "0.1.1"
    }

    object Libraries {

        const val KotlinJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.Kotlin}"

        @JvmField val SupportLibs = arrayOf("com.android.support:support-v4:${Versions.AndroidSupportLib}",
                "com.android.support:appcompat-v7:${Versions.AndroidSupportLib}",
                "com.android.support:cardview-v7:${Versions.AndroidSupportLib}",
                "com.android.support:recyclerview-v7:${Versions.AndroidSupportLib}",
                "com.android.support:support-annotations:${Versions.AndroidSupportLib}",
                "com.android.support:design:${Versions.AndroidSupportLib}",
                "com.android.support.constraint:constraint-layout:${Versions.AndroidConstraintLayout}")

        @JvmField
        val ArchComponents = arrayOf("android.arch.navigation:navigation-fragment-ktx:${Versions.AndroidNavigation}",
                "android.arch.navigation:navigation-ui-ktx:${Versions.AndroidNavigation}",
                "com.android.support.constraint:constraint-layout:${Versions.AndroidConstraintLayout}")

        private val AndroidX = arrayOf("androidx.core:core-ktx:${Versions.AndroidX}",
                "androidx.fragment:fragment-ktx:${Versions.AndroidX}",
                "androidx.legacy:legacy-support-v4:${Versions.AndroidX}")

        @JvmField val Rx = arrayOf("io.reactivex.rxjava2:rxjava:${Versions.RxJava}",
                "io.reactivex.rxjava2:rxandroid:${Versions.RxAndroid}")

        const val Klaxon = "com.beust:klaxon:${Versions.Klaxon}"

        @JvmField val DebugTools = arrayOf("com.jakewharton.timber:timber:${Versions.Timber}",
                "com.facebook.stetho:stetho:${Versions.Stetho}",
                "com.facebook.stetho:stetho-okhttp3:${Versions.Stetho}")

        const val JUnit4 = "junit:junit:${Versions.JUnit4}"

        @JvmField val Spek = arrayOf("org.jetbrains.spek:spek-api:${Versions.Spek}",
                "org.jetbrains.spek:spek-junit-platform-engine:${Versions.Spek}",
                "org.jetbrains.kotlin:kotlin-refleAct:${Versions.Kotlin}")
        @JvmField val Mockito = arrayOf("com.nhaarman:mockito-kotlin:${Versions.MockitoKotlin}",
                "org.mockito:mockito-core:${Versions.Mockito}")
        @JvmField val TestTools = arrayOf("com.github.xgouchet:Elmyr:${Versions.Elmyr}",
                "com.github.memoizr:assertk-core:${Versions.AssertK}")

    }

    object Processors {
    }

    object ClassPaths {

        const val AndroidBuildTools = "com.android.tools.build:gradle:${Versions.AndroidBuildTools}"
        const val Kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin}"

        const val DependencyVersions = "com.github.ben-manes:gradle-versions-plugin:${Versions.DependencyVersions}"

        const val JUnitPlatform = "org.junit.platform:junit-platform-gradle-plugin:${Versions.JUnitPlatform}"
    }
}
