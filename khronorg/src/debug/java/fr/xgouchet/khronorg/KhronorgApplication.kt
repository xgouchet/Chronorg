package fr.xgouchet.khronorg

import com.facebook.stetho.Stetho

/**
 * @author Xavier F. Gouchet
 */
class KhronorgApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }
}
