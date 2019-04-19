package fr.xgouchet.chronorg.data.flow.sink

interface DataSink<T> {

    suspend fun create(entity: T): Boolean

    suspend fun update(entity: T): Boolean

    suspend fun delete(id: Long): Boolean
}
