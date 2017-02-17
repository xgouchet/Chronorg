package fr.xgouchet.khronorg.ui.activities

import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.Kodein
import fr.xgouchet.khronorg.BaseApplication

/**
 * @author Xavier F. Gouchet
 */
open class BaseActivity : AppCompatActivity() {

    protected val kodein: Kodein
        get() = BaseApplication.from(this).kodein

}