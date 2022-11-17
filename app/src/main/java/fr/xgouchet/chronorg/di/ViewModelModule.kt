package fr.xgouchet.chronorg.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.xgouchet.chronorg.feature.entity.editor.EntityEditorViewModel
import fr.xgouchet.chronorg.feature.entity.list.EntityListViewModel
import fr.xgouchet.chronorg.feature.entity.orphans.OrphanEntityListViewModel
import fr.xgouchet.chronorg.feature.entity.timeline.EntityTimelineViewModel
import fr.xgouchet.chronorg.feature.event.editor.EventEditorViewModel
import fr.xgouchet.chronorg.feature.event.list.EventListViewModel
import fr.xgouchet.chronorg.feature.event.orphans.OrphanEventListViewModel
import fr.xgouchet.chronorg.feature.jump.editor.JumpEditorViewModel
import fr.xgouchet.chronorg.feature.portal.editor.PortalEditorViewModel
import fr.xgouchet.chronorg.feature.portal.list.PortalListViewModel
import fr.xgouchet.chronorg.feature.portal.orphans.OrphanPortalListViewModel
import fr.xgouchet.chronorg.feature.project.editor.ProjectEditorViewModel
import fr.xgouchet.chronorg.feature.project.list.ProjectsListViewModel
import fr.xgouchet.chronorg.feature.project.preview.ProjectPreviewViewModel
import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.instanceOrNull
import org.kodein.di.generic.provider

val ViewModelModule = Kodein.Module(name = "ViewModel") {

    // region Projects

    bindViewModel<ProjectsListViewModel>() with provider {
        ProjectsListViewModel(instance(), instance(), instance(), instance(), instance())
    }
    bindViewModel<ProjectEditorViewModel>() with provider {
        ProjectEditorViewModel(instance())
    }
    bindViewModel<ProjectPreviewViewModel>() with provider {
        ProjectPreviewViewModel(instance(), instance(), instance())
    }

    // endregion

    // region Entities

    bindViewModel<EntityListViewModel>() with provider {
        EntityListViewModel(instance(), instance())
    }
    bindViewModel<EntityEditorViewModel>() with provider {
        EntityEditorViewModel(instance(), instance())
    }
    bindViewModel<EntityTimelineViewModel>() with provider {
        EntityTimelineViewModel(instance(), instance(), instance(), instance(), instance())
    }
    bindViewModel<OrphanEntityListViewModel>() with provider {
        OrphanEntityListViewModel(instance(), instance())
    }

    // endregion

    // region Portals

    bindViewModel<PortalListViewModel>() with provider {
        PortalListViewModel(instance(), instance())
    }
    bindViewModel<PortalEditorViewModel>() with provider {
        PortalEditorViewModel(instance(), instance())
    }
    bindViewModel<OrphanPortalListViewModel>() with provider {
        OrphanPortalListViewModel(instance(), instance())
    }

    // endregion

    // region Events

    bindViewModel<EventListViewModel>() with provider {
        EventListViewModel(instance(), instance())
    }
    bindViewModel<EventEditorViewModel>() with provider {
        EventEditorViewModel(instance(), instance())
    }
    bindViewModel<OrphanEventListViewModel>() with provider {
        OrphanEventListViewModel(instance(), instance())
    }

    // endregion

    // region Jumps

    // bindViewModel<JumpListViewModel>() with provider {
    //     JumpListViewModel(instance(), instance())
    // }
    bindViewModel<JumpEditorViewModel>() with provider {
        JumpEditorViewModel(instance(), instance())
    }
    // bindViewModel<OrphanJumpListViewModel>() with provider {
    //     OrphanJumpListViewModel(instance(), instance())
    // }

    // endregion

}

inline fun <reified T : Any> Kodein.Builder.bindViewModel()
        : Kodein.Builder.TypeBinder<ViewModel> {
    return bind<ViewModel>(tag = T::class.java.simpleName)
}


class ViewModelFactory(private val injector: DKodein) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return injector.instanceOrNull<ViewModel>(tag = modelClass.simpleName) as T?
                ?: modelClass.newInstance()
    }
}
