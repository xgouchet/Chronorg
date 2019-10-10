package fr.xgouchet.gradle

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementation(dependencies: Array<String>) {
    dependencies.forEach {
        add("implementation", it)
    }
}

fun DependencyHandler.kapt(dependencies: Array<String>) {
    dependencies.forEach {
        add("kapt", it)
    }
}

