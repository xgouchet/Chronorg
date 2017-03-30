package fr.xgouchet.khronorg

import android.app.Application
import android.content.Context
import com.github.salomonbrys.kodein.*
import fr.xgouchet.khronorg.data.ioproviders.IOProvider
import fr.xgouchet.khronorg.data.ioproviders.JumpProvider
import fr.xgouchet.khronorg.data.ioproviders.ProjectProvider
import fr.xgouchet.khronorg.data.ioproviders.TravellerProvider
import fr.xgouchet.khronorg.data.models.Jump
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.presenters.ProjectListPresenter
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.feature.events.EventProvider
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
        bind<IOProvider<Event>>() with singleton { EventProvider() }
        bind<IOProvider<Jump>>() with singleton { JumpProvider() }

        // Repositories
        bind<BaseRepository<Project>>() with singleton { BaseRepository<Project>(instance(), instance()) }
        bind<BaseRepository<Traveller>>() with singleton { BaseRepository<Traveller>(instance(), instance()) }
        bind<BaseRepository<Event>>() with singleton { BaseRepository<Event>(instance(), instance()) }
        bind<BaseRepository<Jump>>() with singleton { BaseRepository<Jump>(instance(), instance()) }

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
