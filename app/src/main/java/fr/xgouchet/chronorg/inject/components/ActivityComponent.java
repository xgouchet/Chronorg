package fr.xgouchet.chronorg.inject.components;

import dagger.Component;
import fr.xgouchet.chronorg.inject.annotations.ActivityScope;
import fr.xgouchet.chronorg.inject.modules.PresenterModule;
import fr.xgouchet.chronorg.ui.presenters.DateTimePickerPresenter;
import fr.xgouchet.chronorg.ui.presenters.EntityDetailsPresenter;
import fr.xgouchet.chronorg.ui.presenters.EntityEditPresenter;
import fr.xgouchet.chronorg.ui.presenters.EntityListPresenter;
import fr.xgouchet.chronorg.ui.presenters.JumpEditPresenter;
import fr.xgouchet.chronorg.ui.presenters.JumpListPresenter;
import fr.xgouchet.chronorg.ui.presenters.ProjectDetailsPresenter;
import fr.xgouchet.chronorg.ui.presenters.ProjectEditPresenter;
import fr.xgouchet.chronorg.ui.presenters.ProjectListPresenter;
import fr.xgouchet.chronorg.ui.presenters.TimelinePresenter;

/**
 * @author Xavier Gouchet
 */
@Component(dependencies = ChronorgComponent.class, modules = {PresenterModule.class})
@ActivityScope
public interface ActivityComponent {

    ProjectListPresenter getProjectListPresenter();

    ProjectDetailsPresenter getProjectDetailsPresenter();

    ProjectEditPresenter getProjectEditPresenter();

    EntityListPresenter getEntityListPresenter();

    EntityDetailsPresenter getEntityDetailsPresenter();

    EntityEditPresenter getEntityEditPresenter();

    JumpListPresenter getJumpListPresenter();

    JumpEditPresenter getJumpEditPresenter();

    TimelinePresenter getTimelinePresenter();

    DateTimePickerPresenter getDateTimePickerPresenter();
}
