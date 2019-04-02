package fr.xgouchet.chronorg.triplea

import com.beust.klaxon.Klaxon
import fr.xgouchet.chronorg.triplea.model.Definition
import fr.xgouchet.chronorg.triplea.generator.BoilerplateGenerator
import fr.xgouchet.chronorg.triplea.generator.front.mvp.MVPScreenGenerator
import fr.xgouchet.chronorg.triplea.generator.front.mvvm.MVVMScreenGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

open class GeneratorTask : DefaultTask() {

    var variant: String = ""
    var projectBasePath: String = ""
    var configuration: GeneratorExtension = GeneratorExtension()
    var genDirPath: String = ""

    val generator = BoilerplateGenerator(
            listOf(
                    MVPScreenGenerator(),
                    MVVMScreenGenerator()
            )
    )

    init {
        group = "triplea"
        description = "Generate code from definition"
    }

    // region Task

    @TaskAction
    fun generateCode() {
        val file = File("$projectBasePath${File.separator}${configuration.definitionFileName}")

        val definition = Klaxon().parse<Definition>(file)
                ?: throw IllegalStateException("Couldn't parse file at ${file.path}.")

        definition.features.forEach {
            generator.generateFeature(definition.packageName, definition.applicationId, it, getOutputDir())
        }
    }

    @InputFile
    fun getInputFile(): File {
        val projectDir = File(projectBasePath)
        return File(projectDir, configuration.definitionFileName)
    }


    @OutputDirectory
    fun getOutputDir(): File {
        return File(genDirPath)
    }

    // endregion

}