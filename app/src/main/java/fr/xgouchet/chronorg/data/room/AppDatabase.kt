package fr.xgouchet.chronorg.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.xgouchet.chronorg.data.flow.model.Jump
import fr.xgouchet.chronorg.data.room.dao.EntityDao
import fr.xgouchet.chronorg.data.room.dao.EventDao
import fr.xgouchet.chronorg.data.room.dao.JumpDao
import fr.xgouchet.chronorg.data.room.dao.PortalDao
import fr.xgouchet.chronorg.data.room.dao.ProjectDao
import fr.xgouchet.chronorg.data.room.model.RoomEntity
import fr.xgouchet.chronorg.data.room.model.RoomEvent
import fr.xgouchet.chronorg.data.room.model.RoomJump
import fr.xgouchet.chronorg.data.room.model.RoomPortal
import fr.xgouchet.chronorg.data.room.model.RoomProject

@Database(
    entities = [
        RoomProject::class,
        RoomEntity::class,
        RoomEvent::class,
        RoomPortal::class,
        RoomJump::class
    ],
    version = AppDatabase.V1_BASE,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun projectDao(): ProjectDao
    abstract fun entityDao(): EntityDao
    abstract fun portalDao(): PortalDao 
    abstract fun eventDao(): EventDao
    abstract fun jumpDao(): JumpDao


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
                .fallbackToDestructiveMigration()
//                        .addMigrations(MIGRATION_1_2) TODO
                .build()
    }
}
