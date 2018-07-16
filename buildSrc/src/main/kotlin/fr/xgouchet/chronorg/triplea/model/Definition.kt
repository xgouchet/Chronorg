package fr.xgouchet.chronorg.triplea.model

import com.beust.klaxon.Json

data class Definition(@Json("package_name") val packageName: String,
                      @Json("application_id") val applicationId: String,
                      val features: List<Feature>)