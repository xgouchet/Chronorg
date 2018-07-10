package fr.xgouchet.chronorg.triplea.model

import com.beust.klaxon.Json

data class Type(@Json("simple_name") val simpleName: String,
                @Json("canonical_name") val canonicalName: String)
