package fr.xgouchet.chronorg.inject.components;

import dagger.Component;
import fr.xgouchet.chronorg.inject.annotations.ActivityScope;
import fr.xgouchet.chronorg.inject.modules.PresenterModule;
import fr.xgouchet.chronorg.ui.presenters.DateTimePickerPresenter;
import fr.xgouchet.chronorg.ui.presenters.EntityEditPresenter;
import fr.xgouchet.chronorg.ui.presenters.EntityListPresenter;
import fr.xgouchet.chronorg.ui.presenters.ProjectDetailsPresenter;
import fr.xgouchet.chronorg.ui.presenters.ProjectEditPresenter;
import fr.xgouchet.chronorg.ui.presenters.ProjectListPresenter;

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

    EntityEditPresenter getEntityEditPresenter();

    DateTimePickerPresenter getDateTimePickerPresenter();
}
