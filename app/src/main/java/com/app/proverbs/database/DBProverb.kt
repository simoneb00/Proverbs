package com.app.proverbs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.proverbs.model.Proverb

@Database(entities = [Proverb::class], version = 1)
abstract class DBProverb: RoomDatabase() {
    companion object {
        private var db: DBProverb? = null
        fun getInstance(context: Context): DBProverb {
            if (db == null)
                db = Room.databaseBuilder(
                    context.applicationContext,
                    DBProverb::class.java, "p2.db")
                    .createFromAsset("p2.db")
                    .build()
            return db as DBProverb
        }
    }

    abstract fun proverbiDAO(): DaoProverb
}