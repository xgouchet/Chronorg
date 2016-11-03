package fr.xgouchet.chronorg.inject.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import fr.xgouchet.chronorg.data.ioproviders.EntityIOProvider;
import fr.xgouchet.chronorg.data.ioproviders.EventIOProvider;
import fr.xgouchet.chronorg.data.ioproviders.JumpIOProvider;
import fr.xgouchet.chronorg.data.ioproviders.PortalIOProvider;
import fr.xgouchet.chronorg.data.ioproviders.ProjectIOProvider;
import fr.xgouchet.chronorg.data.ioproviders.TimelineIOProvider;
import fr.xgouchet.chronorg.data.queriers.JumpContentQuerier;
import fr.xgouchet.chronorg.data.queriers.PortalContentQuerier;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.data.repositories.EventRepository;
import fr.xgouchet.chronorg.data.repositories.JumpRepository;
import fr.xgouchet.chronorg.data.repositories.PortalRepository;
import fr.xgouchet.chronorg.data.repositories.ProjectRepository;
import fr.xgouchet.chronorg.data.repositories.TimelineRepository;
import fr.xgouchet.chronorg.inject.annotations.ApplicationContext;
import fr.xgouchet.chronorg.inject.annotations.ApplicationScope;

/**
 * @author Xavier Gouchet
 */
@Module
public class RepositoryModule {

    @Provides
    @ApplicationScope
    public ProjectIOProvider provideProjectIOProvider() {
        return new ProjectIOProvider();
    }

    @Provides
    @ApplicationScope
    public ProjectRepository provideProjectRepository(@ApplicationContext Context context,
                                                      ProjectIOProvider provider) {
        return new ProjectRepository(context, provider);
    }

    @Provides
    @ApplicationScope
    public EntityIOProvider provideEntityIOProvider(JumpIOProvider jumpIOProvider) {
        return new EntityIOProvider((JumpContentQuerier) jumpIOProvider.provideQuerier());
    }

    @Provides
    @ApplicationScope
    public EntityRepository provideEntityRepository(@ApplicationContext Context context,
                                                    EntityIOProvider provider) {
        return new EntityRepository(context, provider);
    }

    @Provides
    @ApplicationScope
    public JumpIOProvider provideJumpIOProvider() {
        return new JumpIOProvider();
    }

    @Provides
    @ApplicationScope
    public JumpRepository provideJumpRepository(@ApplicationContext Context context,
                                                JumpIOProvider provider) {
        return new JumpRepository(context, provider);
    }

    @Provides
    @ApplicationScope
    public EventIOProvider provideEventIOProvider() {
        return new EventIOProvider();
    }

    @Provides
    @ApplicationScope
    public EventRepository provideEventRepository(@ApplicationContext Context context,
                                                  EventIOProvider provider) {
        return new EventRepository(context, provider);
    }

    @Provides
    @ApplicationScope
    public PortalIOProvider providePortalIOProvider() {
        return new PortalIOProvider();
    }

    @Provides
    @ApplicationScope
    public PortalRepository providePortalRepository(@ApplicationContext Context context,
                                                    PortalIOProvider provider) {
        return new PortalRepository(context, provider);
    }


    @Provides
    @ApplicationScope
    public TimelineIOProvider provideTimelineIOProvider(PortalIOProvider portalIOProvider) {
        return new TimelineIOProvider((PortalContentQuerier) portalIOProvider.provideQuerier());
    }

    @Provides
    @ApplicationScope
    public TimelineRepository provideTimelineRepository(@ApplicationContext Context context,
                                                        TimelineIOProvider provider) {
        return new TimelineRepository(context, provider);
    }
}
