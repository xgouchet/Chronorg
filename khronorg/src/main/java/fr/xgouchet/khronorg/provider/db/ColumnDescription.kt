package fr.xgouchet.khronorg.provider.db


/**
 * @author Xavier Gouchet
 */
class ColumnDescription(val name: String,
                        val type: ColumnType,
                        val since: Int,
                        vararg options: ColumnOption) {


    val isPrimaryKey: Boolean
    val isAutoIncrement: Boolean
    val isNotNull: Boolean
    val isUnique: Boolean

    constructor(name: String, type: ColumnType, vararg options: ColumnOption) : this(name, type, 0, *options)

    init {

        // read options
        var primaryKey = false
        var autoIncrement = false
        var notNull = false
        var unique = false
        for (option in options) {
            if (ColumnOption.PRIMARY_KEY == option) {
                primaryKey = true
            }
            if (ColumnOption.AUTOINCREMENT == option && ColumnType.TYPE_INTEGER == type) {
                autoIncrement = true
            }
            if (ColumnOption.NOT_NULL == option && ColumnType.TYPE_NULL != type) {
                notNull = true
            }
            if (ColumnOption.UNIQUE == option) {
                unique = true
            }
        }

        // set fields
        this.isPrimaryKey = primaryKey
        this.isAutoIncrement = autoIncrement
        this.isNotNull = notNull
        this.isUnique = unique
    }

    val definition: String
        get() {
            val builder = StringBuilder()

            builder.append(name)
            builder.append(SQLiteDescription.BLANK)
            builder.append(type.keyword)

            if (isPrimaryKey) {
                builder.append(SQLiteDescription.BLANK)
                builder.append(ColumnOption.PRIMARY_KEY.keyword)
            }

            if (isAutoIncrement) {
                builder.append(SQLiteDescription.BLANK)
                builder.append(ColumnOption.AUTOINCREMENT.keyword)
            }

            if (isNotNull) {
                builder.append(SQLiteDescription.BLANK)
                builder.append(ColumnOption.NOT_NULL.keyword)
            }

            if (isUnique) {
                builder.append(SQLiteDescription.BLANK)
                builder.append(ColumnOption.UNIQUE.keyword)
            }

            return builder.toString()
        }

}
