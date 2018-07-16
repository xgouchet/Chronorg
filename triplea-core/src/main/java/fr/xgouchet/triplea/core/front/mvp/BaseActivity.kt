package fr.xgouchet.triplea.core.front.mvp

import android.content.Intent
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import fr.xgouchet.triplea.core.R
import timber.log.Timber

abstract class BaseActivity<P, V>
    : AppCompatActivity()
        where P : BaseContract.Presenter,
              V : Fragment, V : BaseContract.View {

    companion object {
        const val PRESENTER_KEY = "fr.xgouchet.triplea.core.front.mvp.presenter_key"
    }

    protected open val layoutId: Int = R.layout.activity_single_fragment

    protected lateinit var fragment: V
        private set

    protected var presenter: P? = null
        private set

    private var isRestored: Boolean = false
    private lateinit var fab: FloatingActionButton

    // region Activity Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSensitive()) {
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        }

        setContentView(layoutId)
        fab = findViewById(R.id.root_fab)
        val iconRes = getFabIcon()
        if (iconRes != null && iconRes != 0) {
            fab.visibility = View.VISIBLE
            fab.setImageResource(iconRes)
            fab.setOnClickListener { onFabClicked() }
        }

        intent?.let { handleIntent(it) }
        if (isFinishing) return

        if (savedInstanceState != null) {
            isRestored = true
            presenter = restorePresenter(savedInstanceState)
            @Suppress("UNCHECKED_CAST")
            fragment = supportFragmentManager.findFragmentById(R.id.root_fragment) as V
        } else {
            presenter = instantiatePresenter()
            fragment = instantiateFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.root_fragment, fragment)
                    .commit()
        }
    }


    override fun onRestart() {
        super.onRestart()
        isRestored = true
    }

    override fun onStart() {
        super.onStart()
        presenter?.onViewAttached(fragment, isRestored)
    }

    override fun onStop() {
        super.onStop()
        presenter?.onViewDetached()

        if (isFinishing) {
            presenter?.getKey()?.let { PresenterCache.dropPresenter(it) }
        }
    }

    // endregion

    // region Instance State

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState != null) {
            if (isRestored) {
                Timber.w("Presenter already restored but restoring again ‽")
                return
            }
            isRestored = true
            val restoredPresenter = restorePresenter(savedInstanceState)
            if (restoredPresenter !== presenter) {
                presenter?.onViewDetached()
                presenter = restoredPresenter
                presenter?.onViewAttached(fragment, isRestored)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        presenter?.let {
            val key = it.getKey()
            PresenterCache.savePresenter(key, it)
            outState?.putString(PRESENTER_KEY, key)
        }
    }

    // endregion

    // region Internal

    private fun restorePresenter(savedInstanceState: Bundle): P {
        val key = savedInstanceState.getString(PRESENTER_KEY, null)

        if (key == null) {
            Timber.w("Expected to restore presenter, but not Key found")
            return instantiatePresenter()
        }

        return PresenterCache.getPresenter(key) { instantiatePresenter() }
    }

    // endregion

    // region Abstract

    abstract fun handleIntent(intent: Intent)

    abstract fun instantiatePresenter(): P

    abstract fun instantiateFragment(): V

    open fun isSensitive(): Boolean = false

    open fun onFabClicked() {}

    @DrawableRes open fun getFabIcon(): Int? = null

    // endregion
}
