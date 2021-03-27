package fr.xgouchet.chronorg.ui.transformer

import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.data.flow.model.Portal
import fr.xgouchet.chronorg.data.flow.model.symbol
import fr.xgouchet.chronorg.ui.formatter.Formatter
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.items.ItemCard
import fr.xgouchet.chronorg.ui.items.ItemEmpty
import fr.xgouchet.chronorg.ui.source.asImageSource
import fr.xgouchet.chronorg.ui.source.asTextSource
import org.joda.time.Interval

class PortalListTransformer(
    private val intervalFormatter: Formatter<Interval>
) : PrincipledTransformer<Portal>() {

    override fun empty(): Collection<Item.ViewModel> {
        return listOf(
            ItemEmpty.ViewModel(
                icon = R.drawable.ic_portal_empty.asImageSource(),
                title = "Portals".asTextSource(),
                subtitle = "It seems there are no portals yet.".asTextSource()
            )
        )
    }

    override fun transformItem(index: Int, item: Portal): Collection<Item.ViewModel> {
        val delay = intervalFormatter.format(item.delay)
        val direction = item.direction.symbol()

        return listOf(
            ItemCard.ViewModel(
                index = Item.Index(1, index),
                icon = R.drawable.ic_portal.asImageSource(),
                title = item.name.asTextSource(),
                subtitle = "$direction $delay".asTextSource(),
                description = item.notes.asTextSource(),
                data = item
            )
        )
    }
}


