package fr.xgouchet.chronorg.inject.modules;

import org.joda.time.Duration;
import org.joda.time.ReadableInstant;

import dagger.Module;
import dagger.Provides;
import fr.xgouchet.chronorg.data.formatters.DurationFormatter;
import fr.xgouchet.chronorg.data.formatters.Formatter;
import fr.xgouchet.chronorg.data.formatters.ReadableInstantFormatter;
import fr.xgouchet.chronorg.inject.annotations.ApplicationScope;

/**
 * @author Xavier Gouchet
 */
@Module
public class FormatterModule {

    @Provides
    @ApplicationScope
    public Formatter<ReadableInstant> provideReadableInstantFormatter() {
        return new ReadableInstantFormatter();
    }

    @Provides
    @ApplicationScope
    public Formatter<Duration> provideDurationFormatter() {
        return new DurationFormatter();
    }

}
