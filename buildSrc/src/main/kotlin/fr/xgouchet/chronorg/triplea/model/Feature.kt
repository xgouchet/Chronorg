package fr.xgouchet.chronorg.triplea.model

import com.beust.klaxon.Json

data class Feature(val key: String,
                   val template: String,
                   val layers: List<String>,
                   @Json("base_type") val baseType: Type,
                   val params: Map<String, String>)