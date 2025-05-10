package ru.itis.second_sem.data.database.migrations

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.itis.second_sem.data.database.InceptionDatabase
import java.sql.SQLException

class Migration_1_2 : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        try {
            db.execSQL("DROP TABLE IF EXISTS weatherApi")

            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `weatherApi` " +
                        "(`city` TEXT NOT NULL, " +
                        "`current_temp` TEXT NOT NULL, " +
                        "`forecast` TEXT NOT NULL, " +
                        "`timestamp` INTEGER NOT NULL, " +
                        "PRIMARY KEY(`city`))"
            )

        } catch (ex: Exception) {
            Log.e("DB_LOG", "Error while 1_2 migration: ${ex.message}")
        }
    }
}