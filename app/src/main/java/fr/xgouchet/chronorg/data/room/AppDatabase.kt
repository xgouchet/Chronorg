package fr.xgouchet.chronorg.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.xgouchet.chronorg.data.room.dao.ProjectDao
import fr.xgouchet.chronorg.data.room.model.RoomProject

@Database(
        entities = [
            RoomProject::class
        ],
        version = AppDatabase.V1_BASE,
        exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun projectDao(): ProjectDao

    companion object {
        private const val DB_NAME = "AppDatabase"

        private var INSTANCE: AppDatabase? = null

        const val V1_BASE = 1

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        fun destroyInstance() {
            synchronized(this) {
                INSTANCE = null
            }
        }

        private fun buildDatabase(context: Context) =
                Room
                        .databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                DB_NAME
                        )
//                        .addMigrations(MIGRATION_1_2) TODO
                        .build()
    }
}
