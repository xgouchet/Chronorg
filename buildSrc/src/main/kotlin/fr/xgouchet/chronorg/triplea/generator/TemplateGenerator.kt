package fr.xgouchet.chronorg.triplea.generator

import fr.xgouchet.chronorg.triplea.model.Feature
import java.io.File

interface TemplateGenerator {

    fun canHandleTemplateType(template: String): Boolean

    fun generatateFeature(packageName: String,
                          applicationId: String,
                          feature: Feature,
                          outputDir: File)
}