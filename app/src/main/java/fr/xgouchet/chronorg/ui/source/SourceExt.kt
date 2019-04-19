package fr.xgouchet.chronorg.ui.source

import android.view.View

fun <S : Any, V : View> V.applyOrHide(source: S?, applyOn: S.(V) -> Unit = {}) {
    if (source == null) {
        visibility = View.GONE
    } else {
        visibility = View.VISIBLE
        source.applyOn(this)
    }
}
