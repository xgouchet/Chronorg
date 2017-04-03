package fr.xgouchet.khronorg

import android.app.Application
import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import fr.xgouchet.khronorg.commons.ioproviders.IOProvider
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.feature.events.EventProvider
import fr.xgouchet.khronorg.feature.jumps.Jump
import fr.xgouchet.khronorg.feature.jumps.JumpProvider
import fr.xgouchet.khronorg.feature.jumps.JumpRepository
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.feature.projects.ProjectProvider
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.feature.travellers.TravellerProvider
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
        bind<JumpRepository>() with singleton { JumpRepository(instance(), instance()) }
        bind<BaseRepository<Jump>>() with singleton {
            val jumpRepo: JumpRepository = instance()
            return@singleton jumpRepo
        }

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
