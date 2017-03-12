package fr.xgouchet.khronorg.data.query


/**
 * @author Xavier F. Gouchet
 */
class QueryAlterer private constructor(val select: String?, val args: Array<out String?>?, val order: String?)
    : Query {

    companion object {
        fun create(init: Builder.() -> Unit) = Builder(init).build()

        fun where(init: WhereBuilder.() -> Unit) = WhereBuilder(init).build()
    }

    override fun select(): String? = select
    override fun args(): Array<out String?>? = args
    override fun order(): String? = order


    // region Where

    private constructor(builder: Query) : this(builder.select(), builder.args(), builder.order())

    class WhereBuilder private constructor() : Query {

        private var clause: String? = null
        private var value: String? = null
        private var order: String? = null

        constructor(init: WhereBuilder.() -> Unit) : this() {
            init()
        }

        fun equals(clause: String?, value: String?)
                = apply {
            this.clause = clause
            this.value = value
        }

        fun orderAsc(column: String) {
            this.order = "$column ASC"
        }

        override fun select(): String? = "$clause = ?"

        override fun args(): Array<out String?>? = arrayOf(value)

        override fun order(): String? = order
        fun build() = QueryAlterer(this)
    }

    // endregion

    private constructor(builder: Builder) : this(builder.select, builder.args, builder.order)

    class Builder private constructor() {
        constructor(init: Builder.() -> Unit) : this() {
            init()
        }

        var select: String? = null
        var args: Array<String>? = null
        var order: String? = null

        fun select(init: Builder.() -> String?) = apply { select = init() }
        fun args(init: Builder.() -> Array<String>?) = apply { args = init() }
        fun order(init: Builder.() -> String?) = apply { order = init() }

        fun build() = QueryAlterer(this)
    }
}