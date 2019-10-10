
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { setUrl(fr.xgouchet.gradle.Dependencies.Repositories.Jitpack) }
        maven { setUrl(fr.xgouchet.gradle.Dependencies.Repositories.Gradle) }
        maven { setUrl(fr.xgouchet.gradle.Dependencies.Repositories.Google) }
    }
    dependencies {
        classpath(fr.xgouchet.gradle.Dependencies.ClassPaths.AndroidPlugin)
        classpath(fr.xgouchet.gradle.Dependencies.ClassPaths.KotlinPlugin)
        classpath(fr.xgouchet.gradle.Dependencies.ClassPaths.OssLicencesPlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { setUrl(fr.xgouchet.gradle.Dependencies.Repositories.Jitpack) }
        maven { setUrl(fr.xgouchet.gradle.Dependencies.Repositories.Gradle) }
        maven { setUrl(fr.xgouchet.gradle.Dependencies.Repositories.Google) }
        maven { setUrl("https://dl.bintray.com/jetbrains/markdown") }
        // jcenter()
    }
}
