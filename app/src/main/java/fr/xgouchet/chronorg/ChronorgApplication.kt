package fr.xgouchet.chronorg

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import fr.xgouchet.chronorg.di.ConverterModule
import fr.xgouchet.chronorg.di.DataFlowModule
import fr.xgouchet.chronorg.di.TransformerModule
import fr.xgouchet.chronorg.di.ViewModelFactory
import fr.xgouchet.chronorg.di.ViewModelModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import timber.log.Timber

class ChronorgApplication
    : Application(),
        KodeinAware {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

    override val kodein by Kodein.lazy {
        import(androidCoreModule(this@ChronorgApplication))
        import(ViewModelModule)
        import(DataFlowModule)
        import(TransformerModule)
        import(ConverterModule)

        bind<Context>() with singleton { this@ChronorgApplication }

        bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(instance()) }
    }
}
