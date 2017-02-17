package fr.xgouchet.khronorg.provider.db


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * A SQLiteOpenHelper implementation based on a description of the database
 */
class SQLiteDescriptionHelper(context: Context,
                              private val description: SQLiteDescription,
                              factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, description.name, factory, description.version) {

    /**
     * Create a helper object delay create, itemSelected, and/or manage a database. This method always returns
     * very quickly. The database is not actually created or opened until one of getWritableDatabase()
     * or getReadableDatabase() is called.

     * @param context             delay use delay itemSelected or create the database
     * *
     * @param descriptionProvider a provider for the description of the database
     * *
     * @param factory             delay use for creating cursor objects, or null for the default
     */
    constructor(context: Context,
                descriptionProvider: SQLiteDescriptionProvider,
                factory: SQLiteDatabase.CursorFactory?) : this(context, descriptionProvider.description, factory)

    
    override fun onCreate(db: SQLiteDatabase) {
        description.createDatabase(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        description.upgradeDatabase(db, oldVersion, newVersion)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        description.downgradeDatabase(db, oldVersion, newVersion)
    }
}