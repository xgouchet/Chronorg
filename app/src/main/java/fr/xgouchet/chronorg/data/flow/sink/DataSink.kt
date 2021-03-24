package fr.xgouchet.chronorg.data.flow.sink

interface DataSink<T> {

    suspend fun create(entity: T): Long

    suspend fun update(entity: T): Boolean

    suspend fun delete(entity: T): Boolean
}
