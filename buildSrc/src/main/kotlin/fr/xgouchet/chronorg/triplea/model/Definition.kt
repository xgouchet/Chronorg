package fr.xgouchet.chronorg.triplea.model

import com.beust.klaxon.Json

data class Definition(@Json("package_name") val packageName: String,
                      val layers: List<String>,
                      val screens: List<Screen>)