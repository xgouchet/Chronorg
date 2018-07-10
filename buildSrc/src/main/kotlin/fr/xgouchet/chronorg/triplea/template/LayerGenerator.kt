package fr.xgouchet.chronorg.triplea.template

import fr.xgouchet.chronorg.triplea.model.Screen
import java.io.File

interface LayerGenerator {

    fun canHandleLayerType(layer: String): Boolean

    fun generatateScreen(layer: String,
                         screen: Screen,
                         packageName: String,
                         outputDir: File)
}