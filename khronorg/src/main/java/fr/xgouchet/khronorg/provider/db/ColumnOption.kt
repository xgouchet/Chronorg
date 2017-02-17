package fr.xgouchet.khronorg.provider.db

/**
 * @author Xavier Gouchet
 */
enum class ColumnOption(val keyword: String) {
    PRIMARY_KEY("PRIMARY KEY"),
    AUTOINCREMENT("AUTOINCREMENT"),
    NOT_NULL("NOT NULL"),
    UNIQUE("UNIQUE")
}