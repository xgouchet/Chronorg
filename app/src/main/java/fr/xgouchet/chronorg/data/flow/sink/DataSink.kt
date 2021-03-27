package fr.xgouchet.chronorg.data.flow.sink

interface DataSink<T> {

    suspend fun create(data: T): Long

    suspend fun update(data: T): Boolean

    suspend fun delete(data: T): Boolean
}
