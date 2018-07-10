package fr.xgouchet.triplea.core.front.mvvm

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int

    // region Fragment Lifecycle

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(layoutId, container, false)
    }

    // endregion

    // region Abstract

    open fun onBackPressed() {}

    // endregion

}