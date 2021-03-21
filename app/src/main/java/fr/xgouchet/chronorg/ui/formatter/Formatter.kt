package fr.xgouchet.chronorg.ui.formatter

interface Formatter<T> {
    fun format(data: T): String
}