package fr.xgouchet.triplea.core.front.mvp

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import timber.log.Timber
import fr.xgouchet.triplea.core.R

abstract class BaseActivity<P, V>
    : AppCompatActivity()
        where P : BaseContract.Presenter,
              V : Fragment, V : BaseContract.View {

    companion object {
        const val PRESENTER_KEY = "fr.xgouchet.triplea.core.front.mvp.presenter_key"
    }

    protected lateinit var fragment: V
        private set
    protected lateinit var presenter: P
        private set

    private var isRestored: Boolean = false
    private lateinit var fab: FloatingActionButton

    // region Activity Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSensitive()) {
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        }

        setContentView(R.layout.activity_single_fragment)
        fab = findViewById(R.id.root_fab)
        val iconRes = getFabIcon()
        if (iconRes != null && iconRes != 0) {
            fab.visibility = View.VISIBLE
            fab.setImageResource(iconRes)
            fab.setOnClickListener({ onFabClicked() })
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
        presenter.onViewAttached(fragment, isRestored)
    }

    override fun onStop() {
        super.onStop()
        presenter.onViewDetached()

        if (isFinishing) {
            val key = getPresenterKey()
            PresenterCache.dropPresenter(key)
        }
    }

    // endregion

    // region Instance State

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState != null) {
            if (isRestored) {
                Timber.w("Already restored but restoring again ‽")
                return
            }
            isRestored = true
            val restoredPresenter = restorePresenter(savedInstanceState)
            if (restoredPresenter !== presenter) {
                presenter.onViewDetached()
                presenter = restoredPresenter
                presenter.onViewAttached(fragment, isRestored)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val key = getPresenterKey()
        PresenterCache.savePresenter(key, presenter)
        outState?.putString(PRESENTER_KEY, key)
    }

    // endregion

    // region OptionsMenu

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish() // onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // endregion

    // region Internal

    private fun restorePresenter(savedInstanceState: Bundle): P {
        val key = savedInstanceState.getString(PRESENTER_KEY, null)

        if (key == null) {
            Timber.w("Expected to restore presenter, but not Key found")
            return instantiatePresenter()
        }

        return PresenterCache.getPresenter(key, { instantiatePresenter() })
    }

    // endregion

    // region Abstract

    abstract fun handleIntent(intent: Intent)

    abstract fun instantiatePresenter(): P

    abstract fun instantiateFragment(): V

    abstract fun getPresenterKey(): String

    open fun isSensitive(): Boolean = false

    open fun onFabClicked() {}

    open fun getFabIcon(): Int? = null

    // endregion
}
