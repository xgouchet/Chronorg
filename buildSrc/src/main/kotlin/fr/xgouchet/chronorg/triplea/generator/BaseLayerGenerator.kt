package fr.xgouchet.chronorg.triplea.generator

import fr.xgouchet.chronorg.triplea.model.Feature
import java.io.File

open class BaseLayerGenerator(private val layerType: String,
                              private val templates: Set<TemplateGenerator>)
    : LayerGenerator {
    override fun canHandleLayerType(layer: String): Boolean {
        return layer == layerType
    }

    override fun generatateFeatureLayer(packageName: String,
                                        applicationId: String,
                                        feature: Feature,
                                        outputDir: File) {

        val generator = templates.firstOrNull { it.canHandleTemplateType(feature.template) }

        checkNotNull(generator) { "Unable to find a <${feature.template}> template generator for feature ${feature.key}" }

        generator!!.generatateFeature(packageName, applicationId, feature, outputDir)

    }


}