package fr.xgouchet.khronorg.feature.events

import android.content.Intent
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.editor.BaseEditorAktivity
import fr.xgouchet.khronorg.feature.editor.BaseEditorPresenter
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.feature.events.EventEditorPresenter
import fr.xgouchet.khronorg.feature.events.EventNavigator
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.feature.events.EventNavigator.Companion.EXTRA_EVENT

/**
 * @author Xavier F. Gouchet
 */
class EventEditorAktivity : BaseEditorAktivity<Event>() {


    override fun readItem(intent: Intent?): Event? {
        intent?.let {
            if (it.hasExtra(EXTRA_EVENT)) {
                return it.getParcelableExtra(EXTRA_EVENT)
            }
        }
        return null
    }

    override fun instantiatePresenter(item: Event?): BaseEditorPresenter<Event> {
        val traveller = item ?: travellerInCurrentProject()
        val repository = kodein.instance<BaseRepository<Event>>()
        val navigator = EventNavigator(this)
        return EventEditorPresenter(traveller, repository, navigator, item != null)
    }

    private fun travellerInCurrentProject(): Event {
        val project = kodein.instance<BaseRepository<Project>>().getCurrent()
        val event = Event()
        event.projectId = project.id
        return event
    }

}