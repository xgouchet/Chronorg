package fr.xgouchet.khronorg.provider.db

/**
 * @author Xavier Gouchet
 */
enum class ColumnType(val keyword: String) {
    TYPE_INTEGER("INTEGER"),
    TYPE_TEXT("TEXT"),
    TYPE_NULL("NULL"),
    TYPE_REAL("REAL"),
    TYPE_BLOB("BLOB")
}