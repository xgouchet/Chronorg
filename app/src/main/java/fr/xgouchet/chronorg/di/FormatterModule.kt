package fr.xgouchet.chronorg.di

import fr.xgouchet.chronorg.ui.formatter.Formatter
import fr.xgouchet.chronorg.ui.formatter.InstantFormatter
import fr.xgouchet.chronorg.ui.formatter.IntervalFormatter
import org.joda.time.Instant
import org.joda.time.Interval
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

val FormatterModule = Kodein.Module(name = "Formatter") {
    bind<Formatter<Instant>>() with provider { InstantFormatter() }
    bind<Formatter<Interval>>() with provider { IntervalFormatter() }
}
