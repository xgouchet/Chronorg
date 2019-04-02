package fr.xgouchet.triplea.core.front.mvvm

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import fr.xgouchet.triplea.core.R

abstract class BaseActivity<F : BaseFragment>
    : AppCompatActivity() {


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

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.root_fragment, instantiateFragment())
                    .commit()
        }

    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(R.id.root_fragment) as? BaseFragment)?.onBackPressed()
        super.onBackPressed()
    }


    // endregion


    // region Abstract

    abstract fun handleIntent(intent: Intent)

    abstract fun instantiateFragment(): F

    open fun isSensitive(): Boolean = false

    open fun onFabClicked() {}

    open fun getFabIcon(): Int? = null

    // endregion
}