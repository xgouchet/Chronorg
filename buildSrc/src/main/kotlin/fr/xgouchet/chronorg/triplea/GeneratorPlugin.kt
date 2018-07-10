package fr.xgouchet.chronorg.triplea

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class GeneratorPlugin : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "tripleA"
        const val TASK_NAME = "generateTripleA"
    }


    override fun apply(project: Project?) {
        if (project == null) return

        val extension = project.extensions.create(EXTENSION_NAME, GeneratorExtension::class.java)


        project.afterEvaluate {

            val androidExt = project.extensions.getByType(BaseExtension::class.java)

            when (androidExt) {
                is AppExtension -> androidExt.applicationVariants.all {
                    createTaskForVariant(it, project, extension)
                }
                is LibraryExtension -> androidExt.libraryVariants.all {
                    createTaskForVariant(it, project, extension)
                }
                else -> throw IllegalStateException("Project is neither an android app nor an android library")
            }
        }
    }

    private fun createTaskForVariant(v: BaseVariant, project: Project, extension: GeneratorExtension) {
        val taskName = "$TASK_NAME${v.name.capitalize()}"
        val genDir = "${project.buildDir}/generated/source/triplea/${v.dirName}"

        val task = project.tasks.create(taskName, GeneratorTask::class.java)
                .apply {
                    projectBasePath = project.projectDir.path
                    configuration = extension
                    genDirPath = genDir
                }

        project.tasks.matching { it.name == ("pre${v.name.capitalize()}Build") }.firstOrNull()?.dependsOn(task)

        v.registerJavaGeneratingTask(task, File(genDir))
    }
}