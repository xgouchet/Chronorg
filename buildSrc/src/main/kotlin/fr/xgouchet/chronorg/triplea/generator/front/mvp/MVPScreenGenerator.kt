package fr.xgouchet.chronorg.triplea.generator.front.mvp

import fr.xgouchet.chronorg.triplea.generator.BaseLayerGenerator


class MVPScreenGenerator
    : BaseLayerGenerator(LAYER_MVP,
        setOf(MVPListTemplate(),
                MVPViewPagerTemplate())) {

    companion object {
        const val LAYER_MVP = "front-mvp"

    }

}