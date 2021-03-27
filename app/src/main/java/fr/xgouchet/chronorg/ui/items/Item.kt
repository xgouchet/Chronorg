package fr.xgouchet.chronorg.ui.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class Item {

    data class Index(
            val section: Short,
            val positionInSection: Int
    )

    abstract class ViewModel {
        abstract fun type(): Type

        abstract fun index(): Index

        abstract fun data(): Any?

        val stableId: Long by lazy {
            val index = index()
            val typeL = type().ordinal.toLong().shl(48)
            val sectionL = index.section.toLong().shl(32)
            typeL or sectionL or index.positionInSection.toLong()
        }
    }

    abstract class ViewHolder<VM : ViewModel>(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        protected lateinit var boundItem: VM

        @Suppress("UNCHECKED_CAST")
        fun bind(item: ViewModel) {
            val cast = item as? VM ?: return
            boundItem = cast
            onBind(cast)
        }

        abstract fun onBind(item: VM)
    }

    interface ViewHolderInflater<VH : ViewHolder<*>> {
        fun inflate(inflater: LayoutInflater,
                    parent: ViewGroup,
                    listener: (Event) -> Unit): VH
    }

    data class Event(
            val viewModel: ViewModel,
            val action: Action,
            val value: Any?
    )

    enum class Action {
        ITEM_TAPPED,
        PRIMARY_ACTION,
        SECONDARY_ACTION,
        TERTIARY_ACTION,
        VALUE_CHANGED,
        VALUE_VALIDATED
    }

    enum class Type(val inflater: ViewHolderInflater<*>) {

        EMPTY(ItemEmpty.ViewHolderInflater()),
        PROJECT(ItemProject.ViewHolderInflater()),
        ENTITY(ItemEntity.ViewHolderInflater()),
        PORTAL(ItemPortal.ViewHolderInflater()),
        DETAILS(ItemDetail.ViewHolderInflater()),


        TEXT_INPUT(ItemTextInput.ViewHolderInflater()),
        RAW_INPUT(ItemRawInput.ViewHolderInflater())

        ;

    }
}
