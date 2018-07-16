package fr.xgouchet.chronorg.triplea.generator

import fr.xgouchet.chronorg.triplea.model.Feature
import java.io.File


class BoilerplateGenerator(generators: List<LayerGenerator>) {


    private val generators = generators.toSet()

    private lateinit var packageName: String
    private lateinit var applicationId: String
    private lateinit var outputDir: File

    // region Global

    fun generateFeature(packageName: String,
                        applicationId: String,
                        feature: Feature,
                        outputDir: File) {
        this.packageName = packageName
        this.outputDir = outputDir
        this.applicationId = applicationId

        feature.layers.forEach {
            generateLayer(it, feature)
        }
    }

    private fun generateLayer(layer: String, screen: Feature) {

        val generator = generators.firstOrNull { it.canHandleLayerType(layer) }

        checkNotNull(generator) { "Unable to find a <$layer> layer generator for screen ${screen.key}" }

        generator?.generatateFeatureLayer("$packageName.${screen.key}",
                applicationId,
                screen,
                outputDir)


    }

    // endregion
}