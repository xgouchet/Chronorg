package fr.xgouchet.gradle

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.findByType

inline fun <reified T : Any> Project.extensionConfig(
    crossinline configure: T.() -> Unit
) {

    project.afterEvaluate {
        val ext: T? = extensions.findByType(T::class)
        ext?.configure()
    }
}

inline fun <reified T : Task> Project.taskConfig(
    crossinline configure: T.() -> Unit
) {
    project.afterEvaluate {
        tasks.withType(T::class.java) {
            configure()
        }
    }
}

fun Project.dependencyUpdateConfig() {

    taskConfig<DependencyUpdatesTask> {
        revision = "release"
        rejectVersionIf {
            isNonStable(candidate.version)
        }
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}


