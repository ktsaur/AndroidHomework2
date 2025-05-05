package ru.itis.second_sem.data.database.migrations

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration_2_3 : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        try {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `queryHistory` (" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`city` TEXT NOT NULL, " +
                        "`timestamp` INTEGER NOT NULL" +
                        ")"
            )
        } catch (ex: Exception) {
            Log.e("DB_LOG", "Error while 1_2 migration: ${ex.message}")
        }
    }
}