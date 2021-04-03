package fr.xgouchet.chronorg.ui.transformer

import fr.xgouchet.chronorg.data.flow.model.Jump
import fr.xgouchet.chronorg.ui.formatter.Formatter
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.items.ItemJump
import fr.xgouchet.chronorg.ui.source.asTextSource
import org.joda.time.Instant

class JumpListTransformer(
    private val instantFormatter: Formatter<Instant>
) : PrincipledTransformer<Jump>() {

    override fun empty(): Collection<Item.ViewModel> {
        return emptyList()
    }

    override fun transformItem(index: Int, item: Jump): Collection<Item.ViewModel> {
        val fromStr = instantFormatter.format(item.from)
        val toStr = instantFormatter.format(item.to)
        return listOf(
            ItemJump.ViewModel(
                index = Item.Index(1, index),
                title = item.name.asTextSource(),
                from = fromStr.asTextSource(),
                to = toStr.asTextSource(),
                data = item
            )
        )
    }
}


