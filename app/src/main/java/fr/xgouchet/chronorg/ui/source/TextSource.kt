package fr.xgouchet.chronorg.ui.source

import android.content.Context
import android.os.Build
import android.text.Html
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StringRes


sealed class TextSource {

    open fun setText(target: TextView) {
        target.text = getText(target.context)
    }

    open fun setHint(target: EditText) {
        target.hint = getText(target.context)
    }

    abstract fun getText(context: Context): CharSequence

    // region Operators / Utils
    operator fun plus(otherSource: TextSource): TextSource {
        return ConcatSource(this, otherSource)
    }

    fun asHtml(): TextSource {
        return if (this is HtmlSource) this else HtmlSource(this)
    }

    // endregion

    // region Implementations

    data class Raw(val text: CharSequence)
        : TextSource() {

        override fun getText(context: Context): CharSequence {
            return text
        }

    }

    data class Resource(@StringRes val id: Int)
        : TextSource() {

        override fun getText(context: Context): CharSequence {
            return context.getString(id)
        }
    }

    data class FormattedResource(@StringRes val id: Int,
                                 val params: Array<out Any>)
        : TextSource() {

        override fun getText(context: Context): CharSequence {
            return context.getString(id, *params)
        }
    }

    data class HtmlSource(private val delegate: TextSource) : TextSource() {
        override fun getText(context: Context): CharSequence {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(delegate.getText(context).toString(), 0)
            } else {
                @Suppress("DEPRECATION")
                Html.fromHtml(delegate.getText(context).toString())
            }
        }
    }

    data class ConcatSource(
            private val delegateA: TextSource,
            private val delegateB: TextSource) : TextSource() {

        override fun getText(context: Context): CharSequence {
            return delegateA.getText(context).toString() + delegateB.getText(context).toString()
        }
    }

    // endregion
}

// region Extensions

fun String.asTextSource(): TextSource {
    return TextSource.Raw(this)
}

fun Int.asTextSource(params: Array<out Any>): TextSource {
    return TextSource.FormattedResource(this, params)
}

fun Int.asTextSource(): TextSource {
    return TextSource.Resource(this)
}

// endregion
