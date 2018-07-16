package fr.xgouchet.chronorg.triplea.generator

import fr.xgouchet.chronorg.triplea.model.Feature
import java.io.File

interface LayerGenerator {

    fun canHandleLayerType(layer: String): Boolean

    fun generatateFeatureLayer(packageName: String,
                               applicationId: String,
                               feature: Feature,
                               outputDir: File)
}