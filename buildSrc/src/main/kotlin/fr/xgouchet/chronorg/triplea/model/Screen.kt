package fr.xgouchet.chronorg.triplea.model

import com.beust.klaxon.Json

data class Screen(val key: String,
                  val template: String,
                  @Json("base_type") val baseType: Type,
                  val params: Map<String, String>)