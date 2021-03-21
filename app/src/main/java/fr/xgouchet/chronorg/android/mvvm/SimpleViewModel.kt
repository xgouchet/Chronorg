package fr.xgouchet.chronorg.android.mvvm

import androidx.lifecycle.ViewModel

abstract class SimpleViewModel<VM, F> : ViewModel(), BaseViewModel
    where F : BaseFragment<VM>, VM : BaseViewModel, VM : ViewModel {

    var fragment: F? = null

    @Suppress("UNCHECKED_CAST")
    override fun <T : BaseFragment<*>> setLinkedFragment(linkedFragment: T?) {
        fragment = linkedFragment as? F
    }
}
