package fr.xgouchet.chronorg.triplea.generator.front.mvvm

import fr.xgouchet.chronorg.triplea.generator.BaseLayerGenerator


class MVVMScreenGenerator
    : BaseLayerGenerator(LAYER_MVP,
        setOf(MVVMListTemplate())) {

    companion object {
        const val LAYER_MVP = "front-mvvm"
    }

}