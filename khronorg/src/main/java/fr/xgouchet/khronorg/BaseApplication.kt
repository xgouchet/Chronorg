package fr.xgouchet.khronorg

import android.app.Application
import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import net.danlew.android.joda.JodaTimeAndroid

/**
 * @author Xavier Gouchet
 */
open class BaseApplication : Application() {

    val kodein = Kodein {
        bind<Context>() with instance(this@BaseApplication)
    }


    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }

    companion object {
        fun from(context: Context): BaseApplication {
            return context.applicationContext as BaseApplication
        }
    }

}
