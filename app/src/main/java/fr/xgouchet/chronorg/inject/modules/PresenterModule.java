package fr.xgouchet.chronorg.inject.modules;

import org.joda.time.ReadableInstant;

import dagger.Module;
import dagger.Provides;
import fr.xgouchet.chronorg.data.formatters.Formatter;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.data.repositories.EventRepository;
import fr.xgouchet.chronorg.data.repositories.JumpRepository;
import fr.xgouchet.chronorg.data.repositories.PortalRepository;
import fr.xgouchet.chronorg.data.repositories.ProjectRepository;
import fr.xgouchet.chronorg.data.repositories.TimelineRepository;
import fr.xgouchet.chronorg.inject.annotations.ActivityScope;
import fr.xgouchet.chronorg.inject.annotations.ApplicationScope;
import fr.xgouchet.chronorg.ui.presenters.DateTimePickerPresenter;
import fr.xgouchet.chronorg.ui.presenters.EntityDetailsPresenter;
import fr.xgouchet.chronorg.ui.presenters.EntityEditPresenter;
import fr.xgouchet.chronorg.ui.presenters.EntityListPresenter;
import fr.xgouchet.chronorg.ui.presenters.EventEditPresenter;
import fr.xgouchet.chronorg.ui.presenters.EventListPresenter;
import fr.xgouchet.chronorg.ui.presenters.JumpEditPresenter;
import fr.xgouchet.chronorg.ui.presenters.JumpListPresenter;
import fr.xgouchet.chronorg.ui.presenters.PortalEditPresenter;
import fr.xgouchet.chronorg.ui.presenters.PortalListPresenter;
import fr.xgouchet.chronorg.ui.presenters.ProjectDetailsPresenter;
import fr.xgouchet.chronorg.ui.presenters.ProjectEditPresenter;
import fr.xgouchet.chronorg.ui.presenters.ProjectListPresenter;
import fr.xgouchet.chronorg.ui.presenters.ShardListPresenter;
import fr.xgouchet.chronorg.ui.presenters.TimelineListPresenter;
import fr.xgouchet.chronorg.ui.validators.DateTimeInputValidator;

/**
 * @author Xavier Gouchet
 */
@Module
public class PresenterModule {

    @Provides
    @ActivityScope
    public ProjectListPresenter provideProjectListPresenter(ProjectRepository projectRepository) {
        return new ProjectListPresenter(projectRepository);
    }

    @Provides
    @ActivityScope
    public ProjectDetailsPresenter provideProjectDetailsPresenter(ProjectRepository projectRepository) {
        return new ProjectDetailsPresenter(projectRepository);
    }

    @Provides
    @ActivityScope
    public ProjectEditPresenter provideProjectEditPresenter(ProjectRepository projectRepository) {
        return new ProjectEditPresenter(projectRepository);
    }


    @Provides
    @ActivityScope
    public EntityListPresenter provideEntityListPresenter(EntityRepository entityRepository) {
        return new EntityListPresenter(entityRepository);
    }

    @Provides
    @ActivityScope
    public EntityDetailsPresenter provideEntityDetailsPresenter(EntityRepository entityRepository) {
        return new EntityDetailsPresenter(entityRepository);
    }

    @Provides
    @ActivityScope
    public EntityEditPresenter provideEntityEditPresenter(EntityRepository entityRepository) {
        return new EntityEditPresenter(entityRepository);
    }

    @Provides
    @ActivityScope
    public JumpListPresenter provideJumpListPresenter(JumpRepository jumpRepository) {
        return new JumpListPresenter(jumpRepository);
    }

    @Provides
    @ActivityScope
    public JumpEditPresenter provideJumpEditPresenter(JumpRepository jumpRepository) {
        return new JumpEditPresenter(jumpRepository);
    }

    @Provides
    @ActivityScope
    public ShardListPresenter provideShardListPresenter(EntityRepository entityRepository,
                                                       EventRepository eventRepository) {
        return new ShardListPresenter(entityRepository, eventRepository);
    }

    @Provides
    @ActivityScope
    public EventListPresenter provideEventListPresenter(EventRepository eventRepository) {
        return new EventListPresenter(eventRepository);
    }

    @Provides
    @ActivityScope
    public EventEditPresenter provideEventEditPresenter(EventRepository eventRepository) {
        return new EventEditPresenter(eventRepository);
    }

    @Provides
    @ActivityScope
    public PortalListPresenter providePortalListPresenter(PortalRepository portalRepository) {
        return new PortalListPresenter(portalRepository);
    }

    @Provides
    @ActivityScope
    public PortalEditPresenter providePortalEditPresenter(PortalRepository portalRepository,
                                                          Formatter<ReadableInstant> formatter) {
        return new PortalEditPresenter(portalRepository, formatter);
    }

    @Provides
    @ActivityScope
    public TimelineListPresenter provideTimelineListPresenter(TimelineRepository timelineRepository) {
        return new TimelineListPresenter(timelineRepository);
    }


    @Provides
    @ActivityScope
    public DateTimePickerPresenter provideDateTimePickerPresenter(@ApplicationScope DateTimeInputValidator validator) {
        return new DateTimePickerPresenter(validator);
    }

}
