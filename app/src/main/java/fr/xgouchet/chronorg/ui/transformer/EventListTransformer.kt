package fr.xgouchet.chronorg.ui.transformer

import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Event
import fr.xgouchet.chronorg.ui.formatter.Formatter
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.items.ItemEmpty
import fr.xgouchet.chronorg.ui.items.ItemCard
import fr.xgouchet.chronorg.ui.source.asImageSource
import fr.xgouchet.chronorg.ui.source.asTextSource
import org.joda.time.Instant

class EventListTransformer(
    private val instantFormatter: Formatter<Instant>
) : PrincipledTransformer<Event>() {

    override fun empty(): Collection<Item.ViewModel> {
        return listOf(
            ItemEmpty.ViewModel(
                icon = R.drawable.ic_event.asImageSource(),
                title = "Events".asTextSource(),
                subtitle = "It seems there are no events yet.".asTextSource()
            )
        )
    }

    override fun transformItem(index: Int, item: Event): Collection<Item.ViewModel> {
        val dateStr = instantFormatter.format(item.date)
        return listOf(
            ItemCard.ViewModel(
                index = Item.Index(1, index),
                icon = R.drawable.ic_event.asImageSource(),
                title = item.name.asTextSource(),
                subtitle = dateStr.asTextSource(),
                description = item.notes.asTextSource(),
                data = item
            )
        )
    }
}


