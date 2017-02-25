package fr.xgouchet.khronorg

import android.app.Application
import android.content.Context
import com.github.salomonbrys.kodein.*
import fr.xgouchet.khronorg.data.ioproviders.IOProvider
import fr.xgouchet.khronorg.data.ioproviders.ProjectProvider
import fr.xgouchet.khronorg.data.ioproviders.TravellerProvider
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.presenters.ProjectListPresenter
import net.danlew.android.joda.JodaTimeAndroid

/**
 * @author Xavier Gouchet
 */
open class BaseApplication : Application() {

    val kodein = Kodein {
        bind<Context>() with instance(this@BaseApplication)

        // Providers
        bind<IOProvider<Project>>() with singleton { ProjectProvider() }
        bind<IOProvider<Traveller>>() with singleton { TravellerProvider() }

        // Repositories
        bind<BaseRepository<Project>>() with singleton { BaseRepository<Project>(instance(), instance()) }
        bind<BaseRepository<Traveller>>() with singleton { BaseRepository<Traveller>(instance(), instance()) }

        // Presenters
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
