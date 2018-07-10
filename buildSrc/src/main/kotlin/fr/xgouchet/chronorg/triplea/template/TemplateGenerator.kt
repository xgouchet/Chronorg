package fr.xgouchet.chronorg.triplea.template

import fr.xgouchet.chronorg.triplea.model.Screen
import java.io.File


class TemplateGenerator(generators: List<LayerGenerator>) {


    private val generators = generators.toSet()

    private lateinit var packageName: String
    private lateinit var outputDir: File

    // region Global

    fun generateScreen(packageName: String,
                       layers: List<String>,
                       screen: Screen,
                       outputDir: File) {
        this.packageName = packageName
        this.outputDir = outputDir

        layers.forEach {
            generateLayerForScreen(it, screen)
        }
    }

    private fun generateLayerForScreen(layer: String, screen: Screen) {

        val generator = generators
                .filter { it.canHandleLayerType(layer) }
                .firstOrNull()

        checkNotNull(generator == null, { "Unable to generate unknown layer <$layer> for screen “${screen.key}” with template <${screen.template}>" })

        generator?.generatateScreen(layer,
                screen,
                "$packageName.${screen.key}",
                outputDir)


    }

    // endregion
}