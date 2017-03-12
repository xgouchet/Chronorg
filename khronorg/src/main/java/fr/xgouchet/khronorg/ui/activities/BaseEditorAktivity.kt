package fr.xgouchet.khronorg.ui.activities

import android.content.Intent
import android.os.Bundle
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.ui.fragments.EditorFragment
import fr.xgouchet.khronorg.ui.presenters.BaseEditorPresenter

/**
 * @author Xavier F. Gouchet
 */
abstract class BaseEditorAktivity<T> : BaseAktivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_single_fragment)

        if (savedInstanceState == null) {

            val item: T? = readItem(intent)


            val fragment = EditorFragment<T>()
            val presenter = instantiatePresenter(item)

            presenter.view = fragment
            fragment.presenter = presenter

            supportFragmentManager.beginTransaction()
                    .add(R.id.root, fragment)
                    .commit()
        }
    }


    abstract fun instantiatePresenter(item: T?): BaseEditorPresenter<T>

    abstract fun readItem(intent: Intent?): T?

}