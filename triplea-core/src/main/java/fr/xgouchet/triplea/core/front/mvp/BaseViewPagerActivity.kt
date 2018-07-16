package fr.xgouchet.triplea.core.front.mvp

import android.content.Intent
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.PagerTitleStrip
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import fr.xgouchet.triplea.core.R
import timber.log.Timber


abstract class BaseViewPagerActivity<A>(val maxPageCount: Int = 16)
    : AppCompatActivity()
        where A : BaseViewPagerAdapter {

    companion object {
        const val ADAPTER_KEY = "fr.xgouchet.triplea.core.front.mvp.adapter_key"
    }

    protected open val layoutId: Int = R.layout.activity_pager

    private var isRestored: Boolean = false
    private lateinit var fab: FloatingActionButton
    private lateinit var viewPager: ViewPager
    private lateinit var viewPagerStrip: PagerTitleStrip

    private var presenters: MutableList<BaseContract.Presenter?> = mutableListOf()
    private var currentPresenter: BaseContract.Presenter? = null

    protected lateinit var adapter: A
        private set

    // region Activity Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSensitive()) {
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        }

        setContentView(layoutId)

        viewPager = findViewById(R.id.pager)
        viewPagerStrip = findViewById(R.id.pager_strip)
        fab = findViewById(R.id.root_fab)

        val iconRes = getFabIcon()
        if (iconRes != null && iconRes != 0) {
            fab.visibility = View.VISIBLE
            fab.setImageResource(iconRes)
            fab.setOnClickListener { onFabClicked() }
        }

        intent?.let { handleIntent(it) }
        if (isFinishing) return


        adapter = instantiateAdapter()
        if (savedInstanceState != null) {
            isRestored = true
        }
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                this@BaseViewPagerActivity.onPageSelected(position)
            }
        })
    }


    override fun onRestart() {
        super.onRestart()
        isRestored = true
    }

    override fun onStart() {
        super.onStart()
        onPageSelected(viewPager.currentItem)
    }

    override fun onStop() {
        super.onStop()
        currentPresenter?.onViewDetached()
        currentPresenter = null

        if (isFinishing) {
            val adapterKey = getAdapterKey()
            for (i in 0 until presenters.size) {
                val key = getPresenterKey(adapterKey, i)
                PresenterCache.dropPresenter(key)
            }
            presenters.clear()
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

            val adapterKey = savedInstanceState.getString(BaseActivity.PRESENTER_KEY, null)
            if (adapterKey != null) {
                for (i in 0 until maxPageCount) {
                    val key = getPresenterKey(adapterKey, i)
                    val presenter = PresenterCache.getPresenterOrNull<BaseContract.Presenter>(key)
                    presenters[i] = presenter
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        val adapterKey = getAdapterKey()
        for ((i, p) in presenters.withIndex()) {
            if (p != null) {
                val key = getPresenterKey(adapterKey, i)
                PresenterCache.savePresenter(key, p)
            }
        }
        outState?.putString(ADAPTER_KEY, adapterKey)
    }

    // endregion

    // region Internal

    private fun getPresenterKey(adapterKey: String, position: Int): String {
        return "$adapterKey/$position"
    }

    internal fun onPageSelected(position: Int) {
        val restoredPresenter = presenters.getOrNull(position)
        val isRestored = restoredPresenter != null
        val presenter = restoredPresenter
                ?: adapter.instantiatePresenter(this, position)
                        .apply {
                            while (presenters.size <= position) {
                                presenters.add(null)
                            }
                            presenters[position] = this
                        }
        val fragment = adapter.getFragment(position)

        adapter.cancelAttachOnFragmentCreated()
        for (p in presenters) {
            if (p != presenter && p != null) {
                p.onViewDetached()
            }
        }

        if (fragment != null) {
            presenter.onViewAttached(fragment as BaseContract.View, isRestored)
        } else {
            adapter.attachOnFragmentCreated(position, presenter, isRestored)
        }
    }

    // endregion

    // region Abstract

    abstract fun handleIntent(intent: Intent)

    abstract fun instantiateAdapter(): A

    abstract fun getAdapterKey(): String

    open fun isSensitive(): Boolean = false

    open fun onFabClicked() {}

    @DrawableRes open fun getFabIcon(): Int? = null

    // endregion
}
