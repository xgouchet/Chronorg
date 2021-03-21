package fr.xgouchet.chronorg.data.flow.source

interface DataSource<T : Any> {

    suspend fun get(id: Long): T?

    suspend fun getAll(): List<T>

    suspend fun getAllInParent(parentId: Long): List<T>
}
