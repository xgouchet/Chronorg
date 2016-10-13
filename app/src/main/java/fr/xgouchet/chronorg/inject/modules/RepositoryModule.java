package fr.xgouchet.chronorg.inject.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import fr.xgouchet.chronorg.data.ioproviders.EntityIOProvider;
import fr.xgouchet.chronorg.data.ioproviders.JumpIOProvider;
import fr.xgouchet.chronorg.data.ioproviders.ProjectIOProvider;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.data.repositories.JumpRepository;
import fr.xgouchet.chronorg.data.repositories.ProjectRepository;
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
    public EntityIOProvider provideEntityIOProvider() {
        return new EntityIOProvider();
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
}
