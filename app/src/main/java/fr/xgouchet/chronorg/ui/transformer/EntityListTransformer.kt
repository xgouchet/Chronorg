package fr.xgouchet.chronorg.ui.transformer

import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.ui.formatter.Formatter
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.items.ItemEmpty
import fr.xgouchet.chronorg.ui.items.ItemCard
import fr.xgouchet.chronorg.ui.source.asImageSource
import fr.xgouchet.chronorg.ui.source.asTextSource
import org.joda.time.Instant

class EntityListTransformer(
    private val instantFormatter: Formatter<Instant>
) : PrincipledTransformer<Entity>() {

    override fun empty(): Collection<Item.ViewModel> {
        return listOf(
            ItemEmpty.ViewModel(
                icon = R.drawable.ic_entity_empty.asImageSource(),
                title = "Entities".asTextSource(),
                subtitle = "It seems there are no entities yet.".asTextSource()
            )
        )
    }

    override fun transformItem(index: Int, item: Entity): Collection<Item.ViewModel> {
        val birthStr = instantFormatter.format(item.birth)
        val deathStr = instantFormatter.format(item.death)
        return listOf(
            ItemCard.ViewModel(
                index = Item.Index(1, index),
                icon = R.drawable.ic_entity.asImageSource(),
                title = item.name.asTextSource(),
                subtitle = "* $birthStr\n† $deathStr".asTextSource(),
                description = item.notes.asTextSource(),
                data = item
            )
        )
    }
}


