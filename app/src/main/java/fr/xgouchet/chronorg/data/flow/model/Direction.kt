package fr.xgouchet.chronorg.data.flow.model

typealias Direction = Int

const val DIRECTION_NONE = 0
const val DIRECTION_FUTURE = 1
const val DIRECTION_PAST = 2
const val DIRECTION_BOTH = DIRECTION_FUTURE or DIRECTION_PAST

fun Direction.symbol(): String {
    return when (this) {
        DIRECTION_BOTH -> "↔"
        DIRECTION_FUTURE -> "→"
        DIRECTION_PAST -> "←"
        DIRECTION_NONE -> "∅"
        else -> "?"
    }
}
fun Direction.name(): String {
    return when (this) {
        DIRECTION_BOTH -> "Future & Past"
        DIRECTION_FUTURE -> "Future"
        DIRECTION_PAST -> "Past"
        DIRECTION_NONE -> "None"
        else -> "?"
    }
}